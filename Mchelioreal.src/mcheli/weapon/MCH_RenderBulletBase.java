/*    */ package mcheli.weapon;
/*    */ 
/*    */ import mcheli.MCH_Color;
/*    */ import mcheli.wrapper.W_Block;
/*    */ import mcheli.wrapper.W_Entity;
/*    */ import mcheli.wrapper.W_Render;
/*    */ import mcheli.wrapper.W_WorldFunc;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.entity.Entity;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class MCH_RenderBulletBase<T extends W_Entity>
/*    */   extends W_Render<T>
/*    */ {
/*    */   protected MCH_RenderBulletBase(RenderManager renderManager) {
/* 24 */     super(renderManager);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void doRender(T e, double var2, double var4, double var6, float var8, float var9) {
/* 31 */     if (e instanceof MCH_EntityBaseBullet && ((MCH_EntityBaseBullet)e).getInfo() != null) {
/*    */       
/* 33 */       MCH_Color c = (((MCH_EntityBaseBullet)e).getInfo()).color;
/*    */       
/* 35 */       for (int y = 0; y < 3; y++) {
/*    */         
/* 37 */         Block b = W_WorldFunc.getBlock(((W_Entity)e).field_70170_p, (int)(((W_Entity)e).field_70165_t + 0.5D), (int)(((W_Entity)e).field_70163_u + 1.5D - y), (int)(((W_Entity)e).field_70161_v + 0.5D));
/*    */         
/* 39 */         if (b != null && b == W_Block.getWater()) {
/*    */           
/* 41 */           c = (((MCH_EntityBaseBullet)e).getInfo()).colorInWater;
/*    */           
/*    */           break;
/*    */         } 
/*    */       } 
/* 46 */       GL11.glColor4f(c.r, c.g, c.b, c.a);
/*    */     }
/*    */     else {
/*    */       
/* 50 */       GL11.glColor4f(0.75F, 0.75F, 0.75F, 1.0F);
/*    */     } 
/*    */     
/* 53 */     GL11.glAlphaFunc(516, 0.001F);
/*    */     
/* 55 */     GL11.glEnable(2884);
/*    */     
/* 57 */     GL11.glEnable(3042);
/* 58 */     int srcBlend = GL11.glGetInteger(3041);
/* 59 */     int dstBlend = GL11.glGetInteger(3040);
/* 60 */     GL11.glBlendFunc(770, 771);
/*    */     
/* 62 */     renderBullet(e, var2, var4, var6, var8, var9);
/*    */     
/* 64 */     GL11.glColor4f(0.75F, 0.75F, 0.75F, 1.0F);
/*    */     
/* 66 */     GL11.glBlendFunc(srcBlend, dstBlend);
/* 67 */     GL11.glDisable(3042);
/*    */   }
/*    */ 
/*    */   
/*    */   public void renderModel(MCH_EntityBaseBullet e) {
/* 72 */     MCH_BulletModel model = e.getBulletModel();
/* 73 */     if (model != null) {
/*    */       
/* 75 */       bindTexture("textures/bullets/" + model.name + ".png");
/* 76 */       model.model.renderAll();
/*    */     } 
/*    */   }
/*    */   
/*    */   public abstract void renderBullet(T paramT, double paramDouble1, double paramDouble2, double paramDouble3, float paramFloat1, float paramFloat2);
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\weapon\MCH_RenderBulletBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */