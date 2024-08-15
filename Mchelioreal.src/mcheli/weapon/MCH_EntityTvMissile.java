/*    */ package mcheli.weapon;
/*    */ 
/*    */ import mcheli.aircraft.MCH_EntityAircraft;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MCH_EntityTvMissile
/*    */   extends MCH_EntityBaseBullet
/*    */ {
/*    */   public boolean isSpawnParticle = true;
/*    */   
/*    */   public MCH_EntityTvMissile(World par1World) {
/* 20 */     super(par1World);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public MCH_EntityTvMissile(World par1World, double posX, double posY, double posZ, double targetX, double targetY, double targetZ, float yaw, float pitch, double acceleration) {
/* 26 */     super(par1World, posX, posY, posZ, targetX, targetY, targetZ, yaw, pitch, acceleration);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void func_70071_h_() {
/* 32 */     super.func_70071_h_();
/*    */     
/* 34 */     if (this.isSpawnParticle)
/*    */     {
/* 36 */       if (getInfo() != null && !(getInfo()).disableSmoke)
/*    */       {
/* 38 */         spawnParticle((getInfo()).trajectoryParticleName, 3, 5.0F * (getInfo()).smokeSize * 0.5F);
/*    */       }
/*    */     }
/*    */     
/* 42 */     if (this.shootingEntity != null) {
/*    */       
/* 44 */       double x = this.field_70165_t - this.shootingEntity.field_70165_t;
/* 45 */       double y = this.field_70163_u - this.shootingEntity.field_70163_u;
/* 46 */       double z = this.field_70161_v - this.shootingEntity.field_70161_v;
/*    */       
/* 48 */       if (x * x + y * y + z * z > 1440000.0D)
/*    */       {
/* 50 */         func_70106_y();
/*    */       }
/*    */       
/* 53 */       if (!this.field_70170_p.field_72995_K && !this.field_70128_L)
/*    */       {
/* 55 */         onUpdateMotion();
/*    */       }
/*    */     }
/* 58 */     else if (!this.field_70170_p.field_72995_K) {
/*    */       
/* 60 */       func_70106_y();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void onUpdateMotion() {
/* 66 */     Entity e = this.shootingEntity;
/*    */     
/* 68 */     if (e != null && !e.field_70128_L) {
/*    */       
/* 70 */       MCH_EntityAircraft ac = MCH_EntityAircraft.getAircraft_RiddenOrControl(e);
/*    */       
/* 72 */       if (ac != null && ac.getTVMissile() == this) {
/*    */         
/* 74 */         float yaw = e.field_70177_z;
/* 75 */         float pitch = e.field_70125_A;
/* 76 */         double tX = (-MathHelper.func_76126_a(yaw / 180.0F * 3.1415927F) * MathHelper.func_76134_b(pitch / 180.0F * 3.1415927F));
/* 77 */         double tZ = (MathHelper.func_76134_b(yaw / 180.0F * 3.1415927F) * MathHelper.func_76134_b(pitch / 180.0F * 3.1415927F));
/* 78 */         double tY = -MathHelper.func_76126_a(pitch / 180.0F * 3.1415927F);
/*    */         
/* 80 */         setMotion(tX, tY, tZ);
/* 81 */         func_70101_b(yaw, pitch);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public MCH_BulletModel getDefaultBulletModel() {
/* 89 */     return MCH_DefaultBulletModels.ATMissile;
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\weapon\MCH_EntityTvMissile.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */