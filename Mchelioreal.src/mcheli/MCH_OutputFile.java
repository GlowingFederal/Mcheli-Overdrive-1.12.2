/*    */ package mcheli;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.io.FileNotFoundException;
/*    */ import java.io.FileOutputStream;
/*    */ import java.io.OutputStreamWriter;
/*    */ import java.io.PrintWriter;
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
/*    */ public class MCH_OutputFile
/*    */ {
/* 22 */   public File file = null;
/* 23 */   public PrintWriter pw = null;
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean open(String path) {
/* 28 */     close();
/* 29 */     this.file = new File(path);
/*    */     
/*    */     try {
/* 32 */       this.pw = new PrintWriter(this.file);
/*    */     
/*    */     }
/* 35 */     catch (FileNotFoundException e) {
/*    */       
/* 37 */       return false;
/*    */     } 
/* 39 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean openUTF8(String path) {
/* 44 */     close();
/* 45 */     this.file = new File(path);
/*    */     
/*    */     try {
/* 48 */       this.pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(this.file), "UTF-8"));
/*    */     }
/* 50 */     catch (Exception e) {
/*    */       
/* 52 */       e.printStackTrace();
/* 53 */       return false;
/*    */     } 
/* 55 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeLine(String s) {
/* 60 */     if (this.pw != null && s != null)
/*    */     {
/* 62 */       this.pw.println(s);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void close() {
/* 68 */     if (this.pw != null)
/* 69 */       this.pw.close(); 
/* 70 */     this.pw = null;
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\MCH_OutputFile.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */