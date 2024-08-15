/*     */ package mcheli.weapon;
/*     */ 
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MCH_EntityATMissile
/*     */   extends MCH_EntityBaseBullet
/*     */ {
/*     */   public int guidanceType;
/*     */   
/*     */   public MCH_EntityATMissile(World par1World) {
/*  19 */     super(par1World);
/*  20 */     this.guidanceType = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public MCH_EntityATMissile(World par1World, double posX, double posY, double posZ, double targetX, double targetY, double targetZ, float yaw, float pitch, double acceleration) {
/*  26 */     super(par1World, posX, posY, posZ, targetX, targetY, targetZ, yaw, pitch, acceleration);
/*  27 */     this.guidanceType = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_70071_h_() {
/*  33 */     super.func_70071_h_();
/*     */     
/*  35 */     if (getInfo() != null && !(getInfo()).disableSmoke && this.field_70173_aa >= (getInfo()).trajectoryParticleStartTick)
/*     */     {
/*  37 */       spawnParticle((getInfo()).trajectoryParticleName, 3, 5.0F * (getInfo()).smokeSize * 0.5F);
/*     */     }
/*     */     
/*  40 */     if (!this.field_70170_p.field_72995_K)
/*     */     {
/*  42 */       if (this.shootingEntity != null && this.targetEntity != null && !this.targetEntity.field_70128_L) {
/*     */         
/*  44 */         if (usingFlareOfTarget(this.targetEntity)) {
/*     */           
/*  46 */           func_70106_y();
/*     */           
/*     */           return;
/*     */         } 
/*  50 */         onUpdateMotion();
/*     */       }
/*     */       else {
/*     */         
/*  54 */         func_70106_y();
/*     */       } 
/*     */     }
/*     */     
/*  58 */     double a = (float)Math.atan2(this.field_70179_y, this.field_70159_w);
/*  59 */     this.field_70177_z = (float)(a * 180.0D / Math.PI) - 90.0F;
/*     */     
/*  61 */     double r = Math.sqrt(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
/*  62 */     this.field_70125_A = -((float)(Math.atan2(this.field_70181_x, r) * 180.0D / Math.PI));
/*     */   }
/*     */ 
/*     */   
/*     */   public void onUpdateMotion() {
/*  67 */     double x = this.targetEntity.field_70165_t - this.field_70165_t;
/*  68 */     double y = this.targetEntity.field_70163_u - this.field_70163_u;
/*  69 */     double z = this.targetEntity.field_70161_v - this.field_70161_v;
/*  70 */     double d = x * x + y * y + z * z;
/*     */     
/*  72 */     if (d > 2250000.0D || this.targetEntity.field_70128_L) {
/*     */       
/*  74 */       func_70106_y();
/*     */     }
/*  76 */     else if ((getInfo()).proximityFuseDist >= 0.1F && d < (getInfo()).proximityFuseDist) {
/*     */ 
/*     */       
/*  79 */       RayTraceResult mop = new RayTraceResult(this.targetEntity);
/*     */       
/*  81 */       mop.field_72308_g = null;
/*     */       
/*  83 */       onImpact(mop, 1.0F);
/*     */     }
/*     */     else {
/*     */       
/*  87 */       int rigidityTime = (getInfo()).rigidityTime;
/*  88 */       float af = (getCountOnUpdate() < rigidityTime + (getInfo()).trajectoryParticleStartTick) ? 0.5F : 1.0F;
/*     */       
/*  90 */       if (getCountOnUpdate() > rigidityTime)
/*     */       {
/*  92 */         if (this.guidanceType == 1) {
/*     */           
/*  94 */           if (getCountOnUpdate() <= rigidityTime + 20)
/*     */           {
/*  96 */             guidanceToTarget(this.targetEntity.field_70165_t, this.shootingEntity.field_70163_u + 150.0D, this.targetEntity.field_70161_v, af);
/*     */           
/*     */           }
/*  99 */           else if (getCountOnUpdate() <= rigidityTime + 30)
/*     */           {
/* 101 */             guidanceToTarget(this.targetEntity.field_70165_t, this.shootingEntity.field_70163_u, this.targetEntity.field_70161_v, af);
/*     */           }
/*     */           else
/*     */           {
/* 105 */             if (getCountOnUpdate() == rigidityTime + 35) {
/*     */               
/* 107 */               setPower((int)(getPower() * 1.2F));
/* 108 */               if (this.explosionPower > 0)
/* 109 */                 this.explosionPower++; 
/*     */             } 
/* 111 */             guidanceToTarget(this.targetEntity.field_70165_t, this.targetEntity.field_70163_u, this.targetEntity.field_70161_v, af);
/*     */           }
/*     */         
/*     */         } else {
/*     */           
/* 116 */           d = MathHelper.func_76133_a(d);
/*     */           
/* 118 */           this.field_70159_w = x * this.acceleration / d * af;
/* 119 */           this.field_70181_x = y * this.acceleration / d * af;
/* 120 */           this.field_70179_y = z * this.acceleration / d * af;
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public MCH_BulletModel getDefaultBulletModel() {
/* 129 */     return MCH_DefaultBulletModels.ATMissile;
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\weapon\MCH_EntityATMissile.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */