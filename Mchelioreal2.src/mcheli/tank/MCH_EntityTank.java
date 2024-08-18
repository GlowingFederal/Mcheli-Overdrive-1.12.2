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
    this.field_70156_m = true;
    func_70105_a(2.0F, 0.7F);
    this.field_70159_w = 0.0D;
    this.field_70181_x = 0.0D;
    this.field_70179_y = 0.0D;
    this.weapons = createWeapon(0);
    this.soundVolume = 0.0F;
    this.field_70138_W = 0.6F;
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
    MCH_Lib.DbgLog(this.field_70170_p, "MCH_EntityTank.changeType " + type + " : " + toString(), new Object[0]);
    if (!type.isEmpty())
      this.tankInfo = MCH_TankInfoManager.get(type); 
    if (this.tankInfo == null) {
      MCH_Lib.Log((Entity)this, "##### MCH_EntityTank changeTankType() Tank info null %d, %s, %s", new Object[] { Integer.valueOf(W_Entity.getEntityId((Entity)this)), type, getEntityName() });
      func_70106_y();
    } else {
      setAcInfo(this.tankInfo);
      newSeats(getAcInfo().getNumSeatAndRack());
      switchFreeLookModeClient((getAcInfo()).defaultFreelook);
      this.weapons = createWeapon(1 + getSeatNum());
      initPartRotation(getRotYaw(), getRotPitch());
      this.WheelMng.createWheels(this.field_70170_p, (getAcInfo()).wheels, new Vec3d(0.0D, -0.35D, 
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
  
  protected void func_70088_a() {
    super.func_70088_a();
  }
  
  public float getGiveDamageRot() {
    return 91.0F;
  }
  
  protected void func_70014_b(NBTTagCompound par1NBTTagCompound) {
    super.func_70014_b(par1NBTTagCompound);
  }
  
  protected void func_70037_a(NBTTagCompound par1NBTTagCompound) {
    super.func_70037_a(par1NBTTagCompound);
    if (this.tankInfo == null) {
      this.tankInfo = MCH_TankInfoManager.get(getTypeName());
      if (this.tankInfo == null) {
        MCH_Lib.Log((Entity)this, "##### MCH_EntityTank readEntityFromNBT() Tank info null %d, %s", new Object[] { Integer.valueOf(W_Entity.getEntityId((Entity)this)), getEntityName() });
        func_70106_y();
      } else {
        setAcInfo(this.tankInfo);
      } 
    } 
  }
  
  public void func_70106_y() {
    super.func_70106_y();
  }
  
  public void onInteractFirst(EntityPlayer player) {
    this.addkeyRotValue = 0.0F;
    player.field_70759_as = player.field_70758_at = getLastRiderYaw();
    player.field_70126_B = player.field_70177_z = getLastRiderYaw();
    player.field_70125_A = getLastRiderPitch();
  }
  
  public boolean canSwitchGunnerMode() {
    if (!super.canSwitchGunnerMode())
      return false; 
    return false;
  }
  
  public void onUpdateAircraft() {
    if (this.tankInfo == null) {
      changeType(getTypeName());
      this.field_70169_q = this.field_70165_t;
      this.field_70167_r = this.field_70163_u;
      this.field_70166_s = this.field_70161_v;
      return;
    } 
    if (!this.isRequestedSyncStatus) {
      this.isRequestedSyncStatus = true;
      if (this.field_70170_p.field_72995_K)
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
    this.field_70169_q = this.field_70165_t;
    this.field_70167_r = this.field_70163_u;
    this.field_70166_s = this.field_70161_v;
    if (isDestroyed())
      if (getCurrentThrottle() > 0.0D) {
        if (MCH_Lib.getBlockIdY((Entity)this, 3, -2) > 0)
          setCurrentThrottle(getCurrentThrottle() * 0.8D); 
        if (isExploded())
          setCurrentThrottle(getCurrentThrottle() * 0.98D); 
      }  
    updateCameraViewers();
    if (this.field_70170_p.field_72995_K) {
      onUpdate_Client();
    } else {
      onUpdate_Server();
    } 
  }
  
  @SideOnly(Side.CLIENT)
  public boolean func_90999_ad() {
    return (isDestroyed() || super.func_90999_ad());
  }
  
  public void updateExtraBoundingBox() {
    if (this.field_70170_p.field_72995_K) {
      super.updateExtraBoundingBox();
    } else if (getCountOnUpdate() <= 1) {
      super.updateExtraBoundingBox();
      super.updateExtraBoundingBox();
    } 
  }
  
  public ClacAxisBB calculateXOffset(List<AxisAlignedBB> list, AxisAlignedBB bb, double x) {
    for (int j5 = 0; j5 < list.size(); j5++)
      x = ((AxisAlignedBB)list.get(j5)).func_72316_a(bb, x); 
    return new ClacAxisBB(x, bb.func_72317_d(x, 0.0D, 0.0D));
  }
  
  public ClacAxisBB calculateYOffset(List<AxisAlignedBB> list, AxisAlignedBB bb, double y) {
    return calculateYOffset(list, bb, bb, y);
  }
  
  public ClacAxisBB calculateYOffset(List<AxisAlignedBB> list, AxisAlignedBB calcBB, AxisAlignedBB offsetBB, double y) {
    for (int k = 0; k < list.size(); k++)
      y = ((AxisAlignedBB)list.get(k)).func_72323_b(calcBB, y); 
    return new ClacAxisBB(y, offsetBB.func_72317_d(0.0D, y, 0.0D));
  }
  
  public ClacAxisBB calculateZOffset(List<AxisAlignedBB> list, AxisAlignedBB bb, double z) {
    for (int k5 = 0; k5 < list.size(); k5++)
      z = ((AxisAlignedBB)list.get(k5)).func_72322_c(bb, z); 
    return new ClacAxisBB(z, bb.func_72317_d(0.0D, 0.0D, z));
  }
  
  public void func_70091_d(MoverType type, double x, double y, double z) {
    this.field_70170_p.field_72984_F.func_76320_a("move");
    double d2 = x;
    double d3 = y;
    double d4 = z;
    List<AxisAlignedBB> list1 = getCollisionBoxes((Entity)this, func_174813_aQ().func_72321_a(x, y, z));
    AxisAlignedBB axisalignedbb = func_174813_aQ();
    if (y != 0.0D) {
      ClacAxisBB v = calculateYOffset(list1, func_174813_aQ(), y);
      y = v.value;
      func_174826_a(v.bb);
    } 
    boolean flag = (this.field_70122_E || (d3 != y && d3 < 0.0D));
    for (MCH_BoundingBox ebb : this.extraBoundingBox)
      ebb.updatePosition(this.field_70165_t, this.field_70163_u, this.field_70161_v, getRotYaw(), getRotPitch(), getRotRoll()); 
    if (x != 0.0D) {
      ClacAxisBB v = calculateXOffset(list1, func_174813_aQ(), x);
      x = v.value;
      if (x != 0.0D)
        func_174826_a(v.bb); 
    } 
    if (z != 0.0D) {
      ClacAxisBB v = calculateZOffset(list1, func_174813_aQ(), z);
      z = v.value;
      if (z != 0.0D)
        func_174826_a(v.bb); 
    } 
    if (this.field_70138_W > 0.0F && flag && (d2 != x || d4 != z)) {
      double d14 = x;
      double d6 = y;
      double d7 = z;
      AxisAlignedBB axisalignedbb1 = func_174813_aQ();
      func_174826_a(axisalignedbb);
      y = this.field_70138_W;
      List<AxisAlignedBB> list = getCollisionBoxes((Entity)this, func_174813_aQ().func_72321_a(d2, y, d4));
      AxisAlignedBB axisalignedbb2 = func_174813_aQ();
      AxisAlignedBB axisalignedbb3 = axisalignedbb2.func_72321_a(d2, 0.0D, d4);
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
      AxisAlignedBB axisalignedbb4 = func_174813_aQ();
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
        func_174826_a(axisalignedbb2);
      } else {
        x = d21;
        z = d22;
        y = -d20;
        func_174826_a(axisalignedbb4);
      } 
      v = calculateYOffset(list, func_174813_aQ(), y);
      y = v.value;
      func_174826_a(v.bb);
      if (d14 * d14 + d7 * d7 >= x * x + z * z) {
        x = d14;
        y = d6;
        z = d7;
        func_174826_a(axisalignedbb1);
      } 
    } 
    this.field_70170_p.field_72984_F.func_76319_b();
    this.field_70170_p.field_72984_F.func_76320_a("rest");
    func_174829_m();
    this.field_70123_F = (d2 != x || d4 != z);
    this.field_70124_G = (d3 != y);
    this.field_70122_E = (this.field_70124_G && d3 < 0.0D);
    this.field_70132_H = (this.field_70123_F || this.field_70124_G);
    int j6 = MathHelper.func_76128_c(this.field_70165_t);
    int i1 = MathHelper.func_76128_c(this.field_70163_u - 0.20000000298023224D);
    int k6 = MathHelper.func_76128_c(this.field_70161_v);
    BlockPos blockpos = new BlockPos(j6, i1, k6);
    IBlockState iblockstate = this.field_70170_p.func_180495_p(blockpos);
    if (iblockstate.func_185904_a() == Material.field_151579_a) {
      BlockPos blockpos1 = blockpos.func_177977_b();
      IBlockState iblockstate1 = this.field_70170_p.func_180495_p(blockpos1);
      Block block1 = iblockstate1.func_177230_c();
      if (block1 instanceof net.minecraft.block.BlockFence || block1 instanceof net.minecraft.block.BlockWall || block1 instanceof net.minecraft.block.BlockFenceGate) {
        iblockstate = iblockstate1;
        blockpos = blockpos1;
      } 
    } 
    func_184231_a(y, this.field_70122_E, iblockstate, blockpos);
    if (d2 != x)
      this.field_70159_w = 0.0D; 
    if (d4 != z)
      this.field_70179_y = 0.0D; 
    Block block = iblockstate.func_177230_c();
    if (d3 != y)
      block.func_176216_a(this.field_70170_p, (Entity)this); 
    try {
      func_145775_I();
    } catch (Throwable throwable) {
      CrashReport crashreport = CrashReport.func_85055_a(throwable, "Checking entity block collision");
      CrashReportCategory crashreportcategory = crashreport.func_85058_a("Entity being checked for collision");
      func_85029_a(crashreportcategory);
      throw new ReportedException(crashreport);
    } 
    this.field_70170_p.field_72984_F.func_76319_b();
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
      if (MathHelper.func_76135_e(getRotRoll()) > 20.0F)
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
          if (!W_Block.isEqual(block, W_Block.getWater()) && !W_Block.isEqual(block, W_Blocks.field_150350_a))
            gmy = 0.0F; 
        } 
      } 
      float pivotTurnThrottle = (getAcInfo()).pivotTurnThrottle;
      double dx = this.field_70165_t - this.field_70169_q;
      double dz = this.field_70161_v - this.field_70166_s;
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
    if (getRiddenByEntity() != null && !(getRiddenByEntity()).field_70128_L && isCanopyClose() && canUseFuel() && 
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
    if (this.field_70170_p.field_72995_K) {
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
        if (func_184187_bx() != null) {
          double mx = (func_184187_bx()).field_70159_w;
          double mz = (func_184187_bx()).field_70179_y;
          f *= MathHelper.func_76133_a(mx * mx + mz * mz) * (getAcInfo()).throttleUpDownOnEntity;
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
    if (!this.field_70170_p.field_72995_K)
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
        if (d <= 0 || this.field_70146_Z.nextInt(d) > 0);
      } else {
        MCH_BoundingBox bb = (getTankInfo()).extraBoundingBox.get(ri);
        Vec3d pos = getTransformedPosition(bb.offsetX, bb.offsetY, bb.offsetZ);
        double x = pos.field_72450_a;
        double y = pos.field_72448_b;
        double z = pos.field_72449_c;
        onUpdate_Particle2SpawnSmoke(ri, x, y, z, 1.0F);
      } 
    } 
    boolean b = true;
    if (getHP() >= getMaxHP() * 0.2D && getMaxHP() > 0) {
      int d = (int)(((getHP() / getMaxHP()) - 0.2D) / 0.3D * 15.0D);
      if (d > 0 && this.field_70146_Z.nextInt(d) > 0)
        b = false; 
    } 
    if (b) {
      double px = this.field_70165_t;
      double py = this.field_70163_u;
      double pz = this.field_70161_v;
      if (getSeatInfo(0) != null && (getSeatInfo(0)).pos != null) {
        Vec3d pos = MCH_Lib.RotVec3(0.0D, (getSeatInfo(0)).pos.field_72448_b, -2.0D, -yaw, -pitch, -roll);
        px += pos.field_72450_a;
        py += pos.field_72448_b;
        pz += pos.field_72449_c;
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
      float c = 0.2F + this.field_70146_Z.nextFloat() * 0.3F;
      MCH_ParticleParam prm = new MCH_ParticleParam(this.field_70170_p, "smoke", x, y, z);
      prm.motionX = size * (this.field_70146_Z.nextDouble() - 0.5D) * 0.3D;
      prm.motionY = size * this.field_70146_Z.nextDouble() * 0.1D;
      prm.motionZ = size * (this.field_70146_Z.nextDouble() - 0.5D) * 0.3D;
      prm.size = size * (this.field_70146_Z.nextInt(5) + 5.0F) * 1.0F;
      prm.setColor(0.7F + this.field_70146_Z.nextFloat() * 0.1F, c, c, c);
      MCH_ParticlesUtil.spawnParticle(prm);
    } 
    this.prevDamageSmokePos[ri] = new Vec3d(x, y, z);
  }
  
  public void onUpdate_Particle2SpawnSmode(int ri, double x, double y, double z, float size) {
    if (this.isFirstDamageSmoke)
      this.prevDamageSmokePos[ri] = new Vec3d(x, y, z); 
    Vec3d prev = this.prevDamageSmokePos[ri];
    double dx = x - prev.field_72450_a;
    double dy = y - prev.field_72448_b;
    double dz = z - prev.field_72449_c;
    int num = (int)(MathHelper.func_76133_a(dx * dx + dy * dy + dz * dz) / 0.3D) + 1;
    for (int i = 0; i < num; i++) {
      float c = 0.2F + this.field_70146_Z.nextFloat() * 0.3F;
      MCH_ParticleParam prm = new MCH_ParticleParam(this.field_70170_p, "smoke", x, y, z);
      prm.motionX = size * (this.field_70146_Z.nextDouble() - 0.5D) * 0.3D;
      prm.motionY = size * this.field_70146_Z.nextDouble() * 0.1D;
      prm.motionZ = size * (this.field_70146_Z.nextDouble() - 0.5D) * 0.3D;
      prm.size = size * (this.field_70146_Z.nextInt(5) + 5.0F) * 1.0F;
      prm.setColor(0.7F + this.field_70146_Z.nextFloat() * 0.1F, c, c, c);
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
    if (!this.field_70170_p.field_72995_K)
      return; 
    double mx = this.field_70165_t - this.field_70169_q;
    double mz = this.field_70161_v - this.field_70166_s;
    double dist = mx * mx + mz * mz;
    if (dist > 1.0D)
      dist = 1.0D; 
    for (MCH_AircraftInfo.ParticleSplash p : (getAcInfo()).particleSplashs) {
      for (int i = 0; i < p.num; i++) {
        if (dist > 0.03D + this.field_70146_Z.nextFloat() * 0.1D)
          setParticleSplash(p.pos, -mx * p.acceleration, p.motionY, -mz * p.acceleration, p.gravity, p.size * (0.5D + dist * 0.5D), p.age); 
      } 
    } 
  }
  
  private void setParticleSplash(Vec3d pos, double mx, double my, double mz, float gravity, double size, int age) {
    Vec3d v = getTransformedPosition(pos);
    v = v.func_72441_c(this.field_70146_Z.nextDouble() - 0.5D, (this.field_70146_Z.nextDouble() - 0.5D) * 0.5D, this.field_70146_Z
        .nextDouble() - 0.5D);
    int x = (int)(v.field_72450_a + 0.5D);
    int y = (int)(v.field_72448_b + 0.0D);
    int z = (int)(v.field_72449_c + 0.5D);
    if (W_WorldFunc.isBlockWater(this.field_70170_p, x, y, z)) {
      float c = this.field_70146_Z.nextFloat() * 0.3F + 0.7F;
      MCH_ParticleParam prm = new MCH_ParticleParam(this.field_70170_p, "smoke", v.field_72450_a, v.field_72448_b, v.field_72449_c);
      prm.motionX = mx + (this.field_70146_Z.nextFloat() - 0.5D) * 0.7D;
      prm.motionY = my;
      prm.motionZ = mz + (this.field_70146_Z.nextFloat() - 0.5D) * 0.7D;
      prm.size = (float)size * (this.field_70146_Z.nextFloat() * 0.2F + 0.8F);
      prm.setColor(0.9F, c, c, c);
      prm.age = age + (int)(this.field_70146_Z.nextFloat() * 0.5D * age);
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
        (getRiddenByEntity()).field_70125_A = (getRiddenByEntity()).field_70127_C;  
    if (this.aircraftPosRotInc > 0) {
      applyServerPositionAndRotation();
    } else {
      func_70107_b(this.field_70165_t + this.field_70159_w, this.field_70163_u + this.field_70181_x, this.field_70161_v + this.field_70179_y);
      if (!isDestroyed() && (this.field_70122_E || MCH_Lib.getBlockIdY((Entity)this, 1, -2) > 0)) {
        this.field_70159_w *= 0.95D;
        this.field_70179_y *= 0.95D;
        applyOnGroundPitch(0.95F);
      } 
      if (func_70090_H()) {
        this.field_70159_w *= 0.99D;
        this.field_70179_y *= 0.99D;
      } 
    } 
    updateWheels();
    onUpdate_Particle2();
    updateSound();
    if (this.field_70170_p.field_72995_K) {
      onUpdate_ParticleLandingGear();
      onUpdate_ParticleSplash();
      onUpdate_ParticleSandCloud(true);
    } 
    updateCamera(this.field_70165_t, this.field_70163_u, this.field_70161_v);
  }
  
  public void applyOnGroundPitch(float factor) {}
  
  private void onUpdate_Server() {
    double prevMotion = Math.sqrt(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
    double dp = 0.0D;
    if (canFloatWater())
      dp = getWaterDepth(); 
    boolean levelOff = this.isGunnerMode;
    if (dp == 0.0D) {
      if (!levelOff) {
        this.field_70181_x += 0.04D + (!func_70090_H() ? (getAcInfo()).gravity : (getAcInfo()).gravityInWater);
        this.field_70181_x += -0.047D * (1.0D - getCurrentThrottle());
      } else {
        this.field_70181_x *= 0.8D;
      } 
    } else if (MathHelper.func_76135_e(getRotRoll()) >= 40.0F || dp < 1.0D) {
      this.field_70181_x -= 1.0E-4D;
      this.field_70181_x += 0.007D * getCurrentThrottle();
    } else {
      if (this.field_70181_x < 0.0D)
        this.field_70181_x /= 2.0D; 
      this.field_70181_x += 0.007D;
    } 
    float throttle = (float)(getCurrentThrottle() / 10.0D);
    Vec3d v = MCH_Lib.Rot2Vec3(getRotYaw(), getRotPitch() - 10.0F);
    if (!levelOff)
      this.field_70181_x += v.field_72448_b * throttle / 8.0D; 
    boolean canMove = true;
    if (!(getAcInfo()).canMoveOnGround) {
      Block block = MCH_Lib.getBlockY((Entity)this, 3, -2, false);
      if (!W_Block.isEqual(block, W_Block.getWater()) && !W_Block.isEqual(block, W_Blocks.field_150350_a))
        canMove = false; 
    } 
    if (canMove)
      if ((getAcInfo()).enableBack && this.throttleBack > 0.0F) {
        this.field_70159_w -= v.field_72450_a * this.throttleBack;
        this.field_70179_y -= v.field_72449_c * this.throttleBack;
      } else {
        this.field_70159_w += v.field_72450_a * throttle;
        this.field_70179_y += v.field_72449_c * throttle;
      }  
    double motion = Math.sqrt(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
    float speedLimit = getMaxSpeed();
    if (motion > speedLimit) {
      this.field_70159_w *= speedLimit / motion;
      this.field_70179_y *= speedLimit / motion;
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
    if (this.field_70122_E || MCH_Lib.getBlockIdY((Entity)this, 1, -2) > 0) {
      this.field_70159_w *= (getAcInfo()).motionFactor;
      this.field_70179_y *= (getAcInfo()).motionFactor;
      if (MathHelper.func_76135_e(getRotPitch()) < 40.0F)
        applyOnGroundPitch(0.8F); 
    } 
    updateWheels();
    func_70091_d(MoverType.SELF, this.field_70159_w, this.field_70181_x, this.field_70179_y);
    this.field_70181_x *= 0.95D;
    this.field_70159_w *= (getAcInfo()).motionFactor;
    this.field_70179_y *= (getAcInfo()).motionFactor;
    func_70101_b(getRotYaw(), getRotPitch());
    onUpdate_updateBlock();
    updateCollisionBox();
    if (getRiddenByEntity() != null && (getRiddenByEntity()).field_70128_L)
      unmountEntity(); 
  }
  
  private void collisionEntity(AxisAlignedBB bb) {
    if (bb == null)
      return; 
    double speed = Math.sqrt(this.field_70159_w * this.field_70159_w + this.field_70181_x * this.field_70181_x + this.field_70179_y * this.field_70179_y);
    if (speed <= 0.05D)
      return; 
    Entity rider = getRiddenByEntity();
    float damage = (float)(speed * 15.0D);
    MCH_EntityAircraft rideAc = (func_184187_bx() instanceof MCH_EntitySeat) ? ((MCH_EntitySeat)func_184187_bx()).getParent() : ((func_184187_bx() instanceof MCH_EntityAircraft) ? (MCH_EntityAircraft)func_184187_bx() : null);
    List<Entity> list = this.field_70170_p.func_175674_a((Entity)this, bb.func_72314_b(0.3D, 0.3D, 0.3D), e -> {
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
        double dx = e.field_70165_t - this.field_70165_t;
        double dz = e.field_70161_v - this.field_70161_v;
        double dist = Math.sqrt(dx * dx + dz * dz);
        if (dist > 5.0D)
          dist = 5.0D; 
        damage = (float)(damage + 5.0D - dist);
        if (rider instanceof EntityLivingBase) {
          ds = DamageSource.func_76358_a((EntityLivingBase)rider);
        } else {
          ds = DamageSource.field_76377_j;
        } 
        MCH_Lib.applyEntityHurtResistantTimeConfig(e);
        e.func_70097_a(ds, damage);
        if (e instanceof MCH_EntityAircraft) {
          e.field_70159_w += this.field_70159_w * 0.05D;
          e.field_70179_y += this.field_70179_y * 0.05D;
        } else if (e instanceof net.minecraft.entity.projectile.EntityArrow) {
          e.func_70106_y();
        } else {
          e.field_70159_w += this.field_70159_w * 1.5D;
          e.field_70179_y += this.field_70179_y * 1.5D;
        } 
        if ((getTankInfo()).weightType != 2 && (e.field_70130_N >= 1.0F || e.field_70131_O >= 1.5D)) {
          if (e instanceof EntityLivingBase) {
            ds = DamageSource.func_76358_a((EntityLivingBase)e);
          } else {
            ds = DamageSource.field_76377_j;
          } 
          func_70097_a(ds, damage / 3.0F);
        } 
        MCH_Lib.DbgLog(this.field_70170_p, "MCH_EntityTank.collisionEntity damage=%.1f %s", new Object[] { Float.valueOf(damage), e.toString() });
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
    if (e.func_184187_bx() instanceof MCH_EntityAircraft)
      if (this.noCollisionEntities.containsKey(e.func_184187_bx()))
        return false;  
    if (e.func_184187_bx() instanceof MCH_EntitySeat && ((MCH_EntitySeat)e.func_184187_bx()).getParent() != null)
      if (this.noCollisionEntities.containsKey(((MCH_EntitySeat)e.func_184187_bx()).getParent()))
        return false;  
    return true;
  }
  
  public void updateCollisionBox() {
    if (getAcInfo() == null)
      return; 
    this.WheelMng.updateBlock();
    for (MCH_BoundingBox bb : this.extraBoundingBox) {
      if (this.field_70146_Z.nextInt(3) == 0) {
        if (MCH_Config.Collision_DestroyBlock.prmBool) {
          Vec3d v = getTransformedPosition(bb.offsetX, bb.offsetY, bb.offsetZ);
          destoryBlockRange(v, bb.width, bb.height);
        } 
        collisionEntity(bb.getBoundingBox());
      } 
    } 
    if (MCH_Config.Collision_DestroyBlock.prmBool)
      destoryBlockRange(getTransformedPosition(0.0D, 0.0D, 0.0D), this.field_70130_N * 1.5D, (this.field_70131_O * 2.0F)); 
    collisionEntity(func_70046_E());
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
          int bx = (int)(v.field_72450_a + x - 0.5D);
          int by = (int)(v.field_72448_b + y - 1.0D);
          int bz = (int)(v.field_72449_c + z - 0.5D);
          BlockPos blockpos = new BlockPos(bx, by, bz);
          IBlockState iblockstate = this.field_70170_p.func_180495_p(blockpos);
          Block block = (by >= 0 && by < 256) ? iblockstate.func_177230_c() : Blocks.field_150350_a;
          Material mat = iblockstate.func_185904_a();
          if (!Block.func_149680_a(block, Blocks.field_150350_a)) {
            for (Block c : noDestroyBlocks) {
              if (Block.func_149680_a(block, c)) {
                block = null;
                break;
              } 
            } 
            if (block == null)
              break; 
            for (Block c : destroyBlocks) {
              if (Block.func_149680_a(block, c)) {
                destroyBlock(blockpos);
                mat = null;
                break;
              } 
            } 
            if (mat == null)
              break; 
            for (Material m : destroyMaterials) {
              if (iblockstate.func_185904_a() == m) {
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
    if (this.field_70146_Z.nextInt(8) == 0) {
      W_WorldFunc.destroyBlock(this.field_70170_p, blockpos, true);
    } else {
      this.field_70170_p.func_175698_g(blockpos);
    } 
  }
  
  private void updateWheels() {
    this.WheelMng.move(this.field_70159_w, this.field_70181_x, this.field_70179_y);
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
    if (MathHelper.func_76135_e(getRotPitch()) > 90.0F) {
      MCH_Lib.DbgLog(true, "MCH_EntityAircraft.setAngles Error:Pitch=%.1f", new Object[] { Float.valueOf(getRotPitch()) });
      setRotPitch(0.0F);
    } 
    if (getRotRoll() > 180.0F)
      setRotRoll(getRotRoll() - 360.0F); 
    if (getRotRoll() < -180.0F)
      setRotRoll(getRotRoll() + 360.0F); 
    this.prevRotationRoll = getRotRoll();
    this.field_70127_C = getRotPitch();
    if (func_184187_bx() == null)
      this.field_70126_B = getRotYaw(); 
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
      if (func_184187_bx() == null) {
        player.field_70126_B = getRotYaw() + fixYaw;
      } else {
        if (getRotYaw() - player.field_70177_z > 180.0F)
          player.field_70126_B += 360.0F; 
        if (getRotYaw() - player.field_70177_z < -180.0F)
          player.field_70126_B -= 360.0F; 
      } 
      player.field_70177_z = getRotYaw() + fixYaw;
    } else {
      player.func_70082_c(deltaX, 0.0F);
    } 
    if (isOverridePlayerPitch() || fixRot) {
      player.field_70127_C = getRotPitch() + fixPitch;
      player.field_70125_A = getRotPitch() + fixPitch;
    } else {
      player.func_70082_c(0.0F, deltaY);
    } 
    float playerYaw = MathHelper.func_76142_g(getRotYaw() - player.field_70177_z);
    float playerPitch = getRotPitch() * MathHelper.func_76134_b((float)(playerYaw * Math.PI / 180.0D)) + -getRotRoll() * MathHelper.func_76126_a((float)(playerYaw * Math.PI / 180.0D));
    if (MCH_MOD.proxy.isFirstPerson()) {
      player.field_70125_A = MCH_Lib.RNG(player.field_70125_A, playerPitch + (getAcInfo()).minRotationPitch, playerPitch + 
          (getAcInfo()).maxRotationPitch);
      player.field_70125_A = MCH_Lib.RNG(player.field_70125_A, -90.0F, 90.0F);
    } 
    player.field_70127_C = player.field_70125_A;
    if ((func_184187_bx() == null && ac_yaw != getRotYaw()) || ac_pitch != getRotPitch() || ac_roll != 
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
  
  private static class ClacAxisBB {
    public final double value;
    
    public final AxisAlignedBB bb;
    
    public ClacAxisBB(double value, AxisAlignedBB bb) {
      this.value = value;
      this.bb = bb;
    }
  }
}
