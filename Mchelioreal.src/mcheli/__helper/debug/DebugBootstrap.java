/*    */ package mcheli.__helper.debug;
/*    */ 
/*    */ import mcheli.MCH_ClientProxy;
/*    */ import mcheli.MCH_CommonProxy;
/*    */ import mcheli.MCH_MOD;
/*    */ import mcheli.__helper.MCH_Logger;
/*    */ import org.apache.logging.log4j.LogManager;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DebugBootstrap
/*    */ {
/* 17 */   private static final Logger LOGGER = LogManager.getLogger("Debug log");
/*    */ 
/*    */   
/*    */   public static void init() {
/* 21 */     MCH_Logger.setLogger(LOGGER);
/* 22 */     MCH_MOD.instance = new MCH_MOD();
/* 23 */     MCH_MOD.proxy = (MCH_CommonProxy)new MCH_ClientProxy();
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\__helper\debug\DebugBootstrap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */