package mcheli.tank;

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

public class MCH_ClientTankTickHandler extends MCH_AircraftClientTickHandler {
  public MCH_Key KeySwitchMode;
  
  public MCH_Key KeyZoom;
  
  public MCH_Key[] Keys;
  
  public MCH_ClientTankTickHandler(Minecraft minecraft, MCH_Config config) {
    super(minecraft, config);
    updateKeybind(config);
  }
  
  public void updateKeybind(MCH_Config config) {
    super.updateKeybind(config);
    this.KeySwitchMode = new MCH_Key(MCH_Config.KeySwitchMode.prmInt);
    this.KeyZoom = new MCH_Key(MCH_Config.KeyZoom.prmInt);
    this.Keys = new MCH_Key[] { 
        this.KeyUp, this.KeyDown, this.KeyRight, this.KeyLeft, this.KeySwitchMode, this.KeyUseWeapon, this.KeySwWeaponMode, this.KeySwitchWeapon1, this.KeySwitchWeapon2, this.KeyZoom, 
        this.KeyCameraMode, this.KeyUnmount, this.KeyUnmountForce, this.KeyFlare, this.KeyExtra, this.KeyFreeLook, this.KeyGUI, this.KeyGearUpDown, this.KeyBrake, this.KeyPutToRack, 
        this.KeyDownFromRack };
  }
  
  protected void update(EntityPlayer player, MCH_EntityTank tank) {
    if (tank.getIsGunnerMode((Entity)player)) {
      MCH_SeatInfo seatInfo = tank.getSeatInfo((Entity)player);
      if (seatInfo != null)
        setRotLimitPitch(seatInfo.minPitch, seatInfo.maxPitch, (Entity)player); 
    } 
    tank.updateRadar(10);
    tank.updateCameraRotate(player.field_70177_z, player.field_70125_A);
  }
  
  protected void onTick(boolean inGUI) {
    for (MCH_Key k : this.Keys)
      k.update(); 
    this.isBeforeRiding = this.isRiding;
    EntityPlayerSP entityPlayerSP = this.mc.field_71439_g;
    MCH_EntityTank tank = null;
    boolean isPilot = true;
    if (entityPlayerSP != null)
      if (entityPlayerSP.func_184187_bx() instanceof MCH_EntityTank) {
        tank = (MCH_EntityTank)entityPlayerSP.func_184187_bx();
      } else if (entityPlayerSP.func_184187_bx() instanceof MCH_EntitySeat) {
        MCH_EntitySeat seat = (MCH_EntitySeat)entityPlayerSP.func_184187_bx();
        if (seat.getParent() instanceof MCH_EntityTank) {
          isPilot = false;
          tank = (MCH_EntityTank)seat.getParent();
        } 
      } else if (entityPlayerSP.func_184187_bx() instanceof MCH_EntityUavStation) {
        MCH_EntityUavStation uavStation = (MCH_EntityUavStation)entityPlayerSP.func_184187_bx();
        if (uavStation.getControlAircract() instanceof MCH_EntityTank)
          tank = (MCH_EntityTank)uavStation.getControlAircract(); 
      }  
    if (tank != null && tank.getAcInfo() != null) {
      update((EntityPlayer)entityPlayerSP, tank);
      MCH_ViewEntityDummy viewEntityDummy = MCH_ViewEntityDummy.getInstance((World)this.mc.field_71441_e);
      viewEntityDummy.update(tank.camera);
      if (!inGUI) {
        if (!tank.isDestroyed())
          playerControl((EntityPlayer)entityPlayerSP, tank, isPilot); 
      } else {
        playerControlInGUI((EntityPlayer)entityPlayerSP, tank, isPilot);
      } 
      boolean hideHand = true;
      if ((isPilot && tank.isAlwaysCameraView()) || tank.getIsGunnerMode((Entity)entityPlayerSP) || tank.getCameraId() > 0) {
        MCH_Lib.setRenderViewEntity((EntityLivingBase)viewEntityDummy);
      } else {
        MCH_Lib.setRenderViewEntity((EntityLivingBase)entityPlayerSP);
        if (!isPilot && tank.getCurrentWeaponID((Entity)entityPlayerSP) < 0)
          hideHand = false; 
      } 
      if (hideHand)
        MCH_Lib.disableFirstPersonItemRender(entityPlayerSP.func_184614_ca()); 
      this.isRiding = true;
    } else {
      this.isRiding = false;
    } 
    if (!this.isBeforeRiding && this.isRiding && tank != null) {
      W_Reflection.setThirdPersonDistance(tank.thirdPersonDist);
      MCH_ViewEntityDummy.getInstance((World)this.mc.field_71441_e).func_70107_b(tank.field_70165_t, tank.field_70163_u + 0.5D, tank.field_70161_v);
    } else if (this.isBeforeRiding && !this.isRiding) {
      W_Reflection.restoreDefaultThirdPersonDistance();
      MCH_Lib.enableFirstPersonItemRender();
      MCH_Lib.setRenderViewEntity((EntityLivingBase)entityPlayerSP);
      W_Reflection.setCameraRoll(0.0F);
    } 
  }
  
  protected void playerControlInGUI(EntityPlayer player, MCH_EntityTank tank, boolean isPilot) {
    commonPlayerControlInGUI(player, tank, isPilot, new MCH_TankPacketPlayerControl());
  }
  
  protected void playerControl(EntityPlayer player, MCH_EntityTank tank, boolean isPilot) {
    MCH_TankPacketPlayerControl pc = new MCH_TankPacketPlayerControl();
    boolean send = false;
    MCH_EntityAircraft ac = tank;
    send = commonPlayerControl(player, tank, isPilot, pc);
    if ((ac.getAcInfo()).defaultFreelook && pc.switchFreeLook > 0)
      pc.switchFreeLook = 0; 
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
    } else if (this.KeySwitchMode.isKeyDown()) {
      if (tank.canSwitchGunnerModeOtherSeat(player)) {
        tank.switchGunnerModeOtherSeat(player);
        send = true;
      } else {
        playSoundNG();
      } 
    } 
    if (this.KeyZoom.isKeyDown()) {
      boolean isUav = (tank.isUAV() && !tank.getAcInfo().haveHatch());
      if (tank.getIsGunnerMode((Entity)player) || isUav) {
        tank.zoomCamera();
        playSound("zoom", 0.5F, 1.0F);
      } else if (isPilot) {
        if (tank.getAcInfo().haveHatch())
          if (tank.canFoldHatch()) {
            pc.switchHatch = 2;
            send = true;
          } else if (tank.canUnfoldHatch()) {
            pc.switchHatch = 1;
            send = true;
          }  
      } 
    } 
    if (send)
      W_Network.sendToServer((W_PacketBase)pc); 
  }
}
