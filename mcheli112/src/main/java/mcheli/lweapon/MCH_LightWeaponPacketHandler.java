package mcheli.lweapon;

import com.google.common.io.ByteArrayDataInput;
import mcheli.MCH_Lib;
import mcheli.__helper.network.HandleSide;
import mcheli.weapon.MCH_WeaponBase;
import mcheli.weapon.MCH_WeaponCreator;
import mcheli.weapon.MCH_WeaponParam;
import mcheli.wrapper.W_EntityPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.IThreadListener;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;

public class MCH_LightWeaponPacketHandler {
  @HandleSide({Side.SERVER})
  public static void onPacket_PlayerControl(EntityPlayer player, ByteArrayDataInput data, IThreadListener scheduler) {
    if (player.world.isRemote)
      return; 
    MCH_PacketLightWeaponPlayerControl pc = new MCH_PacketLightWeaponPlayerControl();
    pc.readData(data);
    scheduler.addScheduledTask(() -> {
          if (pc.camMode == 1)
            player.removePotionEffect(MobEffects.NIGHT_VISION); 
          ItemStack is = player.getHeldItemMainhand();
          if (is.func_190926_b())
            return; 
          if (!(is.getItem() instanceof MCH_ItemLightWeaponBase))
            return; 
          MCH_ItemLightWeaponBase lweapon = (MCH_ItemLightWeaponBase)is.getItem();
          if (pc.camMode == 2 && MCH_ItemLightWeaponBase.isHeld(player))
            player.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 255, 0, false, false)); 
          if (pc.camMode > 0)
            MCH_Lib.DbgLog(false, "MCH_LightWeaponPacketHandler NV=%s", new Object[] { (pc.camMode == 2) ? "ON" : "OFF" }); 
          if (pc.useWeapon && is.getMetadata() < is.getMaxDamage()) {
            String name = MCH_ItemLightWeaponBase.getName(player.getHeldItemMainhand());
            MCH_WeaponBase w = MCH_WeaponCreator.createWeapon(player.world, name, Vec3d.ZERO, 0.0F, 0.0F, null, false);
            MCH_WeaponParam prm = new MCH_WeaponParam();
            prm.entity = (Entity)player;
            prm.user = (Entity)player;
            prm.setPosAndRot(pc.useWeaponPosX, pc.useWeaponPosY, pc.useWeaponPosZ, player.rotationYaw, player.rotationPitch);
            prm.option1 = pc.useWeaponOption1;
            prm.option2 = pc.useWeaponOption2;
            w.shot(prm);
            if (!player.capabilities.isCreativeMode)
              if (is.getMaxDamage() == 1)
                is.func_190918_g(1);  
            if (is.getMaxDamage() > 1)
              is.setItemDamage(is.getMaxDamage()); 
          } else if (pc.cmpReload > 0) {
            if (is.getMetadata() > 1)
              if (W_EntityPlayer.hasItem(player, (Item)lweapon.bullet)) {
                if (!player.capabilities.isCreativeMode)
                  W_EntityPlayer.consumeInventoryItem(player, (Item)lweapon.bullet); 
                is.setItemDamage(0);
              }  
          } 
        });
  }
}
