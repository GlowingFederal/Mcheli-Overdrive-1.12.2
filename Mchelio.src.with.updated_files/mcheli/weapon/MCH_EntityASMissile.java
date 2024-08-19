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
  
  public void onUpdate() {
    super.onUpdate();
    if (getInfo() != null && !(getInfo()).disableSmoke && getBomblet() == 0)
      spawnParticle((getInfo()).trajectoryParticleName, 3, 10.0F * (getInfo()).smokeSize * 0.5F); 
    if (getInfo() != null && !this.world.isRemote && this.isBomblet != 1) {
      Block block = W_WorldFunc.getBlock(this.world, (int)this.targetPosX, (int)this.targetPosY, (int)this.targetPosZ);
      if (block != null && block.isCollidable()) {
        double dist = getDistance(this.targetPosX, this.targetPosY, this.targetPosZ);
        if (dist < (getInfo()).proximityFuseDist) {
          if ((getInfo()).bomblet > 0) {
            for (int i = 0; i < (getInfo()).bomblet; i++)
              sprinkleBomblet(); 
          } else {
            RayTraceResult mop = new RayTraceResult((Entity)this);
            onImpact(mop, 1.0F);
          } 
          setDead();
        } else if (getGravity() == 0.0D) {
          double up = 0.0D;
          if (getCountOnUpdate() < 10)
            up = 20.0D; 
          double x = this.targetPosX - this.posX;
          double y = this.targetPosY + up - this.posY;
          double z = this.targetPosZ - this.posZ;
          double d = MathHelper.sqrt(x * x + y * y + z * z);
          this.motionX = x * this.acceleration / d;
          this.motionY = y * this.acceleration / d;
          this.motionZ = z * this.acceleration / d;
        } else {
          double x = this.targetPosX - this.posX;
          double y = this.targetPosY - this.posY;
          y *= 0.3D;
          double z = this.targetPosZ - this.posZ;
          double d = MathHelper.sqrt(x * x + y * y + z * z);
          this.motionX = x * this.acceleration / d;
          this.motionZ = z * this.acceleration / d;
        } 
      } 
    } 
    double a = (float)Math.atan2(this.motionZ, this.motionX);
    this.rotationYaw = (float)(a * 180.0D / Math.PI) - 90.0F;
    double r = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
    this.rotationPitch = -((float)(Math.atan2(this.motionY, r) * 180.0D / Math.PI));
    onUpdateBomblet();
  }
  
  public void sprinkleBomblet() {
    if (!this.world.isRemote) {
      MCH_EntityASMissile e = new MCH_EntityASMissile(this.world, this.posX, this.posY, this.posZ, this.motionX, this.motionY, this.motionZ, this.rand.nextInt(360), 0.0F, this.acceleration);
      e.setParameterFromWeapon(this, this.shootingAircraft, this.shootingEntity);
      e.setName(getName());
      float RANDOM = (getInfo()).bombletDiff;
      e.motionX = this.motionX * 0.5D + ((this.rand.nextFloat() - 0.5F) * RANDOM);
      e.motionY = this.motionY * 0.5D / 2.0D + ((this.rand.nextFloat() - 0.5F) * RANDOM / 2.0F);
      e.motionZ = this.motionZ * 0.5D + ((this.rand.nextFloat() - 0.5F) * RANDOM);
      e.setBomblet();
      this.world.spawnEntityInWorld((Entity)e);
    } 
  }
  
  public MCH_BulletModel getDefaultBulletModel() {
    return MCH_DefaultBulletModels.ASMissile;
  }
}
