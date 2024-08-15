/*     */ package mcheli.weapon;
/*     */ 
/*     */ import java.util.Random;
/*     */ import mcheli.MCH_Lib;
/*     */ import mcheli.wrapper.W_McClient;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MCH_WeaponSet
/*     */ {
/*  22 */   private static Random rand = new Random();
/*     */   
/*     */   private final String name;
/*     */   
/*     */   protected MCH_WeaponBase[] weapons;
/*     */   
/*     */   private int currentWeaponIndex;
/*     */   public float rotationYaw;
/*     */   public float rotationPitch;
/*     */   public float prevRotationYaw;
/*     */   public float prevRotationPitch;
/*     */   public float defaultRotationYaw;
/*     */   public float rotationTurretYaw;
/*     */   public float rotBay;
/*     */   public float prevRotBay;
/*     */   public Recoil[] recoilBuf;
/*     */   protected int numAmmo;
/*     */   protected int numRestAllAmmo;
/*     */   public int currentHeat;
/*     */   public int cooldownSpeed;
/*     */   public int countWait;
/*     */   public int countReloadWait;
/*     */   protected int[] lastUsedCount;
/*     */   public int soundWait;
/*  46 */   private int lastUsedOptionParameter1 = 0;
/*  47 */   private int lastUsedOptionParameter2 = 0;
/*     */   
/*     */   public float rotBarrelSpd;
/*     */   public float rotBarrel;
/*     */   public float prevRotBarrel;
/*     */   
/*     */   public MCH_WeaponSet(MCH_WeaponBase[] weapon) {
/*  54 */     this.name = (weapon[0]).name;
/*  55 */     this.weapons = weapon;
/*  56 */     this.currentWeaponIndex = 0;
/*  57 */     this.countWait = 0;
/*  58 */     this.countReloadWait = 0;
/*  59 */     this.lastUsedCount = new int[weapon.length];
/*     */     
/*  61 */     this.rotationYaw = 0.0F;
/*  62 */     this.prevRotationYaw = 0.0F;
/*  63 */     this.rotationPitch = 0.0F;
/*  64 */     this.prevRotationPitch = 0.0F;
/*  65 */     setAmmoNum(0);
/*  66 */     setRestAllAmmoNum(0);
/*  67 */     this.currentHeat = 0;
/*  68 */     this.soundWait = 0;
/*  69 */     this.cooldownSpeed = 1;
/*  70 */     this.rotBarrelSpd = 0.0F;
/*  71 */     this.rotBarrel = 0.0F;
/*  72 */     this.prevRotBarrel = 0.0F;
/*  73 */     this.recoilBuf = new Recoil[weapon.length];
/*  74 */     for (int i = 0; i < this.recoilBuf.length; i++)
/*     */     {
/*  76 */       this.recoilBuf[i] = new Recoil(this, (weapon[i].getInfo()).recoilBufCount, 
/*  77 */           (weapon[i].getInfo()).recoilBufCountSpeed);
/*     */     }
/*     */     
/*  80 */     this.defaultRotationYaw = 0.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public MCH_WeaponSet(MCH_WeaponBase weapon) {
/*  85 */     this(new MCH_WeaponBase[] { weapon });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEqual(String s) {
/*  93 */     return this.name.equalsIgnoreCase(s);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getAmmoNum() {
/*  98 */     return this.numAmmo;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getAmmoNumMax() {
/* 103 */     return getFirstWeapon().getNumAmmoMax();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getRestAllAmmoNum() {
/* 108 */     return this.numRestAllAmmo;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getAllAmmoNum() {
/* 113 */     return getFirstWeapon().getAllAmmoNum();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setAmmoNum(int n) {
/* 118 */     this.numAmmo = n;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setRestAllAmmoNum(int n) {
/* 123 */     int debugBefore = this.numRestAllAmmo;
/* 124 */     int m = (getInfo()).maxAmmo - getAmmoNum();
/* 125 */     this.numRestAllAmmo = (n <= m) ? n : m;
/* 126 */     MCH_Lib.DbgLog((getFirstWeapon()).worldObj, "MCH_WeaponSet.setRestAllAmmoNum:%s %d->%d (%d)", new Object[] {
/*     */           
/* 128 */           getName(), Integer.valueOf(debugBefore), Integer.valueOf(this.numRestAllAmmo), Integer.valueOf(n)
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public void supplyRestAllAmmo() {
/* 134 */     int m = (getInfo()).maxAmmo;
/* 135 */     if (getRestAllAmmoNum() + getAmmoNum() < m)
/*     */     {
/* 137 */       setRestAllAmmoNum(getRestAllAmmoNum() + getAmmoNum() + (getInfo()).suppliedNum);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isInPreparation() {
/* 143 */     return (this.countWait < 0 || this.countReloadWait > 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/* 148 */     MCH_WeaponBase w = getCurrentWeapon();
/* 149 */     return (w != null) ? w.getName() : "";
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canUse() {
/* 154 */     return (this.countWait == 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isLongDelayWeapon() {
/* 159 */     return ((getInfo()).delay > 4);
/*     */   }
/*     */ 
/*     */   
/*     */   public void reload() {
/* 164 */     MCH_WeaponBase crtWpn = getCurrentWeapon();
/* 165 */     if (getAmmoNumMax() > 0 && getAmmoNum() < getAmmoNumMax() && crtWpn.getReloadCount() > 0) {
/*     */       
/* 167 */       this.countReloadWait = crtWpn.getReloadCount();
/*     */       
/* 169 */       if (crtWpn.worldObj.field_72995_K)
/*     */       {
/* 171 */         setAmmoNum(0);
/*     */       }
/*     */       
/* 174 */       if (!crtWpn.worldObj.field_72995_K) {
/*     */         
/* 176 */         this.countReloadWait -= 20;
/* 177 */         if (this.countReloadWait <= 0)
/*     */         {
/* 179 */           this.countReloadWait = 1;
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void reloadMag() {
/* 187 */     int restAmmo = getRestAllAmmoNum();
/* 188 */     int nAmmo = getAmmoNumMax() - getAmmoNum();
/* 189 */     if (nAmmo > 0) {
/*     */       
/* 191 */       if (nAmmo > restAmmo)
/*     */       {
/* 193 */         nAmmo = restAmmo;
/*     */       }
/* 195 */       setAmmoNum(getAmmoNum() + nAmmo);
/* 196 */       setRestAllAmmoNum(getRestAllAmmoNum() - nAmmo);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void switchMode() {
/* 202 */     boolean isChanged = false;
/* 203 */     for (MCH_WeaponBase w : this.weapons) {
/*     */       
/* 205 */       if (w != null)
/* 206 */         isChanged = (w.switchMode() || isChanged); 
/*     */     } 
/* 208 */     if (isChanged) {
/*     */       
/* 210 */       int cntSwitch = 15;
/* 211 */       if (this.countWait >= -cntSwitch)
/*     */       {
/*     */         
/* 214 */         if (this.countWait > cntSwitch) {
/*     */           
/* 216 */           this.countWait = -this.countWait;
/*     */         }
/*     */         else {
/*     */           
/* 220 */           this.countWait = -cntSwitch;
/*     */         } 
/*     */       }
/* 223 */       if ((getCurrentWeapon()).worldObj.field_72995_K)
/*     */       {
/*     */         
/* 226 */         W_McClient.playSoundClick(1.0F, 1.0F);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onSwitchWeapon(boolean isRemote, boolean isCreative) {
/* 233 */     int cntSwitch = 15;
/* 234 */     if (isRemote)
/* 235 */       cntSwitch += 10; 
/* 236 */     if (this.countWait >= -cntSwitch)
/*     */     {
/*     */       
/* 239 */       if (this.countWait > cntSwitch) {
/*     */         
/* 241 */         this.countWait = -this.countWait;
/*     */       }
/*     */       else {
/*     */         
/* 245 */         this.countWait = -cntSwitch;
/*     */       } 
/*     */     }
/* 248 */     this.currentWeaponIndex = 0;
/*     */     
/* 250 */     if (isCreative)
/*     */     {
/* 252 */       setAmmoNum(getAmmoNumMax());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isUsed(int index) {
/* 258 */     MCH_WeaponBase w = getFirstWeapon();
/* 259 */     if (w != null && index < this.lastUsedCount.length) {
/*     */       
/* 261 */       int cnt = this.lastUsedCount[index];
/* 262 */       return ((w.interval >= 4 && cnt > w.interval / 2) || cnt >= 4);
/*     */     } 
/* 264 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void update(Entity shooter, boolean isSelected, boolean isUsed) {
/* 269 */     if (getCurrentWeapon().getInfo() == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 274 */     if (this.countReloadWait > 0) {
/*     */       
/* 276 */       this.countReloadWait--;
/* 277 */       if (this.countReloadWait == 0)
/*     */       {
/* 279 */         reloadMag();
/*     */       }
/*     */     } 
/*     */     
/* 283 */     for (int i = 0; i < this.lastUsedCount.length; i++) {
/*     */       
/* 285 */       if (this.lastUsedCount[i] > 0)
/*     */       {
/* 287 */         if (this.lastUsedCount[i] == 4) {
/*     */           
/* 289 */           if (0 == getCurrentWeaponIndex() && canUse())
/*     */           {
/* 291 */             if (getAmmoNum() > 0 || getAllAmmoNum() <= 0)
/*     */             {
/* 293 */               this.lastUsedCount[i] = this.lastUsedCount[i] - 1;
/*     */             
/*     */             }
/*     */           }
/*     */         }
/*     */         else {
/*     */           
/* 300 */           this.lastUsedCount[i] = this.lastUsedCount[i] - 1;
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 305 */     if (this.currentHeat > 0) {
/*     */       
/* 307 */       if (this.currentHeat < (getCurrentWeapon().getInfo()).maxHeatCount)
/*     */       {
/* 309 */         this.cooldownSpeed++;
/*     */       }
/* 311 */       this.currentHeat -= this.cooldownSpeed / 20 + 1;
/* 312 */       if (this.currentHeat < 0)
/*     */       {
/* 314 */         this.currentHeat = 0;
/*     */       }
/*     */     } 
/*     */     
/* 318 */     if (this.countWait > 0)
/* 319 */       this.countWait--; 
/* 320 */     if (this.countWait < 0)
/*     */     {
/* 322 */       this.countWait++;
/*     */     }
/*     */     
/* 325 */     this.prevRotationYaw = this.rotationYaw;
/* 326 */     this.prevRotationPitch = this.rotationPitch;
/*     */     
/* 328 */     if (this.weapons != null && this.weapons.length > 0)
/*     */     {
/* 330 */       for (MCH_WeaponBase w : this.weapons) {
/*     */         
/* 332 */         if (w != null)
/*     */         {
/* 334 */           w.update(this.countWait);
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 339 */     if (this.soundWait > 0)
/*     */     {
/* 341 */       this.soundWait--;
/*     */     }
/*     */     
/* 344 */     if (isUsed)
/*     */     {
/* 346 */       if (this.rotBarrelSpd < 75.0F) {
/*     */         
/* 348 */         this.rotBarrelSpd += (25 + rand.nextInt(3));
/* 349 */         if (this.rotBarrelSpd > 74.0F)
/*     */         {
/* 351 */           this.rotBarrelSpd = 74.0F;
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 356 */     this.prevRotBarrel = this.rotBarrel;
/* 357 */     this.rotBarrel += this.rotBarrelSpd;
/* 358 */     if (this.rotBarrel >= 360.0F) {
/*     */       
/* 360 */       this.rotBarrel -= 360.0F;
/* 361 */       this.prevRotBarrel -= 360.0F;
/*     */     } 
/* 363 */     if (this.rotBarrelSpd > 0.0F) {
/*     */       
/* 365 */       this.rotBarrelSpd -= 1.48F;
/* 366 */       if (this.rotBarrelSpd < 0.0F)
/*     */       {
/* 368 */         this.rotBarrelSpd = 0.0F;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateWeapon(Entity shooter, boolean isUsed, int index) {
/* 375 */     MCH_WeaponBase crtWpn = getWeapon(index);
/*     */     
/* 377 */     if (isUsed)
/*     */     {
/* 379 */       if (shooter.field_70170_p.field_72995_K && crtWpn != null && crtWpn.cartridge != null) {
/*     */         
/* 381 */         Vec3d v = crtWpn.getShotPos(shooter);
/*     */         
/* 383 */         float yaw = shooter.field_70177_z;
/* 384 */         float pitch = shooter.field_70125_A;
/*     */ 
/*     */         
/* 387 */         if (!(shooter instanceof mcheli.vehicle.MCH_EntityVehicle) || shooter.func_184207_aI());
/*     */ 
/*     */ 
/*     */         
/* 391 */         MCH_EntityCartridge.spawnCartridge(shooter.field_70170_p, crtWpn.cartridge, shooter.field_70165_t + v.field_72450_a, shooter.field_70163_u + v.field_72448_b, shooter.field_70161_v + v.field_72449_c, shooter.field_70159_w, shooter.field_70181_x, shooter.field_70179_y, yaw + this.rotationYaw, pitch + this.rotationPitch);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 397 */     if (index < this.recoilBuf.length) {
/*     */       
/* 399 */       Recoil r = this.recoilBuf[index];
/* 400 */       r.prevRecoilBuf = r.recoilBuf;
/*     */       
/* 402 */       if (isUsed && r.recoilBufCount <= 0)
/*     */       {
/* 404 */         r.recoilBufCount = r.recoilBufCountMax;
/*     */       }
/* 406 */       if (r.recoilBufCount > 0) {
/*     */         
/* 408 */         if (r.recoilBufCountMax <= 1) {
/*     */           
/* 410 */           r.recoilBuf = 1.0F;
/*     */         }
/* 412 */         else if (r.recoilBufCountMax == 2) {
/*     */           
/* 414 */           r.recoilBuf = (r.recoilBufCount == 2) ? 1.0F : 0.6F;
/*     */         }
/*     */         else {
/*     */           
/* 418 */           if (r.recoilBufCount > r.recoilBufCountMax / 2)
/*     */           {
/* 420 */             r.recoilBufCount -= r.recoilBufCountSpeed;
/*     */           }
/* 422 */           float rb = (r.recoilBufCount / r.recoilBufCountMax);
/* 423 */           r.recoilBuf = MathHelper.func_76126_a(rb * 3.1415927F);
/*     */         } 
/* 425 */         r.recoilBufCount--;
/*     */       }
/*     */       else {
/*     */         
/* 429 */         r.recoilBuf = 0.0F;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean use(MCH_WeaponParam prm) {
/* 436 */     MCH_WeaponBase crtWpn = getCurrentWeapon();
/* 437 */     if (crtWpn != null && crtWpn.getInfo() != null) {
/*     */       
/* 439 */       MCH_WeaponInfo info = crtWpn.getInfo();
/* 440 */       if ((getAmmoNumMax() <= 0 || getAmmoNum() > 0) && (info.maxHeatCount <= 0 || this.currentHeat < info.maxHeatCount)) {
/*     */ 
/*     */         
/* 443 */         crtWpn.canPlaySound = (this.soundWait == 0);
/*     */         
/* 445 */         prm.rotYaw = (prm.entity != null) ? prm.entity.field_70177_z : 0.0F;
/* 446 */         prm.rotPitch = (prm.entity != null) ? prm.entity.field_70125_A : 0.0F;
/*     */         
/* 448 */         prm.rotYaw += this.rotationYaw + crtWpn.fixRotationYaw;
/* 449 */         prm.rotPitch += this.rotationPitch + crtWpn.fixRotationPitch;
/*     */         
/* 451 */         if (info.accuracy > 0.0F) {
/*     */           
/* 453 */           prm.rotYaw += (rand.nextFloat() - 0.5F) * info.accuracy;
/* 454 */           prm.rotPitch += (rand.nextFloat() - 0.5F) * info.accuracy;
/*     */         } 
/*     */         
/* 457 */         prm.rotYaw = MathHelper.func_76142_g(prm.rotYaw);
/* 458 */         prm.rotPitch = MathHelper.func_76142_g(prm.rotPitch);
/*     */         
/* 460 */         if (crtWpn.use(prm)) {
/*     */           
/* 462 */           if (info.maxHeatCount > 0) {
/*     */             
/* 464 */             this.cooldownSpeed = 1;
/* 465 */             this.currentHeat += crtWpn.heatCount;
/* 466 */             if (this.currentHeat >= info.maxHeatCount)
/*     */             {
/* 468 */               this.currentHeat += 30;
/*     */             }
/*     */           } 
/*     */           
/* 472 */           if (info.soundDelay > 0 && this.soundWait == 0)
/*     */           {
/* 474 */             this.soundWait = info.soundDelay;
/*     */           }
/*     */           
/* 477 */           this.lastUsedOptionParameter1 = crtWpn.optionParameter1;
/* 478 */           this.lastUsedOptionParameter2 = crtWpn.optionParameter2;
/*     */           
/* 480 */           this.lastUsedCount[this.currentWeaponIndex] = (crtWpn.interval > 0) ? crtWpn.interval : -crtWpn.interval;
/*     */           
/* 482 */           if (crtWpn.isCooldownCountReloadTime() && crtWpn
/* 483 */             .getReloadCount() - 10 > this.lastUsedCount[this.currentWeaponIndex])
/*     */           {
/*     */             
/* 486 */             this.lastUsedCount[this.currentWeaponIndex] = crtWpn.getReloadCount() - 10;
/*     */           }
/* 488 */           this.currentWeaponIndex = (this.currentWeaponIndex + 1) % this.weapons.length;
/*     */           
/* 490 */           this.countWait = (prm.user instanceof net.minecraft.entity.player.EntityPlayer) ? crtWpn.interval : crtWpn.delayedInterval;
/*     */           
/* 492 */           this.countReloadWait = 0;
/* 493 */           if (getAmmoNum() > 0)
/* 494 */             setAmmoNum(getAmmoNum() - 1); 
/* 495 */           if (getAmmoNum() <= 0) {
/*     */             
/* 497 */             if (prm.isInfinity && getRestAllAmmoNum() < getAmmoNumMax())
/*     */             {
/* 499 */               setRestAllAmmoNum(getAmmoNumMax());
/*     */             }
/* 501 */             reload();
/* 502 */             prm.reload = true;
/*     */             
/* 504 */             if (prm.user instanceof mcheli.mob.MCH_EntityGunner)
/*     */             {
/* 506 */               this.countWait += ((this.countWait >= 0) ? 1 : -1) * crtWpn.getReloadCount();
/*     */             }
/*     */           } 
/*     */           
/* 510 */           prm.result = true;
/*     */         } 
/*     */       } 
/*     */     } 
/* 514 */     return prm.result;
/*     */   }
/*     */ 
/*     */   
/*     */   public void waitAndReloadByOther(boolean reload) {
/* 519 */     MCH_WeaponBase crtWpn = getCurrentWeapon();
/* 520 */     if (crtWpn != null && crtWpn.getInfo() != null) {
/*     */       
/* 522 */       this.countWait = crtWpn.interval;
/*     */       
/* 524 */       this.countReloadWait = 0;
/* 525 */       if (reload)
/*     */       {
/* 527 */         if (getAmmoNumMax() > 0 && crtWpn.getReloadCount() > 0) {
/*     */           
/* 529 */           this.countReloadWait = crtWpn.getReloadCount();
/*     */           
/* 531 */           if (!crtWpn.worldObj.field_72995_K) {
/*     */             
/* 533 */             this.countReloadWait -= 20;
/* 534 */             if (this.countReloadWait <= 0)
/*     */             {
/* 536 */               this.countReloadWait = 1;
/*     */             }
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int getLastUsedOptionParameter1() {
/* 546 */     return this.lastUsedOptionParameter1;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getLastUsedOptionParameter2() {
/* 551 */     return this.lastUsedOptionParameter2;
/*     */   }
/*     */ 
/*     */   
/*     */   public MCH_WeaponBase getFirstWeapon() {
/* 556 */     return getWeapon(0);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getCurrentWeaponIndex() {
/* 561 */     return this.currentWeaponIndex;
/*     */   }
/*     */ 
/*     */   
/*     */   public MCH_WeaponBase getCurrentWeapon() {
/* 566 */     return getWeapon(this.currentWeaponIndex);
/*     */   }
/*     */ 
/*     */   
/*     */   public MCH_WeaponBase getWeapon(int idx) {
/* 571 */     if (this.weapons != null && this.weapons.length > 0 && idx < this.weapons.length)
/*     */     {
/* 573 */       return this.weapons[idx];
/*     */     }
/*     */     
/* 576 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getWeaponNum() {
/* 581 */     return (this.weapons != null) ? this.weapons.length : 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public MCH_WeaponInfo getInfo() {
/* 586 */     return getFirstWeapon().getInfo();
/*     */   }
/*     */ 
/*     */   
/*     */   public double getLandInDistance(MCH_WeaponParam prm) {
/* 591 */     double ret = -1.0D;
/* 592 */     MCH_WeaponBase crtWpn = getCurrentWeapon();
/*     */     
/* 594 */     if (crtWpn != null && crtWpn.getInfo() != null) {
/*     */ 
/*     */ 
/*     */       
/* 598 */       prm.rotYaw = (prm.entity != null) ? prm.entity.field_70177_z : 0.0F;
/* 599 */       prm.rotPitch = (prm.entity != null) ? prm.entity.field_70125_A : 0.0F;
/*     */       
/* 601 */       prm.rotYaw += this.rotationYaw + crtWpn.fixRotationYaw;
/* 602 */       prm.rotPitch += this.rotationPitch + crtWpn.fixRotationPitch;
/*     */       
/* 604 */       prm.rotYaw = MathHelper.func_76142_g(prm.rotYaw);
/* 605 */       prm.rotPitch = MathHelper.func_76142_g(prm.rotPitch);
/*     */       
/* 607 */       return crtWpn.getLandInDistance(prm);
/*     */     } 
/* 609 */     return ret;
/*     */   }
/*     */ 
/*     */   
/*     */   public class Recoil
/*     */   {
/*     */     public int recoilBufCount;
/*     */     
/*     */     public final int recoilBufCountMax;
/*     */     
/*     */     public final int recoilBufCountSpeed;
/*     */     
/*     */     public float recoilBuf;
/*     */     
/*     */     public float prevRecoilBuf;
/*     */     
/*     */     public Recoil(MCH_WeaponSet paramMCH_WeaponSet, int max, int spd) {
/* 626 */       this.recoilBufCountMax = max;
/* 627 */       this.recoilBufCountSpeed = spd;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\weapon\MCH_WeaponSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */