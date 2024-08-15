/*    */ package mcheli.wrapper.modelloader;
/*    */ 
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ 
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class W_TextureCoordinate
/*    */ {
/*    */   public float u;
/*    */   public float v;
/*    */   public float w;
/*    */   
/*    */   public W_TextureCoordinate(float u, float v) {
/* 15 */     this(u, v, 0.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   public W_TextureCoordinate(float u, float v, float w) {
/* 20 */     this.u = u;
/* 21 */     this.v = v;
/* 22 */     this.w = w;
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\wrapper\modelloader\W_TextureCoordinate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */