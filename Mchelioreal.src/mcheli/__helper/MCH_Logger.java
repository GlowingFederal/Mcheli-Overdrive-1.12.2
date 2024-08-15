/*    */ package mcheli.__helper;
/*    */ 
/*    */ import org.apache.logging.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MCH_Logger
/*    */ {
/*    */   private static Logger logger;
/*    */   
/*    */   public static void setLogger(Logger loggerIn) {
/* 16 */     if (logger == null)
/*    */     {
/* 18 */       logger = loggerIn;
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public static Logger get() {
/* 24 */     return logger;
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\__helper\MCH_Logger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */