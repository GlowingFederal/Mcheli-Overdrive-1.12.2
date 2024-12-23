package mcheli.particles;

import mcheli.MCH_Lib;
import mcheli.__helper.entity.ITargetMarkerObject;
import mcheli.multiplay.MCH_GuiTargetMarker;
import mcheli.wrapper.W_Reflection;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.scoreboard.Team;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public class MCH_EntityParticleMarkPoint extends MCH_EntityParticleBase implements ITargetMarkerObject {
  final Team taem;
  
  public MCH_EntityParticleMarkPoint(World par1World, double x, double y, double z, Team team) {
    super(par1World, x, y, z, 0.0D, 0.0D, 0.0D);
    setParticleMaxAge(30);
    this.taem = team;
  }
  
  public void func_189213_a() {
    this.field_187123_c = this.field_187126_f;
    this.field_187124_d = this.field_187127_g;
    this.field_187125_e = this.field_187128_h;
    EntityPlayerSP entityPlayerSP = (Minecraft.func_71410_x()).field_71439_g;
    if (entityPlayerSP == null) {
      func_187112_i();
    } else if (entityPlayerSP.func_96124_cp() == null && this.taem != null) {
      func_187112_i();
    } else if (entityPlayerSP.func_96124_cp() != null && !entityPlayerSP.func_184194_a(this.taem)) {
      func_187112_i();
    } 
  }
  
  public void func_187112_i() {
    super.func_187112_i();
    MCH_Lib.DbgLog(true, "MCH_EntityParticleMarkPoint.setExpired : " + this, new Object[0]);
  }
  
  public int func_70537_b() {
    return 3;
  }
  
  public void func_180434_a(BufferBuilder buffer, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
    GL11.glPushMatrix();
    Minecraft mc = Minecraft.func_71410_x();
    EntityPlayerSP entityPlayerSP = mc.field_71439_g;
    if (entityPlayerSP == null)
      return; 
    double ix = field_70556_an;
    double iy = field_70554_ao;
    double iz = field_70555_ap;
    if (mc.field_71474_y.field_74320_O > 0 && entityIn != null) {
      Entity viewer = entityIn;
      double dist = W_Reflection.getThirdPersonDistance();
      float yaw = (mc.field_71474_y.field_74320_O != 2) ? -viewer.field_70177_z : -viewer.field_70177_z;
      float pitch = (mc.field_71474_y.field_74320_O != 2) ? -viewer.field_70125_A : -viewer.field_70125_A;
      Vec3d v = MCH_Lib.RotVec3(0.0D, 0.0D, -dist, yaw, pitch);
      if (mc.field_71474_y.field_74320_O == 2)
        v = new Vec3d(-v.field_72450_a, -v.field_72448_b, -v.field_72449_c); 
      Vec3d vs = new Vec3d(viewer.field_70165_t, viewer.field_70163_u + viewer.func_70047_e(), viewer.field_70161_v);
      RayTraceResult mop = entityIn.field_70170_p.func_72933_a(vs.func_72441_c(0.0D, 0.0D, 0.0D), vs
          .func_72441_c(v.field_72450_a, v.field_72448_b, v.field_72449_c));
      double block_dist = dist;
      if (mop != null && mop.field_72313_a == RayTraceResult.Type.BLOCK) {
        block_dist = vs.func_72438_d(mop.field_72307_f) - 0.4D;
        if (block_dist < 0.0D)
          block_dist = 0.0D; 
      } 
      GL11.glTranslated(v.field_72450_a * block_dist / dist, v.field_72448_b * block_dist / dist, v.field_72449_c * block_dist / dist);
      ix += v.field_72450_a * block_dist / dist;
      iy += v.field_72448_b * block_dist / dist;
      iz += v.field_72449_c * block_dist / dist;
    } 
    double px = (float)(this.field_187123_c + (this.field_187126_f - this.field_187123_c) * partialTicks - ix);
    double py = (float)(this.field_187124_d + (this.field_187127_g - this.field_187124_d) * partialTicks - iy);
    double pz = (float)(this.field_187125_e + (this.field_187128_h - this.field_187125_e) * partialTicks - iz);
    double scale = Math.sqrt(px * px + py * py + pz * pz) / 100.0D;
    if (scale < 1.0D)
      scale = 1.0D; 
    MCH_GuiTargetMarker.addMarkEntityPos(100, this, px / scale, py / scale, pz / scale, false);
    GL11.glPopMatrix();
  }
  
  public double getX() {
    return this.field_187126_f;
  }
  
  public double getY() {
    return this.field_187127_g;
  }
  
  public double getZ() {
    return this.field_187128_h;
  }
}
