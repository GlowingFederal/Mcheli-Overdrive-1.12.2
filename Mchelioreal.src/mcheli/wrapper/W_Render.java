/*    */ package mcheli.wrapper;
/*    */ 
/*    */ import java.nio.FloatBuffer;
/*    */ import mcheli.MCH_Config;
/*    */ import net.minecraft.client.renderer.GLAllocation;
/*    */ import net.minecraft.client.renderer.OpenGlHelper;
/*    */ import net.minecraft.client.renderer.entity.Render;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class W_Render<T extends Entity>
/*    */   extends Render<T>
/*    */ {
/* 24 */   private static FloatBuffer colorBuffer = GLAllocation.func_74529_h(16);
/*    */ 
/*    */   
/*    */   protected W_Render(RenderManager renderManager) {
/* 28 */     super(renderManager);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void bindTexture(String path) {
/* 34 */     func_110776_a(new ResourceLocation(W_MOD.DOMAIN, path));
/*    */   }
/*    */ 
/*    */   
/* 38 */   protected static final ResourceLocation TEX_DEFAULT = new ResourceLocation(W_MOD.DOMAIN, "textures/default.png");
/*    */   
/*    */   public int srcBlend;
/*    */   
/*    */   public int dstBlend;
/*    */ 
/*    */   
/*    */   protected ResourceLocation func_110775_a(T entity) {
/* 46 */     return TEX_DEFAULT;
/*    */   }
/*    */ 
/*    */   
/*    */   public static FloatBuffer setColorBuffer(float p_74521_0_, float p_74521_1_, float p_74521_2_, float p_74521_3_) {
/* 51 */     colorBuffer.clear();
/* 52 */     colorBuffer.put(p_74521_0_).put(p_74521_1_).put(p_74521_2_).put(p_74521_3_);
/* 53 */     colorBuffer.flip();
/*    */     
/* 55 */     return colorBuffer;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setCommonRenderParam(boolean smoothShading, int lighting) {
/* 60 */     if (smoothShading && MCH_Config.SmoothShading.prmBool)
/*    */     {
/* 62 */       GL11.glShadeModel(7425);
/*    */     }
/*    */     
/* 65 */     GL11.glAlphaFunc(516, 0.001F);
/* 66 */     GL11.glEnable(2884);
/*    */     
/* 68 */     int j = lighting % 65536;
/* 69 */     int k = lighting / 65536;
/* 70 */     OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, j / 1.0F, k / 1.0F);
/*    */     
/* 72 */     GL11.glColor4f(0.75F, 0.75F, 0.75F, 1.0F);
/*    */     
/* 74 */     GL11.glEnable(3042);
/* 75 */     this.srcBlend = GL11.glGetInteger(3041);
/* 76 */     this.dstBlend = GL11.glGetInteger(3040);
/* 77 */     GL11.glBlendFunc(770, 771);
/*    */   }
/*    */ 
/*    */   
/*    */   public void restoreCommonRenderParam() {
/* 82 */     GL11.glBlendFunc(this.srcBlend, this.dstBlend);
/* 83 */     GL11.glDisable(3042);
/* 84 */     GL11.glShadeModel(7424);
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\wrapper\W_Render.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */