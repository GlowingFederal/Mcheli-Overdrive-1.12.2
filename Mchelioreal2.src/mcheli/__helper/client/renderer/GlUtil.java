package mcheli.__helper.client.renderer;

import com.google.common.collect.Queues;
import java.util.Deque;
import javax.vecmath.AxisAngle4f;
import javax.vecmath.Matrix4f;
import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.shader.Framebuffer;
import net.minecraftforge.common.model.TRSRTransformation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.tuple.Pair;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Sphere;

@SideOnly(Side.CLIENT)
public class GlUtil extends GlStateManager {
  public static float getFloat(int pname) {
    return GL11.glGetFloat(pname);
  }
  
  public static float getLineWidth() {
    return GL11.glGetFloat(2849);
  }
  
  public static float getPointSize() {
    return GL11.glGetFloat(2833);
  }
  
  public static int getBlendSrcFactor() {
    return GL11.glGetInteger(3041);
  }
  
  public static int getBlendDstFactor() {
    return GL11.glGetInteger(3040);
  }
  
  public static void pointSize(float size) {
    GL11.glPointSize(size);
  }
  
  public static void pushLineWidth(float width) {
    lineWidthState.push(Float.valueOf(getLineWidth()));
    func_187441_d(width);
  }
  
  public static float popLineWidth() {
    float f = ((Float)lineWidthState.pop()).floatValue();
    func_187441_d(f);
    return f;
  }
  
  public static void pushPointSize(float size) {
    pointSizeState.push(Float.valueOf(getPointSize()));
    pointSize(size);
  }
  
  public static float popPointSize() {
    float f = ((Float)pointSizeState.pop()).floatValue();
    pointSize(f);
    return f;
  }
  
  public static void pushBlendFunc(GlStateManager.SourceFactor srcFactor, GlStateManager.DestFactor dstFactor) {
    pushBlendFunc(srcFactor.field_187395_p, dstFactor.field_187345_o);
  }
  
  public static void pushBlendFunc(int srcFactor, int dstFactor) {
    blendFancState.push(Pair.of(Integer.valueOf(getBlendSrcFactor()), Integer.valueOf(getBlendDstFactor())));
    func_179112_b(srcFactor, dstFactor);
  }
  
  public static void popBlendFunc() {
    Pair<Integer, Integer> func = blendFancState.pop();
    func_179112_b(((Integer)func.getLeft()).intValue(), ((Integer)func.getRight()).intValue());
  }
  
  public static void polygonMode(GlStateManager.CullFace face, RasterizeType mode) {
    func_187409_d(face.field_187328_d, mode.mode);
  }
  
  public static void depthFunc(Function depthFunc) {
    func_179143_c(depthFunc.func);
  }
  
  public static void clearStencilBufferBit() {
    func_179086_m(1024);
  }
  
  public static void clearStencil(int stencil) {
    if (stencil != clearState.stencil) {
      clearState.stencil = stencil;
      GL11.glClearStencil(stencil);
    } 
  }
  
  public static void enableStencil() {
    stencilState.stencilTest.setEnabled();
  }
  
  public static void disableStencil() {
    stencilState.stencilTest.setDisabled();
  }
  
  public static void stencilFunc(Function stencilFunc, int ref, int mask) {
    stencilFunc(stencilFunc.func, ref, mask);
  }
  
  public static void stencilFunc(int stencilFunc, int ref, int mask) {
    if (stencilFunc != stencilState.func.func || ref != stencilState.func.ref || mask != stencilState.func.mask) {
      stencilState.func.func = stencilFunc;
      stencilState.func.ref = ref;
      stencilState.func.mask = mask;
      GL11.glStencilFunc(stencilFunc, ref, mask);
    } 
  }
  
  public static void stencilMask(int mask) {
    if (mask != stencilState.mask) {
      stencilState.mask = mask;
      GL11.glStencilMask(mask);
    } 
  }
  
  public static void stencilOp(StencilAction fail, StencilAction zfail, StencilAction zpass) {
    stencilOp(fail.action, zfail.action, zpass.action);
  }
  
  public static void stencilOp(int fail, int zfail, int zpass) {
    if (fail != stencilState.fail || zfail != stencilState.zfail || zpass != stencilState.zpass) {
      stencilState.fail = fail;
      stencilState.zfail = zfail;
      stencilState.zpass = zpass;
      GL11.glStencilOp(fail, zfail, zpass);
    } 
  }
  
  public static void drawSphere(double x, double y, double z, float size, int slices, int stacks) {
    Sphere s = new Sphere();
    GL11.glPushMatrix();
    GL11.glBlendFunc(770, 771);
    GL11.glEnable(3042);
    GL11.glLineWidth(1.2F);
    GL11.glDisable(3553);
    s.setDrawStyle(100013);
    GL11.glTranslatef((float)x, (float)y, (float)z);
    s.draw(size, slices, stacks);
    GL11.glLineWidth(2.0F);
    GL11.glEnable(3553);
    GL11.glDisable(3042);
    GL11.glPopMatrix();
  }
  
  public static void enableScissor() {
    scissorState.setEnabled();
  }
  
  public static void disableScissor() {
    scissorState.setDisabled();
  }
  
  public static void scissor(int x, int y, int width, int height) {
    GL11.glScissor(x, y, width, height);
  }
  
  public static void scissorGui(Minecraft mc, int x, int y, int width, int height) {
    ScaledResolution res = new ScaledResolution(mc);
    double scaleW = mc.field_71443_c / res.func_78327_c();
    double scaleH = mc.field_71440_d / res.func_78324_d();
    scissor((int)(x * scaleW), (int)(mc.field_71440_d - (y + height) * scaleH), (int)(width * scaleW), (int)(height * scaleH));
  }
  
  public static void enableStipple() {
    stippleState.setEnabled();
  }
  
  public static void disableStipple() {
    stippleState.setDisabled();
  }
  
  public static void lineStipple(int factor, short pattern) {
    GL11.glLineStipple(factor, pattern);
  }
  
  private static final StencilState stencilState = new StencilState();
  
  private static final ClearState clearState = new ClearState();
  
  private static final BooleanState scissorState = new BooleanState(3089);
  
  private static final BooleanState stippleState = new BooleanState(2852);
  
  private static final Deque<Float> lineWidthState = Queues.newArrayDeque();
  
  private static final Deque<Float> pointSizeState = Queues.newArrayDeque();
  
  private static final Deque<Pair<Integer, Integer>> blendFancState = Queues.newArrayDeque();
  
  public static boolean enableStencilBuffer() {
    Framebuffer framebuffer = Minecraft.func_71410_x().func_147110_a();
    if (!framebuffer.isStencilEnabled() && OpenGlHelper.func_148822_b()) {
      framebuffer.enableStencil();
      return true;
    } 
    return false;
  }
  
  public static boolean isEnableStencilBuffer() {
    return Minecraft.func_71410_x().func_147110_a().isStencilEnabled();
  }
  
  public static Matrix4f translateAsMatrix(float x, float y, float z) {
    return TRSRTransformation.mul(new Vector3f(x, y, z), null, null, null);
  }
  
  public static Matrix4f rotateAsMatrix(float angle, float x, float y, float z) {
    Quat4f quat = new Quat4f(0.0F, 0.0F, 0.0F, 1.0F), t = new Quat4f();
    t.set(new AxisAngle4f(x, y, z, angle * 0.017453292F));
    quat.mul(t);
    return TRSRTransformation.mul(null, quat, null, null);
  }
  
  public static Matrix4f scaleAsMatrix(float x, float y, float z) {
    return TRSRTransformation.mul(null, null, new Vector3f(x, y, z), null);
  }
  
  @SideOnly(Side.CLIENT)
  static class ClearState {
    private ClearState() {}
    
    public int stencil = 0;
  }
  
  @SideOnly(Side.CLIENT)
  public enum Function {
    NEVER(512),
    LESS(513),
    EQUAL(514),
    LEQUAL(515),
    GREATER(516),
    NOTEQUAL(517),
    GEQUAL(518),
    ALWAYS(519);
    
    public final int func;
    
    Function(int func) {
      this.func = func;
    }
  }
  
  @SideOnly(Side.CLIENT)
  public enum StencilAction {
    KEEP(7680),
    ZERO(0),
    INCR(7682),
    INCR_WRAP(34055),
    DECR(7683),
    DECR_WRAP(34056),
    REPLACE(7681),
    INVERT(5386);
    
    public final int action;
    
    StencilAction(int action) {
      this.action = action;
    }
  }
  
  @SideOnly(Side.CLIENT)
  static class StencilFunc {
    private StencilFunc() {}
    
    public int func = 519;
    
    public int ref = 0;
    
    public int mask = -1;
  }
  
  @SideOnly(Side.CLIENT)
  static class StencilState {
    private StencilState() {}
    
    public GlUtil.BooleanState stencilTest = new GlUtil.BooleanState(2960);
    
    public GlUtil.StencilFunc func = new GlUtil.StencilFunc();
    
    public int mask = -1;
    
    public int fail = 7680;
    
    public int zfail = 7680;
    
    public int zpass = 7680;
  }
  
  @SideOnly(Side.CLIENT)
  public enum RasterizeType {
    POINT(6912),
    LINE(6913),
    FILL(6914);
    
    public final int mode;
    
    RasterizeType(int mode) {
      this.mode = mode;
    }
  }
  
  @SideOnly(Side.CLIENT)
  protected static class BooleanState {
    private final int capability;
    
    private boolean currentState;
    
    public BooleanState(int capabilityIn) {
      this.capability = capabilityIn;
    }
    
    public void setDisabled() {
      setState(false);
    }
    
    public void setEnabled() {
      setState(true);
    }
    
    public void setState(boolean state) {
      if (state != this.currentState) {
        this.currentState = state;
        if (state) {
          GL11.glEnable(this.capability);
        } else {
          GL11.glDisable(this.capability);
        } 
      } 
    }
  }
}
