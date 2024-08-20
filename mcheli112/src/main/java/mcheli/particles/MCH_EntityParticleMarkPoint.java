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
  
  public void onUpdate() {
    this.prevPosX = this.posX;
    this.prevPosY = this.posY;
    this.prevPosZ = this.posZ;
    EntityPlayerSP entityPlayerSP = (Minecraft.getMinecraft()).player;
    if (entityPlayerSP == null) {
      setExpired();
    } else if (entityPlayerSP.getTeam() == null && this.taem != null) {
      setExpired();
    } else if (entityPlayerSP.getTeam() != null && !entityPlayerSP.isOnScoreboardTeam(this.taem)) {
      setExpired();
    } 
  }
  
  public void setExpired() {
    super.setExpired();
    MCH_Lib.DbgLog(true, "MCH_EntityParticleMarkPoint.setExpired : " + this, new Object[0]);
  }
  
  public int getFXLayer() {
    return 3;
  }
  
  public void renderParticle(BufferBuilder buffer, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
    GL11.glPushMatrix();
    Minecraft mc = Minecraft.getMinecraft();
    EntityPlayerSP entityPlayerSP = mc.player;
    if (entityPlayerSP == null)
      return; 
    double ix = interpPosX;
    double iy = interpPosY;
    double iz = interpPosZ;
    if (mc.gameSettings.thirdPersonView > 0 && entityIn != null) {
      Entity viewer = entityIn;
      double dist = W_Reflection.getThirdPersonDistance();
      float yaw = (mc.gameSettings.thirdPersonView != 2) ? -viewer.rotationYaw : -viewer.rotationYaw;
      float pitch = (mc.gameSettings.thirdPersonView != 2) ? -viewer.rotationPitch : -viewer.rotationPitch;
      Vec3d v = MCH_Lib.RotVec3(0.0D, 0.0D, -dist, yaw, pitch);
      if (mc.gameSettings.thirdPersonView == 2)
        v = new Vec3d(-v.x, -v.y, -v.z); 
      Vec3d vs = new Vec3d(viewer.posX, viewer.posY + viewer.getEyeHeight(), viewer.posZ);
      RayTraceResult mop = entityIn.world.rayTraceBlocks(vs.addVector(0.0D, 0.0D, 0.0D), vs
          .addVector(v.x, v.y, v.z));
      double block_dist = dist;
      if (mop != null && mop.typeOfHit == RayTraceResult.Type.BLOCK) {
        block_dist = vs.distanceTo(mop.hitVec) - 0.4D;
        if (block_dist < 0.0D)
          block_dist = 0.0D; 
      } 
      GL11.glTranslated(v.x * block_dist / dist, v.y * block_dist / dist, v.z * block_dist / dist);
      ix += v.x * block_dist / dist;
      iy += v.y * block_dist / dist;
      iz += v.z * block_dist / dist;
    } 
    double px = (float)(this.prevPosX + (this.posX - this.prevPosX) * partialTicks - ix);
    double py = (float)(this.prevPosY + (this.posY - this.prevPosY) * partialTicks - iy);
    double pz = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * partialTicks - iz);
    double scale = Math.sqrt(px * px + py * py + pz * pz) / 100.0D;
    if (scale < 1.0D)
      scale = 1.0D; 
    MCH_GuiTargetMarker.addMarkEntityPos(100, this, px / scale, py / scale, pz / scale, false);
    GL11.glPopMatrix();
  }
  
  public double getX() {
    return this.posX;
  }
  
  public double getY() {
    return this.posY;
  }
  
  public double getZ() {
    return this.posZ;
  }
}
