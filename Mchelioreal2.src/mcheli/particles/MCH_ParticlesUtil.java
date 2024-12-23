package mcheli.particles;

import java.util.function.Supplier;
import mcheli.wrapper.W_Particle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.FMLClientHandler;

public class MCH_ParticlesUtil {
  public static void spawnParticleExplode(World w, double x, double y, double z, float size, float r, float g, float b, float a, int age) {
    MCH_EntityParticleExplode epe = new MCH_EntityParticleExplode(w, x, y, z, size, age, 0.0D);
    epe.setParticleMaxAge(age);
    epe.func_70538_b(r, g, b);
    epe.func_82338_g(a);
    (FMLClientHandler.instance().getClient()).field_71452_i.func_78873_a((Particle)epe);
  }
  
  public static void spawnParticleTileCrack(World w, int blockX, int blockY, int blockZ, double x, double y, double z, double mx, double my, double mz) {
    W_Particle.BlockParticleParam name = W_Particle.getParticleTileCrackName(w, blockX, blockY, blockZ);
    if (!name.isEmpty())
      DEF_spawnParticle(name.name, x, y, z, mx, my, mz, 20.0F, new int[] { name.stateId }); 
  }
  
  public static boolean spawnParticleTileDust(World w, int blockX, int blockY, int blockZ, double x, double y, double z, double mx, double my, double mz, float scale) {
    boolean ret = false;
    int[][] offset = { { 0, 0, 0 }, { 0, 0, -1 }, { 0, 0, 1 }, { 1, 0, 0 }, { -1, 0, 0 } };
    int len = offset.length;
    for (int i = 0; i < len; i++) {
      W_Particle.BlockParticleParam name = W_Particle.getParticleTileDustName(w, blockX + offset[i][0], blockY + offset[i][1], blockZ + offset[i][2]);
      if (!name.isEmpty()) {
        Particle e = DEF_spawnParticle(name.name, x, y, z, mx, my, mz, 20.0F, new int[] { name.stateId });
        if (e instanceof MCH_EntityBlockDustFX) {
          ((MCH_EntityBlockDustFX)e).setScale(scale * 2.0F);
          ret = true;
          break;
        } 
      } 
    } 
    return ret;
  }
  
  public static Particle DEF_spawnParticle(String s, double x, double y, double z, double mx, double my, double mz, float dist, int... params) {
    Particle e = doSpawnParticle(s, x, y, z, mx, my, mz, params);
    if (e != null);
    return e;
  }
  
  public static Particle doSpawnParticle(String type, double x, double y, double z, double mx, double my, double mz, int... params) {
    Minecraft mc = Minecraft.func_71410_x();
    if (mc != null && mc.func_175606_aa() != null && mc.field_71452_i != null) {
      int i = mc.field_71474_y.field_74362_aa;
      if (i == 1 && mc.field_71441_e.field_73012_v.nextInt(3) == 0)
        i = 2; 
      double d6 = (mc.func_175606_aa()).field_70165_t - x;
      double d7 = (mc.func_175606_aa()).field_70163_u - y;
      double d8 = (mc.func_175606_aa()).field_70161_v - z;
      Particle entityfx = null;
      if (type.equalsIgnoreCase("hugeexplosion")) {
        entityfx = create(net.minecraft.client.particle.ParticleExplosionHuge.Factory::new, (World)mc.field_71441_e, x, y, z, mx, my, mz, new int[0]);
        mc.field_71452_i.func_78873_a(entityfx);
      } else if (type.equalsIgnoreCase("largeexplode")) {
        entityfx = create(net.minecraft.client.particle.ParticleExplosionLarge.Factory::new, (World)mc.field_71441_e, x, y, z, mx, my, mz, new int[0]);
        mc.field_71452_i.func_78873_a(entityfx);
      } else if (type.equalsIgnoreCase("fireworksSpark")) {
        entityfx = create(net.minecraft.client.particle.ParticleFirework.Factory::new, (World)mc.field_71441_e, x, y, z, mx, my, mz, new int[0]);
        mc.field_71452_i.func_78873_a(entityfx);
      } 
      if (entityfx != null)
        return entityfx; 
      double d9 = 300.0D;
      if (d6 * d6 + d7 * d7 + d8 * d8 > d9 * d9)
        return null; 
      if (i > 1)
        return null; 
      if (type.equalsIgnoreCase("bubble")) {
        entityfx = create(net.minecraft.client.particle.ParticleBubble.Factory::new, (World)mc.field_71441_e, x, y, z, mx, my, mz, new int[0]);
      } else if (type.equalsIgnoreCase("suspended")) {
        entityfx = create(net.minecraft.client.particle.ParticleSuspend.Factory::new, (World)mc.field_71441_e, x, y, z, mx, my, mz, new int[0]);
      } else if (type.equalsIgnoreCase("depthsuspend")) {
        entityfx = create(net.minecraft.client.particle.ParticleSuspendedTown.Factory::new, (World)mc.field_71441_e, x, y, z, mx, my, mz, new int[0]);
      } else if (type.equalsIgnoreCase("townaura")) {
        entityfx = create(net.minecraft.client.particle.ParticleSuspendedTown.Factory::new, (World)mc.field_71441_e, x, y, z, mx, my, mz, new int[0]);
      } else if (type.equalsIgnoreCase("crit")) {
        entityfx = create(net.minecraft.client.particle.ParticleCrit.Factory::new, (World)mc.field_71441_e, x, y, z, mx, my, mz, new int[0]);
      } else if (type.equalsIgnoreCase("magicCrit")) {
        entityfx = create(net.minecraft.client.particle.ParticleCrit.MagicFactory::new, (World)mc.field_71441_e, x, y, z, mx, my, mz, new int[0]);
      } else if (type.equalsIgnoreCase("smoke")) {
        entityfx = create(net.minecraft.client.particle.ParticleSmokeNormal.Factory::new, (World)mc.field_71441_e, x, y, z, mx, my, mz, new int[0]);
      } else if (type.equalsIgnoreCase("mobSpell")) {
        entityfx = create(net.minecraft.client.particle.ParticleSpell.MobFactory::new, (World)mc.field_71441_e, x, y, z, mx, my, mz, new int[0]);
      } else if (type.equalsIgnoreCase("mobSpellAmbient")) {
        entityfx = create(net.minecraft.client.particle.ParticleSpell.AmbientMobFactory::new, (World)mc.field_71441_e, x, y, z, mx, my, mz, new int[0]);
      } else if (type.equalsIgnoreCase("spell")) {
        entityfx = create(net.minecraft.client.particle.ParticleSpell.Factory::new, (World)mc.field_71441_e, x, y, z, mx, my, mz, new int[0]);
      } else if (type.equalsIgnoreCase("instantSpell")) {
        entityfx = create(net.minecraft.client.particle.ParticleSpell.InstantFactory::new, (World)mc.field_71441_e, x, y, z, mx, my, mz, new int[0]);
      } else if (type.equalsIgnoreCase("witchMagic")) {
        entityfx = create(net.minecraft.client.particle.ParticleSpell.WitchFactory::new, (World)mc.field_71441_e, x, y, z, mx, my, mz, new int[0]);
      } else if (type.equalsIgnoreCase("note")) {
        entityfx = create(net.minecraft.client.particle.ParticleNote.Factory::new, (World)mc.field_71441_e, x, y, z, mx, my, mz, new int[0]);
      } else if (type.equalsIgnoreCase("portal")) {
        entityfx = create(net.minecraft.client.particle.ParticlePortal.Factory::new, (World)mc.field_71441_e, x, y, z, mx, my, mz, new int[0]);
      } else if (type.equalsIgnoreCase("enchantmenttable")) {
        entityfx = create(net.minecraft.client.particle.ParticleEnchantmentTable.EnchantmentTable::new, (World)mc.field_71441_e, x, y, z, mx, my, mz, new int[0]);
      } else if (type.equalsIgnoreCase("explode")) {
        entityfx = create(net.minecraft.client.particle.ParticleExplosion.Factory::new, (World)mc.field_71441_e, x, y, z, mx, my, mz, new int[0]);
      } else if (type.equalsIgnoreCase("flame")) {
        entityfx = create(net.minecraft.client.particle.ParticleFlame.Factory::new, (World)mc.field_71441_e, x, y, z, mx, my, mz, new int[0]);
      } else if (type.equalsIgnoreCase("lava")) {
        entityfx = create(net.minecraft.client.particle.ParticleLava.Factory::new, (World)mc.field_71441_e, x, y, z, mx, my, mz, new int[0]);
      } else if (type.equalsIgnoreCase("footstep")) {
        entityfx = create(net.minecraft.client.particle.ParticleFootStep.Factory::new, (World)mc.field_71441_e, x, y, z, mx, my, mz, new int[0]);
      } else if (type.equalsIgnoreCase("splash")) {
        entityfx = create(net.minecraft.client.particle.ParticleSplash.Factory::new, (World)mc.field_71441_e, x, y, z, mx, my, mz, new int[0]);
      } else if (type.equalsIgnoreCase("wake")) {
        entityfx = create(net.minecraft.client.particle.ParticleWaterWake.Factory::new, (World)mc.field_71441_e, x, y, z, mx, my, mz, new int[0]);
      } else if (type.equalsIgnoreCase("largesmoke")) {
        entityfx = create(net.minecraft.client.particle.ParticleSmokeLarge.Factory::new, (World)mc.field_71441_e, x, y, z, mx, my, mz, new int[0]);
      } else if (type.equalsIgnoreCase("cloud")) {
        entityfx = create(net.minecraft.client.particle.ParticleCloud.Factory::new, (World)mc.field_71441_e, x, y, z, mx, my, mz, new int[0]);
      } else if (type.equalsIgnoreCase("reddust")) {
        entityfx = create(net.minecraft.client.particle.ParticleRedstone.Factory::new, (World)mc.field_71441_e, x, y, z, mx, my, mz, new int[0]);
      } else if (type.equalsIgnoreCase("snowballpoof")) {
        entityfx = create(net.minecraft.client.particle.ParticleBreaking.SnowballFactory::new, (World)mc.field_71441_e, x, y, z, mx, my, mz, new int[0]);
      } else if (type.equalsIgnoreCase("dripWater")) {
        entityfx = create(net.minecraft.client.particle.ParticleDrip.WaterFactory::new, (World)mc.field_71441_e, x, y, z, mx, my, mz, new int[0]);
      } else if (type.equalsIgnoreCase("dripLava")) {
        entityfx = create(net.minecraft.client.particle.ParticleDrip.LavaFactory::new, (World)mc.field_71441_e, x, y, z, mx, my, mz, new int[0]);
      } else if (type.equalsIgnoreCase("snowshovel")) {
        entityfx = create(net.minecraft.client.particle.ParticleSnowShovel.Factory::new, (World)mc.field_71441_e, x, y, z, mx, my, mz, new int[0]);
      } else if (type.equalsIgnoreCase("slime")) {
        entityfx = create(net.minecraft.client.particle.ParticleBreaking.SlimeFactory::new, (World)mc.field_71441_e, x, y, z, mx, my, mz, new int[0]);
      } else if (type.equalsIgnoreCase("heart")) {
        entityfx = create(net.minecraft.client.particle.ParticleHeart.Factory::new, (World)mc.field_71441_e, x, y, z, mx, my, mz, new int[0]);
      } else if (type.equalsIgnoreCase("angryVillager")) {
        entityfx = create(net.minecraft.client.particle.ParticleHeart.AngryVillagerFactory::new, (World)mc.field_71441_e, x, y, z, mx, my, mz, new int[0]);
      } else if (type.equalsIgnoreCase("happyVillager")) {
        entityfx = create(net.minecraft.client.particle.ParticleSuspendedTown.HappyVillagerFactory::new, (World)mc.field_71441_e, x, y, z, mx, my, mz, new int[0]);
      } else if (type.startsWith("iconcrack")) {
        entityfx = create(net.minecraft.client.particle.ParticleBreaking.Factory::new, (World)mc.field_71441_e, x, y, z, mx, my, mz, params);
      } else if (type.startsWith("blockcrack")) {
        entityfx = create(net.minecraft.client.particle.ParticleDigging.Factory::new, (World)mc.field_71441_e, x, y, z, mx, my, mz, params);
      } else if (type.startsWith("blockdust")) {
        entityfx = create(Factory::new, (World)mc.field_71441_e, x, y, z, mx, my, mz, params);
      } 
      if (entityfx != null)
        mc.field_71452_i.func_78873_a(entityfx); 
      return entityfx;
    } 
    return null;
  }
  
  public static void spawnParticle(MCH_ParticleParam p) {
    if (p.world.field_72995_K) {
      MCH_EntityParticleBase entityFX = null;
      if (p.name.equalsIgnoreCase("Splash")) {
        entityFX = new MCH_EntityParticleSplash(p.world, p.posX, p.posY, p.posZ, p.motionX, p.motionY, p.motionZ);
      } else {
        entityFX = new MCH_EntityParticleSmoke(p.world, p.posX, p.posY, p.posZ, p.motionX, p.motionY, p.motionZ);
      } 
      entityFX.func_70538_b(p.r, p.g, p.b);
      entityFX.func_82338_g(p.a);
      if (p.age > 0)
        entityFX.setParticleMaxAge(p.age); 
      entityFX.moutionYUpAge = p.motionYUpAge;
      entityFX.gravity = p.gravity;
      entityFX.isEffectedWind = p.isEffectWind;
      entityFX.diffusible = p.diffusible;
      entityFX.toWhite = p.toWhite;
      if (p.diffusible) {
        entityFX.setParticleScale(p.size * 0.2F);
        entityFX.particleMaxScale = p.size * 2.0F;
      } else {
        entityFX.setParticleScale(p.size);
      } 
      (FMLClientHandler.instance().getClient()).field_71452_i.func_78873_a((Particle)entityFX);
    } 
  }
  
  public static MCH_EntityParticleMarkPoint markPoint = null;
  
  public static void spawnMarkPoint(EntityPlayer player, double x, double y, double z) {
    clearMarkPoint();
    markPoint = new MCH_EntityParticleMarkPoint(player.field_70170_p, x, y, z, player.func_96124_cp());
    (FMLClientHandler.instance().getClient()).field_71452_i.func_78873_a((Particle)markPoint);
  }
  
  public static void clearMarkPoint() {
    if (markPoint != null) {
      markPoint.func_187112_i();
      markPoint = null;
    } 
  }
  
  private static Particle create(Supplier<IParticleFactory> factoryFunc, World world, double x, double y, double z, double mx, double my, double mz, int... param) {
    return ((IParticleFactory)factoryFunc.get()).func_178902_a(-1, world, x, y, z, mx, my, mz, param);
  }
}
