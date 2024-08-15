/*    */ package mcheli.__helper.client.model.loader;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.net.URL;
/*    */ import javax.annotation.Nullable;
/*    */ import mcheli.__helper.client._IModelCustom;
/*    */ import mcheli.__helper.client._IModelCustomLoader;
/*    */ import mcheli.__helper.client._ModelFormatException;
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
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class TechneModelLoader
/*    */   implements _IModelCustomLoader, IVertexModelLoader
/*    */ {
/* 25 */   private static final String[] types = new String[] { "tcn" };
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getType() {
/* 33 */     return "Techne model";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String[] getSuffixes() {
/* 39 */     return types;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public _IModelCustom loadInstance(ResourceLocation resource) throws _ModelFormatException {
/* 46 */     throw new UnsupportedOperationException("Techne model is unsupported. file:" + resource);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public _IModelCustom loadInstance(String resourceName, URL resource) throws _ModelFormatException {
/* 53 */     throw new UnsupportedOperationException("Techne model is unsupported. file:" + resource);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getExtension() {
/* 59 */     return "tcn";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public _IModelCustom load(IResourceManager resourceManager, ResourceLocation location) throws IOException, _ModelFormatException {
/* 67 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\__helper\client\model\loader\TechneModelLoader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */