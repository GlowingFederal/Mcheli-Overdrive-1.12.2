/*    */ package mcheli.__helper.client.renderer.item;
/*    */ 
/*    */ import mcheli.MCH_ModelManager;
/*    */ import mcheli.tool.rangefinder.MCH_ItemRangeFinder;
/*    */ import mcheli.wrapper.W_McClient;
/*    */ import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.player.EntityPlayer;
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
/*    */ public class BuiltInRangeFinderItemRenderer
/*    */   implements IItemModelRenderer
/*    */ {
/*    */   public boolean shouldRenderer(ItemStack itemStack, ItemCameraTransforms.TransformType transformType) {
/* 28 */     return (IItemModelRenderer.isFirstPerson(transformType) || IItemModelRenderer.isThirdPerson(transformType) || transformType == ItemCameraTransforms.TransformType.GROUND);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void renderItem(ItemStack itemStack, EntityLivingBase entity, ItemCameraTransforms.TransformType transformType, float partialTicks) {
/* 36 */     GL11.glPushMatrix();
/* 37 */     W_McClient.MOD_bindTexture("textures/rangefinder.png");
/*    */     
/* 39 */     boolean flag = true;
/*    */     
/* 41 */     if (IItemModelRenderer.isFirstPerson(transformType)) {
/*    */       
/* 43 */       flag = (entity instanceof EntityPlayer && !MCH_ItemRangeFinder.isUsingScope((EntityPlayer)entity));
/*    */       
/* 45 */       if (entity.func_184587_cr() && entity.func_184600_cs() == EnumHand.MAIN_HAND)
/*    */       {
/* 47 */         GL11.glTranslated(0.6563000082969666D, 0.34380000829696655D, 0.009999999776482582D);
/*    */       }
/*    */     } 
/*    */     
/* 51 */     if (flag)
/*    */     {
/* 53 */       MCH_ModelManager.render("rangefinder");
/*    */     }
/*    */     
/* 56 */     GL11.glPopMatrix();
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\__helper\client\renderer\item\BuiltInRangeFinderItemRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */