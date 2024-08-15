/*     */ package mcheli.aircraft;
/*     */ 
/*     */ import mcheli.MCH_Config;
/*     */ import mcheli.MCH_KeyName;
/*     */ import mcheli.MCH_Lib;
/*     */ import mcheli.gui.MCH_Gui;
/*     */ import mcheli.hud.MCH_Hud;
/*     */ import mcheli.weapon.MCH_EntityTvMissile;
/*     */ import mcheli.weapon.MCH_WeaponSet;
/*     */ import mcheli.wrapper.W_McClient;
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
/*     */ public abstract class MCH_AircraftCommonGui
/*     */   extends MCH_Gui
/*     */ {
/*     */   public MCH_AircraftCommonGui(Minecraft minecraft) {
/*  30 */     super(minecraft);
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawHud(MCH_EntityAircraft ac, EntityPlayer player, int seatId) {
/*  35 */     MCH_AircraftInfo info = ac.getAcInfo();
/*  36 */     if (info == null) {
/*     */       return;
/*     */     }
/*     */     
/*  40 */     if (ac.isMissileCameraMode((Entity)player) && ac.getTVMissile() != null && info.hudTvMissile != null) {
/*     */       
/*  42 */       info.hudTvMissile.draw(ac, player, this.smoothCamPartialTicks);
/*     */     }
/*     */     else {
/*     */       
/*  46 */       if (seatId < 0)
/*     */         return; 
/*  48 */       if (seatId < info.hudList.size()) {
/*     */         
/*  50 */         MCH_Hud hud = info.hudList.get(seatId);
/*  51 */         if (hud != null)
/*     */         {
/*  53 */           hud.draw(ac, player, this.smoothCamPartialTicks);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawDebugtInfo(MCH_EntityAircraft ac) {
/*  62 */     if (MCH_Config.DebugLog);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawNightVisionNoise() {
/*  70 */     GL11.glEnable(3042);
/*  71 */     GL11.glColor4f(0.0F, 1.0F, 0.0F, 0.3F);
/*  72 */     int srcBlend = GL11.glGetInteger(3041);
/*  73 */     int dstBlend = GL11.glGetInteger(3040);
/*     */     
/*  75 */     GL11.glBlendFunc(1, 1);
/*     */     
/*  77 */     W_McClient.MOD_bindTexture("textures/gui/alpha.png");
/*  78 */     drawTexturedModalRectRotate(0.0D, 0.0D, this.field_146294_l, this.field_146295_m, this.rand.nextInt(256), this.rand.nextInt(256), 256.0D, 256.0D, 0.0F);
/*     */ 
/*     */     
/*  81 */     GL11.glBlendFunc(srcBlend, dstBlend);
/*  82 */     GL11.glDisable(3042);
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawHitBullet(int hs, int hsMax, int color) {
/*  87 */     if (hs > 0) {
/*     */       
/*  89 */       int cx = this.centerX;
/*  90 */       int cy = this.centerY;
/*  91 */       int IVX = 10;
/*  92 */       int IVY = 10;
/*  93 */       int SZX = 5;
/*  94 */       int SZY = 5;
/*  95 */       double[] ls = { (cx - IVX), (cy - IVY), (cx - SZX), (cy - SZY), (cx - IVX), (cy + IVY), (cx - SZX), (cy + SZY), (cx + IVX), (cy - IVY), (cx + SZX), (cy - SZY), (cx + IVX), (cy + IVY), (cx + SZX), (cy + SZY) };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 101 */       color = MCH_Config.hitMarkColorRGB;
/* 102 */       int alpha = hs * 256 / hsMax;
/* 103 */       color |= (int)(MCH_Config.hitMarkColorAlpha * alpha) << 24;
/* 104 */       drawLine(ls, color);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawHitBullet(MCH_EntityAircraft ac, int color, int seatID) {
/* 110 */     drawHitBullet(ac.getHitStatus(), ac.getMaxHitStatus(), color);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void drawTvMissileNoise(MCH_EntityAircraft ac, MCH_EntityTvMissile tvmissile) {
/* 115 */     GL11.glEnable(3042);
/* 116 */     GL11.glColor4f(0.5F, 0.5F, 0.5F, 0.4F);
/* 117 */     int srcBlend = GL11.glGetInteger(3041);
/* 118 */     int dstBlend = GL11.glGetInteger(3040);
/*     */     
/* 120 */     GL11.glBlendFunc(1, 1);
/*     */     
/* 122 */     W_McClient.MOD_bindTexture("textures/gui/noise.png");
/* 123 */     drawTexturedModalRectRotate(0.0D, 0.0D, this.field_146294_l, this.field_146295_m, this.rand.nextInt(256), this.rand.nextInt(256), 256.0D, 256.0D, 0.0F);
/*     */ 
/*     */     
/* 126 */     GL11.glBlendFunc(srcBlend, dstBlend);
/* 127 */     GL11.glDisable(3042);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawKeyBind(MCH_EntityAircraft ac, MCH_AircraftInfo info, EntityPlayer player, int seatID, int RX, int LX, int colorActive, int colorInactive) {
/* 133 */     String msg = "";
/* 134 */     int c = 0;
/*     */     
/* 136 */     if (seatID == 0 && ac.canPutToRack()) {
/*     */       
/* 138 */       msg = "PutRack : " + MCH_KeyName.getDescOrName(MCH_Config.KeyPutToRack.prmInt);
/* 139 */       drawString(msg, LX, this.centerY - 10, colorActive);
/*     */     } 
/* 141 */     if (seatID == 0 && ac.canDownFromRack()) {
/*     */       
/* 143 */       msg = "DownRack : " + MCH_KeyName.getDescOrName(MCH_Config.KeyDownFromRack.prmInt);
/* 144 */       drawString(msg, LX, this.centerY - 0, colorActive);
/*     */     } 
/* 146 */     if (seatID == 0 && ac.canRideRack()) {
/*     */       
/* 148 */       msg = "RideRack : " + MCH_KeyName.getDescOrName(MCH_Config.KeyPutToRack.prmInt);
/* 149 */       drawString(msg, LX, this.centerY + 10, colorActive);
/*     */     } 
/* 151 */     if (seatID == 0 && ac.func_184187_bx() != null) {
/*     */       
/* 153 */       msg = "DismountRack : " + MCH_KeyName.getDescOrName(MCH_Config.KeyDownFromRack.prmInt);
/* 154 */       drawString(msg, LX, this.centerY + 10, colorActive);
/*     */     } 
/*     */     
/* 157 */     if ((seatID > 0 && ac.getSeatNum() > 1) || Keyboard.isKeyDown(MCH_Config.KeyFreeLook.prmInt)) {
/*     */       
/* 159 */       c = (seatID == 0) ? 65328 : colorActive;
/* 160 */       String sk = (seatID == 0) ? (MCH_KeyName.getDescOrName(MCH_Config.KeyFreeLook.prmInt) + " + ") : "";
/* 161 */       msg = "NextSeat : " + sk + MCH_KeyName.getDescOrName(MCH_Config.KeyGUI.prmInt);
/* 162 */       drawString(msg, RX, this.centerY - 70, c);
/* 163 */       msg = "PrevSeat : " + sk + MCH_KeyName.getDescOrName(MCH_Config.KeyExtra.prmInt);
/* 164 */       drawString(msg, RX, this.centerY - 60, c);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 169 */     msg = "Gunner " + (ac.getGunnerStatus() ? "ON" : "OFF") + " : " + MCH_KeyName.getDescOrName(MCH_Config.KeyFreeLook.prmInt) + " + " + MCH_KeyName.getDescOrName(MCH_Config.KeyCameraMode.prmInt);
/*     */     
/* 171 */     drawString(msg, LX, this.centerY - 40, colorActive);
/*     */     
/* 173 */     if (seatID >= 0 && seatID <= 1 && ac.haveFlare()) {
/*     */       
/* 175 */       c = ac.isFlarePreparation() ? colorInactive : colorActive;
/* 176 */       msg = "Flare : " + MCH_KeyName.getDescOrName(MCH_Config.KeyFlare.prmInt);
/* 177 */       drawString(msg, RX, this.centerY - 50, c);
/*     */     } 
/*     */     
/* 180 */     if (seatID == 0 && info.haveLandingGear())
/*     */     {
/* 182 */       if (ac.canFoldLandingGear()) {
/*     */         
/* 184 */         msg = "Gear Up : " + MCH_KeyName.getDescOrName(MCH_Config.KeyGearUpDown.prmInt);
/* 185 */         drawString(msg, RX, this.centerY - 40, colorActive);
/*     */       }
/* 187 */       else if (ac.canUnfoldLandingGear()) {
/*     */         
/* 189 */         msg = "Gear Down : " + MCH_KeyName.getDescOrName(MCH_Config.KeyGearUpDown.prmInt);
/* 190 */         drawString(msg, RX, this.centerY - 40, colorActive);
/*     */       } 
/*     */     }
/*     */     
/* 194 */     MCH_WeaponSet ws = ac.getCurrentWeapon((Entity)player);
/* 195 */     if (ac.getWeaponNum() > 1) {
/*     */       
/* 197 */       msg = "Weapon : " + MCH_KeyName.getDescOrName(MCH_Config.KeySwitchWeapon2.prmInt);
/* 198 */       drawString(msg, LX, this.centerY - 70, colorActive);
/*     */     } 
/* 200 */     if ((ws.getCurrentWeapon()).numMode > 0) {
/*     */       
/* 202 */       msg = "WeaponMode : " + MCH_KeyName.getDescOrName(MCH_Config.KeySwWeaponMode.prmInt);
/* 203 */       drawString(msg, LX, this.centerY - 60, colorActive);
/*     */     } 
/*     */     
/* 206 */     if (ac.canSwitchSearchLight((Entity)player)) {
/*     */       
/* 208 */       msg = "SearchLight : " + MCH_KeyName.getDescOrName(MCH_Config.KeyCameraMode.prmInt);
/* 209 */       drawString(msg, LX, this.centerY - 50, colorActive);
/*     */     
/*     */     }
/* 212 */     else if (ac.canSwitchCameraMode(seatID)) {
/*     */       
/* 214 */       msg = "CameraMode : " + MCH_KeyName.getDescOrName(MCH_Config.KeyCameraMode.prmInt);
/* 215 */       drawString(msg, LX, this.centerY - 50, colorActive);
/*     */     } 
/*     */     
/* 218 */     if (seatID == 0 && ac.getSeatNum() >= 1) {
/*     */       
/* 220 */       int color = colorActive;
/* 221 */       if (info.isEnableParachuting && MCH_Lib.getBlockIdY((Entity)ac, 3, -10) == 0) {
/*     */         
/* 223 */         msg = "Parachuting : " + MCH_KeyName.getDescOrName(MCH_Config.KeyUnmount.prmInt);
/*     */       }
/* 225 */       else if (ac.canStartRepelling()) {
/*     */         
/* 227 */         msg = "Repelling : " + MCH_KeyName.getDescOrName(MCH_Config.KeyUnmount.prmInt);
/* 228 */         color = 65280;
/*     */       }
/*     */       else {
/*     */         
/* 232 */         msg = "Dismount : " + MCH_KeyName.getDescOrName(MCH_Config.KeyUnmount.prmInt);
/*     */       } 
/* 234 */       drawString(msg, LX, this.centerY - 30, color);
/*     */     } 
/*     */     
/* 237 */     if ((seatID == 0 && ac.canSwitchFreeLook()) || (seatID > 0 && ac.canSwitchGunnerModeOtherSeat(player))) {
/*     */       
/* 239 */       msg = "FreeLook : " + MCH_KeyName.getDescOrName(MCH_Config.KeyFreeLook.prmInt);
/* 240 */       drawString(msg, LX, this.centerY - 20, colorActive);
/*     */     } 
/*     */     
/* 243 */     if (seatID > 1 && info.haveRepellingHook() && ac.canRepelling((Entity)player)) {
/*     */       
/* 245 */       msg = "Use Repelling Drop : " + MCH_KeyName.getDescOrName(MCH_Config.KeySwitchHovering.prmInt);
/* 246 */       drawString(msg, LX, this.centerY - 90, colorActive);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\aircraft\MCH_AircraftCommonGui.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */