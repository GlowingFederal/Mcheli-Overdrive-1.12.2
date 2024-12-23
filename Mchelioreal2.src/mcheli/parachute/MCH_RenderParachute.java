package mcheli.parachute;

import java.util.Random;
import mcheli.MCH_Config;
import mcheli.MCH_ModelManager;
import mcheli.wrapper.W_Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class MCH_RenderParachute extends W_Render<MCH_EntityParachute> {
  public static final IRenderFactory<MCH_EntityParachute> FACTORY = MCH_RenderParachute::new;
  
  public static final Random rand = new Random();
  
  public MCH_RenderParachute(RenderManager renderManager) {
    super(renderManager);
    this.field_76989_e = 0.5F;
  }
  
  public void doRender(MCH_EntityParachute entity, double posX, double posY, double posZ, float par8, float tickTime) {
    if (!(entity instanceof MCH_EntityParachute))
      return; 
    MCH_EntityParachute parachute = entity;
    int type = parachute.getType();
    if (type <= 0)
      return; 
    GL11.glPushMatrix();
    GL11.glEnable(2884);
    GL11.glTranslated(posX, posY, posZ);
    float prevYaw = entity.field_70126_B;
    if (entity.field_70177_z - prevYaw < -180.0F) {
      prevYaw -= 360.0F;
    } else if (prevYaw - entity.field_70177_z < -180.0F) {
      prevYaw += 360.0F;
    } 
    float yaw = prevYaw + (entity.field_70177_z - prevYaw) * tickTime;
    GL11.glRotatef(yaw, 0.0F, -1.0F, 0.0F);
    GL11.glColor4f(0.75F, 0.75F, 0.75F, 1.0F);
    GL11.glEnable(3042);
    int srcBlend = GL11.glGetInteger(3041);
    int dstBlend = GL11.glGetInteger(3040);
    GL11.glBlendFunc(770, 771);
    if (MCH_Config.SmoothShading.prmBool)
      GL11.glShadeModel(7425); 
    switch (type) {
      case 1:
        bindTexture("textures/parachute1.png");
        MCH_ModelManager.render("parachute1");
        break;
      case 2:
        bindTexture("textures/parachute2.png");
        if (parachute.isOpenParachute()) {
          MCH_ModelManager.renderPart("parachute2", "$parachute");
          break;
        } 
        MCH_ModelManager.renderPart("parachute2", "$seat");
        break;
      case 3:
        bindTexture("textures/parachute2.png");
        MCH_ModelManager.renderPart("parachute2", "$parachute");
        break;
    } 
    GL11.glBlendFunc(srcBlend, dstBlend);
    GL11.glDisable(3042);
    GL11.glShadeModel(7424);
    GL11.glPopMatrix();
  }
  
  protected ResourceLocation getEntityTexture(MCH_EntityParachute entity) {
    return TEX_DEFAULT;
  }
}
