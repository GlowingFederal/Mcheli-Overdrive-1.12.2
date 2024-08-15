/*     */ package mcheli;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.TreeSet;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MCH_FileSearch
/*     */ {
/*     */   public static final int TYPE_FILE_OR_DIR = 1;
/*     */   public static final int TYPE_FILE = 2;
/*     */   public static final int TYPE_DIR = 3;
/*     */   
/*     */   public File[] listFiles(String directoryPath, String fileName) {
/*  22 */     if (fileName != null) {
/*     */       
/*  24 */       fileName = fileName.replace(".", "\\.");
/*  25 */       fileName = fileName.replace("*", ".*");
/*     */     } 
/*  27 */     return listFiles(directoryPath, fileName, 2, true, 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public File[] listFiles(String directoryPath, String fileNamePattern, int type, boolean isRecursive, int period) {
/*  32 */     File dir = new File(directoryPath);
/*  33 */     if (!dir.isDirectory())
/*     */     {
/*  35 */       throw new IllegalArgumentException("引数で指定されたパス[" + dir.getAbsolutePath() + "]はディレクトリではありません。");
/*     */     }
/*     */     
/*  38 */     File[] files = dir.listFiles();
/*     */     
/*  40 */     for (int i = 0; i < files.length; i++) {
/*     */       
/*  42 */       File file = files[i];
/*  43 */       addFile(type, fileNamePattern, this.set, file, period);
/*     */       
/*  45 */       if (isRecursive && file.isDirectory())
/*     */       {
/*  47 */         listFiles(file.getAbsolutePath(), fileNamePattern, type, isRecursive, period);
/*     */       }
/*     */     } 
/*     */     
/*  51 */     return (File[])this.set.toArray((Object[])new File[this.set.size()]);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void addFile(int type, String match, TreeSet<File> set, File file, int period) {
/*  57 */     switch (type) {
/*     */       
/*     */       case 2:
/*  60 */         if (!file.isFile()) {
/*     */           return;
/*     */         }
/*     */         break;
/*     */       
/*     */       case 3:
/*  66 */         if (!file.isDirectory()) {
/*     */           return;
/*     */         }
/*     */         break;
/*     */     } 
/*     */     
/*  72 */     if (match != null && !file.getName().matches(match)) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/*  77 */     if (period != 0) {
/*     */       
/*  79 */       Date lastModifiedDate = new Date(file.lastModified());
/*  80 */       String lastModifiedDateStr = (new SimpleDateFormat("yyyyMMdd")).format(lastModifiedDate);
/*     */       
/*  82 */       long oneDayTime = 86400000L;
/*  83 */       long periodTime = oneDayTime * Math.abs(period);
/*  84 */       Date designatedDate = new Date(System.currentTimeMillis() - periodTime);
/*     */       
/*  86 */       String designatedDateStr = (new SimpleDateFormat("yyyyMMdd")).format(designatedDate);
/*     */       
/*  88 */       if (period > 0) {
/*     */         
/*  90 */         if (lastModifiedDateStr.compareTo(designatedDateStr) >= 0);
/*     */ 
/*     */ 
/*     */       
/*     */       }
/*  95 */       else if (lastModifiedDateStr.compareTo(designatedDateStr) > 0) {
/*     */         return;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 101 */     set.add(file);
/*     */   }
/*     */ 
/*     */   
/* 105 */   private TreeSet<File> set = new TreeSet<>();
/*     */ 
/*     */   
/*     */   public void clear() {
/* 109 */     this.set.clear();
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\MCH_FileSearch.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */