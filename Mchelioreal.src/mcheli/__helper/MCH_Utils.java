/*    */ package mcheli.__helper;
/*    */ 
/*    */ import java.io.File;
/*    */ import javax.annotation.Nullable;
/*    */ import mcheli.MCH_MOD;
/*    */ import mcheli.__helper.addon.AddonResourceLocation;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraftforge.fml.common.FMLCommonHandler;
/*    */ import org.apache.logging.log4j.Logger;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MCH_Utils
/*    */ {
/*    */   public static ResourceLocation suffix(String name) {
/* 31 */     return new ResourceLocation("mcheli", name);
/*    */   }
/*    */ 
/*    */   
/*    */   public static AddonResourceLocation addon(String domain, String path) {
/* 36 */     return new AddonResourceLocation(suffix(path), domain);
/*    */   }
/*    */ 
/*    */   
/*    */   public static AddonResourceLocation buildinAddon(String path) {
/* 41 */     return new AddonResourceLocation(suffix(path), "@builtin");
/*    */   }
/*    */ 
/*    */   
/*    */   public static File getSource() {
/* 46 */     return MCH_MOD.getSource();
/*    */   }
/*    */ 
/*    */   
/*    */   public static boolean isClient() {
/* 51 */     return MCH_MOD.proxy.isRemote();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static MinecraftServer getServer() {
/* 59 */     return FMLCommonHandler.instance().getMinecraftServerInstance();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static Logger logger() {
/* 67 */     return MCH_Logger.get();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static <T> boolean inArray(T[] objArray, @Nullable T target) {
/* 75 */     for (T obj : objArray) {
/*    */       
/* 77 */       if ((target == null) ? (obj == null) : target.equals(obj))
/*    */       {
/* 79 */         return true;
/*    */       }
/*    */     } 
/* 82 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\__helper\MCH_Utils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */