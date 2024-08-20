package mcheli.debug._v4;

import mcheli.weapon.MCH_EntityRocket;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntitySampleBullet extends MCH_EntityRocket {
  private Vec3d firstPos;
  
  public EntitySampleBullet(World par1World) {
    super(par1World, 0.0D, 0.0D, 0.0D, 2.0D, 0.0D, 0.0D, 0.0F, 0.0F, 4.0D);
    setName("hydra70");
    setSize(1.0F, 1.0F);
    this.explosionPower = 3;
    setPower(22);
    this.firstPos = Vec3d.ZERO;
    this.accelerationFactor = 1.0D;
    this.delayFuse = 100;
  }
  
  public void readEntityFromNBT(NBTTagCompound par1nbtTagCompound) {
    this.motionX = 1.0D;
  }
  
  public boolean checkValid() {
    double x = this.posX - this.firstPos.x;
    double z = this.posZ - this.firstPos.z;
    return (x * x + z * z < 3.38724E7D && this.posY > -10.0D);
  }
}
