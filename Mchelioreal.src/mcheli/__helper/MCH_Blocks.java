/*    */ package mcheli.__helper;
/*    */ 
/*    */ import com.google.common.collect.Sets;
/*    */ import java.util.Set;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraftforge.event.RegistryEvent;
/*    */ import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ import net.minecraftforge.registries.IForgeRegistryEntry;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @EventBusSubscriber(modid = "mcheli")
/*    */ public class MCH_Blocks
/*    */ {
/* 21 */   private static Set<Block> registryWrapper = Sets.newLinkedHashSet();
/*    */ 
/*    */   
/*    */   @SubscribeEvent
/*    */   static void onBlockRegisterEvent(RegistryEvent.Register<Block> event) {
/* 26 */     for (Block block : registryWrapper)
/*    */     {
/* 28 */       event.getRegistry().register((IForgeRegistryEntry)block);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public static void register(Block block, String name) {
/* 34 */     registryWrapper.add(block.setRegistryName(MCH_Utils.suffix(name)));
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\__helper\MCH_Blocks.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */