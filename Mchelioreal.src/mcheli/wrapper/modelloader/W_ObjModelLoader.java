/*    */ package mcheli.wrapper.modelloader;
/*    */ 
/*    */ import java.net.URL;
/*    */ import mcheli.__helper.client._IModelCustom;
/*    */ import mcheli.__helper.client._IModelCustomLoader;
/*    */ import mcheli.__helper.client._ModelFormatException;
/*    */ import net.minecraft.util.ResourceLocation;
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
/*    */ public class W_ObjModelLoader
/*    */   implements _IModelCustomLoader
/*    */ {
/*    */   public String getType() {
/* 22 */     return "OBJ model";
/*    */   }
/*    */   
/* 25 */   private static final String[] types = new String[] { "obj" };
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String[] getSuffixes() {
/* 33 */     return types;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public _IModelCustom loadInstance(ResourceLocation resource) throws _ModelFormatException {
/* 40 */     return new W_WavefrontObject(resource);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public _IModelCustom loadInstance(String resourceName, URL resource) throws _ModelFormatException {
/* 47 */     return new W_WavefrontObject(resourceName, resource);
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\wrapper\modelloader\W_ObjModelLoader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */