/*    */ package mcheli.__helper.client.renderer.item;
/*    */ 
/*    */ import mcheli.MCH_ModelManager;
/*    */ import mcheli.wrapper.W_McClient;
/*    */ import net.minecraft.client.renderer.GlStateManager;
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
/*    */ public class BuiltInDraftingTableItemRenderer
/*    */   implements IItemModelRenderer
/*    */ {
/*    */   public boolean shouldRenderer(ItemStack itemStack, ItemCameraTransforms.TransformType transformType) {
/* 26 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void renderItem(ItemStack itemStack, EntityLivingBase entityLivingBase, ItemCameraTransforms.TransformType transformType, float partialTicks) {
/* 34 */     GlStateManager.func_179094_E();
/* 35 */     W_McClient.MOD_bindTexture("textures/blocks/drafting_table.png");
/*    */ 
/*    */     
/* 38 */     GlStateManager.func_179091_B();
/*    */     
/* 40 */     switch (transformType) {
/*    */       
/*    */       case GROUND:
/* 43 */         GL11.glTranslatef(0.0F, 0.5F, 0.0F);
/* 44 */         GL11.glScalef(1.5F, 1.5F, 1.5F);
/*    */         break;
/*    */ 
/*    */ 
/*    */ 
/*    */       
/*    */       case GUI:
/*    */       case FIXED:
/* 52 */         GlStateManager.func_179109_b(0.0F, -0.5F, 0.0F);
/* 53 */         GlStateManager.func_179152_a(0.75F, 0.75F, 0.75F);
/*    */         break;
/*    */       
/*    */       case THIRD_PERSON_LEFT_HAND:
/*    */       case THIRD_PERSON_RIGHT_HAND:
/* 58 */         GL11.glTranslatef(0.0F, 0.0F, 0.5F);
/* 59 */         GL11.glScalef(1.0F, 1.0F, 1.0F);
/*    */         break;
/*    */       
/*    */       case FIRST_PERSON_LEFT_HAND:
/*    */       case FIRST_PERSON_RIGHT_HAND:
/* 64 */         GL11.glTranslatef(0.75F, 0.0F, 0.0F);
/* 65 */         GL11.glScalef(1.0F, 1.0F, 1.0F);
/* 66 */         GL11.glRotatef(90.0F, 0.0F, -1.0F, 0.0F);
/*    */         break;
/*    */     } 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 73 */     MCH_ModelManager.render("blocks", "drafting_table");
/*    */ 
/*    */     
/* 76 */     GlStateManager.func_179121_F();
/*    */ 
/*    */ 
/*    */     
/* 80 */     GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
/* 81 */     GlStateManager.func_179147_l();
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\__helper\client\renderer\item\BuiltInDraftingTableItemRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */