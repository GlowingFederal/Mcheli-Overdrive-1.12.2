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


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\__helper\client\_IModelCustomLoader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */