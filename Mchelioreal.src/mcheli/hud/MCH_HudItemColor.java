/*    */ package mcheli.hud;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MCH_HudItemColor
/*    */   extends MCH_HudItem
/*    */ {
/*    */   private final String updateColor;
/*    */   
/*    */   public MCH_HudItemColor(int fileLine, String newColor) {
/* 15 */     super(fileLine);
/* 16 */     this.updateColor = newColor;
/*    */   }
/*    */ 
/*    */   
/*    */   public static MCH_HudItemColor createByParams(int fileLine, String[] prm) {
/* 21 */     if (prm.length == 1)
/*    */     {
/* 23 */       return new MCH_HudItemColor(fileLine, toFormula(prm[0]));
/*    */     }
/* 25 */     if (prm.length == 4)
/*    */     {
/* 27 */       return new MCH_HudItemColor(fileLine, "((" + toFormula(prm[0]) + ")<<24)|((" + toFormula(prm[1]) + ")<<16)|((" + 
/* 28 */           toFormula(prm[2]) + ")<<8 )|((" + toFormula(prm[3]) + ")<<0 )");
/*    */     }
/*    */     
/* 31 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void execute() {
/* 37 */     double d = calc(this.updateColor);
/* 38 */     long l = (long)d;
/* 39 */     MCH_HudItem.colorSetting = (int)l;
/* 40 */     updateVarMapItem("color", getColor());
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\hud\MCH_HudItemColor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */