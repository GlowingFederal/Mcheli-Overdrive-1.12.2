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
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class MCH_RenderBomb
/*    */   extends MCH_RenderBulletBase<MCH_EntityBomb> {
/* 15 */   public static final IRenderFactory<MCH_EntityBomb> FACTORY = MCH_RenderBomb::new;
/*    */ 
/*    */ 
/*    */   
/*    */   public MCH_RenderBomb(RenderManager renderManager) {
/* 20 */     super(renderManager);
/* 21 */     this.field_76989_e = 0.0F;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void renderBullet(MCH_EntityBomb entity, double posX, double posY, double posZ, float yaw, float partialTickTime) {
/* 29 */     if (!(entity instanceof MCH_EntityBomb)) {
/*    */       return;
/*    */     }
/*    */     
/* 33 */     MCH_EntityBomb bomb = entity;
/* 34 */     if (bomb.getInfo() == null) {
/*    */       return;
/*    */     }
/*    */     
/* 38 */     GL11.glPushMatrix();
/*    */     
/* 40 */     GL11.glTranslated(posX, posY, posZ);
/* 41 */     GL11.glRotatef(-entity.field_70177_z, 0.0F, 1.0F, 0.0F);
/* 42 */     GL11.glRotatef(-entity.field_70125_A, -1.0F, 0.0F, 0.0F);
/*    */     
/* 44 */     if (bomb.isBomblet > 0 || (bomb.getInfo()).bomblet <= 0 || (bomb.getInfo()).bombletSTime > 0)
/*    */     {
/*    */       
/* 47 */       renderModel(bomb);
/*    */     }
/*    */     
/* 50 */     GL11.glPopMatrix();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ResourceLocation getEntityTexture(MCH_EntityBomb entity) {
/* 57 */     return TEX_DEFAULT;
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\weapon\MCH_RenderBomb.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */