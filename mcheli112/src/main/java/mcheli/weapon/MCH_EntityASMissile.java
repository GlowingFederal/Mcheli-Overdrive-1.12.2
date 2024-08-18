package mcheli.weapon;

import mcheli.wrapper.W_WorldFunc;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class MCH_EntityASMissile extends MCH_EntityBaseBullet {
  public double targetPosX;
  
  public double targetPosY;
  
  public double targetPosZ;
  
  public MCH_EntityASMissile(World par1World) {
    super(par1World);
    this.targetPosX = 0.0D;
    this.targetPosY = 0.0D;
    this.targetPosZ = 0.0D;
  }
  
  public MCH_EntityASMissile(World par1World, double posX, double posY, double posZ, double targetX, double targetY, double targetZ, float yaw, float pitch, double acceleration) {
    super(par1World, posX, posY, posZ, targetX, targetY, targetZ, yaw, pitch, acceleration);
  }
  
  public float getGravity() {
    if (getBomblet() == 1)
      return -0.03F; 
    return super.getGravity();
  }
  
  public float getGravityInWater() {
    if (getBomblet() == 1)
      return -0.03F; 
    return super.getGravityInWater();
  }
  
  public void func_70071_h_() {
    super.func_70071_h_();
    if (getInfo() != null && !(getInfo()).disableSmoke && getBomblet() == 0)
      spawnParticle((getInfo()).trajectoryParticleName, 3, 10.0F * (getInfo()).smokeSize * 0.5F); 
    if (getInfo() != null && !this.field_70170_p.field_72995_K && this.isBomblet != 1) {
      Block block = W_WorldFunc.getBlock(this.field_70170_p, (int)this.targetPosX, (int)this.targetPosY, (int)this.targetPosZ);
      if (block != null && block.func_149703_v()) {
        double dist = func_70011_f(this.targetPosX, this.targetPosY, this.targetPosZ);
        if (dist < (getInfo()).proximityFuseDist) {
          if ((getInfo()).bomblet > 0) {
            for (int i = 0; i < (getInfo()).bomblet; i++)
              sprinkleBomblet(); 
          } else {
            RayTraceResult mop = new RayTraceResult((Entity)this);
            onImpact(mop, 1.0F);
          } 
          func_70106_y();
        } else if (getGravity() == 0.0D) {
          double up = 0.0D;
          if (getCountOnUpdate() < 10)
            up = 20.0D; 
          double x = this.targetPosX - this.field_70165_t;
          double y = this.targetPosY + up - this.field_70163_u;
          double z = this.targetPosZ - this.field_70161_v;
          double d = MathHelper.func_76133_a(x * x + y * y + z * z);
          this.field_70159_w = x * this.acceleration / d;
          this.field_70181_x = y * this.acceleration / d;
          this.field_70179_y = z * this.acceleration / d;
        } else {
          double x = this.targetPosX - this.field_70165_t;
          double y = this.targetPosY - this.field_70163_u;
          y *= 0.3D;
          double z = this.targetPosZ - this.field_70161_v;
          double d = MathHelper.func_76133_a(x * x + y * y + z * z);
          this.field_70159_w = x * this.acceleration / d;
          this.field_70179_y = z * this.acceleration / d;
        } 
      } 
    } 
    double a = (float)Math.atan2(this.field_70179_y, this.field_70159_w);
    this.field_70177_z = (float)(a * 180.0D / Math.PI) - 90.0F;
    double r = Math.sqrt(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
    this.field_70125_A = -((float)(Math.atan2(this.field_70181_x, r) * 180.0D / Math.PI));
    onUpdateBomblet();
  }
  
  public void sprinkleBomblet() {
    if (!this.field_70170_p.field_72995_K) {
      MCH_EntityASMissile e = new MCH_EntityASMissile(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v, this.field_70159_w, this.field_70181_x, this.field_70179_y, this.field_70146_Z.nextInt(360), 0.0F, this.acceleration);
      e.setParameterFromWeapon(this, this.shootingAircraft, this.shootingEntity);
      e.setName(func_70005_c_());
      float RANDOM = (getInfo()).bombletDiff;
      e.field_70159_w = this.field_70159_w * 0.5D + ((this.field_70146_Z.nextFloat() - 0.5F) * RANDOM);
      e.field_70181_x = this.field_70181_x * 0.5D / 2.0D + ((this.field_70146_Z.nextFloat() - 0.5F) * RANDOM / 2.0F);
      e.field_70179_y = this.field_70179_y * 0.5D + ((this.field_70146_Z.nextFloat() - 0.5F) * RANDOM);
      e.setBomblet();
      this.field_70170_p.func_72838_d((Entity)e);
    } 
  }
  
  public MCH_BulletModel getDefaultBulletModel() {
    return MCH_DefaultBulletModels.ASMissile;
  }
}
