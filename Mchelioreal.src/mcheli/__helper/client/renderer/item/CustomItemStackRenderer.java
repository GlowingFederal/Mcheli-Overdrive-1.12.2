/*    */ package mcheli.__helper.client.renderer.item;
/*    */ 
/*    */ import mcheli.__helper.client.MCH_ItemModelRenderers;
/*    */ import mcheli.__helper.client.model.PooledModelParameters;
/*    */ import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraftforge.client.model.animation.Animation;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class CustomItemStackRenderer
/*    */   extends TileEntityItemStackRenderer
/*    */ {
/*    */   private static CustomItemStackRenderer instance;
/*    */   
/*    */   public void func_192838_a(ItemStack p_192838_1_, float partialTicks) {
/* 25 */     IItemModelRenderer renderer = MCH_ItemModelRenderers.getRenderer(p_192838_1_.func_77973_b());
/*    */     
/* 27 */     if (renderer != null)
/*    */     {
/* 29 */       renderer.renderItem(p_192838_1_, PooledModelParameters.getEntity(), 
/* 30 */           PooledModelParameters.getTransformType(), Animation.getPartialTickTime());
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public static CustomItemStackRenderer getInstance() {
/* 36 */     if (instance == null)
/*    */     {
/* 38 */       instance = new CustomItemStackRenderer();
/*    */     }
/*    */     
/* 41 */     return instance;
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\__helper\client\renderer\item\CustomItemStackRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */