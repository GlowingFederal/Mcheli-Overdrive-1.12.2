/*      */ package mcheli.tank;
/*      */ 
/*      */ import java.util.List;
/*      */ import javax.annotation.Nullable;
/*      */ import mcheli.MCH_Config;
/*      */ import mcheli.MCH_Lib;
/*      */ import mcheli.MCH_MOD;
/*      */ import mcheli.MCH_Math;
/*      */ import mcheli.aircraft.MCH_AircraftInfo;
/*      */ import mcheli.aircraft.MCH_BoundingBox;
/*      */ import mcheli.aircraft.MCH_EntityAircraft;
/*      */ import mcheli.aircraft.MCH_EntityHitBox;
/*      */ import mcheli.aircraft.MCH_EntitySeat;
/*      */ import mcheli.aircraft.MCH_PacketStatusRequest;
/*      */ import mcheli.aircraft.MCH_Parts;
/*      */ import mcheli.particles.MCH_ParticleParam;
/*      */ import mcheli.particles.MCH_ParticlesUtil;
/*      */ import mcheli.weapon.MCH_WeaponSet;
/*      */ import mcheli.wrapper.W_Block;
/*      */ import mcheli.wrapper.W_Blocks;
/*      */ import mcheli.wrapper.W_Entity;
/*      */ import mcheli.wrapper.W_Lib;
/*      */ import mcheli.wrapper.W_WorldFunc;
/*      */ import net.minecraft.block.Block;
/*      */ import net.minecraft.block.material.Material;
/*      */ import net.minecraft.block.state.IBlockState;
/*      */ import net.minecraft.crash.CrashReport;
/*      */ import net.minecraft.crash.CrashReportCategory;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.entity.EntityLivingBase;
/*      */ import net.minecraft.entity.MoverType;
/*      */ import net.minecraft.entity.player.EntityPlayer;
/*      */ import net.minecraft.init.Blocks;
/*      */ import net.minecraft.item.Item;
/*      */ import net.minecraft.nbt.NBTTagCompound;
/*      */ import net.minecraft.util.DamageSource;
/*      */ import net.minecraft.util.ReportedException;
/*      */ import net.minecraft.util.math.AxisAlignedBB;
/*      */ import net.minecraft.util.math.BlockPos;
/*      */ import net.minecraft.util.math.MathHelper;
/*      */ import net.minecraft.util.math.Vec3d;
/*      */ import net.minecraft.world.World;
/*      */ import net.minecraftforge.fml.relauncher.Side;
/*      */ import net.minecraftforge.fml.relauncher.SideOnly;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class MCH_EntityTank
/*      */   extends MCH_EntityAircraft
/*      */ {
/*      */   private MCH_TankInfo tankInfo;
/*      */   public float soundVolume;
/*      */   public float soundVolumeTarget;
/*      */   public float rotationRotor;
/*      */   public float prevRotationRotor;
/*      */   public float addkeyRotValue;
/*      */   public final MCH_WheelManager WheelMng;
/*      */   
/*      */   public MCH_EntityTank(World world) {
/*   74 */     super(world);
/*      */     
/*   76 */     this.tankInfo = null;
/*      */     
/*   78 */     this.currentSpeed = 0.07D;
/*   79 */     this.field_70156_m = true;
/*   80 */     func_70105_a(2.0F, 0.7F);
/*      */     
/*   82 */     this.field_70159_w = 0.0D;
/*   83 */     this.field_70181_x = 0.0D;
/*   84 */     this.field_70179_y = 0.0D;
/*   85 */     this.weapons = createWeapon(0);
/*   86 */     this.soundVolume = 0.0F;
/*   87 */     this.field_70138_W = 0.6F;
/*   88 */     this.rotationRotor = 0.0F;
/*   89 */     this.prevRotationRotor = 0.0F;
/*   90 */     this.WheelMng = new MCH_WheelManager(this);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public String getKindName() {
/*   96 */     return "tanks";
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public String getEntityType() {
/*  102 */     return "Vehicle";
/*      */   }
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public MCH_TankInfo getTankInfo() {
/*  108 */     return this.tankInfo;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void changeType(String type) {
/*  114 */     MCH_Lib.DbgLog(this.field_70170_p, "MCH_EntityTank.changeType " + type + " : " + toString(), new Object[0]);
/*      */     
/*  116 */     if (!type.isEmpty())
/*      */     {
/*  118 */       this.tankInfo = MCH_TankInfoManager.get(type);
/*      */     }
/*      */     
/*  121 */     if (this.tankInfo == null) {
/*      */       
/*  123 */       MCH_Lib.Log((Entity)this, "##### MCH_EntityTank changeTankType() Tank info null %d, %s, %s", new Object[] {
/*      */             
/*  125 */             Integer.valueOf(W_Entity.getEntityId((Entity)this)), type, getEntityName()
/*      */           });
/*      */       
/*  128 */       func_70106_y();
/*      */     }
/*      */     else {
/*      */       
/*  132 */       setAcInfo(this.tankInfo);
/*  133 */       newSeats(getAcInfo().getNumSeatAndRack());
/*  134 */       switchFreeLookModeClient((getAcInfo()).defaultFreelook);
/*  135 */       this.weapons = createWeapon(1 + getSeatNum());
/*  136 */       initPartRotation(getRotYaw(), getRotPitch());
/*  137 */       this.WheelMng.createWheels(this.field_70170_p, (getAcInfo()).wheels, new Vec3d(0.0D, -0.35D, 
/*  138 */             (getTankInfo()).weightedCenterZ));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public Item getItem() {
/*  146 */     return (getTankInfo() != null) ? (Item)(getTankInfo()).item : null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canMountWithNearEmptyMinecart() {
/*  152 */     return MCH_Config.MountMinecartTank.prmBool;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void func_70088_a() {
/*  158 */     super.func_70088_a();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public float getGiveDamageRot() {
/*  164 */     return 91.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void func_70014_b(NBTTagCompound par1NBTTagCompound) {
/*  170 */     super.func_70014_b(par1NBTTagCompound);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void func_70037_a(NBTTagCompound par1NBTTagCompound) {
/*  176 */     super.func_70037_a(par1NBTTagCompound);
/*      */     
/*  178 */     if (this.tankInfo == null) {
/*      */       
/*  180 */       this.tankInfo = MCH_TankInfoManager.get(getTypeName());
/*      */       
/*  182 */       if (this.tankInfo == null) {
/*      */         
/*  184 */         MCH_Lib.Log((Entity)this, "##### MCH_EntityTank readEntityFromNBT() Tank info null %d, %s", new Object[] {
/*      */               
/*  186 */               Integer.valueOf(W_Entity.getEntityId((Entity)this)), getEntityName()
/*      */             });
/*      */         
/*  189 */         func_70106_y();
/*      */       }
/*      */       else {
/*      */         
/*  193 */         setAcInfo(this.tankInfo);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void func_70106_y() {
/*  201 */     super.func_70106_y();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void onInteractFirst(EntityPlayer player) {
/*  207 */     this.addkeyRotValue = 0.0F;
/*  208 */     player.field_70759_as = player.field_70758_at = getLastRiderYaw();
/*  209 */     player.field_70126_B = player.field_70177_z = getLastRiderYaw();
/*  210 */     player.field_70125_A = getLastRiderPitch();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canSwitchGunnerMode() {
/*  216 */     if (!super.canSwitchGunnerMode())
/*      */     {
/*  218 */       return false;
/*      */     }
/*  220 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void onUpdateAircraft() {
/*  226 */     if (this.tankInfo == null) {
/*      */       
/*  228 */       changeType(getTypeName());
/*  229 */       this.field_70169_q = this.field_70165_t;
/*  230 */       this.field_70167_r = this.field_70163_u;
/*  231 */       this.field_70166_s = this.field_70161_v;
/*      */       
/*      */       return;
/*      */     } 
/*  235 */     if (!this.isRequestedSyncStatus) {
/*      */       
/*  237 */       this.isRequestedSyncStatus = true;
/*      */       
/*  239 */       if (this.field_70170_p.field_72995_K)
/*      */       {
/*  241 */         MCH_PacketStatusRequest.requestStatus(this);
/*      */       }
/*      */     } 
/*      */     
/*  245 */     if (this.lastRiddenByEntity == null && getRiddenByEntity() != null)
/*      */     {
/*  247 */       initCurrentWeapon(getRiddenByEntity());
/*      */     }
/*      */     
/*  250 */     updateWeapons();
/*  251 */     onUpdate_Seats();
/*  252 */     onUpdate_Control();
/*      */     
/*  254 */     this.prevRotationRotor = this.rotationRotor;
/*  255 */     this.rotationRotor = (float)(this.rotationRotor + getCurrentThrottle() * (getAcInfo()).rotorSpeed);
/*      */     
/*  257 */     if (this.rotationRotor > 360.0F) {
/*      */       
/*  259 */       this.rotationRotor -= 360.0F;
/*  260 */       this.prevRotationRotor -= 360.0F;
/*      */     } 
/*      */     
/*  263 */     if (this.rotationRotor < 0.0F) {
/*      */       
/*  265 */       this.rotationRotor += 360.0F;
/*  266 */       this.prevRotationRotor += 360.0F;
/*      */     } 
/*      */     
/*  269 */     this.field_70169_q = this.field_70165_t;
/*  270 */     this.field_70167_r = this.field_70163_u;
/*  271 */     this.field_70166_s = this.field_70161_v;
/*      */     
/*  273 */     if (isDestroyed())
/*      */     {
/*  275 */       if (getCurrentThrottle() > 0.0D) {
/*      */         
/*  277 */         if (MCH_Lib.getBlockIdY((Entity)this, 3, -2) > 0)
/*      */         {
/*  279 */           setCurrentThrottle(getCurrentThrottle() * 0.8D);
/*      */         }
/*      */         
/*  282 */         if (isExploded())
/*      */         {
/*  284 */           setCurrentThrottle(getCurrentThrottle() * 0.98D);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*  289 */     updateCameraViewers();
/*      */     
/*  291 */     if (this.field_70170_p.field_72995_K) {
/*      */       
/*  293 */       onUpdate_Client();
/*      */     }
/*      */     else {
/*      */       
/*  297 */       onUpdate_Server();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   @SideOnly(Side.CLIENT)
/*      */   public boolean func_90999_ad() {
/*  305 */     return (isDestroyed() || super.func_90999_ad());
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateExtraBoundingBox() {
/*  311 */     if (this.field_70170_p.field_72995_K) {
/*      */       
/*  313 */       super.updateExtraBoundingBox();
/*      */     }
/*  315 */     else if (getCountOnUpdate() <= 1) {
/*      */       
/*  317 */       super.updateExtraBoundingBox();
/*  318 */       super.updateExtraBoundingBox();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ClacAxisBB calculateXOffset(List<AxisAlignedBB> list, AxisAlignedBB bb, double x) {
/*  331 */     for (int j5 = 0; j5 < list.size(); j5++)
/*      */     {
/*  333 */       x = ((AxisAlignedBB)list.get(j5)).func_72316_a(bb, x);
/*      */     }
/*      */     
/*  336 */     return new ClacAxisBB(x, bb.func_72317_d(x, 0.0D, 0.0D));
/*      */   }
/*      */ 
/*      */   
/*      */   public ClacAxisBB calculateYOffset(List<AxisAlignedBB> list, AxisAlignedBB bb, double y) {
/*  341 */     return calculateYOffset(list, bb, bb, y);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ClacAxisBB calculateYOffset(List<AxisAlignedBB> list, AxisAlignedBB calcBB, AxisAlignedBB offsetBB, double y) {
/*  353 */     for (int k = 0; k < list.size(); k++)
/*      */     {
/*  355 */       y = ((AxisAlignedBB)list.get(k)).func_72323_b(calcBB, y);
/*      */     }
/*      */     
/*  358 */     return new ClacAxisBB(y, offsetBB.func_72317_d(0.0D, y, 0.0D));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ClacAxisBB calculateZOffset(List<AxisAlignedBB> list, AxisAlignedBB bb, double z) {
/*  370 */     for (int k5 = 0; k5 < list.size(); k5++)
/*      */     {
/*  372 */       z = ((AxisAlignedBB)list.get(k5)).func_72322_c(bb, z);
/*      */     }
/*      */     
/*  375 */     return new ClacAxisBB(z, bb.func_72317_d(0.0D, 0.0D, z));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void func_70091_d(MoverType type, double x, double y, double z) {
/*  387 */     this.field_70170_p.field_72984_F.func_76320_a("move");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  397 */     double d2 = x;
/*  398 */     double d3 = y;
/*  399 */     double d4 = z;
/*  400 */     List<AxisAlignedBB> list1 = getCollisionBoxes((Entity)this, func_174813_aQ().func_72321_a(x, y, z));
/*  401 */     AxisAlignedBB axisalignedbb = func_174813_aQ();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  406 */     if (y != 0.0D) {
/*      */       
/*  408 */       ClacAxisBB v = calculateYOffset(list1, func_174813_aQ(), y);
/*  409 */       y = v.value;
/*      */       
/*  411 */       func_174826_a(v.bb);
/*      */     } 
/*      */ 
/*      */     
/*  415 */     boolean flag = (this.field_70122_E || (d3 != y && d3 < 0.0D));
/*      */     
/*  417 */     for (MCH_BoundingBox ebb : this.extraBoundingBox)
/*      */     {
/*  419 */       ebb.updatePosition(this.field_70165_t, this.field_70163_u, this.field_70161_v, getRotYaw(), getRotPitch(), getRotRoll());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  425 */     if (x != 0.0D) {
/*      */       
/*  427 */       ClacAxisBB v = calculateXOffset(list1, func_174813_aQ(), x);
/*  428 */       x = v.value;
/*      */       
/*  430 */       if (x != 0.0D)
/*      */       {
/*  432 */         func_174826_a(v.bb);
/*      */       }
/*      */     } 
/*      */     
/*  436 */     if (z != 0.0D) {
/*      */       
/*  438 */       ClacAxisBB v = calculateZOffset(list1, func_174813_aQ(), z);
/*  439 */       z = v.value;
/*      */       
/*  441 */       if (z != 0.0D)
/*      */       {
/*  443 */         func_174826_a(v.bb);
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  448 */     if (this.field_70138_W > 0.0F && flag && (d2 != x || d4 != z)) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  459 */       double d14 = x;
/*  460 */       double d6 = y;
/*  461 */       double d7 = z;
/*  462 */       AxisAlignedBB axisalignedbb1 = func_174813_aQ();
/*  463 */       func_174826_a(axisalignedbb);
/*  464 */       y = this.field_70138_W;
/*  465 */       List<AxisAlignedBB> list = getCollisionBoxes((Entity)this, func_174813_aQ().func_72321_a(d2, y, d4));
/*  466 */       AxisAlignedBB axisalignedbb2 = func_174813_aQ();
/*  467 */       AxisAlignedBB axisalignedbb3 = axisalignedbb2.func_72321_a(d2, 0.0D, d4);
/*      */ 
/*      */       
/*  470 */       double d8 = y;
/*  471 */       ClacAxisBB v = calculateYOffset(list, axisalignedbb3, axisalignedbb2, d8);
/*  472 */       d8 = v.value;
/*  473 */       axisalignedbb2 = v.bb;
/*      */ 
/*      */       
/*  476 */       double d18 = d2;
/*  477 */       v = calculateXOffset(list, axisalignedbb2, d18);
/*  478 */       d18 = v.value;
/*  479 */       axisalignedbb2 = v.bb;
/*      */ 
/*      */       
/*  482 */       double d19 = d4;
/*  483 */       v = calculateZOffset(list, axisalignedbb2, d19);
/*  484 */       d19 = v.value;
/*  485 */       axisalignedbb2 = v.bb;
/*      */       
/*  487 */       AxisAlignedBB axisalignedbb4 = func_174813_aQ();
/*  488 */       double d20 = y;
/*  489 */       v = calculateYOffset(list, axisalignedbb4, d20);
/*  490 */       d20 = v.value;
/*  491 */       axisalignedbb4 = v.bb;
/*      */       
/*  493 */       double d21 = d2;
/*  494 */       v = calculateXOffset(list, axisalignedbb4, d21);
/*  495 */       d21 = v.value;
/*  496 */       axisalignedbb4 = v.bb;
/*      */       
/*  498 */       double d22 = d4;
/*  499 */       v = calculateZOffset(list, axisalignedbb4, d22);
/*  500 */       d22 = v.value;
/*  501 */       axisalignedbb4 = v.bb;
/*      */       
/*  503 */       double d23 = d18 * d18 + d19 * d19;
/*  504 */       double d9 = d21 * d21 + d22 * d22;
/*      */       
/*  506 */       if (d23 > d9) {
/*      */         
/*  508 */         x = d18;
/*  509 */         z = d19;
/*  510 */         y = -d8;
/*  511 */         func_174826_a(axisalignedbb2);
/*      */       }
/*      */       else {
/*      */         
/*  515 */         x = d21;
/*  516 */         z = d22;
/*  517 */         y = -d20;
/*  518 */         func_174826_a(axisalignedbb4);
/*      */       } 
/*      */ 
/*      */       
/*  522 */       v = calculateYOffset(list, func_174813_aQ(), y);
/*  523 */       y = v.value;
/*      */       
/*  525 */       func_174826_a(v.bb);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  534 */       if (d14 * d14 + d7 * d7 >= x * x + z * z) {
/*      */         
/*  536 */         x = d14;
/*  537 */         y = d6;
/*  538 */         z = d7;
/*  539 */         func_174826_a(axisalignedbb1);
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  548 */     this.field_70170_p.field_72984_F.func_76319_b();
/*  549 */     this.field_70170_p.field_72984_F.func_76320_a("rest");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  563 */     func_174829_m();
/*  564 */     this.field_70123_F = (d2 != x || d4 != z);
/*  565 */     this.field_70124_G = (d3 != y);
/*  566 */     this.field_70122_E = (this.field_70124_G && d3 < 0.0D);
/*  567 */     this.field_70132_H = (this.field_70123_F || this.field_70124_G);
/*      */     
/*  569 */     int j6 = MathHelper.func_76128_c(this.field_70165_t);
/*  570 */     int i1 = MathHelper.func_76128_c(this.field_70163_u - 0.20000000298023224D);
/*  571 */     int k6 = MathHelper.func_76128_c(this.field_70161_v);
/*  572 */     BlockPos blockpos = new BlockPos(j6, i1, k6);
/*  573 */     IBlockState iblockstate = this.field_70170_p.func_180495_p(blockpos);
/*      */     
/*  575 */     if (iblockstate.func_185904_a() == Material.field_151579_a) {
/*      */       
/*  577 */       BlockPos blockpos1 = blockpos.func_177977_b();
/*  578 */       IBlockState iblockstate1 = this.field_70170_p.func_180495_p(blockpos1);
/*  579 */       Block block1 = iblockstate1.func_177230_c();
/*      */       
/*  581 */       if (block1 instanceof net.minecraft.block.BlockFence || block1 instanceof net.minecraft.block.BlockWall || block1 instanceof net.minecraft.block.BlockFenceGate) {
/*      */         
/*  583 */         iblockstate = iblockstate1;
/*  584 */         blockpos = blockpos1;
/*      */       } 
/*      */     } 
/*      */     
/*  588 */     func_184231_a(y, this.field_70122_E, iblockstate, blockpos);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  598 */     if (d2 != x)
/*      */     {
/*  600 */       this.field_70159_w = 0.0D;
/*      */     }
/*      */     
/*  603 */     if (d4 != z)
/*      */     {
/*  605 */       this.field_70179_y = 0.0D;
/*      */     }
/*      */     
/*  608 */     Block block = iblockstate.func_177230_c();
/*      */     
/*  610 */     if (d3 != y)
/*      */     {
/*  612 */       block.func_176216_a(this.field_70170_p, (Entity)this);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  627 */       func_145775_I();
/*      */     }
/*  629 */     catch (Throwable throwable) {
/*      */       
/*  631 */       CrashReport crashreport = CrashReport.func_85055_a(throwable, "Checking entity block collision");
/*  632 */       CrashReportCategory crashreportcategory = crashreport.func_85058_a("Entity being checked for collision");
/*  633 */       func_85029_a(crashreportcategory);
/*  634 */       throw new ReportedException(crashreport);
/*      */     } 
/*      */ 
/*      */     
/*  638 */     this.field_70170_p.field_72984_F.func_76319_b();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void rotationByKey(float partialTicks) {
/*  644 */     float rot = 0.2F;
/*      */     
/*  646 */     if (this.moveLeft && !this.moveRight)
/*      */     {
/*  648 */       this.addkeyRotValue -= rot * partialTicks;
/*      */     }
/*      */     
/*  651 */     if (this.moveRight && !this.moveLeft)
/*      */     {
/*  653 */       this.addkeyRotValue += rot * partialTicks;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void onUpdateAngles(float partialTicks) {
/*  660 */     if (isDestroyed()) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/*  665 */     if (this.isGunnerMode) {
/*      */       
/*  667 */       setRotPitch(getRotPitch() * 0.95F);
/*  668 */       setRotYaw(getRotYaw() + (getAcInfo()).autoPilotRot * 0.2F);
/*      */       
/*  670 */       if (MathHelper.func_76135_e(getRotRoll()) > 20.0F)
/*      */       {
/*  672 */         setRotRoll(getRotRoll() * 0.95F);
/*      */       }
/*      */     } 
/*      */     
/*  676 */     updateRecoil(partialTicks);
/*  677 */     setRotPitch(getRotPitch() + (this.WheelMng.targetPitch - getRotPitch()) * partialTicks);
/*  678 */     setRotRoll(getRotRoll() + (this.WheelMng.targetRoll - getRotRoll()) * partialTicks);
/*      */     
/*  680 */     boolean isFly = (MCH_Lib.getBlockIdY((Entity)this, 3, -3) == 0);
/*      */     
/*  682 */     if (!isFly || ((getAcInfo()).isFloat && getWaterDepth() > 0.0D)) {
/*      */       
/*  684 */       float gmy = 1.0F;
/*      */       
/*  686 */       if (!isFly) {
/*      */         
/*  688 */         gmy = (getAcInfo()).mobilityYawOnGround;
/*      */         
/*  690 */         if (!(getAcInfo()).canRotOnGround) {
/*      */           
/*  692 */           Block block = MCH_Lib.getBlockY((Entity)this, 3, -2, false);
/*      */           
/*  694 */           if (!W_Block.isEqual(block, W_Block.getWater()) && !W_Block.isEqual(block, W_Blocks.field_150350_a))
/*      */           {
/*  696 */             gmy = 0.0F;
/*      */           }
/*      */         } 
/*      */       } 
/*      */       
/*  701 */       float pivotTurnThrottle = (getAcInfo()).pivotTurnThrottle;
/*  702 */       double dx = this.field_70165_t - this.field_70169_q;
/*  703 */       double dz = this.field_70161_v - this.field_70166_s;
/*  704 */       double dist = dx * dx + dz * dz;
/*      */       
/*  706 */       if (pivotTurnThrottle <= 0.0F || getCurrentThrottle() >= pivotTurnThrottle || this.throttleBack >= pivotTurnThrottle / 10.0F || dist > this.throttleBack * 0.01D) {
/*      */ 
/*      */         
/*  709 */         float sf = (float)Math.sqrt((dist <= 1.0D) ? dist : 1.0D);
/*      */         
/*  711 */         if (pivotTurnThrottle <= 0.0F)
/*      */         {
/*  713 */           sf = 1.0F;
/*      */         }
/*      */         
/*  716 */         float flag = (!this.throttleUp && this.throttleDown && getCurrentThrottle() < pivotTurnThrottle + 0.05D) ? -1.0F : 1.0F;
/*      */ 
/*      */ 
/*      */         
/*  720 */         if (this.moveLeft && !this.moveRight)
/*      */         {
/*  722 */           setRotYaw(getRotYaw() - 0.6F * gmy * partialTicks * flag * sf);
/*      */         }
/*      */         
/*  725 */         if (this.moveRight && !this.moveLeft)
/*      */         {
/*  727 */           setRotYaw(getRotYaw() + 0.6F * gmy * partialTicks * flag * sf);
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  732 */     this.addkeyRotValue = (float)(this.addkeyRotValue * (1.0D - (0.1F * partialTicks)));
/*      */   }
/*      */ 
/*      */   
/*      */   protected void onUpdate_Control() {
/*  737 */     if (this.isGunnerMode && !canUseFuel())
/*      */     {
/*  739 */       switchGunnerMode(false);
/*      */     }
/*      */     
/*  742 */     this.throttleBack = (float)(this.throttleBack * 0.8D);
/*      */     
/*  744 */     if (getBrake()) {
/*      */       
/*  746 */       this.throttleBack = (float)(this.throttleBack * 0.5D);
/*      */       
/*  748 */       if (getCurrentThrottle() > 0.0D) {
/*  749 */         addCurrentThrottle(-0.02D * (getAcInfo()).throttleUpDown);
/*      */       } else {
/*      */         
/*  752 */         setCurrentThrottle(0.0D);
/*      */       } 
/*      */     } 
/*      */     
/*  756 */     if (getRiddenByEntity() != null && !(getRiddenByEntity()).field_70128_L && isCanopyClose() && canUseFuel() && 
/*  757 */       !isDestroyed()) {
/*      */       
/*  759 */       onUpdate_ControlSub();
/*      */     }
/*  761 */     else if (isTargetDrone() && canUseFuel() && !isDestroyed()) {
/*      */       
/*  763 */       this.throttleUp = true;
/*  764 */       onUpdate_ControlSub();
/*      */     }
/*  766 */     else if (getCurrentThrottle() > 0.0D) {
/*      */       
/*  768 */       addCurrentThrottle(-0.0025D * (getAcInfo()).throttleUpDown);
/*      */     }
/*      */     else {
/*      */       
/*  772 */       setCurrentThrottle(0.0D);
/*      */     } 
/*      */     
/*  775 */     if (getCurrentThrottle() < 0.0D)
/*      */     {
/*  777 */       setCurrentThrottle(0.0D);
/*      */     }
/*      */     
/*  780 */     if (this.field_70170_p.field_72995_K) {
/*      */       
/*  782 */       if (!W_Lib.isClientPlayer(getRiddenByEntity()) || getCountOnUpdate() % 200 == 0)
/*      */       {
/*  784 */         double ct = getThrottle();
/*      */         
/*  786 */         if (getCurrentThrottle() > ct) {
/*  787 */           addCurrentThrottle(-0.005D);
/*      */         }
/*  789 */         if (getCurrentThrottle() < ct)
/*      */         {
/*  791 */           addCurrentThrottle(0.005D);
/*      */         }
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/*  797 */       setThrottle(getCurrentThrottle());
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void onUpdate_ControlSub() {
/*  803 */     if (!this.isGunnerMode) {
/*      */       
/*  805 */       float throttleUpDown = (getAcInfo()).throttleUpDown;
/*      */       
/*  807 */       if (this.throttleUp) {
/*      */         
/*  809 */         float f = throttleUpDown;
/*      */         
/*  811 */         if (func_184187_bx() != null) {
/*      */           
/*  813 */           double mx = (func_184187_bx()).field_70159_w;
/*  814 */           double mz = (func_184187_bx()).field_70179_y;
/*      */           
/*  816 */           f *= MathHelper.func_76133_a(mx * mx + mz * mz) * (getAcInfo()).throttleUpDownOnEntity;
/*      */         } 
/*      */         
/*  819 */         if ((getAcInfo()).enableBack && this.throttleBack > 0.0F) {
/*      */           
/*  821 */           this.throttleBack = (float)(this.throttleBack - 0.01D * f);
/*      */         }
/*      */         else {
/*      */           
/*  825 */           this.throttleBack = 0.0F;
/*      */           
/*  827 */           if (getCurrentThrottle() < 1.0D) {
/*  828 */             addCurrentThrottle(0.01D * f);
/*      */           } else {
/*      */             
/*  831 */             setCurrentThrottle(1.0D);
/*      */           }
/*      */         
/*      */         } 
/*  835 */       } else if (this.throttleDown) {
/*      */         
/*  837 */         if (getCurrentThrottle() > 0.0D) {
/*      */           
/*  839 */           addCurrentThrottle(-0.01D * throttleUpDown);
/*      */         }
/*      */         else {
/*      */           
/*  843 */           setCurrentThrottle(0.0D);
/*      */           
/*  845 */           if ((getAcInfo()).enableBack)
/*      */           {
/*  847 */             this.throttleBack = (float)(this.throttleBack + 0.0025D * throttleUpDown);
/*  848 */             if (this.throttleBack > 0.6F)
/*      */             {
/*  850 */               this.throttleBack = 0.6F;
/*      */             }
/*      */           }
/*      */         
/*      */         } 
/*  855 */       } else if (this.cs_tankAutoThrottleDown) {
/*      */         
/*  857 */         if (getCurrentThrottle() > 0.0D) {
/*      */           
/*  859 */           addCurrentThrottle(-0.005D * throttleUpDown);
/*      */           
/*  861 */           if (getCurrentThrottle() <= 0.0D)
/*      */           {
/*  863 */             setCurrentThrottle(0.0D);
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void onUpdate_Particle2() {
/*  872 */     if (!this.field_70170_p.field_72995_K) {
/*      */       return;
/*      */     }
/*  875 */     if (getHP() >= getMaxHP() * 0.5D) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/*  880 */     if (getTankInfo() == null) {
/*      */       return;
/*      */     }
/*  883 */     int bbNum = (getTankInfo()).extraBoundingBox.size();
/*      */     
/*  885 */     if (bbNum < 0)
/*      */     {
/*  887 */       bbNum = 0;
/*      */     }
/*      */     
/*  890 */     if (this.isFirstDamageSmoke || this.prevDamageSmokePos.length != bbNum + 1)
/*      */     {
/*  892 */       this.prevDamageSmokePos = new Vec3d[bbNum + 1];
/*      */     }
/*      */     
/*  895 */     float yaw = getRotYaw();
/*  896 */     float pitch = getRotPitch();
/*  897 */     float roll = getRotRoll();
/*      */     
/*  899 */     for (int ri = 0; ri < bbNum; ri++) {
/*      */       
/*  901 */       if (getHP() >= getMaxHP() * 0.2D && getMaxHP() > 0) {
/*      */         
/*  903 */         int d = (int)(((getHP() / getMaxHP()) - 0.2D) / 0.3D * 15.0D);
/*      */         
/*  905 */         if (d <= 0 || this.field_70146_Z.nextInt(d) > 0);
/*      */       
/*      */       }
/*      */       else {
/*      */ 
/*      */         
/*  911 */         MCH_BoundingBox bb = (getTankInfo()).extraBoundingBox.get(ri);
/*  912 */         Vec3d pos = getTransformedPosition(bb.offsetX, bb.offsetY, bb.offsetZ);
/*  913 */         double x = pos.field_72450_a;
/*  914 */         double y = pos.field_72448_b;
/*  915 */         double z = pos.field_72449_c;
/*      */         
/*  917 */         onUpdate_Particle2SpawnSmoke(ri, x, y, z, 1.0F);
/*      */       } 
/*      */     } 
/*      */     
/*  921 */     boolean b = true;
/*      */     
/*  923 */     if (getHP() >= getMaxHP() * 0.2D && getMaxHP() > 0) {
/*      */       
/*  925 */       int d = (int)(((getHP() / getMaxHP()) - 0.2D) / 0.3D * 15.0D);
/*      */       
/*  927 */       if (d > 0 && this.field_70146_Z.nextInt(d) > 0)
/*      */       {
/*  929 */         b = false;
/*      */       }
/*      */     } 
/*      */     
/*  933 */     if (b) {
/*      */       
/*  935 */       double px = this.field_70165_t;
/*  936 */       double py = this.field_70163_u;
/*  937 */       double pz = this.field_70161_v;
/*      */       
/*  939 */       if (getSeatInfo(0) != null && (getSeatInfo(0)).pos != null) {
/*      */         
/*  941 */         Vec3d pos = MCH_Lib.RotVec3(0.0D, (getSeatInfo(0)).pos.field_72448_b, -2.0D, -yaw, -pitch, -roll);
/*      */         
/*  943 */         px += pos.field_72450_a;
/*  944 */         py += pos.field_72448_b;
/*  945 */         pz += pos.field_72449_c;
/*      */       } 
/*      */       
/*  948 */       onUpdate_Particle2SpawnSmoke(bbNum, px, py, pz, (bbNum == 0) ? 2.0F : 1.0F);
/*      */     } 
/*      */     
/*  951 */     this.isFirstDamageSmoke = false;
/*      */   }
/*      */ 
/*      */   
/*      */   public void onUpdate_Particle2SpawnSmoke(int ri, double x, double y, double z, float size) {
/*  956 */     if (this.isFirstDamageSmoke || this.prevDamageSmokePos[ri] == null)
/*      */     {
/*  958 */       this.prevDamageSmokePos[ri] = new Vec3d(x, y, z);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  965 */     int num = 1;
/*      */     
/*  967 */     for (int i = 0; i < num; i++) {
/*      */       
/*  969 */       float c = 0.2F + this.field_70146_Z.nextFloat() * 0.3F;
/*  970 */       MCH_ParticleParam prm = new MCH_ParticleParam(this.field_70170_p, "smoke", x, y, z);
/*      */       
/*  972 */       prm.motionX = size * (this.field_70146_Z.nextDouble() - 0.5D) * 0.3D;
/*  973 */       prm.motionY = size * this.field_70146_Z.nextDouble() * 0.1D;
/*  974 */       prm.motionZ = size * (this.field_70146_Z.nextDouble() - 0.5D) * 0.3D;
/*  975 */       prm.size = size * (this.field_70146_Z.nextInt(5) + 5.0F) * 1.0F;
/*  976 */       prm.setColor(0.7F + this.field_70146_Z.nextFloat() * 0.1F, c, c, c);
/*  977 */       MCH_ParticlesUtil.spawnParticle(prm);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  983 */     this.prevDamageSmokePos[ri] = new Vec3d(x, y, z);
/*      */   }
/*      */ 
/*      */   
/*      */   public void onUpdate_Particle2SpawnSmode(int ri, double x, double y, double z, float size) {
/*  988 */     if (this.isFirstDamageSmoke)
/*      */     {
/*  990 */       this.prevDamageSmokePos[ri] = new Vec3d(x, y, z);
/*      */     }
/*      */     
/*  993 */     Vec3d prev = this.prevDamageSmokePos[ri];
/*  994 */     double dx = x - prev.field_72450_a;
/*  995 */     double dy = y - prev.field_72448_b;
/*  996 */     double dz = z - prev.field_72449_c;
/*  997 */     int num = (int)(MathHelper.func_76133_a(dx * dx + dy * dy + dz * dz) / 0.3D) + 1;
/*      */     
/*  999 */     for (int i = 0; i < num; i++) {
/*      */       
/* 1001 */       float c = 0.2F + this.field_70146_Z.nextFloat() * 0.3F;
/* 1002 */       MCH_ParticleParam prm = new MCH_ParticleParam(this.field_70170_p, "smoke", x, y, z);
/*      */       
/* 1004 */       prm.motionX = size * (this.field_70146_Z.nextDouble() - 0.5D) * 0.3D;
/* 1005 */       prm.motionY = size * this.field_70146_Z.nextDouble() * 0.1D;
/* 1006 */       prm.motionZ = size * (this.field_70146_Z.nextDouble() - 0.5D) * 0.3D;
/* 1007 */       prm.size = size * (this.field_70146_Z.nextInt(5) + 5.0F) * 1.0F;
/* 1008 */       prm.setColor(0.7F + this.field_70146_Z.nextFloat() * 0.1F, c, c, c);
/* 1009 */       MCH_ParticlesUtil.spawnParticle(prm);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1015 */     this.prevDamageSmokePos[ri] = new Vec3d(x, y, z);
/*      */   }
/*      */ 
/*      */   
/*      */   public void onUpdate_ParticleLandingGear() {
/* 1020 */     this.WheelMng.particleLandingGear();
/*      */   }
/*      */ 
/*      */   
/*      */   private void onUpdate_ParticleSplash() {
/* 1025 */     if (getAcInfo() == null) {
/*      */       return;
/*      */     }
/* 1028 */     if (!this.field_70170_p.field_72995_K) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/* 1033 */     double mx = this.field_70165_t - this.field_70169_q;
/* 1034 */     double mz = this.field_70161_v - this.field_70166_s;
/* 1035 */     double dist = mx * mx + mz * mz;
/*      */     
/* 1037 */     if (dist > 1.0D) {
/* 1038 */       dist = 1.0D;
/*      */     }
/* 1040 */     for (MCH_AircraftInfo.ParticleSplash p : (getAcInfo()).particleSplashs) {
/*      */       
/* 1042 */       for (int i = 0; i < p.num; i++) {
/*      */         
/* 1044 */         if (dist > 0.03D + this.field_70146_Z.nextFloat() * 0.1D)
/*      */         {
/* 1046 */           setParticleSplash(p.pos, -mx * p.acceleration, p.motionY, -mz * p.acceleration, p.gravity, p.size * (0.5D + dist * 0.5D), p.age);
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void setParticleSplash(Vec3d pos, double mx, double my, double mz, float gravity, double size, int age) {
/* 1055 */     Vec3d v = getTransformedPosition(pos);
/* 1056 */     v = v.func_72441_c(this.field_70146_Z.nextDouble() - 0.5D, (this.field_70146_Z.nextDouble() - 0.5D) * 0.5D, this.field_70146_Z
/* 1057 */         .nextDouble() - 0.5D);
/* 1058 */     int x = (int)(v.field_72450_a + 0.5D);
/* 1059 */     int y = (int)(v.field_72448_b + 0.0D);
/* 1060 */     int z = (int)(v.field_72449_c + 0.5D);
/*      */     
/* 1062 */     if (W_WorldFunc.isBlockWater(this.field_70170_p, x, y, z)) {
/*      */       
/* 1064 */       float c = this.field_70146_Z.nextFloat() * 0.3F + 0.7F;
/* 1065 */       MCH_ParticleParam prm = new MCH_ParticleParam(this.field_70170_p, "smoke", v.field_72450_a, v.field_72448_b, v.field_72449_c);
/*      */       
/* 1067 */       prm.motionX = mx + (this.field_70146_Z.nextFloat() - 0.5D) * 0.7D;
/* 1068 */       prm.motionY = my;
/* 1069 */       prm.motionZ = mz + (this.field_70146_Z.nextFloat() - 0.5D) * 0.7D;
/* 1070 */       prm.size = (float)size * (this.field_70146_Z.nextFloat() * 0.2F + 0.8F);
/* 1071 */       prm.setColor(0.9F, c, c, c);
/* 1072 */       prm.age = age + (int)(this.field_70146_Z.nextFloat() * 0.5D * age);
/* 1073 */       prm.gravity = gravity;
/*      */       
/* 1075 */       MCH_ParticlesUtil.spawnParticle(prm);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void destroyAircraft() {
/* 1082 */     super.destroyAircraft();
/*      */     
/* 1084 */     this.rotDestroyedPitch = 0.0F;
/* 1085 */     this.rotDestroyedRoll = 0.0F;
/* 1086 */     this.rotDestroyedYaw = 0.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int getClientPositionDelayCorrection() {
/* 1092 */     return ((getTankInfo()).weightType == 1) ? 2 : ((getTankInfo() == null) ? 7 : 7);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void onUpdate_Client() {
/* 1097 */     if (getRiddenByEntity() != null)
/*      */     {
/* 1099 */       if (W_Lib.isClientPlayer(getRiddenByEntity()))
/*      */       {
/* 1101 */         (getRiddenByEntity()).field_70125_A = (getRiddenByEntity()).field_70127_C;
/*      */       }
/*      */     }
/*      */     
/* 1105 */     if (this.aircraftPosRotInc > 0) {
/*      */       
/* 1107 */       applyServerPositionAndRotation();
/*      */     }
/*      */     else {
/*      */       
/* 1111 */       func_70107_b(this.field_70165_t + this.field_70159_w, this.field_70163_u + this.field_70181_x, this.field_70161_v + this.field_70179_y);
/*      */       
/* 1113 */       if (!isDestroyed() && (this.field_70122_E || MCH_Lib.getBlockIdY((Entity)this, 1, -2) > 0)) {
/*      */         
/* 1115 */         this.field_70159_w *= 0.95D;
/* 1116 */         this.field_70179_y *= 0.95D;
/* 1117 */         applyOnGroundPitch(0.95F);
/*      */       } 
/*      */       
/* 1120 */       if (func_70090_H()) {
/*      */         
/* 1122 */         this.field_70159_w *= 0.99D;
/* 1123 */         this.field_70179_y *= 0.99D;
/*      */       } 
/*      */     } 
/*      */     
/* 1127 */     updateWheels();
/* 1128 */     onUpdate_Particle2();
/* 1129 */     updateSound();
/*      */     
/* 1131 */     if (this.field_70170_p.field_72995_K) {
/*      */       
/* 1133 */       onUpdate_ParticleLandingGear();
/* 1134 */       onUpdate_ParticleSplash();
/* 1135 */       onUpdate_ParticleSandCloud(true);
/*      */     } 
/*      */     
/* 1138 */     updateCamera(this.field_70165_t, this.field_70163_u, this.field_70161_v);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void applyOnGroundPitch(float factor) {}
/*      */ 
/*      */ 
/*      */   
/*      */   private void onUpdate_Server() {
/* 1149 */     double prevMotion = Math.sqrt(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
/* 1150 */     double dp = 0.0D;
/*      */     
/* 1152 */     if (canFloatWater())
/*      */     {
/* 1154 */       dp = getWaterDepth();
/*      */     }
/*      */     
/* 1157 */     boolean levelOff = this.isGunnerMode;
/*      */     
/* 1159 */     if (dp == 0.0D) {
/*      */       
/* 1161 */       if (!levelOff)
/*      */       {
/* 1163 */         this.field_70181_x += 0.04D + (!func_70090_H() ? (getAcInfo()).gravity : (getAcInfo()).gravityInWater);
/* 1164 */         this.field_70181_x += -0.047D * (1.0D - getCurrentThrottle());
/*      */       }
/*      */       else
/*      */       {
/* 1168 */         this.field_70181_x *= 0.8D;
/*      */       }
/*      */     
/* 1171 */     } else if (MathHelper.func_76135_e(getRotRoll()) >= 40.0F || dp < 1.0D) {
/*      */       
/* 1173 */       this.field_70181_x -= 1.0E-4D;
/* 1174 */       this.field_70181_x += 0.007D * getCurrentThrottle();
/*      */     }
/*      */     else {
/*      */       
/* 1178 */       if (this.field_70181_x < 0.0D)
/*      */       {
/* 1180 */         this.field_70181_x /= 2.0D;
/*      */       }
/* 1182 */       this.field_70181_x += 0.007D;
/*      */     } 
/*      */     
/* 1185 */     float throttle = (float)(getCurrentThrottle() / 10.0D);
/* 1186 */     Vec3d v = MCH_Lib.Rot2Vec3(getRotYaw(), getRotPitch() - 10.0F);
/*      */     
/* 1188 */     if (!levelOff)
/*      */     {
/* 1190 */       this.field_70181_x += v.field_72448_b * throttle / 8.0D;
/*      */     }
/*      */     
/* 1193 */     boolean canMove = true;
/*      */     
/* 1195 */     if (!(getAcInfo()).canMoveOnGround) {
/*      */       
/* 1197 */       Block block = MCH_Lib.getBlockY((Entity)this, 3, -2, false);
/*      */       
/* 1199 */       if (!W_Block.isEqual(block, W_Block.getWater()) && !W_Block.isEqual(block, W_Blocks.field_150350_a))
/*      */       {
/* 1201 */         canMove = false;
/*      */       }
/*      */     } 
/*      */     
/* 1205 */     if (canMove)
/*      */     {
/* 1207 */       if ((getAcInfo()).enableBack && this.throttleBack > 0.0F) {
/*      */         
/* 1209 */         this.field_70159_w -= v.field_72450_a * this.throttleBack;
/* 1210 */         this.field_70179_y -= v.field_72449_c * this.throttleBack;
/*      */       }
/*      */       else {
/*      */         
/* 1214 */         this.field_70159_w += v.field_72450_a * throttle;
/* 1215 */         this.field_70179_y += v.field_72449_c * throttle;
/*      */       } 
/*      */     }
/*      */     
/* 1219 */     double motion = Math.sqrt(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
/* 1220 */     float speedLimit = getMaxSpeed();
/*      */     
/* 1222 */     if (motion > speedLimit) {
/*      */       
/* 1224 */       this.field_70159_w *= speedLimit / motion;
/* 1225 */       this.field_70179_y *= speedLimit / motion;
/* 1226 */       motion = speedLimit;
/*      */     } 
/*      */     
/* 1229 */     if (motion > prevMotion && this.currentSpeed < speedLimit) {
/*      */       
/* 1231 */       this.currentSpeed += (speedLimit - this.currentSpeed) / 35.0D;
/*      */       
/* 1233 */       if (this.currentSpeed > speedLimit)
/*      */       {
/* 1235 */         this.currentSpeed = speedLimit;
/*      */       }
/*      */     }
/*      */     else {
/*      */       
/* 1240 */       this.currentSpeed -= (this.currentSpeed - 0.07D) / 35.0D;
/*      */       
/* 1242 */       if (this.currentSpeed < 0.07D)
/*      */       {
/* 1244 */         this.currentSpeed = 0.07D;
/*      */       }
/*      */     } 
/*      */     
/* 1248 */     if (this.field_70122_E || MCH_Lib.getBlockIdY((Entity)this, 1, -2) > 0) {
/*      */       
/* 1250 */       this.field_70159_w *= (getAcInfo()).motionFactor;
/* 1251 */       this.field_70179_y *= (getAcInfo()).motionFactor;
/*      */       
/* 1253 */       if (MathHelper.func_76135_e(getRotPitch()) < 40.0F)
/*      */       {
/* 1255 */         applyOnGroundPitch(0.8F);
/*      */       }
/*      */     } 
/*      */     
/* 1259 */     updateWheels();
/*      */     
/* 1261 */     func_70091_d(MoverType.SELF, this.field_70159_w, this.field_70181_x, this.field_70179_y);
/*      */     
/* 1263 */     this.field_70181_x *= 0.95D;
/* 1264 */     this.field_70159_w *= (getAcInfo()).motionFactor;
/* 1265 */     this.field_70179_y *= (getAcInfo()).motionFactor;
/*      */     
/* 1267 */     func_70101_b(getRotYaw(), getRotPitch());
/* 1268 */     onUpdate_updateBlock();
/* 1269 */     updateCollisionBox();
/*      */     
/* 1271 */     if (getRiddenByEntity() != null && (getRiddenByEntity()).field_70128_L)
/*      */     {
/* 1273 */       unmountEntity();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void collisionEntity(AxisAlignedBB bb) {
/* 1280 */     if (bb == null) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1286 */     double speed = Math.sqrt(this.field_70159_w * this.field_70159_w + this.field_70181_x * this.field_70181_x + this.field_70179_y * this.field_70179_y);
/*      */     
/* 1288 */     if (speed <= 0.05D) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/* 1293 */     Entity rider = getRiddenByEntity();
/* 1294 */     float damage = (float)(speed * 15.0D);
/*      */ 
/*      */     
/* 1297 */     MCH_EntityAircraft rideAc = (func_184187_bx() instanceof MCH_EntitySeat) ? ((MCH_EntitySeat)func_184187_bx()).getParent() : ((func_184187_bx() instanceof MCH_EntityAircraft) ? (MCH_EntityAircraft)func_184187_bx() : null);
/*      */ 
/*      */ 
/*      */     
/* 1301 */     List<Entity> list = this.field_70170_p.func_175674_a((Entity)this, bb.func_72314_b(0.3D, 0.3D, 0.3D), e -> {
/*      */           if (e == rideAc || e instanceof net.minecraft.entity.item.EntityItem || e instanceof net.minecraft.entity.item.EntityXPOrb || e instanceof mcheli.weapon.MCH_EntityBaseBullet || e instanceof mcheli.chain.MCH_EntityChain || e instanceof MCH_EntitySeat) {
/*      */             return false;
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*      */           if (e instanceof MCH_EntityTank) {
/*      */             MCH_EntityTank tank = (MCH_EntityTank)e;
/*      */ 
/*      */ 
/*      */             
/*      */             if (tank.getTankInfo() != null && (tank.getTankInfo()).weightType == 2) {
/*      */               return MCH_Config.Collision_EntityTankDamage.prmBool;
/*      */             }
/*      */           } 
/*      */ 
/*      */           
/*      */           return MCH_Config.Collision_EntityDamage.prmBool;
/*      */         });
/*      */ 
/*      */     
/* 1323 */     for (int i = 0; i < list.size(); i++) {
/*      */       
/* 1325 */       Entity e = list.get(i);
/*      */       
/* 1327 */       if (shouldCollisionDamage(e)) {
/*      */         DamageSource ds;
/* 1329 */         double dx = e.field_70165_t - this.field_70165_t;
/* 1330 */         double dz = e.field_70161_v - this.field_70161_v;
/* 1331 */         double dist = Math.sqrt(dx * dx + dz * dz);
/*      */         
/* 1333 */         if (dist > 5.0D) {
/* 1334 */           dist = 5.0D;
/*      */         }
/* 1336 */         damage = (float)(damage + 5.0D - dist);
/*      */ 
/*      */ 
/*      */         
/* 1340 */         if (rider instanceof EntityLivingBase) {
/*      */           
/* 1342 */           ds = DamageSource.func_76358_a((EntityLivingBase)rider);
/*      */         }
/*      */         else {
/*      */           
/* 1346 */           ds = DamageSource.field_76377_j;
/*      */         } 
/*      */         
/* 1349 */         MCH_Lib.applyEntityHurtResistantTimeConfig(e);
/* 1350 */         e.func_70097_a(ds, damage);
/*      */         
/* 1352 */         if (e instanceof MCH_EntityAircraft) {
/*      */           
/* 1354 */           e.field_70159_w += this.field_70159_w * 0.05D;
/* 1355 */           e.field_70179_y += this.field_70179_y * 0.05D;
/*      */         }
/* 1357 */         else if (e instanceof net.minecraft.entity.projectile.EntityArrow) {
/*      */           
/* 1359 */           e.func_70106_y();
/*      */         }
/*      */         else {
/*      */           
/* 1363 */           e.field_70159_w += this.field_70159_w * 1.5D;
/* 1364 */           e.field_70179_y += this.field_70179_y * 1.5D;
/*      */         } 
/*      */         
/* 1367 */         if ((getTankInfo()).weightType != 2 && (e.field_70130_N >= 1.0F || e.field_70131_O >= 1.5D)) {
/*      */           
/* 1369 */           if (e instanceof EntityLivingBase) {
/*      */             
/* 1371 */             ds = DamageSource.func_76358_a((EntityLivingBase)e);
/*      */           }
/*      */           else {
/*      */             
/* 1375 */             ds = DamageSource.field_76377_j;
/*      */           } 
/*      */           
/* 1378 */           func_70097_a(ds, damage / 3.0F);
/*      */         } 
/*      */         
/* 1381 */         MCH_Lib.DbgLog(this.field_70170_p, "MCH_EntityTank.collisionEntity damage=%.1f %s", new Object[] {
/*      */               
/* 1383 */               Float.valueOf(damage), e.toString()
/*      */             });
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean shouldCollisionDamage(Entity e) {
/* 1391 */     if (getSeatIdByEntity(e) >= 0) {
/* 1392 */       return false;
/*      */     }
/* 1394 */     if (this.noCollisionEntities.containsKey(e))
/*      */     {
/* 1396 */       return false;
/*      */     }
/*      */     
/* 1399 */     if (e instanceof MCH_EntityHitBox && ((MCH_EntityHitBox)e).parent != null) {
/*      */       
/* 1401 */       MCH_EntityAircraft ac = ((MCH_EntityHitBox)e).parent;
/*      */       
/* 1403 */       if (this.noCollisionEntities.containsKey(ac))
/*      */       {
/* 1405 */         return false;
/*      */       }
/*      */     } 
/*      */     
/* 1409 */     if (e.func_184187_bx() instanceof MCH_EntityAircraft)
/*      */     {
/* 1411 */       if (this.noCollisionEntities.containsKey(e.func_184187_bx()))
/*      */       {
/* 1413 */         return false;
/*      */       }
/*      */     }
/*      */     
/* 1417 */     if (e.func_184187_bx() instanceof MCH_EntitySeat && ((MCH_EntitySeat)e.func_184187_bx()).getParent() != null)
/*      */     {
/* 1419 */       if (this.noCollisionEntities.containsKey(((MCH_EntitySeat)e.func_184187_bx()).getParent()))
/*      */       {
/* 1421 */         return false;
/*      */       }
/*      */     }
/*      */     
/* 1425 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public void updateCollisionBox() {
/* 1430 */     if (getAcInfo() == null) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/* 1435 */     this.WheelMng.updateBlock();
/*      */     
/* 1437 */     for (MCH_BoundingBox bb : this.extraBoundingBox) {
/*      */       
/* 1439 */       if (this.field_70146_Z.nextInt(3) == 0) {
/*      */         
/* 1441 */         if (MCH_Config.Collision_DestroyBlock.prmBool) {
/*      */           
/* 1443 */           Vec3d v = getTransformedPosition(bb.offsetX, bb.offsetY, bb.offsetZ);
/*      */           
/* 1445 */           destoryBlockRange(v, bb.width, bb.height);
/*      */         } 
/*      */ 
/*      */         
/* 1449 */         collisionEntity(bb.getBoundingBox());
/*      */       } 
/*      */     } 
/*      */     
/* 1453 */     if (MCH_Config.Collision_DestroyBlock.prmBool)
/*      */     {
/* 1455 */       destoryBlockRange(getTransformedPosition(0.0D, 0.0D, 0.0D), this.field_70130_N * 1.5D, (this.field_70131_O * 2.0F));
/*      */     }
/*      */     
/* 1458 */     collisionEntity(func_70046_E());
/*      */   }
/*      */ 
/*      */   
/*      */   public void destoryBlockRange(Vec3d v, double w, double h) {
/* 1463 */     if (getAcInfo() == null) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/* 1468 */     List<Block> destroyBlocks = MCH_Config.getBreakableBlockListFromType((getTankInfo()).weightType);
/* 1469 */     List<Block> noDestroyBlocks = MCH_Config.getNoBreakableBlockListFromType((getTankInfo()).weightType);
/* 1470 */     List<Material> destroyMaterials = MCH_Config.getBreakableMaterialListFromType((getTankInfo()).weightType);
/* 1471 */     int ws = (int)(w + 2.0D) / 2;
/* 1472 */     int hs = (int)(h + 2.0D) / 2;
/*      */     
/* 1474 */     for (int x = -ws; x <= ws; x++) {
/*      */       
/* 1476 */       for (int z = -ws; z <= ws; z++) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1483 */         for (int y = -hs; y <= hs + 1; y++) {
/*      */           
/* 1485 */           int bx = (int)(v.field_72450_a + x - 0.5D);
/* 1486 */           int by = (int)(v.field_72448_b + y - 1.0D);
/* 1487 */           int bz = (int)(v.field_72449_c + z - 0.5D);
/*      */           
/* 1489 */           BlockPos blockpos = new BlockPos(bx, by, bz);
/* 1490 */           IBlockState iblockstate = this.field_70170_p.func_180495_p(blockpos);
/* 1491 */           Block block = (by >= 0 && by < 256) ? iblockstate.func_177230_c() : Blocks.field_150350_a;
/*      */           
/* 1493 */           Material mat = iblockstate.func_185904_a();
/*      */           
/* 1495 */           if (!Block.func_149680_a(block, Blocks.field_150350_a)) {
/*      */             
/* 1497 */             for (Block c : noDestroyBlocks) {
/*      */               
/* 1499 */               if (Block.func_149680_a(block, c)) {
/*      */                 
/* 1501 */                 block = null;
/*      */                 
/*      */                 break;
/*      */               } 
/*      */             } 
/* 1506 */             if (block == null) {
/*      */               break;
/*      */             }
/* 1509 */             for (Block c : destroyBlocks) {
/*      */               
/* 1511 */               if (Block.func_149680_a(block, c)) {
/*      */ 
/*      */                 
/* 1514 */                 destroyBlock(blockpos);
/* 1515 */                 mat = null;
/*      */                 
/*      */                 break;
/*      */               } 
/*      */             } 
/* 1520 */             if (mat == null) {
/*      */               break;
/*      */             }
/* 1523 */             for (Material m : destroyMaterials) {
/*      */ 
/*      */               
/* 1526 */               if (iblockstate.func_185904_a() == m) {
/*      */ 
/*      */                 
/* 1529 */                 destroyBlock(blockpos);
/*      */                 break;
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void destroyBlock(BlockPos blockpos) {
/* 1542 */     if (this.field_70146_Z.nextInt(8) == 0) {
/*      */ 
/*      */       
/* 1545 */       W_WorldFunc.destroyBlock(this.field_70170_p, blockpos, true);
/*      */     
/*      */     }
/*      */     else {
/*      */       
/* 1550 */       this.field_70170_p.func_175698_g(blockpos);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void updateWheels() {
/* 1556 */     this.WheelMng.move(this.field_70159_w, this.field_70181_x, this.field_70179_y);
/*      */   }
/*      */ 
/*      */   
/*      */   public float getMaxSpeed() {
/* 1561 */     return (getTankInfo()).speed + 0.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAngles(Entity player, boolean fixRot, float fixYaw, float fixPitch, float deltaX, float deltaY, float x, float y, float partialTicks) {
/* 1568 */     if (partialTicks < 0.03F) {
/* 1569 */       partialTicks = 0.4F;
/*      */     }
/* 1571 */     if (partialTicks > 0.9F) {
/* 1572 */       partialTicks = 0.6F;
/*      */     }
/* 1574 */     this.lowPassPartialTicks.put(partialTicks);
/* 1575 */     partialTicks = this.lowPassPartialTicks.getAvg();
/*      */     
/* 1577 */     float ac_pitch = getRotPitch();
/* 1578 */     float ac_yaw = getRotYaw();
/* 1579 */     float ac_roll = getRotRoll();
/*      */     
/* 1581 */     if (isFreeLookMode())
/*      */     {
/* 1583 */       x = y = 0.0F;
/*      */     }
/*      */     
/* 1586 */     float yaw = 0.0F;
/* 1587 */     float pitch = 0.0F;
/* 1588 */     float roll = 0.0F;
/*      */     
/* 1590 */     MCH_Math.FMatrix m_add = MCH_Math.newMatrix();
/*      */     
/* 1592 */     MCH_Math.MatTurnZ(m_add, roll / 180.0F * 3.1415927F);
/* 1593 */     MCH_Math.MatTurnX(m_add, pitch / 180.0F * 3.1415927F);
/* 1594 */     MCH_Math.MatTurnY(m_add, yaw / 180.0F * 3.1415927F);
/* 1595 */     MCH_Math.MatTurnZ(m_add, (float)((getRotRoll() / 180.0F) * Math.PI));
/* 1596 */     MCH_Math.MatTurnX(m_add, (float)((getRotPitch() / 180.0F) * Math.PI));
/* 1597 */     MCH_Math.MatTurnY(m_add, (float)((getRotYaw() / 180.0F) * Math.PI));
/* 1598 */     MCH_Math.FVector3D v = MCH_Math.MatrixToEuler(m_add);
/*      */     
/* 1600 */     v.x = MCH_Lib.RNG(v.x, -90.0F, 90.0F);
/* 1601 */     v.z = MCH_Lib.RNG(v.z, -90.0F, 90.0F);
/*      */     
/* 1603 */     if (v.z > 180.0F) {
/* 1604 */       v.z -= 360.0F;
/*      */     }
/* 1606 */     if (v.z < -180.0F)
/*      */     {
/* 1608 */       v.z += 360.0F;
/*      */     }
/*      */     
/* 1611 */     setRotYaw(v.y);
/* 1612 */     setRotPitch(v.x);
/* 1613 */     setRotRoll(v.z);
/* 1614 */     onUpdateAngles(partialTicks);
/*      */     
/* 1616 */     if ((getAcInfo()).limitRotation) {
/*      */       
/* 1618 */       v.x = MCH_Lib.RNG(getRotPitch(), -90.0F, 90.0F);
/* 1619 */       v.z = MCH_Lib.RNG(getRotRoll(), -90.0F, 90.0F);
/* 1620 */       setRotPitch(v.x);
/* 1621 */       setRotRoll(v.z);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1626 */     if (MathHelper.func_76135_e(getRotPitch()) > 90.0F) {
/*      */       
/* 1628 */       MCH_Lib.DbgLog(true, "MCH_EntityAircraft.setAngles Error:Pitch=%.1f", new Object[] {
/*      */             
/* 1630 */             Float.valueOf(getRotPitch())
/*      */           });
/* 1632 */       setRotPitch(0.0F);
/*      */     } 
/*      */     
/* 1635 */     if (getRotRoll() > 180.0F)
/*      */     {
/* 1637 */       setRotRoll(getRotRoll() - 360.0F);
/*      */     }
/*      */     
/* 1640 */     if (getRotRoll() < -180.0F)
/*      */     {
/* 1642 */       setRotRoll(getRotRoll() + 360.0F);
/*      */     }
/*      */     
/* 1645 */     this.prevRotationRoll = getRotRoll();
/* 1646 */     this.field_70127_C = getRotPitch();
/*      */     
/* 1648 */     if (func_184187_bx() == null)
/*      */     {
/* 1650 */       this.field_70126_B = getRotYaw();
/*      */     }
/*      */     
/* 1653 */     float deltaLimit = (getAcInfo()).cameraRotationSpeed * partialTicks;
/*      */     
/* 1655 */     MCH_WeaponSet ws = getCurrentWeapon(player);
/* 1656 */     deltaLimit *= (ws != null && ws.getInfo() != null) ? (ws.getInfo()).cameraRotationSpeedPitch : 1.0F;
/*      */     
/* 1658 */     if (deltaX > deltaLimit)
/*      */     {
/* 1660 */       deltaX = deltaLimit;
/*      */     }
/*      */     
/* 1663 */     if (deltaX < -deltaLimit)
/*      */     {
/* 1665 */       deltaX = -deltaLimit;
/*      */     }
/*      */     
/* 1668 */     if (deltaY > deltaLimit)
/*      */     {
/* 1670 */       deltaY = deltaLimit;
/*      */     }
/*      */     
/* 1673 */     if (deltaY < -deltaLimit)
/*      */     {
/* 1675 */       deltaY = -deltaLimit;
/*      */     }
/*      */     
/* 1678 */     if (isOverridePlayerYaw() || fixRot) {
/*      */       
/* 1680 */       if (func_184187_bx() == null) {
/*      */         
/* 1682 */         player.field_70126_B = getRotYaw() + fixYaw;
/*      */       }
/*      */       else {
/*      */         
/* 1686 */         if (getRotYaw() - player.field_70177_z > 180.0F)
/*      */         {
/* 1688 */           player.field_70126_B += 360.0F;
/*      */         }
/*      */         
/* 1691 */         if (getRotYaw() - player.field_70177_z < -180.0F)
/*      */         {
/* 1693 */           player.field_70126_B -= 360.0F;
/*      */         }
/*      */       } 
/* 1696 */       player.field_70177_z = getRotYaw() + fixYaw;
/*      */     }
/*      */     else {
/*      */       
/* 1700 */       player.func_70082_c(deltaX, 0.0F);
/*      */     } 
/*      */     
/* 1703 */     if (isOverridePlayerPitch() || fixRot) {
/*      */       
/* 1705 */       player.field_70127_C = getRotPitch() + fixPitch;
/* 1706 */       player.field_70125_A = getRotPitch() + fixPitch;
/*      */     }
/*      */     else {
/*      */       
/* 1710 */       player.func_70082_c(0.0F, deltaY);
/*      */     } 
/*      */     
/* 1713 */     float playerYaw = MathHelper.func_76142_g(getRotYaw() - player.field_70177_z);
/*      */     
/* 1715 */     float playerPitch = getRotPitch() * MathHelper.func_76134_b((float)(playerYaw * Math.PI / 180.0D)) + -getRotRoll() * MathHelper.func_76126_a((float)(playerYaw * Math.PI / 180.0D));
/*      */     
/* 1717 */     if (MCH_MOD.proxy.isFirstPerson()) {
/*      */       
/* 1719 */       player.field_70125_A = MCH_Lib.RNG(player.field_70125_A, playerPitch + (getAcInfo()).minRotationPitch, playerPitch + 
/* 1720 */           (getAcInfo()).maxRotationPitch);
/* 1721 */       player.field_70125_A = MCH_Lib.RNG(player.field_70125_A, -90.0F, 90.0F);
/*      */     } 
/*      */     
/* 1724 */     player.field_70127_C = player.field_70125_A;
/*      */     
/* 1726 */     if ((func_184187_bx() == null && ac_yaw != getRotYaw()) || ac_pitch != getRotPitch() || ac_roll != 
/* 1727 */       getRotRoll())
/*      */     {
/* 1729 */       this.aircraftRotChanged = true;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public float getSoundVolume() {
/* 1736 */     if (getAcInfo() != null && (getAcInfo()).throttleUpDown <= 0.0F) {
/* 1737 */       return 0.0F;
/*      */     }
/* 1739 */     return this.soundVolume * 0.7F;
/*      */   }
/*      */ 
/*      */   
/*      */   public void updateSound() {
/* 1744 */     float target = (float)getCurrentThrottle();
/*      */     
/* 1746 */     if (getRiddenByEntity() != null)
/*      */     {
/* 1748 */       if (this.partCanopy == null || getCanopyRotation() < 1.0F)
/*      */       {
/* 1750 */         target += 0.1F;
/*      */       }
/*      */     }
/*      */     
/* 1754 */     if (this.moveLeft || this.moveRight || this.throttleDown) {
/*      */       
/* 1756 */       this.soundVolumeTarget += 0.1F;
/*      */       
/* 1758 */       if (this.soundVolumeTarget > 0.75F)
/*      */       {
/* 1760 */         this.soundVolumeTarget = 0.75F;
/*      */       }
/*      */     }
/*      */     else {
/*      */       
/* 1765 */       this.soundVolumeTarget *= 0.8F;
/*      */     } 
/*      */     
/* 1768 */     if (target < this.soundVolumeTarget)
/*      */     {
/* 1770 */       target = this.soundVolumeTarget;
/*      */     }
/*      */     
/* 1773 */     if (this.soundVolume < target) {
/*      */       
/* 1775 */       this.soundVolume += 0.02F;
/*      */       
/* 1777 */       if (this.soundVolume >= target)
/*      */       {
/* 1779 */         this.soundVolume = target;
/*      */       }
/*      */     }
/* 1782 */     else if (this.soundVolume > target) {
/*      */       
/* 1784 */       this.soundVolume -= 0.02F;
/*      */       
/* 1786 */       if (this.soundVolume <= target)
/*      */       {
/* 1788 */         this.soundVolume = target;
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public float getSoundPitch() {
/* 1796 */     float target1 = (float)(0.5D + getCurrentThrottle() * 0.5D);
/* 1797 */     float target2 = (float)(0.5D + this.soundVolumeTarget * 0.5D);
/*      */     
/* 1799 */     return (target1 > target2) ? target1 : target2;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDefaultSoundName() {
/* 1805 */     return "prop";
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hasBrake() {
/* 1811 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateParts(int stat) {
/* 1817 */     super.updateParts(stat);
/*      */     
/* 1819 */     if (isDestroyed()) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/* 1824 */     MCH_Parts[] parts = new MCH_Parts[0];
/*      */     
/* 1826 */     for (MCH_Parts p : parts) {
/*      */       
/* 1828 */       if (p != null) {
/*      */         
/* 1830 */         p.updateStatusClient(stat);
/* 1831 */         p.update();
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public float getUnfoldLandingGearThrottle() {
/* 1839 */     return 0.7F;
/*      */   }
/*      */ 
/*      */   
/*      */   private static class ClacAxisBB
/*      */   {
/*      */     public final double value;
/*      */     public final AxisAlignedBB bb;
/*      */     
/*      */     public ClacAxisBB(double value, AxisAlignedBB bb) {
/* 1849 */       this.value = value;
/* 1850 */       this.bb = bb;
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\tank\MCH_EntityTank.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */