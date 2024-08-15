/*    */ package mcheli.debug._v1.model;
/*    */ 
/*    */ import mcheli.__helper.debug.DebugInfoObject;
/*    */ import mcheli.debug._v1.PrintStreamWrapper;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class _Vertex
/*    */   implements DebugInfoObject
/*    */ {
/*    */   public final float x;
/*    */   public final float y;
/*    */   public final float z;
/*    */   
/*    */   _Vertex(float x, float y) {
/* 21 */     this(x, y, 0.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   _Vertex(float x, float y, float z) {
/* 26 */     this.x = x;
/* 27 */     this.y = y;
/* 28 */     this.z = z;
/*    */   }
/*    */ 
/*    */   
/*    */   public _Vertex normalize() {
/* 33 */     double d = Math.sqrt((this.x * this.x + this.y * this.y + this.z * this.z));
/*    */     
/* 35 */     return new _Vertex((float)(this.x / d), (float)(this.y / d), (float)(this.z / d));
/*    */   }
/*    */ 
/*    */   
/*    */   public _Vertex add(_Vertex v) {
/* 40 */     return new _Vertex(this.x + v.x, this.y + v.y, this.z + v.z);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void printInfo(PrintStreamWrapper stream) {
/* 46 */     stream.println(String.format("V: [%.6f, %.6f, %.6f]", new Object[] { Float.valueOf(this.x), Float.valueOf(this.y), Float.valueOf(this.z) }));
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\debug\_v1\model\_Vertex.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */