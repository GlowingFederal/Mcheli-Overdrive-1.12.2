/*    */ package mcheli.hud;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MCH_HudItemCall
/*    */   extends MCH_HudItem
/*    */ {
/*    */   private final String hudName;
/*    */   
/*    */   public MCH_HudItemCall(int fileLine, String name) {
/* 15 */     super(fileLine);
/* 16 */     this.hudName = name;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void execute() {
/* 22 */     MCH_Hud hud = MCH_HudManager.get(this.hudName);
/* 23 */     if (hud != null)
/*    */     {
/* 25 */       hud.drawItems();
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\hud\MCH_HudItemCall.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */