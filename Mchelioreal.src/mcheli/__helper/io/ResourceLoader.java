/*     */ package mcheli.__helper.io;
/*     */ 
/*     */ import com.google.common.base.CharMatcher;
/*     */ import com.google.common.collect.Lists;
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.Closeable;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.nio.file.Path;
/*     */ import java.util.List;
/*     */ import java.util.Optional;
/*     */ import java.util.function.Predicate;
/*     */ import java.util.stream.Collectors;
/*     */ import java.util.zip.ZipEntry;
/*     */ import java.util.zip.ZipFile;
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
/*     */ 
/*     */ public abstract class ResourceLoader
/*     */   implements Closeable
/*     */ {
/*     */   protected final File dir;
/*     */   
/*     */   ResourceLoader(File file) {
/*  36 */     this.dir = file;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<ResourceEntry> loadAll() throws IOException {
/*  41 */     return loadAll(null);
/*     */   }
/*     */ 
/*     */   
/*     */   public abstract List<ResourceEntry> loadAll(@Nullable Predicate<? super ResourceEntry> paramPredicate) throws IOException;
/*     */ 
/*     */   
/*     */   public Optional<ResourceEntry> loadFirst() throws IOException {
/*  49 */     return loadAll(null).stream().findFirst();
/*     */   }
/*     */ 
/*     */   
/*     */   public abstract ResourceEntry load(String paramString) throws IOException, FileNotFoundException;
/*     */   
/*     */   public abstract InputStream getInputStreamFromEntry(ResourceEntry paramResourceEntry) throws IOException;
/*     */   
/*     */   public InputStream getInputStream(String relativePath) throws IOException {
/*  58 */     return getInputStreamFromEntry(load(relativePath));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void finalize() throws Throwable {
/*  64 */     close();
/*  65 */     super.finalize();
/*     */   }
/*     */ 
/*     */   
/*     */   public static ResourceLoader create(File file) {
/*  70 */     if (file.isDirectory())
/*     */     {
/*  72 */       return new DirectoryLoader(file);
/*     */     }
/*     */ 
/*     */     
/*  76 */     return new ZipJarFileLoader(file);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class ResourceEntry
/*     */   {
/*     */     private String path;
/*     */ 
/*     */     
/*     */     private boolean isDirectory;
/*     */ 
/*     */ 
/*     */     
/*     */     public ResourceEntry(String path, boolean isDirectory) {
/*  92 */       this.path = path;
/*  93 */       this.isDirectory = isDirectory;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isDirectory() {
/*  98 */       return this.isDirectory;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getPath() {
/* 103 */       return this.path;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static class ZipJarFileLoader
/*     */     extends ResourceLoader
/*     */   {
/*     */     private ZipFile resourcePackZipFile;
/*     */ 
/*     */ 
/*     */     
/*     */     ZipJarFileLoader(File file) {
/* 118 */       super(file);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public List<ResourceLoader.ResourceEntry> loadAll(@Nullable Predicate<? super ResourceLoader.ResourceEntry> filePathFilter) throws IOException {
/* 124 */       ZipFile zipfile = getResourcePackZipFile();
/* 125 */       filePathFilter = (filePathFilter == null) ? (path -> true) : filePathFilter;
/*     */ 
/*     */ 
/*     */       
/* 129 */       List<ResourceLoader.ResourceEntry> list = (List<ResourceLoader.ResourceEntry>)zipfile.stream().<ResourceLoader.ResourceEntry>map(enrty -> new ResourceLoader.ResourceEntry(enrty.getName(), enrty.isDirectory())).filter(filePathFilter).collect(Collectors.toList());
/*     */       
/* 131 */       return list;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public ResourceLoader.ResourceEntry load(String relativePath) throws IOException, FileNotFoundException {
/* 137 */       ZipFile zipfile = getResourcePackZipFile();
/* 138 */       ZipEntry zipEntry = zipfile.getEntry(relativePath);
/*     */       
/* 140 */       if (zipEntry != null)
/*     */       {
/* 142 */         return new ResourceLoader.ResourceEntry(zipEntry.getName(), zipEntry.isDirectory());
/*     */       }
/*     */       
/* 145 */       throw new FileNotFoundException(relativePath);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public InputStream getInputStreamFromEntry(ResourceLoader.ResourceEntry resource) throws IOException {
/* 151 */       ZipFile zipfile = getResourcePackZipFile();
/* 152 */       ZipEntry zipentry = zipfile.getEntry(resource.getPath());
/*     */       
/* 154 */       if (zipentry == null)
/*     */       {
/* 156 */         throw new FileNotFoundException(
/* 157 */             String.format("'%s' in ResourcePack '%s'", new Object[] { this.dir, resource.getPath() }));
/*     */       }
/*     */ 
/*     */       
/* 161 */       return zipfile.getInputStream(zipentry);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private ZipFile getResourcePackZipFile() throws IOException {
/* 167 */       if (this.resourcePackZipFile == null)
/*     */       {
/* 169 */         this.resourcePackZipFile = new ZipFile(this.dir);
/*     */       }
/*     */       
/* 172 */       return this.resourcePackZipFile;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void close() throws IOException {
/* 178 */       if (this.resourcePackZipFile != null) {
/*     */         
/* 180 */         this.resourcePackZipFile.close();
/* 181 */         this.resourcePackZipFile = null;
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static class DirectoryLoader
/*     */     extends ResourceLoader
/*     */   {
/* 193 */     private static final boolean ON_WINDOWS = OSUtils.IS_WINDOWS;
/* 194 */     private static final CharMatcher BACKSLASH_MATCHER = CharMatcher.is('\\');
/*     */ 
/*     */     
/*     */     DirectoryLoader(File file) {
/* 198 */       super(file);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public List<ResourceLoader.ResourceEntry> loadAll(@Nullable Predicate<? super ResourceLoader.ResourceEntry> filePathFilter) throws IOException {
/* 204 */       List<ResourceLoader.ResourceEntry> list = Lists.newLinkedList();
/* 205 */       filePathFilter = (filePathFilter == null) ? (path -> true) : filePathFilter;
/*     */       
/* 207 */       loadFiles(this.dir, list, filePathFilter);
/*     */       
/* 209 */       return list;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private void loadFiles(File dir, List<ResourceLoader.ResourceEntry> list, Predicate<? super ResourceLoader.ResourceEntry> filePathFilter) throws IOException {
/* 215 */       if (dir.exists())
/*     */       {
/* 217 */         if (dir.isDirectory()) {
/*     */           
/* 219 */           for (File file : dir.listFiles())
/*     */           {
/* 221 */             loadFiles(file, list, filePathFilter);
/*     */           }
/*     */         }
/*     */         else {
/*     */           
/* 226 */           Path file = dir.toPath();
/* 227 */           Path root = this.dir.toPath();
/* 228 */           String s = root.relativize(file).toString();
/*     */           
/* 230 */           if (ON_WINDOWS)
/*     */           {
/* 232 */             s = BACKSLASH_MATCHER.replaceFrom(s, '/');
/*     */           }
/*     */           
/* 235 */           ResourceLoader.ResourceEntry resourceFile = new ResourceLoader.ResourceEntry(s, dir.isDirectory());
/*     */           
/* 237 */           if (filePathFilter.test(resourceFile))
/*     */           {
/* 239 */             list.add(resourceFile);
/*     */           }
/*     */         } 
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public ResourceLoader.ResourceEntry load(String relativePath) throws IOException, FileNotFoundException {
/* 248 */       File file = getFile(relativePath);
/*     */       
/* 250 */       if (file != null && file.exists())
/*     */       {
/* 252 */         return new ResourceLoader.ResourceEntry(relativePath, file.isDirectory());
/*     */       }
/*     */       
/* 255 */       throw new FileNotFoundException(relativePath);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public InputStream getInputStreamFromEntry(ResourceLoader.ResourceEntry resource) throws IOException {
/* 261 */       File file1 = getFile(resource.getPath());
/*     */       
/* 263 */       if (file1 == null)
/*     */       {
/* 265 */         throw new FileNotFoundException(
/* 266 */             String.format("'%s' in ResourcePack '%s'", new Object[] { this.dir, resource.getPath() }));
/*     */       }
/*     */ 
/*     */       
/* 270 */       return new BufferedInputStream(new FileInputStream(file1));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     private File getFile(String filepath) {
/*     */       try {
/* 279 */         File file1 = new File(this.dir, filepath);
/*     */         
/* 281 */         if (file1.isFile() && validatePath(file1, filepath))
/*     */         {
/* 283 */           return file1;
/*     */         }
/*     */       }
/* 286 */       catch (IOException iOException) {}
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 291 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     private boolean validatePath(File file, String filepath) throws IOException {
/* 296 */       String s = file.getCanonicalPath();
/*     */       
/* 298 */       if (ON_WINDOWS)
/*     */       {
/* 300 */         s = BACKSLASH_MATCHER.replaceFrom(s, '/');
/*     */       }
/*     */       
/* 303 */       return s.endsWith(filepath);
/*     */     }
/*     */     
/*     */     public void close() throws IOException {}
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\__helper\io\ResourceLoader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */