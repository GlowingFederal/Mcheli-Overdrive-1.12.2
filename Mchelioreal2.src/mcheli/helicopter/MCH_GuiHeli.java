package mcheli.helicopter;

import mcheli.MCH_Config;
import mcheli.MCH_KeyName;
import mcheli.MCH_Lib;
import mcheli.aircraft.MCH_AircraftCommonGui;
import mcheli.aircraft.MCH_EntityAircraft;
import mcheli.weapon.MCH_EntityTvMissile;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class MCH_GuiHeli extends MCH_AircraftCommonGui {
  public MCH_GuiHeli(Minecraft minecraft) {
    super(minecraft);
  }
  
  public boolean isDrawGui(EntityPlayer player) {
    return MCH_EntityAircraft.getAircraft_RiddenOrControl((Entity)player) instanceof MCH_EntityHeli;
  }
  
  public void drawGui(EntityPlayer player, boolean isThirdPersonView) {
    MCH_EntityAircraft ac = MCH_EntityAircraft.getAircraft_RiddenOrControl((Entity)player);
    if (!(ac instanceof MCH_EntityHeli) || ac.isDestroyed())
      return; 
    MCH_EntityHeli heli = (MCH_EntityHeli)ac;
    int seatID = ac.getSeatIdByEntity((Entity)player);
    GL11.glLineWidth(scaleFactor);
    if (heli.getCameraMode(player) == 1)
      drawNightVisionNoise(); 
    if (!isThirdPersonView || MCH_Config.DisplayHUDThirdPerson.prmBool)
      if (seatID == 0 && heli.getIsGunnerMode((Entity)player)) {
        drawHud(ac, player, 1);
      } else {
        drawHud(ac, player, seatID);
      }  
    drawDebugtInfo(heli);
    if (!heli.getIsGunnerMode((Entity)player)) {
      if (!isThirdPersonView || MCH_Config.DisplayHUDThirdPerson.prmBool)
        drawKeyBind(heli, player, seatID); 
      drawHitBullet(heli, -14101432, seatID);
    } else {
      if (!isThirdPersonView || MCH_Config.DisplayHUDThirdPerson.prmBool) {
        MCH_EntityTvMissile tvmissile = heli.getTVMissile();
        if (!heli.isMissileCameraMode((Entity)player)) {
          drawKeyBind(heli, player, seatID);
        } else if (tvmissile != null) {
          drawTvMissileNoise(heli, tvmissile);
        } 
      } 
      drawHitBullet(heli, -805306369, seatID);
    } 
  }
  
  public void drawKeyBind(MCH_EntityHeli heli, EntityPlayer player, int seatID) {
    if (MCH_Config.HideKeybind.prmBool)
      return; 
    MCH_HeliInfo info = heli.getHeliInfo();
    if (info == null)
      return; 
    int colorActive = -1342177281;
    int colorInactive = -1349546097;
    int RX = this.centerX + 120;
    int LX = this.centerX - 200;
    drawKeyBind(heli, info, player, seatID, RX, LX, colorActive, colorInactive);
    if (seatID == 0 && info.isEnableGunnerMode && !Keyboard.isKeyDown(MCH_Config.KeyFreeLook.prmInt)) {
      int c = heli.isHoveringMode() ? colorInactive : colorActive;
      String msg = (heli.getIsGunnerMode((Entity)player) ? "Normal" : "Gunner") + " : " + MCH_KeyName.getDescOrName(MCH_Config.KeySwitchMode.prmInt);
      drawString(msg, RX, this.centerY - 70, c);
    } 
    if (seatID > 0 && heli.canSwitchGunnerModeOtherSeat(player)) {
      String msg = (heli.getIsGunnerMode((Entity)player) ? "Normal" : "Camera") + " : " + MCH_KeyName.getDescOrName(MCH_Config.KeySwitchMode.prmInt);
      drawString(msg, RX, this.centerY - 40, colorActive);
    } 
    if (seatID == 0 && !Keyboard.isKeyDown(MCH_Config.KeyFreeLook.prmInt)) {
      int c = heli.getIsGunnerMode((Entity)player) ? colorInactive : colorActive;
      String msg = (heli.getIsGunnerMode((Entity)player) ? "Normal" : "Hovering") + " : " + MCH_KeyName.getDescOrName(MCH_Config.KeySwitchHovering.prmInt);
      drawString(msg, RX, this.centerY - 60, c);
    } 
    if (seatID == 0)
      if (heli.getTowChainEntity() != null && !(heli.getTowChainEntity()).field_70128_L) {
        String msg = "Drop  : " + MCH_KeyName.getDescOrName(MCH_Config.KeyExtra.prmInt);
        drawString(msg, RX, this.centerY - 30, colorActive);
      } else if (info.isEnableFoldBlade && 
        MCH_Lib.getBlockIdY(heli.field_70170_p, heli.field_70165_t, heli.field_70163_u, heli.field_70161_v, 1, -2, true) > 0 && heli
        .getCurrentThrottle() <= 0.01D) {
        String msg = "FoldBlade  : " + MCH_KeyName.getDescOrName(MCH_Config.KeyExtra.prmInt);
        drawString(msg, RX, this.centerY - 30, colorActive);
      }  
    if ((heli.getIsGunnerMode((Entity)player) || heli.isUAV()) && info.cameraZoom > 1) {
      String msg = "Zoom : " + MCH_KeyName.getDescOrName(MCH_Config.KeyZoom.prmInt);
      drawString(msg, LX, this.centerY - 80, colorActive);
    } else if (seatID == 0) {
      if (heli.canFoldHatch() || heli.canUnfoldHatch()) {
        String msg = "OpenHatch : " + MCH_KeyName.getDescOrName(MCH_Config.KeyZoom.prmInt);
        drawString(msg, LX, this.centerY - 80, colorActive);
      } 
    } 
  }
}
