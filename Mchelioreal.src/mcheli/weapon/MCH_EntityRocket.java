/*    */ package mcheli.weapon;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MCH_EntityRocket
/*    */   extends MCH_EntityBaseBullet
/*    */ {
/*    */   public MCH_EntityRocket(World par1World) {
/* 15 */     super(par1World);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public MCH_EntityRocket(World par1World, double posX, double posY, double posZ, double targetX, double targetY, double targetZ, float yaw, float pitch, double acceleration) {
/* 21 */     super(par1World, posX, posY, posZ, targetX, targetY, targetZ, yaw, pitch, acceleration);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void func_70071_h_() {
/* 27 */     super.func_70071_h_();
/*    */     
/* 29 */     onUpdateBomblet();
/*    */     
/* 31 */     if (this.isBomblet <= 0)
/*    */     {
/* 33 */       if (getInfo() != null && !(getInfo()).disableSmoke)
/*    */       {
/* 35 */         spawnParticle((getInfo()).trajectoryParticleName, 3, 5.0F * (getInfo()).smokeSize * 0.5F);
/*    */       }
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void sprinkleBomblet() {
/* 43 */     if (!this.field_70170_p.field_72995_K) {
/*    */       
/* 45 */       MCH_EntityRocket e = new MCH_EntityRocket(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v, this.field_70159_w, this.field_70181_x, this.field_70179_y, this.field_70177_z, this.field_70125_A, this.acceleration);
/*    */ 
/*    */       
/* 48 */       e.setName(func_70005_c_());
/* 49 */       e.setParameterFromWeapon(this, this.shootingAircraft, this.shootingEntity);
/*    */       
/* 51 */       float MOTION = (getInfo()).bombletDiff;
/*    */ 
/*    */       
/* 54 */       e.field_70159_w += (this.field_70146_Z.nextFloat() - 0.5D) * MOTION;
/* 55 */       e.field_70181_x += (this.field_70146_Z.nextFloat() - 0.5D) * MOTION;
/* 56 */       e.field_70179_y += (this.field_70146_Z.nextFloat() - 0.5D) * MOTION;
/* 57 */       e.setBomblet();
/*    */       
/* 59 */       this.field_70170_p.func_72838_d((Entity)e);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public MCH_BulletModel getDefaultBulletModel() {
/* 66 */     return MCH_DefaultBulletModels.Rocket;
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\weapon\MCH_EntityRocket.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */