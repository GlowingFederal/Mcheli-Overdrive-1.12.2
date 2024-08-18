package mcheli.weapon;

import mcheli.MCH_Config;
import mcheli.particles.MCH_ParticleParam;
import mcheli.particles.MCH_ParticlesUtil;
import mcheli.wrapper.W_Blocks;
import mcheli.wrapper.W_MovingObjectPosition;
import mcheli.wrapper.W_WorldFunc;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class MCH_EntityMarkerRocket extends MCH_EntityBaseBullet {
  private static final DataParameter<Byte> MARKER_STATUS = EntityDataManager.func_187226_a(MCH_EntityMarkerRocket.class, DataSerializers.field_187191_a);
  
  public int countDown;
  
  public MCH_EntityMarkerRocket(World par1World) {
    super(par1World);
    setMarkerStatus(0);
    this.countDown = 0;
  }
  
  public MCH_EntityMarkerRocket(World par1World, double posX, double posY, double posZ, double targetX, double targetY, double targetZ, float yaw, float pitch, double acceleration) {
    super(par1World, posX, posY, posZ, targetX, targetY, targetZ, yaw, pitch, acceleration);
    setMarkerStatus(0);
    this.countDown = 0;
  }
  
  protected void func_70088_a() {
    super.func_70088_a();
    this.field_70180_af.func_187214_a(MARKER_STATUS, Byte.valueOf((byte)0));
  }
  
  public void setMarkerStatus(int n) {
    if (!this.field_70170_p.field_72995_K)
      this.field_70180_af.func_187227_b(MARKER_STATUS, Byte.valueOf((byte)n)); 
  }
  
  public int getMarkerStatus() {
    return ((Byte)this.field_70180_af.func_187225_a(MARKER_STATUS)).byteValue();
  }
  
  public boolean func_70097_a(DamageSource par1DamageSource, float par2) {
    return false;
  }
  
  public void func_70071_h_() {
    int status = getMarkerStatus();
    if (this.field_70170_p.field_72995_K) {
      if (getInfo() == null)
        super.func_70071_h_(); 
      if (getInfo() != null && !(getInfo()).disableSmoke)
        if (status != 0)
          if (status == 1) {
            super.func_70071_h_();
            spawnParticle((getInfo()).trajectoryParticleName, 3, 5.0F * (getInfo()).smokeSize * 0.5F);
          } else {
            float gb = this.field_70146_Z.nextFloat() * 0.3F;
            spawnParticle("explode", 5, (10 + this.field_70146_Z.nextInt(4)), this.field_70146_Z.nextFloat() * 0.2F + 0.8F, gb, gb, (this.field_70146_Z
                .nextFloat() - 0.5F) * 0.7F, 0.3F + this.field_70146_Z.nextFloat() * 0.3F, (this.field_70146_Z
                .nextFloat() - 0.5F) * 0.7F);
          }   
    } else if (status == 0 || func_70090_H()) {
      func_70106_y();
    } else if (status == 1) {
      super.func_70071_h_();
    } else if (this.countDown > 0) {
      this.countDown--;
      if (this.countDown == 40) {
        int num = 6 + this.field_70146_Z.nextInt(2);
        for (int i = 0; i < num; i++) {
          MCH_EntityBomb e = new MCH_EntityBomb(this.field_70170_p, this.field_70165_t + ((this.field_70146_Z.nextFloat() - 0.5F) * 15.0F), (260.0F + this.field_70146_Z.nextFloat() * 10.0F + (i * 30)), this.field_70161_v + ((this.field_70146_Z.nextFloat() - 0.5F) * 15.0F), 0.0D, -0.5D, 0.0D, 0.0F, 90.0F, 4.0D);
          e.setName(func_70005_c_());
          e.explosionPower = 3 + this.field_70146_Z.nextInt(2);
          e.explosionPowerInWater = 0;
          e.setPower(30);
          e.piercing = 0;
          e.shootingAircraft = this.shootingAircraft;
          e.shootingEntity = this.shootingEntity;
          this.field_70170_p.func_72838_d((Entity)e);
        } 
      } 
    } else {
      func_70106_y();
    } 
  }
  
  public void spawnParticle(String name, int num, float size, float r, float g, float b, float mx, float my, float mz) {
    if (this.field_70170_p.field_72995_K) {
      if (name.isEmpty() || num < 1 || num > 50)
        return; 
      double x = (this.field_70165_t - this.field_70169_q) / num;
      double y = (this.field_70163_u - this.field_70167_r) / num;
      double z = (this.field_70161_v - this.field_70166_s) / num;
      for (int i = 0; i < num; i++) {
        MCH_ParticleParam prm = new MCH_ParticleParam(this.field_70170_p, "smoke", this.field_70169_q + x * i, this.field_70167_r + y * i, this.field_70166_s + z * i);
        prm.motionX = mx;
        prm.motionY = my;
        prm.motionZ = mz;
        prm.size = size + this.field_70146_Z.nextFloat();
        prm.setColor(1.0F, r, g, b);
        prm.isEffectWind = true;
        MCH_ParticlesUtil.spawnParticle(prm);
      } 
    } 
  }
  
  protected void onImpact(RayTraceResult m, float damageFactor) {
    if (this.field_70170_p.field_72995_K)
      return; 
    if (m.field_72308_g != null || W_MovingObjectPosition.isHitTypeEntity(m))
      return; 
    BlockPos blockpos = m.func_178782_a();
    blockpos = blockpos.func_177972_a(m.field_178784_b);
    if (this.field_70170_p.func_175623_d(blockpos)) {
      if (MCH_Config.Explosion_FlamingBlock.prmBool)
        W_WorldFunc.setBlock(this.field_70170_p, blockpos, (Block)W_Blocks.field_150480_ab); 
      int noAirBlockCount = 0;
      for (int i = 1; i < 256; i++) {
        if (!this.field_70170_p.func_175623_d(blockpos.func_177981_b(i))) {
          noAirBlockCount++;
          if (noAirBlockCount >= 5)
            break; 
        } 
      } 
      if (noAirBlockCount < 5) {
        setMarkerStatus(2);
        func_70107_b(blockpos.func_177958_n() + 0.5D, blockpos.func_177956_o() + 1.1D, blockpos.func_177952_p() + 0.5D);
        this.field_70169_q = this.field_70165_t;
        this.field_70167_r = this.field_70163_u;
        this.field_70166_s = this.field_70161_v;
        this.countDown = 100;
      } else {
        func_70106_y();
      } 
    } else {
      func_70106_y();
    } 
  }
  
  public MCH_BulletModel getDefaultBulletModel() {
    return MCH_DefaultBulletModels.Rocket;
  }
}
