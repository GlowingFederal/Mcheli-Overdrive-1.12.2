/*     */ package mcheli;
/*     */ 
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import javax.annotation.Nullable;
/*     */ import mcheli.__helper.MCH_Recipes;
/*     */ import mcheli.__helper.MCH_Utils;
/*     */ import mcheli.__helper.info.ContentRegistries;
/*     */ import mcheli.aircraft.MCH_AircraftInfo;
/*     */ import mcheli.aircraft.MCH_AircraftInfoManager;
/*     */ import mcheli.helicopter.MCH_HeliInfo;
/*     */ import mcheli.helicopter.MCH_HeliInfoManager;
/*     */ import mcheli.plane.MCP_PlaneInfo;
/*     */ import mcheli.plane.MCP_PlaneInfoManager;
/*     */ import mcheli.tank.MCH_TankInfo;
/*     */ import mcheli.tank.MCH_TankInfoManager;
/*     */ import mcheli.throwable.MCH_ThrowableInfo;
/*     */ import mcheli.vehicle.MCH_VehicleInfo;
/*     */ import mcheli.vehicle.MCH_VehicleInfoManager;
/*     */ import mcheli.wrapper.W_Block;
/*     */ import mcheli.wrapper.W_Item;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.item.crafting.IRecipe;
/*     */ import net.minecraft.item.crafting.Ingredient;
/*     */ import net.minecraft.item.crafting.ShapedRecipes;
/*     */ import net.minecraft.item.crafting.ShapelessRecipes;
/*     */ import net.minecraft.util.NonNullList;
/*     */ import net.minecraftforge.registries.IForgeRegistry;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MCH_ItemRecipe
/*     */   implements MCH_IRecipeList
/*     */ {
/*  46 */   private static final MCH_ItemRecipe instance = new MCH_ItemRecipe();
/*     */ 
/*     */   
/*     */   public static MCH_ItemRecipe getInstance() {
/*  50 */     return instance;
/*     */   }
/*     */   
/*  53 */   private static List<IRecipe> commonItemRecipe = new ArrayList<>();
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRecipeListSize() {
/*  58 */     return commonItemRecipe.size();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IRecipe getRecipe(int index) {
/*  64 */     return commonItemRecipe.get(index);
/*     */   }
/*     */ 
/*     */   
/*     */   private static void addRecipeList(IRecipe recipe) {
/*  69 */     if (recipe != null) {
/*  70 */       commonItemRecipe.add(recipe);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static void registerCommonItemRecipe(IForgeRegistry<IRecipe> registry) {
/*  76 */     commonItemRecipe.clear();
/*     */ 
/*     */     
/*  79 */     MCH_Recipes.register("charge_fuel", new MCH_RecipeFuel());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  87 */     addRecipeList((IRecipe)registry.getValue(MCH_Utils.suffix("fuel")));
/*  88 */     addRecipeList((IRecipe)registry.getValue(MCH_Utils.suffix("gltd")));
/*  89 */     addRecipeList((IRecipe)registry.getValue(MCH_Utils.suffix("chain")));
/*  90 */     addRecipeList((IRecipe)registry.getValue(MCH_Utils.suffix("parachute")));
/*  91 */     addRecipeList((IRecipe)registry.getValue(MCH_Utils.suffix("container")));
/*     */     
/*  93 */     for (int i = 0; i < MCH_MOD.itemUavStation.length; i++)
/*     */     {
/*  95 */       addRecipeList((IRecipe)registry.getValue(MCH_Utils.suffix("uav_station" + ((i > 0) ? ("" + (i + 1)) : ""))));
/*     */     }
/*     */ 
/*     */     
/*  99 */     addRecipeList((IRecipe)registry.getValue(MCH_Utils.suffix("wrench")));
/* 100 */     addRecipeList((IRecipe)registry.getValue(MCH_Utils.suffix("range_finder")));
/*     */ 
/*     */     
/* 103 */     MCH_Recipes.register("charge_power_range_finder", new MCH_RecipeReloadRangeFinder());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 112 */     addRecipeList((IRecipe)registry.getValue(MCH_Utils.suffix("fim92")));
/* 113 */     addRecipeList((IRecipe)registry.getValue(MCH_Utils.suffix("fim92_bullet")));
/* 114 */     addRecipeList((IRecipe)registry.getValue(MCH_Utils.suffix("fgm148")));
/* 115 */     addRecipeList((IRecipe)registry.getValue(MCH_Utils.suffix("fgm148_bullet")));
/* 116 */     addRecipeList((IRecipe)registry.getValue(MCH_Utils.suffix("spawn_gunner_vs_monster")));
/* 117 */     addRecipeList((IRecipe)registry.getValue(MCH_Utils.suffix("spawn_gunner_vs_player")));
/* 118 */     addRecipeList((IRecipe)registry.getValue(MCH_Utils.suffix("drafting_table")));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void registerItemRecipe(IForgeRegistry<IRecipe> registry) {
/* 125 */     registerCommonItemRecipe(registry);
/*     */ 
/*     */     
/* 128 */     for (MCH_HeliInfo info : ContentRegistries.heli().values())
/*     */     {
/*     */       
/* 131 */       addRecipeAndRegisterList((MCH_AircraftInfo)info, (Item)info.item, (MCH_AircraftInfoManager<MCH_AircraftInfo>)MCH_HeliInfoManager.getInstance());
/*     */     }
/*     */ 
/*     */     
/* 135 */     for (MCP_PlaneInfo info : ContentRegistries.plane().values())
/*     */     {
/*     */       
/* 138 */       addRecipeAndRegisterList((MCH_AircraftInfo)info, (Item)info.item, (MCH_AircraftInfoManager<MCH_AircraftInfo>)MCP_PlaneInfoManager.getInstance());
/*     */     }
/*     */ 
/*     */     
/* 142 */     for (MCH_TankInfo info : ContentRegistries.tank().values())
/*     */     {
/*     */       
/* 145 */       addRecipeAndRegisterList((MCH_AircraftInfo)info, (Item)info.item, (MCH_AircraftInfoManager<MCH_AircraftInfo>)MCH_TankInfoManager.getInstance());
/*     */     }
/*     */ 
/*     */     
/* 149 */     for (MCH_VehicleInfo info : ContentRegistries.vehicle().values())
/*     */     {
/*     */       
/* 152 */       addRecipeAndRegisterList((MCH_AircraftInfo)info, (Item)info.item, (MCH_AircraftInfoManager<MCH_AircraftInfo>)MCH_VehicleInfoManager.getInstance());
/*     */     }
/*     */ 
/*     */     
/* 156 */     for (MCH_ThrowableInfo info : ContentRegistries.throwable().values()) {
/*     */ 
/*     */ 
/*     */       
/* 160 */       for (String s : info.recipeString) {
/*     */         
/* 162 */         if (s.length() >= 3) {
/*     */ 
/*     */           
/* 165 */           IRecipe recipe = addRecipe(info.name, (Item)info.item, s, info.isShapedRecipe);
/*     */ 
/*     */ 
/*     */           
/* 169 */           if (recipe != null) {
/*     */             
/* 171 */             info.recipe.add(recipe);
/* 172 */             addRecipeList(recipe);
/*     */           } 
/*     */         } 
/*     */       } 
/* 176 */       info.recipeString = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static <T extends MCH_AircraftInfo> void addRecipeAndRegisterList(MCH_AircraftInfo info, Item item, MCH_AircraftInfoManager<T> im) {
/* 184 */     int count = 0;
/*     */     
/* 186 */     for (String s : info.recipeString) {
/*     */       
/* 188 */       count++;
/*     */       
/* 190 */       if (s.length() >= 3) {
/*     */ 
/*     */         
/* 193 */         IRecipe recipe = addRecipe(info.name, item, s, info.isShapedRecipe);
/*     */ 
/*     */ 
/*     */         
/* 197 */         if (recipe != null) {
/*     */           
/* 199 */           info.recipe.add(recipe);
/* 200 */           im.addRecipe(recipe, count, info.name, s);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 205 */     info.recipeString = null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static IRecipe addRecipe(String name, Item item, String data) {
/* 211 */     return addShapedRecipe(name, item, data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static IRecipe addRecipe(String name, Item item, String data, boolean isShaped) {
/* 218 */     if (isShaped)
/*     */     {
/*     */       
/* 221 */       return addShapedRecipe(name, item, data);
/*     */     }
/*     */ 
/*     */     
/* 225 */     return addShapelessRecipe(name, item, data);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static IRecipe addShapedRecipe(String name, Item item, String data) {
/*     */     ShapedRecipes r;
/* 232 */     ArrayList<Object> rcp = new ArrayList();
/* 233 */     String[] s = data.split("\\s*,\\s*");
/*     */     
/* 235 */     if (s.length < 3)
/*     */     {
/* 237 */       return null;
/*     */     }
/*     */     
/* 240 */     int start = 0;
/* 241 */     int createNum = 1;
/*     */     
/* 243 */     if (isNumber(s[0])) {
/*     */       
/* 245 */       start = 1;
/* 246 */       createNum = Integer.valueOf(s[0]).intValue();
/*     */       
/* 248 */       if (createNum <= 0)
/*     */       {
/* 250 */         createNum = 1;
/*     */       }
/*     */     } 
/*     */     
/* 254 */     Set<Integer> needShortChars = Sets.newHashSet();
/* 255 */     int idx = start;
/*     */     
/* 257 */     for (int i = start; i < 3 + start; i++) {
/*     */       
/* 259 */       if (s[idx].length() > 0 && s[idx].charAt(0) == '"' && s[idx].charAt(s[idx].length() - 1) == '"') {
/*     */         
/* 261 */         String ingredientStr = s[idx].substring(1, s[idx].length() - 1);
/* 262 */         ingredientStr.toUpperCase().chars().forEach(needShortChars::add);
/*     */         
/* 264 */         rcp.add(s[idx].subSequence(1, s[idx].length() - 1));
/* 265 */         idx++;
/*     */       } 
/*     */     } 
/*     */     
/* 269 */     if (idx == 0) {
/* 270 */       return null;
/*     */     }
/* 272 */     boolean isChar = true;
/* 273 */     boolean flag = false;
/*     */     
/* 275 */     for (; idx < s.length; idx++) {
/*     */       
/* 277 */       if (s[idx].length() <= 0) {
/* 278 */         return null;
/*     */       }
/* 280 */       if (isChar) {
/*     */         
/* 282 */         if (s[idx].length() != 1) {
/* 283 */           return null;
/*     */         }
/* 285 */         char c = s[idx].toUpperCase().charAt(0);
/*     */         
/* 287 */         if (c < 'A' || c > 'Z')
/*     */         {
/* 289 */           return null;
/*     */         }
/*     */         
/* 292 */         if (!needShortChars.contains(Integer.valueOf(c))) {
/*     */           
/* 294 */           MCH_Utils.logger()
/* 295 */             .warn("Key defines symbols that aren't used in pattern: [" + c + "], item:" + name);
/* 296 */           flag = true;
/*     */         } 
/*     */ 
/*     */         
/* 300 */         if (!flag)
/*     */         {
/* 302 */           rcp.add(Character.valueOf(c));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       }
/*     */       else {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 322 */         if (!flag) {
/*     */           
/* 324 */           String nm = s[idx].trim().toLowerCase();
/* 325 */           int dmg = 0;
/*     */           
/* 327 */           if (idx + 1 < s.length && isNumber(s[idx + 1])) {
/*     */             
/* 329 */             idx++;
/* 330 */             dmg = Integer.parseInt(s[idx]);
/*     */           } 
/*     */           
/* 333 */           if (isNumber(nm))
/*     */           {
/* 335 */             return null;
/*     */           }
/*     */           
/* 338 */           rcp.add(new ItemStack(W_Item.getItemByName(nm), 1, dmg));
/*     */         } 
/* 340 */         flag = false;
/*     */       } 
/*     */       
/* 343 */       isChar = !isChar;
/*     */     } 
/*     */     
/* 346 */     Object[] recipe = new Object[rcp.size()];
/*     */     
/* 348 */     for (int j = 0; j < recipe.length; j++)
/*     */     {
/* 350 */       recipe[j] = rcp.get(j);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 357 */       r = MCH_Recipes.addShapedRecipe(name, new ItemStack(item, createNum), recipe);
/*     */     }
/* 359 */     catch (Exception e) {
/*     */       
/* 361 */       MCH_Utils.logger().warn(e.getMessage() + ", name:" + name);
/* 362 */       return null;
/*     */     } 
/*     */ 
/*     */     
/* 366 */     for (int k = 0; k < r.field_77574_d.size(); k++) {
/*     */ 
/*     */       
/* 369 */       if (r.field_77574_d.get(k) != Ingredient.field_193370_a)
/*     */       {
/*     */ 
/*     */         
/* 373 */         if (Arrays.<ItemStack>stream(((Ingredient)r.field_77574_d.get(k)).func_193365_a()).anyMatch(stack -> (stack.func_77973_b() == null)))
/*     */         {
/* 375 */           throw new RuntimeException("Error: Invalid ShapedRecipes! " + item + " : " + data);
/*     */         }
/*     */       }
/*     */     } 
/* 379 */     return (IRecipe)r;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static IRecipe addShapelessRecipe(String name, Item item, String data) {
/* 386 */     ArrayList<Object> rcp = new ArrayList();
/* 387 */     String[] s = data.split("\\s*,\\s*");
/*     */     
/* 389 */     if (s.length < 1)
/*     */     {
/* 391 */       return null;
/*     */     }
/*     */     
/* 394 */     int start = 0;
/* 395 */     int createNum = 1;
/*     */     
/* 397 */     if (isNumber(s[0]))
/*     */     {
/*     */       
/* 400 */       if (createNum <= 0)
/*     */       {
/* 402 */         createNum = 1;
/*     */       }
/*     */     }
/*     */     
/* 406 */     for (int idx = start; idx < s.length; idx++) {
/*     */       
/* 408 */       if (s[idx].length() <= 0)
/*     */       {
/* 410 */         return null;
/*     */       }
/*     */       
/* 413 */       String nm = s[idx].trim().toLowerCase();
/* 414 */       int dmg = 0;
/*     */       
/* 416 */       if (idx + 1 < s.length && isNumber(s[idx + 1])) {
/*     */         
/* 418 */         idx++;
/* 419 */         dmg = Integer.parseInt(s[idx]);
/*     */       } 
/*     */       
/* 422 */       if (isNumber(nm)) {
/*     */         
/* 424 */         int n = Integer.parseInt(nm);
/*     */         
/* 426 */         if (n <= 255) {
/*     */           
/* 428 */           rcp.add(new ItemStack(W_Block.getBlockById(n), 1, dmg));
/*     */         }
/* 430 */         else if (n <= 511) {
/*     */           
/* 432 */           rcp.add(new ItemStack(W_Item.getItemById(n), 1, dmg));
/*     */         }
/* 434 */         else if (n <= 2255) {
/*     */           
/* 436 */           rcp.add(new ItemStack(W_Block.getBlockById(n), 1, dmg));
/*     */         }
/* 438 */         else if (n <= 2267) {
/*     */           
/* 440 */           rcp.add(new ItemStack(W_Item.getItemById(n), 1, dmg));
/*     */         }
/* 442 */         else if (n <= 4095) {
/*     */           
/* 444 */           rcp.add(new ItemStack(W_Block.getBlockById(n), 1, dmg));
/*     */         }
/* 446 */         else if (n <= 31999) {
/* 447 */           rcp.add(new ItemStack(W_Item.getItemById(n + 256), 1, dmg));
/*     */         } 
/*     */       } else {
/*     */         
/* 451 */         rcp.add(new ItemStack(W_Item.getItemByName(nm), 1, dmg));
/*     */       } 
/*     */     } 
/*     */     
/* 455 */     Object[] recipe = new Object[rcp.size()];
/*     */     
/* 457 */     for (int i = 0; i < recipe.length; i++)
/*     */     {
/* 459 */       recipe[i] = rcp.get(i);
/*     */     }
/*     */     
/* 462 */     ShapelessRecipes r = getShapelessRecipe(new ItemStack(item, createNum), recipe);
/*     */     
/* 464 */     for (int j = 0; j < r.field_77579_b.size(); j++) {
/*     */ 
/*     */       
/* 467 */       Ingredient ingredient = (Ingredient)r.field_77579_b.get(j);
/*     */       
/* 469 */       if (Arrays.<ItemStack>stream(ingredient.func_193365_a()).anyMatch(stack -> (stack.func_77973_b() == null)))
/*     */       {
/* 471 */         throw new RuntimeException("Error: Invalid ShapelessRecipes! " + item + " : " + data);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 476 */     MCH_Recipes.register(name, (IRecipe)r);
/*     */     
/* 478 */     return (IRecipe)r;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static ShapelessRecipes getShapelessRecipe(ItemStack par1ItemStack, Object... par2ArrayOfObj) {
/* 484 */     NonNullList<Ingredient> list = NonNullList.func_191196_a();
/* 485 */     Object[] aobject = par2ArrayOfObj;
/* 486 */     int i = par2ArrayOfObj.length;
/*     */     
/* 488 */     for (int j = 0; j < i; j++) {
/*     */       
/* 490 */       Object object1 = aobject[j];
/*     */       
/* 492 */       if (object1 instanceof ItemStack) {
/*     */ 
/*     */         
/* 495 */         list.add(Ingredient.func_193369_a(new ItemStack[] { ((ItemStack)object1).func_77946_l() }));
/*     */       }
/* 497 */       else if (object1 instanceof Item) {
/*     */ 
/*     */         
/* 500 */         list.add(Ingredient.func_193369_a(new ItemStack[] { new ItemStack((Item)object1) }));
/*     */       }
/*     */       else {
/*     */         
/* 504 */         if (!(object1 instanceof Block))
/*     */         {
/* 506 */           throw new RuntimeException("Invalid shapeless recipy!");
/*     */         }
/*     */ 
/*     */         
/* 510 */         list.add(Ingredient.func_193369_a(new ItemStack[] { new ItemStack((Block)object1) }));
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 515 */     return new ShapelessRecipes("", par1ItemStack, list);
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isNumber(@Nullable String s) {
/* 520 */     if (s == null || s.isEmpty()) {
/* 521 */       return false;
/*     */     }
/* 523 */     byte[] buf = s.getBytes();
/*     */     
/* 525 */     for (byte b : buf) {
/* 526 */       if (b < 48 || b > 57)
/* 527 */         return false; 
/*     */     } 
/* 529 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\MCH_ItemRecipe.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */