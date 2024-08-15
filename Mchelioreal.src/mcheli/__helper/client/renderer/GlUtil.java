/*     */ package mcheli.__helper.client.renderer;
/*     */ 
/*     */ import com.google.common.collect.Queues;
/*     */ import java.util.Deque;
/*     */ import javax.vecmath.AxisAngle4f;
/*     */ import javax.vecmath.Matrix4f;
/*     */ import javax.vecmath.Quat4f;
/*     */ import javax.vecmath.Vector3f;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.ScaledResolution;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.client.shader.Framebuffer;
/*     */ import net.minecraftforge.common.model.TRSRTransformation;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import org.apache.commons.lang3.tuple.Pair;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import org.lwjgl.util.glu.Sphere;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @SideOnly(Side.CLIENT)
/*     */ public class GlUtil
/*     */   extends GlStateManager
/*     */ {
/*     */   public static float getFloat(int pname) {
/*  44 */     return GL11.glGetFloat(pname);
/*     */   }
/*     */ 
/*     */   
/*     */   public static float getLineWidth() {
/*  49 */     return GL11.glGetFloat(2849);
/*     */   }
/*     */ 
/*     */   
/*     */   public static float getPointSize() {
/*  54 */     return GL11.glGetFloat(2833);
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getBlendSrcFactor() {
/*  59 */     return GL11.glGetInteger(3041);
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getBlendDstFactor() {
/*  64 */     return GL11.glGetInteger(3040);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void pointSize(float size) {
/*  69 */     GL11.glPointSize(size);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void pushLineWidth(float width) {
/*  74 */     lineWidthState.push(Float.valueOf(getLineWidth()));
/*  75 */     func_187441_d(width);
/*     */   }
/*     */ 
/*     */   
/*     */   public static float popLineWidth() {
/*  80 */     float f = ((Float)lineWidthState.pop()).floatValue();
/*  81 */     func_187441_d(f);
/*     */     
/*  83 */     return f;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void pushPointSize(float size) {
/*  88 */     pointSizeState.push(Float.valueOf(getPointSize()));
/*  89 */     pointSize(size);
/*     */   }
/*     */ 
/*     */   
/*     */   public static float popPointSize() {
/*  94 */     float f = ((Float)pointSizeState.pop()).floatValue();
/*  95 */     pointSize(f);
/*     */     
/*  97 */     return f;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void pushBlendFunc(GlStateManager.SourceFactor srcFactor, GlStateManager.DestFactor dstFactor) {
/* 102 */     pushBlendFunc(srcFactor.field_187395_p, dstFactor.field_187345_o);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void pushBlendFunc(int srcFactor, int dstFactor) {
/* 107 */     blendFancState.push(Pair.of(Integer.valueOf(getBlendSrcFactor()), Integer.valueOf(getBlendDstFactor())));
/* 108 */     func_179112_b(srcFactor, dstFactor);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void popBlendFunc() {
/* 113 */     Pair<Integer, Integer> func = blendFancState.pop();
/* 114 */     func_179112_b(((Integer)func.getLeft()).intValue(), ((Integer)func.getRight()).intValue());
/*     */   }
/*     */ 
/*     */   
/*     */   public static void polygonMode(GlStateManager.CullFace face, RasterizeType mode) {
/* 119 */     func_187409_d(face.field_187328_d, mode.mode);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void depthFunc(Function depthFunc) {
/* 124 */     func_179143_c(depthFunc.func);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void clearStencilBufferBit() {
/* 129 */     func_179086_m(1024);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void clearStencil(int stencil) {
/* 134 */     if (stencil != clearState.stencil) {
/*     */       
/* 136 */       clearState.stencil = stencil;
/* 137 */       GL11.glClearStencil(stencil);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void enableStencil() {
/* 143 */     stencilState.stencilTest.setEnabled();
/*     */   }
/*     */ 
/*     */   
/*     */   public static void disableStencil() {
/* 148 */     stencilState.stencilTest.setDisabled();
/*     */   }
/*     */ 
/*     */   
/*     */   public static void stencilFunc(Function stencilFunc, int ref, int mask) {
/* 153 */     stencilFunc(stencilFunc.func, ref, mask);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void stencilFunc(int stencilFunc, int ref, int mask) {
/* 158 */     if (stencilFunc != stencilState.func.func || ref != stencilState.func.ref || mask != stencilState.func.mask) {
/*     */       
/* 160 */       stencilState.func.func = stencilFunc;
/* 161 */       stencilState.func.ref = ref;
/* 162 */       stencilState.func.mask = mask;
/* 163 */       GL11.glStencilFunc(stencilFunc, ref, mask);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void stencilMask(int mask) {
/* 169 */     if (mask != stencilState.mask) {
/*     */       
/* 171 */       stencilState.mask = mask;
/* 172 */       GL11.glStencilMask(mask);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void stencilOp(StencilAction fail, StencilAction zfail, StencilAction zpass) {
/* 178 */     stencilOp(fail.action, zfail.action, zpass.action);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void stencilOp(int fail, int zfail, int zpass) {
/* 183 */     if (fail != stencilState.fail || zfail != stencilState.zfail || zpass != stencilState.zpass) {
/*     */       
/* 185 */       stencilState.fail = fail;
/* 186 */       stencilState.zfail = zfail;
/* 187 */       stencilState.zpass = zpass;
/* 188 */       GL11.glStencilOp(fail, zfail, zpass);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void drawSphere(double x, double y, double z, float size, int slices, int stacks) {
/* 194 */     Sphere s = new Sphere();
/* 195 */     GL11.glPushMatrix();
/* 196 */     GL11.glBlendFunc(770, 771);
/* 197 */     GL11.glEnable(3042);
/* 198 */     GL11.glLineWidth(1.2F);
/* 199 */     GL11.glDisable(3553);
/* 200 */     s.setDrawStyle(100013);
/* 201 */     GL11.glTranslatef((float)x, (float)y, (float)z);
/* 202 */     s.draw(size, slices, stacks);
/* 203 */     GL11.glLineWidth(2.0F);
/* 204 */     GL11.glEnable(3553);
/* 205 */     GL11.glDisable(3042);
/* 206 */     GL11.glPopMatrix();
/*     */   }
/*     */ 
/*     */   
/*     */   public static void enableScissor() {
/* 211 */     scissorState.setEnabled();
/*     */   }
/*     */ 
/*     */   
/*     */   public static void disableScissor() {
/* 216 */     scissorState.setDisabled();
/*     */   }
/*     */ 
/*     */   
/*     */   public static void scissor(int x, int y, int width, int height) {
/* 221 */     GL11.glScissor(x, y, width, height);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void scissorGui(Minecraft mc, int x, int y, int width, int height) {
/* 226 */     ScaledResolution res = new ScaledResolution(mc);
/* 227 */     double scaleW = mc.field_71443_c / res.func_78327_c();
/* 228 */     double scaleH = mc.field_71440_d / res.func_78324_d();
/*     */     
/* 230 */     scissor((int)(x * scaleW), (int)(mc.field_71440_d - (y + height) * scaleH), (int)(width * scaleW), (int)(height * scaleH));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void enableStipple() {
/* 236 */     stippleState.setEnabled();
/*     */   }
/*     */ 
/*     */   
/*     */   public static void disableStipple() {
/* 241 */     stippleState.setDisabled();
/*     */   }
/*     */ 
/*     */   
/*     */   public static void lineStipple(int factor, short pattern) {
/* 246 */     GL11.glLineStipple(factor, pattern);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 251 */   private static final StencilState stencilState = new StencilState();
/* 252 */   private static final ClearState clearState = new ClearState();
/*     */   
/* 254 */   private static final BooleanState scissorState = new BooleanState(3089);
/* 255 */   private static final BooleanState stippleState = new BooleanState(2852);
/*     */   
/* 257 */   private static final Deque<Float> lineWidthState = Queues.newArrayDeque();
/* 258 */   private static final Deque<Float> pointSizeState = Queues.newArrayDeque();
/* 259 */   private static final Deque<Pair<Integer, Integer>> blendFancState = Queues.newArrayDeque();
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean enableStencilBuffer() {
/* 264 */     Framebuffer framebuffer = Minecraft.func_71410_x().func_147110_a();
/*     */     
/* 266 */     if (!framebuffer.isStencilEnabled() && OpenGlHelper.func_148822_b()) {
/*     */       
/* 268 */       framebuffer.enableStencil();
/* 269 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 273 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isEnableStencilBuffer() {
/* 279 */     return Minecraft.func_71410_x().func_147110_a().isStencilEnabled();
/*     */   }
/*     */ 
/*     */   
/*     */   public static Matrix4f translateAsMatrix(float x, float y, float z) {
/* 284 */     return TRSRTransformation.mul(new Vector3f(x, y, z), null, null, null);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Matrix4f rotateAsMatrix(float angle, float x, float y, float z) {
/* 289 */     Quat4f quat = new Quat4f(0.0F, 0.0F, 0.0F, 1.0F), t = new Quat4f();
/* 290 */     t.set(new AxisAngle4f(x, y, z, angle * 0.017453292F));
/* 291 */     quat.mul(t);
/* 292 */     return TRSRTransformation.mul(null, quat, null, null);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Matrix4f scaleAsMatrix(float x, float y, float z) {
/* 297 */     return TRSRTransformation.mul(null, null, new Vector3f(x, y, z), null);
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   static class ClearState
/*     */   {
/*     */     private ClearState() {}
/*     */ 
/*     */     
/* 307 */     public int stencil = 0;
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public enum Function
/*     */   {
/* 314 */     NEVER(512),
/* 315 */     LESS(513),
/* 316 */     EQUAL(514),
/* 317 */     LEQUAL(515),
/* 318 */     GREATER(516),
/* 319 */     NOTEQUAL(517),
/* 320 */     GEQUAL(518),
/* 321 */     ALWAYS(519);
/*     */     
/*     */     public final int func;
/*     */ 
/*     */     
/*     */     Function(int func) {
/* 327 */       this.func = func;
/*     */     }
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public enum StencilAction
/*     */   {
/* 334 */     KEEP(7680),
/* 335 */     ZERO(0),
/* 336 */     INCR(7682),
/* 337 */     INCR_WRAP(34055),
/* 338 */     DECR(7683),
/* 339 */     DECR_WRAP(34056),
/* 340 */     REPLACE(7681),
/* 341 */     INVERT(5386);
/*     */     
/*     */     public final int action;
/*     */ 
/*     */     
/*     */     StencilAction(int action) {
/* 347 */       this.action = action;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   static class StencilFunc
/*     */   {
/*     */     private StencilFunc() {}
/*     */ 
/*     */ 
/*     */     
/* 360 */     public int func = 519;
/* 361 */     public int ref = 0;
/* 362 */     public int mask = -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   static class StencilState
/*     */   {
/*     */     private StencilState() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 378 */     public GlUtil.BooleanState stencilTest = new GlUtil.BooleanState(2960);
/* 379 */     public GlUtil.StencilFunc func = new GlUtil.StencilFunc();
/* 380 */     public int mask = -1;
/* 381 */     public int fail = 7680;
/* 382 */     public int zfail = 7680;
/* 383 */     public int zpass = 7680;
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public enum RasterizeType
/*     */   {
/* 390 */     POINT(6912),
/* 391 */     LINE(6913),
/* 392 */     FILL(6914);
/*     */     
/*     */     public final int mode;
/*     */ 
/*     */     
/*     */     RasterizeType(int mode) {
/* 398 */       this.mode = mode;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   protected static class BooleanState
/*     */   {
/*     */     private final int capability;
/*     */     private boolean currentState;
/*     */     
/*     */     public BooleanState(int capabilityIn) {
/* 410 */       this.capability = capabilityIn;
/*     */     }
/*     */ 
/*     */     
/*     */     public void setDisabled() {
/* 415 */       setState(false);
/*     */     }
/*     */ 
/*     */     
/*     */     public void setEnabled() {
/* 420 */       setState(true);
/*     */     }
/*     */ 
/*     */     
/*     */     public void setState(boolean state) {
/* 425 */       if (state != this.currentState) {
/*     */         
/* 427 */         this.currentState = state;
/*     */         
/* 429 */         if (state) {
/*     */           
/* 431 */           GL11.glEnable(this.capability);
/*     */         }
/*     */         else {
/*     */           
/* 435 */           GL11.glDisable(this.capability);
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\__helper\client\renderer\GlUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */