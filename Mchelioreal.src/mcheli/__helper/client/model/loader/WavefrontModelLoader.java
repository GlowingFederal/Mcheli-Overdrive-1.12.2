/*    */ package mcheli.__helper.client.model.loader;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import mcheli.__helper.client._IModelCustom;
/*    */ import mcheli.__helper.client._ModelFormatException;
/*    */ import mcheli.wrapper.modelloader.W_WavefrontObject;
/*    */ import net.minecraft.client.resources.IResource;
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
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class WavefrontModelLoader
/*    */   implements IVertexModelLoader
/*    */ {
/*    */   public _IModelCustom load(IResourceManager resourceManager, ResourceLocation location) throws IOException, _ModelFormatException {
/* 26 */     ResourceLocation modelLocation = withExtension(location);
/* 27 */     IResource resource = resourceManager.func_110536_a(modelLocation);
/*    */     
/* 29 */     return (_IModelCustom)new W_WavefrontObject(modelLocation, resource);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getExtension() {
/* 35 */     return "obj";
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\__helper\client\model\loader\WavefrontModelLoader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */