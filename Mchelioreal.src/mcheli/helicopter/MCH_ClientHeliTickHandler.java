/*     */ package mcheli.helicopter;
/*     */ 
/*     */ import mcheli.MCH_Config;
/*     */ import mcheli.MCH_Key;
/*     */ import mcheli.MCH_Lib;
/*     */ import mcheli.MCH_ViewEntityDummy;
/*     */ import mcheli.aircraft.MCH_AircraftClientTickHandler;
/*     */ import mcheli.aircraft.MCH_EntitySeat;
/*     */ import mcheli.aircraft.MCH_SeatInfo;
/*     */ import mcheli.uav.MCH_EntityUavStation;
/*     */ import mcheli.wrapper.W_Entity;
/*     */ import mcheli.wrapper.W_Network;
/*     */ import mcheli.wrapper.W_PacketBase;
/*     */ import mcheli.wrapper.W_Reflection;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ public class MCH_ClientHeliTickHandler
/*     */   extends MCH_AircraftClientTickHandler
/*     */ {
/*     */   public MCH_Key KeySwitchMode;
/*     */   public MCH_Key KeySwitchHovering;
/*     */   public MCH_Key KeyZoom;
/*     */   public MCH_Key[] Keys;
/*     */   
/*     */   public MCH_ClientHeliTickHandler(Minecraft minecraft, MCH_Config config) {
/*  32 */     super(minecraft, config);
/*  33 */     updateKeybind(config);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateKeybind(MCH_Config config) {
/*  39 */     super.updateKeybind(config);
/*  40 */     this.KeySwitchMode = new MCH_Key(MCH_Config.KeySwitchMode.prmInt);
/*  41 */     this.KeySwitchHovering = new MCH_Key(MCH_Config.KeySwitchHovering.prmInt);
/*  42 */     this.KeyZoom = new MCH_Key(MCH_Config.KeyZoom.prmInt);
/*     */     
/*  44 */     this.Keys = new MCH_Key[] { this.KeyUp, this.KeyDown, this.KeyRight, this.KeyLeft, this.KeySwitchMode, this.KeySwitchHovering, this.KeyUseWeapon, this.KeySwWeaponMode, this.KeySwitchWeapon1, this.KeySwitchWeapon2, this.KeyZoom, this.KeyCameraMode, this.KeyUnmount, this.KeyUnmountForce, this.KeyFlare, this.KeyExtra, this.KeyFreeLook, this.KeyGUI, this.KeyGearUpDown, this.KeyPutToRack, this.KeyDownFromRack };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void update(EntityPlayer player, MCH_EntityHeli heli, boolean isPilot) {
/*  55 */     if (heli.getIsGunnerMode((Entity)player)) {
/*     */       
/*  57 */       MCH_SeatInfo seatInfo = heli.getSeatInfo((Entity)player);
/*     */       
/*  59 */       if (seatInfo != null)
/*     */       {
/*  61 */         setRotLimitPitch(seatInfo.minPitch, seatInfo.maxPitch, (Entity)player);
/*     */       }
/*     */     } 
/*     */     
/*  65 */     heli.updateCameraRotate(player.field_70177_z, player.field_70125_A);
/*  66 */     heli.updateRadar(5);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onTick(boolean inGUI) {
/*  72 */     for (MCH_Key k : this.Keys)
/*     */     {
/*  74 */       k.update();
/*     */     }
/*     */     
/*  77 */     this.isBeforeRiding = this.isRiding;
/*     */     
/*  79 */     EntityPlayerSP entityPlayerSP = this.mc.field_71439_g;
/*  80 */     MCH_EntityHeli heli = null;
/*  81 */     boolean isPilot = true;
/*     */     
/*  83 */     if (entityPlayerSP != null)
/*     */     {
/*  85 */       if (entityPlayerSP.func_184187_bx() instanceof MCH_EntityHeli) {
/*     */         
/*  87 */         heli = (MCH_EntityHeli)entityPlayerSP.func_184187_bx();
/*     */       }
/*  89 */       else if (entityPlayerSP.func_184187_bx() instanceof MCH_EntitySeat) {
/*     */         
/*  91 */         MCH_EntitySeat seat = (MCH_EntitySeat)entityPlayerSP.func_184187_bx();
/*     */         
/*  93 */         if (seat.getParent() instanceof MCH_EntityHeli)
/*     */         {
/*  95 */           isPilot = false;
/*  96 */           heli = (MCH_EntityHeli)seat.getParent();
/*     */         }
/*     */       
/*  99 */       } else if (entityPlayerSP.func_184187_bx() instanceof MCH_EntityUavStation) {
/*     */         
/* 101 */         MCH_EntityUavStation uavStation = (MCH_EntityUavStation)entityPlayerSP.func_184187_bx();
/*     */         
/* 103 */         if (uavStation.getControlAircract() instanceof MCH_EntityHeli)
/*     */         {
/* 105 */           heli = (MCH_EntityHeli)uavStation.getControlAircract();
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 110 */     if (heli != null && heli.getAcInfo() != null) {
/*     */       
/* 112 */       update((EntityPlayer)entityPlayerSP, heli, isPilot);
/*     */       
/* 114 */       MCH_ViewEntityDummy viewEntityDummy = MCH_ViewEntityDummy.getInstance((World)this.mc.field_71441_e);
/*     */       
/* 116 */       viewEntityDummy.update(heli.camera);
/*     */       
/* 118 */       if (!inGUI) {
/*     */         
/* 120 */         if (!heli.isDestroyed())
/*     */         {
/* 122 */           playerControl((EntityPlayer)entityPlayerSP, heli, isPilot);
/*     */         }
/*     */       }
/*     */       else {
/*     */         
/* 127 */         playerControlInGUI((EntityPlayer)entityPlayerSP, heli, isPilot);
/*     */       } 
/*     */       
/* 130 */       boolean hideHand = true;
/*     */       
/* 132 */       if ((isPilot && heli.isAlwaysCameraView()) || heli.getIsGunnerMode((Entity)entityPlayerSP)) {
/*     */         
/* 134 */         MCH_Lib.setRenderViewEntity((EntityLivingBase)viewEntityDummy);
/*     */       }
/*     */       else {
/*     */         
/* 138 */         MCH_Lib.setRenderViewEntity((EntityLivingBase)entityPlayerSP);
/*     */         
/* 140 */         if (!isPilot && heli.getCurrentWeaponID((Entity)entityPlayerSP) < 0)
/*     */         {
/* 142 */           hideHand = false;
/*     */         }
/*     */       } 
/*     */       
/* 146 */       if (hideHand)
/*     */       {
/*     */         
/* 149 */         MCH_Lib.disableFirstPersonItemRender(entityPlayerSP.func_184614_ca());
/*     */       }
/*     */       
/* 152 */       this.isRiding = true;
/*     */     }
/*     */     else {
/*     */       
/* 156 */       this.isRiding = false;
/*     */     } 
/*     */     
/* 159 */     if (!this.isBeforeRiding && this.isRiding) {
/*     */       
/* 161 */       W_Reflection.setThirdPersonDistance(heli.thirdPersonDist);
/*     */     }
/* 163 */     else if (this.isBeforeRiding && !this.isRiding) {
/*     */       
/* 165 */       W_Reflection.restoreDefaultThirdPersonDistance();
/* 166 */       W_Reflection.setCameraRoll(0.0F);
/* 167 */       MCH_Lib.enableFirstPersonItemRender();
/* 168 */       MCH_Lib.setRenderViewEntity((EntityLivingBase)entityPlayerSP);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void playerControlInGUI(EntityPlayer player, MCH_EntityHeli heli, boolean isPilot) {
/* 174 */     commonPlayerControlInGUI(player, heli, isPilot, new MCH_HeliPacketPlayerControl());
/*     */   }
/*     */ 
/*     */   
/*     */   protected void playerControl(EntityPlayer player, MCH_EntityHeli heli, boolean isPilot) {
/* 179 */     MCH_HeliPacketPlayerControl pc = new MCH_HeliPacketPlayerControl();
/* 180 */     boolean send = false;
/*     */     
/* 182 */     send = commonPlayerControl(player, heli, isPilot, pc);
/*     */     
/* 184 */     if (isPilot) {
/*     */       
/* 186 */       if (this.KeyExtra.isKeyDown())
/*     */       {
/* 188 */         if (heli.getTowChainEntity() != null) {
/*     */           
/* 190 */           playSoundOK();
/* 191 */           pc.unhitchChainId = W_Entity.getEntityId((Entity)heli.getTowChainEntity());
/* 192 */           send = true;
/*     */         }
/* 194 */         else if (heli.canSwitchFoldBlades()) {
/*     */           
/* 196 */           if (heli.isFoldBlades()) {
/*     */             
/* 198 */             heli.unfoldBlades();
/* 199 */             pc.switchFold = 0;
/*     */           }
/*     */           else {
/*     */             
/* 203 */             heli.foldBlades();
/* 204 */             pc.switchFold = 1;
/*     */           } 
/*     */           
/* 207 */           send = true;
/* 208 */           playSoundOK();
/*     */         }
/*     */         else {
/*     */           
/* 212 */           playSoundNG();
/*     */         } 
/*     */       }
/*     */       
/* 216 */       if (this.KeySwitchHovering.isKeyDown()) {
/*     */         
/* 218 */         if (heli.canSwitchHoveringMode())
/*     */         {
/* 220 */           pc.switchMode = (byte)(heli.isHoveringMode() ? 2 : 3);
/* 221 */           heli.switchHoveringMode(!heli.isHoveringMode());
/* 222 */           send = true;
/*     */         }
/*     */         else
/*     */         {
/* 226 */           playSoundNG();
/*     */         }
/*     */       
/* 229 */       } else if (this.KeySwitchMode.isKeyDown()) {
/*     */         
/* 231 */         if (heli.canSwitchGunnerMode())
/*     */         {
/* 233 */           pc.switchMode = (byte)(heli.getIsGunnerMode((Entity)player) ? 0 : 1);
/* 234 */           heli.switchGunnerMode(!heli.getIsGunnerMode((Entity)player));
/* 235 */           send = true;
/*     */         }
/*     */         else
/*     */         {
/* 239 */           playSoundNG();
/*     */         }
/*     */       
/*     */       } 
/* 243 */     } else if (this.KeySwitchMode.isKeyDown()) {
/*     */       
/* 245 */       if (heli.canSwitchGunnerModeOtherSeat(player)) {
/*     */         
/* 247 */         heli.switchGunnerModeOtherSeat(player);
/* 248 */         send = true;
/*     */       }
/*     */       else {
/*     */         
/* 252 */         playSoundNG();
/*     */       } 
/*     */     } 
/*     */     
/* 256 */     if (this.KeyZoom.isKeyDown()) {
/*     */       
/* 258 */       boolean isUav = (heli.isUAV() && !heli.getAcInfo().haveHatch());
/*     */       
/* 260 */       if (heli.getIsGunnerMode((Entity)player) || isUav) {
/*     */         
/* 262 */         heli.zoomCamera();
/* 263 */         playSound("zoom", 0.5F, 1.0F);
/*     */       }
/* 265 */       else if (isPilot) {
/*     */         
/* 267 */         if (heli.getAcInfo().haveHatch())
/*     */         {
/* 269 */           if (heli.canFoldHatch()) {
/*     */             
/* 271 */             pc.switchHatch = 2;
/* 272 */             send = true;
/*     */           }
/* 274 */           else if (heli.canUnfoldHatch()) {
/*     */             
/* 276 */             pc.switchHatch = 1;
/* 277 */             send = true;
/*     */           }
/*     */           else {
/*     */             
/* 281 */             playSoundNG();
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 287 */     if (send)
/*     */     {
/* 289 */       W_Network.sendToServer((W_PacketBase)pc);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\helicopter\MCH_ClientHeliTickHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */