package mcheli;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class MCH_RecipeReloadRangeFinder extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {
  public boolean func_77569_a(InventoryCrafting inv, World var2) {
    int jcnt = 0;
    int ccnt = 0;
    for (int i = 0; i < inv.func_70302_i_(); i++) {
      ItemStack is = inv.func_70301_a(i);
      if (!is.func_190926_b())
        if (is.func_77973_b() instanceof mcheli.tool.rangefinder.MCH_ItemRangeFinder) {
          if (is.func_77960_j() == 0)
            return false; 
          jcnt++;
          if (jcnt > 1)
            return false; 
        } else if (is.func_77973_b() instanceof net.minecraft.item.ItemRedstone && is.func_190916_E() > 0) {
          ccnt++;
          if (ccnt > 1)
            return false; 
        } else {
          return false;
        }  
    } 
    return (jcnt == 1 && ccnt > 0);
  }
  
  public ItemStack func_77572_b(InventoryCrafting inv) {
    ItemStack output = new ItemStack((Item)MCH_MOD.itemRangeFinder);
    return output;
  }
  
  public boolean func_194133_a(int width, int height) {
    return (width >= 2 && height >= 2);
  }
  
  public ItemStack func_77571_b() {
    return ItemStack.field_190927_a;
  }
}
