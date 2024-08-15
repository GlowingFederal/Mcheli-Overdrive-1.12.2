/*     */ package mcheli.hud;
/*     */ 
/*     */ import mcheli.MCH_Lib;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MCH_HudItemGraduation
/*     */   extends MCH_HudItem
/*     */ {
/*     */   private final String drawRot;
/*     */   private final String drawRoll;
/*     */   private final String drawPosX;
/*     */   private final String drawPosY;
/*     */   private final int type;
/*     */   
/*     */   public MCH_HudItemGraduation(int fileLine, int type, String rot, String roll, String posx, String posy) {
/*  23 */     super(fileLine);
/*  24 */     this.drawRot = toFormula(rot);
/*  25 */     this.drawRoll = toFormula(roll);
/*  26 */     this.drawPosX = toFormula(posx);
/*  27 */     this.drawPosY = toFormula(posy);
/*  28 */     this.type = type;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute() {
/*  34 */     GL11.glPushMatrix();
/*     */     
/*  36 */     int x = (int)(centerX + calc(this.drawPosX));
/*  37 */     int y = (int)(centerY + calc(this.drawPosY));
/*     */     
/*  39 */     GL11.glTranslated(x, y, 0.0D);
/*  40 */     GL11.glRotatef((float)calc(this.drawRoll), 0.0F, 0.0F, 1.0F);
/*  41 */     GL11.glTranslated(-x, -y, 0.0D);
/*     */     
/*  43 */     if (this.type == 0) {
/*     */       
/*  45 */       drawCommonGraduationYaw(calc(this.drawRot), colorSetting, x, y);
/*     */     }
/*  47 */     else if (this.type == 1) {
/*     */       
/*  49 */       drawCommonGraduationPitch1(calc(this.drawRot), colorSetting, x, y);
/*     */     }
/*  51 */     else if (this.type == 2 || this.type == 3) {
/*     */       
/*  53 */       drawCommonGraduationPitch2(calc(this.drawRot), colorSetting, x, y);
/*     */     } 
/*     */     
/*  56 */     GL11.glPopMatrix();
/*     */   }
/*     */ 
/*     */   
/*     */   private void drawCommonGraduationPitch2(double playerPitch, int color, int posX, int posY) {
/*  61 */     playerPitch = -playerPitch;
/*     */     
/*  63 */     int pitch_n = (int)playerPitch / 5 * 5;
/*     */     
/*  65 */     double[] line = new double[8];
/*     */     
/*  67 */     int start = (this.type == 2) ? 0 : 1;
/*  68 */     int end = (this.type == 2) ? 5 : 4;
/*  69 */     int INT = (this.type == 2) ? 1 : 2;
/*     */     
/*  71 */     for (int i = start; i < end; i++) {
/*     */       
/*  73 */       int pitch = -(-pitch_n - 10 + i * 5);
/*  74 */       double p_rest = playerPitch % 5.0D;
/*     */ 
/*     */       
/*  77 */       int x = (pitch != 0) ? 50 : 100;
/*  78 */       int y = posY + (int)((-60 * INT) + p_rest * 6.0D * INT + (i * 30 * INT));
/*     */       
/*  80 */       line[0] = (posX - x);
/*  81 */       line[1] = (y + ((pitch > 0) ? 2 : ((pitch == 0) ? 0 : -2)));
/*  82 */       line[2] = (posX - 50);
/*  83 */       line[3] = y;
/*  84 */       line[4] = (posX + x);
/*  85 */       line[5] = line[1];
/*  86 */       line[6] = (posX + 50);
/*  87 */       line[7] = y;
/*  88 */       drawLine(line, color);
/*     */       
/*  90 */       line[0] = (posX - 50);
/*  91 */       line[1] = y;
/*  92 */       line[2] = (posX - 30);
/*  93 */       line[3] = y;
/*  94 */       line[4] = (posX + 50);
/*  95 */       line[5] = y;
/*  96 */       line[6] = (posX + 30);
/*  97 */       line[7] = y;
/*  98 */       if (pitch >= 0) {
/*     */         
/* 100 */         drawLine(line, color);
/*     */       }
/*     */       else {
/*     */         
/* 104 */         drawLineStipple(line, color, 1, 52428);
/*     */       } 
/*     */       
/* 107 */       if (pitch != 0) {
/*     */         
/* 109 */         drawCenteredString("" + pitch, posX - 50 - 10, y - 4, color);
/* 110 */         drawCenteredString("" + pitch, posX + 50 + 10, y - 4, color);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void drawCommonGraduationPitch1(double playerPitch, int color, int posX, int posY) {
/* 117 */     int pitch = (int)playerPitch % 360;
/*     */     
/* 119 */     int y = (int)(playerPitch * 10.0D % 10.0D);
/*     */     
/* 121 */     if (y < 0)
/*     */     {
/* 123 */       y += 10;
/*     */     }
/*     */ 
/*     */     
/* 127 */     int posX_L = posX - 100;
/* 128 */     int posX_R = posX + 100;
/* 129 */     int linePosY = posY;
/* 130 */     posY -= 80;
/*     */     
/* 132 */     double[] line = new double[144];
/* 133 */     int p = (playerPitch >= 0.0D || y == 0) ? (pitch - 8) : (pitch - 9);
/* 134 */     for (int i = 0; i < line.length / 8; p++) {
/*     */       
/* 136 */       int olx = (p % 3 == 0) ? 15 : 5;
/* 137 */       int ilx = 0;
/*     */       
/* 139 */       line[i * 8 + 0] = (posX_L - olx);
/* 140 */       line[i * 8 + 1] = (posY + i * 10 - y);
/* 141 */       line[i * 8 + 2] = (posX_L + ilx);
/* 142 */       line[i * 8 + 3] = (posY + i * 10 - y);
/*     */       
/* 144 */       line[i * 8 + 4] = (posX_R + olx);
/* 145 */       line[i * 8 + 5] = (posY + i * 10 - y);
/* 146 */       line[i * 8 + 6] = (posX_R - ilx);
/* 147 */       line[i * 8 + 7] = (posY + i * 10 - y);
/* 148 */       i++;
/*     */     } 
/*     */     
/* 151 */     drawLine(line, color);
/*     */     
/* 153 */     double[] verticalLine = { (posX_L - 25), (linePosY - 90), posX_L, (linePosY - 90), posX_L, (linePosY + 90), (posX_L - 25), (linePosY + 90) };
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 158 */     drawLine(verticalLine, color, 3);
/* 159 */     verticalLine = new double[] { (posX_R + 25), (linePosY - 90), posX_R, (linePosY - 90), posX_R, (linePosY + 90), (posX_R + 25), (linePosY + 90) };
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 164 */     drawLine(verticalLine, color, 3);
/*     */   }
/*     */ 
/*     */   
/*     */   private void drawCommonGraduationYaw(double playerYaw, int color, int posX, int posY) {
/* 169 */     double yaw = MCH_Lib.getRotate360(playerYaw);
/*     */ 
/*     */     
/* 172 */     posX -= 90;
/*     */     
/* 174 */     double[] line = new double[76];
/* 175 */     int x = (int)(yaw * 10.0D) % 10;
/* 176 */     int y = (int)yaw - 9;
/*     */     
/* 178 */     for (int i = 0; i < line.length / 4; y++) {
/*     */       
/* 180 */       int azPosX = posX + i * 10 - x;
/* 181 */       line[i * 4 + 0] = azPosX;
/* 182 */       line[i * 4 + 1] = posY;
/* 183 */       line[i * 4 + 2] = azPosX;
/* 184 */       line[i * 4 + 3] = (posY + ((y % 3 == 0) ? 10 : ((y % 45 == 0) ? 15 : 5)));
/* 185 */       if (y % 45 == 0) {
/*     */         
/* 187 */         drawCenteredString(MCH_Lib.getAzimuthStr8(y), azPosX, posY - 10, -65536);
/*     */       }
/* 189 */       else if (y % 3 == 0) {
/*     */         
/* 191 */         int rot = y + 180;
/* 192 */         if (rot < 0)
/* 193 */           rot += 360; 
/* 194 */         if (rot > 360)
/* 195 */           rot -= 360; 
/* 196 */         drawCenteredString(String.format("%d", new Object[] {
/*     */                 
/* 198 */                 Integer.valueOf(rot)
/*     */               }), azPosX, posY - 10, color);
/*     */       } 
/* 201 */       i++;
/*     */     } 
/*     */     
/* 204 */     drawLine(line, color);
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\hud\MCH_HudItemGraduation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */