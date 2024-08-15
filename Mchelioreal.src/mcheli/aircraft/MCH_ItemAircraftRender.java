/*     */ package mcheli.aircraft;
/*     */ 
/*     */ import mcheli.MCH_ModelManager;
/*     */ import mcheli.__helper.client._IItemRenderer;
/*     */ import mcheli.wrapper.W_McClient;
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
/*     */ @Deprecated
/*     */ public class MCH_ItemAircraftRender
/*     */   implements _IItemRenderer
/*     */ {
/*     */   public boolean handleRenderType(ItemStack item, _IItemRenderer.ItemRenderType type) {
/*  25 */     if (item != null && item.func_77973_b() instanceof MCH_ItemAircraft) {
/*     */       
/*  27 */       MCH_AircraftInfo info = ((MCH_ItemAircraft)item.func_77973_b()).getAircraftInfo();
/*     */       
/*  29 */       if (info == null)
/*     */       {
/*  31 */         return false;
/*     */       }
/*     */       
/*  34 */       if (info != null && info.name.equalsIgnoreCase("mh-60l_dap"))
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  40 */         return (type == _IItemRenderer.ItemRenderType.EQUIPPED || type == _IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON || type == _IItemRenderer.ItemRenderType.ENTITY || type == _IItemRenderer.ItemRenderType.INVENTORY);
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  47 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean shouldUseRenderHelper(_IItemRenderer.ItemRenderType type, ItemStack item, _IItemRenderer.ItemRendererHelper helper) {
/*  56 */     return (type == _IItemRenderer.ItemRenderType.ENTITY || type == _IItemRenderer.ItemRenderType.INVENTORY);
/*     */   }
/*     */   
/*  59 */   float size = 0.1F;
/*  60 */   float x = 0.1F;
/*  61 */   float y = 0.1F;
/*  62 */   float z = 0.1F;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderItem(_IItemRenderer.ItemRenderType type, ItemStack item, Object... data) {
/*  69 */     GL11.glPushMatrix();
/*  70 */     GL11.glEnable(2884);
/*     */     
/*  72 */     W_McClient.MOD_bindTexture("textures/helicopters/mh-60l_dap.png");
/*     */ 
/*     */     
/*  75 */     switch (type) {
/*     */ 
/*     */       
/*     */       case ENTITY:
/*  79 */         GL11.glEnable(32826);
/*  80 */         GL11.glEnable(2903);
/*  81 */         GL11.glScalef(0.1F, 0.1F, 0.1F);
/*  82 */         MCH_ModelManager.render("helicopters", "mh-60l_dap");
/*  83 */         GL11.glDisable(32826);
/*     */         break;
/*     */       
/*     */       case EQUIPPED:
/*  87 */         GL11.glEnable(32826);
/*  88 */         GL11.glEnable(2903);
/*  89 */         GL11.glTranslatef(0.0F, 0.005F, -0.165F);
/*  90 */         GL11.glScalef(0.1F, 0.1F, 0.1F);
/*     */         
/*  92 */         GL11.glRotatef(-10.0F, 0.0F, 0.0F, 1.0F);
/*  93 */         GL11.glRotatef(90.0F, 0.0F, -1.0F, 0.0F);
/*  94 */         GL11.glRotatef(-50.0F, 1.0F, 0.0F, 0.0F);
/*  95 */         MCH_ModelManager.render("helicopters", "mh-60l_dap");
/*  96 */         GL11.glDisable(32826);
/*     */         break;
/*     */       
/*     */       case EQUIPPED_FIRST_PERSON:
/* 100 */         GL11.glEnable(32826);
/* 101 */         GL11.glEnable(2903);
/* 102 */         GL11.glTranslatef(0.3F, 0.5F, -0.5F);
/* 103 */         GL11.glScalef(0.1F, 0.1F, 0.1F);
/*     */         
/* 105 */         GL11.glRotatef(10.0F, 0.0F, 0.0F, 1.0F);
/* 106 */         GL11.glRotatef(140.0F, 0.0F, 1.0F, 0.0F);
/* 107 */         GL11.glRotatef(-10.0F, 1.0F, 0.0F, 0.0F);
/* 108 */         MCH_ModelManager.render("helicopters", "mh-60l_dap");
/* 109 */         GL11.glDisable(32826);
/*     */         break;
/*     */       
/*     */       case INVENTORY:
/* 113 */         GL11.glTranslatef(this.x, this.y, this.z);
/* 114 */         GL11.glScalef(this.size, this.size, this.size);
/* 115 */         MCH_ModelManager.render("helicopters", "mh-60l_dap");
/*     */         break;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 123 */     GL11.glPopMatrix();
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\aircraft\MCH_ItemAircraftRender.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */