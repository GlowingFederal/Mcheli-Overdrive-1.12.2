package mcheli.plane;

import javax.annotation.Nullable;
import mcheli.MCH_Config;
import mcheli.MCH_Lib;
import mcheli.aircraft.MCH_AircraftInfo;
import mcheli.aircraft.MCH_EntityAircraft;
import mcheli.aircraft.MCH_PacketStatusRequest;
import mcheli.aircraft.MCH_Parts;
import mcheli.particles.MCH_ParticleParam;
import mcheli.particles.MCH_ParticlesUtil;
import mcheli.wrapper.W_Block;
import mcheli.wrapper.W_Blocks;
import mcheli.wrapper.W_Entity;
import mcheli.wrapper.W_Lib;
import mcheli.wrapper.W_WorldFunc;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class MCP_EntityPlane extends MCH_EntityAircraft {
  private MCP_PlaneInfo planeInfo;
  
  public float soundVolume;
  
  public MCH_Parts partNozzle;
  
  public MCH_Parts partWing;
  
  public float rotationRotor;
  
  public float prevRotationRotor;
  
  public float addkeyRotValue;
  
  public MCP_EntityPlane(World world) {
    super(world);
    this.planeInfo = null;
    this.currentSpeed = 0.07D;
    this.field_70156_m = true;
    func_70105_a(2.0F, 0.7F);
    this.field_70159_w = 0.0D;
    this.field_70181_x = 0.0D;
    this.field_70179_y = 0.0D;
    this.weapons = createWeapon(0);
    this.soundVolume = 0.0F;
    this.partNozzle = null;
    this.partWing = null;
    this.field_70138_W = 0.6F;
    this.rotationRotor = 0.0F;
    this.prevRotationRotor = 0.0F;
  }
  
  public String getKindName() {
    return "planes";
  }
  
  public String getEntityType() {
    return "Plane";
  }
  
  public MCP_PlaneInfo getPlaneInfo() {
    return this.planeInfo;
  }
  
  public void changeType(String type) {
    MCH_Lib.DbgLog(this.field_70170_p, "MCP_EntityPlane.changeType " + type + " : " + toString(), new Object[0]);
    if (!type.isEmpty())
      this.planeInfo = MCP_PlaneInfoManager.get(type); 
    if (this.planeInfo == null) {
      MCH_Lib.Log((Entity)this, "##### MCP_EntityPlane changePlaneType() Plane info null %d, %s, %s", new Object[] { Integer.valueOf(W_Entity.getEntityId((Entity)this)), type, getEntityName() });
      func_70106_y();
    } else {
      setAcInfo(this.planeInfo);
      newSeats(getAcInfo().getNumSeatAndRack());
      this.partNozzle = createNozzle(this.planeInfo);
      this.partWing = createWing(this.planeInfo);
      this.weapons = createWeapon(1 + getSeatNum());
      initPartRotation(getRotYaw(), getRotPitch());
    } 
  }
  
  @Nullable
  public Item getItem() {
    return (getPlaneInfo() != null) ? (Item)(getPlaneInfo()).item : null;
  }
  
  public boolean canMountWithNearEmptyMinecart() {
    return MCH_Config.MountMinecartPlane.prmBool;
  }
  
  protected void func_70088_a() {
    super.func_70088_a();
  }
  
  protected void func_70014_b(NBTTagCompound par1NBTTagCompound) {
    super.func_70014_b(par1NBTTagCompound);
  }
  
  protected void func_70037_a(NBTTagCompound par1NBTTagCompound) {
    super.func_70037_a(par1NBTTagCompound);
    if (this.planeInfo == null) {
      this.planeInfo = MCP_PlaneInfoManager.get(getTypeName());
      if (this.planeInfo == null) {
        MCH_Lib.Log((Entity)this, "##### MCP_EntityPlane readEntityFromNBT() Plane info null %d, %s", new Object[] { Integer.valueOf(W_Entity.getEntityId((Entity)this)), getEntityName() });
        func_70106_y();
      } else {
        setAcInfo(this.planeInfo);
      } 
    } 
  }
  
  public void func_70106_y() {
    super.func_70106_y();
  }
  
  public int getNumEjectionSeat() {
    if (getAcInfo() != null)
      if ((getAcInfo()).isEnableEjectionSeat) {
        int n = getSeatNum() + 1;
        return (n <= 2) ? n : 0;
      }  
    return 0;
  }
  
  public void onInteractFirst(EntityPlayer player) {
    this.addkeyRotValue = 0.0F;
  }
  
  public boolean canSwitchGunnerMode() {
    if (!super.canSwitchGunnerMode())
      return false; 
    float roll = MathHelper.func_76135_e(MathHelper.func_76142_g(getRotRoll()));
    float pitch = MathHelper.func_76135_e(MathHelper.func_76142_g(getRotPitch()));
    if (roll > 40.0F || pitch > 40.0F)
      return false; 
    return (getCurrentThrottle() > 0.6000000238418579D && MCH_Lib.getBlockIdY((Entity)this, 3, -5) == 0);
  }
  
  public void onUpdateAircraft() {
    if (this.planeInfo == null) {
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
    if (this.field_70122_E && getVtolMode() == 0 && this.planeInfo.isDefaultVtol)
      swithVtolMode(true); 
    this.field_70169_q = this.field_70165_t;
    this.field_70167_r = this.field_70163_u;
    this.field_70166_s = this.field_70161_v;
    if (!isDestroyed() && isHovering())
      if (MathHelper.func_76135_e(getRotPitch()) < 70.0F)
        setRotPitch(getRotPitch() * 0.95F, "isHovering()");  
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
  
  public boolean canUpdateYaw(Entity player) {
    return (super.canUpdateYaw(player) && !isHovering());
  }
  
  public boolean canUpdatePitch(Entity player) {
    return (super.canUpdatePitch(player) && !isHovering());
  }
  
  public boolean canUpdateRoll(Entity player) {
    return (super.canUpdateRoll(player) && !isHovering());
  }
  
  public float getYawFactor() {
    float yaw = (getVtolMode() > 0) ? (getPlaneInfo()).vtolYaw : super.getYawFactor();
    return yaw * 0.8F;
  }
  
  public float getPitchFactor() {
    float pitch = (getVtolMode() > 0) ? (getPlaneInfo()).vtolPitch : super.getPitchFactor();
    return pitch * 0.8F;
  }
  
  public float getRollFactor() {
    float roll = (getVtolMode() > 0) ? (getPlaneInfo()).vtolYaw : super.getRollFactor();
    return roll * 0.8F;
  }
  
  public boolean isOverridePlayerPitch() {
    return (super.isOverridePlayerPitch() && !isHovering());
  }
  
  public boolean isOverridePlayerYaw() {
    return (super.isOverridePlayerYaw() && !isHovering());
  }
  
  public float getControlRotYaw(float mouseX, float mouseY, float tick) {
    if (MCH_Config.MouseControlFlightSimMode.prmBool) {
      rotationByKey(tick);
      return this.addkeyRotValue * 20.0F;
    } 
    return mouseX;
  }
  
  public float getControlRotPitch(float mouseX, float mouseY, float tick) {
    return mouseY;
  }
  
  public float getControlRotRoll(float mouseX, float mouseY, float tick) {
    if (MCH_Config.MouseControlFlightSimMode.prmBool)
      return mouseX * 2.0F; 
    if (getVtolMode() == 0)
      return mouseX * 0.5F; 
    return mouseX;
  }
  
  private void rotationByKey(float partialTicks) {
    float rot = 0.2F;
    if (!MCH_Config.MouseControlFlightSimMode.prmBool)
      if (getVtolMode() != 0)
        rot *= 0.0F;  
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
    boolean isFly = (MCH_Lib.getBlockIdY((Entity)this, 3, -3) == 0);
    if (!isFly || isFreeLookMode() || this.isGunnerMode || ((getAcInfo()).isFloat && getWaterDepth() > 0.0D)) {
      float gmy = 1.0F;
      if (!isFly) {
        gmy = (getAcInfo()).mobilityYawOnGround;
        if (!(getAcInfo()).canRotOnGround) {
          Block block = MCH_Lib.getBlockY((Entity)this, 3, -2, false);
          if (!W_Block.isEqual(block, W_Block.getWater()) && !W_Block.isEqual(block, W_Blocks.field_150350_a))
            gmy = 0.0F; 
        } 
      } 
      if (this.moveLeft && !this.moveRight)
        setRotYaw(getRotYaw() - 0.6F * gmy * partialTicks); 
      if (this.moveRight && !this.moveLeft)
        setRotYaw(getRotYaw() + 0.6F * gmy * partialTicks); 
    } else if (isFly) {
      if (!MCH_Config.MouseControlFlightSimMode.prmBool) {
        rotationByKey(partialTicks);
        setRotRoll(getRotRoll() + this.addkeyRotValue * 0.5F * (getAcInfo()).mobilityRoll * partialTicks * 3.3F);
      } 
    } 
    this.addkeyRotValue = (float)(this.addkeyRotValue * (1.0D - (0.1F * partialTicks)));
    if (!isFly && MathHelper.func_76135_e(getRotPitch()) < 40.0F)
      applyOnGroundPitch(0.97F); 
    if (getNozzleRotation() > 0.001F) {
      float rot = 1.0F - 0.03F * partialTicks;
      setRotPitch(getRotPitch() * rot);
      rot = 1.0F - 0.1F * partialTicks;
      setRotRoll(getRotRoll() * rot);
    } 
  }
  
  protected void onUpdate_Control() {
    if (this.isGunnerMode && !canUseFuel())
      switchGunnerMode(false); 
    this.throttleBack = (float)(this.throttleBack * 0.8D);
    if (getRiddenByEntity() != null && !(getRiddenByEntity()).field_70128_L && isCanopyClose() && canUseWing() && 
      canUseFuel() && !isDestroyed()) {
      onUpdate_ControlNotHovering();
    } else if (isTargetDrone() && canUseFuel() && !isDestroyed()) {
      this.throttleUp = true;
      onUpdate_ControlNotHovering();
    } else if (getCurrentThrottle() > 0.0D) {
      addCurrentThrottle(-0.0025D * (getAcInfo()).throttleUpDown);
    } else {
      setCurrentThrottle(0.0D);
    } 
    if (getCurrentThrottle() < 0.0D)
      setCurrentThrottle(0.0D); 
    if (this.field_70170_p.field_72995_K) {
      if (!W_Lib.isClientPlayer(getRiddenByEntity())) {
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
  
  protected void onUpdate_ControlNotHovering() {
    if (!this.isGunnerMode) {
      float throttleUpDown = (getAcInfo()).throttleUpDown;
      boolean turn = ((this.moveLeft && !this.moveRight) || (!this.moveLeft && this.moveRight));
      boolean localThrottleUp = this.throttleUp;
      if (turn && getCurrentThrottle() < (getAcInfo()).pivotTurnThrottle)
        if (!localThrottleUp && !this.throttleDown) {
          localThrottleUp = true;
          throttleUpDown *= 2.0F;
        }  
      if (localThrottleUp) {
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
      } else if (this.cs_planeAutoThrottleDown) {
        if (getCurrentThrottle() > 0.0D) {
          addCurrentThrottle(-0.005D * throttleUpDown);
          if (getCurrentThrottle() <= 0.0D)
            setCurrentThrottle(0.0D); 
        } 
      } 
    } 
  }
  
  protected void onUpdate_Particle() {
    if (this.field_70170_p.field_72995_K) {
      onUpdate_ParticleLandingGear();
      onUpdate_ParticleNozzle();
    } 
  }
  
  protected void onUpdate_Particle2() {
    if (!this.field_70170_p.field_72995_K)
      return; 
    if (getHP() >= getMaxHP() * 0.5D)
      return; 
    if (getPlaneInfo() == null)
      return; 
    int rotorNum = (getPlaneInfo()).rotorList.size();
    if (rotorNum < 0)
      rotorNum = 0; 
    if (this.isFirstDamageSmoke)
      this.prevDamageSmokePos = new Vec3d[rotorNum + 1]; 
    float yaw = getRotYaw();
    float pitch = getRotPitch();
    float roll = getRotRoll();
    boolean spawnSmoke = true;
    for (int ri = 0; ri < rotorNum; ri++) {
      if (getHP() >= getMaxHP() * 0.2D && getMaxHP() > 0) {
        int d = (int)(((getHP() / getMaxHP()) - 0.2D) / 0.3D * 15.0D);
        if (d > 0 && this.field_70146_Z.nextInt(d) > 0)
          spawnSmoke = false; 
      } 
      Vec3d rotor_pos = ((MCP_PlaneInfo.Rotor)(getPlaneInfo()).rotorList.get(ri)).pos;
      Vec3d pos = MCH_Lib.RotVec3(rotor_pos, -yaw, -pitch, -roll);
      double x = this.field_70165_t + pos.field_72450_a;
      double y = this.field_70163_u + pos.field_72448_b;
      double z = this.field_70161_v + pos.field_72449_c;
      onUpdate_Particle2SpawnSmoke(ri, x, y, z, 1.0F, spawnSmoke);
    } 
    spawnSmoke = true;
    if (getHP() >= getMaxHP() * 0.2D && getMaxHP() > 0) {
      int d = (int)(((getHP() / getMaxHP()) - 0.2D) / 0.3D * 15.0D);
      if (d > 0 && this.field_70146_Z.nextInt(d) > 0)
        spawnSmoke = false; 
    } 
    double px = this.field_70165_t;
    double py = this.field_70163_u;
    double pz = this.field_70161_v;
    if (getSeatInfo(0) != null && (getSeatInfo(0)).pos != null) {
      Vec3d pos = MCH_Lib.RotVec3(0.0D, (getSeatInfo(0)).pos.field_72448_b, -2.0D, -yaw, -pitch, -roll);
      px += pos.field_72450_a;
      py += pos.field_72448_b;
      pz += pos.field_72449_c;
    } 
    onUpdate_Particle2SpawnSmoke(rotorNum, px, py, pz, (rotorNum == 0) ? 2.0F : 1.0F, spawnSmoke);
    this.isFirstDamageSmoke = false;
  }
  
  public void onUpdate_Particle2SpawnSmoke(int ri, double x, double y, double z, float size, boolean spawnSmoke) {
    if (this.isFirstDamageSmoke || this.prevDamageSmokePos[ri] == null)
      this.prevDamageSmokePos[ri] = new Vec3d(x, y, z); 
    Vec3d prev = this.prevDamageSmokePos[ri];
    double dx = x - prev.field_72450_a;
    double dy = y - prev.field_72448_b;
    double dz = z - prev.field_72449_c;
    int num = (int)(MathHelper.func_76133_a(dx * dx + dy * dy + dz * dz) / 0.3D) + 1;
    for (int i = 0; i < num; i++) {
      float c = 0.2F + this.field_70146_Z.nextFloat() * 0.3F;
      MCH_ParticleParam prm = new MCH_ParticleParam(this.field_70170_p, "smoke", prev.field_72450_a + (x - prev.field_72450_a) * i / 3.0D, prev.field_72448_b + (y - prev.field_72448_b) * i / 3.0D, prev.field_72449_c + (z - prev.field_72449_c) * i / 3.0D);
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
    double d = this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y;
    if (d > 0.01D) {
      int x = MathHelper.func_76128_c(this.field_70165_t + 0.5D);
      int y = MathHelper.func_76128_c(this.field_70163_u - 0.5D);
      int z = MathHelper.func_76128_c(this.field_70161_v + 0.5D);
      MCH_ParticlesUtil.spawnParticleTileCrack(this.field_70170_p, x, y, z, this.field_70165_t + (this.field_70146_Z
          
          .nextFloat() - 0.5D) * this.field_70130_N, (func_174813_aQ()).field_72338_b + 0.1D, this.field_70161_v + (this.field_70146_Z
          .nextFloat() - 0.5D) * this.field_70130_N, -this.field_70159_w * 4.0D, 1.5D, -this.field_70179_y * 4.0D);
    } 
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
  
  public void onUpdate_ParticleNozzle() {
    if (this.planeInfo == null || !this.planeInfo.haveNozzle())
      return; 
    if (getCurrentThrottle() <= 0.10000000149011612D)
      return; 
    float yaw = getRotYaw();
    float pitch = getRotPitch();
    float roll = getRotRoll();
    Vec3d nozzleRot = MCH_Lib.RotVec3(0.0D, 0.0D, 1.0D, -yaw - 180.0F, pitch - getNozzleRotation(), roll);
    for (MCH_AircraftInfo.DrawnPart nozzle : this.planeInfo.nozzles) {
      if (this.field_70146_Z.nextFloat() <= getCurrentThrottle() * 1.5D) {
        Vec3d nozzlePos = MCH_Lib.RotVec3(nozzle.pos, -yaw, -pitch, -roll);
        double x = this.field_70165_t + nozzlePos.field_72450_a + nozzleRot.field_72450_a;
        double y = this.field_70163_u + nozzlePos.field_72448_b + nozzleRot.field_72448_b;
        double z = this.field_70161_v + nozzlePos.field_72449_c + nozzleRot.field_72449_c;
        float a = 0.7F;
        if (W_WorldFunc.getBlockId(this.field_70170_p, (int)(x + nozzleRot.field_72450_a * 3.0D), (int)(y + nozzleRot.field_72448_b * 3.0D), (int)(z + nozzleRot.field_72449_c * 3.0D)) != 0)
          a = 2.0F; 
        MCH_ParticleParam prm = new MCH_ParticleParam(this.field_70170_p, "smoke", x, y, z, nozzleRot.field_72450_a + ((this.field_70146_Z.nextFloat() - 0.5F) * a), nozzleRot.field_72448_b, nozzleRot.field_72449_c + ((this.field_70146_Z.nextFloat() - 0.5F) * a), 5.0F * (getAcInfo()).particlesScale);
        MCH_ParticlesUtil.spawnParticle(prm);
      } 
    } 
  }
  
  public void destroyAircraft() {
    super.destroyAircraft();
    int inv = 1;
    if (getRotRoll() >= 0.0F) {
      if (getRotRoll() > 90.0F)
        inv = -1; 
    } else if (getRotRoll() > -90.0F) {
      inv = -1;
    } 
    this.rotDestroyedRoll = (0.5F + this.field_70146_Z.nextFloat()) * inv;
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
    if (isDestroyed())
      if (MCH_Lib.getBlockIdY((Entity)this, 3, -3) == 0) {
        if (MathHelper.func_76135_e(getRotPitch()) < 10.0F)
          setRotPitch(getRotPitch() + this.rotDestroyedPitch); 
        float roll = MathHelper.func_76135_e(getRotRoll());
        if (roll < 45.0F || roll > 135.0F)
          setRotRoll(getRotRoll() + this.rotDestroyedRoll); 
      } else if (MathHelper.func_76135_e(getRotPitch()) > 20.0F) {
        setRotPitch(getRotPitch() * 0.99F);
      }  
    if (getRiddenByEntity() != null);
    updateSound();
    onUpdate_Particle();
    onUpdate_Particle2();
    onUpdate_ParticleSplash();
    onUpdate_ParticleSandCloud(true);
    updateCamera(this.field_70165_t, this.field_70163_u, this.field_70161_v);
  }
  
  private void onUpdate_Server() {
    Vec3d v;
    double prevMotion = Math.sqrt(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
    double dp = 0.0D;
    if (canFloatWater())
      dp = getWaterDepth(); 
    boolean levelOff = this.isGunnerMode;
    if (dp == 0.0D) {
      if (isTargetDrone() && canUseFuel() && !isDestroyed()) {
        Block block = MCH_Lib.getBlockY((Entity)this, 3, -40, true);
        if (block == null || W_Block.isEqual(block, W_Blocks.field_150350_a)) {
          setRotYaw(getRotYaw() + (getAcInfo()).autoPilotRot * 1.0F);
          setRotPitch(getRotPitch() * 0.95F);
          if (canFoldLandingGear())
            foldLandingGear(); 
          levelOff = true;
        } else {
          block = MCH_Lib.getBlockY((Entity)this, 3, -5, true);
          if (block == null || W_Block.isEqual(block, W_Blocks.field_150350_a)) {
            setRotYaw(getRotYaw() + (getAcInfo()).autoPilotRot * 2.0F);
            if (getRotPitch() > -20.0F)
              setRotPitch(getRotPitch() - 0.5F); 
          } 
        } 
      } 
      if (!levelOff) {
        this.field_70181_x += 0.04D + (!func_70090_H() ? (getAcInfo()).gravity : (getAcInfo()).gravityInWater);
        this.field_70181_x += -0.047D * (1.0D - getCurrentThrottle());
      } else {
        this.field_70181_x *= 0.8D;
      } 
    } else {
      setRotPitch(getRotPitch() * 0.8F, "getWaterDepth != 0");
      if (MathHelper.func_76135_e(getRotRoll()) < 40.0F)
        setRotRoll(getRotRoll() * 0.9F); 
      if (dp < 1.0D) {
        this.field_70181_x -= 1.0E-4D;
        this.field_70181_x += 0.007D * getCurrentThrottle();
      } else {
        if (this.field_70181_x < 0.0D)
          this.field_70181_x /= 2.0D; 
        this.field_70181_x += 0.007D;
      } 
    } 
    float throttle = (float)(getCurrentThrottle() / 10.0D);
    if (getNozzleRotation() > 0.001F) {
      setRotPitch(getRotPitch() * 0.95F);
      v = MCH_Lib.Rot2Vec3(getRotYaw(), getRotPitch() - getNozzleRotation());
      if (getNozzleRotation() >= 90.0F)
        v = new Vec3d(v.field_72450_a * 0.800000011920929D, v.field_72448_b, v.field_72449_c * 0.800000011920929D); 
    } else {
      v = MCH_Lib.Rot2Vec3(getRotYaw(), getRotPitch() - 10.0F);
    } 
    if (!levelOff)
      if (getNozzleRotation() <= 0.01F) {
        this.field_70181_x += v.field_72448_b * throttle / 2.0D;
      } else {
        this.field_70181_x += v.field_72448_b * throttle / 8.0D;
      }  
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
    func_70091_d(MoverType.SELF, this.field_70159_w, this.field_70181_x, this.field_70179_y);
    this.field_70181_x *= 0.95D;
    this.field_70159_w *= (getAcInfo()).motionFactor;
    this.field_70179_y *= (getAcInfo()).motionFactor;
    func_70101_b(getRotYaw(), getRotPitch());
    onUpdate_updateBlock();
    if (getRiddenByEntity() != null && (getRiddenByEntity()).field_70128_L)
      unmountEntity(); 
  }
  
  public float getMaxSpeed() {
    float f = 0.0F;
    if (this.partWing != null && (getPlaneInfo()).isVariableSweepWing) {
      f = ((getPlaneInfo()).sweepWingSpeed - (getPlaneInfo()).speed) * this.partWing.getFactor();
    } else if (this.partHatch != null && (getPlaneInfo()).isVariableSweepWing) {
      f = ((getPlaneInfo()).sweepWingSpeed - (getPlaneInfo()).speed) * this.partHatch.getFactor();
    } 
    return (getPlaneInfo()).speed + f;
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
    return (float)(0.6D + getCurrentThrottle() * 0.4D);
  }
  
  public String getDefaultSoundName() {
    return "plane";
  }
  
  public void updateParts(int stat) {
    super.updateParts(stat);
    if (isDestroyed())
      return; 
    MCH_Parts[] parts = { this.partNozzle, this.partWing };
    for (MCH_Parts p : parts) {
      if (p != null) {
        p.updateStatusClient(stat);
        p.update();
      } 
    } 
    if (!this.field_70170_p.field_72995_K && this.partWing != null)
      if ((getPlaneInfo()).isVariableSweepWing && this.partWing.isON())
        if (getCurrentThrottle() >= 0.20000000298023224D)
          if (getCurrentThrottle() < 0.5D || MCH_Lib.getBlockIdY((Entity)this, 1, -10) != 0)
            this.partWing.setStatusServer(false);    
  }
  
  public float getUnfoldLandingGearThrottle() {
    return 0.7F;
  }
  
  public boolean canSwitchVtol() {
    if (this.planeInfo == null || !this.planeInfo.isEnableVtol)
      return false; 
    if (getModeSwitchCooldown() > 0)
      return false; 
    if (getVtolMode() == 1)
      return false; 
    if (MathHelper.func_76135_e(getRotRoll()) > 30.0F)
      return false; 
    if (this.field_70122_E && this.planeInfo.isDefaultVtol)
      return false; 
    setModeSwitchCooldown(20);
    return true;
  }
  
  public boolean getNozzleStat() {
    return (this.partNozzle != null) ? this.partNozzle.getStatus() : false;
  }
  
  public int getVtolMode() {
    if (!getNozzleStat())
      return (getNozzleRotation() <= 0.005F) ? 0 : 1; 
    return (getNozzleRotation() >= 89.995F) ? 2 : 1;
  }
  
  public float getFuleConsumptionFactor() {
    return getFuelConsumptionFactor() * ((getVtolMode() == 2) ? true : true);
  }
  
  public float getNozzleRotation() {
    return (this.partNozzle != null) ? this.partNozzle.rotation : 0.0F;
  }
  
  public float getPrevNozzleRotation() {
    return (this.partNozzle != null) ? this.partNozzle.prevRotation : 0.0F;
  }
  
  public void swithVtolMode(boolean mode) {
    if (this.partNozzle != null) {
      if (this.planeInfo.isDefaultVtol && this.field_70122_E && !mode)
        return; 
      if (!this.field_70170_p.field_72995_K)
        this.partNozzle.setStatusServer(mode); 
      if (getRiddenByEntity() != null && !(getRiddenByEntity()).field_70128_L)
        (getRiddenByEntity()).field_70125_A = (getRiddenByEntity()).field_70127_C = 0.0F; 
    } 
  }
  
  protected MCH_Parts createNozzle(MCP_PlaneInfo info) {
    MCH_Parts nozzle = null;
    if (info.haveNozzle() || info.haveRotor() || info.isEnableVtol) {
      nozzle = new MCH_Parts((Entity)this, 1, PART_STAT, "Nozzle");
      nozzle.rotationMax = 90.0F;
      nozzle.rotationInv = 1.5F;
      nozzle.soundStartSwichOn.setPrm("plane_cc", 1.0F, 0.5F);
      nozzle.soundEndSwichOn.setPrm("plane_cc", 1.0F, 0.5F);
      nozzle.soundStartSwichOff.setPrm("plane_cc", 1.0F, 0.5F);
      nozzle.soundEndSwichOff.setPrm("plane_cc", 1.0F, 0.5F);
      nozzle.soundSwitching.setPrm("plane_cv", 1.0F, 0.5F);
      if (info.isDefaultVtol)
        nozzle.forceSwitch(true); 
    } 
    return nozzle;
  }
  
  protected MCH_Parts createWing(MCP_PlaneInfo info) {
    MCH_Parts wing = null;
    if (this.planeInfo.haveWing()) {
      wing = new MCH_Parts((Entity)this, 3, PART_STAT, "Wing");
      wing.rotationMax = 90.0F;
      wing.rotationInv = 2.5F;
      wing.soundStartSwichOn.setPrm("plane_cc", 1.0F, 0.5F);
      wing.soundEndSwichOn.setPrm("plane_cc", 1.0F, 0.5F);
      wing.soundStartSwichOff.setPrm("plane_cc", 1.0F, 0.5F);
      wing.soundEndSwichOff.setPrm("plane_cc", 1.0F, 0.5F);
    } 
    return wing;
  }
  
  public boolean canUseWing() {
    if (this.partWing == null)
      return true; 
    if ((getPlaneInfo()).isVariableSweepWing) {
      if (getCurrentThrottle() < 0.2D)
        return this.partWing.isOFF(); 
      return true;
    } 
    return this.partWing.isOFF();
  }
  
  public boolean canFoldWing() {
    if (this.partWing == null || getModeSwitchCooldown() > 0)
      return false; 
    if ((getPlaneInfo()).isVariableSweepWing) {
      if (this.field_70122_E == true || MCH_Lib.getBlockIdY((Entity)this, 3, -20) != 0) {
        if (getCurrentThrottle() > 0.10000000149011612D)
          return false; 
      } else if (getCurrentThrottle() < 0.699999988079071D) {
        return false;
      } 
    } else {
      if (!this.field_70122_E && MCH_Lib.getBlockIdY((Entity)this, 3, -3) == 0)
        return false; 
      if (getCurrentThrottle() > 0.009999999776482582D)
        return false; 
    } 
    return this.partWing.isOFF();
  }
  
  public boolean canUnfoldWing() {
    if (this.partWing == null || getModeSwitchCooldown() > 0)
      return false; 
    return this.partWing.isON();
  }
  
  public void foldWing(boolean fold) {
    if (this.partWing == null || getModeSwitchCooldown() > 0)
      return; 
    this.partWing.setStatusServer(fold);
    setModeSwitchCooldown(20);
  }
  
  public float getWingRotation() {
    return (this.partWing != null) ? this.partWing.rotation : 0.0F;
  }
  
  public float getPrevWingRotation() {
    return (this.partWing != null) ? this.partWing.prevRotation : 0.0F;
  }
}
