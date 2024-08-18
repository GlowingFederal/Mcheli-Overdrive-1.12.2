package mcheli.weapon;

import mcheli.MCH_Lib;
import mcheli.wrapper.W_Entity;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class MCH_RenderAAMissile extends MCH_RenderBulletBase<MCH_EntityAAMissile> {
  public static final IRenderFactory<MCH_EntityAAMissile> FACTORY = MCH_RenderAAMissile::new;
  
  public MCH_RenderAAMissile(RenderManager renderManager) {
    super(renderManager);
    this.field_76989_e = 0.5F;
  }
  
  public void renderBullet(MCH_EntityAAMissile entity, double posX, double posY, double posZ, float par8, float par9) {
    if (!(entity instanceof MCH_EntityAAMissile))
      return; 
    MCH_EntityAAMissile aam = entity;
    double mx = aam.prevMotionX + (aam.field_70159_w - aam.prevMotionX) * par9;
    double my = aam.prevMotionY + (aam.field_70181_x - aam.prevMotionY) * par9;
    double mz = aam.prevMotionZ + (aam.field_70179_y - aam.prevMotionZ) * par9;
    GL11.glPushMatrix();
    GL11.glTranslated(posX, posY, posZ);
    Vec3d v = MCH_Lib.getYawPitchFromVec(mx, my, mz);
    GL11.glRotatef((float)v.field_72448_b - 90.0F, 0.0F, -1.0F, 0.0F);
    GL11.glRotatef((float)v.field_72449_c, -1.0F, 0.0F, 0.0F);
    renderModel(aam);
    GL11.glPopMatrix();
  }
  
  protected ResourceLocation getEntityTexture(MCH_EntityAAMissile entity) {
    return TEX_DEFAULT;
  }
}
