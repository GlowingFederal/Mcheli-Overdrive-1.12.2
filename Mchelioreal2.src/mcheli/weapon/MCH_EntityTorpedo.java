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
  
  public void func_70071_h_() {
    super.func_70071_h_();
    if (getInfo() != null && (getInfo()).isGuidedTorpedo) {
      onUpdateGuided();
    } else {
      onUpdateNoGuided();
    } 
    if (func_70090_H())
      if (getInfo() != null && !(getInfo()).disableSmoke)
        spawnParticle((getInfo()).trajectoryParticleName, 3, 5.0F * (getInfo()).smokeSize * 0.5F);  
  }
  
  private void onUpdateNoGuided() {
    if (!this.field_70170_p.field_72995_K)
      if (func_70090_H()) {
        this.field_70181_x *= 0.800000011920929D;
        if (this.acceleration < this.accelerationInWater) {
          this.acceleration += 0.1D;
        } else if (this.acceleration > this.accelerationInWater + 0.20000000298023224D) {
          this.acceleration -= 0.1D;
        } 
        double x = this.field_70159_w;
        double y = this.field_70181_x;
        double z = this.field_70179_y;
        double d = MathHelper.func_76133_a(x * x + y * y + z * z);
        this.field_70159_w = x * this.acceleration / d;
        this.field_70181_x = y * this.acceleration / d;
        this.field_70179_y = z * this.acceleration / d;
      }  
    if (func_70090_H()) {
      double a = (float)Math.atan2(this.field_70179_y, this.field_70159_w);
      this.field_70177_z = (float)(a * 180.0D / Math.PI) - 90.0F;
    } 
  }
  
  private void onUpdateGuided() {
    if (!this.field_70170_p.field_72995_K)
      if (func_70090_H()) {
        if (this.acceleration < this.accelerationInWater) {
          this.acceleration += 0.1D;
        } else if (this.acceleration > this.accelerationInWater + 0.20000000298023224D) {
          this.acceleration -= 0.1D;
        } 
        double x = this.targetPosX - this.field_70165_t;
        double y = this.targetPosY - this.field_70163_u;
        double z = this.targetPosZ - this.field_70161_v;
        double d = MathHelper.func_76133_a(x * x + y * y + z * z);
        this.field_70159_w = x * this.acceleration / d;
        this.field_70181_x = y * this.acceleration / d;
        this.field_70179_y = z * this.acceleration / d;
      }  
    if (func_70090_H()) {
      double a = (float)Math.atan2(this.field_70179_y, this.field_70159_w);
      this.field_70177_z = (float)(a * 180.0D / Math.PI) - 90.0F;
      double r = Math.sqrt(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
      this.field_70125_A = -((float)(Math.atan2(this.field_70181_x, r) * 180.0D / Math.PI));
    } 
  }
  
  public MCH_BulletModel getDefaultBulletModel() {
    return MCH_DefaultBulletModels.Torpedo;
  }
}
