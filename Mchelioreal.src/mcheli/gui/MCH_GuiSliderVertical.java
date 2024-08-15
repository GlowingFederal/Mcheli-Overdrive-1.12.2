/*     */ package mcheli.gui;
/*     */ 
/*     */ import mcheli.MCH_Key;
/*     */ import mcheli.wrapper.W_GuiButton;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.FontRenderer;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MCH_GuiSliderVertical
/*     */   extends W_GuiButton
/*     */ {
/*     */   private float currentSlider;
/*     */   private boolean isMousePress;
/*  22 */   public float valueMin = 0.0F;
/*  23 */   public float valueMax = 1.0F;
/*  24 */   public float valueStep = 0.1F;
/*     */ 
/*     */ 
/*     */   
/*     */   public MCH_GuiSliderVertical(int gui_id, int posX, int posY, int sliderWidth, int sliderHeight, String string, float defaultSliderPos, float minVal, float maxVal, float step) {
/*  29 */     super(gui_id, posX, posY, sliderWidth, sliderHeight, string);
/*     */     
/*  31 */     this.valueMin = minVal;
/*  32 */     this.valueMax = maxVal;
/*  33 */     this.valueStep = step;
/*     */     
/*  35 */     setSliderValue(defaultSliderPos);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int func_146114_a(boolean mouseOver) {
/*  41 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void scrollUp(float a) {
/*  46 */     if (isVisible())
/*     */     {
/*  48 */       if (!this.isMousePress)
/*     */       {
/*  50 */         setSliderValue(getSliderValue() + this.valueStep * a);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void scrollDown(float a) {
/*  57 */     if (isVisible())
/*     */     {
/*  59 */       if (!this.isMousePress)
/*     */       {
/*  61 */         setSliderValue(getSliderValue() - this.valueStep * a);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_146119_b(Minecraft mc, int x, int y) {
/*  69 */     if (isVisible()) {
/*     */       
/*  71 */       if (this.isMousePress) {
/*     */         
/*  73 */         this.currentSlider = (y - this.field_146129_i + 4) / (this.field_146121_g - 8);
/*     */         
/*  75 */         if (this.currentSlider < 0.0F)
/*     */         {
/*  77 */           this.currentSlider = 0.0F;
/*     */         }
/*     */         
/*  80 */         if (this.currentSlider > 1.0F)
/*     */         {
/*  82 */           this.currentSlider = 1.0F;
/*     */         }
/*     */         
/*  85 */         this.currentSlider = normalizeValue(denormalizeValue(this.currentSlider));
/*     */       } 
/*     */       
/*  88 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*     */       
/*  90 */       func_73729_b(this.field_146128_h, this.field_146129_i + (int)(this.currentSlider * (this.field_146121_g - 8)), 66, 0, 20, 4);
/*  91 */       func_73729_b(this.field_146128_h, this.field_146129_i + (int)(this.currentSlider * (this.field_146121_g - 8)) + 4, 66, 196, 20, 4);
/*     */       
/*  93 */       if (!MCH_Key.isKeyDown(-100))
/*     */       {
/*  95 */         func_146118_a(x, y);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSliderValue(float f) {
/* 102 */     this.currentSlider = normalizeValue(f);
/*     */   }
/*     */ 
/*     */   
/*     */   public float getSliderValue() {
/* 107 */     return denormalizeValue(this.currentSlider);
/*     */   }
/*     */ 
/*     */   
/*     */   public float getSliderValueInt(int digit) {
/* 112 */     int d = 1;
/* 113 */     while (digit > 0) {
/*     */       
/* 115 */       d *= 10;
/* 116 */       digit--;
/*     */     } 
/* 118 */     int n = (int)(denormalizeValue(this.currentSlider) * d);
/* 119 */     return (n / d);
/*     */   }
/*     */ 
/*     */   
/*     */   public float normalizeValue(float f) {
/* 124 */     return MathHelper.func_76131_a((snapToStepClamp(f) - this.valueMin) / (this.valueMax - this.valueMin), 0.0F, 1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public float denormalizeValue(float f) {
/* 129 */     return snapToStepClamp(this.valueMin + (this.valueMax - this.valueMin) * MathHelper.func_76131_a(f, 0.0F, 1.0F));
/*     */   }
/*     */ 
/*     */   
/*     */   public float snapToStepClamp(float f) {
/* 134 */     f = snapToStep(f);
/* 135 */     return MathHelper.func_76131_a(f, this.valueMin, this.valueMax);
/*     */   }
/*     */ 
/*     */   
/*     */   protected float snapToStep(float f) {
/* 140 */     if (this.valueStep > 0.0F)
/*     */     {
/* 142 */       f = this.valueStep * Math.round(f / this.valueStep);
/*     */     }
/*     */     
/* 145 */     return f;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_146116_c(Minecraft mc, int x, int y) {
/* 151 */     if (super.func_146116_c(mc, x, y)) {
/*     */       
/* 153 */       this.currentSlider = (y - this.field_146129_i + 4) / (this.field_146121_g - 8);
/*     */       
/* 155 */       if (this.currentSlider < 0.0F)
/*     */       {
/* 157 */         this.currentSlider = 0.0F;
/*     */       }
/*     */       
/* 160 */       if (this.currentSlider > 1.0F)
/*     */       {
/* 162 */         this.currentSlider = 1.0F;
/*     */       }
/*     */       
/* 165 */       this.isMousePress = true;
/* 166 */       return true;
/*     */     } 
/*     */     
/* 169 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_146118_a(int mouseX, int mouseY) {
/* 175 */     this.isMousePress = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_191745_a(Minecraft mc, int x, int y, float partialTicks) {
/* 182 */     if (isVisible()) {
/*     */       
/* 184 */       FontRenderer fontrenderer = mc.field_71466_p;
/* 185 */       mc.func_110434_K().func_110577_a(new ResourceLocation("mcheli", "textures/gui/widgets.png"));
/* 186 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 187 */       setOnMouseOver((x >= this.field_146128_h && y >= this.field_146129_i && x < this.field_146128_h + this.field_146120_f && y < this.field_146129_i + this.field_146121_g));
/* 188 */       int k = func_146114_a(isOnMouseOver());
/* 189 */       enableBlend();
/* 190 */       func_73729_b(this.field_146128_h, this.field_146129_i, 46 + k * 20, 0, this.field_146120_f, this.field_146121_g / 2);
/* 191 */       func_73729_b(this.field_146128_h, this.field_146129_i + this.field_146121_g / 2, 46 + k * 20, 200 - this.field_146121_g / 2, this.field_146120_f, this.field_146121_g / 2);
/*     */ 
/*     */       
/* 194 */       func_146119_b(mc, x, y);
/* 195 */       int l = 14737632;
/*     */       
/* 197 */       if (this.packedFGColour != 0) {
/*     */         
/* 199 */         l = this.packedFGColour;
/*     */       }
/* 201 */       else if (!this.field_146124_l) {
/*     */         
/* 203 */         l = 10526880;
/*     */       }
/* 205 */       else if (isOnMouseOver()) {
/*     */         
/* 207 */         l = 16777120;
/*     */       } 
/*     */       
/* 210 */       func_73732_a(fontrenderer, this.field_146126_j, this.field_146128_h + this.field_146120_f / 2, this.field_146129_i + (this.field_146121_g - 8) / 2, l);
/*     */ 
/*     */       
/* 213 */       mc.func_110434_K().func_110577_a(field_146122_a);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\gui\MCH_GuiSliderVertical.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */