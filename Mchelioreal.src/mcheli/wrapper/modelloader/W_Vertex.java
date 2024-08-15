/*    */ package mcheli.wrapper.modelloader;
/*    */ 
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
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class W_Vertex
/*    */ {
/*    */   public float x;
/*    */   public float y;
/*    */   public float z;
/*    */   
/*    */   public W_Vertex(float x, float y) {
/* 21 */     this(x, y, 0.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   public W_Vertex(float x, float y, float z) {
/* 26 */     this.x = x;
/* 27 */     this.y = y;
/* 28 */     this.z = z;
/*    */   }
/*    */ 
/*    */   
/*    */   public void normalize() {
/* 33 */     double d = Math.sqrt((this.x * this.x + this.y * this.y + this.z * this.z));
/* 34 */     this.x = (float)(this.x / d);
/* 35 */     this.y = (float)(this.y / d);
/* 36 */     this.z = (float)(this.z / d);
/*    */   }
/*    */ 
/*    */   
/*    */   public void add(W_Vertex v) {
/* 41 */     this.x += v.x;
/* 42 */     this.y += v.y;
/* 43 */     this.z += v.z;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equal(W_Vertex v) {
/* 48 */     return (this.x == v.x && this.y == v.y && this.z == v.z);
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\wrapper\modelloader\W_Vertex.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */