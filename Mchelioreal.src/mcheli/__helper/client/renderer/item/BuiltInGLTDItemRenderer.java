/*    */ package mcheli.__helper.client.renderer.item;
/*    */ 
/*    */ import mcheli.gltd.MCH_RenderGLTD;
/*    */ import mcheli.wrapper.W_McClient;
/*    */ import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.item.ItemStack;
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
/*    */ public class BuiltInGLTDItemRenderer
/*    */   implements IItemModelRenderer
/*    */ {
/*    */   public boolean shouldRenderer(ItemStack itemStack, ItemCameraTransforms.TransformType transformType) {
/* 25 */     return (IItemModelRenderer.isFirstPerson(transformType) || IItemModelRenderer.isThirdPerson(transformType) || transformType == ItemCameraTransforms.TransformType.GROUND);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void renderItem(ItemStack itemStack, EntityLivingBase entityLivingBase, ItemCameraTransforms.TransformType transformType, float partialTicks) {
/* 33 */     GL11.glPushMatrix();
/* 34 */     GL11.glEnable(2884);
/* 35 */     W_McClient.MOD_bindTexture("textures/gltd.png");
/*    */     
/* 37 */     GL11.glEnable(32826);
/* 38 */     GL11.glEnable(2903);
/* 39 */     MCH_RenderGLTD.model.renderAll();
/* 40 */     GL11.glDisable(32826);
/*    */     
/* 42 */     GL11.glPopMatrix();
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\__helper\client\renderer\item\BuiltInGLTDItemRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */