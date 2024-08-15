/*     */ package mcheli.particles;
/*     */ 
/*     */ import java.util.function.Supplier;
/*     */ import mcheli.wrapper.W_Particle;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.particle.IParticleFactory;
/*     */ import net.minecraft.client.particle.Particle;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.client.FMLClientHandler;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MCH_ParticlesUtil
/*     */ {
/*     */   public static void spawnParticleExplode(World w, double x, double y, double z, float size, float r, float g, float b, float a, int age) {
/*  50 */     MCH_EntityParticleExplode epe = new MCH_EntityParticleExplode(w, x, y, z, size, age, 0.0D);
/*     */     
/*  52 */     epe.setParticleMaxAge(age);
/*  53 */     epe.func_70538_b(r, g, b);
/*  54 */     epe.func_82338_g(a);
/*  55 */     (FMLClientHandler.instance().getClient()).field_71452_i.func_78873_a((Particle)epe);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void spawnParticleTileCrack(World w, int blockX, int blockY, int blockZ, double x, double y, double z, double mx, double my, double mz) {
/*  62 */     W_Particle.BlockParticleParam name = W_Particle.getParticleTileCrackName(w, blockX, blockY, blockZ);
/*     */     
/*  64 */     if (!name.isEmpty())
/*     */     {
/*     */       
/*  67 */       DEF_spawnParticle(name.name, x, y, z, mx, my, mz, 20.0F, new int[] { name.stateId });
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean spawnParticleTileDust(World w, int blockX, int blockY, int blockZ, double x, double y, double z, double mx, double my, double mz, float scale) {
/*  74 */     boolean ret = false;
/*  75 */     int[][] offset = { { 0, 0, 0 }, { 0, 0, -1 }, { 0, 0, 1 }, { 1, 0, 0 }, { -1, 0, 0 } };
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
/*     */     
/*  93 */     int len = offset.length;
/*     */     
/*  95 */     for (int i = 0; i < len; i++) {
/*     */ 
/*     */       
/*  98 */       W_Particle.BlockParticleParam name = W_Particle.getParticleTileDustName(w, blockX + offset[i][0], blockY + offset[i][1], blockZ + offset[i][2]);
/*     */ 
/*     */       
/* 101 */       if (!name.isEmpty()) {
/*     */ 
/*     */         
/* 104 */         Particle e = DEF_spawnParticle(name.name, x, y, z, mx, my, mz, 20.0F, new int[] { name.stateId });
/*     */         
/* 106 */         if (e instanceof MCH_EntityBlockDustFX) {
/*     */           
/* 108 */           ((MCH_EntityBlockDustFX)e).setScale(scale * 2.0F);
/* 109 */           ret = true;
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/* 114 */     return ret;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Particle DEF_spawnParticle(String s, double x, double y, double z, double mx, double my, double mz, float dist, int... params) {
/* 124 */     Particle e = doSpawnParticle(s, x, y, z, mx, my, mz, params);
/*     */     
/* 126 */     if (e != null);
/*     */ 
/*     */ 
/*     */     
/* 130 */     return e;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Particle doSpawnParticle(String type, double x, double y, double z, double mx, double my, double mz, int... params) {
/* 137 */     Minecraft mc = Minecraft.func_71410_x();
/*     */ 
/*     */ 
/*     */     
/* 141 */     if (mc != null && mc.func_175606_aa() != null && mc.field_71452_i != null) {
/*     */       
/* 143 */       int i = mc.field_71474_y.field_74362_aa;
/*     */       
/* 145 */       if (i == 1 && mc.field_71441_e.field_73012_v.nextInt(3) == 0)
/*     */       {
/* 147 */         i = 2;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 153 */       double d6 = (mc.func_175606_aa()).field_70165_t - x;
/* 154 */       double d7 = (mc.func_175606_aa()).field_70163_u - y;
/* 155 */       double d8 = (mc.func_175606_aa()).field_70161_v - z;
/*     */       
/* 157 */       Particle entityfx = null;
/*     */       
/* 159 */       if (type.equalsIgnoreCase("hugeexplosion")) {
/*     */ 
/*     */         
/* 162 */         entityfx = create(net.minecraft.client.particle.ParticleExplosionHuge.Factory::new, (World)mc.field_71441_e, x, y, z, mx, my, mz, new int[0]);
/* 163 */         mc.field_71452_i.func_78873_a(entityfx);
/*     */       }
/* 165 */       else if (type.equalsIgnoreCase("largeexplode")) {
/*     */ 
/*     */         
/* 168 */         entityfx = create(net.minecraft.client.particle.ParticleExplosionLarge.Factory::new, (World)mc.field_71441_e, x, y, z, mx, my, mz, new int[0]);
/* 169 */         mc.field_71452_i.func_78873_a(entityfx);
/*     */       }
/* 171 */       else if (type.equalsIgnoreCase("fireworksSpark")) {
/*     */ 
/*     */         
/* 174 */         entityfx = create(net.minecraft.client.particle.ParticleFirework.Factory::new, (World)mc.field_71441_e, x, y, z, mx, my, mz, new int[0]);
/* 175 */         mc.field_71452_i.func_78873_a(entityfx);
/*     */       } 
/*     */       
/* 178 */       if (entityfx != null)
/*     */       {
/* 180 */         return entityfx;
/*     */       }
/*     */       
/* 183 */       double d9 = 300.0D;
/*     */       
/* 185 */       if (d6 * d6 + d7 * d7 + d8 * d8 > d9 * d9)
/*     */       {
/* 187 */         return null;
/*     */       }
/*     */       
/* 190 */       if (i > 1)
/*     */       {
/* 192 */         return null;
/*     */       }
/*     */       
/* 195 */       if (type.equalsIgnoreCase("bubble")) {
/*     */ 
/*     */         
/* 198 */         entityfx = create(net.minecraft.client.particle.ParticleBubble.Factory::new, (World)mc.field_71441_e, x, y, z, mx, my, mz, new int[0]);
/*     */       }
/* 200 */       else if (type.equalsIgnoreCase("suspended")) {
/*     */ 
/*     */         
/* 203 */         entityfx = create(net.minecraft.client.particle.ParticleSuspend.Factory::new, (World)mc.field_71441_e, x, y, z, mx, my, mz, new int[0]);
/*     */       }
/* 205 */       else if (type.equalsIgnoreCase("depthsuspend")) {
/*     */ 
/*     */         
/* 208 */         entityfx = create(net.minecraft.client.particle.ParticleSuspendedTown.Factory::new, (World)mc.field_71441_e, x, y, z, mx, my, mz, new int[0]);
/*     */       }
/* 210 */       else if (type.equalsIgnoreCase("townaura")) {
/*     */ 
/*     */         
/* 213 */         entityfx = create(net.minecraft.client.particle.ParticleSuspendedTown.Factory::new, (World)mc.field_71441_e, x, y, z, mx, my, mz, new int[0]);
/*     */       }
/* 215 */       else if (type.equalsIgnoreCase("crit")) {
/*     */ 
/*     */         
/* 218 */         entityfx = create(net.minecraft.client.particle.ParticleCrit.Factory::new, (World)mc.field_71441_e, x, y, z, mx, my, mz, new int[0]);
/*     */       }
/* 220 */       else if (type.equalsIgnoreCase("magicCrit")) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 225 */         entityfx = create(net.minecraft.client.particle.ParticleCrit.MagicFactory::new, (World)mc.field_71441_e, x, y, z, mx, my, mz, new int[0]);
/*     */       }
/* 227 */       else if (type.equalsIgnoreCase("smoke")) {
/*     */ 
/*     */         
/* 230 */         entityfx = create(net.minecraft.client.particle.ParticleSmokeNormal.Factory::new, (World)mc.field_71441_e, x, y, z, mx, my, mz, new int[0]);
/*     */       }
/* 232 */       else if (type.equalsIgnoreCase("mobSpell")) {
/*     */ 
/*     */ 
/*     */         
/* 236 */         entityfx = create(net.minecraft.client.particle.ParticleSpell.MobFactory::new, (World)mc.field_71441_e, x, y, z, mx, my, mz, new int[0]);
/*     */       }
/* 238 */       else if (type.equalsIgnoreCase("mobSpellAmbient")) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 243 */         entityfx = create(net.minecraft.client.particle.ParticleSpell.AmbientMobFactory::new, (World)mc.field_71441_e, x, y, z, mx, my, mz, new int[0]);
/*     */       }
/* 245 */       else if (type.equalsIgnoreCase("spell")) {
/*     */ 
/*     */         
/* 248 */         entityfx = create(net.minecraft.client.particle.ParticleSpell.Factory::new, (World)mc.field_71441_e, x, y, z, mx, my, mz, new int[0]);
/*     */       }
/* 250 */       else if (type.equalsIgnoreCase("instantSpell")) {
/*     */ 
/*     */ 
/*     */         
/* 254 */         entityfx = create(net.minecraft.client.particle.ParticleSpell.InstantFactory::new, (World)mc.field_71441_e, x, y, z, mx, my, mz, new int[0]);
/*     */       }
/* 256 */       else if (type.equalsIgnoreCase("witchMagic")) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 262 */         entityfx = create(net.minecraft.client.particle.ParticleSpell.WitchFactory::new, (World)mc.field_71441_e, x, y, z, mx, my, mz, new int[0]);
/*     */       }
/* 264 */       else if (type.equalsIgnoreCase("note")) {
/*     */ 
/*     */         
/* 267 */         entityfx = create(net.minecraft.client.particle.ParticleNote.Factory::new, (World)mc.field_71441_e, x, y, z, mx, my, mz, new int[0]);
/*     */       }
/* 269 */       else if (type.equalsIgnoreCase("portal")) {
/*     */ 
/*     */         
/* 272 */         entityfx = create(net.minecraft.client.particle.ParticlePortal.Factory::new, (World)mc.field_71441_e, x, y, z, mx, my, mz, new int[0]);
/*     */       }
/* 274 */       else if (type.equalsIgnoreCase("enchantmenttable")) {
/*     */ 
/*     */         
/* 277 */         entityfx = create(net.minecraft.client.particle.ParticleEnchantmentTable.EnchantmentTable::new, (World)mc.field_71441_e, x, y, z, mx, my, mz, new int[0]);
/*     */       }
/* 279 */       else if (type.equalsIgnoreCase("explode")) {
/*     */ 
/*     */         
/* 282 */         entityfx = create(net.minecraft.client.particle.ParticleExplosion.Factory::new, (World)mc.field_71441_e, x, y, z, mx, my, mz, new int[0]);
/*     */       }
/* 284 */       else if (type.equalsIgnoreCase("flame")) {
/*     */ 
/*     */         
/* 287 */         entityfx = create(net.minecraft.client.particle.ParticleFlame.Factory::new, (World)mc.field_71441_e, x, y, z, mx, my, mz, new int[0]);
/*     */       }
/* 289 */       else if (type.equalsIgnoreCase("lava")) {
/*     */ 
/*     */         
/* 292 */         entityfx = create(net.minecraft.client.particle.ParticleLava.Factory::new, (World)mc.field_71441_e, x, y, z, mx, my, mz, new int[0]);
/*     */       }
/* 294 */       else if (type.equalsIgnoreCase("footstep")) {
/*     */ 
/*     */         
/* 297 */         entityfx = create(net.minecraft.client.particle.ParticleFootStep.Factory::new, (World)mc.field_71441_e, x, y, z, mx, my, mz, new int[0]);
/*     */       }
/* 299 */       else if (type.equalsIgnoreCase("splash")) {
/*     */ 
/*     */         
/* 302 */         entityfx = create(net.minecraft.client.particle.ParticleSplash.Factory::new, (World)mc.field_71441_e, x, y, z, mx, my, mz, new int[0]);
/*     */       }
/* 304 */       else if (type.equalsIgnoreCase("wake")) {
/*     */ 
/*     */         
/* 307 */         entityfx = create(net.minecraft.client.particle.ParticleWaterWake.Factory::new, (World)mc.field_71441_e, x, y, z, mx, my, mz, new int[0]);
/*     */       }
/* 309 */       else if (type.equalsIgnoreCase("largesmoke")) {
/*     */ 
/*     */         
/* 312 */         entityfx = create(net.minecraft.client.particle.ParticleSmokeLarge.Factory::new, (World)mc.field_71441_e, x, y, z, mx, my, mz, new int[0]);
/*     */       }
/* 314 */       else if (type.equalsIgnoreCase("cloud")) {
/*     */ 
/*     */         
/* 317 */         entityfx = create(net.minecraft.client.particle.ParticleCloud.Factory::new, (World)mc.field_71441_e, x, y, z, mx, my, mz, new int[0]);
/*     */       }
/* 319 */       else if (type.equalsIgnoreCase("reddust")) {
/*     */ 
/*     */         
/* 322 */         entityfx = create(net.minecraft.client.particle.ParticleRedstone.Factory::new, (World)mc.field_71441_e, x, y, z, mx, my, mz, new int[0]);
/*     */       }
/* 324 */       else if (type.equalsIgnoreCase("snowballpoof")) {
/*     */ 
/*     */         
/* 327 */         entityfx = create(net.minecraft.client.particle.ParticleBreaking.SnowballFactory::new, (World)mc.field_71441_e, x, y, z, mx, my, mz, new int[0]);
/*     */       }
/* 329 */       else if (type.equalsIgnoreCase("dripWater")) {
/*     */ 
/*     */         
/* 332 */         entityfx = create(net.minecraft.client.particle.ParticleDrip.WaterFactory::new, (World)mc.field_71441_e, x, y, z, mx, my, mz, new int[0]);
/*     */       }
/* 334 */       else if (type.equalsIgnoreCase("dripLava")) {
/*     */ 
/*     */         
/* 337 */         entityfx = create(net.minecraft.client.particle.ParticleDrip.LavaFactory::new, (World)mc.field_71441_e, x, y, z, mx, my, mz, new int[0]);
/*     */       }
/* 339 */       else if (type.equalsIgnoreCase("snowshovel")) {
/*     */ 
/*     */         
/* 342 */         entityfx = create(net.minecraft.client.particle.ParticleSnowShovel.Factory::new, (World)mc.field_71441_e, x, y, z, mx, my, mz, new int[0]);
/*     */       }
/* 344 */       else if (type.equalsIgnoreCase("slime")) {
/*     */ 
/*     */         
/* 347 */         entityfx = create(net.minecraft.client.particle.ParticleBreaking.SlimeFactory::new, (World)mc.field_71441_e, x, y, z, mx, my, mz, new int[0]);
/*     */       }
/* 349 */       else if (type.equalsIgnoreCase("heart")) {
/*     */ 
/*     */         
/* 352 */         entityfx = create(net.minecraft.client.particle.ParticleHeart.Factory::new, (World)mc.field_71441_e, x, y, z, mx, my, mz, new int[0]);
/*     */       }
/* 354 */       else if (type.equalsIgnoreCase("angryVillager")) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 359 */         entityfx = create(net.minecraft.client.particle.ParticleHeart.AngryVillagerFactory::new, (World)mc.field_71441_e, x, y, z, mx, my, mz, new int[0]);
/*     */       }
/* 361 */       else if (type.equalsIgnoreCase("happyVillager")) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 366 */         entityfx = create(net.minecraft.client.particle.ParticleSuspendedTown.HappyVillagerFactory::new, (World)mc.field_71441_e, x, y, z, mx, my, mz, new int[0]);
/*     */       
/*     */       }
/* 369 */       else if (type.startsWith("iconcrack")) {
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
/* 383 */         entityfx = create(net.minecraft.client.particle.ParticleBreaking.Factory::new, (World)mc.field_71441_e, x, y, z, mx, my, mz, params);
/*     */       
/*     */       }
/* 386 */       else if (type.startsWith("blockcrack")) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 392 */         entityfx = create(net.minecraft.client.particle.ParticleDigging.Factory::new, (World)mc.field_71441_e, x, y, z, mx, my, mz, params);
/*     */       
/*     */       }
/* 395 */       else if (type.startsWith("blockdust")) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 401 */         entityfx = create(Factory::new, (World)mc.field_71441_e, x, y, z, mx, my, mz, params);
/*     */       } 
/*     */       
/* 404 */       if (entityfx != null)
/*     */       {
/* 406 */         mc.field_71452_i.func_78873_a(entityfx);
/*     */       }
/*     */       
/* 409 */       return entityfx;
/*     */     } 
/*     */     
/* 412 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void spawnParticle(MCH_ParticleParam p) {
/* 417 */     if (p.world.field_72995_K) {
/*     */       
/* 419 */       MCH_EntityParticleBase entityFX = null;
/*     */       
/* 421 */       if (p.name.equalsIgnoreCase("Splash")) {
/*     */         
/* 423 */         entityFX = new MCH_EntityParticleSplash(p.world, p.posX, p.posY, p.posZ, p.motionX, p.motionY, p.motionZ);
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 428 */         entityFX = new MCH_EntityParticleSmoke(p.world, p.posX, p.posY, p.posZ, p.motionX, p.motionY, p.motionZ);
/*     */       } 
/*     */       
/* 431 */       entityFX.func_70538_b(p.r, p.g, p.b);
/* 432 */       entityFX.func_82338_g(p.a);
/*     */       
/* 434 */       if (p.age > 0)
/*     */       {
/* 436 */         entityFX.setParticleMaxAge(p.age);
/*     */       }
/*     */       
/* 439 */       entityFX.moutionYUpAge = p.motionYUpAge;
/* 440 */       entityFX.gravity = p.gravity;
/* 441 */       entityFX.isEffectedWind = p.isEffectWind;
/* 442 */       entityFX.diffusible = p.diffusible;
/* 443 */       entityFX.toWhite = p.toWhite;
/*     */       
/* 445 */       if (p.diffusible) {
/*     */         
/* 447 */         entityFX.setParticleScale(p.size * 0.2F);
/* 448 */         entityFX.particleMaxScale = p.size * 2.0F;
/*     */       }
/*     */       else {
/*     */         
/* 452 */         entityFX.setParticleScale(p.size);
/*     */       } 
/*     */       
/* 455 */       (FMLClientHandler.instance().getClient()).field_71452_i.func_78873_a((Particle)entityFX);
/*     */     } 
/*     */   }
/*     */   
/* 459 */   public static MCH_EntityParticleMarkPoint markPoint = null;
/*     */ 
/*     */   
/*     */   public static void spawnMarkPoint(EntityPlayer player, double x, double y, double z) {
/* 463 */     clearMarkPoint();
/* 464 */     markPoint = new MCH_EntityParticleMarkPoint(player.field_70170_p, x, y, z, player.func_96124_cp());
/* 465 */     (FMLClientHandler.instance().getClient()).field_71452_i.func_78873_a((Particle)markPoint);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void clearMarkPoint() {
/* 470 */     if (markPoint != null) {
/*     */ 
/*     */       
/* 473 */       markPoint.func_187112_i();
/* 474 */       markPoint = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static Particle create(Supplier<IParticleFactory> factoryFunc, World world, double x, double y, double z, double mx, double my, double mz, int... param) {
/* 481 */     return ((IParticleFactory)factoryFunc.get()).func_178902_a(-1, world, x, y, z, mx, my, mz, param);
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\particles\MCH_ParticlesUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */