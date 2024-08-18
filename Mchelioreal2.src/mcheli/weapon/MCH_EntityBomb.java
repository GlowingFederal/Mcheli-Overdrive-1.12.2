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
  
  public void func_70071_h_() {
    super.func_70071_h_();
    if (!this.field_70170_p.field_72995_K && getInfo() != null) {
      this.field_70159_w *= 0.999D;
      this.field_70179_y *= 0.999D;
      if (func_70090_H()) {
        this.field_70159_w *= (getInfo()).velocityInWater;
        this.field_70181_x *= (getInfo()).velocityInWater;
        this.field_70179_y *= (getInfo()).velocityInWater;
      } 
      float dist = (getInfo()).proximityFuseDist;
      if (dist > 0.1F && getCountOnUpdate() % 10 == 0) {
        List<Entity> list = this.field_70170_p.func_72839_b((Entity)this, 
            func_174813_aQ().func_72314_b(dist, dist, dist));
        if (list != null)
          for (int i = 0; i < list.size(); i++) {
            Entity entity = list.get(i);
            if (W_Lib.isEntityLivingBase(entity) && canBeCollidedEntity(entity)) {
              RayTraceResult m = new RayTraceResult(new Vec3d(this.field_70165_t, this.field_70163_u, this.field_70161_v), EnumFacing.DOWN, new BlockPos(this.field_70165_t + 0.5D, this.field_70163_u + 0.5D, this.field_70161_v + 0.5D));
              onImpact(m, 1.0F);
              break;
            } 
          }  
      } 
    } 
    onUpdateBomblet();
  }
  
  public void sprinkleBomblet() {
    if (!this.field_70170_p.field_72995_K) {
      MCH_EntityBomb e = new MCH_EntityBomb(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v, this.field_70159_w, this.field_70181_x, this.field_70179_y, this.field_70146_Z.nextInt(360), 0.0F, this.acceleration);
      e.setParameterFromWeapon(this, this.shootingAircraft, this.shootingEntity);
      e.setName(func_70005_c_());
      float RANDOM = (getInfo()).bombletDiff;
      e.field_70159_w = this.field_70159_w * 1.0D + ((this.field_70146_Z.nextFloat() - 0.5F) * RANDOM);
      e.field_70181_x = this.field_70181_x * 1.0D / 2.0D + ((this.field_70146_Z.nextFloat() - 0.5F) * RANDOM / 2.0F);
      e.field_70179_y = this.field_70179_y * 1.0D + ((this.field_70146_Z.nextFloat() - 0.5F) * RANDOM);
      e.setBomblet();
      this.field_70170_p.func_72838_d((Entity)e);
    } 
  }
  
  public MCH_BulletModel getDefaultBulletModel() {
    return MCH_DefaultBulletModels.Bomb;
  }
}
