/*    */ package mcheli.gltd;
/*    */ 
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
/*    */ 
/*    */ @Deprecated
/*    */ public class MCH_ItemGLTDRender
/*    */   implements _IItemRenderer
/*    */ {
/*    */   public boolean handleRenderType(ItemStack item, _IItemRenderer.ItemRenderType type) {
/* 27 */     return (type == _IItemRenderer.ItemRenderType.EQUIPPED || type == _IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON || type == _IItemRenderer.ItemRenderType.ENTITY);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean shouldUseRenderHelper(_IItemRenderer.ItemRenderType type, ItemStack item, _IItemRenderer.ItemRendererHelper helper) {
/* 38 */     return (type == _IItemRenderer.ItemRenderType.ENTITY);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void renderItem(_IItemRenderer.ItemRenderType type, ItemStack item, Object... data) {
/* 46 */     GL11.glPushMatrix();
/* 47 */     GL11.glEnable(2884);
/* 48 */     W_McClient.MOD_bindTexture("textures/gltd.png");
/*    */ 
/*    */     
/* 51 */     switch (type) {
/*    */ 
/*    */       
/*    */       case ENTITY:
/* 55 */         GL11.glEnable(32826);
/* 56 */         GL11.glEnable(2903);
/* 57 */         GL11.glScalef(1.0F, 1.0F, 1.0F);
/* 58 */         MCH_RenderGLTD.model.renderAll();
/* 59 */         GL11.glDisable(32826);
/*    */         break;
/*    */       
/*    */       case EQUIPPED:
/* 63 */         GL11.glEnable(32826);
/* 64 */         GL11.glEnable(2903);
/* 65 */         GL11.glTranslatef(0.0F, 0.005F, -0.165F);
/* 66 */         GL11.glRotatef(-10.0F, 0.0F, 0.0F, 1.0F);
/* 67 */         GL11.glRotatef(-10.0F, 1.0F, 0.0F, 0.0F);
/* 68 */         MCH_RenderGLTD.model.renderAll();
/* 69 */         GL11.glDisable(32826);
/*    */         break;
/*    */       
/*    */       case EQUIPPED_FIRST_PERSON:
/* 73 */         GL11.glEnable(32826);
/* 74 */         GL11.glEnable(2903);
/* 75 */         GL11.glTranslatef(0.3F, 0.5F, -0.5F);
/* 76 */         GL11.glScalef(0.5F, 0.5F, 0.5F);
/* 77 */         GL11.glRotatef(10.0F, 0.0F, 0.0F, 1.0F);
/* 78 */         GL11.glRotatef(50.0F, 0.0F, 1.0F, 0.0F);
/* 79 */         GL11.glRotatef(-10.0F, 1.0F, 0.0F, 0.0F);
/* 80 */         MCH_RenderGLTD.model.renderAll();
/* 81 */         GL11.glDisable(32826);
/*    */         break;
/*    */     } 
/*    */     
/* 85 */     GL11.glPopMatrix();
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\gltd\MCH_ItemGLTDRender.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */