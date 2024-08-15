/*    */ package mcheli.block;
/*    */ 
/*    */ import mcheli.MCH_ModelManager;
/*    */ import mcheli.__helper.client._IItemRenderer;
/*    */ import mcheli.wrapper.W_McClient;
/*    */ import net.minecraft.item.ItemStack;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Deprecated
/*    */ public class MCH_DraftingTableItemRender
/*    */   implements _IItemRenderer
/*    */ {
/*    */   public boolean handleRenderType(ItemStack item, _IItemRenderer.ItemRenderType type) {
/* 27 */     switch (type) {
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */       
/*    */       case ENTITY:
/*    */       case EQUIPPED:
/*    */       case EQUIPPED_FIRST_PERSON:
/*    */       case INVENTORY:
/* 37 */         return true;
/*    */     } 
/*    */     
/* 40 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean shouldUseRenderHelper(_IItemRenderer.ItemRenderType type, ItemStack item, _IItemRenderer.ItemRendererHelper helper) {
/* 48 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void renderItem(_IItemRenderer.ItemRenderType type, ItemStack item, Object... data) {
/* 56 */     GL11.glPushMatrix();
/* 57 */     W_McClient.MOD_bindTexture("textures/blocks/drafting_table.png");
/*    */     
/* 59 */     GL11.glEnable(32826);
/*    */ 
/*    */     
/* 62 */     switch (type) {
/*    */ 
/*    */       
/*    */       case ENTITY:
/* 66 */         GL11.glTranslatef(0.0F, 0.5F, 0.0F);
/* 67 */         GL11.glScalef(1.5F, 1.5F, 1.5F);
/*    */         break;
/*    */ 
/*    */ 
/*    */       
/*    */       case INVENTORY:
/* 73 */         GL11.glTranslatef(0.0F, -0.5F, 0.0F);
/* 74 */         GL11.glScalef(0.75F, 0.75F, 0.75F);
/*    */         break;
/*    */ 
/*    */       
/*    */       case EQUIPPED:
/* 79 */         GL11.glTranslatef(0.0F, 0.0F, 0.5F);
/* 80 */         GL11.glScalef(1.0F, 1.0F, 1.0F);
/*    */         break;
/*    */ 
/*    */       
/*    */       case EQUIPPED_FIRST_PERSON:
/* 85 */         GL11.glTranslatef(0.75F, 0.0F, 0.0F);
/* 86 */         GL11.glScalef(1.0F, 1.0F, 1.0F);
/* 87 */         GL11.glRotatef(90.0F, 0.0F, -1.0F, 0.0F);
/*    */         break;
/*    */     } 
/*    */     
/* 91 */     MCH_ModelManager.render("blocks", "drafting_table");
/*    */     
/* 93 */     GL11.glPopMatrix();
/*    */     
/* 95 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 96 */     GL11.glEnable(3042);
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\block\MCH_DraftingTableItemRender.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */