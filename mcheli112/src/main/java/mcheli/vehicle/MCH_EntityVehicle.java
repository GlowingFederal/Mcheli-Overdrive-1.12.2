package mcheli.vehicle;

import javax.annotation.Nullable;
import mcheli.MCH_Config;
import mcheli.MCH_Lib;
import mcheli.aircraft.MCH_AircraftInfo;
import mcheli.aircraft.MCH_EntityAircraft;
import mcheli.aircraft.MCH_PacketStatusRequest;
import mcheli.weapon.MCH_WeaponParam;
import mcheli.weapon.MCH_WeaponSet;
import mcheli.wrapper.W_Entity;
import mcheli.wrapper.W_Lib;
import mcheli.wrapper.W_WorldFunc;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MCH_EntityVehicle extends MCH_EntityAircraft {
  private MCH_VehicleInfo vehicleInfo;
  
  public boolean isUsedPlayer;
  
  public float lastRiderYaw;
  
  public float lastRiderPitch;
  
  public double fixPosX;
  
  public double fixPosY;
  
  public double fixPosZ;
  
  public MCH_EntityVehicle(World world) {
    super(world);
    this.vehicleInfo = null;
    this.currentSpeed = 0.07D;
    this.preventEntitySpawning = true;
    setSize(2.0F, 0.7F);
    this.motionX = 0.0D;
    this.motionY = 0.0D;
    this.motionZ = 0.0D;
    this.isUsedPlayer = false;
    this.lastRiderYaw = 0.0F;
    this.lastRiderPitch = 0.0F;
    this.weapons = createWeapon(0);
  }
  
  public String getKindName() {
    return "vehicles";
  }
  
  public String getEntityType() {
    return "Vehicle";
  }
  
  @Nullable
  public MCH_VehicleInfo getVehicleInfo() {
    return this.vehicleInfo;
  }
  
  public void changeType(String type) {
    MCH_Lib.DbgLog(this.world, "MCH_EntityVehicle.changeType " + type + " : " + toString(), new Object[0]);
    if (!type.isEmpty())
      this.vehicleInfo = MCH_VehicleInfoManager.get(type); 
    if (this.vehicleInfo == null) {
      MCH_Lib.Log((Entity)this, "##### MCH_EntityVehicle changeVehicleType() Vehicle info null %d, %s, %s", new Object[] { Integer.valueOf(W_Entity.getEntityId((Entity)this)), type, getEntityName() });
      setDead();
    } else {
      setAcInfo(this.vehicleInfo);
      newSeats(getAcInfo().getNumSeatAndRack());
      this.weapons = createWeapon(1 + getSeatNum());
      initPartRotation(this.rotationYaw, this.rotationPitch);
    } 
  }
  
  public boolean canMountWithNearEmptyMinecart() {
    return MCH_Config.MountMinecartVehicle.prmBool;
  }
  
  protected void entityInit() {
    super.entityInit();
  }
  
  protected void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
    super.writeEntityToNBT(par1NBTTagCompound);
  }
  
  protected void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
    super.readEntityFromNBT(par1NBTTagCompound);
    if (this.vehicleInfo == null) {
      this.vehicleInfo = MCH_VehicleInfoManager.get(getTypeName());
      if (this.vehicleInfo == null) {
        MCH_Lib.Log((Entity)this, "##### MCH_EntityVehicle readEntityFromNBT() Vehicle info null %d, %s", new Object[] { Integer.valueOf(W_Entity.getEntityId((Entity)this)), getEntityName() });
        setDead();
      } else {
        setAcInfo(this.vehicleInfo);
      } 
    } 
  }
  
  public Item getItem() {
    return (getVehicleInfo() != null) ? (Item)(getVehicleInfo()).item : null;
  }
  
  public void setDead() {
    super.setDead();
  }
  
  public float getSoundVolume() {
    return (float)getCurrentThrottle() * 2.0F;
  }
  
  public float getSoundPitch() {
    return (float)(getCurrentThrottle() * 0.5D);
  }
  
  public String getDefaultSoundName() {
    return "";
  }
  
  @SideOnly(Side.CLIENT)
  public void zoomCamera() {
    if (canZoom()) {
      float z = this.camera.getCameraZoom();
      z++;
      this.camera.setCameraZoom((z <= getZoomMax() + 0.01D) ? z : 1.0F);
    } 
  }
  
  public void _updateCameraRotate(float yaw, float pitch) {
    this.camera.prevRotationYaw = this.camera.rotationYaw;
    this.camera.prevRotationPitch = this.camera.rotationPitch;
    if (pitch > 89.0F)
      pitch = 89.0F; 
    if (pitch < -89.0F)
      pitch = -89.0F; 
    this.camera.rotationYaw = yaw;
    this.camera.rotationPitch = pitch;
  }
  
  public boolean isCameraView(Entity entity) {
    return true;
  }
  
  public boolean useCurrentWeapon(MCH_WeaponParam prm) {
    if (prm.user != null) {
      MCH_WeaponSet currentWs = getCurrentWeapon(prm.user);
      if (currentWs != null) {
        MCH_AircraftInfo.Weapon w = getAcInfo().getWeaponByName((currentWs.getInfo()).name);
        if (w != null)
          if (w.maxYaw != 0.0F && w.minYaw != 0.0F)
            return super.useCurrentWeapon(prm);  
      } 
    } 
    float breforeUseWeaponPitch = this.rotationPitch;
    float breforeUseWeaponYaw = this.rotationYaw;
    this.rotationPitch = prm.user.rotationPitch;
    this.rotationYaw = prm.user.rotationYaw;
    boolean result = super.useCurrentWeapon(prm);
    this.rotationPitch = breforeUseWeaponPitch;
    this.rotationYaw = breforeUseWeaponYaw;
    return result;
  }
  
  protected void mountWithNearEmptyMinecart() {
    if (!MCH_Config.FixVehicleAtPlacedPoint.prmBool)
      super.mountWithNearEmptyMinecart(); 
  }
  
  public void onUpdateAircraft() {
    if (this.vehicleInfo == null) {
      changeType(getTypeName());
      this.prevPosX = this.posX;
      this.prevPosY = this.posY;
      this.prevPosZ = this.posZ;
      return;
    } 
    if (this.ticksExisted < 200 || !MCH_Config.FixVehicleAtPlacedPoint.prmBool) {
      this.fixPosX = this.posX;
      this.fixPosY = this.posY;
      this.fixPosZ = this.posZ;
    } else {
      dismountRidingEntity();
      this.motionX = 0.0D;
      this.motionY = 0.0D;
      this.motionZ = 0.0D;
      if (this.world.isRemote && this.ticksExisted % 4 == 0)
        this.fixPosY = this.posY; 
      setPosition((this.posX + this.fixPosX) / 2.0D, (this.posY + this.fixPosY) / 2.0D, (this.posZ + this.fixPosZ) / 2.0D);
    } 
    if (!this.isRequestedSyncStatus) {
      this.isRequestedSyncStatus = true;
      if (this.world.isRemote)
        MCH_PacketStatusRequest.requestStatus(this); 
    } 
    if (this.lastRiddenByEntity == null && getRiddenByEntity() != null) {
      (getRiddenByEntity()).rotationPitch = 0.0F;
      (getRiddenByEntity()).prevRotationPitch = 0.0F;
      initCurrentWeapon(getRiddenByEntity());
    } 
    updateWeapons();
    onUpdate_Seats();
    onUpdate_Control();
    this.prevPosX = this.posX;
    this.prevPosY = this.posY;
    this.prevPosZ = this.posZ;
    if (isInWater())
      this.rotationPitch *= 0.9F; 
    if (this.world.isRemote) {
      onUpdate_Client();
    } else {
      onUpdate_Server();
    } 
  }
  
  protected void onUpdate_Control() {
    if (getRiddenByEntity() != null && !(getRiddenByEntity()).isDead) {
      if ((getVehicleInfo()).isEnableMove || (getVehicleInfo()).isEnableRot)
        onUpdate_ControlOnGround(); 
    } else if (getCurrentThrottle() > 0.0D) {
      addCurrentThrottle(-0.00125D);
    } else {
      setCurrentThrottle(0.0D);
    } 
    if (getCurrentThrottle() < 0.0D)
      setCurrentThrottle(0.0D); 
    if (this.world.isRemote) {
      if (!W_Lib.isClientPlayer(getRiddenByEntity())) {
        double ct = getThrottle();
        if (getCurrentThrottle() > ct)
          addCurrentThrottle(-0.005D); 
        if (getCurrentThrottle() < ct)
          addCurrentThrottle(0.005D); 
      } 
    } else {
      setThrottle(getCurrentThrottle());
    } 
  }
  
  protected void onUpdate_ControlOnGround() {
    if (!this.world.isRemote) {
      boolean move = false;
      float yaw = this.rotationYaw;
      double x = 0.0D;
      double z = 0.0D;
      if ((getVehicleInfo()).isEnableMove) {
        if (this.throttleUp) {
          yaw = this.rotationYaw;
          x += Math.sin(yaw * Math.PI / 180.0D);
          z += Math.cos(yaw * Math.PI / 180.0D);
          move = true;
        } 
        if (this.throttleDown) {
          yaw = this.rotationYaw - 180.0F;
          x += Math.sin(yaw * Math.PI / 180.0D);
          z += Math.cos(yaw * Math.PI / 180.0D);
          move = true;
        } 
      } 
      if ((getVehicleInfo()).isEnableMove) {
        if (this.moveLeft && !this.moveRight)
          this.rotationYaw = (float)(this.rotationYaw - 0.5D); 
        if (this.moveRight && !this.moveLeft)
          this.rotationYaw = (float)(this.rotationYaw + 0.5D); 
      } 
      if (move) {
        double d = Math.sqrt(x * x + z * z);
        this.motionX -= x / d * 0.029999999329447746D;
        this.motionZ += z / d * 0.029999999329447746D;
      } 
    } 
  }
  
  protected void onUpdate_Particle() {
    double particlePosY = this.posY;
    boolean b = false;
    int y;
    for (y = 0; y < 5 && !b; y++) {
      int x;
      for (x = -1; x <= 1; x++) {
        for (int z = -1; z <= 1; z++) {
          int block = W_WorldFunc.getBlockId(this.world, (int)(this.posX + 0.5D) + x, (int)(this.posY + 0.5D) - y, (int)(this.posZ + 0.5D) + z);
          if (block != 0 && !b) {
            particlePosY = ((int)(this.posY + 1.0D) - y);
            b = true;
          } 
        } 
      } 
      for (x = -3; b && x <= 3; x++) {
        for (int z = -3; z <= 3; z++) {
          if (W_WorldFunc.isBlockWater(this.world, (int)(this.posX + 0.5D) + x, (int)(this.posY + 0.5D) - y, (int)(this.posZ + 0.5D) + z))
            for (int i = 0; i < 7.0D * getCurrentThrottle(); i++)
              this.world.spawnParticle(EnumParticleTypes.WATER_SPLASH, this.posX + 0.5D + x + (this.rand
                  .nextDouble() - 0.5D) * 2.0D, particlePosY + this.rand
                  .nextDouble(), this.posZ + 0.5D + z + (this.rand
                  .nextDouble() - 0.5D) * 2.0D, x + (this.rand
                  .nextDouble() - 0.5D) * 2.0D, -0.3D, z + (this.rand
                  .nextDouble() - 0.5D) * 2.0D, new int[0]);  
        } 
      } 
    } 
    double pn = (5 - y + 1) / 5.0D;
    if (b)
      for (int k = 0; k < (int)(getCurrentThrottle() * 6.0D * pn); k++)
        this.world.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, this.posX + this.rand
            .nextDouble() - 0.5D, particlePosY + this.rand.nextDouble() - 0.5D, this.posZ + this.rand
            .nextDouble() - 0.5D, (this.rand.nextDouble() - 0.5D) * 2.0D, -0.4D, (this.rand
            .nextDouble() - 0.5D) * 2.0D, new int[0]);  
  }
  
  protected void onUpdate_Client() {
    updateCameraViewers();
    if (getRiddenByEntity() != null)
      if (W_Lib.isClientPlayer(getRiddenByEntity()))
        (getRiddenByEntity()).rotationPitch = (getRiddenByEntity()).prevRotationPitch;  
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
      if (this.onGround) {
        this.motionX *= 0.95D;
        this.motionZ *= 0.95D;
      } 
      if (isInWater()) {
        this.motionX *= 0.99D;
        this.motionZ *= 0.99D;
      } 
    } 
    if (getRiddenByEntity() != null);
    updateCamera(this.posX, this.posY, this.posZ);
  }
  
  private void onUpdate_Server() {
    double prevMotion = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
    updateCameraViewers();
    double dp = 0.0D;
    if (canFloatWater())
      dp = getWaterDepth(); 
    if (dp == 0.0D) {
      this.motionY += (!isInWater() ? (getAcInfo()).gravity : (getAcInfo()).gravityInWater);
    } else if (dp < 1.0D) {
      this.motionY -= 1.0E-4D;
      this.motionY += 0.007D * getCurrentThrottle();
    } else {
      if (this.motionY < 0.0D)
        this.motionY /= 2.0D; 
      this.motionY += 0.007D;
    } 
    double motion = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
    float speedLimit = (getAcInfo()).speed;
    if (motion > speedLimit) {
      this.motionX *= speedLimit / motion;
      this.motionZ *= speedLimit / motion;
      motion = speedLimit;
    } 
    if (motion > prevMotion && this.currentSpeed < speedLimit) {
      this.currentSpeed += (speedLimit - this.currentSpeed) / 35.0D;
      if (this.currentSpeed > speedLimit)
        this.currentSpeed = speedLimit; 
    } else {
      this.currentSpeed -= (this.currentSpeed - 0.07D) / 35.0D;
      if (this.currentSpeed < 0.07D)
        this.currentSpeed = 0.07D; 
    } 
    if (this.onGround) {
      this.motionX *= 0.5D;
      this.motionZ *= 0.5D;
    } 
    move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
    this.motionY *= 0.95D;
    this.motionX *= 0.99D;
    this.motionZ *= 0.99D;
    onUpdate_updateBlock();
    if (getRiddenByEntity() != null && (getRiddenByEntity()).isDead)
      unmountEntity(); 
  }
  
  public void onUpdateAngles(float partialTicks) {}
  
  public boolean canSwitchFreeLook() {
    return false;
  }
}
