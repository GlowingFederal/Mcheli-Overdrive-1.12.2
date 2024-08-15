/*    */ package mcheli.tool;
/*    */ 
/*    */ import mcheli.MCH_ModelManager;
/*    */ import mcheli.__helper.client._IItemRenderer;
/*    */ import mcheli.wrapper.W_McClient;
/*    */ import net.minecraft.entity.player.EntityPlayer;
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
/*    */ public class MCH_ItemRenderWrench
/*    */   implements _IItemRenderer
/*    */ {
/*    */   public boolean handleRenderType(ItemStack item, _IItemRenderer.ItemRenderType type) {
/* 28 */     return (type == _IItemRenderer.ItemRenderType.EQUIPPED || type == _IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON);
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
/* 39 */     return (type == _IItemRenderer.ItemRenderType.EQUIPPED || type == _IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void renderItem(_IItemRenderer.ItemRenderType type, ItemStack item, Object... data) {
/*    */     int useFrame;
/* 48 */     GL11.glPushMatrix();
/* 49 */     W_McClient.MOD_bindTexture("textures/wrench.png");
/*    */     
/* 51 */     float size = 1.0F;
/*    */     
/* 53 */     switch (type) {
/*    */ 
/*    */       
/*    */       case ENTITY:
/* 57 */         size = 2.2F;
/* 58 */         GL11.glScalef(size, size, size);
/* 59 */         GL11.glRotatef(-130.0F, 0.0F, 1.0F, 0.0F);
/* 60 */         GL11.glRotatef(-40.0F, 1.0F, 0.0F, 0.0F);
/* 61 */         GL11.glTranslatef(0.1F, 0.5F, -0.1F);
/*    */         break;
/*    */       
/*    */       case EQUIPPED:
/* 65 */         useFrame = MCH_ItemWrench.getUseAnimCount(item) - 8;
/* 66 */         if (useFrame < 0)
/* 67 */           useFrame = -useFrame; 
/* 68 */         size = 2.2F;
/*    */         
/* 70 */         if (data.length >= 2 && data[1] instanceof EntityPlayer) {
/*    */           
/* 72 */           EntityPlayer player = (EntityPlayer)data[1];
/*    */           
/* 74 */           if (player.func_184605_cv() > 0) {
/*    */             
/* 76 */             float x = 0.8567F;
/* 77 */             float y = -0.0298F;
/* 78 */             float z = 0.0F;
/* 79 */             GL11.glTranslatef(-x, -y, -z);
/* 80 */             GL11.glRotatef((useFrame + 20), 1.0F, 0.0F, 0.0F);
/* 81 */             GL11.glTranslatef(x, y, z);
/*    */           } 
/*    */         } 
/*    */         
/* 85 */         GL11.glScalef(size, size, size);
/* 86 */         GL11.glRotatef(-200.0F, 0.0F, 1.0F, 0.0F);
/* 87 */         GL11.glRotatef(-60.0F, 1.0F, 0.0F, 0.0F);
/* 88 */         GL11.glRotatef(0.0F, 0.0F, 0.0F, 1.0F);
/* 89 */         GL11.glTranslatef(-0.2F, 0.5F, -0.1F);
/*    */         break;
/*    */     } 
/*    */     
/* 93 */     MCH_ModelManager.render("wrench");
/* 94 */     GL11.glPopMatrix();
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\tool\MCH_ItemRenderWrench.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */