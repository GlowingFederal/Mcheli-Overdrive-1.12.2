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
      if (!aircraft.field_70170_p.field_72995_K)
        if (!aircraft.isCreatedSeats())
          aircraft.createSeats(UUID.randomUUID().toString());  
    } else if (W_EntityPlayer.isPlayer(event.getEntity())) {
      Entity e = event.getEntity();
      boolean b = Float.isNaN(e.field_70125_A);
      b |= Float.isNaN(e.field_70127_C);
      b |= Float.isInfinite(e.field_70125_A);
      b |= Float.isInfinite(e.field_70127_C);
      if (b) {
        MCH_Lib.Log(event.getEntity(), "### EntityJoinWorldEvent Error:Player invalid rotation pitch(" + e.field_70125_A + ")", new Object[0]);
        e.field_70125_A = 0.0F;
        e.field_70127_C = 0.0F;
      } 
      b = Float.isInfinite(e.field_70177_z);
      b |= Float.isInfinite(e.field_70126_B);
      b |= Float.isNaN(e.field_70177_z);
      b |= Float.isNaN(e.field_70126_B);
      if (b) {
        MCH_Lib.Log(event.getEntity(), "### EntityJoinWorldEvent Error:Player invalid rotation yaw(" + e.field_70177_z + ")", new Object[0]);
        e.field_70177_z = 0.0F;
        e.field_70126_B = 0.0F;
      } 
      if (!e.field_70170_p.field_72995_K && event.getEntity() instanceof EntityPlayerMP) {
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
    Entity attackEntity = event.getSource().func_76346_g();
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
    Entity attackEntity = event.getSource().func_76346_g();
    float f = event.getAmount();
    if (attackEntity == null) {
      ac.func_70097_a(event.getSource(), f * 2.0F);
      f *= (ac.getAcInfo()).damageFactor;
    } else if (W_Entity.isEqual(attackEntity, event.getEntity())) {
      ac.func_70097_a(event.getSource(), f * 2.0F);
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
        ac.func_70097_a(event.getSource(), f * 2.0F);
        f *= (ac.getAcInfo()).damageFactor;
      } 
    } 
    event.setAmount(f);
  }
  
  public MCH_EntityAircraft getRiddenAircraft(Entity entity) {
    MCH_EntityAircraft ac = null;
    Entity ridden = entity.func_184187_bx();
    if (ridden instanceof MCH_EntityAircraft) {
      ac = (MCH_EntityAircraft)ridden;
    } else if (ridden instanceof MCH_EntitySeat) {
      ac = ((MCH_EntitySeat)ridden).getParent();
    } 
    if (ac == null) {
      List<MCH_EntityAircraft> list = entity.field_70170_p.func_72872_a(MCH_EntityAircraft.class, entity
          .func_174813_aQ().func_72314_b(50.0D, 50.0D, 50.0D));
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
    ItemStack item = event.getEntityPlayer().func_184586_b(event.getHand());
    if (item.func_190926_b())
      return; 
    if (item.func_77973_b() instanceof MCH_ItemChain) {
      MCH_ItemChain.interactEntity(item, event.getTarget(), event.getEntityPlayer(), 
          (event.getEntityPlayer()).field_70170_p);
      event.setCanceled(true);
      event.setCancellationResult(EnumActionResult.SUCCESS);
    } else if (item.func_77973_b() instanceof MCH_ItemAircraft) {
      ((MCH_ItemAircraft)item.func_77973_b()).rideEntity(item, event.getTarget(), event.getEntityPlayer());
    } 
  }
  
  public void entityCanUpdate(EntityEvent.CanUpdate event) {
    if (event.getEntity() instanceof MCH_EntityBaseBullet) {
      MCH_EntityBaseBullet bullet = (MCH_EntityBaseBullet)event.getEntity();
      bullet.func_70106_y();
    } 
  }
}
