/*    */ package mcheli.weapon;
/*    */ 
/*    */ import mcheli.aircraft.MCH_EntityAircraft;
/*    */ import mcheli.aircraft.MCH_EntitySeat;
/*    */ import mcheli.uav.MCH_EntityUavStation;
/*    */ import mcheli.wrapper.W_Entity;
/*    */ import net.minecraft.client.Minecraft;
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
/*    */ public class MCH_RenderTvMissile
/*    */   extends MCH_RenderBulletBase<MCH_EntityBaseBullet>
/*    */ {
/* 27 */   public static final IRenderFactory<MCH_EntityBaseBullet> FACTORY = MCH_RenderTvMissile::new;
/*    */ 
/*    */ 
/*    */   
/*    */   public MCH_RenderTvMissile(RenderManager renderManager) {
/* 32 */     super(renderManager);
/* 33 */     this.field_76989_e = 0.5F;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void renderBullet(MCH_EntityBaseBullet entity, double posX, double posY, double posZ, float par8, float par9) {
/* 40 */     MCH_EntityAircraft ac = null;
/* 41 */     Entity ridingEntity = (Minecraft.func_71410_x()).field_71439_g.func_184187_bx();
/* 42 */     if (ridingEntity instanceof MCH_EntityAircraft) {
/*    */       
/* 44 */       ac = (MCH_EntityAircraft)ridingEntity;
/*    */     }
/* 46 */     else if (ridingEntity instanceof MCH_EntitySeat) {
/*    */       
/* 48 */       ac = ((MCH_EntitySeat)ridingEntity).getParent();
/*    */     }
/* 50 */     else if (ridingEntity instanceof MCH_EntityUavStation) {
/*    */       
/* 52 */       ac = ((MCH_EntityUavStation)ridingEntity).getControlAircract();
/*    */     } 
/*    */     
/* 55 */     if (ac != null && !ac.isRenderBullet((Entity)entity, (Entity)(Minecraft.func_71410_x()).field_71439_g)) {
/*    */       return;
/*    */     }
/*    */ 
/*    */     
/* 60 */     if (entity instanceof MCH_EntityBaseBullet) {
/*    */       
/* 62 */       MCH_EntityBaseBullet bullet = entity;
/*    */       
/* 64 */       GL11.glPushMatrix();
/* 65 */       GL11.glTranslated(posX, posY, posZ);
/* 66 */       GL11.glRotatef(-entity.field_70177_z, 0.0F, 1.0F, 0.0F);
/* 67 */       GL11.glRotatef(-entity.field_70125_A, -1.0F, 0.0F, 0.0F);
/*    */       
/* 69 */       renderModel(bullet);
/*    */       
/* 71 */       GL11.glPopMatrix();
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ResourceLocation getEntityTexture(MCH_EntityBaseBullet entity) {
/* 79 */     return TEX_DEFAULT;
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\weapon\MCH_RenderTvMissile.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */