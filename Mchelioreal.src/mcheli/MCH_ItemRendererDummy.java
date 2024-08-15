/*    */ package mcheli;
/*    */ 
/*    */ import mcheli.wrapper.W_EntityRenderer;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.renderer.ItemRenderer;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class MCH_ItemRendererDummy
/*    */   extends ItemRenderer
/*    */ {
/*    */   protected static Minecraft mc;
/*    */   protected static ItemRenderer backupItemRenderer;
/*    */   protected static MCH_ItemRendererDummy instance;
/*    */   
/*    */   public MCH_ItemRendererDummy(Minecraft par1Minecraft) {
/* 27 */     super(par1Minecraft);
/*    */     
/* 29 */     mc = par1Minecraft;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void func_78440_a(float par1) {
/* 35 */     if (mc.field_71439_g == null) {
/*    */       
/* 37 */       super.func_78440_a(par1);
/*    */     }
/* 39 */     else if (!(mc.field_71439_g.func_184187_bx() instanceof mcheli.aircraft.MCH_EntityAircraft) && 
/* 40 */       !(mc.field_71439_g.func_184187_bx() instanceof mcheli.uav.MCH_EntityUavStation) && 
/* 41 */       !(mc.field_71439_g.func_184187_bx() instanceof mcheli.gltd.MCH_EntityGLTD)) {
/*    */       
/* 43 */       super.func_78440_a(par1);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public static void enableDummyItemRenderer() {
/* 49 */     if (instance == null) {
/* 50 */       instance = new MCH_ItemRendererDummy(Minecraft.func_71410_x());
/*    */     }
/* 52 */     if (!(mc.field_71460_t.field_78516_c instanceof MCH_ItemRendererDummy))
/*    */     {
/* 54 */       backupItemRenderer = mc.field_71460_t.field_78516_c;
/*    */     }
/*    */     
/* 57 */     W_EntityRenderer.setItemRenderer(mc, instance);
/*    */   }
/*    */ 
/*    */   
/*    */   public static void disableDummyItemRenderer() {
/* 62 */     if (backupItemRenderer != null)
/*    */     {
/* 64 */       W_EntityRenderer.setItemRenderer(mc, backupItemRenderer);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\MCH_ItemRendererDummy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */