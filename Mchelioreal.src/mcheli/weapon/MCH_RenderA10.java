/*    */ package mcheli.weapon;
/*    */ 
/*    */ import mcheli.MCH_ModelManager;
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
/*    */ public class MCH_RenderA10
/*    */   extends MCH_RenderBulletBase<MCH_EntityA10>
/*    */ {
/* 22 */   public static final IRenderFactory<MCH_EntityA10> FACTORY = MCH_RenderA10::new;
/*    */ 
/*    */ 
/*    */   
/*    */   public MCH_RenderA10(RenderManager renderManager) {
/* 27 */     super(renderManager);
/* 28 */     this.field_76989_e = 10.5F;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void renderBullet(MCH_EntityA10 e, double posX, double posY, double posZ, float par8, float tickTime) {
/* 35 */     if (!(e instanceof MCH_EntityA10)) {
/*    */       return;
/*    */     }
/*    */     
/* 39 */     if (!e.isRender()) {
/*    */       return;
/*    */     }
/*    */     
/* 43 */     GL11.glPushMatrix();
/* 44 */     GL11.glTranslated(posX, posY, posZ);
/*    */     
/* 46 */     float yaw = -(e.field_70126_B + (e.field_70177_z - e.field_70126_B) * tickTime);
/* 47 */     float pitch = -(e.field_70127_C + (e.field_70125_A - e.field_70127_C) * tickTime);
/* 48 */     GL11.glRotatef(yaw, 0.0F, 1.0F, 0.0F);
/* 49 */     GL11.glRotatef(pitch, 1.0F, 0.0F, 0.0F);
/*    */     
/* 51 */     bindTexture("textures/bullets/a10.png");
/* 52 */     MCH_ModelManager.render("a-10");
/*    */     
/* 54 */     GL11.glPopMatrix();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ResourceLocation getEntityTexture(MCH_EntityA10 entity) {
/* 61 */     return TEX_DEFAULT;
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\weapon\MCH_RenderA10.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */