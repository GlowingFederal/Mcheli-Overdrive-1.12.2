/*     */ package mcheli.vehicle;
/*     */ 
/*     */ import javax.annotation.Nullable;
/*     */ import mcheli.MCH_Config;
/*     */ import mcheli.MCH_Lib;
/*     */ import mcheli.aircraft.MCH_AircraftInfo;
/*     */ import mcheli.aircraft.MCH_EntityAircraft;
/*     */ import mcheli.aircraft.MCH_PacketStatusRequest;
/*     */ import mcheli.weapon.MCH_WeaponParam;
/*     */ import mcheli.weapon.MCH_WeaponSet;
/*     */ import mcheli.wrapper.W_Entity;
/*     */ import mcheli.wrapper.W_Lib;
/*     */ import mcheli.wrapper.W_WorldFunc;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.MoverType;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MCH_EntityVehicle
/*     */   extends MCH_EntityAircraft
/*     */ {
/*     */   private MCH_VehicleInfo vehicleInfo;
/*     */   public boolean isUsedPlayer;
/*     */   public float lastRiderYaw;
/*     */   public float lastRiderPitch;
/*     */   public double fixPosX;
/*     */   public double fixPosY;
/*     */   public double fixPosZ;
/*     */   
/*     */   public MCH_EntityVehicle(World world) {
/*  43 */     super(world);
/*     */     
/*  45 */     this.vehicleInfo = null;
/*  46 */     this.currentSpeed = 0.07D;
/*  47 */     this.field_70156_m = true;
/*  48 */     func_70105_a(2.0F, 0.7F);
/*     */     
/*  50 */     this.field_70159_w = 0.0D;
/*  51 */     this.field_70181_x = 0.0D;
/*  52 */     this.field_70179_y = 0.0D;
/*  53 */     this.isUsedPlayer = false;
/*  54 */     this.lastRiderYaw = 0.0F;
/*  55 */     this.lastRiderPitch = 0.0F;
/*  56 */     this.weapons = createWeapon(0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getKindName() {
/*  62 */     return "vehicles";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getEntityType() {
/*  68 */     return "Vehicle";
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public MCH_VehicleInfo getVehicleInfo() {
/*  74 */     return this.vehicleInfo;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void changeType(String type) {
/*  80 */     MCH_Lib.DbgLog(this.field_70170_p, "MCH_EntityVehicle.changeType " + type + " : " + toString(), new Object[0]);
/*     */     
/*  82 */     if (!type.isEmpty())
/*     */     {
/*  84 */       this.vehicleInfo = MCH_VehicleInfoManager.get(type);
/*     */     }
/*     */     
/*  87 */     if (this.vehicleInfo == null) {
/*     */       
/*  89 */       MCH_Lib.Log((Entity)this, "##### MCH_EntityVehicle changeVehicleType() Vehicle info null %d, %s, %s", new Object[] {
/*     */             
/*  91 */             Integer.valueOf(W_Entity.getEntityId((Entity)this)), type, getEntityName()
/*     */           });
/*     */       
/*  94 */       func_70106_y();
/*     */     }
/*     */     else {
/*     */       
/*  98 */       setAcInfo(this.vehicleInfo);
/*  99 */       newSeats(getAcInfo().getNumSeatAndRack());
/* 100 */       this.weapons = createWeapon(1 + getSeatNum());
/* 101 */       initPartRotation(this.field_70177_z, this.field_70125_A);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canMountWithNearEmptyMinecart() {
/* 108 */     return MCH_Config.MountMinecartVehicle.prmBool;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_70088_a() {
/* 114 */     super.func_70088_a();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_70014_b(NBTTagCompound par1NBTTagCompound) {
/* 120 */     super.func_70014_b(par1NBTTagCompound);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_70037_a(NBTTagCompound par1NBTTagCompound) {
/* 126 */     super.func_70037_a(par1NBTTagCompound);
/*     */     
/* 128 */     if (this.vehicleInfo == null) {
/*     */       
/* 130 */       this.vehicleInfo = MCH_VehicleInfoManager.get(getTypeName());
/*     */       
/* 132 */       if (this.vehicleInfo == null) {
/*     */         
/* 134 */         MCH_Lib.Log((Entity)this, "##### MCH_EntityVehicle readEntityFromNBT() Vehicle info null %d, %s", new Object[] {
/*     */               
/* 136 */               Integer.valueOf(W_Entity.getEntityId((Entity)this)), getEntityName()
/*     */             });
/*     */         
/* 139 */         func_70106_y();
/*     */       }
/*     */       else {
/*     */         
/* 143 */         setAcInfo(this.vehicleInfo);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItem() {
/* 151 */     return (getVehicleInfo() != null) ? (Item)(getVehicleInfo()).item : null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_70106_y() {
/* 157 */     super.func_70106_y();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float getSoundVolume() {
/* 163 */     return (float)getCurrentThrottle() * 2.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float getSoundPitch() {
/* 169 */     return (float)(getCurrentThrottle() * 0.5D);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDefaultSoundName() {
/* 175 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void zoomCamera() {
/* 182 */     if (canZoom()) {
/*     */       
/* 184 */       float z = this.camera.getCameraZoom();
/* 185 */       z++;
/* 186 */       this.camera.setCameraZoom((z <= getZoomMax() + 0.01D) ? z : 1.0F);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void _updateCameraRotate(float yaw, float pitch) {
/* 192 */     this.camera.prevRotationYaw = this.camera.rotationYaw;
/* 193 */     this.camera.prevRotationPitch = this.camera.rotationPitch;
/*     */     
/* 195 */     if (pitch > 89.0F) {
/* 196 */       pitch = 89.0F;
/*     */     }
/* 198 */     if (pitch < -89.0F) {
/* 199 */       pitch = -89.0F;
/*     */     }
/* 201 */     this.camera.rotationYaw = yaw;
/* 202 */     this.camera.rotationPitch = pitch;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isCameraView(Entity entity) {
/* 208 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean useCurrentWeapon(MCH_WeaponParam prm) {
/* 214 */     if (prm.user != null) {
/*     */       
/* 216 */       MCH_WeaponSet currentWs = getCurrentWeapon(prm.user);
/*     */       
/* 218 */       if (currentWs != null) {
/*     */         
/* 220 */         MCH_AircraftInfo.Weapon w = getAcInfo().getWeaponByName((currentWs.getInfo()).name);
/*     */         
/* 222 */         if (w != null)
/*     */         {
/* 224 */           if (w.maxYaw != 0.0F && w.minYaw != 0.0F)
/*     */           {
/* 226 */             return super.useCurrentWeapon(prm);
/*     */           }
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 232 */     float breforeUseWeaponPitch = this.field_70125_A;
/* 233 */     float breforeUseWeaponYaw = this.field_70177_z;
/*     */     
/* 235 */     this.field_70125_A = prm.user.field_70125_A;
/* 236 */     this.field_70177_z = prm.user.field_70177_z;
/*     */     
/* 238 */     boolean result = super.useCurrentWeapon(prm);
/*     */     
/* 240 */     this.field_70125_A = breforeUseWeaponPitch;
/* 241 */     this.field_70177_z = breforeUseWeaponYaw;
/*     */     
/* 243 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mountWithNearEmptyMinecart() {
/* 249 */     if (!MCH_Config.FixVehicleAtPlacedPoint.prmBool)
/*     */     {
/* 251 */       super.mountWithNearEmptyMinecart();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdateAircraft() {
/* 258 */     if (this.vehicleInfo == null) {
/*     */       
/* 260 */       changeType(getTypeName());
/*     */       
/* 262 */       this.field_70169_q = this.field_70165_t;
/* 263 */       this.field_70167_r = this.field_70163_u;
/* 264 */       this.field_70166_s = this.field_70161_v;
/*     */       
/*     */       return;
/*     */     } 
/* 268 */     if (this.field_70173_aa < 200 || !MCH_Config.FixVehicleAtPlacedPoint.prmBool) {
/*     */       
/* 270 */       this.fixPosX = this.field_70165_t;
/* 271 */       this.fixPosY = this.field_70163_u;
/* 272 */       this.fixPosZ = this.field_70161_v;
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 277 */       func_184210_p();
/*     */       
/* 279 */       this.field_70159_w = 0.0D;
/* 280 */       this.field_70181_x = 0.0D;
/* 281 */       this.field_70179_y = 0.0D;
/*     */       
/* 283 */       if (this.field_70170_p.field_72995_K && this.field_70173_aa % 4 == 0)
/*     */       {
/* 285 */         this.fixPosY = this.field_70163_u;
/*     */       }
/*     */       
/* 288 */       func_70107_b((this.field_70165_t + this.fixPosX) / 2.0D, (this.field_70163_u + this.fixPosY) / 2.0D, (this.field_70161_v + this.fixPosZ) / 2.0D);
/*     */     } 
/*     */ 
/*     */     
/* 292 */     if (!this.isRequestedSyncStatus) {
/*     */       
/* 294 */       this.isRequestedSyncStatus = true;
/*     */       
/* 296 */       if (this.field_70170_p.field_72995_K)
/*     */       {
/* 298 */         MCH_PacketStatusRequest.requestStatus(this);
/*     */       }
/*     */     } 
/*     */     
/* 302 */     if (this.lastRiddenByEntity == null && getRiddenByEntity() != null) {
/*     */       
/* 304 */       (getRiddenByEntity()).field_70125_A = 0.0F;
/* 305 */       (getRiddenByEntity()).field_70127_C = 0.0F;
/* 306 */       initCurrentWeapon(getRiddenByEntity());
/*     */     } 
/*     */     
/* 309 */     updateWeapons();
/* 310 */     onUpdate_Seats();
/* 311 */     onUpdate_Control();
/*     */     
/* 313 */     this.field_70169_q = this.field_70165_t;
/* 314 */     this.field_70167_r = this.field_70163_u;
/* 315 */     this.field_70166_s = this.field_70161_v;
/*     */     
/* 317 */     if (func_70090_H())
/*     */     {
/* 319 */       this.field_70125_A *= 0.9F;
/*     */     }
/*     */     
/* 322 */     if (this.field_70170_p.field_72995_K) {
/*     */       
/* 324 */       onUpdate_Client();
/*     */     }
/*     */     else {
/*     */       
/* 328 */       onUpdate_Server();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onUpdate_Control() {
/* 336 */     if (getRiddenByEntity() != null && !(getRiddenByEntity()).field_70128_L) {
/*     */       
/* 338 */       if ((getVehicleInfo()).isEnableMove || (getVehicleInfo()).isEnableRot)
/*     */       {
/* 340 */         onUpdate_ControlOnGround();
/*     */       }
/*     */     }
/* 343 */     else if (getCurrentThrottle() > 0.0D) {
/* 344 */       addCurrentThrottle(-0.00125D);
/*     */     } else {
/*     */       
/* 347 */       setCurrentThrottle(0.0D);
/*     */     } 
/*     */     
/* 350 */     if (getCurrentThrottle() < 0.0D)
/*     */     {
/* 352 */       setCurrentThrottle(0.0D);
/*     */     }
/*     */     
/* 355 */     if (this.field_70170_p.field_72995_K) {
/*     */       
/* 357 */       if (!W_Lib.isClientPlayer(getRiddenByEntity()))
/*     */       {
/* 359 */         double ct = getThrottle();
/*     */         
/* 361 */         if (getCurrentThrottle() > ct) {
/* 362 */           addCurrentThrottle(-0.005D);
/*     */         }
/* 364 */         if (getCurrentThrottle() < ct)
/*     */         {
/* 366 */           addCurrentThrottle(0.005D);
/*     */         }
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 372 */       setThrottle(getCurrentThrottle());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onUpdate_ControlOnGround() {
/* 378 */     if (!this.field_70170_p.field_72995_K) {
/*     */       
/* 380 */       boolean move = false;
/* 381 */       float yaw = this.field_70177_z;
/* 382 */       double x = 0.0D;
/* 383 */       double z = 0.0D;
/*     */       
/* 385 */       if ((getVehicleInfo()).isEnableMove) {
/*     */         
/* 387 */         if (this.throttleUp) {
/*     */           
/* 389 */           yaw = this.field_70177_z;
/* 390 */           x += Math.sin(yaw * Math.PI / 180.0D);
/* 391 */           z += Math.cos(yaw * Math.PI / 180.0D);
/* 392 */           move = true;
/*     */         } 
/* 394 */         if (this.throttleDown) {
/*     */           
/* 396 */           yaw = this.field_70177_z - 180.0F;
/* 397 */           x += Math.sin(yaw * Math.PI / 180.0D);
/* 398 */           z += Math.cos(yaw * Math.PI / 180.0D);
/* 399 */           move = true;
/*     */         } 
/*     */       } 
/*     */       
/* 403 */       if ((getVehicleInfo()).isEnableMove) {
/*     */         
/* 405 */         if (this.moveLeft && !this.moveRight)
/*     */         {
/* 407 */           this.field_70177_z = (float)(this.field_70177_z - 0.5D);
/*     */         }
/*     */         
/* 410 */         if (this.moveRight && !this.moveLeft)
/*     */         {
/* 412 */           this.field_70177_z = (float)(this.field_70177_z + 0.5D);
/*     */         }
/*     */       } 
/*     */       
/* 416 */       if (move) {
/*     */         
/* 418 */         double d = Math.sqrt(x * x + z * z);
/*     */         
/* 420 */         this.field_70159_w -= x / d * 0.029999999329447746D;
/* 421 */         this.field_70179_y += z / d * 0.029999999329447746D;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onUpdate_Particle() {
/* 428 */     double particlePosY = this.field_70163_u;
/* 429 */     boolean b = false;
/*     */     
/*     */     int y;
/*     */     
/* 433 */     for (y = 0; y < 5 && !b; y++) {
/*     */       int x;
/* 435 */       for (x = -1; x <= 1; x++) {
/*     */         
/* 437 */         for (int z = -1; z <= 1; z++) {
/*     */           
/* 439 */           int block = W_WorldFunc.getBlockId(this.field_70170_p, (int)(this.field_70165_t + 0.5D) + x, (int)(this.field_70163_u + 0.5D) - y, (int)(this.field_70161_v + 0.5D) + z);
/*     */ 
/*     */           
/* 442 */           if (block != 0 && !b) {
/*     */             
/* 444 */             particlePosY = ((int)(this.field_70163_u + 1.0D) - y);
/* 445 */             b = true;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 450 */       for (x = -3; b && x <= 3; x++) {
/*     */         
/* 452 */         for (int z = -3; z <= 3; z++) {
/*     */           
/* 454 */           if (W_WorldFunc.isBlockWater(this.field_70170_p, (int)(this.field_70165_t + 0.5D) + x, (int)(this.field_70163_u + 0.5D) - y, (int)(this.field_70161_v + 0.5D) + z))
/*     */           {
/*     */             
/* 457 */             for (int i = 0; i < 7.0D * getCurrentThrottle(); i++)
/*     */             {
/*     */               
/* 460 */               this.field_70170_p.func_175688_a(EnumParticleTypes.WATER_SPLASH, this.field_70165_t + 0.5D + x + (this.field_70146_Z
/* 461 */                   .nextDouble() - 0.5D) * 2.0D, particlePosY + this.field_70146_Z
/* 462 */                   .nextDouble(), this.field_70161_v + 0.5D + z + (this.field_70146_Z
/* 463 */                   .nextDouble() - 0.5D) * 2.0D, x + (this.field_70146_Z
/* 464 */                   .nextDouble() - 0.5D) * 2.0D, -0.3D, z + (this.field_70146_Z
/* 465 */                   .nextDouble() - 0.5D) * 2.0D, new int[0]);
/*     */             }
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 472 */     double pn = (5 - y + 1) / 5.0D;
/*     */     
/* 474 */     if (b)
/*     */     {
/* 476 */       for (int k = 0; k < (int)(getCurrentThrottle() * 6.0D * pn); k++)
/*     */       {
/*     */ 
/*     */         
/* 480 */         this.field_70170_p.func_175688_a(EnumParticleTypes.EXPLOSION_NORMAL, this.field_70165_t + this.field_70146_Z
/* 481 */             .nextDouble() - 0.5D, particlePosY + this.field_70146_Z.nextDouble() - 0.5D, this.field_70161_v + this.field_70146_Z
/* 482 */             .nextDouble() - 0.5D, (this.field_70146_Z.nextDouble() - 0.5D) * 2.0D, -0.4D, (this.field_70146_Z
/* 483 */             .nextDouble() - 0.5D) * 2.0D, new int[0]);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onUpdate_Client() {
/* 490 */     updateCameraViewers();
/*     */ 
/*     */     
/* 493 */     if (getRiddenByEntity() != null)
/*     */     {
/* 495 */       if (W_Lib.isClientPlayer(getRiddenByEntity()))
/*     */       {
/* 497 */         (getRiddenByEntity()).field_70125_A = (getRiddenByEntity()).field_70127_C;
/*     */       }
/*     */     }
/*     */     
/* 501 */     if (this.aircraftPosRotInc > 0) {
/*     */       
/* 503 */       double rpinc = this.aircraftPosRotInc;
/* 504 */       double yaw = MathHelper.func_76138_g(this.aircraftYaw - this.field_70177_z);
/*     */       
/* 506 */       this.field_70177_z = (float)(this.field_70177_z + yaw / rpinc);
/* 507 */       this.field_70125_A = (float)(this.field_70125_A + (this.aircraftPitch - this.field_70125_A) / rpinc);
/*     */       
/* 509 */       func_70107_b(this.field_70165_t + (this.aircraftX - this.field_70165_t) / rpinc, this.field_70163_u + (this.aircraftY - this.field_70163_u) / rpinc, this.field_70161_v + (this.aircraftZ - this.field_70161_v) / rpinc);
/*     */       
/* 511 */       func_70101_b(this.field_70177_z, this.field_70125_A);
/*     */       
/* 513 */       this.aircraftPosRotInc--;
/*     */     }
/*     */     else {
/*     */       
/* 517 */       func_70107_b(this.field_70165_t + this.field_70159_w, this.field_70163_u + this.field_70181_x, this.field_70161_v + this.field_70179_y);
/*     */       
/* 519 */       if (this.field_70122_E) {
/*     */         
/* 521 */         this.field_70159_w *= 0.95D;
/* 522 */         this.field_70179_y *= 0.95D;
/*     */       } 
/*     */       
/* 525 */       if (func_70090_H()) {
/*     */         
/* 527 */         this.field_70159_w *= 0.99D;
/* 528 */         this.field_70179_y *= 0.99D;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 533 */     if (getRiddenByEntity() != null);
/*     */ 
/*     */ 
/*     */     
/* 537 */     updateCamera(this.field_70165_t, this.field_70163_u, this.field_70161_v);
/*     */   }
/*     */ 
/*     */   
/*     */   private void onUpdate_Server() {
/* 542 */     double prevMotion = Math.sqrt(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
/*     */     
/* 544 */     updateCameraViewers();
/*     */     
/* 546 */     double dp = 0.0D;
/*     */     
/* 548 */     if (canFloatWater())
/*     */     {
/* 550 */       dp = getWaterDepth();
/*     */     }
/*     */     
/* 553 */     if (dp == 0.0D) {
/*     */       
/* 555 */       this.field_70181_x += (!func_70090_H() ? (getAcInfo()).gravity : (getAcInfo()).gravityInWater);
/*     */     }
/* 557 */     else if (dp < 1.0D) {
/*     */       
/* 559 */       this.field_70181_x -= 1.0E-4D;
/* 560 */       this.field_70181_x += 0.007D * getCurrentThrottle();
/*     */     }
/*     */     else {
/*     */       
/* 564 */       if (this.field_70181_x < 0.0D)
/*     */       {
/* 566 */         this.field_70181_x /= 2.0D;
/*     */       }
/*     */       
/* 569 */       this.field_70181_x += 0.007D;
/*     */     } 
/*     */     
/* 572 */     double motion = Math.sqrt(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
/* 573 */     float speedLimit = (getAcInfo()).speed;
/*     */     
/* 575 */     if (motion > speedLimit) {
/*     */       
/* 577 */       this.field_70159_w *= speedLimit / motion;
/* 578 */       this.field_70179_y *= speedLimit / motion;
/* 579 */       motion = speedLimit;
/*     */     } 
/*     */     
/* 582 */     if (motion > prevMotion && this.currentSpeed < speedLimit) {
/*     */       
/* 584 */       this.currentSpeed += (speedLimit - this.currentSpeed) / 35.0D;
/*     */       
/* 586 */       if (this.currentSpeed > speedLimit)
/*     */       {
/* 588 */         this.currentSpeed = speedLimit;
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 593 */       this.currentSpeed -= (this.currentSpeed - 0.07D) / 35.0D;
/*     */       
/* 595 */       if (this.currentSpeed < 0.07D)
/*     */       {
/* 597 */         this.currentSpeed = 0.07D;
/*     */       }
/*     */     } 
/*     */     
/* 601 */     if (this.field_70122_E) {
/*     */       
/* 603 */       this.field_70159_w *= 0.5D;
/* 604 */       this.field_70179_y *= 0.5D;
/*     */     } 
/*     */ 
/*     */     
/* 608 */     func_70091_d(MoverType.SELF, this.field_70159_w, this.field_70181_x, this.field_70179_y);
/*     */     
/* 610 */     this.field_70181_x *= 0.95D;
/* 611 */     this.field_70159_w *= 0.99D;
/* 612 */     this.field_70179_y *= 0.99D;
/*     */     
/* 614 */     onUpdate_updateBlock();
/*     */ 
/*     */     
/* 617 */     if (getRiddenByEntity() != null && (getRiddenByEntity()).field_70128_L)
/*     */     {
/* 619 */       unmountEntity();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdateAngles(float partialTicks) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canSwitchFreeLook() {
/* 647 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\vehicle\MCH_EntityVehicle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */