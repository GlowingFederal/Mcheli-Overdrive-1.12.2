/*    */ package mcheli;
/*    */ 
/*    */ import mcheli.__helper.client._IItemRenderer;
/*    */ import net.minecraft.item.ItemStack;
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
/*    */ 
/*    */ 
/*    */ @Deprecated
/*    */ public class MCH_InvisibleItemRender
/*    */   implements _IItemRenderer
/*    */ {
/*    */   public boolean handleRenderType(ItemStack item, _IItemRenderer.ItemRenderType type) {
/* 22 */     return (type == _IItemRenderer.ItemRenderType.EQUIPPED || type == _IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean shouldUseRenderHelper(_IItemRenderer.ItemRenderType type, ItemStack item, _IItemRenderer.ItemRendererHelper helper) {
/* 31 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean useCurrentWeapon() {
/* 36 */     return false;
/*    */   }
/*    */   
/*    */   public void renderItem(_IItemRenderer.ItemRenderType type, ItemStack item, Object... data) {}
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\MCH_InvisibleItemRender.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */