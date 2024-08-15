/*    */ package mcheli.wrapper;
/*    */ 
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.World;
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
/*    */ public class W_Particle
/*    */ {
/*    */   public static BlockParticleParam getParticleTileCrackName(World w, int blockX, int blockY, int blockZ) {
/* 23 */     IBlockState iblockstate = w.func_180495_p(new BlockPos(blockX, blockY, blockZ));
/*    */ 
/*    */     
/* 26 */     if (iblockstate.func_185904_a() != Material.field_151579_a)
/*    */     {
/*    */       
/* 29 */       return new BlockParticleParam("blockcrack", Block.func_176210_f(iblockstate));
/*    */     }
/*    */ 
/*    */     
/* 33 */     return BlockParticleParam.EMPTY;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static BlockParticleParam getParticleTileDustName(World w, int blockX, int blockY, int blockZ) {
/* 40 */     IBlockState iblockstate = w.func_180495_p(new BlockPos(blockX, blockY, blockZ));
/*    */ 
/*    */     
/* 43 */     if (iblockstate.func_185904_a() != Material.field_151579_a)
/*    */     {
/*    */       
/* 46 */       return new BlockParticleParam("blockdust", Block.func_176210_f(iblockstate));
/*    */     }
/*    */ 
/*    */     
/* 50 */     return BlockParticleParam.EMPTY;
/*    */   }
/*    */   
/*    */   public static class BlockParticleParam
/*    */   {
/* 55 */     public static final BlockParticleParam EMPTY = new BlockParticleParam();
/*    */     
/*    */     public final String name;
/*    */     public final int stateId;
/*    */     private final boolean empty;
/*    */     
/*    */     public BlockParticleParam(String name, int stateId) {
/* 62 */       this.name = name;
/* 63 */       this.stateId = stateId;
/* 64 */       this.empty = false;
/*    */     }
/*    */ 
/*    */     
/*    */     private BlockParticleParam() {
/* 69 */       this.name = "";
/* 70 */       this.stateId = 0;
/* 71 */       this.empty = true;
/*    */     }
/*    */ 
/*    */     
/*    */     public boolean isEmpty() {
/* 76 */       return this.empty;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\wrapper\W_Particle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */