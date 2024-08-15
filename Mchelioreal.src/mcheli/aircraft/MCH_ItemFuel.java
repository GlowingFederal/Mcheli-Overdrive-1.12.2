/*    */ package mcheli.aircraft;
/*    */ 
/*    */ import mcheli.wrapper.W_Item;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.ActionResult;
/*    */ import net.minecraft.util.EnumActionResult;
/*    */ import net.minecraft.util.EnumHand;
/*    */ import net.minecraft.util.NonNullList;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MCH_ItemFuel
/*    */   extends W_Item
/*    */ {
/*    */   public MCH_ItemFuel(int itemID) {
/* 24 */     super(itemID);
/* 25 */     func_77656_e(600);
/* 26 */     func_77625_d(1);
/* 27 */     setNoRepair();
/* 28 */     func_77664_n();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ActionResult<ItemStack> func_77659_a(World world, EntityPlayer player, EnumHand handIn) {
/* 35 */     ItemStack stack = player.func_184586_b(handIn);
/*    */ 
/*    */     
/* 38 */     if (!world.field_72995_K && stack.func_77951_h() && !player.field_71075_bZ.field_75098_d) {
/*    */       
/* 40 */       refuel(stack, player, 1);
/* 41 */       refuel(stack, player, 0);
/* 42 */       return new ActionResult(EnumActionResult.SUCCESS, stack);
/*    */     } 
/*    */     
/* 45 */     return new ActionResult(EnumActionResult.PASS, stack);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private void refuel(ItemStack stack, EntityPlayer player, int coalType) {
/* 51 */     NonNullList<ItemStack> nonNullList = player.field_71071_by.field_70462_a;
/*    */ 
/*    */     
/* 54 */     for (int i = 0; i < nonNullList.size(); i++) {
/*    */ 
/*    */       
/* 57 */       ItemStack is = nonNullList.get(i);
/*    */ 
/*    */       
/* 60 */       if (!is.func_190926_b() && is.func_77973_b() instanceof net.minecraft.item.ItemCoal && is.func_77960_j() == coalType) {
/*    */ 
/*    */         
/* 63 */         for (int j = 0; is.func_190916_E() > 0 && stack.func_77951_h() && j < 64; j++) {
/*    */           
/* 65 */           int damage = stack.func_77960_j() - ((coalType == 1) ? 75 : 100);
/*    */           
/* 67 */           if (damage < 0) {
/* 68 */             damage = 0;
/*    */           }
/* 70 */           stack.func_77964_b(damage);
/*    */ 
/*    */           
/* 73 */           is.func_190918_g(1);
/*    */         } 
/*    */ 
/*    */ 
/*    */         
/* 78 */         if (is.func_190916_E() <= 0)
/* 79 */           nonNullList.set(i, ItemStack.field_190927_a); 
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\aircraft\MCH_ItemFuel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */