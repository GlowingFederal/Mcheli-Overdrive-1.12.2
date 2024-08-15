/*     */ package mcheli.lweapon;
/*     */ 
/*     */ import java.nio.FloatBuffer;
/*     */ import java.nio.IntBuffer;
/*     */ import javax.annotation.Nullable;
/*     */ import mcheli.MCH_ClientTickHandlerBase;
/*     */ import mcheli.MCH_Config;
/*     */ import mcheli.MCH_Key;
/*     */ import mcheli.MCH_Lib;
/*     */ import mcheli.aircraft.MCH_AircraftInfo;
/*     */ import mcheli.aircraft.MCH_EntityAircraft;
/*     */ import mcheli.weapon.MCH_WeaponBase;
/*     */ import mcheli.weapon.MCH_WeaponCreator;
/*     */ import mcheli.weapon.MCH_WeaponGuidanceSystem;
/*     */ import mcheli.wrapper.W_Entity;
/*     */ import mcheli.wrapper.W_EntityPlayer;
/*     */ import mcheli.wrapper.W_McClient;
/*     */ import mcheli.wrapper.W_Network;
/*     */ import mcheli.wrapper.W_PacketBase;
/*     */ import mcheli.wrapper.W_Reflection;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.MobEffects;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import org.lwjgl.BufferUtils;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import org.lwjgl.util.glu.GLU;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MCH_ClientLightWeaponTickHandler
/*     */   extends MCH_ClientTickHandlerBase
/*     */ {
/*  43 */   private static FloatBuffer screenPos = BufferUtils.createFloatBuffer(3);
/*  44 */   private static FloatBuffer screenPosBB = BufferUtils.createFloatBuffer(3);
/*  45 */   private static FloatBuffer matModel = BufferUtils.createFloatBuffer(16);
/*  46 */   private static FloatBuffer matProjection = BufferUtils.createFloatBuffer(16);
/*  47 */   private static IntBuffer matViewport = BufferUtils.createIntBuffer(16);
/*     */   
/*     */   protected boolean isHeldItem = false;
/*     */   protected boolean isBeforeHeldItem = false;
/*  51 */   protected EntityPlayer prevThePlayer = null;
/*     */   
/*  53 */   protected ItemStack prevItemStack = ItemStack.field_190927_a;
/*     */   
/*     */   public MCH_Key KeyAttack;
/*     */   
/*     */   public MCH_Key KeyUseWeapon;
/*     */   public MCH_Key KeySwWeaponMode;
/*     */   public MCH_Key KeyZoom;
/*     */   public MCH_Key KeyCameraMode;
/*     */   public MCH_Key[] Keys;
/*     */   protected static MCH_WeaponBase weapon;
/*     */   public static int reloadCount;
/*     */   public static int lockonSoundCount;
/*     */   public static int weaponMode;
/*     */   public static int selectedZoom;
/*  67 */   public static Entity markEntity = null;
/*     */   
/*  69 */   public static Vec3d markPos = Vec3d.field_186680_a;
/*  70 */   public static MCH_WeaponGuidanceSystem gs = new MCH_WeaponGuidanceSystem();
/*  71 */   public static double lockRange = 120.0D;
/*     */ 
/*     */   
/*     */   public MCH_ClientLightWeaponTickHandler(Minecraft minecraft, MCH_Config config) {
/*  75 */     super(minecraft);
/*  76 */     updateKeybind(config);
/*     */     
/*  78 */     gs.canLockInAir = false;
/*  79 */     gs.canLockOnGround = false;
/*  80 */     gs.canLockInWater = false;
/*  81 */     gs.setLockCountMax(40);
/*  82 */     gs.lockRange = 120.0D;
/*     */     
/*  84 */     lockonSoundCount = 0;
/*  85 */     initWeaponParam((EntityPlayer)null);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void markEntity(Entity entity, double x, double y, double z) {
/*  90 */     if (gs.getLockingEntity() == entity) {
/*     */       
/*  92 */       GL11.glGetFloat(2982, matModel);
/*  93 */       GL11.glGetFloat(2983, matProjection);
/*  94 */       GL11.glGetInteger(2978, matViewport);
/*  95 */       GLU.gluProject((float)x, (float)y, (float)z, matModel, matProjection, matViewport, screenPos);
/*     */       
/*  97 */       MCH_AircraftInfo i = (entity instanceof MCH_EntityAircraft) ? ((MCH_EntityAircraft)entity).getAcInfo() : null;
/*     */       
/*  99 */       float w = (entity.field_70130_N > entity.field_70131_O) ? entity.field_70130_N : ((i != null) ? i.markerWidth : entity.field_70131_O);
/* 100 */       float h = (i != null) ? i.markerHeight : entity.field_70131_O;
/* 101 */       GLU.gluProject((float)x + w, (float)y + h, (float)z + w, matModel, matProjection, matViewport, screenPosBB);
/*     */ 
/*     */       
/* 104 */       markEntity = entity;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static Vec3d getMartEntityPos() {
/* 111 */     if (gs.getLockingEntity() == markEntity && markEntity != null)
/*     */     {
/* 113 */       return new Vec3d(screenPos.get(0), screenPos.get(1), screenPos.get(2));
/*     */     }
/* 115 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static Vec3d getMartEntityBBPos() {
/* 121 */     if (gs.getLockingEntity() == markEntity && markEntity != null)
/*     */     {
/* 123 */       return new Vec3d(screenPosBB.get(0), screenPosBB.get(1), screenPosBB.get(2));
/*     */     }
/* 125 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void initWeaponParam(EntityPlayer player) {
/* 130 */     reloadCount = 0;
/* 131 */     weaponMode = 0;
/* 132 */     selectedZoom = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateKeybind(MCH_Config config) {
/* 138 */     this.KeyAttack = new MCH_Key(MCH_Config.KeyAttack.prmInt);
/* 139 */     this.KeyUseWeapon = new MCH_Key(MCH_Config.KeyUseWeapon.prmInt);
/* 140 */     this.KeySwWeaponMode = new MCH_Key(MCH_Config.KeySwWeaponMode.prmInt);
/* 141 */     this.KeyZoom = new MCH_Key(MCH_Config.KeyZoom.prmInt);
/* 142 */     this.KeyCameraMode = new MCH_Key(MCH_Config.KeyCameraMode.prmInt);
/*     */     
/* 144 */     this.Keys = new MCH_Key[] { this.KeyAttack, this.KeyUseWeapon, this.KeySwWeaponMode, this.KeyZoom, this.KeyCameraMode };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onTick(boolean inGUI) {
/* 153 */     for (MCH_Key k : this.Keys)
/*     */     {
/* 155 */       k.update();
/*     */     }
/*     */     
/* 158 */     this.isBeforeHeldItem = this.isHeldItem;
/*     */     
/* 160 */     EntityPlayerSP entityPlayerSP = this.mc.field_71439_g;
/*     */     
/* 162 */     if (this.prevThePlayer == null || this.prevThePlayer != entityPlayerSP) {
/*     */       
/* 164 */       initWeaponParam((EntityPlayer)entityPlayerSP);
/* 165 */       this.prevThePlayer = (EntityPlayer)entityPlayerSP;
/*     */     } 
/*     */ 
/*     */     
/* 169 */     ItemStack is = (entityPlayerSP != null) ? entityPlayerSP.func_184614_ca() : ItemStack.field_190927_a;
/*     */     
/* 171 */     if (entityPlayerSP == null || entityPlayerSP.func_184187_bx() instanceof mcheli.gltd.MCH_EntityGLTD || entityPlayerSP
/* 172 */       .func_184187_bx() instanceof MCH_EntityAircraft)
/*     */     {
/*     */       
/* 175 */       is = ItemStack.field_190927_a;
/*     */     }
/*     */     
/* 178 */     if (gs.getLockingEntity() == null)
/*     */     {
/* 180 */       markEntity = null;
/*     */     }
/*     */ 
/*     */     
/* 184 */     if (!is.func_190926_b() && is.func_77973_b() instanceof MCH_ItemLightWeaponBase) {
/*     */       
/* 186 */       MCH_ItemLightWeaponBase lweapon = (MCH_ItemLightWeaponBase)is.func_77973_b();
/*     */ 
/*     */ 
/*     */       
/* 190 */       if (this.prevItemStack.func_190926_b() || (!this.prevItemStack.func_77969_a(is) && 
/* 191 */         !this.prevItemStack.func_77977_a().equals(is.func_77977_a()))) {
/*     */ 
/*     */         
/* 194 */         initWeaponParam((EntityPlayer)entityPlayerSP);
/*     */ 
/*     */         
/* 197 */         weapon = MCH_WeaponCreator.createWeapon(((EntityPlayer)entityPlayerSP).field_70170_p, MCH_ItemLightWeaponBase.getName(is), Vec3d.field_186680_a, 0.0F, 0.0F, null, false);
/*     */ 
/*     */         
/* 200 */         if (weapon != null && weapon.getInfo() != null && weapon.getGuidanceSystem() != null)
/*     */         {
/* 202 */           gs = weapon.getGuidanceSystem();
/*     */         }
/*     */       } 
/*     */       
/* 206 */       if (weapon == null || gs == null) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 211 */       gs.setWorld(((EntityPlayer)entityPlayerSP).field_70170_p);
/* 212 */       gs.lockRange = lockRange;
/*     */ 
/*     */       
/* 215 */       if (entityPlayerSP.func_184612_cw() > 10) {
/*     */         
/* 217 */         selectedZoom %= (weapon.getInfo()).zoom.length;
/* 218 */         W_Reflection.setCameraZoom((weapon.getInfo()).zoom[selectedZoom]);
/*     */       }
/*     */       else {
/*     */         
/* 222 */         W_Reflection.restoreCameraZoom();
/*     */       } 
/*     */       
/* 225 */       if (is.func_77960_j() < is.func_77958_k()) {
/*     */ 
/*     */         
/* 228 */         if (entityPlayerSP.func_184612_cw() > 10) {
/*     */           
/* 230 */           gs.lock((Entity)entityPlayerSP);
/*     */           
/* 232 */           if (gs.getLockCount() > 0)
/*     */           {
/* 234 */             if (lockonSoundCount > 0)
/*     */             {
/* 236 */               lockonSoundCount--;
/*     */             }
/*     */             else
/*     */             {
/* 240 */               lockonSoundCount = 7;
/*     */ 
/*     */               
/* 243 */               lockonSoundCount = (int)(lockonSoundCount * (1.0D - (gs.getLockCount() / gs.getLockCountMax())));
/*     */               
/* 245 */               if (lockonSoundCount < 3)
/*     */               {
/* 247 */                 lockonSoundCount = 2;
/*     */               }
/*     */               
/* 250 */               W_McClient.MOD_playSoundFX("lockon", 1.0F, 1.0F);
/*     */             }
/*     */           
/*     */           }
/*     */         } else {
/*     */           
/* 256 */           W_Reflection.restoreCameraZoom();
/* 257 */           gs.clearLock();
/*     */         } 
/*     */         
/* 260 */         reloadCount = 0;
/*     */       }
/*     */       else {
/*     */         
/* 264 */         lockonSoundCount = 0;
/* 265 */         if (W_EntityPlayer.hasItem((EntityPlayer)entityPlayerSP, (Item)lweapon.bullet) && entityPlayerSP.func_184605_cv() <= 0) {
/*     */           
/* 267 */           if (reloadCount == 10)
/*     */           {
/* 269 */             W_McClient.MOD_playSoundFX("fim92_reload", 1.0F, 1.0F);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/* 274 */           if (reloadCount < 40)
/*     */           {
/* 276 */             reloadCount++;
/*     */             
/* 278 */             if (reloadCount == 40)
/*     */             {
/* 280 */               onCompleteReload();
/*     */             }
/*     */           }
/*     */         
/*     */         } else {
/*     */           
/* 286 */           reloadCount = 0;
/*     */         } 
/*     */         
/* 289 */         gs.clearLock();
/*     */       } 
/*     */       
/* 292 */       if (!inGUI)
/*     */       {
/* 294 */         playerControl((EntityPlayer)entityPlayerSP, is, (MCH_ItemLightWeaponBase)is.func_77973_b());
/*     */       }
/* 296 */       this.isHeldItem = MCH_ItemLightWeaponBase.isHeld((EntityPlayer)entityPlayerSP);
/*     */     }
/*     */     else {
/*     */       
/* 300 */       lockonSoundCount = 0;
/* 301 */       reloadCount = 0;
/* 302 */       this.isHeldItem = false;
/*     */     } 
/*     */     
/* 305 */     if (this.isBeforeHeldItem != this.isHeldItem) {
/*     */       
/* 307 */       MCH_Lib.DbgLog(true, "LWeapon cancel", new Object[0]);
/*     */       
/* 309 */       if (!this.isHeldItem) {
/*     */         
/* 311 */         if (getPotionNightVisionDuration((EntityPlayer)entityPlayerSP) < 250) {
/*     */           
/* 313 */           MCH_PacketLightWeaponPlayerControl pc = new MCH_PacketLightWeaponPlayerControl();
/* 314 */           pc.camMode = 1;
/* 315 */           W_Network.sendToServer((W_PacketBase)pc);
/*     */ 
/*     */           
/* 318 */           entityPlayerSP.func_184589_d(MobEffects.field_76439_r);
/*     */         } 
/* 320 */         W_Reflection.restoreCameraZoom();
/*     */       } 
/*     */     } 
/*     */     
/* 324 */     this.prevItemStack = is;
/* 325 */     gs.update();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onCompleteReload() {
/* 330 */     MCH_PacketLightWeaponPlayerControl pc = new MCH_PacketLightWeaponPlayerControl();
/* 331 */     pc.cmpReload = 1;
/* 332 */     W_Network.sendToServer((W_PacketBase)pc);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void playerControl(EntityPlayer player, ItemStack is, MCH_ItemLightWeaponBase item) {
/* 337 */     MCH_PacketLightWeaponPlayerControl pc = new MCH_PacketLightWeaponPlayerControl();
/* 338 */     boolean send = false;
/* 339 */     boolean autoShot = false;
/*     */     
/* 341 */     if (MCH_Config.LWeaponAutoFire.prmBool)
/*     */     {
/* 343 */       if (is.func_77960_j() < is.func_77958_k() && gs.isLockComplete())
/*     */       {
/* 345 */         autoShot = true;
/*     */       }
/*     */     }
/*     */     
/* 349 */     if (this.KeySwWeaponMode.isKeyDown() && weapon.numMode > 1) {
/*     */       
/* 351 */       weaponMode = (weaponMode + 1) % weapon.numMode;
/* 352 */       W_McClient.MOD_playSoundFX("pi", 0.5F, 0.9F);
/*     */     } 
/*     */     
/* 355 */     if (this.KeyAttack.isKeyPress() || autoShot) {
/*     */       
/* 357 */       boolean result = false;
/*     */       
/* 359 */       if (is.func_77960_j() < is.func_77958_k())
/*     */       {
/* 361 */         if (gs.isLockComplete()) {
/*     */           
/* 363 */           boolean canFire = true;
/*     */           
/* 365 */           if (weaponMode > 0 && gs.getTargetEntity() != null) {
/*     */             
/* 367 */             double dx = (gs.getTargetEntity()).field_70165_t - player.field_70165_t;
/* 368 */             double dz = (gs.getTargetEntity()).field_70161_v - player.field_70161_v;
/* 369 */             canFire = (Math.sqrt(dx * dx + dz * dz) >= 40.0D);
/*     */           } 
/*     */           
/* 372 */           if (canFire) {
/*     */             
/* 374 */             pc.useWeapon = true;
/* 375 */             pc.useWeaponOption1 = W_Entity.getEntityId(gs.lastLockEntity);
/* 376 */             pc.useWeaponOption2 = weaponMode;
/* 377 */             pc.useWeaponPosX = player.field_70165_t;
/*     */             
/* 379 */             pc.useWeaponPosY = player.field_70163_u + player.func_70047_e();
/* 380 */             pc.useWeaponPosZ = player.field_70161_v;
/* 381 */             gs.clearLock();
/* 382 */             send = true;
/* 383 */             result = true;
/*     */           } 
/*     */         } 
/*     */       }
/*     */       
/* 388 */       if (this.KeyAttack.isKeyDown())
/*     */       {
/*     */         
/* 391 */         if (!result && player.func_184612_cw() > 5)
/*     */         {
/* 393 */           playSoundNG();
/*     */         }
/*     */       }
/*     */     } 
/*     */     
/* 398 */     if (this.KeyZoom.isKeyDown()) {
/*     */       
/* 400 */       int prevZoom = selectedZoom;
/*     */       
/* 402 */       selectedZoom = (selectedZoom + 1) % (weapon.getInfo()).zoom.length;
/*     */       
/* 404 */       if (prevZoom != selectedZoom)
/*     */       {
/* 406 */         playSound("zoom", 0.5F, 1.0F);
/*     */       }
/*     */     } 
/*     */     
/* 410 */     if (this.KeyCameraMode.isKeyDown()) {
/*     */ 
/*     */       
/* 413 */       PotionEffect pe = player.func_70660_b(MobEffects.field_76439_r);
/*     */       
/* 415 */       MCH_Lib.DbgLog(true, "LWeapon NV %s", new Object[] { (pe != null) ? "ON->OFF" : "OFF->ON" });
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 420 */       if (pe != null) {
/*     */ 
/*     */         
/* 423 */         player.func_184589_d(MobEffects.field_76439_r);
/* 424 */         pc.camMode = 1;
/* 425 */         send = true;
/* 426 */         W_McClient.MOD_playSoundFX("pi", 0.5F, 0.9F);
/*     */ 
/*     */       
/*     */       }
/* 430 */       else if (player.func_184612_cw() > 60) {
/*     */         
/* 432 */         pc.camMode = 2;
/* 433 */         send = true;
/* 434 */         W_McClient.MOD_playSoundFX("pi", 0.5F, 0.9F);
/*     */       }
/*     */       else {
/*     */         
/* 438 */         playSoundNG();
/*     */       } 
/*     */     } 
/*     */     
/* 442 */     if (send)
/*     */     {
/* 444 */       W_Network.sendToServer((W_PacketBase)pc);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getPotionNightVisionDuration(EntityPlayer player) {
/* 451 */     PotionEffect cpe = player.func_70660_b(MobEffects.field_76439_r);
/*     */     
/* 453 */     return (player == null || cpe == null) ? 0 : cpe.func_76459_b();
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\lweapon\MCH_ClientLightWeaponTickHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */