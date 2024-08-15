/*    */ package mcheli.hud;
/*    */ 
/*    */ import mcheli.MCH_Camera;
/*    */ import mcheli.MCH_Lib;
/*    */ import net.minecraft.entity.Entity;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MCH_HudItemCameraRot
/*    */   extends MCH_HudItem
/*    */ {
/*    */   private final String drawPosX;
/*    */   private final String drawPosY;
/*    */   
/*    */   public MCH_HudItemCameraRot(int fileLine, String posx, String posy) {
/* 20 */     super(fileLine);
/* 21 */     this.drawPosX = toFormula(posx);
/* 22 */     this.drawPosY = toFormula(posy);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void execute() {
/* 28 */     drawCommonGunnerCamera((Entity)ac, ac.camera, colorSetting, centerX + calc(this.drawPosX), centerY + 
/* 29 */         calc(this.drawPosY));
/*    */   }
/*    */ 
/*    */   
/*    */   private void drawCommonGunnerCamera(Entity ac, MCH_Camera camera, int color, double posX, double posY) {
/* 34 */     if (camera == null) {
/*    */       return;
/*    */     }
/*    */     
/* 38 */     double centerX = posX;
/* 39 */     double centerY = posY;
/*    */ 
/*    */ 
/*    */     
/* 43 */     double[] line = { centerX - 21.0D, centerY - 11.0D, centerX + 21.0D, centerY - 11.0D, centerX + 21.0D, centerY + 11.0D, centerX - 21.0D, centerY + 11.0D };
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 49 */     drawLine(line, color, 2);
/*    */     
/* 51 */     line = new double[] { centerX - 21.0D, centerY, centerX, centerY, centerX + 21.0D, centerY, centerX, centerY, centerX, centerY - 11.0D, centerX, centerY, centerX, centerY + 11.0D, centerX, centerY };
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 57 */     drawLineStipple(line, color, 1, 52428);
/*    */     
/* 59 */     float pitch = camera.rotationPitch;
/* 60 */     if (pitch < -30.0F)
/* 61 */       pitch = -30.0F; 
/* 62 */     if (pitch > 70.0F)
/* 63 */       pitch = 70.0F; 
/* 64 */     pitch -= 20.0F;
/* 65 */     pitch = (float)(pitch * 0.16D);
/*    */ 
/*    */ 
/*    */     
/* 69 */     float yaw = (float)MCH_Lib.getRotateDiff(ac.field_70177_z, camera.rotationYaw);
/*    */     
/* 71 */     yaw *= 2.0F;
/* 72 */     if (yaw < -50.0F)
/* 73 */       yaw = -50.0F; 
/* 74 */     if (yaw > 50.0F)
/* 75 */       yaw = 50.0F; 
/* 76 */     yaw = (float)(yaw * 0.34D);
/*    */     
/* 78 */     line = new double[] { centerX + yaw - 3.0D, centerY + pitch - 2.0D, centerX + yaw + 3.0D, centerY + pitch - 2.0D, centerX + yaw + 3.0D, centerY + pitch + 2.0D, centerX + yaw - 3.0D, centerY + pitch + 2.0D };
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 84 */     drawLine(line, color, 2);
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\hud\MCH_HudItemCameraRot.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */