package mcheli;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class MCH_RecipeReloadRangeFinder extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {
  public boolean matches(InventoryCrafting inv, World var2) {
    int jcnt = 0;
    int ccnt = 0;
    for (int i = 0; i < inv.getSizeInventory(); i++) {
      ItemStack is = inv.getStackInSlot(i);
      if (!is.isEmpty())
        if (is.getItem() instanceof mcheli.tool.rangefinder.MCH_ItemRangeFinder) {
          if (is.getMetadata() == 0)
            return false; 
          jcnt++;
          if (jcnt > 1)
            return false; 
        } else if (is.getItem() instanceof net.minecraft.item.ItemRedstone && is.getCount() > 0) {
          ccnt++;
          if (ccnt > 1)
            return false; 
        } else {
          return false;
        }  
    } 
    return (jcnt == 1 && ccnt > 0);
  }
  
  public ItemStack getCraftingResult(InventoryCrafting inv) {
    ItemStack output = new ItemStack((Item)MCH_MOD.itemRangeFinder);
    return output;
  }
  
  public boolean canFit(int width, int height) {
    return (width >= 2 && height >= 2);
  }
  
  public ItemStack getRecipeOutput() {
    return ItemStack.EMPTY;
  }
}
