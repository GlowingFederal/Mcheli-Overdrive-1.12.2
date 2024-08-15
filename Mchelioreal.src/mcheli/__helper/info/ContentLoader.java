/*     */ package mcheli.__helper.info;
/*     */ 
/*     */ import com.google.common.collect.LinkedHashMultimap;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Multimap;
/*     */ import com.google.common.io.Files;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.util.List;
/*     */ import java.util.function.Predicate;
/*     */ import java.util.stream.Collector;
/*     */ import java.util.stream.Collectors;
/*     */ import javax.annotation.Nullable;
/*     */ import mcheli.MCH_MOD;
/*     */ import mcheli.__helper.MCH_Logger;
/*     */ import mcheli.__helper.MCH_Utils;
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
/*     */ public abstract class ContentLoader
/*     */ {
/*     */   protected final String domain;
/*     */   protected final File dir;
/*     */   protected final String loaderVersion;
/*     */   private Predicate<String> fileFilter;
/*     */   
/*     */   public ContentLoader(String domain, File addonDir, String loaderVersion, Predicate<String> fileFilter) {
/*  40 */     this.domain = domain;
/*  41 */     this.dir = addonDir;
/*  42 */     this.loaderVersion = loaderVersion;
/*  43 */     this.fileFilter = fileFilter;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isReadable(String path) {
/*  48 */     return this.fileFilter.test(path);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public IContentFactory getFactory(@Nullable String dirName) {
/*  54 */     return ContentFactories.getFactory(dirName);
/*     */   }
/*     */ 
/*     */   
/*     */   public Multimap<ContentType, ContentEntry> load() {
/*  59 */     LinkedHashMultimap linkedHashMultimap = LinkedHashMultimap.create();
/*  60 */     List<ContentEntry> list = getEntries();
/*     */     
/*  62 */     for (ContentEntry entry : list)
/*     */     {
/*  64 */       linkedHashMultimap.put(entry.getType(), entry);
/*     */     }
/*     */     
/*  67 */     return (Multimap<ContentType, ContentEntry>)linkedHashMultimap;
/*     */   }
/*     */ 
/*     */   
/*     */   protected abstract List<ContentEntry> getEntries();
/*     */ 
/*     */   
/*     */   protected abstract InputStream getInputStreamByName(String paramString) throws IOException;
/*     */   
/*     */   public <TYPE extends IContentData> List<TYPE> reloadAndParse(Class<TYPE> clazz, List<TYPE> oldContents, IContentFactory factory) {
/*  77 */     List<TYPE> list = Lists.newLinkedList();
/*     */     
/*  79 */     for (IContentData iContentData : oldContents) {
/*     */ 
/*     */       
/*     */       try {
/*  83 */         ContentEntry entry = makeEntry(iContentData.getContentPath(), factory, true);
/*  84 */         IContentData content = entry.parse();
/*     */         
/*  86 */         if (content != null) {
/*     */           
/*  88 */           content.onPostReload();
/*     */         }
/*     */         else {
/*     */           
/*  92 */           content = iContentData;
/*     */         } 
/*     */         
/*  95 */         if (clazz.isInstance(content))
/*     */         {
/*  97 */           list.add(clazz.cast(content));
/*     */         }
/*     */       }
/* 100 */       catch (IOException e) {
/*     */         
/* 102 */         MCH_Logger.get().error("IO Error from file loader!", e);
/*     */       } 
/*     */     } 
/*     */     
/* 106 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   public IContentData reloadAndParseSingle(IContentData oldData, String dir) {
/* 111 */     IContentData content = oldData;
/*     */ 
/*     */     
/*     */     try {
/* 115 */       ContentEntry entry = makeEntry(oldData.getContentPath(), getFactory(dir), true);
/* 116 */       content = entry.parse();
/*     */       
/* 118 */       if (content != null)
/*     */       {
/* 120 */         content.onPostReload();
/*     */       }
/*     */       else
/*     */       {
/* 124 */         content = oldData;
/*     */       }
/*     */     
/* 127 */     } catch (IOException e) {
/*     */       
/* 129 */       MCH_Logger.get().error("IO Error from file loader!", e);
/*     */     } 
/*     */     
/* 132 */     return content;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected ContentEntry makeEntry(String filepath, @Nullable IContentFactory factory, boolean reload) throws IOException {
/* 138 */     List<String> lines = null;
/*     */     
/* 140 */     try (BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(
/* 141 */             getInputStreamByName(filepath), StandardCharsets.UTF_8))) {
/*     */       
/* 143 */       lines = bufferedreader.lines().collect((Collector)Collectors.toList());
/*     */     } 
/*     */     
/* 146 */     return new ContentEntry(filepath, this.domain, factory, lines, reload);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static class ContentEntry
/*     */   {
/*     */     private String filepath;
/*     */     
/*     */     private String domain;
/*     */     
/*     */     private IContentFactory factory;
/*     */     
/*     */     private List<String> lines;
/*     */     
/*     */     private boolean reload;
/*     */ 
/*     */     
/*     */     private ContentEntry(String filepath, String domain, @Nullable IContentFactory factory, List<String> lines, boolean reload) {
/* 165 */       this.filepath = filepath;
/* 166 */       this.domain = domain;
/* 167 */       this.factory = factory;
/* 168 */       this.lines = lines;
/* 169 */       this.reload = reload;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     public IContentData parse() {
/* 175 */       AddonResourceLocation location = MCH_Utils.addon(this.domain, Files.getNameWithoutExtension(this.filepath));
/*     */       
/* 177 */       if (!this.reload) {
/* 178 */         MCH_MOD.proxy.onParseStartFile(location);
/*     */       }
/*     */       
/*     */       try {
/* 182 */         IContentData content = this.factory.create(location, this.filepath);
/*     */         
/* 184 */         if (content != null) {
/*     */           
/* 186 */           content.parse(this.lines, Files.getFileExtension(this.filepath), this.reload);
/*     */           
/* 188 */           if (!content.validate())
/*     */           {
/* 190 */             MCH_Logger.get().debug("Invalid content info: " + this.filepath);
/*     */           }
/*     */         } 
/*     */         
/* 194 */         return content;
/*     */       }
/* 196 */       catch (Exception e) {
/*     */         
/* 198 */         String msg = "An error occurred while file loading ";
/*     */         
/* 200 */         if (e instanceof ContentParseException)
/*     */         {
/* 202 */           msg = msg + "at line:" + ((ContentParseException)e).getLineNo() + ".";
/*     */         }
/*     */         
/* 205 */         MCH_Logger.get().error(msg + " file:{}, domain:{}", location.func_110623_a(), this.domain, e);
/*     */         
/* 207 */         return null;
/*     */       }
/*     */       finally {
/*     */         
/* 211 */         MCH_MOD.proxy.onParseFinishFile(location);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public ContentType getType() {
/* 217 */       return this.factory.getType();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\__helper\info\ContentLoader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */