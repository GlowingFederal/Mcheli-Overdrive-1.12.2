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
  private static final DataParameter<Integer> TARGET_ID = EntityDataManager.createKey(MCH_EntityBaseBullet.class, DataSerializers.VARINT);
  
  private static final DataParameter<String> INFO_NAME = EntityDataManager.createKey(MCH_EntityBaseBullet.class, DataSerializers.STRING);
  
  private static final DataParameter<String> BULLET_MODEL = EntityDataManager.createKey(MCH_EntityBaseBullet.class, DataSerializers.STRING);
  
  private static final DataParameter<Byte> BOMBLET_FLAG = EntityDataManager.createKey(MCH_EntityBaseBullet.class, DataSerializers.BYTE);
  
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
    setSize(1.0F, 1.0F);
    this.prevRotationYaw = this.rotationYaw;
    this.prevRotationPitch = this.rotationPitch;
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
    this.ignoreFrustumCheck = true;
    if (par1World.isRemote)
      this.model = null; 
  }
  
  public MCH_EntityBaseBullet(World par1World, double px, double py, double pz, double mx, double my, double mz, float yaw, float pitch, double acceleration) {
    this(par1World);
    setSize(1.0F, 1.0F);
    setLocationAndAngles(px, py, pz, yaw, pitch);
    setPosition(px, py, pz);
    this.prevRotationYaw = yaw;
    this.prevRotationPitch = pitch;
    if (acceleration > 3.9D)
      acceleration = 3.9D; 
    double d = MathHelper.sqrt(mx * mx + my * my + mz * mz);
    this.motionX = mx * acceleration / d;
    this.motionY = my * acceleration / d;
    this.motionZ = mz * acceleration / d;
    this.prevMotionX = this.motionX;
    this.prevMotionY = this.motionY;
    this.prevMotionZ = this.motionZ;
    this.acceleration = acceleration;
  }
  
  public void setLocationAndAngles(double par1, double par3, double par5, float par7, float par8) {
    super.setLocationAndAngles(par1, par3, par5, par7, par8);
    this.prevPosX2 = par1;
    this.prevPosY2 = par3;
    this.prevPosZ2 = par5;
  }
  
  public void setPositionAndRotation(double x, double y, double z, float yaw, float pitch) {
    super.setPositionAndRotation(x, y, z, yaw, pitch);
  }
  
  protected void setRotation(float yaw, float pitch) {
    super.setRotation(yaw, this.rotationPitch);
  }
  
  @SideOnly(Side.CLIENT)
  public void setPositionAndRotationDirect(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean teleport) {
    setPosition(x, (y + this.posY * 2.0D) / 3.0D, z);
    setRotation(yaw, pitch);
  }
  
  protected void entityInit() {
    super.entityInit();
    this.dataManager.register(TARGET_ID, Integer.valueOf(0));
    this.dataManager.register(INFO_NAME, "");
    this.dataManager.register(BULLET_MODEL, "");
    this.dataManager.register(BOMBLET_FLAG, Byte.valueOf((byte)0));
  }
  
  public void setName(String s) {
    if (s != null && !s.isEmpty()) {
      this.weaponInfo = MCH_WeaponInfoManager.get(s);
      if (this.weaponInfo != null) {
        if (!this.world.isRemote)
          this.dataManager.set(INFO_NAME, s); 
        onSetWeasponInfo();
      } 
    } 
  }
  
  public String getName() {
    return (String)this.dataManager.get(INFO_NAME);
  }
  
  @Nullable
  public MCH_WeaponInfo getInfo() {
    return this.weaponInfo;
  }
  
  public void onSetWeasponInfo() {
    if (!this.world.isRemote)
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
  
  public void setDead() {
    super.setDead();
  }
  
  public void setBomblet() {
    this.isBomblet = 1;
    this.sprinkleTime = 0;
    this.dataManager.set(BOMBLET_FLAG, Byte.valueOf((byte)1));
  }
  
  public byte getBomblet() {
    return ((Byte)this.dataManager.get(BOMBLET_FLAG)).byteValue();
  }
  
  public void setTargetEntity(@Nullable Entity entity) {
    this.targetEntity = entity;
    if (!this.world.isRemote) {
      if (this.targetEntity instanceof EntityPlayerMP) {
        MCH_Lib.DbgLog(this.world, "MCH_EntityBaseBullet.setTargetEntity alert" + this.targetEntity + " / " + this.targetEntity
            .getRidingEntity(), new Object[0]);
        if (this.targetEntity.getRidingEntity() != null && 
          !(this.targetEntity.getRidingEntity() instanceof MCH_EntityAircraft) && 
          !(this.targetEntity.getRidingEntity() instanceof mcheli.aircraft.MCH_EntitySeat))
          W_WorldFunc.MOD_playSoundAtEntity(this.targetEntity, "alert", 2.0F, 1.0F); 
      } 
      if (entity != null) {
        this.dataManager.set(TARGET_ID, Integer.valueOf(W_Entity.getEntityId(entity)));
      } else {
        this.dataManager.set(TARGET_ID, Integer.valueOf(0));
      } 
    } 
  }
  
  public int getTargetEntityID() {
    if (this.targetEntity != null)
      return W_Entity.getEntityId(this.targetEntity); 
    return ((Integer)this.dataManager.get(TARGET_ID)).intValue();
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
    if (this.world.isRemote) {
      if (name.isEmpty() || num < 1 || num > 50)
        return; 
      double x = (this.posX - this.prevPosX) / num;
      double y = (this.posY - this.prevPosY) / num;
      double z = (this.posZ - this.prevPosZ) / num;
      double x2 = (this.prevPosX - this.prevPosX2) / num;
      double y2 = (this.prevPosY - this.prevPosY2) / num;
      double z2 = (this.prevPosZ - this.prevPosZ2) / num;
      if (name.equals("explode")) {
        for (int i = 0; i < num; i++) {
          MCH_ParticleParam prm = new MCH_ParticleParam(this.world, "smoke", (this.prevPosX + x * i + this.prevPosX2 + x2 * i) / 2.0D, (this.prevPosY + y * i + this.prevPosY2 + y2 * i) / 2.0D, (this.prevPosZ + z * i + this.prevPosZ2 + z2 * i) / 2.0D);
          prm.size = size + this.rand.nextFloat();
          MCH_ParticlesUtil.spawnParticle(prm);
        } 
      } else {
        for (int i = 0; i < num; i++)
          MCH_ParticlesUtil.DEF_spawnParticle(name, (this.prevPosX + x * i + this.prevPosX2 + x2 * i) / 2.0D, (this.prevPosY + y * i + this.prevPosY2 + y2 * i) / 2.0D, (this.prevPosZ + z * i + this.prevPosZ2 + z2 * i) / 2.0D, 0.0D, 0.0D, 0.0D, 50.0F, new int[0]); 
      } 
    } 
  }
  
  public void DEF_spawnParticle(String name, int num, float size) {
    if (this.world.isRemote) {
      if (name.isEmpty() || num < 1 || num > 50)
        return; 
      double x = (this.posX - this.prevPosX) / num;
      double y = (this.posY - this.prevPosY) / num;
      double z = (this.posZ - this.prevPosZ) / num;
      double x2 = (this.prevPosX - this.prevPosX2) / num;
      double y2 = (this.prevPosY - this.prevPosY2) / num;
      double z2 = (this.prevPosZ - this.prevPosZ2) / num;
      for (int i = 0; i < num; i++)
        MCH_ParticlesUtil.DEF_spawnParticle(name, (this.prevPosX + x * i + this.prevPosX2 + x2 * i) / 2.0D, (this.prevPosY + y * i + this.prevPosY2 + y2 * i) / 2.0D, (this.prevPosZ + z * i + this.prevPosZ2 + z2 * i) / 2.0D, 0.0D, 0.0D, 0.0D, 150.0F, new int[0]); 
    } 
  }
  
  public int getCountOnUpdate() {
    return this.countOnUpdate;
  }
  
  public void clearCountOnUpdate() {
    this.countOnUpdate = 0;
  }
  
  @SideOnly(Side.CLIENT)
  public boolean isInRangeToRenderDist(double par1) {
    double d1 = getEntityBoundingBox().getAverageEdgeLength() * 4.0D;
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
    double d6 = MathHelper.sqrt(targetX * targetX + targetY * targetY + targetZ * targetZ);
    this.motionX = targetX * this.acceleration / d6;
    this.motionY = targetY * this.acceleration / d6;
    this.motionZ = targetZ * this.acceleration / d6;
  }
  
  public boolean usingFlareOfTarget(Entity entity) {
    if (getCountOnUpdate() % 3 == 0) {
      List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity((Entity)this, entity
          .getEntityBoundingBox().grow(15.0D, 15.0D, 15.0D));
      for (int i = 0; i < list.size(); i++) {
        if (((Entity)list.get(i)).getEntityData().getBoolean("FlareUsing"))
          return true; 
      } 
    } 
    return false;
  }
  
  public void guidanceToTarget(double targetPosX, double targetPosY, double targetPosZ) {
    guidanceToTarget(targetPosX, targetPosY, targetPosZ, 1.0F);
  }
  
  public void guidanceToTarget(double targetPosX, double targetPosY, double targetPosZ, float accelerationFactor) {
    double tx = targetPosX - this.posX;
    double ty = targetPosY - this.posY;
    double tz = targetPosZ - this.posZ;
    double d = MathHelper.sqrt(tx * tx + ty * ty + tz * tz);
    double mx = tx * this.acceleration / d;
    double my = ty * this.acceleration / d;
    double mz = tz * this.acceleration / d;
    this.motionX = (this.motionX * 6.0D + mx) / 7.0D;
    this.motionY = (this.motionY * 6.0D + my) / 7.0D;
    this.motionZ = (this.motionZ * 6.0D + mz) / 7.0D;
    double a = (float)Math.atan2(this.motionZ, this.motionX);
    this.rotationYaw = (float)(a * 180.0D / Math.PI) - 90.0F;
    double r = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
    this.rotationPitch = -((float)(Math.atan2(this.motionY, r) * 180.0D / Math.PI));
  }
  
  public boolean checkValid() {
    if (this.shootingEntity == null && this.shootingAircraft == null)
      return false; 
    Entity shooter = (this.shootingAircraft == null || !this.shootingAircraft.isDead || this.shootingEntity != null) ? this.shootingEntity : this.shootingAircraft;
    double x = this.posX - shooter.posX;
    double z = this.posZ - shooter.posZ;
    return (x * x + z * z < 3.38724E7D && this.posY > -10.0D);
  }
  
  public float getGravity() {
    return (getInfo() != null) ? (getInfo()).gravity : 0.0F;
  }
  
  public float getGravityInWater() {
    return (getInfo() != null) ? (getInfo()).gravityInWater : 0.0F;
  }
  
  public void onUpdate() {
    if (this.world.isRemote && this.countOnUpdate == 0) {
      int tgtEttId = getTargetEntityID();
      if (tgtEttId > 0)
        setTargetEntity(this.world.getEntityByID(tgtEttId)); 
    } 
    if (!this.world.isRemote && getCountOnUpdate() % 20 == 19 && this.targetEntity instanceof EntityPlayerMP) {
      MCH_Lib.DbgLog(this.world, "MCH_EntityBaseBullet.onUpdate alert" + this.targetEntity + " / " + this.targetEntity
          .getRidingEntity(), new Object[0]);
      if (this.targetEntity.getRidingEntity() != null && 
        !(this.targetEntity.getRidingEntity() instanceof MCH_EntityAircraft) && 
        !(this.targetEntity.getRidingEntity() instanceof mcheli.aircraft.MCH_EntitySeat))
        W_WorldFunc.MOD_playSoundAtEntity(this.targetEntity, "alert", 2.0F, 1.0F); 
    } 
    this.prevMotionX = this.motionX;
    this.prevMotionY = this.motionY;
    this.prevMotionZ = this.motionZ;
    this.countOnUpdate++;
    if (this.countOnUpdate > 10000000)
      clearCountOnUpdate(); 
    this.prevPosX2 = this.prevPosX;
    this.prevPosY2 = this.prevPosY;
    this.prevPosZ2 = this.prevPosZ;
    super.onUpdate();
    if (this.prevMotionX != this.motionX || this.prevMotionY != this.motionY || this.prevMotionZ != this.motionZ)
      if (this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ > 0.1D) {
        double a = (float)Math.atan2(this.motionZ, this.motionX);
        this.rotationYaw = (float)(a * 180.0D / Math.PI) - 90.0F;
        double r = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
        this.rotationPitch = -((float)(Math.atan2(this.motionY, r) * 180.0D / Math.PI));
      }  
    if (getInfo() == null) {
      if (this.countOnUpdate < 2) {
        setName(getName());
      } else {
        MCH_Lib.Log((Entity)this, "##### MCH_EntityBaseBullet onUpdate() Weapon info null %d, %s, Name=%s", new Object[] { Integer.valueOf(W_Entity.getEntityId((Entity)this)), getEntityName(), getName() });
        setDead();
        return;
      } 
      if (getInfo() == null)
        return; 
    } 
    if ((getInfo()).bound <= 0.0F && this.onGround) {
      this.motionX *= 0.9D;
      this.motionZ *= 0.9D;
    } 
    if (this.world.isRemote)
      if (this.isBomblet < 0)
        this.isBomblet = getBomblet();  
    if (!this.world.isRemote) {
      BlockPos blockpos = new BlockPos(this.posX, this.posY, this.posZ);
      if ((int)this.posY <= 255 && !this.world.isBlockLoaded(blockpos))
        if ((getInfo()).delayFuse > 0) {
          if (this.delayFuse == 0)
            this.delayFuse = (getInfo()).delayFuse; 
        } else {
          setDead();
          return;
        }  
      if (this.delayFuse > 0) {
        this.delayFuse--;
        if (this.delayFuse == 0) {
          onUpdateTimeout();
          setDead();
          return;
        } 
      } 
      if (!checkValid()) {
        setDead();
        return;
      } 
      if ((getInfo()).timeFuse > 0 && getCountOnUpdate() > (getInfo()).timeFuse) {
        onUpdateTimeout();
        setDead();
        return;
      } 
      if ((getInfo()).explosionAltitude > 0)
        if (MCH_Lib.getBlockIdY((Entity)this, 3, -(getInfo()).explosionAltitude) != 0) {
          RayTraceResult mop = new RayTraceResult(new Vec3d(this.posX, this.posY, this.posZ), EnumFacing.DOWN, new BlockPos(this.posX, this.posY, this.posZ));
          onImpact(mop, 1.0F);
        }  
    } 
    if (!isInWater()) {
      this.motionY += getGravity();
    } else {
      this.motionY += getGravityInWater();
    } 
    if (!this.isDead)
      onUpdateCollided(); 
    this.posX += this.motionX * this.accelerationFactor;
    this.posY += this.motionY * this.accelerationFactor;
    this.posZ += this.motionZ * this.accelerationFactor;
    if (this.world.isRemote)
      updateSplash(); 
    if (isInWater()) {
      float f3 = 0.25F;
      this.world.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX - this.motionX * f3, this.posY - this.motionY * f3, this.posZ - this.motionZ * f3, this.motionX, this.motionY, this.motionZ, new int[0]);
    } 
    setPosition(this.posX, this.posY, this.posZ);
  }
  
  public void updateSplash() {
    if (getInfo() == null)
      return; 
    if ((getInfo()).power <= 0)
      return; 
    if (!W_WorldFunc.isBlockWater(this.world, (int)(this.prevPosX + 0.5D), (int)(this.prevPosY + 0.5D), (int)(this.prevPosZ + 0.5D)))
      if (W_WorldFunc.isBlockWater(this.world, (int)(this.posX + 0.5D), (int)(this.posY + 0.5D), (int)(this.posZ + 0.5D))) {
        double x = this.posX - this.prevPosX;
        double y = this.posY - this.prevPosY;
        double z = this.posZ - this.prevPosZ;
        double d = Math.sqrt(x * x + y * y + z * z);
        if (d <= 0.15D)
          return; 
        x /= d;
        y /= d;
        z /= d;
        double px = this.prevPosX;
        double py = this.prevPosY;
        double pz = this.prevPosZ;
        for (int i = 0; i <= d; i++) {
          px += x;
          py += y;
          pz += z;
          if (W_WorldFunc.isBlockWater(this.world, (int)(px + 0.5D), (int)(py + 0.5D), (int)(pz + 0.5D))) {
            float pwr = ((getInfo()).power < 20) ? (getInfo()).power : 20.0F;
            int n = this.rand.nextInt(1 + (int)pwr / 3) + (int)pwr / 2 + 1;
            pwr *= 0.03F;
            for (int j = 0; j < n; j++) {
              MCH_ParticleParam prm = new MCH_ParticleParam(this.world, "splash", px, py + 0.5D, pz, pwr * (this.rand.nextDouble() - 0.5D) * 0.3D, pwr * (this.rand.nextDouble() * 0.5D + 0.5D) * 1.8D, pwr * (this.rand.nextDouble() - 0.5D) * 0.3D, pwr * 5.0F);
              MCH_ParticlesUtil.spawnParticle(prm);
            } 
            break;
          } 
        } 
      }  
  }
  
  public void onUpdateTimeout() {
    if (isInWater()) {
      if (this.explosionPowerInWater > 0)
        newExplosion(this.posX, this.posY, this.posZ, this.explosionPowerInWater, this.explosionPowerInWater, true); 
    } else if (this.explosionPower > 0) {
      newExplosion(this.posX, this.posY, this.posZ, this.explosionPower, (getInfo()).explosionBlock, false);
    } else if (this.explosionPower < 0) {
      playExplosionSound();
    } 
  }
  
  public void onUpdateBomblet() {
    if (!this.world.isRemote)
      if (this.sprinkleTime > 0 && !this.isDead) {
        this.sprinkleTime--;
        if (this.sprinkleTime == 0) {
          for (int i = 0; i < (getInfo()).bomblet; i++)
            sprinkleBomblet(); 
          setDead();
        } 
      }  
  }
  
  public void boundBullet(EnumFacing sideHit) {
    switch (sideHit) {
      case DOWN:
        if (this.motionY > 0.0D)
          this.motionY = -this.motionY * (getInfo()).bound; 
        break;
      case UP:
        if (this.motionY < 0.0D)
          this.motionY = -this.motionY * (getInfo()).bound; 
        break;
      case NORTH:
        if (this.motionZ > 0.0D) {
          this.motionZ = -this.motionZ * (getInfo()).bound;
          break;
        } 
        this.posZ += this.motionZ;
        break;
      case SOUTH:
        if (this.motionZ < 0.0D) {
          this.motionZ = -this.motionZ * (getInfo()).bound;
          break;
        } 
        this.posZ += this.motionZ;
        break;
      case WEST:
        if (this.motionX > 0.0D) {
          this.motionX = -this.motionX * (getInfo()).bound;
          break;
        } 
        this.posX += this.motionX;
        break;
      case EAST:
        if (this.motionX < 0.0D) {
          this.motionX = -this.motionX * (getInfo()).bound;
          break;
        } 
        this.posX += this.motionX;
        break;
    } 
    if ((getInfo()).bound <= 0.0F) {
      this.motionX *= 0.25D;
      this.motionY *= 0.25D;
      this.motionZ *= 0.25D;
    } 
  }
  
  protected void onUpdateCollided() {
    float damageFator = 1.0F;
    double mx = this.motionX * this.accelerationFactor;
    double my = this.motionY * this.accelerationFactor;
    double mz = this.motionZ * this.accelerationFactor;
    RayTraceResult m = null;
    for (int i = 0; i < 5; i++) {
      Vec3d vec3d1 = W_WorldFunc.getWorldVec3(this.world, this.posX, this.posY, this.posZ);
      Vec3d vec3d2 = W_WorldFunc.getWorldVec3(this.world, this.posX + mx, this.posY + my, this.posZ + mz);
      m = W_WorldFunc.clip(this.world, vec3d1, vec3d2);
      boolean continueClip = false;
      if (this.shootingEntity != null && W_MovingObjectPosition.isHitTypeTile(m)) {
        Block block = W_WorldFunc.getBlock(this.world, m.getBlockPos());
        if (MCH_Config.bulletBreakableBlocks.contains(block)) {
          W_WorldFunc.destroyBlock(this.world, m.getBlockPos(), true);
          continueClip = true;
        } 
      } 
      if (!continueClip)
        break; 
    } 
    Vec3d vec3 = W_WorldFunc.getWorldVec3(this.world, this.posX, this.posY, this.posZ);
    Vec3d vec31 = W_WorldFunc.getWorldVec3(this.world, this.posX + mx, this.posY + my, this.posZ + mz);
    if ((getInfo()).delayFuse > 0) {
      if (m != null) {
        boundBullet(m.sideHit);
        if (this.delayFuse == 0)
          this.delayFuse = (getInfo()).delayFuse; 
      } 
      return;
    } 
    if (m != null)
      vec31 = W_WorldFunc.getWorldVec3(this.world, m.hitVec.x, m.hitVec.y, m.hitVec.z); 
    Entity entity = null;
    List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity((Entity)this, 
        getEntityBoundingBox().expand(mx, my, mz).grow(21.0D, 21.0D, 21.0D));
    double d0 = 0.0D;
    for (int j = 0; j < list.size(); j++) {
      Entity entity1 = list.get(j);
      if (canBeCollidedEntity(entity1)) {
        float f = 0.3F;
        AxisAlignedBB axisalignedbb = entity1.getEntityBoundingBox().grow(f, f, f);
        RayTraceResult m1 = axisalignedbb.calculateIntercept(vec3, vec31);
        if (m1 != null) {
          double d1 = vec3.distanceTo(m1.hitVec);
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
    if (!entity.canBeCollidedWith())
      return false; 
    if (entity instanceof MCH_EntityBaseBullet) {
      if (this.world.isRemote)
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
    if (!this.world.isRemote) {
      if (m.entityHit != null) {
        onImpactEntity(m.entityHit, damageFactor);
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
          newExplosion(m.hitVec.x + dx, m.hitVec.y + dy, m.hitVec.z + dz, 1.0F, 1.0F, false); 
      } else {
        if (expPowerInWater == 0.0F) {
          if ((getInfo()).isFAE) {
            newFAExplosion(this.posX, this.posY, this.posZ, expPower, (getInfo()).explosionBlock);
          } else if (expPower > 0.0F) {
            newExplosion(m.hitVec.x + dx, m.hitVec.y + dy, m.hitVec.z + dz, expPower, 
                (getInfo()).explosionBlock, false);
          } else if (expPower < 0.0F) {
            playExplosionSound();
          } 
        } else if (m.entityHit != null) {
          if (isInWater()) {
            newExplosion(m.hitVec.x + dx, m.hitVec.y + dy, m.hitVec.z + dz, expPowerInWater, expPowerInWater, true);
          } else {
            newExplosion(m.hitVec.x + dx, m.hitVec.y + dy, m.hitVec.z + dz, expPower, 
                (getInfo()).explosionBlock, false);
          } 
        } else if (isInWater() || MCH_Lib.isBlockInWater(this.world, m.getBlockPos().getX(), m
            .getBlockPos().getY(), m.getBlockPos().getZ())) {
          newExplosion(m.getBlockPos().getX(), m.getBlockPos().getY(), m.getBlockPos().getZ(), expPowerInWater, expPowerInWater, true);
        } else if (expPower > 0.0F) {
          newExplosion(m.hitVec.x + dx, m.hitVec.y + dy, m.hitVec.z + dz, expPower, (getInfo()).explosionBlock, false);
        } else if (expPower < 0.0F) {
          playExplosionSound();
        } 
        setDead();
      } 
    } else if (getInfo() != null && ((getInfo()).explosion == 0 || (getInfo()).modeNum >= 2)) {
      if (W_MovingObjectPosition.isHitTypeTile(m)) {
        float p = (getInfo()).power;
        for (int i = 0; i < p / 3.0F; i++)
          MCH_ParticlesUtil.spawnParticleTileCrack(this.world, m.getBlockPos().getX(), m.getBlockPos().getY(), m
              .getBlockPos().getZ(), m.hitVec.x + (this.rand.nextFloat() - 0.5D) * p / 10.0D, m.hitVec.y + 0.1D, m.hitVec.z + (this.rand
              .nextFloat() - 0.5D) * p / 10.0D, -this.motionX * p / 2.0D, (p / 2.0F), -this.motionZ * p / 2.0D); 
      } 
    } 
  }
  
  public void onImpactEntity(Entity entity, float damageFactor) {
    if (!entity.isDead) {
      MCH_Lib.DbgLog(this.world, "MCH_EntityBaseBullet.onImpactEntity:Damage=%d:" + entity.getClass(), new Object[] { Integer.valueOf(getPower()) });
      MCH_Lib.applyEntityHurtResistantTimeConfig(entity);
      DamageSource ds = DamageSource.causeThrownDamage((Entity)this, this.shootingEntity);
      float damage = MCH_Config.applyDamageVsEntity(entity, ds, getPower() * damageFactor);
      damage *= (getInfo() != null) ? getInfo().getDamageFactor(entity) : 1.0F;
      entity.attackEntityFrom(ds, damage);
      if (this instanceof MCH_EntityBullet && entity instanceof net.minecraft.entity.passive.EntityVillager)
        if (this.shootingEntity != null && this.shootingEntity instanceof EntityPlayerMP && this.shootingEntity
          .getRidingEntity() instanceof mcheli.aircraft.MCH_EntitySeat)
          MCH_CriteriaTriggers.VILLAGER_HURT_BULLET.trigger((EntityPlayerMP)this.shootingEntity);  
      if (!entity.isDead);
    } 
    notifyHitBullet();
  }
  
  public void newFAExplosion(double x, double y, double z, float exp, float expBlock) {
    MCH_Explosion.ExplosionResult result = MCH_Explosion.newExplosion(this.world, (Entity)this, this.shootingEntity, x, y, z, exp, expBlock, true, true, 
        (getInfo()).flaming, false, 15);
    if (result != null && result.hitEntity)
      notifyHitBullet(); 
  }
  
  public void newExplosion(double x, double y, double z, float exp, float expBlock, boolean inWater) {
    MCH_Explosion.ExplosionResult result;
    if (!inWater) {
      result = MCH_Explosion.newExplosion(this.world, (Entity)this, this.shootingEntity, x, y, z, exp, expBlock, 
          (this.rand.nextInt(3) == 0), true, (getInfo()).flaming, true, 0, 
          (getInfo() != null) ? (getInfo()).damageFactor : null);
    } else {
      result = MCH_Explosion.newExplosionInWater(this.world, (Entity)this, this.shootingEntity, x, y, z, exp, expBlock, 
          (this.rand.nextInt(3) == 0), true, (getInfo()).flaming, true, 0, 
          (getInfo() != null) ? (getInfo()).damageFactor : null);
    } 
    if (result != null && result.hitEntity)
      notifyHitBullet(); 
  }
  
  public void playExplosionSound() {
    MCH_Explosion.playExplosionSound(this.world, this.posX, this.posY, this.posZ);
  }
  
  public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
    par1NBTTagCompound.setTag("direction", (NBTBase)newDoubleNBTList(new double[] { this.motionX, this.motionY, this.motionZ }));
    par1NBTTagCompound.setString("WeaponName", getName());
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
  
  public boolean attackEntityFrom(DamageSource ds, float par2) {
    if (isEntityInvulnerable(ds))
      return false; 
    if (!this.world.isRemote)
      if (par2 > 0.0F && ds.getDamageType().equalsIgnoreCase("thrown")) {
        markVelocityChanged();
        Vec3d pos = new Vec3d(this.posX, this.posY, this.posZ);
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
