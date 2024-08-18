package mcheli.__helper.client;

import com.google.common.collect.Maps;
import java.util.Map;
import javax.annotation.Nullable;
import mcheli.MCH_MOD;
import mcheli.__helper.client.model.MCH_BakedModel;
import mcheli.__helper.client.renderer.item.CustomItemStackRenderer;
import mcheli.__helper.client.renderer.item.IItemModelRenderer;
import mcheli.__helper.info.ContentRegistries;
import mcheli.__helper.info.IItemContent;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@EventBusSubscriber(modid = "mcheli", value = {Side.CLIENT})
public class MCH_ItemModelRenderers {
  private static final Map<ModelResourceLocation, IItemModelRenderer> renderers = Maps.newHashMap();
  
  @SubscribeEvent
  static void onModelRegistryEvent(ModelRegistryEvent event) {
    registerModelLocation(Item.func_150898_a((Block)MCH_MOD.blockDraftingTable));
    ModelLoader.setCustomStateMapper((Block)MCH_MOD.blockDraftingTable, new IStateMapper() {
          public Map<IBlockState, ModelResourceLocation> func_178130_a(Block blockIn) {
            return Maps.newHashMap();
          }
        });
    ModelLoader.setCustomStateMapper((Block)MCH_MOD.blockDraftingTableLit, new IStateMapper() {
          public Map<IBlockState, ModelResourceLocation> func_178130_a(Block blockIn) {
            return Maps.newHashMap();
          }
        });
    registerModelLocation((Item)MCH_MOD.itemSpawnGunnerVsMonster);
    registerModelLocation((Item)MCH_MOD.itemSpawnGunnerVsPlayer);
    registerModelLocation((Item)MCH_MOD.itemRangeFinder);
    registerModelLocation((Item)MCH_MOD.itemWrench);
    registerModelLocation((Item)MCH_MOD.itemFuel);
    registerModelLocation((Item)MCH_MOD.itemGLTD);
    registerModelLocation((Item)MCH_MOD.itemChain);
    registerModelLocation((Item)MCH_MOD.itemParachute);
    registerModelLocation((Item)MCH_MOD.itemContainer);
    for (int i = 0; i < MCH_MOD.itemUavStation.length; i++)
      registerModelLocation((Item)MCH_MOD.itemUavStation[i]); 
    registerModelLocation((Item)MCH_MOD.invisibleItem);
    registerModelLocation((Item)MCH_MOD.itemStingerBullet);
    registerModelLocation((Item)MCH_MOD.itemJavelinBullet);
    registerModelLocation((Item)MCH_MOD.itemStinger);
    registerModelLocation((Item)MCH_MOD.itemJavelin);
    ContentRegistries.heli().forEachValue(MCH_ItemModelRenderers::registerLegacyModelLocation);
    ContentRegistries.plane().forEachValue(MCH_ItemModelRenderers::registerLegacyModelLocation);
    ContentRegistries.tank().forEachValue(MCH_ItemModelRenderers::registerLegacyModelLocation);
    ContentRegistries.vehicle().forEachValue(MCH_ItemModelRenderers::registerLegacyModelLocation);
    ContentRegistries.throwable().forEachValue(MCH_ItemModelRenderers::registerLegacyModelLocation);
  }
  
  @SubscribeEvent
  static void onBakedModelEvent(ModelBakeEvent event) {
    for (Map.Entry<ModelResourceLocation, IItemModelRenderer> entry : renderers.entrySet()) {
      IBakedModel bakedmodel = (IBakedModel)event.getModelRegistry().func_82594_a(entry.getKey());
      if (bakedmodel != null)
        event.getModelRegistry().func_82595_a(entry.getKey(), new MCH_BakedModel(bakedmodel, entry.getValue())); 
    } 
  }
  
  public static void registerRenderer(Item item, IItemModelRenderer renderer) {
    item.setTileEntityItemStackRenderer((TileEntityItemStackRenderer)CustomItemStackRenderer.getInstance());
    renderers.put(getInventoryModel(item), renderer);
  }
  
  public static void registerModelLocation(Item item) {
    registerModelLocation(item, 0);
  }
  
  public static void registerModelLocation(Item item, int meta) {
    ModelLoader.setCustomModelResourceLocation(item, meta, getInventoryModel(item));
  }
  
  public static void registerLegacyModelLocation(IItemContent content) {
    ModelLoader.setCustomModelResourceLocation(content.getItem(), 0, new ModelResourceLocation(content
          .getItem().getRegistryName(), "mcheli_legacy"));
  }
  
  private static ModelResourceLocation getInventoryModel(Item item) {
    return new ModelResourceLocation(item.getRegistryName(), "inventory");
  }
  
  @Nullable
  public static IItemModelRenderer getRenderer(Item item) {
    return renderers.get(getInventoryModel(item));
  }
}
