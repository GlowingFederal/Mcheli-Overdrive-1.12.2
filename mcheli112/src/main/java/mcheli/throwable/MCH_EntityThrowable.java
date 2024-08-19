package mcheli.throwable;

import javax.annotation.Nullable;
import mcheli.MCH_Explosion;
import mcheli.MCH_Lib;
import mcheli.particles.MCH_ParticleParam;
import mcheli.particles.MCH_ParticlesUtil;
import mcheli.wrapper.W_WorldFunc;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IThrowableEntity;

public class MCH_EntityThrowable extends EntityThrowable implements IThrowableEntity {
  private static final DataParameter<String> INFO_NAME = EntityDataManager.createKey(MCH_EntityThrowable.class, DataSerializers.STRING);
  
  private int countOnUpdate;
  
  private MCH_ThrowableInfo throwableInfo;
  
  public double boundPosX;
  
  public double boundPosY;
  
  public double boundPosZ;
  
  public RayTraceResult lastOnImpact;
  
  public int noInfoCount;
  
  public MCH_EntityThrowable(World par1World) {
    super(par1World);
    init();
  }
  
  public MCH_EntityThrowable(World par1World, EntityLivingBase par2EntityLivingBase, float acceleration) {
    super(par1World, par2EntityLivingBase);
    this.motionX *= acceleration;
    this.motionY *= acceleration;
    this.motionZ *= acceleration;
    init();
  }
  
  public MCH_EntityThrowable(World par1World, double par2, double par4, double par6) {
    super(par1World, par2, par4, par6);
    init();
  }
  
  public MCH_EntityThrowable(World worldIn, double x, double y, double z, float yaw, float pitch) {
    this(worldIn);
    setSize(0.25F, 0.25F);
    setLocationAndAngles(x, y, z, yaw, pitch);
    this.posX -= (MathHelper.cos(this.rotationYaw / 180.0F * 3.1415927F) * 0.16F);
    this.posY -= 0.10000000149011612D;
    this.posZ -= (MathHelper.sin(this.rotationYaw / 180.0F * 3.1415927F) * 0.16F);
    setPosition(this.posX, this.posY, this.posZ);
    setHeadingFromThrower((Entity)null, pitch, yaw, 0.0F, 1.5F, 1.0F);
  }
  
  public void init() {
    this.lastOnImpact = null;
    this.countOnUpdate = 0;
    setInfo((MCH_ThrowableInfo)null);
    this.noInfoCount = 0;
    this.dataManager.register(INFO_NAME, "");
  }
  
  public void setHeadingFromThrower(Entity entityThrower, float rotationPitchIn, float rotationYawIn, float pitchOffset, float velocity, float inaccuracy) {
    float f = 0.4F;
    this
      .motionX = (-MathHelper.sin(rotationYawIn / 180.0F * 3.1415927F) * MathHelper.cos(rotationPitchIn / 180.0F * 3.1415927F) * f);
    this
      .motionZ = (MathHelper.cos(rotationYawIn / 180.0F * 3.1415927F) * MathHelper.cos(rotationPitchIn / 180.0F * 3.1415927F) * f);
    this.motionY = (-MathHelper.sin((rotationPitchIn + pitchOffset) / 180.0F * 3.1415927F) * f);
    setThrowableHeading(this.motionX, this.motionY, this.motionZ, velocity, 1.0F);
  }
  
  public void setDead() {
    String s = (getInfo() != null) ? (getInfo()).name : "null";
    MCH_Lib.DbgLog(this.world, "MCH_EntityThrowable.setDead(%s)", new Object[] { s });
    super.setDead();
  }
  
  public void onUpdate() {
    this.boundPosX = this.posX;
    this.boundPosY = this.posY;
    this.boundPosZ = this.posZ;
    if (getInfo() != null) {
      Block block = W_WorldFunc.getBlock(this.world, (int)(this.posX + 0.5D), (int)this.posY, (int)(this.posZ + 0.5D));
      Material mat = W_WorldFunc.getBlockMaterial(this.world, (int)(this.posX + 0.5D), (int)this.posY, (int)(this.posZ + 0.5D));
      if (block != null && mat == Material.WATER) {
        this.motionY += (getInfo()).gravityInWater;
      } else {
        this.motionY += (getInfo()).gravity;
      } 
    } 
    super.onUpdate();
    if (this.lastOnImpact != null) {
      boundBullet(this.lastOnImpact);
      setPosition(this.boundPosX + this.motionX, this.boundPosY + this.motionY, this.boundPosZ + this.motionZ);
      this.lastOnImpact = null;
    } 
    this.countOnUpdate++;
    if (this.countOnUpdate >= 2147483632) {
      setDead();
      return;
    } 
    if (getInfo() == null) {
      String s = (String)this.dataManager.get(INFO_NAME);
      if (!s.isEmpty())
        setInfo(MCH_ThrowableInfoManager.get(s)); 
      if (getInfo() == null) {
        this.noInfoCount++;
        if (this.noInfoCount > 10)
          setDead(); 
        return;
      } 
    } 
    if (this.isDead)
      return; 
    if (!this.world.isRemote) {
      if (this.countOnUpdate == (getInfo()).timeFuse)
        if ((getInfo()).explosion > 0) {
          MCH_Explosion.newExplosion(this.world, null, null, this.posX, this.posY, this.posZ, 
              (getInfo()).explosion, (getInfo()).explosion, true, true, false, true, 0);
          setDead();
          return;
        }  
      if (this.countOnUpdate >= (getInfo()).aliveTime)
        setDead(); 
    } else if (this.countOnUpdate >= (getInfo()).timeFuse) {
      if ((getInfo()).explosion <= 0)
        for (int i = 0; i < (getInfo()).smokeNum; i++) {
          float r = (getInfo()).smokeColor.r * 0.9F + this.rand.nextFloat() * 0.1F;
          float g = (getInfo()).smokeColor.g * 0.9F + this.rand.nextFloat() * 0.1F;
          float b = (getInfo()).smokeColor.b * 0.9F + this.rand.nextFloat() * 0.1F;
          if ((getInfo()).smokeColor.r == (getInfo()).smokeColor.g)
            g = r; 
          if ((getInfo()).smokeColor.r == (getInfo()).smokeColor.b)
            b = r; 
          if ((getInfo()).smokeColor.g == (getInfo()).smokeColor.b)
            b = g; 
          spawnParticle("explode", 4, 
              (getInfo()).smokeSize + this.rand.nextFloat() * (getInfo()).smokeSize / 3.0F, r, g, b, 
              (getInfo()).smokeVelocityHorizontal * (this.rand.nextFloat() - 0.5F), 
              (getInfo()).smokeVelocityVertical * this.rand.nextFloat(), 
              (getInfo()).smokeVelocityHorizontal * (this.rand.nextFloat() - 0.5F));
        }  
    } 
  }
  
  public void spawnParticle(String name, int num, float size, float r, float g, float b, float mx, float my, float mz) {
    if (this.world.isRemote) {
      if (name.isEmpty() || num < 1)
        return; 
      double x = (this.posX - this.prevPosX) / num;
      double y = (this.posY - this.prevPosY) / num;
      double z = (this.posZ - this.prevPosZ) / num;
      for (int i = 0; i < num; i++) {
        MCH_ParticleParam prm = new MCH_ParticleParam(this.world, "smoke", this.prevPosX + x * i, 1.0D + this.prevPosY + y * i, this.prevPosZ + z * i);
        prm.setMotion(mx, my, mz);
        prm.size = size;
        prm.setColor(1.0F, r, g, b);
        prm.isEffectWind = true;
        prm.toWhite = true;
        MCH_ParticlesUtil.spawnParticle(prm);
      } 
    } 
  }
  
  protected float getGravityVelocity() {
    return 0.0F;
  }
  
  public void boundBullet(RayTraceResult m) {
    if (m.sideHit == null)
      return; 
    float bound = (getInfo()).bound;
    switch (m.sideHit) {
      case DOWN:
      case UP:
        this.motionX *= 0.8999999761581421D;
        this.motionZ *= 0.8999999761581421D;
        this.boundPosY = m.hitVec.yCoord;
        if ((m.sideHit == EnumFacing.DOWN && this.motionY > 0.0D) || (m.sideHit == EnumFacing.UP && this.motionY < 0.0D)) {
          this.motionY = -this.motionY * bound;
          break;
        } 
        this.motionY = 0.0D;
        break;
      case NORTH:
        if (this.motionZ > 0.0D)
          this.motionZ = -this.motionZ * bound; 
        break;
      case SOUTH:
        if (this.motionZ < 0.0D)
          this.motionZ = -this.motionZ * bound; 
        break;
      case WEST:
        if (this.motionX > 0.0D)
          this.motionX = -this.motionX * bound; 
        break;
      case EAST:
        if (this.motionX < 0.0D)
          this.motionX = -this.motionX * bound; 
        break;
    } 
  }
  
  protected void onImpact(RayTraceResult m) {
    if (getInfo() != null)
      this.lastOnImpact = m; 
  }
  
  @Nullable
  public MCH_ThrowableInfo getInfo() {
    return this.throwableInfo;
  }
  
  public void setInfo(MCH_ThrowableInfo info) {
    this.throwableInfo = info;
    if (info != null)
      if (!this.world.isRemote)
        this.dataManager.set(INFO_NAME, info.name);  
  }
  
  public void setThrower(Entity entity) {
    if (entity instanceof EntityLivingBase)
      this.thrower = (EntityLivingBase)entity; 
  }
}
