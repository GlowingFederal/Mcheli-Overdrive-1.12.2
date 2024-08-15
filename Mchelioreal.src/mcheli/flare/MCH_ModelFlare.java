/*    */ package mcheli.flare;
/*    */ 
/*    */ import mcheli.wrapper.W_ModelBase;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class MCH_ModelFlare
/*    */   extends W_ModelBase
/*    */ {
/*    */   public ModelRenderer model;
/*    */   
/*    */   public MCH_ModelFlare() {
/* 22 */     this.model = (new ModelRenderer((ModelBase)this, 0, 0)).func_78787_b(4, 4);
/* 23 */     this.model.func_78790_a(-2.0F, -2.0F, -2.0F, 4, 4, 4, 0.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   public void renderModel(double yaw, double pitch, float par7) {
/* 28 */     this.model.func_78785_a(par7);
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\flare\MCH_ModelFlare.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */