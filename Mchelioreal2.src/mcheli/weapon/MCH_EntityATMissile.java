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
  
  public void func_70071_h_() {
    super.func_70071_h_();
    if (getInfo() != null && !(getInfo()).disableSmoke && this.field_70173_aa >= (getInfo()).trajectoryParticleStartTick)
      spawnParticle((getInfo()).trajectoryParticleName, 3, 5.0F * (getInfo()).smokeSize * 0.5F); 
    if (!this.field_70170_p.field_72995_K)
      if (this.shootingEntity != null && this.targetEntity != null && !this.targetEntity.field_70128_L) {
        if (usingFlareOfTarget(this.targetEntity)) {
          func_70106_y();
          return;
        } 
        onUpdateMotion();
      } else {
        func_70106_y();
      }  
    double a = (float)Math.atan2(this.field_70179_y, this.field_70159_w);
    this.field_70177_z = (float)(a * 180.0D / Math.PI) - 90.0F;
    double r = Math.sqrt(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
    this.field_70125_A = -((float)(Math.atan2(this.field_70181_x, r) * 180.0D / Math.PI));
  }
  
  public void onUpdateMotion() {
    double x = this.targetEntity.field_70165_t - this.field_70165_t;
    double y = this.targetEntity.field_70163_u - this.field_70163_u;
    double z = this.targetEntity.field_70161_v - this.field_70161_v;
    double d = x * x + y * y + z * z;
    if (d > 2250000.0D || this.targetEntity.field_70128_L) {
      func_70106_y();
    } else if ((getInfo()).proximityFuseDist >= 0.1F && d < (getInfo()).proximityFuseDist) {
      RayTraceResult mop = new RayTraceResult(this.targetEntity);
      mop.field_72308_g = null;
      onImpact(mop, 1.0F);
    } else {
      int rigidityTime = (getInfo()).rigidityTime;
      float af = (getCountOnUpdate() < rigidityTime + (getInfo()).trajectoryParticleStartTick) ? 0.5F : 1.0F;
      if (getCountOnUpdate() > rigidityTime)
        if (this.guidanceType == 1) {
          if (getCountOnUpdate() <= rigidityTime + 20) {
            guidanceToTarget(this.targetEntity.field_70165_t, this.shootingEntity.field_70163_u + 150.0D, this.targetEntity.field_70161_v, af);
          } else if (getCountOnUpdate() <= rigidityTime + 30) {
            guidanceToTarget(this.targetEntity.field_70165_t, this.shootingEntity.field_70163_u, this.targetEntity.field_70161_v, af);
          } else {
            if (getCountOnUpdate() == rigidityTime + 35) {
              setPower((int)(getPower() * 1.2F));
              if (this.explosionPower > 0)
                this.explosionPower++; 
            } 
            guidanceToTarget(this.targetEntity.field_70165_t, this.targetEntity.field_70163_u, this.targetEntity.field_70161_v, af);
          } 
        } else {
          d = MathHelper.func_76133_a(d);
          this.field_70159_w = x * this.acceleration / d * af;
          this.field_70181_x = y * this.acceleration / d * af;
          this.field_70179_y = z * this.acceleration / d * af;
        }  
    } 
  }
  
  public MCH_BulletModel getDefaultBulletModel() {
    return MCH_DefaultBulletModels.ATMissile;
  }
}
