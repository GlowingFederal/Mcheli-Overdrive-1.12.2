package mcheli.wrapper;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class W_Item extends Item {
  public W_Item(int par1) {}
  
  public W_Item() {}
  
  public static int getIdFromItem(Item i) {
    return (i == null) ? 0 : REGISTRY.getIDForObject(i);
  }
  
  public static Item getItemById(int i) {
    return Item.getItemById(i);
  }
  
  public static Item getItemByName(String nm) {
    return (Item)ForgeRegistries.ITEMS.getValue(new ResourceLocation(nm));
  }

  public static String getNameForItem(Item item) {
    // Make sure that the item passed is indeed an instance of IForgeRegistryEntry
    if (item instanceof IForgeRegistryEntry) {
      return ForgeRegistries.ITEMS.getKey(item).toString();
    }
    return "Unknown Item";
  }
  
  public static Item getItemFromBlock(Block block) {
    return Item.getItemFromBlock(block);
  }
}
