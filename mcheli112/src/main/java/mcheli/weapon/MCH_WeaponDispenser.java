package mcheli.weapon;

import mcheli.MCH_Lib;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class MCH_WeaponDispenser extends MCH_WeaponBase {
  public MCH_WeaponDispenser(World w, Vec3d v, float yaw, float pitch, String nm, MCH_WeaponInfo wi) {
    super(w, v, yaw, pitch, nm, wi);
    this.acceleration = 0.5F;
    this.explosionPower = 0;
    this.power = 0;
    this.interval = -90;
    if (w.isRemote)
      this.interval -= 10; 
  }
  
  public boolean shot(MCH_WeaponParam prm) {
    if (!this.worldObj.isRemote) {
      playSound(prm.entity);
      Vec3d v = MCH_Lib.RotVec3(0.0D, 0.0D, 1.0D, -prm.rotYaw, -prm.rotPitch, -prm.rotRoll);
      MCH_EntityDispensedItem e = new MCH_EntityDispensedItem(this.worldObj, prm.posX, prm.posY, prm.posZ, v.x, v.y, v.z, prm.rotYaw, prm.rotPitch, this.acceleration);
      e.setName(this.name);
      e.setParameterFromWeapon(this, prm.entity, prm.user);
      e.motionX = prm.entity.motionX + e.motionX * 0.5D;
      e.motionY = prm.entity.motionY + e.motionY * 0.5D;
      e.motionZ = prm.entity.motionZ + e.motionZ * 0.5D;
      e.posX += e.motionX * 0.5D;
      e.posY += e.motionY * 0.5D;
      e.posZ += e.motionZ * 0.5D;
      this.worldObj.spawnEntityInWorld((Entity)e);
    } 
    return true;
  }
}
