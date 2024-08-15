/*    */ package mcheli.wrapper;
/*    */ 
/*    */ import net.minecraft.client.gui.FontRenderer;
/*    */ import net.minecraft.client.gui.inventory.GuiContainer;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.inventory.Container;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.item.crafting.Ingredient;
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class W_GuiContainer
/*    */   extends GuiContainer
/*    */ {
/*    */   private float time;
/*    */   
/*    */   public W_GuiContainer(Container par1Container) {
/* 23 */     super(par1Container);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void drawItemStack(ItemStack item, int x, int y) {
/* 29 */     if (item.func_190926_b()) {
/*    */       return;
/*    */     }
/* 32 */     if (item.func_77973_b() == null) {
/*    */       return;
/*    */     }
/* 35 */     FontRenderer font = item.func_77973_b().getFontRenderer(item);
/*    */     
/* 37 */     if (font == null) {
/* 38 */       font = this.field_146289_q;
/*    */     }
/*    */ 
/*    */     
/* 42 */     GlStateManager.func_179126_j();
/* 43 */     GlStateManager.func_179140_f();
/*    */ 
/*    */     
/* 46 */     this.field_146296_j.func_180450_b(item, x, y);
/* 47 */     this.field_146296_j.func_180453_a(font, item, x, y, null);
/* 48 */     this.field_73735_i = 0.0F;
/* 49 */     this.field_146296_j.field_77023_b = 0.0F;
/*    */   }
/*    */ 
/*    */   
/*    */   public void drawIngredient(Ingredient ingredient, int x, int y) {
/* 54 */     if (ingredient != Ingredient.field_193370_a) {
/*    */       
/* 56 */       ItemStack[] itemstacks = ingredient.func_193365_a();
/* 57 */       int index = MathHelper.func_76141_d(this.time / 20.0F) % itemstacks.length;
/*    */       
/* 59 */       drawItemStack(itemstacks[index], x, y);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void drawString(String s, int x, int y, int color) {
/* 65 */     func_73731_b(this.field_146289_q, s, x, y, color);
/*    */   }
/*    */ 
/*    */   
/*    */   public void drawCenteredString(String s, int x, int y, int color) {
/* 70 */     func_73732_a(this.field_146289_q, s, x, y, color);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getStringWidth(String s) {
/* 75 */     return this.field_146289_q.func_78256_a(s);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void func_73863_a(int mouseX, int mouseY, float partialTicks) {
/* 81 */     this.time += partialTicks;
/*    */     
/* 83 */     super.func_73863_a(mouseX, mouseY, partialTicks);
/*    */   }
/*    */ 
/*    */   
/*    */   public float getAnimationTime() {
/* 88 */     return this.time;
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\wrapper\W_GuiContainer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */