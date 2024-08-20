package mcheli.__helper.client;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Deprecated
@SideOnly(Side.CLIENT)
public interface _IItemRenderer {
  boolean handleRenderType(ItemStack paramItemStack, ItemRenderType paramItemRenderType);
  
  boolean shouldUseRenderHelper(ItemRenderType paramItemRenderType, ItemStack paramItemStack, ItemRendererHelper paramItemRendererHelper);
  
  void renderItem(ItemRenderType paramItemRenderType, ItemStack paramItemStack, Object... paramVarArgs);
  
  @Deprecated
  public enum ItemRenderType {
    ENTITY, EQUIPPED, EQUIPPED_FIRST_PERSON, INVENTORY, FIRST_PERSON_MAP;
  }
  
  @Deprecated
  public enum ItemRendererHelper {
    ENTITY_ROTATION, ENTITY_BOBBING, EQUIPPED_BLOCK, BLOCK_3D, INVENTORY_BLOCK;
  }
}
