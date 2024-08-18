package mcheli.gui;

import mcheli.MCH_Key;
import mcheli.wrapper.W_GuiButton;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;

public class MCH_GuiSliderVertical extends W_GuiButton {
  private float currentSlider;
  
  private boolean isMousePress;
  
  public float valueMin = 0.0F;
  
  public float valueMax = 1.0F;
  
  public float valueStep = 0.1F;
  
  public MCH_GuiSliderVertical(int gui_id, int posX, int posY, int sliderWidth, int sliderHeight, String string, float defaultSliderPos, float minVal, float maxVal, float step) {
    super(gui_id, posX, posY, sliderWidth, sliderHeight, string);
    this.valueMin = minVal;
    this.valueMax = maxVal;
    this.valueStep = step;
    setSliderValue(defaultSliderPos);
  }
  
  public int func_146114_a(boolean mouseOver) {
    return 0;
  }
  
  public void scrollUp(float a) {
    if (isVisible())
      if (!this.isMousePress)
        setSliderValue(getSliderValue() + this.valueStep * a);  
  }
  
  public void scrollDown(float a) {
    if (isVisible())
      if (!this.isMousePress)
        setSliderValue(getSliderValue() - this.valueStep * a);  
  }
  
  protected void func_146119_b(Minecraft mc, int x, int y) {
    if (isVisible()) {
      if (this.isMousePress) {
        this.currentSlider = (y - this.field_146129_i + 4) / (this.field_146121_g - 8);
        if (this.currentSlider < 0.0F)
          this.currentSlider = 0.0F; 
        if (this.currentSlider > 1.0F)
          this.currentSlider = 1.0F; 
        this.currentSlider = normalizeValue(denormalizeValue(this.currentSlider));
      } 
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      func_73729_b(this.field_146128_h, this.field_146129_i + (int)(this.currentSlider * (this.field_146121_g - 8)), 66, 0, 20, 4);
      func_73729_b(this.field_146128_h, this.field_146129_i + (int)(this.currentSlider * (this.field_146121_g - 8)) + 4, 66, 196, 20, 4);
      if (!MCH_Key.isKeyDown(-100))
        func_146118_a(x, y); 
    } 
  }
  
  public void setSliderValue(float f) {
    this.currentSlider = normalizeValue(f);
  }
  
  public float getSliderValue() {
    return denormalizeValue(this.currentSlider);
  }
  
  public float getSliderValueInt(int digit) {
    int d = 1;
    while (digit > 0) {
      d *= 10;
      digit--;
    } 
    int n = (int)(denormalizeValue(this.currentSlider) * d);
    return (n / d);
  }
  
  public float normalizeValue(float f) {
    return MathHelper.func_76131_a((snapToStepClamp(f) - this.valueMin) / (this.valueMax - this.valueMin), 0.0F, 1.0F);
  }
  
  public float denormalizeValue(float f) {
    return snapToStepClamp(this.valueMin + (this.valueMax - this.valueMin) * MathHelper.func_76131_a(f, 0.0F, 1.0F));
  }
  
  public float snapToStepClamp(float f) {
    f = snapToStep(f);
    return MathHelper.func_76131_a(f, this.valueMin, this.valueMax);
  }
  
  protected float snapToStep(float f) {
    if (this.valueStep > 0.0F)
      f = this.valueStep * Math.round(f / this.valueStep); 
    return f;
  }
  
  public boolean func_146116_c(Minecraft mc, int x, int y) {
    if (super.func_146116_c(mc, x, y)) {
      this.currentSlider = (y - this.field_146129_i + 4) / (this.field_146121_g - 8);
      if (this.currentSlider < 0.0F)
        this.currentSlider = 0.0F; 
      if (this.currentSlider > 1.0F)
        this.currentSlider = 1.0F; 
      this.isMousePress = true;
      return true;
    } 
    return false;
  }
  
  public void func_146118_a(int mouseX, int mouseY) {
    this.isMousePress = false;
  }
  
  public void func_191745_a(Minecraft mc, int x, int y, float partialTicks) {
    if (isVisible()) {
      FontRenderer fontrenderer = mc.field_71466_p;
      mc.func_110434_K().func_110577_a(new ResourceLocation("mcheli", "textures/gui/widgets.png"));
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      setOnMouseOver((x >= this.field_146128_h && y >= this.field_146129_i && x < this.field_146128_h + this.field_146120_f && y < this.field_146129_i + this.field_146121_g));
      int k = func_146114_a(isOnMouseOver());
      enableBlend();
      func_73729_b(this.field_146128_h, this.field_146129_i, 46 + k * 20, 0, this.field_146120_f, this.field_146121_g / 2);
      func_73729_b(this.field_146128_h, this.field_146129_i + this.field_146121_g / 2, 46 + k * 20, 200 - this.field_146121_g / 2, this.field_146120_f, this.field_146121_g / 2);
      func_146119_b(mc, x, y);
      int l = 14737632;
      if (this.packedFGColour != 0) {
        l = this.packedFGColour;
      } else if (!this.field_146124_l) {
        l = 10526880;
      } else if (isOnMouseOver()) {
        l = 16777120;
      } 
      func_73732_a(fontrenderer, this.field_146126_j, this.field_146128_h + this.field_146120_f / 2, this.field_146129_i + (this.field_146121_g - 8) / 2, l);
      mc.func_110434_K().func_110577_a(field_146122_a);
    } 
  }
}
