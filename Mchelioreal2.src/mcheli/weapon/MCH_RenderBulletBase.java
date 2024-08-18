package mcheli.weapon;

import mcheli.MCH_Color;
import mcheli.wrapper.W_Block;
import mcheli.wrapper.W_Entity;
import mcheli.wrapper.W_Render;
import mcheli.wrapper.W_WorldFunc;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public abstract class MCH_RenderBulletBase<T extends W_Entity> extends W_Render<T> {
  protected MCH_RenderBulletBase(RenderManager renderManager) {
    super(renderManager);
  }
  
  public void doRender(T e, double var2, double var4, double var6, float var8, float var9) {
    if (e instanceof MCH_EntityBaseBullet && ((MCH_EntityBaseBullet)e).getInfo() != null) {
      MCH_Color c = (((MCH_EntityBaseBullet)e).getInfo()).color;
      for (int y = 0; y < 3; y++) {
        Block b = W_WorldFunc.getBlock(((W_Entity)e).field_70170_p, (int)(((W_Entity)e).field_70165_t + 0.5D), (int)(((W_Entity)e).field_70163_u + 1.5D - y), (int)(((W_Entity)e).field_70161_v + 0.5D));
        if (b != null && b == W_Block.getWater()) {
          c = (((MCH_EntityBaseBullet)e).getInfo()).colorInWater;
          break;
        } 
      } 
      GL11.glColor4f(c.r, c.g, c.b, c.a);
    } else {
      GL11.glColor4f(0.75F, 0.75F, 0.75F, 1.0F);
    } 
    GL11.glAlphaFunc(516, 0.001F);
    GL11.glEnable(2884);
    GL11.glEnable(3042);
    int srcBlend = GL11.glGetInteger(3041);
    int dstBlend = GL11.glGetInteger(3040);
    GL11.glBlendFunc(770, 771);
    renderBullet(e, var2, var4, var6, var8, var9);
    GL11.glColor4f(0.75F, 0.75F, 0.75F, 1.0F);
    GL11.glBlendFunc(srcBlend, dstBlend);
    GL11.glDisable(3042);
  }
  
  public void renderModel(MCH_EntityBaseBullet e) {
    MCH_BulletModel model = e.getBulletModel();
    if (model != null) {
      bindTexture("textures/bullets/" + model.name + ".png");
      model.model.renderAll();
    } 
  }
  
  public abstract void renderBullet(T paramT, double paramDouble1, double paramDouble2, double paramDouble3, float paramFloat1, float paramFloat2);
}
