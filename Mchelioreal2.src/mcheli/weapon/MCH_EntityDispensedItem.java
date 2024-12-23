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
  
  public void func_70071_h_() {
    super.func_70071_h_();
    if (getInfo() != null && !(getInfo()).disableSmoke)
      spawnParticle((getInfo()).trajectoryParticleName, 3, 7.0F * (getInfo()).smokeSize); 
    if (!this.field_70170_p.field_72995_K && getInfo() != null) {
      if (this.acceleration < 1.0E-4D) {
        this.field_70159_w *= 0.999D;
        this.field_70179_y *= 0.999D;
      } 
      if (func_70090_H()) {
        this.field_70159_w *= (getInfo()).velocityInWater;
        this.field_70181_x *= (getInfo()).velocityInWater;
        this.field_70179_y *= (getInfo()).velocityInWater;
      } 
    } 
    onUpdateBomblet();
  }
  
  protected void onImpact(RayTraceResult m, float damageFactor) {
    if (!this.field_70170_p.field_72995_K) {
      func_174826_a(func_174813_aQ().func_72317_d(0.0D, 2000.0D, 0.0D));
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
      if (player != null && !player.field_70128_L && item != null) {
        MCH_DummyEntityPlayer mCH_DummyEntityPlayer = new MCH_DummyEntityPlayer(this.field_70170_p, player);
        ((EntityPlayer)mCH_DummyEntityPlayer).field_70125_A = 90.0F;
        int RNG = (getInfo()).dispenseRange - 1;
        for (int x = -RNG; x <= RNG; x++) {
          for (int y = -RNG; y <= RNG; y++) {
            if (y >= 0 && y < 256)
              for (int z = -RNG; z <= RNG; z++) {
                int dist = x * x + y * y + z * z;
                if (dist <= RNG * RNG)
                  if (dist <= 0.5D * RNG * RNG) {
                    useItemToBlock(m.func_178782_a().func_177982_a(x, y, z), item, itemDamage, (EntityPlayer)mCH_DummyEntityPlayer);
                  } else if (this.field_70146_Z.nextInt(2) == 0) {
                    useItemToBlock(m.func_178782_a().func_177982_a(x, y, z), item, itemDamage, (EntityPlayer)mCH_DummyEntityPlayer);
                  }  
              }  
          } 
        } 
      } 
      func_70106_y();
    } 
  }
  
  private void useItemToBlock(BlockPos blockpos, Item item, int itemDamage, EntityPlayer dummyPlayer) {
    dummyPlayer.field_70165_t = blockpos.func_177958_n() + 0.5D;
    dummyPlayer.field_70163_u = blockpos.func_177956_o() + 2.5D;
    dummyPlayer.field_70161_v = blockpos.func_177952_p() + 0.5D;
    dummyPlayer.field_70177_z = this.field_70146_Z.nextInt(360);
    IBlockState iblockstate = this.field_70170_p.func_180495_p(blockpos);
    Block block = iblockstate.func_177230_c();
    Material blockMat = iblockstate.func_185904_a();
    if (block != W_Blocks.field_150350_a && blockMat != Material.field_151579_a)
      if (item == W_Item.getItemByName("water_bucket")) {
        if (MCH_Config.Collision_DestroyBlock.prmBool)
          if (blockMat == Material.field_151581_o) {
            this.field_70170_p.func_175698_g(blockpos);
          } else if (blockMat == Material.field_151587_i) {
            int metadata = block.func_176201_c(iblockstate);
            if (metadata == 0) {
              this.field_70170_p.func_175656_a(blockpos, ForgeEventFactory.fireFluidPlaceBlockEvent(this.field_70170_p, blockpos, blockpos, W_Blocks.field_150343_Z
                    .func_176223_P()));
            } else if (metadata <= 4) {
              this.field_70170_p.func_175656_a(blockpos, ForgeEventFactory.fireFluidPlaceBlockEvent(this.field_70170_p, blockpos, blockpos, W_Blocks.field_150347_e
                    .func_176223_P()));
            } 
          }  
      } else if (item.onItemUseFirst(dummyPlayer, this.field_70170_p, blockpos, EnumFacing.UP, blockpos.func_177958_n(), blockpos
          .func_177956_o(), blockpos.func_177952_p(), EnumHand.MAIN_HAND) == EnumActionResult.PASS) {
        if (item.func_180614_a(dummyPlayer, this.field_70170_p, blockpos, EnumHand.MAIN_HAND, EnumFacing.UP, blockpos
            .func_177958_n(), blockpos.func_177956_o(), blockpos.func_177952_p()) == EnumActionResult.PASS)
          item.func_77659_a(this.field_70170_p, dummyPlayer, EnumHand.MAIN_HAND); 
      }  
  }
  
  public void sprinkleBomblet() {
    if (!this.field_70170_p.field_72995_K) {
      MCH_EntityDispensedItem e = new MCH_EntityDispensedItem(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v, this.field_70159_w, this.field_70181_x, this.field_70179_y, this.field_70146_Z.nextInt(360), 0.0F, this.acceleration);
      e.setParameterFromWeapon(this, this.shootingAircraft, this.shootingEntity);
      e.setName(func_70005_c_());
      float RANDOM = (getInfo()).bombletDiff;
      e.field_70159_w = this.field_70159_w * 1.0D + ((this.field_70146_Z.nextFloat() - 0.5F) * RANDOM);
      e.field_70181_x = this.field_70181_x * 1.0D / 2.0D + ((this.field_70146_Z.nextFloat() - 0.5F) * RANDOM / 2.0F);
      e.field_70179_y = this.field_70179_y * 1.0D + ((this.field_70146_Z.nextFloat() - 0.5F) * RANDOM);
      e.setBomblet();
      this.field_70170_p.func_72838_d((Entity)e);
    } 
  }
  
  public MCH_BulletModel getDefaultBulletModel() {
    return MCH_DefaultBulletModels.Bomb;
  }
}
