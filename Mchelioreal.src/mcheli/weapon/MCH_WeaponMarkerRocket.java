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
/*    */ public class MCH_WeaponMarkerRocket
/*    */   extends MCH_WeaponBase
/*    */ {
/*    */   public MCH_WeaponMarkerRocket(World w, Vec3d v, float yaw, float pitch, String nm, MCH_WeaponInfo wi) {
/* 17 */     super(w, v, yaw, pitch, nm, wi);
/* 18 */     this.acceleration = 3.0F;
/* 19 */     this.explosionPower = 0;
/* 20 */     this.power = 0;
/* 21 */     this.interval = 60;
/*    */     
/* 23 */     if (w.field_72995_K) {
/* 24 */       this.interval += 10;
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
/* 35 */       MCH_EntityMarkerRocket e = new MCH_EntityMarkerRocket(this.worldObj, prm.posX, prm.posY, prm.posZ, v.field_72450_a, v.field_72448_b, v.field_72449_c, prm.rotYaw, prm.rotPitch, this.acceleration);
/*    */ 
/*    */       
/* 38 */       e.setName(this.name);
/* 39 */       e.setParameterFromWeapon(this, prm.entity, prm.user);
/* 40 */       e.setMarkerStatus(1);
/*    */       
/* 42 */       this.worldObj.func_72838_d((Entity)e);
/*    */     }
/*    */     else {
/*    */       
/* 46 */       this.optionParameter1 = getCurrentMode();
/*    */     } 
/*    */     
/* 49 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\weapon\MCH_WeaponMarkerRocket.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */