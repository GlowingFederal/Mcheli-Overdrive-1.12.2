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
  
  public int getHoverState(boolean mouseOver) {
    return 0;
  }
  
  protected void mouseDragged(Minecraft mc, int x, int y) {
    if (isVisible()) {
      if (this.isMousePress) {
        this.currentSlider = (x - this.x + 4) / (this.width - 8);
        if (this.currentSlider < 0.0F)
          this.currentSlider = 0.0F; 
        if (this.currentSlider > 1.0F)
          this.currentSlider = 1.0F; 
        this.currentSlider = normalizeValue(denormalizeValue(this.currentSlider));
        updateDisplayString();
      } 
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      drawTexturedModalRect(this.x + (int)(this.currentSlider * (this.width - 8)), this.y, 0, 66, 4, 20);
      drawTexturedModalRect(this.x + (int)(this.currentSlider * (this.width - 8)) + 4, this.y, 196, 66, 4, 20);
      if (!MCH_Key.isKeyDown(-100))
        mouseReleased(x, y); 
    } 
  }
  
  public void updateDisplayString() {
    this.displayString = String.format(this.stringFormat, new Object[] { Float.valueOf(denormalizeValue(this.currentSlider)) });
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
    return MathHelper.clamp((snapToStepClamp(f) - this.valueMin) / (this.valueMax - this.valueMin), 0.0F, 1.0F);
  }
  
  public float denormalizeValue(float f) {
    return snapToStepClamp(this.valueMin + (this.valueMax - this.valueMin) * MathHelper.clamp(f, 0.0F, 1.0F));
  }
  
  public float snapToStepClamp(float f) {
    f = snapToStep(f);
    return MathHelper.clamp(f, this.valueMin, this.valueMax);
  }
  
  protected float snapToStep(float f) {
    if (this.valueStep > 0.0F)
      f = this.valueStep * Math.round(f / this.valueStep); 
    return f;
  }
  
  public boolean mousePressed(Minecraft mc, int x, int y) {
    if (super.mousePressed(mc, x, y)) {
      this.currentSlider = (x - this.x + 4) / (this.width - 8);
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
  
  public void mouseReleased(int mouseX, int mouseY) {
    this.isMousePress = false;
  }
}
