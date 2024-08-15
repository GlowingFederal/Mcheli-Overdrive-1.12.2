/*     */ package mcheli.multiplay;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import mcheli.wrapper.W_GuiContainer;
/*     */ import mcheli.wrapper.W_ScaledResolution;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.FontRenderer;
/*     */ import net.minecraft.client.gui.Gui;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.gui.GuiTextField;
/*     */ import net.minecraft.client.network.NetHandlerPlayClient;
/*     */ import net.minecraft.client.network.NetworkPlayerInfo;
/*     */ import net.minecraft.client.renderer.BufferBuilder;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.scoreboard.Score;
/*     */ import net.minecraft.scoreboard.ScoreObjective;
/*     */ import net.minecraft.scoreboard.ScorePlayerTeam;
/*     */ import net.minecraft.scoreboard.Team;
/*     */ import net.minecraft.util.text.TextFormatting;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class MCH_GuiScoreboard_Base
/*     */   extends W_GuiContainer
/*     */ {
/*     */   public List<Gui> listGui;
/*     */   public static final int BUTTON_ID_SHUFFLE = 256;
/*     */   public static final int BUTTON_ID_CREATE_TEAM = 512;
/*     */   public static final int BUTTON_ID_CREATE_TEAM_OK = 528;
/*     */   public static final int BUTTON_ID_CREATE_TEAM_CANCEL = 544;
/*     */   public static final int BUTTON_ID_CREATE_TEAM_FF = 560;
/*     */   public static final int BUTTON_ID_CREATE_TEAM_NEXT_C = 576;
/*     */   public static final int BUTTON_ID_CREATE_TEAM_PREV_C = 577;
/*     */   public static final int BUTTON_ID_JUMP_SPAWN_POINT = 768;
/*     */   public static final int BUTTON_ID_SWITCH_PVP = 1024;
/*     */   public static final int BUTTON_ID_DESTORY_ALL = 1280;
/*     */   private MCH_IGuiScoreboard screen_switcher;
/*     */   
/*     */   public MCH_GuiScoreboard_Base(MCH_IGuiScoreboard switcher, EntityPlayer player) {
/*  59 */     super(new MCH_ContainerScoreboard(player));
/*  60 */     this.screen_switcher = switcher;
/*  61 */     this.field_146297_k = Minecraft.func_71410_x();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_73866_w_() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui(List<GuiButton> buttonList, GuiScreen parents) {
/*  71 */     this.listGui = new ArrayList<>();
/*  72 */     this.field_146297_k = Minecraft.func_71410_x();
/*  73 */     this.field_146289_q = this.field_146297_k.field_71466_p;
/*  74 */     this.field_146294_l = parents.field_146294_l;
/*  75 */     this.field_146295_m = parents.field_146295_m;
/*     */     
/*  77 */     func_73866_w_();
/*     */     
/*  79 */     for (Gui b : this.listGui) {
/*     */       
/*  81 */       if (b instanceof GuiButton) {
/*  82 */         buttonList.add((GuiButton)b);
/*     */       }
/*     */     } 
/*  85 */     this.field_146292_n.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public static void setVisible(Object g, boolean v) {
/*  90 */     if (g instanceof GuiButton) {
/*  91 */       ((GuiButton)g).field_146125_m = v;
/*     */     }
/*  93 */     if (g instanceof GuiTextField)
/*     */     {
/*  95 */       ((GuiTextField)g).func_146189_e(v);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateScreenButtons(List<GuiButton> list) {}
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_146976_a(float partialTicks, int mouseX, int mouseY) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTeamNum() {
/* 110 */     return this.field_146297_k.field_71441_e.func_96441_U().func_96525_g().size();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void acviveScreen() {}
/*     */ 
/*     */   
/*     */   public void onSwitchScreen() {
/* 119 */     for (Gui b : this.listGui)
/*     */     {
/* 121 */       setVisible(b, true);
/*     */     }
/* 123 */     acviveScreen();
/*     */   }
/*     */ 
/*     */   
/*     */   public void leaveScreen() {
/* 128 */     for (Gui b : this.listGui)
/*     */     {
/* 130 */       setVisible(b, false);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void keyTypedScreen(char c, int code) throws IOException {
/* 137 */     func_73869_a(c, code);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mouseClickedScreen(int mouseX, int mouseY, int mouseButton) throws IOException {
/*     */     try {
/* 145 */       func_73864_a(mouseX, mouseY, mouseButton);
/*     */     }
/* 147 */     catch (Exception e) {
/*     */       
/* 149 */       if (mouseButton == 0)
/*     */       {
/* 151 */         for (int l = 0; l < this.field_146292_n.size(); l++) {
/*     */           
/* 153 */           GuiButton guibutton = this.field_146292_n.get(l);
/*     */           
/* 155 */           if (guibutton.func_146116_c(this.field_146297_k, mouseX, mouseY)) {
/*     */             
/* 157 */             guibutton.func_146113_a(this.field_146297_k.func_147118_V());
/* 158 */             func_146284_a(guibutton);
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawGuiContainerForegroundLayerScreen(int param1, int param2) {
/* 167 */     func_146979_b(param1, param2);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformedScreen(GuiButton btn) throws IOException {
/* 173 */     func_146284_a(btn);
/*     */   }
/*     */ 
/*     */   
/*     */   public void switchScreen(SCREEN_ID id) {
/* 178 */     this.screen_switcher.switchScreen(id);
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getScoreboradWidth(Minecraft mc) {
/* 183 */     W_ScaledResolution w_ScaledResolution = new W_ScaledResolution(mc, mc.field_71443_c, mc.field_71440_d);
/* 184 */     int ScaledWidth = w_ScaledResolution.func_78326_a() - 40;
/* 185 */     int width = ScaledWidth * 3 / 4 / (mc.field_71441_e.func_96441_U().func_96525_g().size() + 1);
/* 186 */     if (width > 150)
/*     */     {
/* 188 */       width = 150;
/*     */     }
/* 190 */     return width;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getScoreBoardLeft(Minecraft mc, int teamNum, int teamIndex) {
/* 195 */     W_ScaledResolution w_ScaledResolution = new W_ScaledResolution(mc, mc.field_71443_c, mc.field_71440_d);
/* 196 */     int ScaledWidth = w_ScaledResolution.func_78326_a();
/* 197 */     return (int)((ScaledWidth / 2) + (getScoreboradWidth(mc) + 10) * (-teamNum / 2.0D + teamIndex));
/*     */   }
/*     */ 
/*     */   
/*     */   public static void drawList(Minecraft mc, FontRenderer fontRendererObj, boolean mng) {
/* 202 */     ArrayList<ScorePlayerTeam> teamList = new ArrayList<>();
/*     */     
/* 204 */     teamList.add(null);
/*     */     
/* 206 */     for (Object team : mc.field_71441_e.func_96441_U().func_96525_g())
/*     */     {
/* 208 */       teamList.add((ScorePlayerTeam)team);
/*     */     }
/*     */ 
/*     */     
/* 212 */     Collections.sort(teamList, new Comparator<ScorePlayerTeam>()
/*     */         {
/*     */           
/*     */           public int compare(ScorePlayerTeam o1, ScorePlayerTeam o2)
/*     */           {
/* 217 */             if (o1 == null && o2 == null)
/* 218 */               return 0; 
/* 219 */             if (o1 == null)
/* 220 */               return -1; 
/* 221 */             if (o2 == null)
/* 222 */               return 1; 
/* 223 */             return o1.func_96661_b().compareTo(o2.func_96661_b());
/*     */           }
/*     */         });
/*     */     
/* 227 */     for (int i = 0; i < teamList.size(); i++) {
/*     */       
/* 229 */       if (mng) {
/* 230 */         drawPlayersList(mc, fontRendererObj, teamList.get(i), 1 + i, 1 + teamList.size());
/*     */       }
/*     */       else {
/*     */         
/* 234 */         drawPlayersList(mc, fontRendererObj, teamList.get(i), i, teamList.size());
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void drawPlayersList(Minecraft mc, FontRenderer fontRendererObj, ScorePlayerTeam team, int teamIndex, int teamNum) {
/* 242 */     W_ScaledResolution w_ScaledResolution = new W_ScaledResolution(mc, mc.field_71443_c, mc.field_71440_d);
/*     */     
/* 244 */     int ScaledHeight = w_ScaledResolution.func_78328_b();
/* 245 */     ScoreObjective scoreobjective = mc.field_71441_e.func_96441_U().func_96539_a(0);
/* 246 */     NetHandlerPlayClient nethandlerplayclient = mc.field_71439_g.field_71174_a;
/*     */     
/* 248 */     List<NetworkPlayerInfo> list = Lists.newArrayList(nethandlerplayclient.func_175106_d());
/* 249 */     int MaxPlayers = (list.size() / 5 + 1) * 5;
/* 250 */     MaxPlayers = (MaxPlayers < 10) ? 10 : MaxPlayers;
/*     */     
/* 252 */     if (MaxPlayers > nethandlerplayclient.field_147304_c)
/*     */     {
/* 254 */       MaxPlayers = nethandlerplayclient.field_147304_c;
/*     */     }
/*     */     
/* 257 */     int width = getScoreboradWidth(mc);
/* 258 */     int listLeft = getScoreBoardLeft(mc, teamNum, teamIndex);
/* 259 */     int listTop = ScaledHeight / 2 - (MaxPlayers * 9 + 10) / 2;
/*     */     
/* 261 */     func_73734_a(listLeft - 1, listTop - 1 - 18, listLeft + width, listTop + 9 * MaxPlayers, -2147483648);
/*     */     
/* 263 */     String teamName = ScorePlayerTeam.func_96667_a((Team)team, (team == null) ? "No team" : team.func_96661_b());
/* 264 */     int teamNameX = listLeft + width / 2 - fontRendererObj.func_78256_a(teamName) / 2;
/*     */     
/* 266 */     fontRendererObj.func_175063_a(teamName, teamNameX, (listTop - 18), -1);
/*     */ 
/*     */     
/* 269 */     String ff_onoff = "FriendlyFire : " + ((team == null) ? "ON" : (team.func_96665_g() ? "ON" : "OFF"));
/* 270 */     int ff_onoffX = listLeft + width / 2 - fontRendererObj.func_78256_a(ff_onoff) / 2;
/*     */     
/* 272 */     fontRendererObj.func_175063_a(ff_onoff, ff_onoffX, (listTop - 9), -1);
/*     */     
/* 274 */     int drawY = 0;
/*     */     
/* 276 */     for (int i = 0; i < MaxPlayers; i++) {
/*     */       
/* 278 */       int x = listLeft;
/* 279 */       int y = listTop + drawY * 9;
/* 280 */       int rectY = listTop + i * 9;
/*     */       
/* 282 */       func_73734_a(x, rectY, x + width - 1, rectY + 8, 553648127);
/* 283 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 284 */       GL11.glEnable(3008);
/*     */       
/* 286 */       if (i < list.size()) {
/*     */ 
/*     */         
/* 289 */         NetworkPlayerInfo guiplayerinfo = list.get(i);
/*     */         
/* 291 */         String playerName = guiplayerinfo.func_178845_a().getName();
/* 292 */         ScorePlayerTeam steam = mc.field_71441_e.func_96441_U().func_96509_i(playerName);
/*     */         
/* 294 */         if ((steam == null && team == null) || (steam != null && team != null && steam.func_142054_a((Team)team))) {
/*     */           
/* 296 */           drawY++;
/*     */           
/* 298 */           fontRendererObj.func_175063_a(playerName, x, y, -1);
/*     */           
/* 300 */           if (scoreobjective != null) {
/*     */             
/* 302 */             int j4 = x + fontRendererObj.func_78256_a(playerName) + 5;
/* 303 */             int k4 = x + width - 12 - 5;
/*     */             
/* 305 */             if (k4 - j4 > 5) {
/*     */ 
/*     */ 
/*     */               
/* 309 */               Score score = scoreobjective.func_96682_a().func_96529_a(guiplayerinfo.func_178845_a().getName(), scoreobjective);
/*     */               
/* 311 */               String s1 = TextFormatting.YELLOW + "" + score.func_96652_c();
/*     */               
/* 313 */               fontRendererObj.func_175063_a(s1, (k4 - fontRendererObj.func_78256_a(s1)), y, 16777215);
/*     */             } 
/*     */           } 
/*     */ 
/*     */           
/* 318 */           drawResponseTime(x + width - 12, y, guiplayerinfo.func_178853_c());
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void drawResponseTime(int x, int y, int responseTime) {
/*     */     byte b2;
/* 326 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 327 */     Minecraft.func_71410_x().func_110434_K().func_110577_a(field_110324_m);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 371 */     if (responseTime < 0) {
/*     */       
/* 373 */       b2 = 5;
/*     */     }
/* 375 */     else if (responseTime < 150) {
/*     */       
/* 377 */       b2 = 0;
/*     */     }
/* 379 */     else if (responseTime < 300) {
/*     */       
/* 381 */       b2 = 1;
/*     */     }
/* 383 */     else if (responseTime < 600) {
/*     */       
/* 385 */       b2 = 2;
/*     */     }
/* 387 */     else if (responseTime < 1000) {
/*     */       
/* 389 */       b2 = 3;
/*     */     }
/*     */     else {
/*     */       
/* 393 */       b2 = 4;
/*     */     } 
/*     */     
/* 396 */     static_drawTexturedModalRect(x, y, 0, 176 + b2 * 8, 10, 8, 0.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void static_drawTexturedModalRect(int x, int y, int x2, int y2, int x3, int y3, double zLevel) {
/* 402 */     Tessellator tessellator = Tessellator.func_178181_a();
/* 403 */     BufferBuilder builder = tessellator.func_178180_c();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 410 */     builder.func_181668_a(7, DefaultVertexFormats.field_181707_g);
/* 411 */     builder.func_181662_b((x + 0), (y + y3), zLevel).func_187315_a(((x2 + 0) * 0.00390625F), ((y2 + y3) * 0.00390625F)).func_181675_d();
/* 412 */     builder.func_181662_b((x + x3), (y + y3), zLevel).func_187315_a(((x2 + x3) * 0.00390625F), ((y2 + y3) * 0.00390625F)).func_181675_d();
/* 413 */     builder.func_181662_b((x + x3), (y + 0), zLevel).func_187315_a(((x2 + x3) * 0.00390625F), ((y2 + 0) * 0.00390625F)).func_181675_d();
/* 414 */     builder.func_181662_b((x + 0), (y + 0), zLevel).func_187315_a(((x2 + 0) * 0.00390625F), ((y2 + 0) * 0.00390625F)).func_181675_d();
/* 415 */     tessellator.func_78381_a();
/*     */   }
/*     */   
/*     */   public enum SCREEN_ID
/*     */   {
/* 420 */     MAIN,
/* 421 */     CREATE_TEAM;
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\multiplay\MCH_GuiScoreboard_Base.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */