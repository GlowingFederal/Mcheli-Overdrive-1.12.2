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
    if (player.field_70170_p.field_72995_K)
      return; 
    MCH_PacketLightWeaponPlayerControl pc = new MCH_PacketLightWeaponPlayerControl();
    pc.readData(data);
    scheduler.func_152344_a(() -> {
          if (pc.camMode == 1)
            player.func_184589_d(MobEffects.field_76439_r); 
          ItemStack is = player.func_184614_ca();
          if (is.func_190926_b())
            return; 
          if (!(is.func_77973_b() instanceof MCH_ItemLightWeaponBase))
            return; 
          MCH_ItemLightWeaponBase lweapon = (MCH_ItemLightWeaponBase)is.func_77973_b();
          if (pc.camMode == 2 && MCH_ItemLightWeaponBase.isHeld(player))
            player.func_70690_d(new PotionEffect(MobEffects.field_76439_r, 255, 0, false, false)); 
          if (pc.camMode > 0)
            MCH_Lib.DbgLog(false, "MCH_LightWeaponPacketHandler NV=%s", new Object[] { (pc.camMode == 2) ? "ON" : "OFF" }); 
          if (pc.useWeapon && is.func_77960_j() < is.func_77958_k()) {
            String name = MCH_ItemLightWeaponBase.getName(player.func_184614_ca());
            MCH_WeaponBase w = MCH_WeaponCreator.createWeapon(player.field_70170_p, name, Vec3d.field_186680_a, 0.0F, 0.0F, null, false);
            MCH_WeaponParam prm = new MCH_WeaponParam();
            prm.entity = (Entity)player;
            prm.user = (Entity)player;
            prm.setPosAndRot(pc.useWeaponPosX, pc.useWeaponPosY, pc.useWeaponPosZ, player.field_70177_z, player.field_70125_A);
            prm.option1 = pc.useWeaponOption1;
            prm.option2 = pc.useWeaponOption2;
            w.shot(prm);
            if (!player.field_71075_bZ.field_75098_d)
              if (is.func_77958_k() == 1)
                is.func_190918_g(1);  
            if (is.func_77958_k() > 1)
              is.func_77964_b(is.func_77958_k()); 
          } else if (pc.cmpReload > 0) {
            if (is.func_77960_j() > 1)
              if (W_EntityPlayer.hasItem(player, (Item)lweapon.bullet)) {
                if (!player.field_71075_bZ.field_75098_d)
                  W_EntityPlayer.consumeInventoryItem(player, (Item)lweapon.bullet); 
                is.func_77964_b(0);
              }  
          } 
        });
  }
}
