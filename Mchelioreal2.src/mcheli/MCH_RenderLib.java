package mcheli;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;

public class MCH_RenderLib {
  public static void drawLine(Vec3d[] points, int color) {
    drawLine(points, color, 1, 1);
  }
  
  public static void drawLine(Vec3d[] points, int color, int mode, int width) {
    int prevWidth = GL11.glGetInteger(2849);
    GL11.glDisable(3553);
    GL11.glEnable(3042);
    GL11.glBlendFunc(770, 771);
    GL11.glColor4ub((byte)(color >> 16 & 0xFF), (byte)(color >> 8 & 0xFF), (byte)(color >> 0 & 0xFF), (byte)(color >> 24 & 0xFF));
    GL11.glLineWidth(width);
    Tessellator tessellator = Tessellator.func_178181_a();
    BufferBuilder builder = tessellator.func_178180_c();
    builder.func_181668_a(mode, DefaultVertexFormats.field_181705_e);
    for (Vec3d v : points)
      builder.func_181662_b(v.field_72450_a, v.field_72448_b, v.field_72449_c).func_181675_d(); 
    tessellator.func_78381_a();
    GL11.glEnable(3553);
    GL11.glDisable(3042);
    GL11.glColor4b((byte)-1, (byte)-1, (byte)-1, (byte)-1);
    GL11.glLineWidth(prevWidth);
  }
}
