/*    */ package mcheli.weapon;
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
/*    */ public class MCH_RenderCartridge
/*    */   extends W_Render<MCH_EntityCartridge>
/*    */ {
/* 23 */   public static final IRenderFactory<MCH_EntityCartridge> FACTORY = MCH_RenderCartridge::new;
/*    */ 
/*    */ 
/*    */   
/*    */   public MCH_RenderCartridge(RenderManager renderManager) {
/* 28 */     super(renderManager);
/* 29 */     this.field_76989_e = 0.0F;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void doRender(MCH_EntityCartridge entity, double posX, double posY, double posZ, float par8, float tickTime) {
/* 36 */     MCH_EntityCartridge cartridge = null;
/*    */     
/* 38 */     cartridge = entity;
/*    */     
/* 40 */     if (cartridge.model != null && !cartridge.texture_name.isEmpty()) {
/*    */       
/* 42 */       GL11.glPushMatrix();
/* 43 */       GL11.glTranslated(posX, posY, posZ);
/* 44 */       GL11.glScalef(cartridge.getScale(), cartridge.getScale(), cartridge.getScale());
/*    */       
/* 46 */       float prevYaw = cartridge.field_70126_B;
/*    */       
/* 48 */       if (cartridge.field_70177_z - prevYaw < -180.0F) {
/*    */         
/* 50 */         prevYaw -= 360.0F;
/*    */       }
/* 52 */       else if (prevYaw - cartridge.field_70177_z < -180.0F) {
/*    */         
/* 54 */         prevYaw += 360.0F;
/*    */       } 
/*    */       
/* 57 */       float yaw = -(prevYaw + (cartridge.field_70177_z - prevYaw) * tickTime);
/* 58 */       float pitch = cartridge.field_70127_C + (cartridge.field_70125_A - cartridge.field_70127_C) * tickTime;
/*    */       
/* 60 */       GL11.glRotatef(yaw, 0.0F, 1.0F, 0.0F);
/* 61 */       GL11.glRotatef(pitch, 1.0F, 0.0F, 0.0F);
/*    */       
/* 63 */       bindTexture("textures/bullets/" + cartridge.texture_name + ".png");
/* 64 */       cartridge.model.renderAll();
/* 65 */       GL11.glPopMatrix();
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ResourceLocation getEntityTexture(MCH_EntityCartridge entity) {
/* 73 */     return TEX_DEFAULT;
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\weapon\MCH_RenderCartridge.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */