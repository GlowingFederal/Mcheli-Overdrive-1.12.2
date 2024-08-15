/*    */ package mcheli.weapon;
/*    */ 
/*    */ import mcheli.wrapper.W_Entity;
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
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class MCH_RenderBullet
/*    */   extends MCH_RenderBulletBase<MCH_EntityBaseBullet>
/*    */ {
/* 21 */   public static final IRenderFactory<MCH_EntityBaseBullet> FACTORY = MCH_RenderBullet::new;
/*    */ 
/*    */   
/*    */   protected MCH_RenderBullet(RenderManager renderManager) {
/* 25 */     super(renderManager);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void renderBullet(MCH_EntityBaseBullet entity, double posX, double posY, double posZ, float yaw, float tickTime) {
/* 33 */     MCH_EntityBaseBullet blt = entity;
/*    */     
/* 35 */     GL11.glPushMatrix();
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 40 */     GL11.glTranslated(posX, posY, posZ);
/* 41 */     GL11.glRotatef(-entity.field_70177_z, 0.0F, 1.0F, 0.0F);
/* 42 */     GL11.glRotatef(entity.field_70125_A, 1.0F, 0.0F, 0.0F);
/*    */     
/* 44 */     renderModel(blt);
/*    */     
/* 46 */     GL11.glPopMatrix();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ResourceLocation getEntityTexture(MCH_EntityBaseBullet entity) {
/* 53 */     return TEX_DEFAULT;
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\weapon\MCH_RenderBullet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */