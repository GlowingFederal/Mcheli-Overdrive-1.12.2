package mcheli.__helper;

import com.google.common.collect.Sets;
import java.util.Set;
import mcheli.MCH_ItemRecipe;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistryEntry;

@EventBusSubscriber(modid = "mcheli")
public class MCH_Recipes {
  private static final Set<IRecipe> registryWrapper = Sets.newLinkedHashSet();
  
  @SubscribeEvent
  static void onRecipeRegisterEvent(RegistryEvent.Register<IRecipe> event) {
    MCH_ItemRecipe.registerItemRecipe(event.getRegistry());
    for (IRecipe recipe : registryWrapper)
      event.getRegistry().register((IForgeRegistryEntry)recipe); 
  }
  
  public static void register(String name, IRecipe recipe) {
    registryWrapper.add(recipe.setRegistryName(MCH_Utils.suffix(name)));
  }
  
  public static ShapedRecipes addShapedRecipe(String name, ItemStack output, Object... params) {
    CraftingHelper.ShapedPrimer primer = CraftingHelper.parseShaped(params);
    ShapedRecipes recipe = new ShapedRecipes("", primer.width, primer.height, primer.input, output);
    register(name, (IRecipe)recipe);
    return recipe;
  }
  
  public static boolean canCraft(EntityPlayer player, IRecipe recipe) {
    for (Ingredient ingredient : recipe.func_192400_c()) {
      if (ingredient == Ingredient.field_193370_a)
        continue; 
      boolean flag = false;
      for (ItemStack itemstack : player.field_71071_by.field_70462_a) {
        if (ingredient.apply(itemstack)) {
          flag = true;
          break;
        } 
      } 
      if (!flag)
        return false; 
    } 
    return true;
  }
  
  public static boolean consumeInventory(EntityPlayer player, IRecipe recipe) {
    for (Ingredient ingredient : recipe.func_192400_c()) {
      if (ingredient == Ingredient.field_193370_a)
        continue; 
      int i = 0;
      boolean flag = false;
      for (ItemStack itemstack : player.field_71071_by.field_70462_a) {
        if (ingredient.apply(itemstack)) {
          player.field_71071_by.func_70298_a(i, 1);
          flag = true;
          break;
        } 
        i++;
      } 
      if (!flag)
        return false; 
    } 
    return true;
  }
}
