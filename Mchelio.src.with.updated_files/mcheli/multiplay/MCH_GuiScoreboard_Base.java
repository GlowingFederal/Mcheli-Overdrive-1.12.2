package mcheli.multiplay;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import mcheli.wrapper.W_GuiContainer;
import mcheli.wrapper.W_ScaledResolution;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Team;
import net.minecraft.util.text.TextFormatting;
import org.lwjgl.opengl.GL11;

public abstract class MCH_GuiScoreboard_Base extends W_GuiContainer {
  public List<Gui> listGui;
  
  public static final int BUTTON_ID_SHUFFLE = 256;
  
  public static final int BUTTON_ID_CREATE_TEAM = 512;
  
  public static final int BUTTON_ID_CREATE_TEAM_OK = 528;
  
  public static final int BUTTON_ID_CREATE_TEAM_CANCEL = 544;
  
  public static final int BUTTON_ID_CREATE_TEAM_FF = 560;
  
  public static final int BUTTON_ID_CREATE_TEAM_NEXT_C = 576;
  
  public static final int BUTTON_ID_CREATE_TEAM_PREV_C = 577;
  
  public static final int BUTTON_ID_JUMP_SPAWN_POINT = 768;
  
  public static final int BUTTON_ID_SWITCH_PVP = 1024;
  
  public static final int BUTTON_ID_DESTORY_ALL = 1280;
  
  private MCH_IGuiScoreboard screen_switcher;
  
  public MCH_GuiScoreboard_Base(MCH_IGuiScoreboard switcher, EntityPlayer player) {
    super(new MCH_ContainerScoreboard(player));
    this.screen_switcher = switcher;
    this.mc = Minecraft.getMinecraft();
  }
  
  public void initGui() {}
  
  public void initGui(List<GuiButton> buttonList, GuiScreen parents) {
    this.listGui = new ArrayList<>();
    this.mc = Minecraft.getMinecraft();
    this.fontRenderer = this.mc.fontRenderer;
    this.width = parents.width;
    this.height = parents.height;
    initGui();
    for (Gui b : this.listGui) {
      if (b instanceof GuiButton)
        buttonList.add((GuiButton)b); 
    } 
    this.buttonList.clear();
  }
  
  public static void setVisible(Object g, boolean v) {
    if (g instanceof GuiButton)
      ((GuiButton)g).visible = v; 
    if (g instanceof GuiTextField)
      ((GuiTextField)g).setVisible(v); 
  }
  
  public void updateScreenButtons(List<GuiButton> list) {}
  
  protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {}
  
  public int getTeamNum() {
    return this.mc.world.getScoreboard().getTeams().size();
  }
  
  protected void acviveScreen() {}
  
  public void onSwitchScreen() {
    for (Gui b : this.listGui)
      setVisible(b, true); 
    acviveScreen();
  }
  
  public void leaveScreen() {
    for (Gui b : this.listGui)
      setVisible(b, false); 
  }
  
  public void keyTypedScreen(char c, int code) throws IOException {
    keyTyped(c, code);
  }
  
  public void mouseClickedScreen(int mouseX, int mouseY, int mouseButton) throws IOException {
    try {
      mouseClicked(mouseX, mouseY, mouseButton);
    } catch (Exception e) {
      if (mouseButton == 0)
        for (int l = 0; l < this.buttonList.size(); l++) {
          GuiButton guibutton = this.buttonList.get(l);
          if (guibutton.mousePressed(this.mc, mouseX, mouseY)) {
            guibutton.playPressSound(this.mc.getSoundHandler());
            actionPerformed(guibutton);
          } 
        }  
    } 
  }
  
  public void drawGuiContainerForegroundLayerScreen(int param1, int param2) {
    drawGuiContainerForegroundLayer(param1, param2);
  }
  
  protected void actionPerformedScreen(GuiButton btn) throws IOException {
    actionPerformed(btn);
  }
  
  public void switchScreen(SCREEN_ID id) {
    this.screen_switcher.switchScreen(id);
  }
  
  public static int getScoreboradWidth(Minecraft mc) {
    W_ScaledResolution w_ScaledResolution = new W_ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
    int ScaledWidth = w_ScaledResolution.getScaledWidth() - 40;
    int width = ScaledWidth * 3 / 4 / (mc.world.getScoreboard().getTeams().size() + 1);
    if (width > 150)
      width = 150; 
    return width;
  }
  
  public static int getScoreBoardLeft(Minecraft mc, int teamNum, int teamIndex) {
    W_ScaledResolution w_ScaledResolution = new W_ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
    int ScaledWidth = w_ScaledResolution.getScaledWidth();
    return (int)((ScaledWidth / 2) + (getScoreboradWidth(mc) + 10) * (-teamNum / 2.0D + teamIndex));
  }
  
  public static void drawList(Minecraft mc, FontRenderer fontRendererObj, boolean mng) {
    ArrayList<ScorePlayerTeam> teamList = new ArrayList<>();
    teamList.add(null);
    for (Object team : mc.world.getScoreboard().getTeams())
      teamList.add((ScorePlayerTeam)team); 
    Collections.sort(teamList, new Comparator<ScorePlayerTeam>() {
          public int compare(ScorePlayerTeam o1, ScorePlayerTeam o2) {
            if (o1 == null && o2 == null)
              return 0; 
            if (o1 == null)
              return -1; 
            if (o2 == null)
              return 1; 
            return o1.getName().compareTo(o2.getName());
          }
        });
    for (int i = 0; i < teamList.size(); i++) {
      if (mng) {
        drawPlayersList(mc, fontRendererObj, teamList.get(i), 1 + i, 1 + teamList.size());
      } else {
        drawPlayersList(mc, fontRendererObj, teamList.get(i), i, teamList.size());
      } 
    } 
  }
  
  public static void drawPlayersList(Minecraft mc, FontRenderer fontRendererObj, ScorePlayerTeam team, int teamIndex, int teamNum) {
    W_ScaledResolution w_ScaledResolution = new W_ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
    int ScaledHeight = w_ScaledResolution.getScaledHeight();
    ScoreObjective scoreobjective = mc.world.getScoreboard().getObjectiveInDisplaySlot(0);
    NetHandlerPlayClient nethandlerplayclient = mc.player.connection;
    List<NetworkPlayerInfo> list = Lists.newArrayList(nethandlerplayclient.getPlayerInfoMap());
    int MaxPlayers = (list.size() / 5 + 1) * 5;
    MaxPlayers = (MaxPlayers < 10) ? 10 : MaxPlayers;
    if (MaxPlayers > nethandlerplayclient.currentServerMaxPlayers)
      MaxPlayers = nethandlerplayclient.currentServerMaxPlayers; 
    int width = getScoreboradWidth(mc);
    int listLeft = getScoreBoardLeft(mc, teamNum, teamIndex);
    int listTop = ScaledHeight / 2 - (MaxPlayers * 9 + 10) / 2;
    drawRect(listLeft - 1, listTop - 1 - 18, listLeft + width, listTop + 9 * MaxPlayers, -2147483648);
    String teamName = ScorePlayerTeam.formatPlayerName((Team)team, (team == null) ? "No team" : team.getName());
    int teamNameX = listLeft + width / 2 - fontRendererObj.getStringWidth(teamName) / 2;
    fontRendererObj.drawStringWithShadow(teamName, teamNameX, (listTop - 18), -1);
    String ff_onoff = "FriendlyFire : " + ((team == null) ? "ON" : (team.getAllowFriendlyFire() ? "ON" : "OFF"));
    int ff_onoffX = listLeft + width / 2 - fontRendererObj.getStringWidth(ff_onoff) / 2;
    fontRendererObj.drawStringWithShadow(ff_onoff, ff_onoffX, (listTop - 9), -1);
    int drawY = 0;
    for (int i = 0; i < MaxPlayers; i++) {
      int x = listLeft;
      int y = listTop + drawY * 9;
      int rectY = listTop + i * 9;
      drawRect(x, rectY, x + width - 1, rectY + 8, 553648127);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glEnable(3008);
      if (i < list.size()) {
        NetworkPlayerInfo guiplayerinfo = list.get(i);
        String playerName = guiplayerinfo.getGameProfile().getName();
        ScorePlayerTeam steam = mc.world.getScoreboard().getPlayersTeam(playerName);
        if ((steam == null && team == null) || (steam != null && team != null && steam.isSameTeam((Team)team))) {
          drawY++;
          fontRendererObj.drawStringWithShadow(playerName, x, y, -1);
          if (scoreobjective != null) {
            int j4 = x + fontRendererObj.getStringWidth(playerName) + 5;
            int k4 = x + width - 12 - 5;
            if (k4 - j4 > 5) {
              Score score = scoreobjective.getScoreboard().getOrCreateScore(guiplayerinfo.getGameProfile().getName(), scoreobjective);
              String s1 = TextFormatting.YELLOW + "" + score.getScorePoints();
              fontRendererObj.drawStringWithShadow(s1, (k4 - fontRendererObj.getStringWidth(s1)), y, 16777215);
            } 
          } 
          drawResponseTime(x + width - 12, y, guiplayerinfo.getResponseTime());
        } 
      } 
    } 
  }
  
  public static void drawResponseTime(int x, int y, int responseTime) {
    byte b2;
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    Minecraft.getMinecraft().getTextureManager().bindTexture(ICONS);
    if (responseTime < 0) {
      b2 = 5;
    } else if (responseTime < 150) {
      b2 = 0;
    } else if (responseTime < 300) {
      b2 = 1;
    } else if (responseTime < 600) {
      b2 = 2;
    } else if (responseTime < 1000) {
      b2 = 3;
    } else {
      b2 = 4;
    } 
    static_drawTexturedModalRect(x, y, 0, 176 + b2 * 8, 10, 8, 0.0D);
  }
  
  public static void static_drawTexturedModalRect(int x, int y, int x2, int y2, int x3, int y3, double zLevel) {
    Tessellator tessellator = Tessellator.getInstance();
    BufferBuilder builder = tessellator.getBuffer();
    builder.begin(7, DefaultVertexFormats.POSITION_TEX);
    builder.pos((x + 0), (y + y3), zLevel).tex(((x2 + 0) * 0.00390625F), ((y2 + y3) * 0.00390625F)).endVertex();
    builder.pos((x + x3), (y + y3), zLevel).tex(((x2 + x3) * 0.00390625F), ((y2 + y3) * 0.00390625F)).endVertex();
    builder.pos((x + x3), (y + 0), zLevel).tex(((x2 + x3) * 0.00390625F), ((y2 + 0) * 0.00390625F)).endVertex();
    builder.pos((x + 0), (y + 0), zLevel).tex(((x2 + 0) * 0.00390625F), ((y2 + 0) * 0.00390625F)).endVertex();
    tessellator.draw();
  }
  
  public enum SCREEN_ID {
    MAIN, CREATE_TEAM;
  }
}
