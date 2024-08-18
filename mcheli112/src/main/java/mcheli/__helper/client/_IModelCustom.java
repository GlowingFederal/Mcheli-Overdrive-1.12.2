package mcheli.__helper.client;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface _IModelCustom {
  String getType();
  
  @SideOnly(Side.CLIENT)
  void renderAll();
  
  @SideOnly(Side.CLIENT)
  void renderOnly(String... paramVarArgs);
  
  @SideOnly(Side.CLIENT)
  void renderPart(String paramString);
  
  @SideOnly(Side.CLIENT)
  void renderAllExcept(String... paramVarArgs);
}
