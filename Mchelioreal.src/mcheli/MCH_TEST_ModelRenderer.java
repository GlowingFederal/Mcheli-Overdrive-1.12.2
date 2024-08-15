/*    */ package mcheli;
/*    */ 
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MCH_TEST_ModelRenderer
/*    */   extends ModelRenderer
/*    */ {
/*    */   public MCH_TEST_ModelRenderer(ModelBase par1ModelBase) {
/* 18 */     super(par1ModelBase);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void func_78785_a(float par1) {
/* 24 */     GL11.glPushMatrix();
/* 25 */     GL11.glScaled(0.2D, -0.2D, 0.2D);
/* 26 */     MCH_ModelManager.render("helicopters", "ah-64");
/* 27 */     GL11.glPopMatrix();
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\MCH_TEST_ModelRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */