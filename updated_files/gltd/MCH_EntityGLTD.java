package mcheli.gltd;

import java.util.List;
import javax.annotation.Nullable;
import mcheli.MCH_Camera;
import mcheli.MCH_Config;
import mcheli.MCH_MOD;
import mcheli.__helper.entity.IEntityItemStackPickable;
import mcheli.__helper.entity.IEntitySinglePassenger;
import mcheli.multiplay.MCH_Multiplay;
import mcheli.weapon.MCH_WeaponCAS;
import mcheli.weapon.MCH_WeaponInfo;
import mcheli.weapon.MCH_WeaponInfoManager;
import mcheli.wrapper.W_Block;
import mcheli.wrapper.W_Entity;
import mcheli.wrapper.W_WorldFunc;
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
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MCH_EntityGLTD extends W_Entity implements IEntitySinglePassenger, IEntityItemStackPickable {
  public static final float Y_OFFSET = 0.25F;
  
  private static final DataParameter<Integer> TIME_SINCE_HIT = EntityDataManager.createKey(MCH_EntityGLTD.class, DataSerializers.VARINT);
  
  private static final DataParameter<Integer> FORWARD_DIR = EntityDataManager.createKey(MCH_EntityGLTD.class, DataSerializers.VARINT);
  
  private static final DataParameter<Integer> DAMAGE_TAKEN = EntityDataManager.createKey(MCH_EntityGLTD.class, DataSerializers.VARINT);
  
  private boolean isBoatEmpty;
  
  private double speedMultiplier;
  
  private int gltdPosRotInc;
  
  private double gltdX;
  
  private double gltdY;
  
  private double gltdZ;
  
  private double gltdYaw;
  
  private double gltdPitch;
  
  @SideOnly(Side.CLIENT)
  private double velocityX;
  
  @SideOnly(Side.CLIENT)
  private double velocityY;
  
  @SideOnly(Side.CLIENT)
  private double velocityZ;
  
  public final MCH_Camera camera;
  
  public boolean zoomDir;
  
  public final MCH_WeaponCAS weaponCAS;
  
  public int countWait;
  
  public boolean isUsedPlayer;
  
  public float renderRotaionYaw;
  
  public float renderRotaionPitch;
  
  public int retryRiddenByEntityCheck;
  
  public Entity lastRiddenByEntity;
  
  public MCH_EntityGLTD(World world) {
    super(world);
    this.isBoatEmpty = true;
    this.speedMultiplier = 0.07D;
    this.preventEntitySpawning = true;
    setSize(0.5F, 0.5F);
    this.camera = new MCH_Camera(world, (Entity)this);
    MCH_WeaponInfo wi = MCH_WeaponInfoManager.get("a10gau8");
    this.weaponCAS = new MCH_WeaponCAS(world, Vec3d.ZERO, 0.0F, 0.0F, "a10gau8", wi);
    this.weaponCAS.interval += (this.weaponCAS.interval > 0) ? 150 : 65386;
    this.weaponCAS.displayName = "A-10 GAU-8 Avenger";
    this.ignoreFrustumCheck = true;
    this.countWait = 0;
    this.retryRiddenByEntityCheck = 0;
    this.lastRiddenByEntity = null;
    this.isUsedPlayer = false;
    this.renderRotaionYaw = 0.0F;
    this.renderRotaionYaw = 0.0F;
    this.renderRotaionPitch = 0.0F;
    this.zoomDir = true;
    this._renderDistanceWeight = 2.0D;
  }
  
  public MCH_EntityGLTD(World par1World, double x, double y, double z) {
    this(par1World);
    setPosition(x, y, z);
    this.motionX = 0.0D;
    this.motionY = 0.0D;
    this.motionZ = 0.0D;
    this.prevPosX = x;
    this.prevPosY = y;
    this.prevPosZ = z;
    this.camera.setPosition(x, y, z);
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
    return false;
  }
  
  public double getMountedYOffset() {
    return this.height * 0.0D - 0.3D;
  }
  
  public boolean attackEntityFrom(DamageSource ds, float damage) {
    if (isEntityInvulnerable(ds))
      return false; 
    if (!this.world.isRemote && !this.isDead) {
      damage = MCH_Config.applyDamageByExternal((Entity)this, ds, damage);
      if (!MCH_Multiplay.canAttackEntity(ds, (Entity)this))
        return false; 
      setForwardDirection(-getForwardDirection());
      setTimeSinceHit(10);
      setDamageTaken((int)(getDamageTaken() + damage * 100.0F));
      setBeenAttacked();
      boolean flag = (ds.getEntity() instanceof EntityPlayer && ((EntityPlayer)ds.getEntity()).capabilities.isCreativeMode);
      if (flag || getDamageTaken() > 40.0F) {
        Entity riddenByEntity = getRiddenByEntity();
        this.camera.initCamera(0, riddenByEntity);
        if (riddenByEntity != null)
          riddenByEntity.startRiding((Entity)this); 
        if (!flag)
          dropItemWithOffset((Item)MCH_MOD.itemGLTD, 1, 0.0F); 
        W_WorldFunc.MOD_playSoundEffect(this.world, this.posX, this.posY, this.posZ, "hit", 1.0F, 1.0F);
        setDead();
      } 
      return true;
    } 
    return true;
  }
  
  @SideOnly(Side.CLIENT)
  public void performHurtAnimation() {}
  
  public boolean canBeCollidedWith() {
    return !this.isDead;
  }
  
  @SideOnly(Side.CLIENT)
  public void setPositionAndRotation2(double par1, double par3, double par5, float par7, float par8, int par9) {
    if (this.isBoatEmpty) {
      this.gltdPosRotInc = par9 + 5;
    } else {
      double x = par1 - this.posX;
      double y = par3 - this.posY;
      double z = par5 - this.posZ;
      if (x * x + y * y + z * z <= 1.0D)
        return; 
      this.gltdPosRotInc = 3;
    } 
    this.gltdX = par1;
    this.gltdY = par3;
    this.gltdZ = par5;
    this.gltdYaw = par7;
    this.gltdPitch = par8;
    this.motionX = this.velocityX;
    this.motionY = this.velocityY;
    this.motionZ = this.velocityZ;
  }
  
  @SideOnly(Side.CLIENT)
  public void setVelocity(double x, double y, double z) {
    this.velocityX = this.motionX = x;
    this.velocityY = this.motionY = y;
    this.velocityZ = this.motionZ = z;
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
    double d3 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
    Entity riddenByEntity = getRiddenByEntity();
    if (riddenByEntity != null)
      this.camera.updateViewer(0, riddenByEntity); 
    if (this.world.isRemote && this.isBoatEmpty) {
      if (this.gltdPosRotInc > 0) {
        double d4 = this.posX + (this.gltdX - this.posX) / this.gltdPosRotInc;
        double d5 = this.posY + (this.gltdY - this.posY) / this.gltdPosRotInc;
        double d11 = this.posZ + (this.gltdZ - this.posZ) / this.gltdPosRotInc;
        double d10 = MathHelper.wrapDegrees(this.gltdYaw - this.rotationYaw);
        this.rotationYaw = (float)(this.rotationYaw + d10 / this.gltdPosRotInc);
        this.rotationPitch = (float)(this.rotationPitch + (this.gltdPitch - this.rotationPitch) / this.gltdPosRotInc);
        this.gltdPosRotInc--;
        setPosition(d4, d5, d11);
        setRotation(this.rotationYaw, this.rotationPitch);
      } else {
        double d4 = this.posX + this.motionX;
        double d5 = this.posY + this.motionY;
        double d11 = this.posZ + this.motionZ;
        setPosition(d4, d5, d11);
        if (this.onGround) {
          this.motionX *= 0.5D;
          this.motionY *= 0.5D;
          this.motionZ *= 0.5D;
        } 
        this.motionX *= 0.99D;
        this.motionY *= 0.95D;
        this.motionZ *= 0.99D;
      } 
    } else {
      this.motionY -= 0.04D;
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
        this.motionX *= 0.5D;
        this.motionY *= 0.5D;
        this.motionZ *= 0.5D;
      } 
      moveEntity(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
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
      if (d12 > 20.0D)
        d12 = 20.0D; 
      if (d12 < -20.0D)
        d12 = -20.0D; 
      this.rotationYaw = (float)(this.rotationYaw + d12);
      setRotation(this.rotationYaw, this.rotationPitch);
      if (!this.world.isRemote) {
        if (MCH_Config.Collision_DestroyBlock.prmBool)
          for (int l = 0; l < 4; l++) {
            int i1 = MathHelper.floor(this.posX + ((l % 2) - 0.5D) * 0.8D);
            int j1 = MathHelper.floor(this.posZ + ((l / 2) - 0.5D) * 0.8D);
            for (int k1 = 0; k1 < 2; k1++) {
              int l1 = MathHelper.floor(this.posY) + k1;
              if (W_WorldFunc.isEqualBlock(this.world, i1, l1, j1, W_Block.getSnowLayer()))
                this.world.setBlockToAir(new BlockPos(i1, l1, j1)); 
            } 
          }  
        riddenByEntity = getRiddenByEntity();
        if (riddenByEntity != null && riddenByEntity.isDead)
          riddenByEntity.dismountRidingEntity(); 
      } 
    } 
    updateCameraPosition(false);
    if (this.countWait > 0)
      this.countWait--; 
    if (this.countWait < 0)
      this.countWait++; 
    this.weaponCAS.update(this.countWait);
    riddenByEntity = getRiddenByEntity();
    if (this.lastRiddenByEntity != null && riddenByEntity == null) {
      if (this.retryRiddenByEntityCheck < 3) {
        this.retryRiddenByEntityCheck++;
        setUnmoundPosition(this.lastRiddenByEntity);
      } else {
        unmountEntity();
      } 
    } else {
      this.retryRiddenByEntityCheck = 0;
    } 
    riddenByEntity = getRiddenByEntity();
    if (riddenByEntity != null)
      this.lastRiddenByEntity = riddenByEntity; 
  }
  
  public void setUnmoundPosition(Entity e) {
    if (e == null)
      return; 
    float yaw = this.rotationYaw;
    double d0 = Math.sin(yaw * Math.PI / 180.0D) * 1.2D;
    double d1 = -Math.cos(yaw * Math.PI / 180.0D) * 1.2D;
    e.setPosition(this.posX + d0, this.posY + getMountedYOffset() + e.getYOffset() + 1.0D, this.posZ + d1);
    e.lastTickPosX = e.prevPosX = e.posX;
    e.lastTickPosY = e.prevPosY = e.posY;
    e.lastTickPosZ = e.prevPosZ = e.posZ;
  }
  
  public void unmountEntity() {
    this.camera.setMode(0, 0);
    this.camera.setCameraZoom(1.0F);
    if (!this.world.isRemote) {
      Entity riddenByEntity = getRiddenByEntity();
      if (riddenByEntity != null) {
        if (!riddenByEntity.isDead)
          riddenByEntity.dismountRidingEntity(); 
      } else if (this.lastRiddenByEntity != null && !this.lastRiddenByEntity.isDead) {
        this.camera.updateViewer(0, this.lastRiddenByEntity);
        setUnmoundPosition(this.lastRiddenByEntity);
      } 
    } 
    this.lastRiddenByEntity = null;
  }
  
  public void updateCameraPosition(boolean foreceUpdate) {
    Entity riddenByEntity = getRiddenByEntity();
    if (foreceUpdate || (riddenByEntity != null && this.camera != null)) {
      double x = -Math.sin(this.rotationYaw * Math.PI / 180.0D) * 0.6D;
      double z = Math.cos(this.rotationYaw * Math.PI / 180.0D) * 0.6D;
      this.camera.setPosition(this.posX + x, this.posY + 0.7D, this.posZ + z);
    } 
  }
  
  @SideOnly(Side.CLIENT)
  public void zoomCamera(float f) {
    float z = this.camera.getCameraZoom();
    z += f;
    if (z < 1.0F)
      z = 1.0F; 
    if (z > 10.0F)
      z = 10.0F; 
    this.camera.setCameraZoom(z);
  }
  
  public void updatePassenger(Entity passenger) {
    if (isPassenger(passenger)) {
      double x = Math.sin(this.rotationYaw * Math.PI / 180.0D) * 0.5D;
      double z = -Math.cos(this.rotationYaw * Math.PI / 180.0D) * 0.5D;
      passenger.setPosition(this.posX + x, this.posY + getMountedYOffset() + passenger.getYOffset(), this.posZ + z);
    } 
  }
  
  public void switchWeapon(int id) {}
  
  public boolean useCurrentWeapon(int option1, int option2) {
    Entity riddenByEntity = getRiddenByEntity();
    if (this.countWait == 0 && riddenByEntity != null)
      if (this.weaponCAS.shot(riddenByEntity, this.camera.posX, this.camera.posY, this.camera.posZ, option1, option2)) {
        this.countWait = this.weaponCAS.interval;
        if (this.world.isRemote) {
          this.countWait += (this.countWait > 0) ? 10 : -10;
        } else {
          W_WorldFunc.MOD_playSoundEffect(this.world, this.posX, this.posY, this.posZ, "gltd", 0.5F, 1.0F);
        } 
        return true;
      }  
    return false;
  }
  
  protected void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {}
  
  protected void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {}
  
  @SideOnly(Side.CLIENT)
  public float getShadowSize() {
    return 0.0F;
  }
  
  public boolean processInitialInteract(EntityPlayer player, EnumHand hand) {
    Entity riddenByEntity = getRiddenByEntity();
    if (riddenByEntity != null && riddenByEntity instanceof EntityPlayer && riddenByEntity != player)
      return true; 
    player.rotationYaw = MathHelper.wrapDegrees(this.rotationYaw);
    player.rotationPitch = MathHelper.wrapDegrees(this.rotationPitch);
    if (!this.world.isRemote) {
      player.startRiding((Entity)this);
    } else {
      this.zoomDir = true;
      this.camera.setCameraZoom(1.0F);
      if (this.countWait > 0)
        this.countWait = -this.countWait; 
      if (this.countWait > -60)
        this.countWait = -60; 
    } 
    updateCameraPosition(true);
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
    return 0;
  }
  
  @SideOnly(Side.CLIENT)
  public void setIsBoatEmpty(boolean par1) {
    this.isBoatEmpty = par1;
  }
  
  @Nullable
  public Entity getRiddenByEntity() {
    List<Entity> passengers = getPassengers();
    return passengers.isEmpty() ? null : passengers.get(0);
  }
  
  public ItemStack getPickedResult(RayTraceResult target) {
    return new ItemStack((Item)MCH_MOD.itemGLTD);
  }
}
