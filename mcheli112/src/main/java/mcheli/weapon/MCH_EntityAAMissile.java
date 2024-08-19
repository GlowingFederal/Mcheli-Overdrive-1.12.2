package mcheli.weapon;

import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class MCH_EntityAAMissile extends MCH_EntityBaseBullet {
  public MCH_EntityAAMissile(World par1World) {
    super(par1World);
    this.targetEntity = null;
  }
  
  public MCH_EntityAAMissile(World par1World, double posX, double posY, double posZ, double targetX, double targetY, double targetZ, float yaw, float pitch, double acceleration) {
    super(par1World, posX, posY, posZ, targetX, targetY, targetZ, yaw, pitch, acceleration);
  }
  
  public void onUpdate() {
    super.onUpdate();
    if (getCountOnUpdate() > 4)
      if (getInfo() != null && !(getInfo()).disableSmoke)
        spawnParticle((getInfo()).trajectoryParticleName, 3, 7.0F * (getInfo()).smokeSize * 0.5F);  
    if (!this.world.isRemote && getInfo() != null)
      if (this.shootingEntity != null && this.targetEntity != null && !this.targetEntity.isDead) {
        double x = this.posX - this.targetEntity.posX;
        double y = this.posY - this.targetEntity.posY;
        double z = this.posZ - this.targetEntity.posZ;
        double d = x * x + y * y + z * z;
        if (d > 3422500.0D) {
          setDead();
        } else if (getCountOnUpdate() > (getInfo()).rigidityTime) {
          if (usingFlareOfTarget(this.targetEntity)) {
            setDead();
            return;
          } 
          if ((getInfo()).proximityFuseDist >= 0.1F && d < (getInfo()).proximityFuseDist) {
            RayTraceResult mop = new RayTraceResult(this.targetEntity);
            this.posX = (this.targetEntity.posX + this.posX) / 2.0D;
            this.posY = (this.targetEntity.posY + this.posY) / 2.0D;
            this.posZ = (this.targetEntity.posZ + this.posZ) / 2.0D;
            onImpact(mop, 1.0F);
          } else {
            guidanceToTarget(this.targetEntity.posX, this.targetEntity.posY, this.targetEntity.posZ);
          } 
        } 
      } else {
        setDead();
      }  
  }
  
  public MCH_BulletModel getDefaultBulletModel() {
    return MCH_DefaultBulletModels.AAMissile;
  }
}
