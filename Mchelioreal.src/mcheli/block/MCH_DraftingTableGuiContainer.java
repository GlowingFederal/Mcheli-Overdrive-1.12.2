/*     */ package mcheli.block;
/*     */ 
/*     */ import javax.annotation.Nullable;
/*     */ import mcheli.MCH_IRecipeList;
/*     */ import mcheli.MCH_Lib;
/*     */ import mcheli.MCH_MOD;
/*     */ import mcheli.__helper.MCH_Recipes;
/*     */ import mcheli.wrapper.W_Block;
/*     */ import mcheli.wrapper.W_EntityPlayer;
/*     */ import mcheli.wrapper.W_WorldFunc;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.inventory.InventoryCraftResult;
/*     */ import net.minecraft.inventory.Slot;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.item.crafting.IRecipe;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MCH_DraftingTableGuiContainer
/*     */   extends Container
/*     */ {
/*     */   public final EntityPlayer player;
/*     */   public final int posX;
/*     */   public final int posY;
/*     */   public final int posZ;
/*     */   public final int outputSlotIndex;
/*  34 */   private IInventory outputSlot = (IInventory)new InventoryCraftResult();
/*     */ 
/*     */   
/*     */   public MCH_DraftingTableGuiContainer(EntityPlayer player, int posX, int posY, int posZ) {
/*  38 */     this.player = player;
/*  39 */     this.posX = posX;
/*  40 */     this.posY = posY;
/*  41 */     this.posZ = posZ;
/*     */     
/*  43 */     for (int y = 0; y < 3; y++) {
/*     */       
/*  45 */       for (int i = 0; i < 9; i++)
/*     */       {
/*  47 */         func_75146_a(new Slot((IInventory)player.field_71071_by, 9 + i + y * 9, 30 + i * 18, 140 + y * 18));
/*     */       }
/*     */     } 
/*     */     
/*  51 */     for (int x = 0; x < 9; x++)
/*     */     {
/*  53 */       func_75146_a(new Slot((IInventory)player.field_71071_by, x, 30 + x * 18, 198));
/*     */     }
/*     */     
/*  56 */     this.outputSlotIndex = this.field_75153_a.size();
/*     */     
/*  58 */     Slot a = new Slot(this.outputSlot, this.outputSlotIndex, 178, 90)
/*     */       {
/*     */         
/*     */         public boolean func_75214_a(ItemStack stack)
/*     */         {
/*  63 */           return false;
/*     */         }
/*     */       };
/*     */     
/*  67 */     func_75146_a(a);
/*     */     
/*  69 */     MCH_Lib.DbgLog(player.field_70170_p, "MCH_DraftingTableGuiContainer.MCH_DraftingTableGuiContainer", new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_75142_b() {
/*  75 */     super.func_75142_b();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_75145_c(EntityPlayer player) {
/*  81 */     Block block = W_WorldFunc.getBlock(player.field_70170_p, this.posX, this.posY, this.posZ);
/*     */     
/*  83 */     if (W_Block.isEqual(block, (Block)MCH_MOD.blockDraftingTable) || 
/*  84 */       W_Block.isEqual(block, (Block)MCH_MOD.blockDraftingTableLit))
/*     */     {
/*  86 */       return (player.func_70092_e(this.posX, this.posY, this.posZ) <= 144.0D);
/*     */     }
/*     */     
/*  89 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack func_82846_b(EntityPlayer player, int slotIndex) {
/*  96 */     ItemStack itemstack = ItemStack.field_190927_a;
/*  97 */     Slot slot = this.field_75151_b.get(slotIndex);
/*     */     
/*  99 */     if (slot != null && slot.func_75216_d()) {
/*     */       
/* 101 */       ItemStack itemstack1 = slot.func_75211_c();
/* 102 */       itemstack = itemstack1.func_77946_l();
/*     */       
/* 104 */       if (slotIndex == this.outputSlotIndex) {
/*     */         
/* 106 */         if (!func_75135_a(itemstack1, 0, 36, true))
/*     */         {
/*     */           
/* 109 */           return ItemStack.field_190927_a;
/*     */         }
/*     */         
/* 112 */         slot.func_75220_a(itemstack1, itemstack);
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 117 */         return ItemStack.field_190927_a;
/*     */       } 
/*     */ 
/*     */       
/* 121 */       if (itemstack1.func_190916_E() == 0) {
/*     */ 
/*     */         
/* 124 */         slot.func_75215_d(ItemStack.field_190927_a);
/*     */       }
/*     */       else {
/*     */         
/* 128 */         slot.func_75218_e();
/*     */       } 
/*     */ 
/*     */       
/* 132 */       if (itemstack1.func_190916_E() == itemstack.func_190916_E())
/*     */       {
/*     */         
/* 135 */         return ItemStack.field_190927_a;
/*     */       }
/*     */ 
/*     */       
/* 139 */       slot.func_190901_a(player, itemstack1);
/*     */     } 
/*     */     
/* 142 */     return itemstack;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_75134_a(EntityPlayer player) {
/* 148 */     super.func_75134_a(player);
/*     */     
/* 150 */     if (!player.field_70170_p.field_72995_K) {
/*     */       
/* 152 */       ItemStack itemstack = func_75139_a(this.outputSlotIndex).func_75211_c();
/*     */ 
/*     */       
/* 155 */       if (!itemstack.func_190926_b())
/*     */       {
/* 157 */         W_EntityPlayer.dropPlayerItemWithRandomChoice(player, itemstack, false, false);
/*     */       }
/*     */     } 
/*     */     
/* 161 */     MCH_Lib.DbgLog(player.field_70170_p, "MCH_DraftingTableGuiContainer.onContainerClosed", new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void createRecipeItem(@Nullable IRecipe recipe) {
/* 167 */     boolean isCreativeMode = this.player.field_71075_bZ.field_75098_d;
/*     */     
/* 169 */     if (func_75139_a(this.outputSlotIndex).func_75216_d() && !isCreativeMode) {
/*     */       
/* 171 */       MCH_Lib.DbgLog(this.player.field_70170_p, "MCH_DraftingTableGuiContainer.createRecipeItem:OutputSlot is not empty", new Object[0]);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 183 */     if (recipe == null) {
/*     */ 
/*     */       
/* 186 */       MCH_Lib.DbgLog(this.player.field_70170_p, "Error:MCH_DraftingTableGuiContainer.createRecipeItem:recipe is null : ", new Object[0]);
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 191 */     boolean result = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 208 */     if (recipe != null)
/*     */     {
/*     */       
/* 211 */       if (isCreativeMode || MCH_Recipes.canCraft(this.player, recipe)) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 226 */         if (!isCreativeMode)
/*     */         {
/* 228 */           MCH_Recipes.consumeInventory(this.player, recipe);
/*     */         }
/*     */         
/* 231 */         func_75139_a(this.outputSlotIndex).func_75215_d(recipe.func_77571_b().func_77946_l());
/* 232 */         result = true;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 237 */     MCH_Lib.DbgLog(this.player.field_70170_p, "MCH_DraftingTableGuiContainer:Result=" + result + ":Recipe=" + recipe
/* 238 */         .getRegistryName(), new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int searchRecipeFromList(MCH_IRecipeList list, ItemStack item) {
/* 273 */     for (int i = 0; i < list.getRecipeListSize(); i++) {
/*     */       
/* 275 */       if (list.getRecipe(i).func_77571_b().func_77969_a(item))
/*     */       {
/* 277 */         return i;
/*     */       }
/*     */     } 
/* 280 */     return -1;
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\block\MCH_DraftingTableGuiContainer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */