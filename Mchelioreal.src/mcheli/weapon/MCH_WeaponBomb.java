/*    */ package mcheli.weapon;
/*    */ 
/*    */ import mcheli.MCH_Explosion;
/*    */ import mcheli.aircraft.MCH_EntityAircraft;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.math.Vec3d;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MCH_WeaponBomb
/*    */   extends MCH_WeaponBase
/*    */ {
/*    */   public MCH_WeaponBomb(World w, Vec3d v, float yaw, float pitch, String nm, MCH_WeaponInfo wi) {
/* 19 */     super(w, v, yaw, pitch, nm, wi);
/* 20 */     this.acceleration = 0.5F;
/* 21 */     this.explosionPower = 9;
/* 22 */     this.power = 35;
/* 23 */     this.interval = -90;
/* 24 */     if (w.field_72995_K) {
/* 25 */       this.interval -= 10;
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean shot(MCH_WeaponParam prm) {
/* 31 */     if (getInfo() != null && (getInfo()).destruct) {
/*    */       
/* 33 */       if (prm.entity instanceof mcheli.helicopter.MCH_EntityHeli) {
/*    */         
/* 35 */         MCH_EntityAircraft ac = (MCH_EntityAircraft)prm.entity;
/*    */         
/* 37 */         if (ac.isUAV() && ac.getSeatNum() == 0)
/*    */         {
/* 39 */           if (!this.worldObj.field_72995_K) {
/*    */             
/* 41 */             MCH_Explosion.newExplosion(this.worldObj, null, prm.user, ac.field_70165_t, ac.field_70163_u, ac.field_70161_v, 
/* 42 */                 (getInfo()).explosion, (getInfo()).explosionBlock, true, true, (getInfo()).flaming, true, 0);
/*    */             
/* 44 */             playSound(prm.entity);
/*    */           } 
/*    */           
/* 47 */           ac.destruct();
/*    */         }
/*    */       
/*    */       } 
/* 51 */     } else if (!this.worldObj.field_72995_K) {
/*    */       
/* 53 */       playSound(prm.entity);
/*    */       
/* 55 */       MCH_EntityBomb e = new MCH_EntityBomb(this.worldObj, prm.posX, prm.posY, prm.posZ, prm.entity.field_70159_w, prm.entity.field_70181_x, prm.entity.field_70179_y, prm.entity.field_70177_z, 0.0F, this.acceleration);
/*    */ 
/*    */       
/* 58 */       e.setName(this.name);
/* 59 */       e.setParameterFromWeapon(this, prm.entity, prm.user);
/*    */       
/* 61 */       e.field_70159_w = prm.entity.field_70159_w;
/* 62 */       e.field_70181_x = prm.entity.field_70181_x;
/* 63 */       e.field_70179_y = prm.entity.field_70179_y;
/*    */       
/* 65 */       this.worldObj.func_72838_d((Entity)e);
/*    */     } 
/*    */     
/* 68 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\weapon\MCH_WeaponBomb.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */