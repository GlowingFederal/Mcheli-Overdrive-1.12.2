package mcheli.tank;

import java.util.ArrayList;
import java.util.List;
import mcheli.aircraft.MCH_EntityAircraft;
import mcheli.aircraft.MCH_EntityHitBox;
import mcheli.aircraft.MCH_EntitySeat;
import mcheli.wrapper.W_Entity;
import mcheli.wrapper.W_Lib;
import mcheli.wrapper.W_WorldFunc;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ReportedException;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

import net.minecraft.world.World;

public class MCH_EntityWheel extends W_Entity {

  private MCH_EntityAircraft parents;
  public Vec3d pos;
  boolean isPlus;


  public MCH_EntityWheel(World w) {
    super(w);
    this.setSize(1.0F, 1.0F);
    super.stepHeight = 1.5F;
    super.isImmuneToFire = true;
    this.isPlus = false;
  }

  public void setWheelPos(Vec3d pos, Vec3d weightedCenter) {
    this.pos = pos;
    this.isPlus = pos.z >= weightedCenter.z;
  }

  public void travelToDimension(int p_71027_1_) {}

  public MCH_EntityAircraft getParents() {
    return this.parents;
  }

  public void setParents(MCH_EntityAircraft parents) {
    this.parents = parents;
  }

  protected void readEntityFromNBT(NBTTagCompound p_70037_1_) {
    this.setDead();
  }

  protected void writeEntityToNBT(NBTTagCompound p_70014_1_) {}

  public void move(double parX, double parY, double parZ) {
    super.world.profiler.startSection("move");
    this.setSize(this.width, this.height * 0.4F);
    double nowPosX = super.posX;
    double nowPosY = super.posY;
    double nowPosZ = super.posZ;
    double mx = parX;
    double my = parY;
    double mz = parZ;
    AxisAlignedBB axisalignedbb = super.getEntityBoundingBox().grow(0);
    List list = this.getCollidingBoundingBoxes(this, super.getEntityBoundingBox().expand(parX, parY, parZ));

    for(int flag1 = 0; flag1 < list.size(); ++flag1) {
      parY = ((AxisAlignedBB)list.get(flag1)).calculateYOffset(getEntityBoundingBox(), parY);
    }

    AxisAlignedBB boundingBox = this.getEntityBoundingBox(); // Get the current bounding box
    boundingBox = boundingBox.offset(parX, 0.0D, 0.0D); // Offset the bounding box
    this.setEntityBoundingBox(boundingBox); // Update the entity's bounding box
    boolean var32 = super.onGround || my != parY && my < 0.0D;

    int bkParY;
    for(bkParY = 0; bkParY < list.size(); ++bkParY) {
      parX = ((AxisAlignedBB)list.get(bkParY)).calculateXOffset(super.getEntityBoundingBox(), parX);
    }

    super.getEntityBoundingBox().offset(parX, 0.0D, 0.0D);

    for(bkParY = 0; bkParY < list.size(); ++bkParY) {
      parZ = ((AxisAlignedBB)list.get(bkParY)).calculateZOffset(super.getEntityBoundingBox(), parZ);
    }

    super.getEntityBoundingBox().offset(0.0D, 0.0D, parZ);
    if(super.stepHeight > 0.0F && var32 && this.getEntityBoundingBox().minY - this.prevPosY < 0.05F && (mx != parX || mz != parZ)) {
      double bkParX = parX;
      double var33 = parY;
      double bkParZ = parZ;
      parX = mx;
      parY = (double)super.stepHeight;
      parZ = mz;
      AxisAlignedBB throwable = super.getEntityBoundingBox().grow(0);
      this.setEntityBoundingBox(axisalignedbb);
      list = this.getCollidingBoundingBoxes(this, super.getEntityBoundingBox().offset(mx, parY, mz)); //possible problem here

      int crashreport;
      for(crashreport = 0; crashreport < list.size(); ++crashreport) {
        parY = ((AxisAlignedBB)list.get(crashreport)).calculateYOffset(super.getEntityBoundingBox(), parY);
      }

      super.getEntityBoundingBox().offset(0.0D, parY, 0.0D);

      for(crashreport = 0; crashreport < list.size(); ++crashreport) {
        parX = ((AxisAlignedBB)list.get(crashreport)).calculateXOffset(super.getEntityBoundingBox(), parX);
      }

      super.getEntityBoundingBox().offset(parX, 0.0D, 0.0D);

      for(crashreport = 0; crashreport < list.size(); ++crashreport) {
        parZ = ((AxisAlignedBB)list.get(crashreport)).calculateZOffset(super.getEntityBoundingBox(), parZ);
      }

      super.getEntityBoundingBox().offset(0.0D, 0.0D, parZ);
      parY = (double)(-super.stepHeight);

      for(crashreport = 0; crashreport < list.size(); ++crashreport) {
        parY = ((AxisAlignedBB)list.get(crashreport)).calculateYOffset(super.getEntityBoundingBox(), parY);
      }

      super.getEntityBoundingBox().offset(0.0D, parY, 0.0D);
      if(bkParX * bkParX + bkParZ * bkParZ >= parX * parX + parZ * parZ) {
        parX = bkParX;
        parY = var33;
        parZ = bkParZ;
        super.setEntityBoundingBox(throwable);
      }
    }

    super.world.profiler.endSection();
    super.world.profiler.startSection("rest");
    super.posX = (super.getEntityBoundingBox().minX + super.getEntityBoundingBox().maxX) / 2.0D;
    super.posY = super.getEntityBoundingBox().minY + (double)super.getYOffset() - (double)super.height;
    super.posZ = (super.getEntityBoundingBox().minZ + super.getEntityBoundingBox().maxZ) / 2.0D;
    super.collidedHorizontally = mx != parX || mz != parZ;
    super.collidedVertically = my != parY;
    super.onGround = my != parY && my < 0.0D;
    super.collided = super.collidedHorizontally || super.collidedVertically;
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
    this.updateFallState(parY, super.onGround, iblockstate, blockpos);
    if(mx != parX) {
      super.motionX = 0.0D;
    }

    if(my != parY) {
      super.motionY = 0.0D;
    }

    if(mz != parZ) {
      super.motionZ = 0.0D;
    }

    try {
      this.doBlockCollisions();
    } catch (Throwable var31) {
      CrashReport var34 = CrashReport.makeCrashReport(var31, "Checking entity tile collision");
      CrashReportCategory crashreportcategory = var34.makeCategory("Entity being checked for collision");
      this.addEntityCrashInfo(crashreportcategory);
    }

    super.world.profiler.endSection();
  }

  public List getCollidingBoundingBoxes(Entity par1Entity, AxisAlignedBB par2AxisAlignedBB) {
    ArrayList collidingBoundingBoxes = new ArrayList();
    collidingBoundingBoxes.clear();
    int i = MathHelper.floor(par2AxisAlignedBB.minX);
    int j = MathHelper.floor(par2AxisAlignedBB.maxX + 1.0D);
    int k = MathHelper.floor(par2AxisAlignedBB.minY);
    int l = MathHelper.floor(par2AxisAlignedBB.maxY + 1.0D);
    int i1 = MathHelper.floor(par2AxisAlignedBB.minZ);
    int j1 = MathHelper.floor(par2AxisAlignedBB.maxZ + 1.0D);

    for(int d0 = i; d0 < j; ++d0) {
      for(int l1 = i1; l1 < j1; ++l1) {
        if(par1Entity.world.isBlockLoaded(new BlockPos(d0, 64, l1))) {
          for (int y = k - 1; y < l; ++y) {
            BlockPos blockPos = new BlockPos(d0, y, l1);
            IBlockState state = par1Entity.world.getBlockState(blockPos);
            Block block = state.getBlock();

            if (block != null) {
              block.addCollisionBoxToList(
                      state,
                      par1Entity.world,
                      blockPos,
                      par2AxisAlignedBB,
                      collidingBoundingBoxes,
                      par1Entity,
                      false // this is a boolean for if the collision is a bullet hitbox or similar
              );
            }
          }
        }
      }
    }

    double var16 = 0.25D;
    List var17 = par1Entity.world.getEntitiesWithinAABBExcludingEntity(par1Entity, par2AxisAlignedBB.expand(var16, var16, var16));

    for(int var18 = 0; var18 < var17.size(); ++var18) {
      Entity entity = (Entity)var17.get(var18);
      if(!W_Lib.isEntityLivingBase(entity) && !(entity instanceof MCH_EntitySeat) && !(entity instanceof MCH_EntityHitBox) && entity != this.parents) {
        AxisAlignedBB axisalignedbb1 = entity.getEntityBoundingBox();
        if (axisalignedbb1 != null && axisalignedbb1.intersects(par2AxisAlignedBB)) {
          collidingBoundingBoxes.add(axisalignedbb1);
        }

        axisalignedbb1 = par1Entity.getCollisionBox(entity);
        if(axisalignedbb1 != null && axisalignedbb1.intersects(par2AxisAlignedBB)) {
          collidingBoundingBoxes.add(axisalignedbb1);
        }
      }
    }

    return collidingBoundingBoxes;
  }
}
