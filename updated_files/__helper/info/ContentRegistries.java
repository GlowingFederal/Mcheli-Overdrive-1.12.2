package mcheli.__helper.info;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import mcheli.MCH_MOD;
import mcheli.__helper.addon.AddonManager;
import mcheli.__helper.addon.AddonPack;
import mcheli.__helper.addon.BuiltinAddonPack;
import mcheli.helicopter.MCH_HeliInfo;
import mcheli.hud.MCH_Hud;
import mcheli.plane.MCP_PlaneInfo;
import mcheli.tank.MCH_TankInfo;
import mcheli.throwable.MCH_ThrowableInfo;
import mcheli.vehicle.MCH_VehicleInfo;
import mcheli.weapon.MCH_WeaponInfo;

public class ContentRegistries {
  private static ContentRegistry<MCH_HeliInfo> REGISTORY_HELI = null;
  
  private static ContentRegistry<MCP_PlaneInfo> REGISTORY_PLANE = null;
  
  private static ContentRegistry<MCH_TankInfo> REGISTORY_TANK = null;
  
  private static ContentRegistry<MCH_VehicleInfo> REGISTORY_VEHICLE = null;
  
  private static ContentRegistry<MCH_WeaponInfo> REGISTORY_WEAPON = null;
  
  private static ContentRegistry<MCH_ThrowableInfo> REGISTORY_THROWABLE = null;
  
  private static ContentRegistry<MCH_Hud> REGISTORY_HUD = null;
  
  public static ContentRegistry<MCH_HeliInfo> heli() {
    return REGISTORY_HELI;
  }
  
  public static ContentRegistry<MCP_PlaneInfo> plane() {
    return REGISTORY_PLANE;
  }
  
  public static ContentRegistry<MCH_TankInfo> tank() {
    return REGISTORY_TANK;
  }
  
  public static ContentRegistry<MCH_VehicleInfo> vehicle() {
    return REGISTORY_VEHICLE;
  }
  
  public static ContentRegistry<MCH_WeaponInfo> weapon() {
    return REGISTORY_WEAPON;
  }
  
  public static ContentRegistry<MCH_ThrowableInfo> throwable() {
    return REGISTORY_THROWABLE;
  }
  
  public static ContentRegistry<MCH_Hud> hud() {
    return REGISTORY_HUD;
  }
  
  public static <T extends mcheli.MCH_BaseInfo> ContentRegistry<T> get(Class<T> clazz) {
    if (clazz == MCH_HeliInfo.class)
      return (ContentRegistry)REGISTORY_HELI; 
    if (clazz == MCP_PlaneInfo.class)
      return (ContentRegistry)REGISTORY_PLANE; 
    if (clazz == MCH_TankInfo.class)
      return (ContentRegistry)REGISTORY_TANK; 
    if (clazz == MCH_VehicleInfo.class)
      return (ContentRegistry)REGISTORY_VEHICLE; 
    if (clazz == MCH_WeaponInfo.class)
      return (ContentRegistry)REGISTORY_WEAPON; 
    if (clazz == MCH_ThrowableInfo.class)
      return (ContentRegistry)REGISTORY_THROWABLE; 
    if (clazz == MCH_Hud.class)
      return (ContentRegistry)REGISTORY_HUD; 
    throw new RuntimeException("Unknown type:" + clazz);
  }
  
  public static void loadContents(File addonDir) {
    LinkedHashMultimap linkedHashMultimap = LinkedHashMultimap.create();
    List<AddonPack> addons = MCH_MOD.proxy.loadAddonPacks(addonDir);
    MCH_MOD.proxy.onLoadStartAddons(addons.size());
    linkedHashMultimap.putAll(loadAddonContents((AddonPack)BuiltinAddonPack.instance()));
    for (AddonPack pack : addons)
      linkedHashMultimap.putAll(loadAddonContents(pack)); 
    MCH_MOD.proxy.onLoadFinishAddons();
    REGISTORY_HUD = parseContents(MCH_Hud.class, "hud", linkedHashMultimap.get(ContentType.HUD));
    REGISTORY_WEAPON = parseContents(MCH_WeaponInfo.class, "weapons", linkedHashMultimap.get(ContentType.WEAPON));
    REGISTORY_HELI = parseContents(MCH_HeliInfo.class, "helicopters", linkedHashMultimap.get(ContentType.HELICOPTER));
    REGISTORY_PLANE = parseContents(MCP_PlaneInfo.class, "planes", linkedHashMultimap.get(ContentType.PLANE));
    REGISTORY_TANK = parseContents(MCH_TankInfo.class, "tanks", linkedHashMultimap.get(ContentType.TANK));
    REGISTORY_VEHICLE = parseContents(MCH_VehicleInfo.class, "vehicles", linkedHashMultimap.get(ContentType.VEHICLE));
    REGISTORY_THROWABLE = parseContents(MCH_ThrowableInfo.class, "throwable", linkedHashMultimap.get(ContentType.THROWABLE));
  }
  
  public static IContentData reparseContent(IContentData content, String dir) {
    AddonPack addonPack = AddonManager.get(content.getLoation().getAddonDomain());
    if (addonPack == null)
      return content; 
    ContentLoader packLoader = getDefaultPackLoader(addonPack);
    return packLoader.reloadAndParseSingle(content, dir);
  }
  
  static <T extends mcheli.MCH_BaseInfo> List<T> reloadAllAddonContents(ContentRegistry<T> registry) {
    List<T> list = Lists.newLinkedList();
    for (AddonPack addon : AddonManager.getLoadedAddons()) {
      ContentLoader packLoader = getPackLoader(addon, getFilterOnly(registry.getDirectoryName()));
      list.addAll(packLoader.reloadAndParse(registry.getType(), registry.values(), 
            ContentFactories.getFactory(registry.getDirectoryName())));
    } 
    return list;
  }
  
  private static Multimap<ContentType, ContentLoader.ContentEntry> loadAddonContents(AddonPack pack) {
    ContentLoader packLoader = getDefaultPackLoader(pack);
    MCH_MOD.proxy.onLoadStepAddon(pack.getDomain());
    return packLoader.load();
  }
  
  private static <T extends mcheli.MCH_BaseInfo> ContentRegistry<T> parseContents(Class<T> clazz, String dir, Collection<ContentLoader.ContentEntry> values) {
    ContentRegistry.Builder<T> builder = ContentRegistry.builder(clazz, dir);
    MCH_MOD.proxy.onLoadStartContents(dir, values.size());
    for (ContentLoader.ContentEntry entry : values) {
      IContentData content = entry.parse();
      if (content != null)
        builder.put(clazz.cast(content)); 
    } 
    MCH_MOD.proxy.onLoadFinishContents(dir);
    return builder.build();
  }
  
  public static ContentLoader getDefaultPackLoader(AddonPack pack) {
    return getPackLoader(pack, ContentRegistries::filter);
  }
  
  public static ContentLoader getPackLoader(AddonPack pack, Predicate<String> fileFilter) {
    String loaderVersion = pack.getLoaderVersion();
    if (pack.getFile().isDirectory())
      return new FolderContentLoader(pack.getDomain(), pack.getFile(), loaderVersion, fileFilter); 
    return new FileContentLoader(pack.getDomain(), pack.getFile(), loaderVersion, fileFilter);
  }
  
  private static boolean filter(String filepath) {
    String[] split = filepath.split("/");
    String lootDir = (split.length >= 2) ? split[0] : "";
    if (lootDir.equals("assets") && split.length == 4) {
      String modDir = split[1];
      String infoDir = split[2];
      return (modDir.equals("mcheli") && MCH_MOD.proxy.canLoadContentDirName(infoDir));
    } 
    return false;
  }
  
  private static Predicate<String> getFilterOnly(String dir) {
    return filepath -> {
        String[] split = filepath.split("/");
        String lootDir = (split.length >= 2) ? split[0] : "";
        if (lootDir.equals("assets") && split.length == 4) {
          String modDir = split[1];
          String infoDir = split[2];
          return (modDir.equals("mcheli") && dir.equals(infoDir));
        } 
        return false;
      };
  }
}
