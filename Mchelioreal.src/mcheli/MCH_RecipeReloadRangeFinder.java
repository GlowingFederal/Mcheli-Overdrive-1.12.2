/*    */ package mcheli;
/*    */ 
/*    */ import net.minecraft.inventory.InventoryCrafting;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.item.crafting.IRecipe;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraftforge.registries.IForgeRegistryEntry;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MCH_RecipeReloadRangeFinder
/*    */   extends IForgeRegistryEntry.Impl<IRecipe>
/*    */   implements IRecipe
/*    */ {
/*    */   public boolean func_77569_a(InventoryCrafting inv, World var2) {
/* 23 */     int jcnt = 0;
/* 24 */     int ccnt = 0;
/*    */     
/* 26 */     for (int i = 0; i < inv.func_70302_i_(); i++) {
/*    */       
/* 28 */       ItemStack is = inv.func_70301_a(i);
/*    */ 
/*    */       
/* 31 */       if (!is.func_190926_b())
/*    */       {
/* 33 */         if (is.func_77973_b() instanceof mcheli.tool.rangefinder.MCH_ItemRangeFinder) {
/*    */           
/* 35 */           if (is.func_77960_j() == 0)
/*    */           {
/* 37 */             return false;
/*    */           }
/*    */           
/* 40 */           jcnt++;
/*    */           
/* 42 */           if (jcnt > 1)
/*    */           {
/* 44 */             return false;
/*    */           
/*    */           }
/*    */         }
/* 48 */         else if (is.func_77973_b() instanceof net.minecraft.item.ItemRedstone && is.func_190916_E() > 0) {
/*    */           
/* 50 */           ccnt++;
/*    */           
/* 52 */           if (ccnt > 1)
/*    */           {
/* 54 */             return false;
/*    */           
/*    */           }
/*    */         }
/*    */         else {
/*    */           
/* 60 */           return false;
/*    */         } 
/*    */       }
/*    */     } 
/*    */     
/* 65 */     return (jcnt == 1 && ccnt > 0);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ItemStack func_77572_b(InventoryCrafting inv) {
/* 71 */     ItemStack output = new ItemStack((Item)MCH_MOD.itemRangeFinder);
/* 72 */     return output;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean func_194133_a(int width, int height) {
/* 83 */     return (width >= 2 && height >= 2);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ItemStack func_77571_b() {
/* 90 */     return ItemStack.field_190927_a;
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\MCH_RecipeReloadRangeFinder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */