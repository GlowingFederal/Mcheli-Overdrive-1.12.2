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
  private static final VertexFormat VERTEX_FORMAT = (new VertexFormat()).func_181721_a(DefaultVertexFormats.field_181713_m)
    .func_181721_a(DefaultVertexFormats.field_181715_o).func_181721_a(DefaultVertexFormats.field_181714_n)
    .func_181721_a(DefaultVertexFormats.field_181716_p)
    .func_181721_a(DefaultVertexFormats.field_181717_q)
    .func_181721_a(DefaultVertexFormats.field_181718_r);
  
  public MCH_EntityParticleSmoke(World par1World, double x, double y, double z, double mx, double my, double mz) {
    super(par1World, x, y, z, mx, my, mz);
    this.field_70552_h = this.field_70553_i = this.field_70551_j = this.field_187136_p.nextFloat() * 0.3F + 0.7F;
    setParticleScale(this.field_187136_p.nextFloat() * 0.5F + 5.0F);
    setParticleMaxAge((int)(16.0D / (this.field_187136_p.nextFloat() * 0.8D + 0.2D)) + 2);
  }
  
  public void func_189213_a() {
    this.field_187123_c = this.field_187126_f;
    this.field_187124_d = this.field_187127_g;
    this.field_187125_e = this.field_187128_h;
    if (this.field_70546_d < this.field_70547_e) {
      func_70536_a((int)(8.0D * this.field_70546_d / this.field_70547_e));
      this.field_70546_d++;
    } else {
      func_187112_i();
      return;
    } 
    if (this.diffusible)
      if (this.field_70544_f < this.particleMaxScale)
        this.field_70544_f += 0.8F;  
    if (this.toWhite) {
      float mn = getMinColor();
      float mx = getMaxColor();
      float dist = mx - mn;
      if (dist > 0.2D) {
        this.field_70552_h += (mx - this.field_70552_h) * 0.016F;
        this.field_70553_i += (mx - this.field_70553_i) * 0.016F;
        this.field_70551_j += (mx - this.field_70551_j) * 0.016F;
      } 
    } 
    effectWind();
    if ((this.field_70546_d / this.field_70547_e) > this.moutionYUpAge) {
      this.field_187130_j += 0.02D;
    } else {
      this.field_187130_j += this.gravity;
    } 
    func_187110_a(this.field_187129_i, this.field_187130_j, this.field_187131_k);
    if (this.diffusible) {
      this.field_187129_i *= 0.96D;
      this.field_187131_k *= 0.96D;
      this.field_187130_j *= 0.96D;
    } else {
      this.field_187129_i *= 0.9D;
      this.field_187131_k *= 0.9D;
    } 
  }
  
  public float getMinColor() {
    return min(min(this.field_70551_j, this.field_70553_i), this.field_70552_h);
  }
  
  public float getMaxColor() {
    return max(max(this.field_70551_j, this.field_70553_i), this.field_70552_h);
  }
  
  public float min(float a, float b) {
    return (a < b) ? a : b;
  }
  
  public float max(float a, float b) {
    return (a > b) ? a : b;
  }
  
  public void effectWind() {
    if (this.isEffectedWind) {
      List<MCH_EntityAircraft> list = this.field_187122_b.func_72872_a(MCH_EntityAircraft.class, 
          getCollisionBoundingBox().func_72314_b(15.0D, 15.0D, 15.0D));
      for (int i = 0; i < list.size(); i++) {
        MCH_EntityAircraft ac = list.get(i);
        if (ac.getThrottle() > 0.10000000149011612D) {
          float dist = getDistance(ac);
          double vel = (23.0D - dist) * 0.009999999776482582D * ac.getThrottle();
          double mx = ac.field_70165_t - this.field_187126_f;
          double mz = ac.field_70161_v - this.field_187128_h;
          this.field_187129_i -= mx * vel;
          this.field_187131_k -= mz * vel;
        } 
      } 
    } 
  }
  
  public int func_70537_b() {
    return 3;
  }
  
  @SideOnly(Side.CLIENT)
  public int func_189214_a(float p_70070_1_) {
    double y = this.field_187127_g;
    this.field_187127_g += 3000.0D;
    int i = super.func_189214_a(p_70070_1_);
    this.field_187127_g = y;
    return i;
  }
  
  public void func_180434_a(BufferBuilder buffer, Entity entityIn, float par2, float par3, float par4, float par5, float par6, float par7) {
    W_McClient.MOD_bindTexture("textures/particles/smoke.png");
    GlStateManager.func_179147_l();
    int srcBlend = GlStateManager.func_187397_v(3041);
    int dstBlend = GlStateManager.func_187397_v(3040);
    GlStateManager.func_187401_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
    GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
    GlStateManager.func_179140_f();
    GlStateManager.func_179129_p();
    float f6 = this.field_94054_b / 8.0F;
    float f7 = f6 + 0.125F;
    float f8 = 0.0F;
    float f9 = 1.0F;
    float f10 = 0.1F * this.field_70544_f;
    float f11 = (float)(this.field_187123_c + (this.field_187126_f - this.field_187123_c) * par2 - field_70556_an);
    float f12 = (float)(this.field_187124_d + (this.field_187127_g - this.field_187124_d) * par2 - field_70554_ao);
    float f13 = (float)(this.field_187125_e + (this.field_187128_h - this.field_187125_e) * par2 - field_70555_ap);
    int i = func_189214_a(par2);
    int j = i >> 16 & 0xFFFF;
    int k = i & 0xFFFF;
    buffer.func_181668_a(7, VERTEX_FORMAT);
    buffer.func_181662_b((f11 - par3 * f10 - par6 * f10), (f12 - par4 * f10), (f13 - par5 * f10 - par7 * f10)).func_187315_a(f7, f9)
      .func_181666_a(this.field_70552_h, this.field_70553_i, this.field_70551_j, this.field_82339_as)
      .func_187314_a(j, k)
      .func_181663_c(0.0F, 1.0F, 0.0F).func_181675_d();
    buffer.func_181662_b((f11 - par3 * f10 + par6 * f10), (f12 + par4 * f10), (f13 - par5 * f10 + par7 * f10)).func_187315_a(f7, f8)
      .func_181666_a(this.field_70552_h, this.field_70553_i, this.field_70551_j, this.field_82339_as)
      .func_187314_a(j, k)
      .func_181663_c(0.0F, 1.0F, 0.0F).func_181675_d();
    buffer.func_181662_b((f11 + par3 * f10 + par6 * f10), (f12 + par4 * f10), (f13 + par5 * f10 + par7 * f10)).func_187315_a(f6, f8)
      .func_181666_a(this.field_70552_h, this.field_70553_i, this.field_70551_j, this.field_82339_as)
      .func_187314_a(j, k)
      .func_181663_c(0.0F, 1.0F, 0.0F).func_181675_d();
    buffer.func_181662_b((f11 + par3 * f10 - par6 * f10), (f12 - par4 * f10), (f13 + par5 * f10 - par7 * f10)).func_187315_a(f6, f9)
      .func_181666_a(this.field_70552_h, this.field_70553_i, this.field_70551_j, this.field_82339_as)
      .func_187314_a(j, k)
      .func_181663_c(0.0F, 1.0F, 0.0F).func_181675_d();
    Tessellator.func_178181_a().func_78381_a();
    GlStateManager.func_179089_o();
    GlStateManager.func_179145_e();
    GlStateManager.func_179112_b(srcBlend, dstBlend);
    GlStateManager.func_179084_k();
  }
  
  private float getDistance(MCH_EntityAircraft entity) {
    float f = (float)(this.field_187126_f - entity.field_70165_t);
    float f1 = (float)(this.field_187127_g - entity.field_70163_u);
    float f2 = (float)(this.field_187128_h - entity.field_70161_v);
    return MathHelper.func_76129_c(f * f + f1 * f1 + f2 * f2);
  }
}
