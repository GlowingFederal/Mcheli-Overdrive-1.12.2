/*     */ package mcheli.helicopter;
/*     */ 
/*     */ import mcheli.aircraft.MCH_Blade;
/*     */ import mcheli.aircraft.MCH_EntityAircraft;
/*     */ import mcheli.aircraft.MCH_RenderAircraft;
/*     */ import mcheli.aircraft.MCH_Rotor;
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
/*     */ public class MCH_RenderHeli
/*     */   extends MCH_RenderAircraft<MCH_EntityHeli>
/*     */ {
/*  25 */   public static final IRenderFactory<MCH_EntityHeli> FACTORY = MCH_RenderHeli::new;
/*     */ 
/*     */ 
/*     */   
/*     */   public MCH_RenderHeli(RenderManager renderManager) {
/*  30 */     super(renderManager);
/*  31 */     this.field_76989_e = 2.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderAircraft(MCH_EntityAircraft entity, double posX, double posY, double posZ, float yaw, float pitch, float roll, float tickTime) {
/*     */     MCH_EntityHeli heli;
/*  38 */     MCH_HeliInfo heliInfo = null;
/*     */ 
/*     */     
/*  41 */     if (entity != null && entity instanceof MCH_EntityHeli) {
/*     */ 
/*     */       
/*  44 */       heli = (MCH_EntityHeli)entity;
/*  45 */       heliInfo = heli.getHeliInfo();
/*     */ 
/*     */ 
/*     */       
/*  49 */       if (heliInfo == null) {
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
/*  60 */     posY += 0.3499999940395355D;
/*     */     
/*  62 */     renderDebugHitBox(heli, posX, posY, posZ, yaw, pitch);
/*  63 */     renderDebugPilotSeat(heli, posX, posY, posZ, yaw, pitch, roll);
/*     */     
/*  65 */     GL11.glTranslated(posX, posY, posZ);
/*  66 */     GL11.glRotatef(yaw, 0.0F, -1.0F, 0.0F);
/*  67 */     GL11.glRotatef(pitch, 1.0F, 0.0F, 0.0F);
/*  68 */     GL11.glRotatef(roll, 0.0F, 0.0F, 1.0F);
/*     */     
/*  70 */     bindTexture("textures/helicopters/" + heli.getTextureName() + ".png", heli);
/*     */     
/*  72 */     renderBody(heliInfo.model);
/*     */     
/*  74 */     drawModelBlade(heli, heliInfo, tickTime);
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawModelBlade(MCH_EntityHeli heli, MCH_HeliInfo info, float tickTime) {
/*  79 */     for (int i = 0; i < heli.rotors.length && i < info.rotorList.size(); i++) {
/*     */       
/*  81 */       MCH_HeliInfo.Rotor rotorInfo = info.rotorList.get(i);
/*  82 */       MCH_Rotor rotor = heli.rotors[i];
/*     */       
/*  84 */       GL11.glPushMatrix();
/*     */       
/*  86 */       if (rotorInfo.oldRenderMethod)
/*     */       {
/*  88 */         GL11.glTranslated(rotorInfo.pos.field_72450_a, rotorInfo.pos.field_72448_b, rotorInfo.pos.field_72449_c);
/*     */       }
/*     */       
/*  91 */       for (MCH_Blade b : rotor.blades) {
/*     */         
/*  93 */         GL11.glPushMatrix();
/*     */         
/*  95 */         float rot = b.getRotation();
/*  96 */         float prevRot = b.getPrevRotation();
/*     */         
/*  98 */         if (rot - prevRot < -180.0F) {
/*     */           
/* 100 */           prevRot -= 360.0F;
/*     */         }
/* 102 */         else if (prevRot - rot < -180.0F) {
/*     */           
/* 104 */           prevRot += 360.0F;
/*     */         } 
/*     */         
/* 107 */         if (!rotorInfo.oldRenderMethod)
/*     */         {
/* 109 */           GL11.glTranslated(rotorInfo.pos.field_72450_a, rotorInfo.pos.field_72448_b, rotorInfo.pos.field_72449_c);
/*     */         }
/*     */         
/* 112 */         GL11.glRotatef(prevRot + (rot - prevRot) * tickTime, (float)rotorInfo.rot.field_72450_a, (float)rotorInfo.rot.field_72448_b, (float)rotorInfo.rot.field_72449_c);
/*     */ 
/*     */         
/* 115 */         if (!rotorInfo.oldRenderMethod)
/*     */         {
/* 117 */           GL11.glTranslated(-rotorInfo.pos.field_72450_a, -rotorInfo.pos.field_72448_b, -rotorInfo.pos.field_72449_c);
/*     */         }
/*     */         
/* 120 */         renderPart(rotorInfo.model, info.model, rotorInfo.modelName);
/*     */         
/* 122 */         GL11.glPopMatrix();
/*     */       } 
/* 124 */       GL11.glPopMatrix();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ResourceLocation getEntityTexture(MCH_EntityHeli entity) {
/* 132 */     return TEX_DEFAULT;
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\helicopter\MCH_RenderHeli.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */