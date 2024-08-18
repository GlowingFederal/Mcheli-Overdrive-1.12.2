package mcheli.__helper.client.renderer.item;

import mcheli.MCH_ModelManager;
import mcheli.tool.MCH_ItemWrench;
import mcheli.wrapper.W_McClient;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class BuiltInWrenchItemRenderer implements IItemModelRenderer {
  public boolean shouldRenderer(ItemStack itemStack, ItemCameraTransforms.TransformType transformType) {
    return (IItemModelRenderer.isFirstPerson(transformType) || IItemModelRenderer.isThirdPerson(transformType));
  }
  
  public void renderItem(ItemStack itemStack, EntityLivingBase entity, ItemCameraTransforms.TransformType transformType, float partialTicks) {
    GL11.glPushMatrix();
    W_McClient.MOD_bindTexture("textures/wrench.png");
    if (IItemModelRenderer.isFirstPerson(transformType))
      if (entity.func_184587_cr() && entity.func_184600_cs() == EnumHand.MAIN_HAND) {
        float f = MCH_ItemWrench.getUseAnimSmooth(itemStack, partialTicks);
        GL11.glRotatef(65.0F, 0.0F, 0.0F, 1.0F);
        GL11.glRotatef(f + 20.0F, 1.0F, 0.0F, 0.0F);
      }  
    MCH_ModelManager.render("wrench");
    GL11.glPopMatrix();
  }
}
