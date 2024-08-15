/*    */ package mcheli.weapon;
/*    */ 
/*    */ import mcheli.MCH_Lib;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.math.Vec3d;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MCH_WeaponDispenser
/*    */   extends MCH_WeaponBase
/*    */ {
/*    */   public MCH_WeaponDispenser(World w, Vec3d v, float yaw, float pitch, String nm, MCH_WeaponInfo wi) {
/* 17 */     super(w, v, yaw, pitch, nm, wi);
/* 18 */     this.acceleration = 0.5F;
/* 19 */     this.explosionPower = 0;
/* 20 */     this.power = 0;
/* 21 */     this.interval = -90;
/*    */     
/* 23 */     if (w.field_72995_K) {
/* 24 */       this.interval -= 10;
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean shot(MCH_WeaponParam prm) {
/* 30 */     if (!this.worldObj.field_72995_K) {
/*    */       
/* 32 */       playSound(prm.entity);
/*    */       
/* 34 */       Vec3d v = MCH_Lib.RotVec3(0.0D, 0.0D, 1.0D, -prm.rotYaw, -prm.rotPitch, -prm.rotRoll);
/*    */       
/* 36 */       MCH_EntityDispensedItem e = new MCH_EntityDispensedItem(this.worldObj, prm.posX, prm.posY, prm.posZ, v.field_72450_a, v.field_72448_b, v.field_72449_c, prm.rotYaw, prm.rotPitch, this.acceleration);
/*    */ 
/*    */       
/* 39 */       e.setName(this.name);
/*    */       
/* 41 */       e.setParameterFromWeapon(this, prm.entity, prm.user);
/*    */       
/* 43 */       e.field_70159_w = prm.entity.field_70159_w + e.field_70159_w * 0.5D;
/* 44 */       e.field_70181_x = prm.entity.field_70181_x + e.field_70181_x * 0.5D;
/* 45 */       e.field_70179_y = prm.entity.field_70179_y + e.field_70179_y * 0.5D;
/* 46 */       e.field_70165_t += e.field_70159_w * 0.5D;
/* 47 */       e.field_70163_u += e.field_70181_x * 0.5D;
/* 48 */       e.field_70161_v += e.field_70179_y * 0.5D;
/*    */       
/* 50 */       this.worldObj.func_72838_d((Entity)e);
/*    */     } 
/*    */     
/* 53 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\weapon\MCH_WeaponDispenser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */