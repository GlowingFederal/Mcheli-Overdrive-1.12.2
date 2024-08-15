/*    */ package mcheli.hud;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MCH_HudItemRect
/*    */   extends MCH_HudItem
/*    */ {
/*    */   private final String left;
/*    */   private final String top;
/*    */   private final String width;
/*    */   private final String height;
/*    */   
/*    */   public MCH_HudItemRect(int fileLine, String left, String top, String width, String height) {
/* 18 */     super(fileLine);
/* 19 */     this.left = toFormula(left);
/* 20 */     this.top = toFormula(top);
/* 21 */     this.width = toFormula(width);
/* 22 */     this.height = toFormula(height);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void execute() {
/* 28 */     double x2 = centerX + calc(this.left);
/* 29 */     double y2 = centerY + calc(this.top);
/* 30 */     double x1 = x2 + (int)calc(this.width);
/* 31 */     double y1 = y2 + (int)calc(this.height);
/* 32 */     drawRect(x1, y1, x2, y2, colorSetting);
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\hud\MCH_HudItemRect.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */