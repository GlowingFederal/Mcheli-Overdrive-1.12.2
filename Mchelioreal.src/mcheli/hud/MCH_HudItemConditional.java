/*    */ package mcheli.hud;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MCH_HudItemConditional
/*    */   extends MCH_HudItem
/*    */ {
/*    */   private final boolean isEndif;
/*    */   private final String conditional;
/*    */   
/*    */   public MCH_HudItemConditional(int fileLine, boolean isEndif, String conditional) {
/* 16 */     super(fileLine);
/* 17 */     this.isEndif = isEndif;
/* 18 */     this.conditional = conditional;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean canExecute() {
/* 24 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void execute() {
/* 30 */     if (!this.isEndif) {
/*    */       
/* 32 */       this.parent.isIfFalse = (calc(this.conditional) == 0.0D);
/*    */     }
/*    */     else {
/*    */       
/* 36 */       this.parent.isIfFalse = false;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\hud\MCH_HudItemConditional.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */