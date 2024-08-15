/*    */ package mcheli.chain;
/*    */ 
/*    */ import mcheli.MCH_Lib;
/*    */ import mcheli.MCH_ModelManager;
/*    */ import mcheli.wrapper.W_Render;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraft.util.math.Vec3d;
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
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class MCH_RenderChain
/*    */   extends W_Render<MCH_EntityChain>
/*    */ {
/* 26 */   public static final IRenderFactory<MCH_EntityChain> FACTORY = MCH_RenderChain::new;
/*    */ 
/*    */   
/*    */   public MCH_RenderChain(RenderManager renderManager) {
/* 30 */     super(renderManager);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void doRender(MCH_EntityChain e, double posX, double posY, double posZ, float par8, float par9) {
/* 37 */     if (!(e instanceof MCH_EntityChain)) {
/*    */       return;
/*    */     }
/* 40 */     MCH_EntityChain chain = e;
/*    */     
/* 42 */     if (chain.towedEntity == null || chain.towEntity == null) {
/*    */       return;
/*    */     }
/*    */ 
/*    */     
/* 47 */     GL11.glPushMatrix();
/*    */     
/* 49 */     GL11.glEnable(2884);
/* 50 */     GL11.glColor4f(0.5F, 0.5F, 0.5F, 1.0F);
/*    */ 
/*    */ 
/*    */     
/* 54 */     GL11.glTranslated(chain.towedEntity.field_70142_S - TileEntityRendererDispatcher.field_147554_b, chain.towedEntity.field_70137_T - TileEntityRendererDispatcher.field_147555_c, chain.towedEntity.field_70136_U - TileEntityRendererDispatcher.field_147552_d);
/*    */ 
/*    */ 
/*    */     
/* 58 */     bindTexture("textures/chain.png");
/*    */     
/* 60 */     double dx = chain.towEntity.field_70142_S - chain.towedEntity.field_70142_S;
/* 61 */     double dy = chain.towEntity.field_70137_T - chain.towedEntity.field_70137_T;
/* 62 */     double dz = chain.towEntity.field_70136_U - chain.towedEntity.field_70136_U;
/* 63 */     double diff = Math.sqrt(dx * dx + dy * dy + dz * dz);
/*    */     
/* 65 */     double x = dx * 0.949999988079071D / diff;
/* 66 */     double y = dy * 0.949999988079071D / diff;
/* 67 */     double z = dz * 0.949999988079071D / diff;
/*    */     
/* 69 */     while (diff > 0.949999988079071D) {
/*    */       
/* 71 */       GL11.glTranslated(x, y, z);
/* 72 */       GL11.glPushMatrix();
/* 73 */       Vec3d v = MCH_Lib.getYawPitchFromVec(x, y, z);
/* 74 */       GL11.glRotatef((float)v.field_72448_b, 0.0F, -1.0F, 0.0F);
/* 75 */       GL11.glRotatef((float)v.field_72449_c, 0.0F, 0.0F, 1.0F);
/*    */       
/* 77 */       MCH_ModelManager.render("chain");
/*    */       
/* 79 */       GL11.glPopMatrix();
/* 80 */       diff -= 0.949999988079071D;
/*    */     } 
/*    */     
/* 83 */     GL11.glPopMatrix();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ResourceLocation getEntityTexture(MCH_EntityChain entity) {
/* 90 */     return TEX_DEFAULT;
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\chain\MCH_RenderChain.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */