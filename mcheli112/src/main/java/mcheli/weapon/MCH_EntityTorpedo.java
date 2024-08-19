package mcheli.weapon;

import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class MCH_EntityTorpedo extends MCH_EntityBaseBullet {
  public double targetPosX;
  
  public double targetPosY;
  
  public double targetPosZ;
  
  public double accelerationInWater = 2.0D;
  
  public MCH_EntityTorpedo(World par1World) {
    super(par1World);
    this.targetPosX = 0.0D;
    this.targetPosY = 0.0D;
    this.targetPosZ = 0.0D;
  }
  
  public MCH_EntityTorpedo(World par1World, double posX, double posY, double posZ, double targetX, double targetY, double targetZ, float yaw, float pitch, double acceleration) {
    super(par1World, posX, posY, posZ, targetX, targetY, targetZ, yaw, pitch, acceleration);
  }
  
  public void onUpdate() {
    super.onUpdate();
    if (getInfo() != null && (getInfo()).isGuidedTorpedo) {
      onUpdateGuided();
    } else {
      onUpdateNoGuided();
    } 
    if (isInWater())
      if (getInfo() != null && !(getInfo()).disableSmoke)
        spawnParticle((getInfo()).trajectoryParticleName, 3, 5.0F * (getInfo()).smokeSize * 0.5F);  
  }
  
  private void onUpdateNoGuided() {
    if (!this.world.isRemote)
      if (isInWater()) {
        this.motionY *= 0.800000011920929D;
        if (this.acceleration < this.accelerationInWater) {
          this.acceleration += 0.1D;
        } else if (this.acceleration > this.accelerationInWater + 0.20000000298023224D) {
          this.acceleration -= 0.1D;
        } 
        double x = this.motionX;
        double y = this.motionY;
        double z = this.motionZ;
        double d = MathHelper.sqrt(x * x + y * y + z * z);
        this.motionX = x * this.acceleration / d;
        this.motionY = y * this.acceleration / d;
        this.motionZ = z * this.acceleration / d;
      }  
    if (isInWater()) {
      double a = (float)Math.atan2(this.motionZ, this.motionX);
      this.rotationYaw = (float)(a * 180.0D / Math.PI) - 90.0F;
    } 
  }
  
  private void onUpdateGuided() {
    if (!this.world.isRemote)
      if (isInWater()) {
        if (this.acceleration < this.accelerationInWater) {
          this.acceleration += 0.1D;
        } else if (this.acceleration > this.accelerationInWater + 0.20000000298023224D) {
          this.acceleration -= 0.1D;
        } 
        double x = this.targetPosX - this.posX;
        double y = this.targetPosY - this.posY;
        double z = this.targetPosZ - this.posZ;
        double d = MathHelper.sqrt(x * x + y * y + z * z);
        this.motionX = x * this.acceleration / d;
        this.motionY = y * this.acceleration / d;
        this.motionZ = z * this.acceleration / d;
      }  
    if (isInWater()) {
      double a = (float)Math.atan2(this.motionZ, this.motionX);
      this.rotationYaw = (float)(a * 180.0D / Math.PI) - 90.0F;
      double r = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
      this.rotationPitch = -((float)(Math.atan2(this.motionY, r) * 180.0D / Math.PI));
    } 
  }
  
  public MCH_BulletModel getDefaultBulletModel() {
    return MCH_DefaultBulletModels.Torpedo;
  }
}
