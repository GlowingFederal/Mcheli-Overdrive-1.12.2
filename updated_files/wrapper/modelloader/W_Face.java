package mcheli.wrapper.modelloader;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class W_Face {
  public int[] verticesID;
  
  public W_Vertex[] vertices;
  
  public W_Vertex[] vertexNormals;
  
  public W_Vertex faceNormal;
  
  public W_TextureCoordinate[] textureCoordinates;
  
  public W_Face copy() {
    W_Face f = new W_Face();
    return f;
  }
  
  public void addFaceForRender(Tessellator tessellator) {
    addFaceForRender(tessellator, 0.0F);
  }
  
  public void addFaceForRender(Tessellator tessellator, float textureOffset) {
    BufferBuilder builder = tessellator.getBuffer();
    if (this.faceNormal == null)
      this.faceNormal = calculateFaceNormal(); 
    float averageU = 0.0F;
    float averageV = 0.0F;
    if (this.textureCoordinates != null && this.textureCoordinates.length > 0) {
      for (int j = 0; j < this.textureCoordinates.length; j++) {
        averageU += (this.textureCoordinates[j]).u;
        averageV += (this.textureCoordinates[j]).v;
      } 
      averageU /= this.textureCoordinates.length;
      averageV /= this.textureCoordinates.length;
    } 
    for (int i = 0; i < this.vertices.length; i++) {
      if (this.textureCoordinates != null && this.textureCoordinates.length > 0) {
        float offsetU = textureOffset;
        float offsetV = textureOffset;
        if ((this.textureCoordinates[i]).u > averageU)
          offsetU = -offsetU; 
        if ((this.textureCoordinates[i]).v > averageV)
          offsetV = -offsetV; 
        builder.pos((this.vertices[i]).x, (this.vertices[i]).y, (this.vertices[i]).z)
          .tex(((this.textureCoordinates[i]).u + offsetU), ((this.textureCoordinates[i]).v + offsetV));
      } else {
        builder.pos((this.vertices[i]).x, (this.vertices[i]).y, (this.vertices[i]).z).tex(0.0D, 0.0D);
      } 
      if (this.vertexNormals != null && i < this.vertexNormals.length) {
        builder.normal((this.vertexNormals[i]).x, (this.vertexNormals[i]).y, (this.vertexNormals[i]).z).endVertex();
      } else {
        builder.normal(this.faceNormal.x, this.faceNormal.y, this.faceNormal.z).endVertex();
      } 
    } 
  }
  
  public W_Vertex calculateFaceNormal() {
    Vec3d v1 = new Vec3d(((this.vertices[1]).x - (this.vertices[0]).x), ((this.vertices[1]).y - (this.vertices[0]).y), ((this.vertices[1]).z - (this.vertices[0]).z));
    Vec3d v2 = new Vec3d(((this.vertices[2]).x - (this.vertices[0]).x), ((this.vertices[2]).y - (this.vertices[0]).y), ((this.vertices[2]).z - (this.vertices[0]).z));
    Vec3d normalVector = v1.crossProduct(v2).normalize();
    return new W_Vertex((float)normalVector.xCoord, (float)normalVector.yCoord, (float)normalVector.zCoord);
  }
  
  public String toString() {
    return "W_Face[id:" + this.verticesID + "]";
  }
}
