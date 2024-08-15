/*     */ package mcheli.__helper.client;
/*     */ 
/*     */ import javax.annotation.Nullable;
/*     */ import mcheli.MCH_ViewEntityDummy;
/*     */ import mcheli.aircraft.MCH_EntityAircraft;
/*     */ import mcheli.tool.rangefinder.MCH_ItemRangeFinder;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.client.event.EntityViewRenderEvent;
/*     */ import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @EventBusSubscriber(modid = "mcheli", value = {Side.CLIENT})
/*     */ public class MCH_CameraManager
/*     */ {
/*     */   private static final float DEF_THIRD_CAMERA_DIST = 4.0F;
/*  28 */   private static final Minecraft mc = Minecraft.func_71410_x();
/*     */ 
/*     */   
/*  31 */   private static float cameraRoll = 0.0F;
/*  32 */   private static float cameraDistance = 4.0F;
/*  33 */   private static float cameraZoom = 1.0F;
/*     */   
/*  35 */   private static MCH_EntityAircraft ridingAircraft = null;
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   static void onCameraSetupEvent(EntityViewRenderEvent.CameraSetup event) {
/*  40 */     Entity entity = event.getEntity();
/*  41 */     float f = event.getEntity().func_70047_e();
/*     */     
/*  43 */     if (mc.field_71474_y.field_74320_O > 0) {
/*     */       
/*  45 */       if (mc.field_71474_y.field_74320_O == 2)
/*     */       {
/*  47 */         GlStateManager.func_179114_b(180.0F, 0.0F, 1.0F, 0.0F);
/*     */       }
/*     */       
/*  50 */       GlStateManager.func_179109_b(0.0F, 0.0F, -(cameraDistance - 4.0F));
/*     */       
/*  52 */       if (mc.field_71474_y.field_74320_O == 2)
/*     */       {
/*  54 */         GlStateManager.func_179114_b(-180.0F, 0.0F, 1.0F, 0.0F);
/*     */       }
/*     */     } 
/*     */     
/*  58 */     MCH_EntityAircraft ridingEntity = ridingAircraft;
/*     */     
/*  60 */     if (ridingEntity != null && ridingEntity.canSwitchFreeLook() && ridingEntity.isPilot((Entity)mc.field_71439_g)) {
/*     */       
/*  62 */       boolean flag = !(entity instanceof MCH_ViewEntityDummy);
/*     */       
/*  64 */       GlStateManager.func_179109_b(0.0F, -f, 0.0F);
/*     */       
/*  66 */       if (flag)
/*     */       {
/*  68 */         GlStateManager.func_179114_b(cameraRoll, 0.0F, 0.0F, 1.0F);
/*     */       }
/*     */       
/*  71 */       if (ridingEntity.isOverridePlayerPitch())
/*     */       {
/*  73 */         if (flag) {
/*     */           
/*  75 */           GlStateManager.func_179114_b(ridingEntity.field_70125_A, 1.0F, 0.0F, 0.0F);
/*  76 */           event.setPitch(event.getPitch() - ridingEntity.field_70125_A);
/*     */         } 
/*     */       }
/*     */       
/*  80 */       if (ridingEntity.isOverridePlayerYaw())
/*     */       {
/*  82 */         if (!ridingEntity.isHovering() && flag) {
/*     */           
/*  84 */           GlStateManager.func_179114_b(ridingEntity.field_70177_z, 0.0F, 1.0F, 0.0F);
/*  85 */           event.setYaw(event.getYaw() - ridingEntity.field_70177_z);
/*     */         } 
/*     */       }
/*     */       
/*  89 */       GlStateManager.func_179109_b(0.0F, f, 0.0F);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   static void onFOVModifierEvent(EntityViewRenderEvent.FOVModifier event) {
/*  96 */     MCH_ViewEntityDummy viewer = MCH_ViewEntityDummy.getInstance((World)mc.field_71441_e);
/*     */     
/*  98 */     if (viewer == event.getEntity() || MCH_ItemRangeFinder.isUsingScope((EntityPlayer)mc.field_71439_g))
/*     */     {
/* 100 */       event.setFOV(event.getFOV() * 1.0F / cameraZoom);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static void setCameraRoll(float roll) {
/* 106 */     roll = MathHelper.func_76142_g(roll);
/* 107 */     cameraRoll = roll;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void setThirdPeasonCameraDistance(float distance) {
/* 112 */     distance = MathHelper.func_76131_a(distance, 4.0F, 60.0F);
/* 113 */     cameraDistance = distance;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void setCameraZoom(float zoom) {
/* 118 */     cameraZoom = zoom;
/*     */   }
/*     */ 
/*     */   
/*     */   public static float getThirdPeasonCameraDistance() {
/* 123 */     return cameraDistance;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void setRidingAircraft(@Nullable MCH_EntityAircraft aircraft) {
/* 128 */     ridingAircraft = aircraft;
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\__helper\client\MCH_CameraManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */