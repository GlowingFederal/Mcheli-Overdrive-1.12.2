/*     */ package mcheli.__helper.client;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Map;
/*     */ import javax.annotation.Nullable;
/*     */ import mcheli.MCH_MOD;
/*     */ import mcheli.__helper.client.model.MCH_BakedModel;
/*     */ import mcheli.__helper.client.renderer.item.CustomItemStackRenderer;
/*     */ import mcheli.__helper.client.renderer.item.IItemModelRenderer;
/*     */ import mcheli.__helper.info.ContentRegistries;
/*     */ import mcheli.__helper.info.IItemContent;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.renderer.block.model.IBakedModel;
/*     */ import net.minecraft.client.renderer.block.model.ModelResourceLocation;
/*     */ import net.minecraft.client.renderer.block.statemap.IStateMapper;
/*     */ import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraftforge.client.event.ModelBakeEvent;
/*     */ import net.minecraftforge.client.event.ModelRegistryEvent;
/*     */ import net.minecraftforge.client.model.ModelLoader;
/*     */ import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @EventBusSubscriber(modid = "mcheli", value = {Side.CLIENT})
/*     */ public class MCH_ItemModelRenderers
/*     */ {
/*  37 */   private static final Map<ModelResourceLocation, IItemModelRenderer> renderers = Maps.newHashMap();
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   static void onModelRegistryEvent(ModelRegistryEvent event) {
/*  42 */     registerModelLocation(Item.func_150898_a((Block)MCH_MOD.blockDraftingTable));
/*  43 */     ModelLoader.setCustomStateMapper((Block)MCH_MOD.blockDraftingTable, new IStateMapper()
/*     */         {
/*     */           
/*     */           public Map<IBlockState, ModelResourceLocation> func_178130_a(Block blockIn)
/*     */           {
/*  48 */             return Maps.newHashMap();
/*     */           }
/*     */         });
/*  51 */     ModelLoader.setCustomStateMapper((Block)MCH_MOD.blockDraftingTableLit, new IStateMapper()
/*     */         {
/*     */           
/*     */           public Map<IBlockState, ModelResourceLocation> func_178130_a(Block blockIn)
/*     */           {
/*  56 */             return Maps.newHashMap();
/*     */           }
/*     */         });
/*     */     
/*  60 */     registerModelLocation((Item)MCH_MOD.itemSpawnGunnerVsMonster);
/*  61 */     registerModelLocation((Item)MCH_MOD.itemSpawnGunnerVsPlayer);
/*  62 */     registerModelLocation((Item)MCH_MOD.itemRangeFinder);
/*  63 */     registerModelLocation((Item)MCH_MOD.itemWrench);
/*  64 */     registerModelLocation((Item)MCH_MOD.itemFuel);
/*  65 */     registerModelLocation((Item)MCH_MOD.itemGLTD);
/*  66 */     registerModelLocation((Item)MCH_MOD.itemChain);
/*  67 */     registerModelLocation((Item)MCH_MOD.itemParachute);
/*  68 */     registerModelLocation((Item)MCH_MOD.itemContainer);
/*     */     
/*  70 */     for (int i = 0; i < MCH_MOD.itemUavStation.length; i++)
/*     */     {
/*  72 */       registerModelLocation((Item)MCH_MOD.itemUavStation[i]);
/*     */     }
/*     */     
/*  75 */     registerModelLocation((Item)MCH_MOD.invisibleItem);
/*  76 */     registerModelLocation((Item)MCH_MOD.itemStingerBullet);
/*  77 */     registerModelLocation((Item)MCH_MOD.itemJavelinBullet);
/*  78 */     registerModelLocation((Item)MCH_MOD.itemStinger);
/*  79 */     registerModelLocation((Item)MCH_MOD.itemJavelin);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  85 */     ContentRegistries.heli().forEachValue(MCH_ItemModelRenderers::registerLegacyModelLocation);
/*  86 */     ContentRegistries.plane().forEachValue(MCH_ItemModelRenderers::registerLegacyModelLocation);
/*  87 */     ContentRegistries.tank().forEachValue(MCH_ItemModelRenderers::registerLegacyModelLocation);
/*  88 */     ContentRegistries.vehicle().forEachValue(MCH_ItemModelRenderers::registerLegacyModelLocation);
/*  89 */     ContentRegistries.throwable().forEachValue(MCH_ItemModelRenderers::registerLegacyModelLocation);
/*     */   }
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   static void onBakedModelEvent(ModelBakeEvent event) {
/*  95 */     for (Map.Entry<ModelResourceLocation, IItemModelRenderer> entry : renderers.entrySet()) {
/*     */       
/*  97 */       IBakedModel bakedmodel = (IBakedModel)event.getModelRegistry().func_82594_a(entry.getKey());
/*     */       
/*  99 */       if (bakedmodel != null)
/*     */       {
/* 101 */         event.getModelRegistry().func_82595_a(entry.getKey(), new MCH_BakedModel(bakedmodel, entry.getValue()));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void registerRenderer(Item item, IItemModelRenderer renderer) {
/* 108 */     item.setTileEntityItemStackRenderer((TileEntityItemStackRenderer)CustomItemStackRenderer.getInstance());
/* 109 */     renderers.put(getInventoryModel(item), renderer);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void registerModelLocation(Item item) {
/* 114 */     registerModelLocation(item, 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void registerModelLocation(Item item, int meta) {
/* 119 */     ModelLoader.setCustomModelResourceLocation(item, meta, getInventoryModel(item));
/*     */   }
/*     */ 
/*     */   
/*     */   public static void registerLegacyModelLocation(IItemContent content) {
/* 124 */     ModelLoader.setCustomModelResourceLocation(content.getItem(), 0, new ModelResourceLocation(content
/* 125 */           .getItem().getRegistryName(), "mcheli_legacy"));
/*     */   }
/*     */ 
/*     */   
/*     */   private static ModelResourceLocation getInventoryModel(Item item) {
/* 130 */     return new ModelResourceLocation(item.getRegistryName(), "inventory");
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static IItemModelRenderer getRenderer(Item item) {
/* 136 */     return renderers.get(getInventoryModel(item));
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\__helper\client\MCH_ItemModelRenderers.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */