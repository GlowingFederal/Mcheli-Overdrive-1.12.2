/*     */ package mcheli.tank;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import mcheli.aircraft.MCH_EntityAircraft;
/*     */ import mcheli.wrapper.W_Entity;
/*     */ import mcheli.wrapper.W_Lib;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.crash.CrashReport;
/*     */ import net.minecraft.crash.CrashReportCategory;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.MoverType;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.ReportedException;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.border.WorldBorder;
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
/*     */ public class MCH_EntityWheel
/*     */   extends W_Entity
/*     */ {
/*     */   private MCH_EntityAircraft parents;
/*     */   public Vec3d pos;
/*     */   boolean isPlus;
/*     */   
/*     */   public MCH_EntityWheel(World w) {
/*  45 */     super(w);
/*  46 */     func_70105_a(1.0F, 1.0F);
/*  47 */     this.field_70138_W = 1.5F;
/*  48 */     this.field_70178_ae = true;
/*  49 */     this.isPlus = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setWheelPos(Vec3d pos, Vec3d weightedCenter) {
/*  54 */     this.pos = pos;
/*  55 */     this.isPlus = (pos.field_72449_c >= weightedCenter.field_72449_c);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void travelToDimension(int dimensionId) {}
/*     */ 
/*     */   
/*     */   public MCH_EntityAircraft getParents() {
/*  64 */     return this.parents;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setParents(MCH_EntityAircraft parents) {
/*  69 */     this.parents = parents;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_70037_a(NBTTagCompound compound) {
/*  75 */     func_70106_y();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_70014_b(NBTTagCompound compound) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_70091_d(MoverType type, double x, double y, double z) {
/*  92 */     this.field_70170_p.field_72984_F.func_76320_a("move");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 100 */     double d2 = x;
/* 101 */     double d3 = y;
/* 102 */     double d4 = z;
/*     */ 
/*     */     
/* 105 */     List<AxisAlignedBB> list1 = getCollisionBoxes((Entity)this, func_174813_aQ().func_72321_a(x, y, z));
/* 106 */     AxisAlignedBB axisalignedbb = func_174813_aQ();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 113 */     if (y != 0.0D) {
/*     */       
/* 115 */       for (int k = 0; k < list1.size(); k++)
/*     */       {
/* 117 */         y = ((AxisAlignedBB)list1.get(k)).func_72323_b(func_174813_aQ(), y);
/*     */       }
/*     */       
/* 120 */       func_174826_a(func_174813_aQ().func_72317_d(0.0D, y, 0.0D));
/*     */     } 
/*     */ 
/*     */     
/* 124 */     boolean flag = (this.field_70122_E || (d3 != y && d3 < 0.0D));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 131 */     if (x != 0.0D) {
/*     */       
/* 133 */       for (int j5 = 0; j5 < list1.size(); j5++)
/*     */       {
/* 135 */         x = ((AxisAlignedBB)list1.get(j5)).func_72316_a(func_174813_aQ(), x);
/*     */       }
/*     */       
/* 138 */       if (x != 0.0D)
/*     */       {
/* 140 */         func_174826_a(func_174813_aQ().func_72317_d(x, 0.0D, 0.0D));
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 149 */     if (z != 0.0D) {
/*     */       
/* 151 */       for (int k5 = 0; k5 < list1.size(); k5++)
/*     */       {
/* 153 */         z = ((AxisAlignedBB)list1.get(k5)).func_72322_c(func_174813_aQ(), z);
/*     */       }
/*     */       
/* 156 */       if (z != 0.0D)
/*     */       {
/* 158 */         func_174826_a(func_174813_aQ().func_72317_d(0.0D, 0.0D, z));
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 163 */     if (this.field_70138_W > 0.0F && flag && (d2 != x || d4 != z)) {
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
/* 174 */       double d14 = x;
/* 175 */       double d6 = y;
/* 176 */       double d7 = z;
/* 177 */       AxisAlignedBB axisalignedbb1 = func_174813_aQ();
/* 178 */       func_174826_a(axisalignedbb);
/* 179 */       y = this.field_70138_W;
/* 180 */       List<AxisAlignedBB> list = getCollisionBoxes((Entity)this, func_174813_aQ().func_72321_a(d2, y, d4));
/* 181 */       AxisAlignedBB axisalignedbb2 = func_174813_aQ();
/* 182 */       AxisAlignedBB axisalignedbb3 = axisalignedbb2.func_72321_a(d2, 0.0D, d4);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 188 */       double d8 = y;
/*     */       
/* 190 */       for (int j1 = 0; j1 < list.size(); j1++)
/*     */       {
/* 192 */         d8 = ((AxisAlignedBB)list.get(j1)).func_72323_b(axisalignedbb3, d8);
/*     */       }
/*     */       
/* 195 */       axisalignedbb2 = axisalignedbb2.func_72317_d(0.0D, d8, 0.0D);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 202 */       double d18 = d2;
/*     */       
/* 204 */       for (int l1 = 0; l1 < list.size(); l1++)
/*     */       {
/* 206 */         d18 = ((AxisAlignedBB)list.get(l1)).func_72316_a(axisalignedbb2, d18);
/*     */       }
/*     */       
/* 209 */       axisalignedbb2 = axisalignedbb2.func_72317_d(d18, 0.0D, 0.0D);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 216 */       double d19 = d4;
/*     */       
/* 218 */       for (int j2 = 0; j2 < list.size(); j2++)
/*     */       {
/* 220 */         d19 = ((AxisAlignedBB)list.get(j2)).func_72322_c(axisalignedbb2, d19);
/*     */       }
/*     */       
/* 223 */       axisalignedbb2 = axisalignedbb2.func_72317_d(0.0D, 0.0D, d19);
/*     */       
/* 225 */       AxisAlignedBB axisalignedbb4 = func_174813_aQ();
/* 226 */       double d20 = y;
/*     */       
/* 228 */       for (int l2 = 0; l2 < list.size(); l2++)
/*     */       {
/* 230 */         d20 = ((AxisAlignedBB)list.get(l2)).func_72323_b(axisalignedbb4, d20);
/*     */       }
/* 232 */       axisalignedbb4 = axisalignedbb4.func_72317_d(0.0D, d20, 0.0D);
/*     */       
/* 234 */       double d21 = d2;
/*     */       
/* 236 */       for (int j3 = 0; j3 < list.size(); j3++)
/*     */       {
/* 238 */         d21 = ((AxisAlignedBB)list.get(j3)).func_72316_a(axisalignedbb4, d21);
/*     */       }
/* 240 */       axisalignedbb4 = axisalignedbb4.func_72317_d(d21, 0.0D, 0.0D);
/*     */       
/* 242 */       double d22 = d4;
/*     */       
/* 244 */       for (int l3 = 0; l3 < list.size(); l3++)
/*     */       {
/* 246 */         d22 = ((AxisAlignedBB)list.get(l3)).func_72322_c(axisalignedbb4, d22);
/*     */       }
/* 248 */       axisalignedbb4 = axisalignedbb4.func_72317_d(0.0D, 0.0D, d22);
/*     */       
/* 250 */       double d23 = d18 * d18 + d19 * d19;
/* 251 */       double d9 = d21 * d21 + d22 * d22;
/*     */       
/* 253 */       if (d23 > d9) {
/*     */         
/* 255 */         x = d18;
/* 256 */         z = d19;
/* 257 */         y = -d8;
/* 258 */         func_174826_a(axisalignedbb2);
/*     */       }
/*     */       else {
/*     */         
/* 262 */         x = d21;
/* 263 */         z = d22;
/* 264 */         y = -d20;
/* 265 */         func_174826_a(axisalignedbb4);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 275 */       for (int j4 = 0; j4 < list.size(); j4++)
/*     */       {
/* 277 */         y = ((AxisAlignedBB)list.get(j4)).func_72323_b(func_174813_aQ(), y);
/*     */       }
/*     */       
/* 280 */       func_174826_a(func_174813_aQ().func_72317_d(0.0D, y, 0.0D));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 289 */       if (d14 * d14 + d7 * d7 >= x * x + z * z) {
/*     */         
/* 291 */         x = d14;
/* 292 */         y = d6;
/* 293 */         z = d7;
/* 294 */         func_174826_a(axisalignedbb1);
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
/* 307 */     this.field_70170_p.field_72984_F.func_76319_b();
/* 308 */     this.field_70170_p.field_72984_F.func_76320_a("rest");
/* 309 */     func_174829_m();
/* 310 */     this.field_70123_F = (d2 != x || d4 != z);
/* 311 */     this.field_70124_G = (d3 != y);
/* 312 */     this.field_70122_E = (this.field_70124_G && d3 < 0.0D);
/* 313 */     this.field_70132_H = (this.field_70123_F || this.field_70124_G);
/*     */     
/* 315 */     int j6 = MathHelper.func_76128_c(this.field_70165_t);
/* 316 */     int i1 = MathHelper.func_76128_c(this.field_70163_u - 0.20000000298023224D);
/* 317 */     int k6 = MathHelper.func_76128_c(this.field_70161_v);
/* 318 */     BlockPos blockpos = new BlockPos(j6, i1, k6);
/* 319 */     IBlockState iblockstate = this.field_70170_p.func_180495_p(blockpos);
/*     */     
/* 321 */     if (iblockstate.func_185904_a() == Material.field_151579_a) {
/*     */       
/* 323 */       BlockPos blockpos1 = blockpos.func_177977_b();
/* 324 */       IBlockState iblockstate1 = this.field_70170_p.func_180495_p(blockpos1);
/* 325 */       Block block1 = iblockstate1.func_177230_c();
/*     */       
/* 327 */       if (block1 instanceof net.minecraft.block.BlockFence || block1 instanceof net.minecraft.block.BlockWall || block1 instanceof net.minecraft.block.BlockFenceGate) {
/*     */         
/* 329 */         iblockstate = iblockstate1;
/* 330 */         blockpos = blockpos1;
/*     */       } 
/*     */     } 
/*     */     
/* 334 */     func_184231_a(y, this.field_70122_E, iblockstate, blockpos);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 344 */     if (d2 != x)
/*     */     {
/* 346 */       this.field_70159_w = 0.0D;
/*     */     }
/*     */     
/* 349 */     if (d4 != z)
/*     */     {
/* 351 */       this.field_70179_y = 0.0D;
/*     */     }
/*     */     
/* 354 */     Block block = iblockstate.func_177230_c();
/*     */     
/* 356 */     if (d3 != y)
/*     */     {
/* 358 */       block.func_176216_a(this.field_70170_p, (Entity)this);
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
/*     */     try {
/* 373 */       func_145775_I();
/*     */     }
/* 375 */     catch (Throwable throwable) {
/*     */       
/* 377 */       CrashReport crashreport = CrashReport.func_85055_a(throwable, "Checking entity block collision");
/* 378 */       CrashReportCategory crashreportcategory = crashreport.func_85058_a("Entity being checked for collision");
/* 379 */       func_85029_a(crashreportcategory);
/* 380 */       throw new ReportedException(crashreport);
/*     */     } 
/*     */ 
/*     */     
/* 384 */     this.field_70170_p.field_72984_F.func_76319_b();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<AxisAlignedBB> getCollisionBoxes(Entity entityIn, AxisAlignedBB aabb) {
/* 390 */     ArrayList<AxisAlignedBB> collidingBoundingBoxes = new ArrayList<>();
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
/* 418 */     getCollisionBoxes(entityIn, aabb, collidingBoundingBoxes);
/*     */     
/* 420 */     double d0 = 0.25D;
/*     */     
/* 422 */     List<Entity> list = entityIn.field_70170_p.func_72839_b(entityIn, aabb.func_72314_b(d0, d0, d0));
/*     */     
/* 424 */     for (int j2 = 0; j2 < list.size(); j2++) {
/*     */       
/* 426 */       Entity entity = list.get(j2);
/*     */       
/* 428 */       if (!W_Lib.isEntityLivingBase(entity) && !(entity instanceof mcheli.aircraft.MCH_EntitySeat) && !(entity instanceof mcheli.aircraft.MCH_EntityHitBox) && entity != this.parents) {
/*     */ 
/*     */         
/* 431 */         AxisAlignedBB axisalignedbb1 = entity.func_70046_E();
/*     */ 
/*     */         
/* 434 */         if (axisalignedbb1 != null && axisalignedbb1.func_72326_a(aabb))
/*     */         {
/* 436 */           collidingBoundingBoxes.add(axisalignedbb1);
/*     */         }
/*     */ 
/*     */         
/* 440 */         axisalignedbb1 = entityIn.func_70114_g(entity);
/*     */ 
/*     */         
/* 443 */         if (axisalignedbb1 != null && axisalignedbb1.func_72326_a(aabb))
/*     */         {
/* 445 */           collidingBoundingBoxes.add(axisalignedbb1);
/*     */         }
/*     */       } 
/*     */     } 
/* 449 */     return collidingBoundingBoxes;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean getCollisionBoxes(Entity entityIn, AxisAlignedBB aabb, List<AxisAlignedBB> outList) {
/* 454 */     int i = MathHelper.func_76128_c(aabb.field_72340_a) - 1;
/* 455 */     int j = MathHelper.func_76143_f(aabb.field_72336_d) + 1;
/* 456 */     int k = MathHelper.func_76128_c(aabb.field_72338_b) - 1;
/* 457 */     int l = MathHelper.func_76143_f(aabb.field_72337_e) + 1;
/* 458 */     int i1 = MathHelper.func_76128_c(aabb.field_72339_c) - 1;
/* 459 */     int j1 = MathHelper.func_76143_f(aabb.field_72334_f) + 1;
/* 460 */     WorldBorder worldborder = entityIn.field_70170_p.func_175723_af();
/* 461 */     boolean flag = (entityIn != null && entityIn.func_174832_aS());
/* 462 */     boolean flag1 = (entityIn != null && entityIn.field_70170_p.func_191503_g(entityIn));
/* 463 */     IBlockState iblockstate = Blocks.field_150348_b.func_176223_P();
/* 464 */     BlockPos.PooledMutableBlockPos blockpos = BlockPos.PooledMutableBlockPos.func_185346_s();
/*     */ 
/*     */     
/*     */     try {
/* 468 */       for (int k1 = i; k1 < j; k1++) {
/*     */         
/* 470 */         for (int l1 = i1; l1 < j1; l1++) {
/*     */           
/* 472 */           boolean flag2 = (k1 == i || k1 == j - 1);
/* 473 */           boolean flag3 = (l1 == i1 || l1 == j1 - 1);
/*     */           
/* 475 */           if ((!flag2 || !flag3) && entityIn.field_70170_p.func_175667_e((BlockPos)blockpos.func_181079_c(k1, 64, l1)))
/*     */           {
/* 477 */             for (int i2 = k; i2 < l; i2++)
/*     */             {
/* 479 */               if ((!flag2 && !flag3) || i2 != l - 1) {
/*     */                 IBlockState iblockstate1;
/* 481 */                 if (entityIn != null && flag == flag1)
/*     */                 {
/* 483 */                   entityIn.func_174821_h(!flag1);
/*     */                 }
/*     */                 
/* 486 */                 blockpos.func_181079_c(k1, i2, l1);
/*     */ 
/*     */                 
/* 489 */                 if (!worldborder.func_177746_a((BlockPos)blockpos) && flag1) {
/*     */                   
/* 491 */                   iblockstate1 = iblockstate;
/*     */                 }
/*     */                 else {
/*     */                   
/* 495 */                   iblockstate1 = entityIn.field_70170_p.func_180495_p((BlockPos)blockpos);
/*     */                 } 
/*     */                 
/* 498 */                 iblockstate1.func_185908_a(entityIn.field_70170_p, (BlockPos)blockpos, aabb, outList, entityIn, false);
/*     */               }
/*     */             
/*     */             }
/*     */           
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } finally {
/*     */       
/* 508 */       blockpos.func_185344_t();
/*     */     } 
/*     */     
/* 511 */     return !outList.isEmpty();
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\tank\MCH_EntityWheel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */