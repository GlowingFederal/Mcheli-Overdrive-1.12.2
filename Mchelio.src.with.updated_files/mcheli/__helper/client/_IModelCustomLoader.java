package mcheli.__helper.client;

import java.net.URL;
import net.minecraft.util.ResourceLocation;

@Deprecated
public interface _IModelCustomLoader {
  String getType();
  
  String[] getSuffixes();
  
  @Deprecated
  _IModelCustom loadInstance(ResourceLocation paramResourceLocation) throws _ModelFormatException;
  
  @Deprecated
  _IModelCustom loadInstance(String paramString, URL paramURL) throws _ModelFormatException;
}
