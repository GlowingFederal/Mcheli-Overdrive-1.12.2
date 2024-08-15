/*    */ package mcheli.hud;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MCH_HudItemLine
/*    */   extends MCH_HudItem
/*    */ {
/*    */   private final String[] pos;
/*    */   
/*    */   public MCH_HudItemLine(int fileLine, String[] position) {
/* 15 */     super(fileLine);
/* 16 */     this.pos = new String[position.length];
/* 17 */     for (int i = 0; i < position.length; i++)
/*    */     {
/* 19 */       this.pos[i] = position[i].toLowerCase();
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void execute() {
/* 26 */     double[] lines = new double[this.pos.length];
/* 27 */     for (int i = 0; i < lines.length; i += 2) {
/*    */       
/* 29 */       lines[i + 0] = centerX + calc(this.pos[i + 0]);
/* 30 */       lines[i + 1] = centerY + calc(this.pos[i + 1]);
/*    */     } 
/* 32 */     drawLine(lines, colorSetting, 3);
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\hud\MCH_HudItemLine.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */