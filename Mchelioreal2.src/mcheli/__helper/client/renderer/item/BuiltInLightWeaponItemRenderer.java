package mcheli.__helper.client.renderer.item;

import mcheli.MCH_Config;
import mcheli.MCH_ModelManager;
import mcheli.lweapon.MCH_ItemLightWeaponBase;
import mcheli.wrapper.W_Lib;
import mcheli.wrapper.W_McClient;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class BuiltInLightWeaponItemRenderer implements IItemModelRenderer {
  public boolean shouldRenderer(ItemStack itemStack, ItemCameraTransforms.TransformType transformType) {
    return (IItemModelRenderer.isFirstPerson(transformType) || IItemModelRenderer.isThirdPerson(transformType));
  }
  
  public void renderItem(ItemStack itemStack, EntityLivingBase entityLivingBase, ItemCameraTransforms.TransformType transformType, float partialTicks) {
    boolean isRender = false;
    if (IItemModelRenderer.isFirstPerson(transformType) || IItemModelRenderer.isThirdPerson(transformType)) {
      isRender = true;
      if (entityLivingBase instanceof EntityPlayer) {
        EntityPlayer player = (EntityPlayer)entityLivingBase;
        if (MCH_ItemLightWeaponBase.isHeld(player) && W_Lib.isFirstPerson() && W_Lib.isClientPlayer((Entity)player))
          isRender = false; 
      } 
    } 
    if (isRender)
      renderItem(itemStack, IItemModelRenderer.isFirstPerson(transformType), entityLivingBase); 
  }
  
  private void renderItem(ItemStack itemStack, boolean isFirstPerson, EntityLivingBase entity) {
    String name = MCH_ItemLightWeaponBase.getName(itemStack);
    GL11.glEnable(32826);
    GL11.glEnable(2903);
    GL11.glPushMatrix();
    if (MCH_Config.SmoothShading.prmBool)
      GL11.glShadeModel(7425); 
    GL11.glEnable(2884);
    W_McClient.MOD_bindTexture("textures/lweapon/" + name + ".png");
    if (isFirstPerson && entity.func_184587_cr() && entity.func_184600_cs() == EnumHand.MAIN_HAND)
      GL11.glTranslated(0.12999999523162842D, 0.27000001072883606D, 0.009999999776482582D); 
    MCH_ModelManager.render("lweapons", name);
    GL11.glShadeModel(7424);
    GL11.glPopMatrix();
    GL11.glDisable(32826);
  }
}
