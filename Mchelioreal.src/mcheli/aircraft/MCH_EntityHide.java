/*     */ package mcheli.aircraft;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import mcheli.__helper.entity.IEntitySinglePassenger;
/*     */ import mcheli.wrapper.W_Entity;
/*     */ import mcheli.wrapper.W_Lib;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.crash.CrashReport;
/*     */ import net.minecraft.crash.CrashReportCategory;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.MoverType;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.network.datasync.DataParameter;
/*     */ import net.minecraft.network.datasync.DataSerializers;
/*     */ import net.minecraft.network.datasync.EntityDataManager;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.ReportedException;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.border.WorldBorder;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MCH_EntityHide
/*     */   extends W_Entity
/*     */   implements IEntitySinglePassenger
/*     */ {
/*  49 */   private static final DataParameter<Integer> ROPE_INDEX = EntityDataManager.func_187226_a(MCH_EntityHide.class, DataSerializers.field_187192_b);
/*     */   
/*  51 */   private static final DataParameter<Integer> AC_ID = EntityDataManager.func_187226_a(MCH_EntityHide.class, DataSerializers.field_187192_b);
/*     */   
/*     */   private MCH_EntityAircraft ac;
/*     */   
/*     */   private Entity user;
/*     */   
/*     */   private int paraPosRotInc;
/*     */   private double paraX;
/*     */   private double paraY;
/*     */   private double paraZ;
/*     */   private double paraYaw;
/*     */   private double paraPitch;
/*     */   @SideOnly(Side.CLIENT)
/*     */   private double velocityX;
/*     */   @SideOnly(Side.CLIENT)
/*     */   private double velocityY;
/*     */   @SideOnly(Side.CLIENT)
/*     */   private double velocityZ;
/*     */   
/*     */   public MCH_EntityHide(World par1World) {
/*  71 */     super(par1World);
/*     */     
/*  73 */     func_70105_a(1.0F, 1.0F);
/*     */     
/*  75 */     this.field_70156_m = true;
/*     */     
/*  77 */     this.user = null;
/*  78 */     this.field_70159_w = this.field_70181_x = this.field_70179_y = 0.0D;
/*     */   }
/*     */ 
/*     */   
/*     */   public MCH_EntityHide(World par1World, double x, double y, double z) {
/*  83 */     this(par1World);
/*  84 */     this.field_70165_t = x;
/*  85 */     this.field_70163_u = y;
/*  86 */     this.field_70161_v = z;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_70088_a() {
/*  92 */     super.func_70088_a();
/*     */     
/*  94 */     createRopeIndex(-1);
/*     */     
/*  96 */     this.field_70180_af.func_187214_a(AC_ID, new Integer(0));
/*     */   }
/*     */ 
/*     */   
/*     */   public void setParent(MCH_EntityAircraft ac, Entity user, int ropeIdx) {
/* 101 */     this.ac = ac;
/* 102 */     setRopeIndex(ropeIdx);
/* 103 */     this.user = user;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean func_70041_e_() {
/* 109 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AxisAlignedBB func_70114_g(Entity par1Entity) {
/* 116 */     return par1Entity.func_174813_aQ();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AxisAlignedBB func_70046_E() {
/* 123 */     return func_174813_aQ();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_70104_M() {
/* 129 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double func_70042_X() {
/* 135 */     return this.field_70131_O * 0.0D - 0.3D;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_70097_a(DamageSource par1DamageSource, float par2) {
/* 141 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_70067_L() {
/* 147 */     return !this.field_70128_L;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_70014_b(NBTTagCompound nbt) {}
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_70037_a(NBTTagCompound nbt) {}
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public float getShadowSize() {
/* 163 */     return 0.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_184230_a(EntityPlayer player, EnumHand hand) {
/* 170 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void createRopeIndex(int defaultValue) {
/* 176 */     this.field_70180_af.func_187214_a(ROPE_INDEX, new Integer(defaultValue));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRopeIndex() {
/* 182 */     return ((Integer)this.field_70180_af.func_187225_a(ROPE_INDEX)).intValue();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRopeIndex(int value) {
/* 188 */     this.field_70180_af.func_187227_b(ROPE_INDEX, new Integer(value));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_180426_a(double par1, double par3, double par5, float par7, float par8, int par9, boolean teleport) {
/* 197 */     this.paraPosRotInc = par9 + 10;
/*     */     
/* 199 */     this.paraX = par1;
/* 200 */     this.paraY = par3;
/* 201 */     this.paraZ = par5;
/* 202 */     this.paraYaw = par7;
/* 203 */     this.paraPitch = par8;
/* 204 */     this.field_70159_w = this.velocityX;
/* 205 */     this.field_70181_x = this.velocityY;
/* 206 */     this.field_70179_y = this.velocityZ;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_70016_h(double par1, double par3, double par5) {
/* 213 */     this.velocityX = this.field_70159_w = par1;
/* 214 */     this.velocityY = this.field_70181_x = par3;
/* 215 */     this.velocityZ = this.field_70179_y = par5;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_70106_y() {
/* 221 */     super.func_70106_y();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_70071_h_() {
/* 227 */     super.func_70071_h_();
/*     */     
/* 229 */     if (this.user != null && !this.field_70170_p.field_72995_K) {
/*     */       
/* 231 */       if (this.ac != null)
/*     */       {
/*     */         
/* 234 */         this.field_70180_af.func_187227_b(AC_ID, new Integer(this.ac.func_145782_y()));
/*     */       }
/*     */ 
/*     */       
/* 238 */       this.user.func_184205_a((Entity)this, true);
/* 239 */       this.user = null;
/*     */     } 
/*     */     
/* 242 */     if (this.ac == null && this.field_70170_p.field_72995_K) {
/*     */ 
/*     */       
/* 245 */       int id = ((Integer)this.field_70180_af.func_187225_a(AC_ID)).intValue();
/*     */       
/* 247 */       if (id > 0) {
/*     */         
/* 249 */         Entity entity = this.field_70170_p.func_73045_a(id);
/*     */         
/* 251 */         if (entity instanceof MCH_EntityAircraft)
/*     */         {
/* 253 */           this.ac = (MCH_EntityAircraft)entity;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 258 */     this.field_70169_q = this.field_70165_t;
/* 259 */     this.field_70167_r = this.field_70163_u;
/* 260 */     this.field_70166_s = this.field_70161_v;
/* 261 */     this.field_70143_R = 0.0F;
/*     */     
/* 263 */     Entity riddenByEntity = getRiddenByEntity();
/*     */ 
/*     */     
/* 266 */     if (riddenByEntity != null)
/*     */     {
/*     */       
/* 269 */       riddenByEntity.field_70143_R = 0.0F;
/*     */     }
/*     */     
/* 272 */     if (this.ac != null) {
/*     */       
/* 274 */       if (!this.ac.isRepelling())
/*     */       {
/* 276 */         func_70106_y();
/*     */       }
/*     */       
/* 279 */       int id = getRopeIndex();
/*     */       
/* 281 */       if (id >= 0) {
/*     */         
/* 283 */         Vec3d v = this.ac.getRopePos(id);
/* 284 */         this.field_70165_t = v.field_72450_a;
/* 285 */         this.field_70161_v = v.field_72449_c;
/*     */       } 
/*     */     } 
/*     */     
/* 289 */     func_70107_b(this.field_70165_t, this.field_70163_u, this.field_70161_v);
/*     */     
/* 291 */     if (this.field_70170_p.field_72995_K) {
/*     */       
/* 293 */       onUpdateClient();
/*     */     }
/*     */     else {
/*     */       
/* 297 */       onUpdateServer();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onUpdateClient() {
/* 303 */     if (this.paraPosRotInc > 0) {
/*     */       
/* 305 */       double x = this.field_70165_t + (this.paraX - this.field_70165_t) / this.paraPosRotInc;
/* 306 */       double y = this.field_70163_u + (this.paraY - this.field_70163_u) / this.paraPosRotInc;
/* 307 */       double z = this.field_70161_v + (this.paraZ - this.field_70161_v) / this.paraPosRotInc;
/* 308 */       double yaw = MathHelper.func_76138_g(this.paraYaw - this.field_70177_z);
/*     */       
/* 310 */       this.field_70177_z = (float)(this.field_70177_z + yaw / this.paraPosRotInc);
/* 311 */       this.field_70125_A = (float)(this.field_70125_A + (this.paraPitch - this.field_70125_A) / this.paraPosRotInc);
/*     */ 
/*     */       
/* 314 */       this.paraPosRotInc--;
/*     */       
/* 316 */       func_70107_b(x, y, z);
/* 317 */       func_70101_b(this.field_70177_z, this.field_70125_A);
/*     */       
/* 319 */       Entity riddenByEntity = getRiddenByEntity();
/*     */ 
/*     */       
/* 322 */       if (riddenByEntity != null)
/*     */       {
/*     */         
/* 325 */         func_70101_b(riddenByEntity.field_70126_B, this.field_70125_A);
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 330 */       func_70107_b(this.field_70165_t + this.field_70159_w, this.field_70163_u + this.field_70181_x, this.field_70161_v + this.field_70179_y);
/*     */       
/* 332 */       this.field_70159_w *= 0.99D;
/* 333 */       this.field_70181_x *= 0.95D;
/* 334 */       this.field_70179_y *= 0.99D;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onUpdateServer() {
/* 340 */     this.field_70181_x -= this.field_70122_E ? 0.01D : 0.03D;
/*     */     
/* 342 */     if (this.field_70122_E) {
/*     */       
/* 344 */       onGroundAndDead();
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 349 */     func_70091_d(MoverType.SELF, this.field_70159_w, this.field_70181_x, this.field_70179_y);
/*     */     
/* 351 */     this.field_70181_x *= 0.9D;
/* 352 */     this.field_70159_w *= 0.95D;
/* 353 */     this.field_70179_y *= 0.95D;
/*     */     
/* 355 */     int id = getRopeIndex();
/*     */     
/* 357 */     if (this.ac != null && id >= 0) {
/*     */       
/* 359 */       Vec3d v = this.ac.getRopePos(id);
/*     */       
/* 361 */       if (Math.abs(this.field_70163_u - v.field_72448_b) > (Math.abs(this.ac.ropesLength) + 5.0F))
/*     */       {
/* 363 */         onGroundAndDead();
/*     */       }
/*     */     } 
/*     */     
/* 367 */     Entity riddenByEntity = getRiddenByEntity();
/*     */ 
/*     */     
/* 370 */     if (riddenByEntity != null && riddenByEntity.field_70128_L)
/*     */     {
/*     */       
/* 373 */       func_70106_y();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean getCollisionBoxes(@Nullable Entity entityIn, AxisAlignedBB aabb, List<AxisAlignedBB> outList) {
/* 379 */     int i = MathHelper.func_76128_c(aabb.field_72340_a) - 1;
/* 380 */     int j = MathHelper.func_76143_f(aabb.field_72336_d) + 1;
/* 381 */     int k = MathHelper.func_76128_c(aabb.field_72338_b) - 1;
/* 382 */     int l = MathHelper.func_76143_f(aabb.field_72337_e) + 1;
/* 383 */     int i1 = MathHelper.func_76128_c(aabb.field_72339_c) - 1;
/* 384 */     int j1 = MathHelper.func_76143_f(aabb.field_72334_f) + 1;
/* 385 */     WorldBorder worldborder = this.field_70170_p.func_175723_af();
/* 386 */     boolean flag = (entityIn != null && entityIn.func_174832_aS());
/* 387 */     boolean flag1 = (entityIn != null && this.field_70170_p.func_191503_g(entityIn));
/* 388 */     IBlockState iblockstate = Blocks.field_150348_b.func_176223_P();
/* 389 */     BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos = BlockPos.PooledMutableBlockPos.func_185346_s();
/*     */ 
/*     */     
/*     */     try {
/* 393 */       for (int k1 = i; k1 < j; k1++) {
/*     */         
/* 395 */         for (int l1 = i1; l1 < j1; l1++) {
/*     */           
/* 397 */           boolean flag2 = (k1 == i || k1 == j - 1);
/* 398 */           boolean flag3 = (l1 == i1 || l1 == j1 - 1);
/*     */           
/* 400 */           if ((!flag2 || !flag3) && this.field_70170_p
/* 401 */             .func_175667_e((BlockPos)blockpos$pooledmutableblockpos.func_181079_c(k1, 64, l1)))
/*     */           {
/* 403 */             for (int i2 = k; i2 < l; i2++)
/*     */             {
/* 405 */               if ((!flag2 && !flag3) || i2 != l - 1) {
/*     */                 IBlockState iblockstate1;
/* 407 */                 if (entityIn != null && flag == flag1)
/*     */                 {
/* 409 */                   entityIn.func_174821_h(!flag1);
/*     */                 }
/*     */                 
/* 412 */                 blockpos$pooledmutableblockpos.func_181079_c(k1, i2, l1);
/*     */ 
/*     */                 
/* 415 */                 if (!worldborder.func_177746_a((BlockPos)blockpos$pooledmutableblockpos) && flag1) {
/*     */                   
/* 417 */                   iblockstate1 = iblockstate;
/*     */                 }
/*     */                 else {
/*     */                   
/* 421 */                   iblockstate1 = this.field_70170_p.func_180495_p((BlockPos)blockpos$pooledmutableblockpos);
/*     */                 } 
/*     */                 
/* 424 */                 iblockstate1.func_185908_a(this.field_70170_p, (BlockPos)blockpos$pooledmutableblockpos, aabb, outList, entityIn, false);
/*     */               }
/*     */             
/*     */             }
/*     */           
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } finally {
/*     */       
/* 434 */       blockpos$pooledmutableblockpos.func_185344_t();
/*     */     } 
/*     */     
/* 437 */     return !outList.isEmpty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<AxisAlignedBB> getCollidingBoundingBoxes(Entity par1Entity, AxisAlignedBB par2AxisAlignedBB) {
/* 444 */     List<AxisAlignedBB> list = new ArrayList<>();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 476 */     getCollisionBoxes(par1Entity, par2AxisAlignedBB, list);
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
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 505 */     if (par1Entity != null) {
/*     */       
/* 507 */       List<Entity> list1 = this.field_70170_p.func_72839_b(par1Entity, par2AxisAlignedBB
/* 508 */           .func_186662_g(0.25D));
/*     */       
/* 510 */       for (int i = 0; i < list1.size(); i++) {
/*     */         
/* 512 */         Entity entity = list1.get(i);
/*     */         
/* 514 */         if (!W_Lib.isEntityLivingBase(entity) && !(entity instanceof MCH_EntitySeat) && !(entity instanceof MCH_EntityHitBox)) {
/*     */ 
/*     */           
/* 517 */           AxisAlignedBB axisalignedbb = entity.func_70046_E();
/*     */           
/* 519 */           if (axisalignedbb != null && axisalignedbb.func_72326_a(par2AxisAlignedBB))
/*     */           {
/* 521 */             list.add(axisalignedbb);
/*     */           }
/*     */           
/* 524 */           axisalignedbb = par1Entity.func_70114_g(entity);
/*     */           
/* 526 */           if (axisalignedbb != null && axisalignedbb.func_72326_a(par2AxisAlignedBB))
/*     */           {
/* 528 */             list.add(axisalignedbb);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 534 */     return list;
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
/*     */   public void func_70091_d(MoverType type, double x, double y, double z) {
/* 546 */     this.field_70170_p.field_72984_F.func_76320_a("move");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 554 */     double d2 = x;
/* 555 */     double d3 = y;
/* 556 */     double d4 = z;
/*     */ 
/*     */     
/* 559 */     List<AxisAlignedBB> list1 = getCollidingBoundingBoxes((Entity)this, func_174813_aQ().func_72321_a(x, y, z));
/* 560 */     AxisAlignedBB axisalignedbb = func_174813_aQ();
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
/*     */ 
/*     */     
/* 575 */     if (y != 0.0D) {
/*     */       
/* 577 */       int k = 0;
/*     */       
/* 579 */       for (int l = list1.size(); k < l; k++)
/*     */       {
/* 581 */         y = ((AxisAlignedBB)list1.get(k)).func_72323_b(func_174813_aQ(), y);
/*     */       }
/*     */       
/* 584 */       func_174826_a(func_174813_aQ().func_72317_d(0.0D, y, 0.0D));
/*     */     } 
/*     */ 
/*     */     
/* 588 */     boolean flag = (this.field_70122_E || (d3 != y && d3 < 0.0D));
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
/*     */ 
/*     */ 
/*     */     
/* 604 */     if (x != 0.0D) {
/*     */       
/* 606 */       int j5 = 0;
/*     */       
/* 608 */       for (int l5 = list1.size(); j5 < l5; j5++)
/*     */       {
/* 610 */         x = ((AxisAlignedBB)list1.get(j5)).func_72316_a(func_174813_aQ(), x);
/*     */       }
/*     */       
/* 613 */       if (x != 0.0D)
/*     */       {
/* 615 */         func_174826_a(func_174813_aQ().func_72317_d(x, 0.0D, 0.0D));
/*     */       }
/*     */     } 
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
/*     */ 
/*     */     
/* 632 */     if (z != 0.0D) {
/*     */       
/* 634 */       int k5 = 0;
/*     */       
/* 636 */       for (int i6 = list1.size(); k5 < i6; k5++)
/*     */       {
/* 638 */         z = ((AxisAlignedBB)list1.get(k5)).func_72322_c(func_174813_aQ(), z);
/*     */       }
/*     */       
/* 641 */       if (z != 0.0D)
/*     */       {
/* 643 */         func_174826_a(func_174813_aQ().func_72317_d(0.0D, 0.0D, z));
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 648 */     if (this.field_70138_W > 0.0F && flag && (d2 != x || d4 != z)) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 653 */       double d14 = x;
/* 654 */       double d6 = y;
/* 655 */       double d7 = z;
/*     */ 
/*     */ 
/*     */       
/* 659 */       y = this.field_70138_W;
/*     */ 
/*     */       
/* 662 */       AxisAlignedBB axisalignedbb1 = func_174813_aQ();
/* 663 */       func_174826_a(axisalignedbb);
/*     */       
/* 665 */       List<AxisAlignedBB> list = getCollidingBoundingBoxes((Entity)this, 
/* 666 */           func_174813_aQ().func_72321_a(d2, y, d4));
/* 667 */       AxisAlignedBB axisalignedbb2 = func_174813_aQ();
/* 668 */       AxisAlignedBB axisalignedbb3 = axisalignedbb2.func_72321_a(d2, 0.0D, d4);
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
/*     */ 
/*     */       
/* 683 */       double d8 = y;
/*     */       
/* 685 */       for (int j1 = 0; j1 < list.size(); j1++)
/*     */       {
/* 687 */         d8 = ((AxisAlignedBB)list.get(j1)).func_72323_b(axisalignedbb3, d8);
/*     */       }
/*     */       
/* 690 */       axisalignedbb2 = axisalignedbb2.func_72317_d(0.0D, d8, 0.0D);
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
/*     */ 
/*     */       
/* 705 */       double d18 = d2;
/*     */       
/* 707 */       for (int l1 = 0; l1 < list.size(); l1++)
/*     */       {
/* 709 */         d18 = ((AxisAlignedBB)list.get(l1)).func_72316_a(axisalignedbb2, d18);
/*     */       }
/*     */       
/* 712 */       axisalignedbb2 = axisalignedbb2.func_72317_d(d18, 0.0D, 0.0D);
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
/*     */ 
/*     */       
/* 727 */       double d19 = d4;
/*     */       
/* 729 */       for (int j2 = 0; j2 < list.size(); j2++)
/*     */       {
/* 731 */         d19 = ((AxisAlignedBB)list.get(j2)).func_72322_c(axisalignedbb2, d19);
/*     */       }
/*     */       
/* 734 */       axisalignedbb2 = axisalignedbb2.func_72317_d(0.0D, 0.0D, d19);
/*     */       
/* 736 */       AxisAlignedBB axisalignedbb4 = func_174813_aQ();
/* 737 */       double d20 = y;
/*     */       
/* 739 */       for (int l2 = 0; l2 < list.size(); l2++)
/*     */       {
/* 741 */         d20 = ((AxisAlignedBB)list.get(l2)).func_72323_b(axisalignedbb4, d20);
/*     */       }
/*     */       
/* 744 */       axisalignedbb4 = axisalignedbb4.func_72317_d(0.0D, d20, 0.0D);
/* 745 */       double d21 = d2;
/*     */       
/* 747 */       for (int j3 = 0; j3 < list.size(); j3++)
/*     */       {
/* 749 */         d21 = ((AxisAlignedBB)list.get(j3)).func_72316_a(axisalignedbb4, d21);
/*     */       }
/*     */       
/* 752 */       axisalignedbb4 = axisalignedbb4.func_72317_d(d21, 0.0D, 0.0D);
/* 753 */       double d22 = d4;
/*     */       
/* 755 */       for (int l3 = 0; l3 < list.size(); l3++)
/*     */       {
/* 757 */         d22 = ((AxisAlignedBB)list.get(l3)).func_72322_c(axisalignedbb4, d22);
/*     */       }
/*     */       
/* 760 */       axisalignedbb4 = axisalignedbb4.func_72317_d(0.0D, 0.0D, d22);
/* 761 */       double d23 = d18 * d18 + d19 * d19;
/* 762 */       double d9 = d21 * d21 + d22 * d22;
/*     */       
/* 764 */       if (d23 > d9) {
/*     */         
/* 766 */         x = d18;
/* 767 */         z = d19;
/* 768 */         y = -d8;
/* 769 */         func_174826_a(axisalignedbb2);
/*     */       }
/*     */       else {
/*     */         
/* 773 */         x = d21;
/* 774 */         z = d22;
/* 775 */         y = -d20;
/* 776 */         func_174826_a(axisalignedbb4);
/*     */       } 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 796 */       for (int j4 = 0; j4 < list.size(); j4++)
/*     */       {
/* 798 */         y = ((AxisAlignedBB)list.get(j4)).func_72323_b(func_174813_aQ(), y);
/*     */       }
/*     */       
/* 801 */       func_174826_a(func_174813_aQ().func_72317_d(0.0D, y, 0.0D));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 810 */       if (d14 * d14 + d7 * d7 >= x * x + z * z) {
/*     */         
/* 812 */         x = d14;
/* 813 */         y = d6;
/* 814 */         z = d7;
/* 815 */         func_174826_a(axisalignedbb1);
/*     */       } 
/*     */     } 
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
/* 829 */     this.field_70170_p.field_72984_F.func_76319_b();
/* 830 */     this.field_70170_p.field_72984_F.func_76320_a("rest");
/* 831 */     func_174829_m();
/* 832 */     this.field_70123_F = (d2 != x || d4 != z);
/* 833 */     this.field_70124_G = (d3 != y);
/* 834 */     this.field_70122_E = (this.field_70124_G && d3 < 0.0D);
/* 835 */     this.field_70132_H = (this.field_70123_F || this.field_70124_G);
/* 836 */     int j6 = MathHelper.func_76128_c(this.field_70165_t);
/* 837 */     int i1 = MathHelper.func_76128_c(this.field_70163_u - 0.20000000298023224D);
/* 838 */     int k6 = MathHelper.func_76128_c(this.field_70161_v);
/* 839 */     BlockPos blockpos = new BlockPos(j6, i1, k6);
/* 840 */     IBlockState iblockstate = this.field_70170_p.func_180495_p(blockpos);
/*     */     
/* 842 */     if (iblockstate.func_185904_a() == Material.field_151579_a) {
/*     */       
/* 844 */       BlockPos blockpos1 = blockpos.func_177977_b();
/* 845 */       IBlockState iblockstate1 = this.field_70170_p.func_180495_p(blockpos1);
/* 846 */       Block block1 = iblockstate1.func_177230_c();
/*     */       
/* 848 */       if (block1 instanceof net.minecraft.block.BlockFence || block1 instanceof net.minecraft.block.BlockWall || block1 instanceof net.minecraft.block.BlockFenceGate) {
/*     */         
/* 850 */         iblockstate = iblockstate1;
/* 851 */         blockpos = blockpos1;
/*     */       } 
/*     */     } 
/*     */     
/* 855 */     func_184231_a(y, this.field_70122_E, iblockstate, blockpos);
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
/*     */ 
/*     */ 
/*     */     
/* 871 */     if (d2 != x)
/*     */     {
/* 873 */       this.field_70159_w = 0.0D;
/*     */     }
/*     */     
/* 876 */     if (d4 != z)
/*     */     {
/* 878 */       this.field_70179_y = 0.0D;
/*     */     }
/*     */     
/* 881 */     Block block = iblockstate.func_177230_c();
/*     */     
/* 883 */     if (d3 != y)
/*     */     {
/* 885 */       block.func_176216_a(this.field_70170_p, (Entity)this);
/*     */     }
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 905 */       func_145775_I();
/*     */     }
/* 907 */     catch (Throwable throwable) {
/*     */       
/* 909 */       CrashReport crashreport = CrashReport.func_85055_a(throwable, "Checking entity block collision");
/* 910 */       CrashReportCategory crashreportcategory = crashreport.func_85058_a("Entity being checked for collision");
/* 911 */       func_85029_a(crashreportcategory);
/* 912 */       throw new ReportedException(crashreport);
/*     */     } 
/*     */     
/* 915 */     this.field_70170_p.field_72984_F.func_76319_b();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onGroundAndDead() {
/* 921 */     this.field_70163_u += 0.5D;
/*     */     
/* 923 */     func_184232_k(getRiddenByEntity());
/* 924 */     func_70106_y();
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
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Entity getRiddenByEntity() {
/* 942 */     List<Entity> passengers = func_184188_bt();
/* 943 */     return passengers.isEmpty() ? null : passengers.get(0);
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\aircraft\MCH_EntityHide.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */