package mcheli.__helper;

import com.google.common.collect.Sets;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistryEntry;

@EventBusSubscriber(modid = "mcheli")
public class MCH_Blocks {
  private static Set<Block> registryWrapper = Sets.newLinkedHashSet();
  
  @SubscribeEvent
  static void onBlockRegisterEvent(RegistryEvent.Register<Block> event) {
    for (Block block : registryWrapper)
      event.getRegistry().register((IForgeRegistryEntry)block); 
  }
  
  public static void register(Block block, String name) {
    registryWrapper.add(block.setRegistryName(MCH_Utils.suffix(name)));
  }
}
