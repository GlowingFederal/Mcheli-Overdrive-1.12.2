/*     */ package mcheli.wrapper.modelloader;
/*     */ 
/*     */ import net.minecraft.client.renderer.BufferBuilder;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @SideOnly(Side.CLIENT)
/*     */ public class W_Face
/*     */ {
/*     */   public int[] verticesID;
/*     */   public W_Vertex[] vertices;
/*     */   public W_Vertex[] vertexNormals;
/*     */   public W_Vertex faceNormal;
/*     */   public W_TextureCoordinate[] textureCoordinates;
/*     */   
/*     */   public W_Face copy() {
/*  27 */     W_Face f = new W_Face();
/*  28 */     return f;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addFaceForRender(Tessellator tessellator) {
/*  33 */     addFaceForRender(tessellator, 0.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addFaceForRender(Tessellator tessellator, float textureOffset) {
/*  38 */     BufferBuilder builder = tessellator.func_178180_c();
/*     */     
/*  40 */     if (this.faceNormal == null)
/*     */     {
/*  42 */       this.faceNormal = calculateFaceNormal();
/*     */     }
/*     */ 
/*     */     
/*  46 */     float averageU = 0.0F;
/*  47 */     float averageV = 0.0F;
/*     */     
/*  49 */     if (this.textureCoordinates != null && this.textureCoordinates.length > 0) {
/*     */       
/*  51 */       for (int j = 0; j < this.textureCoordinates.length; j++) {
/*     */         
/*  53 */         averageU += (this.textureCoordinates[j]).u;
/*  54 */         averageV += (this.textureCoordinates[j]).v;
/*     */       } 
/*     */       
/*  57 */       averageU /= this.textureCoordinates.length;
/*  58 */       averageV /= this.textureCoordinates.length;
/*     */     } 
/*     */     
/*  61 */     for (int i = 0; i < this.vertices.length; i++) {
/*     */       
/*  63 */       if (this.textureCoordinates != null && this.textureCoordinates.length > 0) {
/*     */         
/*  65 */         float offsetU = textureOffset;
/*  66 */         float offsetV = textureOffset;
/*     */         
/*  68 */         if ((this.textureCoordinates[i]).u > averageU)
/*     */         {
/*  70 */           offsetU = -offsetU;
/*     */         }
/*  72 */         if ((this.textureCoordinates[i]).v > averageV)
/*     */         {
/*  74 */           offsetV = -offsetV;
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  82 */         builder.func_181662_b((this.vertices[i]).x, (this.vertices[i]).y, (this.vertices[i]).z)
/*  83 */           .func_187315_a(((this.textureCoordinates[i]).u + offsetU), ((this.textureCoordinates[i]).v + offsetV));
/*     */       
/*     */       }
/*     */       else {
/*     */         
/*  88 */         builder.func_181662_b((this.vertices[i]).x, (this.vertices[i]).y, (this.vertices[i]).z).func_187315_a(0.0D, 0.0D);
/*     */       } 
/*     */       
/*  91 */       if (this.vertexNormals != null && i < this.vertexNormals.length) {
/*     */         
/*  93 */         builder.func_181663_c((this.vertexNormals[i]).x, (this.vertexNormals[i]).y, (this.vertexNormals[i]).z).func_181675_d();
/*     */       }
/*     */       else {
/*     */         
/*  97 */         builder.func_181663_c(this.faceNormal.x, this.faceNormal.y, this.faceNormal.z).func_181675_d();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public W_Vertex calculateFaceNormal() {
/* 104 */     Vec3d v1 = new Vec3d(((this.vertices[1]).x - (this.vertices[0]).x), ((this.vertices[1]).y - (this.vertices[0]).y), ((this.vertices[1]).z - (this.vertices[0]).z));
/*     */     
/* 106 */     Vec3d v2 = new Vec3d(((this.vertices[2]).x - (this.vertices[0]).x), ((this.vertices[2]).y - (this.vertices[0]).y), ((this.vertices[2]).z - (this.vertices[0]).z));
/*     */     
/* 108 */     Vec3d normalVector = v1.func_72431_c(v2).func_72432_b();
/*     */     
/* 110 */     return new W_Vertex((float)normalVector.field_72450_a, (float)normalVector.field_72448_b, (float)normalVector.field_72449_c);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 116 */     return "W_Face[id:" + this.verticesID + "]";
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\wrapper\modelloader\W_Face.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */