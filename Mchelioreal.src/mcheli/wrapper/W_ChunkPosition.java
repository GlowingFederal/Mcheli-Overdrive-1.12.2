/*    */ package mcheli.wrapper;
/*    */ 
/*    */ import net.minecraft.util.math.BlockPos;
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
/*    */ public class W_ChunkPosition
/*    */ {
/*    */   public static int getChunkPosX(BlockPos c) {
/* 19 */     return c.func_177958_n();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static int getChunkPosY(BlockPos c) {
/* 26 */     return c.func_177956_o();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static int getChunkPosZ(BlockPos c) {
/* 33 */     return c.func_177952_p();
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\wrapper\W_ChunkPosition.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */