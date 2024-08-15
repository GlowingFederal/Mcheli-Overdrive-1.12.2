/*    */ package mcheli.weapon;
/*    */ 
/*    */ import mcheli.__helper.client._IModelCustom;
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
/*    */ public class MCH_Cartridge
/*    */ {
/*    */   public _IModelCustom model;
/*    */   public final String name;
/*    */   public final float acceleration;
/*    */   public final float yaw;
/*    */   public final float pitch;
/*    */   public final float bound;
/*    */   public final float gravity;
/*    */   public final float scale;
/*    */   
/*    */   public MCH_Cartridge(String nm, float a, float y, float p, float b, float g, float s) {
/* 26 */     this.name = nm;
/* 27 */     this.acceleration = a;
/* 28 */     this.yaw = y;
/* 29 */     this.pitch = p;
/* 30 */     this.bound = b;
/* 31 */     this.gravity = g;
/* 32 */     this.scale = s;
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\weapon\MCH_Cartridge.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */