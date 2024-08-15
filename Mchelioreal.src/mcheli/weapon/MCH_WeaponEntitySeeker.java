/*    */ package mcheli.weapon;
/*    */ 
/*    */ import net.minecraft.util.math.Vec3d;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class MCH_WeaponEntitySeeker
/*    */   extends MCH_WeaponBase
/*    */ {
/*    */   public MCH_IEntityLockChecker entityLockChecker;
/*    */   public MCH_WeaponGuidanceSystem guidanceSystem;
/*    */   
/*    */   public MCH_WeaponEntitySeeker(World w, Vec3d v, float yaw, float pitch, String nm, MCH_WeaponInfo wi) {
/* 19 */     super(w, v, yaw, pitch, nm, wi);
/* 20 */     this.guidanceSystem = new MCH_WeaponGuidanceSystem(w);
/* 21 */     this.guidanceSystem.lockRange = 200.0D;
/* 22 */     this.guidanceSystem.lockAngle = 5;
/* 23 */     this.guidanceSystem.setLockCountMax(25);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public MCH_WeaponGuidanceSystem getGuidanceSystem() {
/* 29 */     return this.guidanceSystem;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getLockCount() {
/* 35 */     return this.guidanceSystem.getLockCount();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void setLockCountMax(int n) {
/* 41 */     this.guidanceSystem.setLockCountMax(n);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getLockCountMax() {
/* 47 */     return this.guidanceSystem.getLockCountMax();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void setLockChecker(MCH_IEntityLockChecker checker) {
/* 53 */     this.guidanceSystem.checker = checker;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void update(int countWait) {
/* 59 */     super.update(countWait);
/* 60 */     this.guidanceSystem.update();
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\weapon\MCH_WeaponEntitySeeker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */