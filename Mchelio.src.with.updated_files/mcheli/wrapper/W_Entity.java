package mcheli.wrapper;

import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class W_Entity extends Entity {
  protected double _renderDistanceWeight = 1.0D;
  
  public W_Entity(World par1World) {
    super(par1World);
  }
  
  protected void entityInit() {}
  
  public static boolean isEntityFallingBlock(Entity entity) {
    return entity instanceof net.minecraft.entity.item.EntityFallingBlock;
  }
  
  public static int getEntityId(@Nullable Entity entity) {
    return (entity != null) ? entity.getEntityId() : -1;
  }
  
  public static boolean isEqual(@Nullable Entity e1, @Nullable Entity e2) {
    int i1 = getEntityId(e1);
    int i2 = getEntityId(e2);
    return (i1 == i2);
  }
  
  public EntityItem dropItemWithOffset(Item item, int par2, float par3) {
    return entityDropItem(new ItemStack(item, par2, 0), par3);
  }
  
  public String getEntityName() {
    return getEntityString();
  }
  
  public boolean attackEntityFrom(DamageSource par1DamageSource, int par2) {
    return attackEntityFrom(par1DamageSource, par2);
  }
  
  public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
    return false;
  }
  
  public static boolean attackEntityFrom(Entity entity, DamageSource ds, float par2) {
    return entity.attackEntityFrom(ds, par2);
  }
  
  public void addEntityCrashInfo(CrashReportCategory par1CrashReportCategory) {
    super.addEntityCrashInfo(par1CrashReportCategory);
  }
  
  public static float getBlockExplosionResistance(Entity entity, Explosion par1Explosion, World par2World, int par3, int par4, int par5, Block par6Block) {
    if (par6Block != null)
      try {
        return entity.getExplosionResistance(par1Explosion, par2World, new BlockPos(par3, par4, par5), par6Block
            .getDefaultState());
      } catch (Exception e) {
        e.printStackTrace();
      }  
    return 0.0F;
  }
  
  public static boolean shouldExplodeBlock(Entity entity, Explosion par1Explosion, World par2World, int par3, int par4, int par5, int par6, float par7) {
    return entity.verifyExplosion(par1Explosion, par2World, new BlockPos(par3, par4, par5), 
        W_Block.getBlockById(par6).getDefaultState(), par7);
  }
  
  public static PotionEffect getActivePotionEffect(Entity entity, Potion par1Potion) {
    return (entity instanceof EntityLivingBase) ? ((EntityLivingBase)entity).getActivePotionEffect(par1Potion) : null;
  }
  
  public static void removePotionEffectClient(Entity entity, Potion potion) {
    if (entity instanceof EntityLivingBase)
      ((EntityLivingBase)entity).removePotionEffect(potion); 
  }
  
  public static void removePotionEffect(Entity entity, Potion potion) {
    if (entity instanceof EntityLivingBase)
      ((EntityLivingBase)entity).removePotionEffect(potion); 
  }
  
  public static void addPotionEffect(Entity entity, PotionEffect pe) {
    if (entity instanceof EntityLivingBase)
      ((EntityLivingBase)entity).addPotionEffect(pe); 
  }
  
  protected void doBlockCollisions() {
    super.doBlockCollisions();
  }
  
  @SideOnly(Side.CLIENT)
  public boolean isInRangeToRenderDist(double distance) {
    double d0 = getEntityBoundingBox().getAverageEdgeLength();
    if (Double.isNaN(d0))
      d0 = 1.0D; 
    d0 = d0 * 64.0D * this._renderDistanceWeight;
    return (distance < d0 * d0);
  }
}
