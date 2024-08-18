package mcheli;

import mcheli.__helper.client._IItemRenderer;
import net.minecraft.item.ItemStack;

@Deprecated
public class MCH_InvisibleItemRender implements _IItemRenderer {
  public boolean handleRenderType(ItemStack item, _IItemRenderer.ItemRenderType type) {
    return (type == _IItemRenderer.ItemRenderType.EQUIPPED || type == _IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON);
  }
  
  public boolean shouldUseRenderHelper(_IItemRenderer.ItemRenderType type, ItemStack item, _IItemRenderer.ItemRendererHelper helper) {
    return false;
  }
  
  public boolean useCurrentWeapon() {
    return false;
  }
  
  public void renderItem(_IItemRenderer.ItemRenderType type, ItemStack item, Object... data) {}
}
