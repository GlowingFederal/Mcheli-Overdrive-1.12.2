package mcheli.aircraft;

import javax.annotation.Nullable;
import mcheli.MCH_ClientCommonTickHandler;
import mcheli.MCH_ClientEventHook;
import mcheli.MCH_Config;
import mcheli.MCH_Lib;
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
    T t = entity;
    MCH_AircraftInfo info = t.getAcInfo();
    if (info != null) {
      GL11.glPushMatrix();
      float yaw = calcRot(t.getRotYaw(), ((MCH_EntityAircraft)t).field_70126_B, tickTime);
      float pitch = t.calcRotPitch(tickTime);
      float roll = calcRot(t.getRotRoll(), ((MCH_EntityAircraft)t).prevRotationRoll, tickTime);
      if (MCH_Config.EnableModEntityRender.prmBool)
        renderRiddenEntity((MCH_EntityAircraft)t, tickTime, yaw, pitch + info.entityPitch, roll + info.entityRoll, info.entityWidth, info.entityHeight); 
      if (!shouldSkipRender((Entity)entity)) {
        setCommonRenderParam(info.smoothShading, t.func_70070_b());
        if (t.isDestroyed()) {
          GL11.glColor4f(0.15F, 0.15F, 0.15F, 1.0F);
        } else {
          GL11.glColor4f(0.75F, 0.75F, 0.75F, (float)MCH_Config.__TextureAlpha.prmDouble);
        } 
        renderAircraft((MCH_EntityAircraft)t, posX, posY, posZ, yaw, pitch, roll, tickTime);
        renderCommonPart((MCH_EntityAircraft)t, info, posX, posY, posZ, tickTime);
        renderLight(posX, posY, posZ, tickTime, (MCH_EntityAircraft)t, info);
        restoreCommonRenderParam();
      } 
      GL11.glPopMatrix();
      MCH_GuiTargetMarker.addMarkEntityPos(1, (ITargetMarkerObject)entity, posX, posY + info.markerHeight, posZ);
      MCH_ClientLightWeaponTickHandler.markEntity((Entity)entity, posX, posY, posZ);
      renderEntityMarker((Entity)t);
      if (MCH_Config.TestMode.prmBool)
        WeaponPointRenderer.renderWeaponPoints((MCH_EntityAircraft)t, info, posX, posY, posZ); 
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
      if (entity.func_184187_bx() instanceof MCH_EntitySeat)
        return !renderingEntity; 
    } 
    return false;
  }
  
  public void func_76979_b(Entity entity, double x, double y, double z, float yaw, float partialTicks) {
    if (entity.func_90999_ad())
      renderEntityOnFire(entity, x, y, z, partialTicks); 
  }
  
  private void renderEntityOnFire(Entity entity, double x, double y, double z, float tick) {
    GL11.glDisable(2896);
    TextureMap texturemap = Minecraft.func_71410_x().func_147117_R();
    TextureAtlasSprite textureatlassprite = texturemap.func_110572_b("minecraft:blocks/fire_layer_0");
    TextureAtlasSprite textureatlassprite1 = texturemap.func_110572_b("minecraft:blocks/fire_layer_1");
    GL11.glPushMatrix();
    GL11.glTranslatef((float)x, (float)y, (float)z);
    float f1 = entity.field_70130_N * 1.4F;
    GL11.glScalef(f1 * 2.0F, f1 * 2.0F, f1 * 2.0F);
    Tessellator tessellator = Tessellator.func_178181_a();
    BufferBuilder bufferbuilder = tessellator.func_178180_c();
    float f2 = 1.5F;
    float f3 = 0.0F;
    float f4 = entity.field_70131_O / f1;
    float f5 = (float)(entity.field_70163_u + (entity.func_174813_aQ()).field_72338_b);
    GL11.glRotatef(-this.field_76990_c.field_78735_i, 0.0F, 1.0F, 0.0F);
    GL11.glTranslatef(0.0F, 0.0F, -0.3F + (int)f4 * 0.02F);
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    float f6 = 0.0F;
    int i = 0;
    bufferbuilder.func_181668_a(7, DefaultVertexFormats.field_181707_g);
    while (f4 > 0.0F) {
      TextureAtlasSprite textureatlassprite2 = (i % 2 == 0) ? textureatlassprite : textureatlassprite1;
      func_110776_a(TextureMap.field_110575_b);
      float f7 = textureatlassprite2.func_94209_e();
      float f8 = textureatlassprite2.func_94206_g();
      float f9 = textureatlassprite2.func_94212_f();
      float f10 = textureatlassprite2.func_94210_h();
      if (i / 2 % 2 == 0) {
        float f11 = f9;
        f9 = f7;
        f7 = f11;
      } 
      bufferbuilder.func_181662_b((f2 - f3), (0.0F - f5), f6).func_187315_a(f9, f10).func_181675_d();
      bufferbuilder.func_181662_b((-f2 - f3), (0.0F - f5), f6).func_187315_a(f7, f10).func_181675_d();
      bufferbuilder.func_181662_b((-f2 - f3), (1.4F - f5), f6).func_187315_a(f7, f8).func_181675_d();
      bufferbuilder.func_181662_b((f2 - f3), (1.4F - f5), f6).func_187315_a(f9, f8).func_181675_d();
      f4 -= 0.45F;
      f5 -= 0.45F;
      f2 *= 0.9F;
      f6 += 0.03F;
      i++;
    } 
    tessellator.func_78381_a();
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
      ac.lastSearchLightYaw = entity.field_70177_z;
      ac.lastSearchLightPitch = entity.field_70125_A;
    } else {
      entity = ac.getEntityBySeatId(0);
      if (entity != null) {
        ac.lastSearchLightYaw = entity.field_70177_z;
        ac.lastSearchLightPitch = entity.field_70125_A;
      } 
    } 
    float yaw = ac.lastSearchLightYaw;
    float pitch = ac.lastSearchLightPitch;
    RenderHelper.func_74518_a();
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
      GL11.glTranslated(sl.pos.field_72450_a, sl.pos.field_72448_b, sl.pos.field_72449_c);
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
      Tessellator tessellator = Tessellator.func_178181_a();
      BufferBuilder builder = tessellator.func_178180_c();
      builder.func_181668_a(6, DefaultVertexFormats.field_181706_f);
      MCH_ColorInt cs = new MCH_ColorInt(sl.colorStart);
      MCH_ColorInt ce = new MCH_ColorInt(sl.colorEnd);
      builder.func_181662_b(0.0D, 0.0D, 0.0D).func_181669_b(cs.r, cs.g, cs.b, cs.a).func_181675_d();
      for (int i = 0; i < 25; i++) {
        float angle = (float)(15.0D * i / 180.0D * Math.PI);
        builder.func_181662_b((MathHelper.func_76126_a(angle) * width), height, (MathHelper.func_76134_b(angle) * width))
          .func_181669_b(ce.r, ce.g, ce.b, ce.a).func_181675_d();
      } 
      tessellator.func_78381_a();
      GL11.glPopMatrix();
    } 
    GL11.glDepthMask(true);
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    GL11.glEnable(3553);
    GL11.glEnable(3008);
    GL11.glBlendFunc(770, 771);
    RenderHelper.func_74519_b();
  }
  
  protected void bindTexture(String path, MCH_EntityAircraft ac) {
    if (ac == MCH_ClientCommonTickHandler.ridingAircraft) {
      int bk = MCH_ClientCommonTickHandler.cameraMode;
      MCH_ClientCommonTickHandler.cameraMode = 0;
      func_110776_a(MCH_Utils.suffix(path));
      MCH_ClientCommonTickHandler.cameraMode = bk;
    } else {
      func_110776_a(MCH_Utils.suffix(path));
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
        if (entity.field_70173_aa == 0) {
          entity.field_70142_S = entity.field_70165_t;
          entity.field_70137_T = entity.field_70163_u;
          entity.field_70136_U = entity.field_70161_v;
        } 
        double x = entity.field_70142_S + (entity.field_70165_t - entity.field_70142_S) * tickTime;
        double y = entity.field_70137_T + (entity.field_70163_u - entity.field_70137_T) * tickTime;
        double z = entity.field_70136_U + (entity.field_70161_v - entity.field_70136_U) * tickTime;
        float f1 = entity.field_70126_B + (entity.field_70177_z - entity.field_70126_B) * tickTime;
        int i = entity.func_70070_b();
        if (entity.func_70027_ad())
          i = 15728880; 
        int j = i % 65536;
        int k = i / 65536;
        OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, j / 1.0F, k / 1.0F);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        double dx = x - TileEntityRendererDispatcher.field_147554_b;
        double dy = y - TileEntityRendererDispatcher.field_147555_c;
        double dz = z - TileEntityRendererDispatcher.field_147552_d;
        GL11.glTranslated(dx, dy, dz);
        GL11.glRotatef(yaw, 0.0F, -1.0F, 0.0F);
        GL11.glRotatef(pitch, 1.0F, 0.0F, 0.0F);
        GL11.glRotatef(roll, 0.0F, 0.0F, 1.0F);
        GL11.glScaled(width, height, width);
        GL11.glRotatef(-yaw, 0.0F, -1.0F, 0.0F);
        GL11.glTranslated(-dx, -dy, -dz);
        boolean bk = renderingEntity;
        renderingEntity = true;
        Entity ridingEntity = entity.func_184187_bx();
        if (!W_Lib.isEntityLivingBase(entity) && !(entity instanceof MCH_IEntityCanRideAircraft))
          entity.func_184210_p(); 
        EntityLivingBase entityLiving = (entity instanceof EntityLivingBase) ? (EntityLivingBase)entity : null;
        float bkPitch = 0.0F;
        float bkPrevPitch = 0.0F;
        if (isPilot && entityLiving != null) {
          entityLiving.field_70761_aq = ac.getRotYaw();
          entityLiving.field_70760_ar = ac.getRotYaw();
          if (ac.getCameraId() > 0) {
            entityLiving.field_70759_as = ac.getRotYaw();
            entityLiving.field_70758_at = ac.getRotYaw();
            bkPitch = entityLiving.field_70125_A;
            bkPrevPitch = entityLiving.field_70127_C;
            entityLiving.field_70125_A = ac.getRotPitch();
            entityLiving.field_70127_C = ac.getRotPitch();
          } 
        } 
        if (isClientPlayer) {
          Entity viewEntity = this.field_76990_c.field_78734_h;
          this.field_76990_c.field_78734_h = entity;
          W_EntityRenderer.renderEntityWithPosYaw(this.field_76990_c, entity, dx, dy, dz, f1, tickTime, false);
          this.field_76990_c.field_78734_h = viewEntity;
        } else {
          W_EntityRenderer.renderEntityWithPosYaw(this.field_76990_c, entity, dx, dy, dz, f1, tickTime, false);
        } 
        if (isPilot && entityLiving != null)
          if (ac.getCameraId() > 0) {
            entityLiving.field_70125_A = bkPitch;
            entityLiving.field_70127_C = bkPrevPitch;
          }  
        entity.func_184220_m(ridingEntity);
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
    rot = MathHelper.func_76142_g(rot);
    prevRot = MathHelper.func_76142_g(prevRot);
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
      GL11.glScalef(e.field_70130_N, e.field_70131_O, e.field_70130_N);
      bindTexture("textures/hit_box.png");
      debugModel.renderAll();
      GL11.glPopMatrix();
      GL11.glPushMatrix();
      GL11.glTranslated(x, y, z);
      for (MCH_BoundingBox bb : e.extraBoundingBox) {
        GL11.glPushMatrix();
        GL11.glTranslated(bb.rotatedOffset.field_72450_a, bb.rotatedOffset.field_72448_b, bb.rotatedOffset.field_72449_c);
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
    GL11.glRotatef(-this.field_76990_c.field_78735_i, 0.0F, 1.0F, 0.0F);
    GL11.glRotatef(this.field_76990_c.field_78732_j, 1.0F, 0.0F, 0.0F);
    GL11.glScalef(-f1, -f1, f1);
    GL11.glDisable(2896);
    GL11.glEnable(3042);
    OpenGlHelper.func_148821_a(770, 771, 1, 0);
    GL11.glDisable(3553);
    FontRenderer fontrenderer = func_76983_a();
    Tessellator tessellator = Tessellator.func_178181_a();
    BufferBuilder builder = tessellator.func_178180_c();
    builder.func_181668_a(7, DefaultVertexFormats.field_181706_f);
    int i = fontrenderer.func_78256_a(s) / 2;
    builder.func_181662_b((-i - 1), -1.0D, 0.1D).func_181666_a(0.0F, 0.0F, 0.0F, 0.4F).func_181675_d();
    builder.func_181662_b((-i - 1), 8.0D, 0.1D).func_181666_a(0.0F, 0.0F, 0.0F, 0.4F).func_181675_d();
    builder.func_181662_b((i + 1), 8.0D, 0.1D).func_181666_a(0.0F, 0.0F, 0.0F, 0.4F).func_181675_d();
    builder.func_181662_b((i + 1), -1.0D, 0.1D).func_181666_a(0.0F, 0.0F, 0.0F, 0.4F).func_181675_d();
    tessellator.func_78381_a();
    GL11.glEnable(3553);
    GL11.glDepthMask(false);
    int color = (bb.damegeFactor > 1.0F) ? 16711680 : ((bb.damegeFactor < 1.0F) ? 65535 : 16777215);
    fontrenderer.func_78276_b(s, -fontrenderer.func_78256_a(s) / 2, 0, 0xC0000000 | color);
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
      GL11.glTranslated(seat.pos.field_72450_a, seat.pos.field_72448_b, seat.pos.field_72449_c);
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
      GL11.glTranslated(t.pos.field_72450_a, t.pos.field_72448_b, t.pos.field_72449_c);
      GL11.glRotated((rot * t.maxRot), t.rot.field_72450_a, t.rot.field_72448_b, t.rot.field_72449_c);
      GL11.glTranslated(-t.pos.field_72450_a, -t.pos.field_72448_b, -t.pos.field_72449_c);
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
      GL11.glTranslated(t.pos.field_72450_a, t.pos.field_72448_b, t.pos.field_72449_c);
      GL11.glRotated((rot * t.rotDir), t.rot.field_72450_a, t.rot.field_72448_b, t.rot.field_72449_c);
      GL11.glTranslated(-t.pos.field_72450_a, -t.pos.field_72448_b, -t.pos.field_72449_c);
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
      GL11.glTranslated(t.pos2.field_72450_a, t.pos2.field_72448_b, t.pos2.field_72449_c);
      GL11.glRotated((yaw * t.rotDir), t.rot.field_72450_a, t.rot.field_72448_b, t.rot.field_72449_c);
      GL11.glTranslated(-t.pos2.field_72450_a, -t.pos2.field_72448_b, -t.pos2.field_72449_c);
      GL11.glTranslated(t.pos.field_72450_a, t.pos.field_72448_b, t.pos.field_72449_c);
      GL11.glRotatef(ac.prevRotWheel + (ac.rotWheel - ac.prevRotWheel) * tickTime, 1.0F, 0.0F, 0.0F);
      GL11.glTranslated(-t.pos.field_72450_a, -t.pos.field_72448_b, -t.pos.field_72449_c);
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
      GL11.glTranslated(h.pos.field_72450_a, h.pos.field_72448_b, h.pos.field_72449_c);
      GL11.glRotatef(rot, (float)h.rot.field_72450_a, (float)h.rot.field_72448_b, (float)h.rot.field_72449_c);
      GL11.glTranslated(-h.pos.field_72450_a, -h.pos.field_72448_b, -h.pos.field_72449_c);
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
        GL11.glTranslated(info.turretPosition.field_72450_a, info.turretPosition.field_72448_b, info.turretPosition.field_72449_c);
        float ty = MCH_Lib.smooth(ac.getLastRiderYaw() - ac.getRotYaw(), ac.prevLastRiderYaw - ac.field_70126_B, tickTime);
        GL11.glRotatef(ty, 0.0F, -1.0F, 0.0F);
        GL11.glTranslated(-info.turretPosition.field_72450_a, -info.turretPosition.field_72448_b, -info.turretPosition.field_72449_c);
      } 
      GL11.glTranslated(w.pos.field_72450_a, w.pos.field_72448_b, w.pos.field_72449_c);
      if (w.yaw) {
        if (ws != null) {
          rotYaw = ws.rotationYaw - ws.defaultRotationYaw;
          prevYaw = ws.prevRotationYaw - ws.defaultRotationYaw;
        } else if (e != null) {
          rotYaw = e.field_70177_z - ac.getRotYaw();
          prevYaw = e.field_70126_B - ac.field_70126_B;
        } else {
          rotYaw = ac.getLastRiderYaw() - ac.field_70177_z;
          prevYaw = ac.prevLastRiderYaw - ac.field_70126_B;
        } 
        if (rotYaw - prevYaw > 180.0F) {
          prevYaw += 360.0F;
        } else if (rotYaw - prevYaw < -180.0F) {
          prevYaw -= 360.0F;
        } 
        GL11.glRotatef(prevYaw + (rotYaw - prevYaw) * tickTime, 0.0F, -1.0F, 0.0F);
      } 
      if (w.turret) {
        float ty = MCH_Lib.smooth(ac.getLastRiderYaw() - ac.getRotYaw(), ac.prevLastRiderYaw - ac.field_70126_B, tickTime);
        ty -= ws.rotationTurretYaw;
        GL11.glRotatef(-ty, 0.0F, -1.0F, 0.0F);
      } 
      boolean rev_sign = false;
      if (ws != null && (int)ws.defaultRotationYaw != 0) {
        float t = MathHelper.func_76142_g(ws.defaultRotationYaw);
        rev_sign = ((t >= 45.0F && t <= 135.0F) || (t <= -45.0F && t >= -135.0F));
        GL11.glRotatef(-ws.defaultRotationYaw, 0.0F, -1.0F, 0.0F);
      } 
      if (w.pitch) {
        if (ws != null) {
          rotPitch = ws.rotationPitch;
          prevPitch = ws.prevRotationPitch;
        } else if (e != null) {
          rotPitch = e.field_70125_A;
          prevPitch = e.field_70127_C;
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
          GL11.glRotatef(rotBrl, (float)w.rot.field_72450_a, (float)w.rot.field_72448_b, (float)w.rot.field_72449_c);
        } 
      } 
      GL11.glTranslated(-w.pos.field_72450_a, -w.pos.field_72448_b, -w.pos.field_72449_c);
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
    GL11.glTranslated(w.pos.field_72450_a, w.pos.field_72448_b, w.pos.field_72449_c);
    if (w.yaw) {
      if (ws != null) {
        rotYaw = ws.rotationYaw - ws.defaultRotationYaw;
        prevYaw = ws.prevRotationYaw - ws.defaultRotationYaw;
      } else if (e != null) {
        rotYaw = e.field_70177_z - ac.getRotYaw();
        prevYaw = e.field_70126_B - ac.field_70126_B;
      } else {
        rotYaw = ac.getLastRiderYaw() - ac.field_70177_z;
        prevYaw = ac.prevLastRiderYaw - ac.field_70126_B;
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
      float t = MathHelper.func_76142_g(ws.defaultRotationYaw);
      rev_sign = ((t >= 45.0F && t <= 135.0F) || (t <= -45.0F && t >= -135.0F));
      GL11.glRotatef(-ws.defaultRotationYaw, 0.0F, -1.0F, 0.0F);
    } 
    if (w.pitch) {
      if (ws != null) {
        rotPitch = ws.rotationPitch;
        prevPitch = ws.prevRotationPitch;
      } else if (e != null) {
        rotPitch = e.field_70125_A;
        prevPitch = e.field_70127_C;
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
    GL11.glTranslated(-w.pos.field_72450_a, -w.pos.field_72448_b, -w.pos.field_72449_c);
    renderPart(w.model, info.model, w.modelName);
  }
  
  public static void renderTrackRoller(MCH_EntityAircraft ac, MCH_AircraftInfo info, float tickTime) {
    if (info.partTrackRoller.size() <= 0)
      return; 
    float[] rot = ac.rotTrackRoller;
    float[] prevRot = ac.prevRotTrackRoller;
    for (MCH_AircraftInfo.TrackRoller t : info.partTrackRoller) {
      GL11.glPushMatrix();
      GL11.glTranslated(t.pos.field_72450_a, t.pos.field_72448_b, t.pos.field_72449_c);
      GL11.glRotatef(prevRot[t.side] + (rot[t.side] - prevRot[t.side]) * tickTime, 1.0F, 0.0F, 0.0F);
      GL11.glTranslated(-t.pos.field_72450_a, -t.pos.field_72448_b, -t.pos.field_72449_c);
      renderPart(t.model, info.model, t.modelName);
      GL11.glPopMatrix();
    } 
  }
  
  public static void renderCrawlerTrack(MCH_EntityAircraft ac, MCH_AircraftInfo info, float tickTime) {
    if (info.partCrawlerTrack.size() <= 0)
      return; 
    int prevWidth = GL11.glGetInteger(2833);
    Tessellator tessellator = Tessellator.func_178181_a();
    BufferBuilder builder = tessellator.func_178180_c();
    for (MCH_AircraftInfo.CrawlerTrack c : info.partCrawlerTrack) {
      GL11.glPointSize(c.len * 20.0F);
      if (MCH_Config.TestMode.prmBool) {
        GL11.glDisable(3553);
        GL11.glDisable(3042);
        builder.func_181668_a(0, DefaultVertexFormats.field_181706_f);
        for (int j = 0; j < c.cx.length; j++)
          builder.func_181662_b(c.z, c.cx[j], c.cy[j])
            .func_181669_b((int)(255.0F / c.cx.length * j), 80, 255 - (int)(255.0F / c.cx.length * j), 255)
            .func_181675_d(); 
        tessellator.func_78381_a();
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
        GL11.glTranslated(h.pos.field_72450_a * f, h.pos.field_72448_b * f, h.pos.field_72449_c * f);
      } else {
        GL11.glTranslated(h.pos.field_72450_a, h.pos.field_72448_b, h.pos.field_72449_c);
        GL11.glRotatef((prevRot + (rot - prevRot) * tickTime) * h.maxRotFactor, (float)h.rot.field_72450_a, (float)h.rot.field_72448_b, (float)h.rot.field_72449_c);
        GL11.glTranslated(-h.pos.field_72450_a, -h.pos.field_72448_b, -h.pos.field_72449_c);
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
      GL11.glTranslated(h.pos.field_72450_a, h.pos.field_72448_b, h.pos.field_72449_c);
      GL11.glRotatef(throttle * h.rot2, (float)h.rot.field_72450_a, (float)h.rot.field_72448_b, (float)h.rot.field_72449_c);
      GL11.glTranslated(-h.pos.field_72450_a, -h.pos.field_72448_b, -h.pos.field_72449_c);
      GL11.glTranslated(h.slide.field_72450_a * throttle, h.slide.field_72448_b * throttle, h.slide.field_72449_c * throttle);
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
        GL11.glTranslated(w.pos.field_72450_a * f, w.pos.field_72448_b * f, w.pos.field_72449_c * f);
      } else {
        GL11.glTranslated(w.pos.field_72450_a, w.pos.field_72448_b, w.pos.field_72449_c);
        GL11.glRotatef((ws.prevRot + (ws.rot - ws.prevRot) * tickTime) * w.maxRotFactor, (float)w.rot.field_72450_a, (float)w.rot.field_72448_b, (float)w.rot.field_72449_c);
        GL11.glTranslated(-w.pos.field_72450_a, -w.pos.field_72448_b, -w.pos.field_72449_c);
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
      GL11.glTranslated(c.pos.field_72450_a, c.pos.field_72448_b, c.pos.field_72449_c);
      if (c.yawSync)
        GL11.glRotatef(yaw, 0.0F, -1.0F, 0.0F); 
      if (c.pitchSync)
        GL11.glRotatef(pitch, 1.0F, 0.0F, 0.0F); 
      GL11.glTranslated(-c.pos.field_72450_a, -c.pos.field_72448_b, -c.pos.field_72449_c);
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
          GL11.glTranslated(c.pos.field_72450_a * f, c.pos.field_72448_b * f, c.pos.field_72449_c * f);
        } else {
          GL11.glTranslated(c.pos.field_72450_a, c.pos.field_72448_b, c.pos.field_72449_c);
          GL11.glRotatef((prevRot + (rot - prevRot) * tickTime) * c.maxRotFactor, (float)c.rot.field_72450_a, (float)c.rot.field_72448_b, (float)c.rot.field_72449_c);
          GL11.glTranslated(-c.pos.field_72450_a, -c.pos.field_72448_b, -c.pos.field_72449_c);
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
      float rotHatch = 90.0F * MathHelper.func_76126_a(rot1 * 2.0F * 3.1415927F / 180.0F) * 3.0F;
      if (rotHatch > 90.0F)
        rotHatch = 90.0F; 
      for (MCH_AircraftInfo.LandingGear n : info.landingGear) {
        GL11.glPushMatrix();
        GL11.glTranslated(n.pos.field_72450_a, n.pos.field_72448_b, n.pos.field_72449_c);
        if (!n.reverse) {
          if (!n.hatch) {
            GL11.glRotatef(rot1 * n.maxRotFactor, (float)n.rot.field_72450_a, (float)n.rot.field_72448_b, (float)n.rot.field_72449_c);
          } else {
            GL11.glRotatef(rotHatch * n.maxRotFactor, (float)n.rot.field_72450_a, (float)n.rot.field_72448_b, (float)n.rot.field_72449_c);
          } 
        } else {
          GL11.glRotatef(rot1Rev * n.maxRotFactor, (float)n.rot.field_72450_a, (float)n.rot.field_72448_b, (float)n.rot.field_72449_c);
        } 
        if (n.enableRot2)
          if (!n.reverse) {
            GL11.glRotatef(rot1 * n.maxRotFactor2, (float)n.rot2.field_72450_a, (float)n.rot2.field_72448_b, (float)n.rot2.field_72449_c);
          } else {
            GL11.glRotatef(rot1Rev * n.maxRotFactor2, (float)n.rot2.field_72450_a, (float)n.rot2.field_72448_b, (float)n.rot2.field_72449_c);
          }  
        GL11.glTranslated(-n.pos.field_72450_a, -n.pos.field_72448_b, -n.pos.field_72449_c);
        if (n.slide != null) {
          float f = rot / 90.0F;
          if (n.reverse)
            f = 1.0F - f; 
          GL11.glTranslated(f * n.slide.field_72450_a, f * n.slide.field_72448_b, f * n.slide.field_72449_c);
        } 
        renderPart(n.model, info.model, n.modelName);
        GL11.glPopMatrix();
      } 
    } 
  }
  
  public static void renderEntityMarker(Entity entity) {
    EntityPlayerSP entityPlayerSP = (Minecraft.func_71410_x()).field_71439_g;
    if (entityPlayerSP == null)
      return; 
    if (W_Entity.isEqual((Entity)entityPlayerSP, entity))
      return; 
    MCH_EntityAircraft ac = null;
    if (entityPlayerSP.func_184187_bx() instanceof MCH_EntityAircraft) {
      ac = (MCH_EntityAircraft)entityPlayerSP.func_184187_bx();
    } else if (entityPlayerSP.func_184187_bx() instanceof MCH_EntitySeat) {
      ac = ((MCH_EntitySeat)entityPlayerSP.func_184187_bx()).getParent();
    } else if (entityPlayerSP.func_184187_bx() instanceof MCH_EntityUavStation) {
      ac = ((MCH_EntityUavStation)entityPlayerSP.func_184187_bx()).getControlAircract();
    } 
    if (ac == null)
      return; 
    if (W_Entity.isEqual((Entity)ac, entity))
      return; 
    MCH_WeaponGuidanceSystem gs = ac.getCurrentWeapon((Entity)entityPlayerSP).getCurrentWeapon().getGuidanceSystem();
    if (gs == null || !gs.canLockEntity(entity))
      return; 
    RenderManager rm = Minecraft.func_71410_x().func_175598_ae();
    double dist = entity.func_70068_e(rm.field_78734_h);
    double x = entity.field_70165_t - TileEntityRendererDispatcher.field_147554_b;
    double y = entity.field_70163_u - TileEntityRendererDispatcher.field_147555_c;
    double z = entity.field_70161_v - TileEntityRendererDispatcher.field_147552_d;
    if (dist < 10000.0D) {
      GL11.glPushMatrix();
      GL11.glTranslatef((float)x, (float)y + entity.field_70131_O + 0.5F, (float)z);
      GL11.glNormal3f(0.0F, 1.0F, 0.0F);
      GL11.glRotatef(-rm.field_78735_i, 0.0F, 1.0F, 0.0F);
      GL11.glRotatef(rm.field_78732_j, 1.0F, 0.0F, 0.0F);
      GL11.glScalef(-0.02666667F, -0.02666667F, 0.02666667F);
      GL11.glDisable(2896);
      GL11.glTranslatef(0.0F, 9.374999F, 0.0F);
      GL11.glDepthMask(false);
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glDisable(3553);
      int prevWidth = GL11.glGetInteger(2849);
      float size = Math.max(entity.field_70130_N, entity.field_70131_O) * 20.0F;
      if (entity instanceof MCH_EntityAircraft)
        size *= 2.0F; 
      Tessellator tessellator = Tessellator.func_178181_a();
      BufferBuilder builder = tessellator.func_178180_c();
      builder.func_181668_a(2, MCH_Verts.POS_COLOR_LMAP);
      boolean isLockEntity = gs.isLockingEntity(entity);
      if (isLockEntity) {
        GL11.glLineWidth(MCH_Gui.scaleFactor * 1.5F);
        builder.func_181662_b((-size - 1.0F), 0.0D, 0.0D).func_181666_a(1.0F, 0.0F, 0.0F, 1.0F).func_187314_a(0, 240).func_181675_d();
        builder.func_181662_b((-size - 1.0F), (size * 2.0F), 0.0D).func_181666_a(1.0F, 0.0F, 0.0F, 1.0F).func_187314_a(0, 240).func_181675_d();
        builder.func_181662_b((size + 1.0F), (size * 2.0F), 0.0D).func_181666_a(1.0F, 0.0F, 0.0F, 1.0F).func_187314_a(0, 240).func_181675_d();
        builder.func_181662_b((size + 1.0F), 0.0D, 0.0D).func_181666_a(1.0F, 0.0F, 0.0F, 1.0F).func_187314_a(0, 240).func_181675_d();
      } else {
        GL11.glLineWidth(MCH_Gui.scaleFactor);
        builder.func_181662_b((-size - 1.0F), 0.0D, 0.0D).func_181666_a(1.0F, 0.3F, 0.0F, 8.0F).func_187314_a(0, 240).func_181675_d();
        builder.func_181662_b((-size - 1.0F), (size * 2.0F), 0.0D).func_181666_a(1.0F, 0.3F, 0.0F, 8.0F).func_187314_a(0, 240).func_181675_d();
        builder.func_181662_b((size + 1.0F), (size * 2.0F), 0.0D).func_181666_a(1.0F, 0.3F, 0.0F, 8.0F).func_187314_a(0, 240).func_181675_d();
        builder.func_181662_b((size + 1.0F), 0.0D, 0.0D).func_181666_a(1.0F, 0.3F, 0.0F, 8.0F).func_187314_a(0, 240).func_181675_d();
      } 
      tessellator.func_78381_a();
      GL11.glPopMatrix();
      if (!ac.isUAV() && isLockEntity && (Minecraft.func_71410_x()).field_71474_y.field_74320_O == 0) {
        GL11.glPushMatrix();
        builder.func_181668_a(1, MCH_Verts.POS_COLOR_LMAP);
        GL11.glLineWidth(1.0F);
        builder.func_181662_b(x, y + (entity.field_70131_O / 2.0F), z).func_181666_a(1.0F, 0.0F, 0.0F, 1.0F).func_187314_a(0, 240).func_181675_d();
        builder.func_181662_b(ac.field_70142_S - TileEntityRendererDispatcher.field_147554_b, ac.field_70137_T - TileEntityRendererDispatcher.field_147555_c - 1.0D, ac.field_70136_U - TileEntityRendererDispatcher.field_147552_d)
          
          .func_181666_a(1.0F, 0.0F, 0.0F, 1.0F)
          .func_187314_a(0, 240).func_181675_d();
        tessellator.func_78381_a();
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
    Tessellator tessellator = Tessellator.func_178181_a();
    BufferBuilder builder = tessellator.func_178180_c();
    if (ac.isRepelling()) {
      GL11.glDisable(3553);
      GL11.glDisable(2896);
      for (int i = 0; i < info.repellingHooks.size(); i++) {
        builder.func_181668_a(3, DefaultVertexFormats.field_181706_f);
        builder.func_181662_b(((MCH_AircraftInfo.RepellingHook)info.repellingHooks.get(i)).pos.field_72450_a, ((MCH_AircraftInfo.RepellingHook)info.repellingHooks.get(i)).pos.field_72448_b, 
            ((MCH_AircraftInfo.RepellingHook)info.repellingHooks.get(i)).pos.field_72449_c).func_181669_b(0, 0, 0, 255).func_181675_d();
        builder.func_181662_b(((MCH_AircraftInfo.RepellingHook)info.repellingHooks.get(i)).pos.field_72450_a, ((MCH_AircraftInfo.RepellingHook)info.repellingHooks.get(i)).pos.field_72448_b + ac.ropesLength, 
            ((MCH_AircraftInfo.RepellingHook)info.repellingHooks.get(i)).pos.field_72449_c).func_181669_b(0, 0, 0, 255).func_181675_d();
        tessellator.func_78381_a();
      } 
      GL11.glEnable(2896);
      GL11.glEnable(3553);
    } 
    GL11.glPopMatrix();
  }
  
  public abstract void renderAircraft(MCH_EntityAircraft paramMCH_EntityAircraft, double paramDouble1, double paramDouble2, double paramDouble3, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4);
}
