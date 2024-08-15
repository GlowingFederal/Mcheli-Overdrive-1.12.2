/*     */ package mcheli.hud;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ import mcheli.MCH_ClientCommonTickHandler;
/*     */ import mcheli.MCH_Config;
/*     */ import mcheli.MCH_Lib;
/*     */ import mcheli.MCH_LowPassFilterFloat;
/*     */ import mcheli.MCH_Vector2;
/*     */ import mcheli.aircraft.MCH_EntityAircraft;
/*     */ import mcheli.eval.eval.ExpRuleFactory;
/*     */ import mcheli.eval.eval.Expression;
/*     */ import mcheli.eval.eval.var.MapVariable;
/*     */ import mcheli.eval.eval.var.Variable;
/*     */ import mcheli.plane.MCP_EntityPlane;
/*     */ import mcheli.weapon.MCH_EntityTvMissile;
/*     */ import mcheli.weapon.MCH_SightType;
/*     */ import mcheli.weapon.MCH_WeaponBase;
/*     */ import mcheli.weapon.MCH_WeaponInfo;
/*     */ import mcheli.weapon.MCH_WeaponSet;
/*     */ import mcheli.wrapper.W_McClient;
/*     */ import mcheli.wrapper.W_OpenGlHelper;
/*     */ import mcheli.wrapper.W_WorldFunc;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.Gui;
/*     */ import net.minecraft.client.renderer.BufferBuilder;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class MCH_HudItem
/*     */   extends Gui
/*     */ {
/*     */   public final int fileLine;
/*     */   public static Minecraft mc;
/*     */   public static EntityPlayer player;
/*     */   public static MCH_EntityAircraft ac;
/*  49 */   protected static double centerX = 0.0D;
/*  50 */   protected static double centerY = 0.0D;
/*     */   public static double width;
/*     */   public static double height;
/*  53 */   protected static Random rand = new Random();
/*     */   public static int scaleFactor;
/*  55 */   public static int colorSetting = -16777216;
/*  56 */   protected static int altitudeUpdateCount = 0;
/*  57 */   protected static int Altitude = 0;
/*     */   protected static float prevRadarRot;
/*  59 */   protected static String WeaponName = "";
/*  60 */   protected static String WeaponAmmo = "";
/*  61 */   protected static String WeaponAllAmmo = "";
/*  62 */   protected static MCH_WeaponSet CurrentWeapon = null;
/*  63 */   protected static float ReloadPer = 0.0F;
/*  64 */   protected static float ReloadSec = 0.0F;
/*  65 */   protected static float MortarDist = 0.0F;
/*  66 */   protected static MCH_LowPassFilterFloat StickX_LPF = new MCH_LowPassFilterFloat(4);
/*  67 */   protected static MCH_LowPassFilterFloat StickY_LPF = new MCH_LowPassFilterFloat(4);
/*     */   
/*     */   protected static double StickX;
/*     */   protected static double StickY;
/*     */   protected static double TVM_PosX;
/*     */   protected static double TVM_PosY;
/*     */   protected static double TVM_PosZ;
/*     */   protected static double TVM_Diff;
/*     */   protected static double UAV_Dist;
/*     */   protected static int countFuelWarn;
/*     */   protected static ArrayList<MCH_Vector2> EntityList;
/*     */   protected static ArrayList<MCH_Vector2> EnemyList;
/*  79 */   protected static Map<Object, Object> varMap = null;
/*     */   protected MCH_Hud parent;
/*     */   protected static float partialTicks;
/*  82 */   private static MCH_HudItemExit dummy = new MCH_HudItemExit(0);
/*     */ 
/*     */   
/*     */   public MCH_HudItem(int fileLine) {
/*  86 */     this.fileLine = fileLine;
/*  87 */     this.field_73735_i = -110.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public abstract void execute();
/*     */   
/*     */   public boolean canExecute() {
/*  94 */     return !this.parent.isIfFalse;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void update() {
/*  99 */     MCH_WeaponSet ws = ac.getCurrentWeapon((Entity)player);
/*     */     
/* 101 */     updateRadar(ac);
/* 102 */     updateStick();
/* 103 */     updateAltitude(ac);
/* 104 */     updateTvMissile(ac);
/* 105 */     updateUAV(ac);
/* 106 */     updateWeapon(ac, ws);
/* 107 */     updateVarMap(ac, ws);
/*     */   }
/*     */ 
/*     */   
/*     */   public static String toFormula(String s) {
/* 112 */     return s.toLowerCase().replaceAll("#", "0x").replace("\t", " ").replace(" ", "");
/*     */   }
/*     */ 
/*     */   
/*     */   public static double calc(String s) {
/* 117 */     Expression exp = ExpRuleFactory.getDefaultRule().parse(s);
/* 118 */     exp.setVariable((Variable)new MapVariable(varMap));
/* 119 */     return exp.evalDouble();
/*     */   }
/*     */ 
/*     */   
/*     */   public static long calcLong(String s) {
/* 124 */     Expression exp = ExpRuleFactory.getDefaultRule().parse(s);
/* 125 */     exp.setVariable((Variable)new MapVariable(varMap));
/* 126 */     return exp.evalLong();
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawCenteredString(String s, int x, int y, int color) {
/* 131 */     func_73732_a(mc.field_71466_p, s, x, y, color);
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawString(String s, int x, int y, int color) {
/* 136 */     func_73731_b(mc.field_71466_p, s, x, y, color);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawTexture(String name, double left, double top, double width, double height, double uLeft, double vTop, double uWidth, double vHeight, float rot, int textureWidth, int textureHeight) {
/* 142 */     W_McClient.MOD_bindTexture("textures/gui/" + name + ".png");
/*     */     
/* 144 */     GL11.glPushMatrix();
/*     */     
/* 146 */     GL11.glTranslated(left + width / 2.0D, top + height / 2.0D, 0.0D);
/* 147 */     GL11.glRotatef(rot, 0.0F, 0.0F, 1.0F);
/*     */     
/* 149 */     float fx = (float)(1.0D / textureWidth);
/* 150 */     float fy = (float)(1.0D / textureHeight);
/* 151 */     Tessellator tessellator = Tessellator.func_178181_a();
/* 152 */     BufferBuilder builder = tessellator.func_178180_c();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 159 */     builder.func_181668_a(7, DefaultVertexFormats.field_181707_g);
/* 160 */     builder.func_181662_b(-width / 2.0D, height / 2.0D, this.field_73735_i).func_187315_a(uLeft * fx, (vTop + vHeight) * fy).func_181675_d();
/* 161 */     builder.func_181662_b(width / 2.0D, height / 2.0D, this.field_73735_i).func_187315_a((uLeft + uWidth) * fx, (vTop + vHeight) * fy)
/* 162 */       .func_181675_d();
/* 163 */     builder.func_181662_b(width / 2.0D, -height / 2.0D, this.field_73735_i).func_187315_a((uLeft + uWidth) * fx, vTop * fy).func_181675_d();
/* 164 */     builder.func_181662_b(-width / 2.0D, -height / 2.0D, this.field_73735_i).func_187315_a(uLeft * fx, vTop * fy).func_181675_d();
/* 165 */     tessellator.func_78381_a();
/*     */     
/* 167 */     GL11.glPopMatrix();
/*     */   }
/*     */ 
/*     */   
/*     */   public static void drawRect(double par0, double par1, double par2, double par3, int par4) {
/* 172 */     if (par0 < par2) {
/*     */       
/* 174 */       double j1 = par0;
/* 175 */       par0 = par2;
/* 176 */       par2 = j1;
/*     */     } 
/*     */     
/* 179 */     if (par1 < par3) {
/*     */       
/* 181 */       double j1 = par1;
/* 182 */       par1 = par3;
/* 183 */       par3 = j1;
/*     */     } 
/*     */     
/* 186 */     float f3 = (par4 >> 24 & 0xFF) / 255.0F;
/* 187 */     float f = (par4 >> 16 & 0xFF) / 255.0F;
/* 188 */     float f1 = (par4 >> 8 & 0xFF) / 255.0F;
/* 189 */     float f2 = (par4 & 0xFF) / 255.0F;
/* 190 */     Tessellator tessellator = Tessellator.func_178181_a();
/* 191 */     BufferBuilder builder = tessellator.func_178180_c();
/* 192 */     GL11.glEnable(3042);
/* 193 */     GL11.glDisable(3553);
/* 194 */     W_OpenGlHelper.glBlendFunc(770, 771, 1, 0);
/* 195 */     GL11.glColor4f(f, f1, f2, f3);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 201 */     builder.func_181668_a(7, DefaultVertexFormats.field_181705_e);
/* 202 */     builder.func_181662_b(par0, par3, 0.0D).func_181675_d();
/* 203 */     builder.func_181662_b(par2, par3, 0.0D).func_181675_d();
/* 204 */     builder.func_181662_b(par2, par1, 0.0D).func_181675_d();
/* 205 */     builder.func_181662_b(par0, par1, 0.0D).func_181675_d();
/* 206 */     tessellator.func_78381_a();
/* 207 */     GL11.glEnable(3553);
/* 208 */     GL11.glDisable(3042);
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawLine(double[] line, int color) {
/* 213 */     drawLine(line, color, 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawLine(double[] line, int color, int mode) {
/* 218 */     GL11.glPushMatrix();
/*     */     
/* 220 */     GL11.glEnable(3042);
/* 221 */     GL11.glDisable(3553);
/* 222 */     GL11.glBlendFunc(770, 771);
/* 223 */     GL11.glColor4ub((byte)(color >> 16 & 0xFF), (byte)(color >> 8 & 0xFF), (byte)(color >> 0 & 0xFF), (byte)(color >> 24 & 0xFF));
/*     */ 
/*     */     
/* 226 */     Tessellator tessellator = Tessellator.func_178181_a();
/* 227 */     BufferBuilder builder = tessellator.func_178180_c();
/*     */ 
/*     */     
/* 230 */     builder.func_181668_a(mode, DefaultVertexFormats.field_181705_e);
/*     */     
/* 232 */     for (int i = 0; i < line.length; i += 2)
/*     */     {
/*     */       
/* 235 */       builder.func_181662_b(line[i + 0], line[i + 1], this.field_73735_i).func_181675_d();
/*     */     }
/* 237 */     tessellator.func_78381_a();
/*     */     
/* 239 */     GL11.glEnable(3553);
/* 240 */     GL11.glDisable(3042);
/*     */     
/* 242 */     GL11.glColor4b((byte)-1, (byte)-1, (byte)-1, (byte)-1);
/* 243 */     GL11.glPopMatrix();
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawLineStipple(double[] line, int color, int factor, int pattern) {
/* 248 */     GL11.glEnable(2852);
/* 249 */     GL11.glLineStipple(factor * scaleFactor, (short)pattern);
/* 250 */     drawLine(line, color);
/* 251 */     GL11.glDisable(2852);
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawPoints(ArrayList<Double> points, int color, int pointWidth) {
/* 256 */     int prevWidth = GL11.glGetInteger(2833);
/*     */     
/* 258 */     GL11.glPushMatrix();
/*     */     
/* 260 */     GL11.glEnable(3042);
/* 261 */     GL11.glDisable(3553);
/* 262 */     GL11.glBlendFunc(770, 771);
/* 263 */     GL11.glColor4ub((byte)(color >> 16 & 0xFF), (byte)(color >> 8 & 0xFF), (byte)(color >> 0 & 0xFF), (byte)(color >> 24 & 0xFF));
/*     */ 
/*     */     
/* 266 */     GL11.glPointSize(pointWidth);
/* 267 */     Tessellator tessellator = Tessellator.func_178181_a();
/* 268 */     BufferBuilder builder = tessellator.func_178180_c();
/*     */ 
/*     */     
/* 271 */     builder.func_181668_a(0, DefaultVertexFormats.field_181705_e);
/* 272 */     for (int i = 0; i < points.size(); i += 2)
/*     */     {
/*     */ 
/*     */       
/* 276 */       builder.func_181662_b(((Double)points.get(i)).doubleValue(), ((Double)points.get(i + 1)).doubleValue(), 0.0D).func_181675_d();
/*     */     }
/* 278 */     tessellator.func_78381_a();
/*     */     
/* 280 */     GL11.glEnable(3553);
/* 281 */     GL11.glDisable(3042);
/*     */     
/* 283 */     GL11.glPopMatrix();
/* 284 */     GL11.glColor4b((byte)-1, (byte)-1, (byte)-1, (byte)-1);
/* 285 */     GL11.glPointSize(prevWidth);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void updateVarMap(MCH_EntityAircraft ac, MCH_WeaponSet ws) {
/* 290 */     if (varMap == null)
/*     */     {
/* 292 */       varMap = new LinkedHashMap<>();
/*     */     }
/* 294 */     updateVarMapItem("color", getColor());
/* 295 */     updateVarMapItem("center_x", centerX);
/* 296 */     updateVarMapItem("center_y", centerY);
/* 297 */     updateVarMapItem("width", width);
/* 298 */     updateVarMapItem("height", height);
/* 299 */     updateVarMapItem("time", (player.field_70170_p.func_72820_D() % 24000L));
/* 300 */     updateVarMapItem("test_mode", MCH_Config.TestMode.prmBool ? 1.0D : 0.0D);
/*     */     
/* 302 */     updateVarMapItem("plyr_yaw", MathHelper.func_76142_g(player.field_70177_z));
/* 303 */     updateVarMapItem("plyr_pitch", player.field_70125_A);
/*     */     
/* 305 */     updateVarMapItem("yaw", MathHelper.func_76142_g(ac.getRotYaw()));
/* 306 */     updateVarMapItem("pitch", ac.getRotPitch());
/* 307 */     updateVarMapItem("roll", MathHelper.func_76142_g(ac.getRotRoll()));
/* 308 */     updateVarMapItem("altitude", Altitude);
/* 309 */     updateVarMapItem("sea_alt", getSeaAltitude(ac));
/* 310 */     updateVarMapItem("have_radar", ac.isEntityRadarMounted() ? 1.0D : 0.0D);
/* 311 */     updateVarMapItem("radar_rot", getRadarRot(ac));
/* 312 */     updateVarMapItem("hp", ac.getHP());
/* 313 */     updateVarMapItem("max_hp", ac.getMaxHP());
/*     */     
/* 315 */     updateVarMapItem("hp_rto", (ac.getMaxHP() > 0) ? (ac.getHP() / ac.getMaxHP()) : 0.0D);
/* 316 */     updateVarMapItem("throttle", ac.getCurrentThrottle());
/* 317 */     updateVarMapItem("pos_x", ac.field_70165_t);
/* 318 */     updateVarMapItem("pos_y", ac.field_70163_u);
/* 319 */     updateVarMapItem("pos_z", ac.field_70161_v);
/* 320 */     updateVarMapItem("motion_x", ac.field_70159_w);
/* 321 */     updateVarMapItem("motion_y", ac.field_70181_x);
/* 322 */     updateVarMapItem("motion_z", ac.field_70179_y);
/* 323 */     updateVarMapItem("speed", 
/* 324 */         Math.sqrt(ac.field_70159_w * ac.field_70159_w + ac.field_70181_x * ac.field_70181_x + ac.field_70179_y * ac.field_70179_y));
/* 325 */     updateVarMapItem("fuel", ac.getFuelP());
/* 326 */     updateVarMapItem("low_fuel", isLowFuel(ac));
/* 327 */     updateVarMapItem("stick_x", StickX);
/* 328 */     updateVarMapItem("stick_y", StickY);
/* 329 */     updateVarMap_Weapon(ws);
/* 330 */     updateVarMapItem("vtol_stat", getVtolStat(ac));
/* 331 */     updateVarMapItem("free_look", getFreeLook(ac, player));
/* 332 */     updateVarMapItem("gunner_mode", ac.getIsGunnerMode((Entity)player) ? 1.0D : 0.0D);
/* 333 */     updateVarMapItem("cam_mode", ac.getCameraMode(player));
/* 334 */     updateVarMapItem("cam_zoom", ac.camera.getCameraZoom());
/* 335 */     updateVarMapItem("auto_pilot", getAutoPilot(ac, player));
/* 336 */     updateVarMapItem("have_flare", ac.haveFlare() ? 1.0D : 0.0D);
/* 337 */     updateVarMapItem("can_flare", ac.canUseFlare() ? 1.0D : 0.0D);
/* 338 */     updateVarMapItem("inventory", ac.func_70302_i_());
/* 339 */     updateVarMapItem("hovering", (ac instanceof mcheli.helicopter.MCH_EntityHeli && ac.isHoveringMode()) ? 1.0D : 0.0D);
/* 340 */     updateVarMapItem("is_uav", ac.isUAV() ? 1.0D : 0.0D);
/* 341 */     updateVarMapItem("uav_fs", getUAV_Fs(ac));
/*     */   }
/*     */ 
/*     */   
/*     */   public static void updateVarMapItem(String key, double value) {
/* 346 */     varMap.put(key, Double.valueOf(value));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void drawVarMap() {
/* 355 */     if (MCH_Config.TestMode.prmBool) {
/*     */       
/* 357 */       int i = 0;
/* 358 */       int x = (int)(-300.0D + centerX);
/* 359 */       int y = (int)(-100.0D + centerY);
/*     */ 
/*     */       
/* 362 */       for (Object keyObj : varMap.keySet()) {
/*     */         
/* 364 */         String key = (String)keyObj;
/*     */         
/* 366 */         dummy.drawString(key, x, y, 52992);
/* 367 */         Double d = (Double)varMap.get(key);
/*     */ 
/*     */ 
/*     */         
/* 371 */         String fmt = key.equalsIgnoreCase("color") ? String.format(": 0x%08X", new Object[] { Integer.valueOf(d.intValue()) }) : String.format(": %.2f", new Object[] { d });
/*     */ 
/*     */ 
/*     */         
/* 375 */         dummy.drawString(fmt, x + 50, y, 52992);
/* 376 */         i++;
/* 377 */         y += 8;
/* 378 */         if (i == varMap.size() / 2) {
/*     */           
/* 380 */           x = (int)(200.0D + centerX);
/* 381 */           y = (int)(-100.0D + centerY);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static double getUAV_Fs(MCH_EntityAircraft ac) {
/* 389 */     double uav_fs = 0.0D;
/*     */     
/* 391 */     if (ac.isUAV() && ac.getUavStation() != null) {
/*     */       
/* 393 */       double dx = ac.field_70165_t - (ac.getUavStation()).field_70165_t;
/* 394 */       double dz = ac.field_70161_v - (ac.getUavStation()).field_70161_v;
/*     */       
/* 396 */       float dist = (float)Math.sqrt(dx * dx + dz * dz);
/*     */ 
/*     */       
/* 399 */       if (dist > 120.0F) {
/* 400 */         dist = 120.0F;
/*     */       }
/* 402 */       uav_fs = (1.0F - dist / 120.0F);
/*     */     } 
/* 404 */     return uav_fs;
/*     */   }
/*     */ 
/*     */   
/*     */   private static void updateVarMap_Weapon(MCH_WeaponSet ws) {
/* 409 */     int reloading = 0;
/* 410 */     double wpn_heat = 0.0D;
/* 411 */     int is_heat_wpn = 0;
/* 412 */     int sight_type = 0;
/* 413 */     double lock = 0.0D;
/* 414 */     float rel_time = 0.0F;
/* 415 */     int display_mortar_dist = 0;
/*     */     
/* 417 */     if (ws != null) {
/*     */       
/* 419 */       MCH_WeaponBase wb = ws.getCurrentWeapon();
/* 420 */       MCH_WeaponInfo wi = wb.getInfo();
/*     */       
/* 422 */       if (wi == null) {
/*     */         return;
/*     */       }
/*     */       
/* 426 */       is_heat_wpn = (wi.maxHeatCount > 0) ? 1 : 0;
/* 427 */       reloading = ws.isInPreparation() ? 1 : 0;
/* 428 */       display_mortar_dist = wi.displayMortarDistance ? 1 : 0;
/*     */       
/* 430 */       if (wi.delay > wi.reloadTime) {
/*     */         
/* 432 */         rel_time = ws.countWait / ((wi.delay > 0) ? wi.delay : true);
/*     */         
/* 434 */         if (rel_time < 0.0F) {
/* 435 */           rel_time = -rel_time;
/*     */         }
/* 437 */         if (rel_time > 1.0F) {
/* 438 */           rel_time = 1.0F;
/*     */         }
/*     */       } else {
/*     */         
/* 442 */         rel_time = ws.countReloadWait / ((wi.reloadTime > 0) ? wi.reloadTime : true);
/*     */       } 
/*     */       
/* 445 */       if (wi.maxHeatCount > 0) {
/*     */         
/* 447 */         double hpp = ws.currentHeat / wi.maxHeatCount;
/* 448 */         wpn_heat = (hpp > 1.0D) ? 1.0D : hpp;
/*     */       } 
/*     */       
/* 451 */       int cntLockMax = wb.getLockCountMax();
/* 452 */       MCH_SightType sight = wb.getSightType();
/*     */       
/* 454 */       if (sight == MCH_SightType.LOCK && cntLockMax > 0) {
/*     */         
/* 456 */         lock = wb.getLockCount() / cntLockMax;
/* 457 */         sight_type = 2;
/*     */       } 
/*     */       
/* 460 */       if (sight == MCH_SightType.ROCKET)
/*     */       {
/* 462 */         sight_type = 1;
/*     */       }
/*     */     } 
/*     */     
/* 466 */     updateVarMapItem("reloading", reloading);
/* 467 */     updateVarMapItem("reload_time", rel_time);
/* 468 */     updateVarMapItem("wpn_heat", wpn_heat);
/* 469 */     updateVarMapItem("is_heat_wpn", is_heat_wpn);
/* 470 */     updateVarMapItem("sight_type", sight_type);
/* 471 */     updateVarMapItem("lock", lock);
/* 472 */     updateVarMapItem("dsp_mt_dist", display_mortar_dist);
/* 473 */     updateVarMapItem("mt_dist", MortarDist);
/*     */   }
/*     */ 
/*     */   
/*     */   public static int isLowFuel(MCH_EntityAircraft ac) {
/* 478 */     int is_low_fuel = 0;
/*     */     
/* 480 */     if (countFuelWarn <= 0)
/*     */     {
/* 482 */       countFuelWarn = 280;
/*     */     }
/*     */     
/* 485 */     countFuelWarn--;
/*     */     
/* 487 */     if (countFuelWarn < 160)
/*     */     {
/* 489 */       if (ac.getMaxFuel() > 0 && ac.getFuelP() < 0.1F && !ac.isInfinityFuel((Entity)player, false))
/*     */       {
/* 491 */         is_low_fuel = 1;
/*     */       }
/*     */     }
/* 494 */     return is_low_fuel;
/*     */   }
/*     */ 
/*     */   
/*     */   public static double getSeaAltitude(MCH_EntityAircraft ac) {
/* 499 */     double a = ac.field_70163_u - ac.field_70170_p.func_72919_O();
/* 500 */     return (a >= 0.0D) ? a : 0.0D;
/*     */   }
/*     */ 
/*     */   
/*     */   public static float getRadarRot(MCH_EntityAircraft ac) {
/* 505 */     float rot = ac.getRadarRotate();
/* 506 */     float prevRot = prevRadarRot;
/*     */     
/* 508 */     if (rot < prevRot) {
/* 509 */       rot += 360.0F;
/*     */     }
/* 511 */     prevRadarRot = ac.getRadarRotate();
/* 512 */     return MCH_Lib.smooth(rot, prevRot, partialTicks);
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getVtolStat(MCH_EntityAircraft ac) {
/* 517 */     if (ac instanceof MCP_EntityPlane)
/*     */     {
/* 519 */       return ((MCP_EntityPlane)ac).getVtolMode();
/*     */     }
/* 521 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getFreeLook(MCH_EntityAircraft ac, EntityPlayer player) {
/* 526 */     if (ac.isPilot((Entity)player) && ac.canSwitchFreeLook() && ac.isFreeLookMode())
/*     */     {
/* 528 */       return 1;
/*     */     }
/* 530 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getAutoPilot(MCH_EntityAircraft ac, EntityPlayer player) {
/* 535 */     if (ac instanceof MCP_EntityPlane)
/*     */     {
/* 537 */       if (ac.isPilot((Entity)player) && ac.getIsGunnerMode((Entity)player))
/*     */       {
/* 539 */         return 1;
/*     */       }
/*     */     }
/* 542 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public static double getColor() {
/* 547 */     long l = colorSetting;
/* 548 */     l &= 0xFFFFFFFFFFFFFFFFL;
/* 549 */     return l;
/*     */   }
/*     */ 
/*     */   
/*     */   private static void updateStick() {
/* 554 */     StickX_LPF.put(
/* 555 */         (float)(MCH_ClientCommonTickHandler.getCurrentStickX() / MCH_ClientCommonTickHandler.getMaxStickLength()));
/*     */     
/* 557 */     StickY_LPF.put(
/* 558 */         (float)(-MCH_ClientCommonTickHandler.getCurrentStickY() / MCH_ClientCommonTickHandler.getMaxStickLength()));
/*     */     
/* 560 */     StickX = StickX_LPF.getAvg();
/* 561 */     StickY = StickY_LPF.getAvg();
/*     */   }
/*     */ 
/*     */   
/*     */   private static void updateRadar(MCH_EntityAircraft ac) {
/* 566 */     EntityList = ac.getRadarEntityList();
/* 567 */     EnemyList = ac.getRadarEnemyList();
/*     */   }
/*     */ 
/*     */   
/*     */   private static void updateAltitude(MCH_EntityAircraft ac) {
/* 572 */     if (altitudeUpdateCount <= 0) {
/*     */       
/* 574 */       int heliY = (int)ac.field_70163_u;
/*     */       
/* 576 */       if (heliY > 256)
/*     */       {
/* 578 */         heliY = 256;
/*     */       }
/*     */       
/* 581 */       for (int i = 0; i < 256; i++) {
/*     */         
/* 583 */         if (heliY - i <= 0) {
/*     */           break;
/*     */         }
/* 586 */         int id = W_WorldFunc.getBlockId(ac.field_70170_p, (int)ac.field_70165_t, heliY - i, (int)ac.field_70161_v);
/*     */         
/* 588 */         if (id != 0) {
/*     */           
/* 590 */           Altitude = i;
/*     */           
/* 592 */           if (ac.field_70163_u <= 256.0D) {
/*     */             break;
/*     */           }
/* 595 */           Altitude = (int)(Altitude + ac.field_70163_u - 256.0D);
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/* 600 */       altitudeUpdateCount = 30;
/*     */     }
/*     */     else {
/*     */       
/* 604 */       altitudeUpdateCount--;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void updateWeapon(MCH_EntityAircraft ac, MCH_WeaponSet ws) {
/* 610 */     if (ac.getWeaponNum() <= 0) {
/*     */       return;
/*     */     }
/* 613 */     if (ws == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 618 */     CurrentWeapon = ws;
/* 619 */     WeaponName = ac.isPilotReloading() ? "-- Reloading --" : ws.getName();
/*     */     
/* 621 */     if (ws.getAmmoNumMax() > 0) {
/*     */       
/* 623 */       WeaponAmmo = ac.isPilotReloading() ? "----" : String.format("%4d", new Object[] {
/*     */             
/* 625 */             Integer.valueOf(ws.getAmmoNum())
/*     */           });
/* 627 */       WeaponAllAmmo = ac.isPilotReloading() ? "----" : String.format("%4d", new Object[] {
/*     */             
/* 629 */             Integer.valueOf(ws.getRestAllAmmoNum())
/*     */           });
/*     */     }
/*     */     else {
/*     */       
/* 634 */       WeaponAmmo = "";
/* 635 */       WeaponAllAmmo = "";
/*     */     } 
/*     */     
/* 638 */     MCH_WeaponInfo wi = ws.getInfo();
/*     */     
/* 640 */     if (wi.displayMortarDistance) {
/*     */       
/* 642 */       MortarDist = (float)ac.getLandInDistance((Entity)player);
/*     */     }
/*     */     else {
/*     */       
/* 646 */       MortarDist = -1.0F;
/*     */     } 
/*     */     
/* 649 */     if (wi.delay > wi.reloadTime) {
/*     */       
/* 651 */       ReloadSec = (ws.countWait >= 0) ? ws.countWait : -ws.countWait;
/* 652 */       ReloadPer = ws.countWait / ((wi.delay > 0) ? wi.delay : true);
/*     */       
/* 654 */       if (ReloadPer < 0.0F) {
/* 655 */         ReloadPer = -ReloadPer;
/*     */       }
/* 657 */       if (ReloadPer > 1.0F) {
/* 658 */         ReloadPer = 1.0F;
/*     */       }
/*     */     } else {
/*     */       
/* 662 */       ReloadSec = ws.countReloadWait;
/* 663 */       ReloadPer = ws.countReloadWait / ((wi.reloadTime > 0) ? wi.reloadTime : true);
/*     */     } 
/* 665 */     ReloadSec /= 20.0F;
/* 666 */     ReloadPer = (1.0F - ReloadPer) * 100.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void updateUAV(MCH_EntityAircraft ac) {
/* 671 */     if (ac.isUAV() && ac.getUavStation() != null) {
/*     */       
/* 673 */       double dx = ac.field_70165_t - (ac.getUavStation()).field_70165_t;
/* 674 */       double dz = ac.field_70161_v - (ac.getUavStation()).field_70161_v;
/* 675 */       UAV_Dist = (float)Math.sqrt(dx * dx + dz * dz);
/*     */     }
/*     */     else {
/*     */       
/* 679 */       UAV_Dist = 0.0D;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static void updateTvMissile(MCH_EntityAircraft ac) {
/* 685 */     MCH_EntityTvMissile mCH_EntityTvMissile = ac.getTVMissile();
/*     */     
/* 687 */     if (mCH_EntityTvMissile != null) {
/*     */       
/* 689 */       TVM_PosX = ((Entity)mCH_EntityTvMissile).field_70165_t;
/* 690 */       TVM_PosY = ((Entity)mCH_EntityTvMissile).field_70163_u;
/* 691 */       TVM_PosZ = ((Entity)mCH_EntityTvMissile).field_70161_v;
/*     */       
/* 693 */       double dx = ((Entity)mCH_EntityTvMissile).field_70165_t - ac.field_70165_t;
/* 694 */       double dy = ((Entity)mCH_EntityTvMissile).field_70163_u - ac.field_70163_u;
/* 695 */       double dz = ((Entity)mCH_EntityTvMissile).field_70161_v - ac.field_70161_v;
/* 696 */       TVM_Diff = Math.sqrt(dx * dx + dy * dy + dz * dz);
/*     */     }
/*     */     else {
/*     */       
/* 700 */       TVM_PosX = 0.0D;
/* 701 */       TVM_PosY = 0.0D;
/* 702 */       TVM_PosZ = 0.0D;
/* 703 */       TVM_Diff = 0.0D;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\hud\MCH_HudItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */