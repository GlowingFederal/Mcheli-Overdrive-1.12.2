/*    */ package mcheli.__helper.client.renderer.item;
/*    */ 
/*    */ import mcheli.MCH_ModelManager;
/*    */ import mcheli.tool.MCH_ItemWrench;
/*    */ import mcheli.wrapper.W_McClient;
/*    */ import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.EnumHand;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class BuiltInWrenchItemRenderer
/*    */   implements IItemModelRenderer
/*    */ {
/*    */   public boolean shouldRenderer(ItemStack itemStack, ItemCameraTransforms.TransformType transformType) {
/* 27 */     return (IItemModelRenderer.isFirstPerson(transformType) || IItemModelRenderer.isThirdPerson(transformType));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void renderItem(ItemStack itemStack, EntityLivingBase entity, ItemCameraTransforms.TransformType transformType, float partialTicks) {
/* 34 */     GL11.glPushMatrix();
/* 35 */     W_McClient.MOD_bindTexture("textures/wrench.png");
/*    */     
/* 37 */     if (IItemModelRenderer.isFirstPerson(transformType))
/*    */     {
/* 39 */       if (entity.func_184587_cr() && entity.func_184600_cs() == EnumHand.MAIN_HAND) {
/*    */         
/* 41 */         float f = MCH_ItemWrench.getUseAnimSmooth(itemStack, partialTicks);
/*    */         
/* 43 */         GL11.glRotatef(65.0F, 0.0F, 0.0F, 1.0F);
/* 44 */         GL11.glRotatef(f + 20.0F, 1.0F, 0.0F, 0.0F);
/*    */       } 
/*    */     }
/*    */     
/* 48 */     MCH_ModelManager.render("wrench");
/* 49 */     GL11.glPopMatrix();
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\__helper\client\renderer\item\BuiltInWrenchItemRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */