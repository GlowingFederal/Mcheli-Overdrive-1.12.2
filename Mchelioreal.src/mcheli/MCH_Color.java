/*    */ package mcheli;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MCH_Color
/*    */ {
/*    */   public float a;
/*    */   public float r;
/*    */   public float g;
/*    */   public float b;
/*    */   
/*    */   public MCH_Color(float aa, float rr, float gg, float bb) {
/* 18 */     this.a = round(aa);
/* 19 */     this.r = round(rr);
/* 20 */     this.g = round(gg);
/* 21 */     this.b = round(bb);
/*    */   }
/*    */ 
/*    */   
/*    */   public MCH_Color(float rr, float gg, float bb) {
/* 26 */     this(1.0F, rr, gg, bb);
/*    */   }
/*    */ 
/*    */   
/*    */   public MCH_Color() {
/* 31 */     this(1.0F, 1.0F, 1.0F, 1.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   public float round(float f) {
/* 36 */     return (f > 1.0F) ? 1.0F : ((f < 0.0F) ? 0.0F : f);
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\MCH_Color.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */