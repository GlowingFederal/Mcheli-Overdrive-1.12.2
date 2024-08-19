package mcheli.parachute;

import java.util.List;
import javax.annotation.Nullable;
import mcheli.MCH_Lib;
import mcheli.__helper.entity.IEntitySinglePassenger;
import mcheli.particles.MCH_ParticleParam;
import mcheli.particles.MCH_ParticlesUtil;
import mcheli.wrapper.W_AxisAlignedBB;
import mcheli.wrapper.W_Block;
import mcheli.wrapper.W_Entity;
import mcheli.wrapper.W_Lib;
import mcheli.wrapper.W_WorldFunc;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MCH_EntityParachute extends W_Entity implements IEntitySinglePassenger {
  private static final DataParameter<Byte> TYPE = EntityDataManager.createKey(MCH_EntityParachute.class, DataSerializers.BYTE);
  
  private double speedMultiplier;
  
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
  
  public Entity user;
  
  public int onGroundCount;
  
  public MCH_EntityParachute(World par1World) {
    super(par1World);
    this.speedMultiplier = 0.07D;
    this.preventEntitySpawning = true;
    setSize(1.5F, 0.6F);
    this.user = null;
    this.onGroundCount = 0;
  }
  
  public MCH_EntityParachute(World par1World, double par2, double par4, double par6) {
    this(par1World);
    setPosition(par2, par4, par6);
    this.motionX = 0.0D;
    this.motionY = 0.0D;
    this.motionZ = 0.0D;
    this.prevPosX = par2;
    this.prevPosY = par4;
    this.prevPosZ = par6;
  }
  
  protected boolean canTriggerWalking() {
    return false;
  }
  
  protected void entityInit() {
    this.dataManager.register(TYPE, Byte.valueOf((byte)0));
  }
  
  public void setType(int n) {
    this.dataManager.set(TYPE, Byte.valueOf((byte)n));
  }
  
  public int getType() {
    return ((Byte)this.dataManager.get(TYPE)).byteValue();
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
    return this.height * 0.0D - 0.30000001192092896D;
  }
  
  public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
    return false;
  }
  
  public boolean canBeCollidedWith() {
    return !this.isDead;
  }
  
  @SideOnly(Side.CLIENT)
  public void setPositionAndRotationDirect(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean teleport) {
    this.paraPosRotInc = posRotationIncrements + 10;
    this.paraX = x;
    this.paraY = y;
    this.paraZ = z;
    this.paraYaw = yaw;
    this.paraPitch = pitch;
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
    if (!this.world.isRemote && this.ticksExisted % 10 == 0)
      MCH_Lib.DbgLog(this.world, "MCH_EntityParachute.onUpdate %d, %.3f", new Object[] { Integer.valueOf(this.ticksExisted), Double.valueOf(this.motionY) }); 
    if (isOpenParachute() && this.motionY > -0.3D && this.ticksExisted > 20)
      this.fallDistance = (float)(this.fallDistance * 0.85D); 
    if (!this.world.isRemote && this.user != null && this.user.getRidingEntity() == null) {
      this.user.startRiding((Entity)this);
      this.rotationYaw = this.prevRotationYaw = this.user.rotationYaw;
      this.user = null;
    } 
    this.prevPosX = this.posX;
    this.prevPosY = this.posY;
    this.prevPosZ = this.posZ;
    double d1 = (getEntityBoundingBox()).minY + ((getEntityBoundingBox()).maxY - (getEntityBoundingBox()).minY) * 0.0D / 5.0D - 0.125D;
    double d2 = (getEntityBoundingBox()).minY + ((getEntityBoundingBox()).maxY - (getEntityBoundingBox()).minY) * 1.0D / 5.0D - 0.125D;
    AxisAlignedBB axisalignedbb = W_AxisAlignedBB.getAABB((getEntityBoundingBox()).minX, d1, 
        (getEntityBoundingBox()).minZ, (getEntityBoundingBox()).maxX, d2, 
        (getEntityBoundingBox()).maxZ);
    if (this.world.isMaterialInBB(axisalignedbb, Material.WATER)) {
      onWaterSetBoat();
      setDead();
    } 
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
      if (getRiddenByEntity() != null)
        setRotation((getRiddenByEntity()).prevRotationYaw, this.rotationPitch); 
    } else {
      setPosition(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
      if (this.onGround);
      this.motionX *= 0.99D;
      this.motionY *= 0.95D;
      this.motionZ *= 0.99D;
    } 
    if (!isOpenParachute() && this.motionY > 0.01D) {
      float color = 0.6F + this.rand.nextFloat() * 0.2F;
      double dx = this.prevPosX - this.posX;
      double dy = this.prevPosY - this.posY;
      double dz = this.prevPosZ - this.posZ;
      int num = 1 + (int)(MathHelper.sqrt(dx * dx + dy * dy + dz * dz) * 2.0D);
      double i;
      for (i = 0.0D; i < num; i++) {
        MCH_ParticleParam prm = new MCH_ParticleParam(this.world, "smoke", this.prevPosX + (this.posX - this.prevPosX) * i / num * 0.8D, this.prevPosY + (this.posY - this.prevPosY) * i / num * 0.8D, this.prevPosZ + (this.posZ - this.prevPosZ) * i / num * 0.8D);
        prm.motionX = this.motionX * 0.5D + (this.rand.nextDouble() - 0.5D) * 0.5D;
        prm.motionX = this.motionY * -0.5D + (this.rand.nextDouble() - 0.5D) * 0.5D;
        prm.motionX = this.motionZ * 0.5D + (this.rand.nextDouble() - 0.5D) * 0.5D;
        prm.size = 5.0F;
        prm.setColor(0.8F + this.rand.nextFloat(), color, color, color);
        MCH_ParticlesUtil.spawnParticle(prm);
      } 
    } 
  }
  
  public void onUpdateServer() {
    double prevSpeed = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
    double gravity = this.onGround ? 0.01D : 0.03D;
    if (getType() == 2 && this.ticksExisted < 20)
      gravity = 0.01D; 
    this.motionY -= gravity;
    if (isOpenParachute()) {
      if (W_Lib.isEntityLivingBase(getRiddenByEntity())) {
        double mv = W_Lib.getEntityMoveDist(getRiddenByEntity());
        if (!isOpenParachute())
          mv = 0.0D; 
        if (mv > 0.0D) {
          double mx = -Math.sin(((getRiddenByEntity()).rotationYaw * 3.1415927F / 180.0F));
          double mz = Math.cos(((getRiddenByEntity()).rotationYaw * 3.1415927F / 180.0F));
          this.motionX += mx * this.speedMultiplier * 0.05D;
          this.motionZ += mz * this.speedMultiplier * 0.05D;
        } 
      } 
      double speed = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
      if (speed > 0.35D) {
        this.motionX *= 0.35D / speed;
        this.motionZ *= 0.35D / speed;
        speed = 0.35D;
      } 
      if (speed > prevSpeed && this.speedMultiplier < 0.35D) {
        this.speedMultiplier += (0.35D - this.speedMultiplier) / 35.0D;
        if (this.speedMultiplier > 0.35D)
          this.speedMultiplier = 0.35D; 
      } else {
        this.speedMultiplier -= (this.speedMultiplier - 0.07D) / 35.0D;
        if (this.speedMultiplier < 0.07D)
          this.speedMultiplier = 0.07D; 
      } 
    } 
    if (this.onGround) {
      this.onGroundCount++;
      if (this.onGroundCount > 5) {
        onGroundAndDead();
        return;
      } 
    } 
    moveEntity(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
    if (getType() == 2 && this.ticksExisted < 20) {
      this.motionY *= 0.95D;
    } else {
      this.motionY *= 0.9D;
    } 
    if (isOpenParachute()) {
      this.motionX *= 0.95D;
      this.motionZ *= 0.95D;
    } else {
      this.motionX *= 0.99D;
      this.motionZ *= 0.99D;
    } 
    this.rotationPitch = 0.0F;
    double yaw = this.rotationYaw;
    double dx = this.prevPosX - this.posX;
    double dz = this.prevPosZ - this.posZ;
    if (dx * dx + dz * dz > 0.001D)
      yaw = (float)(Math.atan2(dx, dz) * 180.0D / Math.PI); 
    double yawDiff = MathHelper.wrapDegrees(yaw - this.rotationYaw);
    if (yawDiff > 20.0D)
      yawDiff = 20.0D; 
    if (yawDiff < -20.0D)
      yawDiff = -20.0D; 
    if (getRiddenByEntity() != null) {
      setRotation((getRiddenByEntity()).rotationYaw, this.rotationPitch);
    } else {
      this.rotationYaw = (float)(this.rotationYaw + yawDiff);
      setRotation(this.rotationYaw, this.rotationPitch);
    } 
    List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity((Entity)this, 
        getEntityBoundingBox().expand(0.2D, 0.0D, 0.2D));
    if (list != null && !list.isEmpty())
      for (int l = 0; l < list.size(); l++) {
        Entity entity = list.get(l);
        if (entity != getRiddenByEntity() && entity.canBePushed() && entity instanceof MCH_EntityParachute)
          entity.applyEntityCollision((Entity)this); 
      }  
    if (getRiddenByEntity() != null && (getRiddenByEntity()).isDead)
      setDead(); 
  }
  
  public void onGroundAndDead() {
    this.posY += 1.2D;
    updatePassenger(getRiddenByEntity());
    setDead();
  }
  
  public void onWaterSetBoat() {
    if (this.world.isRemote)
      return; 
    if (getType() != 2)
      return; 
    if (getRiddenByEntity() == null)
      return; 
    int px = (int)(this.posX + 0.5D);
    int py = (int)(this.posY + 0.5D);
    int pz = (int)(this.posZ + 0.5D);
    boolean foundBlock = false;
    for (int y = 0; y < 5; y++) {
      if (py + y < 0 || py + y > 255)
        break; 
      Block block = W_WorldFunc.getBlock(this.world, px, py - y, pz);
      if (block == W_Block.getWater()) {
        py -= y;
        foundBlock = true;
        break;
      } 
    } 
    if (!foundBlock)
      return; 
    int countWater = 0;
    for (int i = 0; i < 3; i++) {
      if (py + i < 0 || py + i > 255)
        break; 
      for (int x = -2; x <= 2; x++) {
        for (int z = -2; z <= 2; z++) {
          Block block = W_WorldFunc.getBlock(this.world, px + x, py - i, pz + z);
          if (block == W_Block.getWater()) {
            countWater++;
            if (countWater > 37)
              break; 
          } 
        } 
      } 
    } 
    if (countWater > 37) {
      EntityBoat entityboat = new EntityBoat(this.world, px, (py + 1.0F), pz);
      entityboat.rotationYaw = this.rotationYaw - 90.0F;
      this.world.spawnEntityInWorld((Entity)entityboat);
      getRiddenByEntity().startRiding((Entity)entityboat);
    } 
  }
  
  public boolean isOpenParachute() {
    return (getType() != 2 || this.motionY < -0.1D);
  }
  
  public void updatePassenger(Entity passenger) {
    if (isPassenger(passenger)) {
      double x = -Math.sin(this.rotationYaw * Math.PI / 180.0D) * 0.1D;
      double z = Math.cos(this.rotationYaw * Math.PI / 180.0D) * 0.1D;
      passenger.setPosition(this.posX + x, this.posY + getMountedYOffset() + passenger.getYOffset(), this.posZ + z);
    } 
  }
  
  protected void writeEntityToNBT(NBTTagCompound nbt) {
    nbt.setByte("ParachuteModelType", (byte)getType());
  }
  
  protected void readEntityFromNBT(NBTTagCompound nbt) {
    setType(nbt.getByte("ParachuteModelType"));
  }
  
  @SideOnly(Side.CLIENT)
  public float getShadowSize() {
    return 4.0F;
  }
  
  public boolean processInitialInteract(EntityPlayer player, EnumHand hand) {
    return false;
  }
  
  @Nullable
  public Entity getRiddenByEntity() {
    List<Entity> passengers = getPassengers();
    return passengers.isEmpty() ? null : passengers.get(0);
  }
}
