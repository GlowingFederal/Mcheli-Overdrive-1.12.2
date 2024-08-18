package mcheli.vehicle;

import mcheli.MCH_Lib;
import mcheli.MCH_ModelManager;
import mcheli.aircraft.MCH_EntityAircraft;
import mcheli.aircraft.MCH_RenderAircraft;
import mcheli.weapon.MCH_WeaponSet;
import mcheli.wrapper.W_Lib;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class MCH_RenderVehicle extends MCH_RenderAircraft<MCH_EntityVehicle> {
  public static final IRenderFactory<MCH_EntityVehicle> FACTORY = MCH_RenderVehicle::new;
  
  public MCH_RenderVehicle(RenderManager renderManager) {
    super(renderManager);
    this.field_76989_e = 2.0F;
  }
  
  public void renderAircraft(MCH_EntityAircraft entity, double posX, double posY, double posZ, float yaw, float pitch, float roll, float tickTime) {
    MCH_EntityVehicle vehicle;
    MCH_VehicleInfo vehicleInfo = null;
    if (entity != null && entity instanceof MCH_EntityVehicle) {
      vehicle = (MCH_EntityVehicle)entity;
      vehicleInfo = vehicle.getVehicleInfo();
      if (vehicleInfo == null)
        return; 
    } else {
      return;
    } 
    if (vehicle.getRiddenByEntity() != null && !vehicle.isDestroyed()) {
      vehicle.isUsedPlayer = true;
      vehicle.lastRiderYaw = (vehicle.getRiddenByEntity()).field_70177_z;
      vehicle.lastRiderPitch = (vehicle.getRiddenByEntity()).field_70125_A;
    } else if (!vehicle.isUsedPlayer) {
      vehicle.lastRiderYaw = vehicle.field_70177_z;
      vehicle.lastRiderPitch = vehicle.field_70125_A;
    } 
    posY += 0.3499999940395355D;
    renderDebugHitBox(vehicle, posX, posY, posZ, yaw, pitch);
    renderDebugPilotSeat(vehicle, posX, posY, posZ, yaw, pitch, roll);
    GL11.glTranslated(posX, posY, posZ);
    GL11.glRotatef(yaw, 0.0F, -1.0F, 0.0F);
    GL11.glRotatef(pitch, 1.0F, 0.0F, 0.0F);
    bindTexture("textures/vehicles/" + vehicle.getTextureName() + ".png", vehicle);
    renderBody(vehicleInfo.model);
    MCH_WeaponSet ws = vehicle.getFirstSeatWeapon();
    drawPart(vehicle, vehicleInfo, yaw, pitch, ws, tickTime);
  }
  
  public void drawPart(MCH_EntityVehicle vehicle, MCH_VehicleInfo info, float yaw, float pitch, MCH_WeaponSet ws, float tickTime) {
    float rotBrl = ws.prevRotBarrel + (ws.rotBarrel - ws.prevRotBarrel) * tickTime;
    int index = 0;
    for (MCH_VehicleInfo.VPart vp : info.partList)
      index = drawPart(vp, vehicle, info, yaw, pitch, rotBrl, tickTime, ws, index); 
  }
  
  int drawPart(MCH_VehicleInfo.VPart vp, MCH_EntityVehicle vehicle, MCH_VehicleInfo info, float yaw, float pitch, float rotBrl, float tickTime, MCH_WeaponSet ws, int index) {
    GL11.glPushMatrix();
    float recoilBuf = 0.0F;
    if (index < ws.getWeaponNum()) {
      MCH_WeaponSet.Recoil r = ws.recoilBuf[index];
      recoilBuf = r.prevRecoilBuf + (r.recoilBuf - r.prevRecoilBuf) * tickTime;
    } 
    int bkIndex = index;
    if (vp.rotPitch || vp.rotYaw || vp.type == 1) {
      GL11.glTranslated(vp.pos.field_72450_a, vp.pos.field_72448_b, vp.pos.field_72449_c);
      if (vp.rotYaw)
        GL11.glRotatef(-vehicle.lastRiderYaw + yaw, 0.0F, 1.0F, 0.0F); 
      if (vp.rotPitch) {
        float p = MCH_Lib.RNG(vehicle.lastRiderPitch, info.minRotationPitch, info.maxRotationPitch);
        GL11.glRotatef(p - pitch, 1.0F, 0.0F, 0.0F);
      } 
      if (vp.type == 1)
        GL11.glRotatef(rotBrl, 0.0F, 0.0F, -1.0F); 
      GL11.glTranslated(-vp.pos.field_72450_a, -vp.pos.field_72448_b, -vp.pos.field_72449_c);
    } 
    if (vp.type == 2)
      GL11.glTranslated(0.0D, 0.0D, (-vp.recoilBuf * recoilBuf)); 
    if (vp.type == 2 || vp.type == 3)
      index++; 
    if (vp.child != null)
      for (MCH_VehicleInfo.VPart vcp : vp.child)
        index = drawPart(vcp, vehicle, info, yaw, pitch, rotBrl, recoilBuf, ws, index);  
    if (vp.drawFP || !W_Lib.isClientPlayer(vehicle.getRiddenByEntity()) || !W_Lib.isFirstPerson())
      if (vp.type != 3 || !vehicle.isWeaponNotCooldown(ws, bkIndex)) {
        renderPart(vp.model, info.model, vp.modelName);
        MCH_ModelManager.render("vehicles", vp.modelName);
      }  
    GL11.glPopMatrix();
    return index;
  }
  
  protected ResourceLocation getEntityTexture(MCH_EntityVehicle entity) {
    return TEX_DEFAULT;
  }
}
