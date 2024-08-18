package mcheli.weapon;

import java.util.Random;
import mcheli.MCH_Lib;
import mcheli.wrapper.W_McClient;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class MCH_WeaponSet {
  private static Random rand = new Random();
  
  private final String name;
  
  protected MCH_WeaponBase[] weapons;
  
  private int currentWeaponIndex;
  
  public float rotationYaw;
  
  public float rotationPitch;
  
  public float prevRotationYaw;
  
  public float prevRotationPitch;
  
  public float defaultRotationYaw;
  
  public float rotationTurretYaw;
  
  public float rotBay;
  
  public float prevRotBay;
  
  public Recoil[] recoilBuf;
  
  protected int numAmmo;
  
  protected int numRestAllAmmo;
  
  public int currentHeat;
  
  public int cooldownSpeed;
  
  public int countWait;
  
  public int countReloadWait;
  
  protected int[] lastUsedCount;
  
  public int soundWait;
  
  private int lastUsedOptionParameter1 = 0;
  
  private int lastUsedOptionParameter2 = 0;
  
  public float rotBarrelSpd;
  
  public float rotBarrel;
  
  public float prevRotBarrel;
  
  public MCH_WeaponSet(MCH_WeaponBase[] weapon) {
    this.name = (weapon[0]).name;
    this.weapons = weapon;
    this.currentWeaponIndex = 0;
    this.countWait = 0;
    this.countReloadWait = 0;
    this.lastUsedCount = new int[weapon.length];
    this.rotationYaw = 0.0F;
    this.prevRotationYaw = 0.0F;
    this.rotationPitch = 0.0F;
    this.prevRotationPitch = 0.0F;
    setAmmoNum(0);
    setRestAllAmmoNum(0);
    this.currentHeat = 0;
    this.soundWait = 0;
    this.cooldownSpeed = 1;
    this.rotBarrelSpd = 0.0F;
    this.rotBarrel = 0.0F;
    this.prevRotBarrel = 0.0F;
    this.recoilBuf = new Recoil[weapon.length];
    for (int i = 0; i < this.recoilBuf.length; i++)
      this.recoilBuf[i] = new Recoil(this, (weapon[i].getInfo()).recoilBufCount, 
          (weapon[i].getInfo()).recoilBufCountSpeed); 
    this.defaultRotationYaw = 0.0F;
  }
  
  public MCH_WeaponSet(MCH_WeaponBase weapon) {
    this(new MCH_WeaponBase[] { weapon });
  }
  
  public boolean isEqual(String s) {
    return this.name.equalsIgnoreCase(s);
  }
  
  public int getAmmoNum() {
    return this.numAmmo;
  }
  
  public int getAmmoNumMax() {
    return getFirstWeapon().getNumAmmoMax();
  }
  
  public int getRestAllAmmoNum() {
    return this.numRestAllAmmo;
  }
  
  public int getAllAmmoNum() {
    return getFirstWeapon().getAllAmmoNum();
  }
  
  public void setAmmoNum(int n) {
    this.numAmmo = n;
  }
  
  public void setRestAllAmmoNum(int n) {
    int debugBefore = this.numRestAllAmmo;
    int m = (getInfo()).maxAmmo - getAmmoNum();
    this.numRestAllAmmo = (n <= m) ? n : m;
    MCH_Lib.DbgLog((getFirstWeapon()).worldObj, "MCH_WeaponSet.setRestAllAmmoNum:%s %d->%d (%d)", new Object[] { getName(), Integer.valueOf(debugBefore), Integer.valueOf(this.numRestAllAmmo), Integer.valueOf(n) });
  }
  
  public void supplyRestAllAmmo() {
    int m = (getInfo()).maxAmmo;
    if (getRestAllAmmoNum() + getAmmoNum() < m)
      setRestAllAmmoNum(getRestAllAmmoNum() + getAmmoNum() + (getInfo()).suppliedNum); 
  }
  
  public boolean isInPreparation() {
    return (this.countWait < 0 || this.countReloadWait > 0);
  }
  
  public String getName() {
    MCH_WeaponBase w = getCurrentWeapon();
    return (w != null) ? w.getName() : "";
  }
  
  public boolean canUse() {
    return (this.countWait == 0);
  }
  
  public boolean isLongDelayWeapon() {
    return ((getInfo()).delay > 4);
  }
  
  public void reload() {
    MCH_WeaponBase crtWpn = getCurrentWeapon();
    if (getAmmoNumMax() > 0 && getAmmoNum() < getAmmoNumMax() && crtWpn.getReloadCount() > 0) {
      this.countReloadWait = crtWpn.getReloadCount();
      if (crtWpn.worldObj.field_72995_K)
        setAmmoNum(0); 
      if (!crtWpn.worldObj.field_72995_K) {
        this.countReloadWait -= 20;
        if (this.countReloadWait <= 0)
          this.countReloadWait = 1; 
      } 
    } 
  }
  
  public void reloadMag() {
    int restAmmo = getRestAllAmmoNum();
    int nAmmo = getAmmoNumMax() - getAmmoNum();
    if (nAmmo > 0) {
      if (nAmmo > restAmmo)
        nAmmo = restAmmo; 
      setAmmoNum(getAmmoNum() + nAmmo);
      setRestAllAmmoNum(getRestAllAmmoNum() - nAmmo);
    } 
  }
  
  public void switchMode() {
    boolean isChanged = false;
    for (MCH_WeaponBase w : this.weapons) {
      if (w != null)
        isChanged = (w.switchMode() || isChanged); 
    } 
    if (isChanged) {
      int cntSwitch = 15;
      if (this.countWait >= -cntSwitch)
        if (this.countWait > cntSwitch) {
          this.countWait = -this.countWait;
        } else {
          this.countWait = -cntSwitch;
        }  
      if ((getCurrentWeapon()).worldObj.field_72995_K)
        W_McClient.playSoundClick(1.0F, 1.0F); 
    } 
  }
  
  public void onSwitchWeapon(boolean isRemote, boolean isCreative) {
    int cntSwitch = 15;
    if (isRemote)
      cntSwitch += 10; 
    if (this.countWait >= -cntSwitch)
      if (this.countWait > cntSwitch) {
        this.countWait = -this.countWait;
      } else {
        this.countWait = -cntSwitch;
      }  
    this.currentWeaponIndex = 0;
    if (isCreative)
      setAmmoNum(getAmmoNumMax()); 
  }
  
  public boolean isUsed(int index) {
    MCH_WeaponBase w = getFirstWeapon();
    if (w != null && index < this.lastUsedCount.length) {
      int cnt = this.lastUsedCount[index];
      return ((w.interval >= 4 && cnt > w.interval / 2) || cnt >= 4);
    } 
    return false;
  }
  
  public void update(Entity shooter, boolean isSelected, boolean isUsed) {
    if (getCurrentWeapon().getInfo() == null)
      return; 
    if (this.countReloadWait > 0) {
      this.countReloadWait--;
      if (this.countReloadWait == 0)
        reloadMag(); 
    } 
    for (int i = 0; i < this.lastUsedCount.length; i++) {
      if (this.lastUsedCount[i] > 0)
        if (this.lastUsedCount[i] == 4) {
          if (0 == getCurrentWeaponIndex() && canUse())
            if (getAmmoNum() > 0 || getAllAmmoNum() <= 0)
              this.lastUsedCount[i] = this.lastUsedCount[i] - 1;  
        } else {
          this.lastUsedCount[i] = this.lastUsedCount[i] - 1;
        }  
    } 
    if (this.currentHeat > 0) {
      if (this.currentHeat < (getCurrentWeapon().getInfo()).maxHeatCount)
        this.cooldownSpeed++; 
      this.currentHeat -= this.cooldownSpeed / 20 + 1;
      if (this.currentHeat < 0)
        this.currentHeat = 0; 
    } 
    if (this.countWait > 0)
      this.countWait--; 
    if (this.countWait < 0)
      this.countWait++; 
    this.prevRotationYaw = this.rotationYaw;
    this.prevRotationPitch = this.rotationPitch;
    if (this.weapons != null && this.weapons.length > 0)
      for (MCH_WeaponBase w : this.weapons) {
        if (w != null)
          w.update(this.countWait); 
      }  
    if (this.soundWait > 0)
      this.soundWait--; 
    if (isUsed)
      if (this.rotBarrelSpd < 75.0F) {
        this.rotBarrelSpd += (25 + rand.nextInt(3));
        if (this.rotBarrelSpd > 74.0F)
          this.rotBarrelSpd = 74.0F; 
      }  
    this.prevRotBarrel = this.rotBarrel;
    this.rotBarrel += this.rotBarrelSpd;
    if (this.rotBarrel >= 360.0F) {
      this.rotBarrel -= 360.0F;
      this.prevRotBarrel -= 360.0F;
    } 
    if (this.rotBarrelSpd > 0.0F) {
      this.rotBarrelSpd -= 1.48F;
      if (this.rotBarrelSpd < 0.0F)
        this.rotBarrelSpd = 0.0F; 
    } 
  }
  
  public void updateWeapon(Entity shooter, boolean isUsed, int index) {
    MCH_WeaponBase crtWpn = getWeapon(index);
    if (isUsed)
      if (shooter.field_70170_p.field_72995_K && crtWpn != null && crtWpn.cartridge != null) {
        Vec3d v = crtWpn.getShotPos(shooter);
        float yaw = shooter.field_70177_z;
        float pitch = shooter.field_70125_A;
        if (!(shooter instanceof mcheli.vehicle.MCH_EntityVehicle) || shooter.func_184207_aI());
        MCH_EntityCartridge.spawnCartridge(shooter.field_70170_p, crtWpn.cartridge, shooter.field_70165_t + v.field_72450_a, shooter.field_70163_u + v.field_72448_b, shooter.field_70161_v + v.field_72449_c, shooter.field_70159_w, shooter.field_70181_x, shooter.field_70179_y, yaw + this.rotationYaw, pitch + this.rotationPitch);
      }  
    if (index < this.recoilBuf.length) {
      Recoil r = this.recoilBuf[index];
      r.prevRecoilBuf = r.recoilBuf;
      if (isUsed && r.recoilBufCount <= 0)
        r.recoilBufCount = r.recoilBufCountMax; 
      if (r.recoilBufCount > 0) {
        if (r.recoilBufCountMax <= 1) {
          r.recoilBuf = 1.0F;
        } else if (r.recoilBufCountMax == 2) {
          r.recoilBuf = (r.recoilBufCount == 2) ? 1.0F : 0.6F;
        } else {
          if (r.recoilBufCount > r.recoilBufCountMax / 2)
            r.recoilBufCount -= r.recoilBufCountSpeed; 
          float rb = (r.recoilBufCount / r.recoilBufCountMax);
          r.recoilBuf = MathHelper.func_76126_a(rb * 3.1415927F);
        } 
        r.recoilBufCount--;
      } else {
        r.recoilBuf = 0.0F;
      } 
    } 
  }
  
  public boolean use(MCH_WeaponParam prm) {
    MCH_WeaponBase crtWpn = getCurrentWeapon();
    if (crtWpn != null && crtWpn.getInfo() != null) {
      MCH_WeaponInfo info = crtWpn.getInfo();
      if ((getAmmoNumMax() <= 0 || getAmmoNum() > 0) && (info.maxHeatCount <= 0 || this.currentHeat < info.maxHeatCount)) {
        crtWpn.canPlaySound = (this.soundWait == 0);
        prm.rotYaw = (prm.entity != null) ? prm.entity.field_70177_z : 0.0F;
        prm.rotPitch = (prm.entity != null) ? prm.entity.field_70125_A : 0.0F;
        prm.rotYaw += this.rotationYaw + crtWpn.fixRotationYaw;
        prm.rotPitch += this.rotationPitch + crtWpn.fixRotationPitch;
        if (info.accuracy > 0.0F) {
          prm.rotYaw += (rand.nextFloat() - 0.5F) * info.accuracy;
          prm.rotPitch += (rand.nextFloat() - 0.5F) * info.accuracy;
        } 
        prm.rotYaw = MathHelper.func_76142_g(prm.rotYaw);
        prm.rotPitch = MathHelper.func_76142_g(prm.rotPitch);
        if (crtWpn.use(prm)) {
          if (info.maxHeatCount > 0) {
            this.cooldownSpeed = 1;
            this.currentHeat += crtWpn.heatCount;
            if (this.currentHeat >= info.maxHeatCount)
              this.currentHeat += 30; 
          } 
          if (info.soundDelay > 0 && this.soundWait == 0)
            this.soundWait = info.soundDelay; 
          this.lastUsedOptionParameter1 = crtWpn.optionParameter1;
          this.lastUsedOptionParameter2 = crtWpn.optionParameter2;
          this.lastUsedCount[this.currentWeaponIndex] = (crtWpn.interval > 0) ? crtWpn.interval : -crtWpn.interval;
          if (crtWpn.isCooldownCountReloadTime() && crtWpn
            .getReloadCount() - 10 > this.lastUsedCount[this.currentWeaponIndex])
            this.lastUsedCount[this.currentWeaponIndex] = crtWpn.getReloadCount() - 10; 
          this.currentWeaponIndex = (this.currentWeaponIndex + 1) % this.weapons.length;
          this.countWait = (prm.user instanceof net.minecraft.entity.player.EntityPlayer) ? crtWpn.interval : crtWpn.delayedInterval;
          this.countReloadWait = 0;
          if (getAmmoNum() > 0)
            setAmmoNum(getAmmoNum() - 1); 
          if (getAmmoNum() <= 0) {
            if (prm.isInfinity && getRestAllAmmoNum() < getAmmoNumMax())
              setRestAllAmmoNum(getAmmoNumMax()); 
            reload();
            prm.reload = true;
            if (prm.user instanceof mcheli.mob.MCH_EntityGunner)
              this.countWait += ((this.countWait >= 0) ? 1 : -1) * crtWpn.getReloadCount(); 
          } 
          prm.result = true;
        } 
      } 
    } 
    return prm.result;
  }
  
  public void waitAndReloadByOther(boolean reload) {
    MCH_WeaponBase crtWpn = getCurrentWeapon();
    if (crtWpn != null && crtWpn.getInfo() != null) {
      this.countWait = crtWpn.interval;
      this.countReloadWait = 0;
      if (reload)
        if (getAmmoNumMax() > 0 && crtWpn.getReloadCount() > 0) {
          this.countReloadWait = crtWpn.getReloadCount();
          if (!crtWpn.worldObj.field_72995_K) {
            this.countReloadWait -= 20;
            if (this.countReloadWait <= 0)
              this.countReloadWait = 1; 
          } 
        }  
    } 
  }
  
  public int getLastUsedOptionParameter1() {
    return this.lastUsedOptionParameter1;
  }
  
  public int getLastUsedOptionParameter2() {
    return this.lastUsedOptionParameter2;
  }
  
  public MCH_WeaponBase getFirstWeapon() {
    return getWeapon(0);
  }
  
  public int getCurrentWeaponIndex() {
    return this.currentWeaponIndex;
  }
  
  public MCH_WeaponBase getCurrentWeapon() {
    return getWeapon(this.currentWeaponIndex);
  }
  
  public MCH_WeaponBase getWeapon(int idx) {
    if (this.weapons != null && this.weapons.length > 0 && idx < this.weapons.length)
      return this.weapons[idx]; 
    return null;
  }
  
  public int getWeaponNum() {
    return (this.weapons != null) ? this.weapons.length : 0;
  }
  
  public MCH_WeaponInfo getInfo() {
    return getFirstWeapon().getInfo();
  }
  
  public double getLandInDistance(MCH_WeaponParam prm) {
    double ret = -1.0D;
    MCH_WeaponBase crtWpn = getCurrentWeapon();
    if (crtWpn != null && crtWpn.getInfo() != null) {
      prm.rotYaw = (prm.entity != null) ? prm.entity.field_70177_z : 0.0F;
      prm.rotPitch = (prm.entity != null) ? prm.entity.field_70125_A : 0.0F;
      prm.rotYaw += this.rotationYaw + crtWpn.fixRotationYaw;
      prm.rotPitch += this.rotationPitch + crtWpn.fixRotationPitch;
      prm.rotYaw = MathHelper.func_76142_g(prm.rotYaw);
      prm.rotPitch = MathHelper.func_76142_g(prm.rotPitch);
      return crtWpn.getLandInDistance(prm);
    } 
    return ret;
  }
  
  public class Recoil {
    public int recoilBufCount;
    
    public final int recoilBufCountMax;
    
    public final int recoilBufCountSpeed;
    
    public float recoilBuf;
    
    public float prevRecoilBuf;
    
    public Recoil(MCH_WeaponSet paramMCH_WeaponSet, int max, int spd) {
      this.recoilBufCountMax = max;
      this.recoilBufCountSpeed = spd;
    }
  }
}
