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
/*    */ public class MCH_WeaponMachineGun1
/*    */   extends MCH_WeaponBase
/*    */ {
/*    */   public MCH_WeaponMachineGun1(World w, Vec3d v, float yaw, float pitch, String nm, MCH_WeaponInfo wi) {
/* 17 */     super(w, v, yaw, pitch, nm, wi);
/* 18 */     this.power = 8;
/* 19 */     this.acceleration = 4.0F;
/* 20 */     this.explosionPower = 0;
/* 21 */     this.interval = 0;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean shot(MCH_WeaponParam prm) {
/* 27 */     if (!this.worldObj.field_72995_K) {
/*    */       
/* 29 */       Vec3d v = MCH_Lib.RotVec3(0.0D, 0.0D, 1.0D, -prm.rotYaw, -prm.rotPitch, -prm.rotRoll);
/* 30 */       MCH_EntityBullet e = new MCH_EntityBullet(this.worldObj, prm.posX, prm.posY, prm.posZ, v.field_72450_a, v.field_72448_b, v.field_72449_c, prm.rotYaw, prm.rotPitch, this.acceleration);
/*    */ 
/*    */       
/* 33 */       e.setName(this.name);
/* 34 */       e.setParameterFromWeapon(this, prm.entity, prm.user);
/*    */       
/* 36 */       e.field_70165_t += e.field_70159_w * 0.5D;
/* 37 */       e.field_70163_u += e.field_70181_x * 0.5D;
/* 38 */       e.field_70161_v += e.field_70179_y * 0.5D;
/*    */       
/* 40 */       this.worldObj.func_72838_d((Entity)e);
/*    */       
/* 42 */       playSound(prm.entity);
/*    */     } 
/*    */     
/* 45 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\weapon\MCH_WeaponMachineGun1.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */