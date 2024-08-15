/*     */ package mcheli.weapon;
/*     */ 
/*     */ import java.util.Random;
/*     */ import mcheli.MCH_Lib;
/*     */ import mcheli.aircraft.MCH_EntityAircraft;
/*     */ import mcheli.wrapper.W_McClient;
/*     */ import mcheli.wrapper.W_WorldFunc;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.math.RayTraceResult;
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
/*     */ public abstract class MCH_WeaponBase
/*     */ {
/*  24 */   protected static final Random rand = new Random();
/*     */   
/*     */   public final World worldObj;
/*     */   
/*     */   public final Vec3d position;
/*     */   public final float fixRotationYaw;
/*     */   public final float fixRotationPitch;
/*     */   public final String name;
/*     */   public final MCH_WeaponInfo weaponInfo;
/*     */   public String displayName;
/*     */   public int power;
/*     */   public float acceleration;
/*     */   public int explosionPower;
/*     */   public int explosionPowerInWater;
/*     */   public int interval;
/*     */   public int delayedInterval;
/*     */   public int numMode;
/*     */   public int lockTime;
/*     */   public int piercing;
/*     */   public int heatCount;
/*     */   public MCH_Cartridge cartridge;
/*     */   public boolean onTurret;
/*     */   public MCH_EntityAircraft aircraft;
/*     */   public int tick;
/*     */   public int optionParameter1;
/*     */   public int optionParameter2;
/*     */   private int currentMode;
/*     */   public boolean canPlaySound;
/*     */   
/*     */   public MCH_WeaponBase(World w, Vec3d v, float yaw, float pitch, String nm, MCH_WeaponInfo wi) {
/*  54 */     this.worldObj = w;
/*  55 */     this.position = v;
/*  56 */     this.fixRotationYaw = yaw;
/*  57 */     this.fixRotationPitch = pitch;
/*  58 */     this.name = nm;
/*  59 */     this.weaponInfo = wi;
/*     */     
/*  61 */     this.displayName = (wi != null) ? wi.displayName : "";
/*  62 */     this.power = 0;
/*  63 */     this.acceleration = 0.0F;
/*  64 */     this.explosionPower = 0;
/*  65 */     this.explosionPowerInWater = 0;
/*  66 */     this.interval = 1;
/*  67 */     this.numMode = 0;
/*  68 */     this.lockTime = 0;
/*  69 */     this.heatCount = 0;
/*  70 */     this.cartridge = null;
/*     */     
/*  72 */     this.tick = 0;
/*  73 */     this.optionParameter1 = 0;
/*  74 */     this.optionParameter2 = 0;
/*  75 */     setCurrentMode(0);
/*  76 */     this.canPlaySound = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public MCH_WeaponInfo getInfo() {
/*  81 */     return this.weaponInfo;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/*  86 */     return this.displayName;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract boolean shot(MCH_WeaponParam paramMCH_WeaponParam);
/*     */ 
/*     */   
/*     */   public void setLockChecker(MCH_IEntityLockChecker checker) {}
/*     */ 
/*     */   
/*     */   public void setLockCountMax(int n) {}
/*     */ 
/*     */   
/*     */   public int getLockCount() {
/* 101 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getLockCountMax() {
/* 106 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public final int getNumAmmoMax() {
/* 111 */     return (getInfo()).round;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getCurrentMode() {
/* 116 */     return (getInfo() != null && (getInfo()).fixMode > 0) ? (getInfo()).fixMode : this.currentMode;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCurrentMode(int currentMode) {
/* 121 */     this.currentMode = currentMode;
/*     */   }
/*     */ 
/*     */   
/*     */   public final int getAllAmmoNum() {
/* 126 */     return (getInfo()).maxAmmo;
/*     */   }
/*     */ 
/*     */   
/*     */   public final int getReloadCount() {
/* 131 */     return (getInfo()).reloadTime;
/*     */   }
/*     */ 
/*     */   
/*     */   public final MCH_SightType getSightType() {
/* 136 */     return (getInfo()).sight;
/*     */   }
/*     */ 
/*     */   
/*     */   public MCH_WeaponGuidanceSystem getGuidanceSystem() {
/* 141 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void update(int countWait) {
/* 146 */     if (countWait != 0) {
/* 147 */       this.tick++;
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean isCooldownCountReloadTime() {
/* 152 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void modifyCommonParameters() {
/* 157 */     modifyParameters();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void modifyParameters() {}
/*     */ 
/*     */   
/*     */   public boolean switchMode() {
/* 166 */     if (getInfo() != null && (getInfo()).fixMode > 0)
/*     */     {
/* 168 */       return false;
/*     */     }
/*     */     
/* 171 */     int beforeMode = getCurrentMode();
/* 172 */     if (this.numMode > 0) {
/*     */       
/* 174 */       setCurrentMode((getCurrentMode() + 1) % this.numMode);
/*     */     }
/*     */     else {
/*     */       
/* 178 */       setCurrentMode(0);
/*     */     } 
/* 180 */     if (beforeMode != getCurrentMode())
/*     */     {
/* 182 */       onSwitchMode();
/*     */     }
/* 184 */     return (beforeMode != getCurrentMode());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onSwitchMode() {}
/*     */ 
/*     */   
/*     */   public boolean use(MCH_WeaponParam prm) {
/* 193 */     Vec3d v = getShotPos(prm.entity);
/* 194 */     prm.posX += v.field_72450_a;
/* 195 */     prm.posY += v.field_72448_b;
/* 196 */     prm.posZ += v.field_72449_c;
/* 197 */     if (shot(prm)) {
/*     */       
/* 199 */       this.tick = 0;
/* 200 */       return true;
/*     */     } 
/* 202 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public Vec3d getShotPos(Entity entity) {
/* 207 */     if (entity instanceof MCH_EntityAircraft && this.onTurret)
/*     */     {
/* 209 */       return ((MCH_EntityAircraft)entity).calcOnTurretPos(this.position);
/*     */     }
/*     */     
/* 212 */     Vec3d v = new Vec3d(this.position.field_72450_a, this.position.field_72448_b, this.position.field_72449_c);
/* 213 */     float roll = (entity instanceof MCH_EntityAircraft) ? ((MCH_EntityAircraft)entity).getRotRoll() : 0.0F;
/* 214 */     return MCH_Lib.RotVec3(v, -entity.field_70177_z, -entity.field_70125_A, -roll);
/*     */   }
/*     */ 
/*     */   
/*     */   public void playSound(Entity e) {
/* 219 */     playSound(e, (getInfo()).soundFileName);
/*     */   }
/*     */ 
/*     */   
/*     */   public void playSound(Entity e, String snd) {
/* 224 */     if (!e.field_70170_p.field_72995_K && this.canPlaySound && getInfo() != null) {
/*     */       
/* 226 */       float prnd = (getInfo()).soundPitchRandom;
/* 227 */       W_WorldFunc.MOD_playSoundEffect(this.worldObj, e.field_70165_t, e.field_70163_u, e.field_70161_v, snd, (getInfo()).soundVolume, 
/* 228 */           (getInfo()).soundPitch * (1.0F - prnd) + rand.nextFloat() * prnd);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void playSoundClient(Entity e, float volume, float pitch) {
/* 234 */     if (e.field_70170_p.field_72995_K && getInfo() != null)
/*     */     {
/* 236 */       W_McClient.MOD_playSoundFX((getInfo()).soundFileName, volume, pitch);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public double getLandInDistance(MCH_WeaponParam prm) {
/* 242 */     if (this.weaponInfo == null)
/*     */     {
/* 244 */       return -1.0D;
/*     */     }
/*     */     
/* 247 */     if (this.weaponInfo.gravity >= 0.0F)
/*     */     {
/* 249 */       return -1.0D;
/*     */     }
/*     */     
/* 252 */     Vec3d v = MCH_Lib.RotVec3(0.0D, 0.0D, 1.0D, -prm.rotYaw, -prm.rotPitch, -prm.rotRoll);
/* 253 */     double s = Math.sqrt(v.field_72450_a * v.field_72450_a + v.field_72448_b * v.field_72448_b + v.field_72449_c * v.field_72449_c);
/*     */     
/* 255 */     double acc = (this.acceleration < 4.0F) ? this.acceleration : 4.0D;
/* 256 */     double accFac = this.acceleration / acc;
/*     */     
/* 258 */     double my = v.field_72448_b * this.acceleration / s;
/*     */     
/* 260 */     if (my <= 0.0D)
/*     */     {
/* 262 */       return -1.0D;
/*     */     }
/*     */     
/* 265 */     double mx = v.field_72450_a * this.acceleration / s;
/* 266 */     double mz = v.field_72449_c * this.acceleration / s;
/*     */     
/* 268 */     double ls = my / this.weaponInfo.gravity;
/* 269 */     double gravity = this.weaponInfo.gravity * accFac;
/*     */     
/* 271 */     if (ls < -12.0D) {
/*     */       
/* 273 */       double f = ls / -12.0D;
/* 274 */       mx *= f;
/* 275 */       my *= f;
/* 276 */       mz *= f;
/* 277 */       gravity *= f * f * 0.95D;
/*     */     } 
/*     */     
/* 280 */     double spx = prm.posX;
/* 281 */     double spy = prm.posY + 3.0D;
/* 282 */     double spz = prm.posZ;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 287 */     for (int i = 0; i < 50; i++) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 295 */       Vec3d vs = new Vec3d(spx, spy, spz);
/* 296 */       Vec3d ve = new Vec3d(spx + mx, spy + my, spz + mz);
/*     */       
/* 298 */       RayTraceResult mop = this.worldObj.func_72933_a(vs, ve);
/*     */ 
/*     */       
/* 301 */       if (mop != null && mop.field_72313_a == RayTraceResult.Type.BLOCK) {
/*     */ 
/*     */ 
/*     */         
/* 305 */         double dx = mop.func_178782_a().func_177958_n() - prm.posX;
/* 306 */         double dz = mop.func_178782_a().func_177952_p() - prm.posZ;
/* 307 */         return Math.sqrt(dx * dx + dz * dz);
/*     */       } 
/*     */       
/* 310 */       my += gravity;
/* 311 */       spx += mx;
/* 312 */       spy += my;
/* 313 */       spz += mz;
/*     */       
/* 315 */       if (spy < prm.posY) {
/*     */         
/* 317 */         double dx = spx - prm.posX;
/* 318 */         double dz = spz - prm.posZ;
/* 319 */         return Math.sqrt(dx * dx + dz * dz);
/*     */       } 
/*     */     } 
/*     */     
/* 323 */     return -1.0D;
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\weapon\MCH_WeaponBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */