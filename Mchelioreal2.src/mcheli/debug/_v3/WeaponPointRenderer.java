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
    int prevPointSize = GlStateManager.func_187397_v(2833);
    int id = 0;
    int prevFunc = GlStateManager.func_187397_v(2932);
    Map<Vec3d, Integer> poses = Maps.newHashMap();
    GlStateManager.func_179090_x();
    GlStateManager.func_179147_l();
    GlStateManager.func_187401_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
    GlStateManager.func_179145_e();
    GlStateManager.func_179132_a(false);
    GlStateManager.func_179143_c(519);
    GL11.glPointSize(20.0F);
    GlStateManager.func_179094_E();
    GlStateManager.func_179137_b(x, y, z);
    for (MCH_AircraftInfo.WeaponSet wsInfo : info.weaponSetList) {
      MCH_WeaponSet ws = ac.getWeaponByName(wsInfo.type);
      if (ws != null) {
        Tessellator tessellator = Tessellator.func_178181_a();
        BufferBuilder builder = tessellator.func_178180_c();
        builder.func_181668_a(0, DefaultVertexFormats.field_181706_f);
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
            builder.func_181662_b(vec3d.field_72450_a, vec3d.field_72448_b + d, vec3d.field_72449_c)
              .func_181666_a(in(c.x + f), in(c.y + f), in(c.z + f), c.w).func_181675_d();
          } 
        } 
        tessellator.func_78381_a();
        id++;
      } 
    } 
    GlStateManager.func_179121_F();
    GL11.glPointSize(prevPointSize);
    GlStateManager.func_179143_c(prevFunc);
    GlStateManager.func_179132_a(true);
    GlStateManager.func_179098_w();
    GlStateManager.func_179084_k();
  }
  
  static float in(float value) {
    return MathHelper.func_76131_a(value, 0.0F, 1.0F);
  }
}
