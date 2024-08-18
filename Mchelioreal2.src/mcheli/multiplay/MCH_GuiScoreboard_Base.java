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
    this.field_146297_k = Minecraft.func_71410_x();
  }
  
  public void func_73866_w_() {}
  
  public void initGui(List<GuiButton> buttonList, GuiScreen parents) {
    this.listGui = new ArrayList<>();
    this.field_146297_k = Minecraft.func_71410_x();
    this.field_146289_q = this.field_146297_k.field_71466_p;
    this.field_146294_l = parents.field_146294_l;
    this.field_146295_m = parents.field_146295_m;
    func_73866_w_();
    for (Gui b : this.listGui) {
      if (b instanceof GuiButton)
        buttonList.add((GuiButton)b); 
    } 
    this.field_146292_n.clear();
  }
  
  public static void setVisible(Object g, boolean v) {
    if (g instanceof GuiButton)
      ((GuiButton)g).field_146125_m = v; 
    if (g instanceof GuiTextField)
      ((GuiTextField)g).func_146189_e(v); 
  }
  
  public void updateScreenButtons(List<GuiButton> list) {}
  
  protected void func_146976_a(float partialTicks, int mouseX, int mouseY) {}
  
  public int getTeamNum() {
    return this.field_146297_k.field_71441_e.func_96441_U().func_96525_g().size();
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
    func_73869_a(c, code);
  }
  
  public void mouseClickedScreen(int mouseX, int mouseY, int mouseButton) throws IOException {
    try {
      func_73864_a(mouseX, mouseY, mouseButton);
    } catch (Exception e) {
      if (mouseButton == 0)
        for (int l = 0; l < this.field_146292_n.size(); l++) {
          GuiButton guibutton = this.field_146292_n.get(l);
          if (guibutton.func_146116_c(this.field_146297_k, mouseX, mouseY)) {
            guibutton.func_146113_a(this.field_146297_k.func_147118_V());
            func_146284_a(guibutton);
          } 
        }  
    } 
  }
  
  public void drawGuiContainerForegroundLayerScreen(int param1, int param2) {
    func_146979_b(param1, param2);
  }
  
  protected void actionPerformedScreen(GuiButton btn) throws IOException {
    func_146284_a(btn);
  }
  
  public void switchScreen(SCREEN_ID id) {
    this.screen_switcher.switchScreen(id);
  }
  
  public static int getScoreboradWidth(Minecraft mc) {
    W_ScaledResolution w_ScaledResolution = new W_ScaledResolution(mc, mc.field_71443_c, mc.field_71440_d);
    int ScaledWidth = w_ScaledResolution.func_78326_a() - 40;
    int width = ScaledWidth * 3 / 4 / (mc.field_71441_e.func_96441_U().func_96525_g().size() + 1);
    if (width > 150)
      width = 150; 
    return width;
  }
  
  public static int getScoreBoardLeft(Minecraft mc, int teamNum, int teamIndex) {
    W_ScaledResolution w_ScaledResolution = new W_ScaledResolution(mc, mc.field_71443_c, mc.field_71440_d);
    int ScaledWidth = w_ScaledResolution.func_78326_a();
    return (int)((ScaledWidth / 2) + (getScoreboradWidth(mc) + 10) * (-teamNum / 2.0D + teamIndex));
  }
  
  public static void drawList(Minecraft mc, FontRenderer fontRendererObj, boolean mng) {
    ArrayList<ScorePlayerTeam> teamList = new ArrayList<>();
    teamList.add(null);
    for (Object team : mc.field_71441_e.func_96441_U().func_96525_g())
      teamList.add((ScorePlayerTeam)team); 
    Collections.sort(teamList, new Comparator<ScorePlayerTeam>() {
          public int compare(ScorePlayerTeam o1, ScorePlayerTeam o2) {
            if (o1 == null && o2 == null)
              return 0; 
            if (o1 == null)
              return -1; 
            if (o2 == null)
              return 1; 
            return o1.func_96661_b().compareTo(o2.func_96661_b());
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
    W_ScaledResolution w_ScaledResolution = new W_ScaledResolution(mc, mc.field_71443_c, mc.field_71440_d);
    int ScaledHeight = w_ScaledResolution.func_78328_b();
    ScoreObjective scoreobjective = mc.field_71441_e.func_96441_U().func_96539_a(0);
    NetHandlerPlayClient nethandlerplayclient = mc.field_71439_g.field_71174_a;
    List<NetworkPlayerInfo> list = Lists.newArrayList(nethandlerplayclient.func_175106_d());
    int MaxPlayers = (list.size() / 5 + 1) * 5;
    MaxPlayers = (MaxPlayers < 10) ? 10 : MaxPlayers;
    if (MaxPlayers > nethandlerplayclient.field_147304_c)
      MaxPlayers = nethandlerplayclient.field_147304_c; 
    int width = getScoreboradWidth(mc);
    int listLeft = getScoreBoardLeft(mc, teamNum, teamIndex);
    int listTop = ScaledHeight / 2 - (MaxPlayers * 9 + 10) / 2;
    func_73734_a(listLeft - 1, listTop - 1 - 18, listLeft + width, listTop + 9 * MaxPlayers, -2147483648);
    String teamName = ScorePlayerTeam.func_96667_a((Team)team, (team == null) ? "No team" : team.func_96661_b());
    int teamNameX = listLeft + width / 2 - fontRendererObj.func_78256_a(teamName) / 2;
    fontRendererObj.func_175063_a(teamName, teamNameX, (listTop - 18), -1);
    String ff_onoff = "FriendlyFire : " + ((team == null) ? "ON" : (team.func_96665_g() ? "ON" : "OFF"));
    int ff_onoffX = listLeft + width / 2 - fontRendererObj.func_78256_a(ff_onoff) / 2;
    fontRendererObj.func_175063_a(ff_onoff, ff_onoffX, (listTop - 9), -1);
    int drawY = 0;
    for (int i = 0; i < MaxPlayers; i++) {
      int x = listLeft;
      int y = listTop + drawY * 9;
      int rectY = listTop + i * 9;
      func_73734_a(x, rectY, x + width - 1, rectY + 8, 553648127);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glEnable(3008);
      if (i < list.size()) {
        NetworkPlayerInfo guiplayerinfo = list.get(i);
        String playerName = guiplayerinfo.func_178845_a().getName();
        ScorePlayerTeam steam = mc.field_71441_e.func_96441_U().func_96509_i(playerName);
        if ((steam == null && team == null) || (steam != null && team != null && steam.func_142054_a((Team)team))) {
          drawY++;
          fontRendererObj.func_175063_a(playerName, x, y, -1);
          if (scoreobjective != null) {
            int j4 = x + fontRendererObj.func_78256_a(playerName) + 5;
            int k4 = x + width - 12 - 5;
            if (k4 - j4 > 5) {
              Score score = scoreobjective.func_96682_a().func_96529_a(guiplayerinfo.func_178845_a().getName(), scoreobjective);
              String s1 = TextFormatting.YELLOW + "" + score.func_96652_c();
              fontRendererObj.func_175063_a(s1, (k4 - fontRendererObj.func_78256_a(s1)), y, 16777215);
            } 
          } 
          drawResponseTime(x + width - 12, y, guiplayerinfo.func_178853_c());
        } 
      } 
    } 
  }
  
  public static void drawResponseTime(int x, int y, int responseTime) {
    byte b2;
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    Minecraft.func_71410_x().func_110434_K().func_110577_a(field_110324_m);
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
    Tessellator tessellator = Tessellator.func_178181_a();
    BufferBuilder builder = tessellator.func_178180_c();
    builder.func_181668_a(7, DefaultVertexFormats.field_181707_g);
    builder.func_181662_b((x + 0), (y + y3), zLevel).func_187315_a(((x2 + 0) * 0.00390625F), ((y2 + y3) * 0.00390625F)).func_181675_d();
    builder.func_181662_b((x + x3), (y + y3), zLevel).func_187315_a(((x2 + x3) * 0.00390625F), ((y2 + y3) * 0.00390625F)).func_181675_d();
    builder.func_181662_b((x + x3), (y + 0), zLevel).func_187315_a(((x2 + x3) * 0.00390625F), ((y2 + 0) * 0.00390625F)).func_181675_d();
    builder.func_181662_b((x + 0), (y + 0), zLevel).func_187315_a(((x2 + 0) * 0.00390625F), ((y2 + 0) * 0.00390625F)).func_181675_d();
    tessellator.func_78381_a();
  }
  
  public enum SCREEN_ID {
    MAIN, CREATE_TEAM;
  }
}
