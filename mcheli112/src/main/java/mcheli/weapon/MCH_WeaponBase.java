package mcheli.weapon;

import java.util.Random;
import mcheli.MCH_Lib;
import mcheli.aircraft.MCH_EntityAircraft;
import mcheli.wrapper.W_McClient;
import mcheli.wrapper.W_WorldFunc;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public abstract class MCH_WeaponBase {
  protected static final Random rand = new Random();
  
  public final World worldObj;
  
  public final Vec3d position;
  
  public final float fixRotationYaw;
  
  public final float fixRotationPitch;
  
  public final String name;
  
  public final MCH_WeaponInfo weaponInfo;
  
  public String displayName;
  
  public int power;
  
  public float acceleration;
  
  public int explosionPower;
  
  public int explosionPowerInWater;
  
  public int interval;
  
  public int delayedInterval;
  
  public int numMode;
  
  public int lockTime;
  
  public int piercing;
  
  public int heatCount;
  
  public MCH_Cartridge cartridge;
  
  public boolean onTurret;
  
  public MCH_EntityAircraft aircraft;
  
  public int tick;
  
  public int optionParameter1;
  
  public int optionParameter2;
  
  private int currentMode;
  
  public boolean canPlaySound;
  
  public MCH_WeaponBase(World w, Vec3d v, float yaw, float pitch, String nm, MCH_WeaponInfo wi) {
    this.worldObj = w;
    this.position = v;
    this.fixRotationYaw = yaw;
    this.fixRotationPitch = pitch;
    this.name = nm;
    this.weaponInfo = wi;
    this.displayName = (wi != null) ? wi.displayName : "";
    this.power = 0;
    this.acceleration = 0.0F;
    this.explosionPower = 0;
    this.explosionPowerInWater = 0;
    this.interval = 1;
    this.numMode = 0;
    this.lockTime = 0;
    this.heatCount = 0;
    this.cartridge = null;
    this.tick = 0;
    this.optionParameter1 = 0;
    this.optionParameter2 = 0;
    setCurrentMode(0);
    this.canPlaySound = true;
  }
  
  public MCH_WeaponInfo getInfo() {
    return this.weaponInfo;
  }
  
  public String getName() {
    return this.displayName;
  }
  
  public abstract boolean shot(MCH_WeaponParam paramMCH_WeaponParam);
  
  public void setLockChecker(MCH_IEntityLockChecker checker) {}
  
  public void setLockCountMax(int n) {}
  
  public int getLockCount() {
    return 0;
  }
  
  public int getLockCountMax() {
    return 0;
  }
  
  public final int getNumAmmoMax() {
    return (getInfo()).round;
  }
  
  public int getCurrentMode() {
    return (getInfo() != null && (getInfo()).fixMode > 0) ? (getInfo()).fixMode : this.currentMode;
  }
  
  public void setCurrentMode(int currentMode) {
    this.currentMode = currentMode;
  }
  
  public final int getAllAmmoNum() {
    return (getInfo()).maxAmmo;
  }
  
  public final int getReloadCount() {
    return (getInfo()).reloadTime;
  }
  
  public final MCH_SightType getSightType() {
    return (getInfo()).sight;
  }
  
  public MCH_WeaponGuidanceSystem getGuidanceSystem() {
    return null;
  }
  
  public void update(int countWait) {
    if (countWait != 0)
      this.tick++; 
  }
  
  public boolean isCooldownCountReloadTime() {
    return false;
  }
  
  public void modifyCommonParameters() {
    modifyParameters();
  }
  
  public void modifyParameters() {}
  
  public boolean switchMode() {
    if (getInfo() != null && (getInfo()).fixMode > 0)
      return false; 
    int beforeMode = getCurrentMode();
    if (this.numMode > 0) {
      setCurrentMode((getCurrentMode() + 1) % this.numMode);
    } else {
      setCurrentMode(0);
    } 
    if (beforeMode != getCurrentMode())
      onSwitchMode(); 
    return (beforeMode != getCurrentMode());
  }
  
  public void onSwitchMode() {}
  
  public boolean use(MCH_WeaponParam prm) {
    Vec3d v = getShotPos(prm.entity);
    prm.posX += v.field_72450_a;
    prm.posY += v.field_72448_b;
    prm.posZ += v.field_72449_c;
    if (shot(prm)) {
      this.tick = 0;
      return true;
    } 
    return false;
  }
  
  public Vec3d getShotPos(Entity entity) {
    if (entity instanceof MCH_EntityAircraft && this.onTurret)
      return ((MCH_EntityAircraft)entity).calcOnTurretPos(this.position); 
    Vec3d v = new Vec3d(this.position.field_72450_a, this.position.field_72448_b, this.position.field_72449_c);
    float roll = (entity instanceof MCH_EntityAircraft) ? ((MCH_EntityAircraft)entity).getRotRoll() : 0.0F;
    return MCH_Lib.RotVec3(v, -entity.field_70177_z, -entity.field_70125_A, -roll);
  }
  
  public void playSound(Entity e) {
    playSound(e, (getInfo()).soundFileName);
  }
  
  public void playSound(Entity e, String snd) {
    if (!e.field_70170_p.field_72995_K && this.canPlaySound && getInfo() != null) {
      float prnd = (getInfo()).soundPitchRandom;
      W_WorldFunc.MOD_playSoundEffect(this.worldObj, e.field_70165_t, e.field_70163_u, e.field_70161_v, snd, (getInfo()).soundVolume, 
          (getInfo()).soundPitch * (1.0F - prnd) + rand.nextFloat() * prnd);
    } 
  }
  
  public void playSoundClient(Entity e, float volume, float pitch) {
    if (e.field_70170_p.field_72995_K && getInfo() != null)
      W_McClient.MOD_playSoundFX((getInfo()).soundFileName, volume, pitch); 
  }
  
  public double getLandInDistance(MCH_WeaponParam prm) {
    if (this.weaponInfo == null)
      return -1.0D; 
    if (this.weaponInfo.gravity >= 0.0F)
      return -1.0D; 
    Vec3d v = MCH_Lib.RotVec3(0.0D, 0.0D, 1.0D, -prm.rotYaw, -prm.rotPitch, -prm.rotRoll);
    double s = Math.sqrt(v.field_72450_a * v.field_72450_a + v.field_72448_b * v.field_72448_b + v.field_72449_c * v.field_72449_c);
    double acc = (this.acceleration < 4.0F) ? this.acceleration : 4.0D;
    double accFac = this.acceleration / acc;
    double my = v.field_72448_b * this.acceleration / s;
    if (my <= 0.0D)
      return -1.0D; 
    double mx = v.field_72450_a * this.acceleration / s;
    double mz = v.field_72449_c * this.acceleration / s;
    double ls = my / this.weaponInfo.gravity;
    double gravity = this.weaponInfo.gravity * accFac;
    if (ls < -12.0D) {
      double f = ls / -12.0D;
      mx *= f;
      my *= f;
      mz *= f;
      gravity *= f * f * 0.95D;
    } 
    double spx = prm.posX;
    double spy = prm.posY + 3.0D;
    double spz = prm.posZ;
    for (int i = 0; i < 50; i++) {
      Vec3d vs = new Vec3d(spx, spy, spz);
      Vec3d ve = new Vec3d(spx + mx, spy + my, spz + mz);
      RayTraceResult mop = this.worldObj.func_72933_a(vs, ve);
      if (mop != null && mop.field_72313_a == RayTraceResult.Type.BLOCK) {
        double dx = mop.func_178782_a().func_177958_n() - prm.posX;
        double dz = mop.func_178782_a().func_177952_p() - prm.posZ;
        return Math.sqrt(dx * dx + dz * dz);
      } 
      my += gravity;
      spx += mx;
      spy += my;
      spz += mz;
      if (spy < prm.posY) {
        double dx = spx - prm.posX;
        double dz = spz - prm.posZ;
        return Math.sqrt(dx * dx + dz * dz);
      } 
    } 
    return -1.0D;
  }
}
