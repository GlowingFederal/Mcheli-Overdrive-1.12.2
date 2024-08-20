package mcheli;

import mcheli.__helper.client.MCH_CameraManager;
import mcheli.aircraft.MCH_AircraftInfo;
import mcheli.aircraft.MCH_ClientSeatTickHandler;
import mcheli.aircraft.MCH_EntityAircraft;
import mcheli.aircraft.MCH_EntitySeat;
import mcheli.aircraft.MCH_SeatInfo;
import mcheli.command.MCH_GuiTitle;
import mcheli.gltd.MCH_ClientGLTDTickHandler;
import mcheli.gltd.MCH_EntityGLTD;
import mcheli.gltd.MCH_GuiGLTD;
import mcheli.gui.MCH_Gui;
import mcheli.helicopter.MCH_ClientHeliTickHandler;
import mcheli.helicopter.MCH_GuiHeli;
import mcheli.lweapon.MCH_ClientLightWeaponTickHandler;
import mcheli.lweapon.MCH_GuiLightWeapon;
import mcheli.mob.MCH_GuiSpawnGunner;
import mcheli.multiplay.MCH_GuiScoreboard;
import mcheli.multiplay.MCH_GuiTargetMarker;
import mcheli.multiplay.MCH_MultiplayClient;
import mcheli.plane.MCP_ClientPlaneTickHandler;
import mcheli.plane.MCP_GuiPlane;
import mcheli.tank.MCH_ClientTankTickHandler;
import mcheli.tank.MCH_GuiTank;
import mcheli.tool.MCH_ClientToolTickHandler;
import mcheli.tool.MCH_GuiWrench;
import mcheli.tool.rangefinder.MCH_GuiRangeFinder;
import mcheli.uav.MCH_EntityUavStation;
import mcheli.vehicle.MCH_ClientVehicleTickHandler;
import mcheli.vehicle.MCH_GuiVehicle;
import mcheli.weapon.MCH_WeaponSet;
import mcheli.wrapper.W_Lib;
import mcheli.wrapper.W_McClient;
import mcheli.wrapper.W_Reflection;
import mcheli.wrapper.W_TickHandler;
import mcheli.wrapper.W_Vec3;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.Display;

@SideOnly(Side.CLIENT)
public class MCH_ClientCommonTickHandler extends W_TickHandler {
  public static MCH_ClientCommonTickHandler instance;
  
  public MCH_GuiCommon gui_Common;
  
  public MCH_Gui gui_Heli;
  
  public MCH_Gui gui_Plane;
  
  public MCH_Gui gui_Tank;
  
  public MCH_Gui gui_GLTD;
  
  public MCH_Gui gui_Vehicle;
  
  public MCH_Gui gui_LWeapon;
  
  public MCH_Gui gui_Wrench;
  
  public MCH_Gui gui_EMarker;
  
  public MCH_Gui gui_SwnGnr;
  
  public MCH_Gui gui_RngFndr;
  
  public MCH_Gui gui_Title;
  
  public MCH_Gui[] guis;
  
  public MCH_Gui[] guiTicks;
  
  public MCH_ClientTickHandlerBase[] ticks;
  
  public MCH_Key[] Keys;
  
  public MCH_Key KeyCamDistUp;
  
  public MCH_Key KeyCamDistDown;
  
  public MCH_Key KeyScoreboard;
  
  public MCH_Key KeyMultiplayManager;
  
  public static int cameraMode = 0;
  
  public static MCH_EntityAircraft ridingAircraft = null;
  
  public static boolean isDrawScoreboard = false;
  
  public static int sendLDCount = 0;
  
  public static boolean isLocked = false;
  
  public static int lockedSoundCount = 0;
  
  int debugcnt;
  
  private static double prevMouseDeltaX;
  
  private static double prevMouseDeltaY;
  
  public MCH_ClientCommonTickHandler(Minecraft minecraft, MCH_Config config) {
    super(minecraft);
    this.gui_Common = new MCH_GuiCommon(minecraft);
    this.gui_Heli = (MCH_Gui)new MCH_GuiHeli(minecraft);
    this.gui_Plane = (MCH_Gui)new MCP_GuiPlane(minecraft);
    this.gui_Tank = (MCH_Gui)new MCH_GuiTank(minecraft);
    this.gui_GLTD = (MCH_Gui)new MCH_GuiGLTD(minecraft);
    this.gui_Vehicle = (MCH_Gui)new MCH_GuiVehicle(minecraft);
    this.gui_LWeapon = (MCH_Gui)new MCH_GuiLightWeapon(minecraft);
    this.gui_Wrench = (MCH_Gui)new MCH_GuiWrench(minecraft);
    this.gui_SwnGnr = (MCH_Gui)new MCH_GuiSpawnGunner(minecraft);
    this.gui_RngFndr = (MCH_Gui)new MCH_GuiRangeFinder(minecraft);
    this.gui_EMarker = (MCH_Gui)new MCH_GuiTargetMarker(minecraft);
    this.gui_Title = (MCH_Gui)new MCH_GuiTitle(minecraft);
    this.guis = new MCH_Gui[] { this.gui_RngFndr, this.gui_LWeapon, this.gui_Heli, this.gui_Plane, this.gui_Tank, this.gui_GLTD, this.gui_Vehicle };
    this.guiTicks = new MCH_Gui[] { 
        (MCH_Gui)this.gui_Common, this.gui_Heli, this.gui_Plane, this.gui_Tank, this.gui_GLTD, this.gui_Vehicle, this.gui_LWeapon, this.gui_Wrench, this.gui_SwnGnr, this.gui_RngFndr, 
        this.gui_EMarker, this.gui_Title };
    this.ticks = new MCH_ClientTickHandlerBase[] { (MCH_ClientTickHandlerBase)new MCH_ClientHeliTickHandler(minecraft, config), (MCH_ClientTickHandlerBase)new MCP_ClientPlaneTickHandler(minecraft, config), (MCH_ClientTickHandlerBase)new MCH_ClientTankTickHandler(minecraft, config), (MCH_ClientTickHandlerBase)new MCH_ClientGLTDTickHandler(minecraft, config), (MCH_ClientTickHandlerBase)new MCH_ClientVehicleTickHandler(minecraft, config), (MCH_ClientTickHandlerBase)new MCH_ClientLightWeaponTickHandler(minecraft, config), (MCH_ClientTickHandlerBase)new MCH_ClientSeatTickHandler(minecraft, config), (MCH_ClientTickHandlerBase)new MCH_ClientToolTickHandler(minecraft, config) };
    updatekeybind(config);
  }
  
  public void updatekeybind(MCH_Config config) {
    this.KeyCamDistUp = new MCH_Key(MCH_Config.KeyCameraDistUp.prmInt);
    this.KeyCamDistDown = new MCH_Key(MCH_Config.KeyCameraDistDown.prmInt);
    this.KeyScoreboard = new MCH_Key(MCH_Config.KeyScoreboard.prmInt);
    this.KeyMultiplayManager = new MCH_Key(MCH_Config.KeyMultiplayManager.prmInt);
    this.Keys = new MCH_Key[] { this.KeyCamDistUp, this.KeyCamDistDown, this.KeyScoreboard, this.KeyMultiplayManager };
    for (MCH_ClientTickHandlerBase t : this.ticks)
      t.updateKeybind(config); 
  }
  
  public String getLabel() {
    return null;
  }
  
  public void onTick() {
    MCH_ClientTickHandlerBase.initRotLimit();
    for (MCH_Key k : this.Keys)
      k.update(); 
    EntityPlayerSP entityPlayerSP = this.mc.player;
    if (entityPlayerSP != null && this.mc.currentScreen == null) {
      if (MCH_ServerSettings.enableCamDistChange)
        if (this.KeyCamDistUp.isKeyDown() || this.KeyCamDistDown.isKeyDown()) {
          int camdist = (int)W_Reflection.getThirdPersonDistance();
          if (this.KeyCamDistUp.isKeyDown() && camdist < 72) {
            camdist += 4;
            if (camdist > 72)
              camdist = 72; 
            W_Reflection.setThirdPersonDistance(camdist);
          } else if (this.KeyCamDistDown.isKeyDown()) {
            camdist -= 4;
            if (camdist < 4)
              camdist = 4; 
            W_Reflection.setThirdPersonDistance(camdist);
          } 
        }  
      if (this.mc.currentScreen == null && (!this.mc.isSingleplayer() || MCH_Config.DebugLog)) {
        isDrawScoreboard = this.KeyScoreboard.isKeyPress();
        if (!isDrawScoreboard && this.KeyMultiplayManager.isKeyDown())
          MCH_PacketIndOpenScreen.send(5); 
      } 
    } 
    if (sendLDCount < 10) {
      sendLDCount++;
    } else {
      MCH_MultiplayClient.sendImageData();
      sendLDCount = 0;
    } 
    boolean inOtherGui = (this.mc.currentScreen != null);
    for (MCH_ClientTickHandlerBase t : this.ticks)
      t.onTick(inOtherGui); 
    for (MCH_Gui g : this.guiTicks)
      g.onTick(); 
    MCH_EntityAircraft ac = MCH_EntityAircraft.getAircraft_RiddenOrControl((Entity)entityPlayerSP);
    if (entityPlayerSP != null && ac != null && !ac.isDestroyed()) {
      if (isLocked && lockedSoundCount == 0) {
        isLocked = false;
        lockedSoundCount = 20;
        MCH_ClientTickHandlerBase.playSound("locked");
      } 
      MCH_CameraManager.setRidingAircraft(ac);
    } else {
      lockedSoundCount = 0;
      isLocked = false;
      MCH_CameraManager.setRidingAircraft(ac);
    } 
    if (lockedSoundCount > 0)
      lockedSoundCount--; 
  }
  
  public void onTickPre() {
    if (this.mc.player != null && this.mc.world != null)
      onTick(); 
  }
  
  public void onTickPost() {
    if (this.mc.player != null && this.mc.world != null)
      MCH_GuiTargetMarker.onClientTick(); 
  }
  
  private static double mouseDeltaX = 0.0D;
  
  private static double mouseDeltaY = 0.0D;
  
  private static double mouseRollDeltaX = 0.0D;
  
  private static double mouseRollDeltaY = 0.0D;
  
  private static boolean isRideAircraft = false;
  
  private static float prevTick = 0.0F;
  
  public static double getCurrentStickX() {
    return mouseRollDeltaX;
  }
  
  public static double getCurrentStickY() {
    double inv = 1.0D;
    if ((Minecraft.getMinecraft()).gameSettings.invertMouse)
      inv = -inv; 
    if (MCH_Config.InvertMouse.prmBool)
      inv = -inv; 
    return mouseRollDeltaY * inv;
  }
  
  public static double getMaxStickLength() {
    return 40.0D;
  }
  
  public void updateMouseDelta(boolean stickMode, float partialTicks) {
    prevMouseDeltaX = mouseDeltaX;
    prevMouseDeltaY = mouseDeltaY;
    mouseDeltaX = 0.0D;
    mouseDeltaY = 0.0D;
    if (this.mc.inGameHasFocus && Display.isActive() && this.mc.currentScreen == null) {
      if (stickMode) {
        if (Math.abs(mouseRollDeltaX) < getMaxStickLength() * 0.2D)
          mouseRollDeltaX *= (1.0F - 0.15F * partialTicks); 
        if (Math.abs(mouseRollDeltaY) < getMaxStickLength() * 0.2D)
          mouseRollDeltaY *= (1.0F - 0.15F * partialTicks); 
      } 
      this.mc.mouseHelper.mouseXYChange();
      float f1 = this.mc.gameSettings.mouseSensitivity * 0.6F + 0.2F;
      float f2 = f1 * f1 * f1 * 8.0F;
      double ms = MCH_Config.MouseSensitivity.prmDouble * 0.1D;
      mouseDeltaX = ms * this.mc.mouseHelper.deltaX * f2;
      mouseDeltaY = ms * this.mc.mouseHelper.deltaY * f2;
      byte inv = 1;
      if (this.mc.gameSettings.invertMouse)
        inv = -1; 
      if (MCH_Config.InvertMouse.prmBool)
        inv = (byte)(inv * -1); 
      mouseRollDeltaX += mouseDeltaX;
      mouseRollDeltaY += mouseDeltaY * inv;
      double dist = mouseRollDeltaX * mouseRollDeltaX + mouseRollDeltaY * mouseRollDeltaY;
      if (dist > 1.0D) {
        dist = MathHelper.sqrt(dist);
        double d = dist;
        if (d > getMaxStickLength())
          d = getMaxStickLength(); 
        mouseRollDeltaX /= dist;
        mouseRollDeltaY /= dist;
        mouseRollDeltaX *= d;
        mouseRollDeltaY *= d;
      } 
    } 
  }
  
  public void onRenderTickPre(float partialTicks) {
    MCH_GuiTargetMarker.clearMarkEntityPos();
    if (!MCH_ServerSettings.enableDebugBoundingBox)
      Minecraft.getMinecraft().getRenderManager().setDebugBoundingBox(false); 
    MCH_ClientEventHook.haveSearchLightAircraft.clear();
    if (this.mc != null && this.mc.world != null)
      for (Object o : (Minecraft.getMinecraft()).world.loadedEntityList) {
        if (o instanceof MCH_EntityAircraft)
          if (((MCH_EntityAircraft)o).haveSearchLight())
            MCH_ClientEventHook.haveSearchLightAircraft.add((MCH_EntityAircraft)o);  
      }  
    if (W_McClient.isGamePaused())
      return; 
    EntityPlayerSP entityPlayerSP = this.mc.player;
    if (entityPlayerSP == null)
      return; 
    ItemStack currentItemstack = entityPlayerSP.getHeldItem(EnumHand.MAIN_HAND);
    if (currentItemstack != null && currentItemstack.getItem() instanceof mcheli.tool.MCH_ItemWrench)
      if (entityPlayerSP.getItemInUseCount() > 0)
        W_Reflection.setItemRendererMainProgress(1.0F);  
    ridingAircraft = MCH_EntityAircraft.getAircraft_RiddenOrControl((Entity)entityPlayerSP);
    if (ridingAircraft != null) {
      cameraMode = ridingAircraft.getCameraMode((EntityPlayer)entityPlayerSP);
    } else if (entityPlayerSP.getRidingEntity() instanceof MCH_EntityGLTD) {
      MCH_EntityGLTD gltd = (MCH_EntityGLTD)entityPlayerSP.getRidingEntity();
      cameraMode = gltd.camera.getMode(0);
    } else {
      cameraMode = 0;
    } 
    MCH_EntityAircraft ac = null;
    if (entityPlayerSP.getRidingEntity() instanceof mcheli.helicopter.MCH_EntityHeli || entityPlayerSP.getRidingEntity() instanceof mcheli.plane.MCP_EntityPlane || entityPlayerSP
      .getRidingEntity() instanceof mcheli.tank.MCH_EntityTank) {
      ac = (MCH_EntityAircraft)entityPlayerSP.getRidingEntity();
    } else if (entityPlayerSP.getRidingEntity() instanceof MCH_EntityUavStation) {
      ac = ((MCH_EntityUavStation)entityPlayerSP.getRidingEntity()).getControlAircract();
    } else if (entityPlayerSP.getRidingEntity() instanceof mcheli.vehicle.MCH_EntityVehicle) {
      MCH_EntityAircraft vehicle = (MCH_EntityAircraft)entityPlayerSP.getRidingEntity();
      vehicle.setupAllRiderRenderPosition(partialTicks, (EntityPlayer)entityPlayerSP);
    } 
    boolean stickMode = false;
    if (ac instanceof mcheli.helicopter.MCH_EntityHeli)
      stickMode = MCH_Config.MouseControlStickModeHeli.prmBool; 
    if (ac instanceof mcheli.plane.MCP_EntityPlane)
      stickMode = MCH_Config.MouseControlStickModePlane.prmBool; 
    for (int i = 0; i < 10 && prevTick > partialTicks; i++)
      prevTick--; 
    if (ac != null && ac.canMouseRot()) {
      if (!isRideAircraft)
        ac.onInteractFirst((EntityPlayer)entityPlayerSP); 
      isRideAircraft = true;
      updateMouseDelta(stickMode, partialTicks);
      boolean fixRot = false;
      float fixYaw = 0.0F;
      float fixPitch = 0.0F;
      MCH_SeatInfo seatInfo = ac.getSeatInfo((Entity)entityPlayerSP);
      if (seatInfo != null && seatInfo.fixRot && ac.getIsGunnerMode((Entity)entityPlayerSP) && !ac.isGunnerLookMode((EntityPlayer)entityPlayerSP)) {
        fixRot = true;
        fixYaw = seatInfo.fixYaw;
        fixPitch = seatInfo.fixPitch;
        mouseRollDeltaX *= 0.0D;
        mouseRollDeltaY *= 0.0D;
        mouseDeltaX *= 0.0D;
        mouseDeltaY *= 0.0D;
      } else if (ac.isPilot((Entity)entityPlayerSP)) {
        MCH_AircraftInfo.CameraPosition cp = ac.getCameraPosInfo();
        if (cp != null) {
          fixYaw = cp.yaw;
          fixPitch = cp.pitch;
        } 
      } 
      if (ac.getAcInfo() == null) {
        entityPlayerSP.turn((float)mouseDeltaX, (float)mouseDeltaY);
      } else {
        ac.setAngles((Entity)entityPlayerSP, fixRot, fixYaw, fixPitch, (float)(mouseDeltaX + prevMouseDeltaX) / 2.0F, (float)(mouseDeltaY + prevMouseDeltaY) / 2.0F, (float)mouseRollDeltaX, (float)mouseRollDeltaY, partialTicks - prevTick);
      } 
      ac.setupAllRiderRenderPosition(partialTicks, (EntityPlayer)entityPlayerSP);
      double dist = MathHelper.sqrt(mouseRollDeltaX * mouseRollDeltaX + mouseRollDeltaY * mouseRollDeltaY);
      if (!stickMode || dist < getMaxStickLength() * 0.1D) {
        mouseRollDeltaX *= 0.95D;
        mouseRollDeltaY *= 0.95D;
      } 
      float roll = MathHelper.wrapDegrees(ac.getRotRoll());
      float yaw = MathHelper.wrapDegrees(ac.getRotYaw() - ((EntityPlayer)entityPlayerSP).rotationYaw);
      roll *= MathHelper.cos((float)(yaw * Math.PI / 180.0D));
      if (ac.getTVMissile() != null && W_Lib.isClientPlayer((ac.getTVMissile()).shootingEntity) && ac
        .getIsGunnerMode((Entity)entityPlayerSP))
        roll = 0.0F; 
      W_Reflection.setCameraRoll(roll);
      correctViewEntityDummy((Entity)entityPlayerSP);
    } else {
      MCH_EntitySeat seat = (entityPlayerSP.getRidingEntity() instanceof MCH_EntitySeat) ? (MCH_EntitySeat)entityPlayerSP.getRidingEntity() : null;
      if (seat != null && seat.getParent() != null) {
        updateMouseDelta(stickMode, partialTicks);
        ac = seat.getParent();
        boolean fixRot = false;
        MCH_SeatInfo seatInfo = ac.getSeatInfo((Entity)entityPlayerSP);
        if (seatInfo != null && seatInfo.fixRot && ac.getIsGunnerMode((Entity)entityPlayerSP) && !ac.isGunnerLookMode((EntityPlayer)entityPlayerSP)) {
          fixRot = true;
          mouseRollDeltaX *= 0.0D;
          mouseRollDeltaY *= 0.0D;
          mouseDeltaX *= 0.0D;
          mouseDeltaY *= 0.0D;
        } 
        Vec3d v = new Vec3d(mouseDeltaX, mouseRollDeltaY, 0.0D);
        v = W_Vec3.rotateRoll((float)((ac.calcRotRoll(partialTicks) / 180.0F) * Math.PI), v);
        MCH_WeaponSet ws = ac.getCurrentWeapon((Entity)entityPlayerSP);
        mouseDeltaY *= (ws != null && ws.getInfo() != null) ? (ws.getInfo()).cameraRotationSpeedPitch : 1.0D;
        entityPlayerSP.turn((float)mouseDeltaX, (float)mouseDeltaY);
        float y = ac.getRotYaw();
        float p = ac.getRotPitch();
        float r = ac.getRotRoll();
        ac.setRotYaw(ac.calcRotYaw(partialTicks));
        ac.setRotPitch(ac.calcRotPitch(partialTicks));
        ac.setRotRoll(ac.calcRotRoll(partialTicks));
        float revRoll = 0.0F;
        if (fixRot) {
          ((EntityPlayer)entityPlayerSP).rotationYaw = ac.getRotYaw() + seatInfo.fixYaw;
          ((EntityPlayer)entityPlayerSP).rotationPitch = ac.getRotPitch() + seatInfo.fixPitch;
          if (((EntityPlayer)entityPlayerSP).rotationPitch > 90.0F) {
            ((EntityPlayer)entityPlayerSP).prevRotationPitch -= (((EntityPlayer)entityPlayerSP).rotationPitch - 90.0F) * 2.0F;
            ((EntityPlayer)entityPlayerSP).rotationPitch -= (((EntityPlayer)entityPlayerSP).rotationPitch - 90.0F) * 2.0F;
            ((EntityPlayer)entityPlayerSP).prevRotationYaw += 180.0F;
            ((EntityPlayer)entityPlayerSP).rotationYaw += 180.0F;
            revRoll = 180.0F;
          } else if (((EntityPlayer)entityPlayerSP).rotationPitch < -90.0F) {
            ((EntityPlayer)entityPlayerSP).prevRotationPitch -= (((EntityPlayer)entityPlayerSP).rotationPitch - 90.0F) * 2.0F;
            ((EntityPlayer)entityPlayerSP).rotationPitch -= (((EntityPlayer)entityPlayerSP).rotationPitch - 90.0F) * 2.0F;
            ((EntityPlayer)entityPlayerSP).prevRotationYaw += 180.0F;
            ((EntityPlayer)entityPlayerSP).rotationYaw += 180.0F;
            revRoll = 180.0F;
          } 
        } 
        ac.setupAllRiderRenderPosition(partialTicks, (EntityPlayer)entityPlayerSP);
        ac.setRotYaw(y);
        ac.setRotPitch(p);
        ac.setRotRoll(r);
        mouseRollDeltaX *= 0.9D;
        mouseRollDeltaY *= 0.9D;
        float roll = MathHelper.wrapDegrees(ac.getRotRoll());
        float yaw = MathHelper.wrapDegrees(ac.getRotYaw() - ((EntityPlayer)entityPlayerSP).rotationYaw);
        roll *= MathHelper.cos((float)(yaw * Math.PI / 180.0D));
        if (ac.getTVMissile() != null && W_Lib.isClientPlayer((ac.getTVMissile()).shootingEntity) && ac
          .getIsGunnerMode((Entity)entityPlayerSP))
          roll = 0.0F; 
        W_Reflection.setCameraRoll(roll + revRoll);
        correctViewEntityDummy((Entity)entityPlayerSP);
      } else {
        if (isRideAircraft) {
          W_Reflection.setCameraRoll(0.0F);
          isRideAircraft = false;
        } 
        mouseRollDeltaX = 0.0D;
        mouseRollDeltaY = 0.0D;
      } 
    } 
    if (ac != null) {
      if (ac.getSeatIdByEntity((Entity)entityPlayerSP) == 0 && !ac.isDestroyed()) {
        ac.lastRiderYaw = ((EntityPlayer)entityPlayerSP).rotationYaw;
        ac.prevLastRiderYaw = ((EntityPlayer)entityPlayerSP).prevRotationYaw;
        ac.lastRiderPitch = ((EntityPlayer)entityPlayerSP).rotationPitch;
        ac.prevLastRiderPitch = ((EntityPlayer)entityPlayerSP).prevRotationPitch;
      } 
      ac.updateWeaponsRotation();
    } 
    MCH_ViewEntityDummy mCH_ViewEntityDummy = MCH_ViewEntityDummy.getInstance(((EntityPlayer)entityPlayerSP).world);
    if (mCH_ViewEntityDummy != null) {
      ((Entity)mCH_ViewEntityDummy).rotationYaw = ((EntityPlayer)entityPlayerSP).rotationYaw;
      ((Entity)mCH_ViewEntityDummy).prevRotationYaw = ((EntityPlayer)entityPlayerSP).prevRotationYaw;
      if (ac != null) {
        MCH_WeaponSet wi = ac.getCurrentWeapon((Entity)entityPlayerSP);
        if (wi != null && wi.getInfo() != null && (wi.getInfo()).fixCameraPitch)
          ((Entity)mCH_ViewEntityDummy).rotationPitch = ((Entity)mCH_ViewEntityDummy).prevRotationPitch = 0.0F; 
      } 
    } 
    prevTick = partialTicks;
  }
  
  public void correctViewEntityDummy(Entity entity) {
    MCH_ViewEntityDummy mCH_ViewEntityDummy = MCH_ViewEntityDummy.getInstance(entity.world);
    if (mCH_ViewEntityDummy != null)
      if (((Entity)mCH_ViewEntityDummy).rotationYaw - ((Entity)mCH_ViewEntityDummy).prevRotationYaw > 180.0F) {
        ((Entity)mCH_ViewEntityDummy).prevRotationYaw += 360.0F;
      } else if (((Entity)mCH_ViewEntityDummy).rotationYaw - ((Entity)mCH_ViewEntityDummy).prevRotationYaw < -180.0F) {
        ((Entity)mCH_ViewEntityDummy).prevRotationYaw -= 360.0F;
      }  
  }
  
  public void onPlayerTickPre(EntityPlayer player) {
    if (player.world.isRemote) {
      ItemStack currentItemstack = player.getHeldItem(EnumHand.MAIN_HAND);
      if (!currentItemstack.isEmpty() && currentItemstack.getItem() instanceof mcheli.tool.MCH_ItemWrench)
        if (player.getItemInUseCount() > 0 && player.getActiveItemStack() != currentItemstack) {
          int maxdm = currentItemstack.getMaxDamage();
          int dm = currentItemstack.getMetadata();
          if (dm <= maxdm && dm > 0)
            player.setActiveHand(EnumHand.MAIN_HAND); 
        }  
    } 
  }
  
  public void onPlayerTickPost(EntityPlayer player) {}
  
  public void onRenderTickPost(float partialTicks) {
    if (this.mc.player != null) {
      MCH_ClientTickHandlerBase.applyRotLimit((Entity)this.mc.player);
      MCH_ViewEntityDummy mCH_ViewEntityDummy = MCH_ViewEntityDummy.getInstance(this.mc.player.world);
      if (mCH_ViewEntityDummy != null) {
        ((Entity)mCH_ViewEntityDummy).rotationPitch = this.mc.player.rotationPitch;
        ((Entity)mCH_ViewEntityDummy).rotationYaw = this.mc.player.rotationYaw;
        ((Entity)mCH_ViewEntityDummy).prevRotationPitch = this.mc.player.prevRotationPitch;
        ((Entity)mCH_ViewEntityDummy).prevRotationYaw = this.mc.player.prevRotationYaw;
      } 
    } 
    if (this.mc.currentScreen == null || this.mc.currentScreen instanceof net.minecraft.client.gui.GuiChat || this.mc.currentScreen
      .getClass().toString().indexOf("GuiDriveableController") >= 0) {
      for (MCH_Gui gui : this.guis) {
        if (drawGui(gui, partialTicks))
          break; 
      } 
      drawGui((MCH_Gui)this.gui_Common, partialTicks);
      drawGui(this.gui_Wrench, partialTicks);
      drawGui(this.gui_SwnGnr, partialTicks);
      drawGui(this.gui_EMarker, partialTicks);
      if (isDrawScoreboard)
        MCH_GuiScoreboard.drawList(this.mc, this.mc.fontRenderer, false); 
      drawGui(this.gui_Title, partialTicks);
    } 
  }
  
  public boolean drawGui(MCH_Gui gui, float partialTicks) {
    if (gui.isDrawGui((EntityPlayer)this.mc.player)) {
      gui.drawScreen(0, 0, partialTicks);
      return true;
    } 
    return false;
  }
}
