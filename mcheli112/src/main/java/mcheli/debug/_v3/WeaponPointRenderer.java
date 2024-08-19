package mcheli.debug._v3;

import com.google.common.collect.Maps;
import java.util.Map;
import javax.vecmath.Color4f;
import mcheli.aircraft.MCH_AircraftInfo;
import mcheli.aircraft.MCH_EntityAircraft;
import mcheli.weapon.MCH_WeaponBase;
import mcheli.weapon.MCH_WeaponSet;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;

public class WeaponPointRenderer {
  private static final Color4f[] C = new Color4f[] { new Color4f(1.0F, 0.0F, 0.0F, 1.0F), new Color4f(0.0F, 1.0F, 0.0F, 1.0F), new Color4f(0.0F, 0.0F, 1.0F, 1.0F), new Color4f(1.0F, 1.0F, 0.0F, 1.0F), new Color4f(1.0F, 0.0F, 1.0F, 1.0F), new Color4f(0.0F, 1.0F, 1.0F, 1.0F), new Color4f(0.95686275F, 0.6431373F, 0.3764706F, 1.0F), new Color4f(0.5411765F, 0.16862746F, 0.42477876F, 1.0F) };
  
  public static void renderWeaponPoints(MCH_EntityAircraft ac, MCH_AircraftInfo info, double x, double y, double z) {
    int prevPointSize = GlStateManager.glGetInteger(2833);
    int id = 0;
    int prevFunc = GlStateManager.glGetInteger(2932);
    Map<Vec3d, Integer> poses = Maps.newHashMap();
    GlStateManager.disableTexture2D();
    GlStateManager.enableBlend();
    GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
    GlStateManager.enableLighting();
    GlStateManager.depthMask(false);
    GlStateManager.depthFunc(519);
    GL11.glPointSize(20.0F);
    GlStateManager.pushMatrix();
    GlStateManager.translate(x, y, z);
    for (MCH_AircraftInfo.WeaponSet wsInfo : info.weaponSetList) {
      MCH_WeaponSet ws = ac.getWeaponByName(wsInfo.type);
      if (ws != null) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder builder = tessellator.getBuffer();
        builder.begin(0, DefaultVertexFormats.POSITION_COLOR);
        for (int i = 0; i < ws.getWeaponNum(); i++) {
          MCH_WeaponBase weapon = ws.getWeapon(i);
          if (weapon != null) {
            int j = 0;
            if (poses.containsKey(weapon.position)) {
              j = ((Integer)poses.get(weapon.position)).intValue();
              j++;
            } 
            poses.put(weapon.position, Integer.valueOf(j));
            Vec3d vec3d = weapon.getShotPos((Entity)ac);
            Color4f c = C[id % C.length];
            float f = i * 0.1F;
            double d = j * 0.04D;
            builder.pos(vec3d.xCoord, vec3d.yCoord + d, vec3d.zCoord)
              .color(in(c.x + f), in(c.y + f), in(c.z + f), c.w).endVertex();
          } 
        } 
        tessellator.draw();
        id++;
      } 
    } 
    GlStateManager.popMatrix();
    GL11.glPointSize(prevPointSize);
    GlStateManager.depthFunc(prevFunc);
    GlStateManager.depthMask(true);
    GlStateManager.enableTexture2D();
    GlStateManager.disableBlend();
  }
  
  static float in(float value) {
    return MathHelper.clamp(value, 0.0F, 1.0F);
  }
}
