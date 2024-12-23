package mcheli.__helper.client.renderer.item;

import mcheli.MCH_ModelManager;
import mcheli.wrapper.W_McClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class BuiltInDraftingTableItemRenderer implements IItemModelRenderer {
  public boolean shouldRenderer(ItemStack itemStack, ItemCameraTransforms.TransformType transformType) {
    return true;
  }
  
  public void renderItem(ItemStack itemStack, EntityLivingBase entityLivingBase, ItemCameraTransforms.TransformType transformType, float partialTicks) {
    GlStateManager.pushMatrix();
    W_McClient.MOD_bindTexture("textures/blocks/drafting_table.png");
    GlStateManager.enableRescaleNormal();
    switch (transformType) {
      case GROUND:
        GL11.glTranslatef(0.0F, 0.5F, 0.0F);
        GL11.glScalef(1.5F, 1.5F, 1.5F);
        break;
      case GUI:
      case FIXED:
        GlStateManager.translate(0.0F, -0.5F, 0.0F);
        GlStateManager.scale(0.75F, 0.75F, 0.75F);
        break;
      case THIRD_PERSON_LEFT_HAND:
      case THIRD_PERSON_RIGHT_HAND:
        GL11.glTranslatef(0.0F, 0.0F, 0.5F);
        GL11.glScalef(1.0F, 1.0F, 1.0F);
        break;
      case FIRST_PERSON_LEFT_HAND:
      case FIRST_PERSON_RIGHT_HAND:
        GL11.glTranslatef(0.75F, 0.0F, 0.0F);
        GL11.glScalef(1.0F, 1.0F, 1.0F);
        GL11.glRotatef(90.0F, 0.0F, -1.0F, 0.0F);
        break;
    } 
    MCH_ModelManager.render("blocks", "drafting_table");
    GlStateManager.popMatrix();
    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    GlStateManager.enableBlend();
  }
}
