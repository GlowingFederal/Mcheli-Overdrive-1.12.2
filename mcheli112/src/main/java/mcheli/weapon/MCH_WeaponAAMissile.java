package mcheli.weapon;

import mcheli.wrapper.W_Entity;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class MCH_WeaponAAMissile extends MCH_WeaponEntitySeeker {
  public MCH_WeaponAAMissile(World w, Vec3d v, float yaw, float pitch, String nm, MCH_WeaponInfo wi) {
    super(w, v, yaw, pitch, nm, wi);
    this.power = 12;
    this.acceleration = 2.5F;
    this.explosionPower = 4;
    this.interval = 5;
    if (w.isRemote)
      this.interval += 5; 
    this.guidanceSystem.canLockInAir = true;
    this.guidanceSystem.ridableOnly = wi.ridableOnly;
  }
  
  public boolean isCooldownCountReloadTime() {
    return true;
  }
  
  public void update(int countWait) {
    super.update(countWait);
  }
  
  public boolean shot(MCH_WeaponParam prm) {
    boolean result = false;
    if (!this.worldObj.isRemote) {
      Entity tgtEnt = prm.user.world.getEntityByID(prm.option1);
      if (tgtEnt != null && !tgtEnt.isDead) {
        playSound(prm.entity);
        float yaw = prm.entity.rotationYaw + this.fixRotationYaw;
        float pitch = prm.entity.rotationPitch + this.fixRotationPitch;
        double tX = (-MathHelper.sin(yaw / 180.0F * 3.1415927F) * MathHelper.cos(pitch / 180.0F * 3.1415927F));
        double tZ = (MathHelper.cos(yaw / 180.0F * 3.1415927F) * MathHelper.cos(pitch / 180.0F * 3.1415927F));
        double tY = -MathHelper.sin(pitch / 180.0F * 3.1415927F);
        MCH_EntityAAMissile e = new MCH_EntityAAMissile(this.worldObj, prm.posX, prm.posY, prm.posZ, tX, tY, tZ, yaw, pitch, this.acceleration);
        e.setName(this.name);
        e.setParameterFromWeapon(this, prm.entity, prm.user);
        e.setTargetEntity(tgtEnt);
        this.worldObj.spawnEntity((Entity)e);
        result = true;
      } 
    } else if (this.guidanceSystem.lock(prm.user)) {
      if (this.guidanceSystem.lastLockEntity != null) {
        result = true;
        this.optionParameter1 = W_Entity.getEntityId(this.guidanceSystem.lastLockEntity);
      } 
    } 
    return result;
  }
}
