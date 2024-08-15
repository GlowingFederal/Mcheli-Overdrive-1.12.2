/*     */ package mcheli.weapon;
/*     */ 
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import mcheli.MCH_Lib;
/*     */ import mcheli.MCH_PacketNotifyLock;
/*     */ import mcheli.aircraft.MCH_EntityAircraft;
/*     */ import mcheli.uav.MCH_EntityUavStation;
/*     */ import mcheli.wrapper.W_Entity;
/*     */ import mcheli.wrapper.W_Lib;
/*     */ import mcheli.wrapper.W_MovingObjectPosition;
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
/*     */ 
/*     */ public class MCH_WeaponGuidanceSystem
/*     */ {
/*     */   protected World worldObj;
/*     */   public Entity lastLockEntity;
/*     */   private Entity targetEntity;
/*     */   private int lockCount;
/*     */   private int lockSoundCount;
/*     */   private int continueLockCount;
/*     */   private int lockCountMax;
/*     */   private int prevLockCount;
/*     */   public boolean canLockInWater;
/*     */   public boolean canLockOnGround;
/*     */   public boolean canLockInAir;
/*     */   public boolean ridableOnly;
/*     */   public double lockRange;
/*     */   public int lockAngle;
/*     */   public MCH_IEntityLockChecker checker;
/*     */   
/*     */   public MCH_WeaponGuidanceSystem() {
/*  47 */     this(null);
/*     */   }
/*     */ 
/*     */   
/*     */   public MCH_WeaponGuidanceSystem(World w) {
/*  52 */     this.worldObj = w;
/*  53 */     this.targetEntity = null;
/*  54 */     this.lastLockEntity = null;
/*  55 */     this.lockCount = 0;
/*  56 */     this.continueLockCount = 0;
/*  57 */     this.lockCountMax = 1;
/*  58 */     this.prevLockCount = 0;
/*  59 */     this.canLockInWater = false;
/*  60 */     this.canLockOnGround = false;
/*  61 */     this.canLockInAir = false;
/*  62 */     this.ridableOnly = false;
/*  63 */     this.lockRange = 50.0D;
/*  64 */     this.lockAngle = 10;
/*  65 */     this.checker = null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setWorld(World w) {
/*  70 */     this.worldObj = w;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setLockCountMax(int i) {
/*  75 */     this.lockCountMax = (i > 0) ? i : 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getLockCountMax() {
/*  80 */     float stealth = getEntityStealth(this.targetEntity);
/*     */     
/*  82 */     return (int)(this.lockCountMax + this.lockCountMax * stealth);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getLockCount() {
/*  87 */     return this.lockCount;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isLockingEntity(Entity entity) {
/*  92 */     return (getLockCount() > 0 && this.targetEntity != null && !this.targetEntity.field_70128_L && 
/*  93 */       W_Entity.isEqual(entity, this.targetEntity));
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Entity getLockingEntity() {
/*  99 */     return (getLockCount() > 0 && this.targetEntity != null && !this.targetEntity.field_70128_L) ? this.targetEntity : null;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Entity getTargetEntity() {
/* 105 */     return this.targetEntity;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isLockComplete() {
/* 110 */     return (getLockCount() == getLockCountMax() && this.lastLockEntity != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public void update() {
/* 115 */     if (this.worldObj != null && this.worldObj.field_72995_K)
/*     */     {
/* 117 */       if (this.lockCount != this.prevLockCount) {
/*     */         
/* 119 */         this.prevLockCount = this.lockCount;
/*     */       }
/*     */       else {
/*     */         
/* 123 */         this.lockCount = this.prevLockCount = 0;
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isEntityOnGround(@Nullable Entity entity) {
/* 130 */     if (entity != null && !entity.field_70128_L) {
/*     */       
/* 132 */       if (entity.field_70122_E) {
/* 133 */         return true;
/*     */       }
/* 135 */       for (int i = 0; i < 12; i++) {
/*     */         
/* 137 */         int x = (int)(entity.field_70165_t + 0.5D);
/* 138 */         int y = (int)(entity.field_70163_u + 0.5D) - i;
/* 139 */         int z = (int)(entity.field_70161_v + 0.5D);
/* 140 */         int blockId = W_WorldFunc.getBlockId(entity.field_70170_p, x, y, z);
/*     */         
/* 142 */         if (blockId != 0 && !W_WorldFunc.isBlockWater(entity.field_70170_p, x, y, z)) {
/* 143 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/* 147 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean lock(Entity user) {
/* 152 */     return lock(user, true);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean lock(Entity user, boolean isLockContinue) {
/* 157 */     if (!this.worldObj.field_72995_K)
/*     */     {
/* 159 */       return false;
/*     */     }
/*     */     
/* 162 */     boolean result = false;
/*     */     
/* 164 */     if (this.lockCount == 0) {
/*     */ 
/*     */       
/* 167 */       List<Entity> list = this.worldObj.func_72839_b(user, user
/* 168 */           .func_174813_aQ().func_72314_b(this.lockRange, this.lockRange, this.lockRange));
/* 169 */       Entity tgtEnt = null;
/* 170 */       double dist = this.lockRange * this.lockRange * 2.0D;
/*     */       
/* 172 */       for (int i = 0; i < list.size(); i++) {
/*     */         
/* 174 */         Entity entity = list.get(i);
/*     */         
/* 176 */         if (canLockEntity(entity)) {
/*     */           
/* 178 */           double dx = entity.field_70165_t - user.field_70165_t;
/* 179 */           double dy = entity.field_70163_u - user.field_70163_u;
/* 180 */           double dz = entity.field_70161_v - user.field_70161_v;
/* 181 */           double d = dx * dx + dy * dy + dz * dz;
/* 182 */           Entity entityLocker = getLockEntity(user);
/* 183 */           float stealth = 1.0F - getEntityStealth(entity);
/* 184 */           double range = this.lockRange * stealth;
/* 185 */           float angle = this.lockAngle * (stealth / 2.0F + 0.5F);
/*     */           
/* 187 */           if (d < range * range && d < dist && 
/* 188 */             inLockRange(entityLocker, user.field_70177_z, user.field_70125_A, entity, angle)) {
/*     */ 
/*     */ 
/*     */             
/* 192 */             Vec3d v1 = W_WorldFunc.getWorldVec3(this.worldObj, entityLocker.field_70165_t, entityLocker.field_70163_u + entityLocker
/* 193 */                 .func_70047_e(), entityLocker.field_70161_v);
/* 194 */             Vec3d v2 = W_WorldFunc.getWorldVec3(this.worldObj, entity.field_70165_t, entity.field_70163_u + (entity.field_70131_O / 2.0F), entity.field_70161_v);
/*     */ 
/*     */             
/* 197 */             RayTraceResult m = W_WorldFunc.clip(this.worldObj, v1, v2, false, true, false);
/*     */             
/* 199 */             if (m == null || W_MovingObjectPosition.isHitTypeEntity(m)) {
/*     */               
/* 201 */               d = dist;
/* 202 */               tgtEnt = entity;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 208 */       this.targetEntity = tgtEnt;
/*     */       
/* 210 */       if (tgtEnt != null)
/*     */       {
/* 212 */         this.lockCount++;
/*     */       }
/*     */     }
/* 215 */     else if (this.targetEntity != null && !this.targetEntity.field_70128_L) {
/*     */       
/* 217 */       boolean canLock = true;
/*     */       
/* 219 */       if (!this.canLockInWater && this.targetEntity.func_70090_H()) {
/* 220 */         canLock = false;
/*     */       }
/* 222 */       boolean ong = isEntityOnGround(this.targetEntity);
/*     */       
/* 224 */       if (!this.canLockOnGround && ong) {
/* 225 */         canLock = false;
/*     */       }
/* 227 */       if (!this.canLockInAir && !ong)
/*     */       {
/* 229 */         canLock = false;
/*     */       }
/*     */       
/* 232 */       if (canLock) {
/*     */         
/* 234 */         double dx = this.targetEntity.field_70165_t - user.field_70165_t;
/* 235 */         double dy = this.targetEntity.field_70163_u - user.field_70163_u;
/* 236 */         double dz = this.targetEntity.field_70161_v - user.field_70161_v;
/* 237 */         float stealth = 1.0F - getEntityStealth(this.targetEntity);
/* 238 */         double range = this.lockRange * stealth;
/*     */         
/* 240 */         if (dx * dx + dy * dy + dz * dz < range * range) {
/*     */           
/* 242 */           if (this.worldObj.field_72995_K && this.lockSoundCount == 1)
/*     */           {
/* 244 */             MCH_PacketNotifyLock.send(getTargetEntity());
/*     */           }
/*     */           
/* 247 */           this.lockSoundCount = (this.lockSoundCount + 1) % 15;
/*     */           
/* 249 */           Entity entityLocker = getLockEntity(user);
/*     */           
/* 251 */           if (inLockRange(entityLocker, user.field_70177_z, user.field_70125_A, this.targetEntity, this.lockAngle)) {
/*     */ 
/*     */             
/* 254 */             if (this.lockCount < getLockCountMax())
/*     */             {
/* 256 */               this.lockCount++;
/*     */             }
/*     */           }
/* 259 */           else if (this.continueLockCount > 0) {
/*     */             
/* 261 */             this.continueLockCount--;
/*     */             
/* 263 */             if (this.continueLockCount <= 0 && this.lockCount > 0)
/*     */             {
/* 265 */               this.lockCount--;
/*     */             }
/*     */           }
/*     */           else {
/*     */             
/* 270 */             this.continueLockCount = 0;
/* 271 */             this.lockCount--;
/*     */           } 
/*     */           
/* 274 */           if (this.lockCount >= getLockCountMax()) {
/*     */             
/* 276 */             if (this.continueLockCount <= 0) {
/*     */               
/* 278 */               this.continueLockCount = getLockCountMax() / 3;
/*     */               
/* 280 */               if (this.continueLockCount > 20)
/*     */               {
/* 282 */                 this.continueLockCount = 20;
/*     */               }
/*     */             } 
/*     */             
/* 286 */             result = true;
/* 287 */             this.lastLockEntity = this.targetEntity;
/*     */             
/* 289 */             if (isLockContinue)
/*     */             {
/* 291 */               this.prevLockCount = this.lockCount - 1;
/*     */             }
/*     */             else
/*     */             {
/* 295 */               clearLock();
/*     */             }
/*     */           
/*     */           } 
/*     */         } else {
/*     */           
/* 301 */           clearLock();
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 306 */         clearLock();
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 311 */       clearLock();
/*     */     } 
/*     */     
/* 314 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public static float getEntityStealth(@Nullable Entity entity) {
/* 319 */     if (entity instanceof MCH_EntityAircraft)
/*     */     {
/* 321 */       return ((MCH_EntityAircraft)entity).getStealth();
/*     */     }
/*     */     
/* 324 */     if (entity != null && entity.func_184187_bx() instanceof MCH_EntityAircraft)
/*     */     {
/* 326 */       return ((MCH_EntityAircraft)entity.func_184187_bx()).getStealth();
/*     */     }
/*     */     
/* 329 */     return 0.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public void clearLock() {
/* 334 */     this.targetEntity = null;
/* 335 */     this.lockCount = 0;
/* 336 */     this.continueLockCount = 0;
/* 337 */     this.lockSoundCount = 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public Entity getLockEntity(Entity entity) {
/* 342 */     if (entity.func_184187_bx() instanceof MCH_EntityUavStation) {
/*     */       
/* 344 */       MCH_EntityUavStation us = (MCH_EntityUavStation)entity.func_184187_bx();
/*     */       
/* 346 */       if (us.getControlAircract() != null)
/*     */       {
/* 348 */         return (Entity)us.getControlAircract();
/*     */       }
/*     */     } 
/*     */     
/* 352 */     return entity;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canLockEntity(Entity entity) {
/* 357 */     if (this.ridableOnly && entity instanceof net.minecraft.entity.player.EntityPlayer)
/*     */     {
/* 359 */       if (entity.func_184187_bx() == null)
/*     */       {
/* 361 */         return false;
/*     */       }
/*     */     }
/*     */     
/* 365 */     String className = entity.getClass().getName();
/*     */     
/* 367 */     if (className.indexOf("EntityCamera") >= 0)
/*     */     {
/* 369 */       return false;
/*     */     }
/*     */     
/* 372 */     if (!W_Lib.isEntityLivingBase(entity) && !(entity instanceof MCH_EntityAircraft))
/*     */     {
/* 374 */       return false;
/*     */     }
/*     */     
/* 377 */     if (!this.canLockInWater && entity.func_70090_H()) {
/* 378 */       return false;
/*     */     }
/* 380 */     if (this.checker != null && !this.checker.canLockEntity(entity)) {
/* 381 */       return false;
/*     */     }
/* 383 */     boolean ong = isEntityOnGround(entity);
/*     */     
/* 385 */     if (!this.canLockOnGround && ong) {
/* 386 */       return false;
/*     */     }
/* 388 */     if (!this.canLockInAir && !ong) {
/* 389 */       return false;
/*     */     }
/* 391 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean inLockRange(Entity entity, float rotationYaw, float rotationPitch, Entity target, float lockAng) {
/* 397 */     double dx = target.field_70165_t - entity.field_70165_t;
/* 398 */     double dy = target.field_70163_u + (target.field_70131_O / 2.0F) - entity.field_70163_u;
/* 399 */     double dz = target.field_70161_v - entity.field_70161_v;
/* 400 */     float entityYaw = (float)MCH_Lib.getRotate360(rotationYaw);
/* 401 */     float targetYaw = (float)MCH_Lib.getRotate360(Math.atan2(dz, dx) * 180.0D / Math.PI);
/* 402 */     float diffYaw = (float)MCH_Lib.getRotate360((targetYaw - entityYaw - 90.0F));
/* 403 */     double dxz = Math.sqrt(dx * dx + dz * dz);
/* 404 */     float targetPitch = -((float)(Math.atan2(dy, dxz) * 180.0D / Math.PI));
/* 405 */     float diffPitch = targetPitch - rotationPitch;
/*     */     
/* 407 */     return ((diffYaw < lockAng || diffYaw > 360.0F - lockAng) && Math.abs(diffPitch) < lockAng);
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\weapon\MCH_WeaponGuidanceSystem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */