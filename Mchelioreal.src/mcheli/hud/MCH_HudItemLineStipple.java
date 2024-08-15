/*    */ package mcheli.hud;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MCH_HudItemLineStipple
/*    */   extends MCH_HudItem
/*    */ {
/*    */   private final String pat;
/*    */   private final String fac;
/*    */   private final String[] pos;
/*    */   
/*    */   public MCH_HudItemLineStipple(int fileLine, String[] position) {
/* 18 */     super(fileLine);
/* 19 */     this.pat = position[0];
/* 20 */     this.fac = position[1];
/* 21 */     this.pos = new String[position.length - 2];
/* 22 */     for (int i = 0; i < position.length - 2; i++)
/*    */     {
/* 24 */       this.pos[i] = toFormula(position[2 + i]);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void execute() {
/* 31 */     double[] lines = new double[this.pos.length];
/* 32 */     for (int i = 0; i < lines.length; i += 2) {
/*    */       
/* 34 */       lines[i + 0] = centerX + calc(this.pos[i + 0]);
/* 35 */       lines[i + 1] = centerY + calc(this.pos[i + 1]);
/*    */     } 
/* 37 */     drawLineStipple(lines, colorSetting, (int)calc(this.fac), (int)calc(this.pat));
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\hud\MCH_HudItemLineStipple.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */