/*     */ package mcheli;
/*     */ 
/*     */ import java.util.List;
/*     */ import mcheli.__helper.addon.AddonResourceLocation;
/*     */ import mcheli.__helper.info.ContentParseException;
/*     */ import mcheli.__helper.info.IContentData;
/*     */ import net.minecraft.util.math.Vec3d;
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
/*     */ public abstract class MCH_BaseInfo
/*     */   implements IContentData
/*     */ {
/*     */   public final String filePath;
/*     */   public final AddonResourceLocation location;
/*     */   
/*     */   public MCH_BaseInfo(AddonResourceLocation location, String filePath) {
/*  25 */     this.location = location;
/*  26 */     this.filePath = filePath;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean toBool(String s) {
/*  31 */     return s.equalsIgnoreCase("true");
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean toBool(String s, boolean defaultValue) {
/*  36 */     if (s.equalsIgnoreCase("true"))
/*  37 */       return true; 
/*  38 */     if (s.equalsIgnoreCase("false"))
/*  39 */       return false; 
/*  40 */     return defaultValue;
/*     */   }
/*     */ 
/*     */   
/*     */   public float toFloat(String s) {
/*  45 */     return Float.parseFloat(s);
/*     */   }
/*     */ 
/*     */   
/*     */   public float toFloat(String s, float min, float max) {
/*  50 */     float f = Float.parseFloat(s);
/*  51 */     return (f > max) ? max : ((f < min) ? min : f);
/*     */   }
/*     */ 
/*     */   
/*     */   public double toDouble(String s) {
/*  56 */     return Double.parseDouble(s);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Vec3d toVec3(String x, String y, String z) {
/*  62 */     return new Vec3d(toDouble(x), toDouble(y), toDouble(z));
/*     */   }
/*     */ 
/*     */   
/*     */   public int toInt(String s) {
/*  67 */     return Integer.parseInt(s);
/*     */   }
/*     */ 
/*     */   
/*     */   public int toInt(String s, int min, int max) {
/*  72 */     int f = Integer.parseInt(s);
/*  73 */     return (f > max) ? max : ((f < min) ? min : f);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hex2dec(String s) {
/*  78 */     if (!s.startsWith("0x") && !s.startsWith("0X") && s.indexOf(false) != 35)
/*     */     {
/*     */       
/*  81 */       return (int)(Long.decode("0x" + s).longValue() & 0xFFFFFFFFFFFFFFFFL);
/*     */     }
/*     */     
/*  84 */     return (int)(Long.decode(s).longValue() & 0xFFFFFFFFFFFFFFFFL);
/*     */   }
/*     */ 
/*     */   
/*     */   public String[] splitParam(String data) {
/*  89 */     return data.split("\\s*,\\s*");
/*     */   }
/*     */ 
/*     */   
/*     */   public String[] splitParamSlash(String data) {
/*  94 */     return data.split("\\s*/\\s*");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean validate() throws Exception {
/* 101 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void loadItemData(String item, String data) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onPostReload() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canReloadItem(String item) {
/* 122 */     return false;
/*     */   }
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
/*     */   public void parse(List<String> lines, String fileExtension, boolean reload) throws Exception {
/* 141 */     if ("txt".equals(fileExtension)) {
/*     */       
/* 143 */       int line = 0;
/*     */ 
/*     */       
/*     */       try {
/* 147 */         for (String str : lines)
/*     */         {
/* 149 */           line++;
/* 150 */           str = str.trim();
/*     */           
/* 152 */           int eqIdx = str.indexOf('=');
/*     */           
/* 154 */           if (eqIdx >= 0 && str.length() > eqIdx + 1)
/*     */           {
/* 156 */             loadItemData(str.substring(0, eqIdx).trim().toLowerCase(), str
/* 157 */                 .substring(eqIdx + 1).trim());
/*     */           }
/*     */         }
/*     */       
/* 161 */       } catch (Exception e) {
/*     */         
/* 163 */         throw new ContentParseException(e, line);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AddonResourceLocation getLoation() {
/* 171 */     return this.location;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getContentPath() {
/* 177 */     return this.filePath;
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\MCH_BaseInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */