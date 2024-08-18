package mcheli.particles;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class MCH_EntityParticleExplode extends MCH_EntityParticleBase {
  private static final VertexFormat VERTEX_FORMAT = (new VertexFormat()).func_181721_a(DefaultVertexFormats.field_181713_m)
    .func_181721_a(DefaultVertexFormats.field_181715_o).func_181721_a(DefaultVertexFormats.field_181714_n)
    .func_181721_a(DefaultVertexFormats.field_181716_p).func_181721_a(DefaultVertexFormats.field_181717_q)
    .func_181721_a(DefaultVertexFormats.field_181718_r);
  
  private static final ResourceLocation texture = new ResourceLocation("textures/entity/explosion.png");
  
  private int nowCount;
  
  private int endCount;
  
  private TextureManager theRenderEngine;
  
  private float size;
  
  public MCH_EntityParticleExplode(World w, double x, double y, double z, double size, double age, double mz) {
    super(w, x, y, z, 0.0D, 0.0D, 0.0D);
    this.theRenderEngine = (Minecraft.func_71410_x()).field_71446_o;
    this.endCount = 1 + (int)age;
    this.size = (float)size;
  }
  
  public void func_180434_a(BufferBuilder buffer, Entity entityIn, float p_70539_2_, float p_70539_3_, float p_70539_4_, float p_70539_5_, float p_70539_6_, float p_70539_7_) {
    int i = (int)((this.nowCount + p_70539_2_) * 15.0F / this.endCount);
    if (i <= 15) {
      GlStateManager.func_179147_l();
      int srcBlend = GlStateManager.func_187397_v(3041);
      int dstBlend = GlStateManager.func_187397_v(3040);
      GlStateManager.func_187401_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
      GlStateManager.func_179129_p();
      this.theRenderEngine.func_110577_a(texture);
      float f6 = (i % 4) / 4.0F;
      float f7 = f6 + 0.24975F;
      float f8 = (i / 4) / 4.0F;
      float f9 = f8 + 0.24975F;
      float f10 = 2.0F * this.size;
      float f11 = (float)(this.field_187123_c + (this.field_187126_f - this.field_187123_c) * p_70539_2_ - field_70556_an);
      float f12 = (float)(this.field_187124_d + (this.field_187127_g - this.field_187124_d) * p_70539_2_ - field_70554_ao);
      float f13 = (float)(this.field_187125_e + (this.field_187128_h - this.field_187125_e) * p_70539_2_ - field_70555_ap);
      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
      RenderHelper.func_74518_a();
      int j = 15728880;
      int k = j >> 16 & 0xFFFF;
      int l = j & 0xFFFF;
      buffer.func_181668_a(7, VERTEX_FORMAT);
      buffer.func_181662_b((f11 - p_70539_3_ * f10 - p_70539_6_ * f10), (f12 - p_70539_4_ * f10), (f13 - p_70539_5_ * f10 - p_70539_7_ * f10))
        .func_187315_a(f7, f9)
        .func_181666_a(this.field_70552_h, this.field_70553_i, this.field_70551_j, this.field_82339_as).func_187314_a(k, l)
        .func_181663_c(0.0F, 1.0F, 0.0F).func_181675_d();
      buffer.func_181662_b((f11 - p_70539_3_ * f10 + p_70539_6_ * f10), (f12 + p_70539_4_ * f10), (f13 - p_70539_5_ * f10 + p_70539_7_ * f10))
        .func_187315_a(f7, f8)
        .func_181666_a(this.field_70552_h, this.field_70553_i, this.field_70551_j, this.field_82339_as).func_187314_a(k, l)
        .func_181663_c(0.0F, 1.0F, 0.0F).func_181675_d();
      buffer.func_181662_b((f11 + p_70539_3_ * f10 + p_70539_6_ * f10), (f12 + p_70539_4_ * f10), (f13 + p_70539_5_ * f10 + p_70539_7_ * f10))
        .func_187315_a(f6, f8)
        .func_181666_a(this.field_70552_h, this.field_70553_i, this.field_70551_j, this.field_82339_as).func_187314_a(k, l)
        .func_181663_c(0.0F, 1.0F, 0.0F).func_181675_d();
      buffer.func_181662_b((f11 + p_70539_3_ * f10 - p_70539_6_ * f10), (f12 - p_70539_4_ * f10), (f13 + p_70539_5_ * f10 - p_70539_7_ * f10))
        .func_187315_a(f6, f9)
        .func_181666_a(this.field_70552_h, this.field_70553_i, this.field_70551_j, this.field_82339_as).func_187314_a(k, l)
        .func_181663_c(0.0F, 1.0F, 0.0F).func_181675_d();
      Tessellator.func_178181_a().func_78381_a();
      GlStateManager.func_179136_a(0.0F, 0.0F);
      GlStateManager.func_179145_e();
      GlStateManager.func_179089_o();
      GlStateManager.func_179112_b(srcBlend, dstBlend);
      GlStateManager.func_179084_k();
    } 
  }
  
  public int func_189214_a(float p_70070_1_) {
    return 15728880;
  }
  
  public void func_189213_a() {
    this.field_187123_c = this.field_187126_f;
    this.field_187124_d = this.field_187127_g;
    this.field_187125_e = this.field_187128_h;
    this.nowCount++;
    if (this.nowCount == this.endCount)
      func_187112_i(); 
  }
  
  public int func_70537_b() {
    return 3;
  }
}
