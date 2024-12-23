package mcheli.multiplay;

import java.io.IOException;
import mcheli.wrapper.W_McClient;
import mcheli.wrapper.W_ScaledResolution;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextFormatting;
import org.lwjgl.opengl.GL11;

public class MCH_GuiScoreboard_CreateTeam extends MCH_GuiScoreboard_Base {
  private GuiButton buttonCreateTeamOK;
  
  private GuiButton buttonCreateTeamFF;
  
  private GuiTextField editCreateTeamName;
  
  private static boolean friendlyFire = true;
  
  private int lastTeamColor = 0;
  
  private static final String[] colorNames = new String[] { 
      "RESET", "BLACK", "DARK_BLUE", "DARK_GREEN", "DARK_AQUA", "DARK_RED", "DARK_PURPLE", "GOLD", "GRAY", "DARK_GRAY", 
      "BLUE", "GREEN", "AQUA", "RED", "LIGHT_PURPLE", "YELLOW" };
  
  public MCH_GuiScoreboard_CreateTeam(MCH_IGuiScoreboard switcher, EntityPlayer player) {
    super(switcher, player);
  }
  
  public void initGui() {
    super.initGui();
    W_ScaledResolution w_ScaledResolution = new W_ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
    int factor = (w_ScaledResolution.getScaleFactor() > 0) ? w_ScaledResolution.getScaleFactor() : 1;
    this.guiLeft = 0;
    this.guiTop = 0;
    int x = this.mc.displayWidth / 2 / factor;
    int y = this.mc.displayHeight / 2 / factor;
    GuiButton buttonCTNextC = new GuiButton(576, x + 40, y - 20, 40, 20, ">");
    GuiButton buttonCTPrevC = new GuiButton(577, x - 80, y - 20, 40, 20, "<");
    this.buttonCreateTeamFF = new GuiButton(560, x - 80, y + 20, 160, 20, "");
    this.buttonCreateTeamOK = new GuiButton(528, x - 80, y + 60, 80, 20, "OK");
    GuiButton buttonCTCancel = new GuiButton(544, x + 0, y + 60, 80, 20, "Cancel");
    this.editCreateTeamName = new GuiTextField(599, this.fontRendererObj, x - 80, y - 55, 160, 20);
    this.editCreateTeamName.setText("");
    this.editCreateTeamName.setTextColor(-1);
    this.editCreateTeamName.setMaxStringLength(16);
    this.editCreateTeamName.setFocused(true);
    this.listGui.add(buttonCTNextC);
    this.listGui.add(buttonCTPrevC);
    this.listGui.add(this.buttonCreateTeamFF);
    this.listGui.add(this.buttonCreateTeamOK);
    this.listGui.add(buttonCTCancel);
    this.listGui.add(this.editCreateTeamName);
  }
  
  public void updateScreen() {
    String teamName = this.editCreateTeamName.getText();
    this.buttonCreateTeamOK.enabled = (teamName.length() > 0 && teamName.length() <= 16);
    this.editCreateTeamName.updateCursorCounter();
    this.buttonCreateTeamFF.displayString = "Friendly Fire : " + (friendlyFire ? "ON" : "OFF");
  }
  
  public void acviveScreen() {
    this.editCreateTeamName.setText("");
    this.editCreateTeamName.setFocused(true);
  }
  
  protected void keyTyped(char c, int code) throws IOException {
    if (code == 1) {
      switchScreen(MCH_GuiScoreboard_Base.SCREEN_ID.MAIN);
    } else {
      this.editCreateTeamName.textboxKeyTyped(c, code);
    } 
  }
  
  protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
    this.editCreateTeamName.mouseClicked(mouseX, mouseY, mouseButton);
    super.mouseClicked(mouseX, mouseY, mouseButton);
  }
  
  protected void actionPerformed(GuiButton btn) throws IOException {
    if (btn != null && btn.enabled) {
      String teamName;
      switch (btn.id) {
        case 528:
          teamName = this.editCreateTeamName.getText();
          if (teamName.length() > 0 && teamName.length() <= 16) {
            MCH_PacketIndMultiplayCommand.send(768, "scoreboard teams add " + teamName);
            MCH_PacketIndMultiplayCommand.send(768, "scoreboard teams option " + teamName + " color " + colorNames[this.lastTeamColor]);
            MCH_PacketIndMultiplayCommand.send(768, "scoreboard teams option " + teamName + " friendlyfire " + friendlyFire);
          } 
          switchScreen(MCH_GuiScoreboard_Base.SCREEN_ID.MAIN);
          break;
        case 544:
          switchScreen(MCH_GuiScoreboard_Base.SCREEN_ID.MAIN);
          break;
        case 560:
          friendlyFire = !friendlyFire;
          break;
        case 576:
          this.lastTeamColor++;
          if (this.lastTeamColor >= colorNames.length)
            this.lastTeamColor = 0; 
          break;
        case 577:
          this.lastTeamColor--;
          if (this.lastTeamColor < 0)
            this.lastTeamColor = colorNames.length - 1; 
          break;
      } 
    } 
  }
  
  protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
    drawList(this.mc, this.fontRendererObj, true);
    W_ScaledResolution w_ScaledResolution = new W_ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
    int factor = (w_ScaledResolution.getScaleFactor() > 0) ? w_ScaledResolution.getScaleFactor() : 1;
    W_McClient.MOD_bindTexture("textures/gui/mp_new_team.png");
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    int x = (this.mc.displayWidth / factor - 222) / 2;
    int y = (this.mc.displayHeight / factor - 200) / 2;
    drawTexturedModalRect(x, y, 0, 0, 222, 200);
    x = this.mc.displayWidth / 2 / factor;
    y = this.mc.displayHeight / 2 / factor;
    drawCenteredString("Create team", x, y - 85, -1);
    drawCenteredString("Team name", x, y - 70, -1);
    TextFormatting ecf = TextFormatting.getValueByName(colorNames[this.lastTeamColor]);
    drawCenteredString(ecf + "Team Color" + ecf, x, y - 13, -1);
    this.editCreateTeamName.drawTextBox();
  }
}
