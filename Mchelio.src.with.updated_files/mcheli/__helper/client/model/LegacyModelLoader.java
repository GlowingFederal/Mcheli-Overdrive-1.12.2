package mcheli.__helper.client.model;

import net.minecraft.client.renderer.block.model.ModelBlock;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoaderRegistry;

public enum LegacyModelLoader implements ICustomModelLoader {
  INSTANCE;
  
  static final String TEMPLATE;
  
  public static final String VARIANT = "mcheli_legacy";
  
  static {
    TEMPLATE = "{'parent':'item/generated','textures':{'layer0':'__item__'}}".replaceAll("'", "\"");
  }
  
  public void onResourceManagerReload(IResourceManager resourceManager) {}
  
  public boolean accepts(ResourceLocation modelLocation) {
    if (modelLocation instanceof ModelResourceLocation) {
      ModelResourceLocation location = (ModelResourceLocation)modelLocation;
      return (location.getResourceDomain().equals("mcheli") && location.getVariant().equals("mcheli_legacy"));
    } 
    return false;
  }
  
  public IModel loadModel(ResourceLocation modelLocation) throws Exception {
    String path = modelLocation.getResourceDomain() + ":items/" + modelLocation.getResourcePath();
    ModelBlock modelblock = ModelBlock.deserialize(TEMPLATE.replaceAll("__item__", path));
    ModelBlock parent = ModelLoaderRegistry.getModel(modelblock.getParentLocation()).asVanillaModel().get();
    modelblock.parent = parent;
    return new MCH_WrapperItemLayerModel(modelblock);
  }
}
