package mcheli.flare;

import io.netty.buffer.ByteBuf;
import mcheli.particles.MCH_ParticleParam;
import mcheli.particles.MCH_ParticlesUtil;
import mcheli.wrapper.W_Entity;
import mcheli.wrapper.W_WorldFunc;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MCH_EntityFlare extends W_Entity implements IEntityAdditionalSpawnData {
  public double gravity = -0.013D;
  
  public double airResistance = 0.992D;
  
  public float size;
  
  public int fuseCount;
  
  public MCH_EntityFlare(World par1World) {
    super(par1World);
    setSize(1.0F, 1.0F);
    this.prevRotationYaw = this.rotationYaw;
    this.prevRotationPitch = this.rotationPitch;
    this.size = 6.0F;
    this.fuseCount = 0;
  }
  
  public MCH_EntityFlare(World par1World, double pX, double pY, double pZ, double mX, double mY, double mZ, float size, int fuseCount) {
    this(par1World);
    setLocationAndAngles(pX, pY, pZ, 0.0F, 0.0F);
    this.motionX = mX;
    this.motionY = mY;
    this.motionZ = mZ;
    this.size = size;
    this.fuseCount = fuseCount;
  }
  
  public boolean isEntityInvulnerable(DamageSource source) {
    return true;
  }
  
  @SideOnly(Side.CLIENT)
  public boolean isInRangeToRenderDist(double par1) {
    double d1 = getEntityBoundingBox().getAverageEdgeLength() * 4.0D;
    d1 *= 64.0D;
    return (par1 < d1 * d1);
  }
  
  public void setDead() {
    super.setDead();
    if (this.fuseCount > 0 && this.world.isRemote) {
      this.fuseCount = 0;
      for (int i = 0; i < 20; i++) {
        double x = (this.rand.nextDouble() - 0.5D) * 10.0D;
        double y = (this.rand.nextDouble() - 0.5D) * 10.0D;
        double z = (this.rand.nextDouble() - 0.5D) * 10.0D;
        MCH_ParticleParam prm = new MCH_ParticleParam(this.world, "smoke", this.posX + x, this.posY + y, this.posZ + z);
        prm.age = 200 + this.rand.nextInt(100);
        prm.size = (20 + this.rand.nextInt(25));
        prm.motionX = (this.rand.nextDouble() - 0.5D) * 0.45D;
        prm.motionY = (this.rand.nextDouble() - 0.5D) * 0.01D;
        prm.motionZ = (this.rand.nextDouble() - 0.5D) * 0.45D;
        prm.a = this.rand.nextFloat() * 0.1F + 0.85F;
        prm.b = this.rand.nextFloat() * 0.2F + 0.5F;
        prm.g = prm.b + 0.05F;
        prm.r = prm.b + 0.1F;
        MCH_ParticlesUtil.spawnParticle(prm);
      } 
    } 
  }
  
  public void writeSpawnData(ByteBuf buffer) {
    try {
      buffer.writeByte(this.fuseCount);
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public void readSpawnData(ByteBuf additionalData) {
    try {
      this.fuseCount = additionalData.readByte();
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public void onUpdate() {
    if (this.fuseCount > 0 && this.ticksExisted >= this.fuseCount) {
      setDead();
    } else if (!this.world.isRemote && !this.world.isBlockLoaded(new BlockPos(this.posX, this.posY, this.posZ))) {
      setDead();
    } else if (this.ticksExisted > 300 && !this.world.isRemote) {
      setDead();
    } else {
      super.onUpdate();
      if (!this.world.isRemote)
        onUpdateCollided(); 
      this.posX += this.motionX;
      this.posY += this.motionY;
      this.posZ += this.motionZ;
      if (this.world.isRemote) {
        double x = (this.posX - this.prevPosX) / 2.0D;
        double y = (this.posY - this.prevPosY) / 2.0D;
        double z = (this.posZ - this.prevPosZ) / 2.0D;
        for (int i = 0; i < 2; i++) {
          MCH_ParticleParam prm = new MCH_ParticleParam(this.world, "smoke", this.prevPosX + x * i, this.prevPosY + y * i, this.prevPosZ + z * i);
          prm.size = 6.0F + this.rand.nextFloat();
          if (this.size < 5.0F) {
            MCH_ParticleParam tmp290_288 = prm;
            tmp290_288.a = (float)(tmp290_288.a * 0.75D);
            if (this.rand.nextInt(2) == 0);
          } 
          if (this.fuseCount > 0) {
            prm.a = this.rand.nextFloat() * 0.1F + 0.85F;
            prm.b = this.rand.nextFloat() * 0.1F + 0.5F;
            prm.g = prm.b + 0.05F;
            prm.r = prm.b + 0.1F;
          } 
          MCH_ParticlesUtil.spawnParticle(prm);
        } 
      } 
      this.motionY += this.gravity;
      this.motionX *= this.airResistance;
      this.motionZ *= this.airResistance;
      if (isInWater() && !this.world.isRemote)
        setDead(); 
      if (this.onGround && !this.world.isRemote)
        setDead(); 
      setPosition(this.posX, this.posY, this.posZ);
    } 
  }
  
  protected void onUpdateCollided() {
    Vec3d vec3 = W_WorldFunc.getWorldVec3(this.world, this.posX, this.posY, this.posZ);
    Vec3d vec31 = W_WorldFunc.getWorldVec3(this.world, this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
    RayTraceResult mop = W_WorldFunc.clip(this.world, vec3, vec31);
    vec3 = W_WorldFunc.getWorldVec3(this.world, this.posX, this.posY, this.posZ);
    vec31 = W_WorldFunc.getWorldVec3(this.world, this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
    if (mop != null) {
      vec31 = W_WorldFunc.getWorldVec3(this.world, mop.hitVec.xCoord, mop.hitVec.yCoord, mop.hitVec.zCoord);
      onImpact(mop);
    } 
  }
  
  protected void onImpact(RayTraceResult par1MovingObjectPosition) {
    if (!this.world.isRemote)
      setDead(); 
  }
  
  public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
    par1NBTTagCompound.setTag("direction", (NBTBase)newDoubleNBTList(new double[] { this.motionX, this.motionY, this.motionZ }));
  }
  
  public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
    setDead();
  }
  
  public boolean canBeCollidedWith() {
    return true;
  }
  
  public float getCollisionBorderSize() {
    return 1.0F;
  }
  
  public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
    return false;
  }
  
  @SideOnly(Side.CLIENT)
  public float getShadowSize() {
    return 0.0F;
  }
}
