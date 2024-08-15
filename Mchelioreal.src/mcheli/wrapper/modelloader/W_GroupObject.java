/*    */ package mcheli.wrapper.modelloader;
/*    */ 
/*    */ import com.google.common.base.Joiner;
/*    */ import java.util.ArrayList;
/*    */ import net.minecraft.client.renderer.BufferBuilder;
/*    */ import net.minecraft.client.renderer.Tessellator;
/*    */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
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
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class W_GroupObject
/*    */ {
/*    */   public String name;
/* 23 */   public ArrayList<W_Face> faces = new ArrayList<>();
/*    */   
/*    */   public int glDrawingMode;
/*    */   
/*    */   public W_GroupObject() {
/* 28 */     this("");
/*    */   }
/*    */ 
/*    */   
/*    */   public W_GroupObject(String name) {
/* 33 */     this(name, -1);
/*    */   }
/*    */ 
/*    */   
/*    */   public W_GroupObject(String name, int glDrawingMode) {
/* 38 */     this.name = name;
/* 39 */     this.glDrawingMode = glDrawingMode;
/*    */   }
/*    */ 
/*    */   
/*    */   public void render() {
/* 44 */     if (this.faces.size() > 0) {
/*    */       
/* 46 */       Tessellator tessellator = Tessellator.func_178181_a();
/* 47 */       BufferBuilder builder = tessellator.func_178180_c();
/*    */       
/* 49 */       builder.func_181668_a(this.glDrawingMode, DefaultVertexFormats.field_181710_j);
/* 50 */       render(tessellator);
/* 51 */       tessellator.func_78381_a();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(Tessellator tessellator) {
/* 57 */     if (this.faces.size() > 0)
/*    */     {
/* 59 */       for (W_Face face : this.faces)
/*    */       {
/* 61 */         face.addFaceForRender(tessellator);
/*    */       }
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 69 */     return "W_GroupObject[size=" + this.faces.size() + ",values=[" + Joiner.on('\n').join(this.faces) + "]]";
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\wrapper\modelloader\W_GroupObject.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */