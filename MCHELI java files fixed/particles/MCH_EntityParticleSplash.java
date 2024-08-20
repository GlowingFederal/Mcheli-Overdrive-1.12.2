package mcheli.particles;

import mcheli.wrapper.W_Block;
import mcheli.wrapper.W_McClient;
import mcheli.wrapper.W_WorldFunc;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class MCH_EntityParticleSplash extends MCH_EntityParticleBase {
  public MCH_EntityParticleSplash(World par1World, double x, double y, double z, double mx, double my, double mz) {
    super(par1World, x, y, z, mx, my, mz);
    this.particleRed = this.particleGreen = this.particleBlue = this.rand.nextFloat() * 0.3F + 0.7F;
    setParticleScale(this.rand.nextFloat() * 0.5F + 5.0F);
    setParticleMaxAge((int)(80.0D / (this.rand.nextFloat() * 0.8D + 0.2D)) + 2);
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
    } 
    this.motionY -= 0.05999999865889549D;
    Block block = W_WorldFunc.getBlock(this.world, (int)(this.posX + 0.5D), (int)(this.posY + 0.5D), (int)(this.posZ + 0.5D));
    boolean beforeInWater = W_Block.isEqualTo(block, W_Block.getWater());
    move(this.motionX, this.motionY, this.motionZ);
    block = W_WorldFunc.getBlock(this.world, (int)(this.posX + 0.5D), (int)(this.posY + 0.5D), (int)(this.posZ + 0.5D));
    boolean nowInWater = W_Block.isEqualTo(block, W_Block.getWater());
    if (this.motionY < -0.6D && !beforeInWater && nowInWater) {
      double p = -this.motionY * 10.0D;
      for (int i = 0; i < p; i++) {
        this.world.spawnParticle(EnumParticleTypes.WATER_SPLASH, this.posX + 0.5D + (this.rand
            .nextDouble() - 0.5D) * 2.0D, this.posY + this.rand.nextDouble(), this.posZ + 0.5D + (this.rand
            .nextDouble() - 0.5D) * 2.0D, (this.rand
            .nextDouble() - 0.5D) * 2.0D, 4.0D, (this.rand.nextDouble() - 0.5D) * 2.0D, new int[0]);
        this.world.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX + 0.5D + (this.rand
            .nextDouble() - 0.5D) * 2.0D, this.posY - this.rand.nextDouble(), this.posZ + 0.5D + (this.rand
            .nextDouble() - 0.5D) * 2.0D, (this.rand
            .nextDouble() - 0.5D) * 2.0D, -0.5D, (this.rand.nextDouble() - 0.5D) * 2.0D, new int[0]);
      } 
    } else if (this.onGround) {
      setExpired();
    } 
    this.motionX *= 0.9D;
    this.motionZ *= 0.9D;
  }
  
  public void renderParticle(BufferBuilder buffer, Entity entityIn, float par2, float par3, float par4, float par5, float par6, float par7) {
    W_McClient.MOD_bindTexture("textures/particles/smoke.png");
    float f6 = this.particleTextureIndexX / 8.0F;
    float f7 = f6 + 0.125F;
    float f8 = 0.0F;
    float f9 = 1.0F;
    float f10 = 0.1F * this.particleScale;
    float f11 = (float)(this.prevPosX + (this.posX - this.prevPosX) * par2 - interpPosX);
    float f12 = (float)(this.prevPosY + (this.posY - this.prevPosY) * par2 - interpPosY);
    float f13 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * par2 - interpPosZ);
    float f14 = 1.0F;
    int i = getBrightnessForRender(par2);
    int j = i >> 16 & 0xFFFF;
    int k = i & 0xFFFF;
    buffer.pos((f11 - par3 * f10 - par6 * f10), (f12 - par4 * f10), (f13 - par5 * f10 - par7 * f10)).tex(f7, f9)
      .color(this.particleRed * f14, this.particleGreen * f14, this.particleBlue * f14, this.particleAlpha)
      .lightmap(j, k).endVertex();
    buffer.pos((f11 - par3 * f10 + par6 * f10), (f12 + par4 * f10), (f13 - par5 * f10 + par7 * f10)).tex(f7, f8)
      .color(this.particleRed * f14, this.particleGreen * f14, this.particleBlue * f14, this.particleAlpha)
      .lightmap(j, k).endVertex();
    buffer.pos((f11 + par3 * f10 + par6 * f10), (f12 + par4 * f10), (f13 + par5 * f10 + par7 * f10)).tex(f6, f8)
      .color(this.particleRed * f14, this.particleGreen * f14, this.particleBlue * f14, this.particleAlpha)
      .lightmap(j, k).endVertex();
    buffer.pos((f11 + par3 * f10 - par6 * f10), (f12 - par4 * f10), (f13 + par5 * f10 - par7 * f10)).tex(f6, f9)
      .color(this.particleRed * f14, this.particleGreen * f14, this.particleBlue * f14, this.particleAlpha)
      .lightmap(j, k).endVertex();
  }
}
