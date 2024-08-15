/*     */ package mcheli.multiplay;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.List;
/*     */ import mcheli.MCH_ServerSettings;
/*     */ import mcheli.wrapper.W_GuiButton;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MCH_GuiScoreboard_Main
/*     */   extends MCH_GuiScoreboard_Base
/*     */ {
/*     */   private W_GuiButton buttonSwitchPVP;
/*     */   
/*     */   public MCH_GuiScoreboard_Main(MCH_IGuiScoreboard switcher, EntityPlayer player) {
/*  23 */     super(switcher, player);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_73866_w_() {
/*  29 */     super.func_73866_w_();
/*     */     
/*  31 */     if (this.buttonSwitchPVP != null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/*  36 */     this.field_147003_i = 0;
/*  37 */     this.field_147009_r = 0;
/*  38 */     int WIDTH = getScoreboradWidth(this.field_146297_k) * 3 / 4;
/*  39 */     if (WIDTH < 80)
/*  40 */       WIDTH = 80; 
/*  41 */     int LEFT = getScoreBoardLeft(this.field_146297_k, getTeamNum() + 1, 0) / 4;
/*     */     
/*  43 */     this.buttonSwitchPVP = new W_GuiButton(1024, LEFT, 80, WIDTH, 20, "");
/*  44 */     this.listGui.add(this.buttonSwitchPVP);
/*     */     
/*  46 */     W_GuiButton btn = new W_GuiButton(256, LEFT, 100, WIDTH, 20, "Team shuffle");
/*  47 */     btn.addHoverString("Shuffle all players.");
/*  48 */     this.listGui.add(btn);
/*     */     
/*  50 */     this.listGui.add(new W_GuiButton(512, LEFT, 120, WIDTH, 20, "New team"));
/*     */     
/*  52 */     btn = new W_GuiButton(768, LEFT, 140, WIDTH, 20, "Jump spawn pos");
/*  53 */     btn.addHoverString("Teleport all players -> spawn point.");
/*  54 */     this.listGui.add(btn);
/*     */     
/*  56 */     btn = new W_GuiButton(1280, LEFT, 160, WIDTH, 20, "Destroy All");
/*  57 */     btn.addHoverString("Destroy all aircraft and vehicle.");
/*  58 */     this.listGui.add(btn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_73869_a(char c, int code) throws IOException {
/*  65 */     if (code == 1)
/*     */     {
/*  67 */       this.field_146297_k.field_71439_g.func_71053_j();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateScreenButtons(List<GuiButton> list) {
/*  74 */     for (GuiButton o : list) {
/*     */       
/*  76 */       GuiButton button = o;
/*     */       
/*  78 */       if (button.field_146127_k == 1024)
/*     */       {
/*  80 */         button.field_146126_j = "PVP : " + (MCH_ServerSettings.enablePVP ? "ON" : "OFF");
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_146284_a(GuiButton btn) throws IOException {
/*  89 */     if (btn != null && btn.field_146124_l)
/*     */     {
/*  91 */       switch (btn.field_146127_k) {
/*     */         
/*     */         case 256:
/*  94 */           MCH_PacketIndMultiplayCommand.send(256, "");
/*     */           break;
/*     */         case 768:
/*  97 */           MCH_PacketIndMultiplayCommand.send(512, "");
/*     */           break;
/*     */         case 512:
/* 100 */           switchScreen(MCH_GuiScoreboard_Base.SCREEN_ID.CREATE_TEAM);
/*     */           break;
/*     */         case 1024:
/* 103 */           MCH_PacketIndMultiplayCommand.send(1024, "");
/*     */           break;
/*     */         case 1280:
/* 106 */           MCH_PacketIndMultiplayCommand.send(1280, "");
/*     */           break;
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawGuiContainerForegroundLayerScreen(int x, int y) {
/* 115 */     super.drawGuiContainerForegroundLayerScreen(x, y);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_146976_a(float par1, int par2, int par3) {
/* 121 */     drawList(this.field_146297_k, this.field_146289_q, true);
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\multiplay\MCH_GuiScoreboard_Main.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */