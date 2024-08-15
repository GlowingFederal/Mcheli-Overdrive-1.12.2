/*     */ package mcheli.mob;
/*     */ 
/*     */ import mcheli.MCH_MOD;
/*     */ import mcheli.aircraft.MCH_EntityAircraft;
/*     */ import net.minecraft.client.model.ModelBase;
/*     */ import net.minecraft.client.model.ModelBiped;
/*     */ import net.minecraft.client.renderer.entity.RenderLivingBase;
/*     */ import net.minecraft.client.renderer.entity.RenderManager;
/*     */ import net.minecraft.client.resources.DefaultPlayerSkin;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
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
/*     */ @SideOnly(Side.CLIENT)
/*     */ public class MCH_RenderGunner
/*     */   extends RenderLivingBase<MCH_EntityGunner>
/*     */ {
/*  27 */   public static final IRenderFactory<MCH_EntityGunner> FACTORY = MCH_RenderGunner::new;
/*     */   
/*  29 */   private static final ResourceLocation steveTextures = new ResourceLocation("mcheli", "textures/mob/heligunner.png");
/*  30 */   private static final ResourceLocation EMB4_TEXTURE = DefaultPlayerSkin.func_177335_a();
/*     */ 
/*     */   
/*     */   public ModelBiped modelBipedMain;
/*     */ 
/*     */   
/*     */   public ModelBiped modelArmorChestplate;
/*     */   
/*     */   public ModelBiped modelArmor;
/*     */ 
/*     */   
/*     */   public MCH_RenderGunner(RenderManager renderManager) {
/*  42 */     super(renderManager, (ModelBase)new ModelBiped(0.0F, 0.0F, 64, MCH_MOD.isTodaySep01() ? 64 : 32), 0.5F);
/*  43 */     this.modelBipedMain = (ModelBiped)this.field_77045_g;
/*  44 */     this.modelArmorChestplate = new ModelBiped(1.0F, 0.0F, 64, MCH_MOD.isTodaySep01() ? 64 : 32);
/*  45 */     this.modelArmor = new ModelBiped(0.5F, 0.0F, 64, MCH_MOD.isTodaySep01() ? 64 : 32);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected int shouldRenderPass(EntityLivingBase p_77032_1_, int p_77032_2_, float p_77032_3_) {
/*  51 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean canRenderName(MCH_EntityGunner targetEntity) {
/*  58 */     return (targetEntity.func_96124_cp() != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void doRender(MCH_EntityGunner entity, double x, double y, double z, float entityYaw, float partialTicks) {
/*  65 */     GL11.glColor3f(1.0F, 1.0F, 1.0F);
/*     */     
/*  67 */     this.modelBipedMain
/*  68 */       .field_78117_n = entity.func_70093_af();
/*     */     
/*  70 */     double d3 = y;
/*     */ 
/*     */     
/*  73 */     if (entity.func_70093_af())
/*     */     {
/*  75 */       d3 -= 0.125D;
/*     */     }
/*     */     
/*  78 */     MCH_EntityAircraft ac = entity.getAc();
/*     */     
/*  80 */     if (ac != null && ac.getAcInfo() != null && (!(ac.getAcInfo()).hideEntity || !ac.isPilot((Entity)entity)))
/*     */     {
/*  82 */       super.func_76986_a(entity, x, d3, z, entityYaw, partialTicks);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  87 */     this.modelBipedMain.field_78117_n = false;
/*     */     
/*  89 */     this.modelBipedMain.field_187076_m = ModelBiped.ArmPose.EMPTY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void preRenderCallback(MCH_EntityGunner entitylivingbaseIn, float partialTickTime) {
/* 108 */     float f1 = 0.9375F;
/* 109 */     GL11.glScalef(f1, f1, f1);
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderFirstPersonArm(EntityPlayer p_82441_1_) {
/* 114 */     float f = 1.0F;
/* 115 */     GL11.glColor3f(f, f, f);
/* 116 */     this.modelBipedMain.field_78095_p = 0.0F;
/* 117 */     this.modelBipedMain.func_78087_a(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, (Entity)p_82441_1_);
/* 118 */     this.modelBipedMain.field_178723_h.func_78785_a(0.0625F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void _renderOffsetLivingLabel(MCH_EntityGunner p_96449_1_, double p_96449_2_, double p_96449_4_, double p_96449_6_, String p_96449_8_, float p_96449_9_, double p_96449_10_) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void _preRenderCallback(MCH_EntityGunner entitylivingbaseIn, float partialTickTime) {
/* 131 */     preRenderCallback(entitylivingbaseIn, partialTickTime);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int _shouldRenderPass(MCH_EntityGunner p_77032_1_, int p_77032_2_, float p_77032_3_) {
/* 142 */     return shouldRenderPass(p_77032_1_, p_77032_2_, p_77032_3_);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void _renderEquippedItems(MCH_EntityGunner p_77029_1_, float p_77029_2_) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void _rotateCorpse(MCH_EntityGunner entityLiving, float p_77043_2_, float rotationYaw, float partialTicks) {
/* 154 */     func_77043_a(entityLiving, p_77043_2_, rotationYaw, partialTicks);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void _renderLivingAt(MCH_EntityGunner entityLivingBaseIn, double x, double y, double z) {
/* 160 */     func_77039_a(entityLivingBaseIn, x, y, z);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void _doRender(MCH_EntityGunner entity, double x, double y, double z, float entityYaw, float partialTicks) {
/* 166 */     doRender(entity, x, y, z, entityYaw, partialTicks);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ResourceLocation getEntityTexture(MCH_EntityGunner entity) {
/* 174 */     return MCH_MOD.isTodaySep01() ? EMB4_TEXTURE : steveTextures;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void _doRender2(MCH_EntityGunner entity, double x, double y, double z, float entityYaw, float partialTicks) {
/* 180 */     doRender(entity, x, y, z, entityYaw, partialTicks);
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\mob\MCH_RenderGunner.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */