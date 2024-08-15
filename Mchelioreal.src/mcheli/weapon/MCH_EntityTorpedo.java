/*     */ package mcheli.weapon;
/*     */ 
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MCH_EntityTorpedo
/*     */   extends MCH_EntityBaseBullet
/*     */ {
/*     */   public double targetPosX;
/*     */   public double targetPosY;
/*     */   public double targetPosZ;
/*  17 */   public double accelerationInWater = 2.0D;
/*     */ 
/*     */   
/*     */   public MCH_EntityTorpedo(World par1World) {
/*  21 */     super(par1World);
/*  22 */     this.targetPosX = 0.0D;
/*  23 */     this.targetPosY = 0.0D;
/*  24 */     this.targetPosZ = 0.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public MCH_EntityTorpedo(World par1World, double posX, double posY, double posZ, double targetX, double targetY, double targetZ, float yaw, float pitch, double acceleration) {
/*  30 */     super(par1World, posX, posY, posZ, targetX, targetY, targetZ, yaw, pitch, acceleration);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_70071_h_() {
/*  36 */     super.func_70071_h_();
/*     */     
/*  38 */     if (getInfo() != null && (getInfo()).isGuidedTorpedo) {
/*     */       
/*  40 */       onUpdateGuided();
/*     */     }
/*     */     else {
/*     */       
/*  44 */       onUpdateNoGuided();
/*     */     } 
/*     */     
/*  47 */     if (func_70090_H())
/*     */     {
/*  49 */       if (getInfo() != null && !(getInfo()).disableSmoke)
/*     */       {
/*  51 */         spawnParticle((getInfo()).trajectoryParticleName, 3, 5.0F * (getInfo()).smokeSize * 0.5F);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void onUpdateNoGuided() {
/*  58 */     if (!this.field_70170_p.field_72995_K)
/*     */     {
/*  60 */       if (func_70090_H()) {
/*     */         
/*  62 */         this.field_70181_x *= 0.800000011920929D;
/*     */         
/*  64 */         if (this.acceleration < this.accelerationInWater) {
/*     */           
/*  66 */           this.acceleration += 0.1D;
/*     */         }
/*  68 */         else if (this.acceleration > this.accelerationInWater + 0.20000000298023224D) {
/*     */           
/*  70 */           this.acceleration -= 0.1D;
/*     */         } 
/*     */         
/*  73 */         double x = this.field_70159_w;
/*  74 */         double y = this.field_70181_x;
/*  75 */         double z = this.field_70179_y;
/*  76 */         double d = MathHelper.func_76133_a(x * x + y * y + z * z);
/*     */         
/*  78 */         this.field_70159_w = x * this.acceleration / d;
/*  79 */         this.field_70181_x = y * this.acceleration / d;
/*  80 */         this.field_70179_y = z * this.acceleration / d;
/*     */       } 
/*     */     }
/*     */     
/*  84 */     if (func_70090_H()) {
/*     */       
/*  86 */       double a = (float)Math.atan2(this.field_70179_y, this.field_70159_w);
/*     */       
/*  88 */       this.field_70177_z = (float)(a * 180.0D / Math.PI) - 90.0F;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void onUpdateGuided() {
/*  94 */     if (!this.field_70170_p.field_72995_K)
/*     */     {
/*  96 */       if (func_70090_H()) {
/*     */         
/*  98 */         if (this.acceleration < this.accelerationInWater) {
/*     */           
/* 100 */           this.acceleration += 0.1D;
/*     */         }
/* 102 */         else if (this.acceleration > this.accelerationInWater + 0.20000000298023224D) {
/*     */           
/* 104 */           this.acceleration -= 0.1D;
/*     */         } 
/*     */         
/* 107 */         double x = this.targetPosX - this.field_70165_t;
/* 108 */         double y = this.targetPosY - this.field_70163_u;
/* 109 */         double z = this.targetPosZ - this.field_70161_v;
/* 110 */         double d = MathHelper.func_76133_a(x * x + y * y + z * z);
/*     */         
/* 112 */         this.field_70159_w = x * this.acceleration / d;
/* 113 */         this.field_70181_x = y * this.acceleration / d;
/* 114 */         this.field_70179_y = z * this.acceleration / d;
/*     */       } 
/*     */     }
/*     */     
/* 118 */     if (func_70090_H()) {
/*     */       
/* 120 */       double a = (float)Math.atan2(this.field_70179_y, this.field_70159_w);
/* 121 */       this.field_70177_z = (float)(a * 180.0D / Math.PI) - 90.0F;
/*     */       
/* 123 */       double r = Math.sqrt(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
/* 124 */       this.field_70125_A = -((float)(Math.atan2(this.field_70181_x, r) * 180.0D / Math.PI));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MCH_BulletModel getDefaultBulletModel() {
/* 137 */     return MCH_DefaultBulletModels.Torpedo;
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\weapon\MCH_EntityTorpedo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */