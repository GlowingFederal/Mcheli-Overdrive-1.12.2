/*     */ package mcheli.__helper.info;
/*     */ 
/*     */ import com.google.common.base.CharMatcher;
/*     */ import com.google.common.collect.Lists;
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.List;
/*     */ import java.util.function.Predicate;
/*     */ import javax.annotation.Nullable;
/*     */ import org.jline.utils.OSUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FolderContentLoader
/*     */   extends ContentLoader
/*     */ {
/*  26 */   private static final boolean ON_WINDOWS = OSUtils.IS_WINDOWS;
/*  27 */   private static final CharMatcher BACKSLASH_MATCHER = CharMatcher.is('\\');
/*     */   
/*     */   private File addonFolder;
/*     */   
/*     */   public FolderContentLoader(String domain, File addonDir, String loaderVersion, Predicate<String> fileFilter) {
/*  32 */     super(domain, addonDir, loaderVersion, fileFilter);
/*  33 */     this.addonFolder = addonDir.getParentFile();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected List<ContentLoader.ContentEntry> getEntries() {
/*  39 */     return walkDir(this.dir, (IContentFactory)null, this.loaderVersion.equals("2"), 0);
/*     */   }
/*     */ 
/*     */   
/*     */   private List<ContentLoader.ContentEntry> walkDir(File dir, @Nullable IContentFactory factory, boolean loadDeep, int depth) {
/*  44 */     List<ContentLoader.ContentEntry> list = Lists.newArrayList();
/*     */     
/*  46 */     if (dir == null || !dir.exists())
/*     */     {
/*  48 */       return Lists.newArrayList();
/*     */     }
/*     */     
/*  51 */     if (dir.isDirectory()) {
/*     */       
/*  53 */       if (loadDeep || depth <= 1)
/*     */       {
/*  55 */         for (File f : dir.listFiles()) {
/*     */           
/*  57 */           IContentFactory contentFactory = factory;
/*     */           
/*  59 */           boolean flag = (loadDeep || (depth == 0 && "assets".equals(f.getName())));
/*     */           
/*  61 */           if (contentFactory == null)
/*     */           {
/*  63 */             contentFactory = getFactory(f.getName());
/*     */           }
/*     */           
/*  66 */           list.addAll(walkDir(f, contentFactory, flag, depth + 1));
/*     */         } 
/*     */       }
/*     */     } else {
/*     */ 
/*     */       
/*     */       try {
/*     */ 
/*     */         
/*  75 */         String s = getDirPath(dir);
/*     */         
/*  77 */         if (isReadable(s) && factory != null)
/*     */         {
/*  79 */           list.add(makeEntry(s, factory, false));
/*     */         }
/*     */       }
/*  82 */       catch (IOException e) {
/*     */         
/*  84 */         e.printStackTrace();
/*     */       } 
/*     */     } 
/*     */     
/*  88 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   private String getDirPath(File file) throws IOException {
/*  93 */     String s = this.dir.getName();
/*  94 */     String s1 = file.getCanonicalPath();
/*     */     
/*  96 */     if (ON_WINDOWS)
/*     */     {
/*  98 */       s1 = BACKSLASH_MATCHER.replaceFrom(s1, '/');
/*     */     }
/*     */     
/* 101 */     String[] split = s1.split(this.addonFolder.getName() + "/" + s + "/", 2);
/*     */     
/* 103 */     if (split.length < 2) {
/* 104 */       throw new FileNotFoundException(String.format("'%s' in AddonPack '%s'", new Object[] { this.dir, s }));
/*     */     }
/* 106 */     return split[1];
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected InputStream getInputStreamByName(String name) throws IOException {
/* 112 */     File file1 = getFile(name);
/*     */     
/* 114 */     if (file1 == null)
/*     */     {
/* 116 */       throw new FileNotFoundException(String.format("'%s' in AddonPack '%s'", new Object[] { this.dir, name }));
/*     */     }
/*     */ 
/*     */     
/* 120 */     return new BufferedInputStream(new FileInputStream(file1));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private File getFile(String filepath) {
/*     */     try {
/* 129 */       File file1 = new File(this.dir, filepath);
/*     */       
/* 131 */       if (file1.isFile() && validatePath(file1, filepath))
/*     */       {
/* 133 */         return file1;
/*     */       }
/*     */     }
/* 136 */     catch (IOException iOException) {}
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 141 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected static boolean validatePath(File file, String filepath) throws IOException {
/* 146 */     String s = file.getCanonicalPath();
/*     */     
/* 148 */     if (ON_WINDOWS)
/*     */     {
/* 150 */       s = BACKSLASH_MATCHER.replaceFrom(s, '/');
/*     */     }
/*     */     
/* 153 */     return s.endsWith(filepath);
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\__helper\info\FolderContentLoader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */