/*     */ package mcheli.parachute;
/*     */ 
/*     */ import java.util.Random;
/*     */ import mcheli.MCH_Config;
/*     */ import mcheli.MCH_ModelManager;
/*     */ import mcheli.wrapper.W_Render;
/*     */ import net.minecraft.client.renderer.entity.RenderManager;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraftforge.fml.client.registry.IRenderFactory;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @SideOnly(Side.CLIENT)
/*     */ public class MCH_RenderParachute
/*     */   extends W_Render<MCH_EntityParachute>
/*     */ {
/*  26 */   public static final IRenderFactory<MCH_EntityParachute> FACTORY = MCH_RenderParachute::new;
/*     */   
/*  28 */   public static final Random rand = new Random();
/*     */ 
/*     */ 
/*     */   
/*     */   public MCH_RenderParachute(RenderManager renderManager) {
/*  33 */     super(renderManager);
/*  34 */     this.field_76989_e = 0.5F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void doRender(MCH_EntityParachute entity, double posX, double posY, double posZ, float par8, float tickTime) {
/*  41 */     if (!(entity instanceof MCH_EntityParachute)) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/*  46 */     MCH_EntityParachute parachute = entity;
/*  47 */     int type = parachute.getType();
/*     */     
/*  49 */     if (type <= 0) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/*  54 */     GL11.glPushMatrix();
/*  55 */     GL11.glEnable(2884);
/*  56 */     GL11.glTranslated(posX, posY, posZ);
/*  57 */     float prevYaw = entity.field_70126_B;
/*     */     
/*  59 */     if (entity.field_70177_z - prevYaw < -180.0F) {
/*     */       
/*  61 */       prevYaw -= 360.0F;
/*     */     }
/*  63 */     else if (prevYaw - entity.field_70177_z < -180.0F) {
/*     */       
/*  65 */       prevYaw += 360.0F;
/*     */     } 
/*     */     
/*  68 */     float yaw = prevYaw + (entity.field_70177_z - prevYaw) * tickTime;
/*     */     
/*  70 */     GL11.glRotatef(yaw, 0.0F, -1.0F, 0.0F);
/*  71 */     GL11.glColor4f(0.75F, 0.75F, 0.75F, 1.0F);
/*  72 */     GL11.glEnable(3042);
/*     */     
/*  74 */     int srcBlend = GL11.glGetInteger(3041);
/*  75 */     int dstBlend = GL11.glGetInteger(3040);
/*     */     
/*  77 */     GL11.glBlendFunc(770, 771);
/*     */     
/*  79 */     if (MCH_Config.SmoothShading.prmBool)
/*     */     {
/*  81 */       GL11.glShadeModel(7425);
/*     */     }
/*     */     
/*  84 */     switch (type) {
/*     */       
/*     */       case 1:
/*  87 */         bindTexture("textures/parachute1.png");
/*  88 */         MCH_ModelManager.render("parachute1");
/*     */         break;
/*     */       case 2:
/*  91 */         bindTexture("textures/parachute2.png");
/*  92 */         if (parachute.isOpenParachute()) {
/*     */           
/*  94 */           MCH_ModelManager.renderPart("parachute2", "$parachute");
/*     */           
/*     */           break;
/*     */         } 
/*  98 */         MCH_ModelManager.renderPart("parachute2", "$seat");
/*     */         break;
/*     */       
/*     */       case 3:
/* 102 */         bindTexture("textures/parachute2.png");
/* 103 */         MCH_ModelManager.renderPart("parachute2", "$parachute");
/*     */         break;
/*     */     } 
/* 106 */     GL11.glBlendFunc(srcBlend, dstBlend);
/* 107 */     GL11.glDisable(3042);
/* 108 */     GL11.glShadeModel(7424);
/*     */     
/* 110 */     GL11.glPopMatrix();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ResourceLocation getEntityTexture(MCH_EntityParachute entity) {
/* 117 */     return TEX_DEFAULT;
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\parachute\MCH_RenderParachute.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */