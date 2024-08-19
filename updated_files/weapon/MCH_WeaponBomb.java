package mcheli.weapon;

import mcheli.MCH_Explosion;
import mcheli.aircraft.MCH_EntityAircraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class MCH_WeaponBomb extends MCH_WeaponBase {
  public MCH_WeaponBomb(World w, Vec3d v, float yaw, float pitch, String nm, MCH_WeaponInfo wi) {
    super(w, v, yaw, pitch, nm, wi);
    this.acceleration = 0.5F;
    this.explosionPower = 9;
    this.power = 35;
    this.interval = -90;
    if (w.isRemote)
      this.interval -= 10; 
  }
  
  public boolean shot(MCH_WeaponParam prm) {
    if (getInfo() != null && (getInfo()).destruct) {
      if (prm.entity instanceof mcheli.helicopter.MCH_EntityHeli) {
        MCH_EntityAircraft ac = (MCH_EntityAircraft)prm.entity;
        if (ac.isUAV() && ac.getSeatNum() == 0) {
          if (!this.worldObj.isRemote) {
            MCH_Explosion.newExplosion(this.worldObj, null, prm.user, ac.posX, ac.posY, ac.posZ, 
                (getInfo()).explosion, (getInfo()).explosionBlock, true, true, (getInfo()).flaming, true, 0);
            playSound(prm.entity);
          } 
          ac.destruct();
        } 
      } 
    } else if (!this.worldObj.isRemote) {
      playSound(prm.entity);
      MCH_EntityBomb e = new MCH_EntityBomb(this.worldObj, prm.posX, prm.posY, prm.posZ, prm.entity.motionX, prm.entity.motionY, prm.entity.motionZ, prm.entity.rotationYaw, 0.0F, this.acceleration);
      e.setName(this.name);
      e.setParameterFromWeapon(this, prm.entity, prm.user);
      e.motionX = prm.entity.motionX;
      e.motionY = prm.entity.motionY;
      e.motionZ = prm.entity.motionZ;
      this.worldObj.spawnEntityInWorld((Entity)e);
    } 
    return true;
  }
}
