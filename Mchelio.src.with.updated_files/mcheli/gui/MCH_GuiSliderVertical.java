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
  
  public int getHoverState(boolean mouseOver) {
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
  
  protected void mouseDragged(Minecraft mc, int x, int y) {
    if (isVisible()) {
      if (this.isMousePress) {
        this.currentSlider = (y - this.yPosition + 4) / (this.height - 8);
        if (this.currentSlider < 0.0F)
          this.currentSlider = 0.0F; 
        if (this.currentSlider > 1.0F)
          this.currentSlider = 1.0F; 
        this.currentSlider = normalizeValue(denormalizeValue(this.currentSlider));
      } 
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      drawTexturedModalRect(this.xPosition, this.yPosition + (int)(this.currentSlider * (this.height - 8)), 66, 0, 20, 4);
      drawTexturedModalRect(this.xPosition, this.yPosition + (int)(this.currentSlider * (this.height - 8)) + 4, 66, 196, 20, 4);
      if (!MCH_Key.isKeyDown(-100))
        mouseReleased(x, y); 
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
      this.currentSlider = (y - this.yPosition + 4) / (this.height - 8);
      if (this.currentSlider < 0.0F)
        this.currentSlider = 0.0F; 
      if (this.currentSlider > 1.0F)
        this.currentSlider = 1.0F; 
      this.isMousePress = true;
      return true;
    } 
    return false;
  }
  
  public void mouseReleased(int mouseX, int mouseY) {
    this.isMousePress = false;
  }
  
  public void func_191745_a(Minecraft mc, int x, int y, float partialTicks) {
    if (isVisible()) {
      FontRenderer fontrenderer = mc.fontRendererObj;
      mc.getTextureManager().bindTexture(new ResourceLocation("mcheli", "textures/gui/widgets.png"));
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      setOnMouseOver((x >= this.xPosition && y >= this.yPosition && x < this.xPosition + this.width && y < this.yPosition + this.height));
      int k = getHoverState(isOnMouseOver());
      enableBlend();
      drawTexturedModalRect(this.xPosition, this.yPosition, 46 + k * 20, 0, this.width, this.height / 2);
      drawTexturedModalRect(this.xPosition, this.yPosition + this.height / 2, 46 + k * 20, 200 - this.height / 2, this.width, this.height / 2);
      mouseDragged(mc, x, y);
      int l = 14737632;
      if (this.packedFGColour != 0) {
        l = this.packedFGColour;
      } else if (!this.enabled) {
        l = 10526880;
      } else if (isOnMouseOver()) {
        l = 16777120;
      } 
      drawCenteredString(fontrenderer, this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, l);
      mc.getTextureManager().bindTexture(BUTTON_TEXTURES);
    } 
  }
}
