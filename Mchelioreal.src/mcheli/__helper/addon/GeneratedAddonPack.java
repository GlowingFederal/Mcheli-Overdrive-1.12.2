/*    */ package mcheli.__helper.addon;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import com.google.common.collect.ImmutableMap;
/*    */ import java.io.File;
/*    */ import java.util.List;
/*    */ import mcheli.MCH_MOD;
/*    */ import mcheli.MCH_OutputFile;
/*    */ import mcheli.__helper.MCH_Utils;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GeneratedAddonPack
/*    */   extends AddonPack
/*    */ {
/* 20 */   private static GeneratedAddonPack instance = null;
/* 21 */   private static File generatedDir = null;
/*    */ 
/*    */   
/*    */   public static GeneratedAddonPack instance() {
/* 25 */     if (instance == null)
/*    */     {
/* 27 */       instance = new GeneratedAddonPack();
/*    */     }
/*    */     
/* 30 */     return instance;
/*    */   }
/*    */ 
/*    */   
/*    */   public static boolean isGeneratedAddonName(File file) {
/* 35 */     return "generated".equals(file.getName());
/*    */   }
/*    */ 
/*    */   
/*    */   private GeneratedAddonPack() {
/* 40 */     super("@generated", "Generated", "1.0", null, "EMB4-MCHeli", (List<String>)ImmutableList.of("EMB4", "Murachiki27"), "Generated addon(auto generate or update files)", "1", 
/* 41 */         ImmutableMap.of());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public File getFile() {
/* 47 */     if (generatedDir == null)
/*    */     {
/* 49 */       generatedDir = new File(MCH_MOD.getAddonDir(), "/generated/");
/*    */     }
/*    */     
/* 52 */     return generatedDir;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean updateAssetFile(String targetAssetPath, List<String> lines) {
/* 57 */     File assets = checkExistAssets();
/* 58 */     MCH_OutputFile file = new MCH_OutputFile();
/*    */     
/* 60 */     if (file.openUTF8(assets.getPath() + "/" + targetAssetPath)) {
/*    */       
/* 62 */       for (String s : lines)
/*    */       {
/* 64 */         file.writeLine(s);
/*    */       }
/* 66 */       file.close();
/* 67 */       MCH_Utils.logger().info("Update file:" + file.file.getAbsolutePath());
/* 68 */       return true;
/*    */     } 
/* 70 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public File checkMkdirsAssets(String dir) {
/* 75 */     File assets = new File(getFile(), "/assets/mcheli/" + dir + "/");
/*    */     
/* 77 */     if (!assets.exists())
/*    */     {
/* 79 */       assets.mkdirs();
/*    */     }
/* 81 */     return assets;
/*    */   }
/*    */ 
/*    */   
/*    */   private File checkExistAssets() {
/* 86 */     File assets = new File(getFile(), "/assets/mcheli/");
/*    */     
/* 88 */     if (!assets.exists())
/*    */     {
/* 90 */       assets.mkdirs();
/*    */     }
/*    */     
/* 93 */     return assets;
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\__helper\addon\GeneratedAddonPack.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */