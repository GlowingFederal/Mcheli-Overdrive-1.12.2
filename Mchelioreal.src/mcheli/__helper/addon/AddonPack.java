/*     */ package mcheli.__helper.addon;
/*     */ 
/*     */ import com.google.common.base.Joiner;
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParser;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.Closeable;
/*     */ import java.io.File;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.util.List;
/*     */ import mcheli.__helper.MCH_Logger;
/*     */ import mcheli.__helper.io.ResourceLoader;
/*     */ import net.minecraft.util.JsonUtils;
/*     */ import org.apache.commons.io.IOUtils;
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
/*     */ public class AddonPack
/*     */ {
/*     */   private final String addonDomain;
/*     */   private final String addonName;
/*     */   private final String addonVersion;
/*     */   private File addonFile;
/*     */   private String credits;
/*     */   private List<String> authors;
/*     */   private String description;
/*     */   private String loaderVersion;
/*     */   protected ImmutableMap<String, JsonElement> packMetaMap;
/*     */   
/*     */   public AddonPack(String addonDomain, String addonName, String addonVersion, File addonFile, String credits, List<String> authors, String description, String loaderVersion, ImmutableMap<String, JsonElement> packMetaMap) {
/*  46 */     this.addonDomain = addonDomain;
/*  47 */     this.addonName = addonName;
/*  48 */     this.addonVersion = addonVersion;
/*  49 */     this.addonFile = addonFile;
/*  50 */     this.credits = credits;
/*  51 */     this.authors = authors;
/*  52 */     this.description = description;
/*  53 */     this.loaderVersion = loaderVersion;
/*  54 */     this.packMetaMap = packMetaMap;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDomain() {
/*  59 */     return this.addonDomain;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/*  64 */     return this.addonName;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getVersion() {
/*  69 */     return this.addonVersion;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getAuthorsString() {
/*  74 */     return Joiner.on(", ").join(this.authors);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getCredits() {
/*  79 */     return this.credits;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDescription() {
/*  84 */     return this.description;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getLoaderVersion() {
/*  89 */     return this.loaderVersion;
/*     */   }
/*     */ 
/*     */   
/*     */   public File getFile() {
/*  94 */     return this.addonFile;
/*     */   }
/*     */ 
/*     */   
/*     */   public ImmutableMap<String, JsonElement> getPackMetaMap() {
/*  99 */     return this.packMetaMap;
/*     */   }
/*     */ 
/*     */   
/*     */   public static AddonPack create(File addonFile) {
/* 104 */     JsonObject packMetaJson = loadPackMeta(addonFile);
/* 105 */     JsonObject packJson = JsonUtils.func_151218_a(packMetaJson, "pack", new JsonObject());
/* 106 */     JsonObject addonJson = JsonUtils.func_151218_a(packMetaJson, "addon", new JsonObject());
/*     */     
/* 108 */     String addonDomain = JsonUtils.func_151219_a(addonJson, "domain", null);
/* 109 */     String packName = JsonUtils.func_151219_a(packJson, "description", addonFile.getName());
/* 110 */     String version = JsonUtils.func_151219_a(addonJson, "version", "0.0");
/*     */     
/* 112 */     if (addonDomain == null) {
/*     */       
/* 114 */       MCH_Logger.get().warn("A addon domain is not specified! file:{}", addonFile.getName());
/* 115 */       addonDomain = "<!mcheli_share_domain>";
/*     */     } 
/*     */     
/* 118 */     String credits = JsonUtils.func_151219_a(addonJson, "credits", "");
/* 119 */     String description = JsonUtils.func_151219_a(addonJson, "description", "");
/* 120 */     String loaderVersion = JsonUtils.func_151219_a(addonJson, "loader_version", "1");
/* 121 */     List<String> authors = getAuthors(addonJson);
/*     */     
/* 123 */     return new AddonPack(addonDomain, packName, version, addonFile, credits, authors, description, loaderVersion, 
/* 124 */         ImmutableMap.copyOf(packMetaJson.entrySet()));
/*     */   }
/*     */ 
/*     */   
/*     */   private static List<String> getAuthors(JsonObject jsonObject) {
/* 129 */     List<String> list = Lists.newLinkedList();
/*     */     
/* 131 */     if (jsonObject.has("authors")) {
/*     */       
/* 133 */       JsonElement jsonElement = jsonObject.get("authors");
/*     */       
/* 135 */       if (jsonElement.isJsonArray())
/*     */       {
/* 137 */         for (JsonElement jsonElement1 : jsonElement.getAsJsonArray())
/*     */         {
/* 139 */           list.add(jsonElement1.getAsString());
/*     */         }
/*     */       }
/*     */     }
/* 143 */     else if (jsonObject.has("author")) {
/*     */       
/* 145 */       JsonElement jsonElement2 = jsonObject.get("author");
/*     */       
/* 147 */       if (jsonElement2.isJsonPrimitive())
/*     */       {
/* 149 */         list.add(jsonElement2.getAsString());
/*     */       }
/*     */     } 
/*     */     
/* 153 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   private static JsonObject loadPackMeta(File addonFile) {
/* 158 */     ResourceLoader loader = ResourceLoader.create(addonFile);
/* 159 */     BufferedReader bufferedReader = null;
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 164 */       bufferedReader = new BufferedReader(new InputStreamReader(loader.getInputStream("pack.mcmeta"), StandardCharsets.UTF_8));
/* 165 */       return (new JsonParser()).parse(bufferedReader).getAsJsonObject();
/*     */     }
/* 167 */     catch (FileNotFoundException e) {
/*     */       
/* 169 */       MCH_Logger.get().warn("'pack.mcmeta' does not found in '{}'", addonFile.getName());
/*     */     }
/* 171 */     catch (IOException e) {
/*     */       
/* 173 */       e.printStackTrace();
/*     */     }
/*     */     finally {
/*     */       
/* 177 */       IOUtils.closeQuietly(bufferedReader);
/* 178 */       IOUtils.closeQuietly((Closeable)loader);
/*     */     } 
/*     */     
/* 181 */     return new JsonObject();
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\__helper\addon\AddonPack.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */