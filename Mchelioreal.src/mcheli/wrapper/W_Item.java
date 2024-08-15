/*    */ package mcheli.wrapper;
/*    */ 
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraftforge.fml.common.registry.ForgeRegistries;
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
/*    */ 
/*    */ public class W_Item
/*    */   extends Item
/*    */ {
/*    */   public W_Item(int par1) {}
/*    */   
/*    */   public W_Item() {}
/*    */   
/*    */   public static int getIdFromItem(Item i) {
/* 26 */     return (i == null) ? 0 : field_150901_e.func_148757_b(i);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static Item getItemById(int i) {
/* 38 */     return Item.func_150899_d(i);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static Item getItemByName(String nm) {
/* 48 */     return (Item)ForgeRegistries.ITEMS.getValue(new ResourceLocation(nm));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static String getNameForItem(Item item) {
/* 54 */     return ForgeRegistries.ITEMS.getKey((IForgeRegistryEntry)item).toString();
/*    */   }
/*    */ 
/*    */   
/*    */   public static Item getItemFromBlock(Block block) {
/* 59 */     return Item.func_150898_a(block);
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\wrapper\W_Item.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */