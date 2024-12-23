package mcheli.mob;

import mcheli.MCH_MOD;
import mcheli.aircraft.MCH_EntityAircraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class MCH_RenderGunner extends RenderLivingBase<MCH_EntityGunner> {
  public static final IRenderFactory<MCH_EntityGunner> FACTORY = MCH_RenderGunner::new;
  
  private static final ResourceLocation steveTextures = new ResourceLocation("mcheli", "textures/mob/heligunner.png");
  
  private static final ResourceLocation EMB4_TEXTURE = DefaultPlayerSkin.getDefaultSkinLegacy();
  
  public ModelBiped modelBipedMain;
  
  public ModelBiped modelArmorChestplate;
  
  public ModelBiped modelArmor;
  
  public MCH_RenderGunner(RenderManager renderManager) {
    super(renderManager, (ModelBase)new ModelBiped(0.0F, 0.0F, 64, MCH_MOD.isTodaySep01() ? 64 : 32), 0.5F);
    this.modelBipedMain = (ModelBiped)this.mainModel;
    this.modelArmorChestplate = new ModelBiped(1.0F, 0.0F, 64, MCH_MOD.isTodaySep01() ? 64 : 32);
    this.modelArmor = new ModelBiped(0.5F, 0.0F, 64, MCH_MOD.isTodaySep01() ? 64 : 32);
  }
  
  protected int shouldRenderPass(EntityLivingBase p_77032_1_, int p_77032_2_, float p_77032_3_) {
    return -1;
  }
  
  protected boolean canRenderName(MCH_EntityGunner targetEntity) {
    return (targetEntity.getTeam() != null);
  }
  
  public void doRender(MCH_EntityGunner entity, double x, double y, double z, float entityYaw, float partialTicks) {
    GL11.glColor3f(1.0F, 1.0F, 1.0F);
    this.modelBipedMain
      .isSneak = entity.isSneaking();
    double d3 = y;
    if (entity.isSneaking())
      d3 -= 0.125D; 
    MCH_EntityAircraft ac = entity.getAc();
    if (ac != null && ac.getAcInfo() != null && (!(ac.getAcInfo()).hideEntity || !ac.isPilot((Entity)entity)))
      super.doRender(entity, x, d3, z, entityYaw, partialTicks); 
    this.modelBipedMain.isSneak = false;
    this.modelBipedMain.rightArmPose = ModelBiped.ArmPose.EMPTY;
  }
  
  protected void preRenderCallback(MCH_EntityGunner entitylivingbaseIn, float partialTickTime) {
    float f1 = 0.9375F;
    GL11.glScalef(f1, f1, f1);
  }
  
  public void renderFirstPersonArm(EntityPlayer p_82441_1_) {
    float f = 1.0F;
    GL11.glColor3f(f, f, f);
    this.modelBipedMain.swingProgress = 0.0F;
    this.modelBipedMain.setRotationAngles(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, (Entity)p_82441_1_);
    this.modelBipedMain.bipedRightArm.render(0.0625F);
  }
  
  protected void _renderOffsetLivingLabel(MCH_EntityGunner p_96449_1_, double p_96449_2_, double p_96449_4_, double p_96449_6_, String p_96449_8_, float p_96449_9_, double p_96449_10_) {}
  
  protected void _preRenderCallback(MCH_EntityGunner entitylivingbaseIn, float partialTickTime) {
    preRenderCallback(entitylivingbaseIn, partialTickTime);
  }
  
  protected int _shouldRenderPass(MCH_EntityGunner p_77032_1_, int p_77032_2_, float p_77032_3_) {
    return shouldRenderPass(p_77032_1_, p_77032_2_, p_77032_3_);
  }
  
  protected void _renderEquippedItems(MCH_EntityGunner p_77029_1_, float p_77029_2_) {}
  
  protected void _rotateCorpse(MCH_EntityGunner entityLiving, float p_77043_2_, float rotationYaw, float partialTicks) {
    rotateCorpse(entityLiving, p_77043_2_, rotationYaw, partialTicks);
  }
  
  protected void _renderLivingAt(MCH_EntityGunner entityLivingBaseIn, double x, double y, double z) {
    renderLivingAt(entityLivingBaseIn, x, y, z);
  }
  
  public void _doRender(MCH_EntityGunner entity, double x, double y, double z, float entityYaw, float partialTicks) {
    doRender(entity, x, y, z, entityYaw, partialTicks);
  }
  
  protected ResourceLocation getEntityTexture(MCH_EntityGunner entity) {
    return MCH_MOD.isTodaySep01() ? EMB4_TEXTURE : steveTextures;
  }
  
  public void _doRender2(MCH_EntityGunner entity, double x, double y, double z, float entityYaw, float partialTicks) {
    doRender(entity, x, y, z, entityYaw, partialTicks);
  }
}
