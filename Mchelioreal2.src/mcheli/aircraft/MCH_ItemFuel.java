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
    func_77656_e(600);
    func_77625_d(1);
    setNoRepair();
    func_77664_n();
  }
  
  public ActionResult<ItemStack> func_77659_a(World world, EntityPlayer player, EnumHand handIn) {
    ItemStack stack = player.func_184586_b(handIn);
    if (!world.field_72995_K && stack.func_77951_h() && !player.field_71075_bZ.field_75098_d) {
      refuel(stack, player, 1);
      refuel(stack, player, 0);
      return new ActionResult(EnumActionResult.SUCCESS, stack);
    } 
    return new ActionResult(EnumActionResult.PASS, stack);
  }
  
  private void refuel(ItemStack stack, EntityPlayer player, int coalType) {
    NonNullList<ItemStack> nonNullList = player.field_71071_by.field_70462_a;
    for (int i = 0; i < nonNullList.size(); i++) {
      ItemStack is = nonNullList.get(i);
      if (!is.func_190926_b() && is.func_77973_b() instanceof net.minecraft.item.ItemCoal && is.func_77960_j() == coalType) {
        for (int j = 0; is.func_190916_E() > 0 && stack.func_77951_h() && j < 64; j++) {
          int damage = stack.func_77960_j() - ((coalType == 1) ? 75 : 100);
          if (damage < 0)
            damage = 0; 
          stack.func_77964_b(damage);
          is.func_190918_g(1);
        } 
        if (is.func_190916_E() <= 0)
          nonNullList.set(i, ItemStack.field_190927_a); 
      } 
    } 
  }
}
