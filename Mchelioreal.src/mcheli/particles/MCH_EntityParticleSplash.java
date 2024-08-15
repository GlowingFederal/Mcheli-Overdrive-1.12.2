/*     */ package mcheli.particles;
/*     */ 
/*     */ import mcheli.wrapper.W_Block;
/*     */ import mcheli.wrapper.W_McClient;
/*     */ import mcheli.wrapper.W_WorldFunc;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.client.renderer.BufferBuilder;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MCH_EntityParticleSplash
/*     */   extends MCH_EntityParticleBase
/*     */ {
/*     */   public MCH_EntityParticleSplash(World par1World, double x, double y, double z, double mx, double my, double mz) {
/*  22 */     super(par1World, x, y, z, mx, my, mz);
/*  23 */     this.field_70552_h = this.field_70553_i = this.field_70551_j = this.field_187136_p.nextFloat() * 0.3F + 0.7F;
/*  24 */     setParticleScale(this.field_187136_p.nextFloat() * 0.5F + 5.0F);
/*  25 */     setParticleMaxAge((int)(80.0D / (this.field_187136_p.nextFloat() * 0.8D + 0.2D)) + 2);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_189213_a() {
/*  31 */     this.field_187123_c = this.field_187126_f;
/*  32 */     this.field_187124_d = this.field_187127_g;
/*  33 */     this.field_187125_e = this.field_187128_h;
/*     */     
/*  35 */     if (this.field_70546_d < this.field_70547_e) {
/*     */       
/*  37 */       func_70536_a((int)(8.0D * this.field_70546_d / this.field_70547_e));
/*  38 */       this.field_70546_d++;
/*     */     
/*     */     }
/*     */     else {
/*     */       
/*  43 */       func_187112_i();
/*     */     } 
/*     */     
/*  46 */     this.field_187130_j -= 0.05999999865889549D;
/*  47 */     Block block = W_WorldFunc.getBlock(this.field_187122_b, (int)(this.field_187126_f + 0.5D), (int)(this.field_187127_g + 0.5D), (int)(this.field_187128_h + 0.5D));
/*     */     
/*  49 */     boolean beforeInWater = W_Block.func_149680_a(block, W_Block.getWater());
/*     */     
/*  51 */     func_187110_a(this.field_187129_i, this.field_187130_j, this.field_187131_k);
/*     */     
/*  53 */     block = W_WorldFunc.getBlock(this.field_187122_b, (int)(this.field_187126_f + 0.5D), (int)(this.field_187127_g + 0.5D), (int)(this.field_187128_h + 0.5D));
/*     */     
/*  55 */     boolean nowInWater = W_Block.func_149680_a(block, W_Block.getWater());
/*     */     
/*  57 */     if (this.field_187130_j < -0.6D && !beforeInWater && nowInWater) {
/*     */       
/*  59 */       double p = -this.field_187130_j * 10.0D;
/*     */       
/*  61 */       for (int i = 0; i < p; i++)
/*     */       {
/*     */ 
/*     */         
/*  65 */         this.field_187122_b.func_175688_a(EnumParticleTypes.WATER_SPLASH, this.field_187126_f + 0.5D + (this.field_187136_p
/*  66 */             .nextDouble() - 0.5D) * 2.0D, this.field_187127_g + this.field_187136_p.nextDouble(), this.field_187128_h + 0.5D + (this.field_187136_p
/*  67 */             .nextDouble() - 0.5D) * 2.0D, (this.field_187136_p
/*  68 */             .nextDouble() - 0.5D) * 2.0D, 4.0D, (this.field_187136_p.nextDouble() - 0.5D) * 2.0D, new int[0]);
/*  69 */         this.field_187122_b.func_175688_a(EnumParticleTypes.WATER_BUBBLE, this.field_187126_f + 0.5D + (this.field_187136_p
/*  70 */             .nextDouble() - 0.5D) * 2.0D, this.field_187127_g - this.field_187136_p.nextDouble(), this.field_187128_h + 0.5D + (this.field_187136_p
/*  71 */             .nextDouble() - 0.5D) * 2.0D, (this.field_187136_p
/*  72 */             .nextDouble() - 0.5D) * 2.0D, -0.5D, (this.field_187136_p.nextDouble() - 0.5D) * 2.0D, new int[0]);
/*     */       }
/*     */     
/*  75 */     } else if (this.field_187132_l) {
/*     */ 
/*     */       
/*  78 */       func_187112_i();
/*     */     } 
/*     */     
/*  81 */     this.field_187129_i *= 0.9D;
/*  82 */     this.field_187131_k *= 0.9D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_180434_a(BufferBuilder buffer, Entity entityIn, float par2, float par3, float par4, float par5, float par6, float par7) {
/*  90 */     W_McClient.MOD_bindTexture("textures/particles/smoke.png");
/*     */     
/*  92 */     float f6 = this.field_94054_b / 8.0F;
/*  93 */     float f7 = f6 + 0.125F;
/*  94 */     float f8 = 0.0F;
/*  95 */     float f9 = 1.0F;
/*  96 */     float f10 = 0.1F * this.field_70544_f;
/*  97 */     float f11 = (float)(this.field_187123_c + (this.field_187126_f - this.field_187123_c) * par2 - field_70556_an);
/*  98 */     float f12 = (float)(this.field_187124_d + (this.field_187127_g - this.field_187124_d) * par2 - field_70554_ao);
/*  99 */     float f13 = (float)(this.field_187125_e + (this.field_187128_h - this.field_187125_e) * par2 - field_70555_ap);
/* 100 */     float f14 = 1.0F;
/* 101 */     int i = func_189214_a(par2);
/* 102 */     int j = i >> 16 & 0xFFFF;
/* 103 */     int k = i & 0xFFFF;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 111 */     buffer.func_181662_b((f11 - par3 * f10 - par6 * f10), (f12 - par4 * f10), (f13 - par5 * f10 - par7 * f10)).func_187315_a(f7, f9)
/* 112 */       .func_181666_a(this.field_70552_h * f14, this.field_70553_i * f14, this.field_70551_j * f14, this.field_82339_as)
/* 113 */       .func_187314_a(j, k).func_181675_d();
/* 114 */     buffer.func_181662_b((f11 - par3 * f10 + par6 * f10), (f12 + par4 * f10), (f13 - par5 * f10 + par7 * f10)).func_187315_a(f7, f8)
/* 115 */       .func_181666_a(this.field_70552_h * f14, this.field_70553_i * f14, this.field_70551_j * f14, this.field_82339_as)
/* 116 */       .func_187314_a(j, k).func_181675_d();
/* 117 */     buffer.func_181662_b((f11 + par3 * f10 + par6 * f10), (f12 + par4 * f10), (f13 + par5 * f10 + par7 * f10)).func_187315_a(f6, f8)
/* 118 */       .func_181666_a(this.field_70552_h * f14, this.field_70553_i * f14, this.field_70551_j * f14, this.field_82339_as)
/* 119 */       .func_187314_a(j, k).func_181675_d();
/* 120 */     buffer.func_181662_b((f11 + par3 * f10 - par6 * f10), (f12 - par4 * f10), (f13 + par5 * f10 - par7 * f10)).func_187315_a(f6, f9)
/* 121 */       .func_181666_a(this.field_70552_h * f14, this.field_70553_i * f14, this.field_70551_j * f14, this.field_82339_as)
/* 122 */       .func_187314_a(j, k).func_181675_d();
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\particles\MCH_EntityParticleSplash.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */