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
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class MCH_RenderASMissile
/*    */   extends MCH_RenderBulletBase<MCH_EntityBaseBullet>
/*    */ {
/* 20 */   public static final IRenderFactory<MCH_EntityBaseBullet> FACTORY = MCH_RenderASMissile::new;
/*    */ 
/*    */ 
/*    */   
/*    */   public MCH_RenderASMissile(RenderManager renderManager) {
/* 25 */     super(renderManager);
/* 26 */     this.field_76989_e = 0.5F;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void renderBullet(MCH_EntityBaseBullet entity, double posX, double posY, double posZ, float yaw, float partialTickTime) {
/* 34 */     if (entity instanceof MCH_EntityBaseBullet) {
/*    */       
/* 36 */       MCH_EntityBaseBullet bullet = entity;
/*    */       
/* 38 */       GL11.glPushMatrix();
/* 39 */       GL11.glTranslated(posX, posY, posZ);
/*    */       
/* 41 */       GL11.glRotatef(-entity.field_70177_z, 0.0F, 1.0F, 0.0F);
/* 42 */       GL11.glRotatef(-entity.field_70125_A, -1.0F, 0.0F, 0.0F);
/*    */       
/* 44 */       renderModel(bullet);
/*    */       
/* 46 */       GL11.glPopMatrix();
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ResourceLocation getEntityTexture(MCH_EntityBaseBullet entity) {
/* 54 */     return TEX_DEFAULT;
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\weapon\MCH_RenderASMissile.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */