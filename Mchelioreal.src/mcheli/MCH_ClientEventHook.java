/*     */ package mcheli;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import mcheli.__helper.entity.ITargetMarkerObject;
/*     */ import mcheli.aircraft.MCH_EntityAircraft;
/*     */ import mcheli.aircraft.MCH_RenderAircraft;
/*     */ import mcheli.lweapon.MCH_ClientLightWeaponTickHandler;
/*     */ import mcheli.multiplay.MCH_GuiTargetMarker;
/*     */ import mcheli.particles.MCH_ParticlesUtil;
/*     */ import mcheli.tool.rangefinder.MCH_ItemRangeFinder;
/*     */ import mcheli.wrapper.W_ClientEventHook;
/*     */ import mcheli.wrapper.W_Reflection;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.client.renderer.BufferBuilder;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.entity.Render;
/*     */ import net.minecraft.client.renderer.entity.RenderManager;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraftforge.client.event.MouseEvent;
/*     */ import net.minecraftforge.client.event.RenderLivingEvent;
/*     */ import net.minecraftforge.client.event.RenderPlayerEvent;
/*     */ import net.minecraftforge.event.entity.EntityJoinWorldEvent;
/*     */ import net.minecraftforge.event.world.WorldEvent;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MCH_ClientEventHook
/*     */   extends W_ClientEventHook
/*     */ {
/*  40 */   MCH_TextureManagerDummy dummyTextureManager = null;
/*     */   
/*  42 */   public static List<MCH_EntityAircraft> haveSearchLightAircraft = new ArrayList<>();
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderLivingEventSpecialsPre(RenderLivingEvent.Specials.Pre<EntityLivingBase> event) {
/*  47 */     if (MCH_Config.DisableRenderLivingSpecials.prmBool) {
/*     */       
/*  49 */       MCH_EntityAircraft ac = MCH_EntityAircraft.getAircraft_RiddenOrControl((Entity)(Minecraft.func_71410_x()).field_71439_g);
/*     */       
/*  51 */       if (ac != null && ac.isMountedEntity((Entity)event.getEntity())) {
/*     */         
/*  53 */         event.setCanceled(true);
/*     */         return;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*  59 */   private static final ResourceLocation ir_strobe = new ResourceLocation("mcheli", "textures/ir_strobe.png");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderLivingEventSpecialsPost(RenderLivingEvent.Specials.Post<EntityLivingBase> event) {}
/*     */ 
/*     */ 
/*     */   
/*     */   private void renderIRStrobe(EntityLivingBase entity, RenderLivingEvent.Specials.Post<EntityLivingBase> event) {
/*  69 */     int cm = MCH_ClientCommonTickHandler.cameraMode;
/*  70 */     if (cm == 0) {
/*     */       return;
/*     */     }
/*     */     
/*  74 */     int ticks = entity.field_70173_aa % 20;
/*  75 */     if (ticks >= 4)
/*     */       return; 
/*  77 */     float alpha = (ticks == 2 || ticks == 1) ? 1.0F : 0.5F;
/*     */     
/*  79 */     EntityPlayerSP entityPlayerSP = (Minecraft.func_71410_x()).field_71439_g;
/*  80 */     if (entityPlayerSP == null) {
/*     */       return;
/*     */     }
/*     */     
/*  84 */     if (!entityPlayerSP.func_184191_r((Entity)entity)) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/*  89 */     int j = 240;
/*  90 */     int k = 240;
/*  91 */     OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, j / 1.0F, k / 1.0F);
/*  92 */     RenderManager rm = event.getRenderer().func_177068_d();
/*     */     
/*  94 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*  95 */     float f1 = 0.080000006F;
/*  96 */     GL11.glPushMatrix();
/*  97 */     GL11.glTranslated(event.getX(), event.getY() + (float)(entity.field_70131_O * 0.75D), event.getZ());
/*  98 */     GL11.glNormal3f(0.0F, 1.0F, 0.0F);
/*     */ 
/*     */     
/* 101 */     GL11.glRotatef(-rm.field_78735_i, 0.0F, 1.0F, 0.0F);
/* 102 */     GL11.glRotatef(rm.field_78732_j, 1.0F, 0.0F, 0.0F);
/* 103 */     GL11.glScalef(-f1, -f1, f1);
/*     */     
/* 105 */     GL11.glEnable(3042);
/* 106 */     OpenGlHelper.func_148821_a(770, 771, 1, 0);
/*     */     
/* 108 */     GL11.glEnable(3553);
/*     */ 
/*     */     
/* 111 */     rm.field_78724_e.func_110577_a(ir_strobe);
/*     */     
/* 113 */     GL11.glAlphaFunc(516, 0.003921569F);
/*     */     
/* 115 */     Tessellator tessellator = Tessellator.func_178181_a();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 123 */     BufferBuilder builder = tessellator.func_178180_c();
/* 124 */     builder.func_181668_a(7, DefaultVertexFormats.field_181709_i);
/* 125 */     int i = (int)Math.max(entity.field_70130_N, entity.field_70131_O) * 20;
/* 126 */     builder.func_181662_b(-i, -i, 0.1D).func_187315_a(0.0D, 0.0D).func_181666_a(1.0F, 1.0F, 1.0F, alpha * ((cm == 1) ? 0.9F : 0.5F)).func_181675_d();
/* 127 */     builder.func_181662_b(-i, i, 0.1D).func_187315_a(0.0D, 1.0D).func_181666_a(1.0F, 1.0F, 1.0F, alpha * ((cm == 1) ? 0.9F : 0.5F)).func_181675_d();
/* 128 */     builder.func_181662_b(i, i, 0.1D).func_187315_a(1.0D, 1.0D).func_181666_a(1.0F, 1.0F, 1.0F, alpha * ((cm == 1) ? 0.9F : 0.5F)).func_181675_d();
/* 129 */     builder.func_181662_b(i, -i, 0.1D).func_187315_a(1.0D, 0.0D).func_181666_a(1.0F, 1.0F, 1.0F, alpha * ((cm == 1) ? 0.9F : 0.5F)).func_181675_d();
/* 130 */     tessellator.func_78381_a();
/*     */     
/* 132 */     GL11.glEnable(2896);
/* 133 */     GL11.glPopMatrix();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void mouseEvent(MouseEvent event) {
/* 139 */     if (MCH_ClientTickHandlerBase.updateMouseWheel(event.getDwheel()))
/*     */     {
/* 141 */       event.setCanceled(true);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean cancelRender = true;
/*     */   
/*     */   public static void setCancelRender(boolean cancel) {
/* 149 */     cancelRender = cancel;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderLivingEventPre(RenderLivingEvent.Pre<EntityLivingBase> event) {
/* 155 */     for (MCH_EntityAircraft ac : haveSearchLightAircraft)
/*     */     {
/* 157 */       OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, ac
/* 158 */           .getSearchLightValue((Entity)event.getEntity()), 240.0F);
/*     */     }
/*     */     
/* 161 */     if (MCH_Config.EnableModEntityRender.prmBool)
/*     */     {
/* 163 */       if (cancelRender)
/*     */       {
/* 165 */         if (event.getEntity().func_184187_bx() instanceof MCH_EntityAircraft || event
/* 166 */           .getEntity().func_184187_bx() instanceof mcheli.aircraft.MCH_EntitySeat) {
/*     */ 
/*     */           
/* 169 */           event.setCanceled(true);
/*     */           
/*     */           return;
/*     */         } 
/*     */       }
/*     */     }
/* 175 */     if (MCH_Config.EnableReplaceTextureManager.prmBool) {
/*     */       
/* 177 */       RenderManager rm = W_Reflection.getRenderManager((Render)event.getRenderer());
/*     */       
/* 179 */       if (rm != null && !(rm.field_78724_e instanceof MCH_TextureManagerDummy)) {
/*     */         
/* 181 */         if (this.dummyTextureManager == null)
/*     */         {
/* 183 */           this.dummyTextureManager = new MCH_TextureManagerDummy(rm.field_78724_e);
/*     */         }
/* 185 */         rm.field_78724_e = this.dummyTextureManager;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderLivingEventPost(RenderLivingEvent.Post<EntityLivingBase> event) {
/* 193 */     MCH_RenderAircraft.renderEntityMarker((Entity)event.getEntity());
/*     */     
/* 195 */     if (event.getEntity() instanceof ITargetMarkerObject) {
/*     */       
/* 197 */       MCH_GuiTargetMarker.addMarkEntityPos(2, (ITargetMarkerObject)event.getEntity(), event.getX(), event
/* 198 */           .getY() + (event.getEntity()).field_70131_O + 0.5D, event.getZ());
/*     */     }
/*     */     else {
/*     */       
/* 202 */       MCH_GuiTargetMarker.addMarkEntityPos(2, ITargetMarkerObject.fromEntity((Entity)event.getEntity()), event.getX(), event
/* 203 */           .getY() + (event.getEntity()).field_70131_O + 0.5D, event.getZ());
/*     */     } 
/* 205 */     MCH_ClientLightWeaponTickHandler.markEntity((Entity)event.getEntity(), event.getX(), event
/* 206 */         .getY() + ((event.getEntity()).field_70131_O / 2.0F), event.getZ());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderPlayerPre(RenderPlayerEvent.Pre event) {
/* 212 */     if (event.getEntity() == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 217 */     if (event.getEntity().func_184187_bx() instanceof MCH_EntityAircraft) {
/*     */       
/* 219 */       MCH_EntityAircraft v = (MCH_EntityAircraft)event.getEntity().func_184187_bx();
/*     */       
/* 221 */       if (v.getAcInfo() != null && (v.getAcInfo()).hideEntity) {
/*     */         
/* 223 */         event.setCanceled(true);
/*     */         return;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderPlayerPost(RenderPlayerEvent.Post event) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void worldEventUnload(WorldEvent.Unload event) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void entityJoinWorldEvent(EntityJoinWorldEvent event) {
/* 242 */     if (event.getEntity().func_70028_i(MCH_Lib.getClientPlayer())) {
/*     */       
/* 244 */       MCH_Lib.DbgLog(true, "MCH_ClientEventHook.entityJoinWorldEvent : " + event.getEntity(), new Object[0]);
/* 245 */       MCH_ItemRangeFinder.mode = Minecraft.func_71410_x().func_71356_B() ? 1 : 0;
/*     */       
/* 247 */       MCH_ParticlesUtil.clearMarkPoint();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\MCH_ClientEventHook.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */