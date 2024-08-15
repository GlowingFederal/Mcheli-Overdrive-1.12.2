/*     */ package mcheli.__helper.addon;
/*     */ 
/*     */ import com.google.common.base.Strings;
/*     */ import java.util.Locale;
/*     */ import mcheli.__helper.MCH_Utils;
/*     */ import net.minecraft.util.ResourceLocation;
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
/*     */ public class AddonResourceLocation
/*     */   extends ResourceLocation
/*     */ {
/*  23 */   public static final AddonResourceLocation EMPTY_LOCATION = new AddonResourceLocation();
/*     */ 
/*     */   
/*     */   public static final String SHARE_DOMAIN = "<!mcheli_share_domain>";
/*     */   
/*     */   public static final char SEPARATOR = '|';
/*     */   
/*     */   private final String addonDomain;
/*     */   
/*     */   private final boolean isEmpty;
/*     */ 
/*     */   
/*     */   private AddonResourceLocation() {
/*  36 */     super(0, new String[] { "empty", "empty" });
/*  37 */     this.addonDomain = "@empty";
/*  38 */     this.isEmpty = true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected AddonResourceLocation(int unused, String... resourceName) {
/*  43 */     super(unused, new String[] { resourceName[0], resourceName[2] });
/*  44 */     this.addonDomain = resourceName[1];
/*  45 */     this.isEmpty = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public AddonResourceLocation(String resourceName) {
/*  50 */     this(0, parsePath(resourceName));
/*     */   }
/*     */ 
/*     */   
/*     */   public AddonResourceLocation(String resourceName, String addonDomainIn) {
/*  55 */     this(0, parsePath(addonDomainIn, resourceName));
/*     */   }
/*     */ 
/*     */   
/*     */   public AddonResourceLocation(ResourceLocation resourceLocation, String addonDomainIn) {
/*  60 */     this(resourceLocation.toString(), addonDomainIn);
/*     */   }
/*     */ 
/*     */   
/*     */   public AddonResourceLocation(String resourceDomainIn, String addonDomainIn, String resourcePathIn) {
/*  65 */     this(0, parsePath(resourceDomainIn + ":" + addonDomainIn + '|' + resourcePathIn));
/*     */   }
/*     */ 
/*     */   
/*     */   protected static String[] parsePath(String pathIn) {
/*  70 */     String[] spl = func_177516_a(pathIn);
/*  71 */     String[] ret = { spl[0], null, spl[1] };
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  76 */     int i = ret[2].indexOf('|');
/*     */     
/*  78 */     if (i >= 0) {
/*     */       
/*  80 */       ret[1] = ret[2].substring(0, i);
/*  81 */       ret[2] = ret[2].substring(i + 1);
/*     */     } 
/*     */     
/*  84 */     ret[1] = Strings.isNullOrEmpty(ret[1]) ? "<!mcheli_share_domain>" : ret[1].toLowerCase(Locale.ROOT);
/*     */     
/*  86 */     if (ret[0].equals("minecraft")) {
/*     */       
/*  88 */       ret[0] = "mcheli";
/*     */     }
/*  90 */     else if (!spl[0].equals("mcheli")) {
/*     */       
/*  92 */       MCH_Utils.logger().warn("Invalid mod domain '{}', replace at '{}'. path:'{}'", ret[0], "mcheli", ret[2]);
/*     */       
/*  94 */       ret[0] = "mcheli";
/*     */     } 
/*     */     
/*  97 */     return ret;
/*     */   }
/*     */ 
/*     */   
/*     */   protected static String[] parsePath(String addonDomain, String pathIn) {
/* 102 */     String[] spl = func_177516_a(pathIn);
/* 103 */     String[] ret = { spl[0], addonDomain, spl[1] };
/*     */ 
/*     */ 
/*     */     
/* 107 */     return ret;
/*     */   }
/*     */ 
/*     */   
/*     */   public static AddonResourceLocation share(ResourceLocation location) {
/* 112 */     return share(location.func_110624_b(), location.func_110623_a());
/*     */   }
/*     */ 
/*     */   
/*     */   public static AddonResourceLocation share(String modid, String location) {
/* 117 */     return new AddonResourceLocation(modid, "<!mcheli_share_domain>", location);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getAddonDomain() {
/* 122 */     return this.addonDomain;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getAddonLocation() {
/* 130 */     return this.addonDomain + '|' + this.field_110625_b;
/*     */   }
/*     */ 
/*     */   
/*     */   public ResourceLocation asVanillaLocation() {
/* 135 */     return new ResourceLocation(this.field_110626_a, getAddonLocation());
/*     */   }
/*     */ 
/*     */   
/*     */   public ResourceLocation asVanillaDomainPath() {
/* 140 */     return new ResourceLocation(this.field_110626_a, this.field_110625_b);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isShareDomain() {
/* 145 */     return this.addonDomain.equals("<!mcheli_share_domain>");
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEmptyLocation() {
/* 150 */     return (this.isEmpty || equals(EMPTY_LOCATION));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 156 */     return 31 * (31 * super.hashCode() + this.addonDomain.hashCode()) + Boolean.hashCode(this.isEmpty);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object p_equals_1_) {
/* 162 */     if (this == p_equals_1_)
/*     */     {
/* 164 */       return true;
/*     */     }
/* 166 */     if (p_equals_1_ instanceof AddonResourceLocation && super.equals(p_equals_1_)) {
/*     */       
/* 168 */       AddonResourceLocation location = (AddonResourceLocation)p_equals_1_;
/*     */       
/* 170 */       return (location.addonDomain.equals(this.addonDomain) && location.isEmpty == this.isEmpty);
/*     */     } 
/*     */ 
/*     */     
/* 174 */     return false;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equalsIgnore(ResourceLocation location) {
/* 200 */     if (this == location)
/*     */     {
/* 202 */       return true;
/*     */     }
/* 204 */     if (super.equals(location)) {
/*     */       
/* 206 */       if (isShareDomain())
/*     */       {
/* 208 */         return true;
/*     */       }
/* 210 */       if (location instanceof AddonResourceLocation) {
/*     */         
/* 212 */         AddonResourceLocation other = (AddonResourceLocation)location;
/*     */         
/* 214 */         if (other.isShareDomain())
/*     */         {
/* 216 */           return true;
/*     */         }
/*     */ 
/*     */         
/* 220 */         return other.addonDomain.equals(this.addonDomain);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 225 */       return false;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 230 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int compareTo(ResourceLocation p_compareTo_1_) {
/* 237 */     int i = this.field_110626_a.compareTo(p_compareTo_1_.func_110624_b());
/*     */     
/* 239 */     if (i == 0 && p_compareTo_1_ instanceof AddonResourceLocation)
/*     */     {
/* 241 */       i = this.addonDomain.compareTo(((AddonResourceLocation)p_compareTo_1_).addonDomain);
/*     */     }
/*     */     
/* 244 */     if (i == 0)
/*     */     {
/* 246 */       i = this.field_110625_b.compareTo(p_compareTo_1_.func_110623_a());
/*     */     }
/*     */     
/* 249 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 255 */     return this.field_110626_a + ":" + this.addonDomain + '|' + this.field_110625_b;
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\__helper\addon\AddonResourceLocation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */