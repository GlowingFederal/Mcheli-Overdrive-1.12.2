/*    */ package mcheli.particles;
/*    */ 
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MCH_ParticleParam
/*    */ {
/*    */   public final World world;
/*    */   public final String name;
/*    */   public double posX;
/*    */   public double posY;
/*    */   public double posZ;
/* 18 */   public double motionX = 0.0D;
/* 19 */   public double motionY = 0.0D;
/* 20 */   public double motionZ = 0.0D;
/* 21 */   public float size = 1.0F;
/* 22 */   public float a = 1.0F;
/* 23 */   public float r = 1.0F;
/* 24 */   public float g = 1.0F;
/* 25 */   public float b = 1.0F;
/*    */   public boolean isEffectWind = false;
/* 27 */   public int age = 0;
/*    */   public boolean diffusible = false;
/*    */   public boolean toWhite = false;
/* 30 */   public float gravity = 0.0F;
/* 31 */   public float motionYUpAge = 2.0F;
/*    */ 
/*    */   
/*    */   public MCH_ParticleParam(World w, String name, double x, double y, double z) {
/* 35 */     this.world = w;
/* 36 */     this.name = name;
/* 37 */     this.posX = x;
/* 38 */     this.posY = y;
/* 39 */     this.posZ = z;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public MCH_ParticleParam(World w, String name, double x, double y, double z, double mx, double my, double mz, float size) {
/* 45 */     this(w, name, x, y, z);
/* 46 */     this.motionX = mx;
/* 47 */     this.motionY = my;
/* 48 */     this.motionZ = mz;
/* 49 */     this.size = size;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setColor(float a, float r, float g, float b) {
/* 54 */     this.a = a;
/* 55 */     this.r = r;
/* 56 */     this.g = g;
/* 57 */     this.b = b;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setMotion(double x, double y, double z) {
/* 62 */     this.motionX = x;
/* 63 */     this.motionY = y;
/* 64 */     this.motionZ = z;
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\particles\MCH_ParticleParam.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */