/*     */ package mcheli.mob;
/*     */ 
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import mcheli.MCH_Config;
/*     */ import mcheli.MCH_Lib;
/*     */ import mcheli.MCH_MOD;
/*     */ import mcheli.aircraft.MCH_AircraftInfo;
/*     */ import mcheli.aircraft.MCH_EntityAircraft;
/*     */ import mcheli.aircraft.MCH_EntitySeat;
/*     */ import mcheli.aircraft.MCH_SeatInfo;
/*     */ import mcheli.weapon.MCH_WeaponBase;
/*     */ import mcheli.weapon.MCH_WeaponParam;
/*     */ import mcheli.weapon.MCH_WeaponSet;
/*     */ import mcheli.wrapper.W_WorldFunc;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.monster.IMob;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.EntityEquipmentSlot;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.network.datasync.DataParameter;
/*     */ import net.minecraft.network.datasync.DataSerializers;
/*     */ import net.minecraft.network.datasync.EntityDataManager;
/*     */ import net.minecraft.scoreboard.ScorePlayerTeam;
/*     */ import net.minecraft.scoreboard.Team;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.EnumHandSide;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.util.text.ITextComponent;
/*     */ import net.minecraft.util.text.TextComponentString;
/*     */ import net.minecraft.util.text.TextComponentTranslation;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.common.util.ITeleporter;
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
/*     */ public class MCH_EntityGunner
/*     */   extends EntityLivingBase
/*     */ {
/*  54 */   private static final DataParameter<String> TEAM_NAME = EntityDataManager.func_187226_a(MCH_EntityGunner.class, DataSerializers.field_187194_d);
/*     */   
/*     */   public boolean isCreative = false;
/*     */   
/*  58 */   public String ownerUUID = "";
/*  59 */   public int targetType = 0;
/*  60 */   public int despawnCount = 0;
/*  61 */   public int switchTargetCount = 0;
/*  62 */   public Entity targetEntity = null;
/*  63 */   public double targetPrevPosX = 0.0D;
/*  64 */   public double targetPrevPosY = 0.0D;
/*  65 */   public double targetPrevPosZ = 0.0D;
/*     */   public boolean waitCooldown = false;
/*  67 */   public int idleCount = 0;
/*  68 */   public int idleRotation = 0;
/*     */ 
/*     */   
/*     */   public MCH_EntityGunner(World world) {
/*  72 */     super(world);
/*     */   }
/*     */ 
/*     */   
/*     */   public MCH_EntityGunner(World world, double x, double y, double z) {
/*  77 */     this(world);
/*  78 */     func_70107_b(x, y, z);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_70088_a() {
/*  84 */     super.func_70088_a();
/*     */     
/*  86 */     this.field_70180_af.func_187214_a(TEAM_NAME, "");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTeamName() {
/*  92 */     return (String)this.field_70180_af.func_187225_a(TEAM_NAME);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTeamName(String name) {
/*  98 */     this.field_70180_af.func_187227_b(TEAM_NAME, name);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Team func_96124_cp() {
/* 104 */     return (Team)this.field_70170_p.func_96441_U().func_96508_e(getTeamName());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_184191_r(Entity entityIn) {
/* 112 */     return super.func_184191_r(entityIn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ITextComponent func_145748_c_() {
/* 119 */     Team team = func_96124_cp();
/*     */     
/* 121 */     if (team != null) {
/*     */ 
/*     */       
/* 124 */       String name = MCH_MOD.isTodaySep01() ? "'s EMB4" : " Gunner";
/* 125 */       return (ITextComponent)new TextComponentString(ScorePlayerTeam.func_96667_a(team, team.func_96661_b() + name));
/*     */     } 
/*     */ 
/*     */     
/* 129 */     return (ITextComponent)new TextComponentString("");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_180431_b(DamageSource source) {
/* 136 */     return this.isCreative;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_70645_a(DamageSource source) {
/* 142 */     super.func_70645_a(source);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_184230_a(EntityPlayer player, EnumHand hand) {
/* 149 */     if (this.field_70170_p.field_72995_K) {
/* 150 */       return false;
/*     */     }
/* 152 */     if (func_184187_bx() == null)
/*     */     {
/* 154 */       return false;
/*     */     }
/*     */     
/* 157 */     if (player.field_71075_bZ.field_75098_d) {
/*     */       
/* 159 */       removeFromAircraft(player);
/* 160 */       return true;
/*     */     } 
/*     */     
/* 163 */     if (this.isCreative) {
/*     */ 
/*     */       
/* 166 */       player.func_145747_a((ITextComponent)new TextComponentString("Creative mode only."));
/* 167 */       return false;
/*     */     } 
/*     */     
/* 170 */     if (func_96124_cp() == null || func_184191_r((Entity)player)) {
/*     */       
/* 172 */       removeFromAircraft(player);
/* 173 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 177 */     player.func_145747_a((ITextComponent)new TextComponentString("You are other team."));
/*     */     
/* 179 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeFromAircraft(EntityPlayer player) {
/* 184 */     if (!this.field_70170_p.field_72995_K) {
/*     */       
/* 186 */       W_WorldFunc.MOD_playSoundAtEntity((Entity)player, "wrench", 1.0F, 1.0F);
/* 187 */       func_70106_y();
/* 188 */       MCH_EntityAircraft ac = null;
/*     */       
/* 190 */       if (func_184187_bx() instanceof MCH_EntityAircraft) {
/*     */         
/* 192 */         ac = (MCH_EntityAircraft)func_184187_bx();
/*     */       }
/* 194 */       else if (func_184187_bx() instanceof MCH_EntitySeat) {
/*     */         
/* 196 */         ac = ((MCH_EntitySeat)func_184187_bx()).getParent();
/*     */       } 
/*     */       
/* 199 */       String name = "";
/*     */       
/* 201 */       if (ac != null && ac.getAcInfo() != null)
/*     */       {
/* 203 */         name = " on " + (ac.getAcInfo()).displayName + " seat " + (ac.getSeatIdByEntity((Entity)this) + 1);
/*     */       }
/*     */ 
/*     */       
/* 207 */       String playerName = ScorePlayerTeam.func_96667_a(player.func_96124_cp(), player.func_145748_c_().func_150254_d());
/* 208 */       if (MCH_MOD.isTodaySep01()) {
/*     */         
/* 210 */         player.func_145747_a((ITextComponent)new TextComponentTranslation("chat.type.text", new Object[] { "EMB4", new TextComponentString("Bye " + playerName + "! Good vehicle" + name) }));
/*     */       }
/*     */       else {
/*     */         
/* 214 */         player.func_145747_a((ITextComponent)new TextComponentString("Remove gunner" + name + " by " + playerName + "."));
/*     */       } 
/*     */ 
/*     */       
/* 218 */       func_184210_p();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_70071_h_() {
/* 225 */     super.func_70071_h_();
/*     */     
/* 227 */     if (!this.field_70170_p.field_72995_K && !this.field_70128_L) {
/*     */       
/* 229 */       if (func_184187_bx() != null && (func_184187_bx()).field_70128_L)
/*     */       {
/*     */         
/* 232 */         func_184210_p();
/*     */       }
/*     */       
/* 235 */       if (func_184187_bx() instanceof MCH_EntityAircraft) {
/*     */         
/* 237 */         shotTarget((MCH_EntityAircraft)func_184187_bx());
/*     */       }
/* 239 */       else if (func_184187_bx() instanceof MCH_EntitySeat && ((MCH_EntitySeat)
/* 240 */         func_184187_bx()).getParent() != null) {
/*     */         
/* 242 */         shotTarget(((MCH_EntitySeat)func_184187_bx()).getParent());
/*     */       }
/* 244 */       else if (this.despawnCount < 20) {
/*     */         
/* 246 */         this.despawnCount++;
/*     */       }
/* 248 */       else if (func_184187_bx() == null || this.field_70173_aa > 100) {
/*     */         
/* 250 */         func_70106_y();
/*     */       } 
/*     */       
/* 253 */       if (this.targetEntity == null) {
/*     */         
/* 255 */         if (this.idleCount == 0) {
/*     */           
/* 257 */           this.idleCount = (3 + this.field_70146_Z.nextInt(5)) * 20;
/* 258 */           this.idleRotation = this.field_70146_Z.nextInt(5) - 2;
/*     */         } 
/*     */         
/* 261 */         this.field_70177_z += this.idleRotation / 2.0F;
/*     */       }
/*     */       else {
/*     */         
/* 265 */         this.idleCount = 60;
/*     */       } 
/*     */     } 
/*     */     
/* 269 */     if (this.switchTargetCount > 0)
/*     */     {
/* 271 */       this.switchTargetCount--;
/*     */     }
/*     */     
/* 274 */     if (this.idleCount > 0)
/*     */     {
/* 276 */       this.idleCount--;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canAttackEntity(EntityLivingBase entity, MCH_EntityAircraft ac, MCH_WeaponSet ws) {
/* 282 */     boolean ret = false;
/*     */     
/* 284 */     if (this.targetType == 0) {
/*     */ 
/*     */       
/* 287 */       ret = (entity != this && !(entity instanceof net.minecraft.entity.monster.EntityEnderman) && !entity.field_70128_L && !func_184191_r((Entity)entity) && entity.func_110143_aJ() > 0.0F && !ac.isMountedEntity((Entity)entity));
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/* 293 */       ret = (entity != this && !((EntityPlayer)entity).field_71075_bZ.field_75098_d && !entity.field_70128_L && !getTeamName().isEmpty() && !func_184191_r((Entity)entity) && entity.func_110143_aJ() > 0.0F && !ac.isMountedEntity((Entity)entity));
/*     */     } 
/*     */     
/* 296 */     if (ret && ws.getCurrentWeapon().getGuidanceSystem() != null)
/*     */     {
/* 298 */       ret = ws.getCurrentWeapon().getGuidanceSystem().canLockEntity((Entity)entity);
/*     */     }
/*     */     
/* 301 */     return ret;
/*     */   }
/*     */ 
/*     */   
/*     */   public void shotTarget(MCH_EntityAircraft ac) {
/* 306 */     if (ac.isDestroyed()) {
/*     */       return;
/*     */     }
/* 309 */     if (!ac.getGunnerStatus()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 314 */     MCH_WeaponSet ws = ac.getCurrentWeapon((Entity)this);
/*     */     
/* 316 */     if (ws == null || ws.getInfo() == null || ws.getCurrentWeapon() == null) {
/*     */       return;
/*     */     }
/* 319 */     MCH_WeaponBase cw = ws.getCurrentWeapon();
/*     */     
/* 321 */     if (this.targetEntity != null && (this.targetEntity.field_70128_L || ((EntityLivingBase)this.targetEntity)
/* 322 */       .func_110143_aJ() <= 0.0F))
/*     */     {
/* 324 */       if (this.switchTargetCount > 20)
/*     */       {
/* 326 */         this.switchTargetCount = 20;
/*     */       }
/*     */     }
/*     */     
/* 330 */     Vec3d pos = getGunnerWeaponPos(ac, ws);
/*     */     
/* 332 */     if ((this.targetEntity == null && this.switchTargetCount <= 0) || this.switchTargetCount <= 0) {
/*     */       List<? extends EntityLivingBase> list;
/* 334 */       this.switchTargetCount = 20;
/* 335 */       EntityLivingBase nextTarget = null;
/*     */ 
/*     */       
/* 338 */       if (this.targetType == 0) {
/*     */         
/* 340 */         int rh = MCH_Config.RangeOfGunner_VsMonster_Horizontal.prmInt;
/* 341 */         int rv = MCH_Config.RangeOfGunner_VsMonster_Vertical.prmInt;
/*     */         
/* 343 */         list = this.field_70170_p.func_175647_a(EntityLivingBase.class, 
/* 344 */             func_174813_aQ().func_72314_b(rh, rv, rh), IMob.field_82192_a);
/*     */       }
/*     */       else {
/*     */         
/* 348 */         int rh = MCH_Config.RangeOfGunner_VsPlayer_Horizontal.prmInt;
/* 349 */         int rv = MCH_Config.RangeOfGunner_VsPlayer_Vertical.prmInt;
/* 350 */         list = this.field_70170_p.func_72872_a(EntityPlayer.class, 
/* 351 */             func_174813_aQ().func_72314_b(rh, rv, rh));
/*     */       } 
/*     */       
/* 354 */       for (int i = 0; i < list.size(); i++) {
/*     */         
/* 356 */         EntityLivingBase entity = list.get(i);
/*     */         
/* 358 */         if (canAttackEntity(entity, ac, ws))
/*     */         {
/* 360 */           if (checkPitch(entity, ac, pos))
/*     */           {
/* 362 */             if ((nextTarget == null || func_70032_d((Entity)entity) < func_70032_d((Entity)nextTarget)) && 
/* 363 */               func_70685_l((Entity)entity))
/*     */             {
/* 365 */               if (isInAttackable(entity, ac, ws, pos)) {
/*     */                 
/* 367 */                 nextTarget = entity;
/* 368 */                 this.switchTargetCount = 60;
/*     */               } 
/*     */             }
/*     */           }
/*     */         }
/*     */       } 
/*     */       
/* 375 */       if (nextTarget != null && this.targetEntity != nextTarget) {
/*     */         
/* 377 */         this.targetPrevPosX = nextTarget.field_70165_t;
/* 378 */         this.targetPrevPosY = nextTarget.field_70163_u;
/* 379 */         this.targetPrevPosZ = nextTarget.field_70161_v;
/*     */       } 
/*     */       
/* 382 */       this.targetEntity = (Entity)nextTarget;
/*     */     } 
/*     */     
/* 385 */     if (this.targetEntity != null) {
/*     */       
/* 387 */       float rotSpeed = 10.0F;
/*     */       
/* 389 */       if (ac.isPilot((Entity)this))
/*     */       {
/* 391 */         rotSpeed = (ac.getAcInfo()).cameraRotationSpeed / 10.0F;
/*     */       }
/*     */       
/* 394 */       this.field_70125_A = MathHelper.func_76142_g(this.field_70125_A);
/* 395 */       this.field_70177_z = MathHelper.func_76142_g(this.field_70177_z);
/*     */       
/* 397 */       double dist = func_70032_d(this.targetEntity);
/* 398 */       double tick = 1.0D;
/*     */       
/* 400 */       if (dist >= 10.0D && (ws.getInfo()).acceleration > 1.0F)
/*     */       {
/* 402 */         tick = dist / (ws.getInfo()).acceleration;
/*     */       }
/*     */       
/* 405 */       if (this.targetEntity.func_184187_bx() instanceof MCH_EntitySeat || this.targetEntity
/* 406 */         .func_184187_bx() instanceof MCH_EntityAircraft)
/*     */       {
/* 408 */         tick -= MCH_Config.HitBoxDelayTick.prmInt;
/*     */       }
/*     */       
/* 411 */       double dx = (this.targetEntity.field_70165_t - this.targetPrevPosX) * tick;
/*     */       
/* 413 */       double dy = (this.targetEntity.field_70163_u - this.targetPrevPosY) * tick + this.targetEntity.field_70131_O * this.field_70146_Z.nextDouble();
/* 414 */       double dz = (this.targetEntity.field_70161_v - this.targetPrevPosZ) * tick;
/*     */       
/* 416 */       double d0 = this.targetEntity.field_70165_t + dx - pos.field_72450_a;
/* 417 */       double d1 = this.targetEntity.field_70163_u + dy - pos.field_72448_b;
/* 418 */       double d2 = this.targetEntity.field_70161_v + dz - pos.field_72449_c;
/* 419 */       double d3 = MathHelper.func_76133_a(d0 * d0 + d2 * d2);
/* 420 */       float yaw = MathHelper.func_76142_g((float)(Math.atan2(d2, d0) * 180.0D / Math.PI) - 90.0F);
/* 421 */       float pitch = (float)-(Math.atan2(d1, d3) * 180.0D / Math.PI);
/*     */       
/* 423 */       if (Math.abs(this.field_70125_A - pitch) < rotSpeed && Math.abs(this.field_70177_z - yaw) < rotSpeed) {
/*     */         
/* 425 */         float r = ac.isPilot((Entity)this) ? 0.1F : 0.5F;
/* 426 */         this.field_70125_A = pitch + (this.field_70146_Z.nextFloat() - 0.5F) * r - cw.fixRotationPitch;
/* 427 */         this.field_70177_z = yaw + (this.field_70146_Z.nextFloat() - 0.5F) * r;
/*     */         
/* 429 */         if (!this.waitCooldown || ws.currentHeat <= 0 || (ws.getInfo()).maxHeatCount <= 0) {
/*     */           
/* 431 */           this.waitCooldown = false;
/*     */           
/* 433 */           MCH_WeaponParam prm = new MCH_WeaponParam();
/*     */           
/* 435 */           prm.setPosition(ac.field_70165_t, ac.field_70163_u, ac.field_70161_v);
/* 436 */           prm.user = (Entity)this;
/* 437 */           prm.entity = (Entity)ac;
/* 438 */           prm.option1 = (cw instanceof mcheli.weapon.MCH_WeaponEntitySeeker) ? this.targetEntity.func_145782_y() : 0;
/*     */           
/* 440 */           if (ac.useCurrentWeapon(prm))
/*     */           {
/* 442 */             if ((ws.getInfo()).maxHeatCount > 0 && ws.currentHeat > (ws.getInfo()).maxHeatCount * 4 / 5)
/*     */             {
/* 444 */               this.waitCooldown = true;
/*     */             }
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 450 */       if (Math.abs(pitch - this.field_70125_A) >= rotSpeed)
/*     */       {
/* 452 */         this.field_70125_A += (pitch > this.field_70125_A) ? rotSpeed : -rotSpeed;
/*     */       }
/*     */       
/* 455 */       if (Math.abs(yaw - this.field_70177_z) >= rotSpeed)
/*     */       {
/* 457 */         if (Math.abs(yaw - this.field_70177_z) <= 180.0F) {
/*     */           
/* 459 */           this.field_70177_z += (yaw > this.field_70177_z) ? rotSpeed : -rotSpeed;
/*     */         }
/*     */         else {
/*     */           
/* 463 */           this.field_70177_z += (yaw > this.field_70177_z) ? -rotSpeed : rotSpeed;
/*     */         } 
/*     */       }
/*     */       
/* 467 */       this.field_70759_as = this.field_70177_z;
/* 468 */       this.targetPrevPosX = this.targetEntity.field_70165_t;
/* 469 */       this.targetPrevPosY = this.targetEntity.field_70163_u;
/* 470 */       this.targetPrevPosZ = this.targetEntity.field_70161_v;
/*     */     }
/*     */     else {
/*     */       
/* 474 */       this.field_70125_A *= 0.95F;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean checkPitch(EntityLivingBase entity, MCH_EntityAircraft ac, Vec3d pos) {
/*     */     try {
/* 482 */       double d0 = entity.field_70165_t - pos.field_72450_a;
/* 483 */       double d1 = entity.field_70163_u - pos.field_72448_b;
/* 484 */       double d2 = entity.field_70161_v - pos.field_72449_c;
/* 485 */       double d3 = MathHelper.func_76133_a(d0 * d0 + d2 * d2);
/* 486 */       float pitch = (float)-(Math.atan2(d1, d3) * 180.0D / Math.PI);
/* 487 */       MCH_AircraftInfo ai = ac.getAcInfo();
/*     */       
/* 489 */       if (ac instanceof mcheli.vehicle.MCH_EntityVehicle && ac.isPilot((Entity)this))
/*     */       {
/* 491 */         if (Math.abs(ai.minRotationPitch) + Math.abs(ai.maxRotationPitch) > 0.0F) {
/*     */           
/* 493 */           if (pitch < ai.minRotationPitch) {
/* 494 */             return false;
/*     */           }
/* 496 */           if (pitch > ai.maxRotationPitch) {
/* 497 */             return false;
/*     */           }
/*     */         } 
/*     */       }
/* 501 */       MCH_WeaponBase cw = ac.getCurrentWeapon((Entity)this).getCurrentWeapon();
/*     */       
/* 503 */       if (!(cw instanceof mcheli.weapon.MCH_WeaponEntitySeeker)) {
/*     */         
/* 505 */         MCH_AircraftInfo.Weapon wi = ai.getWeaponById(ac.getCurrentWeaponID((Entity)this));
/*     */         
/* 507 */         if (Math.abs(wi.minPitch) + Math.abs(wi.maxPitch) > 0.0F)
/*     */         {
/* 509 */           if (pitch < wi.minPitch) {
/* 510 */             return false;
/*     */           }
/* 512 */           if (pitch > wi.maxPitch)
/*     */           {
/* 514 */             return false;
/*     */           }
/*     */         }
/*     */       
/*     */       } 
/* 519 */     } catch (Exception exception) {}
/*     */ 
/*     */ 
/*     */     
/* 523 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public Vec3d getGunnerWeaponPos(MCH_EntityAircraft ac, MCH_WeaponSet ws) {
/* 528 */     MCH_SeatInfo seatInfo = ac.getSeatInfo((Entity)this);
/*     */     
/* 530 */     if ((seatInfo != null && seatInfo.rotSeat) || ac instanceof mcheli.vehicle.MCH_EntityVehicle)
/*     */     {
/* 532 */       return ac.calcOnTurretPos((ws.getCurrentWeapon()).position).func_72441_c(ac.field_70165_t, ac.field_70163_u, ac.field_70161_v);
/*     */     }
/*     */     
/* 535 */     return ac.getTransformedPosition((ws.getCurrentWeapon()).position);
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isInAttackable(EntityLivingBase entity, MCH_EntityAircraft ac, MCH_WeaponSet ws, Vec3d pos) {
/* 540 */     if (ac instanceof mcheli.vehicle.MCH_EntityVehicle)
/*     */     {
/* 542 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 547 */       if (ac.getCurrentWeapon((Entity)this).getCurrentWeapon() instanceof mcheli.weapon.MCH_WeaponEntitySeeker)
/*     */       {
/* 549 */         return true;
/*     */       }
/*     */       
/* 552 */       MCH_AircraftInfo.Weapon wi = ac.getAcInfo().getWeaponById(ac.getCurrentWeaponID((Entity)this));
/* 553 */       Vec3d v1 = new Vec3d(0.0D, 0.0D, 1.0D);
/* 554 */       float yaw = -ac.getRotYaw() + (wi.maxYaw + wi.minYaw) / 2.0F - wi.defaultYaw;
/*     */       
/* 556 */       v1 = v1.func_178785_b(yaw * 3.1415927F / 180.0F);
/*     */       
/* 558 */       Vec3d v2 = (new Vec3d(entity.field_70165_t - pos.field_72450_a, 0.0D, entity.field_70161_v - pos.field_72449_c)).func_72432_b();
/* 559 */       double dot = v1.func_72430_b(v2);
/* 560 */       double rad = Math.acos(dot);
/* 561 */       double deg = rad * 180.0D / Math.PI;
/*     */       
/* 563 */       return (deg < (Math.abs(wi.maxYaw - wi.minYaw) / 2.0F));
/*     */     }
/* 565 */     catch (Exception exception) {
/*     */ 
/*     */ 
/*     */       
/* 569 */       return false;
/*     */     } 
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public MCH_EntityAircraft getAc() {
/* 575 */     if (func_184187_bx() == null) {
/* 576 */       return null;
/*     */     }
/* 578 */     return (func_184187_bx() instanceof MCH_EntityAircraft) ? (MCH_EntityAircraft)func_184187_bx() : (
/* 579 */       (func_184187_bx() instanceof MCH_EntitySeat) ? ((MCH_EntitySeat)
/* 580 */       func_184187_bx()).getParent() : null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_70014_b(NBTTagCompound nbt) {
/* 587 */     super.func_70014_b(nbt);
/* 588 */     nbt.func_74757_a("Creative", this.isCreative);
/* 589 */     nbt.func_74778_a("OwnerUUID", this.ownerUUID);
/* 590 */     nbt.func_74778_a("TeamName", getTeamName());
/* 591 */     nbt.func_74768_a("TargetType", this.targetType);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_70037_a(NBTTagCompound nbt) {
/* 597 */     super.func_70037_a(nbt);
/* 598 */     this.isCreative = nbt.func_74767_n("Creative");
/* 599 */     this.ownerUUID = nbt.func_74779_i("OwnerUUID");
/* 600 */     setTeamName(nbt.func_74779_i("TeamName"));
/* 601 */     this.targetType = nbt.func_74762_e("TargetType");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Entity changeDimension(int dimensionIn, ITeleporter teleporter) {
/* 609 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_70106_y() {
/* 615 */     if (!this.field_70170_p.field_72995_K && !this.field_70128_L && !this.isCreative)
/*     */     {
/* 617 */       if (this.targetType == 0) {
/*     */         
/* 619 */         func_145779_a((Item)MCH_MOD.itemSpawnGunnerVsMonster, 1);
/*     */       }
/*     */       else {
/*     */         
/* 623 */         func_145779_a((Item)MCH_MOD.itemSpawnGunnerVsPlayer, 1);
/*     */       } 
/*     */     }
/*     */     
/* 627 */     super.func_70106_y();
/*     */     
/* 629 */     MCH_Lib.DbgLog(this.field_70170_p, "MCH_EntityGunner.setDead type=%d :" + toString(), new Object[] {
/*     */           
/* 631 */           Integer.valueOf(this.targetType)
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_70097_a(DamageSource ds, float amount) {
/* 638 */     if (ds == DamageSource.field_76380_i)
/*     */     {
/* 640 */       func_70106_y();
/*     */     }
/*     */     
/* 643 */     return super.func_70097_a(ds, amount);
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
/*     */   public ItemStack func_184582_a(EntityEquipmentSlot slotIn) {
/* 655 */     return ItemStack.field_190927_a;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_184201_a(EntityEquipmentSlot slotIn, ItemStack stack) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterable<ItemStack> func_184193_aE() {
/* 672 */     return Collections.emptyList();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumHandSide func_184591_cq() {
/* 678 */     return EnumHandSide.RIGHT;
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\mob\MCH_EntityGunner.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */