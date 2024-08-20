package mcheli.weapon;

import java.util.List;
import mcheli.MCH_Config;
import mcheli.MCH_Lib;
import mcheli.wrapper.W_MovingObjectPosition;
import mcheli.wrapper.W_WorldFunc;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class MCH_EntityBullet extends MCH_EntityBaseBullet {
  public MCH_EntityBullet(World par1World) {
    super(par1World);
  }
  
  public MCH_EntityBullet(World par1World, double pX, double pY, double pZ, double targetX, double targetY, double targetZ, float yaw, float pitch, double acceleration) {
    super(par1World, pX, pY, pZ, targetX, targetY, targetZ, yaw, pitch, acceleration);
  }
  
  public void onUpdate() {
    super.onUpdate();
    if (!this.isDead && !this.world.isRemote && getCountOnUpdate() > 1 && getInfo() != null && this.explosionPower > 0) {
      float pDist = (getInfo()).proximityFuseDist;
      if (pDist > 0.1D) {
        pDist++;
        float rng = pDist + MathHelper.abs((getInfo()).acceleration);
        List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity((Entity)this, 
            getEntityBoundingBox().expand(rng, rng, rng));
        for (int i = 0; i < list.size(); i++) {
          Entity entity1 = list.get(i);
          if (canBeCollidedEntity(entity1) && entity1.getDistanceSqToEntity((Entity)this) < (pDist * pDist)) {
            MCH_Lib.DbgLog(this.world, "MCH_EntityBullet.onUpdate:proximityFuse:" + entity1, new Object[0]);
            this.posX = (entity1.posX + this.posX) / 2.0D;
            this.posY = (entity1.posY + this.posY) / 2.0D;
            this.posZ = (entity1.posZ + this.posZ) / 2.0D;
            RayTraceResult mop = W_MovingObjectPosition.newMOP((int)this.posX, (int)this.posY, (int)this.posZ, 0, 
                W_WorldFunc.getWorldVec3EntityPos((Entity)this), false);
            onImpact(mop, 1.0F);
            break;
          } 
        } 
      } 
    } 
  }
  
  protected void onUpdateCollided() {
    double mx = this.motionX * this.accelerationFactor;
    double my = this.motionY * this.accelerationFactor;
    double mz = this.motionZ * this.accelerationFactor;
    float damageFactor = 1.0F;
    RayTraceResult m = null;
    for (int i = 0; i < 5; i++) {
      Vec3d vec3d1 = W_WorldFunc.getWorldVec3(this.world, this.posX, this.posY, this.posZ);
      Vec3d vec3d2 = W_WorldFunc.getWorldVec3(this.world, this.posX + mx, this.posY + my, this.posZ + mz);
      m = W_WorldFunc.clip(this.world, vec3d1, vec3d2);
      boolean continueClip = false;
      if (this.shootingEntity != null && W_MovingObjectPosition.isHitTypeTile(m)) {
        Block block = W_WorldFunc.getBlock(this.world, m.getBlockPos());
        if (MCH_Config.bulletBreakableBlocks.contains(block)) {
          W_WorldFunc.destroyBlock(this.world, m.getBlockPos(), true);
          continueClip = true;
        } 
      } 
      if (!continueClip)
        break; 
    } 
    Vec3d vec3 = W_WorldFunc.getWorldVec3(this.world, this.posX, this.posY, this.posZ);
    Vec3d vec31 = W_WorldFunc.getWorldVec3(this.world, this.posX + mx, this.posY + my, this.posZ + mz);
    if ((getInfo()).delayFuse > 0) {
      if (m != null) {
        boundBullet(m.sideHit);
        if (this.delayFuse == 0)
          this.delayFuse = (getInfo()).delayFuse; 
      } 
      return;
    } 
    if (m != null)
      vec31 = W_WorldFunc.getWorldVec3(this.world, m.hitVec.x, m.hitVec.y, m.hitVec.z); 
    Entity entity = null;
    List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity((Entity)this, 
        getEntityBoundingBox().addCoord(mx, my, mz).expand(21.0D, 21.0D, 21.0D));
    double d0 = 0.0D;
    for (int j = 0; j < list.size(); j++) {
      Entity entity1 = list.get(j);
      if (canBeCollidedEntity(entity1)) {
        float f = 0.3F;
        AxisAlignedBB axisalignedbb = entity1.getEntityBoundingBox().expand(f, f, f);
        RayTraceResult m1 = axisalignedbb.calculateIntercept(vec3, vec31);
        if (m1 != null) {
          double d1 = vec3.distanceTo(m1.hitVec);
          if (d1 < d0 || d0 == 0.0D) {
            entity = entity1;
            d0 = d1;
          } 
        } 
      } 
    } 
    if (entity != null)
      m = new RayTraceResult(entity); 
    if (m != null)
      onImpact(m, damageFactor); 
  }
  
  public MCH_BulletModel getDefaultBulletModel() {
    return MCH_DefaultBulletModels.Bullet;
  }
}
