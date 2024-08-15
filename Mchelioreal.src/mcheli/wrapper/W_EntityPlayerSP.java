/*    */ package mcheli.wrapper;
/*    */ 
/*    */ import net.minecraft.client.entity.EntityPlayerSP;
/*    */ import net.minecraft.entity.Entity;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class W_EntityPlayerSP
/*    */ {
/*    */   public static void closeScreen(Entity p) {
/* 16 */     if (p instanceof EntityPlayerSP)
/*    */     {
/* 18 */       ((EntityPlayerSP)p).func_71053_j();
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\wrapper\W_EntityPlayerSP.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */