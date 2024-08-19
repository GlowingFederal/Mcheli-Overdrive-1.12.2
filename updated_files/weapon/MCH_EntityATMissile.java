package mcheli.weapon;

import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class MCH_EntityATMissile extends MCH_EntityBaseBullet {
  public int guidanceType;
  
  public MCH_EntityATMissile(World par1World) {
    super(par1World);
    this.guidanceType = 0;
  }
  
  public MCH_EntityATMissile(World par1World, double posX, double posY, double posZ, double targetX, double targetY, double targetZ, float yaw, float pitch, double acceleration) {
    super(par1World, posX, posY, posZ, targetX, targetY, targetZ, yaw, pitch, acceleration);
    this.guidanceType = 0;
  }
  
  public void onUpdate() {
    super.onUpdate();
    if (getInfo() != null && !(getInfo()).disableSmoke && this.ticksExisted >= (getInfo()).trajectoryParticleStartTick)
      spawnParticle((getInfo()).trajectoryParticleName, 3, 5.0F * (getInfo()).smokeSize * 0.5F); 
    if (!this.world.isRemote)
      if (this.shootingEntity != null && this.targetEntity != null && !this.targetEntity.isDead) {
        if (usingFlareOfTarget(this.targetEntity)) {
          setDead();
          return;
        } 
        onUpdateMotion();
      } else {
        setDead();
      }  
    double a = (float)Math.atan2(this.motionZ, this.motionX);
    this.rotationYaw = (float)(a * 180.0D / Math.PI) - 90.0F;
    double r = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
    this.rotationPitch = -((float)(Math.atan2(this.motionY, r) * 180.0D / Math.PI));
  }
  
  public void onUpdateMotion() {
    double x = this.targetEntity.posX - this.posX;
    double y = this.targetEntity.posY - this.posY;
    double z = this.targetEntity.posZ - this.posZ;
    double d = x * x + y * y + z * z;
    if (d > 2250000.0D || this.targetEntity.isDead) {
      setDead();
    } else if ((getInfo()).proximityFuseDist >= 0.1F && d < (getInfo()).proximityFuseDist) {
      RayTraceResult mop = new RayTraceResult(this.targetEntity);
      mop.entityHit = null;
      onImpact(mop, 1.0F);
    } else {
      int rigidityTime = (getInfo()).rigidityTime;
      float af = (getCountOnUpdate() < rigidityTime + (getInfo()).trajectoryParticleStartTick) ? 0.5F : 1.0F;
      if (getCountOnUpdate() > rigidityTime)
        if (this.guidanceType == 1) {
          if (getCountOnUpdate() <= rigidityTime + 20) {
            guidanceToTarget(this.targetEntity.posX, this.shootingEntity.posY + 150.0D, this.targetEntity.posZ, af);
          } else if (getCountOnUpdate() <= rigidityTime + 30) {
            guidanceToTarget(this.targetEntity.posX, this.shootingEntity.posY, this.targetEntity.posZ, af);
          } else {
            if (getCountOnUpdate() == rigidityTime + 35) {
              setPower((int)(getPower() * 1.2F));
              if (this.explosionPower > 0)
                this.explosionPower++; 
            } 
            guidanceToTarget(this.targetEntity.posX, this.targetEntity.posY, this.targetEntity.posZ, af);
          } 
        } else {
          d = MathHelper.sqrt(d);
          this.motionX = x * this.acceleration / d * af;
          this.motionY = y * this.acceleration / d * af;
          this.motionZ = z * this.acceleration / d * af;
        }  
    } 
  }
  
  public MCH_BulletModel getDefaultBulletModel() {
    return MCH_DefaultBulletModels.ATMissile;
  }
}
