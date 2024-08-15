/*     */ package mcheli.uav;
/*     */ 
/*     */ import mcheli.MCH_Lib;
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
/*     */ @SideOnly(Side.CLIENT)
/*     */ public class MCH_RenderUavStation
/*     */   extends W_Render<MCH_EntityUavStation>
/*     */ {
/*  24 */   public static final IRenderFactory<MCH_EntityUavStation> FACTORY = MCH_RenderUavStation::new;
/*     */   
/*  26 */   public static final String[] MODEL_NAME = new String[] { "uav_station", "uav_portable_controller" };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  31 */   public static final String[] TEX_NAME_ON = new String[] { "uav_station_on", "uav_portable_controller_on" };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  36 */   public static final String[] TEX_NAME_OFF = new String[] { "uav_station", "uav_portable_controller" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MCH_RenderUavStation(RenderManager renderManager) {
/*  44 */     super(renderManager);
/*  45 */     this.field_76989_e = 1.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void doRender(MCH_EntityUavStation entity, double posX, double posY, double posZ, float par8, float tickTime) {
/*  52 */     if (!(entity instanceof MCH_EntityUavStation)) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/*  57 */     MCH_EntityUavStation uavSt = entity;
/*     */     
/*  59 */     if (uavSt.getKind() <= 0) {
/*     */       return;
/*     */     }
/*  62 */     int kind = uavSt.getKind() - 1;
/*  63 */     GL11.glPushMatrix();
/*     */     
/*  65 */     GL11.glTranslated(posX, posY + 0.3499999940395355D, posZ);
/*  66 */     GL11.glEnable(2884);
/*  67 */     GL11.glRotatef(entity.field_70177_z, 0.0F, -1.0F, 0.0F);
/*  68 */     GL11.glRotatef(entity.field_70125_A, 1.0F, 0.0F, 0.0F);
/*  69 */     GL11.glColor4f(0.75F, 0.75F, 0.75F, 1.0F);
/*  70 */     GL11.glEnable(3042);
/*     */     
/*  72 */     int srcBlend = GL11.glGetInteger(3041);
/*  73 */     int dstBlend = GL11.glGetInteger(3040);
/*     */     
/*  75 */     GL11.glBlendFunc(770, 771);
/*     */     
/*  77 */     if (kind == 0) {
/*     */ 
/*     */       
/*  80 */       if (uavSt.getControlAircract() != null && uavSt.getRiddenByEntity() != null) {
/*     */         
/*  82 */         bindTexture("textures/" + TEX_NAME_ON[kind] + ".png");
/*     */       }
/*     */       else {
/*     */         
/*  86 */         bindTexture("textures/" + TEX_NAME_OFF[kind] + ".png");
/*     */       } 
/*  88 */       MCH_ModelManager.render(MODEL_NAME[kind]);
/*     */     }
/*     */     else {
/*     */       
/*  92 */       if (uavSt.rotCover > 0.95F) {
/*     */         
/*  94 */         bindTexture("textures/" + TEX_NAME_ON[kind] + ".png");
/*     */       }
/*     */       else {
/*     */         
/*  98 */         bindTexture("textures/" + TEX_NAME_OFF[kind] + ".png");
/*     */       } 
/* 100 */       renderPortableController(uavSt, MODEL_NAME[kind], tickTime);
/*     */     } 
/*     */     
/* 103 */     GL11.glBlendFunc(srcBlend, dstBlend);
/* 104 */     GL11.glDisable(3042);
/* 105 */     GL11.glPopMatrix();
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderPortableController(MCH_EntityUavStation uavSt, String name, float tickTime) {
/* 110 */     MCH_ModelManager.renderPart(name, "$body");
/*     */     
/* 112 */     float rot = MCH_Lib.smooth(uavSt.rotCover, uavSt.prevRotCover, tickTime);
/*     */     
/* 114 */     renderRotPart(name, "$cover", rot * 60.0F, 0.0D, -0.1812D, -0.3186D);
/* 115 */     renderRotPart(name, "$laptop_cover", rot * 95.0F, 0.0D, -0.1808D, -0.0422D);
/* 116 */     renderRotPart(name, "$display", rot * -85.0F, 0.0D, -0.1807D, 0.2294D);
/*     */   }
/*     */ 
/*     */   
/*     */   private void renderRotPart(String modelName, String partName, float rot, double x, double y, double z) {
/* 121 */     GL11.glPushMatrix();
/* 122 */     GL11.glTranslated(x, y, z);
/* 123 */     GL11.glRotatef(rot, -1.0F, 0.0F, 0.0F);
/* 124 */     GL11.glTranslated(-x, -y, -z);
/* 125 */     MCH_ModelManager.renderPart(modelName, partName);
/* 126 */     GL11.glPopMatrix();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ResourceLocation getEntityTexture(MCH_EntityUavStation entity) {
/* 133 */     return TEX_DEFAULT;
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mchel\\uav\MCH_RenderUavStation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */