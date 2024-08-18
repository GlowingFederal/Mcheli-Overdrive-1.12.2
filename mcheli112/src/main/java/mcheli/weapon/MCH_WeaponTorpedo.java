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
    if (w.field_72995_K)
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
    if (this.worldObj.field_72995_K)
      return true; 
    float yaw = prm.rotYaw;
    float pitch = prm.rotPitch;
    double mx = (-MathHelper.func_76126_a(yaw / 180.0F * 3.1415927F) * MathHelper.func_76134_b(pitch / 180.0F * 3.1415927F));
    double mz = (MathHelper.func_76134_b(yaw / 180.0F * 3.1415927F) * MathHelper.func_76134_b(pitch / 180.0F * 3.1415927F));
    double my = -MathHelper.func_76126_a(pitch / 180.0F * 3.1415927F);
    mx = mx * (getInfo()).acceleration + prm.entity.field_70159_w;
    my = my * (getInfo()).acceleration + prm.entity.field_70181_x;
    mz = mz * (getInfo()).acceleration + prm.entity.field_70179_y;
    this.acceleration = MathHelper.func_76133_a(mx * mx + my * my + mz * mz);
    MCH_EntityTorpedo e = new MCH_EntityTorpedo(this.worldObj, prm.posX, prm.posY, prm.posZ, mx, my, mz, yaw, 0.0F, this.acceleration);
    e.setName(this.name);
    e.setParameterFromWeapon(this, prm.entity, prm.user);
    e.field_70159_w = mx;
    e.field_70181_x = my;
    e.field_70179_y = mz;
    e.accelerationInWater = (getInfo() != null) ? (getInfo()).accelerationInWater : 1.0D;
    this.worldObj.func_72838_d((Entity)e);
    playSound(prm.entity);
    return true;
  }
  
  protected boolean shotGuided(MCH_WeaponParam prm) {
    float yaw = prm.user.field_70177_z;
    float pitch = prm.user.field_70125_A;
    Vec3d v = MCH_Lib.RotVec3(0.0D, 0.0D, 1.0D, -yaw, -pitch, -prm.rotRoll);
    double tX = v.field_72450_a;
    double tZ = v.field_72449_c;
    double tY = v.field_72448_b;
    double dist = MathHelper.func_76133_a(tX * tX + tY * tY + tZ * tZ);
    if (this.worldObj.field_72995_K) {
      tX = tX * 100.0D / dist;
      tY = tY * 100.0D / dist;
      tZ = tZ * 100.0D / dist;
    } else {
      tX = tX * 150.0D / dist;
      tY = tY * 150.0D / dist;
      tZ = tZ * 150.0D / dist;
    } 
    Vec3d src = W_WorldFunc.getWorldVec3(this.worldObj, prm.user.field_70165_t, prm.user.field_70163_u, prm.user.field_70161_v);
    Vec3d dst = W_WorldFunc.getWorldVec3(this.worldObj, prm.user.field_70165_t + tX, prm.user.field_70163_u + tY, prm.user.field_70161_v + tZ);
    RayTraceResult m = W_WorldFunc.clip(this.worldObj, src, dst);
    if (m != null && W_MovingObjectPosition.isHitTypeTile(m) && MCH_Lib.isBlockInWater(this.worldObj, m
        .func_178782_a().func_177958_n(), m.func_178782_a().func_177956_o(), m.func_178782_a().func_177952_p())) {
      if (!this.worldObj.field_72995_K) {
        double mx = (-MathHelper.func_76126_a(yaw / 180.0F * 3.1415927F) * MathHelper.func_76134_b(pitch / 180.0F * 3.1415927F));
        double mz = (MathHelper.func_76134_b(yaw / 180.0F * 3.1415927F) * MathHelper.func_76134_b(pitch / 180.0F * 3.1415927F));
        double my = -MathHelper.func_76126_a(pitch / 180.0F * 3.1415927F);
        mx = mx * (getInfo()).acceleration + prm.entity.field_70159_w;
        my = my * (getInfo()).acceleration + prm.entity.field_70181_x;
        mz = mz * (getInfo()).acceleration + prm.entity.field_70179_y;
        this.acceleration = MathHelper.func_76133_a(mx * mx + my * my + mz * mz);
        MCH_EntityTorpedo e = new MCH_EntityTorpedo(this.worldObj, prm.posX, prm.posY, prm.posZ, prm.entity.field_70159_w, prm.entity.field_70181_x, prm.entity.field_70179_y, yaw, 0.0F, this.acceleration);
        e.setName(this.name);
        e.setParameterFromWeapon(this, prm.entity, prm.user);
        e.targetPosX = m.field_72307_f.field_72450_a;
        e.targetPosY = m.field_72307_f.field_72448_b;
        e.targetPosZ = m.field_72307_f.field_72449_c;
        e.field_70159_w = mx;
        e.field_70181_x = my;
        e.field_70179_y = mz;
        e.accelerationInWater = (getInfo() != null) ? (getInfo()).accelerationInWater : 1.0D;
        this.worldObj.func_72838_d((Entity)e);
        playSound(prm.entity);
      } 
      return true;
    } 
    return false;
  }
}
