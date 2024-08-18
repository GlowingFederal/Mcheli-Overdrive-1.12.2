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
    this.field_70156_m = true;
    func_70105_a(2.0F, 0.7F);
    this.field_70159_w = 0.0D;
    this.field_70181_x = 0.0D;
    this.field_70179_y = 0.0D;
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
    MCH_Lib.DbgLog(this.field_70170_p, "MCH_EntityVehicle.changeType " + type + " : " + toString(), new Object[0]);
    if (!type.isEmpty())
      this.vehicleInfo = MCH_VehicleInfoManager.get(type); 
    if (this.vehicleInfo == null) {
      MCH_Lib.Log((Entity)this, "##### MCH_EntityVehicle changeVehicleType() Vehicle info null %d, %s, %s", new Object[] { Integer.valueOf(W_Entity.getEntityId((Entity)this)), type, getEntityName() });
      func_70106_y();
    } else {
      setAcInfo(this.vehicleInfo);
      newSeats(getAcInfo().getNumSeatAndRack());
      this.weapons = createWeapon(1 + getSeatNum());
      initPartRotation(this.field_70177_z, this.field_70125_A);
    } 
  }
  
  public boolean canMountWithNearEmptyMinecart() {
    return MCH_Config.MountMinecartVehicle.prmBool;
  }
  
  protected void func_70088_a() {
    super.func_70088_a();
  }
  
  protected void func_70014_b(NBTTagCompound par1NBTTagCompound) {
    super.func_70014_b(par1NBTTagCompound);
  }
  
  protected void func_70037_a(NBTTagCompound par1NBTTagCompound) {
    super.func_70037_a(par1NBTTagCompound);
    if (this.vehicleInfo == null) {
      this.vehicleInfo = MCH_VehicleInfoManager.get(getTypeName());
      if (this.vehicleInfo == null) {
        MCH_Lib.Log((Entity)this, "##### MCH_EntityVehicle readEntityFromNBT() Vehicle info null %d, %s", new Object[] { Integer.valueOf(W_Entity.getEntityId((Entity)this)), getEntityName() });
        func_70106_y();
      } else {
        setAcInfo(this.vehicleInfo);
      } 
    } 
  }
  
  public Item getItem() {
    return (getVehicleInfo() != null) ? (Item)(getVehicleInfo()).item : null;
  }
  
  public void func_70106_y() {
    super.func_70106_y();
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
    float breforeUseWeaponPitch = this.field_70125_A;
    float breforeUseWeaponYaw = this.field_70177_z;
    this.field_70125_A = prm.user.field_70125_A;
    this.field_70177_z = prm.user.field_70177_z;
    boolean result = super.useCurrentWeapon(prm);
    this.field_70125_A = breforeUseWeaponPitch;
    this.field_70177_z = breforeUseWeaponYaw;
    return result;
  }
  
  protected void mountWithNearEmptyMinecart() {
    if (!MCH_Config.FixVehicleAtPlacedPoint.prmBool)
      super.mountWithNearEmptyMinecart(); 
  }
  
  public void onUpdateAircraft() {
    if (this.vehicleInfo == null) {
      changeType(getTypeName());
      this.field_70169_q = this.field_70165_t;
      this.field_70167_r = this.field_70163_u;
      this.field_70166_s = this.field_70161_v;
      return;
    } 
    if (this.field_70173_aa < 200 || !MCH_Config.FixVehicleAtPlacedPoint.prmBool) {
      this.fixPosX = this.field_70165_t;
      this.fixPosY = this.field_70163_u;
      this.fixPosZ = this.field_70161_v;
    } else {
      func_184210_p();
      this.field_70159_w = 0.0D;
      this.field_70181_x = 0.0D;
      this.field_70179_y = 0.0D;
      if (this.field_70170_p.field_72995_K && this.field_70173_aa % 4 == 0)
        this.fixPosY = this.field_70163_u; 
      func_70107_b((this.field_70165_t + this.fixPosX) / 2.0D, (this.field_70163_u + this.fixPosY) / 2.0D, (this.field_70161_v + this.fixPosZ) / 2.0D);
    } 
    if (!this.isRequestedSyncStatus) {
      this.isRequestedSyncStatus = true;
      if (this.field_70170_p.field_72995_K)
        MCH_PacketStatusRequest.requestStatus(this); 
    } 
    if (this.lastRiddenByEntity == null && getRiddenByEntity() != null) {
      (getRiddenByEntity()).field_70125_A = 0.0F;
      (getRiddenByEntity()).field_70127_C = 0.0F;
      initCurrentWeapon(getRiddenByEntity());
    } 
    updateWeapons();
    onUpdate_Seats();
    onUpdate_Control();
    this.field_70169_q = this.field_70165_t;
    this.field_70167_r = this.field_70163_u;
    this.field_70166_s = this.field_70161_v;
    if (func_70090_H())
      this.field_70125_A *= 0.9F; 
    if (this.field_70170_p.field_72995_K) {
      onUpdate_Client();
    } else {
      onUpdate_Server();
    } 
  }
  
  protected void onUpdate_Control() {
    if (getRiddenByEntity() != null && !(getRiddenByEntity()).field_70128_L) {
      if ((getVehicleInfo()).isEnableMove || (getVehicleInfo()).isEnableRot)
        onUpdate_ControlOnGround(); 
    } else if (getCurrentThrottle() > 0.0D) {
      addCurrentThrottle(-0.00125D);
    } else {
      setCurrentThrottle(0.0D);
    } 
    if (getCurrentThrottle() < 0.0D)
      setCurrentThrottle(0.0D); 
    if (this.field_70170_p.field_72995_K) {
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
    if (!this.field_70170_p.field_72995_K) {
      boolean move = false;
      float yaw = this.field_70177_z;
      double x = 0.0D;
      double z = 0.0D;
      if ((getVehicleInfo()).isEnableMove) {
        if (this.throttleUp) {
          yaw = this.field_70177_z;
          x += Math.sin(yaw * Math.PI / 180.0D);
          z += Math.cos(yaw * Math.PI / 180.0D);
          move = true;
        } 
        if (this.throttleDown) {
          yaw = this.field_70177_z - 180.0F;
          x += Math.sin(yaw * Math.PI / 180.0D);
          z += Math.cos(yaw * Math.PI / 180.0D);
          move = true;
        } 
      } 
      if ((getVehicleInfo()).isEnableMove) {
        if (this.moveLeft && !this.moveRight)
          this.field_70177_z = (float)(this.field_70177_z - 0.5D); 
        if (this.moveRight && !this.moveLeft)
          this.field_70177_z = (float)(this.field_70177_z + 0.5D); 
      } 
      if (move) {
        double d = Math.sqrt(x * x + z * z);
        this.field_70159_w -= x / d * 0.029999999329447746D;
        this.field_70179_y += z / d * 0.029999999329447746D;
      } 
    } 
  }
  
  protected void onUpdate_Particle() {
    double particlePosY = this.field_70163_u;
    boolean b = false;
    int y;
    for (y = 0; y < 5 && !b; y++) {
      int x;
      for (x = -1; x <= 1; x++) {
        for (int z = -1; z <= 1; z++) {
          int block = W_WorldFunc.getBlockId(this.field_70170_p, (int)(this.field_70165_t + 0.5D) + x, (int)(this.field_70163_u + 0.5D) - y, (int)(this.field_70161_v + 0.5D) + z);
          if (block != 0 && !b) {
            particlePosY = ((int)(this.field_70163_u + 1.0D) - y);
            b = true;
          } 
        } 
      } 
      for (x = -3; b && x <= 3; x++) {
        for (int z = -3; z <= 3; z++) {
          if (W_WorldFunc.isBlockWater(this.field_70170_p, (int)(this.field_70165_t + 0.5D) + x, (int)(this.field_70163_u + 0.5D) - y, (int)(this.field_70161_v + 0.5D) + z))
            for (int i = 0; i < 7.0D * getCurrentThrottle(); i++)
              this.field_70170_p.func_175688_a(EnumParticleTypes.WATER_SPLASH, this.field_70165_t + 0.5D + x + (this.field_70146_Z
                  .nextDouble() - 0.5D) * 2.0D, particlePosY + this.field_70146_Z
                  .nextDouble(), this.field_70161_v + 0.5D + z + (this.field_70146_Z
                  .nextDouble() - 0.5D) * 2.0D, x + (this.field_70146_Z
                  .nextDouble() - 0.5D) * 2.0D, -0.3D, z + (this.field_70146_Z
                  .nextDouble() - 0.5D) * 2.0D, new int[0]);  
        } 
      } 
    } 
    double pn = (5 - y + 1) / 5.0D;
    if (b)
      for (int k = 0; k < (int)(getCurrentThrottle() * 6.0D * pn); k++)
        this.field_70170_p.func_175688_a(EnumParticleTypes.EXPLOSION_NORMAL, this.field_70165_t + this.field_70146_Z
            .nextDouble() - 0.5D, particlePosY + this.field_70146_Z.nextDouble() - 0.5D, this.field_70161_v + this.field_70146_Z
            .nextDouble() - 0.5D, (this.field_70146_Z.nextDouble() - 0.5D) * 2.0D, -0.4D, (this.field_70146_Z
            .nextDouble() - 0.5D) * 2.0D, new int[0]);  
  }
  
  protected void onUpdate_Client() {
    updateCameraViewers();
    if (getRiddenByEntity() != null)
      if (W_Lib.isClientPlayer(getRiddenByEntity()))
        (getRiddenByEntity()).field_70125_A = (getRiddenByEntity()).field_70127_C;  
    if (this.aircraftPosRotInc > 0) {
      double rpinc = this.aircraftPosRotInc;
      double yaw = MathHelper.func_76138_g(this.aircraftYaw - this.field_70177_z);
      this.field_70177_z = (float)(this.field_70177_z + yaw / rpinc);
      this.field_70125_A = (float)(this.field_70125_A + (this.aircraftPitch - this.field_70125_A) / rpinc);
      func_70107_b(this.field_70165_t + (this.aircraftX - this.field_70165_t) / rpinc, this.field_70163_u + (this.aircraftY - this.field_70163_u) / rpinc, this.field_70161_v + (this.aircraftZ - this.field_70161_v) / rpinc);
      func_70101_b(this.field_70177_z, this.field_70125_A);
      this.aircraftPosRotInc--;
    } else {
      func_70107_b(this.field_70165_t + this.field_70159_w, this.field_70163_u + this.field_70181_x, this.field_70161_v + this.field_70179_y);
      if (this.field_70122_E) {
        this.field_70159_w *= 0.95D;
        this.field_70179_y *= 0.95D;
      } 
      if (func_70090_H()) {
        this.field_70159_w *= 0.99D;
        this.field_70179_y *= 0.99D;
      } 
    } 
    if (getRiddenByEntity() != null);
    updateCamera(this.field_70165_t, this.field_70163_u, this.field_70161_v);
  }
  
  private void onUpdate_Server() {
    double prevMotion = Math.sqrt(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
    updateCameraViewers();
    double dp = 0.0D;
    if (canFloatWater())
      dp = getWaterDepth(); 
    if (dp == 0.0D) {
      this.field_70181_x += (!func_70090_H() ? (getAcInfo()).gravity : (getAcInfo()).gravityInWater);
    } else if (dp < 1.0D) {
      this.field_70181_x -= 1.0E-4D;
      this.field_70181_x += 0.007D * getCurrentThrottle();
    } else {
      if (this.field_70181_x < 0.0D)
        this.field_70181_x /= 2.0D; 
      this.field_70181_x += 0.007D;
    } 
    double motion = Math.sqrt(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
    float speedLimit = (getAcInfo()).speed;
    if (motion > speedLimit) {
      this.field_70159_w *= speedLimit / motion;
      this.field_70179_y *= speedLimit / motion;
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
    if (this.field_70122_E) {
      this.field_70159_w *= 0.5D;
      this.field_70179_y *= 0.5D;
    } 
    func_70091_d(MoverType.SELF, this.field_70159_w, this.field_70181_x, this.field_70179_y);
    this.field_70181_x *= 0.95D;
    this.field_70159_w *= 0.99D;
    this.field_70179_y *= 0.99D;
    onUpdate_updateBlock();
    if (getRiddenByEntity() != null && (getRiddenByEntity()).field_70128_L)
      unmountEntity(); 
  }
  
  public void onUpdateAngles(float partialTicks) {}
  
  public boolean canSwitchFreeLook() {
    return false;
  }
}
