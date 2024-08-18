package mcheli.chain;

import mcheli.MCH_Lib;
import mcheli.MCH_ModelManager;
import mcheli.wrapper.W_Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class MCH_RenderChain extends W_Render<MCH_EntityChain> {
  public static final IRenderFactory<MCH_EntityChain> FACTORY = MCH_RenderChain::new;
  
  public MCH_RenderChain(RenderManager renderManager) {
    super(renderManager);
  }
  
  public void doRender(MCH_EntityChain e, double posX, double posY, double posZ, float par8, float par9) {
    if (!(e instanceof MCH_EntityChain))
      return; 
    MCH_EntityChain chain = e;
    if (chain.towedEntity == null || chain.towEntity == null)
      return; 
    GL11.glPushMatrix();
    GL11.glEnable(2884);
    GL11.glColor4f(0.5F, 0.5F, 0.5F, 1.0F);
    GL11.glTranslated(chain.towedEntity.field_70142_S - TileEntityRendererDispatcher.field_147554_b, chain.towedEntity.field_70137_T - TileEntityRendererDispatcher.field_147555_c, chain.towedEntity.field_70136_U - TileEntityRendererDispatcher.field_147552_d);
    bindTexture("textures/chain.png");
    double dx = chain.towEntity.field_70142_S - chain.towedEntity.field_70142_S;
    double dy = chain.towEntity.field_70137_T - chain.towedEntity.field_70137_T;
    double dz = chain.towEntity.field_70136_U - chain.towedEntity.field_70136_U;
    double diff = Math.sqrt(dx * dx + dy * dy + dz * dz);
    double x = dx * 0.949999988079071D / diff;
    double y = dy * 0.949999988079071D / diff;
    double z = dz * 0.949999988079071D / diff;
    while (diff > 0.949999988079071D) {
      GL11.glTranslated(x, y, z);
      GL11.glPushMatrix();
      Vec3d v = MCH_Lib.getYawPitchFromVec(x, y, z);
      GL11.glRotatef((float)v.field_72448_b, 0.0F, -1.0F, 0.0F);
      GL11.glRotatef((float)v.field_72449_c, 0.0F, 0.0F, 1.0F);
      MCH_ModelManager.render("chain");
      GL11.glPopMatrix();
      diff -= 0.949999988079071D;
    } 
    GL11.glPopMatrix();
  }
  
  protected ResourceLocation getEntityTexture(MCH_EntityChain entity) {
    return TEX_DEFAULT;
  }
}
