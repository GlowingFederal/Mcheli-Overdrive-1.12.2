/*    */ package mcheli.weapon;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MCH_WeaponParam
/*    */ {
/* 30 */   public Entity entity = null;
/* 31 */   public Entity user = null;
/* 32 */   public double posX = 0.0D;
/* 33 */   public double posY = 0.0D;
/* 34 */   public double posZ = 0.0D;
/* 35 */   public float rotYaw = 0.0F;
/* 36 */   public float rotPitch = 0.0F;
/* 37 */   public float rotRoll = 0.0F;
/* 38 */   public int option1 = 0;
/* 39 */   public int option2 = 0;
/*    */   public boolean isInfinity = false;
/*    */   public boolean isTurret = false;
/*    */   public boolean result;
/*    */   public boolean reload;
/*    */   
/*    */   public void setPosAndRot(double x, double y, double z, float yaw, float pitch) {
/* 46 */     setPosition(x, y, z);
/* 47 */     setRotation(yaw, pitch);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setPosition(double x, double y, double z) {
/* 52 */     this.posX = x;
/* 53 */     this.posY = y;
/* 54 */     this.posZ = z;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setRotation(float y, float p) {
/* 59 */     this.rotYaw = y;
/* 60 */     this.rotPitch = p;
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\weapon\MCH_WeaponParam.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */