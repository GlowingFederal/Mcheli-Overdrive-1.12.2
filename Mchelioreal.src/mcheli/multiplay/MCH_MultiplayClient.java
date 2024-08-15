/*     */ package mcheli.multiplay;
/*     */ 
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.OutputStream;
/*     */ import java.nio.IntBuffer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Enumeration;
/*     */ import java.util.List;
/*     */ import java.util.jar.JarEntry;
/*     */ import java.util.jar.JarFile;
/*     */ import java.util.zip.ZipEntry;
/*     */ import javax.imageio.ImageIO;
/*     */ import mcheli.MCH_Config;
/*     */ import mcheli.MCH_FileSearch;
/*     */ import mcheli.MCH_Lib;
/*     */ import mcheli.MCH_OStream;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.client.renderer.texture.TextureUtil;
/*     */ import net.minecraft.client.shader.Framebuffer;
/*     */ import net.minecraft.util.text.TextFormatting;
/*     */ import net.minecraftforge.fml.common.Loader;
/*     */ import net.minecraftforge.fml.common.ModContainer;
/*     */ import net.minecraftforge.fml.relauncher.CoreModManager;
/*     */ import org.lwjgl.BufferUtils;
/*     */ import org.lwjgl.opengl.GL11;
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
/*     */ public class MCH_MultiplayClient
/*     */ {
/*     */   private static IntBuffer pixelBuffer;
/*     */   private static int[] pixelValues;
/*     */   private static MCH_OStream dataOutputStream;
/*     */   
/*     */   public static void startSendImageData() {
/*  51 */     Minecraft mc = Minecraft.func_71410_x();
/*  52 */     sendScreenShot(mc.field_71443_c, mc.field_71440_d, mc.func_147110_a());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void sendScreenShot(int displayWidth, int displayHeight, Framebuffer framebufferMc) {
/*     */     try {
/*  59 */       if (OpenGlHelper.func_148822_b()) {
/*     */         
/*  61 */         displayWidth = framebufferMc.field_147622_a;
/*  62 */         displayHeight = framebufferMc.field_147620_b;
/*     */       } 
/*     */       
/*  65 */       int k = displayWidth * displayHeight;
/*     */       
/*  67 */       if (pixelBuffer == null || pixelBuffer.capacity() < k) {
/*     */         
/*  69 */         pixelBuffer = BufferUtils.createIntBuffer(k);
/*  70 */         pixelValues = new int[k];
/*     */       } 
/*     */       
/*  73 */       GL11.glPixelStorei(3333, 1);
/*  74 */       GL11.glPixelStorei(3317, 1);
/*  75 */       pixelBuffer.clear();
/*     */       
/*  77 */       if (OpenGlHelper.func_148822_b()) {
/*     */         
/*  79 */         GL11.glBindTexture(3553, framebufferMc.field_147617_g);
/*  80 */         GL11.glGetTexImage(3553, 0, 32993, 33639, pixelBuffer);
/*     */       }
/*     */       else {
/*     */         
/*  84 */         GL11.glReadPixels(0, 0, displayWidth, displayHeight, 32993, 33639, pixelBuffer);
/*     */       } 
/*     */       
/*  87 */       pixelBuffer.get(pixelValues);
/*  88 */       TextureUtil.func_147953_a(pixelValues, displayWidth, displayHeight);
/*  89 */       BufferedImage bufferedimage = null;
/*     */       
/*  91 */       if (OpenGlHelper.func_148822_b()) {
/*     */         
/*  93 */         bufferedimage = new BufferedImage(framebufferMc.field_147621_c, framebufferMc.field_147618_d, 1);
/*  94 */         int l = framebufferMc.field_147620_b - framebufferMc.field_147618_d;
/*     */         
/*  96 */         for (int i1 = l; i1 < framebufferMc.field_147620_b; i1++)
/*     */         {
/*  98 */           for (int j1 = 0; j1 < framebufferMc.field_147621_c; j1++)
/*     */           {
/* 100 */             bufferedimage.setRGB(j1, i1 - l, pixelValues[i1 * framebufferMc.field_147622_a + j1]);
/*     */           }
/*     */         }
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 107 */         bufferedimage = new BufferedImage(displayWidth, displayHeight, 1);
/* 108 */         bufferedimage.setRGB(0, 0, displayWidth, displayHeight, pixelValues, 0, displayWidth);
/*     */       } 
/*     */       
/* 111 */       dataOutputStream = new MCH_OStream();
/* 112 */       ImageIO.write(bufferedimage, "png", (OutputStream)dataOutputStream);
/*     */     }
/* 114 */     catch (Exception exception) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void readImageData(DataOutputStream dos) throws IOException {
/* 121 */     dataOutputStream.write(dos);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void sendImageData() {
/* 126 */     if (dataOutputStream != null) {
/*     */       
/* 128 */       MCH_PacketLargeData.send();
/*     */       
/* 130 */       if (dataOutputStream.isDataEnd())
/*     */       {
/* 132 */         dataOutputStream = null;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static double getPerData() {
/* 139 */     return (dataOutputStream == null) ? -1.0D : (dataOutputStream.index / dataOutputStream.size());
/*     */   }
/*     */   
/* 142 */   private static List<String> modList = new ArrayList<>();
/*     */ 
/*     */   
/*     */   public static void readModList(String playerName, String commandSenderName) {
/* 146 */     modList = new ArrayList<>();
/*     */ 
/*     */ 
/*     */     
/* 150 */     modList.add(TextFormatting.RED + "###### Name:" + commandSenderName + " ######");
/* 151 */     modList.add(TextFormatting.RED + "###### ID  :" + playerName + " ######");
/*     */     
/* 153 */     String[] classFileNameList = System.getProperty("java.class.path").split(File.pathSeparator);
/*     */     
/* 155 */     for (String classFileName : classFileNameList) {
/*     */       
/* 157 */       MCH_Lib.DbgLog(true, "java.class.path=" + classFileName, new Object[0]);
/*     */       
/* 159 */       if (classFileName.length() > 1) {
/*     */         
/* 161 */         File javaClassFile = new File(classFileName);
/*     */         
/* 163 */         if (javaClassFile.getAbsolutePath().toLowerCase().indexOf("versions") >= 0)
/*     */         {
/*     */           
/* 166 */           modList.add(TextFormatting.AQUA + "# Client class=" + javaClassFile.getName() + " : file size= " + javaClassFile
/* 167 */               .length());
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 173 */     modList.add(TextFormatting.YELLOW + "=== ActiveModList ===");
/*     */ 
/*     */     
/* 176 */     for (ModContainer mod : Loader.instance().getActiveModList())
/*     */     {
/* 178 */       modList.add("" + mod + "  [" + mod.getModId() + "]  " + mod.getName() + "[" + mod.getDisplayVersion() + "]  " + mod
/* 179 */           .getSource().getName());
/*     */     }
/*     */     
/* 182 */     if (CoreModManager.getAccessTransformers().size() > 0) {
/*     */ 
/*     */       
/* 185 */       modList.add(TextFormatting.YELLOW + "=== AccessTransformers ===");
/*     */       
/* 187 */       for (String s : CoreModManager.getAccessTransformers())
/*     */       {
/* 189 */         modList.add(s);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 194 */     if (CoreModManager.getIgnoredMods().size() > 0) {
/*     */ 
/*     */       
/* 197 */       modList.add(TextFormatting.YELLOW + "=== LoadedCoremods ===");
/*     */ 
/*     */       
/* 200 */       for (String s : CoreModManager.getIgnoredMods())
/*     */       {
/* 202 */         modList.add(s);
/*     */       }
/*     */     } 
/*     */     
/* 206 */     if (CoreModManager.getReparseableCoremods().size() > 0) {
/*     */ 
/*     */       
/* 209 */       modList.add(TextFormatting.YELLOW + "=== ReparseableCoremods ===");
/*     */       
/* 211 */       for (String s : CoreModManager.getReparseableCoremods())
/*     */       {
/* 213 */         modList.add(s);
/*     */       }
/*     */     } 
/*     */     
/* 217 */     Minecraft mc = Minecraft.func_71410_x();
/* 218 */     MCH_FileSearch search = new MCH_FileSearch();
/* 219 */     File[] files = search.listFiles((new File(mc.field_71412_D, "mods")).getAbsolutePath(), "*.jar");
/*     */ 
/*     */     
/* 222 */     modList.add(TextFormatting.YELLOW + "=== Manifest ===");
/*     */     
/* 224 */     for (File file : files) {
/*     */ 
/*     */       
/*     */       try {
/* 228 */         String jarPath = file.getCanonicalPath();
/* 229 */         JarFile jarFile = new JarFile(jarPath);
/* 230 */         Enumeration<JarEntry> jarEntries = jarFile.entries();
/* 231 */         String manifest = "";
/*     */         
/* 233 */         while (jarEntries.hasMoreElements()) {
/*     */           
/* 235 */           ZipEntry zipEntry = jarEntries.nextElement();
/*     */           
/* 237 */           if (zipEntry.getName().equalsIgnoreCase("META-INF/MANIFEST.MF") && !zipEntry.isDirectory()) {
/*     */             
/* 239 */             InputStream is = jarFile.getInputStream(zipEntry);
/* 240 */             BufferedReader br = new BufferedReader(new InputStreamReader(is));
/* 241 */             String line = br.readLine();
/*     */             
/* 243 */             while (line != null) {
/*     */               
/* 245 */               line = line.replace(" ", "").trim();
/*     */               
/* 247 */               if (!line.isEmpty())
/*     */               {
/* 249 */                 manifest = manifest + " [" + line + "]";
/*     */               }
/*     */               
/* 252 */               line = br.readLine();
/*     */             } 
/*     */             
/* 255 */             is.close();
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/* 260 */         jarFile.close();
/*     */         
/* 262 */         if (!manifest.isEmpty())
/*     */         {
/* 264 */           modList.add(file.getName() + manifest);
/*     */         }
/*     */       }
/* 267 */       catch (Exception e) {
/*     */         
/* 269 */         modList.add(file.getName() + " : Read Manifest failed.");
/*     */       } 
/*     */     } 
/*     */     
/* 273 */     search = new MCH_FileSearch();
/* 274 */     files = search.listFiles((new File(mc.field_71412_D, "mods")).getAbsolutePath(), "*.litemod");
/*     */     
/* 276 */     modList.add(TextFormatting.LIGHT_PURPLE + "=== LiteLoader ===");
/*     */     
/* 278 */     for (File file : files) {
/*     */ 
/*     */       
/*     */       try {
/* 282 */         String jarPath = file.getCanonicalPath();
/* 283 */         JarFile jarFile = new JarFile(jarPath);
/* 284 */         Enumeration<JarEntry> jarEntries = jarFile.entries();
/* 285 */         String litemod_json = "";
/*     */         
/* 287 */         while (jarEntries.hasMoreElements()) {
/*     */           
/* 289 */           ZipEntry zipEntry = jarEntries.nextElement();
/* 290 */           String fname = zipEntry.getName().toLowerCase();
/*     */           
/* 292 */           if (!zipEntry.isDirectory()) {
/* 293 */             if (fname.equals("litemod.json")) {
/*     */               
/* 295 */               InputStream is = jarFile.getInputStream(zipEntry);
/* 296 */               BufferedReader br = new BufferedReader(new InputStreamReader(is));
/* 297 */               String line = br.readLine();
/*     */               
/* 299 */               while (line != null) {
/*     */                 
/* 301 */                 line = line.replace(" ", "").trim();
/*     */                 
/* 303 */                 if (line.toLowerCase().indexOf("name") >= 0) {
/*     */                   
/* 305 */                   litemod_json = litemod_json + " [" + line + "]";
/*     */                   
/*     */                   break;
/*     */                 } 
/* 309 */                 line = br.readLine();
/*     */               } 
/*     */               
/* 312 */               is.close();
/*     */               
/*     */               continue;
/*     */             } 
/* 316 */             int index = fname.lastIndexOf("/");
/*     */             
/* 318 */             if (index >= 0)
/*     */             {
/* 320 */               fname = fname.substring(index + 1);
/*     */             }
/*     */             
/* 323 */             if (fname.indexOf("litemod") >= 0 && fname.endsWith("class")) {
/*     */               
/* 325 */               fname = zipEntry.getName();
/*     */               
/* 327 */               if (index >= 0)
/*     */               {
/* 329 */                 fname = fname.substring(index + 1);
/*     */               }
/*     */               
/* 332 */               litemod_json = litemod_json + " [" + fname + "]";
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 337 */         jarFile.close();
/*     */         
/* 339 */         if (!litemod_json.isEmpty())
/*     */         {
/* 341 */           modList.add(file.getName() + litemod_json);
/*     */         }
/*     */       }
/* 344 */       catch (Exception e) {
/*     */         
/* 346 */         modList.add(file.getName() + " : Read LiteLoader failed.");
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void sendModsInfo(String playerName, String commandSenderName, int id) {
/* 353 */     if (MCH_Config.DebugLog) {
/*     */       
/* 355 */       modList.clear();
/* 356 */       readModList(playerName, commandSenderName);
/*     */     } 
/* 358 */     MCH_PacketModList.send(modList, id);
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\multiplay\MCH_MultiplayClient.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */