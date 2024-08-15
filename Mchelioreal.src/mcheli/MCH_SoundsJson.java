/*     */ package mcheli;
/*     */ 
/*     */ import com.google.common.base.Joiner;
/*     */ import com.google.common.collect.ListMultimap;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.collect.Multimaps;
/*     */ import java.io.File;
/*     */ import java.util.List;
/*     */ import java.util.stream.Collectors;
/*     */ import mcheli.__helper.MCH_Utils;
/*     */ import mcheli.__helper.addon.GeneratedAddonPack;
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
/*     */ public class MCH_SoundsJson
/*     */ {
/*     */   public static void updateGenerated() {
/* 148 */     File soundsDir = new File(MCH_MOD.getSource().getPath() + "/assets/mcheli/sounds");
/* 149 */     File[] soundFiles = soundsDir.listFiles(f -> {
/*     */           String s = f.getName().toLowerCase();
/*     */           
/* 152 */           return (f.isFile() && s.length() >= 5 && s.substring(s.length() - 4).compareTo(".ogg") == 0);
/*     */         });
/* 154 */     ListMultimap listMultimap = Multimaps.newListMultimap(Maps.newLinkedHashMap(), Lists::newLinkedList);
/* 155 */     List<String> lines = Lists.newLinkedList();
/* 156 */     int cnt = 0;
/*     */     
/* 158 */     if (soundFiles != null) {
/*     */       
/* 160 */       for (File f : soundFiles) {
/*     */         
/* 162 */         String name = f.getName().toLowerCase();
/* 163 */         int ei = name.lastIndexOf(".");
/*     */         
/* 165 */         name = name.substring(0, ei);
/*     */         
/* 167 */         String key = name;
/* 168 */         char c = key.charAt(key.length() - 1);
/*     */         
/* 170 */         if (c >= '0' && c <= '9')
/*     */         {
/* 172 */           key = key.substring(0, key.length() - 1);
/*     */         }
/*     */         
/* 175 */         listMultimap.put(key, name);
/*     */       } 
/*     */       
/* 178 */       lines.add("{");
/* 179 */       for (String key : listMultimap.keySet()) {
/*     */         
/* 181 */         cnt++;
/* 182 */         String sounds = Joiner.on(",").join((Iterable)listMultimap.get(key).stream()
/* 183 */             .map(name -> '"' + MCH_Utils.suffix(name).toString() + '"').collect(Collectors.toList()));
/* 184 */         String line = "\"" + key + "\": {\"category\": \"master\",\"sounds\": [" + sounds + "]}";
/*     */         
/* 186 */         if (cnt < listMultimap.keySet().size()) {
/* 187 */           line = line + ",";
/*     */         }
/* 189 */         lines.add(line);
/*     */       } 
/* 191 */       lines.add("}");
/* 192 */       lines.add("");
/*     */     } 
/*     */     
/* 195 */     GeneratedAddonPack.instance().updateAssetFile("sounds.json", lines);
/* 196 */     MCH_Utils.logger().info("Update sounds.json, %d sounds.", Integer.valueOf(cnt));
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\MCH_SoundsJson.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */