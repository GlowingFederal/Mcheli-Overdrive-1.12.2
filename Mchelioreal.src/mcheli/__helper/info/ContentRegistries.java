/*     */ package mcheli.__helper.info;
/*     */ 
/*     */ import com.google.common.collect.LinkedHashMultimap;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Multimap;
/*     */ import java.io.File;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.function.Predicate;
/*     */ import mcheli.MCH_MOD;
/*     */ import mcheli.__helper.addon.AddonManager;
/*     */ import mcheli.__helper.addon.AddonPack;
/*     */ import mcheli.__helper.addon.BuiltinAddonPack;
/*     */ import mcheli.helicopter.MCH_HeliInfo;
/*     */ import mcheli.hud.MCH_Hud;
/*     */ import mcheli.plane.MCP_PlaneInfo;
/*     */ import mcheli.tank.MCH_TankInfo;
/*     */ import mcheli.throwable.MCH_ThrowableInfo;
/*     */ import mcheli.vehicle.MCH_VehicleInfo;
/*     */ import mcheli.weapon.MCH_WeaponInfo;
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
/*     */ public class ContentRegistries
/*     */ {
/*  33 */   private static ContentRegistry<MCH_HeliInfo> REGISTORY_HELI = null;
/*  34 */   private static ContentRegistry<MCP_PlaneInfo> REGISTORY_PLANE = null;
/*  35 */   private static ContentRegistry<MCH_TankInfo> REGISTORY_TANK = null;
/*  36 */   private static ContentRegistry<MCH_VehicleInfo> REGISTORY_VEHICLE = null;
/*  37 */   private static ContentRegistry<MCH_WeaponInfo> REGISTORY_WEAPON = null;
/*  38 */   private static ContentRegistry<MCH_ThrowableInfo> REGISTORY_THROWABLE = null;
/*  39 */   private static ContentRegistry<MCH_Hud> REGISTORY_HUD = null;
/*     */ 
/*     */   
/*     */   public static ContentRegistry<MCH_HeliInfo> heli() {
/*  43 */     return REGISTORY_HELI;
/*     */   }
/*     */ 
/*     */   
/*     */   public static ContentRegistry<MCP_PlaneInfo> plane() {
/*  48 */     return REGISTORY_PLANE;
/*     */   }
/*     */ 
/*     */   
/*     */   public static ContentRegistry<MCH_TankInfo> tank() {
/*  53 */     return REGISTORY_TANK;
/*     */   }
/*     */ 
/*     */   
/*     */   public static ContentRegistry<MCH_VehicleInfo> vehicle() {
/*  58 */     return REGISTORY_VEHICLE;
/*     */   }
/*     */ 
/*     */   
/*     */   public static ContentRegistry<MCH_WeaponInfo> weapon() {
/*  63 */     return REGISTORY_WEAPON;
/*     */   }
/*     */ 
/*     */   
/*     */   public static ContentRegistry<MCH_ThrowableInfo> throwable() {
/*  68 */     return REGISTORY_THROWABLE;
/*     */   }
/*     */ 
/*     */   
/*     */   public static ContentRegistry<MCH_Hud> hud() {
/*  73 */     return REGISTORY_HUD;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T extends mcheli.MCH_BaseInfo> ContentRegistry<T> get(Class<T> clazz) {
/*  79 */     if (clazz == MCH_HeliInfo.class)
/*     */     {
/*  81 */       return (ContentRegistry)REGISTORY_HELI;
/*     */     }
/*  83 */     if (clazz == MCP_PlaneInfo.class)
/*     */     {
/*  85 */       return (ContentRegistry)REGISTORY_PLANE;
/*     */     }
/*  87 */     if (clazz == MCH_TankInfo.class)
/*     */     {
/*  89 */       return (ContentRegistry)REGISTORY_TANK;
/*     */     }
/*  91 */     if (clazz == MCH_VehicleInfo.class)
/*     */     {
/*  93 */       return (ContentRegistry)REGISTORY_VEHICLE;
/*     */     }
/*  95 */     if (clazz == MCH_WeaponInfo.class)
/*     */     {
/*  97 */       return (ContentRegistry)REGISTORY_WEAPON;
/*     */     }
/*  99 */     if (clazz == MCH_ThrowableInfo.class)
/*     */     {
/* 101 */       return (ContentRegistry)REGISTORY_THROWABLE;
/*     */     }
/* 103 */     if (clazz == MCH_Hud.class)
/*     */     {
/* 105 */       return (ContentRegistry)REGISTORY_HUD;
/*     */     }
/*     */     
/* 108 */     throw new RuntimeException("Unknown type:" + clazz);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void loadContents(File addonDir) {
/* 113 */     LinkedHashMultimap linkedHashMultimap = LinkedHashMultimap.create();
/* 114 */     List<AddonPack> addons = MCH_MOD.proxy.loadAddonPacks(addonDir);
/*     */     
/* 116 */     MCH_MOD.proxy.onLoadStartAddons(addons.size());
/*     */     
/* 118 */     linkedHashMultimap.putAll(loadAddonContents((AddonPack)BuiltinAddonPack.instance()));
/*     */     
/* 120 */     for (AddonPack pack : addons)
/*     */     {
/* 122 */       linkedHashMultimap.putAll(loadAddonContents(pack));
/*     */     }
/*     */     
/* 125 */     MCH_MOD.proxy.onLoadFinishAddons();
/*     */ 
/*     */     
/* 128 */     REGISTORY_HUD = parseContents(MCH_Hud.class, "hud", linkedHashMultimap.get(ContentType.HUD));
/*     */     
/* 130 */     REGISTORY_WEAPON = parseContents(MCH_WeaponInfo.class, "weapons", linkedHashMultimap.get(ContentType.WEAPON));
/*     */     
/* 132 */     REGISTORY_HELI = parseContents(MCH_HeliInfo.class, "helicopters", linkedHashMultimap.get(ContentType.HELICOPTER));
/* 133 */     REGISTORY_PLANE = parseContents(MCP_PlaneInfo.class, "planes", linkedHashMultimap.get(ContentType.PLANE));
/* 134 */     REGISTORY_TANK = parseContents(MCH_TankInfo.class, "tanks", linkedHashMultimap.get(ContentType.TANK));
/* 135 */     REGISTORY_VEHICLE = parseContents(MCH_VehicleInfo.class, "vehicles", linkedHashMultimap.get(ContentType.VEHICLE));
/* 136 */     REGISTORY_THROWABLE = parseContents(MCH_ThrowableInfo.class, "throwable", linkedHashMultimap.get(ContentType.THROWABLE));
/*     */   }
/*     */ 
/*     */   
/*     */   public static IContentData reparseContent(IContentData content, String dir) {
/* 141 */     AddonPack addonPack = AddonManager.get(content.getLoation().getAddonDomain());
/*     */     
/* 143 */     if (addonPack == null)
/*     */     {
/* 145 */       return content;
/*     */     }
/*     */     
/* 148 */     ContentLoader packLoader = getDefaultPackLoader(addonPack);
/*     */     
/* 150 */     return packLoader.reloadAndParseSingle(content, dir);
/*     */   }
/*     */ 
/*     */   
/*     */   static <T extends mcheli.MCH_BaseInfo> List<T> reloadAllAddonContents(ContentRegistry<T> registry) {
/* 155 */     List<T> list = Lists.newLinkedList();
/*     */     
/* 157 */     for (AddonPack addon : AddonManager.getLoadedAddons()) {
/*     */       
/* 159 */       ContentLoader packLoader = getPackLoader(addon, getFilterOnly(registry.getDirectoryName()));
/*     */       
/* 161 */       list.addAll(packLoader.reloadAndParse(registry.getType(), registry.values(), 
/* 162 */             ContentFactories.getFactory(registry.getDirectoryName())));
/*     */     } 
/*     */     
/* 165 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   private static Multimap<ContentType, ContentLoader.ContentEntry> loadAddonContents(AddonPack pack) {
/* 170 */     ContentLoader packLoader = getDefaultPackLoader(pack);
/*     */     
/* 172 */     MCH_MOD.proxy.onLoadStepAddon(pack.getDomain());
/* 173 */     return packLoader.load();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static <T extends mcheli.MCH_BaseInfo> ContentRegistry<T> parseContents(Class<T> clazz, String dir, Collection<ContentLoader.ContentEntry> values) {
/* 179 */     ContentRegistry.Builder<T> builder = ContentRegistry.builder(clazz, dir);
/*     */     
/* 181 */     MCH_MOD.proxy.onLoadStartContents(dir, values.size());
/*     */     
/* 183 */     for (ContentLoader.ContentEntry entry : values) {
/*     */       
/* 185 */       IContentData content = entry.parse();
/*     */       
/* 187 */       if (content != null)
/*     */       {
/* 189 */         builder.put(clazz.cast(content));
/*     */       }
/*     */     } 
/*     */     
/* 193 */     MCH_MOD.proxy.onLoadFinishContents(dir);
/*     */     
/* 195 */     return builder.build();
/*     */   }
/*     */ 
/*     */   
/*     */   public static ContentLoader getDefaultPackLoader(AddonPack pack) {
/* 200 */     return getPackLoader(pack, ContentRegistries::filter);
/*     */   }
/*     */ 
/*     */   
/*     */   public static ContentLoader getPackLoader(AddonPack pack, Predicate<String> fileFilter) {
/* 205 */     String loaderVersion = pack.getLoaderVersion();
/*     */     
/* 207 */     if (pack.getFile().isDirectory())
/*     */     {
/* 209 */       return new FolderContentLoader(pack.getDomain(), pack.getFile(), loaderVersion, fileFilter);
/*     */     }
/*     */ 
/*     */     
/* 213 */     return new FileContentLoader(pack.getDomain(), pack.getFile(), loaderVersion, fileFilter);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean filter(String filepath) {
/* 219 */     String[] split = filepath.split("/");
/* 220 */     String lootDir = (split.length >= 2) ? split[0] : "";
/*     */     
/* 222 */     if (lootDir.equals("assets") && split.length == 4) {
/*     */       
/* 224 */       String modDir = split[1];
/* 225 */       String infoDir = split[2];
/*     */       
/* 227 */       return (modDir.equals("mcheli") && MCH_MOD.proxy.canLoadContentDirName(infoDir));
/*     */     } 
/*     */     
/* 230 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private static Predicate<String> getFilterOnly(String dir) {
/* 235 */     return filepath -> {
/*     */         String[] split = filepath.split("/");
/*     */         
/*     */         String lootDir = (split.length >= 2) ? split[0] : "";
/*     */         
/*     */         if (lootDir.equals("assets") && split.length == 4) {
/*     */           String modDir = split[1];
/*     */           
/*     */           String infoDir = split[2];
/*     */           
/* 245 */           return (modDir.equals("mcheli") && dir.equals(infoDir));
/*     */         } 
/*     */         return false;
/*     */       };
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\__helper\info\ContentRegistries.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */