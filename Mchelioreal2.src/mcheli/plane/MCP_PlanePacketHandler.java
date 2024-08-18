package mcheli.plane;

import com.google.common.io.ByteArrayDataInput;
import mcheli.__helper.network.HandleSide;
import mcheli.aircraft.MCH_EntityAircraft;
import mcheli.aircraft.MCH_EntitySeat;
import mcheli.uav.MCH_EntityUavStation;
import mcheli.weapon.MCH_WeaponParam;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.fml.relauncher.Side;

public class MCP_PlanePacketHandler {
  @HandleSide({Side.SERVER})
  public static void onPacket_PlayerControl(EntityPlayer player, ByteArrayDataInput data, IThreadListener scheduler) {
    if (player.field_70170_p.field_72995_K)
      return; 
    MCP_PlanePacketPlayerControl pc = new MCP_PlanePacketPlayerControl();
    pc.readData(data);
    scheduler.func_152344_a(() -> {
          MCP_EntityPlane plane = null;
          if (player.func_184187_bx() instanceof MCP_EntityPlane) {
            plane = (MCP_EntityPlane)player.func_184187_bx();
          } else if (player.func_184187_bx() instanceof MCH_EntitySeat) {
            if (((MCH_EntitySeat)player.func_184187_bx()).getParent() instanceof MCP_EntityPlane)
              plane = (MCP_EntityPlane)((MCH_EntitySeat)player.func_184187_bx()).getParent(); 
          } else if (player.func_184187_bx() instanceof MCH_EntityUavStation) {
            MCH_EntityUavStation uavStation = (MCH_EntityUavStation)player.func_184187_bx();
            if (uavStation.getControlAircract() instanceof MCP_EntityPlane)
              plane = (MCP_EntityPlane)uavStation.getControlAircract(); 
          } 
          if (plane == null)
            return; 
          MCH_EntityAircraft ac = plane;
          if (pc.isUnmount == 1) {
            ac.unmountEntity();
          } else if (pc.isUnmount == 2) {
            ac.unmountCrew();
          } else if (pc.ejectSeat) {
            ac.ejectSeat((Entity)player);
          } else {
            if (pc.switchVtol == 0)
              plane.swithVtolMode(false); 
            if (pc.switchVtol == 1)
              plane.swithVtolMode(true); 
            if (pc.switchMode == 0)
              ac.switchGunnerMode(false); 
            if (pc.switchMode == 1)
              ac.switchGunnerMode(true); 
            if (pc.switchMode == 2)
              ac.switchHoveringMode(false); 
            if (pc.switchMode == 3)
              ac.switchHoveringMode(true); 
            if (pc.switchSearchLight)
              ac.setSearchLight(!ac.isSearchLightON()); 
            if (pc.switchCameraMode > 0)
              ac.switchCameraMode(player, pc.switchCameraMode - 1); 
            if (pc.switchWeapon >= 0)
              ac.switchWeapon((Entity)player, pc.switchWeapon); 
            if (pc.useWeapon) {
              MCH_WeaponParam prm = new MCH_WeaponParam();
              prm.entity = (Entity)plane;
              prm.user = (Entity)player;
              prm.setPosAndRot(pc.useWeaponPosX, pc.useWeaponPosY, pc.useWeaponPosZ, 0.0F, 0.0F);
              prm.option1 = pc.useWeaponOption1;
              prm.option2 = pc.useWeaponOption2;
              ac.useCurrentWeapon(prm);
            } 
            if (ac.isPilot((Entity)player)) {
              ac.throttleUp = pc.throttleUp;
              ac.throttleDown = pc.throttleDown;
              ac.moveLeft = pc.moveLeft;
              ac.moveRight = pc.moveRight;
            } 
            if (pc.useFlareType > 0)
              ac.useFlare(pc.useFlareType); 
            if (pc.openGui)
              ac.openGui(player); 
            if (pc.switchHatch > 0)
              if (ac.getAcInfo().haveHatch()) {
                ac.foldHatch((pc.switchHatch == 2));
              } else {
                plane.foldWing((pc.switchHatch == 2));
              }  
            if (pc.switchFreeLook > 0)
              ac.switchFreeLookMode((pc.switchFreeLook == 1)); 
            if (pc.switchGear == 1)
              ac.foldLandingGear(); 
            if (pc.switchGear == 2)
              ac.unfoldLandingGear(); 
            if (pc.putDownRack == 1)
              ac.mountEntityToRack(); 
            if (pc.putDownRack == 2)
              ac.unmountEntityFromRack(); 
            if (pc.putDownRack == 3)
              ac.rideRack(); 
            if (pc.isUnmount == 3)
              ac.unmountAircraft(); 
            if (pc.switchGunnerStatus)
              ac.setGunnerStatus(!ac.getGunnerStatus()); 
          } 
        });
  }
}
