/*     */ package mcheli.tank;
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
/*     */ public class MCH_ClientTankTickHandler
/*     */   extends MCH_AircraftClientTickHandler
/*     */ {
/*     */   public MCH_Key KeySwitchMode;
/*     */   public MCH_Key KeyZoom;
/*     */   public MCH_Key[] Keys;
/*     */   
/*     */   public MCH_ClientTankTickHandler(Minecraft minecraft, MCH_Config config) {
/*  31 */     super(minecraft, config);
/*  32 */     updateKeybind(config);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateKeybind(MCH_Config config) {
/*  38 */     super.updateKeybind(config);
/*     */     
/*  40 */     this.KeySwitchMode = new MCH_Key(MCH_Config.KeySwitchMode.prmInt);
/*  41 */     this.KeyZoom = new MCH_Key(MCH_Config.KeyZoom.prmInt);
/*     */     
/*  43 */     this.Keys = new MCH_Key[] { this.KeyUp, this.KeyDown, this.KeyRight, this.KeyLeft, this.KeySwitchMode, this.KeyUseWeapon, this.KeySwWeaponMode, this.KeySwitchWeapon1, this.KeySwitchWeapon2, this.KeyZoom, this.KeyCameraMode, this.KeyUnmount, this.KeyUnmountForce, this.KeyFlare, this.KeyExtra, this.KeyFreeLook, this.KeyGUI, this.KeyGearUpDown, this.KeyBrake, this.KeyPutToRack, this.KeyDownFromRack };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void update(EntityPlayer player, MCH_EntityTank tank) {
/*  55 */     if (tank.getIsGunnerMode((Entity)player)) {
/*     */       
/*  57 */       MCH_SeatInfo seatInfo = tank.getSeatInfo((Entity)player);
/*     */       
/*  59 */       if (seatInfo != null)
/*     */       {
/*  61 */         setRotLimitPitch(seatInfo.minPitch, seatInfo.maxPitch, (Entity)player);
/*     */       }
/*     */     } 
/*     */     
/*  65 */     tank.updateRadar(10);
/*  66 */     tank.updateCameraRotate(player.field_70177_z, player.field_70125_A);
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
/*  80 */     MCH_EntityTank tank = null;
/*  81 */     boolean isPilot = true;
/*     */     
/*  83 */     if (entityPlayerSP != null)
/*     */     {
/*  85 */       if (entityPlayerSP.func_184187_bx() instanceof MCH_EntityTank) {
/*     */         
/*  87 */         tank = (MCH_EntityTank)entityPlayerSP.func_184187_bx();
/*     */       }
/*  89 */       else if (entityPlayerSP.func_184187_bx() instanceof MCH_EntitySeat) {
/*     */         
/*  91 */         MCH_EntitySeat seat = (MCH_EntitySeat)entityPlayerSP.func_184187_bx();
/*     */         
/*  93 */         if (seat.getParent() instanceof MCH_EntityTank)
/*     */         {
/*  95 */           isPilot = false;
/*  96 */           tank = (MCH_EntityTank)seat.getParent();
/*     */         }
/*     */       
/*  99 */       } else if (entityPlayerSP.func_184187_bx() instanceof MCH_EntityUavStation) {
/*     */         
/* 101 */         MCH_EntityUavStation uavStation = (MCH_EntityUavStation)entityPlayerSP.func_184187_bx();
/*     */         
/* 103 */         if (uavStation.getControlAircract() instanceof MCH_EntityTank)
/*     */         {
/* 105 */           tank = (MCH_EntityTank)uavStation.getControlAircract();
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 110 */     if (tank != null && tank.getAcInfo() != null) {
/*     */       
/* 112 */       update((EntityPlayer)entityPlayerSP, tank);
/*     */       
/* 114 */       MCH_ViewEntityDummy viewEntityDummy = MCH_ViewEntityDummy.getInstance((World)this.mc.field_71441_e);
/*     */       
/* 116 */       viewEntityDummy.update(tank.camera);
/*     */       
/* 118 */       if (!inGUI) {
/*     */         
/* 120 */         if (!tank.isDestroyed())
/*     */         {
/* 122 */           playerControl((EntityPlayer)entityPlayerSP, tank, isPilot);
/*     */         }
/*     */       }
/*     */       else {
/*     */         
/* 127 */         playerControlInGUI((EntityPlayer)entityPlayerSP, tank, isPilot);
/*     */       } 
/*     */       
/* 130 */       boolean hideHand = true;
/*     */       
/* 132 */       if ((isPilot && tank.isAlwaysCameraView()) || tank.getIsGunnerMode((Entity)entityPlayerSP) || tank.getCameraId() > 0) {
/*     */         
/* 134 */         MCH_Lib.setRenderViewEntity((EntityLivingBase)viewEntityDummy);
/*     */       }
/*     */       else {
/*     */         
/* 138 */         MCH_Lib.setRenderViewEntity((EntityLivingBase)entityPlayerSP);
/*     */         
/* 140 */         if (!isPilot && tank.getCurrentWeaponID((Entity)entityPlayerSP) < 0)
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
/* 159 */     if (!this.isBeforeRiding && this.isRiding && tank != null) {
/*     */       
/* 161 */       W_Reflection.setThirdPersonDistance(tank.thirdPersonDist);
/* 162 */       MCH_ViewEntityDummy.getInstance((World)this.mc.field_71441_e).func_70107_b(tank.field_70165_t, tank.field_70163_u + 0.5D, tank.field_70161_v);
/*     */     
/*     */     }
/* 165 */     else if (this.isBeforeRiding && !this.isRiding) {
/*     */       
/* 167 */       W_Reflection.restoreDefaultThirdPersonDistance();
/* 168 */       MCH_Lib.enableFirstPersonItemRender();
/* 169 */       MCH_Lib.setRenderViewEntity((EntityLivingBase)entityPlayerSP);
/* 170 */       W_Reflection.setCameraRoll(0.0F);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void playerControlInGUI(EntityPlayer player, MCH_EntityTank tank, boolean isPilot) {
/* 176 */     commonPlayerControlInGUI(player, tank, isPilot, new MCH_TankPacketPlayerControl());
/*     */   }
/*     */ 
/*     */   
/*     */   protected void playerControl(EntityPlayer player, MCH_EntityTank tank, boolean isPilot) {
/* 181 */     MCH_TankPacketPlayerControl pc = new MCH_TankPacketPlayerControl();
/* 182 */     boolean send = false;
/* 183 */     MCH_EntityAircraft ac = tank;
/* 184 */     send = commonPlayerControl(player, tank, isPilot, pc);
/*     */     
/* 186 */     if ((ac.getAcInfo()).defaultFreelook && pc.switchFreeLook > 0)
/*     */     {
/* 188 */       pc.switchFreeLook = 0;
/*     */     }
/*     */     
/* 191 */     if (isPilot) {
/*     */       
/* 193 */       if (this.KeySwitchMode.isKeyDown())
/*     */       {
/* 195 */         if (ac.getIsGunnerMode((Entity)player) && ac.canSwitchCameraPos())
/*     */         {
/* 197 */           pc.switchMode = 0;
/* 198 */           ac.switchGunnerMode(false);
/* 199 */           send = true;
/* 200 */           ac.setCameraId(1);
/*     */         }
/* 202 */         else if (ac.getCameraId() > 0)
/*     */         {
/* 204 */           ac.setCameraId(ac.getCameraId() + 1);
/*     */           
/* 206 */           if (ac.getCameraId() >= ac.getCameraPosNum())
/*     */           {
/* 208 */             ac.setCameraId(0);
/*     */           }
/*     */         }
/* 211 */         else if (ac.canSwitchGunnerMode())
/*     */         {
/* 213 */           pc.switchMode = (byte)(ac.getIsGunnerMode((Entity)player) ? 0 : 1);
/* 214 */           ac.switchGunnerMode(!ac.getIsGunnerMode((Entity)player));
/* 215 */           send = true;
/* 216 */           ac.setCameraId(0);
/*     */         }
/* 218 */         else if (ac.canSwitchCameraPos())
/*     */         {
/* 220 */           ac.setCameraId(1);
/*     */         }
/*     */         else
/*     */         {
/* 224 */           playSoundNG();
/*     */         }
/*     */       
/*     */       }
/* 228 */     } else if (this.KeySwitchMode.isKeyDown()) {
/*     */       
/* 230 */       if (tank.canSwitchGunnerModeOtherSeat(player)) {
/*     */         
/* 232 */         tank.switchGunnerModeOtherSeat(player);
/* 233 */         send = true;
/*     */       }
/*     */       else {
/*     */         
/* 237 */         playSoundNG();
/*     */       } 
/*     */     } 
/*     */     
/* 241 */     if (this.KeyZoom.isKeyDown()) {
/*     */       
/* 243 */       boolean isUav = (tank.isUAV() && !tank.getAcInfo().haveHatch());
/*     */       
/* 245 */       if (tank.getIsGunnerMode((Entity)player) || isUav) {
/*     */         
/* 247 */         tank.zoomCamera();
/* 248 */         playSound("zoom", 0.5F, 1.0F);
/*     */       }
/* 250 */       else if (isPilot) {
/*     */         
/* 252 */         if (tank.getAcInfo().haveHatch())
/*     */         {
/* 254 */           if (tank.canFoldHatch()) {
/*     */             
/* 256 */             pc.switchHatch = 2;
/* 257 */             send = true;
/*     */           }
/* 259 */           else if (tank.canUnfoldHatch()) {
/*     */             
/* 261 */             pc.switchHatch = 1;
/* 262 */             send = true;
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 268 */     if (send)
/*     */     {
/* 270 */       W_Network.sendToServer((W_PacketBase)pc);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\tank\MCH_ClientTankTickHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */