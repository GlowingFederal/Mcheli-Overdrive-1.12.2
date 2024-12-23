package mcheli.tank;

import java.util.List;
import java.util.Random;
import mcheli.MCH_Config;
import mcheli.MCH_Lib;
import mcheli.aircraft.MCH_AircraftInfo;
import mcheli.aircraft.MCH_EntityAircraft;
import mcheli.particles.MCH_ParticlesUtil;
import mcheli.wrapper.W_Block;
import mcheli.wrapper.W_Blocks;
import mcheli.wrapper.W_Lib;
import mcheli.wrapper.W_WorldFunc;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
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
  }
  
  public void createWheels(World w, List<MCH_AircraftInfo.Wheel> list, Vec3d weightedCenter) {
    this.wheels = new MCH_EntityWheel[list.size() * 2];
    this.minZ = 999999.0D;
    this.maxZ = -999999.0D;
    this.weightedCenter = weightedCenter;
    for (int i = 0; i < this.wheels.length; i++) {
      MCH_EntityWheel wheel = new MCH_EntityWheel(w);
      wheel.setParents(this.parent);
      Vec3d wp = ((MCH_AircraftInfo.Wheel)list.get(i / 2)).pos;
      wheel.setWheelPos(new Vec3d((i % 2 == 0) ? wp.x : -wp.x, wp.y, wp.z), this.weightedCenter);
      Vec3d v = this.parent.getTransformedPosition(wheel.pos.x, wheel.pos.y, wheel.pos.z);
      wheel.setLocationAndAngles(v.x, v.y + 1.0D, v.z, 0.0F, 0.0F);
      this.wheels[i] = wheel;
      if (wheel.pos.z <= this.minZ)
        this.minZ = wheel.pos.z; 
      if (wheel.pos.z >= this.maxZ)
        this.maxZ = wheel.pos.z; 
    } 
    this.avgZ = this.maxZ - this.minZ;
  }
  
  public void move(double x, double y, double z) {
    MCH_EntityAircraft ac = this.parent;
    if (ac.getAcInfo() == null)
      return; 
    boolean showLog = (ac.ticksExisted % 1 == 1);
    if (showLog)
      MCH_Lib.DbgLog(ac.world, "[" + (ac.world.isRemote ? "Client" : "Server") + "] ==============================", new Object[0]); 
    for (MCH_EntityWheel wheel : this.wheels) {
      wheel.prevPosX = wheel.posX;
      wheel.prevPosY = wheel.posY;
      wheel.prevPosZ = wheel.posZ;
      Vec3d v = ac.getTransformedPosition(wheel.pos.x, wheel.pos.y, wheel.pos.z);
      wheel.motionX = v.x - wheel.posX + x;
      wheel.motionY = v.y - wheel.posY;
      wheel.motionZ = v.z - wheel.posZ + z;
    } 
    for (MCH_EntityWheel wheel : this.wheels) {
      wheel.motionY *= 0.15D;
      wheel.move(MoverType.SELF, wheel.motionX, wheel.motionY, wheel.motionZ);
      double f = 1.0D;
      wheel.move(MoverType.SELF, 0.0D, -0.1D * f, 0.0D);
    } 
    int zmog = -1;
    int i;
    for (i = 0; i < this.wheels.length / 2; i++) {
      zmog = i;
      MCH_EntityWheel w1 = this.wheels[i * 2 + 0];
      MCH_EntityWheel w2 = this.wheels[i * 2 + 1];
      if (!w1.isPlus)
        if (w1.onGround || w2.onGround) {
          zmog = -1;
          break;
        }  
    } 
    if (zmog >= 0) {
      (this.wheels[zmog * 2 + 0]).onGround = true;
      (this.wheels[zmog * 2 + 1]).onGround = true;
    } 
    zmog = -1;
    for (i = this.wheels.length / 2 - 1; i >= 0; i--) {
      zmog = i;
      MCH_EntityWheel w1 = this.wheels[i * 2 + 0];
      MCH_EntityWheel w2 = this.wheels[i * 2 + 1];
      if (w1.isPlus)
        if (w1.onGround || w2.onGround) {
          zmog = -1;
          break;
        }  
    } 
    if (zmog >= 0) {
      (this.wheels[zmog * 2 + 0]).onGround = true;
      (this.wheels[zmog * 2 + 1]).onGround = true;
    } 
    Vec3d rv = Vec3d.ZERO;
    Vec3d wc = ac.getTransformedPosition(this.weightedCenter);
    wc = new Vec3d(wc.x - ac.posX, this.weightedCenter.y, wc.z - ac.posZ);
    for (int j = 0; j < this.wheels.length / 2; j++) {
      MCH_EntityWheel w1 = this.wheels[j * 2 + 0];
      MCH_EntityWheel w2 = this.wheels[j * 2 + 1];
      Vec3d v1 = new Vec3d(w1.posX - ac.posX + wc.x, w1.posY - ac.posY + wc.y, w1.posZ - ac.posZ + wc.z);
      Vec3d v2 = new Vec3d(w2.posX - ac.posX + wc.x, w2.posY - ac.posY + wc.y, w2.posZ - ac.posZ + wc.z);
      Vec3d v = (w1.pos.z >= 0.0D) ? v2.crossProduct(v1) : v1.crossProduct(v2);
      v = v.normalize();
      double f = Math.abs(w1.pos.z / this.avgZ);
      if (!w1.onGround && !w2.onGround)
        f = 0.0D; 
      rv = rv.addVector(v.x * f, v.y * f, v.z * f);
      if (showLog) {
        v = v.rotateYaw((float)(ac.getRotYaw() * Math.PI / 180.0D));
        MCH_Lib.DbgLog(ac.world, "%2d : %.2f :[%+.1f, %+.1f, %+.1f][%s %d %d][%+.2f(%+.2f), %+.2f(%+.2f)][%+.1f, %+.1f, %+.1f]", new Object[] { 
              Integer.valueOf(j), Double.valueOf(f), Double.valueOf(v.x), Double.valueOf(v.y), 
              Double.valueOf(v.z), w1.isPlus ? "+" : "-", Integer.valueOf(w1.onGround ? 1 : 0), 
              Integer.valueOf(w2.onGround ? 1 : 0), Double.valueOf(w1.posY - w1.prevPosY), 
              Double.valueOf(w1.motionY), 
              Double.valueOf(w2.posY - w2.prevPosY), 
              Double.valueOf(w2.motionY), Double.valueOf(v.x), Double.valueOf(v.y), 
              Double.valueOf(v.z) });
      } 
    } 
    rv = rv.normalize();
    if (rv.y > 0.01D && rv.y < 0.7D) {
      ac.motionX += rv.x / 50.0D;
      ac.motionZ += rv.z / 50.0D;
    } 
    rv = rv.rotateYaw((float)(ac.getRotYaw() * Math.PI / 180.0D));
    float pitch = (float)(90.0D - Math.atan2(rv.y, rv.z) * 180.0D / Math.PI);
    float roll = -((float)(90.0D - Math.atan2(rv.y, rv.x) * 180.0D / Math.PI));
    float ogpf = (ac.getAcInfo()).onGroundPitchFactor;
    if (pitch - ac.getRotPitch() > ogpf)
      pitch = ac.getRotPitch() + ogpf; 
    if (pitch - ac.getRotPitch() < -ogpf)
      pitch = ac.getRotPitch() - ogpf; 
    float ogrf = (ac.getAcInfo()).onGroundRollFactor;
    if (roll - ac.getRotRoll() > ogrf)
      roll = ac.getRotRoll() + ogrf; 
    if (roll - ac.getRotRoll() < -ogrf)
      roll = ac.getRotRoll() - ogrf; 
    this.targetPitch = pitch;
    this.targetRoll = roll;
    if (!W_Lib.isClientPlayer(ac.getRiddenByEntity())) {
      ac.setRotPitch(pitch);
      ac.setRotRoll(roll);
    } 
    if (showLog)
      MCH_Lib.DbgLog(ac.world, "%+03d, %+03d :[%.2f, %.2f, %.2f] yaw=%.2f, pitch=%.2f, roll=%.2f", new Object[] { Integer.valueOf((int)pitch), Integer.valueOf((int)roll), Double.valueOf(rv.x), 
            Double.valueOf(rv.y), Double.valueOf(rv.z), Float.valueOf(ac.getRotYaw()), 
            Float.valueOf(this.targetPitch), Float.valueOf(this.targetRoll) }); 
    for (MCH_EntityWheel wheel : this.wheels) {
      Vec3d v = getTransformedPosition(wheel.pos.x, wheel.pos.y, wheel.pos.z, ac, ac.getRotYaw(), this.targetPitch, this.targetRoll);
      double rangeH = 2.0D;
      double poy = (wheel.stepHeight / 2.0F);
      if (wheel.posX > v.x + rangeH) {
        wheel.posX = v.x + rangeH;
        wheel.posY = v.y + poy;
      } 
      if (wheel.posX < v.x - rangeH) {
        wheel.posX = v.x - rangeH;
        wheel.posY = v.y + poy;
      } 
      if (wheel.posZ > v.z + rangeH) {
        wheel.posZ = v.z + rangeH;
        wheel.posY = v.y + poy;
      } 
      if (wheel.posZ < v.z - rangeH) {
        wheel.posZ = v.z - rangeH;
        wheel.posY = v.y + poy;
      } 
      wheel.setPositionAndRotation(wheel.posX, wheel.posY, wheel.posZ, 0.0F, 0.0F);
    } 
  }
  
  public Vec3d getTransformedPosition(double x, double y, double z, MCH_EntityAircraft ac, float yaw, float pitch, float roll) {
    Vec3d v = MCH_Lib.RotVec3(x, y, z, -yaw, -pitch, -roll);
    return v.addVector(ac.posX, ac.posY, ac.posZ);
  }
  
  public void updateBlock() {
    if (!MCH_Config.Collision_DestroyBlock.prmBool)
      return; 
    MCH_EntityAircraft ac = this.parent;
    for (MCH_EntityWheel w : this.wheels) {
      Vec3d v = ac.getTransformedPosition(w.pos);
      int x = (int)(v.x + 0.5D);
      int y = (int)(v.y + 0.5D);
      int z = (int)(v.z + 0.5D);
      BlockPos blockpos = new BlockPos(x, y, z);
      IBlockState iblockstate = ac.world.getBlockState(blockpos);
      if (iblockstate.getBlock() == W_Block.getSnowLayer())
        ac.world.setBlockToAir(blockpos); 
      if (iblockstate.getBlock() == W_Blocks.WATERLILY || iblockstate.getBlock() == W_Blocks.CAKE)
        W_WorldFunc.destroyBlock(ac.world, x, y, z, false); 
    } 
  }
  
  public void particleLandingGear() {
    if (this.wheels.length <= 0)
      return; 
    MCH_EntityAircraft ac = this.parent;
    double d = ac.motionX * ac.motionX + ac.motionZ * ac.motionZ + Math.abs(this.prevYaw - ac.getRotYaw());
    this.prevYaw = ac.getRotYaw();
    if (d > 0.001D)
      for (int i = 0; i < 2; i++) {
        MCH_EntityWheel w = this.wheels[rand.nextInt(this.wheels.length)];
        Vec3d v = ac.getTransformedPosition(w.pos);
        int x = MathHelper.floor(v.x + 0.5D);
        int y = MathHelper.floor(v.y - 0.5D);
        int z = MathHelper.floor(v.z + 0.5D);
        BlockPos blockpos = new BlockPos(x, y, z);
        IBlockState iblockstate = ac.world.getBlockState(blockpos);
        if (Block.isEqualTo(iblockstate.getBlock(), Blocks.AIR)) {
          y = MathHelper.floor(v.y + 0.5D);
          blockpos = new BlockPos(x, y, z);
          iblockstate = ac.world.getBlockState(blockpos);
        } 
        if (!Block.isEqualTo(iblockstate.getBlock(), Blocks.AIR))
          MCH_ParticlesUtil.spawnParticleTileCrack(ac.world, x, y, z, v.x + rand.nextFloat() - 0.5D, v.y + 0.1D, v.z + rand
              .nextFloat() - 0.5D, -ac.motionX * 4.0D + (rand
              .nextFloat() - 0.5D) * 0.1D, rand.nextFloat() * 0.5D, -ac.motionZ * 4.0D + (rand
              .nextFloat() - 0.5D) * 0.1D); 
      }  
  }
}
