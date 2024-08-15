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
/*    */ public class MCH_WeaponMachineGun2
/*    */   extends MCH_WeaponBase
/*    */ {
/*    */   public MCH_WeaponMachineGun2(World w, Vec3d v, float yaw, float pitch, String nm, MCH_WeaponInfo wi) {
/* 17 */     super(w, v, yaw, pitch, nm, wi);
/* 18 */     this.power = 16;
/* 19 */     this.acceleration = 4.0F;
/* 20 */     this.explosionPower = 1;
/* 21 */     this.interval = 2;
/* 22 */     this.numMode = 2;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void modifyParameters() {
/* 28 */     if (this.explosionPower == 0)
/*    */     {
/* 30 */       this.numMode = 0;
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getName() {
/* 37 */     return super.getName() + ((getCurrentMode() == 0) ? "" : " [HE]");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean shot(MCH_WeaponParam prm) {
/* 43 */     if (!this.worldObj.field_72995_K) {
/*    */       
/* 45 */       Vec3d v = MCH_Lib.RotVec3(0.0D, 0.0D, 1.0D, -prm.rotYaw, -prm.rotPitch, -prm.rotRoll);
/* 46 */       MCH_EntityBullet e = new MCH_EntityBullet(this.worldObj, prm.posX, prm.posY, prm.posZ, v.field_72450_a, v.field_72448_b, v.field_72449_c, prm.rotYaw, prm.rotPitch, this.acceleration);
/*    */ 
/*    */       
/* 49 */       e.setName(this.name);
/* 50 */       e.setParameterFromWeapon(this, prm.entity, prm.user);
/*    */       
/* 52 */       if ((getInfo()).modeNum < 2) {
/*    */         
/* 54 */         e.explosionPower = this.explosionPower;
/*    */       }
/*    */       else {
/*    */         
/* 58 */         e.explosionPower = (prm.option1 == 0) ? -this.explosionPower : this.explosionPower;
/*    */       } 
/*    */       
/* 61 */       e.field_70165_t += e.field_70159_w * 0.5D;
/* 62 */       e.field_70163_u += e.field_70181_x * 0.5D;
/* 63 */       e.field_70161_v += e.field_70179_y * 0.5D;
/*    */       
/* 65 */       this.worldObj.func_72838_d((Entity)e);
/*    */       
/* 67 */       playSound(prm.entity);
/*    */     }
/*    */     else {
/*    */       
/* 71 */       this.optionParameter1 = getCurrentMode();
/*    */     } 
/*    */     
/* 74 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\weapon\MCH_WeaponMachineGun2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */