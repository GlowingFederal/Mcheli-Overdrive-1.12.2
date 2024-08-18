package mcheli.wrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import mcheli.__helper.addon.GeneratedAddonPack;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.resource.IResourceType;
import net.minecraftforge.client.resource.VanillaResourceType;
import net.minecraftforge.fml.client.FMLClientHandler;

public class W_LanguageRegistry {
  private static HashMap<String, ArrayList<String>> map = new HashMap<>();
  
  public static void addName(Object objectToName, String name) {
    addNameForObject(objectToName, "en_us", name);
  }
  
  public static void addNameForObject(Object o, String lang, String name) {
    addNameForObject(o, lang, name, "", "");
  }
  
  public static void addNameForObject(Object o, String lang, String name, String key, String desc) {
    if (o == null)
      return; 
    lang = lang.toLowerCase(Locale.ROOT);
    if (!map.containsKey(lang))
      map.put(lang, new ArrayList<>()); 
    if (o instanceof Item)
      ((ArrayList<String>)map.get(lang)).add(((Item)o).func_77658_a() + ".name=" + name); 
    if (o instanceof Block) {
      ((ArrayList<String>)map.get(lang)).add(((Block)o).func_149739_a() + ".name=" + name);
    } else if (o instanceof net.minecraft.advancements.Advancement) {
      ((ArrayList<String>)map.get(lang)).add("advancement." + key + "=" + name);
      ((ArrayList<String>)map.get(lang)).add("advancement." + key + ".desc=" + desc);
    } 
  }
  
  public static void clear() {
    map.clear();
    map = null;
  }
  
  public static void updateGeneratedLang() {
    GeneratedAddonPack.instance().checkMkdirsAssets("lang");
    for (String key : map.keySet()) {
      ArrayList<String> list = map.get(key);
      GeneratedAddonPack.instance().updateAssetFile("lang/" + key + ".lang", list);
    } 
    FMLClientHandler.instance().refreshResources(resourceType -> (resourceType == VanillaResourceType.LANGUAGES));
    clear();
  }
}
