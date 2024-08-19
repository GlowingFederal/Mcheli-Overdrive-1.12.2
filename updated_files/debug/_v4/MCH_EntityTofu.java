package mcheli.debug._v4;

import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class MCH_EntityTofu extends Entity {
  public MCH_EntityTofu(World worldIn) {
    super(worldIn);
  }
  
  public MCH_EntityTofu(World world, double x, double y, double z) {
    this(world);
    setPosition(x, y, z);
  }
  
  protected void entityInit() {
    setNoGravity(true);
  }
  
  public void onUpdate() {
    super.onUpdate();
    moveEntity(MoverType.SELF, 1.0D, 0.0D, 0.0D);
    if (!this.world.isRemote)
      if (this.ticksExisted > 100)
        setDead();  
  }
  
  public boolean canBeCollidedWith() {
    return false;
  }
  
  public void applyEntityCollision(Entity entityIn) {}
  
  protected void readEntityFromNBT(NBTTagCompound compound) {}
  
  protected void writeEntityToNBT(NBTTagCompound compound) {}
}
