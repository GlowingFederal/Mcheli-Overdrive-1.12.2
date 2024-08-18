package mcheli.weapon;

import mcheli.wrapper.W_Entity;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class MCH_RenderASMissile extends MCH_RenderBulletBase<MCH_EntityBaseBullet> {
  public static final IRenderFactory<MCH_EntityBaseBullet> FACTORY = MCH_RenderASMissile::new;
  
  public MCH_RenderASMissile(RenderManager renderManager) {
    super(renderManager);
    this.field_76989_e = 0.5F;
  }
  
  public void renderBullet(MCH_EntityBaseBullet entity, double posX, double posY, double posZ, float yaw, float partialTickTime) {
    if (entity instanceof MCH_EntityBaseBullet) {
      MCH_EntityBaseBullet bullet = entity;
      GL11.glPushMatrix();
      GL11.glTranslated(posX, posY, posZ);
      GL11.glRotatef(-entity.field_70177_z, 0.0F, 1.0F, 0.0F);
      GL11.glRotatef(-entity.field_70125_A, -1.0F, 0.0F, 0.0F);
      renderModel(bullet);
      GL11.glPopMatrix();
    } 
  }
  
  protected ResourceLocation getEntityTexture(MCH_EntityBaseBullet entity) {
    return TEX_DEFAULT;
  }
}
