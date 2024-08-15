/*     */ package mcheli.helicopter;
/*     */ 
/*     */ import mcheli.MCH_Config;
/*     */ import mcheli.MCH_KeyName;
/*     */ import mcheli.MCH_Lib;
/*     */ import mcheli.aircraft.MCH_AircraftCommonGui;
/*     */ import mcheli.aircraft.MCH_EntityAircraft;
/*     */ import mcheli.weapon.MCH_EntityTvMissile;
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
/*     */ public class MCH_GuiHeli
/*     */   extends MCH_AircraftCommonGui
/*     */ {
/*     */   public MCH_GuiHeli(Minecraft minecraft) {
/*  28 */     super(minecraft);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDrawGui(EntityPlayer player) {
/*  34 */     return MCH_EntityAircraft.getAircraft_RiddenOrControl((Entity)player) instanceof MCH_EntityHeli;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawGui(EntityPlayer player, boolean isThirdPersonView) {
/*  40 */     MCH_EntityAircraft ac = MCH_EntityAircraft.getAircraft_RiddenOrControl((Entity)player);
/*     */     
/*  42 */     if (!(ac instanceof MCH_EntityHeli) || ac.isDestroyed()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/*  47 */     MCH_EntityHeli heli = (MCH_EntityHeli)ac;
/*  48 */     int seatID = ac.getSeatIdByEntity((Entity)player);
/*     */     
/*  50 */     GL11.glLineWidth(scaleFactor);
/*     */     
/*  52 */     if (heli.getCameraMode(player) == 1)
/*     */     {
/*  54 */       drawNightVisionNoise();
/*     */     }
/*     */     
/*  57 */     if (!isThirdPersonView || MCH_Config.DisplayHUDThirdPerson.prmBool)
/*     */     {
/*  59 */       if (seatID == 0 && heli.getIsGunnerMode((Entity)player)) {
/*     */         
/*  61 */         drawHud(ac, player, 1);
/*     */       }
/*     */       else {
/*     */         
/*  65 */         drawHud(ac, player, seatID);
/*     */       } 
/*     */     }
/*     */     
/*  69 */     drawDebugtInfo(heli);
/*     */     
/*  71 */     if (!heli.getIsGunnerMode((Entity)player)) {
/*     */       
/*  73 */       if (!isThirdPersonView || MCH_Config.DisplayHUDThirdPerson.prmBool)
/*     */       {
/*  75 */         drawKeyBind(heli, player, seatID);
/*     */       }
/*     */       
/*  78 */       drawHitBullet(heli, -14101432, seatID);
/*     */     }
/*     */     else {
/*     */       
/*  82 */       if (!isThirdPersonView || MCH_Config.DisplayHUDThirdPerson.prmBool) {
/*     */         
/*  84 */         MCH_EntityTvMissile tvmissile = heli.getTVMissile();
/*     */         
/*  86 */         if (!heli.isMissileCameraMode((Entity)player)) {
/*     */           
/*  88 */           drawKeyBind(heli, player, seatID);
/*     */         }
/*  90 */         else if (tvmissile != null) {
/*     */           
/*  92 */           drawTvMissileNoise(heli, tvmissile);
/*     */         } 
/*     */       } 
/*     */       
/*  96 */       drawHitBullet(heli, -805306369, seatID);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawKeyBind(MCH_EntityHeli heli, EntityPlayer player, int seatID) {
/* 102 */     if (MCH_Config.HideKeybind.prmBool) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 107 */     MCH_HeliInfo info = heli.getHeliInfo();
/*     */     
/* 109 */     if (info == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 114 */     int colorActive = -1342177281;
/* 115 */     int colorInactive = -1349546097;
/* 116 */     int RX = this.centerX + 120;
/* 117 */     int LX = this.centerX - 200;
/*     */     
/* 119 */     drawKeyBind(heli, info, player, seatID, RX, LX, colorActive, colorInactive);
/*     */     
/* 121 */     if (seatID == 0 && info.isEnableGunnerMode && !Keyboard.isKeyDown(MCH_Config.KeyFreeLook.prmInt)) {
/*     */       
/* 123 */       int c = heli.isHoveringMode() ? colorInactive : colorActive;
/*     */       
/* 125 */       String msg = (heli.getIsGunnerMode((Entity)player) ? "Normal" : "Gunner") + " : " + MCH_KeyName.getDescOrName(MCH_Config.KeySwitchMode.prmInt);
/*     */       
/* 127 */       drawString(msg, RX, this.centerY - 70, c);
/*     */     } 
/*     */     
/* 130 */     if (seatID > 0 && heli.canSwitchGunnerModeOtherSeat(player)) {
/*     */ 
/*     */       
/* 133 */       String msg = (heli.getIsGunnerMode((Entity)player) ? "Normal" : "Camera") + " : " + MCH_KeyName.getDescOrName(MCH_Config.KeySwitchMode.prmInt);
/*     */       
/* 135 */       drawString(msg, RX, this.centerY - 40, colorActive);
/*     */     } 
/*     */     
/* 138 */     if (seatID == 0 && !Keyboard.isKeyDown(MCH_Config.KeyFreeLook.prmInt)) {
/*     */       
/* 140 */       int c = heli.getIsGunnerMode((Entity)player) ? colorInactive : colorActive;
/*     */       
/* 142 */       String msg = (heli.getIsGunnerMode((Entity)player) ? "Normal" : "Hovering") + " : " + MCH_KeyName.getDescOrName(MCH_Config.KeySwitchHovering.prmInt);
/*     */       
/* 144 */       drawString(msg, RX, this.centerY - 60, c);
/*     */     } 
/*     */     
/* 147 */     if (seatID == 0)
/*     */     {
/* 149 */       if (heli.getTowChainEntity() != null && !(heli.getTowChainEntity()).field_70128_L) {
/*     */         
/* 151 */         String msg = "Drop  : " + MCH_KeyName.getDescOrName(MCH_Config.KeyExtra.prmInt);
/*     */         
/* 153 */         drawString(msg, RX, this.centerY - 30, colorActive);
/*     */       }
/* 155 */       else if (info.isEnableFoldBlade && 
/* 156 */         MCH_Lib.getBlockIdY(heli.field_70170_p, heli.field_70165_t, heli.field_70163_u, heli.field_70161_v, 1, -2, true) > 0 && heli
/* 157 */         .getCurrentThrottle() <= 0.01D) {
/*     */         
/* 159 */         String msg = "FoldBlade  : " + MCH_KeyName.getDescOrName(MCH_Config.KeyExtra.prmInt);
/*     */         
/* 161 */         drawString(msg, RX, this.centerY - 30, colorActive);
/*     */       } 
/*     */     }
/*     */     
/* 165 */     if ((heli.getIsGunnerMode((Entity)player) || heli.isUAV()) && info.cameraZoom > 1) {
/*     */       
/* 167 */       String msg = "Zoom : " + MCH_KeyName.getDescOrName(MCH_Config.KeyZoom.prmInt);
/*     */       
/* 169 */       drawString(msg, LX, this.centerY - 80, colorActive);
/*     */     }
/* 171 */     else if (seatID == 0) {
/*     */       
/* 173 */       if (heli.canFoldHatch() || heli.canUnfoldHatch()) {
/*     */         
/* 175 */         String msg = "OpenHatch : " + MCH_KeyName.getDescOrName(MCH_Config.KeyZoom.prmInt);
/*     */         
/* 177 */         drawString(msg, LX, this.centerY - 80, colorActive);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\helicopter\MCH_GuiHeli.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */