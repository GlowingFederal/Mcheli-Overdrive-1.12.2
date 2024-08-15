/*     */ package mcheli.plane;
/*     */ 
/*     */ import mcheli.MCH_Config;
/*     */ import mcheli.MCH_KeyName;
/*     */ import mcheli.aircraft.MCH_AircraftCommonGui;
/*     */ import mcheli.aircraft.MCH_EntityAircraft;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import org.lwjgl.input.Keyboard;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @SideOnly(Side.CLIENT)
/*     */ public class MCP_GuiPlane
/*     */   extends MCH_AircraftCommonGui
/*     */ {
/*     */   public MCP_GuiPlane(Minecraft minecraft) {
/*  26 */     super(minecraft);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDrawGui(EntityPlayer player) {
/*  32 */     return MCH_EntityAircraft.getAircraft_RiddenOrControl((Entity)player) instanceof MCP_EntityPlane;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawGui(EntityPlayer player, boolean isThirdPersonView) {
/*  38 */     MCH_EntityAircraft ac = MCH_EntityAircraft.getAircraft_RiddenOrControl((Entity)player);
/*     */     
/*  40 */     if (!(ac instanceof MCP_EntityPlane) || ac.isDestroyed()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/*  45 */     MCP_EntityPlane plane = (MCP_EntityPlane)ac;
/*  46 */     int seatID = ac.getSeatIdByEntity((Entity)player);
/*     */     
/*  48 */     GL11.glLineWidth(scaleFactor);
/*     */     
/*  50 */     if (plane.getCameraMode(player) == 1)
/*     */     {
/*  52 */       drawNightVisionNoise();
/*     */     }
/*     */     
/*  55 */     if (!isThirdPersonView || MCH_Config.DisplayHUDThirdPerson.prmBool)
/*     */     {
/*  57 */       if (seatID == 0 && plane.getIsGunnerMode((Entity)player)) {
/*     */         
/*  59 */         drawHud(ac, player, 1);
/*     */       }
/*     */       else {
/*     */         
/*  63 */         drawHud(ac, player, seatID);
/*     */       } 
/*     */     }
/*     */     
/*  67 */     drawDebugtInfo(plane);
/*     */     
/*  69 */     if (!isThirdPersonView || MCH_Config.DisplayHUDThirdPerson.prmBool)
/*     */     {
/*  71 */       if (plane.getTVMissile() != null && (plane.getIsGunnerMode((Entity)player) || plane.isUAV())) {
/*     */         
/*  73 */         drawTvMissileNoise(plane, plane.getTVMissile());
/*     */       }
/*     */       else {
/*     */         
/*  77 */         drawKeybind(plane, player, seatID);
/*     */       } 
/*     */     }
/*     */     
/*  81 */     drawHitBullet(plane, -14101432, seatID);
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawKeybind(MCP_EntityPlane plane, EntityPlayer player, int seatID) {
/*  86 */     if (MCH_Config.HideKeybind.prmBool) {
/*     */       return;
/*     */     }
/*  89 */     MCP_PlaneInfo info = plane.getPlaneInfo();
/*     */     
/*  91 */     if (info == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/*  96 */     int colorActive = -1342177281;
/*  97 */     int colorInactive = -1349546097;
/*  98 */     int RX = this.centerX + 120;
/*  99 */     int LX = this.centerX - 200;
/*     */     
/* 101 */     drawKeyBind(plane, info, player, seatID, RX, LX, colorActive, colorInactive);
/*     */     
/* 103 */     if (seatID == 0 && info.isEnableGunnerMode && !Keyboard.isKeyDown(MCH_Config.KeyFreeLook.prmInt)) {
/*     */       
/* 105 */       int c = plane.isHoveringMode() ? colorInactive : colorActive;
/*     */       
/* 107 */       String msg = (plane.getIsGunnerMode((Entity)player) ? "Normal" : "Gunner") + " : " + MCH_KeyName.getDescOrName(MCH_Config.KeySwitchMode.prmInt);
/*     */       
/* 109 */       drawString(msg, RX, this.centerY - 70, c);
/*     */     } 
/*     */     
/* 112 */     if (seatID > 0 && plane.canSwitchGunnerModeOtherSeat(player)) {
/*     */ 
/*     */       
/* 115 */       String msg = (plane.getIsGunnerMode((Entity)player) ? "Normal" : "Camera") + " : " + MCH_KeyName.getDescOrName(MCH_Config.KeySwitchMode.prmInt);
/*     */       
/* 117 */       drawString(msg, RX, this.centerY - 40, colorActive);
/*     */     } 
/*     */     
/* 120 */     if (seatID == 0 && info.isEnableVtol && !Keyboard.isKeyDown(MCH_Config.KeyFreeLook.prmInt)) {
/*     */       
/* 122 */       int stat = plane.getVtolMode();
/*     */       
/* 124 */       if (stat != 1) {
/*     */ 
/*     */         
/* 127 */         String msg = ((stat == 0) ? "VTOL : " : "Normal : ") + MCH_KeyName.getDescOrName(MCH_Config.KeyExtra.prmInt);
/* 128 */         drawString(msg, RX, this.centerY - 60, colorActive);
/*     */       } 
/*     */     } 
/*     */     
/* 132 */     if (plane.canEjectSeat((Entity)player)) {
/*     */       
/* 134 */       String msg = "Eject seat: " + MCH_KeyName.getDescOrName(MCH_Config.KeySwitchHovering.prmInt);
/*     */       
/* 136 */       drawString(msg, RX, this.centerY - 30, colorActive);
/*     */     } 
/*     */     
/* 139 */     if (plane.getIsGunnerMode((Entity)player) && info.cameraZoom > 1) {
/*     */       
/* 141 */       String msg = "Zoom : " + MCH_KeyName.getDescOrName(MCH_Config.KeyZoom.prmInt);
/*     */       
/* 143 */       drawString(msg, LX, this.centerY - 80, colorActive);
/*     */     }
/* 145 */     else if (seatID == 0) {
/*     */       
/* 147 */       if (plane.canFoldWing() || plane.canUnfoldWing()) {
/*     */         
/* 149 */         String msg = "FoldWing : " + MCH_KeyName.getDescOrName(MCH_Config.KeyZoom.prmInt);
/*     */         
/* 151 */         drawString(msg, LX, this.centerY - 80, colorActive);
/*     */       }
/* 153 */       else if (plane.canFoldHatch() || plane.canUnfoldHatch()) {
/*     */         
/* 155 */         String msg = "OpenHatch : " + MCH_KeyName.getDescOrName(MCH_Config.KeyZoom.prmInt);
/*     */         
/* 157 */         drawString(msg, LX, this.centerY - 80, colorActive);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\plane\MCP_GuiPlane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */