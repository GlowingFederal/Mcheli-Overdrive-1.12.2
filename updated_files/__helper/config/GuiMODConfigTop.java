package mcheli.__helper.config;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import mcheli.__helper.addon.LegacyPackAssistant;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.client.config.GuiButtonExt;
import net.minecraftforge.fml.client.config.GuiUtils;
import net.minecraftforge.fml.client.config.HoverChecker;

public class GuiMODConfigTop extends GuiScreen {
  public final GuiScreen parentScreen;
  
  private HoverChecker assistHoverChecker;
  
  private int genAnimationTicks;
  
  public GuiMODConfigTop(GuiScreen parentScreen) {
    this.parentScreen = parentScreen;
  }
  
  public void initGui() {
    String doneText = I18n.format("gui.done", new Object[0]);
    int doneWidth = Math.max(this.mc.fontRendererObj.getStringWidth(doneText) + 20, 100);
    addButton((GuiButton)new GuiButtonExt(1, this.width - 20 - doneWidth, this.height - 29, doneWidth, 20, doneText));
    GuiButtonExt configBtn = new GuiButtonExt(16, this.width / 2 - 100, 32, 200, 20, "Game config (in progress...)");
    configBtn.enabled = false;
    GuiButtonExt assistBtn = new GuiButtonExt(16, this.width / 2 - 100, 64, 200, 20, I18n.format("gui.mcheli.generateLegacyAsisst", new Object[0]));
    addButton((GuiButton)configBtn);
    addButton((GuiButton)assistBtn);
    this.assistHoverChecker = new HoverChecker((GuiButton)assistBtn, 800);
  }
  
  public void drawScreen(int mouseX, int mouseY, float partialTicks) {
    drawDefaultBackground();
    drawCenteredString(this.fontRendererObj, "MC Helicopter MOD Config", this.width / 2, 8, 16777215);
    if (this.genAnimationTicks > 0) {
      float f = (this.genAnimationTicks > 10) ? 1.0F : (this.genAnimationTicks / 10.0F);
      drawString(this.fontRendererObj, "Generate Done!", this.width / 2 - 100, 92, 0xFF2222 | (int)(255.0F * f) << 24);
    } 
    super.drawScreen(mouseX, mouseY, partialTicks);
    if (this.assistHoverChecker.checkHover(mouseX, mouseY))
      drawToolTip(Arrays.asList(I18n.format("gui.mcheli.generateLegacyAsisst.desc", new Object[0]).split("\n")), mouseX, mouseY); 
  }
  
  public void updateScreen() {
    super.updateScreen();
    if (this.genAnimationTicks > 0)
      this.genAnimationTicks--; 
  }
  
  protected void actionPerformed(GuiButton button) throws IOException {
    if (button.id == 1) {
      this.mc.displayGuiScreen(this.parentScreen);
    } else if (button.id == 16) {
      LegacyPackAssistant.generateDirectoryPack();
      this.genAnimationTicks = 60;
    } 
  }
  
  protected void keyTyped(char typedChar, int keyCode) throws IOException {
    if (keyCode == 1) {
      this.mc.displayGuiScreen(this.parentScreen);
    } else {
      super.keyTyped(typedChar, keyCode);
    } 
  }
  
  private void drawToolTip(List<String> stringList, int x, int y) {
    GuiUtils.drawHoveringText(stringList, x, y, this.width, this.height, 300, this.fontRendererObj);
  }
}
