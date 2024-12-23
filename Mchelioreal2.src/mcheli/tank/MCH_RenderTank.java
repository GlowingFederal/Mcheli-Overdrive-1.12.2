package mcheli.tank;

import mcheli.MCH_Config;
import mcheli.__helper.MCH_ColorInt;
import mcheli.aircraft.MCH_EntityAircraft;
import mcheli.aircraft.MCH_RenderAircraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class MCH_RenderTank extends MCH_RenderAircraft<MCH_EntityTank> {
  public static final IRenderFactory<MCH_EntityTank> FACTORY = MCH_RenderTank::new;
  
  public MCH_RenderTank(RenderManager renderManager) {
    super(renderManager);
    this.field_76989_e = 2.0F;
  }
  
  public void renderAircraft(MCH_EntityAircraft entity, double posX, double posY, double posZ, float yaw, float pitch, float roll, float tickTime) {
    MCH_EntityTank tank;
    MCH_TankInfo tankInfo = null;
    if (entity != null && entity instanceof MCH_EntityTank) {
      tank = (MCH_EntityTank)entity;
      tankInfo = tank.getTankInfo();
      if (tankInfo == null)
        return; 
    } else {
      return;
    } 
    posY += 0.3499999940395355D;
    renderWheel(tank, posX, posY, posZ);
    renderDebugHitBox(tank, posX, posY, posZ, yaw, pitch);
    renderDebugPilotSeat(tank, posX, posY, posZ, yaw, pitch, roll);
    GL11.glTranslated(posX, posY, posZ);
    GL11.glRotatef(yaw, 0.0F, -1.0F, 0.0F);
    GL11.glRotatef(pitch, 1.0F, 0.0F, 0.0F);
    GL11.glRotatef(roll, 0.0F, 0.0F, 1.0F);
    bindTexture("textures/tanks/" + tank.getTextureName() + ".png", tank);
    renderBody(tankInfo.model);
  }
  
  public void renderWheel(MCH_EntityTank tank, double posX, double posY, double posZ) {
    if (!MCH_Config.TestMode.prmBool)
      return; 
    if (debugModel == null)
      return; 
    GL11.glColor4f(0.75F, 0.75F, 0.75F, 0.5F);
    for (MCH_EntityWheel w : tank.WheelMng.wheels) {
      GL11.glPushMatrix();
      GL11.glTranslated(w.field_70165_t - tank.field_70165_t + posX, w.field_70163_u - tank.field_70163_u + posY + 0.25D, w.field_70161_v - tank.field_70161_v + posZ);
      GL11.glScalef(w.field_70130_N, w.field_70131_O / 2.0F, w.field_70130_N);
      bindTexture("textures/seat_pilot.png");
      debugModel.renderAll();
      GL11.glPopMatrix();
    } 
    GL11.glColor4f(0.75F, 0.75F, 0.75F, 1.0F);
    Tessellator tessellator = Tessellator.func_178181_a();
    BufferBuilder builder = tessellator.func_178180_c();
    builder.func_181668_a(1, DefaultVertexFormats.field_181706_f);
    Vec3d wp = tank.getTransformedPosition(tank.WheelMng.weightedCenter);
    wp = wp.func_178786_a(tank.field_70165_t, tank.field_70163_u, tank.field_70161_v);
    for (int i = 0; i < tank.WheelMng.wheels.length / 2; i++) {
      MCH_ColorInt cint = new MCH_ColorInt(((i & 0x4) > 0) ? 16711680 : 0, ((i & 0x2) > 0) ? 65280 : 0, ((i & 0x1) > 0) ? 255 : 0, 192);
      MCH_EntityWheel w1 = tank.WheelMng.wheels[i * 2 + 0];
      MCH_EntityWheel w2 = tank.WheelMng.wheels[i * 2 + 1];
      if (w1.isPlus) {
        builder.func_181662_b(w2.field_70165_t - tank.field_70165_t + posX, w2.field_70163_u - tank.field_70163_u + posY, w2.field_70161_v - tank.field_70161_v + posZ)
          .func_181669_b(cint.r, cint.g, cint.b, cint.a).func_181675_d();
        builder.func_181662_b(w1.field_70165_t - tank.field_70165_t + posX, w1.field_70163_u - tank.field_70163_u + posY, w1.field_70161_v - tank.field_70161_v + posZ)
          .func_181669_b(cint.r, cint.g, cint.b, cint.a).func_181675_d();
        builder.func_181662_b(w1.field_70165_t - tank.field_70165_t + posX, w1.field_70163_u - tank.field_70163_u + posY, w1.field_70161_v - tank.field_70161_v + posZ)
          .func_181669_b(cint.r, cint.g, cint.b, cint.a).func_181675_d();
        builder.func_181662_b(posX + wp.field_72450_a, posY + wp.field_72448_b, posZ + wp.field_72449_c).func_181669_b(cint.r, cint.g, cint.b, cint.a).func_181675_d();
        builder.func_181662_b(posX + wp.field_72450_a, posY + wp.field_72448_b, posZ + wp.field_72449_c).func_181669_b(cint.r, cint.g, cint.b, cint.a).func_181675_d();
        builder.func_181662_b(w2.field_70165_t - tank.field_70165_t + posX, w2.field_70163_u - tank.field_70163_u + posY, w2.field_70161_v - tank.field_70161_v + posZ)
          .func_181669_b(cint.r, cint.g, cint.b, cint.a).func_181675_d();
      } else {
        builder.func_181662_b(w1.field_70165_t - tank.field_70165_t + posX, w1.field_70163_u - tank.field_70163_u + posY, w1.field_70161_v - tank.field_70161_v + posZ)
          .func_181669_b(cint.r, cint.g, cint.b, cint.a).func_181675_d();
        builder.func_181662_b(w2.field_70165_t - tank.field_70165_t + posX, w2.field_70163_u - tank.field_70163_u + posY, w2.field_70161_v - tank.field_70161_v + posZ)
          .func_181669_b(cint.r, cint.g, cint.b, cint.a).func_181675_d();
        builder.func_181662_b(w2.field_70165_t - tank.field_70165_t + posX, w2.field_70163_u - tank.field_70163_u + posY, w2.field_70161_v - tank.field_70161_v + posZ)
          .func_181669_b(cint.r, cint.g, cint.b, cint.a).func_181675_d();
        builder.func_181662_b(posX + wp.field_72450_a, posY + wp.field_72448_b, posZ + wp.field_72449_c).func_181669_b(cint.r, cint.g, cint.b, cint.a).func_181675_d();
        builder.func_181662_b(posX + wp.field_72450_a, posY + wp.field_72448_b, posZ + wp.field_72449_c).func_181669_b(cint.r, cint.g, cint.b, cint.a).func_181675_d();
        builder.func_181662_b(w1.field_70165_t - tank.field_70165_t + posX, w1.field_70163_u - tank.field_70163_u + posY, w1.field_70161_v - tank.field_70161_v + posZ)
          .func_181669_b(cint.r, cint.g, cint.b, cint.a).func_181675_d();
      } 
    } 
    tessellator.func_78381_a();
  }
  
  protected ResourceLocation getEntityTexture(MCH_EntityTank entity) {
    return TEX_DEFAULT;
  }
}
