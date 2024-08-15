/*    */ package mcheli.debug._v1.model;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.io.FileInputStream;
/*    */ import java.net.URI;
/*    */ import java.net.URISyntaxException;
/*    */ import java.net.URL;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ModelLoaderDebug
/*    */ {
/*    */   public static void main(String[] args) {
/*    */     try {
/* 32 */       ObjModel model = ObjParser.parse(new FileInputStream(new File(getResource("models/gltd.obj"))));
/*    */       
/* 34 */       model.printInfo();
/*    */     }
/* 36 */     catch (Exception e) {
/*    */       
/* 38 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   static URI getResource(String path) {
/* 44 */     URL url = ModelLoaderDebug.class.getClassLoader().getResource("assets/mcheli/" + path);
/*    */ 
/*    */     
/*    */     try {
/* 48 */       return (url != null) ? url.toURI() : null;
/*    */     }
/* 50 */     catch (URISyntaxException e) {
/*    */       
/* 52 */       e.printStackTrace();
/*    */ 
/*    */       
/* 55 */       return null;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\debug\_v1\model\ModelLoaderDebug.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */