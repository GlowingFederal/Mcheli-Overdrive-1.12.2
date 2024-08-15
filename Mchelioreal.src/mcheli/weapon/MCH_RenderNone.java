/*    */ package mcheli.weapon;
/*    */ 
/*    */ import mcheli.wrapper.W_Entity;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraftforge.fml.client.registry.IRenderFactory;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class MCH_RenderNone
/*    */   extends MCH_RenderBulletBase<W_Entity>
/*    */ {
/* 20 */   public static final IRenderFactory<W_Entity> FACTORY = MCH_RenderNone::new;
/*    */ 
/*    */   
/*    */   protected MCH_RenderNone(RenderManager renderManager) {
/* 24 */     super(renderManager);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void renderBullet(W_Entity entity, double posX, double posY, double posZ, float yaw, float partialTickTime) {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ResourceLocation getEntityTexture(W_Entity entity) {
/* 37 */     return TEX_DEFAULT;
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\weapon\MCH_RenderNone.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */