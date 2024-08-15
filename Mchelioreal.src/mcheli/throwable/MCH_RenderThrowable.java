/*    */ package mcheli.throwable;
/*    */ 
/*    */ import mcheli.wrapper.W_Render;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraftforge.fml.client.registry.IRenderFactory;
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
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class MCH_RenderThrowable
/*    */   extends W_Render<MCH_EntityThrowable>
/*    */ {
/* 23 */   public static final IRenderFactory<MCH_EntityThrowable> FACTORY = MCH_RenderThrowable::new;
/*    */ 
/*    */ 
/*    */   
/*    */   public MCH_RenderThrowable(RenderManager renderManager) {
/* 28 */     super(renderManager);
/* 29 */     this.field_76989_e = 0.0F;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void doRender(MCH_EntityThrowable entity, double posX, double posY, double posZ, float par8, float tickTime) {
/* 36 */     MCH_EntityThrowable throwable = entity;
/* 37 */     MCH_ThrowableInfo info = throwable.getInfo();
/*    */     
/* 39 */     if (info == null) {
/*    */       return;
/*    */     }
/*    */ 
/*    */     
/* 44 */     GL11.glPushMatrix();
/* 45 */     GL11.glTranslated(posX, posY, posZ);
/* 46 */     GL11.glRotatef(entity.field_70177_z, 0.0F, -1.0F, 0.0F);
/* 47 */     GL11.glRotatef(entity.field_70125_A, 1.0F, 0.0F, 0.0F);
/*    */ 
/*    */     
/* 50 */     setCommonRenderParam(true, entity.func_70070_b());
/*    */     
/* 52 */     if (info.model != null) {
/*    */       
/* 54 */       bindTexture("textures/throwable/" + info.name + ".png");
/*    */       
/* 56 */       info.model.renderAll();
/*    */     } 
/*    */     
/* 59 */     restoreCommonRenderParam();
/*    */     
/* 61 */     GL11.glPopMatrix();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ResourceLocation getEntityTexture(MCH_EntityThrowable entity) {
/* 68 */     return TEX_DEFAULT;
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\throwable\MCH_RenderThrowable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */