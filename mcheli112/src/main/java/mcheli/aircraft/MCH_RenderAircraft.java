package mcheli.aircraft;

import javax.annotation.Nullable;

import mcheli.*;
import mcheli.__helper.MCH_ColorInt;
import mcheli.__helper.MCH_Utils;
import mcheli.__helper.client._IModelCustom;
import mcheli.__helper.client.renderer.MCH_Verts;
import mcheli.__helper.entity.ITargetMarkerObject;
import mcheli.debug._v3.WeaponPointRenderer;
import mcheli.gui.MCH_Gui;
import mcheli.lweapon.MCH_ClientLightWeaponTickHandler;
import mcheli.multiplay.MCH_GuiTargetMarker;
import mcheli.uav.MCH_EntityUavStation;
import mcheli.weapon.MCH_WeaponGuidanceSystem;
import mcheli.weapon.MCH_WeaponSet;
import mcheli.wrapper.W_Entity;
import mcheli.wrapper.W_EntityRenderer;
import mcheli.wrapper.W_Lib;
import mcheli.wrapper.W_Render;
import mcheli.wrapper.modelloader.W_ModelCustom;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;

public abstract class MCH_RenderAircraft<T extends MCH_EntityAircraft> extends W_Render<T> {
  public static boolean renderingEntity = false;
  
  public static _IModelCustom debugModel = null;
  
  protected MCH_RenderAircraft(RenderManager renderManager) {
    super(renderManager);
  }

  public void doRender(T entity, double posX, double posY, double posZ, float par8, float tickTime) {
    MCH_EntityAircraft ac = (MCH_EntityAircraft)entity;
    MCH_AircraftInfo info = ac.getAcInfo();
    if(info != null) {
      GL11.glPushMatrix();
      float yaw = this.calcRot(ac.getRotYaw(), ac.prevRotationYaw, tickTime);
      float pitch = ac.calcRotPitch(tickTime);
      float roll = this.calcRot(ac.getRotRoll(), ac.prevRotationRoll, tickTime);
      MCH_Config var10000 = MCH_MOD.config;
      if(MCH_Config.EnableModEntityRender.prmBool) {
        this.renderRiddenEntity(ac, tickTime, yaw, pitch + info.entityPitch, roll + info.entityRoll, info.entityWidth, info.entityHeight);
      }

      if(!shouldSkipRender(entity)) {
        this.setCommonRenderParam(info.smoothShading, ac.getBrightnessForRender());
        if(ac.isDestroyed()) {
          GL11.glColor4f(0.15F, 0.15F, 0.15F, 1.0F);
        } else {
          GL11.glColor4f(0.75F, 0.75F, 0.75F, 1.0F);
        }

        this.renderAircraft(ac, posX, posY, posZ, yaw, pitch, roll, tickTime);
        this.renderCommonPart(ac, info, posX, posY, posZ, tickTime);
        renderLight(posX, posY, posZ, tickTime, ac, info);
        this.restoreCommonRenderParam();
      }

      GL11.glPopMatrix();
      MCH_GuiTargetMarker.addMarkEntityPos(1, entity, posX, posY + (double)info.markerHeight, posZ);
      MCH_ClientLightWeaponTickHandler.markEntity(entity, posX, posY, posZ);
      renderEntityMarker(ac);
    }

  }
  
  public boolean shouldRender(T livingEntity, ICamera camera, double camX, double camY, double camZ) {
    return true;
  }
  
  public static boolean shouldSkipRender(Entity entity) {
    if (entity instanceof MCH_IEntityCanRideAircraft) {
      MCH_IEntityCanRideAircraft e = (MCH_IEntityCanRideAircraft)entity;
      if (e.isSkipNormalRender())
        return !renderingEntity; 
    } else if (entity.getClass().toString().indexOf("flansmod.common.driveables.EntityPlane") > 0 || entity
      .getClass().toString().indexOf("flansmod.common.driveables.EntityVehicle") > 0) {
      if (entity.getRidingEntity() instanceof MCH_EntitySeat)
        return !renderingEntity; 
    } 
    return false;
  }
  
  public void doRenderShadowAndFire(Entity entity, double x, double y, double z, float yaw, float partialTicks) {
    if (entity.canRenderOnFire())
      renderEntityOnFire(entity, x, y, z, partialTicks); 
  }
  
  private void renderEntityOnFire(Entity entity, double x, double y, double z, float tick) {
    GL11.glDisable(2896);
    TextureMap texturemap = Minecraft.getMinecraft().getTextureMapBlocks();
    TextureAtlasSprite textureatlassprite = texturemap.getAtlasSprite("minecraft:blocks/fire_layer_0");
    TextureAtlasSprite textureatlassprite1 = texturemap.getAtlasSprite("minecraft:blocks/fire_layer_1");
    GL11.glPushMatrix();
    GL11.glTranslatef((float)x, (float)y, (float)z);
    float f1 = entity.width * 1.4F;
    GL11.glScalef(f1 * 2.0F, f1 * 2.0F, f1 * 2.0F);
    Tessellator tessellator = Tessellator.getInstance();
    BufferBuilder bufferbuilder = tessellator.getBuffer();
    float f2 = 1.5F;
    float f3 = 0.0F;
    float f4 = entity.height / f1;
    float f5 = (float)(entity.posY + (entity.getEntityBoundingBox()).minY);
    GL11.glRotatef(-this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
    GL11.glTranslatef(0.0F, 0.0F, -0.3F + (int)f4 * 0.02F);
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    float f6 = 0.0F;
    int i = 0;
    bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
    while (f4 > 0.0F) {
      TextureAtlasSprite textureatlassprite2 = (i % 2 == 0) ? textureatlassprite : textureatlassprite1;
      bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
      float f7 = textureatlassprite2.getMinU();
      float f8 = textureatlassprite2.getMinV();
      float f9 = textureatlassprite2.getMaxU();
      float f10 = textureatlassprite2.getMaxV();
      if (i / 2 % 2 == 0) {
        float f11 = f9;
        f9 = f7;
        f7 = f11;
      } 
      bufferbuilder.pos((f2 - f3), (0.0F - f5), f6).tex(f9, f10).endVertex();
      bufferbuilder.pos((-f2 - f3), (0.0F - f5), f6).tex(f7, f10).endVertex();
      bufferbuilder.pos((-f2 - f3), (1.4F - f5), f6).tex(f7, f8).endVertex();
      bufferbuilder.pos((f2 - f3), (1.4F - f5), f6).tex(f9, f8).endVertex();
      f4 -= 0.45F;
      f5 -= 0.45F;
      f2 *= 0.9F;
      f6 += 0.03F;
      i++;
    } 
    tessellator.draw();
    GL11.glPopMatrix();
    GL11.glEnable(2896);
  }
  
  public static void renderLight(double x, double y, double z, float tickTime, MCH_EntityAircraft ac, MCH_AircraftInfo info) {
    if (!ac.haveSearchLight())
      return; 
    if (!ac.isSearchLightON())
      return; 
    Entity entity = ac.getEntityBySeatId(1);
    if (entity != null) {
      ac.lastSearchLightYaw = entity.rotationYaw;
      ac.lastSearchLightPitch = entity.rotationPitch;
    } else {
      entity = ac.getEntityBySeatId(0);
      if (entity != null) {
        ac.lastSearchLightYaw = entity.rotationYaw;
        ac.lastSearchLightPitch = entity.rotationPitch;
      } 
    } 
    float yaw = ac.lastSearchLightYaw;
    float pitch = ac.lastSearchLightPitch;
    RenderHelper.disableStandardItemLighting();
    GL11.glDisable(3553);
    GL11.glShadeModel(7425);
    GL11.glEnable(3042);
    GL11.glBlendFunc(770, 1);
    GL11.glDisable(3008);
    GL11.glDisable(2884);
    GL11.glDepthMask(false);
    float rot = ac.prevRotYawWheel + (ac.rotYawWheel - ac.prevRotYawWheel) * tickTime;
    for (MCH_AircraftInfo.SearchLight sl : info.searchLights) {
      GL11.glPushMatrix();
      GL11.glTranslated(sl.pos.x, sl.pos.y, sl.pos.z);
      if (!sl.fixDir) {
        GL11.glRotatef(yaw - ac.getRotYaw() + sl.yaw, 0.0F, -1.0F, 0.0F);
        GL11.glRotatef(pitch + 90.0F - ac.getRotPitch() + sl.pitch, 1.0F, 0.0F, 0.0F);
      } else {
        float stRot = 0.0F;
        if (sl.steering)
          stRot = -rot * sl.stRot; 
        GL11.glRotatef(0.0F + sl.yaw + stRot, 0.0F, -1.0F, 0.0F);
        GL11.glRotatef(90.0F + sl.pitch, 1.0F, 0.0F, 0.0F);
      } 
      float height = sl.height;
      float width = sl.width / 2.0F;
      Tessellator tessellator = Tessellator.getInstance();
      BufferBuilder builder = tessellator.getBuffer();
      builder.begin(6, DefaultVertexFormats.POSITION_COLOR);
      MCH_ColorInt cs = new MCH_ColorInt(sl.colorStart);
      MCH_ColorInt ce = new MCH_ColorInt(sl.colorEnd);
      builder.pos(0.0D, 0.0D, 0.0D).color(cs.r, cs.g, cs.b, cs.a).endVertex();
      for (int i = 0; i < 25; i++) {
        float angle = (float)(15.0D * i / 180.0D * Math.PI);
        builder.pos((MathHelper.sin(angle) * width), height, (MathHelper.cos(angle) * width))
          .color(ce.r, ce.g, ce.b, ce.a).endVertex();
      } 
      tessellator.draw();
      GL11.glPopMatrix();
    } 
    GL11.glDepthMask(true);
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    GL11.glEnable(3553);
    GL11.glEnable(3008);
    GL11.glBlendFunc(770, 771);
    RenderHelper.enableStandardItemLighting();
  }
  
  protected void bindTexture(String path, MCH_EntityAircraft ac) {
    if (ac == MCH_ClientCommonTickHandler.ridingAircraft) {
      int bk = MCH_ClientCommonTickHandler.cameraMode;
      MCH_ClientCommonTickHandler.cameraMode = 0;
      bindTexture(MCH_Utils.suffix(path));
      MCH_ClientCommonTickHandler.cameraMode = bk;
    } else {
      bindTexture(MCH_Utils.suffix(path));
    } 
  }
  
  public void renderRiddenEntity(MCH_EntityAircraft ac, float tickTime, float yaw, float pitch, float roll, float width, float height) {
    MCH_ClientEventHook.setCancelRender(false);
    GL11.glPushMatrix();
    renderEntitySimple(ac, ac.getRiddenByEntity(), tickTime, yaw, pitch, roll, width, height);
    for (MCH_EntitySeat s : ac.getSeats()) {
      if (s != null)
        renderEntitySimple(ac, s.getRiddenByEntity(), tickTime, yaw, pitch, roll, width, height); 
    } 
    GL11.glPopMatrix();
    MCH_ClientEventHook.setCancelRender(true);
  }
  
  public void renderEntitySimple(MCH_EntityAircraft ac, Entity entity, float tickTime, float yaw, float pitch, float roll, float width, float height) {
    if (entity != null) {
      boolean isPilot = ac.isPilot(entity);
      boolean isClientPlayer = W_Lib.isClientPlayer(entity);
      if (!isClientPlayer || !W_Lib.isFirstPerson() || (isClientPlayer && isPilot && ac.getCameraId() > 0)) {
        GL11.glPushMatrix();
        if (entity.ticksExisted == 0) {
          entity.lastTickPosX = entity.posX;
          entity.lastTickPosY = entity.posY;
          entity.lastTickPosZ = entity.posZ;
        } 
        double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * tickTime;
        double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * tickTime;
        double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * tickTime;
        float f1 = entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * tickTime;
        int i = entity.getBrightnessForRender();
        if (entity.isBurning())
          i = 15728880; 
        int j = i % 65536;
        int k = i / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, j / 1.0F, k / 1.0F);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        double dx = x - TileEntityRendererDispatcher.staticPlayerX;
        double dy = y - TileEntityRendererDispatcher.staticPlayerY;
        double dz = z - TileEntityRendererDispatcher.staticPlayerZ;
        GL11.glTranslated(dx, dy, dz);
        GL11.glRotatef(yaw, 0.0F, -1.0F, 0.0F);
        GL11.glRotatef(pitch, 1.0F, 0.0F, 0.0F);
        GL11.glRotatef(roll, 0.0F, 0.0F, 1.0F);
        GL11.glScaled(width, height, width);
        GL11.glRotatef(-yaw, 0.0F, -1.0F, 0.0F);
        GL11.glTranslated(-dx, -dy, -dz);
        boolean bk = renderingEntity;
        renderingEntity = true;
        Entity ridingEntity = entity.getRidingEntity();
        if (!W_Lib.isEntityLivingBase(entity) && !(entity instanceof MCH_IEntityCanRideAircraft))
          entity.dismountRidingEntity(); 
        EntityLivingBase entityLiving = (entity instanceof EntityLivingBase) ? (EntityLivingBase)entity : null;
        float bkPitch = 0.0F;
        float bkPrevPitch = 0.0F;
        if (isPilot && entityLiving != null) {
          entityLiving.renderYawOffset = ac.getRotYaw();
          entityLiving.prevRenderYawOffset = ac.getRotYaw();
          if (ac.getCameraId() > 0) {
            entityLiving.rotationYawHead = ac.getRotYaw();
            entityLiving.prevRotationYawHead = ac.getRotYaw();
            bkPitch = entityLiving.rotationPitch;
            bkPrevPitch = entityLiving.prevRotationPitch;
            entityLiving.rotationPitch = ac.getRotPitch();
            entityLiving.prevRotationPitch = ac.getRotPitch();
          } 
        } 
        if (isClientPlayer) {
          Entity viewEntity = this.renderManager.renderViewEntity;
          this.renderManager.renderViewEntity = entity;
          W_EntityRenderer.renderEntityWithPosYaw(this.renderManager, entity, dx, dy, dz, f1, tickTime, false);
          this.renderManager.renderViewEntity = viewEntity;
        } else {
          W_EntityRenderer.renderEntityWithPosYaw(this.renderManager, entity, dx, dy, dz, f1, tickTime, false);
        } 
        if (isPilot && entityLiving != null)
          if (ac.getCameraId() > 0) {
            entityLiving.rotationPitch = bkPitch;
            entityLiving.prevRotationPitch = bkPrevPitch;
          }  
        entity.startRiding(ridingEntity);
        renderingEntity = bk;
        GL11.glPopMatrix();
      } 
    } 
  }
  
  public static void Test_Material(int light, float a, float b, float c) {
    GL11.glMaterial(1032, light, setColorBuffer(a, b, c, 1.0F));
  }
  
  public static void Test_Light(int light, float a, float b, float c) {
    GL11.glLight(16384, light, setColorBuffer(a, b, c, 1.0F));
    GL11.glLight(16385, light, setColorBuffer(a, b, c, 1.0F));
  }
  
  public float calcRot(float rot, float prevRot, float tickTime) {
    rot = MathHelper.wrapDegrees(rot);
    prevRot = MathHelper.wrapDegrees(prevRot);
    if (rot - prevRot < -180.0F) {
      prevRot -= 360.0F;
    } else if (prevRot - rot < -180.0F) {
      prevRot += 360.0F;
    } 
    return prevRot + (rot - prevRot) * tickTime;
  }
  
  public void renderDebugHitBox(MCH_EntityAircraft e, double x, double y, double z, float yaw, float pitch) {
    if (MCH_Config.TestMode.prmBool && debugModel != null) {
      GL11.glPushMatrix();
      GL11.glTranslated(x, y, z);
      GL11.glScalef(e.width, e.height, e.width);
      bindTexture("textures/hit_box.png");
      debugModel.renderAll();
      GL11.glPopMatrix();
      GL11.glPushMatrix();
      GL11.glTranslated(x, y, z);
      for (MCH_BoundingBox bb : e.extraBoundingBox) {
        GL11.glPushMatrix();
        GL11.glTranslated(bb.rotatedOffset.x, bb.rotatedOffset.y, bb.rotatedOffset.z);
        GL11.glPushMatrix();
        GL11.glScalef(bb.width, bb.height, bb.width);
        bindTexture("textures/bounding_box.png");
        debugModel.renderAll();
        GL11.glPopMatrix();
        drawHitBoxDetail(bb);
        GL11.glPopMatrix();
      } 
      GL11.glPopMatrix();
    } 
  }
  
  public void drawHitBoxDetail(MCH_BoundingBox bb) {
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    float f1 = 0.080000006F;
    String s = String.format("%.2f", new Object[] { Float.valueOf(bb.damegeFactor) });
    GL11.glPushMatrix();
    GL11.glTranslatef(0.0F, 0.5F + (float)(bb.offsetY * 0.0D + bb.height), 0.0F);
    GL11.glNormal3f(0.0F, 1.0F, 0.0F);
    GL11.glRotatef(-this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
    GL11.glRotatef(this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
    GL11.glScalef(-f1, -f1, f1);
    GL11.glDisable(2896);
    GL11.glEnable(3042);
    OpenGlHelper.glBlendFunc(770, 771, 1, 0);
    GL11.glDisable(3553);
    FontRenderer fontrenderer = getFontRendererFromRenderManager();
    Tessellator tessellator = Tessellator.getInstance();
    BufferBuilder builder = tessellator.getBuffer();
    builder.begin(7, DefaultVertexFormats.POSITION_COLOR);
    int i = fontrenderer.getStringWidth(s) / 2;
    builder.pos((-i - 1), -1.0D, 0.1D).color(0.0F, 0.0F, 0.0F, 0.4F).endVertex();
    builder.pos((-i - 1), 8.0D, 0.1D).color(0.0F, 0.0F, 0.0F, 0.4F).endVertex();
    builder.pos((i + 1), 8.0D, 0.1D).color(0.0F, 0.0F, 0.0F, 0.4F).endVertex();
    builder.pos((i + 1), -1.0D, 0.1D).color(0.0F, 0.0F, 0.0F, 0.4F).endVertex();
    tessellator.draw();
    GL11.glEnable(3553);
    GL11.glDepthMask(false);
    int color = (bb.damegeFactor > 1.0F) ? 16711680 : ((bb.damegeFactor < 1.0F) ? 65535 : 16777215);
    fontrenderer.drawString(s, -fontrenderer.getStringWidth(s) / 2, 0, 0xC0000000 | color);
    GL11.glDepthMask(true);
    GL11.glEnable(2896);
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    GL11.glPopMatrix();
  }
  
  public void renderDebugPilotSeat(MCH_EntityAircraft e, double x, double y, double z, float yaw, float pitch, float roll) {
    if (MCH_Config.TestMode.prmBool && debugModel != null) {
      GL11.glPushMatrix();
      MCH_SeatInfo seat = e.getSeatInfo(0);
      GL11.glTranslated(x, y, z);
      GL11.glRotatef(yaw, 0.0F, -1.0F, 0.0F);
      GL11.glRotatef(pitch, 1.0F, 0.0F, 0.0F);
      GL11.glRotatef(roll, 0.0F, 0.0F, 1.0F);
      GL11.glTranslated(seat.pos.x, seat.pos.y, seat.pos.z);
      GL11.glScalef(1.0F, 1.0F, 1.0F);
      bindTexture("textures/seat_pilot.png");
      debugModel.renderAll();
      GL11.glPopMatrix();
    } 
  }
  
  public static void renderBody(@Nullable _IModelCustom model) {
    if (model != null)
      if (model instanceof W_ModelCustom) {
        if (((W_ModelCustom)model).containsPart("$body")) {
          model.renderPart("$body");
        } else {
          model.renderAll();
        } 
      } else {
        model.renderAll();
      }  
  }
  
  public static void renderPart(@Nullable _IModelCustom model, @Nullable _IModelCustom modelBody, String partName) {
    if (model != null) {
      model.renderAll();
    } else if (modelBody instanceof W_ModelCustom) {
      if (((W_ModelCustom)modelBody).containsPart("$" + partName))
        modelBody.renderPart("$" + partName); 
    } 
  }
  
  public void renderCommonPart(MCH_EntityAircraft ac, MCH_AircraftInfo info, double x, double y, double z, float tickTime) {
    renderRope(ac, info, x, y, z, tickTime);
    renderWeapon(ac, info, tickTime);
    renderRotPart(ac, info, tickTime);
    renderHatch(ac, info, tickTime);
    renderTrackRoller(ac, info, tickTime);
    renderCrawlerTrack(ac, info, tickTime);
    renderSteeringWheel(ac, info, tickTime);
    renderLightHatch(ac, info, tickTime);
    renderWheel(ac, info, tickTime);
    renderThrottle(ac, info, tickTime);
    renderCamera(ac, info, tickTime);
    renderLandingGear(ac, info, tickTime);
    renderWeaponBay(ac, info, tickTime);
    renderCanopy(ac, info, tickTime);
  }
  
  public static void renderLightHatch(MCH_EntityAircraft ac, MCH_AircraftInfo info, float tickTime) {
    if (info.lightHatchList.size() <= 0)
      return; 
    float rot = ac.prevRotLightHatch + (ac.rotLightHatch - ac.prevRotLightHatch) * tickTime;
    for (MCH_AircraftInfo.Hatch t : info.lightHatchList) {
      GL11.glPushMatrix();
      GL11.glTranslated(t.pos.x, t.pos.y, t.pos.z);
      GL11.glRotated((rot * t.maxRot), t.rot.x, t.rot.y, t.rot.z);
      GL11.glTranslated(-t.pos.x, -t.pos.y, -t.pos.z);
      renderPart(t.model, info.model, t.modelName);
      GL11.glPopMatrix();
    } 
  }
  
  public static void renderSteeringWheel(MCH_EntityAircraft ac, MCH_AircraftInfo info, float tickTime) {
    if (info.partSteeringWheel.size() <= 0)
      return; 
    float rot = ac.prevRotYawWheel + (ac.rotYawWheel - ac.prevRotYawWheel) * tickTime;
    for (MCH_AircraftInfo.PartWheel t : info.partSteeringWheel) {
      GL11.glPushMatrix();
      GL11.glTranslated(t.pos.x, t.pos.y, t.pos.z);
      GL11.glRotated((rot * t.rotDir), t.rot.x, t.rot.y, t.rot.z);
      GL11.glTranslated(-t.pos.x, -t.pos.y, -t.pos.z);
      renderPart(t.model, info.model, t.modelName);
      GL11.glPopMatrix();
    } 
  }
  
  public static void renderWheel(MCH_EntityAircraft ac, MCH_AircraftInfo info, float tickTime) {
    if (info.partWheel.size() <= 0)
      return; 
    float yaw = ac.prevRotYawWheel + (ac.rotYawWheel - ac.prevRotYawWheel) * tickTime;
    for (MCH_AircraftInfo.PartWheel t : info.partWheel) {
      GL11.glPushMatrix();
      GL11.glTranslated(t.pos2.x, t.pos2.y, t.pos2.z);
      GL11.glRotated((yaw * t.rotDir), t.rot.x, t.rot.y, t.rot.z);
      GL11.glTranslated(-t.pos2.x, -t.pos2.y, -t.pos2.z);
      GL11.glTranslated(t.pos.x, t.pos.y, t.pos.z);
      GL11.glRotatef(ac.prevRotWheel + (ac.rotWheel - ac.prevRotWheel) * tickTime, 1.0F, 0.0F, 0.0F);
      GL11.glTranslated(-t.pos.x, -t.pos.y, -t.pos.z);
      renderPart(t.model, info.model, t.modelName);
      GL11.glPopMatrix();
    } 
  }
  
  public static void renderRotPart(MCH_EntityAircraft ac, MCH_AircraftInfo info, float tickTime) {
    if (!ac.haveRotPart())
      return; 
    for (int i = 0; i < ac.rotPartRotation.length; i++) {
      float rot = ac.rotPartRotation[i];
      float prevRot = ac.prevRotPartRotation[i];
      if (prevRot > rot)
        rot += 360.0F; 
      rot = MCH_Lib.smooth(rot, prevRot, tickTime);
      MCH_AircraftInfo.RotPart h = info.partRotPart.get(i);
      GL11.glPushMatrix();
      GL11.glTranslated(h.pos.x, h.pos.y, h.pos.z);
      GL11.glRotatef(rot, (float)h.rot.x, (float)h.rot.y, (float)h.rot.z);
      GL11.glTranslated(-h.pos.x, -h.pos.y, -h.pos.z);
      renderPart(h.model, info.model, h.modelName);
      GL11.glPopMatrix();
    } 
  }
  
  public static void renderWeapon(MCH_EntityAircraft ac, MCH_AircraftInfo info, float tickTime) {
    MCH_WeaponSet beforeWs = null;
    Entity e = ac.getRiddenByEntity();
    int weaponIndex = 0;
    for (MCH_AircraftInfo.PartWeapon w : info.partWeapon) {
      MCH_WeaponSet ws = ac.getWeaponByName(w.name[0]);
      if (ws != beforeWs) {
        weaponIndex = 0;
        beforeWs = ws;
      } 
      float rotYaw = 0.0F;
      float prevYaw = 0.0F;
      float rotPitch = 0.0F;
      float prevPitch = 0.0F;
      if (w.hideGM && W_Lib.isFirstPerson())
        if (ws != null) {
          boolean hide = false;
          for (String s : w.name) {
            if (W_Lib.isClientPlayer(ac.getWeaponUserByWeaponName(s))) {
              hide = true;
              break;
            } 
          } 
          if (hide)
            continue; 
        } else if (ac.isMountedEntity(MCH_Lib.getClientPlayer())) {
          continue;
        }  
      GL11.glPushMatrix();
      if (w.turret) {
        GL11.glTranslated(info.turretPosition.x, info.turretPosition.y, info.turretPosition.z);
        float ty = MCH_Lib.smooth(ac.getLastRiderYaw() - ac.getRotYaw(), ac.prevLastRiderYaw - ac.prevRotationYaw, tickTime);
        GL11.glRotatef(ty, 0.0F, -1.0F, 0.0F);
        GL11.glTranslated(-info.turretPosition.x, -info.turretPosition.y, -info.turretPosition.z);
      } 
      GL11.glTranslated(w.pos.x, w.pos.y, w.pos.z);
      if (w.yaw) {
        if (ws != null) {
          rotYaw = ws.rotationYaw - ws.defaultRotationYaw;
          prevYaw = ws.prevRotationYaw - ws.defaultRotationYaw;
        } else if (e != null) {
          rotYaw = e.rotationYaw - ac.getRotYaw();
          prevYaw = e.prevRotationYaw - ac.prevRotationYaw;
        } else {
          rotYaw = ac.getLastRiderYaw() - ac.rotationYaw;
          prevYaw = ac.prevLastRiderYaw - ac.prevRotationYaw;
        } 
        if (rotYaw - prevYaw > 180.0F) {
          prevYaw += 360.0F;
        } else if (rotYaw - prevYaw < -180.0F) {
          prevYaw -= 360.0F;
        } 
        GL11.glRotatef(prevYaw + (rotYaw - prevYaw) * tickTime, 0.0F, -1.0F, 0.0F);
      } 
      if (w.turret) {
        float ty = MCH_Lib.smooth(ac.getLastRiderYaw() - ac.getRotYaw(), ac.prevLastRiderYaw - ac.prevRotationYaw, tickTime);
        ty -= ws.rotationTurretYaw;
        GL11.glRotatef(-ty, 0.0F, -1.0F, 0.0F);
      } 
      boolean rev_sign = false;
      if (ws != null && (int)ws.defaultRotationYaw != 0) {
        float t = MathHelper.wrapDegrees(ws.defaultRotationYaw);
        rev_sign = ((t >= 45.0F && t <= 135.0F) || (t <= -45.0F && t >= -135.0F));
        GL11.glRotatef(-ws.defaultRotationYaw, 0.0F, -1.0F, 0.0F);
      } 
      if (w.pitch) {
        if (ws != null) {
          rotPitch = ws.rotationPitch;
          prevPitch = ws.prevRotationPitch;
        } else if (e != null) {
          rotPitch = e.rotationPitch;
          prevPitch = e.prevRotationPitch;
        } else {
          rotPitch = ac.getLastRiderPitch();
          prevPitch = ac.prevLastRiderPitch;
        } 
        if (rev_sign) {
          rotPitch = -rotPitch;
          prevPitch = -prevPitch;
        } 
        GL11.glRotatef(prevPitch + (rotPitch - prevPitch) * tickTime, 1.0F, 0.0F, 0.0F);
      } 
      if (ws != null && w.recoilBuf != 0.0F) {
        MCH_WeaponSet.Recoil r = ws.recoilBuf[0];
        if (w.name.length > 1)
          for (String wnm : w.name) {
            MCH_WeaponSet tws = ac.getWeaponByName(wnm);
            if (tws != null && (tws.recoilBuf[0]).recoilBuf > r.recoilBuf)
              r = tws.recoilBuf[0]; 
          }  
        float recoilBuf = r.prevRecoilBuf + (r.recoilBuf - r.prevRecoilBuf) * tickTime;
        GL11.glTranslated(0.0D, 0.0D, (w.recoilBuf * recoilBuf));
      } 
      if (ws != null) {
        GL11.glRotatef(ws.defaultRotationYaw, 0.0F, -1.0F, 0.0F);
        if (w.rotBarrel) {
          float rotBrl = ws.prevRotBarrel + (ws.rotBarrel - ws.prevRotBarrel) * tickTime;
          GL11.glRotatef(rotBrl, (float)w.rot.x, (float)w.rot.y, (float)w.rot.z);
        } 
      } 
      GL11.glTranslated(-w.pos.x, -w.pos.y, -w.pos.z);
      if (!w.isMissile || !ac.isWeaponNotCooldown(ws, weaponIndex)) {
        renderPart(w.model, info.model, w.modelName);
        for (MCH_AircraftInfo.PartWeaponChild wc : w.child) {
          GL11.glPushMatrix();
          renderWeaponChild(ac, info, wc, ws, e, tickTime);
          GL11.glPopMatrix();
        } 
      } 
      GL11.glPopMatrix();
      weaponIndex++;
    } 
  }
  
  public static void renderWeaponChild(MCH_EntityAircraft ac, MCH_AircraftInfo info, MCH_AircraftInfo.PartWeaponChild w, MCH_WeaponSet ws, Entity e, float tickTime) {
    float rotYaw = 0.0F;
    float prevYaw = 0.0F;
    float rotPitch = 0.0F;
    float prevPitch = 0.0F;
    GL11.glTranslated(w.pos.x, w.pos.y, w.pos.z);
    if (w.yaw) {
      if (ws != null) {
        rotYaw = ws.rotationYaw - ws.defaultRotationYaw;
        prevYaw = ws.prevRotationYaw - ws.defaultRotationYaw;
      } else if (e != null) {
        rotYaw = e.rotationYaw - ac.getRotYaw();
        prevYaw = e.prevRotationYaw - ac.prevRotationYaw;
      } else {
        rotYaw = ac.getLastRiderYaw() - ac.rotationYaw;
        prevYaw = ac.prevLastRiderYaw - ac.prevRotationYaw;
      } 
      if (rotYaw - prevYaw > 180.0F) {
        prevYaw += 360.0F;
      } else if (rotYaw - prevYaw < -180.0F) {
        prevYaw -= 360.0F;
      } 
      GL11.glRotatef(prevYaw + (rotYaw - prevYaw) * tickTime, 0.0F, -1.0F, 0.0F);
    } 
    boolean rev_sign = false;
    if (ws != null && (int)ws.defaultRotationYaw != 0) {
      float t = MathHelper.wrapDegrees(ws.defaultRotationYaw);
      rev_sign = ((t >= 45.0F && t <= 135.0F) || (t <= -45.0F && t >= -135.0F));
      GL11.glRotatef(-ws.defaultRotationYaw, 0.0F, -1.0F, 0.0F);
    } 
    if (w.pitch) {
      if (ws != null) {
        rotPitch = ws.rotationPitch;
        prevPitch = ws.prevRotationPitch;
      } else if (e != null) {
        rotPitch = e.rotationPitch;
        prevPitch = e.prevRotationPitch;
      } else {
        rotPitch = ac.getLastRiderPitch();
        prevPitch = ac.prevLastRiderPitch;
      } 
      if (rev_sign) {
        rotPitch = -rotPitch;
        prevPitch = -prevPitch;
      } 
      GL11.glRotatef(prevPitch + (rotPitch - prevPitch) * tickTime, 1.0F, 0.0F, 0.0F);
    } 
    if (ws != null && w.recoilBuf != 0.0F) {
      MCH_WeaponSet.Recoil r = ws.recoilBuf[0];
      if (w.name.length > 1)
        for (String wnm : w.name) {
          MCH_WeaponSet tws = ac.getWeaponByName(wnm);
          if (tws != null && (tws.recoilBuf[0]).recoilBuf > r.recoilBuf)
            r = tws.recoilBuf[0]; 
        }  
      float recoilBuf = r.prevRecoilBuf + (r.recoilBuf - r.prevRecoilBuf) * tickTime;
      GL11.glTranslated(0.0D, 0.0D, (-w.recoilBuf * recoilBuf));
    } 
    if (ws != null)
      GL11.glRotatef(ws.defaultRotationYaw, 0.0F, -1.0F, 0.0F); 
    GL11.glTranslated(-w.pos.x, -w.pos.y, -w.pos.z);
    renderPart(w.model, info.model, w.modelName);
  }
  
  public static void renderTrackRoller(MCH_EntityAircraft ac, MCH_AircraftInfo info, float tickTime) {
    if (info.partTrackRoller.size() <= 0)
      return; 
    float[] rot = ac.rotTrackRoller;
    float[] prevRot = ac.prevRotTrackRoller;
    for (MCH_AircraftInfo.TrackRoller t : info.partTrackRoller) {
      GL11.glPushMatrix();
      GL11.glTranslated(t.pos.x, t.pos.y, t.pos.z);
      GL11.glRotatef(prevRot[t.side] + (rot[t.side] - prevRot[t.side]) * tickTime, 1.0F, 0.0F, 0.0F);
      GL11.glTranslated(-t.pos.x, -t.pos.y, -t.pos.z);
      renderPart(t.model, info.model, t.modelName);
      GL11.glPopMatrix();
    } 
  }
  
  public static void renderCrawlerTrack(MCH_EntityAircraft ac, MCH_AircraftInfo info, float tickTime) {
    if (info.partCrawlerTrack.size() <= 0)
      return; 
    int prevWidth = GL11.glGetInteger(2833);
    Tessellator tessellator = Tessellator.getInstance();
    BufferBuilder builder = tessellator.getBuffer();
    for (MCH_AircraftInfo.CrawlerTrack c : info.partCrawlerTrack) {
      GL11.glPointSize(c.len * 20.0F);
      if (MCH_Config.TestMode.prmBool) {
        GL11.glDisable(3553);
        GL11.glDisable(3042);
        builder.begin(0, DefaultVertexFormats.POSITION_COLOR);
        for (int j = 0; j < c.cx.length; j++)
          builder.pos(c.z, c.cx[j], c.cy[j])
            .color((int)(255.0F / c.cx.length * j), 80, 255 - (int)(255.0F / c.cx.length * j), 255)
            .endVertex(); 
        tessellator.draw();
      } 
      GL11.glEnable(3553);
      GL11.glEnable(3042);
      int L = c.lp.size() - 1;
      double rc = (ac != null) ? ac.rotCrawlerTrack[c.side] : 0.0D;
      double pc = (ac != null) ? ac.prevRotCrawlerTrack[c.side] : 0.0D;
      for (int i = 0; i < L; i++) {
        MCH_AircraftInfo.CrawlerTrackPrm cp = c.lp.get(i);
        MCH_AircraftInfo.CrawlerTrackPrm np = c.lp.get((i + 1) % L);
        double x1 = cp.x;
        double x2 = np.x;
        double r1 = cp.r;
        double y1 = cp.y;
        double y2 = np.y;
        double r2 = np.r;
        if (r2 - r1 < -180.0D)
          r2 += 360.0D; 
        if (r2 - r1 > 180.0D)
          r2 -= 360.0D; 
        double sx = x1 + (x2 - x1) * rc;
        double sy = y1 + (y2 - y1) * rc;
        double sr = r1 + (r2 - r1) * rc;
        double ex = x1 + (x2 - x1) * pc;
        double ey = y1 + (y2 - y1) * pc;
        double er = r1 + (r2 - r1) * pc;
        double x = sx + (ex - sx) * pc;
        double y = sy + (ey - sy) * pc;
        double r = sr + (er - sr) * pc;
        GL11.glPushMatrix();
        GL11.glTranslated(0.0D, x, y);
        GL11.glRotatef((float)r, -1.0F, 0.0F, 0.0F);
        renderPart(c.model, info.model, c.modelName);
        GL11.glPopMatrix();
      } 
    } 
    GL11.glEnable(3042);
    GL11.glPointSize(prevWidth);
  }
  
  public static void renderHatch(MCH_EntityAircraft ac, MCH_AircraftInfo info, float tickTime) {
    if (!info.haveHatch() || ac.partHatch == null)
      return; 
    float rot = ac.getHatchRotation();
    float prevRot = ac.getPrevHatchRotation();
    for (MCH_AircraftInfo.Hatch h : info.hatchList) {
      GL11.glPushMatrix();
      if (h.isSlide) {
        float r = ac.partHatch.rotation / ac.partHatch.rotationMax;
        float pr = ac.partHatch.prevRotation / ac.partHatch.rotationMax;
        float f = pr + (r - pr) * tickTime;
        GL11.glTranslated(h.pos.x * f, h.pos.y * f, h.pos.z * f);
      } else {
        GL11.glTranslated(h.pos.x, h.pos.y, h.pos.z);
        GL11.glRotatef((prevRot + (rot - prevRot) * tickTime) * h.maxRotFactor, (float)h.rot.x, (float)h.rot.y, (float)h.rot.z);
        GL11.glTranslated(-h.pos.x, -h.pos.y, -h.pos.z);
      } 
      renderPart(h.model, info.model, h.modelName);
      GL11.glPopMatrix();
    } 
  }
  
  public static void renderThrottle(MCH_EntityAircraft ac, MCH_AircraftInfo info, float tickTime) {
    if (!info.havePartThrottle())
      return; 
    float throttle = MCH_Lib.smooth((float)ac.getCurrentThrottle(), (float)ac.getPrevCurrentThrottle(), tickTime);
    for (MCH_AircraftInfo.Throttle h : info.partThrottle) {
      GL11.glPushMatrix();
      GL11.glTranslated(h.pos.x, h.pos.y, h.pos.z);
      GL11.glRotatef(throttle * h.rot2, (float)h.rot.x, (float)h.rot.y, (float)h.rot.z);
      GL11.glTranslated(-h.pos.x, -h.pos.y, -h.pos.z);
      GL11.glTranslated(h.slide.x * throttle, h.slide.y * throttle, h.slide.z * throttle);
      renderPart(h.model, info.model, h.modelName);
      GL11.glPopMatrix();
    } 
  }
  
  public static void renderWeaponBay(MCH_EntityAircraft ac, MCH_AircraftInfo info, float tickTime) {
    for (int i = 0; i < info.partWeaponBay.size(); i++) {
      MCH_AircraftInfo.WeaponBay w = info.partWeaponBay.get(i);
      MCH_EntityAircraft.WeaponBay ws = ac.weaponBays[i];
      GL11.glPushMatrix();
      if (w.isSlide) {
        float r = ws.rot / 90.0F;
        float pr = ws.prevRot / 90.0F;
        float f = pr + (r - pr) * tickTime;
        GL11.glTranslated(w.pos.x * f, w.pos.y * f, w.pos.z * f);
      } else {
        GL11.glTranslated(w.pos.x, w.pos.y, w.pos.z);
        GL11.glRotatef((ws.prevRot + (ws.rot - ws.prevRot) * tickTime) * w.maxRotFactor, (float)w.rot.x, (float)w.rot.y, (float)w.rot.z);
        GL11.glTranslated(-w.pos.x, -w.pos.y, -w.pos.z);
      } 
      renderPart(w.model, info.model, w.modelName);
      GL11.glPopMatrix();
    } 
  }
  
  public static void renderCamera(MCH_EntityAircraft ac, MCH_AircraftInfo info, float tickTime) {
    if (!info.havePartCamera())
      return; 
    float rotYaw = ac.camera.partRotationYaw;
    float prevRotYaw = ac.camera.prevPartRotationYaw;
    float rotPitch = ac.camera.partRotationPitch;
    float prevRotPitch = ac.camera.prevPartRotationPitch;
    float yaw = prevRotYaw + (rotYaw - prevRotYaw) * tickTime - ac.getRotYaw();
    float pitch = prevRotPitch + (rotPitch - prevRotPitch) * tickTime - ac.getRotPitch();
    for (MCH_AircraftInfo.Camera c : info.cameraList) {
      GL11.glPushMatrix();
      GL11.glTranslated(c.pos.x, c.pos.y, c.pos.z);
      if (c.yawSync)
        GL11.glRotatef(yaw, 0.0F, -1.0F, 0.0F); 
      if (c.pitchSync)
        GL11.glRotatef(pitch, 1.0F, 0.0F, 0.0F); 
      GL11.glTranslated(-c.pos.x, -c.pos.y, -c.pos.z);
      renderPart(c.model, info.model, c.modelName);
      GL11.glPopMatrix();
    } 
  }
  
  public static void renderCanopy(MCH_EntityAircraft ac, MCH_AircraftInfo info, float tickTime) {
    if (info.haveCanopy() && ac.partCanopy != null) {
      float rot = ac.getCanopyRotation();
      float prevRot = ac.getPrevCanopyRotation();
      for (MCH_AircraftInfo.Canopy c : info.canopyList) {
        GL11.glPushMatrix();
        if (c.isSlide) {
          float r = ac.partCanopy.rotation / ac.partCanopy.rotationMax;
          float pr = ac.partCanopy.prevRotation / ac.partCanopy.rotationMax;
          float f = pr + (r - pr) * tickTime;
          GL11.glTranslated(c.pos.x * f, c.pos.y * f, c.pos.z * f);
        } else {
          GL11.glTranslated(c.pos.x, c.pos.y, c.pos.z);
          GL11.glRotatef((prevRot + (rot - prevRot) * tickTime) * c.maxRotFactor, (float)c.rot.x, (float)c.rot.y, (float)c.rot.z);
          GL11.glTranslated(-c.pos.x, -c.pos.y, -c.pos.z);
        } 
        renderPart(c.model, info.model, c.modelName);
        GL11.glPopMatrix();
      } 
    } 
  }
  
  public static void renderLandingGear(MCH_EntityAircraft ac, MCH_AircraftInfo info, float tickTime) {
    if (info.haveLandingGear() && ac.partLandingGear != null) {
      float rot = ac.getLandingGearRotation();
      float prevRot = ac.getPrevLandingGearRotation();
      float revR = 90.0F - rot;
      float revPr = 90.0F - prevRot;
      float rot1 = prevRot + (rot - prevRot) * tickTime;
      float rot1Rev = revPr + (revR - revPr) * tickTime;
      float rotHatch = 90.0F * MathHelper.sin(rot1 * 2.0F * 3.1415927F / 180.0F) * 3.0F;
      if (rotHatch > 90.0F)
        rotHatch = 90.0F; 
      for (MCH_AircraftInfo.LandingGear n : info.landingGear) {
        GL11.glPushMatrix();
        GL11.glTranslated(n.pos.x, n.pos.y, n.pos.z);
        if (!n.reverse) {
          if (!n.hatch) {
            GL11.glRotatef(rot1 * n.maxRotFactor, (float)n.rot.x, (float)n.rot.y, (float)n.rot.z);
          } else {
            GL11.glRotatef(rotHatch * n.maxRotFactor, (float)n.rot.x, (float)n.rot.y, (float)n.rot.z);
          } 
        } else {
          GL11.glRotatef(rot1Rev * n.maxRotFactor, (float)n.rot.x, (float)n.rot.y, (float)n.rot.z);
        } 
        if (n.enableRot2)
          if (!n.reverse) {
            GL11.glRotatef(rot1 * n.maxRotFactor2, (float)n.rot2.x, (float)n.rot2.y, (float)n.rot2.z);
          } else {
            GL11.glRotatef(rot1Rev * n.maxRotFactor2, (float)n.rot2.x, (float)n.rot2.y, (float)n.rot2.z);
          }  
        GL11.glTranslated(-n.pos.x, -n.pos.y, -n.pos.z);
        if (n.slide != null) {
          float f = rot / 90.0F;
          if (n.reverse)
            f = 1.0F - f; 
          GL11.glTranslated(f * n.slide.x, f * n.slide.y, f * n.slide.z);
        } 
        renderPart(n.model, info.model, n.modelName);
        GL11.glPopMatrix();
      } 
    } 
  }
  
  public static void renderEntityMarker(Entity entity) {
    EntityPlayerSP entityPlayerSP = (Minecraft.getMinecraft()).player;
    if (entityPlayerSP == null)
      return; 
    if (W_Entity.isEqual((Entity)entityPlayerSP, entity))
      return; 
    MCH_EntityAircraft ac = null;
    if (entityPlayerSP.getRidingEntity() instanceof MCH_EntityAircraft) {
      ac = (MCH_EntityAircraft)entityPlayerSP.getRidingEntity();
    } else if (entityPlayerSP.getRidingEntity() instanceof MCH_EntitySeat) {
      ac = ((MCH_EntitySeat)entityPlayerSP.getRidingEntity()).getParent();
    } else if (entityPlayerSP.getRidingEntity() instanceof MCH_EntityUavStation) {
      ac = ((MCH_EntityUavStation)entityPlayerSP.getRidingEntity()).getControlAircract();
    } 
    if (ac == null)
      return; 
    if (W_Entity.isEqual((Entity)ac, entity))
      return; 
    MCH_WeaponGuidanceSystem gs = ac.getCurrentWeapon((Entity)entityPlayerSP).getCurrentWeapon().getGuidanceSystem();
    if (gs == null || !gs.canLockEntity(entity))
      return; 
    RenderManager rm = Minecraft.getMinecraft().getRenderManager();
    double dist = entity.getDistanceSq(rm.renderViewEntity);
    double x = entity.posX - TileEntityRendererDispatcher.staticPlayerX;
    double y = entity.posY - TileEntityRendererDispatcher.staticPlayerY;
    double z = entity.posZ - TileEntityRendererDispatcher.staticPlayerZ;
    if (dist < 10000.0D) {
      GL11.glPushMatrix();
      GL11.glTranslatef((float)x, (float)y + entity.height + 0.5F, (float)z);
      GL11.glNormal3f(0.0F, 1.0F, 0.0F);
      GL11.glRotatef(-rm.playerViewY, 0.0F, 1.0F, 0.0F);
      GL11.glRotatef(rm.playerViewX, 1.0F, 0.0F, 0.0F);
      GL11.glScalef(-0.02666667F, -0.02666667F, 0.02666667F);
      GL11.glDisable(2896);
      GL11.glTranslatef(0.0F, 9.374999F, 0.0F);
      GL11.glDepthMask(false);
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glDisable(3553);
      int prevWidth = GL11.glGetInteger(2849);
      float size = Math.max(entity.width, entity.height) * 20.0F;
      if (entity instanceof MCH_EntityAircraft)
        size *= 2.0F; 
      Tessellator tessellator = Tessellator.getInstance();
      BufferBuilder builder = tessellator.getBuffer();
      builder.begin(2, MCH_Verts.POS_COLOR_LMAP);
      boolean isLockEntity = gs.isLockingEntity(entity);
      if (isLockEntity) {
        GL11.glLineWidth(MCH_Gui.scaleFactor * 1.5F);
        builder.pos((-size - 1.0F), 0.0D, 0.0D).color(1.0F, 0.0F, 0.0F, 1.0F).lightmap(0, 240).endVertex();
        builder.pos((-size - 1.0F), (size * 2.0F), 0.0D).color(1.0F, 0.0F, 0.0F, 1.0F).lightmap(0, 240).endVertex();
        builder.pos((size + 1.0F), (size * 2.0F), 0.0D).color(1.0F, 0.0F, 0.0F, 1.0F).lightmap(0, 240).endVertex();
        builder.pos((size + 1.0F), 0.0D, 0.0D).color(1.0F, 0.0F, 0.0F, 1.0F).lightmap(0, 240).endVertex();
      } else {
        GL11.glLineWidth(MCH_Gui.scaleFactor);
        builder.pos((-size - 1.0F), 0.0D, 0.0D).color(1.0F, 0.3F, 0.0F, 8.0F).lightmap(0, 240).endVertex();
        builder.pos((-size - 1.0F), (size * 2.0F), 0.0D).color(1.0F, 0.3F, 0.0F, 8.0F).lightmap(0, 240).endVertex();
        builder.pos((size + 1.0F), (size * 2.0F), 0.0D).color(1.0F, 0.3F, 0.0F, 8.0F).lightmap(0, 240).endVertex();
        builder.pos((size + 1.0F), 0.0D, 0.0D).color(1.0F, 0.3F, 0.0F, 8.0F).lightmap(0, 240).endVertex();
      } 
      tessellator.draw();
      GL11.glPopMatrix();
      if (!ac.isUAV() && isLockEntity && (Minecraft.getMinecraft()).gameSettings.thirdPersonView == 0) {
        GL11.glPushMatrix();
        builder.begin(1, MCH_Verts.POS_COLOR_LMAP);
        GL11.glLineWidth(1.0F);
        builder.pos(x, y + (entity.height / 2.0F), z).color(1.0F, 0.0F, 0.0F, 1.0F).lightmap(0, 240).endVertex();
        builder.pos(ac.lastTickPosX - TileEntityRendererDispatcher.staticPlayerX, ac.lastTickPosY - TileEntityRendererDispatcher.staticPlayerY - 1.0D, ac.lastTickPosZ - TileEntityRendererDispatcher.staticPlayerZ)
          
          .color(1.0F, 0.0F, 0.0F, 1.0F)
          .lightmap(0, 240).endVertex();
        tessellator.draw();
        GL11.glPopMatrix();
      } 
      GL11.glLineWidth(prevWidth);
      GL11.glEnable(3553);
      GL11.glDepthMask(true);
      GL11.glEnable(2896);
      GL11.glDisable(3042);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    } 
  }
  
  public static void renderRope(MCH_EntityAircraft ac, MCH_AircraftInfo info, double x, double y, double z, float tickTime) {
    GL11.glPushMatrix();
    Tessellator tessellator = Tessellator.getInstance();
    BufferBuilder builder = tessellator.getBuffer();
    if (ac.isRepelling()) {
      GL11.glDisable(3553);
      GL11.glDisable(2896);
      for (int i = 0; i < info.repellingHooks.size(); i++) {
        builder.begin(3, DefaultVertexFormats.POSITION_COLOR);
        builder.pos(((MCH_AircraftInfo.RepellingHook)info.repellingHooks.get(i)).pos.x, ((MCH_AircraftInfo.RepellingHook)info.repellingHooks.get(i)).pos.y, 
            ((MCH_AircraftInfo.RepellingHook)info.repellingHooks.get(i)).pos.z).color(0, 0, 0, 255).endVertex();
        builder.pos(((MCH_AircraftInfo.RepellingHook)info.repellingHooks.get(i)).pos.x, ((MCH_AircraftInfo.RepellingHook)info.repellingHooks.get(i)).pos.y + ac.ropesLength, 
            ((MCH_AircraftInfo.RepellingHook)info.repellingHooks.get(i)).pos.z).color(0, 0, 0, 255).endVertex();
        tessellator.draw();
      } 
      GL11.glEnable(2896);
      GL11.glEnable(3553);
    } 
    GL11.glPopMatrix();
  }
  
  public abstract void renderAircraft(MCH_EntityAircraft paramMCH_EntityAircraft, double paramDouble1, double paramDouble2, double paramDouble3, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4);
}
