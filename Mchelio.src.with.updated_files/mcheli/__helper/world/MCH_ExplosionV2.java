package mcheli.__helper.world;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import mcheli.MCH_Config;
import mcheli.MCH_DamageFactor;
import mcheli.MCH_Explosion;
import mcheli.MCH_Lib;
import mcheli.flare.MCH_EntityFlare;
import mcheli.particles.MCH_ParticleParam;
import mcheli.particles.MCH_ParticlesUtil;
import mcheli.weapon.MCH_EntityBaseBullet;
import mcheli.wrapper.W_AxisAlignedBB;
import mcheli.wrapper.W_Block;
import mcheli.wrapper.W_Blocks;
import mcheli.wrapper.W_ChunkPosition;
import mcheli.wrapper.W_Entity;
import mcheli.wrapper.W_WorldFunc;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentProtection;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MCH_ExplosionV2 extends Explosion {
  private static Random explosionRNG = new Random();
  
  public final int field_77289_h = 16;
  
  public World worldObj;
  
  public final Entity exploder;
  
  public final double explosionX;
  
  public final double explosionY;
  
  public final double explosionZ;
  
  public final float explosionSize;
  
  public final boolean isFlaming;
  
  public final boolean isSmoking;
  
  public boolean isDestroyBlock;
  
  public int countSetFireEntity;
  
  public boolean isPlaySound;
  
  public boolean isInWater;
  
  private MCH_Explosion.ExplosionResult result;
  
  public EntityPlayer explodedPlayer;
  
  public float explosionSizeBlock;
  
  public MCH_DamageFactor damageFactor = null;
  
  @SideOnly(Side.CLIENT)
  public MCH_ExplosionV2(World worldIn, double x, double y, double z, float size, List<BlockPos> affectedPositions) {
    this(worldIn, (Entity)null, (Entity)null, x, y, z, size, false, true);
    getAffectedBlockPositions().addAll(affectedPositions);
    this.isPlaySound = false;
  }
  
  public MCH_ExplosionV2(World worldIn, @Nullable Entity exploderIn, @Nullable Entity player, double x, double y, double z, float size, boolean flaming, boolean damagesTerrain) {
    super(worldIn, exploderIn, x, y, z, size, flaming, damagesTerrain);
    this.worldObj = worldIn;
    this.exploder = exploderIn;
    this.explodedPlayer = (player instanceof EntityPlayer) ? (EntityPlayer)player : null;
    this.explosionX = x;
    this.explosionY = y;
    this.explosionZ = z;
    this.explosionSize = size;
    this.isFlaming = flaming;
    this.isSmoking = damagesTerrain;
    this.isDestroyBlock = false;
    this.explosionSizeBlock = size;
    this.countSetFireEntity = 0;
    this.isPlaySound = true;
    this.isInWater = false;
    this.result = new MCH_Explosion.ExplosionResult();
  }
  
  public void doExplosionA() {
    HashSet<BlockPos> hashset = new HashSet<>();
    for (int i = 0; i < 16; i++) {
      for (int n = 0; n < 16; n++) {
        for (int i1 = 0; i1 < 16; i1++) {
          if (i != 0)
            if (i != 15 && n != 0)
              if (n != 15 && i1 != 0)
                if (i1 != 15)
                  continue;    
          double d3 = (i / 15.0F * 2.0F - 1.0F);
          double d4 = (n / 15.0F * 2.0F - 1.0F);
          double d5 = (i1 / 15.0F * 2.0F - 1.0F);
          double d6 = Math.sqrt(d3 * d3 + d4 * d4 + d5 * d5);
          d3 /= d6;
          d4 /= d6;
          d5 /= d6;
          float f1 = this.explosionSizeBlock * (0.7F + this.worldObj.rand.nextFloat() * 0.6F);
          double d0 = this.explosionX;
          double d1 = this.explosionY;
          double d2 = this.explosionZ;
          for (; f1 > 0.0F; f1 -= 0.22500001F) {
            int l = MathHelper.floor(d0);
            int i3 = MathHelper.floor(d1);
            int j1 = MathHelper.floor(d2);
            int k1 = W_WorldFunc.getBlockId(this.worldObj, l, i3, j1);
            BlockPos blockpos = new BlockPos(l, i3, j1);
            IBlockState iblockstate = this.worldObj.getBlockState(blockpos);
            Block block = iblockstate.getBlock();
            if (k1 > 0) {
              float f3;
              if (this.exploder != null) {
                f3 = W_Entity.getBlockExplosionResistance(this.exploder, this, this.worldObj, l, i3, j1, block);
              } else {
                f3 = block.getExplosionResistance(this.worldObj, blockpos, this.exploder, this);
              } 
              if (this.isInWater)
                f3 *= this.worldObj.rand.nextFloat() * 0.2F + 0.2F; 
              f1 -= (f3 + 0.3F) * 0.3F;
            } 
            if (f1 > 0.0F && (this.exploder == null || 
              W_Entity.shouldExplodeBlock(this.exploder, this, this.worldObj, l, i3, j1, k1, f1)))
              hashset.add(blockpos); 
            d0 += d3 * 0.30000001192092896D;
            d1 += d4 * 0.30000001192092896D;
            d2 += d5 * 0.30000001192092896D;
          } 
          continue;
        } 
      } 
    } 
    float f = this.explosionSize * 2.0F;
    getAffectedBlockPositions().addAll(hashset);
    int m = MathHelper.floor(this.explosionX - f - 1.0D);
    int j = MathHelper.floor(this.explosionX + f + 1.0D);
    int k = MathHelper.floor(this.explosionY - f - 1.0D);
    int l1 = MathHelper.floor(this.explosionY + f + 1.0D);
    int i2 = MathHelper.floor(this.explosionZ - f - 1.0D);
    int j2 = MathHelper.floor(this.explosionZ + f + 1.0D);
    List<Entity> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this.exploder, 
        W_AxisAlignedBB.getAABB(m, k, i2, j, l1, j2));
    Vec3d vec3 = W_WorldFunc.getWorldVec3(this.worldObj, this.explosionX, this.explosionY, this.explosionZ);
    for (int k2 = 0; k2 < list.size(); k2++) {
      Entity entity = list.get(k2);
      double d7 = entity.getDistance(this.explosionX, this.explosionY, this.explosionZ) / f;
      if (d7 <= 1.0D) {
        double d0 = entity.posX - this.explosionX;
        double d1 = entity.posY + entity.getEyeHeight() - this.explosionY;
        double d2 = entity.posZ - this.explosionZ;
        double d8 = MathHelper.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
        if (d8 != 0.0D) {
          d0 /= d8;
          d1 /= d8;
          d2 /= d8;
          double d9 = getBlockDensity(vec3, entity.getEntityBoundingBox());
          double d10 = (1.0D - d7) * d9;
          float damage = (int)((d10 * d10 + d10) / 2.0D * 8.0D * f + 1.0D);
          if (damage > 0.0F)
            if (!(entity instanceof net.minecraft.entity.item.EntityItem) && !(entity instanceof net.minecraft.entity.item.EntityExpBottle) && !(entity instanceof net.minecraft.entity.item.EntityXPOrb) && 
              !W_Entity.isEntityFallingBlock(entity))
              if (entity instanceof MCH_EntityBaseBullet && this.explodedPlayer instanceof EntityPlayer) {
                if (!W_Entity.isEqual(((MCH_EntityBaseBullet)entity).shootingEntity, (Entity)this.explodedPlayer)) {
                  this.result.hitEntity = true;
                  MCH_Lib.DbgLog(this.worldObj, "MCH_Explosion.doExplosionA:Damage=%.1f:HitEntityBullet=" + entity
                      .getClass(), new Object[] { Float.valueOf(damage) });
                } 
              } else {
                MCH_Lib.DbgLog(this.worldObj, "MCH_Explosion.doExplosionA:Damage=%.1f:HitEntity=" + entity
                    .getClass(), new Object[] { Float.valueOf(damage) });
                this.result.hitEntity = true;
              }   
          MCH_Lib.applyEntityHurtResistantTimeConfig(entity);
          DamageSource ds = DamageSource.causeExplosionDamage(this);
          damage = MCH_Config.applyDamageVsEntity(entity, ds, damage);
          damage *= (this.damageFactor != null) ? this.damageFactor.getDamageFactor(entity) : 1.0F;
          W_Entity.attackEntityFrom(entity, ds, damage);
          double d11 = d10;
          if (entity instanceof EntityLivingBase)
            d11 = EnchantmentProtection.getBlastDamageReduction((EntityLivingBase)entity, d10); 
          if (!(entity instanceof MCH_EntityBaseBullet)) {
            entity.motionX += d0 * d11 * 0.4D;
            entity.motionY += d1 * d11 * 0.1D;
            entity.motionZ += d2 * d11 * 0.4D;
          } 
          if (entity instanceof EntityPlayer)
            getPlayerKnockbackMap().put((EntityPlayer)entity, 
                W_WorldFunc.getWorldVec3(this.worldObj, d0 * d10, d1 * d10, d2 * d10)); 
          if (damage > 0.0F && this.countSetFireEntity > 0) {
            double fireFactor = 1.0D - d8 / f;
            if (fireFactor > 0.0D)
              entity.setFire((int)(fireFactor * this.countSetFireEntity)); 
          } 
        } 
      } 
    } 
  }
  
  private double getBlockDensity(Vec3d vec3, AxisAlignedBB bb) {
    double d0 = 1.0D / ((bb.maxX - bb.minX) * 2.0D + 1.0D);
    double d1 = 1.0D / ((bb.maxY - bb.minY) * 2.0D + 1.0D);
    double d2 = 1.0D / ((bb.maxZ - bb.minZ) * 2.0D + 1.0D);
    if (d0 >= 0.0D && d1 >= 0.0D && d2 >= 0.0D) {
      int i = 0;
      int j = 0;
      float f;
      for (f = 0.0F; f <= 1.0F; f = (float)(f + d0)) {
        float f1;
        for (f1 = 0.0F; f1 <= 1.0F; f1 = (float)(f1 + d1)) {
          float f2;
          for (f2 = 0.0F; f2 <= 1.0F; f2 = (float)(f2 + d2)) {
            double d3 = bb.minX + (bb.maxX - bb.minX) * f;
            double d4 = bb.minY + (bb.maxY - bb.minY) * f1;
            double d5 = bb.minZ + (bb.maxZ - bb.minZ) * f2;
            if (this.worldObj.rayTraceBlocks(new Vec3d(d3, d4, d5), vec3, false, true, false) == null)
              i++; 
            j++;
          } 
        } 
      } 
      return (i / j);
    } 
    return 0.0D;
  }
  
  public void doExplosionB(boolean spawnParticles) {
    doExplosionB(spawnParticles, false);
  }
  
  private void doExplosionB(boolean spawnParticles, boolean vanillaMode) {
    if (this.isPlaySound)
      this.worldObj.playSound(null, this.explosionX, this.explosionY, this.explosionZ, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, 4.0F, (1.0F + (this.worldObj.rand
          .nextFloat() - this.worldObj.rand.nextFloat()) * 0.2F) * 0.7F); 
    if (this.isSmoking) {
      Iterator<BlockPos> iterator = getAffectedBlockPositions().iterator();
      int cnt = 0;
      int flareCnt = (int)this.explosionSize;
      while (iterator.hasNext()) {
        BlockPos chunkposition = iterator.next();
        int i = W_ChunkPosition.getChunkPosX(chunkposition);
        int j = W_ChunkPosition.getChunkPosY(chunkposition);
        int k = W_ChunkPosition.getChunkPosZ(chunkposition);
        int l = W_WorldFunc.getBlockId(this.worldObj, i, j, k);
        cnt++;
        if (spawnParticles)
          if (vanillaMode) {
            spawnVanillaExlosionEffect(i, j, k);
          } else if (spawnExlosionEffect(cnt, i, j, k, (flareCnt > 0))) {
            flareCnt--;
          }  
        if (l > 0 && this.isDestroyBlock && this.explosionSizeBlock > 0.0F && MCH_Config.Explosion_DestroyBlock.prmBool) {
          Block block = W_Block.getBlockById(l);
          if (block.canDropFromExplosion(this))
            block.dropBlockAsItemWithChance(this.worldObj, chunkposition, this.worldObj
                .getBlockState(chunkposition), 1.0F / this.explosionSizeBlock, 0); 
          block.onBlockExploded(this.worldObj, chunkposition, this);
        } 
      } 
    } 
    if (this.isFlaming && MCH_Config.Explosion_FlamingBlock.prmBool) {
      Iterator<BlockPos> iterator = getAffectedBlockPositions().iterator();
      while (iterator.hasNext()) {
        BlockPos chunkposition = iterator.next();
        int i = W_ChunkPosition.getChunkPosX(chunkposition);
        int j = W_ChunkPosition.getChunkPosY(chunkposition);
        int k = W_ChunkPosition.getChunkPosZ(chunkposition);
        int l = W_WorldFunc.getBlockId(this.worldObj, i, j, k);
        IBlockState iblockstate = this.worldObj.getBlockState(chunkposition.down());
        Block b = iblockstate.getBlock();
        if (l == 0 && b != null && iblockstate.isOpaqueCube() && explosionRNG.nextInt(3) == 0)
          W_WorldFunc.setBlock(this.worldObj, i, j, k, (Block)W_Blocks.FIRE); 
      } 
    } 
  }
  
  private boolean spawnExlosionEffect(int cnt, int i, int j, int k, boolean spawnFlare) {
    boolean spawnedFlare = false;
    double d0 = (i + this.worldObj.rand.nextFloat());
    double d1 = (j + this.worldObj.rand.nextFloat());
    double d2 = (k + this.worldObj.rand.nextFloat());
    double mx = d0 - this.explosionX;
    double my = d1 - this.explosionY;
    double mz = d2 - this.explosionZ;
    double d6 = MathHelper.sqrt(mx * mx + my * my + mz * mz);
    mx /= d6;
    my /= d6;
    mz /= d6;
    double d7 = 0.5D / (d6 / this.explosionSize + 0.1D);
    d7 *= (this.worldObj.rand.nextFloat() * this.worldObj.rand.nextFloat() + 0.3F);
    mx *= d7 * 0.5D;
    my *= d7 * 0.5D;
    mz *= d7 * 0.5D;
    double px = (d0 + this.explosionX * 1.0D) / 2.0D;
    double py = (d1 + this.explosionY * 1.0D) / 2.0D;
    double pz = (d2 + this.explosionZ * 1.0D) / 2.0D;
    double r = Math.PI * this.worldObj.rand.nextInt(360) / 180.0D;
    if (this.explosionSize >= 4.0F && spawnFlare) {
      double a = Math.min((this.explosionSize / 12.0F), 0.6D) * (0.5F + this.worldObj.rand.nextFloat() * 0.5F);
      this.worldObj.spawnEntityInWorld((Entity)new MCH_EntityFlare(this.worldObj, px, py + 2.0D, pz, Math.sin(r) * a, (1.0D + my / 5.0D) * a, 
            Math.cos(r) * a, 2.0F, 0));
      spawnedFlare = true;
    } 
    if (cnt % 4 == 0) {
      float bdf = Math.min(this.explosionSize / 3.0F, 2.0F) * (0.5F + this.worldObj.rand.nextFloat() * 0.5F);
      MCH_ParticlesUtil.spawnParticleTileDust(this.worldObj, (int)(px + 0.5D), (int)(py - 0.5D), (int)(pz + 0.5D), px, py + 1.0D, pz, 
          Math.sin(r) * bdf, 0.5D + my / 5.0D * bdf, Math.cos(r) * bdf, 
          Math.min(this.explosionSize / 2.0F, 3.0F) * (0.5F + this.worldObj.rand.nextFloat() * 0.5F));
    } 
    int es = (int)((this.explosionSize >= 4.0F) ? this.explosionSize : 4.0F);
    if (this.explosionSize <= 1.0F || cnt % es == 0) {
      if (this.worldObj.rand.nextBoolean()) {
        my *= 3.0D;
        mx *= 0.1D;
        mz *= 0.1D;
      } else {
        my *= 0.2D;
        mx *= 3.0D;
        mz *= 3.0D;
      } 
      MCH_ParticleParam prm = new MCH_ParticleParam(this.worldObj, "explode", px, py, pz, mx, my, mz, (this.explosionSize < 8.0F) ? (this.explosionSize * 2.0F) : ((this.explosionSize < 2.0F) ? 2.0F : 16.0F));
      prm.r = prm.g = prm.b = 0.3F + this.worldObj.rand.nextFloat() * 0.4F;
      prm.r += 0.1F;
      prm.g += 0.05F;
      prm.b += 0.0F;
      prm.age = 10 + this.worldObj.rand.nextInt(30);
      MCH_ParticleParam tmp1231_1229 = prm;
      tmp1231_1229.age = (int)(tmp1231_1229.age * ((this.explosionSize < 6.0F) ? this.explosionSize : 6.0F));
      prm.age = prm.age * 2 / 3;
      prm.diffusible = true;
      MCH_ParticlesUtil.spawnParticle(prm);
    } 
    return spawnedFlare;
  }
  
  private void spawnVanillaExlosionEffect(int i, int j, int k) {
    double d0 = (i + this.worldObj.rand.nextFloat());
    double d1 = (j + this.worldObj.rand.nextFloat());
    double d2 = (k + this.worldObj.rand.nextFloat());
    double d3 = d0 - this.explosionX;
    double d4 = d1 - this.explosionY;
    double d5 = d2 - this.explosionZ;
    double d6 = MathHelper.sqrt(d3 * d3 + d4 * d4 + d5 * d5);
    d3 /= d6;
    d4 /= d6;
    d5 /= d6;
    double d7 = 0.5D / (d6 / this.explosionSize + 0.1D);
    d7 *= (this.worldObj.rand.nextFloat() * this.worldObj.rand.nextFloat() + 0.3F);
    d3 *= d7;
    d4 *= d7;
    d5 *= d7;
    MCH_ParticlesUtil.DEF_spawnParticle("explode", (d0 + this.explosionX) / 2.0D, (d1 + this.explosionY) / 2.0D, (d2 + this.explosionZ) / 2.0D, d3, d4, d5, 10.0F, new int[0]);
    MCH_ParticlesUtil.DEF_spawnParticle("smoke", d0, d1, d2, d3, d4, d5, 10.0F, new int[0]);
  }
  
  public EntityLivingBase getExplosivePlacedBy() {
    if (this.explodedPlayer != null && this.explodedPlayer instanceof EntityPlayer)
      return (EntityLivingBase)this.explodedPlayer; 
    return super.getExplosivePlacedBy();
  }
  
  public MCH_Explosion.ExplosionResult getResult() {
    return this.result;
  }
  
  public static void playExplosionSound(World world, double x, double y, double z) {
    world.playSound(x, y, z, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, 4.0F, (1.0F + (world.rand
        .nextFloat() - world.rand.nextFloat()) * 0.2F) * 0.7F, false);
  }
  
  public static void effectMODExplosion(World world, double x, double y, double z, float size, List<BlockPos> affectedPositions) {
    MCH_ExplosionV2 explosion = new MCH_ExplosionV2(world, x, y, z, size, affectedPositions);
    explosion.doExplosionA();
    explosion.doExplosionB(true, false);
  }
  
  public static void effectVanillaExplosion(World world, double x, double y, double z, float size, List<BlockPos> affectedPositions) {
    MCH_ExplosionV2 explosion = new MCH_ExplosionV2(world, x, y, z, size, affectedPositions);
    explosion.doExplosionA();
    explosion.doExplosionB(true, true);
  }
  
  public static void effectExplosionInWater(World world, double x, double y, double z, float size) {
    if (size <= 0.0F)
      return; 
    int range = (int)(size + 0.5D) / 1;
    int ex = (int)(x + 0.5D);
    int ey = (int)(y + 0.5D);
    int ez = (int)(z + 0.5D);
    for (int i1 = -range; i1 <= range; i1++) {
      if (ey + i1 >= 1)
        for (int j1 = -range; j1 <= range; j1++) {
          for (int k1 = -range; k1 <= range; k1++) {
            int d = j1 * j1 + i1 * i1 + k1 * k1;
            if (d < range * range)
              if (W_Block.isEqualTo(W_WorldFunc.getBlock(world, ex + j1, ey + i1, ez + k1), 
                  W_Block.getWater())) {
                int n = explosionRNG.nextInt(2);
                for (int i = 0; i < n; i++) {
                  MCH_ParticleParam prm = new MCH_ParticleParam(world, "splash", (ex + j1), (ey + i1), (ez + k1), (j1 / range) * (explosionRNG.nextFloat() - 0.2D), 1.0D - Math.sqrt((j1 * j1 + k1 * k1)) / range + explosionRNG.nextFloat() * 0.4D * range * 0.4D, (k1 / range) * (explosionRNG.nextFloat() - 0.2D), (explosionRNG.nextInt(range) * 3 + range));
                  MCH_ParticlesUtil.spawnParticle(prm);
                } 
              }  
          } 
        }  
    } 
  }
}
