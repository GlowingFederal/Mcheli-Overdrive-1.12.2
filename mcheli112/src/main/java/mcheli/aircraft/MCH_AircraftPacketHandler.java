package mcheli.aircraft;

import com.google.common.io.ByteArrayDataInput;
import java.util.List;
import java.util.UUID;
import mcheli.MCH_Lib;
import mcheli.MCH_MOD;
import mcheli.__helper.MCH_Utils;
import mcheli.__helper.info.ContentRegistries;
import mcheli.__helper.network.HandleSide;
import mcheli.weapon.MCH_EntityTvMissile;
import mcheli.wrapper.W_Entity;
import mcheli.wrapper.W_Lib;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IThreadListener;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.Side;

public class MCH_AircraftPacketHandler {
  @HandleSide({Side.SERVER})
  public static void onPacketIndRotation(EntityPlayer player, ByteArrayDataInput data, IThreadListener scheduler) {
    if (player == null || player.world.isRemote)
      return; 
    MCH_PacketIndRotation req = new MCH_PacketIndRotation();
    req.readData(data);
    if (req.entityID_Ac <= 0)
      return; 
    scheduler.addScheduledTask(() -> {
          Entity e = player.world.getEntityByID(req.entityID_Ac);
          if (e instanceof MCH_EntityAircraft) {
            MCH_EntityAircraft ac = (MCH_EntityAircraft)e;
            ac.setRotRoll(req.roll);
            if (req.rollRev) {
              MCH_Lib.DbgLog(ac.world, "onPacketIndRotation Error:req.rollRev y=%.2f, p=%.2f, r=%.2f", new Object[] { Float.valueOf(req.yaw), Float.valueOf(req.pitch), Float.valueOf(req.roll) });
              if (ac.getRiddenByEntity() != null) {
                (ac.getRiddenByEntity()).rotationYaw = req.yaw;
                (ac.getRiddenByEntity()).prevRotationYaw = req.yaw;
              } 
              for (int sid = 0; sid < ac.getSeatNum(); sid++) {
                Entity entity = ac.getEntityBySeatId(1 + sid);
                if (entity != null)
                  entity.rotationYaw += (entity.rotationYaw <= 0.0F) ? 180.0F : -180.0F; 
              } 
            } 
            ac.setRotYaw(req.yaw);
            ac.setRotPitch(req.pitch);
          } 
        });
  }
  
  @Deprecated
  @HandleSide({Side.SERVER})
  public static void onPacketOnMountEntity(EntityPlayer player, ByteArrayDataInput data, IThreadListener scheduler) {
    if (player == null || !player.world.isRemote)
      return; 
    MCH_PacketNotifyOnMountEntity req = new MCH_PacketNotifyOnMountEntity();
    req.readData(data);
    scheduler.addScheduledTask(() -> {
          MCH_Lib.DbgLog(player.world, "onPacketOnMountEntity.rcv:%d, %d, %d, %d", new Object[] { Integer.valueOf(W_Entity.getEntityId((Entity)player)), Integer.valueOf(req.entityID_Ac), Integer.valueOf(req.entityID_rider), Integer.valueOf(req.seatID) });
          if (req.entityID_Ac <= 0)
            return; 
          if (req.entityID_rider <= 0)
            return; 
          if (req.seatID < 0)
            return; 
          Entity e = player.world.getEntityByID(req.entityID_Ac);
          if (e instanceof MCH_EntityAircraft)
            MCH_Lib.DbgLog(player.world, "onPacketOnMountEntity:" + W_Entity.getEntityId((Entity)player), new Object[0]); 
        });
  }
  
  @HandleSide({Side.CLIENT})
  public static void onPacketNotifyAmmoNum(EntityPlayer player, ByteArrayDataInput data, IThreadListener scheduler) {
    if (player == null || !player.world.isRemote)
      return; 
    MCH_PacketNotifyAmmoNum status = new MCH_PacketNotifyAmmoNum();
    status.readData(data);
    if (status.entityID_Ac <= 0)
      return; 
    scheduler.addScheduledTask(() -> {
          Entity e = player.world.getEntityByID(status.entityID_Ac);
          if (e instanceof MCH_EntityAircraft) {
            MCH_EntityAircraft ac = (MCH_EntityAircraft)e;
            String msg = "onPacketNotifyAmmoNum:";
            msg = msg + ((ac.getAcInfo() != null) ? (ac.getAcInfo()).displayName : "null") + ":";
            if (status.all) {
              msg = msg + "All=true, Num=" + status.num;
              int i = 0;
              while (i < ac.getWeaponNum() && i < status.num) {
                ac.getWeapon(i).setAmmoNum(status.ammo[i]);
                ac.getWeapon(i).setRestAllAmmoNum(status.restAmmo[i]);
                msg = msg + ", [" + status.ammo[i] + "/" + status.restAmmo[i] + "]";
                i++;
              } 
              MCH_Lib.DbgLog(e.world, msg, new Object[0]);
            } else if (status.weaponID < ac.getWeaponNum()) {
              msg = msg + "All=false, WeaponID=" + status.weaponID + ", " + status.ammo[0] + ", " + status.restAmmo[0];
              ac.getWeapon(status.weaponID).setAmmoNum(status.ammo[0]);
              ac.getWeapon(status.weaponID).setRestAllAmmoNum(status.restAmmo[0]);
              MCH_Lib.DbgLog(e.world, msg, new Object[0]);
            } else {
              MCH_Lib.DbgLog(e.world, "Error:" + status.weaponID, new Object[0]);
            } 
          } 
        });
  }
  
  @HandleSide({Side.SERVER})
  public static void onPacketStatusRequest(EntityPlayer player, ByteArrayDataInput data, IThreadListener scheduler) {
    if (player.world.isRemote)
      return; 
    MCH_PacketStatusRequest req = new MCH_PacketStatusRequest();
    req.readData(data);
    if (req.entityID_AC <= 0)
      return; 
    scheduler.addScheduledTask(() -> {
          Entity e = player.world.getEntityByID(req.entityID_AC);
          if (e instanceof MCH_EntityAircraft)
            MCH_PacketStatusResponse.sendStatus((MCH_EntityAircraft)e, player); 
        });
  }
  
  @HandleSide({Side.SERVER})
  public static void onPacketIndNotifyAmmoNum(EntityPlayer player, ByteArrayDataInput data, IThreadListener scheduler) {
    if (player.world.isRemote)
      return; 
    MCH_PacketIndNotifyAmmoNum req = new MCH_PacketIndNotifyAmmoNum();
    req.readData(data);
    if (req.entityID_Ac <= 0)
      return; 
    scheduler.addScheduledTask(() -> {
          Entity e = player.world.getEntityByID(req.entityID_Ac);
          if (e instanceof MCH_EntityAircraft)
            if (req.weaponID >= 0) {
              MCH_PacketNotifyAmmoNum.sendAmmoNum((MCH_EntityAircraft)e, player, req.weaponID);
            } else {
              MCH_PacketNotifyAmmoNum.sendAllAmmoNum((MCH_EntityAircraft)e, player);
            }  
        });
  }
  
  @HandleSide({Side.SERVER})
  public static void onPacketIndReload(EntityPlayer player, ByteArrayDataInput data, IThreadListener scheduler) {
    if (player.world.isRemote)
      return; 
    MCH_PacketIndReload ind = new MCH_PacketIndReload();
    ind.readData(data);
    if (ind.entityID_Ac <= 0)
      return; 
    scheduler.addScheduledTask(() -> {
          Entity e = player.world.getEntityByID(ind.entityID_Ac);
          if (e instanceof MCH_EntityAircraft) {
            MCH_EntityAircraft ac = (MCH_EntityAircraft)e;
            MCH_Lib.DbgLog(e.world, "onPacketIndReload :%s", new Object[] { (ac.getAcInfo()).displayName });
            ac.supplyAmmo(ind.weaponID);
          } 
        });
  }
  
  @HandleSide({Side.CLIENT})
  public static void onPacketStatusResponse(EntityPlayer player, ByteArrayDataInput data, IThreadListener scheduler) {
    if (!player.world.isRemote)
      return; 
    MCH_PacketStatusResponse status = new MCH_PacketStatusResponse();
    status.readData(data);
    if (status.entityID_AC <= 0)
      return; 
    scheduler.addScheduledTask(() -> {
          String msg = "onPacketStatusResponse:EID=" + status.entityID_AC + ":";
          Entity e = player.world.getEntityByID(status.entityID_AC);
          if (e instanceof MCH_EntityAircraft) {
            MCH_EntityAircraft ac = (MCH_EntityAircraft)e;
            if (status.seatNum > 0 && status.weaponIDs != null && status.weaponIDs.length == status.seatNum) {
              msg = msg + "seatNum=" + status.seatNum + ":";
              for (int i = 0; i < status.seatNum; i++) {
                ac.updateWeaponID(i, status.weaponIDs[i]);
                msg = msg + "[" + i + "," + status.weaponIDs[i] + "]";
              } 
            } else {
              msg = msg + "Error seatNum=" + status.seatNum;
            } 
          } 
          MCH_Lib.DbgLog(true, msg, new Object[0]);
        });
  }
  
  @HandleSide({Side.CLIENT})
  public static void onPacketNotifyWeaponID(EntityPlayer player, ByteArrayDataInput data, IThreadListener scheduler) {
    if (!player.world.isRemote)
      return; 
    MCH_PacketNotifyWeaponID status = new MCH_PacketNotifyWeaponID();
    status.readData(data);
    if (status.entityID_Ac <= 0)
      return; 
    scheduler.addScheduledTask(() -> {
          Entity e = player.world.getEntityByID(status.entityID_Ac);
          if (e instanceof MCH_EntityAircraft) {
            MCH_EntityAircraft ac = (MCH_EntityAircraft)e;
            if (ac.isValidSeatID(status.seatID)) {
              ac.getWeapon(status.weaponID).setAmmoNum(status.ammo);
              ac.getWeapon(status.weaponID).setRestAllAmmoNum(status.restAmmo);
              MCH_Lib.DbgLog(true, "onPacketNotifyWeaponID:WeaponID=%d (%d / %d)", new Object[] { Integer.valueOf(status.weaponID), Short.valueOf(status.ammo), Short.valueOf(status.restAmmo) });
              if (W_Lib.isClientPlayer(ac.getEntityBySeatId(status.seatID))) {
                MCH_Lib.DbgLog(true, "onPacketNotifyWeaponID:#discard:SeatID=%d, WeaponID=%d", new Object[] { Integer.valueOf(status.seatID), Integer.valueOf(status.weaponID) });
              } else {
                MCH_Lib.DbgLog(true, "onPacketNotifyWeaponID:SeatID=%d, WeaponID=%d", new Object[] { Integer.valueOf(status.seatID), Integer.valueOf(status.weaponID) });
                ac.updateWeaponID(status.seatID, status.weaponID);
              } 
            } 
          } 
        });
  }
  
  @HandleSide({Side.CLIENT})
  public static void onPacketNotifyHitBullet(EntityPlayer player, ByteArrayDataInput data, IThreadListener scheduler) {
    if (!player.world.isRemote)
      return; 
    MCH_PacketNotifyHitBullet status = new MCH_PacketNotifyHitBullet();
    status.readData(data);
    scheduler.addScheduledTask(() -> {
          if (status.entityID_Ac <= 0) {
            MCH_MOD.proxy.hitBullet();
          } else {
            Entity e = player.world.getEntityByID(status.entityID_Ac);
            if (e instanceof MCH_EntityAircraft)
              ((MCH_EntityAircraft)e).hitBullet(); 
          } 
        });
  }
  
  @HandleSide({Side.SERVER})
  public static void onPacketSeatListRequest(EntityPlayer player, ByteArrayDataInput data, IThreadListener scheduler) {
    if (player.world.isRemote)
      return; 
    MCH_PacketSeatListRequest req = new MCH_PacketSeatListRequest();
    req.readData(data);
    if (req.entityID_AC <= 0)
      return; 
    scheduler.addScheduledTask(() -> {
          Entity e = player.world.getEntityByID(req.entityID_AC);
          if (e instanceof MCH_EntityAircraft)
            MCH_PacketSeatListResponse.sendSeatList((MCH_EntityAircraft)e, player); 
        });
  }
  
  @HandleSide({Side.CLIENT})
  public static void onPacketNotifyTVMissileEntity(EntityPlayer player, ByteArrayDataInput data, IThreadListener scheduler) {
    if (player.world.isRemote) {
      MCH_PacketNotifyTVMissileEntity packet = new MCH_PacketNotifyTVMissileEntity();
      packet.readData(data);
      if (packet.entityID_Ac <= 0)
        return; 
      if (packet.entityID_TVMissile <= 0)
        return; 
      scheduler.addScheduledTask(() -> {
            Entity e = player.world.getEntityByID(packet.entityID_Ac);
            if (e == null || !(e instanceof MCH_EntityAircraft))
              return; 
            MCH_EntityAircraft ac = (MCH_EntityAircraft)e;
            e = player.world.getEntityByID(packet.entityID_TVMissile);
            if (e == null || !(e instanceof MCH_EntityTvMissile))
              return; 
            ((MCH_EntityTvMissile)e).shootingEntity = (Entity)player;
            ac.setTVMissile((MCH_EntityTvMissile)e);
          });
    } 
  }
  
  @HandleSide({Side.CLIENT})
  public static void onPacketSeatListResponse(EntityPlayer player, ByteArrayDataInput data, IThreadListener scheduler) {
    if (!player.world.isRemote)
      return; 
    MCH_PacketSeatListResponse seatList = new MCH_PacketSeatListResponse();
    seatList.readData(data);
    if (seatList.entityID_AC <= 0)
      return; 
    scheduler.addScheduledTask(() -> {
          Entity e = player.world.getEntityByID(seatList.entityID_AC);
          if (e instanceof MCH_EntityAircraft) {
            MCH_EntityAircraft ac = (MCH_EntityAircraft)e;
            if (seatList.seatNum > 0 && seatList.seatNum == (ac.getSeats()).length && seatList.seatEntityID != null && seatList.seatEntityID.length == seatList.seatNum)
              for (int i = 0; i < seatList.seatNum; i++) {
                Entity entity = player.world.getEntityByID(seatList.seatEntityID[i]);
                if (entity instanceof MCH_EntitySeat) {
                  MCH_EntitySeat seat = (MCH_EntitySeat)entity;
                  seat.seatID = i;
                  seat.parentUniqueID = ac.getCommonUniqueId();
                  ac.setSeat(i, seat);
                  seat.setParent(ac);
                } 
              }  
          } 
        });
  }
  
  @HandleSide({Side.SERVER})
  public static void onPacket_PlayerControl(EntityPlayer player, ByteArrayDataInput data, IThreadListener scheduler) {
    if (player.world.isRemote)
      return; 
    MCH_PacketSeatPlayerControl pc = new MCH_PacketSeatPlayerControl();
    pc.readData(data);
    scheduler.addScheduledTask(() -> {
          MCH_EntityAircraft ac = null;
          if (player.getRidingEntity() instanceof MCH_EntitySeat) {
            MCH_EntitySeat seat = (MCH_EntitySeat)player.getRidingEntity();
            ac = seat.getParent();
          } else {
            ac = MCH_EntityAircraft.getAircraft_RiddenOrControl((Entity)player);
          } 
          if (ac == null)
            return; 
          if (pc.isUnmount) {
            ac.unmountEntityFromSeat((Entity)player);
          } else if (pc.switchSeat > 0) {
            if (pc.switchSeat == 3) {
              player.dismountRidingEntity();
              ac.keepOnRideRotation = true;
              ac.processInitialInteract(player, true, EnumHand.MAIN_HAND);
            } 
            if (pc.switchSeat == 1)
              ac.switchNextSeat((Entity)player); 
            if (pc.switchSeat == 2)
              ac.switchPrevSeat((Entity)player); 
          } else if (pc.parachuting) {
            ac.unmount((Entity)player);
          } 
        });
  }
  
  @HandleSide({Side.SERVER})
  public static void onPacket_ClientSetting(EntityPlayer player, ByteArrayDataInput data, IThreadListener scheduler) {
    if (player.world.isRemote)
      return; 
    MCH_PacketNotifyClientSetting pc = new MCH_PacketNotifyClientSetting();
    pc.readData(data);
    scheduler.addScheduledTask(() -> {
          MCH_EntityAircraft ac = MCH_EntityAircraft.getAircraft_RiddenOrControl((Entity)player);
          if (ac != null) {
            int sid = ac.getSeatIdByEntity((Entity)player);
            if (sid == 0) {
              ac.cs_dismountAll = pc.dismountAll;
              ac.cs_heliAutoThrottleDown = pc.heliAutoThrottleDown;
              ac.cs_planeAutoThrottleDown = pc.planeAutoThrottleDown;
              ac.cs_tankAutoThrottleDown = pc.tankAutoThrottleDown;
            } 
            ac.camera.setShaderSupport(sid, Boolean.valueOf(pc.shaderSupport));
          } 
        });
  }
  
  @HandleSide({Side.SERVER})
  public static void onPacketNotifyInfoReloaded(EntityPlayer player, ByteArrayDataInput data, IThreadListener scheduler) {
    if (player.world.isRemote)
      return; 
    MCH_PacketNotifyInfoReloaded pc = new MCH_PacketNotifyInfoReloaded();
    pc.readData(data);
    scheduler.addScheduledTask(() -> {
          MCH_EntityAircraft ac;
          switch (pc.type) {
            case 0:
              ac = MCH_EntityAircraft.getAircraft_RiddenOrControl((Entity)player);
              if (ac != null && ac.getAcInfo() != null) {
                String name = (ac.getAcInfo()).name;
                for (WorldServer world : (MCH_Utils.getServer()).worldServers) {
                  List<Entity> list = world.loadedEntityList;
                  for (int i = 0; i < list.size(); i++) {
                    if (list.get(i) instanceof MCH_EntityAircraft) {
                      ac = (MCH_EntityAircraft)list.get(i);
                      if (ac.getAcInfo() != null && (ac.getAcInfo()).name.equals(name)) {
                        ac.changeType(name);
                        ac.createSeats(UUID.randomUUID().toString());
                        ac.onAcInfoReloaded();
                      } 
                    } 
                  } 
                } 
              } 
              break;
            case 1:
              ContentRegistries.weapon().reloadAll();
              for (WorldServer world : (MCH_Utils.getServer()).worldServers) {
                List<Entity> list = world.loadedEntityList;
                for (int i = 0; i < list.size(); i++) {
                  if (list.get(i) instanceof MCH_EntityAircraft) {
                    ac = (MCH_EntityAircraft)list.get(i);
                    if (ac.getAcInfo() != null) {
                      ac.changeType((ac.getAcInfo()).name);
                      ac.createSeats(UUID.randomUUID().toString());
                    } 
                  } 
                } 
              } 
              break;
          } 
        });
  }
}
