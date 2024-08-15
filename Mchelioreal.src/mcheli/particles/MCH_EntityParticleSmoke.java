/*     */ package mcheli.particles;
/*     */ 
/*     */ import java.util.List;
/*     */ import mcheli.aircraft.MCH_EntityAircraft;
/*     */ import mcheli.wrapper.W_McClient;
/*     */ import net.minecraft.client.renderer.BufferBuilder;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.client.renderer.vertex.VertexFormat;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MCH_EntityParticleSmoke
/*     */   extends MCH_EntityParticleBase
/*     */ {
/*  26 */   private static final VertexFormat VERTEX_FORMAT = (new VertexFormat()).func_181721_a(DefaultVertexFormats.field_181713_m)
/*  27 */     .func_181721_a(DefaultVertexFormats.field_181715_o).func_181721_a(DefaultVertexFormats.field_181714_n)
/*  28 */     .func_181721_a(DefaultVertexFormats.field_181716_p)
/*  29 */     .func_181721_a(DefaultVertexFormats.field_181717_q)
/*  30 */     .func_181721_a(DefaultVertexFormats.field_181718_r);
/*     */ 
/*     */   
/*     */   public MCH_EntityParticleSmoke(World par1World, double x, double y, double z, double mx, double my, double mz) {
/*  34 */     super(par1World, x, y, z, mx, my, mz);
/*  35 */     this.field_70552_h = this.field_70553_i = this.field_70551_j = this.field_187136_p.nextFloat() * 0.3F + 0.7F;
/*  36 */     setParticleScale(this.field_187136_p.nextFloat() * 0.5F + 5.0F);
/*  37 */     setParticleMaxAge((int)(16.0D / (this.field_187136_p.nextFloat() * 0.8D + 0.2D)) + 2);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_189213_a() {
/*  43 */     this.field_187123_c = this.field_187126_f;
/*  44 */     this.field_187124_d = this.field_187127_g;
/*  45 */     this.field_187125_e = this.field_187128_h;
/*     */     
/*  47 */     if (this.field_70546_d < this.field_70547_e) {
/*     */       
/*  49 */       func_70536_a((int)(8.0D * this.field_70546_d / this.field_70547_e));
/*  50 */       this.field_70546_d++;
/*     */     
/*     */     }
/*     */     else {
/*     */       
/*  55 */       func_187112_i();
/*     */       
/*     */       return;
/*     */     } 
/*  59 */     if (this.diffusible)
/*     */     {
/*  61 */       if (this.field_70544_f < this.particleMaxScale)
/*     */       {
/*  63 */         this.field_70544_f += 0.8F;
/*     */       }
/*     */     }
/*     */     
/*  67 */     if (this.toWhite) {
/*     */       
/*  69 */       float mn = getMinColor();
/*  70 */       float mx = getMaxColor();
/*  71 */       float dist = mx - mn;
/*     */       
/*  73 */       if (dist > 0.2D) {
/*     */         
/*  75 */         this.field_70552_h += (mx - this.field_70552_h) * 0.016F;
/*  76 */         this.field_70553_i += (mx - this.field_70553_i) * 0.016F;
/*  77 */         this.field_70551_j += (mx - this.field_70551_j) * 0.016F;
/*     */       } 
/*     */     } 
/*     */     
/*  81 */     effectWind();
/*     */     
/*  83 */     if ((this.field_70546_d / this.field_70547_e) > this.moutionYUpAge) {
/*     */       
/*  85 */       this.field_187130_j += 0.02D;
/*     */     }
/*     */     else {
/*     */       
/*  89 */       this.field_187130_j += this.gravity;
/*     */     } 
/*     */     
/*  92 */     func_187110_a(this.field_187129_i, this.field_187130_j, this.field_187131_k);
/*     */     
/*  94 */     if (this.diffusible) {
/*     */       
/*  96 */       this.field_187129_i *= 0.96D;
/*  97 */       this.field_187131_k *= 0.96D;
/*  98 */       this.field_187130_j *= 0.96D;
/*     */     }
/*     */     else {
/*     */       
/* 102 */       this.field_187129_i *= 0.9D;
/* 103 */       this.field_187131_k *= 0.9D;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public float getMinColor() {
/* 109 */     return min(min(this.field_70551_j, this.field_70553_i), this.field_70552_h);
/*     */   }
/*     */ 
/*     */   
/*     */   public float getMaxColor() {
/* 114 */     return max(max(this.field_70551_j, this.field_70553_i), this.field_70552_h);
/*     */   }
/*     */ 
/*     */   
/*     */   public float min(float a, float b) {
/* 119 */     return (a < b) ? a : b;
/*     */   }
/*     */ 
/*     */   
/*     */   public float max(float a, float b) {
/* 124 */     return (a > b) ? a : b;
/*     */   }
/*     */ 
/*     */   
/*     */   public void effectWind() {
/* 129 */     if (this.isEffectedWind) {
/*     */ 
/*     */       
/* 132 */       List<MCH_EntityAircraft> list = this.field_187122_b.func_72872_a(MCH_EntityAircraft.class, 
/* 133 */           getCollisionBoundingBox().func_72314_b(15.0D, 15.0D, 15.0D));
/*     */       
/* 135 */       for (int i = 0; i < list.size(); i++) {
/*     */         
/* 137 */         MCH_EntityAircraft ac = list.get(i);
/*     */         
/* 139 */         if (ac.getThrottle() > 0.10000000149011612D) {
/*     */           
/* 141 */           float dist = getDistance(ac);
/* 142 */           double vel = (23.0D - dist) * 0.009999999776482582D * ac.getThrottle();
/* 143 */           double mx = ac.field_70165_t - this.field_187126_f;
/* 144 */           double mz = ac.field_70161_v - this.field_187128_h;
/*     */           
/* 146 */           this.field_187129_i -= mx * vel;
/* 147 */           this.field_187131_k -= mz * vel;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int func_70537_b() {
/* 156 */     return 3;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public int func_189214_a(float p_70070_1_) {
/* 163 */     double y = this.field_187127_g;
/*     */     
/* 165 */     this.field_187127_g += 3000.0D;
/* 166 */     int i = super.func_189214_a(p_70070_1_);
/* 167 */     this.field_187127_g = y;
/*     */     
/* 169 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_180434_a(BufferBuilder buffer, Entity entityIn, float par2, float par3, float par4, float par5, float par6, float par7) {
/* 177 */     W_McClient.MOD_bindTexture("textures/particles/smoke.png");
/*     */ 
/*     */     
/* 180 */     GlStateManager.func_179147_l();
/*     */ 
/*     */ 
/*     */     
/* 184 */     int srcBlend = GlStateManager.func_187397_v(3041);
/* 185 */     int dstBlend = GlStateManager.func_187397_v(3040);
/*     */ 
/*     */     
/* 188 */     GlStateManager.func_187401_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
/*     */     
/* 190 */     GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
/*     */     
/* 192 */     GlStateManager.func_179140_f();
/*     */     
/* 194 */     GlStateManager.func_179129_p();
/*     */     
/* 196 */     float f6 = this.field_94054_b / 8.0F;
/* 197 */     float f7 = f6 + 0.125F;
/* 198 */     float f8 = 0.0F;
/* 199 */     float f9 = 1.0F;
/* 200 */     float f10 = 0.1F * this.field_70544_f;
/* 201 */     float f11 = (float)(this.field_187123_c + (this.field_187126_f - this.field_187123_c) * par2 - field_70556_an);
/* 202 */     float f12 = (float)(this.field_187124_d + (this.field_187127_g - this.field_187124_d) * par2 - field_70554_ao);
/* 203 */     float f13 = (float)(this.field_187125_e + (this.field_187128_h - this.field_187125_e) * par2 - field_70555_ap);
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
/* 214 */     int i = func_189214_a(par2);
/* 215 */     int j = i >> 16 & 0xFFFF;
/* 216 */     int k = i & 0xFFFF;
/*     */     
/* 218 */     buffer.func_181668_a(7, VERTEX_FORMAT);
/* 219 */     buffer.func_181662_b((f11 - par3 * f10 - par6 * f10), (f12 - par4 * f10), (f13 - par5 * f10 - par7 * f10)).func_187315_a(f7, f9)
/* 220 */       .func_181666_a(this.field_70552_h, this.field_70553_i, this.field_70551_j, this.field_82339_as)
/* 221 */       .func_187314_a(j, k)
/* 222 */       .func_181663_c(0.0F, 1.0F, 0.0F).func_181675_d();
/* 223 */     buffer.func_181662_b((f11 - par3 * f10 + par6 * f10), (f12 + par4 * f10), (f13 - par5 * f10 + par7 * f10)).func_187315_a(f7, f8)
/* 224 */       .func_181666_a(this.field_70552_h, this.field_70553_i, this.field_70551_j, this.field_82339_as)
/* 225 */       .func_187314_a(j, k)
/* 226 */       .func_181663_c(0.0F, 1.0F, 0.0F).func_181675_d();
/* 227 */     buffer.func_181662_b((f11 + par3 * f10 + par6 * f10), (f12 + par4 * f10), (f13 + par5 * f10 + par7 * f10)).func_187315_a(f6, f8)
/* 228 */       .func_181666_a(this.field_70552_h, this.field_70553_i, this.field_70551_j, this.field_82339_as)
/* 229 */       .func_187314_a(j, k)
/* 230 */       .func_181663_c(0.0F, 1.0F, 0.0F).func_181675_d();
/* 231 */     buffer.func_181662_b((f11 + par3 * f10 - par6 * f10), (f12 - par4 * f10), (f13 + par5 * f10 - par7 * f10)).func_187315_a(f6, f9)
/* 232 */       .func_181666_a(this.field_70552_h, this.field_70553_i, this.field_70551_j, this.field_82339_as)
/* 233 */       .func_187314_a(j, k)
/* 234 */       .func_181663_c(0.0F, 1.0F, 0.0F).func_181675_d();
/* 235 */     Tessellator.func_178181_a().func_78381_a();
/*     */ 
/*     */     
/* 238 */     GlStateManager.func_179089_o();
/*     */     
/* 240 */     GlStateManager.func_179145_e();
/*     */     
/* 242 */     GlStateManager.func_179112_b(srcBlend, dstBlend);
/*     */     
/* 244 */     GlStateManager.func_179084_k();
/*     */   }
/*     */ 
/*     */   
/*     */   private float getDistance(MCH_EntityAircraft entity) {
/* 249 */     float f = (float)(this.field_187126_f - entity.field_70165_t);
/* 250 */     float f1 = (float)(this.field_187127_g - entity.field_70163_u);
/* 251 */     float f2 = (float)(this.field_187128_h - entity.field_70161_v);
/*     */     
/* 253 */     return MathHelper.func_76129_c(f * f + f1 * f1 + f2 * f2);
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\particles\MCH_EntityParticleSmoke.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */