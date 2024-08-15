/*    */ package mcheli.hud;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MCH_HudItemExit
/*    */   extends MCH_HudItem
/*    */ {
/*    */   public MCH_HudItemExit(int fileLine) {
/* 13 */     super(fileLine);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void execute() {
/* 19 */     this.parent.exit = true;
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\hud\MCH_HudItemExit.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */