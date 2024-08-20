package mcheli;

import java.util.List;
import java.util.UUID;
import mcheli.aircraft.MCH_EntityAircraft;
import mcheli.aircraft.MCH_EntitySeat;
import mcheli.aircraft.MCH_ItemAircraft;
import mcheli.chain.MCH_ItemChain;
import mcheli.command.MCH_Command;
import mcheli.weapon.MCH_EntityBaseBullet;
import mcheli.wrapper.W_Entity;
import mcheli.wrapper.W_EntityPlayer;
import mcheli.wrapper.W_EventHook;
import mcheli.wrapper.W_Lib;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public class MCH_EventHook extends W_EventHook {
  public void commandEvent(CommandEvent event) {
    MCH_Command.onCommandEvent(event);
  }
  
  public void entitySpawn(EntityJoinWorldEvent event) {
    if (W_Lib.isEntityLivingBase(event.getEntity()) && !W_EntityPlayer.isPlayer(event.getEntity())) {
      MCH_MOD.proxy.setRenderEntityDistanceWeight(MCH_Config.MobRenderDistanceWeight.prmDouble);
    } else if (event.getEntity() instanceof MCH_EntityAircraft) {
      MCH_EntityAircraft aircraft = (MCH_EntityAircraft)event.getEntity();
      if (!aircraft.world.isRemote)
        if (!aircraft.isCreatedSeats())
          aircraft.createSeats(UUID.randomUUID().toString());  
    } else if (W_EntityPlayer.isPlayer(event.getEntity())) {
      Entity e = event.getEntity();
      boolean b = Float.isNaN(e.rotationPitch);
      b |= Float.isNaN(e.prevRotationPitch);
      b |= Float.isInfinite(e.rotationPitch);
      b |= Float.isInfinite(e.prevRotationPitch);
      if (b) {
        MCH_Lib.Log(event.getEntity(), "### EntityJoinWorldEvent Error:Player invalid rotation pitch(" + e.rotationPitch + ")", new Object[0]);
        e.rotationPitch = 0.0F;
        e.prevRotationPitch = 0.0F;
      } 
      b = Float.isInfinite(e.rotationYaw);
      b |= Float.isInfinite(e.prevRotationYaw);
      b |= Float.isNaN(e.rotationYaw);
      b |= Float.isNaN(e.prevRotationYaw);
      if (b) {
        MCH_Lib.Log(event.getEntity(), "### EntityJoinWorldEvent Error:Player invalid rotation yaw(" + e.rotationYaw + ")", new Object[0]);
        e.rotationYaw = 0.0F;
        e.prevRotationYaw = 0.0F;
      } 
      if (!e.world.isRemote && event.getEntity() instanceof EntityPlayerMP) {
        MCH_Lib.DbgLog(false, "EntityJoinWorldEvent:" + event.getEntity(), new Object[0]);
        MCH_PacketNotifyServerSettings.send((EntityPlayerMP)event.getEntity());
      } 
    } 
  }
  
  public void livingAttackEvent(LivingAttackEvent event) {
    MCH_EntityAircraft ac = getRiddenAircraft(event.getEntity());
    if (ac == null)
      return; 
    if (ac.getAcInfo() == null)
      return; 
    if (ac.isDestroyed())
      return; 
    if ((ac.getAcInfo()).damageFactor > 0.0F)
      return; 
    Entity attackEntity = event.getSource().getEntity();
    if (attackEntity == null) {
      event.setCanceled(true);
    } else if (W_Entity.isEqual(attackEntity, event.getEntity())) {
      event.setCanceled(true);
    } else if (ac.isMountedEntity(attackEntity)) {
      event.setCanceled(true);
    } else {
      MCH_EntityAircraft atkac = getRiddenAircraft(attackEntity);
      if (W_Entity.isEqual((Entity)atkac, (Entity)ac))
        event.setCanceled(true); 
    } 
  }
  
  public void livingHurtEvent(LivingHurtEvent event) {
    MCH_EntityAircraft ac = getRiddenAircraft(event.getEntity());
    if (ac == null)
      return; 
    if (ac.getAcInfo() == null)
      return; 
    if (ac.isDestroyed())
      return; 
    Entity attackEntity = event.getSource().getEntity();
    float f = event.getAmount();
    if (attackEntity == null) {
      ac.attackEntityFrom(event.getSource(), f * 2.0F);
      f *= (ac.getAcInfo()).damageFactor;
    } else if (W_Entity.isEqual(attackEntity, event.getEntity())) {
      ac.attackEntityFrom(event.getSource(), f * 2.0F);
      f *= (ac.getAcInfo()).damageFactor;
    } else if (ac.isMountedEntity(attackEntity)) {
      f = 0.0F;
      event.setCanceled(true);
    } else {
      MCH_EntityAircraft atkac = getRiddenAircraft(attackEntity);
      if (W_Entity.isEqual((Entity)atkac, (Entity)ac)) {
        f = 0.0F;
        event.setCanceled(true);
      } else {
        ac.attackEntityFrom(event.getSource(), f * 2.0F);
        f *= (ac.getAcInfo()).damageFactor;
      } 
    } 
    event.setAmount(f);
  }
  
  public MCH_EntityAircraft getRiddenAircraft(Entity entity) {
    MCH_EntityAircraft ac = null;
    Entity ridden = entity.getRidingEntity();
    if (ridden instanceof MCH_EntityAircraft) {
      ac = (MCH_EntityAircraft)ridden;
    } else if (ridden instanceof MCH_EntitySeat) {
      ac = ((MCH_EntitySeat)ridden).getParent();
    } 
    if (ac == null) {
      List<MCH_EntityAircraft> list = entity.world.getEntitiesWithinAABB(MCH_EntityAircraft.class, entity
          .getEntityBoundingBox().expand(50.0D, 50.0D, 50.0D));
      if (list != null)
        for (int i = 0; i < list.size(); i++) {
          MCH_EntityAircraft tmp = list.get(i);
          if (tmp.isMountedEntity(entity))
            return tmp; 
        }  
    } 
    return ac;
  }
  
  public void entityInteractEvent(PlayerInteractEvent.EntityInteract event) {
    ItemStack item = event.getEntityPlayer().getHeldItem(event.getHand());
    if (item.isEmpty())
      return; 
    if (item.getItem() instanceof MCH_ItemChain) {
      MCH_ItemChain.interactEntity(item, event.getTarget(), event.getEntityPlayer(), 
          (event.getEntityPlayer()).world);
      event.setCanceled(true);
      event.setCancellationResult(EnumActionResult.SUCCESS);
    } else if (item.getItem() instanceof MCH_ItemAircraft) {
      ((MCH_ItemAircraft)item.getItem()).rideEntity(item, event.getTarget(), event.getEntityPlayer());
    } 
  }
  
  public void entityCanUpdate(EntityEvent.CanUpdate event) {
    if (event.getEntity() instanceof MCH_EntityBaseBullet) {
      MCH_EntityBaseBullet bullet = (MCH_EntityBaseBullet)event.getEntity();
      bullet.setDead();
    } 
  }
}
