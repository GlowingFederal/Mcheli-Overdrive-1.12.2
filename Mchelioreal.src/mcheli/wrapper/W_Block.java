/*    */ package mcheli.wrapper;
/*    */ 
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.material.Material;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class W_Block
/*    */   extends Block
/*    */ {
/*    */   protected W_Block(Material materialIn) {
/* 16 */     super(materialIn);
/*    */   }
/*    */ 
/*    */   
/*    */   public static Block getBlockFromName(String name) {
/* 21 */     return Block.func_149684_b(name);
/*    */   }
/*    */ 
/*    */   
/*    */   public static Block getSnowLayer() {
/* 26 */     return W_Blocks.field_150431_aC;
/*    */   }
/*    */ 
/*    */   
/*    */   public static boolean isNull(Block block) {
/* 31 */     return (block == null || block == W_Blocks.field_150350_a);
/*    */   }
/*    */ 
/*    */   
/*    */   public static boolean isEqual(int blockId, Block block) {
/* 36 */     return Block.func_149680_a(Block.func_149729_e(blockId), block);
/*    */   }
/*    */ 
/*    */   
/*    */   public static boolean isEqual(Block block1, Block block2) {
/* 41 */     return Block.func_149680_a(block1, block2);
/*    */   }
/*    */ 
/*    */   
/*    */   public static Block getWater() {
/* 46 */     return (Block)W_Blocks.field_150355_j;
/*    */   }
/*    */ 
/*    */   
/*    */   public static Block getBlockById(int i) {
/* 51 */     return Block.func_149729_e(i);
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\wrapper\W_Block.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */