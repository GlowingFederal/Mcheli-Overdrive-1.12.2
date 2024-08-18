package mcheli.gui;

import mcheli.MCH_Key;
import mcheli.wrapper.W_GuiButton;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;

public class MCH_GuiSlider extends W_GuiButton {
  private float currentSlider;
  
  private boolean isMousePress;
  
  public String stringFormat;
  
  public float valueMin = 0.0F;
  
  public float valueMax = 1.0F;
  
  public float valueStep = 0.1F;
  
  public MCH_GuiSlider(int gui_id, int posX, int posY, int sliderWidth, int sliderHeight, String string_format, float defaultSliderPos, float minVal, float maxVal, float step) {
    super(gui_id, posX, posY, sliderWidth, sliderHeight, "");
    this.stringFormat = string_format;
    this.valueMin = minVal;
    this.valueMax = maxVal;
    this.valueStep = step;
    setSliderValue(defaultSliderPos);
  }
  
  public int func_146114_a(boolean mouseOver) {
    return 0;
  }
  
  protected void func_146119_b(Minecraft mc, int x, int y) {
    if (isVisible()) {
      if (this.isMousePress) {
        this.currentSlider = (x - this.field_146128_h + 4) / (this.field_146120_f - 8);
        if (this.currentSlider < 0.0F)
          this.currentSlider = 0.0F; 
        if (this.currentSlider > 1.0F)
          this.currentSlider = 1.0F; 
        this.currentSlider = normalizeValue(denormalizeValue(this.currentSlider));
        updateDisplayString();
      } 
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      func_73729_b(this.field_146128_h + (int)(this.currentSlider * (this.field_146120_f - 8)), this.field_146129_i, 0, 66, 4, 20);
      func_73729_b(this.field_146128_h + (int)(this.currentSlider * (this.field_146120_f - 8)) + 4, this.field_146129_i, 196, 66, 4, 20);
      if (!MCH_Key.isKeyDown(-100))
        func_146118_a(x, y); 
    } 
  }
  
  public void updateDisplayString() {
    this.field_146126_j = String.format(this.stringFormat, new Object[] { Float.valueOf(denormalizeValue(this.currentSlider)) });
  }
  
  public void setSliderValue(float f) {
    this.currentSlider = normalizeValue(f);
    updateDisplayString();
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
      this.currentSlider = (x - this.field_146128_h + 4) / (this.field_146120_f - 8);
      if (this.currentSlider < 0.0F)
        this.currentSlider = 0.0F; 
      if (this.currentSlider > 1.0F)
        this.currentSlider = 1.0F; 
      updateDisplayString();
      this.isMousePress = true;
      return true;
    } 
    return false;
  }
  
  public void func_146118_a(int mouseX, int mouseY) {
    this.isMousePress = false;
  }
}
