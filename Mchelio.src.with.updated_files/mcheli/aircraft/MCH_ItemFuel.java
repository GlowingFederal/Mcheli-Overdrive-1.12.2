package mcheli.aircraft;

import mcheli.wrapper.W_Item;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

public class MCH_ItemFuel extends W_Item {
  public MCH_ItemFuel(int itemID) {
    super(itemID);
    setMaxDamage(600);
    setMaxStackSize(1);
    setNoRepair();
    setFull3D();
  }
  
  public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand handIn) {
    ItemStack stack = player.getHeldItem(handIn);
    if (!world.isRemote && stack.isItemDamaged() && !player.capabilities.isCreativeMode) {
      refuel(stack, player, 1);
      refuel(stack, player, 0);
      return new ActionResult(EnumActionResult.SUCCESS, stack);
    } 
    return new ActionResult(EnumActionResult.PASS, stack);
  }
  
  private void refuel(ItemStack stack, EntityPlayer player, int coalType) {
    NonNullList<ItemStack> nonNullList = player.inventory.mainInventory;
    for (int i = 0; i < nonNullList.size(); i++) {
      ItemStack is = nonNullList.get(i);
      if (!is.func_190926_b() && is.getItem() instanceof net.minecraft.item.ItemCoal && is.getMetadata() == coalType) {
        for (int j = 0; is.func_190916_E() > 0 && stack.isItemDamaged() && j < 64; j++) {
          int damage = stack.getMetadata() - ((coalType == 1) ? 75 : 100);
          if (damage < 0)
            damage = 0; 
          stack.setItemDamage(damage);
          is.func_190918_g(1);
        } 
        if (is.func_190916_E() <= 0)
          nonNullList.set(i, ItemStack.field_190927_a); 
      } 
    } 
  }
}
