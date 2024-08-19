package mcheli.weapon;

import mcheli.wrapper.W_Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class MCH_RenderCartridge extends W_Render<MCH_EntityCartridge> {
  public static final IRenderFactory<MCH_EntityCartridge> FACTORY = MCH_RenderCartridge::new;
  
  public MCH_RenderCartridge(RenderManager renderManager) {
    super(renderManager);
    this.shadowSize = 0.0F;
  }
  
  public void doRender(MCH_EntityCartridge entity, double posX, double posY, double posZ, float par8, float tickTime) {
    MCH_EntityCartridge cartridge = null;
    cartridge = entity;
    if (cartridge.model != null && !cartridge.texture_name.isEmpty()) {
      GL11.glPushMatrix();
      GL11.glTranslated(posX, posY, posZ);
      GL11.glScalef(cartridge.getScale(), cartridge.getScale(), cartridge.getScale());
      float prevYaw = cartridge.prevRotationYaw;
      if (cartridge.rotationYaw - prevYaw < -180.0F) {
        prevYaw -= 360.0F;
      } else if (prevYaw - cartridge.rotationYaw < -180.0F) {
        prevYaw += 360.0F;
      } 
      float yaw = -(prevYaw + (cartridge.rotationYaw - prevYaw) * tickTime);
      float pitch = cartridge.prevRotationPitch + (cartridge.rotationPitch - cartridge.prevRotationPitch) * tickTime;
      GL11.glRotatef(yaw, 0.0F, 1.0F, 0.0F);
      GL11.glRotatef(pitch, 1.0F, 0.0F, 0.0F);
      bindTexture("textures/bullets/" + cartridge.texture_name + ".png");
      cartridge.model.renderAll();
      GL11.glPopMatrix();
    } 
  }
  
  protected ResourceLocation getEntityTexture(MCH_EntityCartridge entity) {
    return TEX_DEFAULT;
  }
}