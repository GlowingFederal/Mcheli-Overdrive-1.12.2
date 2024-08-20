package mcheli.container;

import java.util.List;
import mcheli.MCH_Config;
import mcheli.MCH_Lib;
import mcheli.MCH_MOD;
import mcheli.__helper.entity.IEntityItemStackPickable;
import mcheli.aircraft.MCH_EntityAircraft;
import mcheli.aircraft.MCH_IEntityCanRideAircraft;
import mcheli.aircraft.MCH_SeatRackInfo;
import mcheli.multiplay.MCH_Multiplay;
import mcheli.wrapper.W_AxisAlignedBB;
import mcheli.wrapper.W_Block;
import mcheli.wrapper.W_Blocks;
import mcheli.wrapper.W_EntityContainer;
import mcheli.wrapper.W_WorldFunc;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MCH_EntityContainer extends W_EntityContainer implements MCH_IEntityCanRideAircraft, IEntityItemStackPickable {
  public static final float Y_OFFSET = 0.5F;
  
  private static final DataParameter<Integer> TIME_SINCE_HIT = EntityDataManager.createKey(MCH_EntityContainer.class, DataSerializers.VARINT);
  
  private static final DataParameter<Integer> FORWARD_DIR = EntityDataManager.createKey(MCH_EntityContainer.class, DataSerializers.VARINT);
  
  private static final DataParameter<Integer> DAMAGE_TAKEN = EntityDataManager.createKey(MCH_EntityContainer.class, DataSerializers.VARINT);
  
  private double speedMultiplier;
  
  private int boatPosRotationIncrements;
  
  private double boatX;
  
  private double boatY;
  
  private double boatZ;
  
  private double boatYaw;
  
  private double boatPitch;
  
  @SideOnly(Side.CLIENT)
  private double velocityX;
  
  @SideOnly(Side.CLIENT)
  private double velocityY;
  
  @SideOnly(Side.CLIENT)
  private double velocityZ;
  
  public MCH_EntityContainer(World par1World) {
    super(par1World);
    this.speedMultiplier = 0.07D;
    this.preventEntitySpawning = true;
    setSize(2.0F, 1.0F);
    this.stepHeight = 0.6F;
    this.isImmuneToFire = true;
    this._renderDistanceWeight = 2.0D;
  }
  
  public MCH_EntityContainer(World par1World, double par2, double par4, double par6) {
    this(par1World);
    setPosition(par2, par4 + 0.5D, par6);
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
    this.dataManager.register(TIME_SINCE_HIT, Integer.valueOf(0));
    this.dataManager.register(FORWARD_DIR, Integer.valueOf(1));
    this.dataManager.register(DAMAGE_TAKEN, Integer.valueOf(0));
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
  
  public int getSizeInventory() {
    return 54;
  }
  
  public String getInvName() {
    return "Container " + super.getInvName();
  }
  
  public double getMountedYOffset() {
    return -0.3D;
  }
  
  public boolean attackEntityFrom(DamageSource ds, float damage) {
    if (isEntityInvulnerable(ds))
      return false; 
    if (this.world.isRemote || this.isDead)
      return false; 
    damage = MCH_Config.applyDamageByExternal((Entity)this, ds, damage);
    if (!MCH_Multiplay.canAttackEntity(ds, (Entity)this))
      return false; 
    if (ds.getTrueSource() instanceof EntityPlayer && ds.getDamageType().equalsIgnoreCase("player")) {
      MCH_Lib.DbgLog(this.world, "MCH_EntityContainer.attackEntityFrom:damage=%.1f:%s", new Object[] { Float.valueOf(damage), ds.getDamageType() });
      W_WorldFunc.MOD_playSoundAtEntity((Entity)this, "hit", 1.0F, 1.3F);
      setDamageTaken(getDamageTaken() + (int)(damage * 20.0F));
    } else {
      return false;
    } 
    setForwardDirection(-getForwardDirection());
    setTimeSinceHit(10);
    markVelocityChanged();
    boolean flag = (ds.getTrueSource() instanceof EntityPlayer && ((EntityPlayer)ds.getTrueSource()).capabilities.isCreativeMode);
    if (flag || getDamageTaken() > 40.0F) {
      if (!flag)
        dropItemWithOffset((Item)MCH_MOD.itemContainer, 1, 0.0F); 
      setDead();
    } 
    return true;
  }
  
  @SideOnly(Side.CLIENT)
  public void performHurtAnimation() {
    setForwardDirection(-getForwardDirection());
    setTimeSinceHit(10);
    setDamageTaken(getDamageTaken() * 11);
  }
  
  public boolean canBeCollidedWith() {
    return !this.isDead;
  }
  
  @SideOnly(Side.CLIENT)
  public void setPositionAndRotationDirect(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean teleport) {
    this.boatPosRotationIncrements = posRotationIncrements + 10;
    this.boatX = x;
    this.boatY = y;
    this.boatZ = z;
    this.boatYaw = yaw;
    this.boatPitch = pitch;
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
  
  public void onUpdate() {
    super.onUpdate();
    if (getTimeSinceHit() > 0)
      setTimeSinceHit(getTimeSinceHit() - 1); 
    if (getDamageTaken() > 0.0F)
      setDamageTaken(getDamageTaken() - 1); 
    this.prevPosX = this.posX;
    this.prevPosY = this.posY;
    this.prevPosZ = this.posZ;
    byte b0 = 5;
    double d0 = 0.0D;
    for (int i = 0; i < b0; i++) {
      AxisAlignedBB boundingBox = getEntityBoundingBox();
      double d1 = boundingBox.minY + (boundingBox.maxY - boundingBox.minY) * (i + 0) / b0 - 0.125D;
      double d2 = boundingBox.minY + (boundingBox.maxY - boundingBox.minY) * (i + 1) / b0 - 0.125D;
      AxisAlignedBB axisalignedbb = W_AxisAlignedBB.getAABB(boundingBox.minX, d1, boundingBox.minZ, boundingBox.maxX, d2, boundingBox.maxZ);
      if (this.world.isMaterialInBB(axisalignedbb, Material.WATER)) {
        d0 += 1.0D / b0;
      } else if (this.world.isMaterialInBB(axisalignedbb, Material.LAVA)) {
        d0 += 1.0D / b0;
      } 
    } 
    double d3 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
    if (d3 > 0.2625D);
    if (this.world.isRemote) {
      if (this.boatPosRotationIncrements > 0) {
        double d4 = this.posX + (this.boatX - this.posX) / this.boatPosRotationIncrements;
        double d5 = this.posY + (this.boatY - this.posY) / this.boatPosRotationIncrements;
        double d11 = this.posZ + (this.boatZ - this.posZ) / this.boatPosRotationIncrements;
        double d10 = MathHelper.wrapDegrees(this.boatYaw - this.rotationYaw);
        this.rotationYaw = (float)(this.rotationYaw + d10 / this.boatPosRotationIncrements);
        this.rotationPitch = (float)(this.rotationPitch + (this.boatPitch - this.rotationPitch) / this.boatPosRotationIncrements);
        this.boatPosRotationIncrements--;
        setPosition(d4, d5, d11);
        setRotation(this.rotationYaw, this.rotationPitch);
      } else {
        double d4 = this.posX + this.motionX;
        double d5 = this.posY + this.motionY;
        double d11 = this.posZ + this.motionZ;
        setPosition(d4, d5, d11);
        if (this.onGround) {
          this.motionX *= 0.8999999761581421D;
          this.motionZ *= 0.8999999761581421D;
        } 
        this.motionX *= 0.99D;
        this.motionY *= 0.95D;
        this.motionZ *= 0.99D;
      } 
    } else {
      if (d0 < 1.0D) {
        double d = d0 * 2.0D - 1.0D;
        this.motionY += 0.04D * d;
      } else {
        if (this.motionY < 0.0D)
          this.motionY /= 2.0D; 
        this.motionY += 0.007D;
      } 
      double d4 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
      if (d4 > 0.35D) {
        double d = 0.35D / d4;
        this.motionX *= d;
        this.motionZ *= d;
        d4 = 0.35D;
      } 
      if (d4 > d3 && this.speedMultiplier < 0.35D) {
        this.speedMultiplier += (0.35D - this.speedMultiplier) / 35.0D;
        if (this.speedMultiplier > 0.35D)
          this.speedMultiplier = 0.35D; 
      } else {
        this.speedMultiplier -= (this.speedMultiplier - 0.07D) / 35.0D;
        if (this.speedMultiplier < 0.07D)
          this.speedMultiplier = 0.07D; 
      } 
      if (this.onGround) {
        this.motionX *= 0.8999999761581421D;
        this.motionZ *= 0.8999999761581421D;
      } 
      move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
      this.motionX *= 0.99D;
      this.motionY *= 0.95D;
      this.motionZ *= 0.99D;
      this.rotationPitch = 0.0F;
      double d5 = this.rotationYaw;
      double d11 = this.prevPosX - this.posX;
      double d10 = this.prevPosZ - this.posZ;
      if (d11 * d11 + d10 * d10 > 0.001D)
        d5 = (float)(Math.atan2(d10, d11) * 180.0D / Math.PI); 
      double d12 = MathHelper.wrapDegrees(d5 - this.rotationYaw);
      if (d12 > 5.0D)
        d12 = 5.0D; 
      if (d12 < -5.0D)
        d12 = -5.0D; 
      this.rotationYaw = (float)(this.rotationYaw + d12);
      setRotation(this.rotationYaw, this.rotationPitch);
      if (!this.world.isRemote) {
        List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity((Entity)this, 
            getEntityBoundingBox().grow(0.2D, 0.0D, 0.2D));
        if (list != null && !list.isEmpty())
          for (int l = 0; l < list.size(); l++) {
            Entity entity = list.get(l);
            if (entity.canBePushed() && entity instanceof MCH_EntityContainer)
              entity.applyEntityCollision((Entity)this); 
          }  
        if (MCH_Config.Collision_DestroyBlock.prmBool)
          for (int l = 0; l < 4; l++) {
            int i1 = MathHelper.floor(this.posX + ((l % 2) - 0.5D) * 0.8D);
            int j1 = MathHelper.floor(this.posZ + ((l / 2) - 0.5D) * 0.8D);
            for (int k1 = 0; k1 < 2; k1++) {
              int l1 = MathHelper.floor(this.posY) + k1;
              if (W_WorldFunc.isEqualBlock(this.world, i1, l1, j1, W_Block.getSnowLayer())) {
                this.world.setBlockToAir(new BlockPos(i1, l1, j1));
              } else if (W_WorldFunc.isEqualBlock(this.world, i1, l1, j1, W_Blocks.WATERLILY)) {
                W_WorldFunc.destroyBlock(this.world, i1, l1, j1, true);
              } 
            } 
          }  
      } 
    } 
  }
  
  protected void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
    super.writeEntityToNBT(par1NBTTagCompound);
  }
  
  protected void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
    super.readEntityFromNBT(par1NBTTagCompound);
  }
  
  @SideOnly(Side.CLIENT)
  public float getShadowSize() {
    return 2.0F;
  }
  
  public boolean processInitialInteract(EntityPlayer player, EnumHand hand) {
    if (player != null)
      displayInventory(player); 
    return true;
  }
  
  public void setDamageTaken(int par1) {
    this.dataManager.set(DAMAGE_TAKEN, Integer.valueOf(par1));
  }
  
  public int getDamageTaken() {
    return ((Integer)this.dataManager.get(DAMAGE_TAKEN)).intValue();
  }
  
  public void setTimeSinceHit(int par1) {
    this.dataManager.set(TIME_SINCE_HIT, Integer.valueOf(par1));
  }
  
  public int getTimeSinceHit() {
    return ((Integer)this.dataManager.get(TIME_SINCE_HIT)).intValue();
  }
  
  public void setForwardDirection(int par1) {
    this.dataManager.set(FORWARD_DIR, Integer.valueOf(par1));
  }
  
  public int getForwardDirection() {
    return ((Integer)this.dataManager.get(FORWARD_DIR)).intValue();
  }
  
  public boolean canRideAircraft(MCH_EntityAircraft ac, int seatID, MCH_SeatRackInfo info) {
    for (String s : info.names) {
      if (s.equalsIgnoreCase("container"))
        return (ac.getRidingEntity() == null && getRidingEntity() == null); 
    } 
    return false;
  }
  
  public boolean isSkipNormalRender() {
    return getRidingEntity() instanceof mcheli.aircraft.MCH_EntitySeat;
  }
  
  public ItemStack getPickedResult(RayTraceResult target) {
    return new ItemStack((Item)MCH_MOD.itemContainer);
  }
}
