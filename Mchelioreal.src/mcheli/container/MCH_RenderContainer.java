/*    */ package mcheli.container;
/*    */ 
/*    */ import java.util.Random;
/*    */ import mcheli.MCH_Lib;
/*    */ import mcheli.MCH_ModelManager;
/*    */ import mcheli.aircraft.MCH_RenderAircraft;
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
/*    */ public class MCH_RenderContainer
/*    */   extends W_Render<MCH_EntityContainer>
/*    */ {
/* 27 */   public static final IRenderFactory<MCH_EntityContainer> FACTORY = MCH_RenderContainer::new;
/*    */   
/* 29 */   public static final Random rand = new Random();
/*    */ 
/*    */ 
/*    */   
/*    */   public MCH_RenderContainer(RenderManager renderManager) {
/* 34 */     super(renderManager);
/* 35 */     this.field_76989_e = 0.5F;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void doRender(MCH_EntityContainer entity, double posX, double posY, double posZ, float par8, float tickTime) {
/* 42 */     if (MCH_RenderAircraft.shouldSkipRender((Entity)entity)) {
/*    */       return;
/*    */     }
/*    */ 
/*    */     
/* 47 */     GL11.glPushMatrix();
/* 48 */     GL11.glEnable(2884);
/*    */     
/* 50 */     GL11.glTranslated(posX, posY - 0.2D + 0.5D, posZ);
/*    */     
/* 52 */     float yaw = MCH_Lib.smoothRot(entity.field_70177_z, entity.field_70126_B, tickTime);
/* 53 */     float pitch = MCH_Lib.smoothRot(entity.field_70125_A, entity.field_70127_C, tickTime);
/*    */     
/* 55 */     GL11.glRotatef(yaw, 0.0F, -1.0F, 0.0F);
/* 56 */     GL11.glRotatef(pitch, 1.0F, 0.0F, 0.0F);
/* 57 */     GL11.glColor4f(0.75F, 0.75F, 0.75F, 1.0F);
/*    */     
/* 59 */     bindTexture("textures/container.png");
/* 60 */     MCH_ModelManager.render("container");
/* 61 */     GL11.glPopMatrix();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ResourceLocation getEntityTexture(MCH_EntityContainer entity) {
/* 68 */     return TEX_DEFAULT;
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\container\MCH_RenderContainer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */