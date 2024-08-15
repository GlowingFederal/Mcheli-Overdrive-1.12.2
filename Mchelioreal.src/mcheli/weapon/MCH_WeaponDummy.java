/*    */ package mcheli.weapon;
/*    */ 
/*    */ import mcheli.__helper.MCH_Utils;
/*    */ import net.minecraft.util.math.Vec3d;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MCH_WeaponDummy
/*    */   extends MCH_WeaponBase
/*    */ {
/* 16 */   static final MCH_WeaponInfo dummy = new MCH_WeaponInfo(MCH_Utils.buildinAddon("none"), "none");
/*    */ 
/*    */   
/*    */   public int getUseInterval() {
/* 20 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public MCH_WeaponDummy(World w, Vec3d v, float yaw, float pitch, String nm, MCH_WeaponInfo wi) {
/* 25 */     super(w, v, yaw, pitch, !nm.isEmpty() ? nm : "none", (wi != null) ? wi : dummy);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean shot(MCH_WeaponParam prm) {
/* 31 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\weapon\MCH_WeaponDummy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */