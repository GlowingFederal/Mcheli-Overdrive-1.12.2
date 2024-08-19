package mcheli.weapon;

import mcheli.MCH_Config;
import mcheli.wrapper.W_Blocks;
import mcheli.wrapper.W_Item;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;

public class MCH_EntityDispensedItem extends MCH_EntityBaseBullet {
  public MCH_EntityDispensedItem(World par1World) {
    super(par1World);
  }
  
  public MCH_EntityDispensedItem(World par1World, double posX, double posY, double posZ, double targetX, double targetY, double targetZ, float yaw, float pitch, double acceleration) {
    super(par1World, posX, posY, posZ, targetX, targetY, targetZ, yaw, pitch, acceleration);
  }
  
  public void onUpdate() {
    super.onUpdate();
    if (getInfo() != null && !(getInfo()).disableSmoke)
      spawnParticle((getInfo()).trajectoryParticleName, 3, 7.0F * (getInfo()).smokeSize); 
    if (!this.world.isRemote && getInfo() != null) {
      if (this.acceleration < 1.0E-4D) {
        this.motionX *= 0.999D;
        this.motionZ *= 0.999D;
      } 
      if (isInWater()) {
        this.motionX *= (getInfo()).velocityInWater;
        this.motionY *= (getInfo()).velocityInWater;
        this.motionZ *= (getInfo()).velocityInWater;
      } 
    } 
    onUpdateBomblet();
  }
  
  protected void onImpact(RayTraceResult m, float damageFactor) {
    if (!this.world.isRemote) {
      setEntityBoundingBox(getEntityBoundingBox().offset(0.0D, 2000.0D, 0.0D));
      EntityPlayer player = null;
      Item item = null;
      int itemDamage = 0;
      if (m != null && getInfo() != null) {
        if (this.shootingAircraft instanceof EntityPlayer)
          player = (EntityPlayer)this.shootingAircraft; 
        if (this.shootingEntity instanceof EntityPlayer)
          player = (EntityPlayer)this.shootingEntity; 
        item = (getInfo()).dispenseItem;
        itemDamage = (getInfo()).dispenseDamege;
      } 
      if (player != null && !player.isDead && item != null) {
        MCH_DummyEntityPlayer mCH_DummyEntityPlayer = new MCH_DummyEntityPlayer(this.world, player);
        ((EntityPlayer)mCH_DummyEntityPlayer).rotationPitch = 90.0F;
        int RNG = (getInfo()).dispenseRange - 1;
        for (int x = -RNG; x <= RNG; x++) {
          for (int y = -RNG; y <= RNG; y++) {
            if (y >= 0 && y < 256)
              for (int z = -RNG; z <= RNG; z++) {
                int dist = x * x + y * y + z * z;
                if (dist <= RNG * RNG)
                  if (dist <= 0.5D * RNG * RNG) {
                    useItemToBlock(m.getBlockPos().add(x, y, z), item, itemDamage, (EntityPlayer)mCH_DummyEntityPlayer);
                  } else if (this.rand.nextInt(2) == 0) {
                    useItemToBlock(m.getBlockPos().add(x, y, z), item, itemDamage, (EntityPlayer)mCH_DummyEntityPlayer);
                  }  
              }  
          } 
        } 
      } 
      setDead();
    } 
  }
  
  private void useItemToBlock(BlockPos blockpos, Item item, int itemDamage, EntityPlayer dummyPlayer) {
    dummyPlayer.posX = blockpos.getX() + 0.5D;
    dummyPlayer.posY = blockpos.getY() + 2.5D;
    dummyPlayer.posZ = blockpos.getZ() + 0.5D;
    dummyPlayer.rotationYaw = this.rand.nextInt(360);
    IBlockState iblockstate = this.world.getBlockState(blockpos);
    Block block = iblockstate.getBlock();
    Material blockMat = iblockstate.getMaterial();
    if (block != W_Blocks.AIR && blockMat != Material.AIR)
      if (item == W_Item.getItemByName("water_bucket")) {
        if (MCH_Config.Collision_DestroyBlock.prmBool)
          if (blockMat == Material.FIRE) {
            this.world.setBlockToAir(blockpos);
          } else if (blockMat == Material.LAVA) {
            int metadata = block.getMetaFromState(iblockstate);
            if (metadata == 0) {
              this.world.setBlockState(blockpos, ForgeEventFactory.fireFluidPlaceBlockEvent(this.world, blockpos, blockpos, W_Blocks.OBSIDIAN
                    .getDefaultState()));
            } else if (metadata <= 4) {
              this.world.setBlockState(blockpos, ForgeEventFactory.fireFluidPlaceBlockEvent(this.world, blockpos, blockpos, W_Blocks.COBBLESTONE
                    .getDefaultState()));
            } 
          }  
      } else if (item.onItemUseFirst(dummyPlayer, this.world, blockpos, EnumFacing.UP, blockpos.getX(), blockpos
          .getY(), blockpos.getZ(), EnumHand.MAIN_HAND) == EnumActionResult.PASS) {
        if (item.onItemUse(dummyPlayer, this.world, blockpos, EnumHand.MAIN_HAND, EnumFacing.UP, blockpos
            .getX(), blockpos.getY(), blockpos.getZ()) == EnumActionResult.PASS)
          item.onItemRightClick(this.world, dummyPlayer, EnumHand.MAIN_HAND); 
      }  
  }
  
  public void sprinkleBomblet() {
    if (!this.world.isRemote) {
      MCH_EntityDispensedItem e = new MCH_EntityDispensedItem(this.world, this.posX, this.posY, this.posZ, this.motionX, this.motionY, this.motionZ, this.rand.nextInt(360), 0.0F, this.acceleration);
      e.setParameterFromWeapon(this, this.shootingAircraft, this.shootingEntity);
      e.setName(getName());
      float RANDOM = (getInfo()).bombletDiff;
      e.motionX = this.motionX * 1.0D + ((this.rand.nextFloat() - 0.5F) * RANDOM);
      e.motionY = this.motionY * 1.0D / 2.0D + ((this.rand.nextFloat() - 0.5F) * RANDOM / 2.0F);
      e.motionZ = this.motionZ * 1.0D + ((this.rand.nextFloat() - 0.5F) * RANDOM);
      e.setBomblet();
      this.world.spawnEntityInWorld((Entity)e);
    } 
  }
  
  public MCH_BulletModel getDefaultBulletModel() {
    return MCH_DefaultBulletModels.Bomb;
  }
}
