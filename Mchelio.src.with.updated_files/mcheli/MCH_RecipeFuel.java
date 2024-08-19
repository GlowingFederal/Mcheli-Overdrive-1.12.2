package mcheli;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class MCH_RecipeFuel extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {
  public boolean matches(InventoryCrafting inv, World var2) {
    int jcnt = 0;
    int ccnt = 0;
    for (int i = 0; i < inv.getSizeInventory(); i++) {
      ItemStack is = inv.getStackInSlot(i);
      if (!is.func_190926_b())
        if (is.getItem() instanceof mcheli.aircraft.MCH_ItemFuel) {
          if (is.getMetadata() == 0)
            return false; 
          jcnt++;
          if (jcnt > 1)
            return false; 
        } else if (is.getItem() instanceof net.minecraft.item.ItemCoal && is.func_190916_E() > 0) {
          ccnt++;
        } else {
          return false;
        }  
    } 
    return (jcnt == 1 && ccnt > 0);
  }
  
  public ItemStack getCraftingResult(InventoryCrafting inv) {
    ItemStack output = new ItemStack((Item)MCH_MOD.itemFuel);
    int i;
    for (i = 0; i < inv.getSizeInventory(); i++) {
      ItemStack is = inv.getStackInSlot(i);
      if (!is.func_190926_b() && is.getItem() instanceof mcheli.aircraft.MCH_ItemFuel) {
        output.setItemDamage(is.getMetadata());
        break;
      } 
    } 
    for (i = 0; i < inv.getSizeInventory(); i++) {
      ItemStack is = inv.getStackInSlot(i);
      if (!is.func_190926_b() && is.getItem() instanceof net.minecraft.item.ItemCoal) {
        int sp = 100;
        if (is.getMetadata() == 1)
          sp = 75; 
        if (output.getMetadata() > sp) {
          output.setItemDamage(output.getMetadata() - sp);
        } else {
          output.setItemDamage(0);
        } 
      } 
    } 
    return output;
  }
  
  public boolean func_194133_a(int width, int height) {
    return (width >= 3 && height >= 3);
  }
  
  public ItemStack getRecipeOutput() {
    return ItemStack.field_190927_a;
  }
}
