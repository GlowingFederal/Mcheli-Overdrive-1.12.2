/*    */ package mcheli.debug._v1.model;
/*    */ 
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
/*    */ public class ObjModel
/*    */   implements DebugInfoObject
/*    */ {
/*    */   private List<_GroupObject> groupObjects;
/*    */   private int vertexNum;
/*    */   private int faceNum;
/*    */   
/*    */   public ObjModel(List<_GroupObject> groupObjects, int verts, int faces) {
/* 23 */     this.groupObjects = groupObjects;
/* 24 */     this.vertexNum = verts;
/* 25 */     this.faceNum = faces;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 31 */     return "ObjModel[verts:" + this.vertexNum + ", face:" + this.faceNum + ", obj:" + this.groupObjects + "]";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void printInfo(PrintStreamWrapper stream) {
/* 37 */     stream.push("Obj Model Info:");
/* 38 */     this.groupObjects.forEach(g -> g.printInfo(stream));
/* 39 */     stream.pop();
/* 40 */     stream.println();
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\debug\_v1\model\ObjModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */