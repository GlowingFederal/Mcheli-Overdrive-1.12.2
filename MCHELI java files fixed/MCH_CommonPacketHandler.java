package mcheli;

import com.google.common.io.ByteArrayDataInput;
import mcheli.__helper.network.HandleSide;
import mcheli.aircraft.MCH_EntityAircraft;
import mcheli.aircraft.MCH_EntitySeat;
import mcheli.lweapon.MCH_ClientLightWeaponTickHandler;
import mcheli.wrapper.W_Reflection;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.fml.relauncher.Side;

public class MCH_CommonPacketHandler {
  @HandleSide({Side.CLIENT})
  public static void onPacketEffectExplosion(EntityPlayer player, ByteArrayDataInput data, IThreadListener scheduler) {
    if (!player.world.isRemote)
      return; 
    MCH_PacketEffectExplosion pkt = new MCH_PacketEffectExplosion();
    pkt.readData(data);
    scheduler.addScheduledTask(() -> {
          Entity exploder = null;
          if (player.getDistanceSq(pkt.prm.posX, pkt.prm.posY, pkt.prm.posZ) <= 40000.0D)
            if (!pkt.prm.inWater) {
              if (!MCH_Config.DefaultExplosionParticle.prmBool) {
                MCH_Explosion.effectExplosion(player.world, exploder, pkt.prm.posX, pkt.prm.posY, pkt.prm.posZ, pkt.prm.size, true, pkt.prm.getAffectedBlockPositions());
              } else {
                MCH_Explosion.DEF_effectExplosion(player.world, exploder, pkt.prm.posX, pkt.prm.posY, pkt.prm.posZ, pkt.prm.size, true, pkt.prm.getAffectedBlockPositions());
              } 
            } else {
              MCH_Explosion.effectExplosionInWater(player.world, exploder, pkt.prm.posX, pkt.prm.posY, pkt.prm.posZ, pkt.prm.size, true);
            }  
        });
  }
  
  @HandleSide({Side.SERVER})
  public static void onPacketIndOpenScreen(EntityPlayer player, ByteArrayDataInput data, IThreadListener scheduler) {
    if (player.world.isRemote)
      return; 
    MCH_PacketIndOpenScreen pkt = new MCH_PacketIndOpenScreen();
    pkt.readData(data);
    scheduler.addScheduledTask(() -> {
          if (pkt.guiID == 3) {
            MCH_EntityAircraft ac = MCH_EntityAircraft.getAircraft_RiddenOrControl((Entity)player);
            if (ac != null)
              ac.displayInventory(player); 
          } else {
            player.openGui(MCH_MOD.instance, pkt.guiID, player.world, (int)player.posX, (int)player.posY, (int)player.posZ);
          } 
        });
  }
  
  @HandleSide({Side.CLIENT})
  public static void onPacketNotifyServerSettings(EntityPlayer player, ByteArrayDataInput data, IThreadListener scheduler) {
    if (!player.world.isRemote)
      return; 
    MCH_PacketNotifyServerSettings pkt = new MCH_PacketNotifyServerSettings();
    pkt.readData(data);
    scheduler.addScheduledTask(() -> {
          MCH_Lib.DbgLog(false, "onPacketNotifyServerSettings:" + player, new Object[0]);
          if (!pkt.enableCamDistChange)
            W_Reflection.setThirdPersonDistance(4.0F); 
          MCH_ServerSettings.enableCamDistChange = pkt.enableCamDistChange;
          MCH_ServerSettings.enableEntityMarker = pkt.enableEntityMarker;
          MCH_ServerSettings.enablePVP = pkt.enablePVP;
          MCH_ServerSettings.stingerLockRange = pkt.stingerLockRange;
          MCH_ServerSettings.enableDebugBoundingBox = pkt.enableDebugBoundingBox;
          MCH_ServerSettings.enableRotationLimit = pkt.enableRotationLimit;
          MCH_ServerSettings.pitchLimitMax = pkt.pitchLimitMax;
          MCH_ServerSettings.pitchLimitMin = pkt.pitchLimitMin;
          MCH_ServerSettings.rollLimit = pkt.rollLimit;
          MCH_ClientLightWeaponTickHandler.lockRange = MCH_ServerSettings.stingerLockRange;
        });
  }
  
  @HandleSide({Side.CLIENT, Side.SERVER})
  public static void onPacketNotifyLock(EntityPlayer player, ByteArrayDataInput data, IThreadListener scheduler) {
    MCH_PacketNotifyLock pkt = new MCH_PacketNotifyLock();
    pkt.readData(data);
    if (!player.world.isRemote) {
      if (pkt.entityID >= 0)
        scheduler.addScheduledTask(() -> {
              Entity target = player.world.getEntityByID(pkt.entityID);
              if (target != null) {
                MCH_EntityAircraft ac = null;
                if (target instanceof MCH_EntityAircraft) {
                  ac = (MCH_EntityAircraft)target;
                } else if (target instanceof MCH_EntitySeat) {
                  ac = ((MCH_EntitySeat)target).getParent();
                } else {
                  ac = MCH_EntityAircraft.getAircraft_RiddenOrControl(target);
                } 
                if (ac != null && ac.haveFlare() && !ac.isDestroyed()) {
                  for (int i = 0; i < 2; i++) {
                    Entity entity = ac.getEntityBySeatId(i);
                    if (entity instanceof net.minecraft.entity.player.EntityPlayerMP)
                      MCH_PacketNotifyLock.sendToPlayer((EntityPlayer)entity); 
                  } 
                } else if (target.getRidingEntity() != null) {
                  if (target instanceof net.minecraft.entity.player.EntityPlayerMP)
                    MCH_PacketNotifyLock.sendToPlayer((EntityPlayer)target); 
                } 
              } 
            }); 
    } else {
      scheduler.addScheduledTask(() -> MCH_MOD.proxy.clientLocked());
    } 
  }
}
