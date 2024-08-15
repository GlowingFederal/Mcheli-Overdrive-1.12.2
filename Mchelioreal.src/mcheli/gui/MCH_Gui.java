/*     */ package mcheli.gui;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Random;
/*     */ import mcheli.wrapper.W_McClient;
/*     */ import mcheli.wrapper.W_ScaledResolution;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.renderer.BufferBuilder;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import org.lwjgl.opengl.GL11;
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
/*     */ public abstract class MCH_Gui
/*     */   extends GuiScreen
/*     */ {
/*  29 */   protected int centerX = 0;
/*  30 */   protected int centerY = 0;
/*  31 */   protected Random rand = new Random();
/*     */   
/*     */   protected float smoothCamPartialTicks;
/*     */   public static int scaleFactor;
/*     */   
/*     */   public MCH_Gui(Minecraft minecraft) {
/*  37 */     this.field_146297_k = minecraft;
/*  38 */     this.smoothCamPartialTicks = 0.0F;
/*  39 */     this.field_73735_i = -110.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_73866_w_() {
/*  45 */     super.func_73866_w_();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_73868_f() {
/*  51 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onTick() {}
/*     */ 
/*     */   
/*     */   public abstract boolean isDrawGui(EntityPlayer paramEntityPlayer);
/*     */ 
/*     */   
/*     */   public abstract void drawGui(EntityPlayer paramEntityPlayer, boolean paramBoolean);
/*     */ 
/*     */   
/*     */   public void func_73863_a(int par1, int par2, float partialTicks) {
/*  65 */     this.smoothCamPartialTicks = partialTicks;
/*     */     
/*  67 */     W_ScaledResolution w_ScaledResolution = new W_ScaledResolution(this.field_146297_k, this.field_146297_k.field_71443_c, this.field_146297_k.field_71440_d);
/*     */     
/*  69 */     scaleFactor = w_ScaledResolution.func_78325_e();
/*     */     
/*  71 */     if (!this.field_146297_k.field_71474_y.field_74319_N) {
/*     */       
/*  73 */       this.field_146294_l = this.field_146297_k.field_71443_c / scaleFactor;
/*  74 */       this.field_146295_m = this.field_146297_k.field_71440_d / scaleFactor;
/*  75 */       this.centerX = this.field_146294_l / 2;
/*  76 */       this.centerY = this.field_146295_m / 2;
/*     */       
/*  78 */       GL11.glPushMatrix();
/*  79 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*     */       
/*  81 */       if (this.field_146297_k.field_71439_g != null)
/*     */       {
/*  83 */         drawGui((EntityPlayer)this.field_146297_k.field_71439_g, (this.field_146297_k.field_71474_y.field_74320_O != 0));
/*     */       }
/*  85 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*  86 */       GL11.glPopMatrix();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawTexturedModalRectRotate(double left, double top, double width, double height, double uLeft, double vTop, double uWidth, double vHeight, float rot) {
/*  93 */     GL11.glPushMatrix();
/*     */     
/*  95 */     GL11.glTranslated(left + width / 2.0D, top + height / 2.0D, 0.0D);
/*  96 */     GL11.glRotatef(rot, 0.0F, 0.0F, 1.0F);
/*     */     
/*  98 */     float f = 0.00390625F;
/*  99 */     Tessellator tessellator = Tessellator.func_178181_a();
/* 100 */     BufferBuilder builder = tessellator.func_178180_c();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 110 */     builder.func_181668_a(7, DefaultVertexFormats.field_181707_g);
/* 111 */     builder.func_181662_b(-width / 2.0D, height / 2.0D, this.field_73735_i).func_187315_a(uLeft * f, (vTop + vHeight) * f).func_181675_d();
/* 112 */     builder.func_181662_b(width / 2.0D, height / 2.0D, this.field_73735_i).func_187315_a((uLeft + uWidth) * f, (vTop + vHeight) * f)
/* 113 */       .func_181675_d();
/* 114 */     builder.func_181662_b(width / 2.0D, -height / 2.0D, this.field_73735_i).func_187315_a((uLeft + uWidth) * f, vTop * f).func_181675_d();
/* 115 */     builder.func_181662_b(-width / 2.0D, -height / 2.0D, this.field_73735_i).func_187315_a(uLeft * f, vTop * f).func_181675_d();
/* 116 */     tessellator.func_78381_a();
/*     */     
/* 118 */     GL11.glPopMatrix();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawTexturedRect(double left, double top, double width, double height, double uLeft, double vTop, double uWidth, double vHeight, double textureWidth, double textureHeight) {
/* 124 */     float fx = (float)(1.0D / textureWidth);
/* 125 */     float fy = (float)(1.0D / textureHeight);
/* 126 */     Tessellator tessellator = Tessellator.func_178181_a();
/* 127 */     BufferBuilder builder = tessellator.func_178180_c();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 134 */     builder.func_181668_a(7, DefaultVertexFormats.field_181707_g);
/* 135 */     builder.func_181662_b(left, top + height, this.field_73735_i).func_187315_a(uLeft * fx, (vTop + vHeight) * fy).func_181675_d();
/* 136 */     builder.func_181662_b(left + width, top + height, this.field_73735_i).func_187315_a((uLeft + uWidth) * fx, (vTop + vHeight) * fy)
/* 137 */       .func_181675_d();
/* 138 */     builder.func_181662_b(left + width, top, this.field_73735_i).func_187315_a((uLeft + uWidth) * fx, vTop * fy).func_181675_d();
/* 139 */     builder.func_181662_b(left, top, this.field_73735_i).func_187315_a(uLeft * fx, vTop * fy).func_181675_d();
/*     */     
/* 141 */     tessellator.func_78381_a();
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawLineStipple(double[] line, int color, int factor, int pattern) {
/* 146 */     GL11.glEnable(2852);
/* 147 */     GL11.glLineStipple(factor, (short)pattern);
/* 148 */     drawLine(line, color);
/* 149 */     GL11.glDisable(2852);
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawLine(double[] line, int color) {
/* 154 */     drawLine(line, color, 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawString(String s, int x, int y, int color) {
/* 159 */     func_73731_b(this.field_146297_k.field_71466_p, s, x, y, color);
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawDigit(String s, int x, int y, int interval, int color) {
/* 164 */     GL11.glEnable(3042);
/* 165 */     GL11.glColor4ub((byte)(color >> 16 & 0xFF), (byte)(color >> 8 & 0xFF), (byte)(color & 0xFF), (byte)(color >> 24 & 0xFF));
/*     */     
/* 167 */     int srcBlend = GL11.glGetInteger(3041);
/* 168 */     int dstBlend = GL11.glGetInteger(3040);
/* 169 */     GL11.glBlendFunc(770, 771);
/*     */     
/* 171 */     W_McClient.MOD_bindTexture("textures/gui/digit.png");
/* 172 */     for (int i = 0; i < s.length(); i++) {
/*     */       
/* 174 */       char c = s.charAt(i);
/* 175 */       if (c >= '0' && c <= '9')
/*     */       {
/* 177 */         func_73729_b(x + interval * i, y, 16 * (c - 48), 0, 16, 16);
/*     */       }
/* 179 */       if (c == '-')
/*     */       {
/* 181 */         func_73729_b(x + interval * i, y, 160, 0, 16, 16);
/*     */       }
/*     */     } 
/*     */     
/* 185 */     GL11.glBlendFunc(srcBlend, dstBlend);
/* 186 */     GL11.glDisable(3042);
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawCenteredString(String s, int x, int y, int color) {
/* 191 */     func_73732_a(this.field_146297_k.field_71466_p, s, x, y, color);
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawLine(double[] line, int color, int mode) {
/* 196 */     GL11.glPushMatrix();
/*     */     
/* 198 */     GL11.glEnable(3042);
/* 199 */     GL11.glDisable(3553);
/* 200 */     GL11.glBlendFunc(770, 771);
/* 201 */     GL11.glColor4ub((byte)(color >> 16 & 0xFF), (byte)(color >> 8 & 0xFF), (byte)(color >> 0 & 0xFF), (byte)(color >> 24 & 0xFF));
/*     */ 
/*     */     
/* 204 */     Tessellator tessellator = Tessellator.func_178181_a();
/* 205 */     BufferBuilder builder = tessellator.func_178180_c();
/*     */ 
/*     */     
/* 208 */     builder.func_181668_a(mode, DefaultVertexFormats.field_181705_e);
/* 209 */     for (int i = 0; i < line.length; i += 2)
/*     */     {
/*     */       
/* 212 */       builder.func_181662_b(line[i + 0], line[i + 1], this.field_73735_i).func_181675_d();
/*     */     }
/* 214 */     tessellator.func_78381_a();
/*     */     
/* 216 */     GL11.glEnable(3553);
/* 217 */     GL11.glDisable(3042);
/*     */     
/* 219 */     GL11.glColor4b((byte)-1, (byte)-1, (byte)-1, (byte)-1);
/* 220 */     GL11.glPopMatrix();
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawPoints(double[] points, int color, int pointWidth) {
/* 225 */     int prevWidth = GL11.glGetInteger(2833);
/*     */     
/* 227 */     GL11.glPushMatrix();
/*     */     
/* 229 */     GL11.glEnable(3042);
/* 230 */     GL11.glDisable(3553);
/* 231 */     GL11.glBlendFunc(770, 771);
/* 232 */     GL11.glColor4ub((byte)(color >> 16 & 0xFF), (byte)(color >> 8 & 0xFF), (byte)(color >> 0 & 0xFF), (byte)(color >> 24 & 0xFF));
/*     */ 
/*     */     
/* 235 */     GL11.glPointSize(pointWidth);
/* 236 */     Tessellator tessellator = Tessellator.func_178181_a();
/* 237 */     BufferBuilder builder = tessellator.func_178180_c();
/*     */ 
/*     */     
/* 240 */     builder.func_181668_a(0, DefaultVertexFormats.field_181705_e);
/*     */     
/* 242 */     for (int i = 0; i < points.length; i += 2)
/*     */     {
/*     */       
/* 245 */       builder.func_181662_b(points[i], points[i + 1], 0.0D).func_181675_d();
/*     */     }
/* 247 */     tessellator.func_78381_a();
/*     */     
/* 249 */     GL11.glEnable(3553);
/* 250 */     GL11.glDisable(3042);
/*     */     
/* 252 */     GL11.glPopMatrix();
/* 253 */     GL11.glColor4b((byte)-1, (byte)-1, (byte)-1, (byte)-1);
/* 254 */     GL11.glPointSize(prevWidth);
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawPoints(ArrayList<Double> points, int color, int pointWidth) {
/* 259 */     int prevWidth = GL11.glGetInteger(2833);
/*     */     
/* 261 */     GL11.glPushMatrix();
/*     */     
/* 263 */     GL11.glEnable(3042);
/* 264 */     GL11.glDisable(3553);
/* 265 */     GL11.glBlendFunc(770, 771);
/* 266 */     GL11.glColor4ub((byte)(color >> 16 & 0xFF), (byte)(color >> 8 & 0xFF), (byte)(color >> 0 & 0xFF), (byte)(color >> 24 & 0xFF));
/*     */     
/* 268 */     GL11.glPointSize(pointWidth);
/* 269 */     Tessellator tessellator = Tessellator.func_178181_a();
/* 270 */     BufferBuilder builder = tessellator.func_178180_c();
/*     */ 
/*     */     
/* 273 */     builder.func_181668_a(0, DefaultVertexFormats.field_181705_e);
/*     */     
/* 275 */     for (int i = 0; i < points.size(); i += 2)
/*     */     {
/*     */       
/* 278 */       builder.func_181662_b(((Double)points.get(i)).doubleValue(), ((Double)points.get(i + 1)).doubleValue(), 0.0D).func_181675_d();
/*     */     }
/* 280 */     tessellator.func_78381_a();
/*     */     
/* 282 */     GL11.glEnable(3553);
/* 283 */     GL11.glDisable(3042);
/*     */     
/* 285 */     GL11.glPopMatrix();
/* 286 */     GL11.glColor4b((byte)-1, (byte)-1, (byte)-1, (byte)-1);
/* 287 */     GL11.glPointSize(prevWidth);
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\gui\MCH_Gui.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */