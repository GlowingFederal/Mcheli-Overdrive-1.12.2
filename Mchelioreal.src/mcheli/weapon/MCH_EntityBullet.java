/*     */ package mcheli.weapon;
/*     */ 
/*     */ import java.util.List;
/*     */ import mcheli.MCH_Config;
/*     */ import mcheli.MCH_Lib;
/*     */ import mcheli.wrapper.W_MovingObjectPosition;
/*     */ import mcheli.wrapper.W_WorldFunc;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.MathHelper;
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
/*     */ public class MCH_EntityBullet
/*     */   extends MCH_EntityBaseBullet
/*     */ {
/*     */   public MCH_EntityBullet(World par1World) {
/*  28 */     super(par1World);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public MCH_EntityBullet(World par1World, double pX, double pY, double pZ, double targetX, double targetY, double targetZ, float yaw, float pitch, double acceleration) {
/*  34 */     super(par1World, pX, pY, pZ, targetX, targetY, targetZ, yaw, pitch, acceleration);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_70071_h_() {
/*  40 */     super.func_70071_h_();
/*     */     
/*  42 */     if (!this.field_70128_L && !this.field_70170_p.field_72995_K && getCountOnUpdate() > 1 && getInfo() != null && this.explosionPower > 0) {
/*     */ 
/*     */       
/*  45 */       float pDist = (getInfo()).proximityFuseDist;
/*  46 */       if (pDist > 0.1D) {
/*     */         
/*  48 */         pDist++;
/*     */         
/*  50 */         float rng = pDist + MathHelper.func_76135_e((getInfo()).acceleration);
/*     */         
/*  52 */         List<Entity> list = this.field_70170_p.func_72839_b((Entity)this, 
/*  53 */             func_174813_aQ().func_72314_b(rng, rng, rng));
/*     */         
/*  55 */         for (int i = 0; i < list.size(); i++) {
/*     */           
/*  57 */           Entity entity1 = list.get(i);
/*     */           
/*  59 */           if (canBeCollidedEntity(entity1) && entity1.func_70068_e((Entity)this) < (pDist * pDist)) {
/*     */             
/*  61 */             MCH_Lib.DbgLog(this.field_70170_p, "MCH_EntityBullet.onUpdate:proximityFuse:" + entity1, new Object[0]);
/*     */             
/*  63 */             this.field_70165_t = (entity1.field_70165_t + this.field_70165_t) / 2.0D;
/*  64 */             this.field_70163_u = (entity1.field_70163_u + this.field_70163_u) / 2.0D;
/*  65 */             this.field_70161_v = (entity1.field_70161_v + this.field_70161_v) / 2.0D;
/*     */ 
/*     */             
/*  68 */             RayTraceResult mop = W_MovingObjectPosition.newMOP((int)this.field_70165_t, (int)this.field_70163_u, (int)this.field_70161_v, 0, 
/*  69 */                 W_WorldFunc.getWorldVec3EntityPos((Entity)this), false);
/*     */             
/*  71 */             onImpact(mop, 1.0F);
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onUpdateCollided() {
/*  82 */     double mx = this.field_70159_w * this.accelerationFactor;
/*  83 */     double my = this.field_70181_x * this.accelerationFactor;
/*  84 */     double mz = this.field_70179_y * this.accelerationFactor;
/*  85 */     float damageFactor = 1.0F;
/*     */     
/*  87 */     RayTraceResult m = null;
/*     */     
/*  89 */     for (int i = 0; i < 5; i++) {
/*     */       
/*  91 */       Vec3d vec3d1 = W_WorldFunc.getWorldVec3(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v);
/*  92 */       Vec3d vec3d2 = W_WorldFunc.getWorldVec3(this.field_70170_p, this.field_70165_t + mx, this.field_70163_u + my, this.field_70161_v + mz);
/*     */       
/*  94 */       m = W_WorldFunc.clip(this.field_70170_p, vec3d1, vec3d2);
/*     */       
/*  96 */       boolean continueClip = false;
/*     */       
/*  98 */       if (this.shootingEntity != null && W_MovingObjectPosition.isHitTypeTile(m)) {
/*     */ 
/*     */         
/* 101 */         Block block = W_WorldFunc.getBlock(this.field_70170_p, m.func_178782_a());
/*     */         
/* 103 */         if (MCH_Config.bulletBreakableBlocks.contains(block)) {
/*     */ 
/*     */           
/* 106 */           W_WorldFunc.destroyBlock(this.field_70170_p, m.func_178782_a(), true);
/*     */           
/* 108 */           continueClip = true;
/*     */         } 
/*     */       } 
/*     */       
/* 112 */       if (!continueClip) {
/*     */         break;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 118 */     Vec3d vec3 = W_WorldFunc.getWorldVec3(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v);
/* 119 */     Vec3d vec31 = W_WorldFunc.getWorldVec3(this.field_70170_p, this.field_70165_t + mx, this.field_70163_u + my, this.field_70161_v + mz);
/*     */     
/* 121 */     if ((getInfo()).delayFuse > 0) {
/*     */       
/* 123 */       if (m != null) {
/*     */         
/* 125 */         boundBullet(m.field_178784_b);
/* 126 */         if (this.delayFuse == 0)
/*     */         {
/* 128 */           this.delayFuse = (getInfo()).delayFuse;
/*     */         }
/*     */       } 
/*     */       
/*     */       return;
/*     */     } 
/* 134 */     if (m != null)
/*     */     {
/* 136 */       vec31 = W_WorldFunc.getWorldVec3(this.field_70170_p, m.field_72307_f.field_72450_a, m.field_72307_f.field_72448_b, m.field_72307_f.field_72449_c);
/*     */     }
/*     */     
/* 139 */     Entity entity = null;
/*     */     
/* 141 */     List<Entity> list = this.field_70170_p.func_72839_b((Entity)this, 
/* 142 */         func_174813_aQ().func_72321_a(mx, my, mz).func_72314_b(21.0D, 21.0D, 21.0D));
/* 143 */     double d0 = 0.0D;
/*     */     
/* 145 */     for (int j = 0; j < list.size(); j++) {
/*     */       
/* 147 */       Entity entity1 = list.get(j);
/*     */       
/* 149 */       if (canBeCollidedEntity(entity1)) {
/*     */         
/* 151 */         float f = 0.3F;
/*     */ 
/*     */         
/* 154 */         AxisAlignedBB axisalignedbb = entity1.func_174813_aQ().func_72314_b(f, f, f);
/* 155 */         RayTraceResult m1 = axisalignedbb.func_72327_a(vec3, vec31);
/*     */         
/* 157 */         if (m1 != null) {
/*     */           
/* 159 */           double d1 = vec3.func_72438_d(m1.field_72307_f);
/*     */           
/* 161 */           if (d1 < d0 || d0 == 0.0D) {
/*     */             
/* 163 */             entity = entity1;
/* 164 */             d0 = d1;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 170 */     if (entity != null)
/*     */     {
/*     */       
/* 173 */       m = new RayTraceResult(entity);
/*     */     }
/*     */     
/* 176 */     if (m != null)
/*     */     {
/* 178 */       onImpact(m, damageFactor);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public MCH_BulletModel getDefaultBulletModel() {
/* 185 */     return MCH_DefaultBulletModels.Bullet;
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\weapon\MCH_EntityBullet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */