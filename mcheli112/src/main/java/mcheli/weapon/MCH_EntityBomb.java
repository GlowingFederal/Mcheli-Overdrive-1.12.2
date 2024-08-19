package mcheli.weapon;

import java.util.List;
import mcheli.wrapper.W_Lib;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class MCH_EntityBomb extends MCH_EntityBaseBullet {
  public MCH_EntityBomb(World par1World) {
    super(par1World);
  }
  
  public MCH_EntityBomb(World par1World, double posX, double posY, double posZ, double targetX, double targetY, double targetZ, float yaw, float pitch, double acceleration) {
    super(par1World, posX, posY, posZ, targetX, targetY, targetZ, yaw, pitch, acceleration);
  }
  
  public void onUpdate() {
    super.onUpdate();
    if (!this.world.isRemote && getInfo() != null) {
      this.motionX *= 0.999D;
      this.motionZ *= 0.999D;
      if (isInWater()) {
        this.motionX *= (getInfo()).velocityInWater;
        this.motionY *= (getInfo()).velocityInWater;
        this.motionZ *= (getInfo()).velocityInWater;
      } 
      float dist = (getInfo()).proximityFuseDist;
      if (dist > 0.1F && getCountOnUpdate() % 10 == 0) {
        List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity((Entity)this, 
            getEntityBoundingBox().expand(dist, dist, dist));
        if (list != null)
          for (int i = 0; i < list.size(); i++) {
            Entity entity = list.get(i);
            if (W_Lib.isEntityLivingBase(entity) && canBeCollidedEntity(entity)) {
              RayTraceResult m = new RayTraceResult(new Vec3d(this.posX, this.posY, this.posZ), EnumFacing.DOWN, new BlockPos(this.posX + 0.5D, this.posY + 0.5D, this.posZ + 0.5D));
              onImpact(m, 1.0F);
              break;
            } 
          }  
      } 
    } 
    onUpdateBomblet();
  }
  
  public void sprinkleBomblet() {
    if (!this.world.isRemote) {
      MCH_EntityBomb e = new MCH_EntityBomb(this.world, this.posX, this.posY, this.posZ, this.motionX, this.motionY, this.motionZ, this.rand.nextInt(360), 0.0F, this.acceleration);
      e.setParameterFromWeapon(this, this.shootingAircraft, this.shootingEntity);
      e.setName(getName());
      float RANDOM = (getInfo()).bombletDiff;
      e.motionX = this.motionX * 1.0D + ((this.rand.nextFloat() - 0.5F) * RANDOM);
      e.motionY = this.motionY * 1.0D / 2.0D + ((this.rand.nextFloat() - 0.5F) * RANDOM / 2.0F);
      e.motionZ = this.motionZ * 1.0D + ((this.rand.nextFloat() - 0.5F) * RANDOM);
      e.setBomblet();
      this.world.spawnEntityInWorld((Entity)e);
    } 
  }
  
  public MCH_BulletModel getDefaultBulletModel() {
    return MCH_DefaultBulletModels.Bomb;
  }
}
