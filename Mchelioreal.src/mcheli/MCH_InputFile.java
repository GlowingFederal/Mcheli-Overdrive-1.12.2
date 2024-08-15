/*    */ package mcheli;
/*    */ 
/*    */ import java.io.BufferedReader;
/*    */ import java.io.File;
/*    */ import java.io.FileInputStream;
/*    */ import java.io.FileNotFoundException;
/*    */ import java.io.FileReader;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStreamReader;
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
/*    */ public class MCH_InputFile
/*    */ {
/* 24 */   public File file = null;
/* 25 */   public BufferedReader br = null;
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean open(String path) {
/* 30 */     close();
/* 31 */     this.file = new File(path);
/* 32 */     String filePath = this.file.getAbsolutePath();
/*    */     
/*    */     try {
/* 35 */       this.br = new BufferedReader(new FileReader(this.file));
/*    */     }
/* 37 */     catch (FileNotFoundException e) {
/*    */       
/* 39 */       MCH_Lib.DbgLog(true, "FILE open failed MCH_InputFile.open:" + filePath, new Object[0]);
/* 40 */       e.printStackTrace();
/* 41 */       return false;
/*    */     } 
/* 43 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean openUTF8(File file) {
/* 48 */     return openUTF8(file.getPath());
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean openUTF8(String path) {
/* 53 */     close();
/* 54 */     this.file = new File(path);
/*    */     
/*    */     try {
/* 57 */       this.br = new BufferedReader(new InputStreamReader(new FileInputStream(this.file), "UTF-8"));
/*    */     }
/* 59 */     catch (Exception e) {
/*    */       
/* 61 */       e.printStackTrace();
/* 62 */       return false;
/*    */     } 
/* 64 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String readLine() {
/*    */     try {
/* 71 */       return (this.br != null) ? this.br.readLine() : null;
/*    */     }
/* 73 */     catch (IOException iOException) {
/*    */ 
/*    */ 
/*    */       
/* 77 */       return null;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void close() {
/*    */     try {
/* 84 */       if (this.br != null)
/*    */       {
/* 86 */         this.br.close();
/*    */       }
/*    */     }
/* 89 */     catch (IOException iOException) {}
/*    */ 
/*    */ 
/*    */     
/* 93 */     this.br = null;
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\MCH_InputFile.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */