/*     */ package mcheli.aircraft;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import mcheli.MCH_PacketIndOpenScreen;
/*     */ import mcheli.command.MCH_PacketCommandSave;
/*     */ import mcheli.multiplay.MCH_PacketIndMultiplayCommand;
/*     */ import mcheli.weapon.MCH_WeaponInfo;
/*     */ import mcheli.weapon.MCH_WeaponSet;
/*     */ import mcheli.wrapper.W_GuiContainer;
/*     */ import mcheli.wrapper.W_McClient;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.gui.GuiTextField;
/*     */ import net.minecraft.entity.player.EntityPlayer;
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
/*     */ public class MCH_AircraftGui
/*     */   extends W_GuiContainer
/*     */ {
/*     */   private final EntityPlayer thePlayer;
/*     */   private final MCH_EntityAircraft aircraft;
/*     */   private GuiButton buttonReload;
/*     */   private GuiButton buttonNext;
/*     */   private GuiButton buttonPrev;
/*     */   private GuiButton buttonInventory;
/*     */   private int currentWeaponId;
/*     */   private int reloadWait;
/*     */   private GuiTextField editCommand;
/*     */   public static final int BUTTON_RELOAD = 1;
/*     */   public static final int BUTTON_NEXT = 2;
/*     */   public static final int BUTTON_PREV = 3;
/*     */   public static final int BUTTON_CLOSE = 4;
/*     */   public static final int BUTTON_CONFIG = 5;
/*     */   public static final int BUTTON_INVENTORY = 6;
/*     */   
/*     */   public MCH_AircraftGui(EntityPlayer player, MCH_EntityAircraft ac) {
/*  47 */     super(new MCH_AircraftGuiContainer(player, ac));
/*  48 */     this.aircraft = ac;
/*  49 */     this.thePlayer = player;
/*  50 */     this.field_146999_f = 210;
/*  51 */     this.field_147000_g = 236;
/*  52 */     this.buttonReload = null;
/*  53 */     this.currentWeaponId = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_73866_w_() {
/*  59 */     super.func_73866_w_();
/*  60 */     this.field_146292_n.clear();
/*     */     
/*  62 */     this.buttonReload = new GuiButton(1, this.field_147003_i + 85, this.field_147009_r + 40, 50, 20, "Reload");
/*  63 */     this.buttonNext = new GuiButton(3, this.field_147003_i + 140, this.field_147009_r + 40, 20, 20, "<<");
/*  64 */     this.buttonPrev = new GuiButton(2, this.field_147003_i + 160, this.field_147009_r + 40, 20, 20, ">>");
/*  65 */     this.buttonReload.field_146124_l = canReload(this.thePlayer);
/*  66 */     this.buttonNext.field_146124_l = (this.aircraft.getWeaponNum() >= 2);
/*  67 */     this.buttonPrev.field_146124_l = (this.aircraft.getWeaponNum() >= 2);
/*     */     
/*  69 */     this.buttonInventory = new GuiButton(6, this.field_147003_i + 210 - 30 - 60, this.field_147009_r + 90, 80, 20, "Inventory");
/*  70 */     this.field_146292_n.add(new GuiButton(5, this.field_147003_i + 210 - 30 - 60, this.field_147009_r + 110, 80, 20, "MOD Options"));
/*  71 */     this.field_146292_n.add(new GuiButton(4, this.field_147003_i + 210 - 30 - 20, this.field_147009_r + 10, 40, 20, "Close"));
/*     */     
/*  73 */     this.field_146292_n.add(this.buttonReload);
/*  74 */     this.field_146292_n.add(this.buttonNext);
/*  75 */     this.field_146292_n.add(this.buttonPrev);
/*     */     
/*  77 */     if (this.aircraft != null && this.aircraft.func_70302_i_() > 0)
/*     */     {
/*  79 */       this.field_146292_n.add(this.buttonInventory);
/*     */     }
/*     */ 
/*     */     
/*  83 */     this.editCommand = new GuiTextField(0, this.field_146289_q, this.field_147003_i + 25, this.field_147009_r + 215, 160, 15);
/*  84 */     this.editCommand.func_146180_a(this.aircraft.getCommand());
/*  85 */     this.editCommand.func_146203_f(512);
/*     */     
/*  87 */     this.currentWeaponId = 0;
/*     */     
/*  89 */     this.reloadWait = 10;
/*     */   }
/*     */ 
/*     */   
/*     */   public void closeScreen() {
/*  94 */     MCH_PacketCommandSave.send(this.editCommand.func_146179_b());
/*  95 */     this.field_146297_k.field_71439_g.func_71053_j();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canReload(EntityPlayer player) {
/* 100 */     return this.aircraft.canPlayerSupplyAmmo(player, this.currentWeaponId);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_73876_c() {
/* 106 */     super.func_73876_c();
/* 107 */     if (this.reloadWait > 0) {
/*     */       
/* 109 */       this.reloadWait--;
/* 110 */       if (this.reloadWait == 0) {
/*     */         
/* 112 */         this.buttonReload.field_146124_l = canReload(this.thePlayer);
/* 113 */         this.reloadWait = 20;
/*     */       } 
/*     */     } 
/*     */     
/* 117 */     this.editCommand.func_146178_a();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_73864_a(int mouseX, int mouseY, int mouseButton) throws IOException {
/* 124 */     this.editCommand.func_146192_a(mouseX, mouseY, mouseButton);
/* 125 */     super.func_73864_a(mouseX, mouseY, mouseButton);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_146281_b() {
/* 131 */     super.func_146281_b();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_146284_a(GuiButton button) throws IOException {
/* 138 */     super.func_146284_a(button);
/*     */     
/* 140 */     if (!button.field_146124_l) {
/*     */       return;
/*     */     }
/*     */     
/* 144 */     switch (button.field_146127_k) {
/*     */       
/*     */       case 4:
/* 147 */         closeScreen();
/*     */         break;
/*     */       case 1:
/* 150 */         this.buttonReload.field_146124_l = canReload(this.thePlayer);
/* 151 */         if (this.buttonReload.field_146124_l) {
/*     */           
/* 153 */           MCH_PacketIndReload.send(this.aircraft, this.currentWeaponId);
/* 154 */           this.aircraft.supplyAmmo(this.currentWeaponId);
/* 155 */           this.reloadWait = 3;
/* 156 */           this.buttonReload.field_146124_l = false;
/*     */         } 
/*     */         break;
/*     */       case 2:
/* 160 */         this.currentWeaponId++;
/* 161 */         if (this.currentWeaponId >= this.aircraft.getWeaponNum())
/*     */         {
/* 163 */           this.currentWeaponId = 0;
/*     */         }
/* 165 */         this.buttonReload.field_146124_l = canReload(this.thePlayer);
/*     */         break;
/*     */       case 3:
/* 168 */         this.currentWeaponId--;
/* 169 */         if (this.currentWeaponId < 0)
/*     */         {
/* 171 */           this.currentWeaponId = this.aircraft.getWeaponNum() - 1;
/*     */         }
/* 173 */         this.buttonReload.field_146124_l = canReload(this.thePlayer);
/*     */         break;
/*     */       case 5:
/* 176 */         MCH_PacketIndOpenScreen.send(2);
/*     */         break;
/*     */       case 6:
/* 179 */         MCH_PacketIndOpenScreen.send(3);
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_146979_b(int par1, int par2) {
/* 187 */     super.func_146979_b(par1, par2);
/* 188 */     MCH_EntityAircraft ac = this.aircraft;
/* 189 */     drawString(ac.getGuiInventory().getInventoryName(), 10, 10, 16777215);
/*     */     
/* 191 */     if (this.aircraft.getNumEjectionSeat() > 0)
/*     */     {
/* 193 */       drawString("Parachute", 9, 95, 16777215);
/*     */     }
/*     */     
/* 196 */     if (this.aircraft.getWeaponNum() > 0) {
/*     */       
/* 198 */       MCH_WeaponSet ws = this.aircraft.getWeapon(this.currentWeaponId);
/* 199 */       if (ws != null && !(ws.getFirstWeapon() instanceof mcheli.weapon.MCH_WeaponDummy)) {
/*     */         
/* 201 */         drawString(ws.getName(), 79, 30, 16777215);
/*     */         
/* 203 */         int rest = ws.getRestAllAmmoNum() + ws.getAmmoNum();
/* 204 */         int color = (rest == ws.getAllAmmoNum()) ? 2675784 : ((rest == 0) ? 16711680 : 16777215);
/* 205 */         String s = String.format("%4d/%4d", new Object[] {
/*     */               
/* 207 */               Integer.valueOf(rest), Integer.valueOf(ws.getAllAmmoNum())
/*     */             });
/* 209 */         drawString(s, 145, 70, color);
/*     */         
/* 211 */         int itemPosX = 90;
/* 212 */         for (MCH_WeaponInfo.RoundItem r : (ws.getInfo()).roundItems) {
/*     */           
/* 214 */           drawString("" + r.num, itemPosX, 80, 16777215);
/* 215 */           itemPosX += 20;
/*     */         } 
/*     */         
/* 218 */         itemPosX = 85;
/* 219 */         for (MCH_WeaponInfo.RoundItem r : (ws.getInfo()).roundItems)
/*     */         {
/* 221 */           drawItemStack(r.itemStack, itemPosX, 62);
/* 222 */           itemPosX += 20;
/*     */         }
/*     */       
/*     */       } 
/*     */     } else {
/*     */       
/* 228 */       drawString("None", 79, 45, 16777215);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_73869_a(char c, int code) {
/* 235 */     if (code == 1) {
/*     */       
/* 237 */       closeScreen();
/*     */     }
/* 239 */     else if (code == 28) {
/*     */       
/* 241 */       String s = this.editCommand.func_146179_b().trim();
/* 242 */       if (s.startsWith("/"))
/*     */       {
/* 244 */         s = s.substring(1);
/*     */       }
/* 246 */       if (!s.isEmpty())
/*     */       {
/* 248 */         MCH_PacketIndMultiplayCommand.send(768, s);
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 253 */       this.editCommand.func_146201_a(c, code);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_146976_a(float var1, int var2, int var3) {
/* 263 */     W_McClient.MOD_bindTexture("textures/gui/gui.png");
/* 264 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 265 */     int x = (this.field_146294_l - this.field_146999_f) / 2;
/* 266 */     int y = (this.field_146295_m - this.field_147000_g) / 2;
/* 267 */     func_73729_b(x, y, 0, 0, this.field_146999_f, this.field_147000_g);
/*     */     
/* 269 */     for (int i = 0; i < this.aircraft.getNumEjectionSeat(); i++)
/*     */     {
/* 271 */       func_73729_b(x + 10 + 18 * i - 1, y + 105 - 1, 215, 55, 18, 18);
/*     */     }
/*     */     
/* 274 */     int ff = (int)(this.aircraft.getFuelP() * 50.0F);
/* 275 */     if (ff >= 99)
/* 276 */       ff = 100; 
/* 277 */     func_73729_b(x + 57, y + 30 + 50 - ff, 215, 0, 12, ff);
/*     */     
/* 279 */     ff = (int)((this.aircraft.getFuelP() * 100.0F) + 0.5D);
/* 280 */     int color = (ff > 20) ? -14101432 : 16711680;
/* 281 */     drawString(String.format("%3d", new Object[] {
/*     */             
/* 283 */             Integer.valueOf(ff)
/*     */           }) + "%", x + 30, y + 65, color);
/*     */     
/* 286 */     this.editCommand.func_146194_f();
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\aircraft\MCH_AircraftGui.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */