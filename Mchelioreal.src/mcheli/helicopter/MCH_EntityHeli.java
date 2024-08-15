/*      */ package mcheli.helicopter;
/*      */ 
/*      */ import javax.annotation.Nullable;
/*      */ import mcheli.MCH_Config;
/*      */ import mcheli.MCH_Lib;
/*      */ import mcheli.MCH_ServerSettings;
/*      */ import mcheli.aircraft.MCH_EntityAircraft;
/*      */ import mcheli.aircraft.MCH_PacketStatusRequest;
/*      */ import mcheli.aircraft.MCH_Rotor;
/*      */ import mcheli.particles.MCH_ParticleParam;
/*      */ import mcheli.particles.MCH_ParticlesUtil;
/*      */ import mcheli.wrapper.W_Entity;
/*      */ import mcheli.wrapper.W_Lib;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.entity.MoverType;
/*      */ import net.minecraft.item.Item;
/*      */ import net.minecraft.nbt.NBTTagCompound;
/*      */ import net.minecraft.network.datasync.DataParameter;
/*      */ import net.minecraft.network.datasync.DataSerializers;
/*      */ import net.minecraft.network.datasync.EntityDataManager;
/*      */ import net.minecraft.util.math.AxisAlignedBB;
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
/*      */ public class MCH_EntityHeli
/*      */   extends MCH_EntityAircraft
/*      */ {
/*   36 */   private static final DataParameter<Byte> FOLD_STAT = EntityDataManager.func_187226_a(MCH_EntityHeli.class, DataSerializers.field_187191_a);
/*      */ 
/*      */ 
/*      */   
/*      */   private MCH_HeliInfo heliInfo;
/*      */ 
/*      */ 
/*      */   
/*   44 */   public double prevRotationRotor = 0.0D;
/*   45 */   public double rotationRotor = 0.0D;
/*      */   public MCH_Rotor[] rotors;
/*      */   public byte lastFoldBladeStat;
/*      */   public int foldBladesCooldown;
/*      */   public float prevRollFactor;
/*      */   public String getKindName() { return "helicopters"; }
/*      */   public String getEntityType() { return "Plane"; }
/*   52 */   public MCH_HeliInfo getHeliInfo() { return this.heliInfo; } public void changeType(String type) { MCH_Lib.DbgLog(this.field_70170_p, "MCH_EntityHeli.changeType " + type + " : " + toString(), new Object[0]); if (!type.isEmpty()) this.heliInfo = MCH_HeliInfoManager.get(type);  if (this.heliInfo == null) { MCH_Lib.Log((Entity)this, "##### MCH_EntityHeli changeHeliType() Heli info null %d, %s, %s", new Object[] { Integer.valueOf(W_Entity.getEntityId((Entity)this)), type, getEntityName() }); setDead(true); } else { setAcInfo(this.heliInfo); newSeats(getAcInfo().getNumSeatAndRack()); createRotors(); this.weapons = createWeapon(1 + getSeatNum()); initPartRotation(getRotYaw(), getRotPitch()); }  } @Nullable public Item getItem() { return (getHeliInfo() != null) ? (Item)(getHeliInfo()).item : null; } public boolean canMountWithNearEmptyMinecart() { return MCH_Config.MountMinecartHeli.prmBool; } protected void func_70088_a() { super.func_70088_a(); this.field_70180_af.func_187214_a(FOLD_STAT, Byte.valueOf((byte)2)); } protected void func_70014_b(NBTTagCompound par1NBTTagCompound) { super.func_70014_b(par1NBTTagCompound); par1NBTTagCompound.func_74780_a("RotorSpeed", getCurrentThrottle()); par1NBTTagCompound.func_74780_a("rotetionRotor", this.rotationRotor); par1NBTTagCompound.func_74757_a("FoldBlade", (getFoldBladeStat() == 0)); } public MCH_EntityHeli(World world) { super(world);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  505 */     this.prevRollFactor = 0.0F; this.heliInfo = null; this.currentSpeed = 0.07D; this.field_70156_m = true; func_70105_a(2.0F, 0.7F); this.field_70159_w = 0.0D; this.field_70181_x = 0.0D; this.field_70179_y = 0.0D; this.weapons = createWeapon(0); this.rotors = new MCH_Rotor[0]; this.lastFoldBladeStat = -1; if (this.field_70170_p.field_72995_K) this.foldBladesCooldown = 40;  }
/*      */   protected void func_70037_a(NBTTagCompound par1NBTTagCompound) { super.func_70037_a(par1NBTTagCompound); boolean beforeFoldBlade = (getFoldBladeStat() == 0); if (getCommonUniqueId().isEmpty()) { setCommonUniqueId(par1NBTTagCompound.func_74779_i("HeliUniqueId")); MCH_Lib.Log((Entity)this, "# MCH_EntityHeli readEntityFromNBT() " + W_Entity.getEntityId((Entity)this) + ", " + getEntityName() + ", AircraftUniqueId=null, HeliUniqueId=" + getCommonUniqueId(), new Object[0]); }  if (getTypeName().isEmpty()) { setTypeName(par1NBTTagCompound.func_74779_i("HeliType")); MCH_Lib.Log((Entity)this, "# MCH_EntityHeli readEntityFromNBT() " + W_Entity.getEntityId((Entity)this) + ", " + getEntityName() + ", TypeName=null, HeliType=" + getTypeName(), new Object[0]); }  setCurrentThrottle(par1NBTTagCompound.func_74769_h("RotorSpeed")); this.rotationRotor = par1NBTTagCompound.func_74769_h("rotetionRotor"); setFoldBladeStat((byte)(par1NBTTagCompound.func_74767_n("FoldBlade") ? 0 : 2)); if (this.heliInfo == null) { this.heliInfo = MCH_HeliInfoManager.get(getTypeName()); if (this.heliInfo == null) { MCH_Lib.Log((Entity)this, "##### MCH_EntityHeli readEntityFromNBT() Heli info null %d, %s", new Object[] { Integer.valueOf(W_Entity.getEntityId((Entity)this)), getEntityName() }); setDead(true); } else { setAcInfo(this.heliInfo); }  }  if (!beforeFoldBlade && getFoldBladeStat() == 0) forceFoldBlade();  this.prevRotationRotor = this.rotationRotor; }
/*      */   public float getSoundVolume() { if (getAcInfo() != null && (getAcInfo()).throttleUpDown <= 0.0F) return 0.0F;  return (float)getCurrentThrottle() * 2.0F; }
/*      */   public float getSoundPitch() { return (float)(0.2D + getCurrentThrottle() * 0.2D); }
/*      */   public String getDefaultSoundName() { return "heli"; }
/*  510 */   public float getUnfoldLandingGearThrottle() { double x = this.field_70165_t - this.field_70169_q; double y = this.field_70163_u - this.field_70167_r; double z = this.field_70161_v - this.field_70166_s; float s = (getAcInfo()).speed / 3.5F; return (x * x + y * y + z * z <= s) ? 0.8F : 0.3F; } protected void createRotors() { if (this.heliInfo == null) return;  this.rotors = new MCH_Rotor[this.heliInfo.rotorList.size()]; int i = 0; for (MCH_HeliInfo.Rotor r : this.heliInfo.rotorList) { this.rotors[i] = new MCH_Rotor(r.bladeNum, r.bladeRot, this.field_70170_p.field_72995_K ? 2 : 2, (float)r.pos.field_72450_a, (float)r.pos.field_72448_b, (float)r.pos.field_72449_c, (float)r.rot.field_72450_a, (float)r.rot.field_72448_b, (float)r.rot.field_72449_c, r.haveFoldFunc); i++; }  } protected void forceFoldBlade() { if (this.heliInfo != null && this.rotors.length > 0) if (this.heliInfo.isEnableFoldBlade) for (MCH_Rotor r : this.rotors) { r.update((float)this.rotationRotor); foldBlades(); r.forceFold(); }    } public float getRollFactor() { float roll = super.getRollFactor();
/*  511 */     double d = func_70092_e(this.field_70169_q, this.field_70163_u, this.field_70166_s);
/*  512 */     double s = (getAcInfo()).speed;
/*      */     
/*  514 */     d = (s > 0.1D) ? (d / s) : 0.0D;
/*      */     
/*  516 */     float f = this.prevRollFactor;
/*      */     
/*  518 */     this.prevRollFactor = roll;
/*      */     
/*  520 */     return (roll + f) / 2.0F; } public boolean isFoldBlades() { if (this.heliInfo == null || this.rotors.length <= 0) return false;  return (getFoldBladeStat() == 0); } protected boolean canSwitchFoldBlades() { if (this.heliInfo == null || this.rotors.length <= 0) return false;  return (this.heliInfo.isEnableFoldBlade && getCurrentThrottle() <= 0.01D && this.foldBladesCooldown == 0 && (getFoldBladeStat() == 2 || getFoldBladeStat() == 0)); }
/*      */   protected boolean canUseBlades() { if (this.heliInfo == null) return false;  if (this.rotors.length <= 0) return true;  if (getFoldBladeStat() == 2) { for (MCH_Rotor r : this.rotors) { if (r.isFoldingOrUnfolding()) return false;  }  return true; }  return false; }
/*      */   protected void foldBlades() { if (this.heliInfo == null || this.rotors.length <= 0) return;  setCurrentThrottle(0.0D); for (MCH_Rotor r : this.rotors) r.startFold();  }
/*      */   public void unfoldBlades() { if (this.heliInfo == null || this.rotors.length <= 0) return;  for (MCH_Rotor r : this.rotors) r.startUnfold();  }
/*      */   public void onRideEntity(Entity ridingEntity) { if (ridingEntity instanceof mcheli.aircraft.MCH_EntitySeat) { if (this.heliInfo == null || this.rotors.length <= 0) return;  if (this.heliInfo.isEnableFoldBlade) { forceFoldBlade(); setFoldBladeStat((byte)0); }  }  }
/*      */   protected byte getFoldBladeStat() { return ((Byte)this.field_70180_af.func_187225_a(FOLD_STAT)).byteValue(); }
/*  526 */   public float getControlRotYaw(float mouseX, float mouseY, float tick) { return mouseX; } public void setFoldBladeStat(byte b) { if (!this.field_70170_p.field_72995_K) if (b >= 0 && b <= 3) this.field_70180_af.func_187227_b(FOLD_STAT, Byte.valueOf(b));   } public boolean canSwitchGunnerMode() { if (super.canSwitchGunnerMode() && canUseBlades()) { float roll = MathHelper.func_76135_e(MathHelper.func_76142_g(getRotRoll())); float pitch = MathHelper.func_76135_e(MathHelper.func_76142_g(getRotPitch())); if (roll < 40.0F && pitch < 40.0F) return true;  }  return false; } public boolean canSwitchHoveringMode() { if (super.canSwitchHoveringMode() && canUseBlades()) { float roll = MathHelper.func_76135_e(MathHelper.func_76142_g(getRotRoll())); float pitch = MathHelper.func_76135_e(MathHelper.func_76142_g(getRotPitch())); if (roll < 40.0F && pitch < 40.0F) return true;  }  return false; }
/*      */   public void onUpdateAircraft() { if (this.heliInfo == null) { changeType(getTypeName()); this.field_70169_q = this.field_70165_t; this.field_70167_r = this.field_70163_u; this.field_70166_s = this.field_70161_v; return; }  if (!this.isRequestedSyncStatus) { this.isRequestedSyncStatus = true; if (this.field_70170_p.field_72995_K) { int stat = getFoldBladeStat(); if (stat == 1 || stat == 0) forceFoldBlade();  MCH_PacketStatusRequest.requestStatus(this); }  }  if (this.lastRiddenByEntity == null && getRiddenByEntity() != null) initCurrentWeapon(getRiddenByEntity());  updateWeapons(); onUpdate_Seats(); onUpdate_Control(); onUpdate_Rotor(); this.field_70169_q = this.field_70165_t; this.field_70167_r = this.field_70163_u; this.field_70166_s = this.field_70161_v; if (!isDestroyed() && isHovering()) if (MathHelper.func_76135_e(getRotPitch()) < 70.0F) setRotPitch(getRotPitch() * 0.95F);   if (isDestroyed()) if (getCurrentThrottle() > 0.0D) { if (MCH_Lib.getBlockIdY((Entity)this, 3, -2) > 0) setCurrentThrottle(getCurrentThrottle() * 0.8D);  if (isExploded()) setCurrentThrottle(getCurrentThrottle() * 0.98D);  }   updateCameraViewers(); if (this.field_70170_p.field_72995_K) { onUpdate_Client(); } else { onUpdate_Server(); }  }
/*      */   public boolean canMouseRot() { return super.canMouseRot(); }
/*      */   public boolean canUpdatePitch(Entity player) { return (super.canUpdatePitch(player) && !isHovering()); }
/*      */   public boolean canUpdateRoll(Entity player) { return (super.canUpdateRoll(player) && !isHovering()); }
/*      */   public boolean isOverridePlayerPitch() { return (super.isOverridePlayerPitch() && !isHovering()); }
/*  532 */   public float getControlRotPitch(float mouseX, float mouseY, float tick) { return mouseY; }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getControlRotRoll(float mouseX, float mouseY, float tick) {
/*  538 */     return mouseX;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void onUpdateAngles(float partialTicks) {
/*  544 */     if (isDestroyed()) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/*  549 */     float rotRoll = !isHovering() ? 0.04F : 0.07F;
/*  550 */     rotRoll = 1.0F - rotRoll * partialTicks;
/*      */     
/*  552 */     if (MCH_ServerSettings.enableRotationLimit) {
/*      */       
/*  554 */       if (getRotPitch() > MCH_ServerSettings.pitchLimitMax)
/*      */       {
/*  556 */         setRotPitch(getRotPitch() - 
/*  557 */             Math.abs((getRotPitch() - MCH_ServerSettings.pitchLimitMax) * 0.1F * partialTicks));
/*      */       }
/*      */       
/*  560 */       if (getRotPitch() < MCH_ServerSettings.pitchLimitMin)
/*      */       {
/*  562 */         setRotPitch(getRotPitch() + 
/*  563 */             Math.abs((getRotPitch() - MCH_ServerSettings.pitchLimitMin) * 0.2F * partialTicks));
/*      */       }
/*      */       
/*  566 */       if (getRotRoll() > MCH_ServerSettings.rollLimit)
/*      */       {
/*  568 */         setRotRoll(
/*  569 */             getRotRoll() - Math.abs((getRotRoll() - MCH_ServerSettings.rollLimit) * 0.03F * partialTicks));
/*      */       }
/*      */       
/*  572 */       if (getRotRoll() < -MCH_ServerSettings.rollLimit)
/*      */       {
/*  574 */         setRotRoll(
/*  575 */             getRotRoll() + Math.abs((getRotRoll() + MCH_ServerSettings.rollLimit) * 0.03F * partialTicks));
/*      */       }
/*      */     } 
/*      */     
/*  579 */     if (getRotRoll() > 0.1D && getRotRoll() < 65.0F)
/*      */     {
/*  581 */       setRotRoll(getRotRoll() * rotRoll);
/*      */     }
/*      */     
/*  584 */     if (getRotRoll() < -0.1D && getRotRoll() > -65.0F)
/*      */     {
/*  586 */       setRotRoll(getRotRoll() * rotRoll);
/*      */     }
/*      */     
/*  589 */     if (MCH_Lib.getBlockIdY((Entity)this, 3, -3) == 0) {
/*      */       
/*  591 */       if (this.moveLeft && !this.moveRight)
/*      */       {
/*  593 */         setRotRoll(getRotRoll() - 1.2F * partialTicks);
/*      */       }
/*      */       
/*  596 */       if (this.moveRight && !this.moveLeft)
/*      */       {
/*  598 */         setRotRoll(getRotRoll() + 1.2F * partialTicks);
/*      */       }
/*      */     }
/*      */     else {
/*      */       
/*  603 */       if (MathHelper.func_76135_e(getRotPitch()) < 40.0F)
/*      */       {
/*  605 */         applyOnGroundPitch(0.97F);
/*      */       }
/*      */       
/*  608 */       if (this.heliInfo.isEnableFoldBlade && this.rotors.length > 0 && getFoldBladeStat() == 0 && !isDestroyed()) {
/*      */         
/*  610 */         if (this.moveLeft && !this.moveRight)
/*      */         {
/*  612 */           setRotYaw(getRotYaw() - 0.5F * partialTicks);
/*      */         }
/*      */         
/*  615 */         if (this.moveRight && !this.moveLeft)
/*      */         {
/*  617 */           setRotYaw(getRotYaw() + 0.5F * partialTicks);
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void onUpdate_Rotor() {
/*  625 */     byte stat = getFoldBladeStat();
/*  626 */     boolean isEndSwitch = true;
/*      */     
/*  628 */     if (stat != this.lastFoldBladeStat) {
/*      */       
/*  630 */       if (stat == 1) {
/*      */         
/*  632 */         foldBlades();
/*      */       }
/*  634 */       else if (stat == 3) {
/*      */         
/*  636 */         unfoldBlades();
/*      */       } 
/*      */       
/*  639 */       if (this.field_70170_p.field_72995_K)
/*      */       {
/*  641 */         this.foldBladesCooldown = 40;
/*      */       }
/*      */       
/*  644 */       this.lastFoldBladeStat = stat;
/*      */     
/*      */     }
/*  647 */     else if (this.foldBladesCooldown > 0) {
/*      */       
/*  649 */       this.foldBladesCooldown--;
/*      */     } 
/*      */     
/*  652 */     for (MCH_Rotor r : this.rotors) {
/*      */       
/*  654 */       r.update((float)this.rotationRotor);
/*      */       
/*  656 */       if (r.isFoldingOrUnfolding())
/*      */       {
/*  658 */         isEndSwitch = false;
/*      */       }
/*      */     } 
/*      */     
/*  662 */     if (isEndSwitch)
/*      */     {
/*  664 */       if (stat == 1) {
/*      */         
/*  666 */         setFoldBladeStat((byte)0);
/*      */       }
/*  668 */       else if (stat == 3) {
/*      */         
/*  670 */         setFoldBladeStat((byte)2);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   protected void onUpdate_Control() {
/*  677 */     if (isHoveringMode() && !canUseFuel(true))
/*      */     {
/*  679 */       switchHoveringMode(false);
/*      */     }
/*      */     
/*  682 */     if (this.isGunnerMode && !canUseFuel())
/*      */     {
/*  684 */       switchGunnerMode(false);
/*      */     }
/*      */     
/*  687 */     if (!isDestroyed() && (getRiddenByEntity() != null || isHoveringMode()) && canUseBlades() && isCanopyClose() && 
/*  688 */       canUseFuel(true)) {
/*      */       
/*  690 */       if (!isHovering())
/*      */       {
/*  692 */         onUpdate_ControlNotHovering();
/*      */       }
/*      */       else
/*      */       {
/*  696 */         onUpdate_ControlHovering();
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/*  701 */       if (getCurrentThrottle() > 0.0D) {
/*  702 */         addCurrentThrottle(-0.00125D);
/*      */       } else {
/*      */         
/*  705 */         setCurrentThrottle(0.0D);
/*      */       } 
/*      */       
/*  708 */       if (this.heliInfo.isEnableFoldBlade && this.rotors.length > 0 && getFoldBladeStat() == 0 && this.field_70122_E && 
/*  709 */         !isDestroyed())
/*      */       {
/*  711 */         onUpdate_ControlFoldBladeAndOnGround();
/*      */       }
/*      */     } 
/*      */     
/*  715 */     if (this.field_70170_p.field_72995_K) {
/*      */       
/*  717 */       if (!W_Lib.isClientPlayer(getRiddenByEntity())) {
/*      */         
/*  719 */         double ct = getThrottle();
/*      */         
/*  721 */         if (getCurrentThrottle() >= ct - 0.02D)
/*      */         {
/*  723 */           addCurrentThrottle(-0.01D);
/*      */         }
/*  725 */         else if (getCurrentThrottle() < ct)
/*      */         {
/*  727 */           addCurrentThrottle(0.01D);
/*      */         }
/*      */       
/*      */       } 
/*      */     } else {
/*      */       
/*  733 */       setThrottle(getCurrentThrottle());
/*      */     } 
/*      */     
/*  736 */     if (getCurrentThrottle() < 0.0D) {
/*  737 */       setCurrentThrottle(0.0D);
/*      */     }
/*  739 */     this.prevRotationRotor = this.rotationRotor;
/*  740 */     this.rotationRotor += (1.0D - Math.pow(1.0D - getCurrentThrottle(), 5.0D)) * (getAcInfo()).rotorSpeed;
/*  741 */     this.rotationRotor %= 360.0D;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void onUpdate_ControlNotHovering() {
/*  746 */     float throttleUpDown = (getAcInfo()).throttleUpDown;
/*      */     
/*  748 */     if (this.throttleUp) {
/*      */       
/*  750 */       if (getCurrentThrottle() < 1.0D) {
/*  751 */         addCurrentThrottle(0.02D * throttleUpDown);
/*      */       } else {
/*      */         
/*  754 */         setCurrentThrottle(1.0D);
/*      */       }
/*      */     
/*  757 */     } else if (this.throttleDown) {
/*      */       
/*  759 */       if (getCurrentThrottle() > 0.0D) {
/*  760 */         addCurrentThrottle(-0.014285714285714285D * throttleUpDown);
/*      */       } else {
/*      */         
/*  763 */         setCurrentThrottle(0.0D);
/*      */       }
/*      */     
/*  766 */     } else if (!this.field_70170_p.field_72995_K || W_Lib.isClientPlayer(getRiddenByEntity())) {
/*      */       
/*  768 */       if (this.cs_heliAutoThrottleDown)
/*      */       {
/*  770 */         if (getCurrentThrottle() > 0.52D) {
/*      */           
/*  772 */           addCurrentThrottle(-0.01D * throttleUpDown);
/*      */         }
/*  774 */         else if (getCurrentThrottle() < 0.48D) {
/*      */           
/*  776 */           addCurrentThrottle(0.01D * throttleUpDown);
/*      */         } 
/*      */       }
/*      */     } 
/*      */     
/*  781 */     if (!this.field_70170_p.field_72995_K) {
/*      */       
/*  783 */       boolean move = false;
/*  784 */       float yaw = getRotYaw();
/*  785 */       double x = 0.0D;
/*  786 */       double z = 0.0D;
/*      */       
/*  788 */       if (this.moveLeft && !this.moveRight) {
/*      */         
/*  790 */         yaw = getRotYaw() - 90.0F;
/*  791 */         x += Math.sin(yaw * Math.PI / 180.0D);
/*  792 */         z += Math.cos(yaw * Math.PI / 180.0D);
/*  793 */         move = true;
/*      */       } 
/*      */       
/*  796 */       if (this.moveRight && !this.moveLeft) {
/*      */         
/*  798 */         yaw = getRotYaw() + 90.0F;
/*  799 */         x += Math.sin(yaw * Math.PI / 180.0D);
/*  800 */         z += Math.cos(yaw * Math.PI / 180.0D);
/*  801 */         move = true;
/*      */       } 
/*      */       
/*  804 */       if (move) {
/*      */         
/*  806 */         double f = 1.0D;
/*  807 */         double d = Math.sqrt(x * x + z * z);
/*  808 */         this.field_70159_w -= x / d * 0.019999999552965164D * f * (getAcInfo()).speed;
/*  809 */         this.field_70179_y += z / d * 0.019999999552965164D * f * (getAcInfo()).speed;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void onUpdate_ControlHovering() {
/*  816 */     if (getCurrentThrottle() < 1.0D) {
/*  817 */       addCurrentThrottle(0.03333333333333333D);
/*      */     } else {
/*      */       
/*  820 */       setCurrentThrottle(1.0D);
/*      */     } 
/*      */     
/*  823 */     if (!this.field_70170_p.field_72995_K) {
/*      */       
/*  825 */       boolean move = false;
/*  826 */       float yaw = getRotYaw();
/*  827 */       double x = 0.0D;
/*  828 */       double z = 0.0D;
/*      */       
/*  830 */       if (this.throttleUp) {
/*      */         
/*  832 */         yaw = getRotYaw();
/*  833 */         x += Math.sin(yaw * Math.PI / 180.0D);
/*  834 */         z += Math.cos(yaw * Math.PI / 180.0D);
/*  835 */         move = true;
/*      */       } 
/*      */       
/*  838 */       if (this.throttleDown) {
/*      */         
/*  840 */         yaw = getRotYaw() - 180.0F;
/*  841 */         x += Math.sin(yaw * Math.PI / 180.0D);
/*  842 */         z += Math.cos(yaw * Math.PI / 180.0D);
/*  843 */         move = true;
/*      */       } 
/*      */       
/*  846 */       if (this.moveLeft && !this.moveRight) {
/*      */         
/*  848 */         yaw = getRotYaw() - 90.0F;
/*  849 */         x += Math.sin(yaw * Math.PI / 180.0D);
/*  850 */         z += Math.cos(yaw * Math.PI / 180.0D);
/*  851 */         move = true;
/*      */       } 
/*      */       
/*  854 */       if (this.moveRight && !this.moveLeft) {
/*      */         
/*  856 */         yaw = getRotYaw() + 90.0F;
/*  857 */         x += Math.sin(yaw * Math.PI / 180.0D);
/*  858 */         z += Math.cos(yaw * Math.PI / 180.0D);
/*  859 */         move = true;
/*      */       } 
/*      */       
/*  862 */       if (move) {
/*      */         
/*  864 */         double d = Math.sqrt(x * x + z * z);
/*      */         
/*  866 */         this.field_70159_w -= x / d * 0.009999999776482582D * (getAcInfo()).speed;
/*  867 */         this.field_70179_y += z / d * 0.009999999776482582D * (getAcInfo()).speed;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void onUpdate_ControlFoldBladeAndOnGround() {
/*  874 */     if (!this.field_70170_p.field_72995_K) {
/*      */       
/*  876 */       boolean move = false;
/*  877 */       float yaw = getRotYaw();
/*  878 */       double x = 0.0D;
/*  879 */       double z = 0.0D;
/*      */       
/*  881 */       if (this.throttleUp) {
/*      */         
/*  883 */         yaw = getRotYaw();
/*  884 */         x += Math.sin(yaw * Math.PI / 180.0D);
/*  885 */         z += Math.cos(yaw * Math.PI / 180.0D);
/*  886 */         move = true;
/*      */       } 
/*      */       
/*  889 */       if (this.throttleDown) {
/*      */         
/*  891 */         yaw = getRotYaw() - 180.0F;
/*  892 */         x += Math.sin(yaw * Math.PI / 180.0D);
/*  893 */         z += Math.cos(yaw * Math.PI / 180.0D);
/*  894 */         move = true;
/*      */       } 
/*      */       
/*  897 */       if (move) {
/*      */         
/*  899 */         double d = Math.sqrt(x * x + z * z);
/*  900 */         this.field_70159_w -= x / d * 0.029999999329447746D;
/*  901 */         this.field_70179_y += z / d * 0.029999999329447746D;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void onUpdate_Particle2() {
/*  908 */     if (!this.field_70170_p.field_72995_K) {
/*      */       return;
/*      */     }
/*  911 */     if (getHP() > getMaxHP() * 0.5D) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/*  916 */     if (getHeliInfo() == null) {
/*      */       return;
/*      */     }
/*  919 */     int rotorNum = (getHeliInfo()).rotorList.size();
/*      */     
/*  921 */     if (rotorNum <= 0) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/*  926 */     if (this.isFirstDamageSmoke)
/*      */     {
/*  928 */       this.prevDamageSmokePos = new Vec3d[rotorNum];
/*      */     }
/*      */     
/*  931 */     for (int ri = 0; ri < rotorNum; ri++) {
/*      */       
/*  933 */       Vec3d rotor_pos = ((MCH_HeliInfo.Rotor)(getHeliInfo()).rotorList.get(ri)).pos;
/*  934 */       float yaw = getRotYaw();
/*  935 */       float pitch = getRotPitch();
/*  936 */       Vec3d pos = MCH_Lib.RotVec3(rotor_pos, -yaw, -pitch, -getRotRoll());
/*  937 */       double x = this.field_70165_t + pos.field_72450_a;
/*  938 */       double y = this.field_70163_u + pos.field_72448_b;
/*  939 */       double z = this.field_70161_v + pos.field_72449_c;
/*      */       
/*  941 */       if (this.isFirstDamageSmoke)
/*      */       {
/*  943 */         this.prevDamageSmokePos[ri] = new Vec3d(x, y, z);
/*      */       }
/*      */       
/*  946 */       Vec3d prev = this.prevDamageSmokePos[ri];
/*  947 */       double dx = x - prev.field_72450_a;
/*  948 */       double dy = y - prev.field_72448_b;
/*  949 */       double dz = z - prev.field_72449_c;
/*  950 */       int num = (int)(MathHelper.func_76133_a(dx * dx + dy * dy + dz * dz) * 2.0F) + 1;
/*      */       double i;
/*  952 */       for (i = 0.0D; i < num; i++) {
/*      */         
/*  954 */         double p = (getHP() / getMaxHP());
/*      */         
/*  956 */         if (p < (this.field_70146_Z.nextFloat() / 2.0F)) {
/*      */           
/*  958 */           float c = 0.2F + this.field_70146_Z.nextFloat() * 0.3F;
/*      */           
/*  960 */           MCH_ParticleParam prm = new MCH_ParticleParam(this.field_70170_p, "smoke", prev.field_72450_a + (x - prev.field_72450_a) * i / num, prev.field_72448_b + (y - prev.field_72448_b) * i / num, prev.field_72449_c + (z - prev.field_72449_c) * i / num);
/*      */ 
/*      */ 
/*      */           
/*  964 */           prm.motionX = (this.field_70146_Z.nextDouble() - 0.5D) * 0.3D;
/*  965 */           prm.motionY = this.field_70146_Z.nextDouble() * 0.1D;
/*  966 */           prm.motionZ = (this.field_70146_Z.nextDouble() - 0.5D) * 0.3D;
/*  967 */           prm.size = (this.field_70146_Z.nextInt(5) + 5.0F) * 1.0F;
/*  968 */           prm.setColor(0.7F + this.field_70146_Z.nextFloat() * 0.1F, c, c, c);
/*      */           
/*  970 */           MCH_ParticlesUtil.spawnParticle(prm);
/*      */           
/*  972 */           int ebi = this.field_70146_Z.nextInt(1 + this.extraBoundingBox.length);
/*      */           
/*  974 */           if (p < 0.3D && ebi > 0) {
/*      */ 
/*      */             
/*  977 */             AxisAlignedBB bb = this.extraBoundingBox[ebi - 1].getBoundingBox();
/*      */             
/*  979 */             double bx = (bb.field_72336_d + bb.field_72340_a) / 2.0D;
/*  980 */             double by = (bb.field_72337_e + bb.field_72338_b) / 2.0D;
/*  981 */             double bz = (bb.field_72334_f + bb.field_72339_c) / 2.0D;
/*      */             
/*  983 */             prm.posX = bx;
/*  984 */             prm.posY = by;
/*  985 */             prm.posZ = bz;
/*      */             
/*  987 */             MCH_ParticlesUtil.spawnParticle(prm);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  994 */       this.prevDamageSmokePos[ri] = new Vec3d(x, y, z);
/*      */     } 
/*      */     
/*  997 */     this.isFirstDamageSmoke = false;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void onUpdate_Client() {
/* 1002 */     if (getRiddenByEntity() != null)
/*      */     {
/* 1004 */       if (W_Lib.isClientPlayer(getRiddenByEntity()))
/*      */       {
/* 1006 */         (getRiddenByEntity()).field_70125_A = (getRiddenByEntity()).field_70127_C;
/*      */       }
/*      */     }
/*      */     
/* 1010 */     if (this.aircraftPosRotInc > 0) {
/*      */       
/* 1012 */       applyServerPositionAndRotation();
/*      */     }
/*      */     else {
/*      */       
/* 1016 */       func_70107_b(this.field_70165_t + this.field_70159_w, this.field_70163_u + this.field_70181_x, this.field_70161_v + this.field_70179_y);
/*      */       
/* 1018 */       if (!isDestroyed() && (this.field_70122_E || MCH_Lib.getBlockIdY((Entity)this, 1, -2) > 0)) {
/*      */         
/* 1020 */         this.field_70159_w *= 0.95D;
/* 1021 */         this.field_70179_y *= 0.95D;
/* 1022 */         applyOnGroundPitch(0.95F);
/*      */       } 
/*      */       
/* 1025 */       if (func_70090_H()) {
/*      */         
/* 1027 */         this.field_70159_w *= 0.99D;
/* 1028 */         this.field_70179_y *= 0.99D;
/*      */       } 
/*      */     } 
/*      */     
/* 1032 */     if (isDestroyed()) {
/*      */       
/* 1034 */       if (this.rotDestroyedYaw < 15.0F)
/*      */       {
/* 1036 */         this.rotDestroyedYaw += 0.3F;
/*      */       }
/*      */       
/* 1039 */       setRotYaw(getRotYaw() + this.rotDestroyedYaw * (float)getCurrentThrottle());
/*      */       
/* 1041 */       if (MCH_Lib.getBlockIdY((Entity)this, 3, -3) == 0) {
/*      */         
/* 1043 */         if (MathHelper.func_76135_e(getRotPitch()) < 10.0F)
/*      */         {
/* 1045 */           setRotPitch(getRotPitch() + this.rotDestroyedPitch);
/*      */         }
/*      */         
/* 1048 */         setRotRoll(getRotRoll() + this.rotDestroyedRoll);
/*      */       } 
/*      */     } 
/*      */     
/* 1052 */     if (getRiddenByEntity() != null);
/*      */ 
/*      */ 
/*      */     
/* 1056 */     onUpdate_ParticleSandCloud(false);
/* 1057 */     onUpdate_Particle2();
/* 1058 */     updateCamera(this.field_70165_t, this.field_70163_u, this.field_70161_v);
/*      */   }
/*      */ 
/*      */   
/*      */   private void onUpdate_Server() {
/* 1063 */     double prevMotion = Math.sqrt(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
/* 1064 */     float ogp = (getAcInfo()).onGroundPitch;
/*      */     
/* 1066 */     if (!isHovering()) {
/*      */       
/* 1068 */       double dp = 0.0D;
/*      */       
/* 1070 */       if (canFloatWater())
/*      */       {
/* 1072 */         dp = getWaterDepth();
/*      */       }
/*      */       
/* 1075 */       if (dp == 0.0D) {
/*      */         
/* 1077 */         this.field_70181_x += (!func_70090_H() ? (getAcInfo()).gravity : (getAcInfo()).gravityInWater);
/*      */         
/* 1079 */         float yaw = getRotYaw() / 180.0F * 3.1415927F;
/* 1080 */         float pitch = getRotPitch();
/*      */         
/* 1082 */         if (MCH_Lib.getBlockIdY((Entity)this, 3, -3) > 0)
/*      */         {
/* 1084 */           pitch -= ogp;
/*      */         }
/*      */         
/* 1087 */         this.field_70159_w += 0.1D * MathHelper.func_76126_a(yaw) * this.currentSpeed * -(pitch * pitch * pitch / 20000.0F) * 
/* 1088 */           getCurrentThrottle();
/* 1089 */         this.field_70179_y += 0.1D * MathHelper.func_76134_b(yaw) * this.currentSpeed * (pitch * pitch * pitch / 20000.0F) * 
/* 1090 */           getCurrentThrottle();
/* 1091 */         double y = 0.0D;
/*      */         
/* 1093 */         if (MathHelper.func_76135_e(getRotPitch()) + MathHelper.func_76135_e(getRotRoll() * 0.9F) <= 40.0F)
/*      */         {
/* 1095 */           y = 1.0D - y / 40.0D;
/*      */         }
/*      */         
/* 1098 */         double throttle = getCurrentThrottle();
/*      */         
/* 1100 */         if (isDestroyed())
/*      */         {
/* 1102 */           throttle *= -0.65D;
/*      */         }
/*      */         
/* 1105 */         this.field_70181_x += (y * 0.025D + 0.03D) * throttle;
/*      */       }
/*      */       else {
/*      */         
/* 1109 */         if (MathHelper.func_76135_e(getRotPitch()) < 40.0F) {
/*      */           
/* 1111 */           float pitch = getRotPitch();
/*      */           
/* 1113 */           pitch -= ogp;
/* 1114 */           pitch *= 0.9F;
/* 1115 */           pitch += ogp;
/* 1116 */           setRotPitch(pitch);
/*      */         } 
/*      */         
/* 1119 */         if (MathHelper.func_76135_e(getRotRoll()) < 40.0F)
/*      */         {
/* 1121 */           setRotRoll(getRotRoll() * 0.9F);
/*      */         }
/*      */         
/* 1124 */         if (dp < 1.0D)
/*      */         {
/* 1126 */           this.field_70181_x -= 1.0E-4D;
/* 1127 */           this.field_70181_x += 0.007D * getCurrentThrottle();
/*      */         }
/*      */         else
/*      */         {
/* 1131 */           if (this.field_70181_x < 0.0D)
/*      */           {
/* 1133 */             this.field_70181_x *= 0.7D;
/*      */           }
/*      */           
/* 1136 */           this.field_70181_x += 0.007D;
/*      */         }
/*      */       
/*      */       } 
/*      */     } else {
/*      */       
/* 1142 */       if (this.field_70146_Z.nextInt(50) == 0) {
/* 1143 */         this.field_70159_w += (this.field_70146_Z.nextDouble() - 0.5D) / 30.0D;
/*      */       }
/* 1145 */       if (this.field_70146_Z.nextInt(50) == 0) {
/* 1146 */         this.field_70181_x += (this.field_70146_Z.nextDouble() - 0.5D) / 50.0D;
/*      */       }
/* 1148 */       if (this.field_70146_Z.nextInt(50) == 0)
/*      */       {
/* 1150 */         this.field_70179_y += (this.field_70146_Z.nextDouble() - 0.5D) / 30.0D;
/*      */       }
/*      */     } 
/*      */     
/* 1154 */     double motion = Math.sqrt(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
/* 1155 */     float speedLimit = (getAcInfo()).speed;
/*      */     
/* 1157 */     if (motion > speedLimit) {
/*      */       
/* 1159 */       this.field_70159_w *= speedLimit / motion;
/* 1160 */       this.field_70179_y *= speedLimit / motion;
/* 1161 */       motion = speedLimit;
/*      */     } 
/*      */     
/* 1164 */     if (isDestroyed()) {
/*      */       
/* 1166 */       this.field_70159_w *= 0.0D;
/* 1167 */       this.field_70179_y *= 0.0D;
/* 1168 */       this.currentSpeed = 0.0D;
/*      */     } 
/*      */     
/* 1171 */     if (motion > prevMotion && this.currentSpeed < speedLimit) {
/*      */       
/* 1173 */       this.currentSpeed += (speedLimit - this.currentSpeed) / 35.0D;
/*      */       
/* 1175 */       if (this.currentSpeed > speedLimit)
/*      */       {
/* 1177 */         this.currentSpeed = speedLimit;
/*      */       }
/*      */     }
/*      */     else {
/*      */       
/* 1182 */       this.currentSpeed -= (this.currentSpeed - 0.07D) / 35.0D;
/*      */       
/* 1184 */       if (this.currentSpeed < 0.07D)
/*      */       {
/* 1186 */         this.currentSpeed = 0.07D;
/*      */       }
/*      */     } 
/*      */     
/* 1190 */     if (this.field_70122_E) {
/*      */       
/* 1192 */       this.field_70159_w *= 0.5D;
/* 1193 */       this.field_70179_y *= 0.5D;
/*      */       
/* 1195 */       if (MathHelper.func_76135_e(getRotPitch()) < 40.0F) {
/*      */         
/* 1197 */         float pitch = getRotPitch();
/* 1198 */         pitch -= ogp;
/* 1199 */         pitch *= 0.9F;
/* 1200 */         pitch += ogp;
/* 1201 */         setRotPitch(pitch);
/*      */       } 
/*      */       
/* 1204 */       if (MathHelper.func_76135_e(getRotRoll()) < 40.0F)
/*      */       {
/* 1206 */         setRotRoll(getRotRoll() * 0.9F);
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1211 */     func_70091_d(MoverType.SELF, this.field_70159_w, this.field_70181_x, this.field_70179_y);
/*      */     
/* 1213 */     this.field_70181_x *= 0.95D;
/* 1214 */     this.field_70159_w *= 0.99D;
/* 1215 */     this.field_70179_y *= 0.99D;
/*      */     
/* 1217 */     func_70101_b(getRotYaw(), getRotPitch());
/* 1218 */     onUpdate_updateBlock();
/*      */     
/* 1220 */     if (getRiddenByEntity() != null && (getRiddenByEntity()).field_70128_L)
/*      */     {
/* 1222 */       unmountEntity();
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\helicopter\MCH_EntityHeli.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */