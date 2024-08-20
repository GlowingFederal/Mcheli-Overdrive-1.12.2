package mcheli.aircraft;

import java.util.List;
import javax.annotation.Nullable;
import mcheli.MCH_Lib;
import mcheli.__helper.entity.IEntitySinglePassenger;
import mcheli.wrapper.W_Entity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MCH_EntitySeat extends W_Entity implements IEntitySinglePassenger {
  public String parentUniqueID;
  
  private MCH_EntityAircraft parent;
  
  public int seatID;
  
  public int parentSearchCount;
  
  protected Entity lastRiddenByEntity;
  
  public static final float BB_SIZE = 1.0F;
  
  public MCH_EntitySeat(World world) {
    super(world);
    setSize(1.0F, 1.0F);
    this.motionX = 0.0D;
    this.motionY = 0.0D;
    this.motionZ = 0.0D;
    this.seatID = -1;
    setParent((MCH_EntityAircraft)null);
    this.parentSearchCount = 0;
    this.lastRiddenByEntity = null;
    this.ignoreFrustumCheck = true;
    this.isImmuneToFire = true;
  }
  
  public MCH_EntitySeat(World world, double x, double y, double z) {
    this(world);
    setPosition(x, y + 1.0D, z);
    this.prevPosX = x;
    this.prevPosY = y + 1.0D;
    this.prevPosZ = z;
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
    return false;
  }
  
  public double getMountedYOffset() {
    return -0.3D;
  }
  
  public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
    if (getParent() != null)
      return getParent().attackEntityFrom(par1DamageSource, par2); 
    return false;
  }
  
  public boolean canBeCollidedWith() {
    return !this.isDead;
  }
  
  @SideOnly(Side.CLIENT)
  public void setPositionAndRotationDirect(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean teleport) {}
  
  public void setDead() {
    super.setDead();
  }
  
  public void onUpdate() {
    super.onUpdate();
    this.fallDistance = 0.0F;
    Entity riddenByEntity = getRiddenByEntity();
    if (riddenByEntity != null)
      riddenByEntity.fallDistance = 0.0F; 
    if (this.lastRiddenByEntity == null && riddenByEntity != null) {
      if (getParent() != null) {
        MCH_Lib.DbgLog(this.world, "MCH_EntitySeat.onUpdate:SeatID=%d", new Object[] { Integer.valueOf(this.seatID), riddenByEntity.toString() });
        getParent().onMountPlayerSeat(this, riddenByEntity);
      } 
    } else if (this.lastRiddenByEntity != null && riddenByEntity == null) {
      if (getParent() != null) {
        MCH_Lib.DbgLog(this.world, "MCH_EntitySeat.onUpdate:SeatID=%d", new Object[] { Integer.valueOf(this.seatID), this.lastRiddenByEntity.toString() });
        getParent().onUnmountPlayerSeat(this, this.lastRiddenByEntity);
      } 
    } 
    if (this.world.isRemote) {
      onUpdate_Client();
    } else {
      onUpdate_Server();
    } 
    this.lastRiddenByEntity = getRiddenByEntity();
  }
  
  private void onUpdate_Client() {
    checkDetachmentAndDelete();
  }
  
  private void onUpdate_Server() {
    checkDetachmentAndDelete();
    Entity riddenByEntity = getRiddenByEntity();
    if (riddenByEntity == null || riddenByEntity.isDead);
  }
  
  public void updatePassenger(Entity passenger) {
    updatePosition(passenger);
  }
  
  public void updatePosition(@Nullable Entity ridEnt) {
    if (ridEnt != null) {
      ridEnt.setPosition(this.posX, this.posY, this.posZ);
      ridEnt.motionX = ridEnt.motionY = ridEnt.motionZ = 0.0D;
    } 
  }
  
  public void updateRotation(@Nullable Entity ridEnt, float yaw, float pitch) {
    if (ridEnt != null) {
      ridEnt.rotationYaw = yaw;
      ridEnt.rotationPitch = pitch;
    } 
  }
  
  protected void checkDetachmentAndDelete() {
    if (!this.isDead && (this.seatID < 0 || getParent() == null || (getParent()).isDead)) {
      if (getParent() != null && (getParent()).isDead)
        this.parentSearchCount = 100000000; 
      if (this.parentSearchCount >= 1200) {
        setDead();
        if (!this.world.isRemote) {
          Entity riddenByEntity = getRiddenByEntity();
          if (riddenByEntity != null)
            riddenByEntity.dismountRidingEntity(); 
        } 
        setParent((MCH_EntityAircraft)null);
        MCH_Lib.DbgLog(this.world, "[Error]座席エンティティは本体が見つからないため削除 seat=%d, parentUniqueID=%s", new Object[] { Integer.valueOf(this.seatID), this.parentUniqueID });
      } else {
        this.parentSearchCount++;
      } 
    } else {
      this.parentSearchCount = 0;
    } 
  }
  
  protected void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
    par1NBTTagCompound.setInteger("SeatID", this.seatID);
    par1NBTTagCompound.setString("ParentUniqueID", this.parentUniqueID);
  }
  
  protected void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
    this.seatID = par1NBTTagCompound.getInteger("SeatID");
    this.parentUniqueID = par1NBTTagCompound.getString("ParentUniqueID");
  }
  
  @SideOnly(Side.CLIENT)
  public float getShadowSize() {
    return 0.0F;
  }
  
  public boolean canRideMob(Entity entity) {
    if (getParent() == null || this.seatID < 0)
      return false; 
    if (getParent().getSeatInfo(this.seatID + 1) instanceof MCH_SeatRackInfo)
      return false; 
    return true;
  }
  
  public boolean isGunnerMode() {
    Entity riddenByEntity = getRiddenByEntity();
    if (riddenByEntity != null)
      if (getParent() != null)
        return getParent().getIsGunnerMode(riddenByEntity);  
    return false;
  }
  
  public boolean processInitialInteract(EntityPlayer player, EnumHand hand) {
    if (getParent() == null || getParent().isDestroyed())
      return false; 
    if (!getParent().checkTeam(player))
      return false; 
    ItemStack itemStack = player.getHeldItem(hand);
    if (!itemStack.isEmpty() && itemStack.getItem() instanceof mcheli.tool.MCH_ItemWrench)
      return getParent().processInitialInteract(player, hand); 
    if (!itemStack.isEmpty() && itemStack.getItem() instanceof mcheli.mob.MCH_ItemSpawnGunner)
      return getParent().processInitialInteract(player, hand); 
    Entity riddenByEntity = getRiddenByEntity();
    if (riddenByEntity != null)
      return false; 
    if (player.getRidingEntity() != null)
      return false; 
    if (!canRideMob((Entity)player))
      return false; 
    player.startRiding((Entity)this);
    return true;
  }
  
  @Nullable
  public MCH_EntityAircraft getParent() {
    return this.parent;
  }
  
  public void setParent(MCH_EntityAircraft parent) {
    this.parent = parent;
    Entity riddenByEntity = getRiddenByEntity();
    if (riddenByEntity != null) {
      MCH_Lib.DbgLog(this.world, "MCH_EntitySeat.setParent:SeatID=%d %s : " + getParent(), new Object[] { Integer.valueOf(this.seatID), riddenByEntity.toString() });
      if (getParent() != null)
        getParent().onMountPlayerSeat(this, riddenByEntity); 
    } 
  }
  
  @Nullable
  public Entity getRiddenByEntity() {
    List<Entity> passengers = getPassengers();
    return passengers.isEmpty() ? null : passengers.get(0);
  }
}
