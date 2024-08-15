/*    */ package mcheli.__helper.client.renderer;
/*    */ 
/*    */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*    */ import net.minecraft.client.renderer.vertex.VertexFormat;
/*    */ import net.minecraft.client.renderer.vertex.VertexFormatElement;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MCH_Verts
/*    */ {
/* 14 */   public static final VertexFormatElement TEX_2S = new VertexFormatElement(0, VertexFormatElement.EnumType.SHORT, VertexFormatElement.EnumUsage.UV, 2);
/*    */ 
/*    */   
/* 17 */   public static final VertexFormat POS_COLOR_LMAP = (new VertexFormat()).func_181721_a(DefaultVertexFormats.field_181713_m)
/* 18 */     .func_181721_a(DefaultVertexFormats.field_181714_n).func_181721_a(TEX_2S);
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\__helper\client\renderer\MCH_Verts.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */