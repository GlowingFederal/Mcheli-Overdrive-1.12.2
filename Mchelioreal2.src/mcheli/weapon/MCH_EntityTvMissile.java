package mcheli.weapon;

import mcheli.aircraft.MCH_EntityAircraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class MCH_EntityTvMissile extends MCH_EntityBaseBullet {
  public boolean isSpawnParticle = true;
  
  public MCH_EntityTvMissile(World par1World) {
    super(par1World);
  }
  
  public MCH_EntityTvMissile(World par1World, double posX, double posY, double posZ, double targetX, double targetY, double targetZ, float yaw, float pitch, double acceleration) {
    super(par1World, posX, posY, posZ, targetX, targetY, targetZ, yaw, pitch, acceleration);
  }
  
  public void func_70071_h_() {
    super.func_70071_h_();
    if (this.isSpawnParticle)
      if (getInfo() != null && !(getInfo()).disableSmoke)
        spawnParticle((getInfo()).trajectoryParticleName, 3, 5.0F * (getInfo()).smokeSize * 0.5F);  
    if (this.shootingEntity != null) {
      double x = this.field_70165_t - this.shootingEntity.field_70165_t;
      double y = this.field_70163_u - this.shootingEntity.field_70163_u;
      double z = this.field_70161_v - this.shootingEntity.field_70161_v;
      if (x * x + y * y + z * z > 1440000.0D)
        func_70106_y(); 
      if (!this.field_70170_p.field_72995_K && !this.field_70128_L)
        onUpdateMotion(); 
    } else if (!this.field_70170_p.field_72995_K) {
      func_70106_y();
    } 
  }
  
  public void onUpdateMotion() {
    Entity e = this.shootingEntity;
    if (e != null && !e.field_70128_L) {
      MCH_EntityAircraft ac = MCH_EntityAircraft.getAircraft_RiddenOrControl(e);
      if (ac != null && ac.getTVMissile() == this) {
        float yaw = e.field_70177_z;
        float pitch = e.field_70125_A;
        double tX = (-MathHelper.func_76126_a(yaw / 180.0F * 3.1415927F) * MathHelper.func_76134_b(pitch / 180.0F * 3.1415927F));
        double tZ = (MathHelper.func_76134_b(yaw / 180.0F * 3.1415927F) * MathHelper.func_76134_b(pitch / 180.0F * 3.1415927F));
        double tY = -MathHelper.func_76126_a(pitch / 180.0F * 3.1415927F);
        setMotion(tX, tY, tZ);
        func_70101_b(yaw, pitch);
      } 
    } 
  }
  
  public MCH_BulletModel getDefaultBulletModel() {
    return MCH_DefaultBulletModels.ATMissile;
  }
}
