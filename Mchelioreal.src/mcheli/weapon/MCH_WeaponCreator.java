/*     */ package mcheli.weapon;
/*     */ 
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.World;
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
/*     */ public class MCH_WeaponCreator
/*     */ {
/*     */   @Nullable
/*     */   public static MCH_WeaponBase createWeapon(World w, String weaponName, Vec3d v, float yaw, float pitch, MCH_IEntityLockChecker lockChecker, boolean onTurret) {
/*  20 */     MCH_WeaponInfo info = MCH_WeaponInfoManager.get(weaponName);
/*     */     
/*  22 */     if (info == null || info.type == "")
/*     */     {
/*  24 */       return null;
/*     */     }
/*     */     
/*  27 */     MCH_WeaponBase weapon = null;
/*     */     
/*  29 */     if (info.type.compareTo("machinegun1") == 0)
/*  30 */       weapon = new MCH_WeaponMachineGun1(w, v, yaw, pitch, weaponName, info); 
/*  31 */     if (info.type.compareTo("machinegun2") == 0)
/*  32 */       weapon = new MCH_WeaponMachineGun2(w, v, yaw, pitch, weaponName, info); 
/*  33 */     if (info.type.compareTo("tvmissile") == 0)
/*  34 */       weapon = new MCH_WeaponTvMissile(w, v, yaw, pitch, weaponName, info); 
/*  35 */     if (info.type.compareTo("torpedo") == 0)
/*  36 */       weapon = new MCH_WeaponTorpedo(w, v, yaw, pitch, weaponName, info); 
/*  37 */     if (info.type.compareTo("cas") == 0)
/*  38 */       weapon = new MCH_WeaponCAS(w, v, yaw, pitch, weaponName, info); 
/*  39 */     if (info.type.compareTo("rocket") == 0)
/*  40 */       weapon = new MCH_WeaponRocket(w, v, yaw, pitch, weaponName, info); 
/*  41 */     if (info.type.compareTo("asmissile") == 0)
/*  42 */       weapon = new MCH_WeaponASMissile(w, v, yaw, pitch, weaponName, info); 
/*  43 */     if (info.type.compareTo("aamissile") == 0)
/*  44 */       weapon = new MCH_WeaponAAMissile(w, v, yaw, pitch, weaponName, info); 
/*  45 */     if (info.type.compareTo("atmissile") == 0)
/*  46 */       weapon = new MCH_WeaponATMissile(w, v, yaw, pitch, weaponName, info); 
/*  47 */     if (info.type.compareTo("bomb") == 0)
/*  48 */       weapon = new MCH_WeaponBomb(w, v, yaw, pitch, weaponName, info); 
/*  49 */     if (info.type.compareTo("mkrocket") == 0)
/*  50 */       weapon = new MCH_WeaponMarkerRocket(w, v, yaw, pitch, weaponName, info); 
/*  51 */     if (info.type.compareTo("dummy") == 0)
/*  52 */       weapon = new MCH_WeaponDummy(w, v, yaw, pitch, weaponName, info); 
/*  53 */     if (info.type.compareTo("smoke") == 0)
/*  54 */       weapon = new MCH_WeaponSmoke(w, v, yaw, pitch, weaponName, info); 
/*  55 */     if (info.type.compareTo("dispenser") == 0)
/*  56 */       weapon = new MCH_WeaponDispenser(w, v, yaw, pitch, weaponName, info); 
/*  57 */     if (info.type.compareTo("targetingpod") == 0)
/*     */     {
/*  59 */       weapon = new MCH_WeaponTargetingPod(w, v, yaw, pitch, weaponName, info);
/*     */     }
/*     */     
/*  62 */     if (weapon != null) {
/*     */       
/*  64 */       weapon.displayName = info.displayName;
/*  65 */       weapon.power = info.power;
/*  66 */       weapon.acceleration = info.acceleration;
/*  67 */       weapon.explosionPower = info.explosion;
/*  68 */       weapon.explosionPowerInWater = info.explosionInWater;
/*  69 */       int interval = info.delay;
/*  70 */       weapon.interval = info.delay;
/*  71 */       weapon.delayedInterval = info.delay;
/*  72 */       weapon.setLockCountMax(info.lockTime);
/*  73 */       weapon.setLockChecker(lockChecker);
/*  74 */       weapon.numMode = info.modeNum;
/*  75 */       weapon.piercing = info.piercing;
/*  76 */       weapon.heatCount = info.heatCount;
/*  77 */       weapon.onTurret = onTurret;
/*     */       
/*  79 */       if (info.maxHeatCount > 0)
/*     */       {
/*  81 */         if (weapon.heatCount < 2)
/*     */         {
/*  83 */           weapon.heatCount = 2;
/*     */         }
/*     */       }
/*     */       
/*  87 */       if (interval < 4) {
/*     */         
/*  89 */         interval++;
/*     */       }
/*  91 */       else if (interval < 7) {
/*     */         
/*  93 */         interval += 2;
/*     */       }
/*  95 */       else if (interval < 10) {
/*     */         
/*  97 */         interval += 3;
/*     */       }
/*  99 */       else if (interval < 20) {
/*     */         
/* 101 */         interval += 6;
/*     */       }
/*     */       else {
/*     */         
/* 105 */         interval += 10;
/*     */         
/* 107 */         if (interval >= 40)
/*     */         {
/* 109 */           interval = -interval;
/*     */         }
/*     */       } 
/*     */       
/* 113 */       weapon.delayedInterval = interval;
/*     */       
/* 115 */       if (w.field_72995_K) {
/*     */         
/* 117 */         weapon.interval = interval;
/*     */         
/* 119 */         weapon.heatCount++;
/*     */         
/* 121 */         weapon.cartridge = info.cartridge;
/*     */       } 
/* 123 */       weapon.modifyCommonParameters();
/*     */     } 
/*     */     
/* 126 */     return weapon;
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\weapon\MCH_WeaponCreator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */