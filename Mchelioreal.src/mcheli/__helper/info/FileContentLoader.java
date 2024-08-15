/*     */ package mcheli.__helper.info;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.io.Closeable;
/*     */ import java.io.File;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.function.Predicate;
/*     */ import java.util.zip.ZipEntry;
/*     */ import java.util.zip.ZipFile;
/*     */ import mcheli.__helper.MCH_Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FileContentLoader
/*     */   extends ContentLoader
/*     */   implements Closeable
/*     */ {
/*     */   private ZipFile resourcePackZipFile;
/*     */   
/*     */   public FileContentLoader(String domain, File addonDir, String loaderVersion, Predicate<String> fileFilter) {
/*  29 */     super(domain, addonDir, loaderVersion, fileFilter);
/*     */   }
/*     */ 
/*     */   
/*     */   private ZipFile getResourcePackZipFile() throws IOException {
/*  34 */     if (this.resourcePackZipFile == null)
/*     */     {
/*  36 */       this.resourcePackZipFile = new ZipFile(this.dir);
/*     */     }
/*     */     
/*  39 */     return this.resourcePackZipFile;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected List<ContentLoader.ContentEntry> getEntries() {
/*  45 */     return walkEntries("2".equals(this.loaderVersion));
/*     */   }
/*     */ 
/*     */   
/*     */   private List<ContentLoader.ContentEntry> walkEntries(boolean findDeep) {
/*  50 */     List<ContentLoader.ContentEntry> list = Lists.newLinkedList();
/*     */ 
/*     */     
/*     */     try {
/*  54 */       ZipFile zipfile = getResourcePackZipFile();
/*  55 */       Iterator<? extends ZipEntry> itr = zipfile.stream().filter(e -> filter(e, findDeep)).iterator();
/*     */       
/*  57 */       while (itr.hasNext())
/*     */       {
/*  59 */         String name = ((ZipEntry)itr.next()).getName();
/*  60 */         String[] s = name.split("/");
/*  61 */         String typeDirName = (s.length >= 3) ? s[2] : null;
/*  62 */         IContentFactory factory = getFactory(typeDirName);
/*     */         
/*  64 */         if (factory != null)
/*     */         {
/*  66 */           list.add(makeEntry(name, factory, false));
/*     */         }
/*     */       }
/*     */     
/*  70 */     } catch (IOException e) {
/*     */       
/*  72 */       MCH_Logger.get().error("IO Error from file loader!", e);
/*     */     } 
/*     */     
/*  75 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean filter(ZipEntry zipEntry, boolean deep) {
/*  80 */     String name = zipEntry.getName();
/*  81 */     String[] split = name.split("/");
/*  82 */     String lootDir = (split.length >= 2) ? split[0] : "";
/*     */     
/*  84 */     if (!zipEntry.isDirectory())
/*     */     {
/*     */       
/*  87 */       if (deep || "assets".equals(lootDir) || split.length <= 2)
/*     */       {
/*  89 */         return isReadable(name);
/*     */       }
/*     */     }
/*     */     
/*  93 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected InputStream getInputStreamByName(String name) throws IOException {
/*  99 */     ZipFile zipfile = getResourcePackZipFile();
/* 100 */     ZipEntry zipentry = zipfile.getEntry(name);
/*     */     
/* 102 */     if (zipentry == null)
/*     */     {
/* 104 */       throw new FileNotFoundException(String.format("'%s' in AddonPack '%s'", new Object[] { this.dir, name }));
/*     */     }
/*     */ 
/*     */     
/* 108 */     return zipfile.getInputStream(zipentry);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void finalize() throws Throwable {
/* 115 */     close();
/* 116 */     super.finalize();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/* 122 */     if (this.resourcePackZipFile != null) {
/*     */       
/* 124 */       this.resourcePackZipFile.close();
/* 125 */       this.resourcePackZipFile = null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\__helper\info\FileContentLoader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */