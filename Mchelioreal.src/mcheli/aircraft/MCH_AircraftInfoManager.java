/*    */ package mcheli.aircraft;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import mcheli.MCH_IRecipeList;
/*    */ import mcheli.MCH_InfoManagerBase;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.crafting.IRecipe;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class MCH_AircraftInfoManager<T extends MCH_AircraftInfo>
/*    */   extends MCH_InfoManagerBase<T>
/*    */   implements MCH_IRecipeList
/*    */ {
/* 21 */   private List<IRecipe> listItemRecipe = new ArrayList<>();
/*    */ 
/*    */ 
/*    */   
/*    */   public int getRecipeListSize() {
/* 26 */     return this.listItemRecipe.size();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IRecipe getRecipe(int index) {
/* 32 */     return this.listItemRecipe.get(index);
/*    */   }
/*    */ 
/*    */   
/*    */   public void addRecipe(IRecipe recipe, int count, String name, String recipeString) {
/* 37 */     if (recipe == null || recipe.func_77571_b() == null || recipe.func_77571_b().func_77973_b() == null)
/*    */     {
/* 39 */       throw new RuntimeException("[mcheli]Recipe Parameter Error! recipe" + count + " : " + name + ".txt : " + 
/* 40 */           String.valueOf(recipe) + " : " + recipeString);
/*    */     }
/* 42 */     this.listItemRecipe.add(recipe);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public abstract MCH_AircraftInfo getAcInfoFromItem(Item paramItem);
/*    */ 
/*    */   
/*    */   public MCH_AircraftInfo getAcInfoFromItem(IRecipe recipe) {
/* 51 */     if (recipe != null)
/*    */     {
/* 53 */       return getAcInfoFromItem(recipe.func_77571_b().func_77973_b());
/*    */     }
/* 55 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\aircraft\MCH_AircraftInfoManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */