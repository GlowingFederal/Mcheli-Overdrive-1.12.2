/*     */ package mcheli.__helper.addon;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.io.File;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.function.Predicate;
/*     */ import java.util.regex.Pattern;
/*     */ import javax.annotation.Nullable;
/*     */ import mcheli.MCH_MOD;
/*     */ import mcheli.__helper.MCH_Utils;
/*     */ import net.minecraftforge.fml.client.FMLClientHandler;
/*     */ import net.minecraftforge.fml.common.FMLModContainer;
/*     */ import net.minecraftforge.fml.common.MetadataCollection;
/*     */ import net.minecraftforge.fml.common.ModContainer;
/*     */ import net.minecraftforge.fml.common.discovery.ContainerType;
/*     */ import net.minecraftforge.fml.common.discovery.ModCandidate;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AddonManager
/*     */ {
/*  33 */   public static final Pattern ZIP_PATTERN = Pattern.compile("(.+).(zip|jar)$");
/*  34 */   private static final Map<String, AddonPack> ADDON_LIST = Maps.newLinkedHashMap();
/*     */   
/*     */   public static final String BUILTIN_ADDON_DOMAIN = "@builtin";
/*     */   
/*     */   @Nullable
/*     */   public static AddonPack get(String addonDomain) {
/*  40 */     if ("@builtin".equals(addonDomain))
/*     */     {
/*  42 */       return BuiltinAddonPack.instance();
/*     */     }
/*     */     
/*  45 */     return ADDON_LIST.get(addonDomain);
/*     */   }
/*     */ 
/*     */   
/*     */   public static List<AddonPack> getLoadedAddons() {
/*  50 */     return (List<AddonPack>)ImmutableList.builder().addAll(ADDON_LIST.values()).build();
/*     */   }
/*     */ 
/*     */   
/*     */   public static List<AddonPack> loadAddons(File addonDir) {
/*  55 */     checkExistAddonDir(addonDir);
/*     */     
/*  57 */     List<AddonPack> addons = Lists.newArrayList();
/*  58 */     File[] addonFiles = addonDir.listFiles();
/*     */     
/*  60 */     for (File addonFile : addonFiles) {
/*     */       
/*  62 */       if (validAddonPath(addonFile)) {
/*     */         
/*  64 */         AddonPack data = loadAddon(addonFile);
/*     */         
/*  66 */         if (data != null)
/*     */         {
/*  68 */           addons.add(data);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/*  73 */     MCH_Utils.logger().info("Load complete addons. count:" + addons.size());
/*     */     
/*  75 */     return addons;
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public static List<AddonPack> loadAddonsAndAddResources(File addonDir) {
/*  81 */     checkExistAddonDir(addonDir);
/*     */     
/*  83 */     List<AddonPack> addons = Lists.newArrayList();
/*  84 */     File[] addonFiles = addonDir.listFiles();
/*     */     
/*  86 */     for (File addonFile : addonFiles) {
/*     */       
/*  88 */       if (validAddonPath(addonFile)) {
/*     */         
/*  90 */         AddonPack data = loadAddonAndAddResource(addonFile, MCH_MOD.class);
/*     */         
/*  92 */         if (data != null)
/*     */         {
/*  94 */           addons.add(data);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/*  99 */     checkExistAddonDir(GeneratedAddonPack.instance().getFile());
/* 100 */     addReloadableResource(GeneratedAddonPack.instance(), MCH_MOD.class);
/*     */     
/* 102 */     FMLClientHandler.instance().refreshResources((Predicate)null);
/* 103 */     MCH_Utils.logger().info("Load complete addons and add resources. count:" + addons.size());
/*     */     
/* 105 */     return addons;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private static AddonPack loadAddon(File addonFile) {
/*     */     try {
/* 113 */       AddonPack addonPack = AddonPack.create(addonFile);
/*     */       
/* 115 */       ADDON_LIST.put(addonPack.getDomain(), addonPack);
/* 116 */       return addonPack;
/*     */     }
/* 118 */     catch (Exception e) {
/*     */       
/* 120 */       MCH_Utils.logger().error("Failed to load for pack:{} :", addonFile.getName(), e);
/* 121 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   @SideOnly(Side.CLIENT)
/*     */   private static AddonPack loadAddonAndAddResource(File addonFile, Class<?> clazz) {
/* 129 */     AddonPack addonPack = loadAddon(addonFile);
/*     */     
/* 131 */     if (addonPack == null)
/*     */     {
/* 133 */       return null;
/*     */     }
/*     */     
/* 136 */     addReloadableResource(addonPack, clazz);
/*     */     
/* 138 */     return addonPack;
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   private static void addReloadableResource(AddonPack addonPack, Class<?> clazz) {
/* 144 */     Map<String, Object> descriptor = Maps.newHashMap();
/*     */     
/* 146 */     descriptor.put("modid", "mcheli");
/* 147 */     descriptor.put("name", "MCHeli#" + addonPack.getName());
/* 148 */     descriptor.put("version", addonPack.getVersion());
/* 149 */     File file = addonPack.getFile();
/*     */ 
/*     */     
/* 152 */     FMLModContainer container = new FMLModContainer(clazz.getName(), new ModCandidate(file, file, file.isDirectory() ? ContainerType.DIR : ContainerType.JAR), descriptor);
/* 153 */     container.bindMetadata(MetadataCollection.from(null, ""));
/* 154 */     FMLClientHandler.instance().addModAsResource((ModContainer)container);
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean validAddonPath(File addonFile) {
/* 159 */     return (!GeneratedAddonPack.isGeneratedAddonName(addonFile) && (addonFile
/* 160 */       .isDirectory() || ZIP_PATTERN.matcher(addonFile.getName()).matches()));
/*     */   }
/*     */ 
/*     */   
/*     */   private static void checkExistAddonDir(File addonDir) {
/* 165 */     if (!addonDir.exists())
/*     */     {
/* 167 */       addonDir.mkdirs();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\__helper\addon\AddonManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */