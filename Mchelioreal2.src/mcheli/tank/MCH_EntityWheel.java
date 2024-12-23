package mcheli.tank;

import java.util.ArrayList;
import java.util.List;
import mcheli.aircraft.MCH_EntityAircraft;
import mcheli.wrapper.W_Entity;
import mcheli.wrapper.W_Lib;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ReportedException;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.border.WorldBorder;

public class MCH_EntityWheel extends W_Entity {
  private MCH_EntityAircraft parents;
  
  public Vec3d pos;
  
  boolean isPlus;
  
  public MCH_EntityWheel(World w) {
    super(w);
    func_70105_a(1.0F, 1.0F);
    this.field_70138_W = 1.5F;
    this.field_70178_ae = true;
    this.isPlus = false;
  }
  
  public void setWheelPos(Vec3d pos, Vec3d weightedCenter) {
    this.pos = pos;
    this.isPlus = (pos.field_72449_c >= weightedCenter.field_72449_c);
  }
  
  public void travelToDimension(int dimensionId) {}
  
  public MCH_EntityAircraft getParents() {
    return this.parents;
  }
  
  public void setParents(MCH_EntityAircraft parents) {
    this.parents = parents;
  }
  
  protected void func_70037_a(NBTTagCompound compound) {
    func_70106_y();
  }
  
  protected void func_70014_b(NBTTagCompound compound) {}
  
  public void func_70091_d(MoverType type, double x, double y, double z) {
    this.field_70170_p.field_72984_F.func_76320_a("move");
    double d2 = x;
    double d3 = y;
    double d4 = z;
    List<AxisAlignedBB> list1 = getCollisionBoxes((Entity)this, func_174813_aQ().func_72321_a(x, y, z));
    AxisAlignedBB axisalignedbb = func_174813_aQ();
    if (y != 0.0D) {
      for (int k = 0; k < list1.size(); k++)
        y = ((AxisAlignedBB)list1.get(k)).func_72323_b(func_174813_aQ(), y); 
      func_174826_a(func_174813_aQ().func_72317_d(0.0D, y, 0.0D));
    } 
    boolean flag = (this.field_70122_E || (d3 != y && d3 < 0.0D));
    if (x != 0.0D) {
      for (int j5 = 0; j5 < list1.size(); j5++)
        x = ((AxisAlignedBB)list1.get(j5)).func_72316_a(func_174813_aQ(), x); 
      if (x != 0.0D)
        func_174826_a(func_174813_aQ().func_72317_d(x, 0.0D, 0.0D)); 
    } 
    if (z != 0.0D) {
      for (int k5 = 0; k5 < list1.size(); k5++)
        z = ((AxisAlignedBB)list1.get(k5)).func_72322_c(func_174813_aQ(), z); 
      if (z != 0.0D)
        func_174826_a(func_174813_aQ().func_72317_d(0.0D, 0.0D, z)); 
    } 
    if (this.field_70138_W > 0.0F && flag && (d2 != x || d4 != z)) {
      double d14 = x;
      double d6 = y;
      double d7 = z;
      AxisAlignedBB axisalignedbb1 = func_174813_aQ();
      func_174826_a(axisalignedbb);
      y = this.field_70138_W;
      List<AxisAlignedBB> list = getCollisionBoxes((Entity)this, func_174813_aQ().func_72321_a(d2, y, d4));
      AxisAlignedBB axisalignedbb2 = func_174813_aQ();
      AxisAlignedBB axisalignedbb3 = axisalignedbb2.func_72321_a(d2, 0.0D, d4);
      double d8 = y;
      for (int j1 = 0; j1 < list.size(); j1++)
        d8 = ((AxisAlignedBB)list.get(j1)).func_72323_b(axisalignedbb3, d8); 
      axisalignedbb2 = axisalignedbb2.func_72317_d(0.0D, d8, 0.0D);
      double d18 = d2;
      for (int l1 = 0; l1 < list.size(); l1++)
        d18 = ((AxisAlignedBB)list.get(l1)).func_72316_a(axisalignedbb2, d18); 
      axisalignedbb2 = axisalignedbb2.func_72317_d(d18, 0.0D, 0.0D);
      double d19 = d4;
      for (int j2 = 0; j2 < list.size(); j2++)
        d19 = ((AxisAlignedBB)list.get(j2)).func_72322_c(axisalignedbb2, d19); 
      axisalignedbb2 = axisalignedbb2.func_72317_d(0.0D, 0.0D, d19);
      AxisAlignedBB axisalignedbb4 = func_174813_aQ();
      double d20 = y;
      for (int l2 = 0; l2 < list.size(); l2++)
        d20 = ((AxisAlignedBB)list.get(l2)).func_72323_b(axisalignedbb4, d20); 
      axisalignedbb4 = axisalignedbb4.func_72317_d(0.0D, d20, 0.0D);
      double d21 = d2;
      for (int j3 = 0; j3 < list.size(); j3++)
        d21 = ((AxisAlignedBB)list.get(j3)).func_72316_a(axisalignedbb4, d21); 
      axisalignedbb4 = axisalignedbb4.func_72317_d(d21, 0.0D, 0.0D);
      double d22 = d4;
      for (int l3 = 0; l3 < list.size(); l3++)
        d22 = ((AxisAlignedBB)list.get(l3)).func_72322_c(axisalignedbb4, d22); 
      axisalignedbb4 = axisalignedbb4.func_72317_d(0.0D, 0.0D, d22);
      double d23 = d18 * d18 + d19 * d19;
      double d9 = d21 * d21 + d22 * d22;
      if (d23 > d9) {
        x = d18;
        z = d19;
        y = -d8;
        func_174826_a(axisalignedbb2);
      } else {
        x = d21;
        z = d22;
        y = -d20;
        func_174826_a(axisalignedbb4);
      } 
      for (int j4 = 0; j4 < list.size(); j4++)
        y = ((AxisAlignedBB)list.get(j4)).func_72323_b(func_174813_aQ(), y); 
      func_174826_a(func_174813_aQ().func_72317_d(0.0D, y, 0.0D));
      if (d14 * d14 + d7 * d7 >= x * x + z * z) {
        x = d14;
        y = d6;
        z = d7;
        func_174826_a(axisalignedbb1);
      } 
    } 
    this.field_70170_p.field_72984_F.func_76319_b();
    this.field_70170_p.field_72984_F.func_76320_a("rest");
    func_174829_m();
    this.field_70123_F = (d2 != x || d4 != z);
    this.field_70124_G = (d3 != y);
    this.field_70122_E = (this.field_70124_G && d3 < 0.0D);
    this.field_70132_H = (this.field_70123_F || this.field_70124_G);
    int j6 = MathHelper.func_76128_c(this.field_70165_t);
    int i1 = MathHelper.func_76128_c(this.field_70163_u - 0.20000000298023224D);
    int k6 = MathHelper.func_76128_c(this.field_70161_v);
    BlockPos blockpos = new BlockPos(j6, i1, k6);
    IBlockState iblockstate = this.field_70170_p.func_180495_p(blockpos);
    if (iblockstate.func_185904_a() == Material.field_151579_a) {
      BlockPos blockpos1 = blockpos.func_177977_b();
      IBlockState iblockstate1 = this.field_70170_p.func_180495_p(blockpos1);
      Block block1 = iblockstate1.func_177230_c();
      if (block1 instanceof net.minecraft.block.BlockFence || block1 instanceof net.minecraft.block.BlockWall || block1 instanceof net.minecraft.block.BlockFenceGate) {
        iblockstate = iblockstate1;
        blockpos = blockpos1;
      } 
    } 
    func_184231_a(y, this.field_70122_E, iblockstate, blockpos);
    if (d2 != x)
      this.field_70159_w = 0.0D; 
    if (d4 != z)
      this.field_70179_y = 0.0D; 
    Block block = iblockstate.func_177230_c();
    if (d3 != y)
      block.func_176216_a(this.field_70170_p, (Entity)this); 
    try {
      func_145775_I();
    } catch (Throwable throwable) {
      CrashReport crashreport = CrashReport.func_85055_a(throwable, "Checking entity block collision");
      CrashReportCategory crashreportcategory = crashreport.func_85058_a("Entity being checked for collision");
      func_85029_a(crashreportcategory);
      throw new ReportedException(crashreport);
    } 
    this.field_70170_p.field_72984_F.func_76319_b();
  }
  
  public List<AxisAlignedBB> getCollisionBoxes(Entity entityIn, AxisAlignedBB aabb) {
    ArrayList<AxisAlignedBB> collidingBoundingBoxes = new ArrayList<>();
    getCollisionBoxes(entityIn, aabb, collidingBoundingBoxes);
    double d0 = 0.25D;
    List<Entity> list = entityIn.field_70170_p.func_72839_b(entityIn, aabb.func_72314_b(d0, d0, d0));
    for (int j2 = 0; j2 < list.size(); j2++) {
      Entity entity = list.get(j2);
      if (!W_Lib.isEntityLivingBase(entity) && !(entity instanceof mcheli.aircraft.MCH_EntitySeat) && !(entity instanceof mcheli.aircraft.MCH_EntityHitBox) && entity != this.parents) {
        AxisAlignedBB axisalignedbb1 = entity.func_70046_E();
        if (axisalignedbb1 != null && axisalignedbb1.func_72326_a(aabb))
          collidingBoundingBoxes.add(axisalignedbb1); 
        axisalignedbb1 = entityIn.func_70114_g(entity);
        if (axisalignedbb1 != null && axisalignedbb1.func_72326_a(aabb))
          collidingBoundingBoxes.add(axisalignedbb1); 
      } 
    } 
    return collidingBoundingBoxes;
  }
  
  private boolean getCollisionBoxes(Entity entityIn, AxisAlignedBB aabb, List<AxisAlignedBB> outList) {
    int i = MathHelper.func_76128_c(aabb.field_72340_a) - 1;
    int j = MathHelper.func_76143_f(aabb.field_72336_d) + 1;
    int k = MathHelper.func_76128_c(aabb.field_72338_b) - 1;
    int l = MathHelper.func_76143_f(aabb.field_72337_e) + 1;
    int i1 = MathHelper.func_76128_c(aabb.field_72339_c) - 1;
    int j1 = MathHelper.func_76143_f(aabb.field_72334_f) + 1;
    WorldBorder worldborder = entityIn.field_70170_p.func_175723_af();
    boolean flag = (entityIn != null && entityIn.func_174832_aS());
    boolean flag1 = (entityIn != null && entityIn.field_70170_p.func_191503_g(entityIn));
    IBlockState iblockstate = Blocks.field_150348_b.func_176223_P();
    BlockPos.PooledMutableBlockPos blockpos = BlockPos.PooledMutableBlockPos.func_185346_s();
    try {
      for (int k1 = i; k1 < j; k1++) {
        for (int l1 = i1; l1 < j1; l1++) {
          boolean flag2 = (k1 == i || k1 == j - 1);
          boolean flag3 = (l1 == i1 || l1 == j1 - 1);
          if ((!flag2 || !flag3) && entityIn.field_70170_p.func_175667_e((BlockPos)blockpos.func_181079_c(k1, 64, l1)))
            for (int i2 = k; i2 < l; i2++) {
              if ((!flag2 && !flag3) || i2 != l - 1) {
                IBlockState iblockstate1;
                if (entityIn != null && flag == flag1)
                  entityIn.func_174821_h(!flag1); 
                blockpos.func_181079_c(k1, i2, l1);
                if (!worldborder.func_177746_a((BlockPos)blockpos) && flag1) {
                  iblockstate1 = iblockstate;
                } else {
                  iblockstate1 = entityIn.field_70170_p.func_180495_p((BlockPos)blockpos);
                } 
                iblockstate1.func_185908_a(entityIn.field_70170_p, (BlockPos)blockpos, aabb, outList, entityIn, false);
              } 
            }  
        } 
      } 
    } finally {
      blockpos.func_185344_t();
    } 
    return !outList.isEmpty();
  }
}
