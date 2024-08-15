/*    */ package mcheli.wrapper;
/*    */ 
/*    */ import net.minecraftforge.common.MinecraftForge;
/*    */ import net.minecraftforge.fml.relauncher.Side;
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
/*    */ public class W_TickRegistry
/*    */ {
/*    */   public static void registerTickHandler(W_TickHandler handler, Side side) {
/* 18 */     MinecraftForge.EVENT_BUS.register(handler);
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\wrapper\W_TickRegistry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */