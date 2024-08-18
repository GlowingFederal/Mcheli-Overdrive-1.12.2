package mcheli.__helper.client;

import java.io.FileNotFoundException;
import java.io.IOException;
import mcheli.__helper.MCH_Utils;
import mcheli.__helper.client.model.loader.IVertexModelLoader;
import mcheli.__helper.client.model.loader.MetasequoiaModelLoader;
import mcheli.__helper.client.model.loader.TechneModelLoader;
import mcheli.__helper.client.model.loader.WavefrontModelLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MCH_Models {
  private static IVertexModelLoader objLoader = (IVertexModelLoader)new WavefrontModelLoader();
  
  private static IVertexModelLoader mqoLoader = (IVertexModelLoader)new MetasequoiaModelLoader();
  
  private static IVertexModelLoader tcnLoader = (IVertexModelLoader)new TechneModelLoader();
  
  public static _IModelCustom loadModel(String name) throws IllegalArgumentException, _ModelFormatException {
    ResourceLocation resource = MCH_Utils.suffix("models/" + name);
    IResourceManager resourceManager = Minecraft.func_71410_x().func_110442_L();
    IVertexModelLoader[] loaders = { objLoader, mqoLoader, tcnLoader };
    _IModelCustom model = null;
    for (IVertexModelLoader loader : loaders) {
      try {
        model = loader.load(resourceManager, resource);
      } catch (FileNotFoundException e) {
        MCH_Utils.logger().debug("model file not found '" + resource + "' at ." + loader.getExtension());
      } catch (IOException e1) {
        MCH_Utils.logger().error("load model error '" + resource + "' at ." + loader.getExtension(), e1);
        return null;
      } 
      if (model != null)
        break; 
    } 
    return model;
  }
}
