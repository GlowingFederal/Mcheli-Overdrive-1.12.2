package mcheli.wrapper;

import mcheli.__helper.client._IModelCustom;
import mcheli.__helper.client._IModelCustomLoader;
import mcheli.__helper.client._ModelFormatException;
import mcheli.__helper.client.model.loader.TechneModelLoader;
import mcheli.wrapper.modelloader.W_MqoModelLoader;
import mcheli.wrapper.modelloader.W_ObjModelLoader;
import net.minecraft.client.model.ModelBase;
import net.minecraft.util.ResourceLocation;

public abstract class W_ModelBase extends ModelBase {
  private static _IModelCustomLoader objLoader = (_IModelCustomLoader)new W_ObjModelLoader();
  
  private static _IModelCustomLoader mqoLoader = (_IModelCustomLoader)new W_MqoModelLoader();
  
  private static _IModelCustomLoader tcnLoader = (_IModelCustomLoader)new TechneModelLoader();
  
  public static _IModelCustom loadModel(String name) throws IllegalArgumentException, _ModelFormatException {
    ResourceLocation resource = new ResourceLocation("mcheli", name);
    String path = resource.func_110623_a();
    int i = path.lastIndexOf('.');
    if (i == -1)
      throw new IllegalArgumentException("The resource name is not valid"); 
    if (path.substring(i).equalsIgnoreCase(".mqo"))
      return mqoLoader.loadInstance(resource); 
    if (path.substring(i).equalsIgnoreCase(".obj"))
      return objLoader.loadInstance(resource); 
    return tcnLoader.loadInstance(resource);
  }
}
