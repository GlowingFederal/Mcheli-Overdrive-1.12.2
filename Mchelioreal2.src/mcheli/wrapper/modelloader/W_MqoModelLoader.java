package mcheli.wrapper.modelloader;

import java.net.URL;
import mcheli.__helper.client._IModelCustom;
import mcheli.__helper.client._IModelCustomLoader;
import mcheli.__helper.client._ModelFormatException;
import net.minecraft.util.ResourceLocation;

public class W_MqoModelLoader implements _IModelCustomLoader {
  public String getType() {
    return "Metasequoia model";
  }
  
  private static final String[] types = new String[] { "mqo" };
  
  public String[] getSuffixes() {
    return types;
  }
  
  public _IModelCustom loadInstance(ResourceLocation resource) throws _ModelFormatException {
    return new W_MetasequoiaObject(resource);
  }
  
  public _IModelCustom loadInstance(String resourceName, URL resource) throws _ModelFormatException {
    return new W_MetasequoiaObject(resourceName, resource);
  }
}
