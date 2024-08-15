/*     */ package mcheli.weapon;
/*     */ 
/*     */ import mcheli.wrapper.W_WorldFunc;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.Entity;
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
/*     */ public class MCH_EntityASMissile
/*     */   extends MCH_EntityBaseBullet
/*     */ {
/*     */   public double targetPosX;
/*     */   public double targetPosY;
/*     */   public double targetPosZ;
/*     */   
/*     */   public MCH_EntityASMissile(World par1World) {
/*  24 */     super(par1World);
/*  25 */     this.targetPosX = 0.0D;
/*  26 */     this.targetPosY = 0.0D;
/*  27 */     this.targetPosZ = 0.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public MCH_EntityASMissile(World par1World, double posX, double posY, double posZ, double targetX, double targetY, double targetZ, float yaw, float pitch, double acceleration) {
/*  33 */     super(par1World, posX, posY, posZ, targetX, targetY, targetZ, yaw, pitch, acceleration);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float getGravity() {
/*  39 */     if (getBomblet() == 1)
/*     */     {
/*  41 */       return -0.03F;
/*     */     }
/*  43 */     return super.getGravity();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float getGravityInWater() {
/*  49 */     if (getBomblet() == 1)
/*     */     {
/*  51 */       return -0.03F;
/*     */     }
/*  53 */     return super.getGravityInWater();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_70071_h_() {
/*  59 */     super.func_70071_h_();
/*     */     
/*  61 */     if (getInfo() != null && !(getInfo()).disableSmoke && getBomblet() == 0)
/*     */     {
/*  63 */       spawnParticle((getInfo()).trajectoryParticleName, 3, 10.0F * (getInfo()).smokeSize * 0.5F);
/*     */     }
/*     */     
/*  66 */     if (getInfo() != null && !this.field_70170_p.field_72995_K && this.isBomblet != 1) {
/*     */       
/*  68 */       Block block = W_WorldFunc.getBlock(this.field_70170_p, (int)this.targetPosX, (int)this.targetPosY, (int)this.targetPosZ);
/*     */ 
/*     */       
/*  71 */       if (block != null && block.func_149703_v()) {
/*     */         
/*  73 */         double dist = func_70011_f(this.targetPosX, this.targetPosY, this.targetPosZ);
/*     */         
/*  75 */         if (dist < (getInfo()).proximityFuseDist) {
/*     */           
/*  77 */           if ((getInfo()).bomblet > 0) {
/*     */             
/*  79 */             for (int i = 0; i < (getInfo()).bomblet; i++)
/*     */             {
/*  81 */               sprinkleBomblet();
/*     */             
/*     */             }
/*     */           }
/*     */           else {
/*     */             
/*  87 */             RayTraceResult mop = new RayTraceResult((Entity)this);
/*     */             
/*  89 */             onImpact(mop, 1.0F);
/*     */           } 
/*     */           
/*  92 */           func_70106_y();
/*     */         }
/*  94 */         else if (getGravity() == 0.0D) {
/*     */           
/*  96 */           double up = 0.0D;
/*     */           
/*  98 */           if (getCountOnUpdate() < 10) {
/*  99 */             up = 20.0D;
/*     */           }
/* 101 */           double x = this.targetPosX - this.field_70165_t;
/* 102 */           double y = this.targetPosY + up - this.field_70163_u;
/* 103 */           double z = this.targetPosZ - this.field_70161_v;
/* 104 */           double d = MathHelper.func_76133_a(x * x + y * y + z * z);
/*     */           
/* 106 */           this.field_70159_w = x * this.acceleration / d;
/* 107 */           this.field_70181_x = y * this.acceleration / d;
/* 108 */           this.field_70179_y = z * this.acceleration / d;
/*     */         }
/*     */         else {
/*     */           
/* 112 */           double x = this.targetPosX - this.field_70165_t;
/* 113 */           double y = this.targetPosY - this.field_70163_u;
/* 114 */           y *= 0.3D;
/* 115 */           double z = this.targetPosZ - this.field_70161_v;
/* 116 */           double d = MathHelper.func_76133_a(x * x + y * y + z * z);
/*     */           
/* 118 */           this.field_70159_w = x * this.acceleration / d;
/* 119 */           this.field_70179_y = z * this.acceleration / d;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 124 */     double a = (float)Math.atan2(this.field_70179_y, this.field_70159_w);
/* 125 */     this.field_70177_z = (float)(a * 180.0D / Math.PI) - 90.0F;
/*     */     
/* 127 */     double r = Math.sqrt(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
/* 128 */     this.field_70125_A = -((float)(Math.atan2(this.field_70181_x, r) * 180.0D / Math.PI));
/*     */     
/* 130 */     onUpdateBomblet();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void sprinkleBomblet() {
/* 136 */     if (!this.field_70170_p.field_72995_K) {
/*     */ 
/*     */       
/* 139 */       MCH_EntityASMissile e = new MCH_EntityASMissile(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v, this.field_70159_w, this.field_70181_x, this.field_70179_y, this.field_70146_Z.nextInt(360), 0.0F, this.acceleration);
/*     */       
/* 141 */       e.setParameterFromWeapon(this, this.shootingAircraft, this.shootingEntity);
/* 142 */       e.setName(func_70005_c_());
/*     */ 
/*     */       
/* 145 */       float RANDOM = (getInfo()).bombletDiff;
/*     */       
/* 147 */       e.field_70159_w = this.field_70159_w * 0.5D + ((this.field_70146_Z.nextFloat() - 0.5F) * RANDOM);
/* 148 */       e.field_70181_x = this.field_70181_x * 0.5D / 2.0D + ((this.field_70146_Z.nextFloat() - 0.5F) * RANDOM / 2.0F);
/* 149 */       e.field_70179_y = this.field_70179_y * 0.5D + ((this.field_70146_Z.nextFloat() - 0.5F) * RANDOM);
/*     */       
/* 151 */       e.setBomblet();
/* 152 */       this.field_70170_p.func_72838_d((Entity)e);
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
/* 165 */     return MCH_DefaultBulletModels.ASMissile;
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\weapon\MCH_EntityASMissile.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */