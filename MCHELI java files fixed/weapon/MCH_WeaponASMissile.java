package mcheli.weapon;

import mcheli.MCH_Lib;
import mcheli.wrapper.W_MovingObjectPosition;
import mcheli.wrapper.W_WorldFunc;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class MCH_WeaponASMissile extends MCH_WeaponBase {
  public MCH_WeaponASMissile(World w, Vec3d v, float yaw, float pitch, String nm, MCH_WeaponInfo wi) {
    super(w, v, yaw, pitch, nm, wi);
    this.acceleration = 3.0F;
    this.explosionPower = 9;
    this.power = 40;
    this.interval = 65186;
    if (w.isRemote)
      this.interval -= 10; 
  }
  
  public boolean isCooldownCountReloadTime() {
    return true;
  }
  
  public void update(int countWait) {
    super.update(countWait);
  }
  
  public boolean shot(MCH_WeaponParam prm) {
    float yaw = prm.user.rotationYaw;
    float pitch = prm.user.rotationPitch;
    double tX = (-MathHelper.sin(yaw / 180.0F * 3.1415927F) * MathHelper.cos(pitch / 180.0F * 3.1415927F));
    double tZ = (MathHelper.cos(yaw / 180.0F * 3.1415927F) * MathHelper.cos(pitch / 180.0F * 3.1415927F));
    double tY = -MathHelper.sin(pitch / 180.0F * 3.1415927F);
    double dist = MathHelper.sqrt(tX * tX + tY * tY + tZ * tZ);
    if (this.worldObj.isRemote) {
      tX = tX * 200.0D / dist;
      tY = tY * 200.0D / dist;
      tZ = tZ * 200.0D / dist;
    } else {
      tX = tX * 250.0D / dist;
      tY = tY * 250.0D / dist;
      tZ = tZ * 250.0D / dist;
    } 
    Vec3d src = W_WorldFunc.getWorldVec3(this.worldObj, prm.entity.posX, prm.entity.posY + 1.62D, prm.entity.posZ);
    Vec3d dst = W_WorldFunc.getWorldVec3(this.worldObj, prm.entity.posX + tX, prm.entity.posY + 1.62D + tY, prm.entity.posZ + tZ);
    RayTraceResult m = W_WorldFunc.clip(this.worldObj, src, dst);
    if (m != null && W_MovingObjectPosition.isHitTypeTile(m) && !MCH_Lib.isBlockInWater(this.worldObj, m
        .getBlockPos().getX(), m.getBlockPos().getY(), m.getBlockPos().getZ())) {
      if (!this.worldObj.isRemote) {
        MCH_EntityASMissile e = new MCH_EntityASMissile(this.worldObj, prm.posX, prm.posY, prm.posZ, tX, tY, tZ, yaw, pitch, this.acceleration);
        e.setName(this.name);
        e.setParameterFromWeapon(this, prm.entity, prm.user);
        e.targetPosX = m.hitVec.x;
        e.targetPosY = m.hitVec.y;
        e.targetPosZ = m.hitVec.z;
        this.worldObj.spawnEntity((Entity)e);
        playSound(prm.entity);
      } 
      return true;
    } 
    return false;
  }
}
