package mcheli.vehicle;

import mcheli.MCH_Config;
import mcheli.MCH_Key;
import mcheli.MCH_Lib;
import mcheli.MCH_ViewEntityDummy;
import mcheli.aircraft.MCH_AircraftClientTickHandler;
import mcheli.aircraft.MCH_EntitySeat;
import mcheli.wrapper.W_Entity;
import mcheli.wrapper.W_Network;
import mcheli.wrapper.W_PacketBase;
import mcheli.wrapper.W_Reflection;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class MCH_ClientVehicleTickHandler extends MCH_AircraftClientTickHandler {
  public MCH_Key KeySwitchMode;
  
  public MCH_Key KeySwitchHovering;
  
  public MCH_Key KeyZoom;
  
  public MCH_Key KeyExtra;
  
  public MCH_Key[] Keys;
  
  public MCH_ClientVehicleTickHandler(Minecraft minecraft, MCH_Config config) {
    super(minecraft, config);
    updateKeybind(config);
  }
  
  public void updateKeybind(MCH_Config config) {
    super.updateKeybind(config);
    this.KeySwitchMode = new MCH_Key(MCH_Config.KeySwitchMode.prmInt);
    this.KeySwitchHovering = new MCH_Key(MCH_Config.KeySwitchHovering.prmInt);
    this.KeyZoom = new MCH_Key(MCH_Config.KeyZoom.prmInt);
    this.KeyExtra = new MCH_Key(MCH_Config.KeyExtra.prmInt);
    this.Keys = new MCH_Key[] { 
        this.KeyUp, this.KeyDown, this.KeyRight, this.KeyLeft, this.KeySwitchMode, this.KeySwitchHovering, this.KeyUseWeapon, this.KeySwWeaponMode, this.KeySwitchWeapon1, this.KeySwitchWeapon2, 
        this.KeyZoom, this.KeyCameraMode, this.KeyUnmount, this.KeyUnmountForce, this.KeyFlare, this.KeyExtra, this.KeyGUI };
  }
  
  protected void update(EntityPlayer player, MCH_EntityVehicle vehicle, MCH_VehicleInfo info) {
    if (info != null)
      setRotLimitPitch(info.minRotationPitch, info.maxRotationPitch, (Entity)player); 
    vehicle.updateCameraRotate(player.field_70177_z, player.field_70125_A);
    vehicle.updateRadar(5);
  }
  
  protected void onTick(boolean inGUI) {
    for (MCH_Key k : this.Keys)
      k.update(); 
    this.isBeforeRiding = this.isRiding;
    EntityPlayerSP entityPlayerSP = this.mc.field_71439_g;
    MCH_EntityVehicle vehicle = null;
    boolean isPilot = true;
    if (entityPlayerSP != null)
      if (entityPlayerSP.func_184187_bx() instanceof MCH_EntityVehicle) {
        vehicle = (MCH_EntityVehicle)entityPlayerSP.func_184187_bx();
      } else if (entityPlayerSP.func_184187_bx() instanceof MCH_EntitySeat) {
        MCH_EntitySeat seat = (MCH_EntitySeat)entityPlayerSP.func_184187_bx();
        if (seat.getParent() instanceof MCH_EntityVehicle) {
          isPilot = false;
          vehicle = (MCH_EntityVehicle)seat.getParent();
        } 
      }  
    if (vehicle != null && vehicle.getAcInfo() != null) {
      MCH_Lib.disableFirstPersonItemRender(entityPlayerSP.func_184614_ca());
      update((EntityPlayer)entityPlayerSP, vehicle, vehicle.getVehicleInfo());
      MCH_ViewEntityDummy viewEntityDummy = MCH_ViewEntityDummy.getInstance((World)this.mc.field_71441_e);
      viewEntityDummy.update(vehicle.camera);
      if (!inGUI) {
        if (!vehicle.isDestroyed())
          playerControl((EntityPlayer)entityPlayerSP, vehicle, isPilot); 
      } else {
        playerControlInGUI((EntityPlayer)entityPlayerSP, vehicle, isPilot);
      } 
      MCH_Lib.setRenderViewEntity((EntityLivingBase)viewEntityDummy);
      this.isRiding = true;
    } else {
      this.isRiding = false;
    } 
    if (!this.isBeforeRiding && this.isRiding) {
      W_Reflection.setThirdPersonDistance(vehicle.thirdPersonDist);
    } else if (this.isBeforeRiding && !this.isRiding) {
      W_Reflection.restoreDefaultThirdPersonDistance();
      MCH_Lib.enableFirstPersonItemRender();
      MCH_Lib.setRenderViewEntity((EntityLivingBase)entityPlayerSP);
    } 
  }
  
  protected void playerControlInGUI(EntityPlayer player, MCH_EntityVehicle vehicle, boolean isPilot) {
    commonPlayerControlInGUI(player, vehicle, isPilot, new MCH_PacketVehiclePlayerControl());
  }
  
  protected void playerControl(EntityPlayer player, MCH_EntityVehicle vehicle, boolean isPilot) {
    MCH_PacketVehiclePlayerControl pc = new MCH_PacketVehiclePlayerControl();
    boolean send = false;
    send = commonPlayerControl(player, vehicle, isPilot, pc);
    if (this.KeyExtra.isKeyDown())
      if (vehicle.getTowChainEntity() != null) {
        playSoundOK();
        pc.unhitchChainId = W_Entity.getEntityId((Entity)vehicle.getTowChainEntity());
        send = true;
      } else {
        playSoundNG();
      }  
    if (!this.KeySwitchHovering.isKeyDown())
      if (this.KeySwitchMode.isKeyDown()); 
    if (this.KeyZoom.isKeyDown())
      if (vehicle.canZoom()) {
        vehicle.zoomCamera();
        playSound("zoom", 0.5F, 1.0F);
      } else if (vehicle.getAcInfo().haveHatch()) {
        if (vehicle.canFoldHatch()) {
          pc.switchHatch = 2;
          send = true;
        } else if (vehicle.canUnfoldHatch()) {
          pc.switchHatch = 1;
          send = true;
        } else {
          playSoundNG();
        } 
      }  
    if (send)
      W_Network.sendToServer((W_PacketBase)pc); 
  }
}
