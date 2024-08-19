package mcheli.particles;

import java.util.List;
import mcheli.aircraft.MCH_EntityAircraft;
import mcheli.wrapper.W_McClient;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MCH_EntityParticleSmoke extends MCH_EntityParticleBase {
  private static final VertexFormat VERTEX_FORMAT = (new VertexFormat()).addElement(DefaultVertexFormats.POSITION_3F)
    .addElement(DefaultVertexFormats.TEX_2F).addElement(DefaultVertexFormats.COLOR_4UB)
    .addElement(DefaultVertexFormats.TEX_2S)
    .addElement(DefaultVertexFormats.NORMAL_3B)
    .addElement(DefaultVertexFormats.PADDING_1B);
  
  public MCH_EntityParticleSmoke(World par1World, double x, double y, double z, double mx, double my, double mz) {
    super(par1World, x, y, z, mx, my, mz);
    this.particleRed = this.particleGreen = this.particleBlue = this.rand.nextFloat() * 0.3F + 0.7F;
    setParticleScale(this.rand.nextFloat() * 0.5F + 5.0F);
    setParticleMaxAge((int)(16.0D / (this.rand.nextFloat() * 0.8D + 0.2D)) + 2);
  }
  
  public void onUpdate() {
    this.prevPosX = this.posX;
    this.prevPosY = this.posY;
    this.prevPosZ = this.posZ;
    if (this.particleAge < this.particleMaxAge) {
      setParticleTextureIndex((int)(8.0D * this.particleAge / this.particleMaxAge));
      this.particleAge++;
    } else {
      setExpired();
      return;
    } 
    if (this.diffusible)
      if (this.particleScale < this.particleMaxScale)
        this.particleScale += 0.8F;  
    if (this.toWhite) {
      float mn = getMinColor();
      float mx = getMaxColor();
      float dist = mx - mn;
      if (dist > 0.2D) {
        this.particleRed += (mx - this.particleRed) * 0.016F;
        this.particleGreen += (mx - this.particleGreen) * 0.016F;
        this.particleBlue += (mx - this.particleBlue) * 0.016F;
      } 
    } 
    effectWind();
    if ((this.particleAge / this.particleMaxAge) > this.moutionYUpAge) {
      this.motionY += 0.02D;
    } else {
      this.motionY += this.gravity;
    } 
    moveEntity(this.motionX, this.motionY, this.motionZ);
    if (this.diffusible) {
      this.motionX *= 0.96D;
      this.motionZ *= 0.96D;
      this.motionY *= 0.96D;
    } else {
      this.motionX *= 0.9D;
      this.motionZ *= 0.9D;
    } 
  }
  
  public float getMinColor() {
    return min(min(this.particleBlue, this.particleGreen), this.particleRed);
  }
  
  public float getMaxColor() {
    return max(max(this.particleBlue, this.particleGreen), this.particleRed);
  }
  
  public float min(float a, float b) {
    return (a < b) ? a : b;
  }
  
  public float max(float a, float b) {
    return (a > b) ? a : b;
  }
  
  public void effectWind() {
    if (this.isEffectedWind) {
      List<MCH_EntityAircraft> list = this.worldObj.getEntitiesWithinAABB(MCH_EntityAircraft.class, 
          getCollisionBoundingBox().expand(15.0D, 15.0D, 15.0D));
      for (int i = 0; i < list.size(); i++) {
        MCH_EntityAircraft ac = list.get(i);
        if (ac.getThrottle() > 0.10000000149011612D) {
          float dist = getDistance(ac);
          double vel = (23.0D - dist) * 0.009999999776482582D * ac.getThrottle();
          double mx = ac.posX - this.posX;
          double mz = ac.posZ - this.posZ;
          this.motionX -= mx * vel;
          this.motionZ -= mz * vel;
        } 
      } 
    } 
  }
  
  public int getFXLayer() {
    return 3;
  }
  
  @SideOnly(Side.CLIENT)
  public int getBrightnessForRender(float partialTicks) {
    double y = this.posY;
    this.posY += 3000.0D;
    int i = super.getBrightnessForRender(partialTicks);
    this.posY = y;
    return i;
  }
  
  public void renderParticle(BufferBuilder buffer, Entity entityIn, float par2, float par3, float par4, float par5, float par6, float par7) {
    W_McClient.MOD_bindTexture("textures/particles/smoke.png");
    GlStateManager.enableBlend();
    int srcBlend = GlStateManager.glGetInteger(3041);
    int dstBlend = GlStateManager.glGetInteger(3040);
    GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    GlStateManager.disableLighting();
    GlStateManager.disableCull();
    float f6 = this.particleTextureIndexX / 8.0F;
    float f7 = f6 + 0.125F;
    float f8 = 0.0F;
    float f9 = 1.0F;
    float f10 = 0.1F * this.particleScale;
    float f11 = (float)(this.prevPosX + (this.posX - this.prevPosX) * par2 - interpPosX);
    float f12 = (float)(this.prevPosY + (this.posY - this.prevPosY) * par2 - interpPosY);
    float f13 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * par2 - interpPosZ);
    int i = getBrightnessForRender(par2);
    int j = i >> 16 & 0xFFFF;
    int k = i & 0xFFFF;
    buffer.begin(7, VERTEX_FORMAT);
    buffer.pos((f11 - par3 * f10 - par6 * f10), (f12 - par4 * f10), (f13 - par5 * f10 - par7 * f10)).tex(f7, f9)
      .color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha)
      .lightmap(j, k)
      .normal(0.0F, 1.0F, 0.0F).endVertex();
    buffer.pos((f11 - par3 * f10 + par6 * f10), (f12 + par4 * f10), (f13 - par5 * f10 + par7 * f10)).tex(f7, f8)
      .color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha)
      .lightmap(j, k)
      .normal(0.0F, 1.0F, 0.0F).endVertex();
    buffer.pos((f11 + par3 * f10 + par6 * f10), (f12 + par4 * f10), (f13 + par5 * f10 + par7 * f10)).tex(f6, f8)
      .color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha)
      .lightmap(j, k)
      .normal(0.0F, 1.0F, 0.0F).endVertex();
    buffer.pos((f11 + par3 * f10 - par6 * f10), (f12 - par4 * f10), (f13 + par5 * f10 - par7 * f10)).tex(f6, f9)
      .color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha)
      .lightmap(j, k)
      .normal(0.0F, 1.0F, 0.0F).endVertex();
    Tessellator.getInstance().draw();
    GlStateManager.enableCull();
    GlStateManager.enableLighting();
    GlStateManager.blendFunc(srcBlend, dstBlend);
    GlStateManager.disableBlend();
  }
  
  private float getDistance(MCH_EntityAircraft entity) {
    float f = (float)(this.posX - entity.posX);
    float f1 = (float)(this.posY - entity.posY);
    float f2 = (float)(this.posZ - entity.posZ);
    return MathHelper.sqrt(f * f + f1 * f1 + f2 * f2);
  }
}
