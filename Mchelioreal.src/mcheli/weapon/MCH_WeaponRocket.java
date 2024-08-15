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
/*    */ public class MCH_WeaponRocket
/*    */   extends MCH_WeaponBase
/*    */ {
/*    */   public MCH_WeaponRocket(World w, Vec3d v, float yaw, float pitch, String nm, MCH_WeaponInfo wi) {
/* 17 */     super(w, v, yaw, pitch, nm, wi);
/* 18 */     this.acceleration = 4.0F;
/* 19 */     this.explosionPower = 3;
/* 20 */     this.power = 22;
/* 21 */     this.interval = 5;
/* 22 */     if (w.field_72995_K) {
/* 23 */       this.interval += 2;
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public String getName() {
/* 29 */     return super.getName() + ((getCurrentMode() == 0) ? "" : " [HEIAP]");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean shot(MCH_WeaponParam prm) {
/* 35 */     if (!this.worldObj.field_72995_K) {
/*    */       
/* 37 */       playSound(prm.entity);
/*    */       
/* 39 */       Vec3d v = MCH_Lib.RotVec3(0.0D, 0.0D, 1.0D, -prm.rotYaw, -prm.rotPitch, -prm.rotRoll);
/* 40 */       MCH_EntityRocket e = new MCH_EntityRocket(this.worldObj, prm.posX, prm.posY, prm.posZ, v.field_72450_a, v.field_72448_b, v.field_72449_c, prm.rotYaw, prm.rotPitch, this.acceleration);
/*    */ 
/*    */       
/* 43 */       e.setName(this.name);
/* 44 */       e.setParameterFromWeapon(this, prm.entity, prm.user);
/*    */       
/* 46 */       if (prm.option1 == 0 && this.numMode > 1)
/*    */       {
/* 48 */         e.piercing = 0;
/*    */       }
/*    */       
/* 51 */       this.worldObj.func_72838_d((Entity)e);
/*    */     }
/*    */     else {
/*    */       
/* 55 */       this.optionParameter1 = getCurrentMode();
/*    */     } 
/*    */     
/* 58 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\weapon\MCH_WeaponRocket.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */