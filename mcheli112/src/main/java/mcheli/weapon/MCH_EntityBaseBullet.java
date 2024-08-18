package mcheli.weapon;

import java.util.List;
import javax.annotation.Nullable;
import mcheli.MCH_Config;
import mcheli.MCH_Explosion;
import mcheli.MCH_Lib;
import mcheli.__helper.MCH_CriteriaTriggers;
import mcheli.aircraft.MCH_EntityAircraft;
import mcheli.aircraft.MCH_PacketNotifyHitBullet;
import mcheli.particles.MCH_ParticleParam;
import mcheli.particles.MCH_ParticlesUtil;
import mcheli.wrapper.W_Entity;
import mcheli.wrapper.W_EntityPlayer;
import mcheli.wrapper.W_MovingObjectPosition;
import mcheli.wrapper.W_WorldFunc;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class MCH_EntityBaseBullet extends W_Entity {
  private static final DataParameter<Integer> TARGET_ID = EntityDataManager.func_187226_a(MCH_EntityBaseBullet.class, DataSerializers.field_187192_b);
  
  private static final DataParameter<String> INFO_NAME = EntityDataManager.func_187226_a(MCH_EntityBaseBullet.class, DataSerializers.field_187194_d);
  
  private static final DataParameter<String> BULLET_MODEL = EntityDataManager.func_187226_a(MCH_EntityBaseBullet.class, DataSerializers.field_187194_d);
  
  private static final DataParameter<Byte> BOMBLET_FLAG = EntityDataManager.func_187226_a(MCH_EntityBaseBullet.class, DataSerializers.field_187191_a);
  
  public Entity shootingEntity;
  
  public Entity shootingAircraft;
  
  private int countOnUpdate = 0;
  
  public int explosionPower;
  
  public int explosionPowerInWater;
  
  private int power;
  
  public double acceleration;
  
  public double accelerationFactor;
  
  public Entity targetEntity;
  
  public int piercing;
  
  public int delayFuse;
  
  public int sprinkleTime;
  
  public byte isBomblet;
  
  private MCH_WeaponInfo weaponInfo;
  
  private MCH_BulletModel model;
  
  public double prevPosX2;
  
  public double prevPosY2;
  
  public double prevPosZ2;
  
  public double prevMotionX;
  
  public double prevMotionY;
  
  public double prevMotionZ;
  
  public MCH_EntityBaseBullet(World par1World) {
    super(par1World);
    func_70105_a(1.0F, 1.0F);
    this.field_70126_B = this.field_70177_z;
    this.field_70127_C = this.field_70125_A;
    this.targetEntity = null;
    setPower(1);
    this.acceleration = 1.0D;
    this.accelerationFactor = 1.0D;
    this.piercing = 0;
    this.explosionPower = 0;
    this.explosionPowerInWater = 0;
    this.delayFuse = 0;
    this.sprinkleTime = 0;
    this.isBomblet = -1;
    this.weaponInfo = null;
    this.field_70158_ak = true;
    if (par1World.field_72995_K)
      this.model = null; 
  }
  
  public MCH_EntityBaseBullet(World par1World, double px, double py, double pz, double mx, double my, double mz, float yaw, float pitch, double acceleration) {
    this(par1World);
    func_70105_a(1.0F, 1.0F);
    func_70012_b(px, py, pz, yaw, pitch);
    func_70107_b(px, py, pz);
    this.field_70126_B = yaw;
    this.field_70127_C = pitch;
    if (acceleration > 3.9D)
      acceleration = 3.9D; 
    double d = MathHelper.func_76133_a(mx * mx + my * my + mz * mz);
    this.field_70159_w = mx * acceleration / d;
    this.field_70181_x = my * acceleration / d;
    this.field_70179_y = mz * acceleration / d;
    this.prevMotionX = this.field_70159_w;
    this.prevMotionY = this.field_70181_x;
    this.prevMotionZ = this.field_70179_y;
    this.acceleration = acceleration;
  }
  
  public void func_70012_b(double par1, double par3, double par5, float par7, float par8) {
    super.func_70012_b(par1, par3, par5, par7, par8);
    this.prevPosX2 = par1;
    this.prevPosY2 = par3;
    this.prevPosZ2 = par5;
  }
  
  public void func_70080_a(double x, double y, double z, float yaw, float pitch) {
    super.func_70080_a(x, y, z, yaw, pitch);
  }
  
  protected void func_70101_b(float yaw, float pitch) {
    super.func_70101_b(yaw, this.field_70125_A);
  }
  
  @SideOnly(Side.CLIENT)
  public void func_180426_a(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean teleport) {
    func_70107_b(x, (y + this.field_70163_u * 2.0D) / 3.0D, z);
    func_70101_b(yaw, pitch);
  }
  
  protected void func_70088_a() {
    super.func_70088_a();
    this.field_70180_af.func_187214_a(TARGET_ID, Integer.valueOf(0));
    this.field_70180_af.func_187214_a(INFO_NAME, "");
    this.field_70180_af.func_187214_a(BULLET_MODEL, "");
    this.field_70180_af.func_187214_a(BOMBLET_FLAG, Byte.valueOf((byte)0));
  }
  
  public void setName(String s) {
    if (s != null && !s.isEmpty()) {
      this.weaponInfo = MCH_WeaponInfoManager.get(s);
      if (this.weaponInfo != null) {
        if (!this.field_70170_p.field_72995_K)
          this.field_70180_af.func_187227_b(INFO_NAME, s); 
        onSetWeasponInfo();
      } 
    } 
  }
  
  public String func_70005_c_() {
    return (String)this.field_70180_af.func_187225_a(INFO_NAME);
  }
  
  @Nullable
  public MCH_WeaponInfo getInfo() {
    return this.weaponInfo;
  }
  
  public void onSetWeasponInfo() {
    if (!this.field_70170_p.field_72995_K)
      this.isBomblet = 0; 
    if ((getInfo()).bomblet > 0)
      this.sprinkleTime = (getInfo()).bombletSTime; 
    this.piercing = (getInfo()).piercing;
    if (this instanceof MCH_EntityBullet) {
      if ((getInfo()).acceleration > 4.0F)
        this.accelerationFactor = ((getInfo()).acceleration / 4.0F); 
    } else if (this instanceof MCH_EntityRocket) {
      if (this.isBomblet == 0 && (getInfo()).acceleration > 4.0F)
        this.accelerationFactor = ((getInfo()).acceleration / 4.0F); 
    } 
  }
  
  public void func_70106_y() {
    super.func_70106_y();
  }
  
  public void setBomblet() {
    this.isBomblet = 1;
    this.sprinkleTime = 0;
    this.field_70180_af.func_187227_b(BOMBLET_FLAG, Byte.valueOf((byte)1));
  }
  
  public byte getBomblet() {
    return ((Byte)this.field_70180_af.func_187225_a(BOMBLET_FLAG)).byteValue();
  }
  
  public void setTargetEntity(@Nullable Entity entity) {
    this.targetEntity = entity;
    if (!this.field_70170_p.field_72995_K) {
      if (this.targetEntity instanceof EntityPlayerMP) {
        MCH_Lib.DbgLog(this.field_70170_p, "MCH_EntityBaseBullet.setTargetEntity alert" + this.targetEntity + " / " + this.targetEntity
            .func_184187_bx(), new Object[0]);
        if (this.targetEntity.func_184187_bx() != null && 
          !(this.targetEntity.func_184187_bx() instanceof MCH_EntityAircraft) && 
          !(this.targetEntity.func_184187_bx() instanceof mcheli.aircraft.MCH_EntitySeat))
          W_WorldFunc.MOD_playSoundAtEntity(this.targetEntity, "alert", 2.0F, 1.0F); 
      } 
      if (entity != null) {
        this.field_70180_af.func_187227_b(TARGET_ID, Integer.valueOf(W_Entity.getEntityId(entity)));
      } else {
        this.field_70180_af.func_187227_b(TARGET_ID, Integer.valueOf(0));
      } 
    } 
  }
  
  public int getTargetEntityID() {
    if (this.targetEntity != null)
      return W_Entity.getEntityId(this.targetEntity); 
    return ((Integer)this.field_70180_af.func_187225_a(TARGET_ID)).intValue();
  }
  
  public MCH_BulletModel getBulletModel() {
    if (getInfo() == null)
      return null; 
    if (this.isBomblet < 0)
      return null; 
    if (this.model == null) {
      if (this.isBomblet == 1) {
        this.model = (getInfo()).bombletModel;
      } else {
        this.model = (getInfo()).bulletModel;
      } 
      if (this.model == null)
        this.model = getDefaultBulletModel(); 
    } 
    return this.model;
  }
  
  public abstract MCH_BulletModel getDefaultBulletModel();
  
  public void sprinkleBomblet() {}
  
  public void spawnParticle(String name, int num, float size) {
    if (this.field_70170_p.field_72995_K) {
      if (name.isEmpty() || num < 1 || num > 50)
        return; 
      double x = (this.field_70165_t - this.field_70169_q) / num;
      double y = (this.field_70163_u - this.field_70167_r) / num;
      double z = (this.field_70161_v - this.field_70166_s) / num;
      double x2 = (this.field_70169_q - this.prevPosX2) / num;
      double y2 = (this.field_70167_r - this.prevPosY2) / num;
      double z2 = (this.field_70166_s - this.prevPosZ2) / num;
      if (name.equals("explode")) {
        for (int i = 0; i < num; i++) {
          MCH_ParticleParam prm = new MCH_ParticleParam(this.field_70170_p, "smoke", (this.field_70169_q + x * i + this.prevPosX2 + x2 * i) / 2.0D, (this.field_70167_r + y * i + this.prevPosY2 + y2 * i) / 2.0D, (this.field_70166_s + z * i + this.prevPosZ2 + z2 * i) / 2.0D);
          prm.size = size + this.field_70146_Z.nextFloat();
          MCH_ParticlesUtil.spawnParticle(prm);
        } 
      } else {
        for (int i = 0; i < num; i++)
          MCH_ParticlesUtil.DEF_spawnParticle(name, (this.field_70169_q + x * i + this.prevPosX2 + x2 * i) / 2.0D, (this.field_70167_r + y * i + this.prevPosY2 + y2 * i) / 2.0D, (this.field_70166_s + z * i + this.prevPosZ2 + z2 * i) / 2.0D, 0.0D, 0.0D, 0.0D, 50.0F, new int[0]); 
      } 
    } 
  }
  
  public void DEF_spawnParticle(String name, int num, float size) {
    if (this.field_70170_p.field_72995_K) {
      if (name.isEmpty() || num < 1 || num > 50)
        return; 
      double x = (this.field_70165_t - this.field_70169_q) / num;
      double y = (this.field_70163_u - this.field_70167_r) / num;
      double z = (this.field_70161_v - this.field_70166_s) / num;
      double x2 = (this.field_70169_q - this.prevPosX2) / num;
      double y2 = (this.field_70167_r - this.prevPosY2) / num;
      double z2 = (this.field_70166_s - this.prevPosZ2) / num;
      for (int i = 0; i < num; i++)
        MCH_ParticlesUtil.DEF_spawnParticle(name, (this.field_70169_q + x * i + this.prevPosX2 + x2 * i) / 2.0D, (this.field_70167_r + y * i + this.prevPosY2 + y2 * i) / 2.0D, (this.field_70166_s + z * i + this.prevPosZ2 + z2 * i) / 2.0D, 0.0D, 0.0D, 0.0D, 150.0F, new int[0]); 
    } 
  }
  
  public int getCountOnUpdate() {
    return this.countOnUpdate;
  }
  
  public void clearCountOnUpdate() {
    this.countOnUpdate = 0;
  }
  
  @SideOnly(Side.CLIENT)
  public boolean func_70112_a(double par1) {
    double d1 = func_174813_aQ().func_72320_b() * 4.0D;
    d1 *= 64.0D;
    return (par1 < d1 * d1);
  }
  
  public void setParameterFromWeapon(MCH_WeaponBase w, Entity entity, Entity user) {
    this.explosionPower = w.explosionPower;
    this.explosionPowerInWater = w.explosionPowerInWater;
    setPower(w.power);
    this.piercing = w.piercing;
    this.shootingAircraft = entity;
    this.shootingEntity = user;
  }
  
  public void setParameterFromWeapon(MCH_EntityBaseBullet b, Entity entity, Entity user) {
    this.explosionPower = b.explosionPower;
    this.explosionPowerInWater = b.explosionPowerInWater;
    setPower(b.getPower());
    this.piercing = b.piercing;
    this.shootingAircraft = entity;
    this.shootingEntity = user;
  }
  
  public void setMotion(double targetX, double targetY, double targetZ) {
    double d6 = MathHelper.func_76133_a(targetX * targetX + targetY * targetY + targetZ * targetZ);
    this.field_70159_w = targetX * this.acceleration / d6;
    this.field_70181_x = targetY * this.acceleration / d6;
    this.field_70179_y = targetZ * this.acceleration / d6;
  }
  
  public boolean usingFlareOfTarget(Entity entity) {
    if (getCountOnUpdate() % 3 == 0) {
      List<Entity> list = this.field_70170_p.func_72839_b((Entity)this, entity
          .func_174813_aQ().func_72314_b(15.0D, 15.0D, 15.0D));
      for (int i = 0; i < list.size(); i++) {
        if (((Entity)list.get(i)).getEntityData().func_74767_n("FlareUsing"))
          return true; 
      } 
    } 
    return false;
  }
  
  public void guidanceToTarget(double targetPosX, double targetPosY, double targetPosZ) {
    guidanceToTarget(targetPosX, targetPosY, targetPosZ, 1.0F);
  }
  
  public void guidanceToTarget(double targetPosX, double targetPosY, double targetPosZ, float accelerationFactor) {
    double tx = targetPosX - this.field_70165_t;
    double ty = targetPosY - this.field_70163_u;
    double tz = targetPosZ - this.field_70161_v;
    double d = MathHelper.func_76133_a(tx * tx + ty * ty + tz * tz);
    double mx = tx * this.acceleration / d;
    double my = ty * this.acceleration / d;
    double mz = tz * this.acceleration / d;
    this.field_70159_w = (this.field_70159_w * 6.0D + mx) / 7.0D;
    this.field_70181_x = (this.field_70181_x * 6.0D + my) / 7.0D;
    this.field_70179_y = (this.field_70179_y * 6.0D + mz) / 7.0D;
    double a = (float)Math.atan2(this.field_70179_y, this.field_70159_w);
    this.field_70177_z = (float)(a * 180.0D / Math.PI) - 90.0F;
    double r = Math.sqrt(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
    this.field_70125_A = -((float)(Math.atan2(this.field_70181_x, r) * 180.0D / Math.PI));
  }
  
  public boolean checkValid() {
    if (this.shootingEntity == null && this.shootingAircraft == null)
      return false; 
    Entity shooter = (this.shootingAircraft == null || !this.shootingAircraft.field_70128_L || this.shootingEntity != null) ? this.shootingEntity : this.shootingAircraft;
    double x = this.field_70165_t - shooter.field_70165_t;
    double z = this.field_70161_v - shooter.field_70161_v;
    return (x * x + z * z < 3.38724E7D && this.field_70163_u > -10.0D);
  }
  
  public float getGravity() {
    return (getInfo() != null) ? (getInfo()).gravity : 0.0F;
  }
  
  public float getGravityInWater() {
    return (getInfo() != null) ? (getInfo()).gravityInWater : 0.0F;
  }
  
  public void func_70071_h_() {
    if (this.field_70170_p.field_72995_K && this.countOnUpdate == 0) {
      int tgtEttId = getTargetEntityID();
      if (tgtEttId > 0)
        setTargetEntity(this.field_70170_p.func_73045_a(tgtEttId)); 
    } 
    if (!this.field_70170_p.field_72995_K && getCountOnUpdate() % 20 == 19 && this.targetEntity instanceof EntityPlayerMP) {
      MCH_Lib.DbgLog(this.field_70170_p, "MCH_EntityBaseBullet.onUpdate alert" + this.targetEntity + " / " + this.targetEntity
          .func_184187_bx(), new Object[0]);
      if (this.targetEntity.func_184187_bx() != null && 
        !(this.targetEntity.func_184187_bx() instanceof MCH_EntityAircraft) && 
        !(this.targetEntity.func_184187_bx() instanceof mcheli.aircraft.MCH_EntitySeat))
        W_WorldFunc.MOD_playSoundAtEntity(this.targetEntity, "alert", 2.0F, 1.0F); 
    } 
    this.prevMotionX = this.field_70159_w;
    this.prevMotionY = this.field_70181_x;
    this.prevMotionZ = this.field_70179_y;
    this.countOnUpdate++;
    if (this.countOnUpdate > 10000000)
      clearCountOnUpdate(); 
    this.prevPosX2 = this.field_70169_q;
    this.prevPosY2 = this.field_70167_r;
    this.prevPosZ2 = this.field_70166_s;
    super.func_70071_h_();
    if (this.prevMotionX != this.field_70159_w || this.prevMotionY != this.field_70181_x || this.prevMotionZ != this.field_70179_y)
      if (this.field_70159_w * this.field_70159_w + this.field_70181_x * this.field_70181_x + this.field_70179_y * this.field_70179_y > 0.1D) {
        double a = (float)Math.atan2(this.field_70179_y, this.field_70159_w);
        this.field_70177_z = (float)(a * 180.0D / Math.PI) - 90.0F;
        double r = Math.sqrt(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
        this.field_70125_A = -((float)(Math.atan2(this.field_70181_x, r) * 180.0D / Math.PI));
      }  
    if (getInfo() == null) {
      if (this.countOnUpdate < 2) {
        setName(func_70005_c_());
      } else {
        MCH_Lib.Log((Entity)this, "##### MCH_EntityBaseBullet onUpdate() Weapon info null %d, %s, Name=%s", new Object[] { Integer.valueOf(W_Entity.getEntityId((Entity)this)), getEntityName(), func_70005_c_() });
        func_70106_y();
        return;
      } 
      if (getInfo() == null)
        return; 
    } 
    if ((getInfo()).bound <= 0.0F && this.field_70122_E) {
      this.field_70159_w *= 0.9D;
      this.field_70179_y *= 0.9D;
    } 
    if (this.field_70170_p.field_72995_K)
      if (this.isBomblet < 0)
        this.isBomblet = getBomblet();  
    if (!this.field_70170_p.field_72995_K) {
      BlockPos blockpos = new BlockPos(this.field_70165_t, this.field_70163_u, this.field_70161_v);
      if ((int)this.field_70163_u <= 255 && !this.field_70170_p.func_175667_e(blockpos))
        if ((getInfo()).delayFuse > 0) {
          if (this.delayFuse == 0)
            this.delayFuse = (getInfo()).delayFuse; 
        } else {
          func_70106_y();
          return;
        }  
      if (this.delayFuse > 0) {
        this.delayFuse--;
        if (this.delayFuse == 0) {
          onUpdateTimeout();
          func_70106_y();
          return;
        } 
      } 
      if (!checkValid()) {
        func_70106_y();
        return;
      } 
      if ((getInfo()).timeFuse > 0 && getCountOnUpdate() > (getInfo()).timeFuse) {
        onUpdateTimeout();
        func_70106_y();
        return;
      } 
      if ((getInfo()).explosionAltitude > 0)
        if (MCH_Lib.getBlockIdY((Entity)this, 3, -(getInfo()).explosionAltitude) != 0) {
          RayTraceResult mop = new RayTraceResult(new Vec3d(this.field_70165_t, this.field_70163_u, this.field_70161_v), EnumFacing.DOWN, new BlockPos(this.field_70165_t, this.field_70163_u, this.field_70161_v));
          onImpact(mop, 1.0F);
        }  
    } 
    if (!func_70090_H()) {
      this.field_70181_x += getGravity();
    } else {
      this.field_70181_x += getGravityInWater();
    } 
    if (!this.field_70128_L)
      onUpdateCollided(); 
    this.field_70165_t += this.field_70159_w * this.accelerationFactor;
    this.field_70163_u += this.field_70181_x * this.accelerationFactor;
    this.field_70161_v += this.field_70179_y * this.accelerationFactor;
    if (this.field_70170_p.field_72995_K)
      updateSplash(); 
    if (func_70090_H()) {
      float f3 = 0.25F;
      this.field_70170_p.func_175688_a(EnumParticleTypes.WATER_BUBBLE, this.field_70165_t - this.field_70159_w * f3, this.field_70163_u - this.field_70181_x * f3, this.field_70161_v - this.field_70179_y * f3, this.field_70159_w, this.field_70181_x, this.field_70179_y, new int[0]);
    } 
    func_70107_b(this.field_70165_t, this.field_70163_u, this.field_70161_v);
  }
  
  public void updateSplash() {
    if (getInfo() == null)
      return; 
    if ((getInfo()).power <= 0)
      return; 
    if (!W_WorldFunc.isBlockWater(this.field_70170_p, (int)(this.field_70169_q + 0.5D), (int)(this.field_70167_r + 0.5D), (int)(this.field_70166_s + 0.5D)))
      if (W_WorldFunc.isBlockWater(this.field_70170_p, (int)(this.field_70165_t + 0.5D), (int)(this.field_70163_u + 0.5D), (int)(this.field_70161_v + 0.5D))) {
        double x = this.field_70165_t - this.field_70169_q;
        double y = this.field_70163_u - this.field_70167_r;
        double z = this.field_70161_v - this.field_70166_s;
        double d = Math.sqrt(x * x + y * y + z * z);
        if (d <= 0.15D)
          return; 
        x /= d;
        y /= d;
        z /= d;
        double px = this.field_70169_q;
        double py = this.field_70167_r;
        double pz = this.field_70166_s;
        for (int i = 0; i <= d; i++) {
          px += x;
          py += y;
          pz += z;
          if (W_WorldFunc.isBlockWater(this.field_70170_p, (int)(px + 0.5D), (int)(py + 0.5D), (int)(pz + 0.5D))) {
            float pwr = ((getInfo()).power < 20) ? (getInfo()).power : 20.0F;
            int n = this.field_70146_Z.nextInt(1 + (int)pwr / 3) + (int)pwr / 2 + 1;
            pwr *= 0.03F;
            for (int j = 0; j < n; j++) {
              MCH_ParticleParam prm = new MCH_ParticleParam(this.field_70170_p, "splash", px, py + 0.5D, pz, pwr * (this.field_70146_Z.nextDouble() - 0.5D) * 0.3D, pwr * (this.field_70146_Z.nextDouble() * 0.5D + 0.5D) * 1.8D, pwr * (this.field_70146_Z.nextDouble() - 0.5D) * 0.3D, pwr * 5.0F);
              MCH_ParticlesUtil.spawnParticle(prm);
            } 
            break;
          } 
        } 
      }  
  }
  
  public void onUpdateTimeout() {
    if (func_70090_H()) {
      if (this.explosionPowerInWater > 0)
        newExplosion(this.field_70165_t, this.field_70163_u, this.field_70161_v, this.explosionPowerInWater, this.explosionPowerInWater, true); 
    } else if (this.explosionPower > 0) {
      newExplosion(this.field_70165_t, this.field_70163_u, this.field_70161_v, this.explosionPower, (getInfo()).explosionBlock, false);
    } else if (this.explosionPower < 0) {
      playExplosionSound();
    } 
  }
  
  public void onUpdateBomblet() {
    if (!this.field_70170_p.field_72995_K)
      if (this.sprinkleTime > 0 && !this.field_70128_L) {
        this.sprinkleTime--;
        if (this.sprinkleTime == 0) {
          for (int i = 0; i < (getInfo()).bomblet; i++)
            sprinkleBomblet(); 
          func_70106_y();
        } 
      }  
  }
  
  public void boundBullet(EnumFacing sideHit) {
    switch (sideHit) {
      case DOWN:
        if (this.field_70181_x > 0.0D)
          this.field_70181_x = -this.field_70181_x * (getInfo()).bound; 
        break;
      case UP:
        if (this.field_70181_x < 0.0D)
          this.field_70181_x = -this.field_70181_x * (getInfo()).bound; 
        break;
      case NORTH:
        if (this.field_70179_y > 0.0D) {
          this.field_70179_y = -this.field_70179_y * (getInfo()).bound;
          break;
        } 
        this.field_70161_v += this.field_70179_y;
        break;
      case SOUTH:
        if (this.field_70179_y < 0.0D) {
          this.field_70179_y = -this.field_70179_y * (getInfo()).bound;
          break;
        } 
        this.field_70161_v += this.field_70179_y;
        break;
      case WEST:
        if (this.field_70159_w > 0.0D) {
          this.field_70159_w = -this.field_70159_w * (getInfo()).bound;
          break;
        } 
        this.field_70165_t += this.field_70159_w;
        break;
      case EAST:
        if (this.field_70159_w < 0.0D) {
          this.field_70159_w = -this.field_70159_w * (getInfo()).bound;
          break;
        } 
        this.field_70165_t += this.field_70159_w;
        break;
    } 
    if ((getInfo()).bound <= 0.0F) {
      this.field_70159_w *= 0.25D;
      this.field_70181_x *= 0.25D;
      this.field_70179_y *= 0.25D;
    } 
  }
  
  protected void onUpdateCollided() {
    float damageFator = 1.0F;
    double mx = this.field_70159_w * this.accelerationFactor;
    double my = this.field_70181_x * this.accelerationFactor;
    double mz = this.field_70179_y * this.accelerationFactor;
    RayTraceResult m = null;
    for (int i = 0; i < 5; i++) {
      Vec3d vec3d1 = W_WorldFunc.getWorldVec3(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v);
      Vec3d vec3d2 = W_WorldFunc.getWorldVec3(this.field_70170_p, this.field_70165_t + mx, this.field_70163_u + my, this.field_70161_v + mz);
      m = W_WorldFunc.clip(this.field_70170_p, vec3d1, vec3d2);
      boolean continueClip = false;
      if (this.shootingEntity != null && W_MovingObjectPosition.isHitTypeTile(m)) {
        Block block = W_WorldFunc.getBlock(this.field_70170_p, m.func_178782_a());
        if (MCH_Config.bulletBreakableBlocks.contains(block)) {
          W_WorldFunc.destroyBlock(this.field_70170_p, m.func_178782_a(), true);
          continueClip = true;
        } 
      } 
      if (!continueClip)
        break; 
    } 
    Vec3d vec3 = W_WorldFunc.getWorldVec3(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v);
    Vec3d vec31 = W_WorldFunc.getWorldVec3(this.field_70170_p, this.field_70165_t + mx, this.field_70163_u + my, this.field_70161_v + mz);
    if ((getInfo()).delayFuse > 0) {
      if (m != null) {
        boundBullet(m.field_178784_b);
        if (this.delayFuse == 0)
          this.delayFuse = (getInfo()).delayFuse; 
      } 
      return;
    } 
    if (m != null)
      vec31 = W_WorldFunc.getWorldVec3(this.field_70170_p, m.field_72307_f.field_72450_a, m.field_72307_f.field_72448_b, m.field_72307_f.field_72449_c); 
    Entity entity = null;
    List<Entity> list = this.field_70170_p.func_72839_b((Entity)this, 
        func_174813_aQ().func_72321_a(mx, my, mz).func_72314_b(21.0D, 21.0D, 21.0D));
    double d0 = 0.0D;
    for (int j = 0; j < list.size(); j++) {
      Entity entity1 = list.get(j);
      if (canBeCollidedEntity(entity1)) {
        float f = 0.3F;
        AxisAlignedBB axisalignedbb = entity1.func_174813_aQ().func_72314_b(f, f, f);
        RayTraceResult m1 = axisalignedbb.func_72327_a(vec3, vec31);
        if (m1 != null) {
          double d1 = vec3.func_72438_d(m1.field_72307_f);
          if (d1 < d0 || d0 == 0.0D) {
            entity = entity1;
            d0 = d1;
          } 
        } 
      } 
    } 
    if (entity != null)
      m = new RayTraceResult(entity); 
    if (m != null)
      onImpact(m, damageFator); 
  }
  
  public boolean canBeCollidedEntity(Entity entity) {
    if (entity instanceof mcheli.chain.MCH_EntityChain)
      return false; 
    if (!entity.func_70067_L())
      return false; 
    if (entity instanceof MCH_EntityBaseBullet) {
      if (this.field_70170_p.field_72995_K)
        return false; 
      MCH_EntityBaseBullet blt = (MCH_EntityBaseBullet)entity;
      if (W_Entity.isEqual(blt.shootingAircraft, this.shootingAircraft))
        return false; 
      if (W_Entity.isEqual(blt.shootingEntity, this.shootingEntity))
        return false; 
    } 
    if (entity instanceof mcheli.aircraft.MCH_EntitySeat)
      return false; 
    if (entity instanceof mcheli.aircraft.MCH_EntityHitBox)
      return false; 
    if (W_Entity.isEqual(entity, this.shootingEntity))
      return false; 
    if (this.shootingAircraft instanceof MCH_EntityAircraft) {
      if (W_Entity.isEqual(entity, this.shootingAircraft))
        return false; 
      if (((MCH_EntityAircraft)this.shootingAircraft).isMountedEntity(entity))
        return false; 
    } 
    for (String s : MCH_Config.IgnoreBulletHitList) {
      if (entity.getClass().getName().toLowerCase().indexOf(s.toLowerCase()) >= 0)
        return false; 
    } 
    return true;
  }
  
  public void notifyHitBullet() {
    if (this.shootingAircraft instanceof MCH_EntityAircraft && W_EntityPlayer.isPlayer(this.shootingEntity))
      MCH_PacketNotifyHitBullet.send((MCH_EntityAircraft)this.shootingAircraft, (EntityPlayer)this.shootingEntity); 
    if (W_EntityPlayer.isPlayer(this.shootingEntity))
      MCH_PacketNotifyHitBullet.send(null, (EntityPlayer)this.shootingEntity); 
  }
  
  protected void onImpact(RayTraceResult m, float damageFactor) {
    if (!this.field_70170_p.field_72995_K) {
      if (m.field_72308_g != null) {
        onImpactEntity(m.field_72308_g, damageFactor);
        this.piercing = 0;
      } 
      float expPower = this.explosionPower * damageFactor;
      float expPowerInWater = this.explosionPowerInWater * damageFactor;
      double dx = 0.0D;
      double dy = 0.0D;
      double dz = 0.0D;
      if (this.piercing > 0) {
        this.piercing--;
        if (expPower > 0.0F)
          newExplosion(m.field_72307_f.field_72450_a + dx, m.field_72307_f.field_72448_b + dy, m.field_72307_f.field_72449_c + dz, 1.0F, 1.0F, false); 
      } else {
        if (expPowerInWater == 0.0F) {
          if ((getInfo()).isFAE) {
            newFAExplosion(this.field_70165_t, this.field_70163_u, this.field_70161_v, expPower, (getInfo()).explosionBlock);
          } else if (expPower > 0.0F) {
            newExplosion(m.field_72307_f.field_72450_a + dx, m.field_72307_f.field_72448_b + dy, m.field_72307_f.field_72449_c + dz, expPower, 
                (getInfo()).explosionBlock, false);
          } else if (expPower < 0.0F) {
            playExplosionSound();
          } 
        } else if (m.field_72308_g != null) {
          if (func_70090_H()) {
            newExplosion(m.field_72307_f.field_72450_a + dx, m.field_72307_f.field_72448_b + dy, m.field_72307_f.field_72449_c + dz, expPowerInWater, expPowerInWater, true);
          } else {
            newExplosion(m.field_72307_f.field_72450_a + dx, m.field_72307_f.field_72448_b + dy, m.field_72307_f.field_72449_c + dz, expPower, 
                (getInfo()).explosionBlock, false);
          } 
        } else if (func_70090_H() || MCH_Lib.isBlockInWater(this.field_70170_p, m.func_178782_a().func_177958_n(), m
            .func_178782_a().func_177956_o(), m.func_178782_a().func_177952_p())) {
          newExplosion(m.func_178782_a().func_177958_n(), m.func_178782_a().func_177956_o(), m.func_178782_a().func_177952_p(), expPowerInWater, expPowerInWater, true);
        } else if (expPower > 0.0F) {
          newExplosion(m.field_72307_f.field_72450_a + dx, m.field_72307_f.field_72448_b + dy, m.field_72307_f.field_72449_c + dz, expPower, (getInfo()).explosionBlock, false);
        } else if (expPower < 0.0F) {
          playExplosionSound();
        } 
        func_70106_y();
      } 
    } else if (getInfo() != null && ((getInfo()).explosion == 0 || (getInfo()).modeNum >= 2)) {
      if (W_MovingObjectPosition.isHitTypeTile(m)) {
        float p = (getInfo()).power;
        for (int i = 0; i < p / 3.0F; i++)
          MCH_ParticlesUtil.spawnParticleTileCrack(this.field_70170_p, m.func_178782_a().func_177958_n(), m.func_178782_a().func_177956_o(), m
              .func_178782_a().func_177952_p(), m.field_72307_f.field_72450_a + (this.field_70146_Z.nextFloat() - 0.5D) * p / 10.0D, m.field_72307_f.field_72448_b + 0.1D, m.field_72307_f.field_72449_c + (this.field_70146_Z
              .nextFloat() - 0.5D) * p / 10.0D, -this.field_70159_w * p / 2.0D, (p / 2.0F), -this.field_70179_y * p / 2.0D); 
      } 
    } 
  }
  
  public void onImpactEntity(Entity entity, float damageFactor) {
    if (!entity.field_70128_L) {
      MCH_Lib.DbgLog(this.field_70170_p, "MCH_EntityBaseBullet.onImpactEntity:Damage=%d:" + entity.getClass(), new Object[] { Integer.valueOf(getPower()) });
      MCH_Lib.applyEntityHurtResistantTimeConfig(entity);
      DamageSource ds = DamageSource.func_76356_a((Entity)this, this.shootingEntity);
      float damage = MCH_Config.applyDamageVsEntity(entity, ds, getPower() * damageFactor);
      damage *= (getInfo() != null) ? getInfo().getDamageFactor(entity) : 1.0F;
      entity.func_70097_a(ds, damage);
      if (this instanceof MCH_EntityBullet && entity instanceof net.minecraft.entity.passive.EntityVillager)
        if (this.shootingEntity != null && this.shootingEntity instanceof EntityPlayerMP && this.shootingEntity
          .func_184187_bx() instanceof mcheli.aircraft.MCH_EntitySeat)
          MCH_CriteriaTriggers.VILLAGER_HURT_BULLET.trigger((EntityPlayerMP)this.shootingEntity);  
      if (!entity.field_70128_L);
    } 
    notifyHitBullet();
  }
  
  public void newFAExplosion(double x, double y, double z, float exp, float expBlock) {
    MCH_Explosion.ExplosionResult result = MCH_Explosion.newExplosion(this.field_70170_p, (Entity)this, this.shootingEntity, x, y, z, exp, expBlock, true, true, 
        (getInfo()).flaming, false, 15);
    if (result != null && result.hitEntity)
      notifyHitBullet(); 
  }
  
  public void newExplosion(double x, double y, double z, float exp, float expBlock, boolean inWater) {
    MCH_Explosion.ExplosionResult result;
    if (!inWater) {
      result = MCH_Explosion.newExplosion(this.field_70170_p, (Entity)this, this.shootingEntity, x, y, z, exp, expBlock, 
          (this.field_70146_Z.nextInt(3) == 0), true, (getInfo()).flaming, true, 0, 
          (getInfo() != null) ? (getInfo()).damageFactor : null);
    } else {
      result = MCH_Explosion.newExplosionInWater(this.field_70170_p, (Entity)this, this.shootingEntity, x, y, z, exp, expBlock, 
          (this.field_70146_Z.nextInt(3) == 0), true, (getInfo()).flaming, true, 0, 
          (getInfo() != null) ? (getInfo()).damageFactor : null);
    } 
    if (result != null && result.hitEntity)
      notifyHitBullet(); 
  }
  
  public void playExplosionSound() {
    MCH_Explosion.playExplosionSound(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v);
  }
  
  public void func_70014_b(NBTTagCompound par1NBTTagCompound) {
    par1NBTTagCompound.func_74782_a("direction", (NBTBase)func_70087_a(new double[] { this.field_70159_w, this.field_70181_x, this.field_70179_y }));
    par1NBTTagCompound.func_74778_a("WeaponName", func_70005_c_());
  }
  
  public void func_70037_a(NBTTagCompound par1NBTTagCompound) {
    func_70106_y();
  }
  
  public boolean func_70067_L() {
    return true;
  }
  
  public float func_70111_Y() {
    return 1.0F;
  }
  
  public boolean func_70097_a(DamageSource ds, float par2) {
    if (func_180431_b(ds))
      return false; 
    if (!this.field_70170_p.field_72995_K)
      if (par2 > 0.0F && ds.func_76355_l().equalsIgnoreCase("thrown")) {
        func_70018_K();
        Vec3d pos = new Vec3d(this.field_70165_t, this.field_70163_u, this.field_70161_v);
        RayTraceResult m = new RayTraceResult(pos, EnumFacing.DOWN, new BlockPos(pos));
        onImpact(m, 1.0F);
        return true;
      }  
    return false;
  }
  
  @SideOnly(Side.CLIENT)
  public float getShadowSize() {
    return 0.0F;
  }
  
  public float getBrightness(float par1) {
    return 1.0F;
  }
  
  @SideOnly(Side.CLIENT)
  public int getBrightnessForRender(float par1) {
    return 15728880;
  }
  
  public int getPower() {
    return this.power;
  }
  
  public void setPower(int power) {
    this.power = power;
  }
}