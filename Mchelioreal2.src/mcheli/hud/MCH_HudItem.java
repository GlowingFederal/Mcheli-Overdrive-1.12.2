package mcheli.hud;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import mcheli.MCH_ClientCommonTickHandler;
import mcheli.MCH_Config;
import mcheli.MCH_Lib;
import mcheli.MCH_LowPassFilterFloat;
import mcheli.MCH_Vector2;
import mcheli.aircraft.MCH_EntityAircraft;
import mcheli.eval.eval.ExpRuleFactory;
import mcheli.eval.eval.Expression;
import mcheli.eval.eval.var.MapVariable;
import mcheli.eval.eval.var.Variable;
import mcheli.plane.MCP_EntityPlane;
import mcheli.weapon.MCH_EntityTvMissile;
import mcheli.weapon.MCH_SightType;
import mcheli.weapon.MCH_WeaponBase;
import mcheli.weapon.MCH_WeaponInfo;
import mcheli.weapon.MCH_WeaponSet;
import mcheli.wrapper.W_McClient;
import mcheli.wrapper.W_OpenGlHelper;
import mcheli.wrapper.W_WorldFunc;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;

public abstract class MCH_HudItem extends Gui {
  public final int fileLine;
  
  public static Minecraft mc;
  
  public static EntityPlayer player;
  
  public static MCH_EntityAircraft ac;
  
  protected static double centerX = 0.0D;
  
  protected static double centerY = 0.0D;
  
  public static double width;
  
  public static double height;
  
  protected static Random rand = new Random();
  
  public static int scaleFactor;
  
  public static int colorSetting = -16777216;
  
  protected static int altitudeUpdateCount = 0;
  
  protected static int Altitude = 0;
  
  protected static float prevRadarRot;
  
  protected static String WeaponName = "";
  
  protected static String WeaponAmmo = "";
  
  protected static String WeaponAllAmmo = "";
  
  protected static MCH_WeaponSet CurrentWeapon = null;
  
  protected static float ReloadPer = 0.0F;
  
  protected static float ReloadSec = 0.0F;
  
  protected static float MortarDist = 0.0F;
  
  protected static MCH_LowPassFilterFloat StickX_LPF = new MCH_LowPassFilterFloat(4);
  
  protected static MCH_LowPassFilterFloat StickY_LPF = new MCH_LowPassFilterFloat(4);
  
  protected static double StickX;
  
  protected static double StickY;
  
  protected static double TVM_PosX;
  
  protected static double TVM_PosY;
  
  protected static double TVM_PosZ;
  
  protected static double TVM_Diff;
  
  protected static double UAV_Dist;
  
  protected static int countFuelWarn;
  
  protected static ArrayList<MCH_Vector2> EntityList;
  
  protected static ArrayList<MCH_Vector2> EnemyList;
  
  protected static Map<Object, Object> varMap = null;
  
  protected MCH_Hud parent;
  
  protected static float partialTicks;
  
  private static MCH_HudItemExit dummy = new MCH_HudItemExit(0);
  
  public MCH_HudItem(int fileLine) {
    this.fileLine = fileLine;
    this.field_73735_i = -110.0F;
  }
  
  public abstract void execute();
  
  public boolean canExecute() {
    return !this.parent.isIfFalse;
  }
  
  public static void update() {
    MCH_WeaponSet ws = ac.getCurrentWeapon((Entity)player);
    updateRadar(ac);
    updateStick();
    updateAltitude(ac);
    updateTvMissile(ac);
    updateUAV(ac);
    updateWeapon(ac, ws);
    updateVarMap(ac, ws);
  }
  
  public static String toFormula(String s) {
    return s.toLowerCase().replaceAll("#", "0x").replace("\t", " ").replace(" ", "");
  }
  
  public static double calc(String s) {
    Expression exp = ExpRuleFactory.getDefaultRule().parse(s);
    exp.setVariable((Variable)new MapVariable(varMap));
    return exp.evalDouble();
  }
  
  public static long calcLong(String s) {
    Expression exp = ExpRuleFactory.getDefaultRule().parse(s);
    exp.setVariable((Variable)new MapVariable(varMap));
    return exp.evalLong();
  }
  
  public void drawCenteredString(String s, int x, int y, int color) {
    func_73732_a(mc.field_71466_p, s, x, y, color);
  }
  
  public void drawString(String s, int x, int y, int color) {
    func_73731_b(mc.field_71466_p, s, x, y, color);
  }
  
  public void drawTexture(String name, double left, double top, double width, double height, double uLeft, double vTop, double uWidth, double vHeight, float rot, int textureWidth, int textureHeight) {
    W_McClient.MOD_bindTexture("textures/gui/" + name + ".png");
    GL11.glPushMatrix();
    GL11.glTranslated(left + width / 2.0D, top + height / 2.0D, 0.0D);
    GL11.glRotatef(rot, 0.0F, 0.0F, 1.0F);
    float fx = (float)(1.0D / textureWidth);
    float fy = (float)(1.0D / textureHeight);
    Tessellator tessellator = Tessellator.func_178181_a();
    BufferBuilder builder = tessellator.func_178180_c();
    builder.func_181668_a(7, DefaultVertexFormats.field_181707_g);
    builder.func_181662_b(-width / 2.0D, height / 2.0D, this.field_73735_i).func_187315_a(uLeft * fx, (vTop + vHeight) * fy).func_181675_d();
    builder.func_181662_b(width / 2.0D, height / 2.0D, this.field_73735_i).func_187315_a((uLeft + uWidth) * fx, (vTop + vHeight) * fy)
      .func_181675_d();
    builder.func_181662_b(width / 2.0D, -height / 2.0D, this.field_73735_i).func_187315_a((uLeft + uWidth) * fx, vTop * fy).func_181675_d();
    builder.func_181662_b(-width / 2.0D, -height / 2.0D, this.field_73735_i).func_187315_a(uLeft * fx, vTop * fy).func_181675_d();
    tessellator.func_78381_a();
    GL11.glPopMatrix();
  }
  
  public static void drawRect(double par0, double par1, double par2, double par3, int par4) {
    if (par0 < par2) {
      double j1 = par0;
      par0 = par2;
      par2 = j1;
    } 
    if (par1 < par3) {
      double j1 = par1;
      par1 = par3;
      par3 = j1;
    } 
    float f3 = (par4 >> 24 & 0xFF) / 255.0F;
    float f = (par4 >> 16 & 0xFF) / 255.0F;
    float f1 = (par4 >> 8 & 0xFF) / 255.0F;
    float f2 = (par4 & 0xFF) / 255.0F;
    Tessellator tessellator = Tessellator.func_178181_a();
    BufferBuilder builder = tessellator.func_178180_c();
    GL11.glEnable(3042);
    GL11.glDisable(3553);
    W_OpenGlHelper.glBlendFunc(770, 771, 1, 0);
    GL11.glColor4f(f, f1, f2, f3);
    builder.func_181668_a(7, DefaultVertexFormats.field_181705_e);
    builder.func_181662_b(par0, par3, 0.0D).func_181675_d();
    builder.func_181662_b(par2, par3, 0.0D).func_181675_d();
    builder.func_181662_b(par2, par1, 0.0D).func_181675_d();
    builder.func_181662_b(par0, par1, 0.0D).func_181675_d();
    tessellator.func_78381_a();
    GL11.glEnable(3553);
    GL11.glDisable(3042);
  }
  
  public void drawLine(double[] line, int color) {
    drawLine(line, color, 1);
  }
  
  public void drawLine(double[] line, int color, int mode) {
    GL11.glPushMatrix();
    GL11.glEnable(3042);
    GL11.glDisable(3553);
    GL11.glBlendFunc(770, 771);
    GL11.glColor4ub((byte)(color >> 16 & 0xFF), (byte)(color >> 8 & 0xFF), (byte)(color >> 0 & 0xFF), (byte)(color >> 24 & 0xFF));
    Tessellator tessellator = Tessellator.func_178181_a();
    BufferBuilder builder = tessellator.func_178180_c();
    builder.func_181668_a(mode, DefaultVertexFormats.field_181705_e);
    for (int i = 0; i < line.length; i += 2)
      builder.func_181662_b(line[i + 0], line[i + 1], this.field_73735_i).func_181675_d(); 
    tessellator.func_78381_a();
    GL11.glEnable(3553);
    GL11.glDisable(3042);
    GL11.glColor4b((byte)-1, (byte)-1, (byte)-1, (byte)-1);
    GL11.glPopMatrix();
  }
  
  public void drawLineStipple(double[] line, int color, int factor, int pattern) {
    GL11.glEnable(2852);
    GL11.glLineStipple(factor * scaleFactor, (short)pattern);
    drawLine(line, color);
    GL11.glDisable(2852);
  }
  
  public void drawPoints(ArrayList<Double> points, int color, int pointWidth) {
    int prevWidth = GL11.glGetInteger(2833);
    GL11.glPushMatrix();
    GL11.glEnable(3042);
    GL11.glDisable(3553);
    GL11.glBlendFunc(770, 771);
    GL11.glColor4ub((byte)(color >> 16 & 0xFF), (byte)(color >> 8 & 0xFF), (byte)(color >> 0 & 0xFF), (byte)(color >> 24 & 0xFF));
    GL11.glPointSize(pointWidth);
    Tessellator tessellator = Tessellator.func_178181_a();
    BufferBuilder builder = tessellator.func_178180_c();
    builder.func_181668_a(0, DefaultVertexFormats.field_181705_e);
    for (int i = 0; i < points.size(); i += 2)
      builder.func_181662_b(((Double)points.get(i)).doubleValue(), ((Double)points.get(i + 1)).doubleValue(), 0.0D).func_181675_d(); 
    tessellator.func_78381_a();
    GL11.glEnable(3553);
    GL11.glDisable(3042);
    GL11.glPopMatrix();
    GL11.glColor4b((byte)-1, (byte)-1, (byte)-1, (byte)-1);
    GL11.glPointSize(prevWidth);
  }
  
  public static void updateVarMap(MCH_EntityAircraft ac, MCH_WeaponSet ws) {
    if (varMap == null)
      varMap = new LinkedHashMap<>(); 
    updateVarMapItem("color", getColor());
    updateVarMapItem("center_x", centerX);
    updateVarMapItem("center_y", centerY);
    updateVarMapItem("width", width);
    updateVarMapItem("height", height);
    updateVarMapItem("time", (player.field_70170_p.func_72820_D() % 24000L));
    updateVarMapItem("test_mode", MCH_Config.TestMode.prmBool ? 1.0D : 0.0D);
    updateVarMapItem("plyr_yaw", MathHelper.func_76142_g(player.field_70177_z));
    updateVarMapItem("plyr_pitch", player.field_70125_A);
    updateVarMapItem("yaw", MathHelper.func_76142_g(ac.getRotYaw()));
    updateVarMapItem("pitch", ac.getRotPitch());
    updateVarMapItem("roll", MathHelper.func_76142_g(ac.getRotRoll()));
    updateVarMapItem("altitude", Altitude);
    updateVarMapItem("sea_alt", getSeaAltitude(ac));
    updateVarMapItem("have_radar", ac.isEntityRadarMounted() ? 1.0D : 0.0D);
    updateVarMapItem("radar_rot", getRadarRot(ac));
    updateVarMapItem("hp", ac.getHP());
    updateVarMapItem("max_hp", ac.getMaxHP());
    updateVarMapItem("hp_rto", (ac.getMaxHP() > 0) ? (ac.getHP() / ac.getMaxHP()) : 0.0D);
    updateVarMapItem("throttle", ac.getCurrentThrottle());
    updateVarMapItem("pos_x", ac.field_70165_t);
    updateVarMapItem("pos_y", ac.field_70163_u);
    updateVarMapItem("pos_z", ac.field_70161_v);
    updateVarMapItem("motion_x", ac.field_70159_w);
    updateVarMapItem("motion_y", ac.field_70181_x);
    updateVarMapItem("motion_z", ac.field_70179_y);
    updateVarMapItem("speed", 
        Math.sqrt(ac.field_70159_w * ac.field_70159_w + ac.field_70181_x * ac.field_70181_x + ac.field_70179_y * ac.field_70179_y));
    updateVarMapItem("fuel", ac.getFuelP());
    updateVarMapItem("low_fuel", isLowFuel(ac));
    updateVarMapItem("stick_x", StickX);
    updateVarMapItem("stick_y", StickY);
    updateVarMap_Weapon(ws);
    updateVarMapItem("vtol_stat", getVtolStat(ac));
    updateVarMapItem("free_look", getFreeLook(ac, player));
    updateVarMapItem("gunner_mode", ac.getIsGunnerMode((Entity)player) ? 1.0D : 0.0D);
    updateVarMapItem("cam_mode", ac.getCameraMode(player));
    updateVarMapItem("cam_zoom", ac.camera.getCameraZoom());
    updateVarMapItem("auto_pilot", getAutoPilot(ac, player));
    updateVarMapItem("have_flare", ac.haveFlare() ? 1.0D : 0.0D);
    updateVarMapItem("can_flare", ac.canUseFlare() ? 1.0D : 0.0D);
    updateVarMapItem("inventory", ac.func_70302_i_());
    updateVarMapItem("hovering", (ac instanceof mcheli.helicopter.MCH_EntityHeli && ac.isHoveringMode()) ? 1.0D : 0.0D);
    updateVarMapItem("is_uav", ac.isUAV() ? 1.0D : 0.0D);
    updateVarMapItem("uav_fs", getUAV_Fs(ac));
  }
  
  public static void updateVarMapItem(String key, double value) {
    varMap.put(key, Double.valueOf(value));
  }
  
  public static void drawVarMap() {
    if (MCH_Config.TestMode.prmBool) {
      int i = 0;
      int x = (int)(-300.0D + centerX);
      int y = (int)(-100.0D + centerY);
      for (Object keyObj : varMap.keySet()) {
        String key = (String)keyObj;
        dummy.drawString(key, x, y, 52992);
        Double d = (Double)varMap.get(key);
        String fmt = key.equalsIgnoreCase("color") ? String.format(": 0x%08X", new Object[] { Integer.valueOf(d.intValue()) }) : String.format(": %.2f", new Object[] { d });
        dummy.drawString(fmt, x + 50, y, 52992);
        i++;
        y += 8;
        if (i == varMap.size() / 2) {
          x = (int)(200.0D + centerX);
          y = (int)(-100.0D + centerY);
        } 
      } 
    } 
  }
  
  private static double getUAV_Fs(MCH_EntityAircraft ac) {
    double uav_fs = 0.0D;
    if (ac.isUAV() && ac.getUavStation() != null) {
      double dx = ac.field_70165_t - (ac.getUavStation()).field_70165_t;
      double dz = ac.field_70161_v - (ac.getUavStation()).field_70161_v;
      float dist = (float)Math.sqrt(dx * dx + dz * dz);
      if (dist > 120.0F)
        dist = 120.0F; 
      uav_fs = (1.0F - dist / 120.0F);
    } 
    return uav_fs;
  }
  
  private static void updateVarMap_Weapon(MCH_WeaponSet ws) {
    int reloading = 0;
    double wpn_heat = 0.0D;
    int is_heat_wpn = 0;
    int sight_type = 0;
    double lock = 0.0D;
    float rel_time = 0.0F;
    int display_mortar_dist = 0;
    if (ws != null) {
      MCH_WeaponBase wb = ws.getCurrentWeapon();
      MCH_WeaponInfo wi = wb.getInfo();
      if (wi == null)
        return; 
      is_heat_wpn = (wi.maxHeatCount > 0) ? 1 : 0;
      reloading = ws.isInPreparation() ? 1 : 0;
      display_mortar_dist = wi.displayMortarDistance ? 1 : 0;
      if (wi.delay > wi.reloadTime) {
        rel_time = ws.countWait / ((wi.delay > 0) ? wi.delay : true);
        if (rel_time < 0.0F)
          rel_time = -rel_time; 
        if (rel_time > 1.0F)
          rel_time = 1.0F; 
      } else {
        rel_time = ws.countReloadWait / ((wi.reloadTime > 0) ? wi.reloadTime : true);
      } 
      if (wi.maxHeatCount > 0) {
        double hpp = ws.currentHeat / wi.maxHeatCount;
        wpn_heat = (hpp > 1.0D) ? 1.0D : hpp;
      } 
      int cntLockMax = wb.getLockCountMax();
      MCH_SightType sight = wb.getSightType();
      if (sight == MCH_SightType.LOCK && cntLockMax > 0) {
        lock = wb.getLockCount() / cntLockMax;
        sight_type = 2;
      } 
      if (sight == MCH_SightType.ROCKET)
        sight_type = 1; 
    } 
    updateVarMapItem("reloading", reloading);
    updateVarMapItem("reload_time", rel_time);
    updateVarMapItem("wpn_heat", wpn_heat);
    updateVarMapItem("is_heat_wpn", is_heat_wpn);
    updateVarMapItem("sight_type", sight_type);
    updateVarMapItem("lock", lock);
    updateVarMapItem("dsp_mt_dist", display_mortar_dist);
    updateVarMapItem("mt_dist", MortarDist);
  }
  
  public static int isLowFuel(MCH_EntityAircraft ac) {
    int is_low_fuel = 0;
    if (countFuelWarn <= 0)
      countFuelWarn = 280; 
    countFuelWarn--;
    if (countFuelWarn < 160)
      if (ac.getMaxFuel() > 0 && ac.getFuelP() < 0.1F && !ac.isInfinityFuel((Entity)player, false))
        is_low_fuel = 1;  
    return is_low_fuel;
  }
  
  public static double getSeaAltitude(MCH_EntityAircraft ac) {
    double a = ac.field_70163_u - ac.field_70170_p.func_72919_O();
    return (a >= 0.0D) ? a : 0.0D;
  }
  
  public static float getRadarRot(MCH_EntityAircraft ac) {
    float rot = ac.getRadarRotate();
    float prevRot = prevRadarRot;
    if (rot < prevRot)
      rot += 360.0F; 
    prevRadarRot = ac.getRadarRotate();
    return MCH_Lib.smooth(rot, prevRot, partialTicks);
  }
  
  public static int getVtolStat(MCH_EntityAircraft ac) {
    if (ac instanceof MCP_EntityPlane)
      return ((MCP_EntityPlane)ac).getVtolMode(); 
    return 0;
  }
  
  public static int getFreeLook(MCH_EntityAircraft ac, EntityPlayer player) {
    if (ac.isPilot((Entity)player) && ac.canSwitchFreeLook() && ac.isFreeLookMode())
      return 1; 
    return 0;
  }
  
  public static int getAutoPilot(MCH_EntityAircraft ac, EntityPlayer player) {
    if (ac instanceof MCP_EntityPlane)
      if (ac.isPilot((Entity)player) && ac.getIsGunnerMode((Entity)player))
        return 1;  
    return 0;
  }
  
  public static double getColor() {
    long l = colorSetting;
    l &= 0xFFFFFFFFFFFFFFFFL;
    return l;
  }
  
  private static void updateStick() {
    StickX_LPF.put(
        (float)(MCH_ClientCommonTickHandler.getCurrentStickX() / MCH_ClientCommonTickHandler.getMaxStickLength()));
    StickY_LPF.put(
        (float)(-MCH_ClientCommonTickHandler.getCurrentStickY() / MCH_ClientCommonTickHandler.getMaxStickLength()));
    StickX = StickX_LPF.getAvg();
    StickY = StickY_LPF.getAvg();
  }
  
  private static void updateRadar(MCH_EntityAircraft ac) {
    EntityList = ac.getRadarEntityList();
    EnemyList = ac.getRadarEnemyList();
  }
  
  private static void updateAltitude(MCH_EntityAircraft ac) {
    if (altitudeUpdateCount <= 0) {
      int heliY = (int)ac.field_70163_u;
      if (heliY > 256)
        heliY = 256; 
      for (int i = 0; i < 256; i++) {
        if (heliY - i <= 0)
          break; 
        int id = W_WorldFunc.getBlockId(ac.field_70170_p, (int)ac.field_70165_t, heliY - i, (int)ac.field_70161_v);
        if (id != 0) {
          Altitude = i;
          if (ac.field_70163_u <= 256.0D)
            break; 
          Altitude = (int)(Altitude + ac.field_70163_u - 256.0D);
          break;
        } 
      } 
      altitudeUpdateCount = 30;
    } else {
      altitudeUpdateCount--;
    } 
  }
  
  public static void updateWeapon(MCH_EntityAircraft ac, MCH_WeaponSet ws) {
    if (ac.getWeaponNum() <= 0)
      return; 
    if (ws == null)
      return; 
    CurrentWeapon = ws;
    WeaponName = ac.isPilotReloading() ? "-- Reloading --" : ws.getName();
    if (ws.getAmmoNumMax() > 0) {
      WeaponAmmo = ac.isPilotReloading() ? "----" : String.format("%4d", new Object[] { Integer.valueOf(ws.getAmmoNum()) });
      WeaponAllAmmo = ac.isPilotReloading() ? "----" : String.format("%4d", new Object[] { Integer.valueOf(ws.getRestAllAmmoNum()) });
    } else {
      WeaponAmmo = "";
      WeaponAllAmmo = "";
    } 
    MCH_WeaponInfo wi = ws.getInfo();
    if (wi.displayMortarDistance) {
      MortarDist = (float)ac.getLandInDistance((Entity)player);
    } else {
      MortarDist = -1.0F;
    } 
    if (wi.delay > wi.reloadTime) {
      ReloadSec = (ws.countWait >= 0) ? ws.countWait : -ws.countWait;
      ReloadPer = ws.countWait / ((wi.delay > 0) ? wi.delay : true);
      if (ReloadPer < 0.0F)
        ReloadPer = -ReloadPer; 
      if (ReloadPer > 1.0F)
        ReloadPer = 1.0F; 
    } else {
      ReloadSec = ws.countReloadWait;
      ReloadPer = ws.countReloadWait / ((wi.reloadTime > 0) ? wi.reloadTime : true);
    } 
    ReloadSec /= 20.0F;
    ReloadPer = (1.0F - ReloadPer) * 100.0F;
  }
  
  public static void updateUAV(MCH_EntityAircraft ac) {
    if (ac.isUAV() && ac.getUavStation() != null) {
      double dx = ac.field_70165_t - (ac.getUavStation()).field_70165_t;
      double dz = ac.field_70161_v - (ac.getUavStation()).field_70161_v;
      UAV_Dist = (float)Math.sqrt(dx * dx + dz * dz);
    } else {
      UAV_Dist = 0.0D;
    } 
  }
  
  private static void updateTvMissile(MCH_EntityAircraft ac) {
    MCH_EntityTvMissile mCH_EntityTvMissile = ac.getTVMissile();
    if (mCH_EntityTvMissile != null) {
      TVM_PosX = ((Entity)mCH_EntityTvMissile).field_70165_t;
      TVM_PosY = ((Entity)mCH_EntityTvMissile).field_70163_u;
      TVM_PosZ = ((Entity)mCH_EntityTvMissile).field_70161_v;
      double dx = ((Entity)mCH_EntityTvMissile).field_70165_t - ac.field_70165_t;
      double dy = ((Entity)mCH_EntityTvMissile).field_70163_u - ac.field_70163_u;
      double dz = ((Entity)mCH_EntityTvMissile).field_70161_v - ac.field_70161_v;
      TVM_Diff = Math.sqrt(dx * dx + dy * dy + dz * dz);
    } else {
      TVM_PosX = 0.0D;
      TVM_PosY = 0.0D;
      TVM_PosZ = 0.0D;
      TVM_Diff = 0.0D;
    } 
  }
}
