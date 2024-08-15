/*     */ package mcheli.tank;
/*     */ 
/*     */ import mcheli.MCH_Config;
/*     */ import mcheli.__helper.MCH_ColorInt;
/*     */ import mcheli.aircraft.MCH_EntityAircraft;
/*     */ import mcheli.aircraft.MCH_RenderAircraft;
/*     */ import net.minecraft.client.renderer.BufferBuilder;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.entity.RenderManager;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.math.Vec3d;
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
/*     */ 
/*     */ @SideOnly(Side.CLIENT)
/*     */ public class MCH_RenderTank
/*     */   extends MCH_RenderAircraft<MCH_EntityTank>
/*     */ {
/*  30 */   public static final IRenderFactory<MCH_EntityTank> FACTORY = MCH_RenderTank::new;
/*     */ 
/*     */ 
/*     */   
/*     */   public MCH_RenderTank(RenderManager renderManager) {
/*  35 */     super(renderManager);
/*  36 */     this.field_76989_e = 2.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderAircraft(MCH_EntityAircraft entity, double posX, double posY, double posZ, float yaw, float pitch, float roll, float tickTime) {
/*     */     MCH_EntityTank tank;
/*  43 */     MCH_TankInfo tankInfo = null;
/*     */ 
/*     */     
/*  46 */     if (entity != null && entity instanceof MCH_EntityTank) {
/*     */ 
/*     */       
/*  49 */       tank = (MCH_EntityTank)entity;
/*  50 */       tankInfo = tank.getTankInfo();
/*     */ 
/*     */ 
/*     */       
/*  54 */       if (tankInfo == null) {
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
/*  65 */     posY += 0.3499999940395355D;
/*     */     
/*  67 */     renderWheel(tank, posX, posY, posZ);
/*  68 */     renderDebugHitBox(tank, posX, posY, posZ, yaw, pitch);
/*  69 */     renderDebugPilotSeat(tank, posX, posY, posZ, yaw, pitch, roll);
/*     */     
/*  71 */     GL11.glTranslated(posX, posY, posZ);
/*     */     
/*  73 */     GL11.glRotatef(yaw, 0.0F, -1.0F, 0.0F);
/*  74 */     GL11.glRotatef(pitch, 1.0F, 0.0F, 0.0F);
/*  75 */     GL11.glRotatef(roll, 0.0F, 0.0F, 1.0F);
/*     */     
/*  77 */     bindTexture("textures/tanks/" + tank.getTextureName() + ".png", tank);
/*     */     
/*  79 */     renderBody(tankInfo.model);
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderWheel(MCH_EntityTank tank, double posX, double posY, double posZ) {
/*  84 */     if (!MCH_Config.TestMode.prmBool)
/*     */       return; 
/*  86 */     if (debugModel == null) {
/*     */       return;
/*     */     }
/*     */     
/*  90 */     GL11.glColor4f(0.75F, 0.75F, 0.75F, 0.5F);
/*  91 */     for (MCH_EntityWheel w : tank.WheelMng.wheels) {
/*     */       
/*  93 */       GL11.glPushMatrix();
/*  94 */       GL11.glTranslated(w.field_70165_t - tank.field_70165_t + posX, w.field_70163_u - tank.field_70163_u + posY + 0.25D, w.field_70161_v - tank.field_70161_v + posZ);
/*     */       
/*  96 */       GL11.glScalef(w.field_70130_N, w.field_70131_O / 2.0F, w.field_70130_N);
/*     */       
/*  98 */       bindTexture("textures/seat_pilot.png");
/*  99 */       debugModel.renderAll();
/* 100 */       GL11.glPopMatrix();
/*     */     } 
/* 102 */     GL11.glColor4f(0.75F, 0.75F, 0.75F, 1.0F);
/* 103 */     Tessellator tessellator = Tessellator.func_178181_a();
/* 104 */     BufferBuilder builder = tessellator.func_178180_c();
/*     */ 
/*     */     
/* 107 */     builder.func_181668_a(1, DefaultVertexFormats.field_181706_f);
/* 108 */     Vec3d wp = tank.getTransformedPosition(tank.WheelMng.weightedCenter);
/*     */ 
/*     */ 
/*     */     
/* 112 */     wp = wp.func_178786_a(tank.field_70165_t, tank.field_70163_u, tank.field_70161_v);
/*     */     
/* 114 */     for (int i = 0; i < tank.WheelMng.wheels.length / 2; i++) {
/*     */ 
/*     */ 
/*     */       
/* 118 */       MCH_ColorInt cint = new MCH_ColorInt(((i & 0x4) > 0) ? 16711680 : 0, ((i & 0x2) > 0) ? 65280 : 0, ((i & 0x1) > 0) ? 255 : 0, 192);
/*     */ 
/*     */       
/* 121 */       MCH_EntityWheel w1 = tank.WheelMng.wheels[i * 2 + 0];
/* 122 */       MCH_EntityWheel w2 = tank.WheelMng.wheels[i * 2 + 1];
/*     */       
/* 124 */       if (w1.isPlus) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 132 */         builder.func_181662_b(w2.field_70165_t - tank.field_70165_t + posX, w2.field_70163_u - tank.field_70163_u + posY, w2.field_70161_v - tank.field_70161_v + posZ)
/* 133 */           .func_181669_b(cint.r, cint.g, cint.b, cint.a).func_181675_d();
/* 134 */         builder.func_181662_b(w1.field_70165_t - tank.field_70165_t + posX, w1.field_70163_u - tank.field_70163_u + posY, w1.field_70161_v - tank.field_70161_v + posZ)
/* 135 */           .func_181669_b(cint.r, cint.g, cint.b, cint.a).func_181675_d();
/* 136 */         builder.func_181662_b(w1.field_70165_t - tank.field_70165_t + posX, w1.field_70163_u - tank.field_70163_u + posY, w1.field_70161_v - tank.field_70161_v + posZ)
/* 137 */           .func_181669_b(cint.r, cint.g, cint.b, cint.a).func_181675_d();
/* 138 */         builder.func_181662_b(posX + wp.field_72450_a, posY + wp.field_72448_b, posZ + wp.field_72449_c).func_181669_b(cint.r, cint.g, cint.b, cint.a).func_181675_d();
/* 139 */         builder.func_181662_b(posX + wp.field_72450_a, posY + wp.field_72448_b, posZ + wp.field_72449_c).func_181669_b(cint.r, cint.g, cint.b, cint.a).func_181675_d();
/* 140 */         builder.func_181662_b(w2.field_70165_t - tank.field_70165_t + posX, w2.field_70163_u - tank.field_70163_u + posY, w2.field_70161_v - tank.field_70161_v + posZ)
/* 141 */           .func_181669_b(cint.r, cint.g, cint.b, cint.a).func_181675_d();
/*     */ 
/*     */ 
/*     */       
/*     */       }
/*     */       else {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 151 */         builder.func_181662_b(w1.field_70165_t - tank.field_70165_t + posX, w1.field_70163_u - tank.field_70163_u + posY, w1.field_70161_v - tank.field_70161_v + posZ)
/* 152 */           .func_181669_b(cint.r, cint.g, cint.b, cint.a).func_181675_d();
/* 153 */         builder.func_181662_b(w2.field_70165_t - tank.field_70165_t + posX, w2.field_70163_u - tank.field_70163_u + posY, w2.field_70161_v - tank.field_70161_v + posZ)
/* 154 */           .func_181669_b(cint.r, cint.g, cint.b, cint.a).func_181675_d();
/* 155 */         builder.func_181662_b(w2.field_70165_t - tank.field_70165_t + posX, w2.field_70163_u - tank.field_70163_u + posY, w2.field_70161_v - tank.field_70161_v + posZ)
/* 156 */           .func_181669_b(cint.r, cint.g, cint.b, cint.a).func_181675_d();
/* 157 */         builder.func_181662_b(posX + wp.field_72450_a, posY + wp.field_72448_b, posZ + wp.field_72449_c).func_181669_b(cint.r, cint.g, cint.b, cint.a).func_181675_d();
/* 158 */         builder.func_181662_b(posX + wp.field_72450_a, posY + wp.field_72448_b, posZ + wp.field_72449_c).func_181669_b(cint.r, cint.g, cint.b, cint.a).func_181675_d();
/* 159 */         builder.func_181662_b(w1.field_70165_t - tank.field_70165_t + posX, w1.field_70163_u - tank.field_70163_u + posY, w1.field_70161_v - tank.field_70161_v + posZ)
/* 160 */           .func_181669_b(cint.r, cint.g, cint.b, cint.a).func_181675_d();
/*     */       } 
/*     */     } 
/* 163 */     tessellator.func_78381_a();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ResourceLocation getEntityTexture(MCH_EntityTank entity) {
/* 170 */     return TEX_DEFAULT;
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\tank\MCH_RenderTank.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */