package mcheli.__helper.client.model;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ModelBlock;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ItemLayerModel;
import net.minecraftforge.client.model.PerspectiveMapWrapper;
import net.minecraftforge.client.model.SimpleModelState;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.TRSRTransformation;

public class MCH_WrapperItemLayerModel implements IModel {
  private ItemLayerModel model;
  
  private ModelBlock raw;
  
  public MCH_WrapperItemLayerModel(ModelBlock modelBlock) {
    this.raw = modelBlock;
    this.model = new ItemLayerModel(modelBlock);
  }
  
  public Collection<ResourceLocation> getTextures() {
    return this.model.getTextures();
  }
  
  public IModel retexture(ImmutableMap<String, String> textures) {
    return (IModel)this.model.retexture(textures);
  }
  
  public IBakedModel bake(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
    ItemCameraTransforms transforms = this.raw.func_181682_g();
    Map<ItemCameraTransforms.TransformType, TRSRTransformation> tMap = Maps.newEnumMap(ItemCameraTransforms.TransformType.class);
    tMap.putAll((Map<? extends ItemCameraTransforms.TransformType, ? extends TRSRTransformation>)PerspectiveMapWrapper.getTransforms(transforms));
    tMap.putAll((Map<? extends ItemCameraTransforms.TransformType, ? extends TRSRTransformation>)PerspectiveMapWrapper.getTransforms(state));
    SimpleModelState simpleModelState = new SimpleModelState(ImmutableMap.copyOf(tMap), state.apply(Optional.empty()));
    return this.model.bake((IModelState)simpleModelState, format, bakedTextureGetter);
  }
}
