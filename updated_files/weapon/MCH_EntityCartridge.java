package mcheli.weapon;

import mcheli.MCH_Config;
import mcheli.__helper.client._IModelCustom;
import mcheli.wrapper.W_Entity;
import mcheli.wrapper.W_MovingObjectPosition;
import mcheli.wrapper.W_WorldFunc;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MCH_EntityCartridge extends W_Entity {
  public final String texture_name;
  
  public final _IModelCustom model;
  
  private final float bound;
  
  private final float gravity;
  
  private final float scale;
  
  private int countOnUpdate;
  
  public float targetYaw;
  
  public float targetPitch;
  
  @SideOnly(Side.CLIENT)
  public static void spawnCartridge(World world, MCH_Cartridge cartridge, double x, double y, double z, double mx, double my, double mz, float yaw, float pitch) {
    if (cartridge != null) {
      MCH_EntityCartridge entityFX = new MCH_EntityCartridge(world, cartridge, x, y, z, mx + (world.rand.nextFloat() - 0.5D) * 0.07D, my, mz + (world.rand.nextFloat() - 0.5D) * 0.07D);
      entityFX.prevRotationYaw = yaw;
      entityFX.rotationYaw = yaw;
      entityFX.targetYaw = yaw;
      entityFX.prevRotationPitch = pitch;
      entityFX.rotationPitch = pitch;
      entityFX.targetPitch = pitch;
      float cy = yaw + cartridge.yaw;
      float cp = pitch + cartridge.pitch;
      double tX = (-MathHelper.sin(cy / 180.0F * 3.1415927F) * MathHelper.cos(cp / 180.0F * 3.1415927F));
      double tZ = (MathHelper.cos(cy / 180.0F * 3.1415927F) * MathHelper.cos(cp / 180.0F * 3.1415927F));
      double tY = -MathHelper.sin(cp / 180.0F * 3.1415927F);
      double d = MathHelper.sqrt(tX * tX + tY * tY + tZ * tZ);
      if (Math.abs(d) > 0.001D) {
        entityFX.motionX += tX * cartridge.acceleration / d;
        entityFX.motionY += tY * cartridge.acceleration / d;
        entityFX.motionZ += tZ * cartridge.acceleration / d;
      } 
      world.spawnEntityInWorld((Entity)entityFX);
    } 
  }
  
  public MCH_EntityCartridge(World par1World, MCH_Cartridge c, double x, double y, double z, double mx, double my, double mz) {
    super(par1World);
    setPositionAndRotation(x, y, z, 0.0F, 0.0F);
    this.motionX = mx;
    this.motionY = my;
    this.motionZ = mz;
    this.texture_name = c.name;
    this.model = c.model;
    this.bound = c.bound;
    this.gravity = c.gravity;
    this.scale = c.scale;
    this.countOnUpdate = 0;
  }
  
  public float getScale() {
    return this.scale;
  }
  
  public void onUpdate() {
    this.prevPosX = this.posX;
    this.prevPosY = this.posY;
    this.prevPosZ = this.posZ;
    this.prevRotationYaw = this.rotationYaw;
    this.prevRotationPitch = this.rotationPitch;
    if (this.countOnUpdate < MCH_Config.AliveTimeOfCartridge.prmInt) {
      this.countOnUpdate++;
    } else {
      setDead();
    } 
    this.motionX *= 0.98D;
    this.motionZ *= 0.98D;
    this.motionY += this.gravity;
    move();
  }
  
  public void rotation() {
    if (this.rotationYaw < this.targetYaw - 3.0F) {
      this.rotationYaw += 10.0F;
      if (this.rotationYaw > this.targetYaw)
        this.rotationYaw = this.targetYaw; 
    } else if (this.rotationYaw > this.targetYaw + 3.0F) {
      this.rotationYaw -= 10.0F;
      if (this.rotationYaw < this.targetYaw)
        this.rotationYaw = this.targetYaw; 
    } 
    if (this.rotationPitch < this.targetPitch) {
      this.rotationPitch += 10.0F;
      if (this.rotationPitch > this.targetPitch)
        this.rotationPitch = this.targetPitch; 
    } else if (this.rotationPitch > this.targetPitch) {
      this.rotationPitch -= 10.0F;
      if (this.rotationPitch < this.targetPitch)
        this.rotationPitch = this.targetPitch; 
    } 
  }
  
  public void move() {
    Vec3d vec1 = W_WorldFunc.getWorldVec3(this.world, this.posX, this.posY, this.posZ);
    Vec3d vec2 = W_WorldFunc.getWorldVec3(this.world, this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
    RayTraceResult m = W_WorldFunc.clip(this.world, vec1, vec2);
    double d = Math.max(Math.abs(this.motionX), Math.abs(this.motionY));
    d = Math.max(d, Math.abs(this.motionZ));
    if (W_MovingObjectPosition.isHitTypeTile(m)) {
      setPosition(m.hitVec.xCoord, m.hitVec.yCoord, m.hitVec.zCoord);
      this.motionX += d * (this.rand.nextFloat() - 0.5F) * 0.10000000149011612D;
      this.motionY += d * (this.rand.nextFloat() - 0.5F) * 0.10000000149011612D;
      this.motionZ += d * (this.rand.nextFloat() - 0.5F) * 0.10000000149011612D;
      if (d > 0.10000000149011612D) {
        this.targetYaw += (float)(d * (this.rand.nextFloat() - 0.5F) * 720.0D);
        this.targetPitch = (float)(d * (this.rand.nextFloat() - 0.5F) * 720.0D);
      } else {
        this.targetPitch = 0.0F;
      } 
      switch (m.sideHit) {
        case DOWN:
          if (this.motionY > 0.0D)
            this.motionY = -this.motionY * this.bound; 
          break;
        case UP:
          if (this.motionY < 0.0D)
            this.motionY = -this.motionY * this.bound; 
          this.targetPitch *= 0.3F;
          break;
        case NORTH:
          if (this.motionZ > 0.0D) {
            this.motionZ = -this.motionZ * this.bound;
            break;
          } 
          this.posZ += this.motionZ;
          break;
        case SOUTH:
          if (this.motionZ < 0.0D) {
            this.motionZ = -this.motionZ * this.bound;
            break;
          } 
          this.posZ += this.motionZ;
          break;
        case WEST:
          if (this.motionX > 0.0D) {
            this.motionX = -this.motionX * this.bound;
            break;
          } 
          this.posX += this.motionX;
          break;
        case EAST:
          if (this.motionX < 0.0D) {
            this.motionX = -this.motionX * this.bound;
            break;
          } 
          this.posX += this.motionX;
          break;
      } 
    } else {
      this.posX += this.motionX;
      this.posY += this.motionY;
      this.posZ += this.motionZ;
      if (d > 0.05000000074505806D)
        rotation(); 
    } 
  }
  
  protected void readEntityFromNBT(NBTTagCompound var1) {}
  
  protected void writeEntityToNBT(NBTTagCompound var1) {}
}
