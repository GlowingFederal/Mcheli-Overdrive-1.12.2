/*     */ package mcheli.__helper;
/*     */ 
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.Set;
/*     */ import mcheli.MCH_ItemRecipe;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.item.crafting.IRecipe;
/*     */ import net.minecraft.item.crafting.Ingredient;
/*     */ import net.minecraft.item.crafting.ShapedRecipes;
/*     */ import net.minecraftforge.common.crafting.CraftingHelper;
/*     */ import net.minecraftforge.event.RegistryEvent;
/*     */ import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ import net.minecraftforge.registries.IForgeRegistryEntry;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @EventBusSubscriber(modid = "mcheli")
/*     */ public class MCH_Recipes
/*     */ {
/*  28 */   private static final Set<IRecipe> registryWrapper = Sets.newLinkedHashSet();
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   static void onRecipeRegisterEvent(RegistryEvent.Register<IRecipe> event) {
/*  33 */     MCH_ItemRecipe.registerItemRecipe(event.getRegistry());
/*     */     
/*  35 */     for (IRecipe recipe : registryWrapper)
/*     */     {
/*  37 */       event.getRegistry().register((IForgeRegistryEntry)recipe);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static void register(String name, IRecipe recipe) {
/*  43 */     registryWrapper.add(recipe.setRegistryName(MCH_Utils.suffix(name)));
/*     */   }
/*     */ 
/*     */   
/*     */   public static ShapedRecipes addShapedRecipe(String name, ItemStack output, Object... params) {
/*  48 */     CraftingHelper.ShapedPrimer primer = CraftingHelper.parseShaped(params);
/*  49 */     ShapedRecipes recipe = new ShapedRecipes("", primer.width, primer.height, primer.input, output);
/*     */     
/*  51 */     register(name, (IRecipe)recipe);
/*     */     
/*  53 */     return recipe;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean canCraft(EntityPlayer player, IRecipe recipe) {
/*  58 */     for (Ingredient ingredient : recipe.func_192400_c()) {
/*     */       
/*  60 */       if (ingredient == Ingredient.field_193370_a) {
/*     */         continue;
/*     */       }
/*     */ 
/*     */       
/*  65 */       boolean flag = false;
/*     */       
/*  67 */       for (ItemStack itemstack : player.field_71071_by.field_70462_a) {
/*     */         
/*  69 */         if (ingredient.apply(itemstack)) {
/*     */           
/*  71 */           flag = true;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*  76 */       if (!flag)
/*     */       {
/*  78 */         return false;
/*     */       }
/*     */     } 
/*     */     
/*  82 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean consumeInventory(EntityPlayer player, IRecipe recipe) {
/*  87 */     for (Ingredient ingredient : recipe.func_192400_c()) {
/*     */       
/*  89 */       if (ingredient == Ingredient.field_193370_a) {
/*     */         continue;
/*     */       }
/*     */ 
/*     */       
/*  94 */       int i = 0;
/*  95 */       boolean flag = false;
/*     */       
/*  97 */       for (ItemStack itemstack : player.field_71071_by.field_70462_a) {
/*     */         
/*  99 */         if (ingredient.apply(itemstack)) {
/*     */           
/* 101 */           player.field_71071_by.func_70298_a(i, 1);
/* 102 */           flag = true;
/*     */           
/*     */           break;
/*     */         } 
/* 106 */         i++;
/*     */       } 
/*     */       
/* 109 */       if (!flag)
/*     */       {
/* 111 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 115 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\__helper\MCH_Recipes.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */