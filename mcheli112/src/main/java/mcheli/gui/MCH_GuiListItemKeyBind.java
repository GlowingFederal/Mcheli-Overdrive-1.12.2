package mcheli.gui;

import mcheli.MCH_ConfigPrm;
import mcheli.MCH_KeyName;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

public class MCH_GuiListItemKeyBind extends MCH_GuiListItem {
  public String displayString;
  
  public GuiButton button;
  
  public GuiButton buttonReset;
  
  public int keycode;
  
  public final int defaultKeycode;
  
  public MCH_ConfigPrm config;
  
  public GuiButton lastPushButton;
  
  public MCH_GuiListItemKeyBind(int id, int idReset, int posX, String dispStr, MCH_ConfigPrm prm) {
    this.displayString = dispStr;
    this.defaultKeycode = prm.prmIntDefault;
    this.button = new GuiButton(id, posX + 160, 0, 70, 20, "");
    this.buttonReset = new GuiButton(idReset, posX + 240, 0, 40, 20, "Reset");
    this.config = prm;
    this.lastPushButton = null;
    setKeycode(prm.prmInt);
  }
  
  public void mouseReleased(int x, int y) {
    this.button.func_146118_a(x, y);
    this.buttonReset.func_146118_a(x, y);
  }
  
  public boolean mousePressed(Minecraft mc, int x, int y) {
    if (this.button.func_146116_c(mc, x, y)) {
      this.lastPushButton = this.button;
      return true;
    } 
    if (this.buttonReset.func_146116_c(mc, x, y)) {
      this.lastPushButton = this.buttonReset;
      return true;
    } 
    return false;
  }
  
  public void draw(Minecraft mc, int mouseX, int mouseY, int posX, int posY, float partialTicks) {
    int y = 6;
    this.button.func_73731_b(mc.field_71466_p, this.displayString, posX + 10, posY + y, -1);
    this.button.field_146129_i = posY;
    this.button.func_191745_a(mc, mouseX, mouseY, partialTicks);
    this.buttonReset.field_146124_l = (this.keycode != this.defaultKeycode);
    this.buttonReset.field_146129_i = posY;
    this.buttonReset.func_191745_a(mc, mouseX, mouseY, partialTicks);
  }
  
  public void applyKeycode() {
    this.config.setPrm(this.keycode);
  }
  
  public void resetKeycode() {
    setKeycode(this.defaultKeycode);
  }
  
  public void setKeycode(int k) {
    if (k != 0 && !MCH_KeyName.getDescOrName(k).isEmpty()) {
      this.keycode = k;
      this.button.field_146126_j = MCH_KeyName.getDescOrName(k);
    } 
  }
}
