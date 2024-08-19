package mcheli.weapon;

import mcheli.MCH_Lib;
import mcheli.wrapper.W_MovingObjectPosition;
import mcheli.wrapper.W_WorldFunc;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class MCH_WeaponTorpedo extends MCH_WeaponBase {
  public MCH_WeaponTorpedo(World w, Vec3d v, float yaw, float pitch, String nm, MCH_WeaponInfo wi) {
    super(w, v, yaw, pitch, nm, wi);
    this.acceleration = 0.5F;
    this.explosionPower = 8;
    this.power = 35;
    this.interval = -100;
    if (w.isRemote)
      this.interval -= 10; 
  }
  
  public boolean shot(MCH_WeaponParam prm) {
    if (getInfo() != null) {
      if ((getInfo()).isGuidedTorpedo)
        return shotGuided(prm); 
      return shotNoGuided(prm);
    } 
    return false;
  }
  
  protected boolean shotNoGuided(MCH_WeaponParam prm) {
    if (this.worldObj.isRemote)
      return true; 
    float yaw = prm.rotYaw;
    float pitch = prm.rotPitch;
    double mx = (-MathHelper.sin(yaw / 180.0F * 3.1415927F) * MathHelper.cos(pitch / 180.0F * 3.1415927F));
    double mz = (MathHelper.cos(yaw / 180.0F * 3.1415927F) * MathHelper.cos(pitch / 180.0F * 3.1415927F));
    double my = -MathHelper.sin(pitch / 180.0F * 3.1415927F);
    mx = mx * (getInfo()).acceleration + prm.entity.motionX;
    my = my * (getInfo()).acceleration + prm.entity.motionY;
    mz = mz * (getInfo()).acceleration + prm.entity.motionZ;
    this.acceleration = MathHelper.sqrt(mx * mx + my * my + mz * mz);
    MCH_EntityTorpedo e = new MCH_EntityTorpedo(this.worldObj, prm.posX, prm.posY, prm.posZ, mx, my, mz, yaw, 0.0F, this.acceleration);
    e.setName(this.name);
    e.setParameterFromWeapon(this, prm.entity, prm.user);
    e.motionX = mx;
    e.motionY = my;
    e.motionZ = mz;
    e.accelerationInWater = (getInfo() != null) ? (getInfo()).accelerationInWater : 1.0D;
    this.worldObj.spawnEntityInWorld((Entity)e);
    playSound(prm.entity);
    return true;
  }
  
  protected boolean shotGuided(MCH_WeaponParam prm) {
    float yaw = prm.user.rotationYaw;
    float pitch = prm.user.rotationPitch;
    Vec3d v = MCH_Lib.RotVec3(0.0D, 0.0D, 1.0D, -yaw, -pitch, -prm.rotRoll);
    double tX = v.xCoord;
    double tZ = v.zCoord;
    double tY = v.yCoord;
    double dist = MathHelper.sqrt(tX * tX + tY * tY + tZ * tZ);
    if (this.worldObj.isRemote) {
      tX = tX * 100.0D / dist;
      tY = tY * 100.0D / dist;
      tZ = tZ * 100.0D / dist;
    } else {
      tX = tX * 150.0D / dist;
      tY = tY * 150.0D / dist;
      tZ = tZ * 150.0D / dist;
    } 
    Vec3d src = W_WorldFunc.getWorldVec3(this.worldObj, prm.user.posX, prm.user.posY, prm.user.posZ);
    Vec3d dst = W_WorldFunc.getWorldVec3(this.worldObj, prm.user.posX + tX, prm.user.posY + tY, prm.user.posZ + tZ);
    RayTraceResult m = W_WorldFunc.clip(this.worldObj, src, dst);
    if (m != null && W_MovingObjectPosition.isHitTypeTile(m) && MCH_Lib.isBlockInWater(this.worldObj, m
        .getBlockPos().getX(), m.getBlockPos().getY(), m.getBlockPos().getZ())) {
      if (!this.worldObj.isRemote) {
        double mx = (-MathHelper.sin(yaw / 180.0F * 3.1415927F) * MathHelper.cos(pitch / 180.0F * 3.1415927F));
        double mz = (MathHelper.cos(yaw / 180.0F * 3.1415927F) * MathHelper.cos(pitch / 180.0F * 3.1415927F));
        double my = -MathHelper.sin(pitch / 180.0F * 3.1415927F);
        mx = mx * (getInfo()).acceleration + prm.entity.motionX;
        my = my * (getInfo()).acceleration + prm.entity.motionY;
        mz = mz * (getInfo()).acceleration + prm.entity.motionZ;
        this.acceleration = MathHelper.sqrt(mx * mx + my * my + mz * mz);
        MCH_EntityTorpedo e = new MCH_EntityTorpedo(this.worldObj, prm.posX, prm.posY, prm.posZ, prm.entity.motionX, prm.entity.motionY, prm.entity.motionZ, yaw, 0.0F, this.acceleration);
        e.setName(this.name);
        e.setParameterFromWeapon(this, prm.entity, prm.user);
        e.targetPosX = m.hitVec.xCoord;
        e.targetPosY = m.hitVec.yCoord;
        e.targetPosZ = m.hitVec.zCoord;
        e.motionX = mx;
        e.motionY = my;
        e.motionZ = mz;
        e.accelerationInWater = (getInfo() != null) ? (getInfo()).accelerationInWater : 1.0D;
        this.worldObj.spawnEntityInWorld((Entity)e);
        playSound(prm.entity);
      } 
      return true;
    } 
    return false;
  }
}
