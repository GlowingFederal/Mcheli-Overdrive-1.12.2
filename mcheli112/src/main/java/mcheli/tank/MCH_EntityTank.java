package mcheli.tank;

import java.util.List;
import javax.annotation.Nullable;
import mcheli.MCH_Config;
import mcheli.MCH_Lib;
import mcheli.MCH_MOD;
import mcheli.MCH_Math;
import mcheli.aircraft.MCH_AircraftInfo;
import mcheli.aircraft.MCH_BoundingBox;
import mcheli.aircraft.MCH_EntityAircraft;
import mcheli.aircraft.MCH_EntityHitBox;
import mcheli.aircraft.MCH_EntitySeat;
import mcheli.aircraft.MCH_PacketStatusRequest;
import mcheli.aircraft.MCH_Parts;
import mcheli.particles.MCH_ParticleParam;
import mcheli.particles.MCH_ParticlesUtil;
import mcheli.weapon.MCH_WeaponSet;
import mcheli.wrapper.W_Block;
import mcheli.wrapper.W_Blocks;
import mcheli.wrapper.W_Entity;
import mcheli.wrapper.W_Lib;
import mcheli.wrapper.W_WorldFunc;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ReportedException;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MCH_EntityTank extends MCH_EntityAircraft {
  private MCH_TankInfo tankInfo;
  
  public float soundVolume;
  
  public float soundVolumeTarget;
  
  public float rotationRotor;
  
  public float prevRotationRotor;
  
  public float addkeyRotValue;
  
  public final MCH_WheelManager WheelMng;
  public float customYOffset;
  public float partialTicks;

  @Override
  public float getEyeHeight() {
    return this.height / 2.0F;
  }

  @Override
  public void setSize(float width, float height) {
    super.setSize(width, height);
    this.setPosition(this.posX, this.posY - height / 2.0F, this.posZ);
  }

  public MCH_EntityTank(World world) {
    super(world);
    super.currentSpeed = 0.07D;
    super.preventEntitySpawning = true;
    this.setSize(2.0F, 0.7F);
    this.customYOffset = this.height / 2.0F;
    super.motionX = 0.0D;
    super.motionY = 0.0D;
    super.motionZ = 0.0D;
    super.weapons = this.createWeapon(0);
    this.soundVolume = 0.0F;
    super.stepHeight = 0.6F;
    this.rotationRotor = 0.0F;
    this.prevRotationRotor = 0.0F;
    this.WheelMng = new MCH_WheelManager(this);
  }
  
  public String getKindName() {
    return "tanks";
  }
  
  public String getEntityType() {
    return "Vehicle";
  }
  
  @Nullable
  public MCH_TankInfo getTankInfo() {
    return this.tankInfo;
  }

  public void changeType(String type) {
    if(!type.isEmpty()) {
      this.tankInfo = MCH_TankInfoManager.get(type);
    }

    if(this.tankInfo == null) {
      MCH_Lib.Log((Entity)this, "##### MCH_EntityTank changeTankType() Tank info null %d, %s, %s", new Object[]{Integer.valueOf(W_Entity.getEntityId(this)), type, this.getEntityName()});
      this.setDead();
    } else {
      this.setAcInfo(this.tankInfo);
      this.newSeats(this.getAcInfo().getNumSeatAndRack());
      this.switchFreeLookModeClient(this.getAcInfo().defaultFreelook);
      super.weapons = this.createWeapon(1 + this.getSeatNum());
      this.initPartRotation(this.getRotYaw(), this.getRotPitch());
      this.WheelMng.createWheels(this.world, this.getAcInfo().wheels, new Vec3d(0.0D, -0.35D, (double)this.getTankInfo().weightedCenterZ));
    }

  }
  
  @Nullable
  public Item getItem() {
    return (getTankInfo() != null) ? (Item)(getTankInfo()).item : null;
  }
  
  public boolean canMountWithNearEmptyMinecart() {
    return MCH_Config.MountMinecartTank.prmBool;
  }
  
  protected void entityInit() {
    super.entityInit();
  }
  
  public float getGiveDamageRot() {
    return 91.0F;
  }
  
  protected void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
    super.writeEntityToNBT(par1NBTTagCompound);
  }
  
  protected void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
    super.readEntityFromNBT(par1NBTTagCompound);
    if (this.tankInfo == null) {
      this.tankInfo = MCH_TankInfoManager.get(getTypeName());
      if (this.tankInfo == null) {
        MCH_Lib.Log((Entity)this, "##### MCH_EntityTank readEntityFromNBT() Tank info null %d, %s", new Object[] { Integer.valueOf(W_Entity.getEntityId((Entity)this)), getEntityName() });
        setDead();
      } else {
        setAcInfo(this.tankInfo);
      } 
    } 
  }
  
  public void setDead() {
    super.setDead();
  }

  public void onInteractFirst(EntityPlayer player) {
    this.addkeyRotValue = 0.0F;
    player.rotationYawHead = player.prevRotationYawHead = this.getLastRiderYaw();
    player.prevRotationYaw = player.rotationYaw = this.getLastRiderYaw();
    player.rotationPitch = this.getLastRiderPitch();
  }
  
  public boolean canSwitchGunnerMode() {
    if (!super.canSwitchGunnerMode())
      return false; 
    return false;
  }

  public void onUpdateAircraft() {

    //add partial ticks here???


    if(this.tankInfo == null) {
      this.changeType(this.getTypeName());
      super.prevPosX = super.posX;
      super.prevPosY = super.posY;
      super.prevPosZ = super.posZ;
    } else {
      if(!super.isRequestedSyncStatus) {
        super.isRequestedSyncStatus = true;
        if(this.world.isRemote) {
          MCH_PacketStatusRequest.requestStatus(this);
        }
      }

      if(super.lastRiddenByEntity == null && this.getRiddenByEntity() != null) {
        this.initCurrentWeapon(this.getRiddenByEntity());
      }

      this.updateWeapons();
      this.onUpdate_Seats();
      this.onUpdate_Control(partialTicks);
      this.prevRotationRotor = this.rotationRotor;
      this.rotationRotor = (float)((double)this.rotationRotor + this.getCurrentThrottle() * (double)this.getAcInfo().rotorSpeed);
      if(this.rotationRotor > 360.0F) {
        this.rotationRotor -= 360.0F;
        this.prevRotationRotor -= 360.0F;
      }

      if(this.rotationRotor < 0.0F) {
        this.rotationRotor += 360.0F;
        this.prevRotationRotor += 360.0F;
      }

      super.prevPosX = super.posX;
      super.prevPosY = super.posY;
      super.prevPosZ = super.posZ;
      if(this.isDestroyed() && this.getCurrentThrottle() > 0.0D) {
        if(MCH_Lib.getBlockIdY(this, 3, -2) > 0) {
          this.setCurrentThrottle(this.getCurrentThrottle() * 0.8D);
        }

        if(this.isExploded()) {
          this.setCurrentThrottle(this.getCurrentThrottle() * 0.98D);
        }
      }

      this.updateCameraViewers();
      if(this.world.isRemote) {
        this.onUpdate_Client();
      } else {
        this.onUpdate_Server();
      }

    }
  }
  
  @SideOnly(Side.CLIENT)
  public boolean canRenderOnFire() {
    return (isDestroyed() || super.canRenderOnFire());
  }
  
  public void updateExtraBoundingBox() {
    if (this.world.isRemote) {
      super.updateExtraBoundingBox();
    } else if (getCountOnUpdate() <= 1) {
      super.updateExtraBoundingBox();
      super.updateExtraBoundingBox();
    } 
  }
  
  public ClacAxisBB calculateXOffset(List<AxisAlignedBB> list, AxisAlignedBB bb, double x) {
    for (int j5 = 0; j5 < list.size(); j5++)
      x = ((AxisAlignedBB)list.get(j5)).calculateXOffset(bb, x); 
    return new ClacAxisBB(x, bb.offset(x, 0.0D, 0.0D));
  }
  
  public ClacAxisBB calculateYOffset(List<AxisAlignedBB> list, AxisAlignedBB bb, double y) {
    return calculateYOffset(list, bb, bb, y);
  }
  
  public ClacAxisBB calculateYOffset(List<AxisAlignedBB> list, AxisAlignedBB calcBB, AxisAlignedBB offsetBB, double y) {
    for (int k = 0; k < list.size(); k++)
      y = ((AxisAlignedBB)list.get(k)).calculateYOffset(calcBB, y); 
    return new ClacAxisBB(y, offsetBB.offset(0.0D, y, 0.0D));
  }
  
  public ClacAxisBB calculateZOffset(List<AxisAlignedBB> list, AxisAlignedBB bb, double z) {
    for (int k5 = 0; k5 < list.size(); k5++)
      z = ((AxisAlignedBB)list.get(k5)).calculateZOffset(bb, z); 
    return new ClacAxisBB(z, bb.offset(0.0D, 0.0D, z));
  }

  public void move(double parX, double parY, double parZ) {
    super.world.profiler.startSection("move");
    //todo blame
    //ysize
    super.stepHeight *= 0.4F;
    double nowPosX = super.posX;
    double nowPosY = super.posY;
    double nowPosZ = super.posZ;
    double mx = parX;
    double my = parY;
    double mz = parZ;
    List<AxisAlignedBB> list = getCollisionBoxes((Entity)this, getEntityBoundingBox().expand(parX, parY, parZ));
    AxisAlignedBB backUpAxisalignedBB = getEntityBoundingBox();
    ClacAxisBB v = calculateYOffset(list, getEntityBoundingBox(), parY);
    parY = v.value;
    setEntityBoundingBox(v.bb);
    //parY = this.calculateYOffset(list, getEntityBoundingBox(), parY);
    boolean flag1 = super.onGround || my != parY && my < 0.0D;
    MCH_BoundingBox[] prevPX = super.extraBoundingBox;
    int len$ = prevPX.length;

    for(int prevPZ = 0; prevPZ < len$; ++prevPZ) {
      MCH_BoundingBox ebb = prevPX[prevPZ];
      ebb.updatePosition(super.posX, super.posY, super.posZ, this.getRotYaw(), this.getRotPitch(), this.getRotRoll());
    }

    //parX = this.calculateXOffset(list, super.getEntityBoundingBox(), parX);
    //parZ = this.calculateZOffset(list, super.getEntityBoundingBox(), parZ);
    //im so fucking lost

    if (parX != 0.0D) {
      ClacAxisBB vx = calculateXOffset(list, super.getEntityBoundingBox(), parX);
      parX = vx.value;
      if (parX != 0.0D)
        setEntityBoundingBox(vx.bb);
    }
    if (parZ != 0.0D) {
      ClacAxisBB vz = calculateZOffset(list, super.getEntityBoundingBox(), parZ);
      parZ = vz.value;
      if (parZ != 0.0D)
        setEntityBoundingBox(vz.bb);
    }

    double minX;
    double var38;
    double var39;
    if(super.stepHeight > 0.0F && flag1 && super.height < 0.05F && (mx != parX || mz != parZ)) {
      double d14 = parX;
      double d6 = parY;
      double d7 = parZ;
      AxisAlignedBB axisalignedbb1 = getEntityBoundingBox();
      setEntityBoundingBox(axisalignedbb1);
      parY = this.stepHeight;
      List<AxisAlignedBB> list1 = getCollisionBoxes((Entity)this, getEntityBoundingBox().expand(mx, parY, mz));
      AxisAlignedBB axisalignedbb2 = getEntityBoundingBox();
      AxisAlignedBB axisalignedbb3 = axisalignedbb2.expand(mx, 0.0D, mz);
      double d8 = parY;
      ClacAxisBB vy = calculateYOffset(list1, axisalignedbb3, axisalignedbb2, d8);
      d8 = vy.value;
      axisalignedbb2 = vy.bb;
      double d18 = mx;
      v = calculateXOffset(list1, axisalignedbb2, d18);
      d18 = v.value;
      axisalignedbb2 = v.bb;
      double d19 = mz;
      v = calculateZOffset(list1, axisalignedbb2, d19);
      d19 = v.value;
      axisalignedbb2 = v.bb;
      AxisAlignedBB axisalignedbb4 = getEntityBoundingBox();
      double d20 = parY;
      v = calculateYOffset(list1, axisalignedbb4, d20);
      d20 = v.value;
      axisalignedbb4 = v.bb;
      double d21 = mx;
      v = calculateXOffset(list1, axisalignedbb4, d21);
      d21 = v.value;
      axisalignedbb4 = v.bb;
      double d22 = mz;
      v = calculateZOffset(list1, axisalignedbb4, d22);
      d22 = v.value;
      axisalignedbb4 = v.bb;
      double d23 = d18 * d18 + d19 * d19;
      double d9 = d21 * d21 + d22 * d22;
      if (d23 > d9) {
        parX = d18;
        parZ = d19;
        parY = -d8;
        setEntityBoundingBox(axisalignedbb2);
      } else {
        parX = d21;
        parZ = d22;
        parY = -d20;
        setEntityBoundingBox(axisalignedbb4);
      }
      v = calculateYOffset(list, getEntityBoundingBox(), parY);
      parY = v.value;
      setEntityBoundingBox(v.bb);
      if (d14 * d14 + d7 * d7 >= parX * parX + parZ * parZ) {
        parX = d14;
        parY = d6;
        parZ = d7;
        setEntityBoundingBox(axisalignedbb1);
      }
    }

    var38 = super.posX;
    var39 = super.posZ;
    super.world.profiler.endSection();
    super.world.profiler.startSection("rest");
    AxisAlignedBB boundingBox = this.getEntityBoundingBox(); // Get the bounding box

    minX = boundingBox.minX;
    double var40 = boundingBox.minZ;
    double maxX = boundingBox.maxX;
    double maxZ = boundingBox.maxZ;

    super.posX = (minX + maxX) / 2.0D;
    this.posY = this.getEntityBoundingBox().minY + (double) this.customYOffset - (double) this.stepHeight;

    super.posZ = (var40 + maxZ) / 2.0D;
    super.collidedHorizontally = mx != parX || mz != parZ;
    super.collidedVertically = my != parY;
    super.onGround = my != parY && my < 0.0D;
    super.collided = super.collidedHorizontally || super.collidedVertically;

    int j6 = MathHelper.floor(this.posX);
    int i1 = MathHelper.floor(this.posY - 0.20000000298023224D);
    int k6 = MathHelper.floor(this.posZ);
    BlockPos blockpos = new BlockPos(j6, i1, k6);
    IBlockState iblockstate = this.world.getBlockState(blockpos);
    if (iblockstate.getMaterial() == Material.AIR) {
      BlockPos blockpos1 = blockpos.down();
      IBlockState iblockstate1 = this.world.getBlockState(blockpos1);
      Block block1 = iblockstate1.getBlock();
      if (block1 instanceof net.minecraft.block.BlockFence || block1 instanceof net.minecraft.block.BlockWall || block1 instanceof net.minecraft.block.BlockFenceGate) {
        iblockstate = iblockstate1;
        blockpos = blockpos1;
      }
    }

    //this.updateFallState(parY, super.onGround);
    this.updateFallState(parY, super.onGround, iblockstate, blockpos);
    if(mx != parX) {
      super.motionX = 0.0D;
    }

    if(my != parY) {
      super.motionY = 0.0D;
    }

    if(mz != parZ) {
      super.motionZ = 0.0D;
    }

    try {
      this.doBlockCollisions();
    } catch (Throwable var37) {
      CrashReport crashreport = CrashReport.makeCrashReport(var37, "Checking entity tile collision");
      CrashReportCategory crashreportcategory = crashreport.makeCategory("Entity being checked for collision");
      this.addEntityCrashInfo(crashreportcategory);
    }

    super.world.profiler.endSection();
  }
  
  private void rotationByKey(float partialTicks) {
    float rot = 0.2F;
    if (this.moveLeft && !this.moveRight)
      this.addkeyRotValue -= rot * partialTicks; 
    if (this.moveRight && !this.moveLeft)
      this.addkeyRotValue += rot * partialTicks; 
  }

  public void onUpdateAngles(float partialTicks) {
    //updated
    if(!this.isDestroyed()) {
      if(super.isGunnerMode) {
        this.setRotPitch(this.getRotPitch() * 0.95F);
        this.setRotYaw(this.getRotYaw() + this.getAcInfo().autoPilotRot * 0.2F);
        if(MathHelper.abs(this.getRotRoll()) > 20.0F) {
          this.setRotRoll(this.getRotRoll() * 0.95F);
        }
      }

      this.updateRecoil(partialTicks);
      //todo: blame
      this.setRotPitch(this.getRotPitch() + (this.WheelMng.targetPitch - this.getRotPitch()) * partialTicks);
      this.setRotRoll(this.getRotRoll() + (this.WheelMng.targetRoll - this.getRotRoll()) * partialTicks);
      boolean isFly = MCH_Lib.getBlockIdY(this, 3, -3) == 0;
      System.out.println("isfly" + isFly);

      //logic for like rotation
      if(!isFly || this.getAcInfo().isFloat && this.getWaterDepth() > 0.0D) {
        float rotonground = 1.0F;
        if(!isFly) {
          rotonground = this.getAcInfo().mobilityYawOnGround;
          if(!this.getAcInfo().canRotOnGround) {
            Block pivotTurnThrottle = MCH_Lib.getBlockY(this, 3, -2, false);
            if(!W_Block.isEqual(pivotTurnThrottle, W_Block.getWater()) && !W_Block.isEqual(pivotTurnThrottle, Blocks.AIR)) {
              rotonground = 0.0F;
            }
          }
        }

        float pivotTurnThrottle1 = this.getAcInfo().pivotTurnThrottle;
        double dx = super.posX - super.prevPosX;
        double dz = super.posZ - super.prevPosZ;
        double dist = dx * dx + dz * dz;

        if(pivotTurnThrottle1 <= 0.0F || this.getCurrentThrottle() >= (double)pivotTurnThrottle1 || super.throttleBack >= pivotTurnThrottle1 / 10.0F || dist > (double)super.throttleBack * 0.01D) {
          float sf = (float)Math.sqrt(dist <= 1.0D?dist:1.0D);
          if(pivotTurnThrottle1 <= 0.0F) {
            sf = 1.0F;
          }

          float flag = !super.throttleUp && super.throttleDown && this.getCurrentThrottle() < (double)pivotTurnThrottle1 + 0.05D?-1.0F:1.0F;
          if(super.moveLeft && !super.moveRight) {
            this.setRotYaw(this.getRotYaw() - 0.6F * rotonground * partialTicks * flag * sf);
          }

          if(super.moveRight && !super.moveLeft) {
            this.setRotYaw(this.getRotYaw() + 0.6F * rotonground * partialTicks * flag * sf);
          }

        }
      }

      this.addkeyRotValue = (float)((double)this.addkeyRotValue * (1.0D - (double)(0.1F * partialTicks)));
    }
  }

  protected void onUpdate_Control(float partialTicks) {
    if(super.isGunnerMode && !this.canUseFuel()) {
      this.switchGunnerMode(false);
    }

    super.throttleBack = (float)((double)super.throttleBack * 0.8D);
    if(this.getBrake()) {
      super.throttleBack = (float)((double)super.throttleBack * 0.5D); //todo: add braking force variable here
      if(this.getCurrentThrottle() > 0.0D) {
        this.addCurrentThrottle(-0.02D * (double)this.getAcInfo().throttleUpDown);
      } else {
        this.setCurrentThrottle(0.0D);
      }
    }

    if(this.getRiddenByEntity() != null && !this.getRiddenByEntity().isDead && this.isCanopyClose() && this.canUseFuel() && !this.isDestroyed()) {
      this.onUpdate_ControlSub(partialTicks);
    } else if(this.isTargetDrone() && this.canUseFuel() && !this.isDestroyed()) {
      super.throttleUp = true;
      this.onUpdate_ControlSub(partialTicks);
    } else if(this.getCurrentThrottle() > 0.0D) {
      this.addCurrentThrottle(-0.0025D * (double)this.getAcInfo().throttleUpDown);
    } else {
      this.setCurrentThrottle(0.0D);
    }

    if(this.getCurrentThrottle() < 0.0D) {
      this.setCurrentThrottle(0.0D);
    }

    if(this.world.isRemote) {
      if(!W_Lib.isClientPlayer(this.getRiddenByEntity()) || this.getCountOnUpdate() % 200 == 0) {
        double ct = this.getThrottle();
        if(this.getCurrentThrottle() > ct) {
          this.addCurrentThrottle(-0.005D);
        }

        if(this.getCurrentThrottle() < ct) {
          this.addCurrentThrottle(0.005D);
        }
      }
    } else {
      this.setThrottle(this.getCurrentThrottle());
    }

  }

  protected void onUpdate_ControlSub(float partialTicks) {
    if(!super.isGunnerMode) {
      float throttleUpDown = this.getAcInfo().throttleUpDown;
      if(super.throttleUp) {
        float f = throttleUpDown;
        if(this.getRidingEntity() != null) {
          double mx = this.getRidingEntity().motionX;
          double mz = this.getRidingEntity().motionZ;
          f = throttleUpDown * MathHelper.sqrt(mx * mx + mz * mz) * this.getAcInfo().throttleUpDownOnEntity;
        }

        if(this.getAcInfo().enableBack && super.throttleBack > 0.0F) {
          super.throttleBack = (float)((double)super.throttleBack - 0.01D * (double)f);
        } else {
          super.throttleBack = 0.0F;
          if(this.getCurrentThrottle() < 1.0D) {
            this.addCurrentThrottle(0.01D * (double)f);
            //here as well?
          } else {
            this.setCurrentThrottle(1.0D);
            //implement a new variable here to add throttle control for specific vehicles specifically 1.8D
          }
        }



      } else if(super.throttleDown) {



        if(this.getCurrentThrottle() > 0.0D) {
          this.addCurrentThrottle(-0.01D * (double)throttleUpDown);
        } else {
          this.setCurrentThrottle(0.0D);
          if(this.getAcInfo().enableBack) {
            super.throttleBack = (float)((double)super.throttleBack + 0.0025D * (double)throttleUpDown);
            if(super.throttleBack > 0.6F) { //todo: add a new variable here for reversespeed
              super.throttleBack = 0.6F;
            }
            float pivotTurnThrottle1 = this.getAcInfo().pivotTurnThrottle;
            if (pivotTurnThrottle1 > 0) {
              if (super.throttleBack > 0) {
                double dx = super.posX - super.prevPosX;
                double dz = super.posZ - super.prevPosZ;
                double dist = dx * dx + dz * dz;
                float sf = (float)Math.sqrt(dist <= 1.0D ? dist : 1.0D);
                if (pivotTurnThrottle1 <= 0.0F) {
                  sf = 1.0F;
                }

                float rotonground = 1.0F;
                boolean isFly = MCH_Lib.getBlockIdY(this, 3, -3) == 0;

                if (!isFly) {
                  rotonground = this.getAcInfo().mobilityYawOnGround;
                  if (!this.getAcInfo().canRotOnGround) {
                    Block pivotTurnThrottle = MCH_Lib.getBlockY(this, 3, -2, false);
                    if (!W_Block.isEqual(pivotTurnThrottle, W_Block.getWater()) && !W_Block.isEqual(pivotTurnThrottle, Blocks.AIR)) {
                      rotonground = 0.0F;
                    }
                  }
                }

                float flag = !super.throttleUp && super.throttleDown && this.getCurrentThrottle() < (double)pivotTurnThrottle1 + 0.05D ? -1.0F : 1.0F;
                if(super.moveLeft && !super.moveRight) {
                  this.setRotYaw(this.getRotYaw() + 0.6F * rotonground * partialTicks * flag * sf);
                }

                if(super.moveRight && !super.moveLeft) {
                  this.setRotYaw(this.getRotYaw() - 0.6F * rotonground * partialTicks * flag * sf);
                }
              }
            }
          }
        }
      } else if(super.cs_tankAutoThrottleDown && this.getCurrentThrottle() > 0.0D) {
        this.addCurrentThrottle(-0.005D * (double)throttleUpDown);
        if(this.getCurrentThrottle() <= 0.0D) {
          this.setCurrentThrottle(0.0D);
        }
      }
    }

  }

  protected void onUpdate_Particle2() {
    if(this.world.isRemote) {
      if((double)this.getHP() < (double)this.getMaxHP() * 0.5D) {
        if(this.getTankInfo() != null) {
          int bbNum = this.getTankInfo().extraBoundingBox.size();
          if(bbNum < 0) {
            bbNum = 0;
          }

          if(super.isFirstDamageSmoke || super.prevDamageSmokePos.length != bbNum + 1) {
            super.prevDamageSmokePos = new Vec3d[bbNum + 1];
          }

          float yaw = this.getRotYaw();
          float pitch = this.getRotPitch();
          float roll = this.getRotRoll();

          int px;
          double py;
          double pz;
          for(int b = 0; b < bbNum; ++b) {
            if((double)this.getHP() >= (double)this.getMaxHP() * 0.2D && this.getMaxHP() > 0) {
              px = (int)(((double)this.getHP() / (double)this.getMaxHP() - 0.2D) / 0.3D * 15.0D);
              if(px > 0 && super.rand.nextInt(px) > 0) {
                continue;
              }
            }

            MCH_BoundingBox var15 = (MCH_BoundingBox)this.getTankInfo().extraBoundingBox.get(b);
            Vec3d pos = this.getTransformedPosition(var15.offsetX, var15.offsetY, var15.offsetZ);
            py = pos.x;
            pz = pos.y;
            double pos1 = pos.z;
            this.onUpdate_Particle2SpawnSmoke(b, py, pz, pos1, 1.0F);
          }

          boolean var14 = true;
          if((double)this.getHP() >= (double)this.getMaxHP() * 0.2D && this.getMaxHP() > 0) {
            px = (int)(((double)this.getHP() / (double)this.getMaxHP() - 0.2D) / 0.3D * 15.0D);
            if(px > 0 && super.rand.nextInt(px) > 0) {
              var14 = false;
            }
          }

          if(var14) {
            double var16 = super.posX;
            py = super.posY;
            pz = super.posZ;
            if(this.getSeatInfo(0) != null && this.getSeatInfo(0).pos != null) {
              Vec3d var17 = MCH_Lib.RotVec3(0.0D, this.getSeatInfo(0).pos.y, -2.0D, -yaw, -pitch, -roll);
              var16 += var17.x;
              py += var17.y;
              pz += var17.z;
            }

            this.onUpdate_Particle2SpawnSmoke(bbNum, var16, py, pz, bbNum == 0?2.0F:1.0F);
          }

          super.isFirstDamageSmoke = false;
        }
      }
    }
  }
  
  public void onUpdate_Particle2SpawnSmoke(int ri, double x, double y, double z, float size) {
    if (this.isFirstDamageSmoke || this.prevDamageSmokePos[ri] == null)
      this.prevDamageSmokePos[ri] = new Vec3d(x, y, z); 
    int num = 1;
    for (int i = 0; i < num; i++) {
      float c = 0.2F + this.rand.nextFloat() * 0.3F;
      MCH_ParticleParam prm = new MCH_ParticleParam(this.world, "smoke", x, y, z);
      prm.motionX = size * (this.rand.nextDouble() - 0.5D) * 0.3D;
      prm.motionY = size * this.rand.nextDouble() * 0.1D;
      prm.motionZ = size * (this.rand.nextDouble() - 0.5D) * 0.3D;
      prm.size = size * (this.rand.nextInt(5) + 5.0F) * 1.0F;
      prm.setColor(0.7F + this.rand.nextFloat() * 0.1F, c, c, c);
      MCH_ParticlesUtil.spawnParticle(prm);
    } 
    this.prevDamageSmokePos[ri] = new Vec3d(x, y, z);
  }
  
  public void onUpdate_Particle2SpawnSmode(int ri, double x, double y, double z, float size) {
    if (this.isFirstDamageSmoke)
      this.prevDamageSmokePos[ri] = new Vec3d(x, y, z); 
    Vec3d prev = this.prevDamageSmokePos[ri];
    double dx = x - prev.x;
    double dy = y - prev.y;
    double dz = z - prev.z;
    int num = (int)(MathHelper.sqrt(dx * dx + dy * dy + dz * dz) / 0.3D) + 1;
    for (int i = 0; i < num; i++) {
      float c = 0.2F + this.rand.nextFloat() * 0.3F;
      MCH_ParticleParam prm = new MCH_ParticleParam(this.world, "smoke", x, y, z);
      prm.motionX = size * (this.rand.nextDouble() - 0.5D) * 0.3D;
      prm.motionY = size * this.rand.nextDouble() * 0.1D;
      prm.motionZ = size * (this.rand.nextDouble() - 0.5D) * 0.3D;
      prm.size = size * (this.rand.nextInt(5) + 5.0F) * 1.0F;
      prm.setColor(0.7F + this.rand.nextFloat() * 0.1F, c, c, c);
      MCH_ParticlesUtil.spawnParticle(prm);
    } 
    this.prevDamageSmokePos[ri] = new Vec3d(x, y, z);
  }
  
  public void onUpdate_ParticleLandingGear() {
    this.WheelMng.particleLandingGear();
  }
  
  private void onUpdate_ParticleSplash() {
    if (getAcInfo() == null)
      return; 
    if (!this.world.isRemote)
      return; 
    double mx = this.posX - this.prevPosX;
    double mz = this.posZ - this.prevPosZ;
    double dist = mx * mx + mz * mz;
    if (dist > 1.0D)
      dist = 1.0D; 
    for (MCH_AircraftInfo.ParticleSplash p : (getAcInfo()).particleSplashs) {
      for (int i = 0; i < p.num; i++) {
        if (dist > 0.03D + this.rand.nextFloat() * 0.1D)
          setParticleSplash(p.pos, -mx * p.acceleration, p.motionY, -mz * p.acceleration, p.gravity, p.size * (0.5D + dist * 0.5D), p.age); 
      } 
    } 
  }
  
  private void setParticleSplash(Vec3d pos, double mx, double my, double mz, float gravity, double size, int age) {
    Vec3d v = getTransformedPosition(pos);
    v = v.addVector(this.rand.nextDouble() - 0.5D, (this.rand.nextDouble() - 0.5D) * 0.5D, this.rand
        .nextDouble() - 0.5D);
    int x = (int)(v.x + 0.5D);
    int y = (int)(v.y + 0.0D);
    int z = (int)(v.z + 0.5D);
    if (W_WorldFunc.isBlockWater(this.world, x, y, z)) {
      float c = this.rand.nextFloat() * 0.3F + 0.7F;
      MCH_ParticleParam prm = new MCH_ParticleParam(this.world, "smoke", v.x, v.y, v.z);
      prm.motionX = mx + (this.rand.nextFloat() - 0.5D) * 0.7D;
      prm.motionY = my;
      prm.motionZ = mz + (this.rand.nextFloat() - 0.5D) * 0.7D;
      prm.size = (float)size * (this.rand.nextFloat() * 0.2F + 0.8F);
      prm.setColor(0.9F, c, c, c);
      prm.age = age + (int)(this.rand.nextFloat() * 0.5D * age);
      prm.gravity = gravity;
      MCH_ParticlesUtil.spawnParticle(prm);
    } 
  }
  
  public void destroyAircraft() {
    super.destroyAircraft();
    this.rotDestroyedPitch = 0.0F;
    this.rotDestroyedRoll = 0.0F;
    this.rotDestroyedYaw = 0.0F;
  }

  public int getClientPositionDelayCorrection() {
    return this.getTankInfo() == null?7:(this.getTankInfo().weightType == 1?2:7);
  }

  protected void onUpdate_Client() {
    if(this.getRiddenByEntity() != null && W_Lib.isClientPlayer(this.getRiddenByEntity())) {
      this.getRiddenByEntity().rotationPitch = this.getRiddenByEntity().prevRotationPitch;
    }

    if(super.aircraftPosRotInc > 0) {
      this.applyServerPositionAndRotation();
    } else {
      this.setPosition(super.posX + super.motionX, super.posY + super.motionY, super.posZ + super.motionZ);
      if(!this.isDestroyed() && (super.onGround || MCH_Lib.getBlockIdY(this, 1, -2) > 0)) {
        super.motionX *= 0.95D;
        super.motionZ *= 0.95D;
        this.applyOnGroundPitch(0.95F);
        //System.out.println(aircraftPitch);
      }

      if(this.isInWater()) {
        super.motionX *= 0.99D;
        super.motionZ *= 0.99D;
      }
    }

    this.updateWheels();
    this.onUpdate_Particle2();
    this.updateSound();
    if(this.world.isRemote) {
      this.onUpdate_ParticleLandingGear();
      this.onUpdate_ParticleSplash();
      this.onUpdate_ParticleSandCloud(true);
    }

    this.updateCamera(super.posX, super.posY, super.posZ);
  }
  
  public void applyOnGroundPitch(float factor) {}

  private void onUpdate_Server() {
    //todo gear shifts here
    Entity rdnEnt = this.getRiddenByEntity();
    double prevMotion = Math.sqrt(super.motionX * super.motionX + super.motionZ * super.motionZ);
    double dp = 0.0D;
    if(this.canFloatWater()) {
      dp = this.getWaterDepth();
    }

    boolean levelOff = super.isGunnerMode;
    if(dp == 0.0D) {
      if(!levelOff) {
        super.motionY += 0.04D + (double)(!this.isInWater()?this.getAcInfo().gravity:this.getAcInfo().gravityInWater);
        super.motionY += -0.047D * (1.0D - this.getCurrentThrottle());
      } else {
        super.motionY *= 0.8D;
      }
    } else {
      if(MathHelper.abs(this.getRotRoll()) < 40.0F) {
        ;
      }

      if(dp < 1.0D) {
        super.motionY -= 1.0E-4D;
        super.motionY += 0.007D * this.getCurrentThrottle();
      } else {
        if(super.motionY < 0.0D) {
          super.motionY /= 2.0D;
        }

        super.motionY += 0.007D;
      }
    }

    float throttle = (float)(this.getCurrentThrottle() / 10.0D);
    //im gonna scream
    Vec3d v = MCH_Lib.Rot2Vec3(this.getRotYaw(), this.getRotPitch() - 10.0F);
    if(!levelOff) {
      super.motionY += v.y * (double)throttle / 8.0D;
    }

    boolean canMove = true;
    if(!this.getAcInfo().canMoveOnGround) {
      Block motion = MCH_Lib.getBlockY(this, 3, -2, false);
      if(!W_Block.isEqual(motion, W_Block.getWater()) && !W_Block.isEqual(motion, Blocks.AIR)) {
        canMove = false;
      }
    }

    if(canMove) {
      if(this.getAcInfo().enableBack && super.throttleBack > 0.0F) {
        super.motionX -= v.x * (double)super.throttleBack;
        super.motionZ -= v.z * (double)super.throttleBack;
      } else {
        super.motionX += v.x * (double)throttle;
        super.motionZ += v.z * (double)throttle;
      }
    }

    double motion1 = Math.sqrt(super.motionX * super.motionX + super.motionZ * super.motionZ);
    float speedLimit = this.getMaxSpeed();
    if(motion1 > (double)speedLimit) {
      super.motionX *= (double)speedLimit / motion1;
      super.motionZ *= (double)speedLimit / motion1;
      motion1 = (double)speedLimit;
    }

    if(motion1 > prevMotion && super.currentSpeed < (double)speedLimit) {
      super.currentSpeed += ((double)speedLimit - super.currentSpeed) / 35.0D;
      if(super.currentSpeed > (double)speedLimit) {
        super.currentSpeed = (double)speedLimit;
      }
    } else {
      super.currentSpeed -= (super.currentSpeed - 0.07D) / 35.0D;
      if(super.currentSpeed < 0.07D) {
        super.currentSpeed = 0.07D;
      }
    }

    if(super.onGround || MCH_Lib.getBlockIdY(this, 1, -2) > 0) {
      super.motionX *= (double)this.getAcInfo().motionFactor;
      super.motionZ *= (double)this.getAcInfo().motionFactor;
      if(MathHelper.abs(this.getRotPitch()) < 40.0F) {
        this.applyOnGroundPitch(0.8F);
      }
    }

    this.updateWheels();
    this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
    super.motionY *= 0.95D;
    super.motionX *= (double)this.getAcInfo().motionFactor;
    super.motionZ *= (double)this.getAcInfo().motionFactor;
    this.setRotation(this.getRotYaw(), this.getRotPitch());
    this.onUpdate_updateBlock();
    this.updateCollisionBox();
    if(this.getRiddenByEntity() != null && this.getRiddenByEntity().isDead) {
      this.unmountEntity();
      //super.riddenByEntity = null;
    }

    //if(this.) todo gear check

  }
  
  private void collisionEntity(AxisAlignedBB bb) {
    if (bb == null)
      return; 
    double speed = Math.sqrt(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
    if (speed <= 0.05D)
      return; 
    Entity rider = getRiddenByEntity();
    float damage = (float)(speed * 15.0D);
    MCH_EntityAircraft rideAc = (getRidingEntity() instanceof MCH_EntitySeat) ? ((MCH_EntitySeat)getRidingEntity()).getParent() : ((getRidingEntity() instanceof MCH_EntityAircraft) ? (MCH_EntityAircraft)getRidingEntity() : null);
    List<Entity> list = this.world.getEntitiesInAABBexcluding((Entity)this, bb.grow(0.3D, 0.3D, 0.3D), e -> {
          if (e == rideAc || e instanceof net.minecraft.entity.item.EntityItem || e instanceof net.minecraft.entity.item.EntityXPOrb || e instanceof mcheli.weapon.MCH_EntityBaseBullet || e instanceof mcheli.chain.MCH_EntityChain || e instanceof MCH_EntitySeat)
            return false; 
          if (e instanceof MCH_EntityTank) {
            MCH_EntityTank tank = (MCH_EntityTank)e;
            if (tank.getTankInfo() != null && (tank.getTankInfo()).weightType == 2)
              return MCH_Config.Collision_EntityTankDamage.prmBool; 
          } 
          return MCH_Config.Collision_EntityDamage.prmBool;
        });
    for (int i = 0; i < list.size(); i++) {
      Entity e = list.get(i);
      if (shouldCollisionDamage(e)) {
        DamageSource ds;
        double dx = e.posX - this.posX;
        double dz = e.posZ - this.posZ;
        double dist = Math.sqrt(dx * dx + dz * dz);
        if (dist > 5.0D)
          dist = 5.0D; 
        damage = (float)(damage + 5.0D - dist);
        if (rider instanceof EntityLivingBase) {
          ds = DamageSource.causeMobDamage((EntityLivingBase)rider);
        } else {
          ds = DamageSource.GENERIC;
        } 
        MCH_Lib.applyEntityHurtResistantTimeConfig(e);
        e.attackEntityFrom(ds, damage);
        if (e instanceof MCH_EntityAircraft) {
          e.motionX += this.motionX * 0.05D;
          e.motionZ += this.motionZ * 0.05D;
        } else if (e instanceof net.minecraft.entity.projectile.EntityArrow) {
          e.setDead();
        } else {
          e.motionX += this.motionX * 1.5D;
          e.motionZ += this.motionZ * 1.5D;
        } 
        if ((getTankInfo()).weightType != 2 && (e.width >= 1.0F || e.height >= 1.5D)) {
          if (e instanceof EntityLivingBase) {
            ds = DamageSource.causeMobDamage((EntityLivingBase)e);
          } else {
            ds = DamageSource.GENERIC;
          } 
          attackEntityFrom(ds, damage / 3.0F);
        } 
        MCH_Lib.DbgLog(this.world, "MCH_EntityTank.collisionEntity damage=%.1f %s", new Object[] { Float.valueOf(damage), e.toString() });
      } 
    } 
  }
  
  private boolean shouldCollisionDamage(Entity e) {
    if (getSeatIdByEntity(e) >= 0)
      return false; 
    if (this.noCollisionEntities.containsKey(e))
      return false; 
    if (e instanceof MCH_EntityHitBox && ((MCH_EntityHitBox)e).parent != null) {
      MCH_EntityAircraft ac = ((MCH_EntityHitBox)e).parent;
      if (this.noCollisionEntities.containsKey(ac))
        return false; 
    } 
    if (e.getRidingEntity() instanceof MCH_EntityAircraft)
      if (this.noCollisionEntities.containsKey(e.getRidingEntity()))
        return false;  
    if (e.getRidingEntity() instanceof MCH_EntitySeat && ((MCH_EntitySeat)e.getRidingEntity()).getParent() != null)
      if (this.noCollisionEntities.containsKey(((MCH_EntitySeat)e.getRidingEntity()).getParent()))
        return false;  
    return true;
  }
  
  public void updateCollisionBox() {
    if (getAcInfo() == null)
      return; 
    this.WheelMng.updateBlock();
    for (MCH_BoundingBox bb : this.extraBoundingBox) {
      if (this.rand.nextInt(3) == 0) {
        if (MCH_Config.Collision_DestroyBlock.prmBool) {
          Vec3d v = getTransformedPosition(bb.offsetX, bb.offsetY, bb.offsetZ);
          destoryBlockRange(v, bb.width, bb.height);
        } 
        collisionEntity(bb.getBoundingBox());
      } 
    } 
    if (MCH_Config.Collision_DestroyBlock.prmBool)
      destoryBlockRange(getTransformedPosition(0.0D, 0.0D, 0.0D), this.width * 1.5D, (this.height * 2.0F)); 
    collisionEntity(getCollisionBoundingBox());
  }
  
  public void destoryBlockRange(Vec3d v, double w, double h) {
    if (getAcInfo() == null)
      return; 
    List<Block> destroyBlocks = MCH_Config.getBreakableBlockListFromType((getTankInfo()).weightType);
    List<Block> noDestroyBlocks = MCH_Config.getNoBreakableBlockListFromType((getTankInfo()).weightType);
    List<Material> destroyMaterials = MCH_Config.getBreakableMaterialListFromType((getTankInfo()).weightType);
    int ws = (int)(w + 2.0D) / 2;
    int hs = (int)(h + 2.0D) / 2;
    for (int x = -ws; x <= ws; x++) {
      for (int z = -ws; z <= ws; z++) {
        for (int y = -hs; y <= hs + 1; y++) {
          int bx = (int)(v.x + x - 0.5D);
          int by = (int)(v.y + y - 1.0D);
          int bz = (int)(v.z + z - 0.5D);
          BlockPos blockpos = new BlockPos(bx, by, bz);
          IBlockState iblockstate = this.world.getBlockState(blockpos);
          Block block = (by >= 0 && by < 256) ? iblockstate.getBlock() : Blocks.AIR;
          Material mat = iblockstate.getMaterial();
          if (!Block.isEqualTo(block, Blocks.AIR)) {
            for (Block c : noDestroyBlocks) {
              if (Block.isEqualTo(block, c)) {
                block = null;
                break;
              } 
            } 
            if (block == null)
              break; 
            for (Block c : destroyBlocks) {
              if (Block.isEqualTo(block, c)) {
                destroyBlock(blockpos);
                mat = null;
                break;
              } 
            } 
            if (mat == null)
              break; 
            for (Material m : destroyMaterials) {
              if (iblockstate.getMaterial() == m) {
                destroyBlock(blockpos);
                break;
              } 
            } 
          } 
        } 
      } 
    } 
  }
  
  public void destroyBlock(BlockPos blockpos) {
    if (this.rand.nextInt(8) == 0) {
      W_WorldFunc.destroyBlock(this.world, blockpos, true);
    } else {
      this.world.setBlockToAir(blockpos);
    } 
  }
  
  private void updateWheels() {
    this.WheelMng.move(this.motionX, this.motionY, this.motionZ);
  }
  
  public float getMaxSpeed() {
    return (getTankInfo()).speed + 0.0F;
  }

  //set angles is le turret
  public void setAngles(Entity player, boolean fixRot, float fixYaw, float fixPitch, float deltaX, float deltaY, float x, float y, float partialTicks) {
    if (partialTicks < 0.03F)
      partialTicks = 0.4F;
    if (partialTicks > 0.9F)
      partialTicks = 0.6F;
    super.lowPassPartialTicks.put(partialTicks);
    partialTicks = super.lowPassPartialTicks.getAvg();
    float ac_pitch = this.getRotPitch();
    float ac_yaw = this.getRotYaw();
    float ac_roll = this.getRotRoll();
    if (this.isFreeLookMode()) {
      x = y = 0.0F;
  }
    float yaw = 0.0F;
    float pitch = 0.0F;
    float roll = 0.0F;
    MCH_Math.FMatrix m_add = MCH_Math.newMatrix();
    MCH_Math.MatTurnZ(m_add, roll / 180.0F * 3.1415927F);
    MCH_Math.MatTurnX(m_add, pitch / 180.0F * 3.1415927F);
    MCH_Math.MatTurnY(m_add, yaw / 180.0F * 3.1415927F);
    //MCH_Math.MatTurnZ(m_add, (float)((kill() / 180.0F) * Math.PI));
    //MCH_Math.MatTurnX(m_add, (float)((getRotPitch() / 180.0F) * Math.PI));
    //MCH_Math.MatTurnY(m_add, (float)((getRotYaw() / 180.0F) * Math.PI));
    MCH_Math.MatTurnZ(m_add, (float)((double)(this.getRotRoll() / 180.0F) * 3.141592653589793D));
    MCH_Math.MatTurnX(m_add, (float)((double)(this.getRotPitch() / 180.0F) * 3.141592653589793D));
    MCH_Math.MatTurnY(m_add, (float)((double)(this.getRotYaw() / 180.0F) * 3.141592653589793D));
    MCH_Math.FVector3D v = MCH_Math.MatrixToEuler(m_add);
    v.x = MCH_Lib.RNG(v.x, -90.0F, 90.0F);
    v.z = MCH_Lib.RNG(v.z, -90.0F, 90.0F);
    if (v.z > 180.0F)
      v.z -= 360.0F;
    if (v.z < -180.0F)
      v.z += 360.0F;
    this.setRotYaw(v.y);
    this.setRotPitch(v.x);
    this.setRotRoll(v.z);
    this.onUpdateAngles(partialTicks);
    if(this.getAcInfo().limitRotation) {
      v.x = MCH_Lib.RNG(this.getRotPitch(), -90.0F, 90.0F);
      v.z = MCH_Lib.RNG(this.getRotRoll(), -90.0F, 90.0F);
      this.setRotPitch(v.x);
      this.setRotRoll(v.z);
    }
    if (MathHelper.abs(getRotPitch()) > 90.0F) {
      MCH_Lib.DbgLog(true, "MCH_EntityAircraft.setAngles Error:Pitch=%.1f", new Object[] { Float.valueOf(getRotPitch()) });
      setRotPitch(0.0F);
    }
    if(this.getRotRoll() > 180.0F) {
      this.setRotRoll(this.getRotRoll() - 360.0F);
    }

    if(this.getRotRoll() < -180.0F) {
      this.setRotRoll(this.getRotRoll() + 360.0F);
    }

    super.prevRotationRoll = this.getRotRoll();
    this.prevRotationPitch = this.getRotPitch();
    if (this.getRidingEntity() == null)
      this.prevRotationYaw = this.getRotYaw();
    float deltaLimit = (getAcInfo()).cameraRotationSpeed * partialTicks;
    MCH_WeaponSet ws = getCurrentWeapon(player);
    deltaLimit *= (ws != null && ws.getInfo() != null) ? (ws.getInfo()).cameraRotationSpeedPitch : 1.0F;
    if (deltaX > deltaLimit)
      deltaX = deltaLimit;
    if (deltaX < -deltaLimit)
      deltaX = -deltaLimit;
    if (deltaY > deltaLimit)
      deltaY = deltaLimit;
    if (deltaY < -deltaLimit)
      deltaY = -deltaLimit;
    if (isOverridePlayerYaw() || fixRot) {
      if (getRidingEntity() == null) {
        player.prevRotationYaw = getRotYaw() + fixYaw;
      } else {
        if (getRotYaw() - player.rotationYaw > 180.0F)
          player.prevRotationYaw += 360.0F;
        if (getRotYaw() - player.rotationYaw < -180.0F)
          player.prevRotationYaw -= 360.0F;
      }
      player.rotationYaw = getRotYaw() + fixYaw;
    } else {
      player.turn(deltaX, 0.0F);
    }
    if (isOverridePlayerPitch() || fixRot) {
      player.prevRotationPitch = getRotPitch() + fixPitch;
      player.rotationPitch = getRotPitch() + fixPitch;
    } else {
      player.turn(0.0F, deltaY);
    }
    float playerYaw = MathHelper.wrapDegrees(this.getRotYaw() - player.rotationYaw);
    float playerPitch = this.getRotPitch() * MathHelper.cos((float)((double)playerYaw * 3.141592653589793D / 180.0D)) + -this.getRotRoll() * MathHelper.sin((float)((double)playerYaw * 3.141592653589793D / 180.0D));
    if (MCH_MOD.proxy.isFirstPerson()) {
      player.rotationPitch = MCH_Lib.RNG(player.rotationPitch, playerPitch + (getAcInfo()).minRotationPitch, playerPitch +
          (getAcInfo()).maxRotationPitch);
      player.rotationPitch = MCH_Lib.RNG(player.rotationPitch, -90.0F, 90.0F);
    }
    player.prevRotationPitch = player.rotationPitch;
    if(this.getRidingEntity() == null && ac_yaw != this.getRotYaw() || ac_pitch != this.getRotPitch() || ac_roll != this.getRotRoll()) {
      super.aircraftRotChanged = true;
    }
  }
  
  public float getSoundVolume() {
    if (getAcInfo() != null && (getAcInfo()).throttleUpDown <= 0.0F)
      return 0.0F; 
    return this.soundVolume * 0.7F;
  }
  
  public void updateSound() {
    float target = (float)getCurrentThrottle();
    if (getRiddenByEntity() != null)
      if (this.partCanopy == null || getCanopyRotation() < 1.0F)
        target += 0.1F;  
    if (this.moveLeft || this.moveRight || this.throttleDown) {
      this.soundVolumeTarget += 0.1F;
      if (this.soundVolumeTarget > 0.75F)
        this.soundVolumeTarget = 0.75F; 
    } else {
      this.soundVolumeTarget *= 0.8F;
    } 
    if (target < this.soundVolumeTarget)
      target = this.soundVolumeTarget; 
    if (this.soundVolume < target) {
      this.soundVolume += 0.02F;
      if (this.soundVolume >= target)
        this.soundVolume = target; 
    } else if (this.soundVolume > target) {
      this.soundVolume -= 0.02F;
      if (this.soundVolume <= target)
        this.soundVolume = target; 
    } 
  }
  
  public float getSoundPitch() {
    float target1 = (float)(0.5D + getCurrentThrottle() * 0.5D);
    float target2 = (float)(0.5D + this.soundVolumeTarget * 0.5D);
    return (target1 > target2) ? target1 : target2;
  }
  
  public String getDefaultSoundName() {
    return "prop";
  }
  
  public boolean hasBrake() {
    return true;
  }
  
  public void updateParts(int stat) {
    super.updateParts(stat);
    if (isDestroyed())
      return; 
    MCH_Parts[] parts = new MCH_Parts[0];
    for (MCH_Parts p : parts) {
      if (p != null) {
        p.updateStatusClient(stat);
        p.update();
      } 
    } 
  }
  
  public float getUnfoldLandingGearThrottle() {
    return 0.7F;
  }
  
  private static class ClacAxisBB {
    public final double value;
    
    public final AxisAlignedBB bb;
    
    public ClacAxisBB(double value, AxisAlignedBB bb) {
      this.value = value;
      this.bb = bb;
    }
  }
}
