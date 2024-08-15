/*     */ package mcheli.tool.rangefinder;
/*     */ 
/*     */ import mcheli.MCH_ModelManager;
/*     */ import mcheli.__helper.client._IItemRenderer;
/*     */ import mcheli.wrapper.W_McClient;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Deprecated
/*     */ public class MCH_ItemRenderRangeFinder
/*     */   implements _IItemRenderer
/*     */ {
/*     */   public boolean handleRenderType(ItemStack item, _IItemRenderer.ItemRenderType type) {
/*  29 */     return (type == _IItemRenderer.ItemRenderType.EQUIPPED || type == _IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON || type == _IItemRenderer.ItemRenderType.ENTITY);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean shouldUseRenderHelper(_IItemRenderer.ItemRenderType type, ItemStack item, _IItemRenderer.ItemRendererHelper helper) {
/*  42 */     return (type == _IItemRenderer.ItemRenderType.EQUIPPED || type == _IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON || type == _IItemRenderer.ItemRenderType.ENTITY);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderItem(_IItemRenderer.ItemRenderType type, ItemStack item, Object... data) {
/*  52 */     GL11.glPushMatrix();
/*     */     
/*  54 */     W_McClient.MOD_bindTexture("textures/rangefinder.png");
/*     */     
/*  56 */     float size = 1.0F;
/*     */ 
/*     */     
/*  59 */     switch (type) {
/*     */ 
/*     */       
/*     */       case ENTITY:
/*  63 */         size = 2.2F;
/*  64 */         GL11.glScalef(size, size, size);
/*  65 */         GL11.glRotatef(-130.0F, 0.0F, 1.0F, 0.0F);
/*  66 */         GL11.glRotatef(70.0F, 1.0F, 0.0F, 0.0F);
/*  67 */         GL11.glRotatef(5.0F, 0.0F, 0.0F, 1.0F);
/*  68 */         GL11.glTranslatef(0.0F, 0.0F, -0.0F);
/*  69 */         MCH_ModelManager.render("rangefinder");
/*     */         break;
/*     */       
/*     */       case EQUIPPED:
/*  73 */         size = 2.2F;
/*  74 */         GL11.glScalef(size, size, size);
/*  75 */         GL11.glRotatef(-130.0F, 0.0F, 1.0F, 0.0F);
/*  76 */         GL11.glRotatef(70.0F, 1.0F, 0.0F, 0.0F);
/*  77 */         GL11.glRotatef(5.0F, 0.0F, 0.0F, 1.0F);
/*     */ 
/*     */         
/*  80 */         if ((Minecraft.func_71410_x()).field_71439_g.func_184612_cw() > 0) {
/*     */           
/*  82 */           GL11.glTranslatef(0.4F, -0.35F, -0.3F);
/*     */         }
/*     */         else {
/*     */           
/*  86 */           GL11.glTranslatef(0.2F, -0.35F, -0.3F);
/*     */         } 
/*     */         
/*  89 */         MCH_ModelManager.render("rangefinder");
/*     */         break;
/*     */       
/*     */       case EQUIPPED_FIRST_PERSON:
/*  93 */         if (!MCH_ItemRangeFinder.isUsingScope((EntityPlayer)(Minecraft.func_71410_x()).field_71439_g)) {
/*     */           
/*  95 */           size = 2.2F;
/*  96 */           GL11.glScalef(size, size, size);
/*  97 */           GL11.glRotatef(-210.0F, 0.0F, 1.0F, 0.0F);
/*  98 */           GL11.glRotatef(-10.0F, 1.0F, 0.0F, 0.0F);
/*  99 */           GL11.glRotatef(-10.0F, 0.0F, 0.0F, 1.0F);
/* 100 */           GL11.glTranslatef(0.06F, 0.53F, -0.1F);
/* 101 */           MCH_ModelManager.render("rangefinder");
/*     */         } 
/*     */         break;
/*     */     } 
/*     */ 
/*     */     
/* 107 */     GL11.glPopMatrix();
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\tool\rangefinder\MCH_ItemRenderRangeFinder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */