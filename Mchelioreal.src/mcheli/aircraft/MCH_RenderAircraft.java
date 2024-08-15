/*      */ package mcheli.aircraft;
/*      */ 
/*      */ import javax.annotation.Nullable;
/*      */ import mcheli.MCH_ClientCommonTickHandler;
/*      */ import mcheli.MCH_ClientEventHook;
/*      */ import mcheli.MCH_Config;
/*      */ import mcheli.MCH_Lib;
/*      */ import mcheli.__helper.MCH_ColorInt;
/*      */ import mcheli.__helper.MCH_Utils;
/*      */ import mcheli.__helper.client._IModelCustom;
/*      */ import mcheli.__helper.client.renderer.MCH_Verts;
/*      */ import mcheli.__helper.entity.ITargetMarkerObject;
/*      */ import mcheli.debug._v3.WeaponPointRenderer;
/*      */ import mcheli.gui.MCH_Gui;
/*      */ import mcheli.lweapon.MCH_ClientLightWeaponTickHandler;
/*      */ import mcheli.multiplay.MCH_GuiTargetMarker;
/*      */ import mcheli.uav.MCH_EntityUavStation;
/*      */ import mcheli.weapon.MCH_WeaponGuidanceSystem;
/*      */ import mcheli.weapon.MCH_WeaponSet;
/*      */ import mcheli.wrapper.W_Entity;
/*      */ import mcheli.wrapper.W_EntityRenderer;
/*      */ import mcheli.wrapper.W_Lib;
/*      */ import mcheli.wrapper.W_Render;
/*      */ import mcheli.wrapper.modelloader.W_ModelCustom;
/*      */ import net.minecraft.client.Minecraft;
/*      */ import net.minecraft.client.entity.EntityPlayerSP;
/*      */ import net.minecraft.client.gui.FontRenderer;
/*      */ import net.minecraft.client.renderer.BufferBuilder;
/*      */ import net.minecraft.client.renderer.OpenGlHelper;
/*      */ import net.minecraft.client.renderer.RenderHelper;
/*      */ import net.minecraft.client.renderer.Tessellator;
/*      */ import net.minecraft.client.renderer.culling.ICamera;
/*      */ import net.minecraft.client.renderer.entity.RenderManager;
/*      */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*      */ import net.minecraft.client.renderer.texture.TextureMap;
/*      */ import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
/*      */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.entity.EntityLivingBase;
/*      */ import net.minecraft.util.math.MathHelper;
/*      */ import org.lwjgl.opengl.GL11;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class MCH_RenderAircraft<T extends MCH_EntityAircraft>
/*      */   extends W_Render<T>
/*      */ {
/*      */   public static boolean renderingEntity = false;
/*   54 */   public static _IModelCustom debugModel = null;
/*      */ 
/*      */   
/*      */   protected MCH_RenderAircraft(RenderManager renderManager) {
/*   58 */     super(renderManager);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void doRender(T entity, double posX, double posY, double posZ, float par8, float tickTime) {
/*   66 */     T t = entity;
/*   67 */     MCH_AircraftInfo info = t.getAcInfo();
/*      */     
/*   69 */     if (info != null) {
/*      */       
/*   71 */       GL11.glPushMatrix();
/*      */       
/*   73 */       float yaw = calcRot(t.getRotYaw(), ((MCH_EntityAircraft)t).field_70126_B, tickTime);
/*   74 */       float pitch = t.calcRotPitch(tickTime);
/*   75 */       float roll = calcRot(t.getRotRoll(), ((MCH_EntityAircraft)t).prevRotationRoll, tickTime);
/*      */       
/*   77 */       if (MCH_Config.EnableModEntityRender.prmBool)
/*      */       {
/*   79 */         renderRiddenEntity((MCH_EntityAircraft)t, tickTime, yaw, pitch + info.entityPitch, roll + info.entityRoll, info.entityWidth, info.entityHeight);
/*      */       }
/*      */ 
/*      */       
/*   83 */       if (!shouldSkipRender((Entity)entity)) {
/*      */ 
/*      */         
/*   86 */         setCommonRenderParam(info.smoothShading, t.func_70070_b());
/*      */         
/*   88 */         if (t.isDestroyed()) {
/*      */           
/*   90 */           GL11.glColor4f(0.15F, 0.15F, 0.15F, 1.0F);
/*      */         
/*      */         }
/*      */         else {
/*      */           
/*   95 */           GL11.glColor4f(0.75F, 0.75F, 0.75F, (float)MCH_Config.__TextureAlpha.prmDouble);
/*      */         } 
/*      */         
/*   98 */         renderAircraft((MCH_EntityAircraft)t, posX, posY, posZ, yaw, pitch, roll, tickTime);
/*   99 */         renderCommonPart((MCH_EntityAircraft)t, info, posX, posY, posZ, tickTime);
/*  100 */         renderLight(posX, posY, posZ, tickTime, (MCH_EntityAircraft)t, info);
/*  101 */         restoreCommonRenderParam();
/*      */       } 
/*      */       
/*  104 */       GL11.glPopMatrix();
/*      */       
/*  106 */       MCH_GuiTargetMarker.addMarkEntityPos(1, (ITargetMarkerObject)entity, posX, posY + info.markerHeight, posZ);
/*  107 */       MCH_ClientLightWeaponTickHandler.markEntity((Entity)entity, posX, posY, posZ);
/*      */       
/*  109 */       renderEntityMarker((Entity)t);
/*      */       
/*  111 */       if (MCH_Config.TestMode.prmBool)
/*      */       {
/*  113 */         WeaponPointRenderer.renderWeaponPoints((MCH_EntityAircraft)t, info, posX, posY, posZ);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean shouldRender(T livingEntity, ICamera camera, double camX, double camY, double camZ) {
/*  121 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean shouldSkipRender(Entity entity) {
/*  126 */     if (entity instanceof MCH_IEntityCanRideAircraft) {
/*      */       
/*  128 */       MCH_IEntityCanRideAircraft e = (MCH_IEntityCanRideAircraft)entity;
/*      */       
/*  130 */       if (e.isSkipNormalRender())
/*      */       {
/*  132 */         return !renderingEntity;
/*      */       
/*      */       }
/*      */     }
/*  136 */     else if (entity.getClass().toString().indexOf("flansmod.common.driveables.EntityPlane") > 0 || entity
/*  137 */       .getClass().toString().indexOf("flansmod.common.driveables.EntityVehicle") > 0) {
/*      */       
/*  139 */       if (entity.func_184187_bx() instanceof MCH_EntitySeat)
/*      */       {
/*  141 */         return !renderingEntity;
/*      */       }
/*      */     } 
/*      */     
/*  145 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void func_76979_b(Entity entity, double x, double y, double z, float yaw, float partialTicks) {
/*  151 */     if (entity.func_90999_ad())
/*      */     {
/*  153 */       renderEntityOnFire(entity, x, y, z, partialTicks);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private void renderEntityOnFire(Entity entity, double x, double y, double z, float tick) {
/*  159 */     GL11.glDisable(2896);
/*      */ 
/*      */     
/*  162 */     TextureMap texturemap = Minecraft.func_71410_x().func_147117_R();
/*  163 */     TextureAtlasSprite textureatlassprite = texturemap.func_110572_b("minecraft:blocks/fire_layer_0");
/*  164 */     TextureAtlasSprite textureatlassprite1 = texturemap.func_110572_b("minecraft:blocks/fire_layer_1");
/*      */     
/*  166 */     GL11.glPushMatrix();
/*  167 */     GL11.glTranslatef((float)x, (float)y, (float)z);
/*      */     
/*  169 */     float f1 = entity.field_70130_N * 1.4F;
/*      */     
/*  171 */     GL11.glScalef(f1 * 2.0F, f1 * 2.0F, f1 * 2.0F);
/*      */     
/*  173 */     Tessellator tessellator = Tessellator.func_178181_a();
/*  174 */     BufferBuilder bufferbuilder = tessellator.func_178180_c();
/*  175 */     float f2 = 1.5F;
/*  176 */     float f3 = 0.0F;
/*  177 */     float f4 = entity.field_70131_O / f1;
/*      */     
/*  179 */     float f5 = (float)(entity.field_70163_u + (entity.func_174813_aQ()).field_72338_b);
/*  180 */     GL11.glRotatef(-this.field_76990_c.field_78735_i, 0.0F, 1.0F, 0.0F);
/*  181 */     GL11.glTranslatef(0.0F, 0.0F, -0.3F + (int)f4 * 0.02F);
/*  182 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*  183 */     float f6 = 0.0F;
/*  184 */     int i = 0;
/*      */     
/*  186 */     bufferbuilder.func_181668_a(7, DefaultVertexFormats.field_181707_g);
/*      */     
/*  188 */     while (f4 > 0.0F) {
/*      */ 
/*      */       
/*  191 */       TextureAtlasSprite textureatlassprite2 = (i % 2 == 0) ? textureatlassprite : textureatlassprite1;
/*  192 */       func_110776_a(TextureMap.field_110575_b);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  197 */       float f7 = textureatlassprite2.func_94209_e();
/*  198 */       float f8 = textureatlassprite2.func_94206_g();
/*  199 */       float f9 = textureatlassprite2.func_94212_f();
/*  200 */       float f10 = textureatlassprite2.func_94210_h();
/*      */       
/*  202 */       if (i / 2 % 2 == 0) {
/*      */         
/*  204 */         float f11 = f9;
/*  205 */         f9 = f7;
/*  206 */         f7 = f11;
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  213 */       bufferbuilder.func_181662_b((f2 - f3), (0.0F - f5), f6).func_187315_a(f9, f10).func_181675_d();
/*  214 */       bufferbuilder.func_181662_b((-f2 - f3), (0.0F - f5), f6).func_187315_a(f7, f10).func_181675_d();
/*  215 */       bufferbuilder.func_181662_b((-f2 - f3), (1.4F - f5), f6).func_187315_a(f7, f8).func_181675_d();
/*  216 */       bufferbuilder.func_181662_b((f2 - f3), (1.4F - f5), f6).func_187315_a(f9, f8).func_181675_d();
/*  217 */       f4 -= 0.45F;
/*  218 */       f5 -= 0.45F;
/*  219 */       f2 *= 0.9F;
/*  220 */       f6 += 0.03F;
/*  221 */       i++;
/*      */     } 
/*      */     
/*  224 */     tessellator.func_78381_a();
/*  225 */     GL11.glPopMatrix();
/*  226 */     GL11.glEnable(2896);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void renderLight(double x, double y, double z, float tickTime, MCH_EntityAircraft ac, MCH_AircraftInfo info) {
/*  232 */     if (!ac.haveSearchLight()) {
/*      */       return;
/*      */     }
/*  235 */     if (!ac.isSearchLightON()) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/*  240 */     Entity entity = ac.getEntityBySeatId(1);
/*      */     
/*  242 */     if (entity != null) {
/*      */       
/*  244 */       ac.lastSearchLightYaw = entity.field_70177_z;
/*  245 */       ac.lastSearchLightPitch = entity.field_70125_A;
/*      */     }
/*      */     else {
/*      */       
/*  249 */       entity = ac.getEntityBySeatId(0);
/*      */       
/*  251 */       if (entity != null) {
/*      */         
/*  253 */         ac.lastSearchLightYaw = entity.field_70177_z;
/*  254 */         ac.lastSearchLightPitch = entity.field_70125_A;
/*      */       } 
/*      */     } 
/*      */     
/*  258 */     float yaw = ac.lastSearchLightYaw;
/*  259 */     float pitch = ac.lastSearchLightPitch;
/*      */     
/*  261 */     RenderHelper.func_74518_a();
/*  262 */     GL11.glDisable(3553);
/*  263 */     GL11.glShadeModel(7425);
/*  264 */     GL11.glEnable(3042);
/*  265 */     GL11.glBlendFunc(770, 1);
/*  266 */     GL11.glDisable(3008);
/*  267 */     GL11.glDisable(2884);
/*  268 */     GL11.glDepthMask(false);
/*      */     
/*  270 */     float rot = ac.prevRotYawWheel + (ac.rotYawWheel - ac.prevRotYawWheel) * tickTime;
/*      */     
/*  272 */     for (MCH_AircraftInfo.SearchLight sl : info.searchLights) {
/*      */       
/*  274 */       GL11.glPushMatrix();
/*  275 */       GL11.glTranslated(sl.pos.field_72450_a, sl.pos.field_72448_b, sl.pos.field_72449_c);
/*      */       
/*  277 */       if (!sl.fixDir) {
/*      */         
/*  279 */         GL11.glRotatef(yaw - ac.getRotYaw() + sl.yaw, 0.0F, -1.0F, 0.0F);
/*  280 */         GL11.glRotatef(pitch + 90.0F - ac.getRotPitch() + sl.pitch, 1.0F, 0.0F, 0.0F);
/*      */       }
/*      */       else {
/*      */         
/*  284 */         float stRot = 0.0F;
/*      */         
/*  286 */         if (sl.steering)
/*      */         {
/*  288 */           stRot = -rot * sl.stRot;
/*      */         }
/*  290 */         GL11.glRotatef(0.0F + sl.yaw + stRot, 0.0F, -1.0F, 0.0F);
/*  291 */         GL11.glRotatef(90.0F + sl.pitch, 1.0F, 0.0F, 0.0F);
/*      */       } 
/*      */       
/*  294 */       float height = sl.height;
/*  295 */       float width = sl.width / 2.0F;
/*  296 */       Tessellator tessellator = Tessellator.func_178181_a();
/*  297 */       BufferBuilder builder = tessellator.func_178180_c();
/*      */       
/*  299 */       builder.func_181668_a(6, DefaultVertexFormats.field_181706_f);
/*      */ 
/*      */       
/*  302 */       MCH_ColorInt cs = new MCH_ColorInt(sl.colorStart);
/*  303 */       MCH_ColorInt ce = new MCH_ColorInt(sl.colorEnd);
/*  304 */       builder.func_181662_b(0.0D, 0.0D, 0.0D).func_181669_b(cs.r, cs.g, cs.b, cs.a).func_181675_d();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  309 */       for (int i = 0; i < 25; i++) {
/*      */         
/*  311 */         float angle = (float)(15.0D * i / 180.0D * Math.PI);
/*      */         
/*  313 */         builder.func_181662_b((MathHelper.func_76126_a(angle) * width), height, (MathHelper.func_76134_b(angle) * width))
/*  314 */           .func_181669_b(ce.r, ce.g, ce.b, ce.a).func_181675_d();
/*      */       } 
/*      */       
/*  317 */       tessellator.func_78381_a();
/*  318 */       GL11.glPopMatrix();
/*      */     } 
/*      */     
/*  321 */     GL11.glDepthMask(true);
/*      */     
/*  323 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*  324 */     GL11.glEnable(3553);
/*  325 */     GL11.glEnable(3008);
/*  326 */     GL11.glBlendFunc(770, 771);
/*  327 */     RenderHelper.func_74519_b();
/*      */   }
/*      */ 
/*      */   
/*      */   protected void bindTexture(String path, MCH_EntityAircraft ac) {
/*  332 */     if (ac == MCH_ClientCommonTickHandler.ridingAircraft) {
/*      */       
/*  334 */       int bk = MCH_ClientCommonTickHandler.cameraMode;
/*  335 */       MCH_ClientCommonTickHandler.cameraMode = 0;
/*      */       
/*  337 */       func_110776_a(MCH_Utils.suffix(path));
/*  338 */       MCH_ClientCommonTickHandler.cameraMode = bk;
/*      */     
/*      */     }
/*      */     else {
/*      */       
/*  343 */       func_110776_a(MCH_Utils.suffix(path));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void renderRiddenEntity(MCH_EntityAircraft ac, float tickTime, float yaw, float pitch, float roll, float width, float height) {
/*  350 */     MCH_ClientEventHook.setCancelRender(false);
/*  351 */     GL11.glPushMatrix();
/*      */ 
/*      */     
/*  354 */     renderEntitySimple(ac, ac.getRiddenByEntity(), tickTime, yaw, pitch, roll, width, height);
/*      */     
/*  356 */     for (MCH_EntitySeat s : ac.getSeats()) {
/*      */       
/*  358 */       if (s != null)
/*      */       {
/*      */         
/*  361 */         renderEntitySimple(ac, s.getRiddenByEntity(), tickTime, yaw, pitch, roll, width, height);
/*      */       }
/*      */     } 
/*      */     
/*  365 */     GL11.glPopMatrix();
/*  366 */     MCH_ClientEventHook.setCancelRender(true);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void renderEntitySimple(MCH_EntityAircraft ac, Entity entity, float tickTime, float yaw, float pitch, float roll, float width, float height) {
/*  372 */     if (entity != null) {
/*      */       
/*  374 */       boolean isPilot = ac.isPilot(entity);
/*  375 */       boolean isClientPlayer = W_Lib.isClientPlayer(entity);
/*      */       
/*  377 */       if (!isClientPlayer || !W_Lib.isFirstPerson() || (isClientPlayer && isPilot && ac.getCameraId() > 0)) {
/*      */         
/*  379 */         GL11.glPushMatrix();
/*      */         
/*  381 */         if (entity.field_70173_aa == 0) {
/*      */           
/*  383 */           entity.field_70142_S = entity.field_70165_t;
/*  384 */           entity.field_70137_T = entity.field_70163_u;
/*  385 */           entity.field_70136_U = entity.field_70161_v;
/*      */         } 
/*      */         
/*  388 */         double x = entity.field_70142_S + (entity.field_70165_t - entity.field_70142_S) * tickTime;
/*  389 */         double y = entity.field_70137_T + (entity.field_70163_u - entity.field_70137_T) * tickTime;
/*  390 */         double z = entity.field_70136_U + (entity.field_70161_v - entity.field_70136_U) * tickTime;
/*  391 */         float f1 = entity.field_70126_B + (entity.field_70177_z - entity.field_70126_B) * tickTime;
/*      */         
/*  393 */         int i = entity.func_70070_b();
/*      */         
/*  395 */         if (entity.func_70027_ad())
/*      */         {
/*  397 */           i = 15728880;
/*      */         }
/*      */         
/*  400 */         int j = i % 65536;
/*  401 */         int k = i / 65536;
/*      */         
/*  403 */         OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, j / 1.0F, k / 1.0F);
/*  404 */         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  409 */         double dx = x - TileEntityRendererDispatcher.field_147554_b;
/*  410 */         double dy = y - TileEntityRendererDispatcher.field_147555_c;
/*  411 */         double dz = z - TileEntityRendererDispatcher.field_147552_d;
/*      */         
/*  413 */         GL11.glTranslated(dx, dy, dz);
/*  414 */         GL11.glRotatef(yaw, 0.0F, -1.0F, 0.0F);
/*  415 */         GL11.glRotatef(pitch, 1.0F, 0.0F, 0.0F);
/*  416 */         GL11.glRotatef(roll, 0.0F, 0.0F, 1.0F);
/*  417 */         GL11.glScaled(width, height, width);
/*  418 */         GL11.glRotatef(-yaw, 0.0F, -1.0F, 0.0F);
/*  419 */         GL11.glTranslated(-dx, -dy, -dz);
/*      */         
/*  421 */         boolean bk = renderingEntity;
/*  422 */         renderingEntity = true;
/*  423 */         Entity ridingEntity = entity.func_184187_bx();
/*      */         
/*  425 */         if (!W_Lib.isEntityLivingBase(entity) && !(entity instanceof MCH_IEntityCanRideAircraft))
/*      */         {
/*      */           
/*  428 */           entity.func_184210_p();
/*      */         }
/*      */         
/*  431 */         EntityLivingBase entityLiving = (entity instanceof EntityLivingBase) ? (EntityLivingBase)entity : null;
/*      */ 
/*      */         
/*  434 */         float bkPitch = 0.0F;
/*  435 */         float bkPrevPitch = 0.0F;
/*      */         
/*  437 */         if (isPilot && entityLiving != null) {
/*      */           
/*  439 */           entityLiving.field_70761_aq = ac.getRotYaw();
/*  440 */           entityLiving.field_70760_ar = ac.getRotYaw();
/*      */           
/*  442 */           if (ac.getCameraId() > 0) {
/*      */             
/*  444 */             entityLiving.field_70759_as = ac.getRotYaw();
/*  445 */             entityLiving.field_70758_at = ac.getRotYaw();
/*  446 */             bkPitch = entityLiving.field_70125_A;
/*  447 */             bkPrevPitch = entityLiving.field_70127_C;
/*  448 */             entityLiving.field_70125_A = ac.getRotPitch();
/*  449 */             entityLiving.field_70127_C = ac.getRotPitch();
/*      */           } 
/*      */         } 
/*      */ 
/*      */         
/*  454 */         if (isClientPlayer) {
/*      */           
/*  456 */           Entity viewEntity = this.field_76990_c.field_78734_h;
/*  457 */           this.field_76990_c.field_78734_h = entity;
/*  458 */           W_EntityRenderer.renderEntityWithPosYaw(this.field_76990_c, entity, dx, dy, dz, f1, tickTime, false);
/*      */           
/*  460 */           this.field_76990_c.field_78734_h = viewEntity;
/*      */         }
/*      */         else {
/*      */           
/*  464 */           W_EntityRenderer.renderEntityWithPosYaw(this.field_76990_c, entity, dx, dy, dz, f1, tickTime, false);
/*      */         } 
/*      */ 
/*      */         
/*  468 */         if (isPilot && entityLiving != null)
/*      */         {
/*  470 */           if (ac.getCameraId() > 0) {
/*      */             
/*  472 */             entityLiving.field_70125_A = bkPitch;
/*  473 */             entityLiving.field_70127_C = bkPrevPitch;
/*      */           } 
/*      */         }
/*      */         
/*  477 */         entity.func_184220_m(ridingEntity);
/*      */         
/*  479 */         renderingEntity = bk;
/*      */         
/*  481 */         GL11.glPopMatrix();
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void Test_Material(int light, float a, float b, float c) {
/*  488 */     GL11.glMaterial(1032, light, setColorBuffer(a, b, c, 1.0F));
/*      */   }
/*      */ 
/*      */   
/*      */   public static void Test_Light(int light, float a, float b, float c) {
/*  493 */     GL11.glLight(16384, light, setColorBuffer(a, b, c, 1.0F));
/*  494 */     GL11.glLight(16385, light, setColorBuffer(a, b, c, 1.0F));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float calcRot(float rot, float prevRot, float tickTime) {
/*  503 */     rot = MathHelper.func_76142_g(rot);
/*  504 */     prevRot = MathHelper.func_76142_g(prevRot);
/*      */     
/*  506 */     if (rot - prevRot < -180.0F) {
/*      */       
/*  508 */       prevRot -= 360.0F;
/*      */     }
/*  510 */     else if (prevRot - rot < -180.0F) {
/*      */       
/*  512 */       prevRot += 360.0F;
/*      */     } 
/*      */     
/*  515 */     return prevRot + (rot - prevRot) * tickTime;
/*      */   }
/*      */ 
/*      */   
/*      */   public void renderDebugHitBox(MCH_EntityAircraft e, double x, double y, double z, float yaw, float pitch) {
/*  520 */     if (MCH_Config.TestMode.prmBool && debugModel != null) {
/*      */       
/*  522 */       GL11.glPushMatrix();
/*  523 */       GL11.glTranslated(x, y, z);
/*  524 */       GL11.glScalef(e.field_70130_N, e.field_70131_O, e.field_70130_N);
/*  525 */       bindTexture("textures/hit_box.png");
/*      */       
/*  527 */       debugModel.renderAll();
/*      */       
/*  529 */       GL11.glPopMatrix();
/*  530 */       GL11.glPushMatrix();
/*  531 */       GL11.glTranslated(x, y, z);
/*      */       
/*  533 */       for (MCH_BoundingBox bb : e.extraBoundingBox) {
/*      */         
/*  535 */         GL11.glPushMatrix();
/*  536 */         GL11.glTranslated(bb.rotatedOffset.field_72450_a, bb.rotatedOffset.field_72448_b, bb.rotatedOffset.field_72449_c);
/*  537 */         GL11.glPushMatrix();
/*  538 */         GL11.glScalef(bb.width, bb.height, bb.width);
/*      */         
/*  540 */         bindTexture("textures/bounding_box.png");
/*  541 */         debugModel.renderAll();
/*      */         
/*  543 */         GL11.glPopMatrix();
/*  544 */         drawHitBoxDetail(bb);
/*  545 */         GL11.glPopMatrix();
/*      */       } 
/*      */       
/*  548 */       GL11.glPopMatrix();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void drawHitBoxDetail(MCH_BoundingBox bb) {
/*  554 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*      */     
/*  556 */     float f1 = 0.080000006F;
/*  557 */     String s = String.format("%.2f", new Object[] {
/*      */           
/*  559 */           Float.valueOf(bb.damegeFactor)
/*      */         });
/*  561 */     GL11.glPushMatrix();
/*  562 */     GL11.glTranslatef(0.0F, 0.5F + (float)(bb.offsetY * 0.0D + bb.height), 0.0F);
/*  563 */     GL11.glNormal3f(0.0F, 1.0F, 0.0F);
/*  564 */     GL11.glRotatef(-this.field_76990_c.field_78735_i, 0.0F, 1.0F, 0.0F);
/*  565 */     GL11.glRotatef(this.field_76990_c.field_78732_j, 1.0F, 0.0F, 0.0F);
/*  566 */     GL11.glScalef(-f1, -f1, f1);
/*  567 */     GL11.glDisable(2896);
/*  568 */     GL11.glEnable(3042);
/*  569 */     OpenGlHelper.func_148821_a(770, 771, 1, 0);
/*  570 */     GL11.glDisable(3553);
/*      */     
/*  572 */     FontRenderer fontrenderer = func_76983_a();
/*  573 */     Tessellator tessellator = Tessellator.func_178181_a();
/*  574 */     BufferBuilder builder = tessellator.func_178180_c();
/*      */ 
/*      */     
/*  577 */     builder.func_181668_a(7, DefaultVertexFormats.field_181706_f);
/*  578 */     int i = fontrenderer.func_78256_a(s) / 2;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  584 */     builder.func_181662_b((-i - 1), -1.0D, 0.1D).func_181666_a(0.0F, 0.0F, 0.0F, 0.4F).func_181675_d();
/*  585 */     builder.func_181662_b((-i - 1), 8.0D, 0.1D).func_181666_a(0.0F, 0.0F, 0.0F, 0.4F).func_181675_d();
/*  586 */     builder.func_181662_b((i + 1), 8.0D, 0.1D).func_181666_a(0.0F, 0.0F, 0.0F, 0.4F).func_181675_d();
/*  587 */     builder.func_181662_b((i + 1), -1.0D, 0.1D).func_181666_a(0.0F, 0.0F, 0.0F, 0.4F).func_181675_d();
/*  588 */     tessellator.func_78381_a();
/*      */     
/*  590 */     GL11.glEnable(3553);
/*  591 */     GL11.glDepthMask(false);
/*      */     
/*  593 */     int color = (bb.damegeFactor > 1.0F) ? 16711680 : ((bb.damegeFactor < 1.0F) ? 65535 : 16777215);
/*  594 */     fontrenderer.func_78276_b(s, -fontrenderer.func_78256_a(s) / 2, 0, 0xC0000000 | color);
/*      */     
/*  596 */     GL11.glDepthMask(true);
/*  597 */     GL11.glEnable(2896);
/*  598 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*  599 */     GL11.glPopMatrix();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void renderDebugPilotSeat(MCH_EntityAircraft e, double x, double y, double z, float yaw, float pitch, float roll) {
/*  605 */     if (MCH_Config.TestMode.prmBool && debugModel != null) {
/*      */       
/*  607 */       GL11.glPushMatrix();
/*      */       
/*  609 */       MCH_SeatInfo seat = e.getSeatInfo(0);
/*      */       
/*  611 */       GL11.glTranslated(x, y, z);
/*  612 */       GL11.glRotatef(yaw, 0.0F, -1.0F, 0.0F);
/*  613 */       GL11.glRotatef(pitch, 1.0F, 0.0F, 0.0F);
/*  614 */       GL11.glRotatef(roll, 0.0F, 0.0F, 1.0F);
/*  615 */       GL11.glTranslated(seat.pos.field_72450_a, seat.pos.field_72448_b, seat.pos.field_72449_c);
/*  616 */       GL11.glScalef(1.0F, 1.0F, 1.0F);
/*      */       
/*  618 */       bindTexture("textures/seat_pilot.png");
/*  619 */       debugModel.renderAll();
/*      */       
/*  621 */       GL11.glPopMatrix();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void renderBody(@Nullable _IModelCustom model) {
/*  628 */     if (model != null)
/*      */     {
/*  630 */       if (model instanceof W_ModelCustom) {
/*      */         
/*  632 */         if (((W_ModelCustom)model).containsPart("$body"))
/*      */         {
/*  634 */           model.renderPart("$body");
/*      */         }
/*      */         else
/*      */         {
/*  638 */           model.renderAll();
/*      */         }
/*      */       
/*      */       } else {
/*      */         
/*  643 */         model.renderAll();
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void renderPart(@Nullable _IModelCustom model, @Nullable _IModelCustom modelBody, String partName) {
/*  652 */     if (model != null) {
/*      */       
/*  654 */       model.renderAll();
/*      */     }
/*  656 */     else if (modelBody instanceof W_ModelCustom) {
/*      */       
/*  658 */       if (((W_ModelCustom)modelBody).containsPart("$" + partName))
/*      */       {
/*  660 */         modelBody.renderPart("$" + partName);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void renderCommonPart(MCH_EntityAircraft ac, MCH_AircraftInfo info, double x, double y, double z, float tickTime) {
/*  668 */     renderRope(ac, info, x, y, z, tickTime);
/*  669 */     renderWeapon(ac, info, tickTime);
/*  670 */     renderRotPart(ac, info, tickTime);
/*  671 */     renderHatch(ac, info, tickTime);
/*  672 */     renderTrackRoller(ac, info, tickTime);
/*  673 */     renderCrawlerTrack(ac, info, tickTime);
/*  674 */     renderSteeringWheel(ac, info, tickTime);
/*  675 */     renderLightHatch(ac, info, tickTime);
/*  676 */     renderWheel(ac, info, tickTime);
/*  677 */     renderThrottle(ac, info, tickTime);
/*  678 */     renderCamera(ac, info, tickTime);
/*  679 */     renderLandingGear(ac, info, tickTime);
/*  680 */     renderWeaponBay(ac, info, tickTime);
/*  681 */     renderCanopy(ac, info, tickTime);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void renderLightHatch(MCH_EntityAircraft ac, MCH_AircraftInfo info, float tickTime) {
/*  686 */     if (info.lightHatchList.size() <= 0) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/*  691 */     float rot = ac.prevRotLightHatch + (ac.rotLightHatch - ac.prevRotLightHatch) * tickTime;
/*      */     
/*  693 */     for (MCH_AircraftInfo.Hatch t : info.lightHatchList) {
/*      */       
/*  695 */       GL11.glPushMatrix();
/*  696 */       GL11.glTranslated(t.pos.field_72450_a, t.pos.field_72448_b, t.pos.field_72449_c);
/*  697 */       GL11.glRotated((rot * t.maxRot), t.rot.field_72450_a, t.rot.field_72448_b, t.rot.field_72449_c);
/*  698 */       GL11.glTranslated(-t.pos.field_72450_a, -t.pos.field_72448_b, -t.pos.field_72449_c);
/*      */       
/*  700 */       renderPart(t.model, info.model, t.modelName);
/*      */       
/*  702 */       GL11.glPopMatrix();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void renderSteeringWheel(MCH_EntityAircraft ac, MCH_AircraftInfo info, float tickTime) {
/*  708 */     if (info.partSteeringWheel.size() <= 0) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/*  713 */     float rot = ac.prevRotYawWheel + (ac.rotYawWheel - ac.prevRotYawWheel) * tickTime;
/*      */     
/*  715 */     for (MCH_AircraftInfo.PartWheel t : info.partSteeringWheel) {
/*      */       
/*  717 */       GL11.glPushMatrix();
/*  718 */       GL11.glTranslated(t.pos.field_72450_a, t.pos.field_72448_b, t.pos.field_72449_c);
/*  719 */       GL11.glRotated((rot * t.rotDir), t.rot.field_72450_a, t.rot.field_72448_b, t.rot.field_72449_c);
/*  720 */       GL11.glTranslated(-t.pos.field_72450_a, -t.pos.field_72448_b, -t.pos.field_72449_c);
/*      */       
/*  722 */       renderPart(t.model, info.model, t.modelName);
/*      */       
/*  724 */       GL11.glPopMatrix();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void renderWheel(MCH_EntityAircraft ac, MCH_AircraftInfo info, float tickTime) {
/*  730 */     if (info.partWheel.size() <= 0) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/*  735 */     float yaw = ac.prevRotYawWheel + (ac.rotYawWheel - ac.prevRotYawWheel) * tickTime;
/*      */     
/*  737 */     for (MCH_AircraftInfo.PartWheel t : info.partWheel) {
/*      */       
/*  739 */       GL11.glPushMatrix();
/*  740 */       GL11.glTranslated(t.pos2.field_72450_a, t.pos2.field_72448_b, t.pos2.field_72449_c);
/*  741 */       GL11.glRotated((yaw * t.rotDir), t.rot.field_72450_a, t.rot.field_72448_b, t.rot.field_72449_c);
/*  742 */       GL11.glTranslated(-t.pos2.field_72450_a, -t.pos2.field_72448_b, -t.pos2.field_72449_c);
/*  743 */       GL11.glTranslated(t.pos.field_72450_a, t.pos.field_72448_b, t.pos.field_72449_c);
/*  744 */       GL11.glRotatef(ac.prevRotWheel + (ac.rotWheel - ac.prevRotWheel) * tickTime, 1.0F, 0.0F, 0.0F);
/*  745 */       GL11.glTranslated(-t.pos.field_72450_a, -t.pos.field_72448_b, -t.pos.field_72449_c);
/*      */       
/*  747 */       renderPart(t.model, info.model, t.modelName);
/*      */       
/*  749 */       GL11.glPopMatrix();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void renderRotPart(MCH_EntityAircraft ac, MCH_AircraftInfo info, float tickTime) {
/*  755 */     if (!ac.haveRotPart()) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/*  760 */     for (int i = 0; i < ac.rotPartRotation.length; i++) {
/*      */       
/*  762 */       float rot = ac.rotPartRotation[i];
/*  763 */       float prevRot = ac.prevRotPartRotation[i];
/*      */       
/*  765 */       if (prevRot > rot)
/*      */       {
/*  767 */         rot += 360.0F;
/*      */       }
/*      */       
/*  770 */       rot = MCH_Lib.smooth(rot, prevRot, tickTime);
/*      */       
/*  772 */       MCH_AircraftInfo.RotPart h = info.partRotPart.get(i);
/*      */       
/*  774 */       GL11.glPushMatrix();
/*  775 */       GL11.glTranslated(h.pos.field_72450_a, h.pos.field_72448_b, h.pos.field_72449_c);
/*  776 */       GL11.glRotatef(rot, (float)h.rot.field_72450_a, (float)h.rot.field_72448_b, (float)h.rot.field_72449_c);
/*  777 */       GL11.glTranslated(-h.pos.field_72450_a, -h.pos.field_72448_b, -h.pos.field_72449_c);
/*      */       
/*  779 */       renderPart(h.model, info.model, h.modelName);
/*      */       
/*  781 */       GL11.glPopMatrix();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void renderWeapon(MCH_EntityAircraft ac, MCH_AircraftInfo info, float tickTime) {
/*  787 */     MCH_WeaponSet beforeWs = null;
/*      */     
/*  789 */     Entity e = ac.getRiddenByEntity();
/*  790 */     int weaponIndex = 0;
/*      */     
/*  792 */     for (MCH_AircraftInfo.PartWeapon w : info.partWeapon) {
/*      */       
/*  794 */       MCH_WeaponSet ws = ac.getWeaponByName(w.name[0]);
/*      */ 
/*      */       
/*  797 */       if (ws != beforeWs) {
/*      */         
/*  799 */         weaponIndex = 0;
/*  800 */         beforeWs = ws;
/*      */       } 
/*      */       
/*  803 */       float rotYaw = 0.0F;
/*  804 */       float prevYaw = 0.0F;
/*  805 */       float rotPitch = 0.0F;
/*  806 */       float prevPitch = 0.0F;
/*      */       
/*  808 */       if (w.hideGM && W_Lib.isFirstPerson())
/*      */       {
/*  810 */         if (ws != null) {
/*      */           
/*  812 */           boolean hide = false;
/*      */           
/*  814 */           for (String s : w.name) {
/*      */             
/*  816 */             if (W_Lib.isClientPlayer(ac.getWeaponUserByWeaponName(s))) {
/*      */               
/*  818 */               hide = true;
/*      */               
/*      */               break;
/*      */             } 
/*      */           } 
/*  823 */           if (hide)
/*      */           {
/*      */             continue;
/*      */           
/*      */           }
/*      */         
/*      */         }
/*  830 */         else if (ac.isMountedEntity(MCH_Lib.getClientPlayer())) {
/*      */           continue;
/*      */         } 
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  837 */       GL11.glPushMatrix();
/*      */       
/*  839 */       if (w.turret) {
/*      */         
/*  841 */         GL11.glTranslated(info.turretPosition.field_72450_a, info.turretPosition.field_72448_b, info.turretPosition.field_72449_c);
/*  842 */         float ty = MCH_Lib.smooth(ac.getLastRiderYaw() - ac.getRotYaw(), ac.prevLastRiderYaw - ac.field_70126_B, tickTime);
/*      */         
/*  844 */         GL11.glRotatef(ty, 0.0F, -1.0F, 0.0F);
/*  845 */         GL11.glTranslated(-info.turretPosition.field_72450_a, -info.turretPosition.field_72448_b, -info.turretPosition.field_72449_c);
/*      */       } 
/*      */       
/*  848 */       GL11.glTranslated(w.pos.field_72450_a, w.pos.field_72448_b, w.pos.field_72449_c);
/*      */       
/*  850 */       if (w.yaw) {
/*      */         
/*  852 */         if (ws != null) {
/*      */           
/*  854 */           rotYaw = ws.rotationYaw - ws.defaultRotationYaw;
/*  855 */           prevYaw = ws.prevRotationYaw - ws.defaultRotationYaw;
/*      */         }
/*  857 */         else if (e != null) {
/*      */           
/*  859 */           rotYaw = e.field_70177_z - ac.getRotYaw();
/*  860 */           prevYaw = e.field_70126_B - ac.field_70126_B;
/*      */         }
/*      */         else {
/*      */           
/*  864 */           rotYaw = ac.getLastRiderYaw() - ac.field_70177_z;
/*  865 */           prevYaw = ac.prevLastRiderYaw - ac.field_70126_B;
/*      */         } 
/*      */         
/*  868 */         if (rotYaw - prevYaw > 180.0F) {
/*      */           
/*  870 */           prevYaw += 360.0F;
/*      */         }
/*  872 */         else if (rotYaw - prevYaw < -180.0F) {
/*      */           
/*  874 */           prevYaw -= 360.0F;
/*      */         } 
/*  876 */         GL11.glRotatef(prevYaw + (rotYaw - prevYaw) * tickTime, 0.0F, -1.0F, 0.0F);
/*      */       } 
/*      */       
/*  879 */       if (w.turret) {
/*      */         
/*  881 */         float ty = MCH_Lib.smooth(ac.getLastRiderYaw() - ac.getRotYaw(), ac.prevLastRiderYaw - ac.field_70126_B, tickTime);
/*      */         
/*  883 */         ty -= ws.rotationTurretYaw;
/*  884 */         GL11.glRotatef(-ty, 0.0F, -1.0F, 0.0F);
/*      */       } 
/*      */       
/*  887 */       boolean rev_sign = false;
/*      */       
/*  889 */       if (ws != null && (int)ws.defaultRotationYaw != 0) {
/*      */         
/*  891 */         float t = MathHelper.func_76142_g(ws.defaultRotationYaw);
/*  892 */         rev_sign = ((t >= 45.0F && t <= 135.0F) || (t <= -45.0F && t >= -135.0F));
/*  893 */         GL11.glRotatef(-ws.defaultRotationYaw, 0.0F, -1.0F, 0.0F);
/*      */       } 
/*      */       
/*  896 */       if (w.pitch) {
/*      */         
/*  898 */         if (ws != null) {
/*      */           
/*  900 */           rotPitch = ws.rotationPitch;
/*  901 */           prevPitch = ws.prevRotationPitch;
/*      */         }
/*  903 */         else if (e != null) {
/*      */           
/*  905 */           rotPitch = e.field_70125_A;
/*  906 */           prevPitch = e.field_70127_C;
/*      */         }
/*      */         else {
/*      */           
/*  910 */           rotPitch = ac.getLastRiderPitch();
/*  911 */           prevPitch = ac.prevLastRiderPitch;
/*      */         } 
/*      */         
/*  914 */         if (rev_sign) {
/*      */           
/*  916 */           rotPitch = -rotPitch;
/*  917 */           prevPitch = -prevPitch;
/*      */         } 
/*      */         
/*  920 */         GL11.glRotatef(prevPitch + (rotPitch - prevPitch) * tickTime, 1.0F, 0.0F, 0.0F);
/*      */       } 
/*      */       
/*  923 */       if (ws != null && w.recoilBuf != 0.0F) {
/*      */         
/*  925 */         MCH_WeaponSet.Recoil r = ws.recoilBuf[0];
/*      */         
/*  927 */         if (w.name.length > 1)
/*      */         {
/*  929 */           for (String wnm : w.name) {
/*      */             
/*  931 */             MCH_WeaponSet tws = ac.getWeaponByName(wnm);
/*      */             
/*  933 */             if (tws != null && (tws.recoilBuf[0]).recoilBuf > r.recoilBuf)
/*      */             {
/*  935 */               r = tws.recoilBuf[0];
/*      */             }
/*      */           } 
/*      */         }
/*      */         
/*  940 */         float recoilBuf = r.prevRecoilBuf + (r.recoilBuf - r.prevRecoilBuf) * tickTime;
/*  941 */         GL11.glTranslated(0.0D, 0.0D, (w.recoilBuf * recoilBuf));
/*      */       } 
/*      */       
/*  944 */       if (ws != null) {
/*      */         
/*  946 */         GL11.glRotatef(ws.defaultRotationYaw, 0.0F, -1.0F, 0.0F);
/*      */         
/*  948 */         if (w.rotBarrel) {
/*      */           
/*  950 */           float rotBrl = ws.prevRotBarrel + (ws.rotBarrel - ws.prevRotBarrel) * tickTime;
/*  951 */           GL11.glRotatef(rotBrl, (float)w.rot.field_72450_a, (float)w.rot.field_72448_b, (float)w.rot.field_72449_c);
/*      */         } 
/*      */       } 
/*      */       
/*  955 */       GL11.glTranslated(-w.pos.field_72450_a, -w.pos.field_72448_b, -w.pos.field_72449_c);
/*      */       
/*  957 */       if (!w.isMissile || !ac.isWeaponNotCooldown(ws, weaponIndex)) {
/*      */         
/*  959 */         renderPart(w.model, info.model, w.modelName);
/*      */         
/*  961 */         for (MCH_AircraftInfo.PartWeaponChild wc : w.child) {
/*      */           
/*  963 */           GL11.glPushMatrix();
/*  964 */           renderWeaponChild(ac, info, wc, ws, e, tickTime);
/*  965 */           GL11.glPopMatrix();
/*      */         } 
/*      */       } 
/*      */       
/*  969 */       GL11.glPopMatrix();
/*      */       
/*  971 */       weaponIndex++;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void renderWeaponChild(MCH_EntityAircraft ac, MCH_AircraftInfo info, MCH_AircraftInfo.PartWeaponChild w, MCH_WeaponSet ws, Entity e, float tickTime) {
/*  979 */     float rotYaw = 0.0F;
/*  980 */     float prevYaw = 0.0F;
/*  981 */     float rotPitch = 0.0F;
/*  982 */     float prevPitch = 0.0F;
/*      */     
/*  984 */     GL11.glTranslated(w.pos.field_72450_a, w.pos.field_72448_b, w.pos.field_72449_c);
/*      */     
/*  986 */     if (w.yaw) {
/*      */       
/*  988 */       if (ws != null) {
/*      */         
/*  990 */         rotYaw = ws.rotationYaw - ws.defaultRotationYaw;
/*  991 */         prevYaw = ws.prevRotationYaw - ws.defaultRotationYaw;
/*      */       }
/*  993 */       else if (e != null) {
/*      */         
/*  995 */         rotYaw = e.field_70177_z - ac.getRotYaw();
/*  996 */         prevYaw = e.field_70126_B - ac.field_70126_B;
/*      */       }
/*      */       else {
/*      */         
/* 1000 */         rotYaw = ac.getLastRiderYaw() - ac.field_70177_z;
/* 1001 */         prevYaw = ac.prevLastRiderYaw - ac.field_70126_B;
/*      */       } 
/*      */       
/* 1004 */       if (rotYaw - prevYaw > 180.0F) {
/*      */         
/* 1006 */         prevYaw += 360.0F;
/*      */       }
/* 1008 */       else if (rotYaw - prevYaw < -180.0F) {
/*      */         
/* 1010 */         prevYaw -= 360.0F;
/*      */       } 
/* 1012 */       GL11.glRotatef(prevYaw + (rotYaw - prevYaw) * tickTime, 0.0F, -1.0F, 0.0F);
/*      */     } 
/*      */     
/* 1015 */     boolean rev_sign = false;
/*      */     
/* 1017 */     if (ws != null && (int)ws.defaultRotationYaw != 0) {
/*      */       
/* 1019 */       float t = MathHelper.func_76142_g(ws.defaultRotationYaw);
/* 1020 */       rev_sign = ((t >= 45.0F && t <= 135.0F) || (t <= -45.0F && t >= -135.0F));
/*      */       
/* 1022 */       GL11.glRotatef(-ws.defaultRotationYaw, 0.0F, -1.0F, 0.0F);
/*      */     } 
/*      */     
/* 1025 */     if (w.pitch) {
/*      */       
/* 1027 */       if (ws != null) {
/*      */         
/* 1029 */         rotPitch = ws.rotationPitch;
/* 1030 */         prevPitch = ws.prevRotationPitch;
/*      */       }
/* 1032 */       else if (e != null) {
/*      */         
/* 1034 */         rotPitch = e.field_70125_A;
/* 1035 */         prevPitch = e.field_70127_C;
/*      */       }
/*      */       else {
/*      */         
/* 1039 */         rotPitch = ac.getLastRiderPitch();
/* 1040 */         prevPitch = ac.prevLastRiderPitch;
/*      */       } 
/*      */       
/* 1043 */       if (rev_sign) {
/*      */         
/* 1045 */         rotPitch = -rotPitch;
/* 1046 */         prevPitch = -prevPitch;
/*      */       } 
/*      */       
/* 1049 */       GL11.glRotatef(prevPitch + (rotPitch - prevPitch) * tickTime, 1.0F, 0.0F, 0.0F);
/*      */     } 
/*      */     
/* 1052 */     if (ws != null && w.recoilBuf != 0.0F) {
/*      */       
/* 1054 */       MCH_WeaponSet.Recoil r = ws.recoilBuf[0];
/*      */       
/* 1056 */       if (w.name.length > 1)
/*      */       {
/* 1058 */         for (String wnm : w.name) {
/*      */           
/* 1060 */           MCH_WeaponSet tws = ac.getWeaponByName(wnm);
/*      */           
/* 1062 */           if (tws != null && (tws.recoilBuf[0]).recoilBuf > r.recoilBuf)
/*      */           {
/* 1064 */             r = tws.recoilBuf[0];
/*      */           }
/*      */         } 
/*      */       }
/*      */       
/* 1069 */       float recoilBuf = r.prevRecoilBuf + (r.recoilBuf - r.prevRecoilBuf) * tickTime;
/*      */       
/* 1071 */       GL11.glTranslated(0.0D, 0.0D, (-w.recoilBuf * recoilBuf));
/*      */     } 
/*      */     
/* 1074 */     if (ws != null)
/*      */     {
/* 1076 */       GL11.glRotatef(ws.defaultRotationYaw, 0.0F, -1.0F, 0.0F);
/*      */     }
/*      */     
/* 1079 */     GL11.glTranslated(-w.pos.field_72450_a, -w.pos.field_72448_b, -w.pos.field_72449_c);
/*      */     
/* 1081 */     renderPart(w.model, info.model, w.modelName);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void renderTrackRoller(MCH_EntityAircraft ac, MCH_AircraftInfo info, float tickTime) {
/* 1086 */     if (info.partTrackRoller.size() <= 0) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/* 1091 */     float[] rot = ac.rotTrackRoller;
/* 1092 */     float[] prevRot = ac.prevRotTrackRoller;
/*      */     
/* 1094 */     for (MCH_AircraftInfo.TrackRoller t : info.partTrackRoller) {
/*      */       
/* 1096 */       GL11.glPushMatrix();
/* 1097 */       GL11.glTranslated(t.pos.field_72450_a, t.pos.field_72448_b, t.pos.field_72449_c);
/* 1098 */       GL11.glRotatef(prevRot[t.side] + (rot[t.side] - prevRot[t.side]) * tickTime, 1.0F, 0.0F, 0.0F);
/* 1099 */       GL11.glTranslated(-t.pos.field_72450_a, -t.pos.field_72448_b, -t.pos.field_72449_c);
/*      */       
/* 1101 */       renderPart(t.model, info.model, t.modelName);
/*      */       
/* 1103 */       GL11.glPopMatrix();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void renderCrawlerTrack(MCH_EntityAircraft ac, MCH_AircraftInfo info, float tickTime) {
/* 1109 */     if (info.partCrawlerTrack.size() <= 0) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/* 1114 */     int prevWidth = GL11.glGetInteger(2833);
/* 1115 */     Tessellator tessellator = Tessellator.func_178181_a();
/* 1116 */     BufferBuilder builder = tessellator.func_178180_c();
/*      */     
/* 1118 */     for (MCH_AircraftInfo.CrawlerTrack c : info.partCrawlerTrack) {
/*      */       
/* 1120 */       GL11.glPointSize(c.len * 20.0F);
/*      */       
/* 1122 */       if (MCH_Config.TestMode.prmBool) {
/*      */         
/* 1124 */         GL11.glDisable(3553);
/* 1125 */         GL11.glDisable(3042);
/*      */         
/* 1127 */         builder.func_181668_a(0, DefaultVertexFormats.field_181706_f);
/*      */         
/* 1129 */         for (int j = 0; j < c.cx.length; j++)
/*      */         {
/*      */ 
/*      */ 
/*      */           
/* 1134 */           builder.func_181662_b(c.z, c.cx[j], c.cy[j])
/* 1135 */             .func_181669_b((int)(255.0F / c.cx.length * j), 80, 255 - (int)(255.0F / c.cx.length * j), 255)
/* 1136 */             .func_181675_d();
/*      */         }
/* 1138 */         tessellator.func_78381_a();
/*      */       } 
/*      */       
/* 1141 */       GL11.glEnable(3553);
/* 1142 */       GL11.glEnable(3042);
/*      */       
/* 1144 */       int L = c.lp.size() - 1;
/* 1145 */       double rc = (ac != null) ? ac.rotCrawlerTrack[c.side] : 0.0D;
/* 1146 */       double pc = (ac != null) ? ac.prevRotCrawlerTrack[c.side] : 0.0D;
/*      */       
/* 1148 */       for (int i = 0; i < L; i++) {
/*      */         
/* 1150 */         MCH_AircraftInfo.CrawlerTrackPrm cp = c.lp.get(i);
/* 1151 */         MCH_AircraftInfo.CrawlerTrackPrm np = c.lp.get((i + 1) % L);
/* 1152 */         double x1 = cp.x;
/* 1153 */         double x2 = np.x;
/* 1154 */         double r1 = cp.r;
/* 1155 */         double y1 = cp.y;
/* 1156 */         double y2 = np.y;
/* 1157 */         double r2 = np.r;
/*      */         
/* 1159 */         if (r2 - r1 < -180.0D)
/*      */         {
/* 1161 */           r2 += 360.0D;
/*      */         }
/*      */         
/* 1164 */         if (r2 - r1 > 180.0D)
/*      */         {
/* 1166 */           r2 -= 360.0D;
/*      */         }
/*      */         
/* 1169 */         double sx = x1 + (x2 - x1) * rc;
/* 1170 */         double sy = y1 + (y2 - y1) * rc;
/* 1171 */         double sr = r1 + (r2 - r1) * rc;
/* 1172 */         double ex = x1 + (x2 - x1) * pc;
/* 1173 */         double ey = y1 + (y2 - y1) * pc;
/* 1174 */         double er = r1 + (r2 - r1) * pc;
/* 1175 */         double x = sx + (ex - sx) * pc;
/* 1176 */         double y = sy + (ey - sy) * pc;
/* 1177 */         double r = sr + (er - sr) * pc;
/*      */         
/* 1179 */         GL11.glPushMatrix();
/* 1180 */         GL11.glTranslated(0.0D, x, y);
/* 1181 */         GL11.glRotatef((float)r, -1.0F, 0.0F, 0.0F);
/*      */         
/* 1183 */         renderPart(c.model, info.model, c.modelName);
/*      */         
/* 1185 */         GL11.glPopMatrix();
/*      */       } 
/*      */     } 
/*      */     
/* 1189 */     GL11.glEnable(3042);
/* 1190 */     GL11.glPointSize(prevWidth);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void renderHatch(MCH_EntityAircraft ac, MCH_AircraftInfo info, float tickTime) {
/* 1195 */     if (!info.haveHatch() || ac.partHatch == null) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/* 1200 */     float rot = ac.getHatchRotation();
/* 1201 */     float prevRot = ac.getPrevHatchRotation();
/*      */     
/* 1203 */     for (MCH_AircraftInfo.Hatch h : info.hatchList) {
/*      */       
/* 1205 */       GL11.glPushMatrix();
/*      */       
/* 1207 */       if (h.isSlide) {
/*      */         
/* 1209 */         float r = ac.partHatch.rotation / ac.partHatch.rotationMax;
/* 1210 */         float pr = ac.partHatch.prevRotation / ac.partHatch.rotationMax;
/* 1211 */         float f = pr + (r - pr) * tickTime;
/*      */         
/* 1213 */         GL11.glTranslated(h.pos.field_72450_a * f, h.pos.field_72448_b * f, h.pos.field_72449_c * f);
/*      */       }
/*      */       else {
/*      */         
/* 1217 */         GL11.glTranslated(h.pos.field_72450_a, h.pos.field_72448_b, h.pos.field_72449_c);
/* 1218 */         GL11.glRotatef((prevRot + (rot - prevRot) * tickTime) * h.maxRotFactor, (float)h.rot.field_72450_a, (float)h.rot.field_72448_b, (float)h.rot.field_72449_c);
/*      */         
/* 1220 */         GL11.glTranslated(-h.pos.field_72450_a, -h.pos.field_72448_b, -h.pos.field_72449_c);
/*      */       } 
/*      */       
/* 1223 */       renderPart(h.model, info.model, h.modelName);
/*      */       
/* 1225 */       GL11.glPopMatrix();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void renderThrottle(MCH_EntityAircraft ac, MCH_AircraftInfo info, float tickTime) {
/* 1231 */     if (!info.havePartThrottle()) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/* 1236 */     float throttle = MCH_Lib.smooth((float)ac.getCurrentThrottle(), (float)ac.getPrevCurrentThrottle(), tickTime);
/*      */     
/* 1238 */     for (MCH_AircraftInfo.Throttle h : info.partThrottle) {
/*      */       
/* 1240 */       GL11.glPushMatrix();
/* 1241 */       GL11.glTranslated(h.pos.field_72450_a, h.pos.field_72448_b, h.pos.field_72449_c);
/* 1242 */       GL11.glRotatef(throttle * h.rot2, (float)h.rot.field_72450_a, (float)h.rot.field_72448_b, (float)h.rot.field_72449_c);
/* 1243 */       GL11.glTranslated(-h.pos.field_72450_a, -h.pos.field_72448_b, -h.pos.field_72449_c);
/* 1244 */       GL11.glTranslated(h.slide.field_72450_a * throttle, h.slide.field_72448_b * throttle, h.slide.field_72449_c * throttle);
/*      */       
/* 1246 */       renderPart(h.model, info.model, h.modelName);
/*      */       
/* 1248 */       GL11.glPopMatrix();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void renderWeaponBay(MCH_EntityAircraft ac, MCH_AircraftInfo info, float tickTime) {
/* 1254 */     for (int i = 0; i < info.partWeaponBay.size(); i++) {
/*      */       
/* 1256 */       MCH_AircraftInfo.WeaponBay w = info.partWeaponBay.get(i);
/* 1257 */       MCH_EntityAircraft.WeaponBay ws = ac.weaponBays[i];
/*      */       
/* 1259 */       GL11.glPushMatrix();
/*      */       
/* 1261 */       if (w.isSlide) {
/*      */         
/* 1263 */         float r = ws.rot / 90.0F;
/* 1264 */         float pr = ws.prevRot / 90.0F;
/* 1265 */         float f = pr + (r - pr) * tickTime;
/* 1266 */         GL11.glTranslated(w.pos.field_72450_a * f, w.pos.field_72448_b * f, w.pos.field_72449_c * f);
/*      */       }
/*      */       else {
/*      */         
/* 1270 */         GL11.glTranslated(w.pos.field_72450_a, w.pos.field_72448_b, w.pos.field_72449_c);
/* 1271 */         GL11.glRotatef((ws.prevRot + (ws.rot - ws.prevRot) * tickTime) * w.maxRotFactor, (float)w.rot.field_72450_a, (float)w.rot.field_72448_b, (float)w.rot.field_72449_c);
/*      */         
/* 1273 */         GL11.glTranslated(-w.pos.field_72450_a, -w.pos.field_72448_b, -w.pos.field_72449_c);
/*      */       } 
/*      */       
/* 1276 */       renderPart(w.model, info.model, w.modelName);
/*      */       
/* 1278 */       GL11.glPopMatrix();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void renderCamera(MCH_EntityAircraft ac, MCH_AircraftInfo info, float tickTime) {
/* 1284 */     if (!info.havePartCamera()) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/* 1289 */     float rotYaw = ac.camera.partRotationYaw;
/* 1290 */     float prevRotYaw = ac.camera.prevPartRotationYaw;
/* 1291 */     float rotPitch = ac.camera.partRotationPitch;
/* 1292 */     float prevRotPitch = ac.camera.prevPartRotationPitch;
/* 1293 */     float yaw = prevRotYaw + (rotYaw - prevRotYaw) * tickTime - ac.getRotYaw();
/* 1294 */     float pitch = prevRotPitch + (rotPitch - prevRotPitch) * tickTime - ac.getRotPitch();
/*      */     
/* 1296 */     for (MCH_AircraftInfo.Camera c : info.cameraList) {
/*      */       
/* 1298 */       GL11.glPushMatrix();
/* 1299 */       GL11.glTranslated(c.pos.field_72450_a, c.pos.field_72448_b, c.pos.field_72449_c);
/*      */       
/* 1301 */       if (c.yawSync)
/*      */       {
/* 1303 */         GL11.glRotatef(yaw, 0.0F, -1.0F, 0.0F);
/*      */       }
/* 1305 */       if (c.pitchSync)
/*      */       {
/* 1307 */         GL11.glRotatef(pitch, 1.0F, 0.0F, 0.0F);
/*      */       }
/*      */       
/* 1310 */       GL11.glTranslated(-c.pos.field_72450_a, -c.pos.field_72448_b, -c.pos.field_72449_c);
/*      */       
/* 1312 */       renderPart(c.model, info.model, c.modelName);
/*      */       
/* 1314 */       GL11.glPopMatrix();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void renderCanopy(MCH_EntityAircraft ac, MCH_AircraftInfo info, float tickTime) {
/* 1323 */     if (info.haveCanopy() && ac.partCanopy != null) {
/*      */       
/* 1325 */       float rot = ac.getCanopyRotation();
/* 1326 */       float prevRot = ac.getPrevCanopyRotation();
/*      */       
/* 1328 */       for (MCH_AircraftInfo.Canopy c : info.canopyList) {
/*      */         
/* 1330 */         GL11.glPushMatrix();
/*      */         
/* 1332 */         if (c.isSlide) {
/*      */           
/* 1334 */           float r = ac.partCanopy.rotation / ac.partCanopy.rotationMax;
/* 1335 */           float pr = ac.partCanopy.prevRotation / ac.partCanopy.rotationMax;
/* 1336 */           float f = pr + (r - pr) * tickTime;
/* 1337 */           GL11.glTranslated(c.pos.field_72450_a * f, c.pos.field_72448_b * f, c.pos.field_72449_c * f);
/*      */         }
/*      */         else {
/*      */           
/* 1341 */           GL11.glTranslated(c.pos.field_72450_a, c.pos.field_72448_b, c.pos.field_72449_c);
/* 1342 */           GL11.glRotatef((prevRot + (rot - prevRot) * tickTime) * c.maxRotFactor, (float)c.rot.field_72450_a, (float)c.rot.field_72448_b, (float)c.rot.field_72449_c);
/*      */           
/* 1344 */           GL11.glTranslated(-c.pos.field_72450_a, -c.pos.field_72448_b, -c.pos.field_72449_c);
/*      */         } 
/*      */         
/* 1347 */         renderPart(c.model, info.model, c.modelName);
/*      */         
/* 1349 */         GL11.glPopMatrix();
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void renderLandingGear(MCH_EntityAircraft ac, MCH_AircraftInfo info, float tickTime) {
/* 1361 */     if (info.haveLandingGear() && ac.partLandingGear != null) {
/*      */       
/* 1363 */       float rot = ac.getLandingGearRotation();
/* 1364 */       float prevRot = ac.getPrevLandingGearRotation();
/* 1365 */       float revR = 90.0F - rot;
/* 1366 */       float revPr = 90.0F - prevRot;
/* 1367 */       float rot1 = prevRot + (rot - prevRot) * tickTime;
/* 1368 */       float rot1Rev = revPr + (revR - revPr) * tickTime;
/* 1369 */       float rotHatch = 90.0F * MathHelper.func_76126_a(rot1 * 2.0F * 3.1415927F / 180.0F) * 3.0F;
/*      */       
/* 1371 */       if (rotHatch > 90.0F) {
/* 1372 */         rotHatch = 90.0F;
/*      */       }
/* 1374 */       for (MCH_AircraftInfo.LandingGear n : info.landingGear) {
/*      */         
/* 1376 */         GL11.glPushMatrix();
/* 1377 */         GL11.glTranslated(n.pos.field_72450_a, n.pos.field_72448_b, n.pos.field_72449_c);
/*      */         
/* 1379 */         if (!n.reverse) {
/*      */           
/* 1381 */           if (!n.hatch)
/*      */           {
/* 1383 */             GL11.glRotatef(rot1 * n.maxRotFactor, (float)n.rot.field_72450_a, (float)n.rot.field_72448_b, (float)n.rot.field_72449_c);
/*      */           }
/*      */           else
/*      */           {
/* 1387 */             GL11.glRotatef(rotHatch * n.maxRotFactor, (float)n.rot.field_72450_a, (float)n.rot.field_72448_b, (float)n.rot.field_72449_c);
/*      */           }
/*      */         
/*      */         } else {
/*      */           
/* 1392 */           GL11.glRotatef(rot1Rev * n.maxRotFactor, (float)n.rot.field_72450_a, (float)n.rot.field_72448_b, (float)n.rot.field_72449_c);
/*      */         } 
/*      */         
/* 1395 */         if (n.enableRot2)
/*      */         {
/* 1397 */           if (!n.reverse) {
/*      */             
/* 1399 */             GL11.glRotatef(rot1 * n.maxRotFactor2, (float)n.rot2.field_72450_a, (float)n.rot2.field_72448_b, (float)n.rot2.field_72449_c);
/*      */           }
/*      */           else {
/*      */             
/* 1403 */             GL11.glRotatef(rot1Rev * n.maxRotFactor2, (float)n.rot2.field_72450_a, (float)n.rot2.field_72448_b, (float)n.rot2.field_72449_c);
/*      */           } 
/*      */         }
/*      */         
/* 1407 */         GL11.glTranslated(-n.pos.field_72450_a, -n.pos.field_72448_b, -n.pos.field_72449_c);
/*      */         
/* 1409 */         if (n.slide != null) {
/*      */           
/* 1411 */           float f = rot / 90.0F;
/*      */           
/* 1413 */           if (n.reverse)
/*      */           {
/* 1415 */             f = 1.0F - f;
/*      */           }
/* 1417 */           GL11.glTranslated(f * n.slide.field_72450_a, f * n.slide.field_72448_b, f * n.slide.field_72449_c);
/*      */         } 
/*      */         
/* 1420 */         renderPart(n.model, info.model, n.modelName);
/*      */         
/* 1422 */         GL11.glPopMatrix();
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void renderEntityMarker(Entity entity) {
/* 1429 */     EntityPlayerSP entityPlayerSP = (Minecraft.func_71410_x()).field_71439_g;
/*      */     
/* 1431 */     if (entityPlayerSP == null) {
/*      */       return;
/*      */     }
/* 1434 */     if (W_Entity.isEqual((Entity)entityPlayerSP, entity)) {
/*      */       return;
/*      */     }
/* 1437 */     MCH_EntityAircraft ac = null;
/*      */     
/* 1439 */     if (entityPlayerSP.func_184187_bx() instanceof MCH_EntityAircraft) {
/*      */       
/* 1441 */       ac = (MCH_EntityAircraft)entityPlayerSP.func_184187_bx();
/*      */     }
/* 1443 */     else if (entityPlayerSP.func_184187_bx() instanceof MCH_EntitySeat) {
/*      */       
/* 1445 */       ac = ((MCH_EntitySeat)entityPlayerSP.func_184187_bx()).getParent();
/*      */     }
/* 1447 */     else if (entityPlayerSP.func_184187_bx() instanceof MCH_EntityUavStation) {
/*      */       
/* 1449 */       ac = ((MCH_EntityUavStation)entityPlayerSP.func_184187_bx()).getControlAircract();
/*      */     } 
/*      */     
/* 1452 */     if (ac == null) {
/*      */       return;
/*      */     }
/* 1455 */     if (W_Entity.isEqual((Entity)ac, entity)) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/* 1460 */     MCH_WeaponGuidanceSystem gs = ac.getCurrentWeapon((Entity)entityPlayerSP).getCurrentWeapon().getGuidanceSystem();
/*      */     
/* 1462 */     if (gs == null || !gs.canLockEntity(entity)) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/* 1467 */     RenderManager rm = Minecraft.func_71410_x().func_175598_ae();
/* 1468 */     double dist = entity.func_70068_e(rm.field_78734_h);
/*      */ 
/*      */ 
/*      */     
/* 1472 */     double x = entity.field_70165_t - TileEntityRendererDispatcher.field_147554_b;
/* 1473 */     double y = entity.field_70163_u - TileEntityRendererDispatcher.field_147555_c;
/* 1474 */     double z = entity.field_70161_v - TileEntityRendererDispatcher.field_147552_d;
/*      */     
/* 1476 */     if (dist < 10000.0D) {
/*      */ 
/*      */ 
/*      */       
/* 1480 */       GL11.glPushMatrix();
/* 1481 */       GL11.glTranslatef((float)x, (float)y + entity.field_70131_O + 0.5F, (float)z);
/* 1482 */       GL11.glNormal3f(0.0F, 1.0F, 0.0F);
/* 1483 */       GL11.glRotatef(-rm.field_78735_i, 0.0F, 1.0F, 0.0F);
/* 1484 */       GL11.glRotatef(rm.field_78732_j, 1.0F, 0.0F, 0.0F);
/* 1485 */       GL11.glScalef(-0.02666667F, -0.02666667F, 0.02666667F);
/* 1486 */       GL11.glDisable(2896);
/* 1487 */       GL11.glTranslatef(0.0F, 9.374999F, 0.0F);
/* 1488 */       GL11.glDepthMask(false);
/* 1489 */       GL11.glEnable(3042);
/* 1490 */       GL11.glBlendFunc(770, 771);
/* 1491 */       GL11.glDisable(3553);
/*      */       
/* 1493 */       int prevWidth = GL11.glGetInteger(2849);
/* 1494 */       float size = Math.max(entity.field_70130_N, entity.field_70131_O) * 20.0F;
/*      */       
/* 1496 */       if (entity instanceof MCH_EntityAircraft)
/*      */       {
/* 1498 */         size *= 2.0F;
/*      */       }
/*      */       
/* 1501 */       Tessellator tessellator = Tessellator.func_178181_a();
/* 1502 */       BufferBuilder builder = tessellator.func_178180_c();
/*      */ 
/*      */ 
/*      */       
/* 1506 */       builder.func_181668_a(2, MCH_Verts.POS_COLOR_LMAP);
/*      */       
/* 1508 */       boolean isLockEntity = gs.isLockingEntity(entity);
/*      */       
/* 1510 */       if (isLockEntity) {
/*      */         
/* 1512 */         GL11.glLineWidth(MCH_Gui.scaleFactor * 1.5F);
/*      */         
/* 1514 */         builder.func_181662_b((-size - 1.0F), 0.0D, 0.0D).func_181666_a(1.0F, 0.0F, 0.0F, 1.0F).func_187314_a(0, 240).func_181675_d();
/* 1515 */         builder.func_181662_b((-size - 1.0F), (size * 2.0F), 0.0D).func_181666_a(1.0F, 0.0F, 0.0F, 1.0F).func_187314_a(0, 240).func_181675_d();
/* 1516 */         builder.func_181662_b((size + 1.0F), (size * 2.0F), 0.0D).func_181666_a(1.0F, 0.0F, 0.0F, 1.0F).func_187314_a(0, 240).func_181675_d();
/* 1517 */         builder.func_181662_b((size + 1.0F), 0.0D, 0.0D).func_181666_a(1.0F, 0.0F, 0.0F, 1.0F).func_187314_a(0, 240).func_181675_d();
/*      */       }
/*      */       else {
/*      */         
/* 1521 */         GL11.glLineWidth(MCH_Gui.scaleFactor);
/*      */         
/* 1523 */         builder.func_181662_b((-size - 1.0F), 0.0D, 0.0D).func_181666_a(1.0F, 0.3F, 0.0F, 8.0F).func_187314_a(0, 240).func_181675_d();
/* 1524 */         builder.func_181662_b((-size - 1.0F), (size * 2.0F), 0.0D).func_181666_a(1.0F, 0.3F, 0.0F, 8.0F).func_187314_a(0, 240).func_181675_d();
/* 1525 */         builder.func_181662_b((size + 1.0F), (size * 2.0F), 0.0D).func_181666_a(1.0F, 0.3F, 0.0F, 8.0F).func_187314_a(0, 240).func_181675_d();
/* 1526 */         builder.func_181662_b((size + 1.0F), 0.0D, 0.0D).func_181666_a(1.0F, 0.3F, 0.0F, 8.0F).func_187314_a(0, 240).func_181675_d();
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1532 */       tessellator.func_78381_a();
/*      */       
/* 1534 */       GL11.glPopMatrix();
/*      */       
/* 1536 */       if (!ac.isUAV() && isLockEntity && (Minecraft.func_71410_x()).field_71474_y.field_74320_O == 0) {
/*      */         
/* 1538 */         GL11.glPushMatrix();
/*      */         
/* 1540 */         builder.func_181668_a(1, MCH_Verts.POS_COLOR_LMAP);
/* 1541 */         GL11.glLineWidth(1.0F);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1547 */         builder.func_181662_b(x, y + (entity.field_70131_O / 2.0F), z).func_181666_a(1.0F, 0.0F, 0.0F, 1.0F).func_187314_a(0, 240).func_181675_d();
/* 1548 */         builder.func_181662_b(ac.field_70142_S - TileEntityRendererDispatcher.field_147554_b, ac.field_70137_T - TileEntityRendererDispatcher.field_147555_c - 1.0D, ac.field_70136_U - TileEntityRendererDispatcher.field_147552_d)
/*      */           
/* 1550 */           .func_181666_a(1.0F, 0.0F, 0.0F, 1.0F)
/* 1551 */           .func_187314_a(0, 240).func_181675_d();
/*      */         
/* 1553 */         tessellator.func_78381_a();
/* 1554 */         GL11.glPopMatrix();
/*      */       } 
/*      */       
/* 1557 */       GL11.glLineWidth(prevWidth);
/*      */       
/* 1559 */       GL11.glEnable(3553);
/* 1560 */       GL11.glDepthMask(true);
/* 1561 */       GL11.glEnable(2896);
/* 1562 */       GL11.glDisable(3042);
/* 1563 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void renderRope(MCH_EntityAircraft ac, MCH_AircraftInfo info, double x, double y, double z, float tickTime) {
/* 1570 */     GL11.glPushMatrix();
/*      */     
/* 1572 */     Tessellator tessellator = Tessellator.func_178181_a();
/* 1573 */     BufferBuilder builder = tessellator.func_178180_c();
/*      */     
/* 1575 */     if (ac.isRepelling()) {
/*      */       
/* 1577 */       GL11.glDisable(3553);
/* 1578 */       GL11.glDisable(2896);
/*      */       
/* 1580 */       for (int i = 0; i < info.repellingHooks.size(); i++) {
/*      */ 
/*      */ 
/*      */         
/* 1584 */         builder.func_181668_a(3, DefaultVertexFormats.field_181706_f);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1590 */         builder.func_181662_b(((MCH_AircraftInfo.RepellingHook)info.repellingHooks.get(i)).pos.field_72450_a, ((MCH_AircraftInfo.RepellingHook)info.repellingHooks.get(i)).pos.field_72448_b, 
/* 1591 */             ((MCH_AircraftInfo.RepellingHook)info.repellingHooks.get(i)).pos.field_72449_c).func_181669_b(0, 0, 0, 255).func_181675_d();
/* 1592 */         builder.func_181662_b(((MCH_AircraftInfo.RepellingHook)info.repellingHooks.get(i)).pos.field_72450_a, ((MCH_AircraftInfo.RepellingHook)info.repellingHooks.get(i)).pos.field_72448_b + ac.ropesLength, 
/* 1593 */             ((MCH_AircraftInfo.RepellingHook)info.repellingHooks.get(i)).pos.field_72449_c).func_181669_b(0, 0, 0, 255).func_181675_d();
/*      */         
/* 1595 */         tessellator.func_78381_a();
/*      */       } 
/*      */       
/* 1598 */       GL11.glEnable(2896);
/* 1599 */       GL11.glEnable(3553);
/*      */     } 
/*      */     
/* 1602 */     GL11.glPopMatrix();
/*      */   }
/*      */   
/*      */   public abstract void renderAircraft(MCH_EntityAircraft paramMCH_EntityAircraft, double paramDouble1, double paramDouble2, double paramDouble3, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4);
/*      */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\aircraft\MCH_RenderAircraft.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */