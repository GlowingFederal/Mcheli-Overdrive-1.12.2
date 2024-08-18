package mcheli.hud;

import java.util.Date;
import mcheli.MCH_Config;
import mcheli.MCH_KeyName;
import mcheli.MCH_Lib;
import mcheli.MCH_MOD;
import net.minecraft.util.math.MathHelper;

public class MCH_HudItemString extends MCH_HudItem {
  private final String posX;
  
  private final String posY;
  
  private final String format;
  
  private final MCH_HudItemStringArgs[] args;
  
  private final boolean isCenteredString;
  
  public MCH_HudItemString(int fileLine, String posx, String posy, String fmt, String[] arg, boolean centered) {
    super(fileLine);
    this.posX = posx.toLowerCase();
    this.posY = posy.toLowerCase();
    this.format = fmt;
    int len = (arg.length < 3) ? 0 : (arg.length - 3);
    this.args = new MCH_HudItemStringArgs[len];
    for (int i = 0; i < len; i++)
      this.args[i] = MCH_HudItemStringArgs.toArgs(arg[3 + i]); 
    this.isCenteredString = centered;
  }
  
  public void execute() {
    int x = (int)(centerX + calc(this.posX));
    int y = (int)(centerY + calc(this.posY));
    int worldTime = (int)((ac.field_70170_p.func_72820_D() + 6000L) % 24000L);
    Date date = new Date();
    Object[] prm = new Object[this.args.length];
    double hp_per = (ac.getMaxHP() > 0) ? (ac.getHP() / ac.getMaxHP()) : 0.0D;
    for (int i = 0; i < prm.length; i++) {
      switch (this.args[i]) {
        case NAME:
          prm[i] = (ac.getAcInfo()).displayName;
          break;
        case ALTITUDE:
          prm[i] = Integer.valueOf(Altitude);
          break;
        case DATE:
          prm[i] = date;
          break;
        case MC_THOR:
          prm[i] = Integer.valueOf(worldTime / 1000);
          break;
        case MC_TMIN:
          prm[i] = Integer.valueOf(worldTime % 1000 * 36 / 10 / 60);
          break;
        case MC_TSEC:
          prm[i] = Integer.valueOf(worldTime % 1000 * 36 / 10 % 60);
          break;
        case MAX_HP:
          prm[i] = Integer.valueOf(ac.getMaxHP());
          break;
        case HP:
          prm[i] = Integer.valueOf(ac.getHP());
          break;
        case HP_PER:
          prm[i] = Double.valueOf(hp_per * 100.0D);
          break;
        case POS_X:
          prm[i] = Double.valueOf(ac.field_70165_t);
          break;
        case POS_Y:
          prm[i] = Double.valueOf(ac.field_70163_u);
          break;
        case POS_Z:
          prm[i] = Double.valueOf(ac.field_70161_v);
          break;
        case MOTION_X:
          prm[i] = Double.valueOf(ac.field_70159_w);
          break;
        case MOTION_Y:
          prm[i] = Double.valueOf(ac.field_70181_x);
          break;
        case MOTION_Z:
          prm[i] = Double.valueOf(ac.field_70179_y);
          break;
        case INVENTORY:
          prm[i] = Integer.valueOf(ac.func_70302_i_());
          break;
        case WPN_NAME:
          prm[i] = WeaponName;
          if (CurrentWeapon == null)
            return; 
          break;
        case WPN_AMMO:
          prm[i] = WeaponAmmo;
          if (CurrentWeapon == null)
            return; 
          if (CurrentWeapon.getAmmoNumMax() <= 0)
            return; 
          break;
        case WPN_RM_AMMO:
          prm[i] = WeaponAllAmmo;
          if (CurrentWeapon == null)
            return; 
          if (CurrentWeapon.getAmmoNumMax() <= 0)
            return; 
          break;
        case RELOAD_PER:
          prm[i] = Float.valueOf(ReloadPer);
          if (CurrentWeapon == null)
            return; 
          break;
        case RELOAD_SEC:
          prm[i] = Float.valueOf(ReloadSec);
          if (CurrentWeapon == null)
            return; 
          break;
        case MORTAR_DIST:
          prm[i] = Float.valueOf(MortarDist);
          if (CurrentWeapon == null)
            return; 
          break;
        case MC_VER:
          prm[i] = "1.12.2";
          break;
        case MOD_VER:
          prm[i] = MCH_MOD.VER;
          break;
        case MOD_NAME:
          prm[i] = "MC Helicopter MOD";
          break;
        case YAW:
          prm[i] = Double.valueOf(MCH_Lib.getRotate360((ac.getRotYaw() + 180.0F)));
          break;
        case PITCH:
          prm[i] = Float.valueOf(-ac.getRotPitch());
          break;
        case ROLL:
          prm[i] = Float.valueOf(MathHelper.func_76142_g(ac.getRotRoll()));
          break;
        case PLYR_YAW:
          prm[i] = Double.valueOf(MCH_Lib.getRotate360((player.field_70177_z + 180.0F)));
          break;
        case PLYR_PITCH:
          prm[i] = Float.valueOf(-player.field_70125_A);
          break;
        case TVM_POS_X:
          prm[i] = Double.valueOf(TVM_PosX);
          break;
        case TVM_POS_Y:
          prm[i] = Double.valueOf(TVM_PosY);
          break;
        case TVM_POS_Z:
          prm[i] = Double.valueOf(TVM_PosZ);
          break;
        case TVM_DIFF:
          prm[i] = Double.valueOf(TVM_Diff);
          break;
        case CAM_ZOOM:
          prm[i] = Float.valueOf(ac.camera.getCameraZoom());
          break;
        case UAV_DIST:
          prm[i] = Double.valueOf(UAV_Dist);
          break;
        case KEY_GUI:
          prm[i] = MCH_KeyName.getDescOrName(MCH_Config.KeyGUI.prmInt);
          break;
        case THROTTLE:
          prm[i] = Double.valueOf(ac.getCurrentThrottle() * 100.0D);
          break;
      } 
    } 
    if (this.isCenteredString) {
      drawCenteredString(String.format(this.format, prm), x, y, colorSetting);
    } else {
      drawString(String.format(this.format, prm), x, y, colorSetting);
    } 
  }
}
