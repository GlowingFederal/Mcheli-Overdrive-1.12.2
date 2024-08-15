/*     */ package mcheli.multiplay;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import mcheli.wrapper.W_McClient;
/*     */ import mcheli.wrapper.W_ScaledResolution;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.gui.GuiTextField;
/*     */ import net.minecraft.entity.player.EntityPlayer;
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
/*     */ public class MCH_GuiScoreboard_CreateTeam
/*     */   extends MCH_GuiScoreboard_Base
/*     */ {
/*     */   private GuiButton buttonCreateTeamOK;
/*     */   private GuiButton buttonCreateTeamFF;
/*     */   private GuiTextField editCreateTeamName;
/*     */   private static boolean friendlyFire = true;
/*  29 */   private int lastTeamColor = 0;
/*     */   
/*  31 */   private static final String[] colorNames = new String[] { "RESET", "BLACK", "DARK_BLUE", "DARK_GREEN", "DARK_AQUA", "DARK_RED", "DARK_PURPLE", "GOLD", "GRAY", "DARK_GRAY", "BLUE", "GREEN", "AQUA", "RED", "LIGHT_PURPLE", "YELLOW" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MCH_GuiScoreboard_CreateTeam(MCH_IGuiScoreboard switcher, EntityPlayer player) {
/*  39 */     super(switcher, player);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_73866_w_() {
/*  45 */     super.func_73866_w_();
/*  46 */     W_ScaledResolution w_ScaledResolution = new W_ScaledResolution(this.field_146297_k, this.field_146297_k.field_71443_c, this.field_146297_k.field_71440_d);
/*  47 */     int factor = (w_ScaledResolution.func_78325_e() > 0) ? w_ScaledResolution.func_78325_e() : 1;
/*     */     
/*  49 */     this.field_147003_i = 0;
/*  50 */     this.field_147009_r = 0;
/*  51 */     int x = this.field_146297_k.field_71443_c / 2 / factor;
/*  52 */     int y = this.field_146297_k.field_71440_d / 2 / factor;
/*     */     
/*  54 */     GuiButton buttonCTNextC = new GuiButton(576, x + 40, y - 20, 40, 20, ">");
/*  55 */     GuiButton buttonCTPrevC = new GuiButton(577, x - 80, y - 20, 40, 20, "<");
/*  56 */     this.buttonCreateTeamFF = new GuiButton(560, x - 80, y + 20, 160, 20, "");
/*  57 */     this.buttonCreateTeamOK = new GuiButton(528, x - 80, y + 60, 80, 20, "OK");
/*  58 */     GuiButton buttonCTCancel = new GuiButton(544, x + 0, y + 60, 80, 20, "Cancel");
/*     */     
/*  60 */     this.editCreateTeamName = new GuiTextField(599, this.field_146289_q, x - 80, y - 55, 160, 20);
/*  61 */     this.editCreateTeamName.func_146180_a("");
/*  62 */     this.editCreateTeamName.func_146193_g(-1);
/*  63 */     this.editCreateTeamName.func_146203_f(16);
/*  64 */     this.editCreateTeamName.func_146195_b(true);
/*     */     
/*  66 */     this.listGui.add(buttonCTNextC);
/*  67 */     this.listGui.add(buttonCTPrevC);
/*  68 */     this.listGui.add(this.buttonCreateTeamFF);
/*  69 */     this.listGui.add(this.buttonCreateTeamOK);
/*  70 */     this.listGui.add(buttonCTCancel);
/*  71 */     this.listGui.add(this.editCreateTeamName);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_73876_c() {
/*  77 */     String teamName = this.editCreateTeamName.func_146179_b();
/*  78 */     this.buttonCreateTeamOK.field_146124_l = (teamName.length() > 0 && teamName.length() <= 16);
/*     */     
/*  80 */     this.editCreateTeamName.func_146178_a();
/*     */     
/*  82 */     this.buttonCreateTeamFF.field_146126_j = "Friendly Fire : " + (friendlyFire ? "ON" : "OFF");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void acviveScreen() {
/*  88 */     this.editCreateTeamName.func_146180_a("");
/*  89 */     this.editCreateTeamName.func_146195_b(true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_73869_a(char c, int code) throws IOException {
/*  96 */     if (code == 1) {
/*     */       
/*  98 */       switchScreen(MCH_GuiScoreboard_Base.SCREEN_ID.MAIN);
/*     */     }
/*     */     else {
/*     */       
/* 102 */       this.editCreateTeamName.func_146201_a(c, code);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_73864_a(int mouseX, int mouseY, int mouseButton) throws IOException {
/* 110 */     this.editCreateTeamName.func_146192_a(mouseX, mouseY, mouseButton);
/* 111 */     super.func_73864_a(mouseX, mouseY, mouseButton);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_146284_a(GuiButton btn) throws IOException {
/* 118 */     if (btn != null && btn.field_146124_l) {
/*     */       String teamName;
/* 120 */       switch (btn.field_146127_k) {
/*     */         
/*     */         case 528:
/* 123 */           teamName = this.editCreateTeamName.func_146179_b();
/* 124 */           if (teamName.length() > 0 && teamName.length() <= 16) {
/*     */             
/* 126 */             MCH_PacketIndMultiplayCommand.send(768, "scoreboard teams add " + teamName);
/* 127 */             MCH_PacketIndMultiplayCommand.send(768, "scoreboard teams option " + teamName + " color " + colorNames[this.lastTeamColor]);
/*     */ 
/*     */             
/* 130 */             MCH_PacketIndMultiplayCommand.send(768, "scoreboard teams option " + teamName + " friendlyfire " + friendlyFire);
/*     */           } 
/*     */ 
/*     */           
/* 134 */           switchScreen(MCH_GuiScoreboard_Base.SCREEN_ID.MAIN);
/*     */           break;
/*     */         case 544:
/* 137 */           switchScreen(MCH_GuiScoreboard_Base.SCREEN_ID.MAIN);
/*     */           break;
/*     */         case 560:
/* 140 */           friendlyFire = !friendlyFire;
/*     */           break;
/*     */         case 576:
/* 143 */           this.lastTeamColor++;
/* 144 */           if (this.lastTeamColor >= colorNames.length)
/*     */           {
/* 146 */             this.lastTeamColor = 0;
/*     */           }
/*     */           break;
/*     */         case 577:
/* 150 */           this.lastTeamColor--;
/* 151 */           if (this.lastTeamColor < 0)
/*     */           {
/* 153 */             this.lastTeamColor = colorNames.length - 1;
/*     */           }
/*     */           break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_146976_a(float par1, int par2, int par3) {
/* 164 */     drawList(this.field_146297_k, this.field_146289_q, true);
/*     */     
/* 166 */     W_ScaledResolution w_ScaledResolution = new W_ScaledResolution(this.field_146297_k, this.field_146297_k.field_71443_c, this.field_146297_k.field_71440_d);
/* 167 */     int factor = (w_ScaledResolution.func_78325_e() > 0) ? w_ScaledResolution.func_78325_e() : 1;
/*     */     
/* 169 */     W_McClient.MOD_bindTexture("textures/gui/mp_new_team.png");
/* 170 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*     */     
/* 172 */     int x = (this.field_146297_k.field_71443_c / factor - 222) / 2;
/* 173 */     int y = (this.field_146297_k.field_71440_d / factor - 200) / 2;
/*     */     
/* 175 */     func_73729_b(x, y, 0, 0, 222, 200);
/*     */     
/* 177 */     x = this.field_146297_k.field_71443_c / 2 / factor;
/* 178 */     y = this.field_146297_k.field_71440_d / 2 / factor;
/*     */     
/* 180 */     drawCenteredString("Create team", x, y - 85, -1);
/* 181 */     drawCenteredString("Team name", x, y - 70, -1);
/*     */ 
/*     */     
/* 184 */     TextFormatting ecf = TextFormatting.func_96300_b(colorNames[this.lastTeamColor]);
/* 185 */     drawCenteredString(ecf + "Team Color" + ecf, x, y - 13, -1);
/*     */     
/* 187 */     this.editCreateTeamName.func_146194_f();
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\multiplay\MCH_GuiScoreboard_CreateTeam.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */