package mcheli.plane;

import mcheli.MCH_Config;
import mcheli.MCH_Key;
import mcheli.MCH_Lib;
import mcheli.MCH_ViewEntityDummy;
import mcheli.aircraft.MCH_AircraftClientTickHandler;
import mcheli.aircraft.MCH_EntityAircraft;
import mcheli.aircraft.MCH_EntitySeat;
import mcheli.aircraft.MCH_SeatInfo;
import mcheli.uav.MCH_EntityUavStation;
import mcheli.wrapper.W_Network;
import mcheli.wrapper.W_PacketBase;
import mcheli.wrapper.W_Reflection;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class MCP_ClientPlaneTickHandler extends MCH_AircraftClientTickHandler {
  public MCH_Key KeySwitchMode;
  
  public MCH_Key KeyEjectSeat;
  
  public MCH_Key KeyZoom;
  
  public MCH_Key[] Keys;
  
  public MCP_ClientPlaneTickHandler(Minecraft minecraft, MCH_Config config) {
    super(minecraft, config);
    updateKeybind(config);
  }
  
  public void updateKeybind(MCH_Config config) {
    super.updateKeybind(config);
    this.KeySwitchMode = new MCH_Key(MCH_Config.KeySwitchMode.prmInt);
    this.KeyEjectSeat = new MCH_Key(MCH_Config.KeySwitchHovering.prmInt);
    this.KeyZoom = new MCH_Key(MCH_Config.KeyZoom.prmInt);
    this.Keys = new MCH_Key[] { 
        this.KeyUp, this.KeyDown, this.KeyRight, this.KeyLeft, this.KeySwitchMode, this.KeyEjectSeat, this.KeyUseWeapon, this.KeySwWeaponMode, this.KeySwitchWeapon1, this.KeySwitchWeapon2, 
        this.KeyZoom, this.KeyCameraMode, this.KeyUnmount, this.KeyUnmountForce, this.KeyFlare, this.KeyExtra, this.KeyFreeLook, this.KeyGUI, this.KeyGearUpDown, this.KeyPutToRack, 
        this.KeyDownFromRack };
  }
  
  protected void update(EntityPlayer player, MCP_EntityPlane plane) {
    if (plane.getIsGunnerMode((Entity)player)) {
      MCH_SeatInfo seatInfo = plane.getSeatInfo((Entity)player);
      if (seatInfo != null)
        setRotLimitPitch(seatInfo.minPitch, seatInfo.maxPitch, (Entity)player); 
    } 
    plane.updateRadar(10);
    plane.updateCameraRotate(player.field_70177_z, player.field_70125_A);
  }
  
  protected void onTick(boolean inGUI) {
    for (MCH_Key k : this.Keys)
      k.update(); 
    this.isBeforeRiding = this.isRiding;
    EntityPlayerSP entityPlayerSP = this.mc.field_71439_g;
    MCP_EntityPlane plane = null;
    boolean isPilot = true;
    if (entityPlayerSP != null)
      if (entityPlayerSP.func_184187_bx() instanceof MCP_EntityPlane) {
        plane = (MCP_EntityPlane)entityPlayerSP.func_184187_bx();
      } else if (entityPlayerSP.func_184187_bx() instanceof MCH_EntitySeat) {
        MCH_EntitySeat seat = (MCH_EntitySeat)entityPlayerSP.func_184187_bx();
        if (seat.getParent() instanceof MCP_EntityPlane) {
          isPilot = false;
          plane = (MCP_EntityPlane)seat.getParent();
        } 
      } else if (entityPlayerSP.func_184187_bx() instanceof MCH_EntityUavStation) {
        MCH_EntityUavStation uavStation = (MCH_EntityUavStation)entityPlayerSP.func_184187_bx();
        if (uavStation.getControlAircract() instanceof MCP_EntityPlane)
          plane = (MCP_EntityPlane)uavStation.getControlAircract(); 
      }  
    if (plane != null && plane.getAcInfo() != null) {
      update((EntityPlayer)entityPlayerSP, plane);
      MCH_ViewEntityDummy viewEntityDummy = MCH_ViewEntityDummy.getInstance((World)this.mc.field_71441_e);
      viewEntityDummy.update(plane.camera);
      if (!inGUI) {
        if (!plane.isDestroyed())
          playerControl((EntityPlayer)entityPlayerSP, plane, isPilot); 
      } else {
        playerControlInGUI((EntityPlayer)entityPlayerSP, plane, isPilot);
      } 
      boolean hideHand = true;
      if ((isPilot && plane.isAlwaysCameraView()) || plane.getIsGunnerMode((Entity)entityPlayerSP) || plane.getCameraId() > 0) {
        MCH_Lib.setRenderViewEntity((EntityLivingBase)viewEntityDummy);
      } else {
        MCH_Lib.setRenderViewEntity((EntityLivingBase)entityPlayerSP);
        if (!isPilot && plane.getCurrentWeaponID((Entity)entityPlayerSP) < 0)
          hideHand = false; 
      } 
      if (hideHand)
        MCH_Lib.disableFirstPersonItemRender(entityPlayerSP.func_184614_ca()); 
      this.isRiding = true;
    } else {
      this.isRiding = false;
    } 
    if (!this.isBeforeRiding && this.isRiding && plane != null) {
      W_Reflection.setThirdPersonDistance(plane.thirdPersonDist);
      MCH_ViewEntityDummy.getInstance((World)this.mc.field_71441_e).func_70107_b(plane.field_70165_t, plane.field_70163_u + 0.5D, plane.field_70161_v);
    } else if (this.isBeforeRiding && !this.isRiding) {
      W_Reflection.restoreDefaultThirdPersonDistance();
      MCH_Lib.enableFirstPersonItemRender();
      MCH_Lib.setRenderViewEntity((EntityLivingBase)entityPlayerSP);
      W_Reflection.setCameraRoll(0.0F);
    } 
  }
  
  protected void playerControlInGUI(EntityPlayer player, MCP_EntityPlane plane, boolean isPilot) {
    commonPlayerControlInGUI(player, plane, isPilot, new MCP_PlanePacketPlayerControl());
  }
  
  protected void playerControl(EntityPlayer player, MCP_EntityPlane plane, boolean isPilot) {
    MCP_PlanePacketPlayerControl pc = new MCP_PlanePacketPlayerControl();
    boolean send = false;
    MCH_EntityAircraft ac = plane;
    send = commonPlayerControl(player, plane, isPilot, pc);
    if (isPilot) {
      if (this.KeySwitchMode.isKeyDown())
        if (ac.getIsGunnerMode((Entity)player) && ac.canSwitchCameraPos()) {
          pc.switchMode = 0;
          ac.switchGunnerMode(false);
          send = true;
          ac.setCameraId(1);
        } else if (ac.getCameraId() > 0) {
          ac.setCameraId(ac.getCameraId() + 1);
          if (ac.getCameraId() >= ac.getCameraPosNum())
            ac.setCameraId(0); 
        } else if (ac.canSwitchGunnerMode()) {
          pc.switchMode = (byte)(ac.getIsGunnerMode((Entity)player) ? 0 : 1);
          ac.switchGunnerMode(!ac.getIsGunnerMode((Entity)player));
          send = true;
          ac.setCameraId(0);
        } else if (ac.canSwitchCameraPos()) {
          ac.setCameraId(1);
        } else {
          playSoundNG();
        }  
      if (this.KeyExtra.isKeyDown())
        if (plane.canSwitchVtol()) {
          boolean currentMode = plane.getNozzleStat();
          if (!currentMode) {
            pc.switchVtol = 1;
          } else {
            pc.switchVtol = 0;
          } 
          plane.swithVtolMode(!currentMode);
          send = true;
        } else {
          playSoundNG();
        }  
    } else if (this.KeySwitchMode.isKeyDown()) {
      if (plane.canSwitchGunnerModeOtherSeat(player)) {
        plane.switchGunnerModeOtherSeat(player);
        send = true;
      } else {
        playSoundNG();
      } 
    } 
    if (this.KeyZoom.isKeyDown()) {
      boolean isUav = (plane.isUAV() && !plane.getAcInfo().haveHatch() && !plane.getPlaneInfo().haveWing());
      if (plane.getIsGunnerMode((Entity)player) || isUav) {
        plane.zoomCamera();
        playSound("zoom", 0.5F, 1.0F);
      } else if (isPilot) {
        if (plane.getAcInfo().haveHatch()) {
          if (plane.canFoldHatch()) {
            pc.switchHatch = 2;
            send = true;
          } else if (plane.canUnfoldHatch()) {
            pc.switchHatch = 1;
            send = true;
          } 
        } else if (plane.canFoldWing()) {
          pc.switchHatch = 2;
          send = true;
        } else if (plane.canUnfoldWing()) {
          pc.switchHatch = 1;
          send = true;
        } 
      } 
    } 
    if (this.KeyEjectSeat.isKeyDown() && plane.canEjectSeat((Entity)player)) {
      pc.ejectSeat = true;
      send = true;
    } 
    if (send)
      W_Network.sendToServer((W_PacketBase)pc); 
  }
}
