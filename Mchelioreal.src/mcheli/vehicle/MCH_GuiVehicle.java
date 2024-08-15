/*     */ package mcheli.vehicle;
/*     */ 
/*     */ import mcheli.MCH_Config;
/*     */ import mcheli.MCH_KeyName;
/*     */ import mcheli.aircraft.MCH_AircraftCommonGui;
/*     */ import mcheli.weapon.MCH_WeaponSet;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @SideOnly(Side.CLIENT)
/*     */ public class MCH_GuiVehicle
/*     */   extends MCH_AircraftCommonGui
/*     */ {
/*     */   static final int COLOR1 = -14066;
/*     */   static final int COLOR2 = -2161656;
/*     */   
/*     */   public MCH_GuiVehicle(Minecraft minecraft) {
/*  28 */     super(minecraft);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDrawGui(EntityPlayer player) {
/*  34 */     return (player.func_184187_bx() != null && player.func_184187_bx() instanceof MCH_EntityVehicle);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawGui(EntityPlayer player, boolean isThirdPersonView) {
/*  40 */     if (player.func_184187_bx() == null || !(player.func_184187_bx() instanceof MCH_EntityVehicle)) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/*  45 */     MCH_EntityVehicle vehicle = (MCH_EntityVehicle)player.func_184187_bx();
/*     */     
/*  47 */     if (vehicle.isDestroyed()) {
/*     */       return;
/*     */     }
/*  50 */     int seatID = vehicle.getSeatIdByEntity((Entity)player);
/*     */     
/*  52 */     GL11.glLineWidth(scaleFactor);
/*     */     
/*  54 */     if (vehicle.getCameraMode(player) == 1)
/*     */     {
/*  56 */       drawNightVisionNoise();
/*     */     }
/*     */     
/*  59 */     if (vehicle.getIsGunnerMode((Entity)player) && vehicle.getTVMissile() != null)
/*     */     {
/*  61 */       drawTvMissileNoise(vehicle, vehicle.getTVMissile());
/*     */     }
/*     */     
/*  64 */     drawDebugtInfo(vehicle);
/*     */     
/*  66 */     if (!isThirdPersonView || MCH_Config.DisplayHUDThirdPerson.prmBool) {
/*     */       
/*  68 */       drawHud(vehicle, player, seatID);
/*  69 */       drawKeyBind(vehicle, player);
/*     */     } 
/*     */     
/*  72 */     drawHitBullet(vehicle, 51470, seatID);
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawKeyBind(MCH_EntityVehicle vehicle, EntityPlayer player) {
/*  77 */     if (MCH_Config.HideKeybind.prmBool) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/*  82 */     MCH_VehicleInfo info = vehicle.getVehicleInfo();
/*     */     
/*  84 */     if (info == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/*  89 */     int colorActive = -1342177281;
/*  90 */     int colorInactive = -1349546097;
/*  91 */     int RX = this.centerX + 120;
/*  92 */     int LX = this.centerX - 200;
/*     */     
/*  94 */     if (vehicle.haveFlare()) {
/*     */       
/*  96 */       int c = vehicle.isFlarePreparation() ? colorInactive : colorActive;
/*  97 */       String str = "Flare : " + MCH_KeyName.getDescOrName(MCH_Config.KeyFlare.prmInt);
/*  98 */       drawString(str, RX, this.centerY - 50, c);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 103 */     String msg = "Gunner " + (vehicle.getGunnerStatus() ? "ON" : "OFF") + " : " + MCH_KeyName.getDescOrName(MCH_Config.KeyFreeLook.prmInt) + " + " + MCH_KeyName.getDescOrName(MCH_Config.KeyCameraMode.prmInt);
/*     */     
/* 105 */     drawString(msg, LX, this.centerY - 40, colorActive);
/*     */     
/* 107 */     if (vehicle.func_70302_i_() <= 0 || (vehicle
/* 108 */       .getTowChainEntity() != null && !(vehicle.getTowChainEntity()).field_70128_L)) {
/*     */       
/* 110 */       msg = "Drop  : " + MCH_KeyName.getDescOrName(MCH_Config.KeyExtra.prmInt);
/*     */       
/* 112 */       drawString(msg, RX, this.centerY - 30, colorActive);
/*     */     } 
/*     */ 
/*     */     
/* 116 */     if (vehicle.canZoom()) {
/*     */       
/* 118 */       msg = "Zoom : " + MCH_KeyName.getDescOrName(MCH_Config.KeyZoom.prmInt);
/*     */       
/* 120 */       drawString(msg, LX, this.centerY - 80, colorActive);
/*     */     } 
/*     */     
/* 123 */     MCH_WeaponSet ws = vehicle.getCurrentWeapon((Entity)player);
/*     */     
/* 125 */     if (vehicle.getWeaponNum() > 1) {
/*     */       
/* 127 */       msg = "Weapon : " + MCH_KeyName.getDescOrName(MCH_Config.KeySwitchWeapon2.prmInt);
/*     */       
/* 129 */       drawString(msg, LX, this.centerY - 70, colorActive);
/*     */     } 
/*     */     
/* 132 */     if ((ws.getCurrentWeapon()).numMode > 0) {
/*     */       
/* 134 */       msg = "WeaponMode : " + MCH_KeyName.getDescOrName(MCH_Config.KeySwWeaponMode.prmInt);
/*     */       
/* 136 */       drawString(msg, LX, this.centerY - 60, colorActive);
/*     */     } 
/*     */     
/* 139 */     if (info.isEnableNightVision) {
/*     */       
/* 141 */       msg = "CameraMode : " + MCH_KeyName.getDescOrName(MCH_Config.KeyCameraMode.prmInt);
/*     */       
/* 143 */       drawString(msg, LX, this.centerY - 50, colorActive);
/*     */     } 
/*     */     
/* 146 */     msg = "Dismount all : LShift";
/* 147 */     drawString(msg, LX, this.centerY - 30, colorActive);
/*     */     
/* 149 */     if (vehicle.getSeatNum() >= 2) {
/*     */       
/* 151 */       msg = "Dismount : " + MCH_KeyName.getDescOrName(MCH_Config.KeyUnmount.prmInt);
/*     */       
/* 153 */       drawString(msg, LX, this.centerY - 40, colorActive);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\vehicle\MCH_GuiVehicle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */