/*     */ package mcheli;
/*     */ 
/*     */ import mcheli.__helper.client.MCH_CameraManager;
/*     */ import mcheli.aircraft.MCH_AircraftInfo;
/*     */ import mcheli.aircraft.MCH_ClientSeatTickHandler;
/*     */ import mcheli.aircraft.MCH_EntityAircraft;
/*     */ import mcheli.aircraft.MCH_EntitySeat;
/*     */ import mcheli.aircraft.MCH_SeatInfo;
/*     */ import mcheli.command.MCH_GuiTitle;
/*     */ import mcheli.gltd.MCH_ClientGLTDTickHandler;
/*     */ import mcheli.gltd.MCH_EntityGLTD;
/*     */ import mcheli.gltd.MCH_GuiGLTD;
/*     */ import mcheli.gui.MCH_Gui;
/*     */ import mcheli.helicopter.MCH_ClientHeliTickHandler;
/*     */ import mcheli.helicopter.MCH_GuiHeli;
/*     */ import mcheli.lweapon.MCH_ClientLightWeaponTickHandler;
/*     */ import mcheli.lweapon.MCH_GuiLightWeapon;
/*     */ import mcheli.mob.MCH_GuiSpawnGunner;
/*     */ import mcheli.multiplay.MCH_GuiScoreboard;
/*     */ import mcheli.multiplay.MCH_GuiTargetMarker;
/*     */ import mcheli.multiplay.MCH_MultiplayClient;
/*     */ import mcheli.plane.MCP_ClientPlaneTickHandler;
/*     */ import mcheli.plane.MCP_GuiPlane;
/*     */ import mcheli.tank.MCH_ClientTankTickHandler;
/*     */ import mcheli.tank.MCH_GuiTank;
/*     */ import mcheli.tool.MCH_ClientToolTickHandler;
/*     */ import mcheli.tool.MCH_GuiWrench;
/*     */ import mcheli.tool.rangefinder.MCH_GuiRangeFinder;
/*     */ import mcheli.uav.MCH_EntityUavStation;
/*     */ import mcheli.vehicle.MCH_ClientVehicleTickHandler;
/*     */ import mcheli.vehicle.MCH_GuiVehicle;
/*     */ import mcheli.weapon.MCH_WeaponSet;
/*     */ import mcheli.wrapper.W_Lib;
/*     */ import mcheli.wrapper.W_McClient;
/*     */ import mcheli.wrapper.W_Reflection;
/*     */ import mcheli.wrapper.W_TickHandler;
/*     */ import mcheli.wrapper.W_Vec3;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import org.lwjgl.opengl.Display;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @SideOnly(Side.CLIENT)
/*     */ public class MCH_ClientCommonTickHandler
/*     */   extends W_TickHandler
/*     */ {
/*     */   public static MCH_ClientCommonTickHandler instance;
/*     */   public MCH_GuiCommon gui_Common;
/*     */   public MCH_Gui gui_Heli;
/*     */   public MCH_Gui gui_Plane;
/*     */   public MCH_Gui gui_Tank;
/*     */   public MCH_Gui gui_GLTD;
/*     */   public MCH_Gui gui_Vehicle;
/*     */   public MCH_Gui gui_LWeapon;
/*     */   public MCH_Gui gui_Wrench;
/*     */   public MCH_Gui gui_EMarker;
/*     */   public MCH_Gui gui_SwnGnr;
/*     */   public MCH_Gui gui_RngFndr;
/*     */   public MCH_Gui gui_Title;
/*     */   public MCH_Gui[] guis;
/*     */   public MCH_Gui[] guiTicks;
/*     */   public MCH_ClientTickHandlerBase[] ticks;
/*     */   public MCH_Key[] Keys;
/*     */   public MCH_Key KeyCamDistUp;
/*     */   public MCH_Key KeyCamDistDown;
/*     */   public MCH_Key KeyScoreboard;
/*     */   public MCH_Key KeyMultiplayManager;
/*  86 */   public static int cameraMode = 0;
/*  87 */   public static MCH_EntityAircraft ridingAircraft = null;
/*     */   
/*     */   public static boolean isDrawScoreboard = false;
/*     */   
/*  91 */   public static int sendLDCount = 0;
/*     */   
/*     */   public static boolean isLocked = false;
/*  94 */   public static int lockedSoundCount = 0;
/*     */   
/*     */   int debugcnt;
/*     */   
/*     */   public MCH_ClientCommonTickHandler(Minecraft minecraft, MCH_Config config) {
/*  99 */     super(minecraft);
/*     */     
/* 101 */     this.gui_Common = new MCH_GuiCommon(minecraft);
/* 102 */     this.gui_Heli = (MCH_Gui)new MCH_GuiHeli(minecraft);
/* 103 */     this.gui_Plane = (MCH_Gui)new MCP_GuiPlane(minecraft);
/* 104 */     this.gui_Tank = (MCH_Gui)new MCH_GuiTank(minecraft);
/* 105 */     this.gui_GLTD = (MCH_Gui)new MCH_GuiGLTD(minecraft);
/* 106 */     this.gui_Vehicle = (MCH_Gui)new MCH_GuiVehicle(minecraft);
/* 107 */     this.gui_LWeapon = (MCH_Gui)new MCH_GuiLightWeapon(minecraft);
/* 108 */     this.gui_Wrench = (MCH_Gui)new MCH_GuiWrench(minecraft);
/* 109 */     this.gui_SwnGnr = (MCH_Gui)new MCH_GuiSpawnGunner(minecraft);
/* 110 */     this.gui_RngFndr = (MCH_Gui)new MCH_GuiRangeFinder(minecraft);
/* 111 */     this.gui_EMarker = (MCH_Gui)new MCH_GuiTargetMarker(minecraft);
/* 112 */     this.gui_Title = (MCH_Gui)new MCH_GuiTitle(minecraft);
/* 113 */     this.guis = new MCH_Gui[] { this.gui_RngFndr, this.gui_LWeapon, this.gui_Heli, this.gui_Plane, this.gui_Tank, this.gui_GLTD, this.gui_Vehicle };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 119 */     this.guiTicks = new MCH_Gui[] { (MCH_Gui)this.gui_Common, this.gui_Heli, this.gui_Plane, this.gui_Tank, this.gui_GLTD, this.gui_Vehicle, this.gui_LWeapon, this.gui_Wrench, this.gui_SwnGnr, this.gui_RngFndr, this.gui_EMarker, this.gui_Title };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 126 */     this.ticks = new MCH_ClientTickHandlerBase[] { (MCH_ClientTickHandlerBase)new MCH_ClientHeliTickHandler(minecraft, config), (MCH_ClientTickHandlerBase)new MCP_ClientPlaneTickHandler(minecraft, config), (MCH_ClientTickHandlerBase)new MCH_ClientTankTickHandler(minecraft, config), (MCH_ClientTickHandlerBase)new MCH_ClientGLTDTickHandler(minecraft, config), (MCH_ClientTickHandlerBase)new MCH_ClientVehicleTickHandler(minecraft, config), (MCH_ClientTickHandlerBase)new MCH_ClientLightWeaponTickHandler(minecraft, config), (MCH_ClientTickHandlerBase)new MCH_ClientSeatTickHandler(minecraft, config), (MCH_ClientTickHandlerBase)new MCH_ClientToolTickHandler(minecraft, config) };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 135 */     updatekeybind(config);
/*     */   }
/*     */   private static double prevMouseDeltaX; private static double prevMouseDeltaY;
/*     */   
/*     */   public void updatekeybind(MCH_Config config) {
/* 140 */     this.KeyCamDistUp = new MCH_Key(MCH_Config.KeyCameraDistUp.prmInt);
/* 141 */     this.KeyCamDistDown = new MCH_Key(MCH_Config.KeyCameraDistDown.prmInt);
/* 142 */     this.KeyScoreboard = new MCH_Key(MCH_Config.KeyScoreboard.prmInt);
/* 143 */     this.KeyMultiplayManager = new MCH_Key(MCH_Config.KeyMultiplayManager.prmInt);
/* 144 */     this.Keys = new MCH_Key[] { this.KeyCamDistUp, this.KeyCamDistDown, this.KeyScoreboard, this.KeyMultiplayManager };
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 149 */     for (MCH_ClientTickHandlerBase t : this.ticks)
/*     */     {
/* 151 */       t.updateKeybind(config);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public String getLabel() {
/* 157 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onTick() {
/* 162 */     MCH_ClientTickHandlerBase.initRotLimit();
/*     */     
/* 164 */     for (MCH_Key k : this.Keys)
/*     */     {
/* 166 */       k.update();
/*     */     }
/*     */     
/* 169 */     EntityPlayerSP entityPlayerSP = this.mc.field_71439_g;
/*     */     
/* 171 */     if (entityPlayerSP != null && this.mc.field_71462_r == null) {
/*     */       
/* 173 */       if (MCH_ServerSettings.enableCamDistChange)
/*     */       {
/* 175 */         if (this.KeyCamDistUp.isKeyDown() || this.KeyCamDistDown.isKeyDown()) {
/*     */           
/* 177 */           int camdist = (int)W_Reflection.getThirdPersonDistance();
/*     */           
/* 179 */           if (this.KeyCamDistUp.isKeyDown() && camdist < 72) {
/*     */             
/* 181 */             camdist += 4;
/*     */             
/* 183 */             if (camdist > 72) {
/* 184 */               camdist = 72;
/*     */             }
/* 186 */             W_Reflection.setThirdPersonDistance(camdist);
/*     */           }
/* 188 */           else if (this.KeyCamDistDown.isKeyDown()) {
/*     */             
/* 190 */             camdist -= 4;
/*     */             
/* 192 */             if (camdist < 4) {
/* 193 */               camdist = 4;
/*     */             }
/* 195 */             W_Reflection.setThirdPersonDistance(camdist);
/*     */           } 
/*     */         } 
/*     */       }
/*     */       
/* 200 */       if (this.mc.field_71462_r == null && (!this.mc.func_71356_B() || MCH_Config.DebugLog)) {
/*     */         
/* 202 */         isDrawScoreboard = this.KeyScoreboard.isKeyPress();
/*     */         
/* 204 */         if (!isDrawScoreboard && this.KeyMultiplayManager.isKeyDown())
/*     */         {
/* 206 */           MCH_PacketIndOpenScreen.send(5);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 211 */     if (sendLDCount < 10) {
/*     */       
/* 213 */       sendLDCount++;
/*     */     }
/*     */     else {
/*     */       
/* 217 */       MCH_MultiplayClient.sendImageData();
/* 218 */       sendLDCount = 0;
/*     */     } 
/*     */     
/* 221 */     boolean inOtherGui = (this.mc.field_71462_r != null);
/*     */     
/* 223 */     for (MCH_ClientTickHandlerBase t : this.ticks)
/*     */     {
/* 225 */       t.onTick(inOtherGui);
/*     */     }
/*     */     
/* 228 */     for (MCH_Gui g : this.guiTicks)
/*     */     {
/* 230 */       g.onTick();
/*     */     }
/*     */     
/* 233 */     MCH_EntityAircraft ac = MCH_EntityAircraft.getAircraft_RiddenOrControl((Entity)entityPlayerSP);
/*     */     
/* 235 */     if (entityPlayerSP != null && ac != null && !ac.isDestroyed()) {
/*     */       
/* 237 */       if (isLocked && lockedSoundCount == 0) {
/*     */         
/* 239 */         isLocked = false;
/* 240 */         lockedSoundCount = 20;
/* 241 */         MCH_ClientTickHandlerBase.playSound("locked");
/*     */       } 
/*     */       
/* 244 */       MCH_CameraManager.setRidingAircraft(ac);
/*     */     }
/*     */     else {
/*     */       
/* 248 */       lockedSoundCount = 0;
/* 249 */       isLocked = false;
/* 250 */       MCH_CameraManager.setRidingAircraft(ac);
/*     */     } 
/*     */     
/* 253 */     if (lockedSoundCount > 0)
/*     */     {
/* 255 */       lockedSoundCount--;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onTickPre() {
/* 262 */     if (this.mc.field_71439_g != null && this.mc.field_71441_e != null)
/*     */     {
/* 264 */       onTick();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onTickPost() {
/* 271 */     if (this.mc.field_71439_g != null && this.mc.field_71441_e != null)
/*     */     {
/* 273 */       MCH_GuiTargetMarker.onClientTick();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 279 */   private static double mouseDeltaX = 0.0D;
/* 280 */   private static double mouseDeltaY = 0.0D;
/* 281 */   private static double mouseRollDeltaX = 0.0D;
/* 282 */   private static double mouseRollDeltaY = 0.0D;
/*     */   private static boolean isRideAircraft = false;
/* 284 */   private static float prevTick = 0.0F;
/*     */ 
/*     */   
/*     */   public static double getCurrentStickX() {
/* 288 */     return mouseRollDeltaX;
/*     */   }
/*     */ 
/*     */   
/*     */   public static double getCurrentStickY() {
/* 293 */     double inv = 1.0D;
/* 294 */     if ((Minecraft.func_71410_x()).field_71474_y.field_74338_d)
/*     */     {
/* 296 */       inv = -inv;
/*     */     }
/* 298 */     if (MCH_Config.InvertMouse.prmBool)
/*     */     {
/* 300 */       inv = -inv;
/*     */     }
/* 302 */     return mouseRollDeltaY * inv;
/*     */   }
/*     */ 
/*     */   
/*     */   public static double getMaxStickLength() {
/* 307 */     return 40.0D;
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateMouseDelta(boolean stickMode, float partialTicks) {
/* 312 */     prevMouseDeltaX = mouseDeltaX;
/* 313 */     prevMouseDeltaY = mouseDeltaY;
/* 314 */     mouseDeltaX = 0.0D;
/* 315 */     mouseDeltaY = 0.0D;
/*     */     
/* 317 */     if (this.mc.field_71415_G && Display.isActive() && this.mc.field_71462_r == null) {
/*     */       
/* 319 */       if (stickMode) {
/*     */         
/* 321 */         if (Math.abs(mouseRollDeltaX) < getMaxStickLength() * 0.2D)
/*     */         {
/* 323 */           mouseRollDeltaX *= (1.0F - 0.15F * partialTicks);
/*     */         }
/* 325 */         if (Math.abs(mouseRollDeltaY) < getMaxStickLength() * 0.2D)
/*     */         {
/* 327 */           mouseRollDeltaY *= (1.0F - 0.15F * partialTicks);
/*     */         }
/*     */       } 
/*     */       
/* 331 */       this.mc.field_71417_B.func_74374_c();
/*     */       
/* 333 */       float f1 = this.mc.field_71474_y.field_74341_c * 0.6F + 0.2F;
/* 334 */       float f2 = f1 * f1 * f1 * 8.0F;
/* 335 */       double ms = MCH_Config.MouseSensitivity.prmDouble * 0.1D;
/*     */       
/* 337 */       mouseDeltaX = ms * this.mc.field_71417_B.field_74377_a * f2;
/* 338 */       mouseDeltaY = ms * this.mc.field_71417_B.field_74375_b * f2;
/*     */       
/* 340 */       byte inv = 1;
/*     */       
/* 342 */       if (this.mc.field_71474_y.field_74338_d)
/*     */       {
/* 344 */         inv = -1;
/*     */       }
/*     */       
/* 347 */       if (MCH_Config.InvertMouse.prmBool)
/*     */       {
/* 349 */         inv = (byte)(inv * -1);
/*     */       }
/*     */       
/* 352 */       mouseRollDeltaX += mouseDeltaX;
/* 353 */       mouseRollDeltaY += mouseDeltaY * inv;
/*     */       
/* 355 */       double dist = mouseRollDeltaX * mouseRollDeltaX + mouseRollDeltaY * mouseRollDeltaY;
/*     */       
/* 357 */       if (dist > 1.0D) {
/*     */         
/* 359 */         dist = MathHelper.func_76133_a(dist);
/* 360 */         double d = dist;
/*     */         
/* 362 */         if (d > getMaxStickLength())
/*     */         {
/* 364 */           d = getMaxStickLength();
/*     */         }
/*     */         
/* 367 */         mouseRollDeltaX /= dist;
/* 368 */         mouseRollDeltaY /= dist;
/* 369 */         mouseRollDeltaX *= d;
/* 370 */         mouseRollDeltaY *= d;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onRenderTickPre(float partialTicks) {
/* 378 */     MCH_GuiTargetMarker.clearMarkEntityPos();
/*     */     
/* 380 */     if (!MCH_ServerSettings.enableDebugBoundingBox)
/*     */     {
/*     */       
/* 383 */       Minecraft.func_71410_x().func_175598_ae().func_178629_b(false);
/*     */     }
/*     */     
/* 386 */     MCH_ClientEventHook.haveSearchLightAircraft.clear();
/*     */     
/* 388 */     if (this.mc != null && this.mc.field_71441_e != null)
/*     */     {
/* 390 */       for (Object o : (Minecraft.func_71410_x()).field_71441_e.field_72996_f) {
/*     */         
/* 392 */         if (o instanceof MCH_EntityAircraft)
/*     */         {
/* 394 */           if (((MCH_EntityAircraft)o).haveSearchLight())
/*     */           {
/* 396 */             MCH_ClientEventHook.haveSearchLightAircraft.add((MCH_EntityAircraft)o);
/*     */           }
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 402 */     if (W_McClient.isGamePaused()) {
/*     */       return;
/*     */     }
/* 405 */     EntityPlayerSP entityPlayerSP = this.mc.field_71439_g;
/*     */     
/* 407 */     if (entityPlayerSP == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 413 */     ItemStack currentItemstack = entityPlayerSP.func_184586_b(EnumHand.MAIN_HAND);
/*     */     
/* 415 */     if (currentItemstack != null && currentItemstack.func_77973_b() instanceof mcheli.tool.MCH_ItemWrench)
/*     */     {
/* 417 */       if (entityPlayerSP.func_184605_cv() > 0)
/*     */       {
/*     */         
/* 420 */         W_Reflection.setItemRendererMainProgress(1.0F);
/*     */       }
/*     */     }
/*     */     
/* 424 */     ridingAircraft = MCH_EntityAircraft.getAircraft_RiddenOrControl((Entity)entityPlayerSP);
/*     */     
/* 426 */     if (ridingAircraft != null) {
/*     */       
/* 428 */       cameraMode = ridingAircraft.getCameraMode((EntityPlayer)entityPlayerSP);
/*     */     }
/* 430 */     else if (entityPlayerSP.func_184187_bx() instanceof MCH_EntityGLTD) {
/*     */       
/* 432 */       MCH_EntityGLTD gltd = (MCH_EntityGLTD)entityPlayerSP.func_184187_bx();
/*     */       
/* 434 */       cameraMode = gltd.camera.getMode(0);
/*     */     }
/*     */     else {
/*     */       
/* 438 */       cameraMode = 0;
/*     */     } 
/*     */     
/* 441 */     MCH_EntityAircraft ac = null;
/*     */     
/* 443 */     if (entityPlayerSP.func_184187_bx() instanceof mcheli.helicopter.MCH_EntityHeli || entityPlayerSP.func_184187_bx() instanceof mcheli.plane.MCP_EntityPlane || entityPlayerSP
/* 444 */       .func_184187_bx() instanceof mcheli.tank.MCH_EntityTank) {
/*     */       
/* 446 */       ac = (MCH_EntityAircraft)entityPlayerSP.func_184187_bx();
/*     */     }
/* 448 */     else if (entityPlayerSP.func_184187_bx() instanceof MCH_EntityUavStation) {
/*     */       
/* 450 */       ac = ((MCH_EntityUavStation)entityPlayerSP.func_184187_bx()).getControlAircract();
/*     */     }
/* 452 */     else if (entityPlayerSP.func_184187_bx() instanceof mcheli.vehicle.MCH_EntityVehicle) {
/*     */       
/* 454 */       MCH_EntityAircraft vehicle = (MCH_EntityAircraft)entityPlayerSP.func_184187_bx();
/* 455 */       vehicle.setupAllRiderRenderPosition(partialTicks, (EntityPlayer)entityPlayerSP);
/*     */     } 
/*     */     
/* 458 */     boolean stickMode = false;
/*     */     
/* 460 */     if (ac instanceof mcheli.helicopter.MCH_EntityHeli)
/*     */     {
/* 462 */       stickMode = MCH_Config.MouseControlStickModeHeli.prmBool;
/*     */     }
/*     */     
/* 465 */     if (ac instanceof mcheli.plane.MCP_EntityPlane)
/*     */     {
/* 467 */       stickMode = MCH_Config.MouseControlStickModePlane.prmBool;
/*     */     }
/*     */     
/* 470 */     for (int i = 0; i < 10 && prevTick > partialTicks; i++)
/*     */     {
/* 472 */       prevTick--;
/*     */     }
/*     */     
/* 475 */     if (ac != null && ac.canMouseRot()) {
/*     */       
/* 477 */       if (!isRideAircraft)
/*     */       {
/* 479 */         ac.onInteractFirst((EntityPlayer)entityPlayerSP);
/*     */       }
/*     */       
/* 482 */       isRideAircraft = true;
/* 483 */       updateMouseDelta(stickMode, partialTicks);
/*     */       
/* 485 */       boolean fixRot = false;
/* 486 */       float fixYaw = 0.0F;
/* 487 */       float fixPitch = 0.0F;
/* 488 */       MCH_SeatInfo seatInfo = ac.getSeatInfo((Entity)entityPlayerSP);
/*     */       
/* 490 */       if (seatInfo != null && seatInfo.fixRot && ac.getIsGunnerMode((Entity)entityPlayerSP) && !ac.isGunnerLookMode((EntityPlayer)entityPlayerSP)) {
/*     */         
/* 492 */         fixRot = true;
/* 493 */         fixYaw = seatInfo.fixYaw;
/* 494 */         fixPitch = seatInfo.fixPitch;
/* 495 */         mouseRollDeltaX *= 0.0D;
/* 496 */         mouseRollDeltaY *= 0.0D;
/* 497 */         mouseDeltaX *= 0.0D;
/* 498 */         mouseDeltaY *= 0.0D;
/*     */       }
/* 500 */       else if (ac.isPilot((Entity)entityPlayerSP)) {
/*     */         
/* 502 */         MCH_AircraftInfo.CameraPosition cp = ac.getCameraPosInfo();
/*     */         
/* 504 */         if (cp != null) {
/*     */           
/* 506 */           fixYaw = cp.yaw;
/* 507 */           fixPitch = cp.pitch;
/*     */         } 
/*     */       } 
/*     */       
/* 511 */       if (ac.getAcInfo() == null) {
/*     */         
/* 513 */         entityPlayerSP.func_70082_c((float)mouseDeltaX, (float)mouseDeltaY);
/*     */       }
/*     */       else {
/*     */         
/* 517 */         ac.setAngles((Entity)entityPlayerSP, fixRot, fixYaw, fixPitch, (float)(mouseDeltaX + prevMouseDeltaX) / 2.0F, (float)(mouseDeltaY + prevMouseDeltaY) / 2.0F, (float)mouseRollDeltaX, (float)mouseRollDeltaY, partialTicks - prevTick);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 522 */       ac.setupAllRiderRenderPosition(partialTicks, (EntityPlayer)entityPlayerSP);
/*     */       
/* 524 */       double dist = MathHelper.func_76133_a(mouseRollDeltaX * mouseRollDeltaX + mouseRollDeltaY * mouseRollDeltaY);
/*     */       
/* 526 */       if (!stickMode || dist < getMaxStickLength() * 0.1D) {
/*     */         
/* 528 */         mouseRollDeltaX *= 0.95D;
/* 529 */         mouseRollDeltaY *= 0.95D;
/*     */       } 
/*     */       
/* 532 */       float roll = MathHelper.func_76142_g(ac.getRotRoll());
/* 533 */       float yaw = MathHelper.func_76142_g(ac.getRotYaw() - ((EntityPlayer)entityPlayerSP).field_70177_z);
/*     */       
/* 535 */       roll *= MathHelper.func_76134_b((float)(yaw * Math.PI / 180.0D));
/*     */       
/* 537 */       if (ac.getTVMissile() != null && W_Lib.isClientPlayer((ac.getTVMissile()).shootingEntity) && ac
/* 538 */         .getIsGunnerMode((Entity)entityPlayerSP))
/*     */       {
/* 540 */         roll = 0.0F;
/*     */       }
/*     */       
/* 543 */       W_Reflection.setCameraRoll(roll);
/* 544 */       correctViewEntityDummy((Entity)entityPlayerSP);
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 549 */       MCH_EntitySeat seat = (entityPlayerSP.func_184187_bx() instanceof MCH_EntitySeat) ? (MCH_EntitySeat)entityPlayerSP.func_184187_bx() : null;
/*     */ 
/*     */       
/* 552 */       if (seat != null && seat.getParent() != null) {
/*     */         
/* 554 */         updateMouseDelta(stickMode, partialTicks);
/*     */         
/* 556 */         ac = seat.getParent();
/*     */         
/* 558 */         boolean fixRot = false;
/* 559 */         MCH_SeatInfo seatInfo = ac.getSeatInfo((Entity)entityPlayerSP);
/*     */         
/* 561 */         if (seatInfo != null && seatInfo.fixRot && ac.getIsGunnerMode((Entity)entityPlayerSP) && !ac.isGunnerLookMode((EntityPlayer)entityPlayerSP)) {
/*     */           
/* 563 */           fixRot = true;
/* 564 */           mouseRollDeltaX *= 0.0D;
/* 565 */           mouseRollDeltaY *= 0.0D;
/* 566 */           mouseDeltaX *= 0.0D;
/* 567 */           mouseDeltaY *= 0.0D;
/*     */         } 
/*     */         
/* 570 */         Vec3d v = new Vec3d(mouseDeltaX, mouseRollDeltaY, 0.0D);
/*     */         
/* 572 */         v = W_Vec3.rotateRoll((float)((ac.calcRotRoll(partialTicks) / 180.0F) * Math.PI), v);
/*     */         
/* 574 */         MCH_WeaponSet ws = ac.getCurrentWeapon((Entity)entityPlayerSP);
/* 575 */         mouseDeltaY *= (ws != null && ws.getInfo() != null) ? (ws.getInfo()).cameraRotationSpeedPitch : 1.0D;
/* 576 */         entityPlayerSP.func_70082_c((float)mouseDeltaX, (float)mouseDeltaY);
/*     */         
/* 578 */         float y = ac.getRotYaw();
/* 579 */         float p = ac.getRotPitch();
/* 580 */         float r = ac.getRotRoll();
/* 581 */         ac.setRotYaw(ac.calcRotYaw(partialTicks));
/* 582 */         ac.setRotPitch(ac.calcRotPitch(partialTicks));
/* 583 */         ac.setRotRoll(ac.calcRotRoll(partialTicks));
/*     */         
/* 585 */         float revRoll = 0.0F;
/*     */         
/* 587 */         if (fixRot) {
/*     */           
/* 589 */           ((EntityPlayer)entityPlayerSP).field_70177_z = ac.getRotYaw() + seatInfo.fixYaw;
/* 590 */           ((EntityPlayer)entityPlayerSP).field_70125_A = ac.getRotPitch() + seatInfo.fixPitch;
/*     */           
/* 592 */           if (((EntityPlayer)entityPlayerSP).field_70125_A > 90.0F) {
/*     */             
/* 594 */             ((EntityPlayer)entityPlayerSP).field_70127_C -= (((EntityPlayer)entityPlayerSP).field_70125_A - 90.0F) * 2.0F;
/* 595 */             ((EntityPlayer)entityPlayerSP).field_70125_A -= (((EntityPlayer)entityPlayerSP).field_70125_A - 90.0F) * 2.0F;
/* 596 */             ((EntityPlayer)entityPlayerSP).field_70126_B += 180.0F;
/* 597 */             ((EntityPlayer)entityPlayerSP).field_70177_z += 180.0F;
/* 598 */             revRoll = 180.0F;
/*     */           }
/* 600 */           else if (((EntityPlayer)entityPlayerSP).field_70125_A < -90.0F) {
/*     */             
/* 602 */             ((EntityPlayer)entityPlayerSP).field_70127_C -= (((EntityPlayer)entityPlayerSP).field_70125_A - 90.0F) * 2.0F;
/* 603 */             ((EntityPlayer)entityPlayerSP).field_70125_A -= (((EntityPlayer)entityPlayerSP).field_70125_A - 90.0F) * 2.0F;
/* 604 */             ((EntityPlayer)entityPlayerSP).field_70126_B += 180.0F;
/* 605 */             ((EntityPlayer)entityPlayerSP).field_70177_z += 180.0F;
/* 606 */             revRoll = 180.0F;
/*     */           } 
/*     */         } 
/*     */         
/* 610 */         ac.setupAllRiderRenderPosition(partialTicks, (EntityPlayer)entityPlayerSP);
/* 611 */         ac.setRotYaw(y);
/* 612 */         ac.setRotPitch(p);
/* 613 */         ac.setRotRoll(r);
/*     */         
/* 615 */         mouseRollDeltaX *= 0.9D;
/* 616 */         mouseRollDeltaY *= 0.9D;
/*     */         
/* 618 */         float roll = MathHelper.func_76142_g(ac.getRotRoll());
/* 619 */         float yaw = MathHelper.func_76142_g(ac.getRotYaw() - ((EntityPlayer)entityPlayerSP).field_70177_z);
/* 620 */         roll *= MathHelper.func_76134_b((float)(yaw * Math.PI / 180.0D));
/*     */         
/* 622 */         if (ac.getTVMissile() != null && W_Lib.isClientPlayer((ac.getTVMissile()).shootingEntity) && ac
/* 623 */           .getIsGunnerMode((Entity)entityPlayerSP))
/*     */         {
/* 625 */           roll = 0.0F;
/*     */         }
/*     */         
/* 628 */         W_Reflection.setCameraRoll(roll + revRoll);
/* 629 */         correctViewEntityDummy((Entity)entityPlayerSP);
/*     */       }
/*     */       else {
/*     */         
/* 633 */         if (isRideAircraft) {
/*     */           
/* 635 */           W_Reflection.setCameraRoll(0.0F);
/* 636 */           isRideAircraft = false;
/*     */         } 
/*     */         
/* 639 */         mouseRollDeltaX = 0.0D;
/* 640 */         mouseRollDeltaY = 0.0D;
/*     */       } 
/*     */     } 
/*     */     
/* 644 */     if (ac != null) {
/*     */       
/* 646 */       if (ac.getSeatIdByEntity((Entity)entityPlayerSP) == 0 && !ac.isDestroyed()) {
/*     */         
/* 648 */         ac.lastRiderYaw = ((EntityPlayer)entityPlayerSP).field_70177_z;
/* 649 */         ac.prevLastRiderYaw = ((EntityPlayer)entityPlayerSP).field_70126_B;
/* 650 */         ac.lastRiderPitch = ((EntityPlayer)entityPlayerSP).field_70125_A;
/* 651 */         ac.prevLastRiderPitch = ((EntityPlayer)entityPlayerSP).field_70127_C;
/*     */       } 
/*     */       
/* 654 */       ac.updateWeaponsRotation();
/*     */     } 
/*     */     
/* 657 */     MCH_ViewEntityDummy mCH_ViewEntityDummy = MCH_ViewEntityDummy.getInstance(((EntityPlayer)entityPlayerSP).field_70170_p);
/*     */     
/* 659 */     if (mCH_ViewEntityDummy != null) {
/*     */       
/* 661 */       ((Entity)mCH_ViewEntityDummy).field_70177_z = ((EntityPlayer)entityPlayerSP).field_70177_z;
/* 662 */       ((Entity)mCH_ViewEntityDummy).field_70126_B = ((EntityPlayer)entityPlayerSP).field_70126_B;
/*     */       
/* 664 */       if (ac != null) {
/*     */         
/* 666 */         MCH_WeaponSet wi = ac.getCurrentWeapon((Entity)entityPlayerSP);
/*     */         
/* 668 */         if (wi != null && wi.getInfo() != null && (wi.getInfo()).fixCameraPitch)
/*     */         {
/* 670 */           ((Entity)mCH_ViewEntityDummy).field_70125_A = ((Entity)mCH_ViewEntityDummy).field_70127_C = 0.0F;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 675 */     prevTick = partialTicks;
/*     */   }
/*     */ 
/*     */   
/*     */   public void correctViewEntityDummy(Entity entity) {
/* 680 */     MCH_ViewEntityDummy mCH_ViewEntityDummy = MCH_ViewEntityDummy.getInstance(entity.field_70170_p);
/*     */     
/* 682 */     if (mCH_ViewEntityDummy != null)
/*     */     {
/* 684 */       if (((Entity)mCH_ViewEntityDummy).field_70177_z - ((Entity)mCH_ViewEntityDummy).field_70126_B > 180.0F) {
/*     */         
/* 686 */         ((Entity)mCH_ViewEntityDummy).field_70126_B += 360.0F;
/*     */       }
/* 688 */       else if (((Entity)mCH_ViewEntityDummy).field_70177_z - ((Entity)mCH_ViewEntityDummy).field_70126_B < -180.0F) {
/*     */         
/* 690 */         ((Entity)mCH_ViewEntityDummy).field_70126_B -= 360.0F;
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onPlayerTickPre(EntityPlayer player) {
/* 698 */     if (player.field_70170_p.field_72995_K) {
/*     */ 
/*     */       
/* 701 */       ItemStack currentItemstack = player.func_184586_b(EnumHand.MAIN_HAND);
/*     */ 
/*     */       
/* 704 */       if (!currentItemstack.func_190926_b() && currentItemstack.func_77973_b() instanceof mcheli.tool.MCH_ItemWrench)
/*     */       {
/*     */         
/* 707 */         if (player.func_184605_cv() > 0 && player.func_184607_cu() != currentItemstack) {
/*     */           
/* 709 */           int maxdm = currentItemstack.func_77958_k();
/* 710 */           int dm = currentItemstack.func_77960_j();
/*     */           
/* 712 */           if (dm <= maxdm && dm > 0)
/*     */           {
/*     */             
/* 715 */             player.func_184598_c(EnumHand.MAIN_HAND);
/*     */           }
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onPlayerTickPost(EntityPlayer player) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void onRenderTickPost(float partialTicks) {
/* 730 */     if (this.mc.field_71439_g != null) {
/*     */       
/* 732 */       MCH_ClientTickHandlerBase.applyRotLimit((Entity)this.mc.field_71439_g);
/*     */       
/* 734 */       MCH_ViewEntityDummy mCH_ViewEntityDummy = MCH_ViewEntityDummy.getInstance(this.mc.field_71439_g.field_70170_p);
/*     */       
/* 736 */       if (mCH_ViewEntityDummy != null) {
/*     */         
/* 738 */         ((Entity)mCH_ViewEntityDummy).field_70125_A = this.mc.field_71439_g.field_70125_A;
/* 739 */         ((Entity)mCH_ViewEntityDummy).field_70177_z = this.mc.field_71439_g.field_70177_z;
/* 740 */         ((Entity)mCH_ViewEntityDummy).field_70127_C = this.mc.field_71439_g.field_70127_C;
/* 741 */         ((Entity)mCH_ViewEntityDummy).field_70126_B = this.mc.field_71439_g.field_70126_B;
/*     */       } 
/*     */     } 
/*     */     
/* 745 */     if (this.mc.field_71462_r == null || this.mc.field_71462_r instanceof net.minecraft.client.gui.GuiChat || this.mc.field_71462_r
/* 746 */       .getClass().toString().indexOf("GuiDriveableController") >= 0) {
/*     */       
/* 748 */       for (MCH_Gui gui : this.guis) {
/*     */         
/* 750 */         if (drawGui(gui, partialTicks)) {
/*     */           break;
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 756 */       drawGui((MCH_Gui)this.gui_Common, partialTicks);
/* 757 */       drawGui(this.gui_Wrench, partialTicks);
/* 758 */       drawGui(this.gui_SwnGnr, partialTicks);
/* 759 */       drawGui(this.gui_EMarker, partialTicks);
/*     */       
/* 761 */       if (isDrawScoreboard)
/*     */       {
/* 763 */         MCH_GuiScoreboard.drawList(this.mc, this.mc.field_71466_p, false);
/*     */       }
/*     */       
/* 766 */       drawGui(this.gui_Title, partialTicks);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean drawGui(MCH_Gui gui, float partialTicks) {
/* 772 */     if (gui.isDrawGui((EntityPlayer)this.mc.field_71439_g)) {
/*     */       
/* 774 */       gui.func_73863_a(0, 0, partialTicks);
/* 775 */       return true;
/*     */     } 
/* 777 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\MCH_ClientCommonTickHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */