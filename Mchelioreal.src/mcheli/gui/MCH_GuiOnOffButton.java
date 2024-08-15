/*    */ package mcheli.gui;
/*    */ 
/*    */ import mcheli.wrapper.W_GuiButton;
/*    */ import net.minecraft.client.Minecraft;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MCH_GuiOnOffButton
/*    */   extends W_GuiButton
/*    */ {
/*    */   private boolean statOnOff;
/*    */   private final String dispOnOffString;
/*    */   
/*    */   public MCH_GuiOnOffButton(int par1, int par2, int par3, int par4, int par5, String par6Str) {
/* 19 */     super(par1, par2, par3, par4, par5, "");
/* 20 */     this.dispOnOffString = par6Str;
/* 21 */     setOnOff(false);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setOnOff(boolean b) {
/* 26 */     this.statOnOff = b;
/* 27 */     this.field_146126_j = this.dispOnOffString + (getOnOff() ? "ON" : "OFF");
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean getOnOff() {
/* 32 */     return this.statOnOff;
/*    */   }
/*    */ 
/*    */   
/*    */   public void switchOnOff() {
/* 37 */     setOnOff(!getOnOff());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean func_146116_c(Minecraft mc, int x, int y) {
/* 43 */     if (super.func_146116_c(mc, x, y)) {
/*    */       
/* 45 */       switchOnOff();
/* 46 */       return true;
/*    */     } 
/* 48 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\gui\MCH_GuiOnOffButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */