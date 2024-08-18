package mcheli;

import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import javax.annotation.Nullable;
import mcheli.__helper.MCH_Recipes;
import mcheli.__helper.MCH_Utils;
import mcheli.__helper.info.ContentRegistries;
import mcheli.aircraft.MCH_AircraftInfo;
import mcheli.aircraft.MCH_AircraftInfoManager;
import mcheli.helicopter.MCH_HeliInfo;
import mcheli.helicopter.MCH_HeliInfoManager;
import mcheli.plane.MCP_PlaneInfo;
import mcheli.plane.MCP_PlaneInfoManager;
import mcheli.tank.MCH_TankInfo;
import mcheli.tank.MCH_TankInfoManager;
import mcheli.throwable.MCH_ThrowableInfo;
import mcheli.vehicle.MCH_VehicleInfo;
import mcheli.vehicle.MCH_VehicleInfoManager;
import mcheli.wrapper.W_Block;
import mcheli.wrapper.W_Item;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.util.NonNullList;
import net.minecraftforge.registries.IForgeRegistry;

public class MCH_ItemRecipe implements MCH_IRecipeList {
  private static final MCH_ItemRecipe instance = new MCH_ItemRecipe();
  
  public static MCH_ItemRecipe getInstance() {
    return instance;
  }
  
  private static List<IRecipe> commonItemRecipe = new ArrayList<>();
  
  public int getRecipeListSize() {
    return commonItemRecipe.size();
  }
  
  public IRecipe getRecipe(int index) {
    return commonItemRecipe.get(index);
  }
  
  private static void addRecipeList(IRecipe recipe) {
    if (recipe != null)
      commonItemRecipe.add(recipe); 
  }
  
  private static void registerCommonItemRecipe(IForgeRegistry<IRecipe> registry) {
    commonItemRecipe.clear();
    MCH_Recipes.register("charge_fuel", new MCH_RecipeFuel());
    addRecipeList((IRecipe)registry.getValue(MCH_Utils.suffix("fuel")));
    addRecipeList((IRecipe)registry.getValue(MCH_Utils.suffix("gltd")));
    addRecipeList((IRecipe)registry.getValue(MCH_Utils.suffix("chain")));
    addRecipeList((IRecipe)registry.getValue(MCH_Utils.suffix("parachute")));
    addRecipeList((IRecipe)registry.getValue(MCH_Utils.suffix("container")));
    for (int i = 0; i < MCH_MOD.itemUavStation.length; i++)
      addRecipeList((IRecipe)registry.getValue(MCH_Utils.suffix("uav_station" + ((i > 0) ? ("" + (i + 1)) : "")))); 
    addRecipeList((IRecipe)registry.getValue(MCH_Utils.suffix("wrench")));
    addRecipeList((IRecipe)registry.getValue(MCH_Utils.suffix("range_finder")));
    MCH_Recipes.register("charge_power_range_finder", new MCH_RecipeReloadRangeFinder());
    addRecipeList((IRecipe)registry.getValue(MCH_Utils.suffix("fim92")));
    addRecipeList((IRecipe)registry.getValue(MCH_Utils.suffix("fim92_bullet")));
    addRecipeList((IRecipe)registry.getValue(MCH_Utils.suffix("fgm148")));
    addRecipeList((IRecipe)registry.getValue(MCH_Utils.suffix("fgm148_bullet")));
    addRecipeList((IRecipe)registry.getValue(MCH_Utils.suffix("spawn_gunner_vs_monster")));
    addRecipeList((IRecipe)registry.getValue(MCH_Utils.suffix("spawn_gunner_vs_player")));
    addRecipeList((IRecipe)registry.getValue(MCH_Utils.suffix("drafting_table")));
  }
  
  public static void registerItemRecipe(IForgeRegistry<IRecipe> registry) {
    registerCommonItemRecipe(registry);
    for (MCH_HeliInfo info : ContentRegistries.heli().values())
      addRecipeAndRegisterList((MCH_AircraftInfo)info, (Item)info.item, (MCH_AircraftInfoManager<MCH_AircraftInfo>)MCH_HeliInfoManager.getInstance()); 
    for (MCP_PlaneInfo info : ContentRegistries.plane().values())
      addRecipeAndRegisterList((MCH_AircraftInfo)info, (Item)info.item, (MCH_AircraftInfoManager<MCH_AircraftInfo>)MCP_PlaneInfoManager.getInstance()); 
    for (MCH_TankInfo info : ContentRegistries.tank().values())
      addRecipeAndRegisterList((MCH_AircraftInfo)info, (Item)info.item, (MCH_AircraftInfoManager<MCH_AircraftInfo>)MCH_TankInfoManager.getInstance()); 
    for (MCH_VehicleInfo info : ContentRegistries.vehicle().values())
      addRecipeAndRegisterList((MCH_AircraftInfo)info, (Item)info.item, (MCH_AircraftInfoManager<MCH_AircraftInfo>)MCH_VehicleInfoManager.getInstance()); 
    for (MCH_ThrowableInfo info : ContentRegistries.throwable().values()) {
      for (String s : info.recipeString) {
        if (s.length() >= 3) {
          IRecipe recipe = addRecipe(info.name, (Item)info.item, s, info.isShapedRecipe);
          if (recipe != null) {
            info.recipe.add(recipe);
            addRecipeList(recipe);
          } 
        } 
      } 
      info.recipeString = null;
    } 
  }
  
  private static <T extends MCH_AircraftInfo> void addRecipeAndRegisterList(MCH_AircraftInfo info, Item item, MCH_AircraftInfoManager<T> im) {
    int count = 0;
    for (String s : info.recipeString) {
      count++;
      if (s.length() >= 3) {
        IRecipe recipe = addRecipe(info.name, item, s, info.isShapedRecipe);
        if (recipe != null) {
          info.recipe.add(recipe);
          im.addRecipe(recipe, count, info.name, s);
        } 
      } 
    } 
    info.recipeString = null;
  }
  
  public static IRecipe addRecipe(String name, Item item, String data) {
    return addShapedRecipe(name, item, data);
  }
  
  @Nullable
  public static IRecipe addRecipe(String name, Item item, String data, boolean isShaped) {
    if (isShaped)
      return addShapedRecipe(name, item, data); 
    return addShapelessRecipe(name, item, data);
  }
  
  @Nullable
  public static IRecipe addShapedRecipe(String name, Item item, String data) {
    ShapedRecipes r;
    ArrayList<Object> rcp = new ArrayList();
    String[] s = data.split("\\s*,\\s*");
    if (s.length < 3)
      return null; 
    int start = 0;
    int createNum = 1;
    if (isNumber(s[0])) {
      start = 1;
      createNum = Integer.valueOf(s[0]).intValue();
      if (createNum <= 0)
        createNum = 1; 
    } 
    Set<Integer> needShortChars = Sets.newHashSet();
    int idx = start;
    for (int i = start; i < 3 + start; i++) {
      if (s[idx].length() > 0 && s[idx].charAt(0) == '"' && s[idx].charAt(s[idx].length() - 1) == '"') {
        String ingredientStr = s[idx].substring(1, s[idx].length() - 1);
        ingredientStr.toUpperCase().chars().forEach(needShortChars::add);
        rcp.add(s[idx].subSequence(1, s[idx].length() - 1));
        idx++;
      } 
    } 
    if (idx == 0)
      return null; 
    boolean isChar = true;
    boolean flag = false;
    for (; idx < s.length; idx++) {
      if (s[idx].length() <= 0)
        return null; 
      if (isChar) {
        if (s[idx].length() != 1)
          return null; 
        char c = s[idx].toUpperCase().charAt(0);
        if (c < 'A' || c > 'Z')
          return null; 
        if (!needShortChars.contains(Integer.valueOf(c))) {
          MCH_Utils.logger()
            .warn("Key defines symbols that aren't used in pattern: [" + c + "], item:" + name);
          flag = true;
        } 
        if (!flag)
          rcp.add(Character.valueOf(c)); 
      } else {
        if (!flag) {
          String nm = s[idx].trim().toLowerCase();
          int dmg = 0;
          if (idx + 1 < s.length && isNumber(s[idx + 1])) {
            idx++;
            dmg = Integer.parseInt(s[idx]);
          } 
          if (isNumber(nm))
            return null; 
          rcp.add(new ItemStack(W_Item.getItemByName(nm), 1, dmg));
        } 
        flag = false;
      } 
      isChar = !isChar;
    } 
    Object[] recipe = new Object[rcp.size()];
    for (int j = 0; j < recipe.length; j++)
      recipe[j] = rcp.get(j); 
    try {
      r = MCH_Recipes.addShapedRecipe(name, new ItemStack(item, createNum), recipe);
    } catch (Exception e) {
      MCH_Utils.logger().warn(e.getMessage() + ", name:" + name);
      return null;
    } 
    for (int k = 0; k < r.field_77574_d.size(); k++) {
      if (r.field_77574_d.get(k) != Ingredient.field_193370_a)
        if (Arrays.<ItemStack>stream(((Ingredient)r.field_77574_d.get(k)).func_193365_a()).anyMatch(stack -> (stack.func_77973_b() == null)))
          throw new RuntimeException("Error: Invalid ShapedRecipes! " + item + " : " + data);  
    } 
    return (IRecipe)r;
  }
  
  @Nullable
  public static IRecipe addShapelessRecipe(String name, Item item, String data) {
    ArrayList<Object> rcp = new ArrayList();
    String[] s = data.split("\\s*,\\s*");
    if (s.length < 1)
      return null; 
    int start = 0;
    int createNum = 1;
    if (isNumber(s[0]))
      if (createNum <= 0)
        createNum = 1;  
    for (int idx = start; idx < s.length; idx++) {
      if (s[idx].length() <= 0)
        return null; 
      String nm = s[idx].trim().toLowerCase();
      int dmg = 0;
      if (idx + 1 < s.length && isNumber(s[idx + 1])) {
        idx++;
        dmg = Integer.parseInt(s[idx]);
      } 
      if (isNumber(nm)) {
        int n = Integer.parseInt(nm);
        if (n <= 255) {
          rcp.add(new ItemStack(W_Block.getBlockById(n), 1, dmg));
        } else if (n <= 511) {
          rcp.add(new ItemStack(W_Item.getItemById(n), 1, dmg));
        } else if (n <= 2255) {
          rcp.add(new ItemStack(W_Block.getBlockById(n), 1, dmg));
        } else if (n <= 2267) {
          rcp.add(new ItemStack(W_Item.getItemById(n), 1, dmg));
        } else if (n <= 4095) {
          rcp.add(new ItemStack(W_Block.getBlockById(n), 1, dmg));
        } else if (n <= 31999) {
          rcp.add(new ItemStack(W_Item.getItemById(n + 256), 1, dmg));
        } 
      } else {
        rcp.add(new ItemStack(W_Item.getItemByName(nm), 1, dmg));
      } 
    } 
    Object[] recipe = new Object[rcp.size()];
    for (int i = 0; i < recipe.length; i++)
      recipe[i] = rcp.get(i); 
    ShapelessRecipes r = getShapelessRecipe(new ItemStack(item, createNum), recipe);
    for (int j = 0; j < r.field_77579_b.size(); j++) {
      Ingredient ingredient = (Ingredient)r.field_77579_b.get(j);
      if (Arrays.<ItemStack>stream(ingredient.func_193365_a()).anyMatch(stack -> (stack.func_77973_b() == null)))
        throw new RuntimeException("Error: Invalid ShapelessRecipes! " + item + " : " + data); 
    } 
    MCH_Recipes.register(name, (IRecipe)r);
    return (IRecipe)r;
  }
  
  public static ShapelessRecipes getShapelessRecipe(ItemStack par1ItemStack, Object... par2ArrayOfObj) {
    NonNullList<Ingredient> list = NonNullList.func_191196_a();
    Object[] aobject = par2ArrayOfObj;
    int i = par2ArrayOfObj.length;
    for (int j = 0; j < i; j++) {
      Object object1 = aobject[j];
      if (object1 instanceof ItemStack) {
        list.add(Ingredient.func_193369_a(new ItemStack[] { ((ItemStack)object1).func_77946_l() }));
      } else if (object1 instanceof Item) {
        list.add(Ingredient.func_193369_a(new ItemStack[] { new ItemStack((Item)object1) }));
      } else {
        if (!(object1 instanceof Block))
          throw new RuntimeException("Invalid shapeless recipy!"); 
        list.add(Ingredient.func_193369_a(new ItemStack[] { new ItemStack((Block)object1) }));
      } 
    } 
    return new ShapelessRecipes("", par1ItemStack, list);
  }
  
  public static boolean isNumber(@Nullable String s) {
    if (s == null || s.isEmpty())
      return false; 
    byte[] buf = s.getBytes();
    for (byte b : buf) {
      if (b < 48 || b > 57)
        return false; 
    } 
    return true;
  }
}
