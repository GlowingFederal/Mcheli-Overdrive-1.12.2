package mcheli.weapon;

import mcheli.aircraft.MCH_EntityAircraft;
import mcheli.multiplay.MCH_Multiplay;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class MCH_WeaponTargetingPod extends MCH_WeaponBase {
  public MCH_WeaponTargetingPod(World w, Vec3d v, float yaw, float pitch, String nm, MCH_WeaponInfo wi) {
    super(w, v, yaw, pitch, nm, wi);
    this.interval = -90;
    if (w.field_72995_K)
      this.interval -= 10; 
  }
  
  public boolean shot(MCH_WeaponParam prm) {
    if (!this.worldObj.field_72995_K) {
      MCH_WeaponInfo info = getInfo();
      if ((info.target & 0x40) != 0) {
        if (MCH_Multiplay.markPoint((EntityPlayer)prm.user, prm.posX, prm.posY, prm.posZ)) {
          playSound(prm.user);
        } else {
          playSound(prm.user, "ng");
        } 
      } else if (MCH_Multiplay.spotEntity((EntityLivingBase)prm.user, (MCH_EntityAircraft)prm.entity, prm.posX, prm.posY, prm.posZ, info.target, info.length, info.markTime, info.angle)) {
        playSound(prm.entity);
      } else {
        playSound(prm.entity, "ng");
      } 
    } 
    return true;
  }
}
