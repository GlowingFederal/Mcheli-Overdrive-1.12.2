package mcheli.__helper.client;

import javax.annotation.Nullable;
import mcheli.MCH_ViewEntityDummy;
import mcheli.aircraft.MCH_EntityAircraft;
import mcheli.tool.rangefinder.MCH_ItemRangeFinder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@EventBusSubscriber(modid = "mcheli", value = {Side.CLIENT})
public class MCH_CameraManager {
  private static final float DEF_THIRD_CAMERA_DIST = 4.0F;
  
  private static final Minecraft mc = Minecraft.getMinecraft();
  
  private static float cameraRoll = 0.0F;
  
  private static float cameraDistance = 4.0F;
  
  private static float cameraZoom = 1.0F;
  
  private static MCH_EntityAircraft ridingAircraft = null;
  
  @SubscribeEvent
  static void onCameraSetupEvent(EntityViewRenderEvent.CameraSetup event) {
    Entity entity = event.getEntity();
    float f = event.getEntity().getEyeHeight();
    if (mc.gameSettings.thirdPersonView > 0) {
      if (mc.gameSettings.thirdPersonView == 2)
        GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F); 
      GlStateManager.translate(0.0F, 0.0F, -(cameraDistance - 4.0F));
      if (mc.gameSettings.thirdPersonView == 2)
        GlStateManager.rotate(-180.0F, 0.0F, 1.0F, 0.0F); 
    } 
    MCH_EntityAircraft ridingEntity = ridingAircraft;
    if (ridingEntity != null && ridingEntity.canSwitchFreeLook() && ridingEntity.isPilot((Entity)mc.player)) {
      boolean flag = !(entity instanceof MCH_ViewEntityDummy);
      GlStateManager.translate(0.0F, -f, 0.0F);
      if (flag)
        GlStateManager.rotate(cameraRoll, 0.0F, 0.0F, 1.0F); 
      if (ridingEntity.isOverridePlayerPitch())
        if (flag) {
          GlStateManager.rotate(ridingEntity.rotationPitch, 1.0F, 0.0F, 0.0F);
          event.setPitch(event.getPitch() - ridingEntity.rotationPitch);
        }  
      if (ridingEntity.isOverridePlayerYaw())
        if (!ridingEntity.isHovering() && flag) {
          GlStateManager.rotate(ridingEntity.rotationYaw, 0.0F, 1.0F, 0.0F);
          event.setYaw(event.getYaw() - ridingEntity.rotationYaw);
        }  
      GlStateManager.translate(0.0F, f, 0.0F);
    } 
  }
  
  @SubscribeEvent
  static void onFOVModifierEvent(EntityViewRenderEvent.FOVModifier event) {
    MCH_ViewEntityDummy viewer = MCH_ViewEntityDummy.getInstance((World)mc.world);
    if (viewer == event.getEntity() || MCH_ItemRangeFinder.isUsingScope((EntityPlayer)mc.player))
      event.setFOV(event.getFOV() * 1.0F / cameraZoom); 
  }
  
  public static void setCameraRoll(float roll) {
    roll = MathHelper.wrapDegrees(roll);
    cameraRoll = roll;
  }
  
  public static void setThirdPeasonCameraDistance(float distance) {
    distance = MathHelper.clamp(distance, 4.0F, 60.0F);
    cameraDistance = distance;
  }
  
  public static void setCameraZoom(float zoom) {
    cameraZoom = zoom;
  }
  
  public static float getThirdPeasonCameraDistance() {
    return cameraDistance;
  }
  
  public static void setRidingAircraft(@Nullable MCH_EntityAircraft aircraft) {
    ridingAircraft = aircraft;
  }
}
