/*      */ package mcheli.plane;
/*      */ 
/*      */ import javax.annotation.Nullable;
/*      */ import mcheli.MCH_Config;
/*      */ import mcheli.MCH_Lib;
/*      */ import mcheli.aircraft.MCH_AircraftInfo;
/*      */ import mcheli.aircraft.MCH_EntityAircraft;
/*      */ import mcheli.aircraft.MCH_PacketStatusRequest;
/*      */ import mcheli.aircraft.MCH_Parts;
/*      */ import mcheli.particles.MCH_ParticleParam;
/*      */ import mcheli.particles.MCH_ParticlesUtil;
/*      */ import mcheli.wrapper.W_Block;
/*      */ import mcheli.wrapper.W_Blocks;
/*      */ import mcheli.wrapper.W_Entity;
/*      */ import mcheli.wrapper.W_Lib;
/*      */ import mcheli.wrapper.W_WorldFunc;
/*      */ import net.minecraft.block.Block;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.entity.MoverType;
/*      */ import net.minecraft.entity.player.EntityPlayer;
/*      */ import net.minecraft.item.Item;
/*      */ import net.minecraft.nbt.NBTTagCompound;
/*      */ import net.minecraft.util.math.MathHelper;
/*      */ import net.minecraft.util.math.Vec3d;
/*      */ import net.minecraft.world.World;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class MCP_EntityPlane
/*      */   extends MCH_EntityAircraft
/*      */ {
/*      */   private MCP_PlaneInfo planeInfo;
/*      */   public float soundVolume;
/*      */   public MCH_Parts partNozzle;
/*      */   public MCH_Parts partWing;
/*      */   public float rotationRotor;
/*      */   public float prevRotationRotor;
/*      */   public float addkeyRotValue;
/*      */   
/*      */   public MCP_EntityPlane(World world) {
/*   46 */     super(world);
/*      */     
/*   48 */     this.planeInfo = null;
/*   49 */     this.currentSpeed = 0.07D;
/*   50 */     this.field_70156_m = true;
/*   51 */     func_70105_a(2.0F, 0.7F);
/*      */     
/*   53 */     this.field_70159_w = 0.0D;
/*   54 */     this.field_70181_x = 0.0D;
/*   55 */     this.field_70179_y = 0.0D;
/*   56 */     this.weapons = createWeapon(0);
/*   57 */     this.soundVolume = 0.0F;
/*   58 */     this.partNozzle = null;
/*   59 */     this.partWing = null;
/*   60 */     this.field_70138_W = 0.6F;
/*   61 */     this.rotationRotor = 0.0F;
/*   62 */     this.prevRotationRotor = 0.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public String getKindName() {
/*   68 */     return "planes";
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public String getEntityType() {
/*   74 */     return "Plane";
/*      */   }
/*      */ 
/*      */   
/*      */   public MCP_PlaneInfo getPlaneInfo() {
/*   79 */     return this.planeInfo;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void changeType(String type) {
/*   85 */     MCH_Lib.DbgLog(this.field_70170_p, "MCP_EntityPlane.changeType " + type + " : " + toString(), new Object[0]);
/*      */     
/*   87 */     if (!type.isEmpty())
/*      */     {
/*   89 */       this.planeInfo = MCP_PlaneInfoManager.get(type);
/*      */     }
/*      */     
/*   92 */     if (this.planeInfo == null) {
/*      */       
/*   94 */       MCH_Lib.Log((Entity)this, "##### MCP_EntityPlane changePlaneType() Plane info null %d, %s, %s", new Object[] {
/*      */             
/*   96 */             Integer.valueOf(W_Entity.getEntityId((Entity)this)), type, getEntityName()
/*      */           });
/*      */       
/*   99 */       func_70106_y();
/*      */     }
/*      */     else {
/*      */       
/*  103 */       setAcInfo(this.planeInfo);
/*  104 */       newSeats(getAcInfo().getNumSeatAndRack());
/*      */       
/*  106 */       this.partNozzle = createNozzle(this.planeInfo);
/*  107 */       this.partWing = createWing(this.planeInfo);
/*  108 */       this.weapons = createWeapon(1 + getSeatNum());
/*      */       
/*  110 */       initPartRotation(getRotYaw(), getRotPitch());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public Item getItem() {
/*  118 */     return (getPlaneInfo() != null) ? (Item)(getPlaneInfo()).item : null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canMountWithNearEmptyMinecart() {
/*  124 */     return MCH_Config.MountMinecartPlane.prmBool;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void func_70088_a() {
/*  130 */     super.func_70088_a();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void func_70014_b(NBTTagCompound par1NBTTagCompound) {
/*  136 */     super.func_70014_b(par1NBTTagCompound);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void func_70037_a(NBTTagCompound par1NBTTagCompound) {
/*  142 */     super.func_70037_a(par1NBTTagCompound);
/*      */     
/*  144 */     if (this.planeInfo == null) {
/*      */       
/*  146 */       this.planeInfo = MCP_PlaneInfoManager.get(getTypeName());
/*      */       
/*  148 */       if (this.planeInfo == null) {
/*      */         
/*  150 */         MCH_Lib.Log((Entity)this, "##### MCP_EntityPlane readEntityFromNBT() Plane info null %d, %s", new Object[] {
/*      */               
/*  152 */               Integer.valueOf(W_Entity.getEntityId((Entity)this)), getEntityName()
/*      */             });
/*      */         
/*  155 */         func_70106_y();
/*      */       }
/*      */       else {
/*      */         
/*  159 */         setAcInfo(this.planeInfo);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void func_70106_y() {
/*  167 */     super.func_70106_y();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int getNumEjectionSeat() {
/*  173 */     if (getAcInfo() != null)
/*      */     {
/*  175 */       if ((getAcInfo()).isEnableEjectionSeat) {
/*      */         
/*  177 */         int n = getSeatNum() + 1;
/*      */         
/*  179 */         return (n <= 2) ? n : 0;
/*      */       } 
/*      */     }
/*  182 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void onInteractFirst(EntityPlayer player) {
/*  188 */     this.addkeyRotValue = 0.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canSwitchGunnerMode() {
/*  194 */     if (!super.canSwitchGunnerMode())
/*      */     {
/*  196 */       return false;
/*      */     }
/*      */     
/*  199 */     float roll = MathHelper.func_76135_e(MathHelper.func_76142_g(getRotRoll()));
/*  200 */     float pitch = MathHelper.func_76135_e(MathHelper.func_76142_g(getRotPitch()));
/*      */     
/*  202 */     if (roll > 40.0F || pitch > 40.0F)
/*      */     {
/*  204 */       return false;
/*      */     }
/*      */     
/*  207 */     return (getCurrentThrottle() > 0.6000000238418579D && MCH_Lib.getBlockIdY((Entity)this, 3, -5) == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void onUpdateAircraft() {
/*  213 */     if (this.planeInfo == null) {
/*      */       
/*  215 */       changeType(getTypeName());
/*      */       
/*  217 */       this.field_70169_q = this.field_70165_t;
/*  218 */       this.field_70167_r = this.field_70163_u;
/*  219 */       this.field_70166_s = this.field_70161_v;
/*      */       
/*      */       return;
/*      */     } 
/*  223 */     if (!this.isRequestedSyncStatus) {
/*      */       
/*  225 */       this.isRequestedSyncStatus = true;
/*      */       
/*  227 */       if (this.field_70170_p.field_72995_K)
/*      */       {
/*  229 */         MCH_PacketStatusRequest.requestStatus(this);
/*      */       }
/*      */     } 
/*      */     
/*  233 */     if (this.lastRiddenByEntity == null && getRiddenByEntity() != null)
/*      */     {
/*  235 */       initCurrentWeapon(getRiddenByEntity());
/*      */     }
/*      */     
/*  238 */     updateWeapons();
/*  239 */     onUpdate_Seats();
/*  240 */     onUpdate_Control();
/*      */     
/*  242 */     this.prevRotationRotor = this.rotationRotor;
/*  243 */     this.rotationRotor = (float)(this.rotationRotor + getCurrentThrottle() * (getAcInfo()).rotorSpeed);
/*      */     
/*  245 */     if (this.rotationRotor > 360.0F) {
/*      */       
/*  247 */       this.rotationRotor -= 360.0F;
/*  248 */       this.prevRotationRotor -= 360.0F;
/*      */     } 
/*      */     
/*  251 */     if (this.rotationRotor < 0.0F) {
/*      */       
/*  253 */       this.rotationRotor += 360.0F;
/*  254 */       this.prevRotationRotor += 360.0F;
/*      */     } 
/*      */     
/*  257 */     if (this.field_70122_E && getVtolMode() == 0 && this.planeInfo.isDefaultVtol)
/*      */     {
/*  259 */       swithVtolMode(true);
/*      */     }
/*      */     
/*  262 */     this.field_70169_q = this.field_70165_t;
/*  263 */     this.field_70167_r = this.field_70163_u;
/*  264 */     this.field_70166_s = this.field_70161_v;
/*      */     
/*  266 */     if (!isDestroyed() && isHovering())
/*      */     {
/*  268 */       if (MathHelper.func_76135_e(getRotPitch()) < 70.0F)
/*      */       {
/*  270 */         setRotPitch(getRotPitch() * 0.95F, "isHovering()");
/*      */       }
/*      */     }
/*      */     
/*  274 */     if (isDestroyed())
/*      */     {
/*  276 */       if (getCurrentThrottle() > 0.0D) {
/*      */         
/*  278 */         if (MCH_Lib.getBlockIdY((Entity)this, 3, -2) > 0)
/*      */         {
/*  280 */           setCurrentThrottle(getCurrentThrottle() * 0.8D);
/*      */         }
/*      */         
/*  283 */         if (isExploded())
/*      */         {
/*  285 */           setCurrentThrottle(getCurrentThrottle() * 0.98D);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*  290 */     updateCameraViewers();
/*      */     
/*  292 */     if (this.field_70170_p.field_72995_K) {
/*      */       
/*  294 */       onUpdate_Client();
/*      */     }
/*      */     else {
/*      */       
/*  298 */       onUpdate_Server();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canUpdateYaw(Entity player) {
/*  305 */     return (super.canUpdateYaw(player) && !isHovering());
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canUpdatePitch(Entity player) {
/*  311 */     return (super.canUpdatePitch(player) && !isHovering());
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canUpdateRoll(Entity player) {
/*  317 */     return (super.canUpdateRoll(player) && !isHovering());
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public float getYawFactor() {
/*  323 */     float yaw = (getVtolMode() > 0) ? (getPlaneInfo()).vtolYaw : super.getYawFactor();
/*  324 */     return yaw * 0.8F;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public float getPitchFactor() {
/*  330 */     float pitch = (getVtolMode() > 0) ? (getPlaneInfo()).vtolPitch : super.getPitchFactor();
/*  331 */     return pitch * 0.8F;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public float getRollFactor() {
/*  337 */     float roll = (getVtolMode() > 0) ? (getPlaneInfo()).vtolYaw : super.getRollFactor();
/*  338 */     return roll * 0.8F;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isOverridePlayerPitch() {
/*  344 */     return (super.isOverridePlayerPitch() && !isHovering());
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isOverridePlayerYaw() {
/*  350 */     return (super.isOverridePlayerYaw() && !isHovering());
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public float getControlRotYaw(float mouseX, float mouseY, float tick) {
/*  356 */     if (MCH_Config.MouseControlFlightSimMode.prmBool) {
/*      */       
/*  358 */       rotationByKey(tick);
/*  359 */       return this.addkeyRotValue * 20.0F;
/*      */     } 
/*      */     
/*  362 */     return mouseX;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public float getControlRotPitch(float mouseX, float mouseY, float tick) {
/*  368 */     return mouseY;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public float getControlRotRoll(float mouseX, float mouseY, float tick) {
/*  374 */     if (MCH_Config.MouseControlFlightSimMode.prmBool)
/*      */     {
/*  376 */       return mouseX * 2.0F;
/*      */     }
/*      */     
/*  379 */     if (getVtolMode() == 0)
/*      */     {
/*  381 */       return mouseX * 0.5F;
/*      */     }
/*      */     
/*  384 */     return mouseX;
/*      */   }
/*      */ 
/*      */   
/*      */   private void rotationByKey(float partialTicks) {
/*  389 */     float rot = 0.2F;
/*      */     
/*  391 */     if (!MCH_Config.MouseControlFlightSimMode.prmBool)
/*      */     {
/*  393 */       if (getVtolMode() != 0)
/*      */       {
/*  395 */         rot *= 0.0F;
/*      */       }
/*      */     }
/*      */     
/*  399 */     if (this.moveLeft && !this.moveRight)
/*      */     {
/*  401 */       this.addkeyRotValue -= rot * partialTicks;
/*      */     }
/*      */     
/*  404 */     if (this.moveRight && !this.moveLeft)
/*      */     {
/*  406 */       this.addkeyRotValue += rot * partialTicks;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void onUpdateAngles(float partialTicks) {
/*  413 */     if (isDestroyed()) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/*  418 */     if (this.isGunnerMode) {
/*      */       
/*  420 */       setRotPitch(getRotPitch() * 0.95F);
/*  421 */       setRotYaw(getRotYaw() + (getAcInfo()).autoPilotRot * 0.2F);
/*      */       
/*  423 */       if (MathHelper.func_76135_e(getRotRoll()) > 20.0F)
/*      */       {
/*  425 */         setRotRoll(getRotRoll() * 0.95F);
/*      */       }
/*      */     } 
/*      */     
/*  429 */     boolean isFly = (MCH_Lib.getBlockIdY((Entity)this, 3, -3) == 0);
/*      */     
/*  431 */     if (!isFly || isFreeLookMode() || this.isGunnerMode || ((getAcInfo()).isFloat && getWaterDepth() > 0.0D)) {
/*      */       
/*  433 */       float gmy = 1.0F;
/*      */       
/*  435 */       if (!isFly) {
/*      */         
/*  437 */         gmy = (getAcInfo()).mobilityYawOnGround;
/*      */         
/*  439 */         if (!(getAcInfo()).canRotOnGround) {
/*      */           
/*  441 */           Block block = MCH_Lib.getBlockY((Entity)this, 3, -2, false);
/*      */           
/*  443 */           if (!W_Block.isEqual(block, W_Block.getWater()) && !W_Block.isEqual(block, W_Blocks.field_150350_a))
/*      */           {
/*  445 */             gmy = 0.0F;
/*      */           }
/*      */         } 
/*      */       } 
/*      */       
/*  450 */       if (this.moveLeft && !this.moveRight)
/*      */       {
/*  452 */         setRotYaw(getRotYaw() - 0.6F * gmy * partialTicks);
/*      */       }
/*      */       
/*  455 */       if (this.moveRight && !this.moveLeft)
/*      */       {
/*  457 */         setRotYaw(getRotYaw() + 0.6F * gmy * partialTicks);
/*      */       }
/*      */     }
/*  460 */     else if (isFly) {
/*      */       
/*  462 */       if (!MCH_Config.MouseControlFlightSimMode.prmBool) {
/*      */         
/*  464 */         rotationByKey(partialTicks);
/*      */ 
/*      */         
/*  467 */         setRotRoll(getRotRoll() + this.addkeyRotValue * 0.5F * (getAcInfo()).mobilityRoll * partialTicks * 3.3F);
/*      */       } 
/*      */     } 
/*      */     
/*  471 */     this.addkeyRotValue = (float)(this.addkeyRotValue * (1.0D - (0.1F * partialTicks)));
/*      */     
/*  473 */     if (!isFly && MathHelper.func_76135_e(getRotPitch()) < 40.0F)
/*      */     {
/*  475 */       applyOnGroundPitch(0.97F);
/*      */     }
/*      */     
/*  478 */     if (getNozzleRotation() > 0.001F) {
/*      */       
/*  480 */       float rot = 1.0F - 0.03F * partialTicks;
/*      */       
/*  482 */       setRotPitch(getRotPitch() * rot);
/*      */       
/*  484 */       rot = 1.0F - 0.1F * partialTicks;
/*      */       
/*  486 */       setRotRoll(getRotRoll() * rot);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void onUpdate_Control() {
/*  492 */     if (this.isGunnerMode && !canUseFuel())
/*      */     {
/*  494 */       switchGunnerMode(false);
/*      */     }
/*      */     
/*  497 */     this.throttleBack = (float)(this.throttleBack * 0.8D);
/*      */     
/*  499 */     if (getRiddenByEntity() != null && !(getRiddenByEntity()).field_70128_L && isCanopyClose() && canUseWing() && 
/*  500 */       canUseFuel() && !isDestroyed()) {
/*      */       
/*  502 */       onUpdate_ControlNotHovering();
/*      */     }
/*  504 */     else if (isTargetDrone() && canUseFuel() && !isDestroyed()) {
/*      */       
/*  506 */       this.throttleUp = true;
/*  507 */       onUpdate_ControlNotHovering();
/*      */     }
/*  509 */     else if (getCurrentThrottle() > 0.0D) {
/*      */       
/*  511 */       addCurrentThrottle(-0.0025D * (getAcInfo()).throttleUpDown);
/*      */     }
/*      */     else {
/*      */       
/*  515 */       setCurrentThrottle(0.0D);
/*      */     } 
/*      */     
/*  518 */     if (getCurrentThrottle() < 0.0D)
/*      */     {
/*  520 */       setCurrentThrottle(0.0D);
/*      */     }
/*      */     
/*  523 */     if (this.field_70170_p.field_72995_K) {
/*      */       
/*  525 */       if (!W_Lib.isClientPlayer(getRiddenByEntity()))
/*      */       {
/*  527 */         double ct = getThrottle();
/*      */         
/*  529 */         if (getCurrentThrottle() > ct) {
/*  530 */           addCurrentThrottle(-0.005D);
/*      */         }
/*  532 */         if (getCurrentThrottle() < ct)
/*      */         {
/*  534 */           addCurrentThrottle(0.005D);
/*      */         }
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/*  540 */       setThrottle(getCurrentThrottle());
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void onUpdate_ControlNotHovering() {
/*  546 */     if (!this.isGunnerMode) {
/*      */       
/*  548 */       float throttleUpDown = (getAcInfo()).throttleUpDown;
/*  549 */       boolean turn = ((this.moveLeft && !this.moveRight) || (!this.moveLeft && this.moveRight));
/*      */       
/*  551 */       boolean localThrottleUp = this.throttleUp;
/*      */       
/*  553 */       if (turn && getCurrentThrottle() < (getAcInfo()).pivotTurnThrottle)
/*      */       {
/*  555 */         if (!localThrottleUp && !this.throttleDown) {
/*      */           
/*  557 */           localThrottleUp = true;
/*  558 */           throttleUpDown *= 2.0F;
/*      */         } 
/*      */       }
/*      */       
/*  562 */       if (localThrottleUp) {
/*      */         
/*  564 */         float f = throttleUpDown;
/*      */         
/*  566 */         if (func_184187_bx() != null) {
/*      */           
/*  568 */           double mx = (func_184187_bx()).field_70159_w;
/*  569 */           double mz = (func_184187_bx()).field_70179_y;
/*      */           
/*  571 */           f *= MathHelper.func_76133_a(mx * mx + mz * mz) * (getAcInfo()).throttleUpDownOnEntity;
/*      */         } 
/*      */         
/*  574 */         if ((getAcInfo()).enableBack && this.throttleBack > 0.0F) {
/*      */           
/*  576 */           this.throttleBack = (float)(this.throttleBack - 0.01D * f);
/*      */         }
/*      */         else {
/*      */           
/*  580 */           this.throttleBack = 0.0F;
/*      */           
/*  582 */           if (getCurrentThrottle() < 1.0D) {
/*  583 */             addCurrentThrottle(0.01D * f);
/*      */           } else {
/*      */             
/*  586 */             setCurrentThrottle(1.0D);
/*      */           }
/*      */         
/*      */         } 
/*  590 */       } else if (this.throttleDown) {
/*      */         
/*  592 */         if (getCurrentThrottle() > 0.0D) {
/*      */           
/*  594 */           addCurrentThrottle(-0.01D * throttleUpDown);
/*      */         }
/*      */         else {
/*      */           
/*  598 */           setCurrentThrottle(0.0D);
/*      */           
/*  600 */           if ((getAcInfo()).enableBack)
/*      */           {
/*  602 */             this.throttleBack = (float)(this.throttleBack + 0.0025D * throttleUpDown);
/*      */             
/*  604 */             if (this.throttleBack > 0.6F)
/*      */             {
/*  606 */               this.throttleBack = 0.6F;
/*      */             }
/*      */           }
/*      */         
/*      */         } 
/*  611 */       } else if (this.cs_planeAutoThrottleDown) {
/*      */         
/*  613 */         if (getCurrentThrottle() > 0.0D) {
/*      */           
/*  615 */           addCurrentThrottle(-0.005D * throttleUpDown);
/*      */           
/*  617 */           if (getCurrentThrottle() <= 0.0D)
/*      */           {
/*  619 */             setCurrentThrottle(0.0D);
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void onUpdate_Particle() {
/*  628 */     if (this.field_70170_p.field_72995_K) {
/*      */       
/*  630 */       onUpdate_ParticleLandingGear();
/*  631 */       onUpdate_ParticleNozzle();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void onUpdate_Particle2() {
/*  637 */     if (!this.field_70170_p.field_72995_K) {
/*      */       return;
/*      */     }
/*  640 */     if (getHP() >= getMaxHP() * 0.5D) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/*  645 */     if (getPlaneInfo() == null) {
/*      */       return;
/*      */     }
/*  648 */     int rotorNum = (getPlaneInfo()).rotorList.size();
/*      */     
/*  650 */     if (rotorNum < 0)
/*      */     {
/*  652 */       rotorNum = 0;
/*      */     }
/*      */     
/*  655 */     if (this.isFirstDamageSmoke)
/*      */     {
/*  657 */       this.prevDamageSmokePos = new Vec3d[rotorNum + 1];
/*      */     }
/*      */     
/*  660 */     float yaw = getRotYaw();
/*  661 */     float pitch = getRotPitch();
/*  662 */     float roll = getRotRoll();
/*  663 */     boolean spawnSmoke = true;
/*      */     
/*  665 */     for (int ri = 0; ri < rotorNum; ri++) {
/*      */       
/*  667 */       if (getHP() >= getMaxHP() * 0.2D && getMaxHP() > 0) {
/*      */         
/*  669 */         int d = (int)(((getHP() / getMaxHP()) - 0.2D) / 0.3D * 15.0D);
/*      */         
/*  671 */         if (d > 0 && this.field_70146_Z.nextInt(d) > 0)
/*      */         {
/*  673 */           spawnSmoke = false;
/*      */         }
/*      */       } 
/*      */       
/*  677 */       Vec3d rotor_pos = ((MCP_PlaneInfo.Rotor)(getPlaneInfo()).rotorList.get(ri)).pos;
/*  678 */       Vec3d pos = MCH_Lib.RotVec3(rotor_pos, -yaw, -pitch, -roll);
/*  679 */       double x = this.field_70165_t + pos.field_72450_a;
/*  680 */       double y = this.field_70163_u + pos.field_72448_b;
/*  681 */       double z = this.field_70161_v + pos.field_72449_c;
/*      */       
/*  683 */       onUpdate_Particle2SpawnSmoke(ri, x, y, z, 1.0F, spawnSmoke);
/*      */     } 
/*      */     
/*  686 */     spawnSmoke = true;
/*      */     
/*  688 */     if (getHP() >= getMaxHP() * 0.2D && getMaxHP() > 0) {
/*      */       
/*  690 */       int d = (int)(((getHP() / getMaxHP()) - 0.2D) / 0.3D * 15.0D);
/*      */       
/*  692 */       if (d > 0 && this.field_70146_Z.nextInt(d) > 0)
/*      */       {
/*  694 */         spawnSmoke = false;
/*      */       }
/*      */     } 
/*      */     
/*  698 */     double px = this.field_70165_t;
/*  699 */     double py = this.field_70163_u;
/*  700 */     double pz = this.field_70161_v;
/*      */     
/*  702 */     if (getSeatInfo(0) != null && (getSeatInfo(0)).pos != null) {
/*      */       
/*  704 */       Vec3d pos = MCH_Lib.RotVec3(0.0D, (getSeatInfo(0)).pos.field_72448_b, -2.0D, -yaw, -pitch, -roll);
/*  705 */       px += pos.field_72450_a;
/*  706 */       py += pos.field_72448_b;
/*  707 */       pz += pos.field_72449_c;
/*      */     } 
/*      */     
/*  710 */     onUpdate_Particle2SpawnSmoke(rotorNum, px, py, pz, (rotorNum == 0) ? 2.0F : 1.0F, spawnSmoke);
/*      */     
/*  712 */     this.isFirstDamageSmoke = false;
/*      */   }
/*      */ 
/*      */   
/*      */   public void onUpdate_Particle2SpawnSmoke(int ri, double x, double y, double z, float size, boolean spawnSmoke) {
/*  717 */     if (this.isFirstDamageSmoke || this.prevDamageSmokePos[ri] == null)
/*      */     {
/*  719 */       this.prevDamageSmokePos[ri] = new Vec3d(x, y, z);
/*      */     }
/*      */     
/*  722 */     Vec3d prev = this.prevDamageSmokePos[ri];
/*  723 */     double dx = x - prev.field_72450_a;
/*  724 */     double dy = y - prev.field_72448_b;
/*  725 */     double dz = z - prev.field_72449_c;
/*  726 */     int num = (int)(MathHelper.func_76133_a(dx * dx + dy * dy + dz * dz) / 0.3D) + 1;
/*      */     
/*  728 */     for (int i = 0; i < num; i++) {
/*      */       
/*  730 */       float c = 0.2F + this.field_70146_Z.nextFloat() * 0.3F;
/*  731 */       MCH_ParticleParam prm = new MCH_ParticleParam(this.field_70170_p, "smoke", prev.field_72450_a + (x - prev.field_72450_a) * i / 3.0D, prev.field_72448_b + (y - prev.field_72448_b) * i / 3.0D, prev.field_72449_c + (z - prev.field_72449_c) * i / 3.0D);
/*      */ 
/*      */       
/*  734 */       prm.motionX = size * (this.field_70146_Z.nextDouble() - 0.5D) * 0.3D;
/*  735 */       prm.motionY = size * this.field_70146_Z.nextDouble() * 0.1D;
/*  736 */       prm.motionZ = size * (this.field_70146_Z.nextDouble() - 0.5D) * 0.3D;
/*  737 */       prm.size = size * (this.field_70146_Z.nextInt(5) + 5.0F) * 1.0F;
/*  738 */       prm.setColor(0.7F + this.field_70146_Z.nextFloat() * 0.1F, c, c, c);
/*      */       
/*  740 */       MCH_ParticlesUtil.spawnParticle(prm);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  746 */     this.prevDamageSmokePos[ri] = new Vec3d(x, y, z);
/*      */   }
/*      */ 
/*      */   
/*      */   public void onUpdate_ParticleLandingGear() {
/*  751 */     double d = this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y;
/*      */     
/*  753 */     if (d > 0.01D) {
/*      */       
/*  755 */       int x = MathHelper.func_76128_c(this.field_70165_t + 0.5D);
/*  756 */       int y = MathHelper.func_76128_c(this.field_70163_u - 0.5D);
/*  757 */       int z = MathHelper.func_76128_c(this.field_70161_v + 0.5D);
/*  758 */       MCH_ParticlesUtil.spawnParticleTileCrack(this.field_70170_p, x, y, z, this.field_70165_t + (this.field_70146_Z
/*      */           
/*  760 */           .nextFloat() - 0.5D) * this.field_70130_N, (func_174813_aQ()).field_72338_b + 0.1D, this.field_70161_v + (this.field_70146_Z
/*  761 */           .nextFloat() - 0.5D) * this.field_70130_N, -this.field_70159_w * 4.0D, 1.5D, -this.field_70179_y * 4.0D);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void onUpdate_ParticleSplash() {
/*  768 */     if (getAcInfo() == null) {
/*      */       return;
/*      */     }
/*  771 */     if (!this.field_70170_p.field_72995_K) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/*  776 */     double mx = this.field_70165_t - this.field_70169_q;
/*  777 */     double mz = this.field_70161_v - this.field_70166_s;
/*  778 */     double dist = mx * mx + mz * mz;
/*      */     
/*  780 */     if (dist > 1.0D) {
/*  781 */       dist = 1.0D;
/*      */     }
/*  783 */     for (MCH_AircraftInfo.ParticleSplash p : (getAcInfo()).particleSplashs) {
/*      */       
/*  785 */       for (int i = 0; i < p.num; i++) {
/*      */         
/*  787 */         if (dist > 0.03D + this.field_70146_Z.nextFloat() * 0.1D)
/*      */         {
/*  789 */           setParticleSplash(p.pos, -mx * p.acceleration, p.motionY, -mz * p.acceleration, p.gravity, p.size * (0.5D + dist * 0.5D), p.age);
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void setParticleSplash(Vec3d pos, double mx, double my, double mz, float gravity, double size, int age) {
/*  798 */     Vec3d v = getTransformedPosition(pos);
/*  799 */     v = v.func_72441_c(this.field_70146_Z.nextDouble() - 0.5D, (this.field_70146_Z.nextDouble() - 0.5D) * 0.5D, this.field_70146_Z
/*  800 */         .nextDouble() - 0.5D);
/*  801 */     int x = (int)(v.field_72450_a + 0.5D);
/*  802 */     int y = (int)(v.field_72448_b + 0.0D);
/*  803 */     int z = (int)(v.field_72449_c + 0.5D);
/*      */     
/*  805 */     if (W_WorldFunc.isBlockWater(this.field_70170_p, x, y, z)) {
/*      */       
/*  807 */       float c = this.field_70146_Z.nextFloat() * 0.3F + 0.7F;
/*  808 */       MCH_ParticleParam prm = new MCH_ParticleParam(this.field_70170_p, "smoke", v.field_72450_a, v.field_72448_b, v.field_72449_c);
/*      */       
/*  810 */       prm.motionX = mx + (this.field_70146_Z.nextFloat() - 0.5D) * 0.7D;
/*  811 */       prm.motionY = my;
/*  812 */       prm.motionZ = mz + (this.field_70146_Z.nextFloat() - 0.5D) * 0.7D;
/*  813 */       prm.size = (float)size * (this.field_70146_Z.nextFloat() * 0.2F + 0.8F);
/*  814 */       prm.setColor(0.9F, c, c, c);
/*  815 */       prm.age = age + (int)(this.field_70146_Z.nextFloat() * 0.5D * age);
/*  816 */       prm.gravity = gravity;
/*      */       
/*  818 */       MCH_ParticlesUtil.spawnParticle(prm);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void onUpdate_ParticleNozzle() {
/*  824 */     if (this.planeInfo == null || !this.planeInfo.haveNozzle()) {
/*      */       return;
/*      */     }
/*  827 */     if (getCurrentThrottle() <= 0.10000000149011612D) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/*  832 */     float yaw = getRotYaw();
/*  833 */     float pitch = getRotPitch();
/*  834 */     float roll = getRotRoll();
/*  835 */     Vec3d nozzleRot = MCH_Lib.RotVec3(0.0D, 0.0D, 1.0D, -yaw - 180.0F, pitch - getNozzleRotation(), roll);
/*      */     
/*  837 */     for (MCH_AircraftInfo.DrawnPart nozzle : this.planeInfo.nozzles) {
/*      */       
/*  839 */       if (this.field_70146_Z.nextFloat() <= getCurrentThrottle() * 1.5D) {
/*      */         
/*  841 */         Vec3d nozzlePos = MCH_Lib.RotVec3(nozzle.pos, -yaw, -pitch, -roll);
/*  842 */         double x = this.field_70165_t + nozzlePos.field_72450_a + nozzleRot.field_72450_a;
/*  843 */         double y = this.field_70163_u + nozzlePos.field_72448_b + nozzleRot.field_72448_b;
/*  844 */         double z = this.field_70161_v + nozzlePos.field_72449_c + nozzleRot.field_72449_c;
/*  845 */         float a = 0.7F;
/*      */         
/*  847 */         if (W_WorldFunc.getBlockId(this.field_70170_p, (int)(x + nozzleRot.field_72450_a * 3.0D), (int)(y + nozzleRot.field_72448_b * 3.0D), (int)(z + nozzleRot.field_72449_c * 3.0D)) != 0)
/*      */         {
/*      */           
/*  850 */           a = 2.0F;
/*      */         }
/*      */ 
/*      */ 
/*      */         
/*  855 */         MCH_ParticleParam prm = new MCH_ParticleParam(this.field_70170_p, "smoke", x, y, z, nozzleRot.field_72450_a + ((this.field_70146_Z.nextFloat() - 0.5F) * a), nozzleRot.field_72448_b, nozzleRot.field_72449_c + ((this.field_70146_Z.nextFloat() - 0.5F) * a), 5.0F * (getAcInfo()).particlesScale);
/*      */         
/*  857 */         MCH_ParticlesUtil.spawnParticle(prm);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void destroyAircraft() {
/*  865 */     super.destroyAircraft();
/*      */     
/*  867 */     int inv = 1;
/*      */     
/*  869 */     if (getRotRoll() >= 0.0F) {
/*      */       
/*  871 */       if (getRotRoll() > 90.0F)
/*      */       {
/*  873 */         inv = -1;
/*      */       }
/*      */     }
/*  876 */     else if (getRotRoll() > -90.0F) {
/*      */       
/*  878 */       inv = -1;
/*      */     } 
/*      */     
/*  881 */     this.rotDestroyedRoll = (0.5F + this.field_70146_Z.nextFloat()) * inv;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void onUpdate_Client() {
/*  886 */     if (getRiddenByEntity() != null)
/*      */     {
/*  888 */       if (W_Lib.isClientPlayer(getRiddenByEntity()))
/*      */       {
/*  890 */         (getRiddenByEntity()).field_70125_A = (getRiddenByEntity()).field_70127_C;
/*      */       }
/*      */     }
/*      */     
/*  894 */     if (this.aircraftPosRotInc > 0) {
/*      */       
/*  896 */       applyServerPositionAndRotation();
/*      */     }
/*      */     else {
/*      */       
/*  900 */       func_70107_b(this.field_70165_t + this.field_70159_w, this.field_70163_u + this.field_70181_x, this.field_70161_v + this.field_70179_y);
/*      */       
/*  902 */       if (!isDestroyed() && (this.field_70122_E || MCH_Lib.getBlockIdY((Entity)this, 1, -2) > 0)) {
/*      */         
/*  904 */         this.field_70159_w *= 0.95D;
/*  905 */         this.field_70179_y *= 0.95D;
/*  906 */         applyOnGroundPitch(0.95F);
/*      */       } 
/*      */       
/*  909 */       if (func_70090_H()) {
/*      */         
/*  911 */         this.field_70159_w *= 0.99D;
/*  912 */         this.field_70179_y *= 0.99D;
/*      */       } 
/*      */     } 
/*      */     
/*  916 */     if (isDestroyed())
/*      */     {
/*  918 */       if (MCH_Lib.getBlockIdY((Entity)this, 3, -3) == 0) {
/*      */         
/*  920 */         if (MathHelper.func_76135_e(getRotPitch()) < 10.0F)
/*      */         {
/*  922 */           setRotPitch(getRotPitch() + this.rotDestroyedPitch);
/*      */         }
/*      */         
/*  925 */         float roll = MathHelper.func_76135_e(getRotRoll());
/*      */         
/*  927 */         if (roll < 45.0F || roll > 135.0F)
/*      */         {
/*  929 */           setRotRoll(getRotRoll() + this.rotDestroyedRoll);
/*      */         }
/*      */       }
/*  932 */       else if (MathHelper.func_76135_e(getRotPitch()) > 20.0F) {
/*      */         
/*  934 */         setRotPitch(getRotPitch() * 0.99F);
/*      */       } 
/*      */     }
/*      */     
/*  938 */     if (getRiddenByEntity() != null);
/*      */ 
/*      */ 
/*      */     
/*  942 */     updateSound();
/*  943 */     onUpdate_Particle();
/*  944 */     onUpdate_Particle2();
/*  945 */     onUpdate_ParticleSplash();
/*  946 */     onUpdate_ParticleSandCloud(true);
/*  947 */     updateCamera(this.field_70165_t, this.field_70163_u, this.field_70161_v);
/*      */   }
/*      */ 
/*      */   
/*      */   private void onUpdate_Server() {
/*      */     Vec3d v;
/*  953 */     double prevMotion = Math.sqrt(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
/*  954 */     double dp = 0.0D;
/*      */     
/*  956 */     if (canFloatWater())
/*      */     {
/*  958 */       dp = getWaterDepth();
/*      */     }
/*      */     
/*  961 */     boolean levelOff = this.isGunnerMode;
/*      */     
/*  963 */     if (dp == 0.0D) {
/*      */       
/*  965 */       if (isTargetDrone() && canUseFuel() && !isDestroyed()) {
/*      */         
/*  967 */         Block block = MCH_Lib.getBlockY((Entity)this, 3, -40, true);
/*      */         
/*  969 */         if (block == null || W_Block.isEqual(block, W_Blocks.field_150350_a)) {
/*      */           
/*  971 */           setRotYaw(getRotYaw() + (getAcInfo()).autoPilotRot * 1.0F);
/*  972 */           setRotPitch(getRotPitch() * 0.95F);
/*      */           
/*  974 */           if (canFoldLandingGear())
/*      */           {
/*  976 */             foldLandingGear();
/*      */           }
/*      */           
/*  979 */           levelOff = true;
/*      */         }
/*      */         else {
/*      */           
/*  983 */           block = MCH_Lib.getBlockY((Entity)this, 3, -5, true);
/*      */           
/*  985 */           if (block == null || W_Block.isEqual(block, W_Blocks.field_150350_a)) {
/*      */             
/*  987 */             setRotYaw(getRotYaw() + (getAcInfo()).autoPilotRot * 2.0F);
/*      */             
/*  989 */             if (getRotPitch() > -20.0F)
/*      */             {
/*  991 */               setRotPitch(getRotPitch() - 0.5F);
/*      */             }
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/*  997 */       if (!levelOff)
/*      */       {
/*  999 */         this.field_70181_x += 0.04D + (!func_70090_H() ? (getAcInfo()).gravity : (getAcInfo()).gravityInWater);
/* 1000 */         this.field_70181_x += -0.047D * (1.0D - getCurrentThrottle());
/*      */       }
/*      */       else
/*      */       {
/* 1004 */         this.field_70181_x *= 0.8D;
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/* 1009 */       setRotPitch(getRotPitch() * 0.8F, "getWaterDepth != 0");
/*      */       
/* 1011 */       if (MathHelper.func_76135_e(getRotRoll()) < 40.0F)
/*      */       {
/* 1013 */         setRotRoll(getRotRoll() * 0.9F);
/*      */       }
/*      */       
/* 1016 */       if (dp < 1.0D) {
/*      */         
/* 1018 */         this.field_70181_x -= 1.0E-4D;
/* 1019 */         this.field_70181_x += 0.007D * getCurrentThrottle();
/*      */       }
/*      */       else {
/*      */         
/* 1023 */         if (this.field_70181_x < 0.0D)
/*      */         {
/* 1025 */           this.field_70181_x /= 2.0D;
/*      */         }
/*      */         
/* 1028 */         this.field_70181_x += 0.007D;
/*      */       } 
/*      */     } 
/*      */     
/* 1032 */     float throttle = (float)(getCurrentThrottle() / 10.0D);
/*      */ 
/*      */     
/* 1035 */     if (getNozzleRotation() > 0.001F) {
/*      */       
/* 1037 */       setRotPitch(getRotPitch() * 0.95F);
/*      */       
/* 1039 */       v = MCH_Lib.Rot2Vec3(getRotYaw(), getRotPitch() - getNozzleRotation());
/*      */       
/* 1041 */       if (getNozzleRotation() >= 90.0F)
/*      */       {
/*      */ 
/*      */         
/* 1045 */         v = new Vec3d(v.field_72450_a * 0.800000011920929D, v.field_72448_b, v.field_72449_c * 0.800000011920929D);
/*      */       }
/*      */     }
/*      */     else {
/*      */       
/* 1050 */       v = MCH_Lib.Rot2Vec3(getRotYaw(), getRotPitch() - 10.0F);
/*      */     } 
/*      */     
/* 1053 */     if (!levelOff)
/*      */     {
/* 1055 */       if (getNozzleRotation() <= 0.01F) {
/*      */         
/* 1057 */         this.field_70181_x += v.field_72448_b * throttle / 2.0D;
/*      */       }
/*      */       else {
/*      */         
/* 1061 */         this.field_70181_x += v.field_72448_b * throttle / 8.0D;
/*      */       } 
/*      */     }
/*      */     
/* 1065 */     boolean canMove = true;
/*      */     
/* 1067 */     if (!(getAcInfo()).canMoveOnGround) {
/*      */       
/* 1069 */       Block block = MCH_Lib.getBlockY((Entity)this, 3, -2, false);
/*      */       
/* 1071 */       if (!W_Block.isEqual(block, W_Block.getWater()) && !W_Block.isEqual(block, W_Blocks.field_150350_a))
/*      */       {
/* 1073 */         canMove = false;
/*      */       }
/*      */     } 
/*      */     
/* 1077 */     if (canMove)
/*      */     {
/* 1079 */       if ((getAcInfo()).enableBack && this.throttleBack > 0.0F) {
/*      */         
/* 1081 */         this.field_70159_w -= v.field_72450_a * this.throttleBack;
/* 1082 */         this.field_70179_y -= v.field_72449_c * this.throttleBack;
/*      */       }
/*      */       else {
/*      */         
/* 1086 */         this.field_70159_w += v.field_72450_a * throttle;
/* 1087 */         this.field_70179_y += v.field_72449_c * throttle;
/*      */       } 
/*      */     }
/*      */     
/* 1091 */     double motion = Math.sqrt(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
/* 1092 */     float speedLimit = getMaxSpeed();
/*      */     
/* 1094 */     if (motion > speedLimit) {
/*      */       
/* 1096 */       this.field_70159_w *= speedLimit / motion;
/* 1097 */       this.field_70179_y *= speedLimit / motion;
/* 1098 */       motion = speedLimit;
/*      */     } 
/*      */     
/* 1101 */     if (motion > prevMotion && this.currentSpeed < speedLimit) {
/*      */       
/* 1103 */       this.currentSpeed += (speedLimit - this.currentSpeed) / 35.0D;
/*      */       
/* 1105 */       if (this.currentSpeed > speedLimit)
/*      */       {
/* 1107 */         this.currentSpeed = speedLimit;
/*      */       }
/*      */     }
/*      */     else {
/*      */       
/* 1112 */       this.currentSpeed -= (this.currentSpeed - 0.07D) / 35.0D;
/*      */       
/* 1114 */       if (this.currentSpeed < 0.07D)
/*      */       {
/* 1116 */         this.currentSpeed = 0.07D;
/*      */       }
/*      */     } 
/*      */     
/* 1120 */     if (this.field_70122_E || MCH_Lib.getBlockIdY((Entity)this, 1, -2) > 0) {
/*      */       
/* 1122 */       this.field_70159_w *= (getAcInfo()).motionFactor;
/* 1123 */       this.field_70179_y *= (getAcInfo()).motionFactor;
/*      */       
/* 1125 */       if (MathHelper.func_76135_e(getRotPitch()) < 40.0F)
/*      */       {
/*      */         
/* 1128 */         applyOnGroundPitch(0.8F);
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1133 */     func_70091_d(MoverType.SELF, this.field_70159_w, this.field_70181_x, this.field_70179_y);
/*      */     
/* 1135 */     this.field_70181_x *= 0.95D;
/* 1136 */     this.field_70159_w *= (getAcInfo()).motionFactor;
/* 1137 */     this.field_70179_y *= (getAcInfo()).motionFactor;
/*      */     
/* 1139 */     func_70101_b(getRotYaw(), getRotPitch());
/* 1140 */     onUpdate_updateBlock();
/*      */     
/* 1142 */     if (getRiddenByEntity() != null && (getRiddenByEntity()).field_70128_L)
/*      */     {
/* 1144 */       unmountEntity();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public float getMaxSpeed() {
/* 1151 */     float f = 0.0F;
/*      */     
/* 1153 */     if (this.partWing != null && (getPlaneInfo()).isVariableSweepWing) {
/*      */       
/* 1155 */       f = ((getPlaneInfo()).sweepWingSpeed - (getPlaneInfo()).speed) * this.partWing.getFactor();
/*      */     }
/* 1157 */     else if (this.partHatch != null && (getPlaneInfo()).isVariableSweepWing) {
/*      */       
/* 1159 */       f = ((getPlaneInfo()).sweepWingSpeed - (getPlaneInfo()).speed) * this.partHatch.getFactor();
/*      */     } 
/*      */     
/* 1162 */     return (getPlaneInfo()).speed + f;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public float getSoundVolume() {
/* 1168 */     if (getAcInfo() != null && (getAcInfo()).throttleUpDown <= 0.0F) {
/* 1169 */       return 0.0F;
/*      */     }
/* 1171 */     return this.soundVolume * 0.7F;
/*      */   }
/*      */ 
/*      */   
/*      */   public void updateSound() {
/* 1176 */     float target = (float)getCurrentThrottle();
/*      */     
/* 1178 */     if (getRiddenByEntity() != null)
/*      */     {
/* 1180 */       if (this.partCanopy == null || getCanopyRotation() < 1.0F)
/*      */       {
/* 1182 */         target += 0.1F;
/*      */       }
/*      */     }
/*      */     
/* 1186 */     if (this.soundVolume < target) {
/*      */       
/* 1188 */       this.soundVolume += 0.02F;
/*      */       
/* 1190 */       if (this.soundVolume >= target)
/*      */       {
/* 1192 */         this.soundVolume = target;
/*      */       }
/*      */     }
/* 1195 */     else if (this.soundVolume > target) {
/*      */       
/* 1197 */       this.soundVolume -= 0.02F;
/*      */       
/* 1199 */       if (this.soundVolume <= target)
/*      */       {
/* 1201 */         this.soundVolume = target;
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public float getSoundPitch() {
/* 1209 */     return (float)(0.6D + getCurrentThrottle() * 0.4D);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDefaultSoundName() {
/* 1215 */     return "plane";
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateParts(int stat) {
/* 1221 */     super.updateParts(stat);
/*      */     
/* 1223 */     if (isDestroyed()) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/* 1228 */     MCH_Parts[] parts = { this.partNozzle, this.partWing };
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1233 */     for (MCH_Parts p : parts) {
/*      */       
/* 1235 */       if (p != null) {
/*      */         
/* 1237 */         p.updateStatusClient(stat);
/* 1238 */         p.update();
/*      */       } 
/*      */     } 
/*      */     
/* 1242 */     if (!this.field_70170_p.field_72995_K && this.partWing != null)
/*      */     {
/* 1244 */       if ((getPlaneInfo()).isVariableSweepWing && this.partWing.isON())
/*      */       {
/* 1246 */         if (getCurrentThrottle() >= 0.20000000298023224D)
/*      */         {
/* 1248 */           if (getCurrentThrottle() < 0.5D || MCH_Lib.getBlockIdY((Entity)this, 1, -10) != 0)
/*      */           {
/* 1250 */             this.partWing.setStatusServer(false);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public float getUnfoldLandingGearThrottle() {
/* 1260 */     return 0.7F;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean canSwitchVtol() {
/* 1265 */     if (this.planeInfo == null || !this.planeInfo.isEnableVtol)
/*      */     {
/* 1267 */       return false;
/*      */     }
/*      */     
/* 1270 */     if (getModeSwitchCooldown() > 0) {
/* 1271 */       return false;
/*      */     }
/* 1273 */     if (getVtolMode() == 1) {
/* 1274 */       return false;
/*      */     }
/* 1276 */     if (MathHelper.func_76135_e(getRotRoll()) > 30.0F)
/*      */     {
/* 1278 */       return false;
/*      */     }
/*      */     
/* 1281 */     if (this.field_70122_E && this.planeInfo.isDefaultVtol)
/*      */     {
/* 1283 */       return false;
/*      */     }
/*      */     
/* 1286 */     setModeSwitchCooldown(20);
/*      */     
/* 1288 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean getNozzleStat() {
/* 1293 */     return (this.partNozzle != null) ? this.partNozzle.getStatus() : false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int getVtolMode() {
/* 1299 */     if (!getNozzleStat())
/*      */     {
/* 1301 */       return (getNozzleRotation() <= 0.005F) ? 0 : 1;
/*      */     }
/*      */     
/* 1304 */     return (getNozzleRotation() >= 89.995F) ? 2 : 1;
/*      */   }
/*      */ 
/*      */   
/*      */   public float getFuleConsumptionFactor() {
/* 1309 */     return getFuelConsumptionFactor() * ((getVtolMode() == 2) ? true : true);
/*      */   }
/*      */ 
/*      */   
/*      */   public float getNozzleRotation() {
/* 1314 */     return (this.partNozzle != null) ? this.partNozzle.rotation : 0.0F;
/*      */   }
/*      */ 
/*      */   
/*      */   public float getPrevNozzleRotation() {
/* 1319 */     return (this.partNozzle != null) ? this.partNozzle.prevRotation : 0.0F;
/*      */   }
/*      */ 
/*      */   
/*      */   public void swithVtolMode(boolean mode) {
/* 1324 */     if (this.partNozzle != null) {
/*      */       
/* 1326 */       if (this.planeInfo.isDefaultVtol && this.field_70122_E && !mode) {
/*      */         return;
/*      */       }
/*      */ 
/*      */       
/* 1331 */       if (!this.field_70170_p.field_72995_K)
/*      */       {
/* 1333 */         this.partNozzle.setStatusServer(mode);
/*      */       }
/*      */       
/* 1336 */       if (getRiddenByEntity() != null && !(getRiddenByEntity()).field_70128_L)
/*      */       {
/* 1338 */         (getRiddenByEntity()).field_70125_A = (getRiddenByEntity()).field_70127_C = 0.0F;
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected MCH_Parts createNozzle(MCP_PlaneInfo info) {
/* 1345 */     MCH_Parts nozzle = null;
/*      */     
/* 1347 */     if (info.haveNozzle() || info.haveRotor() || info.isEnableVtol) {
/*      */ 
/*      */       
/* 1350 */       nozzle = new MCH_Parts((Entity)this, 1, PART_STAT, "Nozzle");
/* 1351 */       nozzle.rotationMax = 90.0F;
/* 1352 */       nozzle.rotationInv = 1.5F;
/* 1353 */       nozzle.soundStartSwichOn.setPrm("plane_cc", 1.0F, 0.5F);
/* 1354 */       nozzle.soundEndSwichOn.setPrm("plane_cc", 1.0F, 0.5F);
/* 1355 */       nozzle.soundStartSwichOff.setPrm("plane_cc", 1.0F, 0.5F);
/* 1356 */       nozzle.soundEndSwichOff.setPrm("plane_cc", 1.0F, 0.5F);
/* 1357 */       nozzle.soundSwitching.setPrm("plane_cv", 1.0F, 0.5F);
/*      */       
/* 1359 */       if (info.isDefaultVtol)
/*      */       {
/* 1361 */         nozzle.forceSwitch(true);
/*      */       }
/*      */     } 
/*      */     
/* 1365 */     return nozzle;
/*      */   }
/*      */ 
/*      */   
/*      */   protected MCH_Parts createWing(MCP_PlaneInfo info) {
/* 1370 */     MCH_Parts wing = null;
/*      */     
/* 1372 */     if (this.planeInfo.haveWing()) {
/*      */ 
/*      */       
/* 1375 */       wing = new MCH_Parts((Entity)this, 3, PART_STAT, "Wing");
/* 1376 */       wing.rotationMax = 90.0F;
/* 1377 */       wing.rotationInv = 2.5F;
/* 1378 */       wing.soundStartSwichOn.setPrm("plane_cc", 1.0F, 0.5F);
/* 1379 */       wing.soundEndSwichOn.setPrm("plane_cc", 1.0F, 0.5F);
/* 1380 */       wing.soundStartSwichOff.setPrm("plane_cc", 1.0F, 0.5F);
/* 1381 */       wing.soundEndSwichOff.setPrm("plane_cc", 1.0F, 0.5F);
/*      */     } 
/*      */     
/* 1384 */     return wing;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean canUseWing() {
/* 1389 */     if (this.partWing == null)
/*      */     {
/* 1391 */       return true;
/*      */     }
/*      */     
/* 1394 */     if ((getPlaneInfo()).isVariableSweepWing) {
/*      */       
/* 1396 */       if (getCurrentThrottle() < 0.2D)
/*      */       {
/* 1398 */         return this.partWing.isOFF();
/*      */       }
/*      */       
/* 1401 */       return true;
/*      */     } 
/*      */     
/* 1404 */     return this.partWing.isOFF();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean canFoldWing() {
/* 1409 */     if (this.partWing == null || getModeSwitchCooldown() > 0)
/*      */     {
/* 1411 */       return false;
/*      */     }
/*      */     
/* 1414 */     if ((getPlaneInfo()).isVariableSweepWing) {
/*      */       
/* 1416 */       if (this.field_70122_E == true || MCH_Lib.getBlockIdY((Entity)this, 3, -20) != 0)
/*      */       {
/* 1418 */         if (getCurrentThrottle() > 0.10000000149011612D)
/*      */         {
/* 1420 */           return false;
/*      */         }
/*      */       }
/* 1423 */       else if (getCurrentThrottle() < 0.699999988079071D)
/*      */       {
/* 1425 */         return false;
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/* 1430 */       if (!this.field_70122_E && MCH_Lib.getBlockIdY((Entity)this, 3, -3) == 0) {
/* 1431 */         return false;
/*      */       }
/* 1433 */       if (getCurrentThrottle() > 0.009999999776482582D) {
/* 1434 */         return false;
/*      */       }
/*      */     } 
/* 1437 */     return this.partWing.isOFF();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean canUnfoldWing() {
/* 1442 */     if (this.partWing == null || getModeSwitchCooldown() > 0) {
/* 1443 */       return false;
/*      */     }
/* 1445 */     return this.partWing.isON();
/*      */   }
/*      */ 
/*      */   
/*      */   public void foldWing(boolean fold) {
/* 1450 */     if (this.partWing == null || getModeSwitchCooldown() > 0) {
/*      */       return;
/*      */     }
/* 1453 */     this.partWing.setStatusServer(fold);
/* 1454 */     setModeSwitchCooldown(20);
/*      */   }
/*      */ 
/*      */   
/*      */   public float getWingRotation() {
/* 1459 */     return (this.partWing != null) ? this.partWing.rotation : 0.0F;
/*      */   }
/*      */ 
/*      */   
/*      */   public float getPrevWingRotation() {
/* 1464 */     return (this.partWing != null) ? this.partWing.prevRotation : 0.0F;
/*      */   }
/*      */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\plane\MCP_EntityPlane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */