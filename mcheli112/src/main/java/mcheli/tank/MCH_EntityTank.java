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
  
  public MCH_EntityTank(World world) {
    super(world);
    this.tankInfo = null;
    this.currentSpeed = 0.07D;
    this.preventEntitySpawning = true;
    setSize(2.0F, 0.7F);
    this.motionX = 0.0D;
    this.motionY = 0.0D;
    this.motionZ = 0.0D;
    this.weapons = createWeapon(0);
    this.soundVolume = 0.0F;
    this.stepHeight = 0.6F;
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
    MCH_Lib.DbgLog(this.world, "MCH_EntityTank.changeType " + type + " : " + toString(), new Object[0]);
    if (!type.isEmpty())
      this.tankInfo = MCH_TankInfoManager.get(type); 
    if (this.tankInfo == null) {
      MCH_Lib.Log((Entity)this, "##### MCH_EntityTank changeTankType() Tank info null %d, %s, %s", new Object[] { Integer.valueOf(W_Entity.getEntityId((Entity)this)), type, getEntityName() });
      setDead();
    } else {
      setAcInfo(this.tankInfo);
      newSeats(getAcInfo().getNumSeatAndRack());
      switchFreeLookModeClient((getAcInfo()).defaultFreelook);
      this.weapons = createWeapon(1 + getSeatNum());
      initPartRotation(getRotYaw(), getRotPitch());
      this.WheelMng.createWheels(this.world, (getAcInfo()).wheels, new Vec3d(0.0D, -0.35D, 
            (getTankInfo()).weightedCenterZ));
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
    player.rotationYawHead = player.prevRotationYawHead = getLastRiderYaw();
    player.prevRotationYaw = player.rotationYaw = getLastRiderYaw();
    player.rotationPitch = getLastRiderPitch();
  }
  
  public boolean canSwitchGunnerMode() {
    if (!super.canSwitchGunnerMode())
      return false; 
    return false;
  }
  
  public void onUpdateAircraft() {
    if (this.tankInfo == null) {
      changeType(getTypeName());
      this.prevPosX = this.posX;
      this.prevPosY = this.posY;
      this.prevPosZ = this.posZ;
      return;
    } 
    if (!this.isRequestedSyncStatus) {
      this.isRequestedSyncStatus = true;
      if (this.world.isRemote)
        MCH_PacketStatusRequest.requestStatus(this); 
    } 
    if (this.lastRiddenByEntity == null && getRiddenByEntity() != null)
      initCurrentWeapon(getRiddenByEntity()); 
    updateWeapons();
    onUpdate_Seats();
    onUpdate_Control();
    this.prevRotationRotor = this.rotationRotor;
    this.rotationRotor = (float)(this.rotationRotor + getCurrentThrottle() * (getAcInfo()).rotorSpeed);
    if (this.rotationRotor > 360.0F) {
      this.rotationRotor -= 360.0F;
      this.prevRotationRotor -= 360.0F;
    } 
    if (this.rotationRotor < 0.0F) {
      this.rotationRotor += 360.0F;
      this.prevRotationRotor += 360.0F;
    } 
    this.prevPosX = this.posX;
    this.prevPosY = this.posY;
    this.prevPosZ = this.posZ;
    if (isDestroyed())
      if (getCurrentThrottle() > 0.0D) {
        if (MCH_Lib.getBlockIdY((Entity)this, 3, -2) > 0)
          setCurrentThrottle(getCurrentThrottle() * 0.8D); 
        if (isExploded())
          setCurrentThrottle(getCurrentThrottle() * 0.98D); 
      }  
    updateCameraViewers();
    if (this.world.isRemote) {
      onUpdate_Client();
    } else {
      onUpdate_Server();
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
  
  public void moveEntity(MoverType type, double x, double y, double z) {
    this.world.theProfiler.startSection("move");
    double d2 = x;
    double d3 = y;
    double d4 = z;
    List<AxisAlignedBB> list1 = getCollisionBoxes((Entity)this, getEntityBoundingBox().addCoord(x, y, z));
    AxisAlignedBB axisalignedbb = getEntityBoundingBox();
    if (y != 0.0D) {
      ClacAxisBB v = calculateYOffset(list1, getEntityBoundingBox(), y);
      y = v.value;
      setEntityBoundingBox(v.bb);
    } 
    boolean flag = (this.onGround || (d3 != y && d3 < 0.0D));
    for (MCH_BoundingBox ebb : this.extraBoundingBox)
      ebb.updatePosition(this.posX, this.posY, this.posZ, getRotYaw(), getRotPitch(), getRotRoll()); 
    if (x != 0.0D) {
      ClacAxisBB v = calculateXOffset(list1, getEntityBoundingBox(), x);
      x = v.value;
      if (x != 0.0D)
        setEntityBoundingBox(v.bb); 
    } 
    if (z != 0.0D) {
      ClacAxisBB v = calculateZOffset(list1, getEntityBoundingBox(), z);
      z = v.value;
      if (z != 0.0D)
        setEntityBoundingBox(v.bb); 
    } 
    if (this.stepHeight > 0.0F && flag && (d2 != x || d4 != z)) {
      double d14 = x;
      double d6 = y;
      double d7 = z;
      AxisAlignedBB axisalignedbb1 = getEntityBoundingBox();
      setEntityBoundingBox(axisalignedbb);
      y = this.stepHeight;
      List<AxisAlignedBB> list = getCollisionBoxes((Entity)this, getEntityBoundingBox().addCoord(d2, y, d4));
      AxisAlignedBB axisalignedbb2 = getEntityBoundingBox();
      AxisAlignedBB axisalignedbb3 = axisalignedbb2.addCoord(d2, 0.0D, d4);
      double d8 = y;
      ClacAxisBB v = calculateYOffset(list, axisalignedbb3, axisalignedbb2, d8);
      d8 = v.value;
      axisalignedbb2 = v.bb;
      double d18 = d2;
      v = calculateXOffset(list, axisalignedbb2, d18);
      d18 = v.value;
      axisalignedbb2 = v.bb;
      double d19 = d4;
      v = calculateZOffset(list, axisalignedbb2, d19);
      d19 = v.value;
      axisalignedbb2 = v.bb;
      AxisAlignedBB axisalignedbb4 = getEntityBoundingBox();
      double d20 = y;
      v = calculateYOffset(list, axisalignedbb4, d20);
      d20 = v.value;
      axisalignedbb4 = v.bb;
      double d21 = d2;
      v = calculateXOffset(list, axisalignedbb4, d21);
      d21 = v.value;
      axisalignedbb4 = v.bb;
      double d22 = d4;
      v = calculateZOffset(list, axisalignedbb4, d22);
      d22 = v.value;
      axisalignedbb4 = v.bb;
      double d23 = d18 * d18 + d19 * d19;
      double d9 = d21 * d21 + d22 * d22;
      if (d23 > d9) {
        x = d18;
        z = d19;
        y = -d8;
        setEntityBoundingBox(axisalignedbb2);
      } else {
        x = d21;
        z = d22;
        y = -d20;
        setEntityBoundingBox(axisalignedbb4);
      } 
      v = calculateYOffset(list, getEntityBoundingBox(), y);
      y = v.value;
      setEntityBoundingBox(v.bb);
      if (d14 * d14 + d7 * d7 >= x * x + z * z) {
        x = d14;
        y = d6;
        z = d7;
        setEntityBoundingBox(axisalignedbb1);
      } 
    } 
    this.world.theProfiler.endSection();
    this.world.theProfiler.startSection("rest");
    resetPositionToBB();
    this.isCollidedHorizontally = (d2 != x || d4 != z);
    this.isCollidedVertically = (d3 != y);
    this.onGround = (this.isCollidedVertically && d3 < 0.0D);
    this.isCollided = (this.isCollidedHorizontally || this.isCollidedVertically);
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
    updateFallState(y, this.onGround, iblockstate, blockpos);
    if (d2 != x)
      this.motionX = 0.0D; 
    if (d4 != z)
      this.motionZ = 0.0D; 
    Block block = iblockstate.getBlock();
    if (d3 != y)
      block.onLanded(this.world, (Entity)this); 
    try {
      doBlockCollisions();
    } catch (Throwable throwable) {
      CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Checking entity block collision");
      CrashReportCategory crashreportcategory = crashreport.makeCategory("Entity being checked for collision");
      addEntityCrashInfo(crashreportcategory);
      throw new ReportedException(crashreport);
    } 
    this.world.theProfiler.endSection();
  }
  
  private void rotationByKey(float partialTicks) {
    float rot = 0.2F;
    if (this.moveLeft && !this.moveRight)
      this.addkeyRotValue -= rot * partialTicks; 
    if (this.moveRight && !this.moveLeft)
      this.addkeyRotValue += rot * partialTicks; 
  }
  
  public void onUpdateAngles(float partialTicks) {
    if (isDestroyed())
      return; 
    if (this.isGunnerMode) {
      setRotPitch(getRotPitch() * 0.95F);
      setRotYaw(getRotYaw() + (getAcInfo()).autoPilotRot * 0.2F);
      if (MathHelper.abs(getRotRoll()) > 20.0F)
        setRotRoll(getRotRoll() * 0.95F); 
    } 
    updateRecoil(partialTicks);
    setRotPitch(getRotPitch() + (this.WheelMng.targetPitch - getRotPitch()) * partialTicks);
    setRotRoll(getRotRoll() + (this.WheelMng.targetRoll - getRotRoll()) * partialTicks);
    boolean isFly = (MCH_Lib.getBlockIdY((Entity)this, 3, -3) == 0);
    if (!isFly || ((getAcInfo()).isFloat && getWaterDepth() > 0.0D)) {
      float gmy = 1.0F;
      if (!isFly) {
        gmy = (getAcInfo()).mobilityYawOnGround;
        if (!(getAcInfo()).canRotOnGround) {
          Block block = MCH_Lib.getBlockY((Entity)this, 3, -2, false);
          if (!W_Block.isEqual(block, W_Block.getWater()) && !W_Block.isEqual(block, W_Blocks.AIR))
            gmy = 0.0F; 
        } 
      } 
      float pivotTurnThrottle = (getAcInfo()).pivotTurnThrottle;
      double dx = this.posX - this.prevPosX;
      double dz = this.posZ - this.prevPosZ;
      double dist = dx * dx + dz * dz;
      if (pivotTurnThrottle <= 0.0F || getCurrentThrottle() >= pivotTurnThrottle || this.throttleBack >= pivotTurnThrottle / 10.0F || dist > this.throttleBack * 0.01D) {
        float sf = (float)Math.sqrt((dist <= 1.0D) ? dist : 1.0D);
        if (pivotTurnThrottle <= 0.0F)
          sf = 1.0F; 
        float flag = (!this.throttleUp && this.throttleDown && getCurrentThrottle() < pivotTurnThrottle + 0.05D) ? -1.0F : 1.0F;
        if (this.moveLeft && !this.moveRight)
          setRotYaw(getRotYaw() - 0.6F * gmy * partialTicks * flag * sf); 
        if (this.moveRight && !this.moveLeft)
          setRotYaw(getRotYaw() + 0.6F * gmy * partialTicks * flag * sf); 
      } 
    } 
    this.addkeyRotValue = (float)(this.addkeyRotValue * (1.0D - (0.1F * partialTicks)));
  }
  
  protected void onUpdate_Control() {
    if (this.isGunnerMode && !canUseFuel())
      switchGunnerMode(false); 
    this.throttleBack = (float)(this.throttleBack * 0.8D);
    if (getBrake()) {
      this.throttleBack = (float)(this.throttleBack * 0.5D);
      if (getCurrentThrottle() > 0.0D) {
        addCurrentThrottle(-0.02D * (getAcInfo()).throttleUpDown);
      } else {
        setCurrentThrottle(0.0D);
      } 
    } 
    if (getRiddenByEntity() != null && !(getRiddenByEntity()).isDead && isCanopyClose() && canUseFuel() && 
      !isDestroyed()) {
      onUpdate_ControlSub();
    } else if (isTargetDrone() && canUseFuel() && !isDestroyed()) {
      this.throttleUp = true;
      onUpdate_ControlSub();
    } else if (getCurrentThrottle() > 0.0D) {
      addCurrentThrottle(-0.0025D * (getAcInfo()).throttleUpDown);
    } else {
      setCurrentThrottle(0.0D);
    } 
    if (getCurrentThrottle() < 0.0D)
      setCurrentThrottle(0.0D); 
    if (this.world.isRemote) {
      if (!W_Lib.isClientPlayer(getRiddenByEntity()) || getCountOnUpdate() % 200 == 0) {
        double ct = getThrottle();
        if (getCurrentThrottle() > ct)
          addCurrentThrottle(-0.005D); 
        if (getCurrentThrottle() < ct)
          addCurrentThrottle(0.005D); 
      } 
    } else {
      setThrottle(getCurrentThrottle());
    } 
  }
  
  protected void onUpdate_ControlSub() {
    if (!this.isGunnerMode) {
      float throttleUpDown = (getAcInfo()).throttleUpDown;
      if (this.throttleUp) {
        float f = throttleUpDown;
        if (getRidingEntity() != null) {
          double mx = (getRidingEntity()).motionX;
          double mz = (getRidingEntity()).motionZ;
          f *= MathHelper.sqrt(mx * mx + mz * mz) * (getAcInfo()).throttleUpDownOnEntity;
        } 
        if ((getAcInfo()).enableBack && this.throttleBack > 0.0F) {
          this.throttleBack = (float)(this.throttleBack - 0.01D * f);
        } else {
          this.throttleBack = 0.0F;
          if (getCurrentThrottle() < 1.0D) {
            addCurrentThrottle(0.01D * f);
          } else {
            setCurrentThrottle(1.0D);
          } 
        } 
      } else if (this.throttleDown) {
        if (getCurrentThrottle() > 0.0D) {
          addCurrentThrottle(-0.01D * throttleUpDown);
        } else {
          setCurrentThrottle(0.0D);
          if ((getAcInfo()).enableBack) {
            this.throttleBack = (float)(this.throttleBack + 0.0025D * throttleUpDown);
            if (this.throttleBack > 0.6F)
              this.throttleBack = 0.6F; 
          } 
        } 
      } else if (this.cs_tankAutoThrottleDown) {
        if (getCurrentThrottle() > 0.0D) {
          addCurrentThrottle(-0.005D * throttleUpDown);
          if (getCurrentThrottle() <= 0.0D)
            setCurrentThrottle(0.0D); 
        } 
      } 
    } 
  }
  
  protected void onUpdate_Particle2() {
    if (!this.world.isRemote)
      return; 
    if (getHP() >= getMaxHP() * 0.5D)
      return; 
    if (getTankInfo() == null)
      return; 
    int bbNum = (getTankInfo()).extraBoundingBox.size();
    if (bbNum < 0)
      bbNum = 0; 
    if (this.isFirstDamageSmoke || this.prevDamageSmokePos.length != bbNum + 1)
      this.prevDamageSmokePos = new Vec3d[bbNum + 1]; 
    float yaw = getRotYaw();
    float pitch = getRotPitch();
    float roll = getRotRoll();
    for (int ri = 0; ri < bbNum; ri++) {
      if (getHP() >= getMaxHP() * 0.2D && getMaxHP() > 0) {
        int d = (int)(((getHP() / getMaxHP()) - 0.2D) / 0.3D * 15.0D);
        if (d <= 0 || this.rand.nextInt(d) > 0);
      } else {
        MCH_BoundingBox bb = (getTankInfo()).extraBoundingBox.get(ri);
        Vec3d pos = getTransformedPosition(bb.offsetX, bb.offsetY, bb.offsetZ);
        double x = pos.xCoord;
        double y = pos.yCoord;
        double z = pos.zCoord;
        onUpdate_Particle2SpawnSmoke(ri, x, y, z, 1.0F);
      } 
    } 
    boolean b = true;
    if (getHP() >= getMaxHP() * 0.2D && getMaxHP() > 0) {
      int d = (int)(((getHP() / getMaxHP()) - 0.2D) / 0.3D * 15.0D);
      if (d > 0 && this.rand.nextInt(d) > 0)
        b = false; 
    } 
    if (b) {
      double px = this.posX;
      double py = this.posY;
      double pz = this.posZ;
      if (getSeatInfo(0) != null && (getSeatInfo(0)).pos != null) {
        Vec3d pos = MCH_Lib.RotVec3(0.0D, (getSeatInfo(0)).pos.yCoord, -2.0D, -yaw, -pitch, -roll);
        px += pos.xCoord;
        py += pos.yCoord;
        pz += pos.zCoord;
      } 
      onUpdate_Particle2SpawnSmoke(bbNum, px, py, pz, (bbNum == 0) ? 2.0F : 1.0F);
    } 
    this.isFirstDamageSmoke = false;
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
    double dx = x - prev.xCoord;
    double dy = y - prev.yCoord;
    double dz = z - prev.zCoord;
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
    int x = (int)(v.xCoord + 0.5D);
    int y = (int)(v.yCoord + 0.0D);
    int z = (int)(v.zCoord + 0.5D);
    if (W_WorldFunc.isBlockWater(this.world, x, y, z)) {
      float c = this.rand.nextFloat() * 0.3F + 0.7F;
      MCH_ParticleParam prm = new MCH_ParticleParam(this.world, "smoke", v.xCoord, v.yCoord, v.zCoord);
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
    return ((getTankInfo()).weightType == 1) ? 2 : ((getTankInfo() == null) ? 7 : 7);
  }
  
  protected void onUpdate_Client() {
    if (getRiddenByEntity() != null)
      if (W_Lib.isClientPlayer(getRiddenByEntity()))
        (getRiddenByEntity()).rotationPitch = (getRiddenByEntity()).prevRotationPitch;  
    if (this.aircraftPosRotInc > 0) {
      applyServerPositionAndRotation();
    } else {
      setPosition(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
      if (!isDestroyed() && (this.onGround || MCH_Lib.getBlockIdY((Entity)this, 1, -2) > 0)) {
        this.motionX *= 0.95D;
        this.motionZ *= 0.95D;
        applyOnGroundPitch(0.95F);
      } 
      if (isInWater()) {
        this.motionX *= 0.99D;
        this.motionZ *= 0.99D;
      } 
    } 
    updateWheels();
    onUpdate_Particle2();
    updateSound();
    if (this.world.isRemote) {
      onUpdate_ParticleLandingGear();
      onUpdate_ParticleSplash();
      onUpdate_ParticleSandCloud(true);
    } 
    updateCamera(this.posX, this.posY, this.posZ);
  }
  
  public void applyOnGroundPitch(float factor) {}
  
  private void onUpdate_Server() {
    double prevMotion = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
    double dp = 0.0D;
    if (canFloatWater())
      dp = getWaterDepth(); 
    boolean levelOff = this.isGunnerMode;
    if (dp == 0.0D) {
      if (!levelOff) {
        this.motionY += 0.04D + (!isInWater() ? (getAcInfo()).gravity : (getAcInfo()).gravityInWater);
        this.motionY += -0.047D * (1.0D - getCurrentThrottle());
      } else {
        this.motionY *= 0.8D;
      } 
    } else if (MathHelper.abs(getRotRoll()) >= 40.0F || dp < 1.0D) {
      this.motionY -= 1.0E-4D;
      this.motionY += 0.007D * getCurrentThrottle();
    } else {
      if (this.motionY < 0.0D)
        this.motionY /= 2.0D; 
      this.motionY += 0.007D;
    } 
    float throttle = (float)(getCurrentThrottle() / 10.0D);
    Vec3d v = MCH_Lib.Rot2Vec3(getRotYaw(), getRotPitch() - 10.0F);
    if (!levelOff)
      this.motionY += v.yCoord * throttle / 8.0D; 
    boolean canMove = true;
    if (!(getAcInfo()).canMoveOnGround) {
      Block block = MCH_Lib.getBlockY((Entity)this, 3, -2, false);
      if (!W_Block.isEqual(block, W_Block.getWater()) && !W_Block.isEqual(block, W_Blocks.AIR))
        canMove = false; 
    } 
    if (canMove)
      if ((getAcInfo()).enableBack && this.throttleBack > 0.0F) {
        this.motionX -= v.xCoord * this.throttleBack;
        this.motionZ -= v.zCoord * this.throttleBack;
      } else {
        this.motionX += v.xCoord * throttle;
        this.motionZ += v.zCoord * throttle;
      }  
    double motion = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
    float speedLimit = getMaxSpeed();
    if (motion > speedLimit) {
      this.motionX *= speedLimit / motion;
      this.motionZ *= speedLimit / motion;
      motion = speedLimit;
    } 
    if (motion > prevMotion && this.currentSpeed < speedLimit) {
      this.currentSpeed += (speedLimit - this.currentSpeed) / 35.0D;
      if (this.currentSpeed > speedLimit)
        this.currentSpeed = speedLimit; 
    } else {
      this.currentSpeed -= (this.currentSpeed - 0.07D) / 35.0D;
      if (this.currentSpeed < 0.07D)
        this.currentSpeed = 0.07D; 
    } 
    if (this.onGround || MCH_Lib.getBlockIdY((Entity)this, 1, -2) > 0) {
      this.motionX *= (getAcInfo()).motionFactor;
      this.motionZ *= (getAcInfo()).motionFactor;
      if (MathHelper.abs(getRotPitch()) < 40.0F)
        applyOnGroundPitch(0.8F); 
    } 
    updateWheels();
    moveEntity(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
    this.motionY *= 0.95D;
    this.motionX *= (getAcInfo()).motionFactor;
    this.motionZ *= (getAcInfo()).motionFactor;
    setRotation(getRotYaw(), getRotPitch());
    onUpdate_updateBlock();
    updateCollisionBox();
    if (getRiddenByEntity() != null && (getRiddenByEntity()).isDead)
      unmountEntity(); 
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
    List<Entity> list = this.world.getEntitiesInAABBexcluding((Entity)this, bb.expand(0.3D, 0.3D, 0.3D), e -> {
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
          ds = DamageSource.generic;
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
            ds = DamageSource.generic;
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
          int bx = (int)(v.xCoord + x - 0.5D);
          int by = (int)(v.yCoord + y - 1.0D);
          int bz = (int)(v.zCoord + z - 0.5D);
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
  
  public void setAngles(Entity player, boolean fixRot, float fixYaw, float fixPitch, float deltaX, float deltaY, float x, float y, float partialTicks) {
    if (partialTicks < 0.03F)
      partialTicks = 0.4F; 
    if (partialTicks > 0.9F)
      partialTicks = 0.6F; 
    this.lowPassPartialTicks.put(partialTicks);
    partialTicks = this.lowPassPartialTicks.getAvg();
    float ac_pitch = getRotPitch();
    float ac_yaw = getRotYaw();
    float ac_roll = getRotRoll();
    if (isFreeLookMode())
      x = y = 0.0F; 
    float yaw = 0.0F;
    float pitch = 0.0F;
    float roll = 0.0F;
    MCH_Math.FMatrix m_add = MCH_Math.newMatrix();
    MCH_Math.MatTurnZ(m_add, roll / 180.0F * 3.1415927F);
    MCH_Math.MatTurnX(m_add, pitch / 180.0F * 3.1415927F);
    MCH_Math.MatTurnY(m_add, yaw / 180.0F * 3.1415927F);
    MCH_Math.MatTurnZ(m_add, (float)((getRotRoll() / 180.0F) * Math.PI));
    MCH_Math.MatTurnX(m_add, (float)((getRotPitch() / 180.0F) * Math.PI));
    MCH_Math.MatTurnY(m_add, (float)((getRotYaw() / 180.0F) * Math.PI));
    MCH_Math.FVector3D v = MCH_Math.MatrixToEuler(m_add);
    v.x = MCH_Lib.RNG(v.x, -90.0F, 90.0F);
    v.z = MCH_Lib.RNG(v.z, -90.0F, 90.0F);
    if (v.z > 180.0F)
      v.z -= 360.0F; 
    if (v.z < -180.0F)
      v.z += 360.0F; 
    setRotYaw(v.y);
    setRotPitch(v.x);
    setRotRoll(v.z);
    onUpdateAngles(partialTicks);
    if ((getAcInfo()).limitRotation) {
      v.x = MCH_Lib.RNG(getRotPitch(), -90.0F, 90.0F);
      v.z = MCH_Lib.RNG(getRotRoll(), -90.0F, 90.0F);
      setRotPitch(v.x);
      setRotRoll(v.z);
    } 
    if (MathHelper.abs(getRotPitch()) > 90.0F) {
      MCH_Lib.DbgLog(true, "MCH_EntityAircraft.setAngles Error:Pitch=%.1f", new Object[] { Float.valueOf(getRotPitch()) });
      setRotPitch(0.0F);
    } 
    if (getRotRoll() > 180.0F)
      setRotRoll(getRotRoll() - 360.0F); 
    if (getRotRoll() < -180.0F)
      setRotRoll(getRotRoll() + 360.0F); 
    this.prevRotationRoll = getRotRoll();
    this.prevRotationPitch = getRotPitch();
    if (getRidingEntity() == null)
      this.prevRotationYaw = getRotYaw(); 
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
      player.setAngles(deltaX, 0.0F);
    } 
    if (isOverridePlayerPitch() || fixRot) {
      player.prevRotationPitch = getRotPitch() + fixPitch;
      player.rotationPitch = getRotPitch() + fixPitch;
    } else {
      player.setAngles(0.0F, deltaY);
    } 
    float playerYaw = MathHelper.wrapDegrees(getRotYaw() - player.rotationYaw);
    float playerPitch = getRotPitch() * MathHelper.cos((float)(playerYaw * Math.PI / 180.0D)) + -getRotRoll() * MathHelper.sin((float)(playerYaw * Math.PI / 180.0D));
    if (MCH_MOD.proxy.isFirstPerson()) {
      player.rotationPitch = MCH_Lib.RNG(player.rotationPitch, playerPitch + (getAcInfo()).minRotationPitch, playerPitch + 
          (getAcInfo()).maxRotationPitch);
      player.rotationPitch = MCH_Lib.RNG(player.rotationPitch, -90.0F, 90.0F);
    } 
    player.prevRotationPitch = player.rotationPitch;
    if ((getRidingEntity() == null && ac_yaw != getRotYaw()) || ac_pitch != getRotPitch() || ac_roll != 
      getRotRoll())
      this.aircraftRotChanged = true; 
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

  @Override
  public boolean isEmpty() {
    return false;
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
