/*    */ package mcheli.gui;
/*    */ 
/*    */ import mcheli.MCH_ConfigPrm;
/*    */ import mcheli.MCH_KeyName;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.gui.GuiButton;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MCH_GuiListItemKeyBind
/*    */   extends MCH_GuiListItem
/*    */ {
/*    */   public String displayString;
/*    */   public GuiButton button;
/*    */   public GuiButton buttonReset;
/*    */   public int keycode;
/*    */   public final int defaultKeycode;
/*    */   public MCH_ConfigPrm config;
/*    */   public GuiButton lastPushButton;
/*    */   
/*    */   public MCH_GuiListItemKeyBind(int id, int idReset, int posX, String dispStr, MCH_ConfigPrm prm) {
/* 26 */     this.displayString = dispStr;
/* 27 */     this.defaultKeycode = prm.prmIntDefault;
/* 28 */     this.button = new GuiButton(id, posX + 160, 0, 70, 20, "");
/* 29 */     this.buttonReset = new GuiButton(idReset, posX + 240, 0, 40, 20, "Reset");
/* 30 */     this.config = prm;
/* 31 */     this.lastPushButton = null;
/*    */     
/* 33 */     setKeycode(prm.prmInt);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void mouseReleased(int x, int y) {
/* 39 */     this.button.func_146118_a(x, y);
/* 40 */     this.buttonReset.func_146118_a(x, y);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean mousePressed(Minecraft mc, int x, int y) {
/* 46 */     if (this.button.func_146116_c(mc, x, y)) {
/*    */       
/* 48 */       this.lastPushButton = this.button;
/*    */       
/* 50 */       return true;
/*    */     } 
/* 52 */     if (this.buttonReset.func_146116_c(mc, x, y)) {
/*    */       
/* 54 */       this.lastPushButton = this.buttonReset;
/*    */       
/* 56 */       return true;
/*    */     } 
/* 58 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void draw(Minecraft mc, int mouseX, int mouseY, int posX, int posY, float partialTicks) {
/* 65 */     int y = 6;
/* 66 */     this.button.func_73731_b(mc.field_71466_p, this.displayString, posX + 10, posY + y, -1);
/* 67 */     this.button.field_146129_i = posY;
/* 68 */     this.button.func_191745_a(mc, mouseX, mouseY, partialTicks);
/* 69 */     this.buttonReset.field_146124_l = (this.keycode != this.defaultKeycode);
/* 70 */     this.buttonReset.field_146129_i = posY;
/* 71 */     this.buttonReset.func_191745_a(mc, mouseX, mouseY, partialTicks);
/*    */   }
/*    */ 
/*    */   
/*    */   public void applyKeycode() {
/* 76 */     this.config.setPrm(this.keycode);
/*    */   }
/*    */ 
/*    */   
/*    */   public void resetKeycode() {
/* 81 */     setKeycode(this.defaultKeycode);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setKeycode(int k) {
/* 86 */     if (k != 0 && !MCH_KeyName.getDescOrName(k).isEmpty()) {
/*    */       
/* 88 */       this.keycode = k;
/* 89 */       this.button.field_146126_j = MCH_KeyName.getDescOrName(k);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\gui\MCH_GuiListItemKeyBind.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */