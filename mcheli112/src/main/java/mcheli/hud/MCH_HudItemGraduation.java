package mcheli.hud;

import mcheli.MCH_Lib;
import org.lwjgl.opengl.GL11;

public class MCH_HudItemGraduation extends MCH_HudItem {
  private final String drawRot;
  
  private final String drawRoll;
  
  private final String drawPosX;
  
  private final String drawPosY;
  
  private final int type;
  
  public MCH_HudItemGraduation(int fileLine, int type, String rot, String roll, String posx, String posy) {
    super(fileLine);
    this.drawRot = toFormula(rot);
    this.drawRoll = toFormula(roll);
    this.drawPosX = toFormula(posx);
    this.drawPosY = toFormula(posy);
    this.type = type;
  }
  
  public void execute() {
    GL11.glPushMatrix();
    int x = (int)(centerX + calc(this.drawPosX));
    int y = (int)(centerY + calc(this.drawPosY));
    GL11.glTranslated(x, y, 0.0D);
    GL11.glRotatef((float)calc(this.drawRoll), 0.0F, 0.0F, 1.0F);
    GL11.glTranslated(-x, -y, 0.0D);
    if (this.type == 0) {
      drawCommonGraduationYaw(calc(this.drawRot), colorSetting, x, y);
    } else if (this.type == 1) {
      drawCommonGraduationPitch1(calc(this.drawRot), colorSetting, x, y);
    } else if (this.type == 2 || this.type == 3) {
      drawCommonGraduationPitch2(calc(this.drawRot), colorSetting, x, y);
    } 
    GL11.glPopMatrix();
  }
  
  private void drawCommonGraduationPitch2(double playerPitch, int color, int posX, int posY) {
    playerPitch = -playerPitch;
    int pitch_n = (int)playerPitch / 5 * 5;
    double[] line = new double[8];
    int start = (this.type == 2) ? 0 : 1;
    int end = (this.type == 2) ? 5 : 4;
    int INT = (this.type == 2) ? 1 : 2;
    for (int i = start; i < end; i++) {
      int pitch = -(-pitch_n - 10 + i * 5);
      double p_rest = playerPitch % 5.0D;
      int x = (pitch != 0) ? 50 : 100;
      int y = posY + (int)((-60 * INT) + p_rest * 6.0D * INT + (i * 30 * INT));
      line[0] = (posX - x);
      line[1] = (y + ((pitch > 0) ? 2 : ((pitch == 0) ? 0 : -2)));
      line[2] = (posX - 50);
      line[3] = y;
      line[4] = (posX + x);
      line[5] = line[1];
      line[6] = (posX + 50);
      line[7] = y;
      drawLine(line, color);
      line[0] = (posX - 50);
      line[1] = y;
      line[2] = (posX - 30);
      line[3] = y;
      line[4] = (posX + 50);
      line[5] = y;
      line[6] = (posX + 30);
      line[7] = y;
      if (pitch >= 0) {
        drawLine(line, color);
      } else {
        drawLineStipple(line, color, 1, 52428);
      } 
      if (pitch != 0) {
        drawCenteredString("" + pitch, posX - 50 - 10, y - 4, color);
        drawCenteredString("" + pitch, posX + 50 + 10, y - 4, color);
      } 
    } 
  }
  
  private void drawCommonGraduationPitch1(double playerPitch, int color, int posX, int posY) {
    int pitch = (int)playerPitch % 360;
    int y = (int)(playerPitch * 10.0D % 10.0D);
    if (y < 0)
      y += 10; 
    int posX_L = posX - 100;
    int posX_R = posX + 100;
    int linePosY = posY;
    posY -= 80;
    double[] line = new double[144];
    int p = (playerPitch >= 0.0D || y == 0) ? (pitch - 8) : (pitch - 9);
    for (int i = 0; i < line.length / 8; p++) {
      int olx = (p % 3 == 0) ? 15 : 5;
      int ilx = 0;
      line[i * 8 + 0] = (posX_L - olx);
      line[i * 8 + 1] = (posY + i * 10 - y);
      line[i * 8 + 2] = (posX_L + ilx);
      line[i * 8 + 3] = (posY + i * 10 - y);
      line[i * 8 + 4] = (posX_R + olx);
      line[i * 8 + 5] = (posY + i * 10 - y);
      line[i * 8 + 6] = (posX_R - ilx);
      line[i * 8 + 7] = (posY + i * 10 - y);
      i++;
    } 
    drawLine(line, color);
    double[] verticalLine = { (posX_L - 25), (linePosY - 90), posX_L, (linePosY - 90), posX_L, (linePosY + 90), (posX_L - 25), (linePosY + 90) };
    drawLine(verticalLine, color, 3);
    verticalLine = new double[] { (posX_R + 25), (linePosY - 90), posX_R, (linePosY - 90), posX_R, (linePosY + 90), (posX_R + 25), (linePosY + 90) };
    drawLine(verticalLine, color, 3);
  }
  
  private void drawCommonGraduationYaw(double playerYaw, int color, int posX, int posY) {
    double yaw = MCH_Lib.getRotate360(playerYaw);
    posX -= 90;
    double[] line = new double[76];
    int x = (int)(yaw * 10.0D) % 10;
    int y = (int)yaw - 9;
    for (int i = 0; i < line.length / 4; y++) {
      int azPosX = posX + i * 10 - x;
      line[i * 4 + 0] = azPosX;
      line[i * 4 + 1] = posY;
      line[i * 4 + 2] = azPosX;
      line[i * 4 + 3] = (posY + ((y % 3 == 0) ? 10 : ((y % 45 == 0) ? 15 : 5)));
      if (y % 45 == 0) {
        drawCenteredString(MCH_Lib.getAzimuthStr8(y), azPosX, posY - 10, -65536);
      } else if (y % 3 == 0) {
        int rot = y + 180;
        if (rot < 0)
          rot += 360; 
        if (rot > 360)
          rot -= 360; 
        drawCenteredString(String.format("%d", new Object[] { Integer.valueOf(rot) }), azPosX, posY - 10, color);
      } 
      i++;
    } 
    drawLine(line, color);
  }
}
