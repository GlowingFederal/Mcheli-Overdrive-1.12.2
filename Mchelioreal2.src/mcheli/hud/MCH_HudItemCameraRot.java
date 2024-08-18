package mcheli.hud;

import mcheli.MCH_Camera;
import mcheli.MCH_Lib;
import net.minecraft.entity.Entity;

public class MCH_HudItemCameraRot extends MCH_HudItem {
  private final String drawPosX;
  
  private final String drawPosY;
  
  public MCH_HudItemCameraRot(int fileLine, String posx, String posy) {
    super(fileLine);
    this.drawPosX = toFormula(posx);
    this.drawPosY = toFormula(posy);
  }
  
  public void execute() {
    drawCommonGunnerCamera((Entity)ac, ac.camera, colorSetting, centerX + calc(this.drawPosX), centerY + 
        calc(this.drawPosY));
  }
  
  private void drawCommonGunnerCamera(Entity ac, MCH_Camera camera, int color, double posX, double posY) {
    if (camera == null)
      return; 
    double centerX = posX;
    double centerY = posY;
    double[] line = { centerX - 21.0D, centerY - 11.0D, centerX + 21.0D, centerY - 11.0D, centerX + 21.0D, centerY + 11.0D, centerX - 21.0D, centerY + 11.0D };
    drawLine(line, color, 2);
    line = new double[] { 
        centerX - 21.0D, centerY, centerX, centerY, centerX + 21.0D, centerY, centerX, centerY, centerX, centerY - 11.0D, 
        centerX, centerY, centerX, centerY + 11.0D, centerX, centerY };
    drawLineStipple(line, color, 1, 52428);
    float pitch = camera.rotationPitch;
    if (pitch < -30.0F)
      pitch = -30.0F; 
    if (pitch > 70.0F)
      pitch = 70.0F; 
    pitch -= 20.0F;
    pitch = (float)(pitch * 0.16D);
    float yaw = (float)MCH_Lib.getRotateDiff(ac.field_70177_z, camera.rotationYaw);
    yaw *= 2.0F;
    if (yaw < -50.0F)
      yaw = -50.0F; 
    if (yaw > 50.0F)
      yaw = 50.0F; 
    yaw = (float)(yaw * 0.34D);
    line = new double[] { centerX + yaw - 3.0D, centerY + pitch - 2.0D, centerX + yaw + 3.0D, centerY + pitch - 2.0D, centerX + yaw + 3.0D, centerY + pitch + 2.0D, centerX + yaw - 3.0D, centerY + pitch + 2.0D };
    drawLine(line, color, 2);
  }
}
