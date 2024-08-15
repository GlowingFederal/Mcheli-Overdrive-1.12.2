/*     */ package mcheli.__helper.info;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.Predicate;
/*     */ import javax.annotation.Nullable;
/*     */ import mcheli.MCH_BaseInfo;
/*     */ import mcheli.__helper.MCH_Logger;
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
/*     */ public class ContentRegistry<T extends MCH_BaseInfo>
/*     */ {
/*     */   private Class<T> contentClass;
/*     */   private String dir;
/*     */   private Map<String, T> registry;
/*     */   
/*     */   private ContentRegistry(Class<T> contentClass, String dir, Map<String, T> table) {
/*  30 */     this.contentClass = contentClass;
/*  31 */     this.dir = dir;
/*  32 */     this.registry = Maps.newHashMap(table);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public T get(@Nullable String key) {
/*  38 */     return (key == null) ? null : this.registry.get(key);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public T findFirst(Predicate<? super T> filter) {
/*  44 */     return (T)this.registry.values().stream().filter(filter).findFirst().orElse(null);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean reload(String key) {
/*  49 */     T content = get(key);
/*     */     
/*  51 */     if (content != null) {
/*     */       
/*  53 */       IContentData newContent = ContentRegistries.reparseContent((IContentData)content, this.dir);
/*     */       
/*  55 */       if (this.contentClass.isInstance(newContent)) {
/*     */         
/*  57 */         MCH_BaseInfo mCH_BaseInfo = (MCH_BaseInfo)this.contentClass.cast(newContent);
/*     */         
/*  59 */         this.registry.replace(key, (T)mCH_BaseInfo);
/*  60 */         return true;
/*     */       } 
/*     */ 
/*     */       
/*  64 */       MCH_Logger.get().error("Content cast error, old dir:{}, new dir:{}", content.getClass(), newContent
/*  65 */           .getClass());
/*     */     } 
/*     */ 
/*     */     
/*  69 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void reloadAll() {
/*  74 */     List<T> contents = ContentRegistries.reloadAllAddonContents(this);
/*     */     
/*  76 */     for (MCH_BaseInfo mCH_BaseInfo : contents)
/*     */     {
/*  78 */       this.registry.replace(mCH_BaseInfo.getLoation().func_110623_a(), (T)mCH_BaseInfo);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public List<T> values() {
/*  84 */     return (List<T>)ImmutableList.copyOf(this.registry.values());
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<Map.Entry<String, T>> entries() {
/*  89 */     return this.registry.entrySet();
/*     */   }
/*     */ 
/*     */   
/*     */   public void forEachValue(Consumer<? super T> action) {
/*  94 */     this.registry.values().forEach(action);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean contains(String key) {
/*  99 */     return this.registry.containsKey(key);
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/* 104 */     return this.registry.size();
/*     */   }
/*     */ 
/*     */   
/*     */   public Class<T> getType() {
/* 109 */     return this.contentClass;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDirectoryName() {
/* 114 */     return this.dir;
/*     */   }
/*     */ 
/*     */   
/*     */   private static <TYPE extends IContentData> void putTable(Map<String, TYPE> table, TYPE content) {
/* 119 */     table.put(content.getLoation().func_110623_a(), content);
/*     */   }
/*     */ 
/*     */   
/*     */   public static <TYPE extends MCH_BaseInfo> Builder<TYPE> builder(Class<TYPE> type, String dir) {
/* 124 */     return new Builder<>(type, dir);
/*     */   }
/*     */   
/*     */   public static class Builder<E extends MCH_BaseInfo>
/*     */   {
/*     */     private Class<E> clazz;
/*     */     private String dirName;
/* 131 */     private Map<String, E> map = Maps.newHashMap();
/*     */ 
/*     */     
/*     */     Builder(Class<E> clazz, String dir) {
/* 135 */       this.clazz = clazz;
/* 136 */       this.dirName = dir;
/*     */     }
/*     */ 
/*     */     
/*     */     public void put(E content) {
/* 141 */       ContentRegistry.putTable(this.map, (TYPE)content);
/*     */     }
/*     */ 
/*     */     
/*     */     public ContentRegistry<E> build() {
/* 146 */       return new ContentRegistry<>(this.clazz, this.dirName, this.map);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\__helper\info\ContentRegistry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */