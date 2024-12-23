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
  
  public void func_70071_h_() {
    super.func_70071_h_();
    if (!this.field_70128_L && !this.field_70170_p.field_72995_K && getCountOnUpdate() > 1 && getInfo() != null && this.explosionPower > 0) {
      float pDist = (getInfo()).proximityFuseDist;
      if (pDist > 0.1D) {
        pDist++;
        float rng = pDist + MathHelper.func_76135_e((getInfo()).acceleration);
        List<Entity> list = this.field_70170_p.func_72839_b((Entity)this, 
            func_174813_aQ().func_72314_b(rng, rng, rng));
        for (int i = 0; i < list.size(); i++) {
          Entity entity1 = list.get(i);
          if (canBeCollidedEntity(entity1) && entity1.func_70068_e((Entity)this) < (pDist * pDist)) {
            MCH_Lib.DbgLog(this.field_70170_p, "MCH_EntityBullet.onUpdate:proximityFuse:" + entity1, new Object[0]);
            this.field_70165_t = (entity1.field_70165_t + this.field_70165_t) / 2.0D;
            this.field_70163_u = (entity1.field_70163_u + this.field_70163_u) / 2.0D;
            this.field_70161_v = (entity1.field_70161_v + this.field_70161_v) / 2.0D;
            RayTraceResult mop = W_MovingObjectPosition.newMOP((int)this.field_70165_t, (int)this.field_70163_u, (int)this.field_70161_v, 0, 
                W_WorldFunc.getWorldVec3EntityPos((Entity)this), false);
            onImpact(mop, 1.0F);
            break;
          } 
        } 
      } 
    } 
  }
  
  protected void onUpdateCollided() {
    double mx = this.field_70159_w * this.accelerationFactor;
    double my = this.field_70181_x * this.accelerationFactor;
    double mz = this.field_70179_y * this.accelerationFactor;
    float damageFactor = 1.0F;
    RayTraceResult m = null;
    for (int i = 0; i < 5; i++) {
      Vec3d vec3d1 = W_WorldFunc.getWorldVec3(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v);
      Vec3d vec3d2 = W_WorldFunc.getWorldVec3(this.field_70170_p, this.field_70165_t + mx, this.field_70163_u + my, this.field_70161_v + mz);
      m = W_WorldFunc.clip(this.field_70170_p, vec3d1, vec3d2);
      boolean continueClip = false;
      if (this.shootingEntity != null && W_MovingObjectPosition.isHitTypeTile(m)) {
        Block block = W_WorldFunc.getBlock(this.field_70170_p, m.func_178782_a());
        if (MCH_Config.bulletBreakableBlocks.contains(block)) {
          W_WorldFunc.destroyBlock(this.field_70170_p, m.func_178782_a(), true);
          continueClip = true;
        } 
      } 
      if (!continueClip)
        break; 
    } 
    Vec3d vec3 = W_WorldFunc.getWorldVec3(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v);
    Vec3d vec31 = W_WorldFunc.getWorldVec3(this.field_70170_p, this.field_70165_t + mx, this.field_70163_u + my, this.field_70161_v + mz);
    if ((getInfo()).delayFuse > 0) {
      if (m != null) {
        boundBullet(m.field_178784_b);
        if (this.delayFuse == 0)
          this.delayFuse = (getInfo()).delayFuse; 
      } 
      return;
    } 
    if (m != null)
      vec31 = W_WorldFunc.getWorldVec3(this.field_70170_p, m.field_72307_f.field_72450_a, m.field_72307_f.field_72448_b, m.field_72307_f.field_72449_c); 
    Entity entity = null;
    List<Entity> list = this.field_70170_p.func_72839_b((Entity)this, 
        func_174813_aQ().func_72321_a(mx, my, mz).func_72314_b(21.0D, 21.0D, 21.0D));
    double d0 = 0.0D;
    for (int j = 0; j < list.size(); j++) {
      Entity entity1 = list.get(j);
      if (canBeCollidedEntity(entity1)) {
        float f = 0.3F;
        AxisAlignedBB axisalignedbb = entity1.func_174813_aQ().func_72314_b(f, f, f);
        RayTraceResult m1 = axisalignedbb.func_72327_a(vec3, vec31);
        if (m1 != null) {
          double d1 = vec3.func_72438_d(m1.field_72307_f);
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
