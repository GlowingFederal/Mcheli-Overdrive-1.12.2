/*    */ package mcheli.gui;
/*    */ 
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.inventory.Container;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MCH_ConfigGuiContainer
/*    */   extends Container
/*    */ {
/*    */   public final EntityPlayer player;
/*    */   
/*    */   public MCH_ConfigGuiContainer(EntityPlayer player) {
/* 19 */     this.player = player;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void func_75142_b() {
/* 25 */     super.func_75142_b();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean func_75145_c(EntityPlayer player) {
/* 31 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ItemStack func_82846_b(EntityPlayer par1EntityPlayer, int par2) {
/* 38 */     return ItemStack.field_190927_a;
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\gui\MCH_ConfigGuiContainer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */