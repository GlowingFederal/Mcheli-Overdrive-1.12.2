/*    */ package mcheli.wrapper.modelloader;
/*    */ 
/*    */ import mcheli.__helper.client._IModelCustom;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class W_ModelCustom
/*    */   implements _IModelCustom
/*    */ {
/* 15 */   public float min = 100000.0F;
/* 16 */   public float minX = 100000.0F;
/* 17 */   public float minY = 100000.0F;
/* 18 */   public float minZ = 100000.0F;
/*    */   
/* 20 */   public float max = -100000.0F;
/* 21 */   public float maxX = -100000.0F;
/* 22 */   public float maxY = -100000.0F;
/* 23 */   public float maxZ = -100000.0F;
/*    */   
/* 25 */   public float size = 0.0F;
/* 26 */   public float sizeX = 0.0F;
/* 27 */   public float sizeY = 0.0F;
/* 28 */   public float sizeZ = 0.0F;
/*    */ 
/*    */   
/*    */   public void checkMinMax(W_Vertex v) {
/* 32 */     if (v.x < this.minX)
/* 33 */       this.minX = v.x; 
/* 34 */     if (v.y < this.minY)
/* 35 */       this.minY = v.y; 
/* 36 */     if (v.z < this.minZ)
/* 37 */       this.minZ = v.z; 
/* 38 */     if (v.x > this.maxX)
/* 39 */       this.maxX = v.x; 
/* 40 */     if (v.y > this.maxY)
/* 41 */       this.maxY = v.y; 
/* 42 */     if (v.z > this.maxZ) {
/* 43 */       this.maxZ = v.z;
/*    */     }
/*    */   }
/*    */   
/*    */   public void checkMinMaxFinal() {
/* 48 */     if (this.minX < this.min)
/* 49 */       this.min = this.minX; 
/* 50 */     if (this.minY < this.min)
/* 51 */       this.min = this.minY; 
/* 52 */     if (this.minZ < this.min)
/* 53 */       this.min = this.minZ; 
/* 54 */     if (this.maxX > this.max)
/* 55 */       this.max = this.maxX; 
/* 56 */     if (this.maxY > this.max)
/* 57 */       this.max = this.maxY; 
/* 58 */     if (this.maxZ > this.max)
/* 59 */       this.max = this.maxZ; 
/* 60 */     this.sizeX = this.maxX - this.minX;
/* 61 */     this.sizeY = this.maxY - this.minY;
/* 62 */     this.sizeZ = this.maxZ - this.minZ;
/* 63 */     this.size = this.max - this.min;
/*    */   }
/*    */   
/*    */   public abstract boolean containsPart(String paramString);
/*    */   
/*    */   public abstract void renderAll(int paramInt1, int paramInt2);
/*    */   
/*    */   public abstract void renderAllLine(int paramInt1, int paramInt2);
/*    */   
/*    */   public abstract int getVertexNum();
/*    */   
/*    */   public abstract int getFaceNum();
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\wrapper\modelloader\W_ModelCustom.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */