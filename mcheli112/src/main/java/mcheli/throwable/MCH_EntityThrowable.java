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
  private static final DataParameter<String> INFO_NAME = EntityDataManager.func_187226_a(MCH_EntityThrowable.class, DataSerializers.field_187194_d);
  
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
    this.field_70159_w *= acceleration;
    this.field_70181_x *= acceleration;
    this.field_70179_y *= acceleration;
    init();
  }
  
  public MCH_EntityThrowable(World par1World, double par2, double par4, double par6) {
    super(par1World, par2, par4, par6);
    init();
  }
  
  public MCH_EntityThrowable(World worldIn, double x, double y, double z, float yaw, float pitch) {
    this(worldIn);
    func_70105_a(0.25F, 0.25F);
    func_70012_b(x, y, z, yaw, pitch);
    this.field_70165_t -= (MathHelper.func_76134_b(this.field_70177_z / 180.0F * 3.1415927F) * 0.16F);
    this.field_70163_u -= 0.10000000149011612D;
    this.field_70161_v -= (MathHelper.func_76126_a(this.field_70177_z / 180.0F * 3.1415927F) * 0.16F);
    func_70107_b(this.field_70165_t, this.field_70163_u, this.field_70161_v);
    func_184538_a((Entity)null, pitch, yaw, 0.0F, 1.5F, 1.0F);
  }
  
  public void init() {
    this.lastOnImpact = null;
    this.countOnUpdate = 0;
    setInfo((MCH_ThrowableInfo)null);
    this.noInfoCount = 0;
    this.field_70180_af.func_187214_a(INFO_NAME, "");
  }
  
  public void func_184538_a(Entity entityThrower, float rotationPitchIn, float rotationYawIn, float pitchOffset, float velocity, float inaccuracy) {
    float f = 0.4F;
    this
      .field_70159_w = (-MathHelper.func_76126_a(rotationYawIn / 180.0F * 3.1415927F) * MathHelper.func_76134_b(rotationPitchIn / 180.0F * 3.1415927F) * f);
    this
      .field_70179_y = (MathHelper.func_76134_b(rotationYawIn / 180.0F * 3.1415927F) * MathHelper.func_76134_b(rotationPitchIn / 180.0F * 3.1415927F) * f);
    this.field_70181_x = (-MathHelper.func_76126_a((rotationPitchIn + pitchOffset) / 180.0F * 3.1415927F) * f);
    func_70186_c(this.field_70159_w, this.field_70181_x, this.field_70179_y, velocity, 1.0F);
  }
  
  public void func_70106_y() {
    String s = (getInfo() != null) ? (getInfo()).name : "null";
    MCH_Lib.DbgLog(this.field_70170_p, "MCH_EntityThrowable.setDead(%s)", new Object[] { s });
    super.func_70106_y();
  }
  
  public void func_70071_h_() {
    this.boundPosX = this.field_70165_t;
    this.boundPosY = this.field_70163_u;
    this.boundPosZ = this.field_70161_v;
    if (getInfo() != null) {
      Block block = W_WorldFunc.getBlock(this.field_70170_p, (int)(this.field_70165_t + 0.5D), (int)this.field_70163_u, (int)(this.field_70161_v + 0.5D));
      Material mat = W_WorldFunc.getBlockMaterial(this.field_70170_p, (int)(this.field_70165_t + 0.5D), (int)this.field_70163_u, (int)(this.field_70161_v + 0.5D));
      if (block != null && mat == Material.field_151586_h) {
        this.field_70181_x += (getInfo()).gravityInWater;
      } else {
        this.field_70181_x += (getInfo()).gravity;
      } 
    } 
    super.func_70071_h_();
    if (this.lastOnImpact != null) {
      boundBullet(this.lastOnImpact);
      func_70107_b(this.boundPosX + this.field_70159_w, this.boundPosY + this.field_70181_x, this.boundPosZ + this.field_70179_y);
      this.lastOnImpact = null;
    } 
    this.countOnUpdate++;
    if (this.countOnUpdate >= 2147483632) {
      func_70106_y();
      return;
    } 
    if (getInfo() == null) {
      String s = (String)this.field_70180_af.func_187225_a(INFO_NAME);
      if (!s.isEmpty())
        setInfo(MCH_ThrowableInfoManager.get(s)); 
      if (getInfo() == null) {
        this.noInfoCount++;
        if (this.noInfoCount > 10)
          func_70106_y(); 
        return;
      } 
    } 
    if (this.field_70128_L)
      return; 
    if (!this.field_70170_p.field_72995_K) {
      if (this.countOnUpdate == (getInfo()).timeFuse)
        if ((getInfo()).explosion > 0) {
          MCH_Explosion.newExplosion(this.field_70170_p, null, null, this.field_70165_t, this.field_70163_u, this.field_70161_v, 
              (getInfo()).explosion, (getInfo()).explosion, true, true, false, true, 0);
          func_70106_y();
          return;
        }  
      if (this.countOnUpdate >= (getInfo()).aliveTime)
        func_70106_y(); 
    } else if (this.countOnUpdate >= (getInfo()).timeFuse) {
      if ((getInfo()).explosion <= 0)
        for (int i = 0; i < (getInfo()).smokeNum; i++) {
          float r = (getInfo()).smokeColor.r * 0.9F + this.field_70146_Z.nextFloat() * 0.1F;
          float g = (getInfo()).smokeColor.g * 0.9F + this.field_70146_Z.nextFloat() * 0.1F;
          float b = (getInfo()).smokeColor.b * 0.9F + this.field_70146_Z.nextFloat() * 0.1F;
          if ((getInfo()).smokeColor.r == (getInfo()).smokeColor.g)
            g = r; 
          if ((getInfo()).smokeColor.r == (getInfo()).smokeColor.b)
            b = r; 
          if ((getInfo()).smokeColor.g == (getInfo()).smokeColor.b)
            b = g; 
          spawnParticle("explode", 4, 
              (getInfo()).smokeSize + this.field_70146_Z.nextFloat() * (getInfo()).smokeSize / 3.0F, r, g, b, 
              (getInfo()).smokeVelocityHorizontal * (this.field_70146_Z.nextFloat() - 0.5F), 
              (getInfo()).smokeVelocityVertical * this.field_70146_Z.nextFloat(), 
              (getInfo()).smokeVelocityHorizontal * (this.field_70146_Z.nextFloat() - 0.5F));
        }  
    } 
  }
  
  public void spawnParticle(String name, int num, float size, float r, float g, float b, float mx, float my, float mz) {
    if (this.field_70170_p.field_72995_K) {
      if (name.isEmpty() || num < 1)
        return; 
      double x = (this.field_70165_t - this.field_70169_q) / num;
      double y = (this.field_70163_u - this.field_70167_r) / num;
      double z = (this.field_70161_v - this.field_70166_s) / num;
      for (int i = 0; i < num; i++) {
        MCH_ParticleParam prm = new MCH_ParticleParam(this.field_70170_p, "smoke", this.field_70169_q + x * i, 1.0D + this.field_70167_r + y * i, this.field_70166_s + z * i);
        prm.setMotion(mx, my, mz);
        prm.size = size;
        prm.setColor(1.0F, r, g, b);
        prm.isEffectWind = true;
        prm.toWhite = true;
        MCH_ParticlesUtil.spawnParticle(prm);
      } 
    } 
  }
  
  protected float func_70185_h() {
    return 0.0F;
  }
  
  public void boundBullet(RayTraceResult m) {
    if (m.field_178784_b == null)
      return; 
    float bound = (getInfo()).bound;
    switch (m.field_178784_b) {
      case DOWN:
      case UP:
        this.field_70159_w *= 0.8999999761581421D;
        this.field_70179_y *= 0.8999999761581421D;
        this.boundPosY = m.field_72307_f.field_72448_b;
        if ((m.field_178784_b == EnumFacing.DOWN && this.field_70181_x > 0.0D) || (m.field_178784_b == EnumFacing.UP && this.field_70181_x < 0.0D)) {
          this.field_70181_x = -this.field_70181_x * bound;
          break;
        } 
        this.field_70181_x = 0.0D;
        break;
      case NORTH:
        if (this.field_70179_y > 0.0D)
          this.field_70179_y = -this.field_70179_y * bound; 
        break;
      case SOUTH:
        if (this.field_70179_y < 0.0D)
          this.field_70179_y = -this.field_70179_y * bound; 
        break;
      case WEST:
        if (this.field_70159_w > 0.0D)
          this.field_70159_w = -this.field_70159_w * bound; 
        break;
      case EAST:
        if (this.field_70159_w < 0.0D)
          this.field_70159_w = -this.field_70159_w * bound; 
        break;
    } 
  }
  
  protected void func_70184_a(RayTraceResult m) {
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
      if (!this.field_70170_p.field_72995_K)
        this.field_70180_af.func_187227_b(INFO_NAME, info.name);  
  }
  
  public void setThrower(Entity entity) {
    if (entity instanceof EntityLivingBase)
      this.field_70192_c = (EntityLivingBase)entity; 
  }
}
