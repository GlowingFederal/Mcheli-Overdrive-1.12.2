/*     */ package mcheli.vehicle;
/*     */ 
/*     */ import mcheli.MCH_Lib;
/*     */ import mcheli.MCH_ModelManager;
/*     */ import mcheli.aircraft.MCH_EntityAircraft;
/*     */ import mcheli.aircraft.MCH_RenderAircraft;
/*     */ import mcheli.weapon.MCH_WeaponSet;
/*     */ import mcheli.wrapper.W_Lib;
/*     */ import net.minecraft.client.renderer.entity.RenderManager;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraftforge.fml.client.registry.IRenderFactory;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @SideOnly(Side.CLIENT)
/*     */ public class MCH_RenderVehicle
/*     */   extends MCH_RenderAircraft<MCH_EntityVehicle>
/*     */ {
/*  27 */   public static final IRenderFactory<MCH_EntityVehicle> FACTORY = MCH_RenderVehicle::new;
/*     */ 
/*     */ 
/*     */   
/*     */   public MCH_RenderVehicle(RenderManager renderManager) {
/*  32 */     super(renderManager);
/*  33 */     this.field_76989_e = 2.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderAircraft(MCH_EntityAircraft entity, double posX, double posY, double posZ, float yaw, float pitch, float roll, float tickTime) {
/*     */     MCH_EntityVehicle vehicle;
/*  40 */     MCH_VehicleInfo vehicleInfo = null;
/*     */ 
/*     */     
/*  43 */     if (entity != null && entity instanceof MCH_EntityVehicle) {
/*     */ 
/*     */       
/*  46 */       vehicle = (MCH_EntityVehicle)entity;
/*  47 */       vehicleInfo = vehicle.getVehicleInfo();
/*     */ 
/*     */ 
/*     */       
/*  51 */       if (vehicleInfo == null) {
/*     */         return;
/*     */       }
/*     */     } else {
/*     */       return;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  63 */     if (vehicle.getRiddenByEntity() != null && !vehicle.isDestroyed()) {
/*     */       
/*  65 */       vehicle.isUsedPlayer = true;
/*     */ 
/*     */       
/*  68 */       vehicle.lastRiderYaw = (vehicle.getRiddenByEntity()).field_70177_z;
/*  69 */       vehicle.lastRiderPitch = (vehicle.getRiddenByEntity()).field_70125_A;
/*     */     }
/*  71 */     else if (!vehicle.isUsedPlayer) {
/*     */       
/*  73 */       vehicle.lastRiderYaw = vehicle.field_70177_z;
/*  74 */       vehicle.lastRiderPitch = vehicle.field_70125_A;
/*     */     } 
/*     */     
/*  77 */     posY += 0.3499999940395355D;
/*     */     
/*  79 */     renderDebugHitBox(vehicle, posX, posY, posZ, yaw, pitch);
/*  80 */     renderDebugPilotSeat(vehicle, posX, posY, posZ, yaw, pitch, roll);
/*     */     
/*  82 */     GL11.glTranslated(posX, posY, posZ);
/*  83 */     GL11.glRotatef(yaw, 0.0F, -1.0F, 0.0F);
/*  84 */     GL11.glRotatef(pitch, 1.0F, 0.0F, 0.0F);
/*     */     
/*  86 */     bindTexture("textures/vehicles/" + vehicle.getTextureName() + ".png", vehicle);
/*     */     
/*  88 */     renderBody(vehicleInfo.model);
/*     */     
/*  90 */     MCH_WeaponSet ws = vehicle.getFirstSeatWeapon();
/*     */     
/*  92 */     drawPart(vehicle, vehicleInfo, yaw, pitch, ws, tickTime);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawPart(MCH_EntityVehicle vehicle, MCH_VehicleInfo info, float yaw, float pitch, MCH_WeaponSet ws, float tickTime) {
/*  98 */     float rotBrl = ws.prevRotBarrel + (ws.rotBarrel - ws.prevRotBarrel) * tickTime;
/*  99 */     int index = 0;
/*     */     
/* 101 */     for (MCH_VehicleInfo.VPart vp : info.partList)
/*     */     {
/* 103 */       index = drawPart(vp, vehicle, info, yaw, pitch, rotBrl, tickTime, ws, index);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   int drawPart(MCH_VehicleInfo.VPart vp, MCH_EntityVehicle vehicle, MCH_VehicleInfo info, float yaw, float pitch, float rotBrl, float tickTime, MCH_WeaponSet ws, int index) {
/* 110 */     GL11.glPushMatrix();
/* 111 */     float recoilBuf = 0.0F;
/*     */     
/* 113 */     if (index < ws.getWeaponNum()) {
/*     */       
/* 115 */       MCH_WeaponSet.Recoil r = ws.recoilBuf[index];
/* 116 */       recoilBuf = r.prevRecoilBuf + (r.recoilBuf - r.prevRecoilBuf) * tickTime;
/*     */     } 
/*     */     
/* 119 */     int bkIndex = index;
/*     */     
/* 121 */     if (vp.rotPitch || vp.rotYaw || vp.type == 1) {
/*     */       
/* 123 */       GL11.glTranslated(vp.pos.field_72450_a, vp.pos.field_72448_b, vp.pos.field_72449_c);
/*     */       
/* 125 */       if (vp.rotYaw)
/*     */       {
/* 127 */         GL11.glRotatef(-vehicle.lastRiderYaw + yaw, 0.0F, 1.0F, 0.0F);
/*     */       }
/* 129 */       if (vp.rotPitch) {
/*     */         
/* 131 */         float p = MCH_Lib.RNG(vehicle.lastRiderPitch, info.minRotationPitch, info.maxRotationPitch);
/* 132 */         GL11.glRotatef(p - pitch, 1.0F, 0.0F, 0.0F);
/*     */       } 
/* 134 */       if (vp.type == 1)
/*     */       {
/* 136 */         GL11.glRotatef(rotBrl, 0.0F, 0.0F, -1.0F);
/*     */       }
/*     */       
/* 139 */       GL11.glTranslated(-vp.pos.field_72450_a, -vp.pos.field_72448_b, -vp.pos.field_72449_c);
/*     */     } 
/* 141 */     if (vp.type == 2)
/*     */     {
/* 143 */       GL11.glTranslated(0.0D, 0.0D, (-vp.recoilBuf * recoilBuf));
/*     */     }
/*     */     
/* 146 */     if (vp.type == 2 || vp.type == 3)
/*     */     {
/* 148 */       index++;
/*     */     }
/* 150 */     if (vp.child != null)
/*     */     {
/* 152 */       for (MCH_VehicleInfo.VPart vcp : vp.child)
/*     */       {
/* 154 */         index = drawPart(vcp, vehicle, info, yaw, pitch, rotBrl, recoilBuf, ws, index);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/* 159 */     if (vp.drawFP || !W_Lib.isClientPlayer(vehicle.getRiddenByEntity()) || !W_Lib.isFirstPerson())
/*     */     {
/* 161 */       if (vp.type != 3 || !vehicle.isWeaponNotCooldown(ws, bkIndex)) {
/*     */         
/* 163 */         renderPart(vp.model, info.model, vp.modelName);
/* 164 */         MCH_ModelManager.render("vehicles", vp.modelName);
/*     */       } 
/*     */     }
/* 167 */     GL11.glPopMatrix();
/* 168 */     return index;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ResourceLocation getEntityTexture(MCH_EntityVehicle entity) {
/* 175 */     return TEX_DEFAULT;
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\vehicle\MCH_RenderVehicle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */