package mcheli.__helper.client.renderer.item;

import mcheli.gltd.MCH_RenderGLTD;
import mcheli.wrapper.W_McClient;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class BuiltInGLTDItemRenderer implements IItemModelRenderer {
  public boolean shouldRenderer(ItemStack itemStack, ItemCameraTransforms.TransformType transformType) {
    return (IItemModelRenderer.isFirstPerson(transformType) || IItemModelRenderer.isThirdPerson(transformType) || transformType == ItemCameraTransforms.TransformType.GROUND);
  }
  
  public void renderItem(ItemStack itemStack, EntityLivingBase entityLivingBase, ItemCameraTransforms.TransformType transformType, float partialTicks) {
    GL11.glPushMatrix();
    GL11.glEnable(2884);
    W_McClient.MOD_bindTexture("textures/gltd.png");
    GL11.glEnable(32826);
    GL11.glEnable(2903);
    MCH_RenderGLTD.model.renderAll();
    GL11.glDisable(32826);
    GL11.glPopMatrix();
  }
}
