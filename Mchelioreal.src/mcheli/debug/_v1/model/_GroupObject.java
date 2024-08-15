/*    */ package mcheli.debug._v1.model;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
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
/*    */ 
/*    */ class _GroupObject
/*    */   implements DebugInfoObject
/*    */ {
/*    */   public final String name;
/*    */   private List<_Face> faces;
/*    */   
/*    */   private _GroupObject(String name, List<_Face> faces) {
/* 23 */     this.name = name;
/* 24 */     this.faces = faces;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void printInfo(PrintStreamWrapper stream) {
/* 30 */     stream.push(String.format("G: [name: %s]", new Object[] { this.name }));
/* 31 */     this.faces.forEach(f -> f.printInfo(stream));
/* 32 */     stream.pop();
/* 33 */     stream.println();
/*    */   }
/*    */ 
/*    */   
/*    */   public static Builder builder() {
/* 38 */     return new Builder();
/*    */   }
/*    */   
/*    */   static class Builder
/*    */   {
/*    */     private String name;
/* 44 */     private List<_Face> faces = new ArrayList<>();
/*    */ 
/*    */     
/*    */     public Builder name(String name) {
/* 48 */       this.name = name;
/* 49 */       return this;
/*    */     }
/*    */ 
/*    */     
/*    */     public Builder addFace(_Face face) {
/* 54 */       this.faces.add(face);
/* 55 */       return this;
/*    */     }
/*    */ 
/*    */     
/*    */     public int faceSize() {
/* 60 */       return this.faces.size();
/*    */     }
/*    */ 
/*    */     
/*    */     public _GroupObject build() {
/* 65 */       return new _GroupObject(this.name, this.faces);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\debug\_v1\model\_GroupObject.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */