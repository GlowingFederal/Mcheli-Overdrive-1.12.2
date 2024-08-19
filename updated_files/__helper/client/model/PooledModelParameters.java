package mcheli.__helper.client.model;

import javax.annotation.Nullable;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public class PooledModelParameters {
  private static EntityLivingBase heldItemUser;
  
  private static ItemStack rendererTargetItem = ItemStack.field_190927_a;
  
  private static ItemCameraTransforms.TransformType transformType = ItemCameraTransforms.TransformType.NONE;
  
  static void setItemAndUser(ItemStack itemstack, @Nullable EntityLivingBase user) {
    rendererTargetItem = itemstack;
    heldItemUser = user;
  }
  
  static void setTransformType(ItemCameraTransforms.TransformType type) {
    transformType = type;
  }
  
  @Nullable
  public static EntityLivingBase getEntity() {
    return heldItemUser;
  }
  
  public static ItemStack getTargetRendererStack() {
    return rendererTargetItem;
  }
  
  public static ItemCameraTransforms.TransformType getTransformType() {
    return transformType;
  }
}
