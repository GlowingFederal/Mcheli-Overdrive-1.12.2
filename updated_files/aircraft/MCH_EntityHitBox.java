package mcheli.aircraft;

import mcheli.wrapper.W_Entity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MCH_EntityHitBox extends W_Entity {
  public MCH_EntityAircraft parent;
  
  public int debugId;
  
  public MCH_EntityHitBox(World world) {
    super(world);
    setSize(1.0F, 1.0F);
    this.motionX = 0.0D;
    this.motionY = 0.0D;
    this.motionZ = 0.0D;
    this.parent = null;
    this.ignoreFrustumCheck = true;
    this.isImmuneToFire = true;
  }
  
  public MCH_EntityHitBox(World world, MCH_EntityAircraft ac, float w, float h) {
    this(world);
    setPosition(ac.posX, ac.posY + 1.0D, ac.posZ);
    this.prevPosX = ac.posX;
    this.prevPosY = ac.posY + 1.0D;
    this.prevPosZ = ac.posZ;
    this.parent = ac;
    setSize(w, h);
  }
  
  protected boolean canTriggerWalking() {
    return false;
  }
  
  public AxisAlignedBB getCollisionBox(Entity par1Entity) {
    return par1Entity.getEntityBoundingBox();
  }
  
  public AxisAlignedBB getCollisionBoundingBox() {
    return getEntityBoundingBox();
  }
  
  public boolean canBePushed() {
    return false;
  }
  
  public double getMountedYOffset() {
    return -0.3D;
  }
  
  public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
    if (this.parent != null)
      return this.parent.attackEntityFrom(par1DamageSource, par2); 
    return false;
  }
  
  public boolean canBeCollidedWith() {
    return !this.isDead;
  }
  
  public void setDead() {
    super.setDead();
  }
  
  public void onUpdate() {
    super.onUpdate();
  }
  
  protected void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {}
  
  protected void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {}
  
  @SideOnly(Side.CLIENT)
  public float getShadowSize() {
    return 0.0F;
  }
  
  public boolean processInitialInteract(EntityPlayer player, EnumHand hand) {
    return (this.parent != null) ? this.parent.processInitialInteract(player, hand) : false;
  }
}
