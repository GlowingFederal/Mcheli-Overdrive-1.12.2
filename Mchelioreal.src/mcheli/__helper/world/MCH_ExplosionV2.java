/*     */ package mcheli.__helper.world;
/*     */ 
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import javax.annotation.Nullable;
/*     */ import mcheli.MCH_Config;
/*     */ import mcheli.MCH_DamageFactor;
/*     */ import mcheli.MCH_Explosion;
/*     */ import mcheli.MCH_Lib;
/*     */ import mcheli.flare.MCH_EntityFlare;
/*     */ import mcheli.particles.MCH_ParticleParam;
/*     */ import mcheli.particles.MCH_ParticlesUtil;
/*     */ import mcheli.weapon.MCH_EntityBaseBullet;
/*     */ import mcheli.wrapper.W_AxisAlignedBB;
/*     */ import mcheli.wrapper.W_Block;
/*     */ import mcheli.wrapper.W_Blocks;
/*     */ import mcheli.wrapper.W_ChunkPosition;
/*     */ import mcheli.wrapper.W_Entity;
/*     */ import mcheli.wrapper.W_WorldFunc;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.enchantment.EnchantmentProtection;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.SoundCategory;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.Explosion;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MCH_ExplosionV2
/*     */   extends Explosion
/*     */ {
/*  53 */   private static Random explosionRNG = new Random();
/*  54 */   public final int field_77289_h = 16;
/*     */   public World field_77287_j;
/*     */   public final Entity field_77283_e;
/*     */   public final double field_77284_b;
/*     */   public final double field_77285_c;
/*     */   public final double field_77282_d;
/*     */   public final float field_77280_f;
/*     */   public final boolean field_77286_a;
/*     */   public final boolean field_82755_b;
/*     */   public boolean isDestroyBlock;
/*     */   public int countSetFireEntity;
/*     */   public boolean isPlaySound;
/*     */   public boolean isInWater;
/*     */   private MCH_Explosion.ExplosionResult result;
/*     */   public EntityPlayer explodedPlayer;
/*     */   public float explosionSizeBlock;
/*  70 */   public MCH_DamageFactor damageFactor = null;
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public MCH_ExplosionV2(World worldIn, double x, double y, double z, float size, List<BlockPos> affectedPositions) {
/*  75 */     this(worldIn, (Entity)null, (Entity)null, x, y, z, size, false, true);
/*  76 */     func_180343_e().addAll(affectedPositions);
/*  77 */     this.isPlaySound = false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public MCH_ExplosionV2(World worldIn, @Nullable Entity exploderIn, @Nullable Entity player, double x, double y, double z, float size, boolean flaming, boolean damagesTerrain) {
/*  83 */     super(worldIn, exploderIn, x, y, z, size, flaming, damagesTerrain);
/*  84 */     this.field_77287_j = worldIn;
/*  85 */     this.field_77283_e = exploderIn;
/*  86 */     this.explodedPlayer = (player instanceof EntityPlayer) ? (EntityPlayer)player : null;
/*  87 */     this.field_77284_b = x;
/*  88 */     this.field_77285_c = y;
/*  89 */     this.field_77282_d = z;
/*  90 */     this.field_77280_f = size;
/*  91 */     this.field_77286_a = flaming;
/*  92 */     this.field_82755_b = damagesTerrain;
/*     */     
/*  94 */     this.isDestroyBlock = false;
/*  95 */     this.explosionSizeBlock = size;
/*  96 */     this.countSetFireEntity = 0;
/*  97 */     this.isPlaySound = true;
/*  98 */     this.isInWater = false;
/*  99 */     this.result = new MCH_Explosion.ExplosionResult();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_77278_a() {
/* 105 */     HashSet<BlockPos> hashset = new HashSet<>();
/*     */     
/* 107 */     for (int i = 0; i < 16; i++) {
/*     */       
/* 109 */       for (int n = 0; n < 16; n++) {
/*     */         
/* 111 */         for (int i1 = 0; i1 < 16; i1++) {
/*     */           
/* 113 */           if (i != 0)
/*     */           {
/* 115 */             if (i != 15 && n != 0)
/*     */             {
/* 117 */               if (n != 15 && i1 != 0)
/*     */               {
/* 119 */                 if (i1 != 15) {
/*     */                   continue;
/*     */                 }
/*     */               }
/*     */             }
/*     */           }
/* 125 */           double d3 = (i / 15.0F * 2.0F - 1.0F);
/* 126 */           double d4 = (n / 15.0F * 2.0F - 1.0F);
/* 127 */           double d5 = (i1 / 15.0F * 2.0F - 1.0F);
/* 128 */           double d6 = Math.sqrt(d3 * d3 + d4 * d4 + d5 * d5);
/* 129 */           d3 /= d6;
/* 130 */           d4 /= d6;
/* 131 */           d5 /= d6;
/* 132 */           float f1 = this.explosionSizeBlock * (0.7F + this.field_77287_j.field_73012_v.nextFloat() * 0.6F);
/* 133 */           double d0 = this.field_77284_b;
/* 134 */           double d1 = this.field_77285_c;
/* 135 */           double d2 = this.field_77282_d;
/*     */           
/* 137 */           for (; f1 > 0.0F; f1 -= 0.22500001F) {
/*     */             
/* 139 */             int l = MathHelper.func_76128_c(d0);
/* 140 */             int i3 = MathHelper.func_76128_c(d1);
/* 141 */             int j1 = MathHelper.func_76128_c(d2);
/* 142 */             int k1 = W_WorldFunc.getBlockId(this.field_77287_j, l, i3, j1);
/* 143 */             BlockPos blockpos = new BlockPos(l, i3, j1);
/* 144 */             IBlockState iblockstate = this.field_77287_j.func_180495_p(blockpos);
/* 145 */             Block block = iblockstate.func_177230_c();
/*     */             
/* 147 */             if (k1 > 0) {
/*     */               float f3;
/*     */ 
/*     */               
/* 151 */               if (this.field_77283_e != null) {
/*     */                 
/* 153 */                 f3 = W_Entity.getBlockExplosionResistance(this.field_77283_e, this, this.field_77287_j, l, i3, j1, block);
/*     */               
/*     */               }
/*     */               else {
/*     */                 
/* 158 */                 f3 = block.getExplosionResistance(this.field_77287_j, blockpos, this.field_77283_e, this);
/*     */               } 
/*     */               
/* 161 */               if (this.isInWater)
/*     */               {
/* 163 */                 f3 *= this.field_77287_j.field_73012_v.nextFloat() * 0.2F + 0.2F;
/*     */               }
/*     */               
/* 166 */               f1 -= (f3 + 0.3F) * 0.3F;
/*     */             } 
/*     */             
/* 169 */             if (f1 > 0.0F && (this.field_77283_e == null || 
/* 170 */               W_Entity.shouldExplodeBlock(this.field_77283_e, this, this.field_77287_j, l, i3, j1, k1, f1)))
/*     */             {
/* 172 */               hashset.add(blockpos);
/*     */             }
/*     */             
/* 175 */             d0 += d3 * 0.30000001192092896D;
/* 176 */             d1 += d4 * 0.30000001192092896D;
/* 177 */             d2 += d5 * 0.30000001192092896D;
/*     */           } 
/*     */           continue;
/*     */         } 
/*     */       } 
/*     */     } 
/* 183 */     float f = this.field_77280_f * 2.0F;
/* 184 */     func_180343_e().addAll(hashset);
/* 185 */     int m = MathHelper.func_76128_c(this.field_77284_b - f - 1.0D);
/* 186 */     int j = MathHelper.func_76128_c(this.field_77284_b + f + 1.0D);
/* 187 */     int k = MathHelper.func_76128_c(this.field_77285_c - f - 1.0D);
/* 188 */     int l1 = MathHelper.func_76128_c(this.field_77285_c + f + 1.0D);
/* 189 */     int i2 = MathHelper.func_76128_c(this.field_77282_d - f - 1.0D);
/* 190 */     int j2 = MathHelper.func_76128_c(this.field_77282_d + f + 1.0D);
/* 191 */     List<Entity> list = this.field_77287_j.func_72839_b(this.field_77283_e, 
/* 192 */         W_AxisAlignedBB.getAABB(m, k, i2, j, l1, j2));
/* 193 */     Vec3d vec3 = W_WorldFunc.getWorldVec3(this.field_77287_j, this.field_77284_b, this.field_77285_c, this.field_77282_d);
/*     */     
/* 195 */     for (int k2 = 0; k2 < list.size(); k2++) {
/*     */       
/* 197 */       Entity entity = list.get(k2);
/* 198 */       double d7 = entity.func_70011_f(this.field_77284_b, this.field_77285_c, this.field_77282_d) / f;
/*     */       
/* 200 */       if (d7 <= 1.0D) {
/*     */         
/* 202 */         double d0 = entity.field_70165_t - this.field_77284_b;
/* 203 */         double d1 = entity.field_70163_u + entity.func_70047_e() - this.field_77285_c;
/* 204 */         double d2 = entity.field_70161_v - this.field_77282_d;
/* 205 */         double d8 = MathHelper.func_76133_a(d0 * d0 + d1 * d1 + d2 * d2);
/*     */         
/* 207 */         if (d8 != 0.0D) {
/*     */           
/* 209 */           d0 /= d8;
/* 210 */           d1 /= d8;
/* 211 */           d2 /= d8;
/* 212 */           double d9 = getBlockDensity(vec3, entity.func_174813_aQ());
/* 213 */           double d10 = (1.0D - d7) * d9;
/* 214 */           float damage = (int)((d10 * d10 + d10) / 2.0D * 8.0D * f + 1.0D);
/*     */           
/* 216 */           if (damage > 0.0F)
/*     */           {
/* 218 */             if (!(entity instanceof net.minecraft.entity.item.EntityItem) && !(entity instanceof net.minecraft.entity.item.EntityExpBottle) && !(entity instanceof net.minecraft.entity.item.EntityXPOrb) && 
/* 219 */               !W_Entity.isEntityFallingBlock(entity))
/*     */             {
/* 221 */               if (entity instanceof MCH_EntityBaseBullet && this.explodedPlayer instanceof EntityPlayer) {
/*     */                 
/* 223 */                 if (!W_Entity.isEqual(((MCH_EntityBaseBullet)entity).shootingEntity, (Entity)this.explodedPlayer))
/*     */                 {
/*     */                   
/* 226 */                   this.result.hitEntity = true;
/* 227 */                   MCH_Lib.DbgLog(this.field_77287_j, "MCH_Explosion.doExplosionA:Damage=%.1f:HitEntityBullet=" + entity
/* 228 */                       .getClass(), new Object[] { Float.valueOf(damage) });
/*     */                 }
/*     */               
/*     */               } else {
/*     */                 
/* 233 */                 MCH_Lib.DbgLog(this.field_77287_j, "MCH_Explosion.doExplosionA:Damage=%.1f:HitEntity=" + entity
/* 234 */                     .getClass(), new Object[] {
/* 235 */                       Float.valueOf(damage) });
/* 236 */                 this.result.hitEntity = true;
/*     */               } 
/*     */             }
/*     */           }
/*     */           
/* 241 */           MCH_Lib.applyEntityHurtResistantTimeConfig(entity);
/* 242 */           DamageSource ds = DamageSource.func_94539_a(this);
/* 243 */           damage = MCH_Config.applyDamageVsEntity(entity, ds, damage);
/* 244 */           damage *= (this.damageFactor != null) ? this.damageFactor.getDamageFactor(entity) : 1.0F;
/* 245 */           W_Entity.attackEntityFrom(entity, ds, damage);
/* 246 */           double d11 = d10;
/*     */           
/* 248 */           if (entity instanceof EntityLivingBase)
/*     */           {
/* 250 */             d11 = EnchantmentProtection.func_92092_a((EntityLivingBase)entity, d10);
/*     */           }
/*     */           
/* 253 */           if (!(entity instanceof MCH_EntityBaseBullet)) {
/*     */             
/* 255 */             entity.field_70159_w += d0 * d11 * 0.4D;
/* 256 */             entity.field_70181_x += d1 * d11 * 0.1D;
/* 257 */             entity.field_70179_y += d2 * d11 * 0.4D;
/*     */           } 
/*     */           
/* 260 */           if (entity instanceof EntityPlayer)
/*     */           {
/* 262 */             func_77277_b().put((EntityPlayer)entity, 
/* 263 */                 W_WorldFunc.getWorldVec3(this.field_77287_j, d0 * d10, d1 * d10, d2 * d10));
/*     */           }
/*     */           
/* 266 */           if (damage > 0.0F && this.countSetFireEntity > 0) {
/*     */             
/* 268 */             double fireFactor = 1.0D - d8 / f;
/*     */             
/* 270 */             if (fireFactor > 0.0D)
/*     */             {
/* 272 */               entity.func_70015_d((int)(fireFactor * this.countSetFireEntity));
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private double getBlockDensity(Vec3d vec3, AxisAlignedBB bb) {
/* 282 */     double d0 = 1.0D / ((bb.field_72336_d - bb.field_72340_a) * 2.0D + 1.0D);
/* 283 */     double d1 = 1.0D / ((bb.field_72337_e - bb.field_72338_b) * 2.0D + 1.0D);
/* 284 */     double d2 = 1.0D / ((bb.field_72334_f - bb.field_72339_c) * 2.0D + 1.0D);
/*     */     
/* 286 */     if (d0 >= 0.0D && d1 >= 0.0D && d2 >= 0.0D) {
/*     */       
/* 288 */       int i = 0;
/* 289 */       int j = 0;
/*     */       float f;
/* 291 */       for (f = 0.0F; f <= 1.0F; f = (float)(f + d0)) {
/*     */         float f1;
/* 293 */         for (f1 = 0.0F; f1 <= 1.0F; f1 = (float)(f1 + d1)) {
/*     */           float f2;
/* 295 */           for (f2 = 0.0F; f2 <= 1.0F; f2 = (float)(f2 + d2)) {
/*     */             
/* 297 */             double d3 = bb.field_72340_a + (bb.field_72336_d - bb.field_72340_a) * f;
/* 298 */             double d4 = bb.field_72338_b + (bb.field_72337_e - bb.field_72338_b) * f1;
/* 299 */             double d5 = bb.field_72339_c + (bb.field_72334_f - bb.field_72339_c) * f2;
/*     */             
/* 301 */             if (this.field_77287_j.func_147447_a(new Vec3d(d3, d4, d5), vec3, false, true, false) == null)
/*     */             {
/* 303 */               i++;
/*     */             }
/* 305 */             j++;
/*     */           } 
/*     */         } 
/*     */       } 
/* 309 */       return (i / j);
/*     */     } 
/*     */     
/* 312 */     return 0.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_77279_a(boolean spawnParticles) {
/* 318 */     doExplosionB(spawnParticles, false);
/*     */   }
/*     */ 
/*     */   
/*     */   private void doExplosionB(boolean spawnParticles, boolean vanillaMode) {
/* 323 */     if (this.isPlaySound)
/*     */     {
/* 325 */       this.field_77287_j.func_184148_a(null, this.field_77284_b, this.field_77285_c, this.field_77282_d, SoundEvents.field_187539_bB, SoundCategory.BLOCKS, 4.0F, (1.0F + (this.field_77287_j.field_73012_v
/* 326 */           .nextFloat() - this.field_77287_j.field_73012_v.nextFloat()) * 0.2F) * 0.7F);
/*     */     }
/*     */     
/* 329 */     if (this.field_82755_b) {
/*     */       
/* 331 */       Iterator<BlockPos> iterator = func_180343_e().iterator();
/* 332 */       int cnt = 0;
/* 333 */       int flareCnt = (int)this.field_77280_f;
/*     */       
/* 335 */       while (iterator.hasNext()) {
/*     */         
/* 337 */         BlockPos chunkposition = iterator.next();
/* 338 */         int i = W_ChunkPosition.getChunkPosX(chunkposition);
/* 339 */         int j = W_ChunkPosition.getChunkPosY(chunkposition);
/* 340 */         int k = W_ChunkPosition.getChunkPosZ(chunkposition);
/* 341 */         int l = W_WorldFunc.getBlockId(this.field_77287_j, i, j, k);
/* 342 */         cnt++;
/*     */         
/* 344 */         if (spawnParticles)
/*     */         {
/* 346 */           if (vanillaMode) {
/*     */             
/* 348 */             spawnVanillaExlosionEffect(i, j, k);
/*     */ 
/*     */           
/*     */           }
/* 352 */           else if (spawnExlosionEffect(cnt, i, j, k, (flareCnt > 0))) {
/*     */             
/* 354 */             flareCnt--;
/*     */           } 
/*     */         }
/*     */ 
/*     */         
/* 359 */         if (l > 0 && this.isDestroyBlock && this.explosionSizeBlock > 0.0F && MCH_Config.Explosion_DestroyBlock.prmBool) {
/*     */ 
/*     */           
/* 362 */           Block block = W_Block.getBlockById(l);
/*     */           
/* 364 */           if (block.func_149659_a(this))
/*     */           {
/* 366 */             block.func_180653_a(this.field_77287_j, chunkposition, this.field_77287_j
/* 367 */                 .func_180495_p(chunkposition), 1.0F / this.explosionSizeBlock, 0);
/*     */           }
/*     */           
/* 370 */           block.onBlockExploded(this.field_77287_j, chunkposition, this);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 375 */     if (this.field_77286_a && MCH_Config.Explosion_FlamingBlock.prmBool) {
/*     */       
/* 377 */       Iterator<BlockPos> iterator = func_180343_e().iterator();
/*     */       
/* 379 */       while (iterator.hasNext()) {
/*     */         
/* 381 */         BlockPos chunkposition = iterator.next();
/* 382 */         int i = W_ChunkPosition.getChunkPosX(chunkposition);
/* 383 */         int j = W_ChunkPosition.getChunkPosY(chunkposition);
/* 384 */         int k = W_ChunkPosition.getChunkPosZ(chunkposition);
/* 385 */         int l = W_WorldFunc.getBlockId(this.field_77287_j, i, j, k);
/* 386 */         IBlockState iblockstate = this.field_77287_j.func_180495_p(chunkposition.func_177977_b());
/* 387 */         Block b = iblockstate.func_177230_c();
/*     */         
/* 389 */         if (l == 0 && b != null && iblockstate.func_185914_p() && explosionRNG.nextInt(3) == 0)
/*     */         {
/* 391 */           W_WorldFunc.setBlock(this.field_77287_j, i, j, k, (Block)W_Blocks.field_150480_ab);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean spawnExlosionEffect(int cnt, int i, int j, int k, boolean spawnFlare) {
/* 399 */     boolean spawnedFlare = false;
/* 400 */     double d0 = (i + this.field_77287_j.field_73012_v.nextFloat());
/* 401 */     double d1 = (j + this.field_77287_j.field_73012_v.nextFloat());
/* 402 */     double d2 = (k + this.field_77287_j.field_73012_v.nextFloat());
/* 403 */     double mx = d0 - this.field_77284_b;
/* 404 */     double my = d1 - this.field_77285_c;
/* 405 */     double mz = d2 - this.field_77282_d;
/* 406 */     double d6 = MathHelper.func_76133_a(mx * mx + my * my + mz * mz);
/* 407 */     mx /= d6;
/* 408 */     my /= d6;
/* 409 */     mz /= d6;
/* 410 */     double d7 = 0.5D / (d6 / this.field_77280_f + 0.1D);
/* 411 */     d7 *= (this.field_77287_j.field_73012_v.nextFloat() * this.field_77287_j.field_73012_v.nextFloat() + 0.3F);
/* 412 */     mx *= d7 * 0.5D;
/* 413 */     my *= d7 * 0.5D;
/* 414 */     mz *= d7 * 0.5D;
/*     */     
/* 416 */     double px = (d0 + this.field_77284_b * 1.0D) / 2.0D;
/* 417 */     double py = (d1 + this.field_77285_c * 1.0D) / 2.0D;
/* 418 */     double pz = (d2 + this.field_77282_d * 1.0D) / 2.0D;
/*     */     
/* 420 */     double r = Math.PI * this.field_77287_j.field_73012_v.nextInt(360) / 180.0D;
/* 421 */     if (this.field_77280_f >= 4.0F && spawnFlare) {
/*     */       
/* 423 */       double a = Math.min((this.field_77280_f / 12.0F), 0.6D) * (0.5F + this.field_77287_j.field_73012_v.nextFloat() * 0.5F);
/*     */       
/* 425 */       this.field_77287_j.func_72838_d((Entity)new MCH_EntityFlare(this.field_77287_j, px, py + 2.0D, pz, Math.sin(r) * a, (1.0D + my / 5.0D) * a, 
/* 426 */             Math.cos(r) * a, 2.0F, 0));
/* 427 */       spawnedFlare = true;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 432 */     if (cnt % 4 == 0) {
/*     */       
/* 434 */       float bdf = Math.min(this.field_77280_f / 3.0F, 2.0F) * (0.5F + this.field_77287_j.field_73012_v.nextFloat() * 0.5F);
/*     */       
/* 436 */       MCH_ParticlesUtil.spawnParticleTileDust(this.field_77287_j, (int)(px + 0.5D), (int)(py - 0.5D), (int)(pz + 0.5D), px, py + 1.0D, pz, 
/* 437 */           Math.sin(r) * bdf, 0.5D + my / 5.0D * bdf, Math.cos(r) * bdf, 
/* 438 */           Math.min(this.field_77280_f / 2.0F, 3.0F) * (0.5F + this.field_77287_j.field_73012_v.nextFloat() * 0.5F));
/*     */     } 
/*     */     
/* 441 */     int es = (int)((this.field_77280_f >= 4.0F) ? this.field_77280_f : 4.0F);
/* 442 */     if (this.field_77280_f <= 1.0F || cnt % es == 0) {
/*     */       
/* 444 */       if (this.field_77287_j.field_73012_v.nextBoolean()) {
/*     */         
/* 446 */         my *= 3.0D;
/* 447 */         mx *= 0.1D;
/* 448 */         mz *= 0.1D;
/*     */       }
/*     */       else {
/*     */         
/* 452 */         my *= 0.2D;
/* 453 */         mx *= 3.0D;
/* 454 */         mz *= 3.0D;
/*     */       } 
/*     */       
/* 457 */       MCH_ParticleParam prm = new MCH_ParticleParam(this.field_77287_j, "explode", px, py, pz, mx, my, mz, (this.field_77280_f < 8.0F) ? (this.field_77280_f * 2.0F) : ((this.field_77280_f < 2.0F) ? 2.0F : 16.0F));
/*     */ 
/*     */       
/* 460 */       prm.r = prm.g = prm.b = 0.3F + this.field_77287_j.field_73012_v.nextFloat() * 0.4F;
/* 461 */       prm.r += 0.1F;
/* 462 */       prm.g += 0.05F;
/* 463 */       prm.b += 0.0F;
/* 464 */       prm.age = 10 + this.field_77287_j.field_73012_v.nextInt(30);
/* 465 */       MCH_ParticleParam tmp1231_1229 = prm;
/* 466 */       tmp1231_1229.age = (int)(tmp1231_1229.age * ((this.field_77280_f < 6.0F) ? this.field_77280_f : 6.0F));
/* 467 */       prm.age = prm.age * 2 / 3;
/* 468 */       prm.diffusible = true;
/*     */       
/* 470 */       MCH_ParticlesUtil.spawnParticle(prm);
/*     */     } 
/*     */     
/* 473 */     return spawnedFlare;
/*     */   }
/*     */ 
/*     */   
/*     */   private void spawnVanillaExlosionEffect(int i, int j, int k) {
/* 478 */     double d0 = (i + this.field_77287_j.field_73012_v.nextFloat());
/* 479 */     double d1 = (j + this.field_77287_j.field_73012_v.nextFloat());
/* 480 */     double d2 = (k + this.field_77287_j.field_73012_v.nextFloat());
/* 481 */     double d3 = d0 - this.field_77284_b;
/* 482 */     double d4 = d1 - this.field_77285_c;
/* 483 */     double d5 = d2 - this.field_77282_d;
/* 484 */     double d6 = MathHelper.func_76133_a(d3 * d3 + d4 * d4 + d5 * d5);
/* 485 */     d3 /= d6;
/* 486 */     d4 /= d6;
/* 487 */     d5 /= d6;
/* 488 */     double d7 = 0.5D / (d6 / this.field_77280_f + 0.1D);
/* 489 */     d7 *= (this.field_77287_j.field_73012_v.nextFloat() * this.field_77287_j.field_73012_v.nextFloat() + 0.3F);
/* 490 */     d3 *= d7;
/* 491 */     d4 *= d7;
/* 492 */     d5 *= d7;
/* 493 */     MCH_ParticlesUtil.DEF_spawnParticle("explode", (d0 + this.field_77284_b) / 2.0D, (d1 + this.field_77285_c) / 2.0D, (d2 + this.field_77282_d) / 2.0D, d3, d4, d5, 10.0F, new int[0]);
/*     */     
/* 495 */     MCH_ParticlesUtil.DEF_spawnParticle("smoke", d0, d1, d2, d3, d4, d5, 10.0F, new int[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public EntityLivingBase func_94613_c() {
/* 501 */     if (this.explodedPlayer != null && this.explodedPlayer instanceof EntityPlayer)
/*     */     {
/* 503 */       return (EntityLivingBase)this.explodedPlayer;
/*     */     }
/* 505 */     return super.func_94613_c();
/*     */   }
/*     */ 
/*     */   
/*     */   public MCH_Explosion.ExplosionResult getResult() {
/* 510 */     return this.result;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void playExplosionSound(World world, double x, double y, double z) {
/* 515 */     world.func_184134_a(x, y, z, SoundEvents.field_187539_bB, SoundCategory.BLOCKS, 4.0F, (1.0F + (world.field_73012_v
/* 516 */         .nextFloat() - world.field_73012_v.nextFloat()) * 0.2F) * 0.7F, false);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void effectMODExplosion(World world, double x, double y, double z, float size, List<BlockPos> affectedPositions) {
/* 522 */     MCH_ExplosionV2 explosion = new MCH_ExplosionV2(world, x, y, z, size, affectedPositions);
/* 523 */     explosion.func_77278_a();
/* 524 */     explosion.doExplosionB(true, false);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void effectVanillaExplosion(World world, double x, double y, double z, float size, List<BlockPos> affectedPositions) {
/* 530 */     MCH_ExplosionV2 explosion = new MCH_ExplosionV2(world, x, y, z, size, affectedPositions);
/* 531 */     explosion.func_77278_a();
/* 532 */     explosion.doExplosionB(true, true);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void effectExplosionInWater(World world, double x, double y, double z, float size) {
/* 537 */     if (size <= 0.0F)
/*     */       return; 
/* 539 */     int range = (int)(size + 0.5D) / 1;
/* 540 */     int ex = (int)(x + 0.5D);
/* 541 */     int ey = (int)(y + 0.5D);
/* 542 */     int ez = (int)(z + 0.5D);
/*     */     
/* 544 */     for (int i1 = -range; i1 <= range; i1++) {
/*     */       
/* 546 */       if (ey + i1 >= 1)
/*     */       {
/* 548 */         for (int j1 = -range; j1 <= range; j1++) {
/*     */           
/* 550 */           for (int k1 = -range; k1 <= range; k1++) {
/*     */             
/* 552 */             int d = j1 * j1 + i1 * i1 + k1 * k1;
/*     */             
/* 554 */             if (d < range * range)
/*     */             {
/* 556 */               if (W_Block.func_149680_a(W_WorldFunc.getBlock(world, ex + j1, ey + i1, ez + k1), 
/* 557 */                   W_Block.getWater())) {
/*     */                 
/* 559 */                 int n = explosionRNG.nextInt(2);
/*     */                 
/* 561 */                 for (int i = 0; i < n; i++) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */                   
/* 568 */                   MCH_ParticleParam prm = new MCH_ParticleParam(world, "splash", (ex + j1), (ey + i1), (ez + k1), (j1 / range) * (explosionRNG.nextFloat() - 0.2D), 1.0D - Math.sqrt((j1 * j1 + k1 * k1)) / range + explosionRNG.nextFloat() * 0.4D * range * 0.4D, (k1 / range) * (explosionRNG.nextFloat() - 0.2D), (explosionRNG.nextInt(range) * 3 + range));
/*     */                   
/* 570 */                   MCH_ParticlesUtil.spawnParticle(prm);
/*     */                 } 
/*     */               } 
/*     */             }
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\__helper\world\MCH_ExplosionV2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */