/*     */ package mcheli.plane;
/*     */ 
/*     */ import mcheli.MCH_Config;
/*     */ import mcheli.MCH_Key;
/*     */ import mcheli.MCH_Lib;
/*     */ import mcheli.MCH_ViewEntityDummy;
/*     */ import mcheli.aircraft.MCH_AircraftClientTickHandler;
/*     */ import mcheli.aircraft.MCH_EntityAircraft;
/*     */ import mcheli.aircraft.MCH_EntitySeat;
/*     */ import mcheli.aircraft.MCH_SeatInfo;
/*     */ import mcheli.uav.MCH_EntityUavStation;
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
/*     */ public class MCP_ClientPlaneTickHandler
/*     */   extends MCH_AircraftClientTickHandler
/*     */ {
/*     */   public MCH_Key KeySwitchMode;
/*     */   public MCH_Key KeyEjectSeat;
/*     */   public MCH_Key KeyZoom;
/*     */   public MCH_Key[] Keys;
/*     */   
/*     */   public MCP_ClientPlaneTickHandler(Minecraft minecraft, MCH_Config config) {
/*  32 */     super(minecraft, config);
/*  33 */     updateKeybind(config);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateKeybind(MCH_Config config) {
/*  39 */     super.updateKeybind(config);
/*  40 */     this.KeySwitchMode = new MCH_Key(MCH_Config.KeySwitchMode.prmInt);
/*  41 */     this.KeyEjectSeat = new MCH_Key(MCH_Config.KeySwitchHovering.prmInt);
/*  42 */     this.KeyZoom = new MCH_Key(MCH_Config.KeyZoom.prmInt);
/*     */     
/*  44 */     this.Keys = new MCH_Key[] { this.KeyUp, this.KeyDown, this.KeyRight, this.KeyLeft, this.KeySwitchMode, this.KeyEjectSeat, this.KeyUseWeapon, this.KeySwWeaponMode, this.KeySwitchWeapon1, this.KeySwitchWeapon2, this.KeyZoom, this.KeyCameraMode, this.KeyUnmount, this.KeyUnmountForce, this.KeyFlare, this.KeyExtra, this.KeyFreeLook, this.KeyGUI, this.KeyGearUpDown, this.KeyPutToRack, this.KeyDownFromRack };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void update(EntityPlayer player, MCP_EntityPlane plane) {
/*  55 */     if (plane.getIsGunnerMode((Entity)player)) {
/*     */       
/*  57 */       MCH_SeatInfo seatInfo = plane.getSeatInfo((Entity)player);
/*     */       
/*  59 */       if (seatInfo != null)
/*     */       {
/*  61 */         setRotLimitPitch(seatInfo.minPitch, seatInfo.maxPitch, (Entity)player);
/*     */       }
/*     */     } 
/*     */     
/*  65 */     plane.updateRadar(10);
/*  66 */     plane.updateCameraRotate(player.field_70177_z, player.field_70125_A);
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
/*  80 */     MCP_EntityPlane plane = null;
/*  81 */     boolean isPilot = true;
/*     */     
/*  83 */     if (entityPlayerSP != null)
/*     */     {
/*  85 */       if (entityPlayerSP.func_184187_bx() instanceof MCP_EntityPlane) {
/*     */         
/*  87 */         plane = (MCP_EntityPlane)entityPlayerSP.func_184187_bx();
/*     */       }
/*  89 */       else if (entityPlayerSP.func_184187_bx() instanceof MCH_EntitySeat) {
/*     */         
/*  91 */         MCH_EntitySeat seat = (MCH_EntitySeat)entityPlayerSP.func_184187_bx();
/*     */         
/*  93 */         if (seat.getParent() instanceof MCP_EntityPlane)
/*     */         {
/*  95 */           isPilot = false;
/*  96 */           plane = (MCP_EntityPlane)seat.getParent();
/*     */         }
/*     */       
/*  99 */       } else if (entityPlayerSP.func_184187_bx() instanceof MCH_EntityUavStation) {
/*     */         
/* 101 */         MCH_EntityUavStation uavStation = (MCH_EntityUavStation)entityPlayerSP.func_184187_bx();
/*     */         
/* 103 */         if (uavStation.getControlAircract() instanceof MCP_EntityPlane)
/*     */         {
/* 105 */           plane = (MCP_EntityPlane)uavStation.getControlAircract();
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 110 */     if (plane != null && plane.getAcInfo() != null) {
/*     */       
/* 112 */       update((EntityPlayer)entityPlayerSP, plane);
/*     */       
/* 114 */       MCH_ViewEntityDummy viewEntityDummy = MCH_ViewEntityDummy.getInstance((World)this.mc.field_71441_e);
/* 115 */       viewEntityDummy.update(plane.camera);
/*     */       
/* 117 */       if (!inGUI) {
/*     */         
/* 119 */         if (!plane.isDestroyed())
/*     */         {
/* 121 */           playerControl((EntityPlayer)entityPlayerSP, plane, isPilot);
/*     */         }
/*     */       }
/*     */       else {
/*     */         
/* 126 */         playerControlInGUI((EntityPlayer)entityPlayerSP, plane, isPilot);
/*     */       } 
/*     */       
/* 129 */       boolean hideHand = true;
/*     */       
/* 131 */       if ((isPilot && plane.isAlwaysCameraView()) || plane.getIsGunnerMode((Entity)entityPlayerSP) || plane.getCameraId() > 0) {
/*     */         
/* 133 */         MCH_Lib.setRenderViewEntity((EntityLivingBase)viewEntityDummy);
/*     */       }
/*     */       else {
/*     */         
/* 137 */         MCH_Lib.setRenderViewEntity((EntityLivingBase)entityPlayerSP);
/*     */         
/* 139 */         if (!isPilot && plane.getCurrentWeaponID((Entity)entityPlayerSP) < 0)
/*     */         {
/* 141 */           hideHand = false;
/*     */         }
/*     */       } 
/*     */       
/* 145 */       if (hideHand)
/*     */       {
/*     */         
/* 148 */         MCH_Lib.disableFirstPersonItemRender(entityPlayerSP.func_184614_ca());
/*     */       }
/*     */       
/* 151 */       this.isRiding = true;
/*     */     }
/*     */     else {
/*     */       
/* 155 */       this.isRiding = false;
/*     */     } 
/*     */     
/* 158 */     if (!this.isBeforeRiding && this.isRiding && plane != null) {
/*     */       
/* 160 */       W_Reflection.setThirdPersonDistance(plane.thirdPersonDist);
/* 161 */       MCH_ViewEntityDummy.getInstance((World)this.mc.field_71441_e).func_70107_b(plane.field_70165_t, plane.field_70163_u + 0.5D, plane.field_70161_v);
/*     */     
/*     */     }
/* 164 */     else if (this.isBeforeRiding && !this.isRiding) {
/*     */       
/* 166 */       W_Reflection.restoreDefaultThirdPersonDistance();
/* 167 */       MCH_Lib.enableFirstPersonItemRender();
/* 168 */       MCH_Lib.setRenderViewEntity((EntityLivingBase)entityPlayerSP);
/* 169 */       W_Reflection.setCameraRoll(0.0F);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void playerControlInGUI(EntityPlayer player, MCP_EntityPlane plane, boolean isPilot) {
/* 175 */     commonPlayerControlInGUI(player, plane, isPilot, new MCP_PlanePacketPlayerControl());
/*     */   }
/*     */ 
/*     */   
/*     */   protected void playerControl(EntityPlayer player, MCP_EntityPlane plane, boolean isPilot) {
/* 180 */     MCP_PlanePacketPlayerControl pc = new MCP_PlanePacketPlayerControl();
/* 181 */     boolean send = false;
/* 182 */     MCH_EntityAircraft ac = plane;
/*     */     
/* 184 */     send = commonPlayerControl(player, plane, isPilot, pc);
/*     */     
/* 186 */     if (isPilot) {
/*     */       
/* 188 */       if (this.KeySwitchMode.isKeyDown())
/*     */       {
/* 190 */         if (ac.getIsGunnerMode((Entity)player) && ac.canSwitchCameraPos()) {
/*     */           
/* 192 */           pc.switchMode = 0;
/* 193 */           ac.switchGunnerMode(false);
/* 194 */           send = true;
/* 195 */           ac.setCameraId(1);
/*     */         }
/* 197 */         else if (ac.getCameraId() > 0) {
/*     */           
/* 199 */           ac.setCameraId(ac.getCameraId() + 1);
/*     */           
/* 201 */           if (ac.getCameraId() >= ac.getCameraPosNum())
/*     */           {
/* 203 */             ac.setCameraId(0);
/*     */           }
/*     */         }
/* 206 */         else if (ac.canSwitchGunnerMode()) {
/*     */           
/* 208 */           pc.switchMode = (byte)(ac.getIsGunnerMode((Entity)player) ? 0 : 1);
/* 209 */           ac.switchGunnerMode(!ac.getIsGunnerMode((Entity)player));
/* 210 */           send = true;
/* 211 */           ac.setCameraId(0);
/*     */         }
/* 213 */         else if (ac.canSwitchCameraPos()) {
/*     */           
/* 215 */           ac.setCameraId(1);
/*     */         }
/*     */         else {
/*     */           
/* 219 */           playSoundNG();
/*     */         } 
/*     */       }
/*     */       
/* 223 */       if (this.KeyExtra.isKeyDown())
/*     */       {
/* 225 */         if (plane.canSwitchVtol())
/*     */         {
/* 227 */           boolean currentMode = plane.getNozzleStat();
/*     */           
/* 229 */           if (!currentMode) {
/* 230 */             pc.switchVtol = 1;
/*     */           } else {
/* 232 */             pc.switchVtol = 0;
/*     */           } 
/* 234 */           plane.swithVtolMode(!currentMode);
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
/* 245 */       if (plane.canSwitchGunnerModeOtherSeat(player)) {
/*     */         
/* 247 */         plane.switchGunnerModeOtherSeat(player);
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
/* 258 */       boolean isUav = (plane.isUAV() && !plane.getAcInfo().haveHatch() && !plane.getPlaneInfo().haveWing());
/*     */       
/* 260 */       if (plane.getIsGunnerMode((Entity)player) || isUav) {
/*     */         
/* 262 */         plane.zoomCamera();
/* 263 */         playSound("zoom", 0.5F, 1.0F);
/*     */       }
/* 265 */       else if (isPilot) {
/*     */         
/* 267 */         if (plane.getAcInfo().haveHatch()) {
/*     */           
/* 269 */           if (plane.canFoldHatch())
/*     */           {
/* 271 */             pc.switchHatch = 2;
/* 272 */             send = true;
/*     */           }
/* 274 */           else if (plane.canUnfoldHatch())
/*     */           {
/* 276 */             pc.switchHatch = 1;
/* 277 */             send = true;
/*     */           }
/*     */         
/* 280 */         } else if (plane.canFoldWing()) {
/*     */           
/* 282 */           pc.switchHatch = 2;
/* 283 */           send = true;
/*     */         }
/* 285 */         else if (plane.canUnfoldWing()) {
/*     */           
/* 287 */           pc.switchHatch = 1;
/* 288 */           send = true;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 293 */     if (this.KeyEjectSeat.isKeyDown() && plane.canEjectSeat((Entity)player)) {
/*     */       
/* 295 */       pc.ejectSeat = true;
/* 296 */       send = true;
/*     */     } 
/*     */     
/* 299 */     if (send)
/*     */     {
/* 301 */       W_Network.sendToServer((W_PacketBase)pc);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\plane\MCP_ClientPlaneTickHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */