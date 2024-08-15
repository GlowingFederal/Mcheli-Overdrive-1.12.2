/*     */ package mcheli.gui;
/*     */ 
/*     */ import mcheli.MCH_Key;
/*     */ import mcheli.wrapper.W_GuiButton;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MCH_GuiSlider
/*     */   extends W_GuiButton
/*     */ {
/*     */   private float currentSlider;
/*     */   private boolean isMousePress;
/*     */   public String stringFormat;
/*  21 */   public float valueMin = 0.0F;
/*  22 */   public float valueMax = 1.0F;
/*  23 */   public float valueStep = 0.1F;
/*     */ 
/*     */ 
/*     */   
/*     */   public MCH_GuiSlider(int gui_id, int posX, int posY, int sliderWidth, int sliderHeight, String string_format, float defaultSliderPos, float minVal, float maxVal, float step) {
/*  28 */     super(gui_id, posX, posY, sliderWidth, sliderHeight, "");
/*     */     
/*  30 */     this.stringFormat = string_format;
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
/*     */   
/*     */   protected void func_146119_b(Minecraft mc, int x, int y) {
/*  47 */     if (isVisible()) {
/*     */       
/*  49 */       if (this.isMousePress) {
/*     */         
/*  51 */         this.currentSlider = (x - this.field_146128_h + 4) / (this.field_146120_f - 8);
/*     */         
/*  53 */         if (this.currentSlider < 0.0F)
/*     */         {
/*  55 */           this.currentSlider = 0.0F;
/*     */         }
/*     */         
/*  58 */         if (this.currentSlider > 1.0F)
/*     */         {
/*  60 */           this.currentSlider = 1.0F;
/*     */         }
/*     */         
/*  63 */         this.currentSlider = normalizeValue(denormalizeValue(this.currentSlider));
/*     */         
/*  65 */         updateDisplayString();
/*     */       } 
/*     */       
/*  68 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*  69 */       func_73729_b(this.field_146128_h + (int)(this.currentSlider * (this.field_146120_f - 8)), this.field_146129_i, 0, 66, 4, 20);
/*  70 */       func_73729_b(this.field_146128_h + (int)(this.currentSlider * (this.field_146120_f - 8)) + 4, this.field_146129_i, 196, 66, 4, 20);
/*     */       
/*  72 */       if (!MCH_Key.isKeyDown(-100))
/*     */       {
/*  74 */         func_146118_a(x, y);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateDisplayString() {
/*  81 */     this.field_146126_j = String.format(this.stringFormat, new Object[] {
/*     */           
/*  83 */           Float.valueOf(denormalizeValue(this.currentSlider))
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSliderValue(float f) {
/*  89 */     this.currentSlider = normalizeValue(f);
/*  90 */     updateDisplayString();
/*     */   }
/*     */ 
/*     */   
/*     */   public float getSliderValue() {
/*  95 */     return denormalizeValue(this.currentSlider);
/*     */   }
/*     */ 
/*     */   
/*     */   public float getSliderValueInt(int digit) {
/* 100 */     int d = 1;
/* 101 */     while (digit > 0) {
/*     */       
/* 103 */       d *= 10;
/* 104 */       digit--;
/*     */     } 
/* 106 */     int n = (int)(denormalizeValue(this.currentSlider) * d);
/* 107 */     return (n / d);
/*     */   }
/*     */ 
/*     */   
/*     */   public float normalizeValue(float f) {
/* 112 */     return MathHelper.func_76131_a((snapToStepClamp(f) - this.valueMin) / (this.valueMax - this.valueMin), 0.0F, 1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public float denormalizeValue(float f) {
/* 117 */     return snapToStepClamp(this.valueMin + (this.valueMax - this.valueMin) * MathHelper.func_76131_a(f, 0.0F, 1.0F));
/*     */   }
/*     */ 
/*     */   
/*     */   public float snapToStepClamp(float f) {
/* 122 */     f = snapToStep(f);
/* 123 */     return MathHelper.func_76131_a(f, this.valueMin, this.valueMax);
/*     */   }
/*     */ 
/*     */   
/*     */   protected float snapToStep(float f) {
/* 128 */     if (this.valueStep > 0.0F)
/*     */     {
/* 130 */       f = this.valueStep * Math.round(f / this.valueStep);
/*     */     }
/*     */     
/* 133 */     return f;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_146116_c(Minecraft mc, int x, int y) {
/* 139 */     if (super.func_146116_c(mc, x, y)) {
/*     */       
/* 141 */       this.currentSlider = (x - this.field_146128_h + 4) / (this.field_146120_f - 8);
/*     */       
/* 143 */       if (this.currentSlider < 0.0F)
/*     */       {
/* 145 */         this.currentSlider = 0.0F;
/*     */       }
/*     */       
/* 148 */       if (this.currentSlider > 1.0F)
/*     */       {
/* 150 */         this.currentSlider = 1.0F;
/*     */       }
/*     */       
/* 153 */       updateDisplayString();
/* 154 */       this.isMousePress = true;
/* 155 */       return true;
/*     */     } 
/*     */     
/* 158 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_146118_a(int mouseX, int mouseY) {
/* 164 */     this.isMousePress = false;
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\gui\MCH_GuiSlider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */