/*     */ package mcheli.multiplay;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import mcheli.wrapper.W_GuiButton;
/*     */ import mcheli.wrapper.W_GuiContainer;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.FontRenderer;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.gui.GuiTextField;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import org.lwjgl.input.Keyboard;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MCH_GuiScoreboard
/*     */   extends W_GuiContainer
/*     */   implements MCH_IGuiScoreboard
/*     */ {
/*     */   public final EntityPlayer thePlayer;
/*     */   private MCH_GuiScoreboard_Base.SCREEN_ID screenID;
/*     */   private Map<MCH_GuiScoreboard_Base.SCREEN_ID, MCH_GuiScoreboard_Base> listScreen;
/*  29 */   private int lastTeamNum = 0;
/*     */ 
/*     */   
/*     */   public MCH_GuiScoreboard(EntityPlayer player) {
/*  33 */     super(new MCH_ContainerScoreboard(player));
/*  34 */     this.thePlayer = player;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_73866_w_() {
/*  40 */     Keyboard.enableRepeatEvents(true);
/*  41 */     super.func_73866_w_();
/*     */     
/*  43 */     this.field_146292_n.clear();
/*  44 */     this.field_146293_o.clear();
/*     */     
/*  46 */     this.field_147003_i = 0;
/*  47 */     this.field_147009_r = 0;
/*     */     
/*  49 */     this.listScreen = new HashMap<>();
/*  50 */     this.listScreen.put(MCH_GuiScoreboard_Base.SCREEN_ID.MAIN, new MCH_GuiScoreboard_Main(this, this.thePlayer));
/*  51 */     this.listScreen.put(MCH_GuiScoreboard_Base.SCREEN_ID.CREATE_TEAM, new MCH_GuiScoreboard_CreateTeam(this, this.thePlayer));
/*     */     
/*  53 */     for (MCH_GuiScoreboard_Base s : this.listScreen.values())
/*     */     {
/*  55 */       s.initGui(this.field_146292_n, (GuiScreen)this);
/*     */     }
/*     */     
/*  58 */     this.lastTeamNum = this.field_146297_k.field_71441_e.func_96441_U().func_96525_g().size();
/*     */     
/*  60 */     switchScreen(MCH_GuiScoreboard_Base.SCREEN_ID.MAIN);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_73876_c() {
/*  66 */     super.func_73876_c();
/*     */     
/*  68 */     int nowTeamNum = this.field_146297_k.field_71441_e.func_96441_U().func_96525_g().size();
/*     */     
/*  70 */     if (this.lastTeamNum != nowTeamNum) {
/*     */       
/*  72 */       this.lastTeamNum = nowTeamNum;
/*  73 */       func_73866_w_();
/*     */     } 
/*     */     
/*  76 */     for (MCH_GuiScoreboard_Base s : this.listScreen.values()) {
/*     */ 
/*     */       
/*     */       try {
/*  80 */         s.updateScreenButtons(this.field_146292_n);
/*  81 */         s.func_73876_c();
/*     */       }
/*  83 */       catch (Exception exception) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void switchScreen(MCH_GuiScoreboard_Base.SCREEN_ID id) {
/*  92 */     for (MCH_GuiScoreboard_Base b : this.listScreen.values())
/*     */     {
/*  94 */       b.leaveScreen();
/*     */     }
/*     */     
/*  97 */     this.screenID = id;
/*     */     
/*  99 */     getCurrentScreen().onSwitchScreen();
/*     */   }
/*     */ 
/*     */   
/*     */   private MCH_GuiScoreboard_Base getCurrentScreen() {
/* 104 */     return this.listScreen.get(this.screenID);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void setVisible(Object g, boolean v) {
/* 109 */     if (g instanceof GuiButton) {
/* 110 */       ((GuiButton)g).field_146125_m = v;
/*     */     }
/* 112 */     if (g instanceof GuiTextField) {
/* 113 */       ((GuiTextField)g).func_146189_e(v);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_73869_a(char c, int code) throws IOException {
/* 120 */     getCurrentScreen().keyTypedScreen(c, code);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_73864_a(int mouseX, int mouseY, int mouseButton) throws IOException {
/*     */     try {
/* 129 */       for (MCH_GuiScoreboard_Base s : this.listScreen.values())
/*     */       {
/* 131 */         s.mouseClickedScreen(mouseX, mouseY, mouseButton);
/*     */       }
/*     */       
/* 134 */       super.func_73864_a(mouseX, mouseY, mouseButton);
/*     */     }
/* 136 */     catch (Exception exception) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_146284_a(GuiButton btn) throws IOException {
/* 145 */     if (btn != null && btn.field_146124_l)
/*     */     {
/* 147 */       getCurrentScreen().actionPerformedScreen(btn);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_146276_q_() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_146278_c(int tint) {
/* 159 */     GL11.glDisable(2896);
/* 160 */     GL11.glDisable(2912);
/* 161 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_146979_b(int x, int y) {
/* 167 */     getCurrentScreen().drawGuiContainerForegroundLayerScreen(x, y);
/*     */     
/* 169 */     for (Object o : this.field_146292_n) {
/*     */       
/* 171 */       if (o instanceof W_GuiButton) {
/*     */         
/* 173 */         W_GuiButton btn = (W_GuiButton)o;
/*     */         
/* 175 */         if (btn.isOnMouseOver() && btn.hoverStringList != null) {
/*     */           
/* 177 */           drawHoveringText(btn.hoverStringList, x, y, this.field_146289_q);
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void drawList(Minecraft mc, FontRenderer fontRendererObj, boolean mng) {
/* 186 */     MCH_GuiScoreboard_Base.drawList(mc, fontRendererObj, mng);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_146976_a(float par1, int par2, int par3) {
/* 192 */     getCurrentScreen().func_146976_a(par1, par2, par3);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_146280_a(Minecraft mc, int width, int height) {
/* 198 */     super.func_146280_a(mc, width, height);
/* 199 */     for (MCH_GuiScoreboard_Base s : this.listScreen.values())
/*     */     {
/* 201 */       s.func_146280_a(mc, width, height);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\multiplay\MCH_GuiScoreboard.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */