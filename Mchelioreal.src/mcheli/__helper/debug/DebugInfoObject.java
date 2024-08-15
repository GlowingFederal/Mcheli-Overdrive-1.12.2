/*    */ package mcheli.__helper.debug;
/*    */ 
/*    */ import mcheli.debug._v1.PrintStreamWrapper;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface DebugInfoObject
/*    */ {
/*    */   void printInfo(PrintStreamWrapper paramPrintStreamWrapper);
/*    */   
/*    */   default void printInfo() {
/* 25 */     printInfo(PrintStreamWrapper.create(System.out));
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\__helper\debug\DebugInfoObject.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */