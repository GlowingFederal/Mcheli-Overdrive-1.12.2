/*    */ package mcheli.__helper.client;
/*    */ 
/*    */ import net.minecraftforge.fml.common.ProgressManager;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ProgressHolder
/*    */ {
/*    */   private static ProgressManager.ProgressBar currentBar;
/*    */   
/*    */   public static void push(String title, int steps) {
/* 17 */     currentBar = ProgressManager.push(title, steps);
/*    */   }
/*    */ 
/*    */   
/*    */   public static void step(String message) {
/* 22 */     currentBar.step(message);
/*    */   }
/*    */ 
/*    */   
/*    */   public static void pop() {
/* 27 */     ProgressManager.pop(currentBar);
/* 28 */     currentBar = null;
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\__helper\client\ProgressHolder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */