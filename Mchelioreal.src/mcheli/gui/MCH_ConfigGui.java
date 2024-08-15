/*     */ package mcheli.gui;
/*     */ 
/*     */ import com.google.common.collect.Sets;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import mcheli.MCH_ClientCommonTickHandler;
/*     */ import mcheli.MCH_Config;
/*     */ import mcheli.MCH_Lib;
/*     */ import mcheli.MCH_MOD;
/*     */ import mcheli.__helper.info.ContentRegistries;
/*     */ import mcheli.aircraft.MCH_AircraftInfo;
/*     */ import mcheli.aircraft.MCH_EntityAircraft;
/*     */ import mcheli.aircraft.MCH_PacketNotifyInfoReloaded;
/*     */ import mcheli.multiplay.MCH_GuiTargetMarker;
/*     */ import mcheli.weapon.MCH_WeaponInfo;
/*     */ import mcheli.wrapper.W_GuiButton;
/*     */ import mcheli.wrapper.W_GuiContainer;
/*     */ import mcheli.wrapper.W_McClient;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.renderer.BufferBuilder;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import org.lwjgl.input.Mouse;
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
/*     */ public class MCH_ConfigGui
/*     */   extends W_GuiContainer
/*     */ {
/*     */   private final EntityPlayer thePlayer;
/*     */   private MCH_GuiOnOffButton buttonMouseInv;
/*     */   private MCH_GuiOnOffButton buttonStickModeHeli;
/*     */   private MCH_GuiOnOffButton buttonStickModePlane;
/*     */   private MCH_GuiOnOffButton buttonHideKeyBind;
/*     */   private MCH_GuiOnOffButton buttonShowHUDTP;
/*     */   private MCH_GuiOnOffButton buttonSmoothShading;
/*     */   private MCH_GuiOnOffButton buttonShowEntityMarker;
/*     */   private MCH_GuiOnOffButton buttonMarkThroughWall;
/*     */   private MCH_GuiOnOffButton buttonReplaceCamera;
/*     */   private MCH_GuiOnOffButton buttonNewExplosion;
/*     */   private MCH_GuiSlider sliderEntityMarkerSize;
/*     */   private MCH_GuiSlider sliderBlockMarkerSize;
/*     */   private MCH_GuiSlider sliderSensitivity;
/*     */   private MCH_GuiSlider[] sliderHitMark;
/*     */   private MCH_GuiOnOffButton buttonTestMode;
/*     */   private MCH_GuiOnOffButton buttonThrottleHeli;
/*     */   private MCH_GuiOnOffButton buttonThrottlePlane;
/*     */   private MCH_GuiOnOffButton buttonThrottleTank;
/*     */   private MCH_GuiOnOffButton buttonFlightSimMode;
/*     */   private MCH_GuiOnOffButton buttonSwitchWeaponWheel;
/*     */   private W_GuiButton buttonReloadAircraftInfo;
/*     */   private W_GuiButton buttonReloadWeaponInfo;
/*     */   private W_GuiButton buttonReloadAllHUD;
/*     */   private MCH_GuiSlider __sliderTextureAlpha;
/*     */   public List<W_GuiButton> listControlButtons;
/*     */   public List<W_GuiButton> listRenderButtons;
/*     */   public List<W_GuiButton> listKeyBindingButtons;
/*     */   public List<W_GuiButton> listDevelopButtons;
/*     */   public MCH_GuiList keyBindingList;
/*     */   public int waitKeyButtonId;
/*     */   public int waitKeyAcceptCount;
/*     */   public static final int BUTTON_RENDER = 50;
/*     */   public static final int BUTTON_KEY_BINDING = 51;
/*     */   public static final int BUTTON_PREV_CONTROL = 52;
/*     */   public static final int BUTTON_DEVELOP = 55;
/*     */   public static final int BUTTON_KEY_LIST = 53;
/*     */   public static final int BUTTON_KEY_RESET_ALL = 54;
/*     */   public static final int BUTTON_KEY_LIST_BASE = 200;
/*     */   public static final int BUTTON_KEY_RESET_BASE = 300;
/*     */   public static final int BUTTON_DEV_RELOAD_AC = 400;
/*     */   public static final int BUTTON_DEV_RELOAD_WEAPON = 401;
/*     */   public static final int BUTTON_DEV_RELOAD_HUD = 402;
/*     */   public static final int BUTTON_SAVE_CLOSE = 100;
/*     */   public static final int BUTTON_CANCEL = 101;
/*  90 */   public int currentScreenId = 0;
/*     */   
/*     */   public static final int SCREEN_CONTROLS = 0;
/*     */   public static final int SCREEN_RENDER = 1;
/*     */   public static final int SCREEN_KEY_BIND = 2;
/*     */   public static final int SCREEN_DEVELOP = 3;
/*  96 */   private int ignoreButtonCounter = 0;
/*     */ 
/*     */   
/*     */   public MCH_ConfigGui(EntityPlayer player) {
/* 100 */     super(new MCH_ConfigGuiContainer(player));
/* 101 */     this.thePlayer = player;
/* 102 */     this.field_146999_f = 330;
/* 103 */     this.field_147000_g = 200;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_73866_w_() {
/* 109 */     super.func_73866_w_();
/* 110 */     this.field_146292_n.clear();
/*     */     
/* 112 */     int x1 = this.field_147003_i + 10;
/* 113 */     int x2 = this.field_147003_i + 10 + 150 + 10;
/* 114 */     int y = this.field_147009_r;
/*     */ 
/*     */     
/* 117 */     this.listControlButtons = new ArrayList<>();
/*     */     
/* 119 */     this.buttonMouseInv = new MCH_GuiOnOffButton(0, x1, y + 25, 150, 20, "Invert Mouse : ");
/* 120 */     this.sliderSensitivity = new MCH_GuiSlider(0, x1, y + 50, 150, 20, "Sensitivity : %.1f", 0.0F, 0.0F, 30.0F, 0.1F);
/*     */ 
/*     */     
/* 123 */     this.buttonFlightSimMode = new MCH_GuiOnOffButton(0, x1, y + 75, 150, 20, "Mouse Flight Sim Mode : ");
/* 124 */     this.buttonSwitchWeaponWheel = new MCH_GuiOnOffButton(0, x1, y + 100, 150, 20, "Switch Weapon Wheel : ");
/* 125 */     this.listControlButtons.add(new W_GuiButton(50, x1, y + 125, 150, 20, "Render Settings >>"));
/* 126 */     this.listControlButtons.add(new W_GuiButton(51, x1, y + 150, 150, 20, "Key Binding >>"));
/* 127 */     this.listControlButtons.add(new W_GuiButton(55, x2, y + 150, 150, 20, "Development >>"));
/* 128 */     this.buttonTestMode = new MCH_GuiOnOffButton(0, x1, y + 175, 150, 20, "Test Mode : ");
/*     */     
/* 130 */     this.buttonStickModeHeli = new MCH_GuiOnOffButton(0, x2, y + 25, 150, 20, "Stick Mode Heli : ");
/* 131 */     this.buttonStickModePlane = new MCH_GuiOnOffButton(0, x2, y + 50, 150, 20, "Stick Mode Plane : ");
/* 132 */     this.buttonThrottleHeli = new MCH_GuiOnOffButton(0, x2, y + 75, 150, 20, "Throttle Down Heli : ");
/* 133 */     this.buttonThrottlePlane = new MCH_GuiOnOffButton(0, x2, y + 100, 150, 20, "Throttle Down Plane : ");
/* 134 */     this.buttonThrottleTank = new MCH_GuiOnOffButton(0, x2, y + 125, 150, 20, "Throttle Down Tank : ");
/*     */     
/* 136 */     this.listControlButtons.add(this.buttonMouseInv);
/* 137 */     this.listControlButtons.add(this.buttonStickModeHeli);
/* 138 */     this.listControlButtons.add(this.buttonStickModePlane);
/* 139 */     this.listControlButtons.add(this.sliderSensitivity);
/* 140 */     this.listControlButtons.add(this.buttonThrottleHeli);
/* 141 */     this.listControlButtons.add(this.buttonThrottlePlane);
/* 142 */     this.listControlButtons.add(this.buttonThrottleTank);
/* 143 */     this.listControlButtons.add(this.buttonTestMode);
/* 144 */     this.listControlButtons.add(this.buttonFlightSimMode);
/* 145 */     this.listControlButtons.add(this.buttonSwitchWeaponWheel);
/*     */     
/* 147 */     for (GuiButton b : this.listControlButtons)
/*     */     {
/* 149 */       this.field_146292_n.add(b);
/*     */     }
/*     */     
/* 152 */     this.listRenderButtons = new ArrayList<>();
/*     */     
/* 154 */     this.buttonShowHUDTP = new MCH_GuiOnOffButton(0, x1, y + 25, 150, 20, "Show HUD Third Person : ");
/* 155 */     this.buttonHideKeyBind = new MCH_GuiOnOffButton(0, x1, y + 50, 150, 20, "Hide Key Binding : ");
/*     */     
/* 157 */     this.sliderHitMark = new MCH_GuiSlider[] { new MCH_GuiSlider(0, x1 + 0, y + 125, 75, 20, "Alpha:%.0f", 0.0F, 0.0F, 255.0F, 16.0F), new MCH_GuiSlider(0, x1 + 75, y + 75, 75, 20, "Red:%.0f", 0.0F, 0.0F, 255.0F, 16.0F), new MCH_GuiSlider(0, x1 + 75, y + 100, 75, 20, "Green:%.0f", 0.0F, 0.0F, 255.0F, 16.0F), new MCH_GuiSlider(0, x1 + 75, y + 125, 75, 20, "Blue:%.0f", 0.0F, 0.0F, 255.0F, 16.0F) };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 165 */     this.buttonReplaceCamera = new MCH_GuiOnOffButton(0, x1, y + 150, 150, 20, "Change Camera Pos : ");
/* 166 */     this.listRenderButtons.add(new W_GuiButton(52, x1, y + 175, 90, 20, "Controls <<"));
/*     */     
/* 168 */     this.buttonSmoothShading = new MCH_GuiOnOffButton(0, x2, y + 25, 150, 20, "Smooth Shading : ");
/* 169 */     this.buttonShowEntityMarker = new MCH_GuiOnOffButton(0, x2, y + 50, 150, 20, "Show Entity Maker : ");
/* 170 */     this.sliderEntityMarkerSize = new MCH_GuiSlider(0, x2 + 30, y + 75, 120, 20, "Entity Marker Size:%.0f", 10.0F, 0.0F, 30.0F, 1.0F);
/*     */     
/* 172 */     this.sliderBlockMarkerSize = new MCH_GuiSlider(0, x2 + 60, y + 100, 90, 20, "Block Marker Size:%.0f", 10.0F, 0.0F, 20.0F, 1.0F);
/*     */     
/* 174 */     this.buttonMarkThroughWall = new MCH_GuiOnOffButton(0, x2 + 30, y + 100, 120, 20, "Mark Through Wall : ");
/* 175 */     this.buttonNewExplosion = new MCH_GuiOnOffButton(0, x2, y + 150, 150, 20, "Default Explosion : ");
/*     */     
/* 177 */     this.listRenderButtons.add(this.buttonShowHUDTP);
/* 178 */     for (int i = 0; i < this.sliderHitMark.length; i++)
/* 179 */       this.listRenderButtons.add(this.sliderHitMark[i]); 
/* 180 */     this.listRenderButtons.add(this.buttonSmoothShading);
/* 181 */     this.listRenderButtons.add(this.buttonHideKeyBind);
/* 182 */     this.listRenderButtons.add(this.buttonShowEntityMarker);
/*     */     
/* 184 */     this.listRenderButtons.add(this.buttonReplaceCamera);
/* 185 */     this.listRenderButtons.add(this.buttonNewExplosion);
/* 186 */     this.listRenderButtons.add(this.sliderEntityMarkerSize);
/* 187 */     this.listRenderButtons.add(this.sliderBlockMarkerSize);
/*     */     
/* 189 */     for (GuiButton b : this.listRenderButtons)
/*     */     {
/* 191 */       this.field_146292_n.add(b);
/*     */     }
/*     */     
/* 194 */     this.listKeyBindingButtons = new ArrayList<>();
/*     */     
/* 196 */     this.waitKeyButtonId = 0;
/* 197 */     this.waitKeyAcceptCount = 0;
/*     */     
/* 199 */     this.keyBindingList = new MCH_GuiList(53, 7, x1, y + 25 - 2, 310, 150, "");
/* 200 */     this.listKeyBindingButtons.add(this.keyBindingList);
/*     */     
/* 202 */     this.listKeyBindingButtons.add(new W_GuiButton(52, x1, y + 175, 90, 20, "Controls <<"));
/* 203 */     this.listKeyBindingButtons.add(new W_GuiButton(54, x1 + 90, y + 175, 60, 20, "Reset All"));
/*     */ 
/*     */ 
/*     */     
/* 207 */     MCH_GuiListItemKeyBind[] listKeyBindItems = { new MCH_GuiListItemKeyBind(200, 300, x1, "Up", MCH_Config.KeyUp), new MCH_GuiListItemKeyBind(201, 301, x1, "Down", MCH_Config.KeyDown), new MCH_GuiListItemKeyBind(202, 302, x1, "Right", MCH_Config.KeyRight), new MCH_GuiListItemKeyBind(203, 303, x1, "Left", MCH_Config.KeyLeft), new MCH_GuiListItemKeyBind(204, 304, x1, "Switch Gunner", MCH_Config.KeySwitchMode), new MCH_GuiListItemKeyBind(205, 305, x1, "Switch Hovering", MCH_Config.KeySwitchHovering), new MCH_GuiListItemKeyBind(206, 306, x1, "Switch Weapon1", MCH_Config.KeySwitchWeapon1), new MCH_GuiListItemKeyBind(207, 307, x1, "Switch Weapon2", MCH_Config.KeySwitchWeapon2), new MCH_GuiListItemKeyBind(208, 308, x1, "Switch Weapon Mode", MCH_Config.KeySwWeaponMode), new MCH_GuiListItemKeyBind(209, 309, x1, "Zoom / Fold Wing", MCH_Config.KeyZoom), new MCH_GuiListItemKeyBind(210, 310, x1, "Camera Mode", MCH_Config.KeyCameraMode), new MCH_GuiListItemKeyBind(211, 311, x1, "Unmount Mobs", MCH_Config.KeyUnmount), new MCH_GuiListItemKeyBind(212, 312, x1, "Flare", MCH_Config.KeyFlare), new MCH_GuiListItemKeyBind(213, 313, x1, "Vtol / Drop / Fold Blade", MCH_Config.KeyExtra), new MCH_GuiListItemKeyBind(214, 314, x1, "Third Person Distance Up", MCH_Config.KeyCameraDistUp), new MCH_GuiListItemKeyBind(215, 315, x1, "Third Person Distance Down", MCH_Config.KeyCameraDistDown), new MCH_GuiListItemKeyBind(216, 316, x1, "Switch Free Look", MCH_Config.KeyFreeLook), new MCH_GuiListItemKeyBind(217, 317, x1, "Open GUI", MCH_Config.KeyGUI), new MCH_GuiListItemKeyBind(218, 318, x1, "Gear Up Down", MCH_Config.KeyGearUpDown), new MCH_GuiListItemKeyBind(219, 319, x1, "Put entity in the rack", MCH_Config.KeyPutToRack), new MCH_GuiListItemKeyBind(220, 320, x1, "Drop entity from the rack", MCH_Config.KeyDownFromRack), new MCH_GuiListItemKeyBind(221, 321, x1, "[MP]Score board", MCH_Config.KeyScoreboard), new MCH_GuiListItemKeyBind(222, 322, x1, "[MP][OP]Multiplay manager", MCH_Config.KeyMultiplayManager) };
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
/* 236 */     for (MCH_GuiListItemKeyBind item : listKeyBindItems)
/*     */     {
/* 238 */       this.keyBindingList.addItem(item);
/*     */     }
/*     */     
/* 241 */     for (GuiButton b : this.listKeyBindingButtons)
/*     */     {
/* 243 */       this.field_146292_n.add(b);
/*     */     }
/*     */     
/* 246 */     this.listDevelopButtons = new ArrayList<>();
/*     */     
/* 248 */     if (Minecraft.func_71410_x().func_71356_B()) {
/*     */       
/* 250 */       this.buttonReloadAircraftInfo = new W_GuiButton(400, x1, y + 50, 150, 20, "Reload aircraft setting");
/* 251 */       this.buttonReloadWeaponInfo = new W_GuiButton(401, x1, y + 75, 150, 20, "Reload All Weapons");
/* 252 */       this.buttonReloadAllHUD = new W_GuiButton(402, x1, y + 100, 150, 20, "Reload All HUD");
/* 253 */       this.__sliderTextureAlpha = new MCH_GuiSlider(432, x1, y + 125, 150, 20, "Texture Alpha:%.0f", 1.0F, 0.0F, 255.0F, 1.0F);
/*     */       
/* 255 */       this.listDevelopButtons.add(this.buttonReloadAircraftInfo);
/* 256 */       this.listDevelopButtons.add(this.buttonReloadWeaponInfo);
/* 257 */       this.listDevelopButtons.add(this.buttonReloadAllHUD);
/* 258 */       this.listDevelopButtons.add(this.__sliderTextureAlpha);
/*     */     } 
/*     */     
/* 261 */     this.listDevelopButtons.add(new W_GuiButton(52, x1, y + 175, 90, 20, "Controls <<"));
/*     */     
/* 263 */     for (GuiButton b : this.listDevelopButtons)
/*     */     {
/* 265 */       this.field_146292_n.add(b);
/*     */     }
/*     */     
/* 268 */     this.field_146292_n.add(new GuiButton(100, x2, y + 175, 80, 20, "Save & Close"));
/* 269 */     this.field_146292_n.add(new GuiButton(101, x2 + 90, y + 175, 60, 20, "Cancel"));
/*     */     
/* 271 */     switchScreen(0);
/* 272 */     applySwitchScreen();
/*     */     
/* 274 */     getAllStatusFromConfig();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canButtonClick() {
/* 279 */     return (this.ignoreButtonCounter <= 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void getAllStatusFromConfig() {
/* 284 */     this.buttonMouseInv.setOnOff(MCH_Config.InvertMouse.prmBool);
/* 285 */     this.buttonStickModeHeli.setOnOff(MCH_Config.MouseControlStickModeHeli.prmBool);
/* 286 */     this.buttonStickModePlane.setOnOff(MCH_Config.MouseControlStickModePlane.prmBool);
/* 287 */     this.sliderSensitivity.setSliderValue((float)MCH_Config.MouseSensitivity.prmDouble);
/* 288 */     this.buttonShowHUDTP.setOnOff(MCH_Config.DisplayHUDThirdPerson.prmBool);
/* 289 */     this.buttonSmoothShading.setOnOff(MCH_Config.SmoothShading.prmBool);
/* 290 */     this.buttonHideKeyBind.setOnOff(MCH_Config.HideKeybind.prmBool);
/* 291 */     this.buttonShowEntityMarker.setOnOff(MCH_Config.DisplayEntityMarker.prmBool);
/* 292 */     this.buttonMarkThroughWall.setOnOff(MCH_Config.DisplayMarkThroughWall.prmBool);
/* 293 */     this.sliderEntityMarkerSize.setSliderValue((float)MCH_Config.EntityMarkerSize.prmDouble);
/* 294 */     this.sliderBlockMarkerSize.setSliderValue((float)MCH_Config.BlockMarkerSize.prmDouble);
/* 295 */     this.buttonReplaceCamera.setOnOff(MCH_Config.ReplaceRenderViewEntity.prmBool);
/* 296 */     this.buttonNewExplosion.setOnOff(MCH_Config.DefaultExplosionParticle.prmBool);
/* 297 */     this.sliderHitMark[0].setSliderValue(MCH_Config.hitMarkColorAlpha * 255.0F);
/* 298 */     this.sliderHitMark[1].setSliderValue((MCH_Config.hitMarkColorRGB >> 16 & 0xFF));
/* 299 */     this.sliderHitMark[2].setSliderValue((MCH_Config.hitMarkColorRGB >> 8 & 0xFF));
/* 300 */     this.sliderHitMark[3].setSliderValue((MCH_Config.hitMarkColorRGB >> 0 & 0xFF));
/* 301 */     this.buttonThrottleHeli.setOnOff(MCH_Config.AutoThrottleDownHeli.prmBool);
/* 302 */     this.buttonThrottlePlane.setOnOff(MCH_Config.AutoThrottleDownPlane.prmBool);
/* 303 */     this.buttonThrottleTank.setOnOff(MCH_Config.AutoThrottleDownTank.prmBool);
/* 304 */     this.buttonTestMode.setOnOff(MCH_Config.TestMode.prmBool);
/* 305 */     this.buttonFlightSimMode.setOnOff(MCH_Config.MouseControlFlightSimMode.prmBool);
/* 306 */     this.buttonSwitchWeaponWheel.setOnOff(MCH_Config.SwitchWeaponWithMouseWheel.prmBool);
/*     */     
/* 308 */     if (this.__sliderTextureAlpha != null)
/*     */     {
/* 310 */       this.__sliderTextureAlpha.setSliderValue((float)MCH_Config.__TextureAlpha.prmDouble * 255.0F);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void saveAndApplyConfig() {
/* 317 */     MCH_Config.InvertMouse.setPrm(this.buttonMouseInv.getOnOff());
/* 318 */     MCH_Config.MouseControlStickModeHeli.setPrm(this.buttonStickModeHeli.getOnOff());
/* 319 */     MCH_Config.MouseControlStickModePlane.setPrm(this.buttonStickModePlane.getOnOff());
/* 320 */     MCH_Config.MouseControlFlightSimMode.setPrm(this.buttonFlightSimMode.getOnOff());
/* 321 */     MCH_Config.SwitchWeaponWithMouseWheel.setPrm(this.buttonSwitchWeaponWheel.getOnOff());
/* 322 */     MCH_Config.MouseSensitivity.setPrm(this.sliderSensitivity.getSliderValueInt(1));
/* 323 */     MCH_Config.DisplayHUDThirdPerson.setPrm(this.buttonShowHUDTP.getOnOff());
/* 324 */     MCH_Config.SmoothShading.setPrm(this.buttonSmoothShading.getOnOff());
/* 325 */     MCH_Config.HideKeybind.setPrm(this.buttonHideKeyBind.getOnOff());
/* 326 */     MCH_Config.DisplayEntityMarker.setPrm(this.buttonShowEntityMarker.getOnOff());
/* 327 */     MCH_Config.DisplayMarkThroughWall.setPrm(this.buttonMarkThroughWall.getOnOff());
/* 328 */     MCH_Config.EntityMarkerSize.setPrm(this.sliderEntityMarkerSize.getSliderValueInt(1));
/* 329 */     MCH_Config.BlockMarkerSize.setPrm(this.sliderBlockMarkerSize.getSliderValueInt(1));
/* 330 */     MCH_Config.ReplaceRenderViewEntity.setPrm(this.buttonReplaceCamera.getOnOff());
/* 331 */     MCH_Config.DefaultExplosionParticle.setPrm(this.buttonNewExplosion.getOnOff());
/*     */     
/* 333 */     float a = this.sliderHitMark[0].getSliderValue();
/* 334 */     int r = (int)this.sliderHitMark[1].getSliderValue();
/* 335 */     int g = (int)this.sliderHitMark[2].getSliderValue();
/* 336 */     int b = (int)this.sliderHitMark[3].getSliderValue();
/* 337 */     MCH_Config.hitMarkColorAlpha = a / 255.0F;
/* 338 */     MCH_Config.hitMarkColorRGB = r << 16 | g << 8 | b;
/* 339 */     MCH_Config.HitMarkColor.setPrm(String.format("%d, %d, %d, %d", new Object[] {
/*     */             
/* 341 */             Integer.valueOf((int)a), Integer.valueOf(r), Integer.valueOf(g), Integer.valueOf(b)
/*     */           }));
/*     */     
/* 344 */     boolean b1 = MCH_Config.AutoThrottleDownHeli.prmBool;
/* 345 */     boolean b2 = MCH_Config.AutoThrottleDownPlane.prmBool;
/* 346 */     MCH_Config.AutoThrottleDownHeli.setPrm(this.buttonThrottleHeli.getOnOff());
/* 347 */     MCH_Config.AutoThrottleDownPlane.setPrm(this.buttonThrottlePlane.getOnOff());
/* 348 */     MCH_Config.AutoThrottleDownTank.setPrm(this.buttonThrottleTank.getOnOff());
/* 349 */     if (b1 != MCH_Config.AutoThrottleDownHeli.prmBool || b2 != MCH_Config.AutoThrottleDownPlane.prmBool)
/*     */     {
/* 351 */       sendClientSettings();
/*     */     }
/*     */     
/* 354 */     for (int i = 0; i < this.keyBindingList.getItemNum(); i++)
/*     */     {
/* 356 */       ((MCH_GuiListItemKeyBind)this.keyBindingList.getItem(i)).applyKeycode();
/*     */     }
/* 358 */     MCH_ClientCommonTickHandler.instance.updatekeybind(MCH_MOD.config);
/*     */     
/* 360 */     MCH_Config.TestMode.setPrm(this.buttonTestMode.getOnOff());
/*     */     
/* 362 */     if (this.__sliderTextureAlpha != null)
/*     */     {
/* 364 */       MCH_Config.__TextureAlpha.setPrm(this.__sliderTextureAlpha.getSliderValue() / 255.0D);
/*     */     }
/*     */     
/* 367 */     MCH_MOD.config.write();
/*     */   }
/*     */ 
/*     */   
/*     */   public void switchScreen(int screenID) {
/* 372 */     this.waitKeyButtonId = 0;
/* 373 */     this.currentScreenId = screenID;
/*     */     
/* 375 */     for (W_GuiButton b : this.listControlButtons) b.setVisible(false);
/*     */ 
/*     */     
/* 378 */     for (W_GuiButton b : this.listRenderButtons) b.setVisible(false);
/*     */ 
/*     */     
/* 381 */     for (W_GuiButton b : this.listKeyBindingButtons) b.setVisible(false);
/*     */ 
/*     */     
/* 384 */     for (W_GuiButton b : this.listDevelopButtons) b.setVisible(false);
/*     */ 
/*     */ 
/*     */     
/* 388 */     this.ignoreButtonCounter = 3;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void applySwitchScreen() {
/* 394 */     switch (this.currentScreenId) {
/*     */ 
/*     */       
/*     */       case 1:
/* 398 */         for (W_GuiButton b : this.listRenderButtons) b.setVisible(true);
/*     */         
/*     */         return;
/*     */       
/*     */       case 3:
/* 403 */         for (W_GuiButton b : this.listDevelopButtons) b.setVisible(true);
/*     */         
/*     */         return;
/*     */       
/*     */       case 2:
/* 408 */         for (W_GuiButton b : this.listKeyBindingButtons) b.setVisible(true);
/*     */         
/*     */         return;
/*     */     } 
/*     */ 
/*     */     
/* 414 */     for (W_GuiButton b : this.listControlButtons) b.setVisible(true);
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendClientSettings() {
/* 423 */     if (this.field_146297_k.field_71439_g != null) {
/*     */       
/* 425 */       MCH_EntityAircraft ac = MCH_EntityAircraft.getAircraft_RiddenOrControl((Entity)this.field_146297_k.field_71439_g);
/* 426 */       if (ac != null) {
/*     */         
/* 428 */         int seatId = ac.getSeatIdByEntity((Entity)this.field_146297_k.field_71439_g);
/* 429 */         if (seatId == 0)
/*     */         {
/* 431 */           ac.updateClientSettings(seatId);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_73869_a(char a, int code) throws IOException {
/* 441 */     if (this.waitKeyButtonId != 0) {
/*     */       
/* 443 */       if (code != 1)
/*     */       {
/* 445 */         super.func_73869_a(a, code);
/*     */       }
/* 447 */       acceptKeycode(code);
/* 448 */       this.waitKeyButtonId = 0;
/*     */     }
/*     */     else {
/*     */       
/* 452 */       super.func_73869_a(a, code);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_73864_a(int par1, int par2, int par3) throws IOException {
/* 460 */     super.func_73864_a(par1, par2, par3);
/* 461 */     if (this.waitKeyButtonId != 0 && this.waitKeyAcceptCount == 0) {
/*     */       
/* 463 */       acceptKeycode(par3 - 100);
/* 464 */       this.waitKeyButtonId = 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void acceptKeycode(int code) {
/* 470 */     if (code != 1)
/*     */     {
/* 472 */       if (this.field_146297_k.field_71462_r instanceof MCH_ConfigGui) {
/*     */ 
/*     */ 
/*     */         
/* 476 */         MCH_GuiListItemKeyBind kb = (MCH_GuiListItemKeyBind)this.keyBindingList.getItem(this.waitKeyButtonId - 200);
/* 477 */         if (kb != null)
/*     */         {
/* 479 */           kb.setKeycode(code);
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_146274_d() throws IOException {
/* 489 */     super.func_146274_d();
/*     */     
/* 491 */     if (this.waitKeyButtonId != 0) {
/*     */       return;
/*     */     }
/*     */     
/* 495 */     int var16 = Mouse.getEventDWheel();
/* 496 */     if (var16 != 0)
/*     */     {
/* 498 */       if (var16 > 0) {
/*     */         
/* 500 */         this.keyBindingList.scrollDown(2.0F);
/*     */       }
/* 502 */       else if (var16 < 0) {
/*     */         
/* 504 */         this.keyBindingList.scrollUp(2.0F);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_73876_c() {
/* 512 */     super.func_73876_c();
/* 513 */     if (this.waitKeyAcceptCount > 0)
/* 514 */       this.waitKeyAcceptCount--; 
/* 515 */     if (this.ignoreButtonCounter > 0) {
/*     */       
/* 517 */       this.ignoreButtonCounter--;
/* 518 */       if (this.ignoreButtonCounter == 0)
/*     */       {
/* 520 */         applySwitchScreen();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_146281_b() {
/* 528 */     super.func_146281_b(); } protected void func_146284_a(GuiButton button) {
/*     */     try {
/*     */       MCH_EntityAircraft ac;
/*     */       MCH_GuiListItem item;
/*     */       int i;
/*     */       List<Entity> list;
/*     */       Set<String> reloaded;
/*     */       int j;
/* 536 */       super.func_146284_a(button);
/*     */       
/* 538 */       if (!button.field_146124_l) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 543 */       if (this.waitKeyButtonId != 0) {
/*     */         return;
/*     */       }
/* 546 */       if (!canButtonClick()) {
/*     */         return;
/*     */       }
/*     */       
/* 550 */       switch (button.field_146127_k) {
/*     */         
/*     */         case 50:
/* 553 */           switchScreen(1);
/*     */           break;
/*     */         case 51:
/* 556 */           switchScreen(2);
/*     */           break;
/*     */         case 52:
/* 559 */           switchScreen(0);
/*     */           break;
/*     */         case 55:
/* 562 */           switchScreen(3);
/*     */           break;
/*     */         case 100:
/* 565 */           saveAndApplyConfig();
/* 566 */           this.field_146297_k.field_71439_g.func_71053_j();
/*     */           break;
/*     */         case 101:
/* 569 */           this.field_146297_k.field_71439_g.func_71053_j();
/*     */           break;
/*     */         case 53:
/* 572 */           item = this.keyBindingList.lastPushItem;
/* 573 */           if (item != null) {
/*     */             
/* 575 */             MCH_GuiListItemKeyBind kb = (MCH_GuiListItemKeyBind)item;
/* 576 */             if (kb.lastPushButton != null) {
/*     */               
/* 578 */               int kb_num = this.keyBindingList.getItemNum();
/*     */               
/* 580 */               if (kb.lastPushButton.field_146127_k >= 200 && kb.lastPushButton.field_146127_k < 200 + kb_num) {
/*     */                 
/* 582 */                 this.waitKeyButtonId = kb.lastPushButton.field_146127_k;
/* 583 */                 this.waitKeyAcceptCount = 5;
/*     */               }
/* 585 */               else if (kb.lastPushButton.field_146127_k >= 300 && kb.lastPushButton.field_146127_k < 300 + kb_num) {
/*     */                 
/* 587 */                 kb.resetKeycode();
/*     */               } 
/*     */               
/* 590 */               kb.lastPushButton = null;
/*     */             } 
/*     */           } 
/*     */           break;
/*     */         
/*     */         case 54:
/* 596 */           for (i = 0; i < this.keyBindingList.getItemNum(); i++)
/*     */           {
/* 598 */             ((MCH_GuiListItemKeyBind)this.keyBindingList.getItem(i)).resetKeycode();
/*     */           }
/*     */           break;
/*     */ 
/*     */         
/*     */         case 402:
/* 604 */           MCH_MOD.proxy.reloadHUD();
/*     */ 
/*     */         
/*     */         case 400:
/* 608 */           ac = MCH_EntityAircraft.getAircraft_RiddenOrControl((Entity)this.thePlayer);
/*     */           
/* 610 */           if (ac != null && ac.getAcInfo() != null) {
/*     */             
/* 612 */             String name = (ac.getAcInfo()).name;
/* 613 */             MCH_Lib.DbgLog(true, "MCH_BaseInfo.reload : " + name, new Object[0]);
/*     */             
/* 615 */             ContentRegistries.get(ac.getAcInfo().getClass()).reload(name);
/*     */             
/* 617 */             List<Entity> list1 = this.field_146297_k.field_71441_e.field_72996_f;
/* 618 */             for (int k = 0; k < list1.size(); k++) {
/*     */               
/* 620 */               if (list1.get(k) instanceof MCH_EntityAircraft) {
/*     */                 
/* 622 */                 ac = (MCH_EntityAircraft)list1.get(k);
/* 623 */                 if (ac.getAcInfo() != null && (ac.getAcInfo()).name.equals(name)) {
/*     */ 
/*     */                   
/* 626 */                   ac.changeType(name);
/* 627 */                   ac.onAcInfoReloaded();
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */             
/* 632 */             MCH_PacketNotifyInfoReloaded.sendRealodAc();
/*     */           } 
/* 634 */           this.field_146297_k.field_71439_g.func_71053_j();
/*     */           break;
/*     */ 
/*     */         
/*     */         case 401:
/* 639 */           MCH_Lib.DbgLog(true, "MCH_BaseInfo.reload all weapon info.", new Object[0]);
/*     */           
/* 641 */           ContentRegistries.get(MCH_WeaponInfo.class).reloadAll();
/*     */           
/* 643 */           MCH_PacketNotifyInfoReloaded.sendRealodAllWeapon();
/*     */           
/* 645 */           list = this.field_146297_k.field_71441_e.field_72996_f;
/* 646 */           reloaded = Sets.newHashSet();
/*     */           
/* 648 */           for (j = 0; j < list.size(); j++) {
/*     */             
/* 650 */             if (list.get(j) instanceof MCH_EntityAircraft) {
/*     */               
/* 652 */               ac = (MCH_EntityAircraft)list.get(j);
/* 653 */               if (ac.getAcInfo() != null)
/*     */               {
/*     */                 
/* 656 */                 if (!reloaded.contains((ac.getAcInfo()).name)) {
/*     */ 
/*     */ 
/*     */                   
/* 660 */                   ContentRegistries.get(ac.getAcInfo().getClass()).reload((ac.getAcInfo()).name);
/* 661 */                   ac.changeType((ac.getAcInfo()).name);
/* 662 */                   ac.onAcInfoReloaded();
/* 663 */                   reloaded.add((ac.getAcInfo()).name);
/*     */                 }  } 
/*     */             } 
/*     */           } 
/* 667 */           this.field_146297_k.field_71439_g.func_71053_j();
/*     */           break;
/*     */       } 
/*     */     
/* 671 */     } catch (Exception e) {
/*     */       
/* 673 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_73868_f() {
/* 680 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_146979_b(int par1, int par2) {
/* 686 */     super.func_146979_b(par1, par2);
/*     */     
/* 688 */     drawString("MC Helicopter MOD Options", 10, 10, 16777215);
/*     */     
/* 690 */     if (this.currentScreenId == 0) {
/*     */       
/* 692 */       drawString("< Controls >", 170, 10, 16777215);
/*     */     }
/* 694 */     else if (this.currentScreenId == 1) {
/*     */       
/* 696 */       drawString("< Render >", 170, 10, 16777215);
/*     */       
/* 698 */       drawString("Hit Mark", 10, 75, 16777215);
/* 699 */       int color = 0;
/* 700 */       color |= (int)this.sliderHitMark[0].getSliderValue() << 24;
/* 701 */       color |= (int)this.sliderHitMark[1].getSliderValue() << 16;
/* 702 */       color |= (int)this.sliderHitMark[2].getSliderValue() << 8;
/* 703 */       color |= (int)this.sliderHitMark[3].getSliderValue() << 0;
/* 704 */       drawSampleHitMark(40, 105, color);
/*     */       
/* 706 */       double size = this.sliderEntityMarkerSize.getSliderValue();
/* 707 */       double x = 170.0D + (30.0D - size) / 2.0D;
/* 708 */       double y = (this.sliderEntityMarkerSize.field_146129_i - this.sliderEntityMarkerSize.getHeight());
/* 709 */       double[] ls = { x + size, y, x, y, x + size / 2.0D, y + size };
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 714 */       drawLine(ls, -65536, 4);
/*     */       
/* 716 */       size = this.sliderBlockMarkerSize.getSliderValue();
/* 717 */       x = 185.0D;
/* 718 */       y = this.sliderBlockMarkerSize.field_146129_i;
/* 719 */       color = -65536;
/*     */       
/* 721 */       GL11.glPushMatrix();
/*     */       
/* 723 */       GL11.glEnable(3042);
/* 724 */       GL11.glDisable(3553);
/* 725 */       GL11.glBlendFunc(770, 771);
/* 726 */       GL11.glColor4ub((byte)(color >> 16 & 0xFF), (byte)(color >> 8 & 0xFF), (byte)(color >> 0 & 0xFF), (byte)(color >> 24 & 0xFF));
/*     */       
/* 728 */       Tessellator tessellator = Tessellator.func_178181_a();
/* 729 */       BufferBuilder builder = tessellator.func_178180_c();
/*     */       
/* 731 */       builder.func_181668_a(1, DefaultVertexFormats.field_181706_f);
/*     */ 
/*     */       
/* 734 */       MCH_GuiTargetMarker.drawRhombus(builder, 15, x, y, this.field_73735_i, size, color);
/*     */       
/* 736 */       tessellator.func_78381_a();
/*     */       
/* 738 */       GL11.glEnable(3553);
/* 739 */       GL11.glDisable(3042);
/*     */       
/* 741 */       GL11.glColor4b((byte)-1, (byte)-1, (byte)-1, (byte)-1);
/* 742 */       GL11.glPopMatrix();
/*     */     }
/* 744 */     else if (this.currentScreenId == 2) {
/*     */       
/* 746 */       drawString("< Key Binding >", 170, 10, 16777215);
/*     */       
/* 748 */       if (this.waitKeyButtonId != 0)
/*     */       {
/* 750 */         func_73734_a(30, 30, this.field_146999_f - 30, this.field_147000_g - 30, -533712848);
/*     */         
/* 752 */         String msg = "Please ant key or mouse button.";
/* 753 */         int w = getStringWidth(msg);
/* 754 */         drawString(msg, (this.field_146999_f - w) / 2, this.field_147000_g / 2 - 4, 16777215);
/*     */       }
/*     */     
/* 757 */     } else if (this.currentScreenId == 3) {
/*     */       
/* 759 */       drawString("< Development >", 170, 10, 16777215);
/* 760 */       drawString("Single player only!", 10, 30, 16711680);
/*     */       
/* 762 */       if (this.buttonReloadAircraftInfo != null && this.buttonReloadAircraftInfo.isOnMouseOver()) {
/*     */         
/* 764 */         drawString("The following items are not reload.", 170, 30, 16777215);
/* 765 */         String[] ignoreItems = MCH_AircraftInfo.getCannotReloadItem();
/*     */         
/* 767 */         int y = 10;
/* 768 */         for (String s : ignoreItems) {
/*     */           
/* 770 */           drawString("  " + s, 170, 30 + y, 16777215);
/* 771 */           y += 10;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_146976_a(float var1, int var2, int var3) {
/* 783 */     W_McClient.MOD_bindTexture("textures/gui/config.png");
/* 784 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 785 */     int x = (this.field_146294_l - this.field_146999_f) / 2;
/* 786 */     int y = (this.field_146295_m - this.field_147000_g) / 2;
/* 787 */     drawTexturedModalRectRotate(x, y, this.field_146999_f, this.field_147000_g, 0.0D, 0.0D, this.field_146999_f, this.field_147000_g, 0.0F, 512.0D, 256.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawSampleHitMark(int x, int y, int color) {
/* 793 */     int cx = x;
/* 794 */     int cy = y;
/* 795 */     int IVX = 10;
/* 796 */     int IVY = 10;
/* 797 */     int SZX = 5;
/* 798 */     int SZY = 5;
/* 799 */     double[] ls = { (cx - IVX), (cy - IVY), (cx - SZX), (cy - SZY), (cx - IVX), (cy + IVY), (cx - SZX), (cy + SZY), (cx + IVX), (cy - IVY), (cx + SZX), (cy - SZY), (cx + IVX), (cy + IVY), (cx + SZX), (cy + SZY) };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 805 */     drawLine(ls, color, 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawLine(double[] line, int color, int mode) {
/* 810 */     GL11.glPushMatrix();
/*     */     
/* 812 */     GL11.glEnable(3042);
/* 813 */     GL11.glDisable(3553);
/* 814 */     GL11.glBlendFunc(770, 771);
/* 815 */     GL11.glColor4ub((byte)(color >> 16 & 0xFF), (byte)(color >> 8 & 0xFF), (byte)(color >> 0 & 0xFF), (byte)(color >> 24 & 0xFF));
/*     */ 
/*     */     
/* 818 */     Tessellator tessellator = Tessellator.func_178181_a();
/* 819 */     BufferBuilder buffer = tessellator.func_178180_c();
/*     */     
/* 821 */     buffer.func_181668_a(mode, DefaultVertexFormats.field_181705_e);
/*     */     
/* 823 */     for (int i = 0; i < line.length; i += 2)
/*     */     {
/*     */       
/* 826 */       buffer.func_181662_b(line[i + 0], line[i + 1], this.field_73735_i).func_181675_d();
/*     */     }
/*     */     
/* 829 */     tessellator.func_78381_a();
/*     */     
/* 831 */     GL11.glEnable(3553);
/* 832 */     GL11.glDisable(3042);
/*     */     
/* 834 */     GL11.glColor4b((byte)-1, (byte)-1, (byte)-1, (byte)-1);
/* 835 */     GL11.glPopMatrix();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawTexturedModalRectRotate(double left, double top, double width, double height, double uLeft, double vTop, double uWidth, double vHeight, float rot, double texWidth, double texHeight) {
/* 841 */     GL11.glPushMatrix();
/*     */     
/* 843 */     GL11.glTranslated(left + width / 2.0D, top + height / 2.0D, 0.0D);
/* 844 */     GL11.glRotatef(rot, 0.0F, 0.0F, 1.0F);
/*     */     
/* 846 */     float fw = (float)(1.0D / texWidth);
/* 847 */     float fh = (float)(1.0D / texHeight);
/* 848 */     Tessellator tessellator = Tessellator.func_178181_a();
/* 849 */     BufferBuilder buffer = tessellator.func_178180_c();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 856 */     buffer.func_181668_a(7, DefaultVertexFormats.field_181707_g);
/* 857 */     buffer.func_181662_b(-width / 2.0D, height / 2.0D, this.field_73735_i).func_187315_a(uLeft * fw, (vTop + vHeight) * fh).func_181675_d();
/* 858 */     buffer.func_181662_b(width / 2.0D, height / 2.0D, this.field_73735_i).func_187315_a((uLeft + uWidth) * fw, (vTop + vHeight) * fh)
/* 859 */       .func_181675_d();
/* 860 */     buffer.func_181662_b(width / 2.0D, -height / 2.0D, this.field_73735_i).func_187315_a((uLeft + uWidth) * fw, vTop * fh).func_181675_d();
/* 861 */     buffer.func_181662_b(-width / 2.0D, -height / 2.0D, this.field_73735_i).func_187315_a(uLeft * fw, vTop * fh).func_181675_d();
/* 862 */     tessellator.func_78381_a();
/*     */     
/* 864 */     GL11.glPopMatrix();
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\gui\MCH_ConfigGui.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */