/*     */ package mcheli.tank;
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
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @SideOnly(Side.CLIENT)
/*     */ public class MCH_GuiTank
/*     */   extends MCH_AircraftCommonGui
/*     */ {
/*     */   public MCH_GuiTank(Minecraft minecraft) {
/*  25 */     super(minecraft);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDrawGui(EntityPlayer player) {
/*  31 */     return MCH_EntityAircraft.getAircraft_RiddenOrControl((Entity)player) instanceof MCH_EntityTank;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawGui(EntityPlayer player, boolean isThirdPersonView) {
/*  37 */     MCH_EntityAircraft ac = MCH_EntityAircraft.getAircraft_RiddenOrControl((Entity)player);
/*     */     
/*  39 */     if (!(ac instanceof MCH_EntityTank) || ac.isDestroyed()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/*  44 */     MCH_EntityTank tank = (MCH_EntityTank)ac;
/*  45 */     int seatID = ac.getSeatIdByEntity((Entity)player);
/*     */     
/*  47 */     GL11.glLineWidth(scaleFactor);
/*     */     
/*  49 */     if (tank.getCameraMode(player) == 1)
/*     */     {
/*  51 */       drawNightVisionNoise();
/*     */     }
/*     */     
/*  54 */     if (!isThirdPersonView || MCH_Config.DisplayHUDThirdPerson.prmBool)
/*     */     {
/*  56 */       drawHud(ac, player, seatID);
/*     */     }
/*     */     
/*  59 */     drawDebugtInfo(tank);
/*     */     
/*  61 */     if (!isThirdPersonView || MCH_Config.DisplayHUDThirdPerson.prmBool)
/*     */     {
/*  63 */       if (tank.getTVMissile() != null && (tank.getIsGunnerMode((Entity)player) || tank.isUAV())) {
/*     */         
/*  65 */         drawTvMissileNoise(tank, tank.getTVMissile());
/*     */       }
/*     */       else {
/*     */         
/*  69 */         drawKeybind(tank, player, seatID);
/*     */       } 
/*     */     }
/*     */     
/*  73 */     drawHitBullet(tank, -14101432, seatID);
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawDebugtInfo(MCH_EntityTank ac) {
/*  78 */     if (MCH_Config.DebugLog)
/*     */     {
/*     */ 
/*     */       
/*  82 */       drawDebugtInfo(ac);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawKeybind(MCH_EntityTank tank, EntityPlayer player, int seatID) {
/*  88 */     if (MCH_Config.HideKeybind.prmBool) {
/*     */       return;
/*     */     }
/*  91 */     MCH_TankInfo info = tank.getTankInfo();
/*     */     
/*  93 */     if (info == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/*  98 */     int colorActive = -1342177281;
/*  99 */     int colorInactive = -1349546097;
/* 100 */     int RX = this.centerX + 120;
/* 101 */     int LX = this.centerX - 200;
/*     */     
/* 103 */     drawKeyBind(tank, info, player, seatID, RX, LX, colorActive, colorInactive);
/*     */     
/* 105 */     if (seatID == 0 && tank.hasBrake()) {
/*     */       
/* 107 */       String msg = "Brake : " + MCH_KeyName.getDescOrName(MCH_Config.KeySwitchHovering.prmInt);
/*     */       
/* 109 */       drawString(msg, RX, this.centerY - 30, colorActive);
/*     */     } 
/*     */     
/* 112 */     if (seatID > 0 && tank.canSwitchGunnerModeOtherSeat(player)) {
/*     */ 
/*     */       
/* 115 */       String msg = (tank.getIsGunnerMode((Entity)player) ? "Normal" : "Camera") + " : " + MCH_KeyName.getDescOrName(MCH_Config.KeySwitchMode.prmInt);
/*     */       
/* 117 */       drawString(msg, RX, this.centerY - 40, colorActive);
/*     */     } 
/*     */     
/* 120 */     if (tank.getIsGunnerMode((Entity)player) && info.cameraZoom > 1) {
/*     */       
/* 122 */       String msg = "Zoom : " + MCH_KeyName.getDescOrName(MCH_Config.KeyZoom.prmInt);
/*     */       
/* 124 */       drawString(msg, LX, this.centerY - 80, colorActive);
/*     */     }
/* 126 */     else if (seatID == 0) {
/*     */       
/* 128 */       if (tank.canFoldHatch() || tank.canUnfoldHatch()) {
/*     */         
/* 130 */         String msg = "OpenHatch : " + MCH_KeyName.getDescOrName(MCH_Config.KeyZoom.prmInt);
/*     */         
/* 132 */         drawString(msg, LX, this.centerY - 80, colorActive);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\tank\MCH_GuiTank.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */