/*     */ package mcheli.plane;
/*     */ 
/*     */ import mcheli.aircraft.MCH_AircraftInfo;
/*     */ import mcheli.aircraft.MCH_EntityAircraft;
/*     */ import mcheli.aircraft.MCH_RenderAircraft;
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
/*     */ public class MCP_RenderPlane
/*     */   extends MCH_RenderAircraft<MCP_EntityPlane>
/*     */ {
/*  24 */   public static final IRenderFactory<MCP_EntityPlane> FACTORY = MCP_RenderPlane::new;
/*     */ 
/*     */ 
/*     */   
/*     */   public MCP_RenderPlane(RenderManager renderManager) {
/*  29 */     super(renderManager);
/*  30 */     this.field_76989_e = 2.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderAircraft(MCH_EntityAircraft entity, double posX, double posY, double posZ, float yaw, float pitch, float roll, float tickTime) {
/*     */     MCP_EntityPlane plane;
/*  37 */     MCP_PlaneInfo planeInfo = null;
/*     */ 
/*     */     
/*  40 */     if (entity != null && entity instanceof MCP_EntityPlane) {
/*     */ 
/*     */       
/*  43 */       plane = (MCP_EntityPlane)entity;
/*  44 */       planeInfo = plane.getPlaneInfo();
/*     */ 
/*     */ 
/*     */       
/*  48 */       if (planeInfo == null) {
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
/*  59 */     posY += 0.3499999940395355D;
/*     */     
/*  61 */     renderDebugHitBox(plane, posX, posY, posZ, yaw, pitch);
/*  62 */     renderDebugPilotSeat(plane, posX, posY, posZ, yaw, pitch, roll);
/*     */     
/*  64 */     GL11.glTranslated(posX, posY, posZ);
/*  65 */     GL11.glRotatef(yaw, 0.0F, -1.0F, 0.0F);
/*  66 */     GL11.glRotatef(pitch, 1.0F, 0.0F, 0.0F);
/*  67 */     GL11.glRotatef(roll, 0.0F, 0.0F, 1.0F);
/*     */     
/*  69 */     bindTexture("textures/planes/" + plane.getTextureName() + ".png", plane);
/*     */     
/*  71 */     if (planeInfo.haveNozzle() && plane.partNozzle != null)
/*     */     {
/*  73 */       renderNozzle(plane, planeInfo, tickTime);
/*     */     }
/*     */     
/*  76 */     if (planeInfo.haveWing() && plane.partWing != null)
/*     */     {
/*  78 */       renderWing(plane, planeInfo, tickTime);
/*     */     }
/*     */     
/*  81 */     if (planeInfo.haveRotor() && plane.partNozzle != null)
/*     */     {
/*  83 */       renderRotor(plane, planeInfo, tickTime);
/*     */     }
/*     */     
/*  86 */     renderBody(planeInfo.model);
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderRotor(MCP_EntityPlane plane, MCP_PlaneInfo planeInfo, float tickTime) {
/*  91 */     float rot = plane.getNozzleRotation();
/*  92 */     float prevRot = plane.getPrevNozzleRotation();
/*     */     
/*  94 */     for (MCP_PlaneInfo.Rotor r : planeInfo.rotorList) {
/*     */       
/*  96 */       GL11.glPushMatrix();
/*  97 */       GL11.glTranslated(r.pos.field_72450_a, r.pos.field_72448_b, r.pos.field_72449_c);
/*  98 */       GL11.glRotatef((prevRot + (rot - prevRot) * tickTime) * r.maxRotFactor, (float)r.rot.field_72450_a, (float)r.rot.field_72448_b, (float)r.rot.field_72449_c);
/*     */       
/* 100 */       GL11.glTranslated(-r.pos.field_72450_a, -r.pos.field_72448_b, -r.pos.field_72449_c);
/*     */       
/* 102 */       renderPart(r.model, planeInfo.model, r.modelName);
/*     */       
/* 104 */       for (MCP_PlaneInfo.Blade b : r.blades) {
/*     */         
/* 106 */         float br = plane.prevRotationRotor;
/* 107 */         br += (plane.rotationRotor - plane.prevRotationRotor) * tickTime;
/*     */         
/* 109 */         GL11.glPushMatrix();
/* 110 */         GL11.glTranslated(b.pos.field_72450_a, b.pos.field_72448_b, b.pos.field_72449_c);
/* 111 */         GL11.glRotatef(br, (float)b.rot.field_72450_a, (float)b.rot.field_72448_b, (float)b.rot.field_72449_c);
/* 112 */         GL11.glTranslated(-b.pos.field_72450_a, -b.pos.field_72448_b, -b.pos.field_72449_c);
/*     */         
/* 114 */         for (int i = 0; i < b.numBlade; i++) {
/*     */           
/* 116 */           GL11.glTranslated(b.pos.field_72450_a, b.pos.field_72448_b, b.pos.field_72449_c);
/* 117 */           GL11.glRotatef(b.rotBlade, (float)b.rot.field_72450_a, (float)b.rot.field_72448_b, (float)b.rot.field_72449_c);
/* 118 */           GL11.glTranslated(-b.pos.field_72450_a, -b.pos.field_72448_b, -b.pos.field_72449_c);
/*     */           
/* 120 */           renderPart(b.model, planeInfo.model, b.modelName);
/*     */         } 
/*     */         
/* 123 */         GL11.glPopMatrix();
/*     */       } 
/*     */       
/* 126 */       GL11.glPopMatrix();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderWing(MCP_EntityPlane plane, MCP_PlaneInfo planeInfo, float tickTime) {
/* 132 */     float rot = plane.getWingRotation();
/* 133 */     float prevRot = plane.getPrevWingRotation();
/*     */     
/* 135 */     for (MCP_PlaneInfo.Wing w : planeInfo.wingList) {
/*     */       
/* 137 */       GL11.glPushMatrix();
/* 138 */       GL11.glTranslated(w.pos.field_72450_a, w.pos.field_72448_b, w.pos.field_72449_c);
/* 139 */       GL11.glRotatef((prevRot + (rot - prevRot) * tickTime) * w.maxRotFactor, (float)w.rot.field_72450_a, (float)w.rot.field_72448_b, (float)w.rot.field_72449_c);
/*     */       
/* 141 */       GL11.glTranslated(-w.pos.field_72450_a, -w.pos.field_72448_b, -w.pos.field_72449_c);
/*     */       
/* 143 */       renderPart(w.model, planeInfo.model, w.modelName);
/*     */       
/* 145 */       if (w.pylonList != null)
/*     */       {
/* 147 */         for (MCP_PlaneInfo.Pylon p : w.pylonList) {
/*     */           
/* 149 */           GL11.glPushMatrix();
/* 150 */           GL11.glTranslated(p.pos.field_72450_a, p.pos.field_72448_b, p.pos.field_72449_c);
/* 151 */           GL11.glRotatef((prevRot + (rot - prevRot) * tickTime) * p.maxRotFactor, (float)p.rot.field_72450_a, (float)p.rot.field_72448_b, (float)p.rot.field_72449_c);
/*     */           
/* 153 */           GL11.glTranslated(-p.pos.field_72450_a, -p.pos.field_72448_b, -p.pos.field_72449_c);
/*     */           
/* 155 */           renderPart(p.model, planeInfo.model, p.modelName);
/*     */           
/* 157 */           GL11.glPopMatrix();
/*     */         } 
/*     */       }
/*     */       
/* 161 */       GL11.glPopMatrix();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderNozzle(MCP_EntityPlane plane, MCP_PlaneInfo planeInfo, float tickTime) {
/* 167 */     float rot = plane.getNozzleRotation();
/* 168 */     float prevRot = plane.getPrevNozzleRotation();
/*     */     
/* 170 */     for (MCH_AircraftInfo.DrawnPart n : planeInfo.nozzles) {
/*     */       
/* 172 */       GL11.glPushMatrix();
/* 173 */       GL11.glTranslated(n.pos.field_72450_a, n.pos.field_72448_b, n.pos.field_72449_c);
/* 174 */       GL11.glRotatef(prevRot + (rot - prevRot) * tickTime, (float)n.rot.field_72450_a, (float)n.rot.field_72448_b, (float)n.rot.field_72449_c);
/* 175 */       GL11.glTranslated(-n.pos.field_72450_a, -n.pos.field_72448_b, -n.pos.field_72449_c);
/*     */       
/* 177 */       renderPart(n.model, planeInfo.model, n.modelName);
/*     */       
/* 179 */       GL11.glPopMatrix();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ResourceLocation getEntityTexture(MCP_EntityPlane entity) {
/* 187 */     return TEX_DEFAULT;
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\plane\MCP_RenderPlane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */