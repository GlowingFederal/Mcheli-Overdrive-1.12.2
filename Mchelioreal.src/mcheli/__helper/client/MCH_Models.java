/*    */ package mcheli.__helper.client;
/*    */ 
/*    */ import java.io.FileNotFoundException;
/*    */ import java.io.IOException;
/*    */ import mcheli.__helper.MCH_Utils;
/*    */ import mcheli.__helper.client.model.loader.IVertexModelLoader;
/*    */ import mcheli.__helper.client.model.loader.MetasequoiaModelLoader;
/*    */ import mcheli.__helper.client.model.loader.TechneModelLoader;
/*    */ import mcheli.__helper.client.model.loader.WavefrontModelLoader;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.resources.IResourceManager;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class MCH_Models
/*    */ {
/* 25 */   private static IVertexModelLoader objLoader = (IVertexModelLoader)new WavefrontModelLoader();
/* 26 */   private static IVertexModelLoader mqoLoader = (IVertexModelLoader)new MetasequoiaModelLoader();
/* 27 */   private static IVertexModelLoader tcnLoader = (IVertexModelLoader)new TechneModelLoader();
/*    */ 
/*    */   
/*    */   public static _IModelCustom loadModel(String name) throws IllegalArgumentException, _ModelFormatException {
/* 31 */     ResourceLocation resource = MCH_Utils.suffix("models/" + name);
/* 32 */     IResourceManager resourceManager = Minecraft.func_71410_x().func_110442_L();
/* 33 */     IVertexModelLoader[] loaders = { objLoader, mqoLoader, tcnLoader };
/*    */ 
/*    */ 
/*    */     
/* 37 */     _IModelCustom model = null;
/*    */     
/* 39 */     for (IVertexModelLoader loader : loaders) {
/*    */ 
/*    */       
/*    */       try {
/* 43 */         model = loader.load(resourceManager, resource);
/*    */       }
/* 45 */       catch (FileNotFoundException e) {
/*    */         
/* 47 */         MCH_Utils.logger().debug("model file not found '" + resource + "' at ." + loader.getExtension());
/*    */       }
/* 49 */       catch (IOException e1) {
/*    */         
/* 51 */         MCH_Utils.logger().error("load model error '" + resource + "' at ." + loader.getExtension(), e1);
/* 52 */         return null;
/*    */       } 
/*    */       
/* 55 */       if (model != null) {
/*    */         break;
/*    */       }
/*    */     } 
/*    */ 
/*    */     
/* 61 */     return model;
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\__helper\client\MCH_Models.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */