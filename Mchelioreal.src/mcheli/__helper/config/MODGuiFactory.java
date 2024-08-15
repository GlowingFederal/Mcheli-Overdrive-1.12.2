/*    */ package mcheli.__helper.config;
/*    */ 
/*    */ import net.minecraft.client.gui.GuiScreen;
/*    */ import net.minecraftforge.fml.client.DefaultGuiFactory;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MODGuiFactory
/*    */   extends DefaultGuiFactory
/*    */ {
/*    */   public MODGuiFactory() {
/* 16 */     super("mcheli", "MC Helicopter MOD");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public GuiScreen createConfigGui(GuiScreen parentScreen) {
/* 22 */     return new GuiMODConfigTop(parentScreen);
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\__helper\config\MODGuiFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */