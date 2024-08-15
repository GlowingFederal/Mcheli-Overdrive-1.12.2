/*     */ package mcheli;
/*     */ 
/*     */ import net.minecraft.inventory.InventoryCrafting;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.item.crafting.IRecipe;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.registries.IForgeRegistryEntry;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MCH_RecipeFuel
/*     */   extends IForgeRegistryEntry.Impl<IRecipe>
/*     */   implements IRecipe
/*     */ {
/*     */   public boolean func_77569_a(InventoryCrafting inv, World var2) {
/*  22 */     int jcnt = 0;
/*  23 */     int ccnt = 0;
/*     */     
/*  25 */     for (int i = 0; i < inv.func_70302_i_(); i++) {
/*     */       
/*  27 */       ItemStack is = inv.func_70301_a(i);
/*     */ 
/*     */       
/*  30 */       if (!is.func_190926_b())
/*     */       {
/*  32 */         if (is.func_77973_b() instanceof mcheli.aircraft.MCH_ItemFuel) {
/*     */           
/*  34 */           if (is.func_77960_j() == 0)
/*     */           {
/*  36 */             return false;
/*     */           }
/*     */           
/*  39 */           jcnt++;
/*     */           
/*  41 */           if (jcnt > 1)
/*     */           {
/*  43 */             return false;
/*     */           
/*     */           }
/*     */         }
/*  47 */         else if (is.func_77973_b() instanceof net.minecraft.item.ItemCoal && is.func_190916_E() > 0) {
/*     */           
/*  49 */           ccnt++;
/*     */         }
/*     */         else {
/*     */           
/*  53 */           return false;
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/*  58 */     return (jcnt == 1 && ccnt > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack func_77572_b(InventoryCrafting inv) {
/*  64 */     ItemStack output = new ItemStack((Item)MCH_MOD.itemFuel);
/*     */     int i;
/*  66 */     for (i = 0; i < inv.func_70302_i_(); i++) {
/*     */       
/*  68 */       ItemStack is = inv.func_70301_a(i);
/*     */ 
/*     */       
/*  71 */       if (!is.func_190926_b() && is.func_77973_b() instanceof mcheli.aircraft.MCH_ItemFuel) {
/*     */         
/*  73 */         output.func_77964_b(is.func_77960_j());
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/*  78 */     for (i = 0; i < inv.func_70302_i_(); i++) {
/*     */       
/*  80 */       ItemStack is = inv.func_70301_a(i);
/*     */ 
/*     */       
/*  83 */       if (!is.func_190926_b() && is.func_77973_b() instanceof net.minecraft.item.ItemCoal) {
/*     */         
/*  85 */         int sp = 100;
/*  86 */         if (is.func_77960_j() == 1)
/*     */         {
/*  88 */           sp = 75;
/*     */         }
/*  90 */         if (output.func_77960_j() > sp) {
/*     */           
/*  92 */           output.func_77964_b(output.func_77960_j() - sp);
/*     */         }
/*     */         else {
/*     */           
/*  96 */           output.func_77964_b(0);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 101 */     return output;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_194133_a(int width, int height) {
/* 112 */     return (width >= 3 && height >= 3);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack func_77571_b() {
/* 119 */     return ItemStack.field_190927_a;
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\MCH_RecipeFuel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */