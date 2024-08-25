package mcheli.uav;

import java.util.List;
import javax.annotation.Nullable;
import mcheli.MCH_Config;
import mcheli.MCH_Explosion;
import mcheli.MCH_Lib;
import mcheli.MCH_MOD;
import mcheli.__helper.entity.IEntityItemStackPickable;
import mcheli.__helper.entity.IEntitySinglePassenger;
import mcheli.__helper.network.PooledGuiParameter;
import mcheli.aircraft.MCH_EntityAircraft;
import mcheli.helicopter.MCH_EntityHeli;
import mcheli.helicopter.MCH_HeliInfo;
import mcheli.helicopter.MCH_HeliInfoManager;
import mcheli.helicopter.MCH_ItemHeli;
import mcheli.multiplay.MCH_Multiplay;
import mcheli.plane.MCP_EntityPlane;
import mcheli.plane.MCP_ItemPlane;
import mcheli.plane.MCP_PlaneInfo;
import mcheli.plane.MCP_PlaneInfoManager;
import mcheli.tank.MCH_EntityTank;
import mcheli.tank.MCH_ItemTank;
import mcheli.tank.MCH_TankInfo;
import mcheli.tank.MCH_TankInfoManager;
import mcheli.wrapper.W_Entity;
import mcheli.wrapper.W_EntityContainer;
import mcheli.wrapper.W_EntityPlayer;
import mcheli.wrapper.W_WorldFunc;
import net.minecraft.block.state.IBlockState;
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

public class MCH_EntityUavStation extends W_EntityContainer implements IEntitySinglePassenger, IEntityItemStackPickable {
  public static final float Y_OFFSET = 0.35F;
  
  private static final DataParameter<Byte> STATUS = EntityDataManager.createKey(MCH_EntityUavStation.class, DataSerializers.BYTE);
  
  private static final DataParameter<Integer> LAST_AC_ID = EntityDataManager.createKey(MCH_EntityUavStation.class, DataSerializers.VARINT);
  
  private static final DataParameter<BlockPos> UAV_POS = EntityDataManager.createKey(MCH_EntityUavStation.class, DataSerializers.BLOCK_POS);
  
  protected Entity lastRiddenByEntity;
  
  public boolean isRequestedSyncStatus;
  
  @SideOnly(Side.CLIENT)
  protected double velocityX;
  
  @SideOnly(Side.CLIENT)
  protected double velocityY;
  
  @SideOnly(Side.CLIENT)
  protected double velocityZ;
  
  protected int aircraftPosRotInc;
  
  protected double aircraftX;
  
  protected double aircraftY;
  
  protected double aircraftZ;
  
  protected double aircraftYaw;
  
  protected double aircraftPitch;
  
  private MCH_EntityAircraft controlAircraft;
  
  private MCH_EntityAircraft lastControlAircraft;
  
  private String loadedLastControlAircraftGuid;
  
  public int posUavX;
  
  public int posUavY;
  
  public int posUavZ;
  
  public float rotCover;
  
  public float prevRotCover;
  
  public MCH_EntityUavStation(World world) {
    super(world);
    this.dropContentsWhenDead = false;
    this.preventEntitySpawning = true;
    setSize(2.0F, 0.7F);
    this.motionX = 0.0D;
    this.motionY = 0.0D;
    this.motionZ = 0.0D;
    this.ignoreFrustumCheck = true;
    this.lastRiddenByEntity = null;
    this.aircraftPosRotInc = 0;
    this.aircraftX = 0.0D;
    this.aircraftY = 0.0D;
    this.aircraftZ = 0.0D;
    this.aircraftYaw = 0.0D;
    this.aircraftPitch = 0.0D;
    this.posUavX = 0;
    this.posUavY = 0;
    this.posUavZ = 0;
    this.rotCover = 0.0F;
    this.prevRotCover = 0.0F;
    setControlAircract((MCH_EntityAircraft)null);
    setLastControlAircraft((MCH_EntityAircraft)null);
    this.loadedLastControlAircraftGuid = "";
  }
  
  protected void entityInit() {
    super.entityInit();
    this.dataManager.register(STATUS, Byte.valueOf((byte)0));
    this.dataManager.register(LAST_AC_ID, Integer.valueOf(0));
    this.dataManager.register(UAV_POS, BlockPos.ORIGIN);
    setOpen(true);
  }
  
  public int getStatus() {
    return ((Byte)this.dataManager.get(STATUS)).byteValue();
  }
  
  public void setStatus(int n) {
    if (!this.world.isRemote) {
      MCH_Lib.DbgLog(this.world, "MCH_EntityUavStation.setStatus(%d)", new Object[] { Integer.valueOf(n) });
      this.dataManager.set(STATUS, Byte.valueOf((byte)n));
    } 
  }
  
  public int getKind() {
    return 0x7F & getStatus();
  }
  
  public void setKind(int n) {
    setStatus(getStatus() & 0x80 | n);
  }
  
  public boolean isOpen() {
    return ((getStatus() & 0x80) != 0);
  }
  
  public void setOpen(boolean b) {
    setStatus((b ? 128 : 0) | getStatus() & 0x7F);
  }
  
  @Nullable
  public MCH_EntityAircraft getControlAircract() {
    return this.controlAircraft;
  }
  
  public void setControlAircract(@Nullable MCH_EntityAircraft ac) {
    this.controlAircraft = ac;
    if (ac != null && !ac.isDead)
      setLastControlAircraft(ac); 
  }
  
  public void setUavPosition(int x, int y, int z) {
    if (!this.world.isRemote) {
      this.posUavX = x;
      this.posUavY = y;
      this.posUavZ = z;
      this.dataManager.set(UAV_POS, new BlockPos(x, y, z));
    } 
  }
  
  public void updateUavPosition() {
    BlockPos uavPos = (BlockPos)this.dataManager.get(UAV_POS);
    this.posUavX = uavPos.getX();
    this.posUavY = uavPos.getY();
    this.posUavZ = uavPos.getZ();
  }
  
  protected void writeEntityToNBT(NBTTagCompound nbt) {
    super.writeEntityToNBT(nbt);
    nbt.setInteger("UavStatus", getStatus());
    nbt.setInteger("PosUavX", this.posUavX);
    nbt.setInteger("PosUavY", this.posUavY);
    nbt.setInteger("PosUavZ", this.posUavZ);
    String s = "";
    if (getLastControlAircraft() != null && !(getLastControlAircraft()).isDead)
      s = getLastControlAircraft().getCommonUniqueId(); 
    if (s.isEmpty())
      s = this.loadedLastControlAircraftGuid; 
    nbt.setString("LastCtrlAc", s);
  }
  
  protected void readEntityFromNBT(NBTTagCompound nbt) {
    super.readEntityFromNBT(nbt);
    setUavPosition(nbt.getInteger("PosUavX"), nbt.getInteger("PosUavY"), nbt.getInteger("PosUavZ"));
    if (nbt.hasKey("UavStatus")) {
      setStatus(nbt.getInteger("UavStatus"));
    } else {
      setKind(1);
    } 
    this.loadedLastControlAircraftGuid = nbt.getString("LastCtrlAc");
  }
  
  public void initUavPostion() {
    int rt = (int)(MCH_Lib.getRotate360((this.rotationYaw + 45.0F)) / 90.0D);
    this.posUavX = (rt == 0 || rt == 3) ? 12 : -12;
    this.posUavZ = (rt == 0 || rt == 1) ? 12 : -12;
    this.posUavY = 2;
    setUavPosition(this.posUavX, this.posUavY, this.posUavZ);
  }
  
  public void setDead() {
    super.setDead();
  }
  
  public boolean attackEntityFrom(DamageSource damageSource, float damage) {
    if (isEntityInvulnerable(damageSource))
      return false; 
    if (this.isDead)
      return true; 
    if (this.world.isRemote)
      return true; 
    String dmt = damageSource.getDamageType();
    damage = MCH_Config.applyDamageByExternal((Entity)this, damageSource, damage);
    if (!MCH_Multiplay.canAttackEntity(damageSource, (Entity)this))
      return false; 
    boolean isCreative = false;
    Entity entity = damageSource.getTrueSource();
    boolean isDamegeSourcePlayer = false;
    if (entity instanceof EntityPlayer) {
      isCreative = ((EntityPlayer)entity).capabilities.isCreativeMode;
      if (dmt.compareTo("player") == 0)
        isDamegeSourcePlayer = true; 
      W_WorldFunc.MOD_playSoundAtEntity((Entity)this, "hit", 1.0F, 1.0F);
    } else {
      W_WorldFunc.MOD_playSoundAtEntity((Entity)this, "helidmg", 1.0F, 0.9F + this.rand.nextFloat() * 0.1F);
    } 
    markVelocityChanged();
    if (damage > 0.0F) {
      Entity riddenByEntity = getRiddenByEntity();
      if (riddenByEntity != null)
        riddenByEntity.startRiding((Entity)this); 
      this.dropContentsWhenDead = true;
      setDead();
      if (!isDamegeSourcePlayer)
        MCH_Explosion.newExplosion(this.world, null, riddenByEntity, this.posX, this.posY, this.posZ, 1.0F, 0.0F, true, true, false, false, 0); 
      if (!isCreative) {
        int kind = getKind();
        if (kind > 0)
          dropItemWithOffset((Item)MCH_MOD.itemUavStation[kind - 1], 1, 0.0F); 
      } 
    } 
    return true;
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
    Entity riddenByEntity = getRiddenByEntity();
    if (getKind() == 2 && riddenByEntity != null) {
      double px = -Math.sin(this.rotationYaw * Math.PI / 180.0D) * 0.9D;
      double pz = Math.cos(this.rotationYaw * Math.PI / 180.0D) * 0.9D;
      int x = (int)(this.posX + px);
      int y = (int)(this.posY - 0.5D);
      int z = (int)(this.posZ + pz);
      BlockPos blockpos = new BlockPos(x, y, z);
      IBlockState iblockstate = this.world.getBlockState(blockpos);
      return iblockstate.isOpaqueCube() ? -0.4D : -0.9D;
    } 
    return 0.35D;
  }
  
  @SideOnly(Side.CLIENT)
  public float getShadowSize() {
    return 2.0F;
  }
  
  public boolean canBeCollidedWith() {
    return !this.isDead;
  }
  
  public void applyEntityCollision(Entity par1Entity) {}
  
  public void addVelocity(double par1, double par3, double par5) {}
  
  @SideOnly(Side.CLIENT)
  public void setVelocity(double par1, double par3, double par5) {
    this.velocityX = this.motionX = par1;
    this.velocityY = this.motionY = par3;
    this.velocityZ = this.motionZ = par5;
  }
  
  public void onUpdate() {
    super.onUpdate();
    this.prevRotCover = this.rotCover;
    if (isOpen()) {
      if (this.rotCover < 1.0F) {
        this.rotCover += 0.1F;
      } else {
        this.rotCover = 1.0F;
      } 
    } else if (this.rotCover > 0.0F) {
      this.rotCover -= 0.1F;
    } else {
      this.rotCover = 0.0F;
    } 
    Entity riddenByEntity = getRiddenByEntity();
    if (riddenByEntity == null) {
      if (this.lastRiddenByEntity != null)
        unmountEntity(true); 
      setControlAircract((MCH_EntityAircraft)null);
    } 
    int uavStationKind = getKind();
    if (this.ticksExisted >= 30 || uavStationKind <= 0 || uavStationKind == 1 || uavStationKind != 2 || (this.world.isRemote && !this.isRequestedSyncStatus))
      this.isRequestedSyncStatus = true; 
    this.prevPosX = this.posX;
    this.prevPosY = this.posY;
    this.prevPosZ = this.posZ;
    if (getControlAircract() != null && (getControlAircract()).isDead)
      setControlAircract((MCH_EntityAircraft)null); 
    if (getLastControlAircraft() != null && (getLastControlAircraft()).isDead)
      setLastControlAircraft((MCH_EntityAircraft)null); 
    if (this.world.isRemote) {
      onUpdate_Client();
    } else {
      onUpdate_Server();
    } 
    this.lastRiddenByEntity = getRiddenByEntity();
  }
  
  @Nullable
  public MCH_EntityAircraft getLastControlAircraft() {
    return this.lastControlAircraft;
  }
  
  public MCH_EntityAircraft getAndSearchLastControlAircraft() {
    if (getLastControlAircraft() == null) {
      int id = getLastControlAircraftEntityId().intValue();
      if (id > 0) {
        Entity entity = this.world.getEntityByID(id);
        if (entity instanceof MCH_EntityAircraft) {
          MCH_EntityAircraft ac = (MCH_EntityAircraft)entity;
          if (ac.isUAV())
            setLastControlAircraft(ac); 
        } 
      } 
    } 
    return getLastControlAircraft();
  }
  
  public void setLastControlAircraft(MCH_EntityAircraft ac) {
    MCH_Lib.DbgLog(this.world, "MCH_EntityUavStation.setLastControlAircraft:" + ac, new Object[0]);
    this.lastControlAircraft = ac;
  }
  
  public Integer getLastControlAircraftEntityId() {
    return (Integer)this.dataManager.get(LAST_AC_ID);
  }
  
  public void setLastControlAircraftEntityId(int s) {
    if (!this.world.isRemote)
      this.dataManager.set(LAST_AC_ID, Integer.valueOf(s)); 
  }
  
  public void searchLastControlAircraft() {
    if (this.loadedLastControlAircraftGuid.isEmpty())
      return; 
    List<MCH_EntityAircraft> list = this.world.getEntitiesWithinAABB(MCH_EntityAircraft.class, 
        getCollisionBoundingBox().grow(120.0D, 120.0D, 120.0D));
    if (list == null)
      return; 
    for (int i = 0; i < list.size(); i++) {
      MCH_EntityAircraft ac = list.get(i);
      if (ac.getCommonUniqueId().equals(this.loadedLastControlAircraftGuid)) {
        String n = "no info : " + ac;
        MCH_Lib.DbgLog(this.world, "MCH_EntityUavStation.searchLastControlAircraft:found" + n, new Object[0]);
        setLastControlAircraft(ac);
        setLastControlAircraftEntityId(W_Entity.getEntityId((Entity)ac));
        this.loadedLastControlAircraftGuid = "";
        return;
      } 
    } 
  }
  
  protected void onUpdate_Client() {
    if (this.aircraftPosRotInc > 0) {
      double rpinc = this.aircraftPosRotInc;
      double yaw = MathHelper.wrapDegrees(this.aircraftYaw - this.rotationYaw);
      this.rotationYaw = (float)(this.rotationYaw + yaw / rpinc);
      this.rotationPitch = (float)(this.rotationPitch + (this.aircraftPitch - this.rotationPitch) / rpinc);
      setPosition(this.posX + (this.aircraftX - this.posX) / rpinc, this.posY + (this.aircraftY - this.posY) / rpinc, this.posZ + (this.aircraftZ - this.posZ) / rpinc);
      setRotation(this.rotationYaw, this.rotationPitch);
      this.aircraftPosRotInc--;
    } else {
      setPosition(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
      this.motionY *= 0.96D;
      this.motionX = 0.0D;
      this.motionZ = 0.0D;
    } 
    updateUavPosition();
  }
  
  private void onUpdate_Server() {
    this.motionY -= 0.03D;
    move(MoverType.SELF, 0.0D, this.motionY, 0.0D);
    this.motionY *= 0.96D;
    this.motionX = 0.0D;
    this.motionZ = 0.0D;
    setRotation(this.rotationYaw, this.rotationPitch);
    Entity riddenByEntity = getRiddenByEntity();
    if (riddenByEntity != null)
      if (riddenByEntity.isDead) {
        unmountEntity(true);
      } else {
        ItemStack item = getStackInSlot(0);
        if (!item.isEmpty()) {
          handleItem(riddenByEntity, item);
          if (item.getCount() == 0)
            setInventorySlotContents(0, ItemStack.EMPTY); 
        } 
      }  
    if (getLastControlAircraft() == null)
      if (this.ticksExisted % 40 == 0)
        searchLastControlAircraft();  
  }
  
  public void setPositionAndRotationDirect(double par1, double par3, double par5, float par7, float par8, int par9, boolean teleport) {
    this.aircraftPosRotInc = par9 + 8;
    this.aircraftX = par1;
    this.aircraftY = par3;
    this.aircraftZ = par5;
    this.aircraftYaw = par7;
    this.aircraftPitch = par8;
    this.motionX = this.velocityX;
    this.motionY = this.velocityY;
    this.motionZ = this.velocityZ;
  }
  
  public void updatePassenger(Entity passenger) {
    if (isPassenger(passenger)) {
      double x = -Math.sin(this.rotationYaw * Math.PI / 180.0D) * 0.9D;
      double z = Math.cos(this.rotationYaw * Math.PI / 180.0D) * 0.9D;
      passenger.setPosition(this.posX + x, this.posY + getMountedYOffset() + passenger.getYOffset() + 0.3499999940395355D, this.posZ + z);
    } 
  }
  
  public void controlLastAircraft(Entity user) {
    if (getLastControlAircraft() != null && !(getLastControlAircraft()).isDead) {
      getLastControlAircraft().setUavStation(this);
      setControlAircract(getLastControlAircraft());
      W_EntityPlayer.closeScreen(user);
    } 
  }

  public void handleItem(Entity user, ItemStack itemStack) {
    if(user != null && !user.isDead && itemStack != null && itemStack.getMaxStackSize() == 1) {
      if(!super.world.isRemote) {
        Object ac = null;
        double x = super.posX + (double)this.posUavX;
        double y = super.posY + (double)this.posUavY;
        double z = super.posZ + (double)this.posUavZ;
        if(y <= 1.0D) {
          y = 2.0D;
        }

        Item item = itemStack.getItem();
        if(item instanceof MCP_ItemPlane) {
          MCP_PlaneInfo hi = MCP_PlaneInfoManager.getFromItem(item);
          if(hi != null && hi.isUAV) {
            if(!hi.isSmallUAV && this.getKind() == 2) {
              ac = null;
            } else {
              ac = ((MCP_ItemPlane)item).createAircraft(super.world, x, y, z, itemStack);
            }
          }
        }

        if(item instanceof MCH_ItemHeli) {
          MCH_HeliInfo hi1 = MCH_HeliInfoManager.getFromItem(item);
          if(hi1 != null && hi1.isUAV) {
            if(!hi1.isSmallUAV && this.getKind() == 2) {
              ac = null;
            } else {
              ac = ((MCH_ItemHeli)item).createAircraft(super.world, x, y, z, itemStack);
            }
          }
        }

        if(item instanceof MCH_ItemTank) {
          MCH_TankInfo hi2 = MCH_TankInfoManager.getFromItem(item);
          if(hi2 != null && hi2.isUAV) {
            if(!hi2.isSmallUAV && this.getKind() == 2) {
              ac = null;
            } else {
              ac = ((MCH_ItemTank)item).createAircraft(super.world, x, y, z, itemStack);
            }
          }
        }

        if(ac != null) {
          ((Entity)ac).rotationYaw = super.rotationYaw - 180.0F;
          ((Entity)ac).prevRotationYaw = ((Entity)ac).rotationYaw;
          user.rotationYaw = super.rotationYaw - 180.0F;
          if(super.world.getCollisionBoxes((Entity)ac, ((Entity)ac).getCollisionBoundingBox().expand(-0.1D, -0.1D, -0.1D)).isEmpty()) {
            int itemCount = itemStack.getCount();
            --itemCount;
            MCH_Lib.DbgLog(super.world, "Create UAV: %s : %s", new Object[]{item.getUnlocalizedName(), item});
            user.rotationYaw = super.rotationYaw - 180.0F;
            if(!((MCH_EntityAircraft)ac).isTargetDrone()) {
              ((MCH_EntityAircraft)ac).setUavStation(this);
              this.setControlAircract((MCH_EntityAircraft)ac);
            }

            super.world.spawnEntity((Entity)ac);
            if(!((MCH_EntityAircraft)ac).isTargetDrone()) {
              ((MCH_EntityAircraft)ac).setFuel((int)((float)((MCH_EntityAircraft)ac).getMaxFuel() * 0.05F));
              W_EntityPlayer.closeScreen(user);
            } else {
              ((MCH_EntityAircraft)ac).setFuel(((MCH_EntityAircraft)ac).getMaxFuel());
            }
          } else {
            ((MCH_EntityAircraft)ac).setDead();
          }

        }
      }
    }
  }
  
  public void _setInventorySlotContents(int par1, ItemStack itemStack) {
    setInventorySlotContents(par1, itemStack);
  }
  
  public boolean processInitialInteract(EntityPlayer player, EnumHand hand) {
    if (hand != EnumHand.MAIN_HAND)
      return false; 
    int kind = getKind();
    if (kind <= 0)
      return false; 
    if (getRiddenByEntity() != null)
      return false; 
    if (kind == 2) {
      if (player.isSneaking()) {
        setOpen(!isOpen());
        return false;
      } 
      if (!isOpen())
        return false; 
    } 
    this.lastRiddenByEntity = null;
    PooledGuiParameter.setEntity(player, (Entity)this);
    if (!this.world.isRemote) {
      player.startRiding((Entity)this);
      player.openGui(MCH_MOD.instance, 0, player.world, (int)this.posX, (int)this.posY, (int)this.posZ);
    } 
    return true;
  }
  
  public int getSizeInventory() {
    return 1;
  }
  
  public int getInventoryStackLimit() {
    return 1;
  }
  
  public void unmountEntity(boolean unmountAllEntity) {
    Entity rByEntity = null;
    Entity riddenByEntity = getRiddenByEntity();
    if (riddenByEntity != null) {
      if (!this.world.isRemote) {
        rByEntity = riddenByEntity;
        riddenByEntity.dismountRidingEntity();
      } 
    } else if (this.lastRiddenByEntity != null) {
      rByEntity = this.lastRiddenByEntity;
    } 
    if (getControlAircract() != null)
      getControlAircract().setUavStation(null); 
    setControlAircract((MCH_EntityAircraft)null);
    if (this.world.isRemote)
      W_EntityPlayer.closeScreen(rByEntity); 
    this.lastRiddenByEntity = null;
  }
  
  @Nullable
  public Entity getRiddenByEntity() {
    List<Entity> passengers = getPassengers();
    return passengers.isEmpty() ? null : passengers.get(0);
  }
  
  public ItemStack getPickedResult(RayTraceResult target) {
    int kind = getKind();
    return (kind > 0) ? new ItemStack((Item)MCH_MOD.itemUavStation[kind - 1]) : ItemStack.EMPTY;
  }
}
