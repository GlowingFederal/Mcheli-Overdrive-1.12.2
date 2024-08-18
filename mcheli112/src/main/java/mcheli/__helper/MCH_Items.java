package mcheli.__helper;

import com.google.common.collect.Sets;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.registries.IForgeRegistryEntry;

@ObjectHolder("mcheli")
@EventBusSubscriber(modid = "mcheli")
public class MCH_Items {
  private static Set<Item> registryWrapper = Sets.newLinkedHashSet();
  
  @SubscribeEvent
  static void onItemRegistryEvent(RegistryEvent.Register<Item> event) {
    for (Item item : registryWrapper)
      event.getRegistry().register((IForgeRegistryEntry)item); 
  }
  
  public static Item register(Item item, String name) {
    registryWrapper.add(item.setRegistryName(MCH_Utils.suffix(name)));
    return item;
  }
  
  public static ItemBlock registerBlock(Block block) {
    ItemBlock itemBlock = new ItemBlock(block);
    registryWrapper.add(itemBlock.setRegistryName(block.getRegistryName()));
    return itemBlock;
  }
  
  @Nullable
  public static Item get(String name) {
    return (Item)ForgeRegistries.ITEMS.getValue(MCH_Utils.suffix(name));
  }
  
  public static String getName(Item item) {
    return ForgeRegistries.ITEMS.getKey((IForgeRegistryEntry)item).toString();
  }
}
