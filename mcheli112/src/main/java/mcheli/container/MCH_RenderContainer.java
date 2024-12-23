package mcheli.container;

import java.util.Random;
import mcheli.MCH_Lib;
import mcheli.MCH_ModelManager;
import mcheli.aircraft.MCH_RenderAircraft;
import mcheli.wrapper.W_Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class MCH_RenderContainer extends W_Render<MCH_EntityContainer> {
  public static final IRenderFactory<MCH_EntityContainer> FACTORY = MCH_RenderContainer::new;
  
  public static final Random rand = new Random();
  
  public MCH_RenderContainer(RenderManager renderManager) {
    super(renderManager);
    this.shadowSize = 0.5F;
  }
  
  public void doRender(MCH_EntityContainer entity, double posX, double posY, double posZ, float par8, float tickTime) {
    if (MCH_RenderAircraft.shouldSkipRender((Entity)entity))
      return; 
    GL11.glPushMatrix();
    GL11.glEnable(2884);
    GL11.glTranslated(posX, posY - 0.2D + 0.5D, posZ);
    float yaw = MCH_Lib.smoothRot(entity.rotationYaw, entity.prevRotationYaw, tickTime);
    float pitch = MCH_Lib.smoothRot(entity.rotationPitch, entity.prevRotationPitch, tickTime);
    GL11.glRotatef(yaw, 0.0F, -1.0F, 0.0F);
    GL11.glRotatef(pitch, 1.0F, 0.0F, 0.0F);
    GL11.glColor4f(0.75F, 0.75F, 0.75F, 1.0F);
    bindTexture("textures/container.png");
    MCH_ModelManager.render("container");
    GL11.glPopMatrix();
  }
  
  protected ResourceLocation getEntityTexture(MCH_EntityContainer entity) {
    return TEX_DEFAULT;
  }
}
