/*     */ package mcheli.vehicle;
/*     */ 
/*     */ import mcheli.MCH_Config;
/*     */ import mcheli.MCH_Key;
/*     */ import mcheli.MCH_Lib;
/*     */ import mcheli.MCH_ViewEntityDummy;
/*     */ import mcheli.aircraft.MCH_AircraftClientTickHandler;
/*     */ import mcheli.aircraft.MCH_EntitySeat;
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
/*     */ public class MCH_ClientVehicleTickHandler
/*     */   extends MCH_AircraftClientTickHandler
/*     */ {
/*     */   public MCH_Key KeySwitchMode;
/*     */   public MCH_Key KeySwitchHovering;
/*     */   public MCH_Key KeyZoom;
/*     */   public MCH_Key KeyExtra;
/*     */   public MCH_Key[] Keys;
/*     */   
/*     */   public MCH_ClientVehicleTickHandler(Minecraft minecraft, MCH_Config config) {
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
/*  41 */     this.KeySwitchHovering = new MCH_Key(MCH_Config.KeySwitchHovering.prmInt);
/*  42 */     this.KeyZoom = new MCH_Key(MCH_Config.KeyZoom.prmInt);
/*  43 */     this.KeyExtra = new MCH_Key(MCH_Config.KeyExtra.prmInt);
/*     */     
/*  45 */     this.Keys = new MCH_Key[] { this.KeyUp, this.KeyDown, this.KeyRight, this.KeyLeft, this.KeySwitchMode, this.KeySwitchHovering, this.KeyUseWeapon, this.KeySwWeaponMode, this.KeySwitchWeapon1, this.KeySwitchWeapon2, this.KeyZoom, this.KeyCameraMode, this.KeyUnmount, this.KeyUnmountForce, this.KeyFlare, this.KeyExtra, this.KeyGUI };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void update(EntityPlayer player, MCH_EntityVehicle vehicle, MCH_VehicleInfo info) {
/*  55 */     if (info != null)
/*     */     {
/*  57 */       setRotLimitPitch(info.minRotationPitch, info.maxRotationPitch, (Entity)player);
/*     */     }
/*     */     
/*  60 */     vehicle.updateCameraRotate(player.field_70177_z, player.field_70125_A);
/*  61 */     vehicle.updateRadar(5);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onTick(boolean inGUI) {
/*  67 */     for (MCH_Key k : this.Keys)
/*     */     {
/*  69 */       k.update();
/*     */     }
/*     */     
/*  72 */     this.isBeforeRiding = this.isRiding;
/*     */     
/*  74 */     EntityPlayerSP entityPlayerSP = this.mc.field_71439_g;
/*  75 */     MCH_EntityVehicle vehicle = null;
/*  76 */     boolean isPilot = true;
/*     */     
/*  78 */     if (entityPlayerSP != null)
/*     */     {
/*  80 */       if (entityPlayerSP.func_184187_bx() instanceof MCH_EntityVehicle) {
/*     */         
/*  82 */         vehicle = (MCH_EntityVehicle)entityPlayerSP.func_184187_bx();
/*     */       }
/*  84 */       else if (entityPlayerSP.func_184187_bx() instanceof MCH_EntitySeat) {
/*     */         
/*  86 */         MCH_EntitySeat seat = (MCH_EntitySeat)entityPlayerSP.func_184187_bx();
/*     */         
/*  88 */         if (seat.getParent() instanceof MCH_EntityVehicle) {
/*     */           
/*  90 */           isPilot = false;
/*  91 */           vehicle = (MCH_EntityVehicle)seat.getParent();
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/*  96 */     if (vehicle != null && vehicle.getAcInfo() != null) {
/*     */ 
/*     */       
/*  99 */       MCH_Lib.disableFirstPersonItemRender(entityPlayerSP.func_184614_ca());
/* 100 */       update((EntityPlayer)entityPlayerSP, vehicle, vehicle.getVehicleInfo());
/*     */       
/* 102 */       MCH_ViewEntityDummy viewEntityDummy = MCH_ViewEntityDummy.getInstance((World)this.mc.field_71441_e);
/*     */       
/* 104 */       viewEntityDummy.update(vehicle.camera);
/*     */       
/* 106 */       if (!inGUI) {
/*     */         
/* 108 */         if (!vehicle.isDestroyed())
/*     */         {
/* 110 */           playerControl((EntityPlayer)entityPlayerSP, vehicle, isPilot);
/*     */         }
/*     */       }
/*     */       else {
/*     */         
/* 115 */         playerControlInGUI((EntityPlayer)entityPlayerSP, vehicle, isPilot);
/*     */       } 
/*     */       
/* 118 */       MCH_Lib.setRenderViewEntity((EntityLivingBase)viewEntityDummy);
/* 119 */       this.isRiding = true;
/*     */     }
/*     */     else {
/*     */       
/* 123 */       this.isRiding = false;
/*     */     } 
/*     */     
/* 126 */     if (!this.isBeforeRiding && this.isRiding) {
/*     */       
/* 128 */       W_Reflection.setThirdPersonDistance(vehicle.thirdPersonDist);
/*     */     }
/* 130 */     else if (this.isBeforeRiding && !this.isRiding) {
/*     */       
/* 132 */       W_Reflection.restoreDefaultThirdPersonDistance();
/* 133 */       MCH_Lib.enableFirstPersonItemRender();
/* 134 */       MCH_Lib.setRenderViewEntity((EntityLivingBase)entityPlayerSP);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void playerControlInGUI(EntityPlayer player, MCH_EntityVehicle vehicle, boolean isPilot) {
/* 140 */     commonPlayerControlInGUI(player, vehicle, isPilot, new MCH_PacketVehiclePlayerControl());
/*     */   }
/*     */ 
/*     */   
/*     */   protected void playerControl(EntityPlayer player, MCH_EntityVehicle vehicle, boolean isPilot) {
/* 145 */     MCH_PacketVehiclePlayerControl pc = new MCH_PacketVehiclePlayerControl();
/* 146 */     boolean send = false;
/* 147 */     send = commonPlayerControl(player, vehicle, isPilot, pc);
/*     */     
/* 149 */     if (this.KeyExtra.isKeyDown())
/*     */     {
/* 151 */       if (vehicle.getTowChainEntity() != null) {
/*     */         
/* 153 */         playSoundOK();
/* 154 */         pc.unhitchChainId = W_Entity.getEntityId((Entity)vehicle.getTowChainEntity());
/* 155 */         send = true;
/*     */       }
/*     */       else {
/*     */         
/* 159 */         playSoundNG();
/*     */       } 
/*     */     }
/*     */     
/* 163 */     if (!this.KeySwitchHovering.isKeyDown())
/*     */     {
/* 165 */       if (this.KeySwitchMode.isKeyDown());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 171 */     if (this.KeyZoom.isKeyDown())
/*     */     {
/* 173 */       if (vehicle.canZoom()) {
/*     */         
/* 175 */         vehicle.zoomCamera();
/* 176 */         playSound("zoom", 0.5F, 1.0F);
/*     */       }
/* 178 */       else if (vehicle.getAcInfo().haveHatch()) {
/*     */         
/* 180 */         if (vehicle.canFoldHatch()) {
/*     */           
/* 182 */           pc.switchHatch = 2;
/* 183 */           send = true;
/*     */         }
/* 185 */         else if (vehicle.canUnfoldHatch()) {
/*     */           
/* 187 */           pc.switchHatch = 1;
/* 188 */           send = true;
/*     */         }
/*     */         else {
/*     */           
/* 192 */           playSoundNG();
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 197 */     if (send)
/*     */     {
/* 199 */       W_Network.sendToServer((W_PacketBase)pc);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\vehicle\MCH_ClientVehicleTickHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */