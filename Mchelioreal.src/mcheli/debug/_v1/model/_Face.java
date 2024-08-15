/*     */ package mcheli.debug._v1.model;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import mcheli.__helper.debug.DebugInfoObject;
/*     */ import mcheli.debug._v1.PrintStreamWrapper;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class _Face
/*     */   implements DebugInfoObject
/*     */ {
/*     */   private int[] verticesID;
/*     */   private _Vertex[] vertices;
/*     */   private _Vertex[] vertexNormals;
/*     */   private _TextureCoord[] textureCoordinates;
/*     */   private _Vertex faceNormal;
/*     */   
/*     */   _Face(int[] ids, _Vertex[] verts, _TextureCoord[] texCoords) {
/*  27 */     this(ids, verts, verts, texCoords);
/*     */   }
/*     */ 
/*     */   
/*     */   _Face(int[] ids, _Vertex[] verts, _Vertex[] normals, _TextureCoord[] texCoords) {
/*  32 */     this.verticesID = ids;
/*  33 */     this.vertices = verts;
/*  34 */     this.vertexNormals = normals;
/*  35 */     this.textureCoordinates = texCoords;
/*  36 */     this.faceNormal = calculateFaceNormal(verts);
/*     */   }
/*     */ 
/*     */   
/*     */   private static _Vertex calculateFaceNormal(_Vertex[] verts) {
/*  41 */     Vec3d v1 = new Vec3d(((verts[1]).x - (verts[0]).x), ((verts[1]).y - (verts[0]).y), ((verts[1]).z - (verts[0]).z));
/*  42 */     Vec3d v2 = new Vec3d(((verts[2]).x - (verts[0]).x), ((verts[2]).y - (verts[0]).y), ((verts[2]).z - (verts[0]).z));
/*  43 */     Vec3d normalVector = v1.func_72431_c(v2).func_72432_b();
/*     */     
/*  45 */     return new _Vertex((float)normalVector.field_72450_a, (float)normalVector.field_72448_b, (float)normalVector.field_72449_c);
/*     */   }
/*     */ 
/*     */   
/*     */   _Face calcVerticesNormal(List<_Face> faces, boolean shading, double facet) {
/*  50 */     _Vertex[] vnormals = new _Vertex[this.verticesID.length];
/*     */     
/*  52 */     for (int i = 0; i < this.verticesID.length; i++) {
/*     */       
/*  54 */       _Vertex vn = getVerticesNormalFromFace(this.faceNormal, this.verticesID[i], faces, (float)facet);
/*     */       
/*  56 */       vn = vn.normalize();
/*     */       
/*  58 */       if (shading) {
/*     */         
/*  60 */         if ((this.faceNormal.x * vn.x + this.faceNormal.y * vn.y + this.faceNormal.z * vn.z) >= facet)
/*     */         {
/*  62 */           vnormals[i] = vn;
/*     */         }
/*     */         else
/*     */         {
/*  66 */           vnormals[i] = this.faceNormal;
/*     */         }
/*     */       
/*     */       }
/*     */       else {
/*     */         
/*  72 */         vnormals[i] = this.faceNormal;
/*     */       } 
/*     */     } 
/*     */     
/*  76 */     return new _Face(this.verticesID, this.vertices, vnormals, this.textureCoordinates);
/*     */   }
/*     */ 
/*     */   
/*     */   private static _Vertex getVerticesNormalFromFace(_Vertex fnormal, int verticesID, List<_Face> faces, float facet) {
/*  81 */     _Vertex v = new _Vertex(0.0F, 0.0F, 0.0F);
/*     */     
/*  83 */     for (_Face f : faces) {
/*     */       
/*  85 */       for (int id : f.verticesID) {
/*     */         
/*  87 */         if (id == verticesID) {
/*     */           
/*  89 */           if (f.faceNormal.x * fnormal.x + f.faceNormal.y * fnormal.y + f.faceNormal.z * fnormal.z < facet) {
/*     */             break;
/*     */           }
/*  92 */           v = v.add(f.faceNormal);
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/*  98 */     v = v.normalize();
/*  99 */     return v;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void printInfo(PrintStreamWrapper stream) {
/* 105 */     stream.println("F: [");
/* 106 */     stream.push();
/*     */     
/* 108 */     stream.println("ids: " + Arrays.toString(this.verticesID));
/* 109 */     stream.println("--- verts");
/* 110 */     Arrays.<_Vertex>stream(this.vertices).forEach(v -> v.printInfo(stream));
/* 111 */     stream.println("--- normals");
/* 112 */     Arrays.<_Vertex>stream(this.vertexNormals).forEach(n -> n.printInfo(stream));
/* 113 */     stream.println("--- tex coords");
/* 114 */     Arrays.<_TextureCoord>stream(this.textureCoordinates).forEach(t -> t.printInfo(stream));
/* 115 */     stream.println("--- face normal");
/* 116 */     this.faceNormal.printInfo(stream);
/*     */     
/* 118 */     stream.pop();
/* 119 */     stream.println("]");
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\debug\_v1\model\_Face.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */