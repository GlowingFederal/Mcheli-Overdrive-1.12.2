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
    setSize(1.0F, 1.0F);
    this.stepHeight = 1.5F;
    this.isImmuneToFire = true;
    this.isPlus = false;
  }
  
  public void setWheelPos(Vec3d pos, Vec3d weightedCenter) {
    this.pos = pos;
    this.isPlus = (pos.z >= weightedCenter.z);
  }
  
  public void travelToDimension(int dimensionId) {}
  
  public MCH_EntityAircraft getParents() {
    return this.parents;
  }
  
  public void setParents(MCH_EntityAircraft parents) {
    this.parents = parents;
  }
  
  protected void readEntityFromNBT(NBTTagCompound compound) {
    setDead();
  }
  
  protected void writeEntityToNBT(NBTTagCompound compound) {}
  
  public void moveEntity(MoverType type, double x, double y, double z) {
    this.world.theProfiler.startSection("move");
    double d2 = x;
    double d3 = y;
    double d4 = z;
    List<AxisAlignedBB> list1 = getCollisionBoxes((Entity)this, getEntityBoundingBox().addCoord(x, y, z));
    AxisAlignedBB axisalignedbb = getEntityBoundingBox();
    if (y != 0.0D) {
      for (int k = 0; k < list1.size(); k++)
        y = ((AxisAlignedBB)list1.get(k)).calculateYOffset(getEntityBoundingBox(), y); 
      setEntityBoundingBox(getEntityBoundingBox().offset(0.0D, y, 0.0D));
    } 
    boolean flag = (this.onGround || (d3 != y && d3 < 0.0D));
    if (x != 0.0D) {
      for (int j5 = 0; j5 < list1.size(); j5++)
        x = ((AxisAlignedBB)list1.get(j5)).calculateXOffset(getEntityBoundingBox(), x); 
      if (x != 0.0D)
        setEntityBoundingBox(getEntityBoundingBox().offset(x, 0.0D, 0.0D)); 
    } 
    if (z != 0.0D) {
      for (int k5 = 0; k5 < list1.size(); k5++)
        z = ((AxisAlignedBB)list1.get(k5)).calculateZOffset(getEntityBoundingBox(), z); 
      if (z != 0.0D)
        setEntityBoundingBox(getEntityBoundingBox().offset(0.0D, 0.0D, z)); 
    } 
    if (this.stepHeight > 0.0F && flag && (d2 != x || d4 != z)) {
      double d14 = x;
      double d6 = y;
      double d7 = z;
      AxisAlignedBB axisalignedbb1 = getEntityBoundingBox();
      setEntityBoundingBox(axisalignedbb);
      y = this.stepHeight;
      List<AxisAlignedBB> list = getCollisionBoxes((Entity)this, getEntityBoundingBox().addCoord(d2, y, d4));
      AxisAlignedBB axisalignedbb2 = getEntityBoundingBox();
      AxisAlignedBB axisalignedbb3 = axisalignedbb2.addCoord(d2, 0.0D, d4);
      double d8 = y;
      for (int j1 = 0; j1 < list.size(); j1++)
        d8 = ((AxisAlignedBB)list.get(j1)).calculateYOffset(axisalignedbb3, d8); 
      axisalignedbb2 = axisalignedbb2.offset(0.0D, d8, 0.0D);
      double d18 = d2;
      for (int l1 = 0; l1 < list.size(); l1++)
        d18 = ((AxisAlignedBB)list.get(l1)).calculateXOffset(axisalignedbb2, d18); 
      axisalignedbb2 = axisalignedbb2.offset(d18, 0.0D, 0.0D);
      double d19 = d4;
      for (int j2 = 0; j2 < list.size(); j2++)
        d19 = ((AxisAlignedBB)list.get(j2)).calculateZOffset(axisalignedbb2, d19); 
      axisalignedbb2 = axisalignedbb2.offset(0.0D, 0.0D, d19);
      AxisAlignedBB axisalignedbb4 = getEntityBoundingBox();
      double d20 = y;
      for (int l2 = 0; l2 < list.size(); l2++)
        d20 = ((AxisAlignedBB)list.get(l2)).calculateYOffset(axisalignedbb4, d20); 
      axisalignedbb4 = axisalignedbb4.offset(0.0D, d20, 0.0D);
      double d21 = d2;
      for (int j3 = 0; j3 < list.size(); j3++)
        d21 = ((AxisAlignedBB)list.get(j3)).calculateXOffset(axisalignedbb4, d21); 
      axisalignedbb4 = axisalignedbb4.offset(d21, 0.0D, 0.0D);
      double d22 = d4;
      for (int l3 = 0; l3 < list.size(); l3++)
        d22 = ((AxisAlignedBB)list.get(l3)).calculateZOffset(axisalignedbb4, d22); 
      axisalignedbb4 = axisalignedbb4.offset(0.0D, 0.0D, d22);
      double d23 = d18 * d18 + d19 * d19;
      double d9 = d21 * d21 + d22 * d22;
      if (d23 > d9) {
        x = d18;
        z = d19;
        y = -d8;
        setEntityBoundingBox(axisalignedbb2);
      } else {
        x = d21;
        z = d22;
        y = -d20;
        setEntityBoundingBox(axisalignedbb4);
      } 
      for (int j4 = 0; j4 < list.size(); j4++)
        y = ((AxisAlignedBB)list.get(j4)).calculateYOffset(getEntityBoundingBox(), y); 
      setEntityBoundingBox(getEntityBoundingBox().offset(0.0D, y, 0.0D));
      if (d14 * d14 + d7 * d7 >= x * x + z * z) {
        x = d14;
        y = d6;
        z = d7;
        setEntityBoundingBox(axisalignedbb1);
      } 
    } 
    this.world.theProfiler.endSection();
    this.world.theProfiler.startSection("rest");
    resetPositionToBB();
    this.isCollidedHorizontally = (d2 != x || d4 != z);
    this.isCollidedVertically = (d3 != y);
    this.onGround = (this.isCollidedVertically && d3 < 0.0D);
    this.isCollided = (this.isCollidedHorizontally || this.isCollidedVertically);
    int j6 = MathHelper.floor(this.posX);
    int i1 = MathHelper.floor(this.posY - 0.20000000298023224D);
    int k6 = MathHelper.floor(this.posZ);
    BlockPos blockpos = new BlockPos(j6, i1, k6);
    IBlockState iblockstate = this.world.getBlockState(blockpos);
    if (iblockstate.getMaterial() == Material.AIR) {
      BlockPos blockpos1 = blockpos.down();
      IBlockState iblockstate1 = this.world.getBlockState(blockpos1);
      Block block1 = iblockstate1.getBlock();
      if (block1 instanceof net.minecraft.block.BlockFence || block1 instanceof net.minecraft.block.BlockWall || block1 instanceof net.minecraft.block.BlockFenceGate) {
        iblockstate = iblockstate1;
        blockpos = blockpos1;
      } 
    } 
    updateFallState(y, this.onGround, iblockstate, blockpos);
    if (d2 != x)
      this.motionX = 0.0D; 
    if (d4 != z)
      this.motionZ = 0.0D; 
    Block block = iblockstate.getBlock();
    if (d3 != y)
      block.onLanded(this.world, (Entity)this); 
    try {
      doBlockCollisions();
    } catch (Throwable throwable) {
      CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Checking entity block collision");
      CrashReportCategory crashreportcategory = crashreport.makeCategory("Entity being checked for collision");
      addEntityCrashInfo(crashreportcategory);
      throw new ReportedException(crashreport);
    } 
    this.world.theProfiler.endSection();
  }
  
  public List<AxisAlignedBB> getCollisionBoxes(Entity entityIn, AxisAlignedBB aabb) {
    ArrayList<AxisAlignedBB> collidingBoundingBoxes = new ArrayList<>();
    getCollisionBoxes(entityIn, aabb, collidingBoundingBoxes);
    double d0 = 0.25D;
    List<Entity> list = entityIn.world.getEntitiesWithinAABBExcludingEntity(entityIn, aabb.expand(d0, d0, d0));
    for (int j2 = 0; j2 < list.size(); j2++) {
      Entity entity = list.get(j2);
      if (!W_Lib.isEntityLivingBase(entity) && !(entity instanceof mcheli.aircraft.MCH_EntitySeat) && !(entity instanceof mcheli.aircraft.MCH_EntityHitBox) && entity != this.parents) {
        AxisAlignedBB axisalignedbb1 = entity.getCollisionBoundingBox();
        if (axisalignedbb1 != null && axisalignedbb1.intersectsWith(aabb))
          collidingBoundingBoxes.add(axisalignedbb1); 
        axisalignedbb1 = entityIn.getCollisionBox(entity);
        if (axisalignedbb1 != null && axisalignedbb1.intersectsWith(aabb))
          collidingBoundingBoxes.add(axisalignedbb1); 
      } 
    } 
    return collidingBoundingBoxes;
  }
  
  private boolean getCollisionBoxes(Entity entityIn, AxisAlignedBB aabb, List<AxisAlignedBB> outList) {
    int i = MathHelper.floor(aabb.minX) - 1;
    int j = MathHelper.ceil(aabb.maxX) + 1;
    int k = MathHelper.floor(aabb.minY) - 1;
    int l = MathHelper.ceil(aabb.maxY) + 1;
    int i1 = MathHelper.floor(aabb.minZ) - 1;
    int j1 = MathHelper.ceil(aabb.maxZ) + 1;
    WorldBorder worldborder = entityIn.world.getWorldBorder();
    boolean flag = (entityIn != null && entityIn.isOutsideBorder());
    boolean flag1 = (entityIn != null && entityIn.world.func_191503_g(entityIn));
    IBlockState iblockstate = Blocks.STONE.getDefaultState();
    BlockPos.PooledMutableBlockPos blockpos = BlockPos.PooledMutableBlockPos.retain();
    try {
      for (int k1 = i; k1 < j; k1++) {
        for (int l1 = i1; l1 < j1; l1++) {
          boolean flag2 = (k1 == i || k1 == j - 1);
          boolean flag3 = (l1 == i1 || l1 == j1 - 1);
          if ((!flag2 || !flag3) && entityIn.world.isBlockLoaded((BlockPos)blockpos.setPos(k1, 64, l1)))
            for (int i2 = k; i2 < l; i2++) {
              if ((!flag2 && !flag3) || i2 != l - 1) {
                IBlockState iblockstate1;
                if (entityIn != null && flag == flag1)
                  entityIn.setOutsideBorder(!flag1); 
                blockpos.setPos(k1, i2, l1);
                if (!worldborder.contains((BlockPos)blockpos) && flag1) {
                  iblockstate1 = iblockstate;
                } else {
                  iblockstate1 = entityIn.world.getBlockState((BlockPos)blockpos);
                } 
                iblockstate1.addCollisionBoxToList(entityIn.world, (BlockPos)blockpos, aabb, outList, entityIn, false);
              } 
            }  
        } 
      } 
    } finally {
      blockpos.release();
    } 
    return !outList.isEmpty();
  }
}
