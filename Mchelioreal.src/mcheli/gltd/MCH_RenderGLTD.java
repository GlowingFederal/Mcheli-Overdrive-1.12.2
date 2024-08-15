/*     */ package mcheli.gltd;
/*     */ 
/*     */ import java.util.Random;
/*     */ import mcheli.MCH_RenderLib;
/*     */ import mcheli.__helper.client._IModelCustom;
/*     */ import mcheli.wrapper.W_Lib;
/*     */ import mcheli.wrapper.W_Render;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.culling.ICamera;
/*     */ import net.minecraft.client.renderer.entity.RenderManager;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.math.Vec3d;
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
/*     */ 
/*     */ @SideOnly(Side.CLIENT)
/*     */ public class MCH_RenderGLTD
/*     */   extends W_Render<MCH_EntityGLTD>
/*     */ {
/*  31 */   public static final IRenderFactory<MCH_EntityGLTD> FACTORY = MCH_RenderGLTD::new;
/*     */   
/*  33 */   public static final Random rand = new Random();
/*     */ 
/*     */   
/*     */   public static _IModelCustom model;
/*     */ 
/*     */   
/*     */   public MCH_RenderGLTD(RenderManager renderManager) {
/*  40 */     super(renderManager);
/*  41 */     this.field_76989_e = 0.5F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void doRender(MCH_EntityGLTD entity, double posX, double posY, double posZ, float par8, float tickTime) {
/*  51 */     MCH_EntityGLTD gltd = entity;
/*     */     
/*  53 */     GL11.glPushMatrix();
/*     */     
/*  55 */     GL11.glTranslated(posX, posY + 0.25D, posZ);
/*     */ 
/*     */     
/*  58 */     setCommonRenderParam(true, entity.func_70070_b());
/*     */     
/*  60 */     bindTexture("textures/gltd.png");
/*     */     
/*  62 */     Minecraft mc = Minecraft.func_71410_x();
/*  63 */     boolean isNotRenderHead = false;
/*     */ 
/*     */     
/*  66 */     if (gltd.getRiddenByEntity() != null) {
/*     */       
/*  68 */       gltd.isUsedPlayer = true;
/*     */ 
/*     */ 
/*     */       
/*  72 */       gltd.renderRotaionYaw = (gltd.getRiddenByEntity()).field_70177_z;
/*  73 */       gltd.renderRotaionPitch = (gltd.getRiddenByEntity()).field_70125_A;
/*  74 */       isNotRenderHead = (mc.field_71474_y.field_74320_O == 0 && W_Lib.isClientPlayer(gltd.getRiddenByEntity()));
/*     */     } 
/*     */     
/*  77 */     if (gltd.isUsedPlayer) {
/*     */       
/*  79 */       GL11.glPushMatrix();
/*  80 */       GL11.glRotatef(-gltd.field_70177_z, 0.0F, 1.0F, 0.0F);
/*  81 */       model.renderPart("$body");
/*  82 */       GL11.glPopMatrix();
/*     */     }
/*     */     else {
/*     */       
/*  86 */       GL11.glRotatef(-gltd.field_70177_z, 0.0F, 1.0F, 0.0F);
/*  87 */       model.renderPart("$body");
/*     */     } 
/*     */     
/*  90 */     GL11.glTranslatef(0.0F, 0.45F, 0.0F);
/*     */     
/*  92 */     if (gltd.isUsedPlayer) {
/*     */       
/*  94 */       GL11.glRotatef(gltd.renderRotaionYaw, 0.0F, -1.0F, 0.0F);
/*  95 */       GL11.glRotatef(gltd.renderRotaionPitch, 1.0F, 0.0F, 0.0F);
/*     */     } 
/*     */     
/*  98 */     GL11.glTranslatef(0.0F, -0.45F, 0.0F);
/*     */     
/* 100 */     if (!isNotRenderHead)
/*     */     {
/* 102 */       model.renderPart("$head");
/*     */     }
/*     */     
/* 105 */     GL11.glTranslatef(0.0F, 0.45F, 0.0F);
/*     */     
/* 107 */     restoreCommonRenderParam();
/*     */     
/* 109 */     GL11.glDisable(2896);
/*     */     
/* 111 */     Vec3d[] v = { new Vec3d(0.0D, 0.2D, 0.0D), new Vec3d(0.0D, 0.2D, 100.0D) };
/*     */ 
/*     */ 
/*     */     
/* 115 */     int a = rand.nextInt(64);
/*     */     
/* 117 */     MCH_RenderLib.drawLine(v, 0x6080FF80 | a << 24);
/* 118 */     GL11.glEnable(2896);
/*     */     
/* 120 */     GL11.glPopMatrix();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean shouldRender(MCH_EntityGLTD livingEntity, ICamera camera, double camX, double camY, double camZ) {
/* 126 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ResourceLocation getEntityTexture(MCH_EntityGLTD entity) {
/* 133 */     return TEX_DEFAULT;
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\gltd\MCH_RenderGLTD.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */