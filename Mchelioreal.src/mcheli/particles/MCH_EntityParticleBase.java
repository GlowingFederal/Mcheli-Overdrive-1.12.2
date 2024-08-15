/*    */ package mcheli.particles;
/*    */ 
/*    */ import mcheli.wrapper.W_EntityFX;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class MCH_EntityParticleBase
/*    */   extends W_EntityFX
/*    */ {
/*    */   public boolean isEffectedWind;
/*    */   public boolean diffusible;
/*    */   public boolean toWhite;
/*    */   public float particleMaxScale;
/*    */   public float gravity;
/*    */   public float moutionYUpAge;
/*    */   
/*    */   public MCH_EntityParticleBase(World par1World, double x, double y, double z, double mx, double my, double mz) {
/* 23 */     super(par1World, x, y, z, mx, my, mz);
/* 24 */     this.field_187129_i = mx;
/* 25 */     this.field_187130_j = my;
/* 26 */     this.field_187131_k = mz;
/* 27 */     this.isEffectedWind = false;
/* 28 */     this.particleMaxScale = this.field_70544_f;
/*    */   }
/*    */ 
/*    */   
/*    */   public MCH_EntityParticleBase setParticleScale(float scale) {
/* 33 */     this.field_70544_f = scale;
/* 34 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setParticleMaxAge(int age) {
/* 39 */     this.field_70547_e = age;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void func_70536_a(int par1) {
/* 45 */     this.field_94054_b = par1 % 8;
/* 46 */     this.field_94055_c = par1 / 8;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int func_70537_b() {
/* 52 */     return 2;
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\particles\MCH_EntityParticleBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */