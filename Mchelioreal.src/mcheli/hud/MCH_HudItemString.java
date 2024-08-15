/*     */ package mcheli.hud;
/*     */ 
/*     */ import java.util.Date;
/*     */ import mcheli.MCH_Config;
/*     */ import mcheli.MCH_KeyName;
/*     */ import mcheli.MCH_Lib;
/*     */ import mcheli.MCH_MOD;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MCH_HudItemString
/*     */   extends MCH_HudItem
/*     */ {
/*     */   private final String posX;
/*     */   private final String posY;
/*     */   private final String format;
/*     */   private final MCH_HudItemStringArgs[] args;
/*     */   private final boolean isCenteredString;
/*     */   
/*     */   public MCH_HudItemString(int fileLine, String posx, String posy, String fmt, String[] arg, boolean centered) {
/*  27 */     super(fileLine);
/*  28 */     this.posX = posx.toLowerCase();
/*  29 */     this.posY = posy.toLowerCase();
/*  30 */     this.format = fmt;
/*  31 */     int len = (arg.length < 3) ? 0 : (arg.length - 3);
/*  32 */     this.args = new MCH_HudItemStringArgs[len];
/*  33 */     for (int i = 0; i < len; i++)
/*     */     {
/*  35 */       this.args[i] = MCH_HudItemStringArgs.toArgs(arg[3 + i]);
/*     */     }
/*  37 */     this.isCenteredString = centered;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute() {
/*  43 */     int x = (int)(centerX + calc(this.posX));
/*  44 */     int y = (int)(centerY + calc(this.posY));
/*     */     
/*  46 */     int worldTime = (int)((ac.field_70170_p.func_72820_D() + 6000L) % 24000L);
/*  47 */     Date date = new Date();
/*  48 */     Object[] prm = new Object[this.args.length];
/*     */     
/*  50 */     double hp_per = (ac.getMaxHP() > 0) ? (ac.getHP() / ac.getMaxHP()) : 0.0D;
/*     */     
/*  52 */     for (int i = 0; i < prm.length; i++) {
/*     */ 
/*     */       
/*  55 */       switch (this.args[i]) {
/*     */ 
/*     */         
/*     */         case NAME:
/*  59 */           prm[i] = (ac.getAcInfo()).displayName;
/*     */           break;
/*     */ 
/*     */         
/*     */         case ALTITUDE:
/*  64 */           prm[i] = Integer.valueOf(Altitude);
/*     */           break;
/*     */         
/*     */         case DATE:
/*  68 */           prm[i] = date;
/*     */           break;
/*     */         
/*     */         case MC_THOR:
/*  72 */           prm[i] = Integer.valueOf(worldTime / 1000);
/*     */           break;
/*     */         
/*     */         case MC_TMIN:
/*  76 */           prm[i] = Integer.valueOf(worldTime % 1000 * 36 / 10 / 60);
/*     */           break;
/*     */         
/*     */         case MC_TSEC:
/*  80 */           prm[i] = Integer.valueOf(worldTime % 1000 * 36 / 10 % 60);
/*     */           break;
/*     */         
/*     */         case MAX_HP:
/*  84 */           prm[i] = Integer.valueOf(ac.getMaxHP());
/*     */           break;
/*     */         
/*     */         case HP:
/*  88 */           prm[i] = Integer.valueOf(ac.getHP());
/*     */           break;
/*     */         
/*     */         case HP_PER:
/*  92 */           prm[i] = Double.valueOf(hp_per * 100.0D);
/*     */           break;
/*     */         
/*     */         case POS_X:
/*  96 */           prm[i] = Double.valueOf(ac.field_70165_t);
/*     */           break;
/*     */         
/*     */         case POS_Y:
/* 100 */           prm[i] = Double.valueOf(ac.field_70163_u);
/*     */           break;
/*     */         
/*     */         case POS_Z:
/* 104 */           prm[i] = Double.valueOf(ac.field_70161_v);
/*     */           break;
/*     */         
/*     */         case MOTION_X:
/* 108 */           prm[i] = Double.valueOf(ac.field_70159_w);
/*     */           break;
/*     */         
/*     */         case MOTION_Y:
/* 112 */           prm[i] = Double.valueOf(ac.field_70181_x);
/*     */           break;
/*     */         
/*     */         case MOTION_Z:
/* 116 */           prm[i] = Double.valueOf(ac.field_70179_y);
/*     */           break;
/*     */         
/*     */         case INVENTORY:
/* 120 */           prm[i] = Integer.valueOf(ac.func_70302_i_());
/*     */           break;
/*     */ 
/*     */         
/*     */         case WPN_NAME:
/* 125 */           prm[i] = WeaponName;
/* 126 */           if (CurrentWeapon == null) {
/*     */             return;
/*     */           }
/*     */           break;
/*     */         case WPN_AMMO:
/* 131 */           prm[i] = WeaponAmmo;
/* 132 */           if (CurrentWeapon == null)
/*     */             return; 
/* 134 */           if (CurrentWeapon.getAmmoNumMax() <= 0) {
/*     */             return;
/*     */           }
/*     */           break;
/*     */         case WPN_RM_AMMO:
/* 139 */           prm[i] = WeaponAllAmmo;
/* 140 */           if (CurrentWeapon == null)
/*     */             return; 
/* 142 */           if (CurrentWeapon.getAmmoNumMax() <= 0) {
/*     */             return;
/*     */           }
/*     */           break;
/*     */         case RELOAD_PER:
/* 147 */           prm[i] = Float.valueOf(ReloadPer);
/* 148 */           if (CurrentWeapon == null) {
/*     */             return;
/*     */           }
/*     */           break;
/*     */         case RELOAD_SEC:
/* 153 */           prm[i] = Float.valueOf(ReloadSec);
/* 154 */           if (CurrentWeapon == null) {
/*     */             return;
/*     */           }
/*     */           break;
/*     */         case MORTAR_DIST:
/* 159 */           prm[i] = Float.valueOf(MortarDist);
/* 160 */           if (CurrentWeapon == null) {
/*     */             return;
/*     */           }
/*     */           break;
/*     */         
/*     */         case MC_VER:
/* 166 */           prm[i] = "1.12.2";
/*     */           break;
/*     */         
/*     */         case MOD_VER:
/* 170 */           prm[i] = MCH_MOD.VER;
/*     */           break;
/*     */ 
/*     */         
/*     */         case MOD_NAME:
/* 175 */           prm[i] = "MC Helicopter MOD";
/*     */           break;
/*     */         
/*     */         case YAW:
/* 179 */           prm[i] = Double.valueOf(MCH_Lib.getRotate360((ac.getRotYaw() + 180.0F)));
/*     */           break;
/*     */         
/*     */         case PITCH:
/* 183 */           prm[i] = Float.valueOf(-ac.getRotPitch());
/*     */           break;
/*     */         
/*     */         case ROLL:
/* 187 */           prm[i] = Float.valueOf(MathHelper.func_76142_g(ac.getRotRoll()));
/*     */           break;
/*     */         
/*     */         case PLYR_YAW:
/* 191 */           prm[i] = Double.valueOf(MCH_Lib.getRotate360((player.field_70177_z + 180.0F)));
/*     */           break;
/*     */         
/*     */         case PLYR_PITCH:
/* 195 */           prm[i] = Float.valueOf(-player.field_70125_A);
/*     */           break;
/*     */         
/*     */         case TVM_POS_X:
/* 199 */           prm[i] = Double.valueOf(TVM_PosX);
/*     */           break;
/*     */         
/*     */         case TVM_POS_Y:
/* 203 */           prm[i] = Double.valueOf(TVM_PosY);
/*     */           break;
/*     */         
/*     */         case TVM_POS_Z:
/* 207 */           prm[i] = Double.valueOf(TVM_PosZ);
/*     */           break;
/*     */         
/*     */         case TVM_DIFF:
/* 211 */           prm[i] = Double.valueOf(TVM_Diff);
/*     */           break;
/*     */         
/*     */         case CAM_ZOOM:
/* 215 */           prm[i] = Float.valueOf(ac.camera.getCameraZoom());
/*     */           break;
/*     */         
/*     */         case UAV_DIST:
/* 219 */           prm[i] = Double.valueOf(UAV_Dist);
/*     */           break;
/*     */         
/*     */         case KEY_GUI:
/* 223 */           prm[i] = MCH_KeyName.getDescOrName(MCH_Config.KeyGUI.prmInt);
/*     */           break;
/*     */         
/*     */         case THROTTLE:
/* 227 */           prm[i] = Double.valueOf(ac.getCurrentThrottle() * 100.0D);
/*     */           break;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     } 
/* 235 */     if (this.isCenteredString) {
/*     */       
/* 237 */       drawCenteredString(String.format(this.format, prm), x, y, colorSetting);
/*     */     }
/*     */     else {
/*     */       
/* 241 */       drawString(String.format(this.format, prm), x, y, colorSetting);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\hud\MCH_HudItemString.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */