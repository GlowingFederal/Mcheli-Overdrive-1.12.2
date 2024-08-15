/*    */ package mcheli.weapon;
/*    */ 
/*    */ import net.minecraft.util.math.RayTraceResult;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MCH_EntityAAMissile
/*    */   extends MCH_EntityBaseBullet
/*    */ {
/*    */   public MCH_EntityAAMissile(World par1World) {
/* 17 */     super(par1World);
/* 18 */     this.targetEntity = null;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public MCH_EntityAAMissile(World par1World, double posX, double posY, double posZ, double targetX, double targetY, double targetZ, float yaw, float pitch, double acceleration) {
/* 24 */     super(par1World, posX, posY, posZ, targetX, targetY, targetZ, yaw, pitch, acceleration);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void func_70071_h_() {
/* 30 */     super.func_70071_h_();
/*    */     
/* 32 */     if (getCountOnUpdate() > 4)
/*    */     {
/* 34 */       if (getInfo() != null && !(getInfo()).disableSmoke)
/*    */       {
/* 36 */         spawnParticle((getInfo()).trajectoryParticleName, 3, 7.0F * (getInfo()).smokeSize * 0.5F);
/*    */       }
/*    */     }
/*    */     
/* 40 */     if (!this.field_70170_p.field_72995_K && getInfo() != null)
/*    */     {
/* 42 */       if (this.shootingEntity != null && this.targetEntity != null && !this.targetEntity.field_70128_L) {
/*    */         
/* 44 */         double x = this.field_70165_t - this.targetEntity.field_70165_t;
/* 45 */         double y = this.field_70163_u - this.targetEntity.field_70163_u;
/* 46 */         double z = this.field_70161_v - this.targetEntity.field_70161_v;
/* 47 */         double d = x * x + y * y + z * z;
/*    */         
/* 49 */         if (d > 3422500.0D) {
/*    */           
/* 51 */           func_70106_y();
/*    */         }
/* 53 */         else if (getCountOnUpdate() > (getInfo()).rigidityTime) {
/*    */           
/* 55 */           if (usingFlareOfTarget(this.targetEntity)) {
/*    */             
/* 57 */             func_70106_y();
/*    */             
/*    */             return;
/*    */           } 
/* 61 */           if ((getInfo()).proximityFuseDist >= 0.1F && d < (getInfo()).proximityFuseDist)
/*    */           {
/*    */             
/* 64 */             RayTraceResult mop = new RayTraceResult(this.targetEntity);
/*    */             
/* 66 */             this.field_70165_t = (this.targetEntity.field_70165_t + this.field_70165_t) / 2.0D;
/* 67 */             this.field_70163_u = (this.targetEntity.field_70163_u + this.field_70163_u) / 2.0D;
/* 68 */             this.field_70161_v = (this.targetEntity.field_70161_v + this.field_70161_v) / 2.0D;
/*    */             
/* 70 */             onImpact(mop, 1.0F);
/*    */           }
/*    */           else
/*    */           {
/* 74 */             guidanceToTarget(this.targetEntity.field_70165_t, this.targetEntity.field_70163_u, this.targetEntity.field_70161_v);
/*    */           }
/*    */         
/*    */         } 
/*    */       } else {
/*    */         
/* 80 */         func_70106_y();
/*    */       } 
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public MCH_BulletModel getDefaultBulletModel() {
/* 88 */     return MCH_DefaultBulletModels.AAMissile;
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\weapon\MCH_EntityAAMissile.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */