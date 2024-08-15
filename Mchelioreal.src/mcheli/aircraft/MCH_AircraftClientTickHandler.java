/*     */ package mcheli.aircraft;
/*     */ 
/*     */ import mcheli.MCH_ClientTickHandlerBase;
/*     */ import mcheli.MCH_Config;
/*     */ import mcheli.MCH_Key;
/*     */ import mcheli.MCH_PacketIndOpenScreen;
/*     */ import mcheli.wrapper.W_Network;
/*     */ import mcheli.wrapper.W_PacketBase;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import org.lwjgl.input.Keyboard;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class MCH_AircraftClientTickHandler
/*     */   extends MCH_ClientTickHandlerBase
/*     */ {
/*     */   protected boolean isRiding = false;
/*     */   protected boolean isBeforeRiding = false;
/*     */   public MCH_Key KeyUp;
/*     */   public MCH_Key KeyDown;
/*     */   public MCH_Key KeyRight;
/*     */   public MCH_Key KeyLeft;
/*     */   public MCH_Key KeyUseWeapon;
/*     */   public MCH_Key KeySwitchWeapon1;
/*     */   public MCH_Key KeySwitchWeapon2;
/*     */   public MCH_Key KeySwWeaponMode;
/*     */   public MCH_Key KeyUnmount;
/*     */   public MCH_Key KeyUnmountForce;
/*     */   public MCH_Key KeyExtra;
/*     */   public MCH_Key KeyFlare;
/*     */   public MCH_Key KeyCameraMode;
/*     */   public MCH_Key KeyFreeLook;
/*     */   public MCH_Key KeyGUI;
/*     */   public MCH_Key KeyGearUpDown;
/*     */   public MCH_Key KeyPutToRack;
/*     */   public MCH_Key KeyDownFromRack;
/*     */   public MCH_Key KeyBrake;
/*     */   
/*     */   public MCH_AircraftClientTickHandler(Minecraft minecraft, MCH_Config config) {
/*  46 */     super(minecraft);
/*  47 */     updateKeybind(config);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateKeybind(MCH_Config config) {
/*  53 */     this.KeyUp = new MCH_Key(MCH_Config.KeyUp.prmInt);
/*  54 */     this.KeyDown = new MCH_Key(MCH_Config.KeyDown.prmInt);
/*  55 */     this.KeyRight = new MCH_Key(MCH_Config.KeyRight.prmInt);
/*  56 */     this.KeyLeft = new MCH_Key(MCH_Config.KeyLeft.prmInt);
/*  57 */     this.KeyUseWeapon = new MCH_Key(MCH_Config.KeyUseWeapon.prmInt);
/*  58 */     this.KeySwitchWeapon1 = new MCH_Key(MCH_Config.KeySwitchWeapon1.prmInt);
/*  59 */     this.KeySwitchWeapon2 = new MCH_Key(MCH_Config.KeySwitchWeapon2.prmInt);
/*  60 */     this.KeySwWeaponMode = new MCH_Key(MCH_Config.KeySwWeaponMode.prmInt);
/*  61 */     this.KeyUnmount = new MCH_Key(MCH_Config.KeyUnmount.prmInt);
/*  62 */     this.KeyUnmountForce = new MCH_Key(42);
/*  63 */     this.KeyExtra = new MCH_Key(MCH_Config.KeyExtra.prmInt);
/*  64 */     this.KeyFlare = new MCH_Key(MCH_Config.KeyFlare.prmInt);
/*  65 */     this.KeyCameraMode = new MCH_Key(MCH_Config.KeyCameraMode.prmInt);
/*  66 */     this.KeyFreeLook = new MCH_Key(MCH_Config.KeyFreeLook.prmInt);
/*  67 */     this.KeyGUI = new MCH_Key(MCH_Config.KeyGUI.prmInt);
/*  68 */     this.KeyGearUpDown = new MCH_Key(MCH_Config.KeyGearUpDown.prmInt);
/*  69 */     this.KeyPutToRack = new MCH_Key(MCH_Config.KeyPutToRack.prmInt);
/*  70 */     this.KeyDownFromRack = new MCH_Key(MCH_Config.KeyDownFromRack.prmInt);
/*  71 */     this.KeyBrake = new MCH_Key(MCH_Config.KeySwitchHovering.prmInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void commonPlayerControlInGUI(EntityPlayer player, MCH_EntityAircraft ac, boolean isPilot, MCH_PacketPlayerControlBase pc) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean commonPlayerControl(EntityPlayer player, MCH_EntityAircraft ac, boolean isPilot, MCH_PacketPlayerControlBase pc) {
/*  82 */     if (Keyboard.isKeyDown(MCH_Config.KeyFreeLook.prmInt)) {
/*     */       
/*  84 */       if (this.KeyGUI.isKeyDown() || this.KeyExtra.isKeyDown())
/*     */       {
/*  86 */         MCH_PacketSeatPlayerControl psc = new MCH_PacketSeatPlayerControl();
/*  87 */         if (isPilot) {
/*     */           
/*  89 */           psc.switchSeat = (byte)(this.KeyGUI.isKeyDown() ? 1 : 2);
/*     */         }
/*     */         else {
/*     */           
/*  93 */           ac.keepOnRideRotation = true;
/*  94 */           psc.switchSeat = 3;
/*     */         } 
/*  96 */         W_Network.sendToServer((W_PacketBase)psc);
/*  97 */         return false;
/*     */       }
/*     */     
/* 100 */     } else if (!isPilot && ac.getSeatNum() > 1) {
/*     */       
/* 102 */       MCH_PacketSeatPlayerControl psc = new MCH_PacketSeatPlayerControl();
/*     */       
/* 104 */       if (this.KeyGUI.isKeyDown()) {
/*     */         
/* 106 */         psc.switchSeat = 1;
/* 107 */         W_Network.sendToServer((W_PacketBase)psc);
/* 108 */         return false;
/*     */       } 
/* 110 */       if (this.KeyExtra.isKeyDown()) {
/*     */         
/* 112 */         psc.switchSeat = 2;
/* 113 */         W_Network.sendToServer((W_PacketBase)psc);
/* 114 */         return false;
/*     */       } 
/*     */     } 
/*     */     
/* 118 */     boolean send = false;
/*     */     
/* 120 */     if (Keyboard.isKeyDown(MCH_Config.KeyFreeLook.prmInt)) {
/*     */       
/* 122 */       if (this.KeyCameraMode.isKeyDown())
/*     */       {
/*     */         
/* 125 */         pc.switchGunnerStatus = true;
/* 126 */         playSoundOK();
/* 127 */         send = true;
/*     */       
/*     */       }
/*     */     
/*     */     }
/* 132 */     else if (this.KeyCameraMode.isKeyDown()) {
/*     */       
/* 134 */       if (ac.haveSearchLight()) {
/*     */         
/* 136 */         if (ac.canSwitchSearchLight((Entity)player))
/*     */         {
/* 138 */           pc.switchSearchLight = true;
/* 139 */           playSoundOK();
/* 140 */           send = true;
/*     */         }
/*     */       
/* 143 */       } else if (ac.canSwitchCameraMode()) {
/*     */         
/* 145 */         int beforeMode = ac.getCameraMode(player);
/* 146 */         ac.switchCameraMode(player);
/* 147 */         int mode = ac.getCameraMode(player);
/* 148 */         if (mode != beforeMode)
/*     */         {
/* 150 */           pc.switchCameraMode = (byte)(mode + 1);
/* 151 */           playSoundOK();
/* 152 */           send = true;
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 157 */         playSoundNG();
/*     */       } 
/*     */     } 
/*     */     
/* 161 */     if (this.KeyUnmount.isKeyDown() && !ac.isDestroyed() && ac.func_70302_i_() > 0 && !isPilot)
/*     */     {
/* 163 */       MCH_PacketIndOpenScreen.send(3);
/*     */     }
/*     */     
/* 166 */     if (isPilot) {
/*     */       
/* 168 */       if (this.KeyUnmount.isKeyDown()) {
/*     */         
/* 170 */         pc.isUnmount = 2;
/* 171 */         send = true;
/*     */       } 
/*     */       
/* 174 */       if (this.KeyPutToRack.isKeyDown()) {
/*     */         
/* 176 */         ac.checkRideRack();
/* 177 */         if (ac.canRideRack())
/*     */         {
/* 179 */           pc.putDownRack = 3;
/* 180 */           send = true;
/*     */         }
/* 182 */         else if (ac.canPutToRack())
/*     */         {
/* 184 */           pc.putDownRack = 1;
/* 185 */           send = true;
/*     */         }
/*     */       
/* 188 */       } else if (this.KeyDownFromRack.isKeyDown()) {
/*     */         
/* 190 */         if (ac.func_184187_bx() != null) {
/*     */           
/* 192 */           pc.isUnmount = 3;
/* 193 */           send = true;
/*     */         }
/* 195 */         else if (ac.canDownFromRack()) {
/*     */           
/* 197 */           pc.putDownRack = 2;
/* 198 */           send = true;
/*     */         } 
/*     */       } 
/*     */       
/* 202 */       if (this.KeyGearUpDown.isKeyDown() && ac.getAcInfo().haveLandingGear())
/*     */       {
/* 204 */         if (ac.canFoldLandingGear()) {
/*     */           
/* 206 */           pc.switchGear = 1;
/* 207 */           send = true;
/*     */         }
/* 209 */         else if (ac.canUnfoldLandingGear()) {
/*     */           
/* 211 */           pc.switchGear = 2;
/* 212 */           send = true;
/*     */         } 
/*     */       }
/*     */       
/* 216 */       if (this.KeyFreeLook.isKeyDown())
/*     */       {
/* 218 */         if (ac.canSwitchFreeLook()) {
/*     */           
/* 220 */           pc.switchFreeLook = (byte)(ac.isFreeLookMode() ? 2 : 1);
/*     */           
/* 222 */           send = true;
/*     */         } 
/*     */       }
/*     */       
/* 226 */       if (this.KeyGUI.isKeyDown()) {
/*     */         
/* 228 */         pc.openGui = true;
/* 229 */         send = true;
/*     */       } 
/*     */       
/* 232 */       if (ac.isRepelling()) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 238 */         pc.throttleDown = ac.throttleDown = false;
/* 239 */         pc.throttleUp = ac.throttleUp = false;
/* 240 */         pc.moveRight = ac.moveRight = false;
/* 241 */         pc.moveLeft = ac.moveLeft = false;
/*     */       }
/* 243 */       else if (ac.hasBrake() && this.KeyBrake.isKeyPress()) {
/*     */         
/* 245 */         send |= this.KeyBrake.isKeyDown();
/*     */ 
/*     */ 
/*     */         
/* 249 */         pc.throttleDown = ac.throttleDown = false;
/* 250 */         pc.throttleUp = ac.throttleUp = false;
/* 251 */         double dx = ac.field_70165_t - ac.field_70169_q;
/* 252 */         double dz = ac.field_70161_v - ac.field_70166_s;
/* 253 */         double dist = dx * dx + dz * dz;
/* 254 */         if (ac.getCurrentThrottle() <= 0.03D && dist < 0.01D) {
/*     */ 
/*     */ 
/*     */           
/* 258 */           pc.moveRight = ac.moveRight = false;
/* 259 */           pc.moveLeft = ac.moveLeft = false;
/*     */         } 
/* 261 */         pc.useBrake = true;
/*     */       }
/*     */       else {
/*     */         
/* 265 */         send |= this.KeyBrake.isKeyUp();
/*     */         
/* 267 */         MCH_Key[] dKey = { this.KeyUp, this.KeyDown, this.KeyRight, this.KeyLeft };
/*     */ 
/*     */ 
/*     */         
/* 271 */         for (MCH_Key k : dKey) {
/*     */           
/* 273 */           if (k.isKeyDown() || k.isKeyUp()) {
/*     */             
/* 275 */             send = true;
/*     */             break;
/*     */           } 
/*     */         } 
/* 279 */         pc.throttleDown = ac.throttleDown = this.KeyDown.isKeyPress();
/* 280 */         pc.throttleUp = ac.throttleUp = this.KeyUp.isKeyPress();
/* 281 */         pc.moveRight = ac.moveRight = this.KeyRight.isKeyPress();
/* 282 */         pc.moveLeft = ac.moveLeft = this.KeyLeft.isKeyPress();
/*     */       } 
/*     */     } 
/*     */     
/* 286 */     if (!ac.isDestroyed() && this.KeyFlare.isKeyDown())
/*     */     {
/* 288 */       if (ac.getSeatIdByEntity((Entity)player) <= 1)
/*     */       {
/* 290 */         if (ac.canUseFlare() && ac.useFlare(ac.getCurrentFlareType())) {
/*     */           
/* 292 */           pc.useFlareType = (byte)ac.getCurrentFlareType();
/* 293 */           ac.nextFlareType();
/* 294 */           send = true;
/*     */         }
/*     */         else {
/*     */           
/* 298 */           playSoundNG();
/*     */         } 
/*     */       }
/*     */     }
/*     */     
/* 303 */     if (!ac.isDestroyed() && !ac.isPilotReloading())
/*     */     {
/* 305 */       if (this.KeySwitchWeapon1.isKeyDown() || this.KeySwitchWeapon2.isKeyDown() || getMouseWheel() != 0) {
/*     */         
/* 307 */         if (getMouseWheel() > 0) {
/*     */           
/* 309 */           pc.switchWeapon = (byte)ac.getNextWeaponID((Entity)player, -1);
/*     */         }
/*     */         else {
/*     */           
/* 313 */           pc.switchWeapon = (byte)ac.getNextWeaponID((Entity)player, 1);
/*     */         } 
/* 315 */         setMouseWheel(0);
/* 316 */         ac.switchWeapon((Entity)player, pc.switchWeapon);
/* 317 */         send = true;
/*     */       
/*     */       }
/* 320 */       else if (this.KeySwWeaponMode.isKeyDown()) {
/*     */         
/* 322 */         ac.switchCurrentWeaponMode((Entity)player);
/*     */       }
/* 324 */       else if (this.KeyUseWeapon.isKeyPress()) {
/*     */         
/* 326 */         if (ac.useCurrentWeapon((Entity)player)) {
/*     */           
/* 328 */           pc.useWeapon = true;
/* 329 */           pc.useWeaponOption1 = ac.getCurrentWeapon((Entity)player).getLastUsedOptionParameter1();
/* 330 */           pc.useWeaponOption2 = ac.getCurrentWeapon((Entity)player).getLastUsedOptionParameter2();
/* 331 */           pc.useWeaponPosX = ac.field_70169_q;
/* 332 */           pc.useWeaponPosY = ac.field_70167_r;
/* 333 */           pc.useWeaponPosZ = ac.field_70166_s;
/* 334 */           send = true;
/*     */         } 
/*     */       } 
/*     */     }
/* 338 */     return (send || player.field_70173_aa % 100 == 0);
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\aircraft\MCH_AircraftClientTickHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */