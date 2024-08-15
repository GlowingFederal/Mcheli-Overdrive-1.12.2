/*    */ package mcheli.__helper.client;
/*    */ 
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ 
/*    */ @Deprecated
/*    */ @SideOnly(Side.CLIENT)
/*    */ public interface _IItemRenderer
/*    */ {
/*    */   boolean handleRenderType(ItemStack paramItemStack, ItemRenderType paramItemRenderType);
/*    */   
/*    */   boolean shouldUseRenderHelper(ItemRenderType paramItemRenderType, ItemStack paramItemStack, ItemRendererHelper paramItemRendererHelper);
/*    */   
/*    */   void renderItem(ItemRenderType paramItemRenderType, ItemStack paramItemStack, Object... paramVarArgs);
/*    */   
/*    */   @Deprecated
/*    */   public enum ItemRenderType
/*    */   {
/* 20 */     ENTITY,
/* 21 */     EQUIPPED,
/* 22 */     EQUIPPED_FIRST_PERSON,
/* 23 */     INVENTORY,
/* 24 */     FIRST_PERSON_MAP;
/*    */   }
/*    */   
/*    */   @Deprecated
/*    */   public enum ItemRendererHelper
/*    */   {
/* 30 */     ENTITY_ROTATION,
/* 31 */     ENTITY_BOBBING,
/* 32 */     EQUIPPED_BLOCK,
/* 33 */     BLOCK_3D,
/* 34 */     INVENTORY_BLOCK;
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\__helper\client\_IItemRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */