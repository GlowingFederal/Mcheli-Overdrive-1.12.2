/*    */ package mcheli.wrapper;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.HashMap;
/*    */ import java.util.Locale;
/*    */ import mcheli.__helper.addon.GeneratedAddonPack;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraftforge.client.resource.IResourceType;
/*    */ import net.minecraftforge.client.resource.VanillaResourceType;
/*    */ import net.minecraftforge.fml.client.FMLClientHandler;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class W_LanguageRegistry
/*    */ {
/* 22 */   private static HashMap<String, ArrayList<String>> map = new HashMap<>();
/*    */ 
/*    */ 
/*    */   
/*    */   public static void addName(Object objectToName, String name) {
/* 27 */     addNameForObject(objectToName, "en_us", name);
/*    */   }
/*    */ 
/*    */   
/*    */   public static void addNameForObject(Object o, String lang, String name) {
/* 32 */     addNameForObject(o, lang, name, "", "");
/*    */   }
/*    */ 
/*    */   
/*    */   public static void addNameForObject(Object o, String lang, String name, String key, String desc) {
/* 37 */     if (o == null) {
/*    */       return;
/*    */     }
/*    */ 
/*    */ 
/*    */     
/* 43 */     lang = lang.toLowerCase(Locale.ROOT);
/*    */     
/* 45 */     if (!map.containsKey(lang))
/*    */     {
/* 47 */       map.put(lang, new ArrayList<>());
/*    */     }
/*    */     
/* 50 */     if (o instanceof Item)
/*    */     {
/* 52 */       ((ArrayList<String>)map.get(lang)).add(((Item)o).func_77658_a() + ".name=" + name);
/*    */     }
/*    */     
/* 55 */     if (o instanceof Block) {
/*    */       
/* 57 */       ((ArrayList<String>)map.get(lang)).add(((Block)o).func_149739_a() + ".name=" + name);
/*    */     
/*    */     }
/* 60 */     else if (o instanceof net.minecraft.advancements.Advancement) {
/*    */ 
/*    */ 
/*    */       
/* 64 */       ((ArrayList<String>)map.get(lang)).add("advancement." + key + "=" + name);
/* 65 */       ((ArrayList<String>)map.get(lang)).add("advancement." + key + ".desc=" + desc);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public static void clear() {
/* 71 */     map.clear();
/* 72 */     map = null;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static void updateGeneratedLang() {
/* 78 */     GeneratedAddonPack.instance().checkMkdirsAssets("lang");
/*    */     
/* 80 */     for (String key : map.keySet()) {
/*    */       
/* 82 */       ArrayList<String> list = map.get(key);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 94 */       GeneratedAddonPack.instance().updateAssetFile("lang/" + key + ".lang", list);
/*    */     } 
/*    */     
/* 97 */     FMLClientHandler.instance().refreshResources(resourceType -> (resourceType == VanillaResourceType.LANGUAGES));
/*    */     
/* 99 */     clear();
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\wrapper\W_LanguageRegistry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */