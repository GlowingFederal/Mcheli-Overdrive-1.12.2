/*    */ package mcheli.__helper.addon;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.util.Arrays;
/*    */ import mcheli.MCH_MOD;
/*    */ import mcheli.MCH_OutputFile;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LegacyPackAssistant
/*    */ {
/* 16 */   private static File templeteAddonDir = null;
/*    */ 
/*    */   
/*    */   public static void generateDirectoryPack() {
/* 20 */     if (templeteAddonDir == null)
/*    */     {
/* 22 */       templeteAddonDir = new File(MCH_MOD.getAddonDir(), "/templete-addon/");
/*    */     }
/*    */     
/* 25 */     Arrays.<String>stream(new String[] { "helicopters", "planes", "tanks", "vehicles", "weapons", "throwable", "hud"
/*    */ 
/*    */         
/* 28 */         }).forEach(name -> {
/*    */           File dir = new File(templeteAddonDir, "/assets/mcheli/" + name + "/");
/*    */ 
/*    */           
/*    */           if (!dir.exists()) {
/*    */             dir.mkdirs();
/*    */           }
/*    */         });
/*    */ 
/*    */     
/* 38 */     Arrays.<String>stream(new String[] { "helicopters", "planes", "tanks", "vehicles", "bullets", "throwable"
/*    */ 
/*    */         
/* 41 */         }).forEach(name -> {
/*    */           File modelsDir = new File(templeteAddonDir, "/assets/mcheli/models/" + name + "/");
/*    */ 
/*    */           
/*    */           if (!modelsDir.exists()) {
/*    */             modelsDir.mkdirs();
/*    */           }
/*    */ 
/*    */           
/*    */           File texturesDir = new File(templeteAddonDir, "/assets/mcheli/textures/" + name + "/");
/*    */ 
/*    */           
/*    */           if (!texturesDir.exists()) {
/*    */             texturesDir.mkdirs();
/*    */           }
/*    */         });
/*    */ 
/*    */     
/* 59 */     MCH_OutputFile file = new MCH_OutputFile();
/*    */     
/* 61 */     if (file.openUTF8(templeteAddonDir.getPath() + "/pack.meta")) {
/*    */ 
/*    */       
/* 64 */       String[] lines = { "{", "  \"pack\": {", "    \"description\": \"Template addon\"", "  },", "  \"addon\": {", "    \"domain\": \"template_addon\",", "    \"version\": \"1.0\",", "    \"credits\": \"\",", "    \"description\": \"\",", "    \"loader_version\": \"1\",", "    \"authors\": []", "  }", "}" };
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 71 */       for (String s : lines)
/*    */       {
/* 73 */         file.writeLine(s);
/*    */       }
/*    */     } 
/* 76 */     file.close();
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\__helper\addon\LegacyPackAssistant.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */