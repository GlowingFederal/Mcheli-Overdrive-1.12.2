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
    func_70105_a(1.0F, 1.0F);
    this.explosionPower = 3;
    setPower(22);
    this.firstPos = Vec3d.field_186680_a;
    this.acceleration = 4.0D;
    this.explosionPower = 1;
    this.delayFuse = 100;
  }
  
  public void func_70037_a(NBTTagCompound par1nbtTagCompound) {
    this.field_70159_w = 1.0D;
  }
  
  public boolean checkValid() {
    double x = this.field_70165_t - this.firstPos.field_72450_a;
    double z = this.field_70161_v - this.firstPos.field_72449_c;
    return (x * x + z * z < 3.38724E7D && this.field_70163_u > -10.0D);
  }
}
