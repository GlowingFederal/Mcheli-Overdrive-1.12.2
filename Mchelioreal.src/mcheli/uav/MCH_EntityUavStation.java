/*     */ package mcheli.uav;
/*     */ 
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import mcheli.MCH_Config;
/*     */ import mcheli.MCH_Explosion;
/*     */ import mcheli.MCH_Lib;
/*     */ import mcheli.MCH_MOD;
/*     */ import mcheli.__helper.entity.IEntityItemStackPickable;
/*     */ import mcheli.__helper.entity.IEntitySinglePassenger;
/*     */ import mcheli.__helper.network.PooledGuiParameter;
/*     */ import mcheli.aircraft.MCH_EntityAircraft;
/*     */ import mcheli.helicopter.MCH_EntityHeli;
/*     */ import mcheli.helicopter.MCH_HeliInfo;
/*     */ import mcheli.helicopter.MCH_HeliInfoManager;
/*     */ import mcheli.helicopter.MCH_ItemHeli;
/*     */ import mcheli.multiplay.MCH_Multiplay;
/*     */ import mcheli.plane.MCP_EntityPlane;
/*     */ import mcheli.plane.MCP_ItemPlane;
/*     */ import mcheli.plane.MCP_PlaneInfo;
/*     */ import mcheli.plane.MCP_PlaneInfoManager;
/*     */ import mcheli.tank.MCH_EntityTank;
/*     */ import mcheli.tank.MCH_ItemTank;
/*     */ import mcheli.tank.MCH_TankInfo;
/*     */ import mcheli.tank.MCH_TankInfoManager;
/*     */ import mcheli.wrapper.W_Entity;
/*     */ import mcheli.wrapper.W_EntityContainer;
/*     */ import mcheli.wrapper.W_EntityPlayer;
/*     */ import mcheli.wrapper.W_WorldFunc;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.MoverType;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.network.datasync.DataParameter;
/*     */ import net.minecraft.network.datasync.DataSerializers;
/*     */ import net.minecraft.network.datasync.EntityDataManager;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MCH_EntityUavStation
/*     */   extends W_EntityContainer
/*     */   implements IEntitySinglePassenger, IEntityItemStackPickable
/*     */ {
/*     */   public static final float Y_OFFSET = 0.35F;
/*  60 */   private static final DataParameter<Byte> STATUS = EntityDataManager.func_187226_a(MCH_EntityUavStation.class, DataSerializers.field_187191_a);
/*     */   
/*  62 */   private static final DataParameter<Integer> LAST_AC_ID = EntityDataManager.func_187226_a(MCH_EntityUavStation.class, DataSerializers.field_187192_b);
/*     */   
/*  64 */   private static final DataParameter<BlockPos> UAV_POS = EntityDataManager.func_187226_a(MCH_EntityUavStation.class, DataSerializers.field_187200_j);
/*     */   
/*     */   protected Entity lastRiddenByEntity;
/*     */   
/*     */   public boolean isRequestedSyncStatus;
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   protected double velocityX;
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   protected double velocityY;
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   protected double velocityZ;
/*     */   
/*     */   protected int aircraftPosRotInc;
/*     */   
/*     */   protected double aircraftX;
/*     */   
/*     */   protected double aircraftY;
/*     */   protected double aircraftZ;
/*     */   protected double aircraftYaw;
/*     */   protected double aircraftPitch;
/*     */   private MCH_EntityAircraft controlAircraft;
/*     */   private MCH_EntityAircraft lastControlAircraft;
/*     */   private String loadedLastControlAircraftGuid;
/*     */   public int posUavX;
/*     */   public int posUavY;
/*     */   public int posUavZ;
/*     */   public float rotCover;
/*     */   public float prevRotCover;
/*     */   
/*     */   public MCH_EntityUavStation(World world) {
/*  97 */     super(world);
/*  98 */     this.dropContentsWhenDead = false;
/*  99 */     this.field_70156_m = true;
/* 100 */     func_70105_a(2.0F, 0.7F);
/*     */     
/* 102 */     this.field_70159_w = 0.0D;
/* 103 */     this.field_70181_x = 0.0D;
/* 104 */     this.field_70179_y = 0.0D;
/* 105 */     this.field_70158_ak = true;
/*     */     
/* 107 */     this.lastRiddenByEntity = null;
/*     */     
/* 109 */     this.aircraftPosRotInc = 0;
/* 110 */     this.aircraftX = 0.0D;
/* 111 */     this.aircraftY = 0.0D;
/* 112 */     this.aircraftZ = 0.0D;
/* 113 */     this.aircraftYaw = 0.0D;
/* 114 */     this.aircraftPitch = 0.0D;
/*     */     
/* 116 */     this.posUavX = 0;
/* 117 */     this.posUavY = 0;
/* 118 */     this.posUavZ = 0;
/*     */     
/* 120 */     this.rotCover = 0.0F;
/* 121 */     this.prevRotCover = 0.0F;
/*     */     
/* 123 */     setControlAircract((MCH_EntityAircraft)null);
/* 124 */     setLastControlAircraft((MCH_EntityAircraft)null);
/* 125 */     this.loadedLastControlAircraftGuid = "";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_70088_a() {
/* 131 */     super.func_70088_a();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 137 */     this.field_70180_af.func_187214_a(STATUS, Byte.valueOf((byte)0));
/* 138 */     this.field_70180_af.func_187214_a(LAST_AC_ID, Integer.valueOf(0));
/* 139 */     this.field_70180_af.func_187214_a(UAV_POS, BlockPos.field_177992_a);
/*     */     
/* 141 */     setOpen(true);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getStatus() {
/* 147 */     return ((Byte)this.field_70180_af.func_187225_a(STATUS)).byteValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setStatus(int n) {
/* 152 */     if (!this.field_70170_p.field_72995_K) {
/*     */       
/* 154 */       MCH_Lib.DbgLog(this.field_70170_p, "MCH_EntityUavStation.setStatus(%d)", new Object[] {
/*     */             
/* 156 */             Integer.valueOf(n)
/*     */           });
/*     */       
/* 159 */       this.field_70180_af.func_187227_b(STATUS, Byte.valueOf((byte)n));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int getKind() {
/* 165 */     return 0x7F & getStatus();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setKind(int n) {
/* 170 */     setStatus(getStatus() & 0x80 | n);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isOpen() {
/* 175 */     return ((getStatus() & 0x80) != 0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOpen(boolean b) {
/* 181 */     setStatus((b ? 128 : 0) | getStatus() & 0x7F);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public MCH_EntityAircraft getControlAircract() {
/* 187 */     return this.controlAircraft;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setControlAircract(@Nullable MCH_EntityAircraft ac) {
/* 192 */     this.controlAircraft = ac;
/*     */     
/* 194 */     if (ac != null && !ac.field_70128_L)
/*     */     {
/* 196 */       setLastControlAircraft(ac);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void setUavPosition(int x, int y, int z) {
/* 202 */     if (!this.field_70170_p.field_72995_K) {
/*     */       
/* 204 */       this.posUavX = x;
/* 205 */       this.posUavY = y;
/* 206 */       this.posUavZ = z;
/*     */ 
/*     */ 
/*     */       
/* 210 */       this.field_70180_af.func_187227_b(UAV_POS, new BlockPos(x, y, z));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateUavPosition() {
/* 219 */     BlockPos uavPos = (BlockPos)this.field_70180_af.func_187225_a(UAV_POS);
/* 220 */     this.posUavX = uavPos.func_177958_n();
/* 221 */     this.posUavY = uavPos.func_177956_o();
/* 222 */     this.posUavZ = uavPos.func_177952_p();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_70014_b(NBTTagCompound nbt) {
/* 228 */     super.func_70014_b(nbt);
/*     */     
/* 230 */     nbt.func_74768_a("UavStatus", getStatus());
/* 231 */     nbt.func_74768_a("PosUavX", this.posUavX);
/* 232 */     nbt.func_74768_a("PosUavY", this.posUavY);
/* 233 */     nbt.func_74768_a("PosUavZ", this.posUavZ);
/*     */     
/* 235 */     String s = "";
/*     */     
/* 237 */     if (getLastControlAircraft() != null && !(getLastControlAircraft()).field_70128_L)
/*     */     {
/* 239 */       s = getLastControlAircraft().getCommonUniqueId();
/*     */     }
/*     */     
/* 242 */     if (s.isEmpty())
/*     */     {
/* 244 */       s = this.loadedLastControlAircraftGuid;
/*     */     }
/*     */     
/* 247 */     nbt.func_74778_a("LastCtrlAc", s);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_70037_a(NBTTagCompound nbt) {
/* 253 */     super.func_70037_a(nbt);
/*     */     
/* 255 */     setUavPosition(nbt.func_74762_e("PosUavX"), nbt.func_74762_e("PosUavY"), nbt.func_74762_e("PosUavZ"));
/*     */     
/* 257 */     if (nbt.func_74764_b("UavStatus")) {
/*     */       
/* 259 */       setStatus(nbt.func_74762_e("UavStatus"));
/*     */     }
/*     */     else {
/*     */       
/* 263 */       setKind(1);
/*     */     } 
/*     */     
/* 266 */     this.loadedLastControlAircraftGuid = nbt.func_74779_i("LastCtrlAc");
/*     */   }
/*     */ 
/*     */   
/*     */   public void initUavPostion() {
/* 271 */     int rt = (int)(MCH_Lib.getRotate360((this.field_70177_z + 45.0F)) / 90.0D);
/*     */     
/* 273 */     this.posUavX = (rt == 0 || rt == 3) ? 12 : -12;
/* 274 */     this.posUavZ = (rt == 0 || rt == 1) ? 12 : -12;
/* 275 */     this.posUavY = 2;
/*     */     
/* 277 */     setUavPosition(this.posUavX, this.posUavY, this.posUavZ);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_70106_y() {
/* 283 */     super.func_70106_y();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_70097_a(DamageSource damageSource, float damage) {
/* 290 */     if (func_180431_b(damageSource)) {
/* 291 */       return false;
/*     */     }
/* 293 */     if (this.field_70128_L) {
/* 294 */       return true;
/*     */     }
/* 296 */     if (this.field_70170_p.field_72995_K) {
/* 297 */       return true;
/*     */     }
/* 299 */     String dmt = damageSource.func_76355_l();
/*     */     
/* 301 */     damage = MCH_Config.applyDamageByExternal((Entity)this, damageSource, damage);
/*     */     
/* 303 */     if (!MCH_Multiplay.canAttackEntity(damageSource, (Entity)this))
/*     */     {
/* 305 */       return false;
/*     */     }
/*     */     
/* 308 */     boolean isCreative = false;
/* 309 */     Entity entity = damageSource.func_76346_g();
/* 310 */     boolean isDamegeSourcePlayer = false;
/*     */     
/* 312 */     if (entity instanceof EntityPlayer) {
/*     */       
/* 314 */       isCreative = ((EntityPlayer)entity).field_71075_bZ.field_75098_d;
/*     */       
/* 316 */       if (dmt.compareTo("player") == 0)
/*     */       {
/* 318 */         isDamegeSourcePlayer = true;
/*     */       }
/*     */       
/* 321 */       W_WorldFunc.MOD_playSoundAtEntity((Entity)this, "hit", 1.0F, 1.0F);
/*     */     }
/*     */     else {
/*     */       
/* 325 */       W_WorldFunc.MOD_playSoundAtEntity((Entity)this, "helidmg", 1.0F, 0.9F + this.field_70146_Z.nextFloat() * 0.1F);
/*     */     } 
/*     */     
/* 328 */     func_70018_K();
/*     */     
/* 330 */     if (damage > 0.0F) {
/*     */       
/* 332 */       Entity riddenByEntity = getRiddenByEntity();
/*     */ 
/*     */       
/* 335 */       if (riddenByEntity != null)
/*     */       {
/*     */         
/* 338 */         riddenByEntity.func_184220_m((Entity)this);
/*     */       }
/*     */       
/* 341 */       this.dropContentsWhenDead = true;
/* 342 */       func_70106_y();
/*     */       
/* 344 */       if (!isDamegeSourcePlayer)
/*     */       {
/*     */         
/* 347 */         MCH_Explosion.newExplosion(this.field_70170_p, null, riddenByEntity, this.field_70165_t, this.field_70163_u, this.field_70161_v, 1.0F, 0.0F, true, true, false, false, 0);
/*     */       }
/*     */ 
/*     */       
/* 351 */       if (!isCreative) {
/*     */         
/* 353 */         int kind = getKind();
/*     */         
/* 355 */         if (kind > 0)
/*     */         {
/* 357 */           func_145778_a((Item)MCH_MOD.itemUavStation[kind - 1], 1, 0.0F);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 362 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean func_70041_e_() {
/* 368 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AxisAlignedBB func_70114_g(Entity par1Entity) {
/* 375 */     return par1Entity.func_174813_aQ();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AxisAlignedBB func_70046_E() {
/* 382 */     return func_174813_aQ();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_70104_M() {
/* 388 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double func_70042_X() {
/* 394 */     Entity riddenByEntity = getRiddenByEntity();
/*     */     
/* 396 */     if (getKind() == 2 && riddenByEntity != null) {
/*     */       
/* 398 */       double px = -Math.sin(this.field_70177_z * Math.PI / 180.0D) * 0.9D;
/* 399 */       double pz = Math.cos(this.field_70177_z * Math.PI / 180.0D) * 0.9D;
/* 400 */       int x = (int)(this.field_70165_t + px);
/* 401 */       int y = (int)(this.field_70163_u - 0.5D);
/* 402 */       int z = (int)(this.field_70161_v + pz);
/* 403 */       BlockPos blockpos = new BlockPos(x, y, z);
/*     */       
/* 405 */       IBlockState iblockstate = this.field_70170_p.func_180495_p(blockpos);
/*     */       
/* 407 */       return iblockstate.func_185914_p() ? -0.4D : -0.9D;
/*     */     } 
/* 409 */     return 0.35D;
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public float getShadowSize() {
/* 415 */     return 2.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_70067_L() {
/* 421 */     return !this.field_70128_L;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_70108_f(Entity par1Entity) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_70024_g(double par1, double par3, double par5) {}
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_70016_h(double par1, double par3, double par5) {
/* 438 */     this.velocityX = this.field_70159_w = par1;
/* 439 */     this.velocityY = this.field_70181_x = par3;
/* 440 */     this.velocityZ = this.field_70179_y = par5;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_70071_h_() {
/* 446 */     super.func_70071_h_();
/*     */     
/* 448 */     this.prevRotCover = this.rotCover;
/*     */     
/* 450 */     if (isOpen()) {
/*     */       
/* 452 */       if (this.rotCover < 1.0F)
/*     */       {
/* 454 */         this.rotCover += 0.1F;
/*     */       }
/*     */       else
/*     */       {
/* 458 */         this.rotCover = 1.0F;
/*     */       }
/*     */     
/* 461 */     } else if (this.rotCover > 0.0F) {
/*     */       
/* 463 */       this.rotCover -= 0.1F;
/*     */     }
/*     */     else {
/*     */       
/* 467 */       this.rotCover = 0.0F;
/*     */     } 
/*     */     
/* 470 */     Entity riddenByEntity = getRiddenByEntity();
/*     */ 
/*     */     
/* 473 */     if (riddenByEntity == null) {
/*     */       
/* 475 */       if (this.lastRiddenByEntity != null)
/*     */       {
/* 477 */         unmountEntity(true);
/*     */       }
/* 479 */       setControlAircract((MCH_EntityAircraft)null);
/*     */     } 
/*     */     
/* 482 */     int uavStationKind = getKind();
/*     */     
/* 484 */     if (this.field_70173_aa >= 30 || uavStationKind <= 0 || uavStationKind == 1 || uavStationKind != 2 || (this.field_70170_p.field_72995_K && !this.isRequestedSyncStatus))
/*     */     {
/*     */       
/* 487 */       this.isRequestedSyncStatus = true;
/*     */     }
/*     */     
/* 490 */     this.field_70169_q = this.field_70165_t;
/* 491 */     this.field_70167_r = this.field_70163_u;
/* 492 */     this.field_70166_s = this.field_70161_v;
/*     */     
/* 494 */     if (getControlAircract() != null && (getControlAircract()).field_70128_L)
/*     */     {
/* 496 */       setControlAircract((MCH_EntityAircraft)null);
/*     */     }
/*     */     
/* 499 */     if (getLastControlAircraft() != null && (getLastControlAircraft()).field_70128_L)
/*     */     {
/* 501 */       setLastControlAircraft((MCH_EntityAircraft)null);
/*     */     }
/*     */     
/* 504 */     if (this.field_70170_p.field_72995_K) {
/*     */       
/* 506 */       onUpdate_Client();
/*     */     }
/*     */     else {
/*     */       
/* 510 */       onUpdate_Server();
/*     */     } 
/*     */ 
/*     */     
/* 514 */     this.lastRiddenByEntity = getRiddenByEntity();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public MCH_EntityAircraft getLastControlAircraft() {
/* 520 */     return this.lastControlAircraft;
/*     */   }
/*     */ 
/*     */   
/*     */   public MCH_EntityAircraft getAndSearchLastControlAircraft() {
/* 525 */     if (getLastControlAircraft() == null) {
/*     */       
/* 527 */       int id = getLastControlAircraftEntityId().intValue();
/*     */       
/* 529 */       if (id > 0) {
/*     */         
/* 531 */         Entity entity = this.field_70170_p.func_73045_a(id);
/*     */         
/* 533 */         if (entity instanceof MCH_EntityAircraft) {
/*     */           
/* 535 */           MCH_EntityAircraft ac = (MCH_EntityAircraft)entity;
/*     */           
/* 537 */           if (ac.isUAV())
/*     */           {
/* 539 */             setLastControlAircraft(ac);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/* 544 */     return getLastControlAircraft();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setLastControlAircraft(MCH_EntityAircraft ac) {
/* 549 */     MCH_Lib.DbgLog(this.field_70170_p, "MCH_EntityUavStation.setLastControlAircraft:" + ac, new Object[0]);
/* 550 */     this.lastControlAircraft = ac;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Integer getLastControlAircraftEntityId() {
/* 556 */     return (Integer)this.field_70180_af.func_187225_a(LAST_AC_ID);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setLastControlAircraftEntityId(int s) {
/* 561 */     if (!this.field_70170_p.field_72995_K)
/*     */     {
/*     */       
/* 564 */       this.field_70180_af.func_187227_b(LAST_AC_ID, Integer.valueOf(s));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void searchLastControlAircraft() {
/* 570 */     if (this.loadedLastControlAircraftGuid.isEmpty()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 575 */     List<MCH_EntityAircraft> list = this.field_70170_p.func_72872_a(MCH_EntityAircraft.class, 
/* 576 */         func_70046_E().func_72314_b(120.0D, 120.0D, 120.0D));
/*     */     
/* 578 */     if (list == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 583 */     for (int i = 0; i < list.size(); i++) {
/*     */       
/* 585 */       MCH_EntityAircraft ac = list.get(i);
/*     */       
/* 587 */       if (ac.getCommonUniqueId().equals(this.loadedLastControlAircraftGuid)) {
/*     */         
/* 589 */         String n = "no info : " + ac;
/* 590 */         MCH_Lib.DbgLog(this.field_70170_p, "MCH_EntityUavStation.searchLastControlAircraft:found" + n, new Object[0]);
/* 591 */         setLastControlAircraft(ac);
/* 592 */         setLastControlAircraftEntityId(W_Entity.getEntityId((Entity)ac));
/* 593 */         this.loadedLastControlAircraftGuid = "";
/*     */         return;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onUpdate_Client() {
/* 601 */     if (this.aircraftPosRotInc > 0) {
/*     */       
/* 603 */       double rpinc = this.aircraftPosRotInc;
/* 604 */       double yaw = MathHelper.func_76138_g(this.aircraftYaw - this.field_70177_z);
/*     */       
/* 606 */       this.field_70177_z = (float)(this.field_70177_z + yaw / rpinc);
/* 607 */       this.field_70125_A = (float)(this.field_70125_A + (this.aircraftPitch - this.field_70125_A) / rpinc);
/* 608 */       func_70107_b(this.field_70165_t + (this.aircraftX - this.field_70165_t) / rpinc, this.field_70163_u + (this.aircraftY - this.field_70163_u) / rpinc, this.field_70161_v + (this.aircraftZ - this.field_70161_v) / rpinc);
/*     */ 
/*     */       
/* 611 */       func_70101_b(this.field_70177_z, this.field_70125_A);
/* 612 */       this.aircraftPosRotInc--;
/*     */     }
/*     */     else {
/*     */       
/* 616 */       func_70107_b(this.field_70165_t + this.field_70159_w, this.field_70163_u + this.field_70181_x, this.field_70161_v + this.field_70179_y);
/*     */       
/* 618 */       this.field_70181_x *= 0.96D;
/* 619 */       this.field_70159_w = 0.0D;
/* 620 */       this.field_70179_y = 0.0D;
/*     */     } 
/*     */     
/* 623 */     updateUavPosition();
/*     */   }
/*     */ 
/*     */   
/*     */   private void onUpdate_Server() {
/* 628 */     this.field_70181_x -= 0.03D;
/*     */ 
/*     */     
/* 631 */     func_70091_d(MoverType.SELF, 0.0D, this.field_70181_x, 0.0D);
/*     */     
/* 633 */     this.field_70181_x *= 0.96D;
/* 634 */     this.field_70159_w = 0.0D;
/* 635 */     this.field_70179_y = 0.0D;
/*     */     
/* 637 */     func_70101_b(this.field_70177_z, this.field_70125_A);
/*     */     
/* 639 */     Entity riddenByEntity = getRiddenByEntity();
/*     */ 
/*     */     
/* 642 */     if (riddenByEntity != null)
/*     */     {
/*     */       
/* 645 */       if (riddenByEntity.field_70128_L) {
/*     */         
/* 647 */         unmountEntity(true);
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 652 */         ItemStack item = func_70301_a(0);
/*     */ 
/*     */         
/* 655 */         if (!item.func_190926_b()) {
/*     */ 
/*     */           
/* 658 */           handleItem(riddenByEntity, item);
/*     */ 
/*     */           
/* 661 */           if (item.func_190916_E() == 0)
/*     */           {
/*     */             
/* 664 */             func_70299_a(0, ItemStack.field_190927_a);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 670 */     if (getLastControlAircraft() == null)
/*     */     {
/* 672 */       if (this.field_70173_aa % 40 == 0)
/*     */       {
/* 674 */         searchLastControlAircraft();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_180426_a(double par1, double par3, double par5, float par7, float par8, int par9, boolean teleport) {
/* 684 */     this.aircraftPosRotInc = par9 + 8;
/*     */     
/* 686 */     this.aircraftX = par1;
/* 687 */     this.aircraftY = par3;
/* 688 */     this.aircraftZ = par5;
/* 689 */     this.aircraftYaw = par7;
/* 690 */     this.aircraftPitch = par8;
/* 691 */     this.field_70159_w = this.velocityX;
/* 692 */     this.field_70181_x = this.velocityY;
/* 693 */     this.field_70179_y = this.velocityZ;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_184232_k(Entity passenger) {
/* 701 */     if (func_184196_w(passenger)) {
/*     */       
/* 703 */       double x = -Math.sin(this.field_70177_z * Math.PI / 180.0D) * 0.9D;
/* 704 */       double z = Math.cos(this.field_70177_z * Math.PI / 180.0D) * 0.9D;
/*     */ 
/*     */       
/* 707 */       passenger.func_70107_b(this.field_70165_t + x, this.field_70163_u + func_70042_X() + passenger.func_70033_W() + 0.3499999940395355D, this.field_70161_v + z);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void controlLastAircraft(Entity user) {
/* 714 */     if (getLastControlAircraft() != null && !(getLastControlAircraft()).field_70128_L) {
/*     */       
/* 716 */       getLastControlAircraft().setUavStation(this);
/* 717 */       setControlAircract(getLastControlAircraft());
/*     */       
/* 719 */       W_EntityPlayer.closeScreen(user);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void handleItem(@Nullable Entity user, ItemStack itemStack) {
/*     */     MCH_EntityTank mCH_EntityTank;
/* 726 */     if (user == null || user.field_70128_L || itemStack.func_190926_b() || itemStack.func_190916_E() != 1) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 731 */     if (this.field_70170_p.field_72995_K) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 736 */     MCH_EntityAircraft ac = null;
/* 737 */     double x = this.field_70165_t + this.posUavX;
/* 738 */     double y = this.field_70163_u + this.posUavY;
/* 739 */     double z = this.field_70161_v + this.posUavZ;
/*     */     
/* 741 */     if (y <= 1.0D)
/*     */     {
/* 743 */       y = 2.0D;
/*     */     }
/*     */     
/* 746 */     Item item = itemStack.func_77973_b();
/*     */     
/* 748 */     if (item instanceof MCP_ItemPlane) {
/*     */       
/* 750 */       MCP_PlaneInfo pi = MCP_PlaneInfoManager.getFromItem(item);
/*     */       
/* 752 */       if (pi != null && pi.isUAV)
/*     */       {
/* 754 */         if (!pi.isSmallUAV && getKind() == 2) {
/*     */           
/* 756 */           ac = null;
/*     */         }
/*     */         else {
/*     */           
/* 760 */           MCP_EntityPlane mCP_EntityPlane = ((MCP_ItemPlane)item).createAircraft(this.field_70170_p, x, y, z, itemStack);
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 765 */     if (item instanceof MCH_ItemHeli) {
/*     */       
/* 767 */       MCH_HeliInfo hi = MCH_HeliInfoManager.getFromItem(item);
/*     */       
/* 769 */       if (hi != null && hi.isUAV)
/*     */       {
/* 771 */         if (!hi.isSmallUAV && getKind() == 2) {
/*     */           
/* 773 */           ac = null;
/*     */         }
/*     */         else {
/*     */           
/* 777 */           MCH_EntityHeli mCH_EntityHeli = ((MCH_ItemHeli)item).createAircraft(this.field_70170_p, x, y, z, itemStack);
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 782 */     if (item instanceof MCH_ItemTank) {
/*     */       
/* 784 */       MCH_TankInfo hi = MCH_TankInfoManager.getFromItem(item);
/*     */       
/* 786 */       if (hi != null && hi.isUAV)
/*     */       {
/* 788 */         if (!hi.isSmallUAV && getKind() == 2) {
/*     */           
/* 790 */           ac = null;
/*     */         }
/*     */         else {
/*     */           
/* 794 */           mCH_EntityTank = ((MCH_ItemTank)item).createAircraft(this.field_70170_p, x, y, z, itemStack);
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 799 */     if (mCH_EntityTank == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 804 */     ((MCH_EntityAircraft)mCH_EntityTank).field_70177_z = this.field_70177_z - 180.0F;
/* 805 */     ((MCH_EntityAircraft)mCH_EntityTank).field_70126_B = ((MCH_EntityAircraft)mCH_EntityTank).field_70177_z;
/* 806 */     user.field_70177_z = this.field_70177_z - 180.0F;
/*     */ 
/*     */     
/* 809 */     if (this.field_70170_p.func_184144_a((Entity)mCH_EntityTank, mCH_EntityTank.func_174813_aQ().func_72314_b(-0.1D, -0.1D, -0.1D)).isEmpty()) {
/*     */ 
/*     */       
/* 812 */       itemStack.func_190918_g(1);
/*     */       
/* 814 */       MCH_Lib.DbgLog(this.field_70170_p, "Create UAV: %s : %s", new Object[] { item
/*     */             
/* 816 */             .func_77658_a(), item });
/*     */ 
/*     */       
/* 819 */       user.field_70177_z = this.field_70177_z - 180.0F;
/*     */       
/* 821 */       if (!mCH_EntityTank.isTargetDrone()) {
/*     */         
/* 823 */         mCH_EntityTank.setUavStation(this);
/* 824 */         setControlAircract((MCH_EntityAircraft)mCH_EntityTank);
/*     */       } 
/*     */       
/* 827 */       this.field_70170_p.func_72838_d((Entity)mCH_EntityTank);
/*     */       
/* 829 */       if (!mCH_EntityTank.isTargetDrone())
/*     */       {
/* 831 */         mCH_EntityTank.setFuel((int)(mCH_EntityTank.getMaxFuel() * 0.05F));
/* 832 */         W_EntityPlayer.closeScreen(user);
/*     */       }
/*     */       else
/*     */       {
/* 836 */         mCH_EntityTank.setFuel(mCH_EntityTank.getMaxFuel());
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 841 */       mCH_EntityTank.func_70106_y();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void _setInventorySlotContents(int par1, ItemStack itemStack) {
/* 847 */     func_70299_a(par1, itemStack);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_184230_a(EntityPlayer player, EnumHand hand) {
/* 854 */     if (hand != EnumHand.MAIN_HAND)
/*     */     {
/* 856 */       return false;
/*     */     }
/*     */     
/* 859 */     int kind = getKind();
/*     */     
/* 861 */     if (kind <= 0)
/*     */     {
/* 863 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 867 */     if (getRiddenByEntity() != null)
/*     */     {
/* 869 */       return false;
/*     */     }
/*     */     
/* 872 */     if (kind == 2) {
/*     */       
/* 874 */       if (player.func_70093_af()) {
/*     */         
/* 876 */         setOpen(!isOpen());
/* 877 */         return false;
/*     */       } 
/*     */       
/* 880 */       if (!isOpen())
/*     */       {
/* 882 */         return false;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 887 */     this.lastRiddenByEntity = null;
/*     */     
/* 889 */     PooledGuiParameter.setEntity(player, (Entity)this);
/*     */     
/* 891 */     if (!this.field_70170_p.field_72995_K) {
/*     */ 
/*     */       
/* 894 */       player.func_184220_m((Entity)this);
/* 895 */       player.openGui(MCH_MOD.instance, 0, player.field_70170_p, (int)this.field_70165_t, (int)this.field_70163_u, (int)this.field_70161_v);
/*     */     } 
/*     */     
/* 898 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int func_70302_i_() {
/* 904 */     return 1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int func_70297_j_() {
/* 910 */     return 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public void unmountEntity(boolean unmountAllEntity) {
/* 915 */     Entity rByEntity = null;
/* 916 */     Entity riddenByEntity = getRiddenByEntity();
/*     */ 
/*     */     
/* 919 */     if (riddenByEntity != null) {
/*     */       
/* 921 */       if (!this.field_70170_p.field_72995_K)
/*     */       {
/*     */ 
/*     */         
/* 925 */         rByEntity = riddenByEntity;
/* 926 */         riddenByEntity.func_184210_p();
/*     */       }
/*     */     
/* 929 */     } else if (this.lastRiddenByEntity != null) {
/*     */       
/* 931 */       rByEntity = this.lastRiddenByEntity;
/*     */     } 
/*     */     
/* 934 */     if (getControlAircract() != null)
/*     */     {
/* 936 */       getControlAircract().setUavStation(null);
/*     */     }
/*     */     
/* 939 */     setControlAircract((MCH_EntityAircraft)null);
/*     */     
/* 941 */     if (this.field_70170_p.field_72995_K)
/*     */     {
/* 943 */       W_EntityPlayer.closeScreen(rByEntity);
/*     */     }
/*     */ 
/*     */     
/* 947 */     this.lastRiddenByEntity = null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Entity getRiddenByEntity() {
/* 954 */     List<Entity> passengers = func_184188_bt();
/* 955 */     return passengers.isEmpty() ? null : passengers.get(0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getPickedResult(RayTraceResult target) {
/* 961 */     int kind = getKind();
/* 962 */     return (kind > 0) ? new ItemStack((Item)MCH_MOD.itemUavStation[kind - 1]) : ItemStack.field_190927_a;
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mchel\\uav\MCH_EntityUavStation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */