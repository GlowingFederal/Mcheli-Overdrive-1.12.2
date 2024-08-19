package mcheli.throwable;

import mcheli.MCH_Lib;
import mcheli.wrapper.W_Item;
import net.minecraft.block.BlockDispenser;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class MCH_ItemThrowable extends W_Item {
  public MCH_ItemThrowable(int par1) {
    super(par1);
    setMaxStackSize(1);
  }
  
  public static void registerDispenseBehavior(Item item) {
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(item, new MCH_ItemThrowableDispenseBehavior());
  }
  
  public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand handIn) {
    ItemStack itemstack = player.getHeldItem(handIn);
    player.setActiveHand(handIn);
    return ActionResult.newResult(EnumActionResult.SUCCESS, itemstack);
  }
  
  public void onPlayerStoppedUsing(ItemStack itemStack, World world, EntityLivingBase entityLiving, int par4) {
    if (!(entityLiving instanceof EntityPlayer))
      return; 
    EntityPlayer player = (EntityPlayer)entityLiving;
    if (!itemStack.func_190926_b() && itemStack.func_190916_E() > 0) {
      MCH_ThrowableInfo info = MCH_ThrowableInfoManager.get(itemStack.getItem());
      if (info != null) {
        if (!player.capabilities.isCreativeMode) {
          itemStack.func_190918_g(1);
          if (itemStack.func_190916_E() <= 0)
            player.inventory.setInventorySlotContents(player.inventory.currentItem, ItemStack.field_190927_a); 
        } 
        world.playSound((EntityPlayer)null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 0.5F, 0.4F / (itemRand
            
            .nextFloat() * 0.4F + 0.8F));
        if (!world.isRemote) {
          float acceleration = 1.0F;
          par4 = itemStack.getMaxItemUseDuration() - par4;
          if (par4 <= 35) {
            if (par4 < 5)
              par4 = 5; 
            acceleration = par4 / 25.0F;
          } 
          MCH_Lib.DbgLog(world, "MCH_ItemThrowable.onPlayerStoppedUsing(%d)", new Object[] { Integer.valueOf(par4) });
          MCH_EntityThrowable entity = new MCH_EntityThrowable(world, (EntityLivingBase)player, acceleration);
          entity.setHeadingFromThrower((Entity)player, player.rotationPitch, player.rotationYaw, 0.0F, acceleration, 1.0F);
          entity.setInfo(info);
          world.spawnEntityInWorld((Entity)entity);
        } 
      } 
    } 
  }
  
  public int getMaxItemUseDuration(ItemStack par1ItemStack) {
    return 72000;
  }
  
  public EnumAction getItemUseAction(ItemStack par1ItemStack) {
    return EnumAction.BOW;
  }
}
