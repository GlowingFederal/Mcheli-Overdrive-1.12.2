package mcheli.wrapper;

import java.nio.FloatBuffer;
import mcheli.MCH_Config;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public abstract class W_Render<T extends Entity> extends Render<T> {
  private static FloatBuffer colorBuffer = GLAllocation.func_74529_h(16);
  
  protected W_Render(RenderManager renderManager) {
    super(renderManager);
  }
  
  protected void bindTexture(String path) {
    func_110776_a(new ResourceLocation(W_MOD.DOMAIN, path));
  }
  
  protected static final ResourceLocation TEX_DEFAULT = new ResourceLocation(W_MOD.DOMAIN, "textures/default.png");
  
  public int srcBlend;
  
  public int dstBlend;
  
  protected ResourceLocation func_110775_a(T entity) {
    return TEX_DEFAULT;
  }
  
  public static FloatBuffer setColorBuffer(float p_74521_0_, float p_74521_1_, float p_74521_2_, float p_74521_3_) {
    colorBuffer.clear();
    colorBuffer.put(p_74521_0_).put(p_74521_1_).put(p_74521_2_).put(p_74521_3_);
    colorBuffer.flip();
    return colorBuffer;
  }
  
  public void setCommonRenderParam(boolean smoothShading, int lighting) {
    if (smoothShading && MCH_Config.SmoothShading.prmBool)
      GL11.glShadeModel(7425); 
    GL11.glAlphaFunc(516, 0.001F);
    GL11.glEnable(2884);
    int j = lighting % 65536;
    int k = lighting / 65536;
    OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, j / 1.0F, k / 1.0F);
    GL11.glColor4f(0.75F, 0.75F, 0.75F, 1.0F);
    GL11.glEnable(3042);
    this.srcBlend = GL11.glGetInteger(3041);
    this.dstBlend = GL11.glGetInteger(3040);
    GL11.glBlendFunc(770, 771);
  }
  
  public void restoreCommonRenderParam() {
    GL11.glBlendFunc(this.srcBlend, this.dstBlend);
    GL11.glDisable(3042);
    GL11.glShadeModel(7424);
  }
}
