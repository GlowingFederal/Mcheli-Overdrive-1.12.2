/*    */ package mcheli.wrapper;
/*    */ 
/*    */ import net.minecraft.client.particle.Particle;
/*    */ import net.minecraft.util.math.AxisAlignedBB;
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
/*    */ public abstract class W_EntityFX
/*    */   extends Particle
/*    */ {
/*    */   public W_EntityFX(World par1World, double par2, double par4, double par6) {
/* 20 */     super(par1World, par2, par4, par6);
/*    */   }
/*    */ 
/*    */   
/*    */   public W_EntityFX(World par1World, double par2, double par4, double par6, double par8, double par10, double par12) {
/* 25 */     super(par1World, par2, par4, par6, par8, par10, par12);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public AxisAlignedBB getCollisionBoundingBox() {
/* 31 */     return func_187116_l();
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\wrapper\W_EntityFX.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */