package mcheli.aircraft;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import mcheli.__helper.entity.IEntitySinglePassenger;
import mcheli.wrapper.W_Entity;
import mcheli.wrapper.W_Lib;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ReportedException;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.border.WorldBorder;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MCH_EntityHide extends W_Entity implements IEntitySinglePassenger {
  private static final DataParameter<Integer> ROPE_INDEX = EntityDataManager.createKey(MCH_EntityHide.class, DataSerializers.VARINT);
  
  private static final DataParameter<Integer> AC_ID = EntityDataManager.createKey(MCH_EntityHide.class, DataSerializers.VARINT);
  
  private MCH_EntityAircraft ac;
  
  private Entity user;
  
  private int paraPosRotInc;
  
  private double paraX;
  
  private double paraY;
  
  private double paraZ;
  
  private double paraYaw;
  
  private double paraPitch;
  
  @SideOnly(Side.CLIENT)
  private double velocityX;
  
  @SideOnly(Side.CLIENT)
  private double velocityY;
  
  @SideOnly(Side.CLIENT)
  private double velocityZ;
  
  public MCH_EntityHide(World par1World) {
    super(par1World);
    setSize(1.0F, 1.0F);
    this.preventEntitySpawning = true;
    this.user = null;
    this.motionX = this.motionY = this.motionZ = 0.0D;
  }
  
  public MCH_EntityHide(World par1World, double x, double y, double z) {
    this(par1World);
    this.posX = x;
    this.posY = y;
    this.posZ = z;
  }
  
  protected void entityInit() {
    super.entityInit();
    createRopeIndex(-1);
    this.dataManager.register(AC_ID, new Integer(0));
  }
  
  public void setParent(MCH_EntityAircraft ac, Entity user, int ropeIdx) {
    this.ac = ac;
    setRopeIndex(ropeIdx);
    this.user = user;
  }
  
  protected boolean canTriggerWalking() {
    return false;
  }
  
  public AxisAlignedBB getCollisionBox(Entity par1Entity) {
    return par1Entity.getEntityBoundingBox();
  }
  
  public AxisAlignedBB getCollisionBoundingBox() {
    return getEntityBoundingBox();
  }
  
  public boolean canBePushed() {
    return true;
  }
  
  public double getMountedYOffset() {
    return this.height * 0.0D - 0.3D;
  }
  
  public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
    return false;
  }
  
  public boolean canBeCollidedWith() {
    return !this.isDead;
  }
  
  protected void writeEntityToNBT(NBTTagCompound nbt) {}
  
  protected void readEntityFromNBT(NBTTagCompound nbt) {}
  
  @SideOnly(Side.CLIENT)
  public float getShadowSize() {
    return 0.0F;
  }
  
  public boolean processInitialInteract(EntityPlayer player, EnumHand hand) {
    return false;
  }
  
  public void createRopeIndex(int defaultValue) {
    this.dataManager.register(ROPE_INDEX, new Integer(defaultValue));
  }
  
  public int getRopeIndex() {
    return ((Integer)this.dataManager.get(ROPE_INDEX)).intValue();
  }
  
  public void setRopeIndex(int value) {
    this.dataManager.set(ROPE_INDEX, new Integer(value));
  }
  
  @SideOnly(Side.CLIENT)
  public void setPositionAndRotationDirect(double par1, double par3, double par5, float par7, float par8, int par9, boolean teleport) {
    this.paraPosRotInc = par9 + 10;
    this.paraX = par1;
    this.paraY = par3;
    this.paraZ = par5;
    this.paraYaw = par7;
    this.paraPitch = par8;
    this.motionX = this.velocityX;
    this.motionY = this.velocityY;
    this.motionZ = this.velocityZ;
  }
  
  @SideOnly(Side.CLIENT)
  public void setVelocity(double par1, double par3, double par5) {
    this.velocityX = this.motionX = par1;
    this.velocityY = this.motionY = par3;
    this.velocityZ = this.motionZ = par5;
  }
  
  public void setDead() {
    super.setDead();
  }
  
  public void onUpdate() {
    super.onUpdate();
    if (this.user != null && !this.world.isRemote) {
      if (this.ac != null)
        this.dataManager.set(AC_ID, new Integer(this.ac.getEntityId())); 
      this.user.startRiding((Entity)this, true);
      this.user = null;
    } 
    if (this.ac == null && this.world.isRemote) {
      int id = ((Integer)this.dataManager.get(AC_ID)).intValue();
      if (id > 0) {
        Entity entity = this.world.getEntityByID(id);
        if (entity instanceof MCH_EntityAircraft)
          this.ac = (MCH_EntityAircraft)entity; 
      } 
    } 
    this.prevPosX = this.posX;
    this.prevPosY = this.posY;
    this.prevPosZ = this.posZ;
    this.fallDistance = 0.0F;
    Entity riddenByEntity = getRiddenByEntity();
    if (riddenByEntity != null)
      riddenByEntity.fallDistance = 0.0F; 
    if (this.ac != null) {
      if (!this.ac.isRepelling())
        setDead(); 
      int id = getRopeIndex();
      if (id >= 0) {
        Vec3d v = this.ac.getRopePos(id);
        this.posX = v.x;
        this.posZ = v.z;
      } 
    } 
    setPosition(this.posX, this.posY, this.posZ);
    if (this.world.isRemote) {
      onUpdateClient();
    } else {
      onUpdateServer();
    } 
  }
  
  public void onUpdateClient() {
    if (this.paraPosRotInc > 0) {
      double x = this.posX + (this.paraX - this.posX) / this.paraPosRotInc;
      double y = this.posY + (this.paraY - this.posY) / this.paraPosRotInc;
      double z = this.posZ + (this.paraZ - this.posZ) / this.paraPosRotInc;
      double yaw = MathHelper.wrapDegrees(this.paraYaw - this.rotationYaw);
      this.rotationYaw = (float)(this.rotationYaw + yaw / this.paraPosRotInc);
      this.rotationPitch = (float)(this.rotationPitch + (this.paraPitch - this.rotationPitch) / this.paraPosRotInc);
      this.paraPosRotInc--;
      setPosition(x, y, z);
      setRotation(this.rotationYaw, this.rotationPitch);
      Entity riddenByEntity = getRiddenByEntity();
      if (riddenByEntity != null)
        setRotation(riddenByEntity.prevRotationYaw, this.rotationPitch); 
    } else {
      setPosition(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
      this.motionX *= 0.99D;
      this.motionY *= 0.95D;
      this.motionZ *= 0.99D;
    } 
  }
  
  public void onUpdateServer() {
    this.motionY -= this.onGround ? 0.01D : 0.03D;
    if (this.onGround) {
      onGroundAndDead();
      return;
    } 
    move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
    this.motionY *= 0.9D;
    this.motionX *= 0.95D;
    this.motionZ *= 0.95D;
    int id = getRopeIndex();
    if (this.ac != null && id >= 0) {
      Vec3d v = this.ac.getRopePos(id);
      if (Math.abs(this.posY - v.y) > (Math.abs(this.ac.ropesLength) + 5.0F))
        onGroundAndDead(); 
    } 
    Entity riddenByEntity = getRiddenByEntity();
    if (riddenByEntity != null && riddenByEntity.isDead)
      setDead(); 
  }
  
  private boolean getCollisionBoxes(@Nullable Entity entityIn, AxisAlignedBB aabb, List<AxisAlignedBB> outList) {
    int i = MathHelper.floor(aabb.minX) - 1;
    int j = MathHelper.ceil(aabb.maxX) + 1;
    int k = MathHelper.floor(aabb.minY) - 1;
    int l = MathHelper.ceil(aabb.maxY) + 1;
    int i1 = MathHelper.floor(aabb.minZ) - 1;
    int j1 = MathHelper.ceil(aabb.maxZ) + 1;
    WorldBorder worldborder = this.world.getWorldBorder();
    boolean flag = (entityIn != null && entityIn.isOutsideBorder());
    boolean flag1 = (entityIn != null && this.world.isInsideWorldBorder(entityIn));
    IBlockState iblockstate = Blocks.STONE.getDefaultState();
    BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos = BlockPos.PooledMutableBlockPos.retain();
    try {
      for (int k1 = i; k1 < j; k1++) {
        for (int l1 = i1; l1 < j1; l1++) {
          boolean flag2 = (k1 == i || k1 == j - 1);
          boolean flag3 = (l1 == i1 || l1 == j1 - 1);
          if ((!flag2 || !flag3) && this.world
            .isBlockLoaded((BlockPos)blockpos$pooledmutableblockpos.setPos(k1, 64, l1)))
            for (int i2 = k; i2 < l; i2++) {
              if ((!flag2 && !flag3) || i2 != l - 1) {
                IBlockState iblockstate1;
                if (entityIn != null && flag == flag1)
                  entityIn.setOutsideBorder(!flag1); 
                blockpos$pooledmutableblockpos.setPos(k1, i2, l1);
                if (!worldborder.contains((BlockPos)blockpos$pooledmutableblockpos) && flag1) {
                  iblockstate1 = iblockstate;
                } else {
                  iblockstate1 = this.world.getBlockState((BlockPos)blockpos$pooledmutableblockpos);
                } 
                iblockstate1.addCollisionBoxToList(this.world, (BlockPos)blockpos$pooledmutableblockpos, aabb, outList, entityIn, false);
              } 
            }  
        } 
      } 
    } finally {
      blockpos$pooledmutableblockpos.release();
    } 
    return !outList.isEmpty();
  }
  
  public List<AxisAlignedBB> getCollidingBoundingBoxes(Entity par1Entity, AxisAlignedBB par2AxisAlignedBB) {
    List<AxisAlignedBB> list = new ArrayList<>();
    getCollisionBoxes(par1Entity, par2AxisAlignedBB, list);
    if (par1Entity != null) {
      List<Entity> list1 = this.world.getEntitiesWithinAABBExcludingEntity(par1Entity, par2AxisAlignedBB
          .grow(0.25D));
      for (int i = 0; i < list1.size(); i++) {
        Entity entity = list1.get(i);
        if (!W_Lib.isEntityLivingBase(entity) && !(entity instanceof MCH_EntitySeat) && !(entity instanceof MCH_EntityHitBox)) {
          AxisAlignedBB axisalignedbb = entity.getCollisionBoundingBox();
          if (axisalignedbb != null && axisalignedbb.intersects(par2AxisAlignedBB))
            list.add(axisalignedbb); 
          axisalignedbb = par1Entity.getCollisionBox(entity);
          if (axisalignedbb != null && axisalignedbb.intersects(par2AxisAlignedBB))
            list.add(axisalignedbb); 
        } 
      } 
    } 
    return list;
  }
  
  public void move(MoverType type, double x, double y, double z) {
    this.world.profiler.startSection("move");
    double d2 = x;
    double d3 = y;
    double d4 = z;
    List<AxisAlignedBB> list1 = getCollidingBoundingBoxes((Entity)this, getEntityBoundingBox().expand(x, y, z));
    AxisAlignedBB axisalignedbb = getEntityBoundingBox();
    if (y != 0.0D) {
      int k = 0;
      for (int l = list1.size(); k < l; k++)
        y = ((AxisAlignedBB)list1.get(k)).calculateYOffset(getEntityBoundingBox(), y); 
      setEntityBoundingBox(getEntityBoundingBox().offset(0.0D, y, 0.0D));
    } 
    boolean flag = (this.onGround || (d3 != y && d3 < 0.0D));
    if (x != 0.0D) {
      int j5 = 0;
      for (int l5 = list1.size(); j5 < l5; j5++)
        x = ((AxisAlignedBB)list1.get(j5)).calculateXOffset(getEntityBoundingBox(), x); 
      if (x != 0.0D)
        setEntityBoundingBox(getEntityBoundingBox().offset(x, 0.0D, 0.0D)); 
    } 
    if (z != 0.0D) {
      int k5 = 0;
      for (int i6 = list1.size(); k5 < i6; k5++)
        z = ((AxisAlignedBB)list1.get(k5)).calculateZOffset(getEntityBoundingBox(), z); 
      if (z != 0.0D)
        setEntityBoundingBox(getEntityBoundingBox().offset(0.0D, 0.0D, z)); 
    } 
    if (this.stepHeight > 0.0F && flag && (d2 != x || d4 != z)) {
      double d14 = x;
      double d6 = y;
      double d7 = z;
      y = this.stepHeight;
      AxisAlignedBB axisalignedbb1 = getEntityBoundingBox();
      setEntityBoundingBox(axisalignedbb);
      List<AxisAlignedBB> list = getCollidingBoundingBoxes((Entity)this, 
          getEntityBoundingBox().expand(d2, y, d4));
      AxisAlignedBB axisalignedbb2 = getEntityBoundingBox();
      AxisAlignedBB axisalignedbb3 = axisalignedbb2.expand(d2, 0.0D, d4);
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
    this.world.profiler.endSection();
    this.world.profiler.startSection("rest");
    resetPositionToBB();
    this.collidedHorizontally = (d2 != x || d4 != z);
    this.collidedVertically = (d3 != y);
    this.onGround = (this.collidedVertically && d3 < 0.0D);
    this.collided = (this.collidedHorizontally || this.collidedVertically);
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
    this.world.profiler.endSection();
  }
  
  public void onGroundAndDead() {
    this.posY += 0.5D;
    updatePassenger(getRiddenByEntity());
    setDead();
  }
  
  @Nullable
  public Entity getRiddenByEntity() {
    List<Entity> passengers = getPassengers();
    return passengers.isEmpty() ? null : passengers.get(0);
  }
}
