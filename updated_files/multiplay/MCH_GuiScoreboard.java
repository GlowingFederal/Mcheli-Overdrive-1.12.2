package mcheli.multiplay;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import mcheli.wrapper.W_GuiButton;
import mcheli.wrapper.W_GuiContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class MCH_GuiScoreboard extends W_GuiContainer implements MCH_IGuiScoreboard {
  public final EntityPlayer thePlayer;
  
  private MCH_GuiScoreboard_Base.SCREEN_ID screenID;
  
  private Map<MCH_GuiScoreboard_Base.SCREEN_ID, MCH_GuiScoreboard_Base> listScreen;
  
  private int lastTeamNum = 0;
  
  public MCH_GuiScoreboard(EntityPlayer player) {
    super(new MCH_ContainerScoreboard(player));
    this.thePlayer = player;
  }
  
  public void initGui() {
    Keyboard.enableRepeatEvents(true);
    super.initGui();
    this.buttonList.clear();
    this.labelList.clear();
    this.guiLeft = 0;
    this.guiTop = 0;
    this.listScreen = new HashMap<>();
    this.listScreen.put(MCH_GuiScoreboard_Base.SCREEN_ID.MAIN, new MCH_GuiScoreboard_Main(this, this.thePlayer));
    this.listScreen.put(MCH_GuiScoreboard_Base.SCREEN_ID.CREATE_TEAM, new MCH_GuiScoreboard_CreateTeam(this, this.thePlayer));
    for (MCH_GuiScoreboard_Base s : this.listScreen.values())
      s.initGui(this.buttonList, (GuiScreen)this); 
    this.lastTeamNum = this.mc.world.getScoreboard().getTeams().size();
    switchScreen(MCH_GuiScoreboard_Base.SCREEN_ID.MAIN);
  }
  
  public void updateScreen() {
    super.updateScreen();
    int nowTeamNum = this.mc.world.getScoreboard().getTeams().size();
    if (this.lastTeamNum != nowTeamNum) {
      this.lastTeamNum = nowTeamNum;
      initGui();
    } 
    for (MCH_GuiScoreboard_Base s : this.listScreen.values()) {
      try {
        s.updateScreenButtons(this.buttonList);
        s.updateScreen();
      } catch (Exception exception) {}
    } 
  }
  
  public void switchScreen(MCH_GuiScoreboard_Base.SCREEN_ID id) {
    for (MCH_GuiScoreboard_Base b : this.listScreen.values())
      b.leaveScreen(); 
    this.screenID = id;
    getCurrentScreen().onSwitchScreen();
  }
  
  private MCH_GuiScoreboard_Base getCurrentScreen() {
    return this.listScreen.get(this.screenID);
  }
  
  public static void setVisible(Object g, boolean v) {
    if (g instanceof GuiButton)
      ((GuiButton)g).visible = v; 
    if (g instanceof GuiTextField)
      ((GuiTextField)g).setVisible(v); 
  }
  
  protected void keyTyped(char c, int code) throws IOException {
    getCurrentScreen().keyTypedScreen(c, code);
  }
  
  protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
    try {
      for (MCH_GuiScoreboard_Base s : this.listScreen.values())
        s.mouseClickedScreen(mouseX, mouseY, mouseButton); 
      super.mouseClicked(mouseX, mouseY, mouseButton);
    } catch (Exception exception) {}
  }
  
  protected void actionPerformed(GuiButton btn) throws IOException {
    if (btn != null && btn.enabled)
      getCurrentScreen().actionPerformedScreen(btn); 
  }
  
  public void drawDefaultBackground() {}
  
  public void drawBackground(int tint) {
    GL11.glDisable(2896);
    GL11.glDisable(2912);
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
  }
  
  protected void drawGuiContainerForegroundLayer(int x, int y) {
    getCurrentScreen().drawGuiContainerForegroundLayerScreen(x, y);
    for (Object o : this.buttonList) {
      if (o instanceof W_GuiButton) {
        W_GuiButton btn = (W_GuiButton)o;
        if (btn.isOnMouseOver() && btn.hoverStringList != null) {
          drawHoveringText(btn.hoverStringList, x, y, this.fontRendererObj);
          break;
        } 
      } 
    } 
  }
  
  public static void drawList(Minecraft mc, FontRenderer fontRendererObj, boolean mng) {
    MCH_GuiScoreboard_Base.drawList(mc, fontRendererObj, mng);
  }
  
  protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
    getCurrentScreen().drawGuiContainerBackgroundLayer(par1, par2, par3);
  }
  
  public void setWorldAndResolution(Minecraft mc, int width, int height) {
    super.setWorldAndResolution(mc, width, height);
    for (MCH_GuiScoreboard_Base s : this.listScreen.values())
      s.setWorldAndResolution(mc, width, height); 
  }
}
