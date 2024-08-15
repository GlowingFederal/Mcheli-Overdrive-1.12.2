/*    */ package mcheli.__helper;
/*    */ 
/*    */ import com.google.common.collect.Sets;
/*    */ import java.util.Set;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemBlock;
/*    */ import net.minecraftforge.event.RegistryEvent;
/*    */ import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ import net.minecraftforge.fml.common.registry.ForgeRegistries;
/*    */ import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
/*    */ import net.minecraftforge.registries.IForgeRegistryEntry;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @ObjectHolder("mcheli")
/*    */ @EventBusSubscriber(modid = "mcheli")
/*    */ public class MCH_Items
/*    */ {
/* 28 */   private static Set<Item> registryWrapper = Sets.newLinkedHashSet();
/*    */ 
/*    */   
/*    */   @SubscribeEvent
/*    */   static void onItemRegistryEvent(RegistryEvent.Register<Item> event) {
/* 33 */     for (Item item : registryWrapper)
/*    */     {
/* 35 */       event.getRegistry().register((IForgeRegistryEntry)item);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public static Item register(Item item, String name) {
/* 41 */     registryWrapper.add(item.setRegistryName(MCH_Utils.suffix(name)));
/* 42 */     return item;
/*    */   }
/*    */ 
/*    */   
/*    */   public static ItemBlock registerBlock(Block block) {
/* 47 */     ItemBlock itemBlock = new ItemBlock(block);
/* 48 */     registryWrapper.add(itemBlock.setRegistryName(block.getRegistryName()));
/* 49 */     return itemBlock;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public static Item get(String name) {
/* 55 */     return (Item)ForgeRegistries.ITEMS.getValue(MCH_Utils.suffix(name));
/*    */   }
/*    */ 
/*    */   
/*    */   public static String getName(Item item) {
/* 60 */     return ForgeRegistries.ITEMS.getKey((IForgeRegistryEntry)item).toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\__helper\MCH_Items.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */