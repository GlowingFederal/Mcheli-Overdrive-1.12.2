package mcheli.plane;

import mcheli.aircraft.MCH_AircraftInfo;
import mcheli.aircraft.MCH_EntityAircraft;
import mcheli.aircraft.MCH_RenderAircraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class MCP_RenderPlane extends MCH_RenderAircraft<MCP_EntityPlane> {
  public static final IRenderFactory<MCP_EntityPlane> FACTORY = MCP_RenderPlane::new;
  
  public MCP_RenderPlane(RenderManager renderManager) {
    super(renderManager);
    this.field_76989_e = 2.0F;
  }
  
  public void renderAircraft(MCH_EntityAircraft entity, double posX, double posY, double posZ, float yaw, float pitch, float roll, float tickTime) {
    MCP_EntityPlane plane;
    MCP_PlaneInfo planeInfo = null;
    if (entity != null && entity instanceof MCP_EntityPlane) {
      plane = (MCP_EntityPlane)entity;
      planeInfo = plane.getPlaneInfo();
      if (planeInfo == null)
        return; 
    } else {
      return;
    } 
    posY += 0.3499999940395355D;
    renderDebugHitBox(plane, posX, posY, posZ, yaw, pitch);
    renderDebugPilotSeat(plane, posX, posY, posZ, yaw, pitch, roll);
    GL11.glTranslated(posX, posY, posZ);
    GL11.glRotatef(yaw, 0.0F, -1.0F, 0.0F);
    GL11.glRotatef(pitch, 1.0F, 0.0F, 0.0F);
    GL11.glRotatef(roll, 0.0F, 0.0F, 1.0F);
    bindTexture("textures/planes/" + plane.getTextureName() + ".png", plane);
    if (planeInfo.haveNozzle() && plane.partNozzle != null)
      renderNozzle(plane, planeInfo, tickTime); 
    if (planeInfo.haveWing() && plane.partWing != null)
      renderWing(plane, planeInfo, tickTime); 
    if (planeInfo.haveRotor() && plane.partNozzle != null)
      renderRotor(plane, planeInfo, tickTime); 
    renderBody(planeInfo.model);
  }
  
  public void renderRotor(MCP_EntityPlane plane, MCP_PlaneInfo planeInfo, float tickTime) {
    float rot = plane.getNozzleRotation();
    float prevRot = plane.getPrevNozzleRotation();
    for (MCP_PlaneInfo.Rotor r : planeInfo.rotorList) {
      GL11.glPushMatrix();
      GL11.glTranslated(r.pos.field_72450_a, r.pos.field_72448_b, r.pos.field_72449_c);
      GL11.glRotatef((prevRot + (rot - prevRot) * tickTime) * r.maxRotFactor, (float)r.rot.field_72450_a, (float)r.rot.field_72448_b, (float)r.rot.field_72449_c);
      GL11.glTranslated(-r.pos.field_72450_a, -r.pos.field_72448_b, -r.pos.field_72449_c);
      renderPart(r.model, planeInfo.model, r.modelName);
      for (MCP_PlaneInfo.Blade b : r.blades) {
        float br = plane.prevRotationRotor;
        br += (plane.rotationRotor - plane.prevRotationRotor) * tickTime;
        GL11.glPushMatrix();
        GL11.glTranslated(b.pos.field_72450_a, b.pos.field_72448_b, b.pos.field_72449_c);
        GL11.glRotatef(br, (float)b.rot.field_72450_a, (float)b.rot.field_72448_b, (float)b.rot.field_72449_c);
        GL11.glTranslated(-b.pos.field_72450_a, -b.pos.field_72448_b, -b.pos.field_72449_c);
        for (int i = 0; i < b.numBlade; i++) {
          GL11.glTranslated(b.pos.field_72450_a, b.pos.field_72448_b, b.pos.field_72449_c);
          GL11.glRotatef(b.rotBlade, (float)b.rot.field_72450_a, (float)b.rot.field_72448_b, (float)b.rot.field_72449_c);
          GL11.glTranslated(-b.pos.field_72450_a, -b.pos.field_72448_b, -b.pos.field_72449_c);
          renderPart(b.model, planeInfo.model, b.modelName);
        } 
        GL11.glPopMatrix();
      } 
      GL11.glPopMatrix();
    } 
  }
  
  public void renderWing(MCP_EntityPlane plane, MCP_PlaneInfo planeInfo, float tickTime) {
    float rot = plane.getWingRotation();
    float prevRot = plane.getPrevWingRotation();
    for (MCP_PlaneInfo.Wing w : planeInfo.wingList) {
      GL11.glPushMatrix();
      GL11.glTranslated(w.pos.field_72450_a, w.pos.field_72448_b, w.pos.field_72449_c);
      GL11.glRotatef((prevRot + (rot - prevRot) * tickTime) * w.maxRotFactor, (float)w.rot.field_72450_a, (float)w.rot.field_72448_b, (float)w.rot.field_72449_c);
      GL11.glTranslated(-w.pos.field_72450_a, -w.pos.field_72448_b, -w.pos.field_72449_c);
      renderPart(w.model, planeInfo.model, w.modelName);
      if (w.pylonList != null)
        for (MCP_PlaneInfo.Pylon p : w.pylonList) {
          GL11.glPushMatrix();
          GL11.glTranslated(p.pos.field_72450_a, p.pos.field_72448_b, p.pos.field_72449_c);
          GL11.glRotatef((prevRot + (rot - prevRot) * tickTime) * p.maxRotFactor, (float)p.rot.field_72450_a, (float)p.rot.field_72448_b, (float)p.rot.field_72449_c);
          GL11.glTranslated(-p.pos.field_72450_a, -p.pos.field_72448_b, -p.pos.field_72449_c);
          renderPart(p.model, planeInfo.model, p.modelName);
          GL11.glPopMatrix();
        }  
      GL11.glPopMatrix();
    } 
  }
  
  public void renderNozzle(MCP_EntityPlane plane, MCP_PlaneInfo planeInfo, float tickTime) {
    float rot = plane.getNozzleRotation();
    float prevRot = plane.getPrevNozzleRotation();
    for (MCH_AircraftInfo.DrawnPart n : planeInfo.nozzles) {
      GL11.glPushMatrix();
      GL11.glTranslated(n.pos.field_72450_a, n.pos.field_72448_b, n.pos.field_72449_c);
      GL11.glRotatef(prevRot + (rot - prevRot) * tickTime, (float)n.rot.field_72450_a, (float)n.rot.field_72448_b, (float)n.rot.field_72449_c);
      GL11.glTranslated(-n.pos.field_72450_a, -n.pos.field_72448_b, -n.pos.field_72449_c);
      renderPart(n.model, planeInfo.model, n.modelName);
      GL11.glPopMatrix();
    } 
  }
  
  protected ResourceLocation getEntityTexture(MCP_EntityPlane entity) {
    return TEX_DEFAULT;
  }
}
