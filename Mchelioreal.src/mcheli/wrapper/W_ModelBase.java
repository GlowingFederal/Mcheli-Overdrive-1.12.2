/*    */ package mcheli.wrapper;
/*    */ 
/*    */ import mcheli.__helper.client._IModelCustom;
/*    */ import mcheli.__helper.client._IModelCustomLoader;
/*    */ import mcheli.__helper.client._ModelFormatException;
/*    */ import mcheli.__helper.client.model.loader.TechneModelLoader;
/*    */ import mcheli.wrapper.modelloader.W_MqoModelLoader;
/*    */ import mcheli.wrapper.modelloader.W_ObjModelLoader;
/*    */ import net.minecraft.client.model.ModelBase;
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
/*    */ public abstract class W_ModelBase
/*    */   extends ModelBase
/*    */ {
/* 24 */   private static _IModelCustomLoader objLoader = (_IModelCustomLoader)new W_ObjModelLoader();
/*    */   
/* 26 */   private static _IModelCustomLoader mqoLoader = (_IModelCustomLoader)new W_MqoModelLoader();
/*    */   
/* 28 */   private static _IModelCustomLoader tcnLoader = (_IModelCustomLoader)new TechneModelLoader();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static _IModelCustom loadModel(String name) throws IllegalArgumentException, _ModelFormatException {
/* 34 */     ResourceLocation resource = new ResourceLocation("mcheli", name);
/* 35 */     String path = resource.func_110623_a();
/* 36 */     int i = path.lastIndexOf('.');
/* 37 */     if (i == -1)
/*    */     {
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 43 */       throw new IllegalArgumentException("The resource name is not valid");
/*    */     }
/*    */ 
/*    */ 
/*    */     
/* 48 */     if (path.substring(i).equalsIgnoreCase(".mqo"))
/*    */     {
/* 50 */       return mqoLoader.loadInstance(resource);
/*    */     }
/*    */     
/* 53 */     if (path.substring(i).equalsIgnoreCase(".obj"))
/*    */     {
/* 55 */       return objLoader.loadInstance(resource);
/*    */     }
/*    */ 
/*    */     
/* 59 */     return tcnLoader.loadInstance(resource);
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\wrapper\W_ModelBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */