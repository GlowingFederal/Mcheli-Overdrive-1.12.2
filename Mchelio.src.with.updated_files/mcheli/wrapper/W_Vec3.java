package mcheli.wrapper;

import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class W_Vec3 {
  public static Vec3d rotateRoll(float par1, Vec3d vOut) {
    float f1 = MathHelper.cos(par1);
    float f2 = MathHelper.sin(par1);
    double d0 = vOut.xCoord * f1 + vOut.yCoord * f2;
    double d1 = vOut.yCoord * f1 - vOut.xCoord * f2;
    double d2 = vOut.zCoord;
    return new Vec3d(d0, d1, d2);
  }
}
