/*    */ package mcheli.debug;
/*    */ 
/*    */ import mcheli.MCH_Config;
/*    */ import mcheli.wrapper.W_Render;
/*    */ import net.minecraft.client.renderer.entity.Render;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraftforge.fml.client.registry.IRenderFactory;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class MCH_RenderTest
/*    */   extends W_Render<Entity>
/*    */ {
/*    */   protected MCH_ModelTest model;
/*    */   private float offsetX;
/*    */   private float offsetY;
/*    */   private float offsetZ;
/*    */   private String textureName;
/*    */   
/*    */   public static final IRenderFactory<Entity> factory(float x, float y, float z, String texture_name) {
/* 26 */     return renderManager -> new MCH_RenderTest(renderManager, x, y, z, texture_name);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public MCH_RenderTest(RenderManager renderManager, float x, float y, float z, String texture_name) {
/* 38 */     super(renderManager);
/* 39 */     this.offsetX = x;
/* 40 */     this.offsetY = y;
/* 41 */     this.offsetZ = z;
/* 42 */     this.textureName = texture_name;
/* 43 */     this.model = new MCH_ModelTest();
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_76986_a(Entity e, double posX, double posY, double posZ, float par8, float par9) {
/*    */     float prevYaw;
/* 49 */     if (!MCH_Config.TestMode.prmBool) {
/*    */       return;
/*    */     }
/*    */     
/* 53 */     GL11.glPushMatrix();
/* 54 */     GL11.glTranslated(posX + this.offsetX, posY + this.offsetY, posZ + this.offsetZ);
/*    */     
/* 56 */     GL11.glScalef(e.field_70130_N, e.field_70131_O, e.field_70130_N);
/*    */     
/* 58 */     GL11.glColor4f(0.5F, 0.5F, 0.5F, 1.0F);
/*    */ 
/*    */ 
/*    */     
/* 62 */     if (e.field_70177_z - e.field_70126_B < -180.0F) {
/*    */       
/* 64 */       prevYaw = e.field_70126_B - 360.0F;
/*    */ 
/*    */ 
/*    */     
/*    */     }
/* 69 */     else if (e.field_70126_B - e.field_70177_z < -180.0F) {
/* 70 */       prevYaw = e.field_70126_B + 360.0F;
/*    */     } else {
/* 72 */       prevYaw = e.field_70126_B;
/*    */     } 
/*    */     
/* 75 */     float yaw = -(prevYaw + (e.field_70177_z - prevYaw) * par9) - 180.0F;
/* 76 */     float pitch = -(e.field_70127_C + (e.field_70125_A - e.field_70127_C) * par9);
/* 77 */     GL11.glRotatef(yaw, 0.0F, 1.0F, 0.0F);
/* 78 */     GL11.glRotatef(pitch, 1.0F, 0.0F, 0.0F);
/*    */     
/* 80 */     bindTexture("textures/" + this.textureName + ".png");
/* 81 */     this.model.renderModel(0.0D, 0.0D, 0.1F);
/* 82 */     GL11.glPopMatrix();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected ResourceLocation func_110775_a(Entity entity) {
/* 88 */     return TEX_DEFAULT;
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\debug\MCH_RenderTest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */