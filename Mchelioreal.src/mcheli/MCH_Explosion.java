/*     */ package mcheli;
/*     */ 
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import mcheli.__helper.world.MCH_ExplosionV2;
/*     */ import mcheli.wrapper.W_Entity;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.World;
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
/*     */ public class MCH_Explosion
/*     */ {
/*     */   public static ExplosionResult newExplosion(World w, @Nullable Entity entityExploded, @Nullable Entity player, double x, double y, double z, float size, float sizeBlock, boolean playSound, boolean isSmoking, boolean isFlaming, boolean isDestroyBlock, int countSetFireEntity) {
/* 355 */     return newExplosion(w, entityExploded, player, x, y, z, size, sizeBlock, playSound, isSmoking, isFlaming, isDestroyBlock, countSetFireEntity, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ExplosionResult newExplosion(World w, @Nullable Entity entityExploded, @Nullable Entity player, double x, double y, double z, float size, float sizeBlock, boolean playSound, boolean isSmoking, boolean isFlaming, boolean isDestroyBlock, int countSetFireEntity, MCH_DamageFactor df) {
/* 363 */     if (w.field_72995_K)
/*     */     {
/* 365 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 369 */     MCH_ExplosionV2 exp = new MCH_ExplosionV2(w, entityExploded, player, x, y, z, size, isFlaming, w.func_82736_K().func_82766_b("mobGriefing"));
/* 370 */     exp.isDestroyBlock = isDestroyBlock;
/* 371 */     exp.explosionSizeBlock = sizeBlock;
/* 372 */     exp.countSetFireEntity = countSetFireEntity;
/* 373 */     exp.isPlaySound = playSound;
/* 374 */     exp.isInWater = false;
/*     */     
/* 376 */     exp.damageFactor = df;
/*     */     
/* 378 */     exp.func_77278_a();
/*     */     
/* 380 */     exp.func_77279_a(false);
/*     */     
/* 382 */     MCH_PacketEffectExplosion.ExplosionParam param = MCH_PacketEffectExplosion.create();
/*     */     
/* 384 */     param.exploderID = W_Entity.getEntityId(entityExploded);
/* 385 */     param.posX = x;
/* 386 */     param.posY = y;
/* 387 */     param.posZ = z;
/* 388 */     param.size = size;
/* 389 */     param.inWater = false;
/* 390 */     param.setAffectedPositions(exp.func_180343_e());
/*     */     
/* 392 */     MCH_PacketEffectExplosion.send(param);
/*     */     
/* 394 */     return exp.getResult();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static ExplosionResult newExplosionInWater(World w, @Nullable Entity entityExploded, @Nullable Entity player, double x, double y, double z, float size, float sizeBlock, boolean playSound, boolean isSmoking, boolean isFlaming, boolean isDestroyBlock, int countSetFireEntity, MCH_DamageFactor df) {
/* 402 */     if (w.field_72995_K)
/*     */     {
/* 404 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 409 */     MCH_ExplosionV2 exp = new MCH_ExplosionV2(w, entityExploded, player, x, y, z, size, isFlaming, w.func_82736_K().func_82766_b("mobGriefing"));
/* 410 */     exp.isDestroyBlock = isDestroyBlock;
/* 411 */     exp.explosionSizeBlock = sizeBlock;
/* 412 */     exp.countSetFireEntity = countSetFireEntity;
/* 413 */     exp.isPlaySound = playSound;
/* 414 */     exp.isInWater = true;
/*     */     
/* 416 */     exp.damageFactor = df;
/*     */     
/* 418 */     exp.func_77278_a();
/*     */     
/* 420 */     exp.func_77279_a(false);
/*     */     
/* 422 */     MCH_PacketEffectExplosion.ExplosionParam param = MCH_PacketEffectExplosion.create();
/*     */     
/* 424 */     param.exploderID = W_Entity.getEntityId(entityExploded);
/* 425 */     param.posX = x;
/* 426 */     param.posY = y;
/* 427 */     param.posZ = z;
/* 428 */     param.size = size;
/* 429 */     param.inWater = true;
/* 430 */     param.setAffectedPositions(exp.func_180343_e());
/*     */     
/* 432 */     MCH_PacketEffectExplosion.send(param);
/*     */     
/* 434 */     return exp.getResult();
/*     */   }
/*     */ 
/*     */   
/*     */   public static void playExplosionSound(World w, double x, double y, double z) {
/* 439 */     MCH_ExplosionV2.playExplosionSound(w, x, y, z);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void effectExplosion(World world, Entity exploder, double explosionX, double explosionY, double explosionZ, float explosionSize, boolean isSmoking, List<BlockPos> affectedPositions) {
/* 445 */     MCH_ExplosionV2.effectMODExplosion(world, explosionX, explosionY, explosionZ, explosionSize, affectedPositions);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void DEF_effectExplosion(World world, Entity exploder, double explosionX, double explosionY, double explosionZ, float explosionSize, boolean isSmoking, List<BlockPos> affectedPositions) {
/* 451 */     MCH_ExplosionV2.effectVanillaExplosion(world, explosionX, explosionY, explosionZ, explosionSize, affectedPositions);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void effectExplosionInWater(World world, Entity exploder, double explosionX, double explosionY, double explosionZ, float explosionSize, boolean isSmoking) {
/* 458 */     MCH_ExplosionV2.effectExplosionInWater(world, explosionX, explosionY, explosionZ, explosionSize);
/*     */   }
/*     */   
/*     */   public static class ExplosionResult {
/*     */     public boolean hitEntity = false;
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\MCH_Explosion.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */