package mcheli.helicopter;

import javax.annotation.Nullable;
import mcheli.MCH_Config;
import mcheli.MCH_Lib;
import mcheli.MCH_ServerSettings;
import mcheli.aircraft.MCH_EntityAircraft;
import mcheli.aircraft.MCH_PacketStatusRequest;
import mcheli.aircraft.MCH_Rotor;
import mcheli.particles.MCH_ParticleParam;
import mcheli.particles.MCH_ParticlesUtil;
import mcheli.wrapper.W_Entity;
import mcheli.wrapper.W_Lib;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class MCH_EntityHeli extends MCH_EntityAircraft {
  private static final DataParameter<Byte> FOLD_STAT = EntityDataManager.func_187226_a(MCH_EntityHeli.class, DataSerializers.field_187191_a);
  
  private MCH_HeliInfo heliInfo;
  
  public double prevRotationRotor = 0.0D;
  
  public double rotationRotor = 0.0D;
  
  public MCH_Rotor[] rotors;
  
  public byte lastFoldBladeStat;
  
  public int foldBladesCooldown;
  
  public float prevRollFactor;
  
  public MCH_EntityHeli(World world) {
    super(world);
    this.prevRollFactor = 0.0F;
    this.heliInfo = null;
    this.currentSpeed = 0.07D;
    this.field_70156_m = true;
    func_70105_a(2.0F, 0.7F);
    this.field_70159_w = 0.0D;
    this.field_70181_x = 0.0D;
    this.field_70179_y = 0.0D;
    this.weapons = createWeapon(0);
    this.rotors = new MCH_Rotor[0];
    this.lastFoldBladeStat = -1;
    if (this.field_70170_p.field_72995_K)
      this.foldBladesCooldown = 40; 
  }
  
  public String getKindName() {
    return "helicopters";
  }
  
  public String getEntityType() {
    return "Plane";
  }
  
  public MCH_HeliInfo getHeliInfo() {
    return this.heliInfo;
  }
  
  public void changeType(String type) {
    MCH_Lib.DbgLog(this.field_70170_p, "MCH_EntityHeli.changeType " + type + " : " + toString(), new Object[0]);
    if (!type.isEmpty())
      this.heliInfo = MCH_HeliInfoManager.get(type); 
    if (this.heliInfo == null) {
      MCH_Lib.Log((Entity)this, "##### MCH_EntityHeli changeHeliType() Heli info null %d, %s, %s", new Object[] { Integer.valueOf(W_Entity.getEntityId((Entity)this)), type, getEntityName() });
      setDead(true);
    } else {
      setAcInfo(this.heliInfo);
      newSeats(getAcInfo().getNumSeatAndRack());
      createRotors();
      this.weapons = createWeapon(1 + getSeatNum());
      initPartRotation(getRotYaw(), getRotPitch());
    } 
  }
  
  @Nullable
  public Item getItem() {
    return (getHeliInfo() != null) ? (Item)(getHeliInfo()).item : null;
  }
  
  public boolean canMountWithNearEmptyMinecart() {
    return MCH_Config.MountMinecartHeli.prmBool;
  }
  
  protected void func_70088_a() {
    super.func_70088_a();
    this.field_70180_af.func_187214_a(FOLD_STAT, Byte.valueOf((byte)2));
  }
  
  protected void func_70014_b(NBTTagCompound par1NBTTagCompound) {
    super.func_70014_b(par1NBTTagCompound);
    par1NBTTagCompound.func_74780_a("RotorSpeed", getCurrentThrottle());
    par1NBTTagCompound.func_74780_a("rotetionRotor", this.rotationRotor);
    par1NBTTagCompound.func_74757_a("FoldBlade", (getFoldBladeStat() == 0));
  }
  
  protected void func_70037_a(NBTTagCompound par1NBTTagCompound) {
    super.func_70037_a(par1NBTTagCompound);
    boolean beforeFoldBlade = (getFoldBladeStat() == 0);
    if (getCommonUniqueId().isEmpty()) {
      setCommonUniqueId(par1NBTTagCompound.func_74779_i("HeliUniqueId"));
      MCH_Lib.Log((Entity)this, "# MCH_EntityHeli readEntityFromNBT() " + W_Entity.getEntityId((Entity)this) + ", " + getEntityName() + ", AircraftUniqueId=null, HeliUniqueId=" + getCommonUniqueId(), new Object[0]);
    } 
    if (getTypeName().isEmpty()) {
      setTypeName(par1NBTTagCompound.func_74779_i("HeliType"));
      MCH_Lib.Log((Entity)this, "# MCH_EntityHeli readEntityFromNBT() " + W_Entity.getEntityId((Entity)this) + ", " + getEntityName() + ", TypeName=null, HeliType=" + getTypeName(), new Object[0]);
    } 
    setCurrentThrottle(par1NBTTagCompound.func_74769_h("RotorSpeed"));
    this.rotationRotor = par1NBTTagCompound.func_74769_h("rotetionRotor");
    setFoldBladeStat((byte)(par1NBTTagCompound.func_74767_n("FoldBlade") ? 0 : 2));
    if (this.heliInfo == null) {
      this.heliInfo = MCH_HeliInfoManager.get(getTypeName());
      if (this.heliInfo == null) {
        MCH_Lib.Log((Entity)this, "##### MCH_EntityHeli readEntityFromNBT() Heli info null %d, %s", new Object[] { Integer.valueOf(W_Entity.getEntityId((Entity)this)), getEntityName() });
        setDead(true);
      } else {
        setAcInfo(this.heliInfo);
      } 
    } 
    if (!beforeFoldBlade && getFoldBladeStat() == 0)
      forceFoldBlade(); 
    this.prevRotationRotor = this.rotationRotor;
  }
  
  public float getSoundVolume() {
    if (getAcInfo() != null && (getAcInfo()).throttleUpDown <= 0.0F)
      return 0.0F; 
    return (float)getCurrentThrottle() * 2.0F;
  }
  
  public float getSoundPitch() {
    return (float)(0.2D + getCurrentThrottle() * 0.2D);
  }
  
  public String getDefaultSoundName() {
    return "heli";
  }
  
  public float getUnfoldLandingGearThrottle() {
    double x = this.field_70165_t - this.field_70169_q;
    double y = this.field_70163_u - this.field_70167_r;
    double z = this.field_70161_v - this.field_70166_s;
    float s = (getAcInfo()).speed / 3.5F;
    return (x * x + y * y + z * z <= s) ? 0.8F : 0.3F;
  }
  
  protected void createRotors() {
    if (this.heliInfo == null)
      return; 
    this.rotors = new MCH_Rotor[this.heliInfo.rotorList.size()];
    int i = 0;
    for (MCH_HeliInfo.Rotor r : this.heliInfo.rotorList) {
      this.rotors[i] = new MCH_Rotor(r.bladeNum, r.bladeRot, this.field_70170_p.field_72995_K ? 2 : 2, (float)r.pos.field_72450_a, (float)r.pos.field_72448_b, (float)r.pos.field_72449_c, (float)r.rot.field_72450_a, (float)r.rot.field_72448_b, (float)r.rot.field_72449_c, r.haveFoldFunc);
      i++;
    } 
  }
  
  protected void forceFoldBlade() {
    if (this.heliInfo != null && this.rotors.length > 0)
      if (this.heliInfo.isEnableFoldBlade)
        for (MCH_Rotor r : this.rotors) {
          r.update((float)this.rotationRotor);
          foldBlades();
          r.forceFold();
        }   
  }
  
  public boolean isFoldBlades() {
    if (this.heliInfo == null || this.rotors.length <= 0)
      return false; 
    return (getFoldBladeStat() == 0);
  }
  
  protected boolean canSwitchFoldBlades() {
    if (this.heliInfo == null || this.rotors.length <= 0)
      return false; 
    return (this.heliInfo.isEnableFoldBlade && getCurrentThrottle() <= 0.01D && this.foldBladesCooldown == 0 && (getFoldBladeStat() == 2 || getFoldBladeStat() == 0));
  }
  
  protected boolean canUseBlades() {
    if (this.heliInfo == null)
      return false; 
    if (this.rotors.length <= 0)
      return true; 
    if (getFoldBladeStat() == 2) {
      for (MCH_Rotor r : this.rotors) {
        if (r.isFoldingOrUnfolding())
          return false; 
      } 
      return true;
    } 
    return false;
  }
  
  protected void foldBlades() {
    if (this.heliInfo == null || this.rotors.length <= 0)
      return; 
    setCurrentThrottle(0.0D);
    for (MCH_Rotor r : this.rotors)
      r.startFold(); 
  }
  
  public void unfoldBlades() {
    if (this.heliInfo == null || this.rotors.length <= 0)
      return; 
    for (MCH_Rotor r : this.rotors)
      r.startUnfold(); 
  }
  
  public void onRideEntity(Entity ridingEntity) {
    if (ridingEntity instanceof mcheli.aircraft.MCH_EntitySeat) {
      if (this.heliInfo == null || this.rotors.length <= 0)
        return; 
      if (this.heliInfo.isEnableFoldBlade) {
        forceFoldBlade();
        setFoldBladeStat((byte)0);
      } 
    } 
  }
  
  protected byte getFoldBladeStat() {
    return ((Byte)this.field_70180_af.func_187225_a(FOLD_STAT)).byteValue();
  }
  
  public void setFoldBladeStat(byte b) {
    if (!this.field_70170_p.field_72995_K)
      if (b >= 0 && b <= 3)
        this.field_70180_af.func_187227_b(FOLD_STAT, Byte.valueOf(b));  
  }
  
  public boolean canSwitchGunnerMode() {
    if (super.canSwitchGunnerMode() && canUseBlades()) {
      float roll = MathHelper.func_76135_e(MathHelper.func_76142_g(getRotRoll()));
      float pitch = MathHelper.func_76135_e(MathHelper.func_76142_g(getRotPitch()));
      if (roll < 40.0F && pitch < 40.0F)
        return true; 
    } 
    return false;
  }
  
  public boolean canSwitchHoveringMode() {
    if (super.canSwitchHoveringMode() && canUseBlades()) {
      float roll = MathHelper.func_76135_e(MathHelper.func_76142_g(getRotRoll()));
      float pitch = MathHelper.func_76135_e(MathHelper.func_76142_g(getRotPitch()));
      if (roll < 40.0F && pitch < 40.0F)
        return true; 
    } 
    return false;
  }
  
  public void onUpdateAircraft() {
    if (this.heliInfo == null) {
      changeType(getTypeName());
      this.field_70169_q = this.field_70165_t;
      this.field_70167_r = this.field_70163_u;
      this.field_70166_s = this.field_70161_v;
      return;
    } 
    if (!this.isRequestedSyncStatus) {
      this.isRequestedSyncStatus = true;
      if (this.field_70170_p.field_72995_K) {
        int stat = getFoldBladeStat();
        if (stat == 1 || stat == 0)
          forceFoldBlade(); 
        MCH_PacketStatusRequest.requestStatus(this);
      } 
    } 
    if (this.lastRiddenByEntity == null && getRiddenByEntity() != null)
      initCurrentWeapon(getRiddenByEntity()); 
    updateWeapons();
    onUpdate_Seats();
    onUpdate_Control();
    onUpdate_Rotor();
    this.field_70169_q = this.field_70165_t;
    this.field_70167_r = this.field_70163_u;
    this.field_70166_s = this.field_70161_v;
    if (!isDestroyed() && isHovering())
      if (MathHelper.func_76135_e(getRotPitch()) < 70.0F)
        setRotPitch(getRotPitch() * 0.95F);  
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
  
  public boolean canMouseRot() {
    return super.canMouseRot();
  }
  
  public boolean canUpdatePitch(Entity player) {
    return (super.canUpdatePitch(player) && !isHovering());
  }
  
  public boolean canUpdateRoll(Entity player) {
    return (super.canUpdateRoll(player) && !isHovering());
  }
  
  public boolean isOverridePlayerPitch() {
    return (super.isOverridePlayerPitch() && !isHovering());
  }
  
  public float getRollFactor() {
    float roll = super.getRollFactor();
    double d = func_70092_e(this.field_70169_q, this.field_70163_u, this.field_70166_s);
    double s = (getAcInfo()).speed;
    d = (s > 0.1D) ? (d / s) : 0.0D;
    float f = this.prevRollFactor;
    this.prevRollFactor = roll;
    return (roll + f) / 2.0F;
  }
  
  public float getControlRotYaw(float mouseX, float mouseY, float tick) {
    return mouseX;
  }
  
  public float getControlRotPitch(float mouseX, float mouseY, float tick) {
    return mouseY;
  }
  
  public float getControlRotRoll(float mouseX, float mouseY, float tick) {
    return mouseX;
  }
  
  public void onUpdateAngles(float partialTicks) {
    if (isDestroyed())
      return; 
    float rotRoll = !isHovering() ? 0.04F : 0.07F;
    rotRoll = 1.0F - rotRoll * partialTicks;
    if (MCH_ServerSettings.enableRotationLimit) {
      if (getRotPitch() > MCH_ServerSettings.pitchLimitMax)
        setRotPitch(getRotPitch() - 
            Math.abs((getRotPitch() - MCH_ServerSettings.pitchLimitMax) * 0.1F * partialTicks)); 
      if (getRotPitch() < MCH_ServerSettings.pitchLimitMin)
        setRotPitch(getRotPitch() + 
            Math.abs((getRotPitch() - MCH_ServerSettings.pitchLimitMin) * 0.2F * partialTicks)); 
      if (getRotRoll() > MCH_ServerSettings.rollLimit)
        setRotRoll(
            getRotRoll() - Math.abs((getRotRoll() - MCH_ServerSettings.rollLimit) * 0.03F * partialTicks)); 
      if (getRotRoll() < -MCH_ServerSettings.rollLimit)
        setRotRoll(
            getRotRoll() + Math.abs((getRotRoll() + MCH_ServerSettings.rollLimit) * 0.03F * partialTicks)); 
    } 
    if (getRotRoll() > 0.1D && getRotRoll() < 65.0F)
      setRotRoll(getRotRoll() * rotRoll); 
    if (getRotRoll() < -0.1D && getRotRoll() > -65.0F)
      setRotRoll(getRotRoll() * rotRoll); 
    if (MCH_Lib.getBlockIdY((Entity)this, 3, -3) == 0) {
      if (this.moveLeft && !this.moveRight)
        setRotRoll(getRotRoll() - 1.2F * partialTicks); 
      if (this.moveRight && !this.moveLeft)
        setRotRoll(getRotRoll() + 1.2F * partialTicks); 
    } else {
      if (MathHelper.func_76135_e(getRotPitch()) < 40.0F)
        applyOnGroundPitch(0.97F); 
      if (this.heliInfo.isEnableFoldBlade && this.rotors.length > 0 && getFoldBladeStat() == 0 && !isDestroyed()) {
        if (this.moveLeft && !this.moveRight)
          setRotYaw(getRotYaw() - 0.5F * partialTicks); 
        if (this.moveRight && !this.moveLeft)
          setRotYaw(getRotYaw() + 0.5F * partialTicks); 
      } 
    } 
  }
  
  protected void onUpdate_Rotor() {
    byte stat = getFoldBladeStat();
    boolean isEndSwitch = true;
    if (stat != this.lastFoldBladeStat) {
      if (stat == 1) {
        foldBlades();
      } else if (stat == 3) {
        unfoldBlades();
      } 
      if (this.field_70170_p.field_72995_K)
        this.foldBladesCooldown = 40; 
      this.lastFoldBladeStat = stat;
    } else if (this.foldBladesCooldown > 0) {
      this.foldBladesCooldown--;
    } 
    for (MCH_Rotor r : this.rotors) {
      r.update((float)this.rotationRotor);
      if (r.isFoldingOrUnfolding())
        isEndSwitch = false; 
    } 
    if (isEndSwitch)
      if (stat == 1) {
        setFoldBladeStat((byte)0);
      } else if (stat == 3) {
        setFoldBladeStat((byte)2);
      }  
  }
  
  protected void onUpdate_Control() {
    if (isHoveringMode() && !canUseFuel(true))
      switchHoveringMode(false); 
    if (this.isGunnerMode && !canUseFuel())
      switchGunnerMode(false); 
    if (!isDestroyed() && (getRiddenByEntity() != null || isHoveringMode()) && canUseBlades() && isCanopyClose() && 
      canUseFuel(true)) {
      if (!isHovering()) {
        onUpdate_ControlNotHovering();
      } else {
        onUpdate_ControlHovering();
      } 
    } else {
      if (getCurrentThrottle() > 0.0D) {
        addCurrentThrottle(-0.00125D);
      } else {
        setCurrentThrottle(0.0D);
      } 
      if (this.heliInfo.isEnableFoldBlade && this.rotors.length > 0 && getFoldBladeStat() == 0 && this.field_70122_E && 
        !isDestroyed())
        onUpdate_ControlFoldBladeAndOnGround(); 
    } 
    if (this.field_70170_p.field_72995_K) {
      if (!W_Lib.isClientPlayer(getRiddenByEntity())) {
        double ct = getThrottle();
        if (getCurrentThrottle() >= ct - 0.02D) {
          addCurrentThrottle(-0.01D);
        } else if (getCurrentThrottle() < ct) {
          addCurrentThrottle(0.01D);
        } 
      } 
    } else {
      setThrottle(getCurrentThrottle());
    } 
    if (getCurrentThrottle() < 0.0D)
      setCurrentThrottle(0.0D); 
    this.prevRotationRotor = this.rotationRotor;
    this.rotationRotor += (1.0D - Math.pow(1.0D - getCurrentThrottle(), 5.0D)) * (getAcInfo()).rotorSpeed;
    this.rotationRotor %= 360.0D;
  }
  
  protected void onUpdate_ControlNotHovering() {
    float throttleUpDown = (getAcInfo()).throttleUpDown;
    if (this.throttleUp) {
      if (getCurrentThrottle() < 1.0D) {
        addCurrentThrottle(0.02D * throttleUpDown);
      } else {
        setCurrentThrottle(1.0D);
      } 
    } else if (this.throttleDown) {
      if (getCurrentThrottle() > 0.0D) {
        addCurrentThrottle(-0.014285714285714285D * throttleUpDown);
      } else {
        setCurrentThrottle(0.0D);
      } 
    } else if (!this.field_70170_p.field_72995_K || W_Lib.isClientPlayer(getRiddenByEntity())) {
      if (this.cs_heliAutoThrottleDown)
        if (getCurrentThrottle() > 0.52D) {
          addCurrentThrottle(-0.01D * throttleUpDown);
        } else if (getCurrentThrottle() < 0.48D) {
          addCurrentThrottle(0.01D * throttleUpDown);
        }  
    } 
    if (!this.field_70170_p.field_72995_K) {
      boolean move = false;
      float yaw = getRotYaw();
      double x = 0.0D;
      double z = 0.0D;
      if (this.moveLeft && !this.moveRight) {
        yaw = getRotYaw() - 90.0F;
        x += Math.sin(yaw * Math.PI / 180.0D);
        z += Math.cos(yaw * Math.PI / 180.0D);
        move = true;
      } 
      if (this.moveRight && !this.moveLeft) {
        yaw = getRotYaw() + 90.0F;
        x += Math.sin(yaw * Math.PI / 180.0D);
        z += Math.cos(yaw * Math.PI / 180.0D);
        move = true;
      } 
      if (move) {
        double f = 1.0D;
        double d = Math.sqrt(x * x + z * z);
        this.field_70159_w -= x / d * 0.019999999552965164D * f * (getAcInfo()).speed;
        this.field_70179_y += z / d * 0.019999999552965164D * f * (getAcInfo()).speed;
      } 
    } 
  }
  
  protected void onUpdate_ControlHovering() {
    if (getCurrentThrottle() < 1.0D) {
      addCurrentThrottle(0.03333333333333333D);
    } else {
      setCurrentThrottle(1.0D);
    } 
    if (!this.field_70170_p.field_72995_K) {
      boolean move = false;
      float yaw = getRotYaw();
      double x = 0.0D;
      double z = 0.0D;
      if (this.throttleUp) {
        yaw = getRotYaw();
        x += Math.sin(yaw * Math.PI / 180.0D);
        z += Math.cos(yaw * Math.PI / 180.0D);
        move = true;
      } 
      if (this.throttleDown) {
        yaw = getRotYaw() - 180.0F;
        x += Math.sin(yaw * Math.PI / 180.0D);
        z += Math.cos(yaw * Math.PI / 180.0D);
        move = true;
      } 
      if (this.moveLeft && !this.moveRight) {
        yaw = getRotYaw() - 90.0F;
        x += Math.sin(yaw * Math.PI / 180.0D);
        z += Math.cos(yaw * Math.PI / 180.0D);
        move = true;
      } 
      if (this.moveRight && !this.moveLeft) {
        yaw = getRotYaw() + 90.0F;
        x += Math.sin(yaw * Math.PI / 180.0D);
        z += Math.cos(yaw * Math.PI / 180.0D);
        move = true;
      } 
      if (move) {
        double d = Math.sqrt(x * x + z * z);
        this.field_70159_w -= x / d * 0.009999999776482582D * (getAcInfo()).speed;
        this.field_70179_y += z / d * 0.009999999776482582D * (getAcInfo()).speed;
      } 
    } 
  }
  
  protected void onUpdate_ControlFoldBladeAndOnGround() {
    if (!this.field_70170_p.field_72995_K) {
      boolean move = false;
      float yaw = getRotYaw();
      double x = 0.0D;
      double z = 0.0D;
      if (this.throttleUp) {
        yaw = getRotYaw();
        x += Math.sin(yaw * Math.PI / 180.0D);
        z += Math.cos(yaw * Math.PI / 180.0D);
        move = true;
      } 
      if (this.throttleDown) {
        yaw = getRotYaw() - 180.0F;
        x += Math.sin(yaw * Math.PI / 180.0D);
        z += Math.cos(yaw * Math.PI / 180.0D);
        move = true;
      } 
      if (move) {
        double d = Math.sqrt(x * x + z * z);
        this.field_70159_w -= x / d * 0.029999999329447746D;
        this.field_70179_y += z / d * 0.029999999329447746D;
      } 
    } 
  }
  
  protected void onUpdate_Particle2() {
    if (!this.field_70170_p.field_72995_K)
      return; 
    if (getHP() > getMaxHP() * 0.5D)
      return; 
    if (getHeliInfo() == null)
      return; 
    int rotorNum = (getHeliInfo()).rotorList.size();
    if (rotorNum <= 0)
      return; 
    if (this.isFirstDamageSmoke)
      this.prevDamageSmokePos = new Vec3d[rotorNum]; 
    for (int ri = 0; ri < rotorNum; ri++) {
      Vec3d rotor_pos = ((MCH_HeliInfo.Rotor)(getHeliInfo()).rotorList.get(ri)).pos;
      float yaw = getRotYaw();
      float pitch = getRotPitch();
      Vec3d pos = MCH_Lib.RotVec3(rotor_pos, -yaw, -pitch, -getRotRoll());
      double x = this.field_70165_t + pos.field_72450_a;
      double y = this.field_70163_u + pos.field_72448_b;
      double z = this.field_70161_v + pos.field_72449_c;
      if (this.isFirstDamageSmoke)
        this.prevDamageSmokePos[ri] = new Vec3d(x, y, z); 
      Vec3d prev = this.prevDamageSmokePos[ri];
      double dx = x - prev.field_72450_a;
      double dy = y - prev.field_72448_b;
      double dz = z - prev.field_72449_c;
      int num = (int)(MathHelper.func_76133_a(dx * dx + dy * dy + dz * dz) * 2.0F) + 1;
      double i;
      for (i = 0.0D; i < num; i++) {
        double p = (getHP() / getMaxHP());
        if (p < (this.field_70146_Z.nextFloat() / 2.0F)) {
          float c = 0.2F + this.field_70146_Z.nextFloat() * 0.3F;
          MCH_ParticleParam prm = new MCH_ParticleParam(this.field_70170_p, "smoke", prev.field_72450_a + (x - prev.field_72450_a) * i / num, prev.field_72448_b + (y - prev.field_72448_b) * i / num, prev.field_72449_c + (z - prev.field_72449_c) * i / num);
          prm.motionX = (this.field_70146_Z.nextDouble() - 0.5D) * 0.3D;
          prm.motionY = this.field_70146_Z.nextDouble() * 0.1D;
          prm.motionZ = (this.field_70146_Z.nextDouble() - 0.5D) * 0.3D;
          prm.size = (this.field_70146_Z.nextInt(5) + 5.0F) * 1.0F;
          prm.setColor(0.7F + this.field_70146_Z.nextFloat() * 0.1F, c, c, c);
          MCH_ParticlesUtil.spawnParticle(prm);
          int ebi = this.field_70146_Z.nextInt(1 + this.extraBoundingBox.length);
          if (p < 0.3D && ebi > 0) {
            AxisAlignedBB bb = this.extraBoundingBox[ebi - 1].getBoundingBox();
            double bx = (bb.field_72336_d + bb.field_72340_a) / 2.0D;
            double by = (bb.field_72337_e + bb.field_72338_b) / 2.0D;
            double bz = (bb.field_72334_f + bb.field_72339_c) / 2.0D;
            prm.posX = bx;
            prm.posY = by;
            prm.posZ = bz;
            MCH_ParticlesUtil.spawnParticle(prm);
          } 
        } 
      } 
      this.prevDamageSmokePos[ri] = new Vec3d(x, y, z);
    } 
    this.isFirstDamageSmoke = false;
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
    if (isDestroyed()) {
      if (this.rotDestroyedYaw < 15.0F)
        this.rotDestroyedYaw += 0.3F; 
      setRotYaw(getRotYaw() + this.rotDestroyedYaw * (float)getCurrentThrottle());
      if (MCH_Lib.getBlockIdY((Entity)this, 3, -3) == 0) {
        if (MathHelper.func_76135_e(getRotPitch()) < 10.0F)
          setRotPitch(getRotPitch() + this.rotDestroyedPitch); 
        setRotRoll(getRotRoll() + this.rotDestroyedRoll);
      } 
    } 
    if (getRiddenByEntity() != null);
    onUpdate_ParticleSandCloud(false);
    onUpdate_Particle2();
    updateCamera(this.field_70165_t, this.field_70163_u, this.field_70161_v);
  }
  
  private void onUpdate_Server() {
    double prevMotion = Math.sqrt(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
    float ogp = (getAcInfo()).onGroundPitch;
    if (!isHovering()) {
      double dp = 0.0D;
      if (canFloatWater())
        dp = getWaterDepth(); 
      if (dp == 0.0D) {
        this.field_70181_x += (!func_70090_H() ? (getAcInfo()).gravity : (getAcInfo()).gravityInWater);
        float yaw = getRotYaw() / 180.0F * 3.1415927F;
        float pitch = getRotPitch();
        if (MCH_Lib.getBlockIdY((Entity)this, 3, -3) > 0)
          pitch -= ogp; 
        this.field_70159_w += 0.1D * MathHelper.func_76126_a(yaw) * this.currentSpeed * -(pitch * pitch * pitch / 20000.0F) * 
          getCurrentThrottle();
        this.field_70179_y += 0.1D * MathHelper.func_76134_b(yaw) * this.currentSpeed * (pitch * pitch * pitch / 20000.0F) * 
          getCurrentThrottle();
        double y = 0.0D;
        if (MathHelper.func_76135_e(getRotPitch()) + MathHelper.func_76135_e(getRotRoll() * 0.9F) <= 40.0F)
          y = 1.0D - y / 40.0D; 
        double throttle = getCurrentThrottle();
        if (isDestroyed())
          throttle *= -0.65D; 
        this.field_70181_x += (y * 0.025D + 0.03D) * throttle;
      } else {
        if (MathHelper.func_76135_e(getRotPitch()) < 40.0F) {
          float pitch = getRotPitch();
          pitch -= ogp;
          pitch *= 0.9F;
          pitch += ogp;
          setRotPitch(pitch);
        } 
        if (MathHelper.func_76135_e(getRotRoll()) < 40.0F)
          setRotRoll(getRotRoll() * 0.9F); 
        if (dp < 1.0D) {
          this.field_70181_x -= 1.0E-4D;
          this.field_70181_x += 0.007D * getCurrentThrottle();
        } else {
          if (this.field_70181_x < 0.0D)
            this.field_70181_x *= 0.7D; 
          this.field_70181_x += 0.007D;
        } 
      } 
    } else {
      if (this.field_70146_Z.nextInt(50) == 0)
        this.field_70159_w += (this.field_70146_Z.nextDouble() - 0.5D) / 30.0D; 
      if (this.field_70146_Z.nextInt(50) == 0)
        this.field_70181_x += (this.field_70146_Z.nextDouble() - 0.5D) / 50.0D; 
      if (this.field_70146_Z.nextInt(50) == 0)
        this.field_70179_y += (this.field_70146_Z.nextDouble() - 0.5D) / 30.0D; 
    } 
    double motion = Math.sqrt(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
    float speedLimit = (getAcInfo()).speed;
    if (motion > speedLimit) {
      this.field_70159_w *= speedLimit / motion;
      this.field_70179_y *= speedLimit / motion;
      motion = speedLimit;
    } 
    if (isDestroyed()) {
      this.field_70159_w *= 0.0D;
      this.field_70179_y *= 0.0D;
      this.currentSpeed = 0.0D;
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
    if (this.field_70122_E) {
      this.field_70159_w *= 0.5D;
      this.field_70179_y *= 0.5D;
      if (MathHelper.func_76135_e(getRotPitch()) < 40.0F) {
        float pitch = getRotPitch();
        pitch -= ogp;
        pitch *= 0.9F;
        pitch += ogp;
        setRotPitch(pitch);
      } 
      if (MathHelper.func_76135_e(getRotRoll()) < 40.0F)
        setRotRoll(getRotRoll() * 0.9F); 
    } 
    func_70091_d(MoverType.SELF, this.field_70159_w, this.field_70181_x, this.field_70179_y);
    this.field_70181_x *= 0.95D;
    this.field_70159_w *= 0.99D;
    this.field_70179_y *= 0.99D;
    func_70101_b(getRotYaw(), getRotPitch());
    onUpdate_updateBlock();
    if (getRiddenByEntity() != null && (getRiddenByEntity()).field_70128_L)
      unmountEntity(); 
  }
}
