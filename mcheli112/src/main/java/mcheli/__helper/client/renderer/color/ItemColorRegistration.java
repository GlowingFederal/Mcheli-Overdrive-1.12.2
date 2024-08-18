package mcheli.__helper.client.renderer.color;

import mcheli.MCH_MOD;
import mcheli.mob.MCH_ItemSpawnGunner;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@EventBusSubscriber(modid = "mcheli", value = {Side.CLIENT})
public class ItemColorRegistration {
  @SubscribeEvent
  static void onRegisterItemColor(ColorHandlerEvent.Item event) {
    ItemColors itemColors = event.getItemColors();
    itemColors.func_186730_a(MCH_ItemSpawnGunner::getColorFromItemStack, new Item[] { (Item)MCH_MOD.itemSpawnGunnerVsMonster, (Item)MCH_MOD.itemSpawnGunnerVsPlayer });
  }
}
