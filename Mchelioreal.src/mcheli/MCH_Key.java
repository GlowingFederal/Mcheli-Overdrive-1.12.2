/*    */ package mcheli;
/*    */ 
/*    */ import mcheli.wrapper.W_KeyBinding;
/*    */ import net.minecraft.client.settings.KeyBinding;
/*    */ import org.lwjgl.input.Keyboard;
/*    */ import org.lwjgl.input.Mouse;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MCH_Key
/*    */ {
/*    */   public int key;
/*    */   private boolean isPress;
/*    */   private boolean isBeforePress;
/*    */   
/*    */   public MCH_Key(int k) {
/* 23 */     this.key = k;
/* 24 */     this.isPress = false;
/* 25 */     this.isBeforePress = false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isKeyDown() {
/* 30 */     return (!this.isBeforePress && this.isPress == true);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isKeyPress() {
/* 35 */     return this.isPress;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isKeyUp() {
/* 40 */     return (this.isBeforePress == true && !this.isPress);
/*    */   }
/*    */ 
/*    */   
/*    */   public void update() {
/* 45 */     if (this.key == 0) {
/*    */       return;
/*    */     }
/* 48 */     this.isBeforePress = this.isPress;
/*    */     
/* 50 */     if (this.key >= 0) {
/*    */       
/* 52 */       this.isPress = Keyboard.isKeyDown(this.key);
/*    */     }
/*    */     else {
/*    */       
/* 56 */       this.isPress = Mouse.isButtonDown(this.key + 100);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public static boolean isKeyDown(int key) {
/* 62 */     if (key > 0) {
/* 63 */       return Keyboard.isKeyDown(key);
/*    */     }
/* 65 */     if (key < 0) {
/* 66 */       return Mouse.isButtonDown(key + 100);
/*    */     }
/* 68 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public static boolean isKeyDown(KeyBinding keyBind) {
/* 73 */     return isKeyDown(W_KeyBinding.getKeyCode(keyBind));
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\MCH_Key.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */