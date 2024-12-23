package mcheli.debug;

import mcheli.MCH_Config;
import mcheli.wrapper.W_Render;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class MCH_RenderTest extends W_Render<Entity> {
  protected MCH_ModelTest model;
  
  private float offsetX;
  
  private float offsetY;
  
  private float offsetZ;
  
  private String textureName;
  
  public static final IRenderFactory<Entity> factory(float x, float y, float z, String texture_name) {
    return renderManager -> new MCH_RenderTest(renderManager, x, y, z, texture_name);
  }
  
  public MCH_RenderTest(RenderManager renderManager, float x, float y, float z, String texture_name) {
    super(renderManager);
    this.offsetX = x;
    this.offsetY = y;
    this.offsetZ = z;
    this.textureName = texture_name;
    this.model = new MCH_ModelTest();
  }
  
  public void doRender(Entity e, double posX, double posY, double posZ, float par8, float par9) {
    float prevYaw;
    if (!MCH_Config.TestMode.prmBool)
      return; 
    GL11.glPushMatrix();
    GL11.glTranslated(posX + this.offsetX, posY + this.offsetY, posZ + this.offsetZ);
    GL11.glScalef(e.width, e.height, e.width);
    GL11.glColor4f(0.5F, 0.5F, 0.5F, 1.0F);
    if (e.rotationYaw - e.prevRotationYaw < -180.0F) {
      prevYaw = e.prevRotationYaw - 360.0F;
    } else if (e.prevRotationYaw - e.rotationYaw < -180.0F) {
      prevYaw = e.prevRotationYaw + 360.0F;
    } else {
      prevYaw = e.prevRotationYaw;
    } 
    float yaw = -(prevYaw + (e.rotationYaw - prevYaw) * par9) - 180.0F;
    float pitch = -(e.prevRotationPitch + (e.rotationPitch - e.prevRotationPitch) * par9);
    GL11.glRotatef(yaw, 0.0F, 1.0F, 0.0F);
    GL11.glRotatef(pitch, 1.0F, 0.0F, 0.0F);
    bindTexture("textures/" + this.textureName + ".png");
    this.model.renderModel(0.0D, 0.0D, 0.1F);
    GL11.glPopMatrix();
  }
  
  protected ResourceLocation getEntityTexture(Entity entity) {
    return TEX_DEFAULT;
  }
}
