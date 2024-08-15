/*    */ package mcheli.__helper;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MCH_ColorInt
/*    */ {
/*    */   public final int r;
/*    */   public final int g;
/*    */   public final int b;
/*    */   public final int a;
/*    */   
/*    */   public MCH_ColorInt(int color) {
/* 14 */     this(color >> 16 & 0xFF, color >> 8 & 0xFF, color & 0xFF, color >> 24 & 0xFF);
/*    */   }
/*    */ 
/*    */   
/*    */   public MCH_ColorInt(int r, int g, int b, int a) {
/* 19 */     this.r = r;
/* 20 */     this.g = g;
/* 21 */     this.b = b;
/* 22 */     this.a = a;
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\__helper\MCH_ColorInt.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */