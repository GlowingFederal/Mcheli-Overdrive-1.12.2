/*     */ package mcheli;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileFilter;
/*     */ import mcheli.__helper.addon.AddonResourceLocation;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class MCH_InfoManagerBase<T extends MCH_BaseInfo>
/*     */ {
/*     */   public abstract T newInfo(AddonResourceLocation paramAddonResourceLocation, String paramString);
/*     */   
/*     */   protected void put(String name, T info) {}
/*     */   
/*     */   protected abstract boolean contains(String paramString);
/*     */   
/*     */   protected abstract int size();
/*     */   
/*     */   public boolean load(String path, String type) {
/*  34 */     path = path.replace('\\', '/');
/*  35 */     File dir = new File(path + type);
/*  36 */     File[] files = dir.listFiles(new FileFilter()
/*     */         {
/*     */           
/*     */           public boolean accept(File pathname)
/*     */           {
/*  41 */             String s = pathname.getName().toLowerCase();
/*  42 */             return (pathname.isFile() && s.length() >= 5 && s
/*  43 */               .substring(s.length() - 4).compareTo(".txt") == 0);
/*     */           }
/*     */         });
/*     */     
/*  47 */     if (files == null || files.length <= 0)
/*     */     {
/*  49 */       return false;
/*     */     }
/*     */     
/*  52 */     for (File f : files) {
/*     */       
/*  54 */       int line = 0;
/*  55 */       MCH_InputFile inFile = new MCH_InputFile();
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 115 */     MCH_Lib.Log("Read %d %s", new Object[] {
/*     */ 
/*     */           
/* 118 */           Integer.valueOf(size()), type
/*     */         });
/*     */     
/* 121 */     return (size() > 0);
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\MCH_InfoManagerBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */