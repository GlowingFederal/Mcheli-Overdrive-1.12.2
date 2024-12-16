package mcheli.tank;

import java.util.List;
import java.util.Random;
import mcheli.MCH_Config;
import mcheli.MCH_Lib;
import mcheli.MCH_MOD;
import mcheli.aircraft.MCH_AircraftInfo;
import mcheli.aircraft.MCH_EntityAircraft;
import mcheli.particles.MCH_ParticlesUtil;
import mcheli.tank.MCH_EntityWheel;
import mcheli.wrapper.W_Block;
import mcheli.wrapper.W_Lib;
import mcheli.wrapper.W_WorldFunc;
import net.minecraft.block.Block;
import net.minecraft.entity.MoverType;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class MCH_WheelManager {

  public final MCH_EntityAircraft parent;
  public MCH_EntityWheel[] wheels;
  private double minZ;
  private double maxZ;
  private double avgZ;
  public Vec3d weightedCenter;
  public float targetPitch;
  public float targetRoll;
  public float prevYaw;
  private static Random rand = new Random();


  public MCH_WheelManager(MCH_EntityAircraft ac) {
    this.parent = ac;
    this.wheels = new MCH_EntityWheel[0];
    this.weightedCenter = Vec3d.ZERO;
    //createVectorHelper(0.0D, 0.0D, 0.0D);
  }

  public void createWheels(World w, List list, Vec3d weightedCenter) {
    this.wheels = new MCH_EntityWheel[list.size() * 2];
    this.minZ = 999999.0D;
    this.maxZ = -999999.0D;
    this.weightedCenter = weightedCenter;

    for(int i = 0; i < this.wheels.length; ++i) {
      MCH_EntityWheel wheel = new MCH_EntityWheel(w);
      wheel.setParents(this.parent);
      Vec3d wp = ((MCH_AircraftInfo.Wheel)list.get(i / 2)).pos;
      wheel.setWheelPos(new Vec3d(i % 2 == 0?wp.x:-wp.x, wp.y, wp.z), this.weightedCenter);
      Vec3d v = this.parent.getTransformedPosition(wheel.pos.x, wheel.pos.y, wheel.pos.z);
      //todo blame
      wheel.setLocationAndAngles(v.x, v.y + 1.0D, v.z, 0.0F, 0.0F);
      this.wheels[i] = wheel;
      if(wheel.pos.z <= this.minZ) {
        this.minZ = wheel.pos.z;
      }

      if(wheel.pos.z >= this.maxZ) {
        this.maxZ = wheel.pos.z;
      }
    }

    this.avgZ = this.maxZ - this.minZ;
  }

  public void move(double x, double y, double z) {
    MCH_EntityAircraft ac = this.parent;
    if(ac.getAcInfo() != null) {
      boolean showLog = ac.ticksExisted % 1 == 1;
      if(showLog) {
        MCH_Lib.DbgLog(ac.world, "[" + (ac.world.isRemote?"Client":"Server") + "] ==============================", new Object[0]);
      }

      MCH_EntityWheel[] zmog = this.wheels;
      int rv = zmog.length;

      int wc;
      MCH_EntityWheel pitch;
      for(wc = 0; wc < rv; ++wc) {
        pitch = zmog[wc];
        pitch.prevPosX = pitch.posX;
        pitch.prevPosY = pitch.posY;
        pitch.prevPosZ = pitch.posZ;
        Vec3d roll = ac.getTransformedPosition(pitch.pos.x, pitch.pos.y, pitch.pos.z);
        pitch.motionX = roll.x - pitch.posX + x;
        pitch.motionY = roll.y - pitch.posY;
        pitch.motionZ = roll.z - pitch.posZ + z;
      }

      zmog = this.wheels;
      rv = zmog.length;

      for(wc = 0; wc < rv; ++wc) {
        pitch = zmog[wc];
        pitch.motionY *= 0.15D;
        pitch.move(MoverType.SELF, pitch.motionX, pitch.motionY, pitch.motionZ);
        double var32 = 1.0D;
        pitch.move(MoverType.SELF,0.0D, -0.1D * var32, 0.0D);
      }

      int var28 = -1;

      MCH_EntityWheel var30;
      for(rv = 0; rv < this.wheels.length / 2; ++rv) {
        var28 = rv;
        var30 = this.wheels[rv * 2 + 0];
        pitch = this.wheels[rv * 2 + 1];
        if(!var30.isPlus && (var30.onGround || pitch.onGround)) {
          var28 = -1;
          break;
        }
      }

      if(var28 >= 0) {
        this.wheels[var28 * 2 + 0].onGround = true;
        this.wheels[var28 * 2 + 1].onGround = true;
      }

      var28 = -1;

      for(rv = this.wheels.length / 2 - 1; rv >= 0; --rv) {
        var28 = rv;
        var30 = this.wheels[rv * 2 + 0];
        pitch = this.wheels[rv * 2 + 1];
        if(var30.isPlus && (var30.onGround || pitch.onGround)) {
          var28 = -1;
          break;
        }
      }

      if(var28 >= 0) {
        this.wheels[var28 * 2 + 0].onGround = true;
        this.wheels[var28 * 2 + 1].onGround = true;
      }

      Vec3d var29 = Vec3d.ZERO;

      //Vec3D.createVectorHelper(0.0D, 0.0D, 0.0D);
      Vec3d var31 = ac.getTransformedPosition(this.weightedCenter);
      var31 = new Vec3d(var31.x - ac.posX, this.weightedCenter.y, var31.z - ac.posZ);

      for(int var33 = 0; var33 < this.wheels.length / 2; ++var33) {
        MCH_EntityWheel var34 = this.wheels[var33 * 2 + 0];
        MCH_EntityWheel ogpf = this.wheels[var33 * 2 + 1];
        Vec3d ogrf = new Vec3d(var34.posX - (ac.posX + var31.x), var34.posY - (ac.posY + var31.y), var34.posZ - (ac.posZ + var31.z));
        Vec3d arr$ = new Vec3d(ogpf.posX - (ac.posX + var31.x), ogpf.posY - (ac.posY + var31.y), ogpf.posZ - (ac.posZ + var31.z));
        Vec3d len$ = var34.pos.z >= 0.0D?arr$.crossProduct(ogrf):ogrf.crossProduct(arr$);
        len$ = len$.normalize();
        double i$ = Math.abs(var34.pos.z / this.avgZ);
        if(!var34.onGround && !ogpf.onGround) {
          i$ = 0.0D;
        }

        var29 = var29.add(new Vec3d(len$.x * i$, len$.y * i$, len$.z * i$));
        if(showLog) {
          double yawRadians = Math.toRadians(ac.getRotYaw());
          double cos = Math.cos(yawRadians);
          double sin = Math.sin(yawRadians);
          Vec3d rotatedLen = new Vec3d(
          len$.x * cos - len$.z * sin,
                  len$.y,
                  len$.x * sin + len$.z * cos
          );
          len$ = rotatedLen;
          MCH_Lib.DbgLog(ac.world, "%2d : %.2f :[%+.1f, %+.1f, %+.1f][%s %d %d][%+.2f(%+.2f), %+.2f(%+.2f)][%+.1f, %+.1f, %+.1f]", new Object[]{Integer.valueOf(var33), Double.valueOf(i$), Double.valueOf(len$.x), Double.valueOf(len$.y), Double.valueOf(len$.z), var34.isPlus?"+":"-", Integer.valueOf(var34.onGround?1:0), Integer.valueOf(ogpf.onGround?1:0), Double.valueOf(var34.posY - var34.prevPosY), Double.valueOf(var34.motionY), Double.valueOf(ogpf.posY - ogpf.prevPosY), Double.valueOf(ogpf.motionY), Double.valueOf(len$.x), Double.valueOf(len$.y), Double.valueOf(len$.z)});
        }
      }

      var29 = var29.normalize();
      if(var29.y > 0.01D && var29.y < 0.7D) {
        ac.motionX += var29.x / 50.0D;
        ac.motionZ += var29.z / 50.0D;
      }

      double yawRadians = Math.toRadians(ac.getRotYaw());
      double cos = Math.cos(yawRadians);
      double sin = Math.sin(yawRadians);

      var29 = new Vec3d(
              var29.x * cos - var29.z * sin,
              var29.y,
              var29.x * sin + var29.z * cos
      );
      float var35 = (float)(90.0D - Math.atan2(var29.y, var29.z) * 180.0D / 3.141592653589793D);
      float var36 = -((float)(90.0D - Math.atan2(var29.y, var29.x) * 180.0D / 3.141592653589793D));
      float var37 = ac.getAcInfo().onGroundPitchFactor;
      if(var35 - ac.getRotPitch() > var37) {
        var35 = ac.getRotPitch() + var37;
      }

      if(var35 - ac.getRotPitch() < -var37) {
        var35 = ac.getRotPitch() - var37;
      }

      float var38 = ac.getAcInfo().onGroundRollFactor;
      if(var36 - ac.getRotRoll() > var38) {
        var36 = ac.getRotRoll() + var38;
      }

      if(var36 - ac.getRotRoll() < -var38) {
        var36 = ac.getRotRoll() - var38;
      }

      this.targetPitch = var35;
      this.targetRoll = var36;
      if(!W_Lib.isClientPlayer(ac.getRiddenByEntity())) {
        ac.setRotPitch(var35);
        ac.setRotRoll(var36);
      }

      if(showLog) {
        MCH_Lib.DbgLog(ac.world, "%+03d, %+03d :[%.2f, %.2f, %.2f] yaw=%.2f, pitch=%.2f, roll=%.2f", new Object[]{Integer.valueOf((int)var35), Integer.valueOf((int)var36), Double.valueOf(var29.x), Double.valueOf(var29.y), Double.valueOf(var29.z), Float.valueOf(ac.getRotYaw()), Float.valueOf(this.targetPitch), Float.valueOf(this.targetRoll)});
      }

      MCH_EntityWheel[] var39 = this.wheels;
      int var40 = var39.length;

      for(int var41 = 0; var41 < var40; ++var41) {
        MCH_EntityWheel wheel = var39[var41];
        Vec3d v = this.getTransformedPosition(wheel.pos.x, wheel.pos.y, wheel.pos.z, ac, ac.getRotYaw(), this.targetPitch, this.targetRoll);
        double offset = wheel.onGround?0.01D:-0.0D;
        double rangeH = 2.0D;
        double poy = (double)(wheel.stepHeight / 2.0F);
        int b = 0;
        if(wheel.posX > v.x + rangeH) {
          wheel.posX = v.x + rangeH;
          wheel.posY = v.y + poy;
          b |= 1;
        }

        if(wheel.posX < v.x - rangeH) {
          wheel.posX = v.x - rangeH;
          wheel.posY = v.y + poy;
          b |= 2;
        }

        if(wheel.posZ > v.z + rangeH) {
          wheel.posZ = v.z + rangeH;
          wheel.posY = v.y + poy;
          b |= 4;
        }

        if(wheel.posZ < v.z - rangeH) {
          wheel.posZ = v.z - rangeH;
          wheel.posY = v.y + poy;
          b |= 8;
        }

        wheel.setPositionAndRotation(wheel.posX, wheel.posY, wheel.posZ, 0.0F, 0.0F);
      }

    }
  }

  public Vec3d getTransformedPosition(double x, double y, double z, MCH_EntityAircraft ac, float yaw, float pitch, float roll) {
    Vec3d v = MCH_Lib.RotVec3(x, y, z, -yaw, -pitch, -roll);
    return v.addVector(ac.posX, ac.posY, ac.posZ);
  }

  public void updateBlock() {
    MCH_Config var10000 = MCH_MOD.config;
    if(MCH_Config.Collision_DestroyBlock.prmBool) {
      MCH_EntityAircraft ac = this.parent;
      MCH_EntityWheel[] arr$ = this.wheels;
      int len$ = arr$.length;

      for(int i$ = 0; i$ < len$; ++i$) {
        MCH_EntityWheel w = arr$[i$];
        Vec3d v = ac.getTransformedPosition(w.pos);
        int x = (int)(v.x + 0.5D);
        int y = (int)(v.y + 0.5D);
        int z = (int)(v.z + 0.5D);
        Block block = ac.world.getBlockState(new BlockPos(x, y, z)).getBlock();
        if(block == W_Block.getSnowLayer()) {
          ac.world.setBlockToAir(new BlockPos(x, y, z));
        }

        if(block == Blocks.WATERLILY || block == Blocks.CAKE) {
          W_WorldFunc.destroyBlock(ac.world, x, y, z, false);
        }
      }

    }
  }

  public void particleLandingGear() {
    if(this.wheels.length > 0) {
      MCH_EntityAircraft ac = this.parent;
      double d = ac.motionX * ac.motionX + ac.motionZ * ac.motionZ + (double)Math.abs(this.prevYaw - ac.getRotYaw());
      this.prevYaw = ac.getRotYaw();
      if(d > 0.001D) {
        for(int i = 0; i < 2; ++i) {
          MCH_EntityWheel w = this.wheels[rand.nextInt(this.wheels.length)];
          Vec3d v = ac.getTransformedPosition(w.pos);
          int x = MathHelper.floor(v.x + 0.5D);
          int y = MathHelper.floor(v.y - 0.5D);
          int z = MathHelper.floor(v.z + 0.5D);
          Block block = ac.world.getBlockState(new BlockPos(x, y, z)).getBlock();
          if(Block.isEqualTo(block, Blocks.AIR)) {
            y = MathHelper.floor(v.y + 0.5D);
            block = ac.world.getBlockState(new BlockPos(x, y, z)).getBlock();
          }

          if(!Block.isEqualTo(block, Blocks.AIR)) {
            MCH_ParticlesUtil.spawnParticleTileCrack(ac.world, x, y, z, v.x + ((double)rand.nextFloat() - 0.5D), v.y + 0.1D, v.z + ((double)rand.nextFloat() - 0.5D), -ac.motionX * 4.0D + ((double)rand.nextFloat() - 0.5D) * 0.1D, (double)rand.nextFloat() * 0.5D, -ac.motionZ * 4.0D + ((double)rand.nextFloat() - 0.5D) * 0.1D);
          }
        }
      }

    }
  }

}
