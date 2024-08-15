/*     */ package mcheli.__helper.client;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import mcheli.MCH_IRecipeList;
/*     */ import mcheli.MCH_ItemRecipe;
/*     */ import mcheli.__helper.MCH_Utils;
/*     */ import mcheli.helicopter.MCH_HeliInfoManager;
/*     */ import mcheli.plane.MCP_PlaneInfoManager;
/*     */ import mcheli.tank.MCH_TankInfoManager;
/*     */ import mcheli.vehicle.MCH_VehicleInfoManager;
/*     */ import net.minecraft.client.resources.IResource;
/*     */ import net.minecraft.client.resources.IResourceManager;
/*     */ import net.minecraft.item.crafting.IRecipe;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @SideOnly(Side.CLIENT)
/*     */ public class RecipeDescriptionManager
/*     */ {
/*  35 */   private static final Map<ResourceLocation, DescriptionInfo> INFO_TABLE = Maps.newHashMap();
/*     */ 
/*     */   
/*     */   public static void registerDescriptionInfos(IResourceManager resourceManager) {
/*  39 */     INFO_TABLE.clear();
/*     */     
/*  41 */     registerDescriptions(resourceManager, (MCH_IRecipeList)MCH_ItemRecipe.getInstance());
/*  42 */     registerDescriptions(resourceManager, (MCH_IRecipeList)MCH_HeliInfoManager.getInstance());
/*  43 */     registerDescriptions(resourceManager, (MCH_IRecipeList)MCP_PlaneInfoManager.getInstance());
/*  44 */     registerDescriptions(resourceManager, (MCH_IRecipeList)MCH_TankInfoManager.getInstance());
/*  45 */     registerDescriptions(resourceManager, (MCH_IRecipeList)MCH_VehicleInfoManager.getInstance());
/*     */   }
/*     */ 
/*     */   
/*     */   private static void registerDescriptions(IResourceManager resourceManager, MCH_IRecipeList recipeList) {
/*  50 */     for (int i = 0; i < recipeList.getRecipeListSize(); i++) {
/*     */       
/*  52 */       IRecipe recipe = recipeList.getRecipe(i);
/*  53 */       DescriptionInfo info = createDescriptionInfo(resourceManager, recipe);
/*  54 */       ResourceLocation registryName = recipe.getRegistryName();
/*     */       
/*  56 */       INFO_TABLE.put(registryName, info);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static DescriptionInfo createDescriptionInfo(IResourceManager resourceManager, IRecipe recipe) {
/*  62 */     List<ResourceLocation> textures = Lists.newLinkedList();
/*     */     
/*  64 */     for (int i = 0; i < 20; i++) {
/*     */       
/*  66 */       String itemName = recipe.func_77571_b().func_77977_a();
/*     */       
/*  68 */       if (itemName.startsWith("tile."))
/*     */       {
/*  70 */         itemName = itemName.substring(5);
/*     */       }
/*     */       
/*  73 */       if (itemName.indexOf(":") >= 0)
/*     */       {
/*  75 */         itemName = itemName.substring(itemName.indexOf(":") + 1);
/*     */       }
/*     */       
/*  78 */       String filepath = "textures/drafting_table_desc/" + itemName + "#" + i + ".png";
/*     */       
/*  80 */       try (IResource resource = resourceManager.func_110536_a(MCH_Utils.suffix(filepath))) {
/*     */         
/*  82 */         textures.add(resource.func_177241_a());
/*     */       }
/*  84 */       catch (FileNotFoundException fileNotFoundException) {
/*     */ 
/*     */       
/*  87 */       } catch (IOException e1) {
/*     */         
/*  89 */         e1.printStackTrace();
/*     */       } 
/*     */     } 
/*     */     
/*  93 */     return new DescriptionInfo(textures);
/*     */   }
/*     */ 
/*     */   
/*     */   public static ImmutableList<ResourceLocation> getDescriptionTextures(ResourceLocation recipeRegistryName) {
/*  98 */     return ((DescriptionInfo)INFO_TABLE.getOrDefault(recipeRegistryName, new DescriptionInfo(Collections.emptyList()))).getTextures();
/*     */   }
/*     */ 
/*     */   
/*     */   static class DescriptionInfo
/*     */   {
/*     */     private ImmutableList<ResourceLocation> textures;
/*     */     
/*     */     public DescriptionInfo(List<ResourceLocation> textures) {
/* 107 */       this.textures = ImmutableList.copyOf(textures);
/*     */     }
/*     */ 
/*     */     
/*     */     public ImmutableList<ResourceLocation> getTextures() {
/* 112 */       return this.textures;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\__helper\client\RecipeDescriptionManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */