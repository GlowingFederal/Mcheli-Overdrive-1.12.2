package mcheli.debug._v4;

import mcheli.weapon.MCH_EntityBullet;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityBulletTest extends MCH_EntityBullet {
  private Vec3d firstPos;
  
  public EntityBulletTest(World par1World) {
    super(par1World);
    setName("m230");
    setSize(1.0F, 1.0F);
    this.explosionPower = 3;
    setPower(22);
    this.firstPos = Vec3d.ZERO;
    this.acceleration = 4.0D;
    this.explosionPower = 1;
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
