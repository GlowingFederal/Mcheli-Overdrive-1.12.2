/*      */ package mcheli.weapon;
/*      */ 
/*      */ import java.util.List;
/*      */ import javax.annotation.Nullable;
/*      */ import mcheli.MCH_Config;
/*      */ import mcheli.MCH_Explosion;
/*      */ import mcheli.MCH_Lib;
/*      */ import mcheli.__helper.MCH_CriteriaTriggers;
/*      */ import mcheli.aircraft.MCH_EntityAircraft;
/*      */ import mcheli.aircraft.MCH_PacketNotifyHitBullet;
/*      */ import mcheli.particles.MCH_ParticleParam;
/*      */ import mcheli.particles.MCH_ParticlesUtil;
/*      */ import mcheli.wrapper.W_Entity;
/*      */ import mcheli.wrapper.W_EntityPlayer;
/*      */ import mcheli.wrapper.W_MovingObjectPosition;
/*      */ import mcheli.wrapper.W_WorldFunc;
/*      */ import net.minecraft.block.Block;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.entity.player.EntityPlayer;
/*      */ import net.minecraft.entity.player.EntityPlayerMP;
/*      */ import net.minecraft.nbt.NBTBase;
/*      */ import net.minecraft.nbt.NBTTagCompound;
/*      */ import net.minecraft.network.datasync.DataParameter;
/*      */ import net.minecraft.network.datasync.DataSerializers;
/*      */ import net.minecraft.network.datasync.EntityDataManager;
/*      */ import net.minecraft.util.DamageSource;
/*      */ import net.minecraft.util.EnumFacing;
/*      */ import net.minecraft.util.EnumParticleTypes;
/*      */ import net.minecraft.util.math.AxisAlignedBB;
/*      */ import net.minecraft.util.math.BlockPos;
/*      */ import net.minecraft.util.math.MathHelper;
/*      */ import net.minecraft.util.math.RayTraceResult;
/*      */ import net.minecraft.util.math.Vec3d;
/*      */ import net.minecraft.world.World;
/*      */ import net.minecraftforge.fml.relauncher.Side;
/*      */ import net.minecraftforge.fml.relauncher.SideOnly;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class MCH_EntityBaseBullet
/*      */   extends W_Entity
/*      */ {
/*   53 */   private static final DataParameter<Integer> TARGET_ID = EntityDataManager.func_187226_a(MCH_EntityBaseBullet.class, DataSerializers.field_187192_b);
/*      */   
/*   55 */   private static final DataParameter<String> INFO_NAME = EntityDataManager.func_187226_a(MCH_EntityBaseBullet.class, DataSerializers.field_187194_d);
/*      */   
/*   57 */   private static final DataParameter<String> BULLET_MODEL = EntityDataManager.func_187226_a(MCH_EntityBaseBullet.class, DataSerializers.field_187194_d);
/*      */   
/*   59 */   private static final DataParameter<Byte> BOMBLET_FLAG = EntityDataManager.func_187226_a(MCH_EntityBaseBullet.class, DataSerializers.field_187191_a);
/*      */ 
/*      */ 
/*      */   
/*      */   public Entity shootingEntity;
/*      */ 
/*      */ 
/*      */   
/*      */   public Entity shootingAircraft;
/*      */ 
/*      */   
/*   70 */   private int countOnUpdate = 0;
/*      */   
/*      */   public int explosionPower;
/*      */   
/*      */   public int explosionPowerInWater;
/*      */   
/*      */   private int power;
/*      */   public double acceleration;
/*      */   public double accelerationFactor;
/*      */   public Entity targetEntity;
/*      */   public int piercing;
/*      */   public int delayFuse;
/*      */   public int sprinkleTime;
/*      */   public byte isBomblet;
/*      */   private MCH_WeaponInfo weaponInfo;
/*      */   private MCH_BulletModel model;
/*      */   public double prevPosX2;
/*      */   public double prevPosY2;
/*      */   public double prevPosZ2;
/*      */   public double prevMotionX;
/*      */   public double prevMotionY;
/*      */   public double prevMotionZ;
/*      */   
/*      */   public MCH_EntityBaseBullet(World par1World) {
/*   94 */     super(par1World);
/*   95 */     func_70105_a(1.0F, 1.0F);
/*   96 */     this.field_70126_B = this.field_70177_z;
/*   97 */     this.field_70127_C = this.field_70125_A;
/*   98 */     this.targetEntity = null;
/*   99 */     setPower(1);
/*  100 */     this.acceleration = 1.0D;
/*  101 */     this.accelerationFactor = 1.0D;
/*  102 */     this.piercing = 0;
/*  103 */     this.explosionPower = 0;
/*  104 */     this.explosionPowerInWater = 0;
/*  105 */     this.delayFuse = 0;
/*  106 */     this.sprinkleTime = 0;
/*  107 */     this.isBomblet = -1;
/*  108 */     this.weaponInfo = null;
/*  109 */     this.field_70158_ak = true;
/*  110 */     if (par1World.field_72995_K)
/*      */     {
/*  112 */       this.model = null;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public MCH_EntityBaseBullet(World par1World, double px, double py, double pz, double mx, double my, double mz, float yaw, float pitch, double acceleration) {
/*  119 */     this(par1World);
/*  120 */     func_70105_a(1.0F, 1.0F);
/*  121 */     func_70012_b(px, py, pz, yaw, pitch);
/*  122 */     func_70107_b(px, py, pz);
/*  123 */     this.field_70126_B = yaw;
/*  124 */     this.field_70127_C = pitch;
/*      */ 
/*      */     
/*  127 */     if (acceleration > 3.9D)
/*      */     {
/*  129 */       acceleration = 3.9D;
/*      */     }
/*      */     
/*  132 */     double d = MathHelper.func_76133_a(mx * mx + my * my + mz * mz);
/*  133 */     this.field_70159_w = mx * acceleration / d;
/*  134 */     this.field_70181_x = my * acceleration / d;
/*  135 */     this.field_70179_y = mz * acceleration / d;
/*  136 */     this.prevMotionX = this.field_70159_w;
/*  137 */     this.prevMotionY = this.field_70181_x;
/*  138 */     this.prevMotionZ = this.field_70179_y;
/*  139 */     this.acceleration = acceleration;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void func_70012_b(double par1, double par3, double par5, float par7, float par8) {
/*  145 */     super.func_70012_b(par1, par3, par5, par7, par8);
/*  146 */     this.prevPosX2 = par1;
/*  147 */     this.prevPosY2 = par3;
/*  148 */     this.prevPosZ2 = par5;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void func_70080_a(double x, double y, double z, float yaw, float pitch) {
/*  154 */     super.func_70080_a(x, y, z, yaw, pitch);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void func_70101_b(float yaw, float pitch) {
/*  160 */     super.func_70101_b(yaw, this.field_70125_A);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @SideOnly(Side.CLIENT)
/*      */   public void func_180426_a(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean teleport) {
/*  169 */     func_70107_b(x, (y + this.field_70163_u * 2.0D) / 3.0D, z);
/*  170 */     func_70101_b(yaw, pitch);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void func_70088_a() {
/*  176 */     super.func_70088_a();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  181 */     this.field_70180_af.func_187214_a(TARGET_ID, Integer.valueOf(0));
/*  182 */     this.field_70180_af.func_187214_a(INFO_NAME, "");
/*  183 */     this.field_70180_af.func_187214_a(BULLET_MODEL, "");
/*  184 */     this.field_70180_af.func_187214_a(BOMBLET_FLAG, Byte.valueOf((byte)0));
/*      */   }
/*      */ 
/*      */   
/*      */   public void setName(String s) {
/*  189 */     if (s != null && !s.isEmpty()) {
/*      */       
/*  191 */       this.weaponInfo = MCH_WeaponInfoManager.get(s);
/*      */       
/*  193 */       if (this.weaponInfo != null) {
/*      */         
/*  195 */         if (!this.field_70170_p.field_72995_K)
/*      */         {
/*      */           
/*  198 */           this.field_70180_af.func_187227_b(INFO_NAME, s);
/*      */         }
/*      */         
/*  201 */         onSetWeasponInfo();
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String func_70005_c_() {
/*  210 */     return (String)this.field_70180_af.func_187225_a(INFO_NAME);
/*      */   }
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public MCH_WeaponInfo getInfo() {
/*  216 */     return this.weaponInfo;
/*      */   }
/*      */ 
/*      */   
/*      */   public void onSetWeasponInfo() {
/*  221 */     if (!this.field_70170_p.field_72995_K)
/*      */     {
/*  223 */       this.isBomblet = 0;
/*      */     }
/*      */     
/*  226 */     if ((getInfo()).bomblet > 0)
/*      */     {
/*  228 */       this.sprinkleTime = (getInfo()).bombletSTime;
/*      */     }
/*      */     
/*  231 */     this.piercing = (getInfo()).piercing;
/*      */     
/*  233 */     if (this instanceof MCH_EntityBullet) {
/*      */       
/*  235 */       if ((getInfo()).acceleration > 4.0F)
/*      */       {
/*  237 */         this.accelerationFactor = ((getInfo()).acceleration / 4.0F);
/*      */       }
/*      */     }
/*  240 */     else if (this instanceof MCH_EntityRocket) {
/*      */       
/*  242 */       if (this.isBomblet == 0 && (getInfo()).acceleration > 4.0F)
/*      */       {
/*  244 */         this.accelerationFactor = ((getInfo()).acceleration / 4.0F);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void func_70106_y() {
/*  252 */     super.func_70106_y();
/*      */   }
/*      */ 
/*      */   
/*      */   public void setBomblet() {
/*  257 */     this.isBomblet = 1;
/*  258 */     this.sprinkleTime = 0;
/*      */     
/*  260 */     this.field_70180_af.func_187227_b(BOMBLET_FLAG, Byte.valueOf((byte)1));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public byte getBomblet() {
/*  266 */     return ((Byte)this.field_70180_af.func_187225_a(BOMBLET_FLAG)).byteValue();
/*      */   }
/*      */ 
/*      */   
/*      */   public void setTargetEntity(@Nullable Entity entity) {
/*  271 */     this.targetEntity = entity;
/*      */     
/*  273 */     if (!this.field_70170_p.field_72995_K) {
/*      */       
/*  275 */       if (this.targetEntity instanceof EntityPlayerMP) {
/*      */         
/*  277 */         MCH_Lib.DbgLog(this.field_70170_p, "MCH_EntityBaseBullet.setTargetEntity alert" + this.targetEntity + " / " + this.targetEntity
/*  278 */             .func_184187_bx(), new Object[0]);
/*      */         
/*  280 */         if (this.targetEntity.func_184187_bx() != null && 
/*  281 */           !(this.targetEntity.func_184187_bx() instanceof MCH_EntityAircraft) && 
/*  282 */           !(this.targetEntity.func_184187_bx() instanceof mcheli.aircraft.MCH_EntitySeat))
/*      */         {
/*  284 */           W_WorldFunc.MOD_playSoundAtEntity(this.targetEntity, "alert", 2.0F, 1.0F);
/*      */         }
/*      */       } 
/*      */       
/*  288 */       if (entity != null) {
/*      */ 
/*      */         
/*  291 */         this.field_70180_af.func_187227_b(TARGET_ID, Integer.valueOf(W_Entity.getEntityId(entity)));
/*      */       
/*      */       }
/*      */       else {
/*      */         
/*  296 */         this.field_70180_af.func_187227_b(TARGET_ID, Integer.valueOf(0));
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public int getTargetEntityID() {
/*  303 */     if (this.targetEntity != null) {
/*  304 */       return W_Entity.getEntityId(this.targetEntity);
/*      */     }
/*      */     
/*  307 */     return ((Integer)this.field_70180_af.func_187225_a(TARGET_ID)).intValue();
/*      */   }
/*      */ 
/*      */   
/*      */   public MCH_BulletModel getBulletModel() {
/*  312 */     if (getInfo() == null) {
/*  313 */       return null;
/*      */     }
/*  315 */     if (this.isBomblet < 0)
/*      */     {
/*  317 */       return null;
/*      */     }
/*      */     
/*  320 */     if (this.model == null) {
/*      */       
/*  322 */       if (this.isBomblet == 1) {
/*      */         
/*  324 */         this.model = (getInfo()).bombletModel;
/*      */       }
/*      */       else {
/*      */         
/*  328 */         this.model = (getInfo()).bulletModel;
/*      */       } 
/*  330 */       if (this.model == null)
/*      */       {
/*  332 */         this.model = getDefaultBulletModel();
/*      */       }
/*      */     } 
/*      */     
/*  336 */     return this.model;
/*      */   }
/*      */ 
/*      */   
/*      */   public abstract MCH_BulletModel getDefaultBulletModel();
/*      */ 
/*      */   
/*      */   public void sprinkleBomblet() {}
/*      */ 
/*      */   
/*      */   public void spawnParticle(String name, int num, float size) {
/*  347 */     if (this.field_70170_p.field_72995_K) {
/*      */       
/*  349 */       if (name.isEmpty() || num < 1 || num > 50) {
/*      */         return;
/*      */       }
/*      */ 
/*      */       
/*  354 */       double x = (this.field_70165_t - this.field_70169_q) / num;
/*  355 */       double y = (this.field_70163_u - this.field_70167_r) / num;
/*  356 */       double z = (this.field_70161_v - this.field_70166_s) / num;
/*  357 */       double x2 = (this.field_70169_q - this.prevPosX2) / num;
/*  358 */       double y2 = (this.field_70167_r - this.prevPosY2) / num;
/*  359 */       double z2 = (this.field_70166_s - this.prevPosZ2) / num;
/*      */       
/*  361 */       if (name.equals("explode")) {
/*      */         
/*  363 */         for (int i = 0; i < num; i++)
/*      */         {
/*  365 */           MCH_ParticleParam prm = new MCH_ParticleParam(this.field_70170_p, "smoke", (this.field_70169_q + x * i + this.prevPosX2 + x2 * i) / 2.0D, (this.field_70167_r + y * i + this.prevPosY2 + y2 * i) / 2.0D, (this.field_70166_s + z * i + this.prevPosZ2 + z2 * i) / 2.0D);
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  370 */           prm.size = size + this.field_70146_Z.nextFloat();
/*      */           
/*  372 */           MCH_ParticlesUtil.spawnParticle(prm);
/*      */         }
/*      */       
/*      */       } else {
/*      */         
/*  377 */         for (int i = 0; i < num; i++)
/*      */         {
/*  379 */           MCH_ParticlesUtil.DEF_spawnParticle(name, (this.field_70169_q + x * i + this.prevPosX2 + x2 * i) / 2.0D, (this.field_70167_r + y * i + this.prevPosY2 + y2 * i) / 2.0D, (this.field_70166_s + z * i + this.prevPosZ2 + z2 * i) / 2.0D, 0.0D, 0.0D, 0.0D, 50.0F, new int[0]);
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void DEF_spawnParticle(String name, int num, float size) {
/*  390 */     if (this.field_70170_p.field_72995_K) {
/*      */       
/*  392 */       if (name.isEmpty() || num < 1 || num > 50) {
/*      */         return;
/*      */       }
/*  395 */       double x = (this.field_70165_t - this.field_70169_q) / num;
/*  396 */       double y = (this.field_70163_u - this.field_70167_r) / num;
/*  397 */       double z = (this.field_70161_v - this.field_70166_s) / num;
/*  398 */       double x2 = (this.field_70169_q - this.prevPosX2) / num;
/*  399 */       double y2 = (this.field_70167_r - this.prevPosY2) / num;
/*  400 */       double z2 = (this.field_70166_s - this.prevPosZ2) / num;
/*      */       
/*  402 */       for (int i = 0; i < num; i++)
/*      */       {
/*  404 */         MCH_ParticlesUtil.DEF_spawnParticle(name, (this.field_70169_q + x * i + this.prevPosX2 + x2 * i) / 2.0D, (this.field_70167_r + y * i + this.prevPosY2 + y2 * i) / 2.0D, (this.field_70166_s + z * i + this.prevPosZ2 + z2 * i) / 2.0D, 0.0D, 0.0D, 0.0D, 150.0F, new int[0]);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getCountOnUpdate() {
/*  413 */     return this.countOnUpdate;
/*      */   }
/*      */ 
/*      */   
/*      */   public void clearCountOnUpdate() {
/*  418 */     this.countOnUpdate = 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @SideOnly(Side.CLIENT)
/*      */   public boolean func_70112_a(double par1) {
/*  426 */     double d1 = func_174813_aQ().func_72320_b() * 4.0D;
/*  427 */     d1 *= 64.0D;
/*      */     
/*  429 */     return (par1 < d1 * d1);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setParameterFromWeapon(MCH_WeaponBase w, Entity entity, Entity user) {
/*  434 */     this.explosionPower = w.explosionPower;
/*  435 */     this.explosionPowerInWater = w.explosionPowerInWater;
/*  436 */     setPower(w.power);
/*  437 */     this.piercing = w.piercing;
/*  438 */     this.shootingAircraft = entity;
/*  439 */     this.shootingEntity = user;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setParameterFromWeapon(MCH_EntityBaseBullet b, Entity entity, Entity user) {
/*  444 */     this.explosionPower = b.explosionPower;
/*  445 */     this.explosionPowerInWater = b.explosionPowerInWater;
/*  446 */     setPower(b.getPower());
/*  447 */     this.piercing = b.piercing;
/*  448 */     this.shootingAircraft = entity;
/*  449 */     this.shootingEntity = user;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setMotion(double targetX, double targetY, double targetZ) {
/*  454 */     double d6 = MathHelper.func_76133_a(targetX * targetX + targetY * targetY + targetZ * targetZ);
/*      */     
/*  456 */     this.field_70159_w = targetX * this.acceleration / d6;
/*  457 */     this.field_70181_x = targetY * this.acceleration / d6;
/*  458 */     this.field_70179_y = targetZ * this.acceleration / d6;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean usingFlareOfTarget(Entity entity) {
/*  463 */     if (getCountOnUpdate() % 3 == 0) {
/*      */ 
/*      */       
/*  466 */       List<Entity> list = this.field_70170_p.func_72839_b((Entity)this, entity
/*  467 */           .func_174813_aQ().func_72314_b(15.0D, 15.0D, 15.0D));
/*      */       
/*  469 */       for (int i = 0; i < list.size(); i++) {
/*      */         
/*  471 */         if (((Entity)list.get(i)).getEntityData().func_74767_n("FlareUsing"))
/*      */         {
/*  473 */           return true;
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  478 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public void guidanceToTarget(double targetPosX, double targetPosY, double targetPosZ) {
/*  483 */     guidanceToTarget(targetPosX, targetPosY, targetPosZ, 1.0F);
/*      */   }
/*      */ 
/*      */   
/*      */   public void guidanceToTarget(double targetPosX, double targetPosY, double targetPosZ, float accelerationFactor) {
/*  488 */     double tx = targetPosX - this.field_70165_t;
/*  489 */     double ty = targetPosY - this.field_70163_u;
/*  490 */     double tz = targetPosZ - this.field_70161_v;
/*      */     
/*  492 */     double d = MathHelper.func_76133_a(tx * tx + ty * ty + tz * tz);
/*  493 */     double mx = tx * this.acceleration / d;
/*  494 */     double my = ty * this.acceleration / d;
/*  495 */     double mz = tz * this.acceleration / d;
/*      */     
/*  497 */     this.field_70159_w = (this.field_70159_w * 6.0D + mx) / 7.0D;
/*  498 */     this.field_70181_x = (this.field_70181_x * 6.0D + my) / 7.0D;
/*  499 */     this.field_70179_y = (this.field_70179_y * 6.0D + mz) / 7.0D;
/*      */     
/*  501 */     double a = (float)Math.atan2(this.field_70179_y, this.field_70159_w);
/*      */     
/*  503 */     this.field_70177_z = (float)(a * 180.0D / Math.PI) - 90.0F;
/*      */     
/*  505 */     double r = Math.sqrt(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
/*      */     
/*  507 */     this.field_70125_A = -((float)(Math.atan2(this.field_70181_x, r) * 180.0D / Math.PI));
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean checkValid() {
/*  512 */     if (this.shootingEntity == null && this.shootingAircraft == null)
/*      */     {
/*  514 */       return false;
/*      */     }
/*      */     
/*  517 */     Entity shooter = (this.shootingAircraft == null || !this.shootingAircraft.field_70128_L || this.shootingEntity != null) ? this.shootingEntity : this.shootingAircraft;
/*      */ 
/*      */ 
/*      */     
/*  521 */     double x = this.field_70165_t - shooter.field_70165_t;
/*  522 */     double z = this.field_70161_v - shooter.field_70161_v;
/*      */     
/*  524 */     return (x * x + z * z < 3.38724E7D && this.field_70163_u > -10.0D);
/*      */   }
/*      */ 
/*      */   
/*      */   public float getGravity() {
/*  529 */     return (getInfo() != null) ? (getInfo()).gravity : 0.0F;
/*      */   }
/*      */ 
/*      */   
/*      */   public float getGravityInWater() {
/*  534 */     return (getInfo() != null) ? (getInfo()).gravityInWater : 0.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void func_70071_h_() {
/*  540 */     if (this.field_70170_p.field_72995_K && this.countOnUpdate == 0) {
/*      */       
/*  542 */       int tgtEttId = getTargetEntityID();
/*      */       
/*  544 */       if (tgtEttId > 0)
/*      */       {
/*  546 */         setTargetEntity(this.field_70170_p.func_73045_a(tgtEttId));
/*      */       }
/*      */     } 
/*      */     
/*  550 */     if (!this.field_70170_p.field_72995_K && getCountOnUpdate() % 20 == 19 && this.targetEntity instanceof EntityPlayerMP) {
/*      */       
/*  552 */       MCH_Lib.DbgLog(this.field_70170_p, "MCH_EntityBaseBullet.onUpdate alert" + this.targetEntity + " / " + this.targetEntity
/*  553 */           .func_184187_bx(), new Object[0]);
/*      */       
/*  555 */       if (this.targetEntity.func_184187_bx() != null && 
/*  556 */         !(this.targetEntity.func_184187_bx() instanceof MCH_EntityAircraft) && 
/*  557 */         !(this.targetEntity.func_184187_bx() instanceof mcheli.aircraft.MCH_EntitySeat))
/*      */       {
/*  559 */         W_WorldFunc.MOD_playSoundAtEntity(this.targetEntity, "alert", 2.0F, 1.0F);
/*      */       }
/*      */     } 
/*      */     
/*  563 */     this.prevMotionX = this.field_70159_w;
/*  564 */     this.prevMotionY = this.field_70181_x;
/*  565 */     this.prevMotionZ = this.field_70179_y;
/*      */     
/*  567 */     this.countOnUpdate++;
/*      */     
/*  569 */     if (this.countOnUpdate > 10000000)
/*      */     {
/*  571 */       clearCountOnUpdate();
/*      */     }
/*      */     
/*  574 */     this.prevPosX2 = this.field_70169_q;
/*  575 */     this.prevPosY2 = this.field_70167_r;
/*  576 */     this.prevPosZ2 = this.field_70166_s;
/*      */     
/*  578 */     super.func_70071_h_();
/*      */     
/*  580 */     if (this.prevMotionX != this.field_70159_w || this.prevMotionY != this.field_70181_x || this.prevMotionZ != this.field_70179_y)
/*      */     {
/*  582 */       if (this.field_70159_w * this.field_70159_w + this.field_70181_x * this.field_70181_x + this.field_70179_y * this.field_70179_y > 0.1D) {
/*      */         
/*  584 */         double a = (float)Math.atan2(this.field_70179_y, this.field_70159_w);
/*      */         
/*  586 */         this.field_70177_z = (float)(a * 180.0D / Math.PI) - 90.0F;
/*      */         
/*  588 */         double r = Math.sqrt(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
/*      */         
/*  590 */         this.field_70125_A = -((float)(Math.atan2(this.field_70181_x, r) * 180.0D / Math.PI));
/*      */       } 
/*      */     }
/*      */     
/*  594 */     if (getInfo() == null) {
/*      */       
/*  596 */       if (this.countOnUpdate < 2) {
/*      */         
/*  598 */         setName(func_70005_c_());
/*      */       }
/*      */       else {
/*      */         
/*  602 */         MCH_Lib.Log((Entity)this, "##### MCH_EntityBaseBullet onUpdate() Weapon info null %d, %s, Name=%s", new Object[] {
/*      */               
/*  604 */               Integer.valueOf(W_Entity.getEntityId((Entity)this)), getEntityName(), func_70005_c_()
/*      */             });
/*      */         
/*  607 */         func_70106_y();
/*      */         
/*      */         return;
/*      */       } 
/*  611 */       if (getInfo() == null) {
/*      */         return;
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  617 */     if ((getInfo()).bound <= 0.0F && this.field_70122_E) {
/*      */       
/*  619 */       this.field_70159_w *= 0.9D;
/*  620 */       this.field_70179_y *= 0.9D;
/*      */     } 
/*      */     
/*  623 */     if (this.field_70170_p.field_72995_K)
/*      */     {
/*  625 */       if (this.isBomblet < 0)
/*      */       {
/*  627 */         this.isBomblet = getBomblet();
/*      */       }
/*      */     }
/*      */     
/*  631 */     if (!this.field_70170_p.field_72995_K) {
/*      */       
/*  633 */       BlockPos blockpos = new BlockPos(this.field_70165_t, this.field_70163_u, this.field_70161_v);
/*      */       
/*  635 */       if ((int)this.field_70163_u <= 255 && !this.field_70170_p.func_175667_e(blockpos))
/*      */       {
/*  637 */         if ((getInfo()).delayFuse > 0) {
/*      */           
/*  639 */           if (this.delayFuse == 0)
/*      */           {
/*  641 */             this.delayFuse = (getInfo()).delayFuse;
/*      */           }
/*      */         }
/*      */         else {
/*      */           
/*  646 */           func_70106_y();
/*      */           
/*      */           return;
/*      */         } 
/*      */       }
/*  651 */       if (this.delayFuse > 0) {
/*      */         
/*  653 */         this.delayFuse--;
/*      */         
/*  655 */         if (this.delayFuse == 0) {
/*      */           
/*  657 */           onUpdateTimeout();
/*  658 */           func_70106_y();
/*      */           
/*      */           return;
/*      */         } 
/*      */       } 
/*  663 */       if (!checkValid()) {
/*      */         
/*  665 */         func_70106_y();
/*      */         
/*      */         return;
/*      */       } 
/*  669 */       if ((getInfo()).timeFuse > 0 && getCountOnUpdate() > (getInfo()).timeFuse) {
/*      */         
/*  671 */         onUpdateTimeout();
/*  672 */         func_70106_y();
/*      */         
/*      */         return;
/*      */       } 
/*  676 */       if ((getInfo()).explosionAltitude > 0)
/*      */       {
/*  678 */         if (MCH_Lib.getBlockIdY((Entity)this, 3, -(getInfo()).explosionAltitude) != 0) {
/*      */ 
/*      */           
/*  681 */           RayTraceResult mop = new RayTraceResult(new Vec3d(this.field_70165_t, this.field_70163_u, this.field_70161_v), EnumFacing.DOWN, new BlockPos(this.field_70165_t, this.field_70163_u, this.field_70161_v));
/*      */ 
/*      */           
/*  684 */           onImpact(mop, 1.0F);
/*      */         } 
/*      */       }
/*      */     } 
/*      */     
/*  689 */     if (!func_70090_H()) {
/*      */       
/*  691 */       this.field_70181_x += getGravity();
/*      */     }
/*      */     else {
/*      */       
/*  695 */       this.field_70181_x += getGravityInWater();
/*      */     } 
/*      */     
/*  698 */     if (!this.field_70128_L)
/*      */     {
/*  700 */       onUpdateCollided();
/*      */     }
/*      */     
/*  703 */     this.field_70165_t += this.field_70159_w * this.accelerationFactor;
/*  704 */     this.field_70163_u += this.field_70181_x * this.accelerationFactor;
/*  705 */     this.field_70161_v += this.field_70179_y * this.accelerationFactor;
/*      */     
/*  707 */     if (this.field_70170_p.field_72995_K)
/*      */     {
/*  709 */       updateSplash();
/*      */     }
/*      */     
/*  712 */     if (func_70090_H()) {
/*      */       
/*  714 */       float f3 = 0.25F;
/*      */ 
/*      */       
/*  717 */       this.field_70170_p.func_175688_a(EnumParticleTypes.WATER_BUBBLE, this.field_70165_t - this.field_70159_w * f3, this.field_70163_u - this.field_70181_x * f3, this.field_70161_v - this.field_70179_y * f3, this.field_70159_w, this.field_70181_x, this.field_70179_y, new int[0]);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  722 */     func_70107_b(this.field_70165_t, this.field_70163_u, this.field_70161_v);
/*      */   }
/*      */ 
/*      */   
/*      */   public void updateSplash() {
/*  727 */     if (getInfo() == null) {
/*      */       return;
/*      */     }
/*  730 */     if ((getInfo()).power <= 0) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/*  735 */     if (!W_WorldFunc.isBlockWater(this.field_70170_p, (int)(this.field_70169_q + 0.5D), (int)(this.field_70167_r + 0.5D), (int)(this.field_70166_s + 0.5D)))
/*      */     {
/*      */       
/*  738 */       if (W_WorldFunc.isBlockWater(this.field_70170_p, (int)(this.field_70165_t + 0.5D), (int)(this.field_70163_u + 0.5D), (int)(this.field_70161_v + 0.5D))) {
/*      */ 
/*      */         
/*  741 */         double x = this.field_70165_t - this.field_70169_q;
/*  742 */         double y = this.field_70163_u - this.field_70167_r;
/*  743 */         double z = this.field_70161_v - this.field_70166_s;
/*  744 */         double d = Math.sqrt(x * x + y * y + z * z);
/*      */         
/*  746 */         if (d <= 0.15D) {
/*      */           return;
/*      */         }
/*      */ 
/*      */         
/*  751 */         x /= d;
/*  752 */         y /= d;
/*  753 */         z /= d;
/*  754 */         double px = this.field_70169_q;
/*  755 */         double py = this.field_70167_r;
/*  756 */         double pz = this.field_70166_s;
/*      */         
/*  758 */         for (int i = 0; i <= d; i++) {
/*      */           
/*  760 */           px += x;
/*  761 */           py += y;
/*  762 */           pz += z;
/*      */           
/*  764 */           if (W_WorldFunc.isBlockWater(this.field_70170_p, (int)(px + 0.5D), (int)(py + 0.5D), (int)(pz + 0.5D))) {
/*      */             
/*  766 */             float pwr = ((getInfo()).power < 20) ? (getInfo()).power : 20.0F;
/*  767 */             int n = this.field_70146_Z.nextInt(1 + (int)pwr / 3) + (int)pwr / 2 + 1;
/*  768 */             pwr *= 0.03F;
/*      */             
/*  770 */             for (int j = 0; j < n; j++) {
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  775 */               MCH_ParticleParam prm = new MCH_ParticleParam(this.field_70170_p, "splash", px, py + 0.5D, pz, pwr * (this.field_70146_Z.nextDouble() - 0.5D) * 0.3D, pwr * (this.field_70146_Z.nextDouble() * 0.5D + 0.5D) * 1.8D, pwr * (this.field_70146_Z.nextDouble() - 0.5D) * 0.3D, pwr * 5.0F);
/*      */               
/*  777 */               MCH_ParticlesUtil.spawnParticle(prm);
/*      */             } 
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void onUpdateTimeout() {
/*  788 */     if (func_70090_H()) {
/*      */       
/*  790 */       if (this.explosionPowerInWater > 0)
/*      */       {
/*  792 */         newExplosion(this.field_70165_t, this.field_70163_u, this.field_70161_v, this.explosionPowerInWater, this.explosionPowerInWater, true);
/*      */       
/*      */       }
/*      */     }
/*  796 */     else if (this.explosionPower > 0) {
/*      */       
/*  798 */       newExplosion(this.field_70165_t, this.field_70163_u, this.field_70161_v, this.explosionPower, (getInfo()).explosionBlock, false);
/*      */     }
/*  800 */     else if (this.explosionPower < 0) {
/*      */       
/*  802 */       playExplosionSound();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void onUpdateBomblet() {
/*  808 */     if (!this.field_70170_p.field_72995_K)
/*      */     {
/*  810 */       if (this.sprinkleTime > 0 && !this.field_70128_L) {
/*      */         
/*  812 */         this.sprinkleTime--;
/*      */         
/*  814 */         if (this.sprinkleTime == 0) {
/*      */           
/*  816 */           for (int i = 0; i < (getInfo()).bomblet; i++)
/*      */           {
/*  818 */             sprinkleBomblet();
/*      */           }
/*      */           
/*  821 */           func_70106_y();
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void boundBullet(EnumFacing sideHit) {
/*  830 */     switch (sideHit) {
/*      */ 
/*      */       
/*      */       case DOWN:
/*  834 */         if (this.field_70181_x > 0.0D) {
/*  835 */           this.field_70181_x = -this.field_70181_x * (getInfo()).bound;
/*      */         }
/*      */         break;
/*      */       case UP:
/*  839 */         if (this.field_70181_x < 0.0D) {
/*  840 */           this.field_70181_x = -this.field_70181_x * (getInfo()).bound;
/*      */         }
/*      */         break;
/*      */       case NORTH:
/*  844 */         if (this.field_70179_y > 0.0D) {
/*  845 */           this.field_70179_y = -this.field_70179_y * (getInfo()).bound; break;
/*      */         } 
/*  847 */         this.field_70161_v += this.field_70179_y;
/*      */         break;
/*      */       
/*      */       case SOUTH:
/*  851 */         if (this.field_70179_y < 0.0D) {
/*  852 */           this.field_70179_y = -this.field_70179_y * (getInfo()).bound; break;
/*      */         } 
/*  854 */         this.field_70161_v += this.field_70179_y;
/*      */         break;
/*      */       
/*      */       case WEST:
/*  858 */         if (this.field_70159_w > 0.0D) {
/*  859 */           this.field_70159_w = -this.field_70159_w * (getInfo()).bound; break;
/*      */         } 
/*  861 */         this.field_70165_t += this.field_70159_w;
/*      */         break;
/*      */       
/*      */       case EAST:
/*  865 */         if (this.field_70159_w < 0.0D) {
/*  866 */           this.field_70159_w = -this.field_70159_w * (getInfo()).bound; break;
/*      */         } 
/*  868 */         this.field_70165_t += this.field_70159_w;
/*      */         break;
/*      */     } 
/*      */     
/*  872 */     if ((getInfo()).bound <= 0.0F) {
/*      */       
/*  874 */       this.field_70159_w *= 0.25D;
/*  875 */       this.field_70181_x *= 0.25D;
/*  876 */       this.field_70179_y *= 0.25D;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void onUpdateCollided() {
/*  882 */     float damageFator = 1.0F;
/*  883 */     double mx = this.field_70159_w * this.accelerationFactor;
/*  884 */     double my = this.field_70181_x * this.accelerationFactor;
/*  885 */     double mz = this.field_70179_y * this.accelerationFactor;
/*      */     
/*  887 */     RayTraceResult m = null;
/*      */     
/*  889 */     for (int i = 0; i < 5; i++) {
/*      */       
/*  891 */       Vec3d vec3d1 = W_WorldFunc.getWorldVec3(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v);
/*  892 */       Vec3d vec3d2 = W_WorldFunc.getWorldVec3(this.field_70170_p, this.field_70165_t + mx, this.field_70163_u + my, this.field_70161_v + mz);
/*  893 */       m = W_WorldFunc.clip(this.field_70170_p, vec3d1, vec3d2);
/*  894 */       boolean continueClip = false;
/*      */       
/*  896 */       if (this.shootingEntity != null && W_MovingObjectPosition.isHitTypeTile(m)) {
/*      */ 
/*      */         
/*  899 */         Block block = W_WorldFunc.getBlock(this.field_70170_p, m.func_178782_a());
/*      */         
/*  901 */         if (MCH_Config.bulletBreakableBlocks.contains(block)) {
/*      */ 
/*      */           
/*  904 */           W_WorldFunc.destroyBlock(this.field_70170_p, m.func_178782_a(), true);
/*  905 */           continueClip = true;
/*      */         } 
/*      */       } 
/*      */       
/*  909 */       if (!continueClip) {
/*      */         break;
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  915 */     Vec3d vec3 = W_WorldFunc.getWorldVec3(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v);
/*  916 */     Vec3d vec31 = W_WorldFunc.getWorldVec3(this.field_70170_p, this.field_70165_t + mx, this.field_70163_u + my, this.field_70161_v + mz);
/*      */     
/*  918 */     if ((getInfo()).delayFuse > 0) {
/*      */       
/*  920 */       if (m != null) {
/*      */         
/*  922 */         boundBullet(m.field_178784_b);
/*      */         
/*  924 */         if (this.delayFuse == 0)
/*      */         {
/*  926 */           this.delayFuse = (getInfo()).delayFuse;
/*      */         }
/*      */       } 
/*      */       
/*      */       return;
/*      */     } 
/*  932 */     if (m != null)
/*      */     {
/*  934 */       vec31 = W_WorldFunc.getWorldVec3(this.field_70170_p, m.field_72307_f.field_72450_a, m.field_72307_f.field_72448_b, m.field_72307_f.field_72449_c);
/*      */     }
/*      */     
/*  937 */     Entity entity = null;
/*      */     
/*  939 */     List<Entity> list = this.field_70170_p.func_72839_b((Entity)this, 
/*  940 */         func_174813_aQ().func_72321_a(mx, my, mz).func_72314_b(21.0D, 21.0D, 21.0D));
/*  941 */     double d0 = 0.0D;
/*      */     
/*  943 */     for (int j = 0; j < list.size(); j++) {
/*      */       
/*  945 */       Entity entity1 = list.get(j);
/*      */       
/*  947 */       if (canBeCollidedEntity(entity1)) {
/*      */         
/*  949 */         float f = 0.3F;
/*      */         
/*  951 */         AxisAlignedBB axisalignedbb = entity1.func_174813_aQ().func_72314_b(f, f, f);
/*      */         
/*  953 */         RayTraceResult m1 = axisalignedbb.func_72327_a(vec3, vec31);
/*      */         
/*  955 */         if (m1 != null) {
/*      */           
/*  957 */           double d1 = vec3.func_72438_d(m1.field_72307_f);
/*      */           
/*  959 */           if (d1 < d0 || d0 == 0.0D) {
/*      */             
/*  961 */             entity = entity1;
/*  962 */             d0 = d1;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  968 */     if (entity != null)
/*      */     {
/*      */       
/*  971 */       m = new RayTraceResult(entity);
/*      */     }
/*      */     
/*  974 */     if (m != null)
/*      */     {
/*  976 */       onImpact(m, damageFator);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean canBeCollidedEntity(Entity entity) {
/*  982 */     if (entity instanceof mcheli.chain.MCH_EntityChain)
/*      */     {
/*  984 */       return false;
/*      */     }
/*      */     
/*  987 */     if (!entity.func_70067_L())
/*      */     {
/*  989 */       return false;
/*      */     }
/*      */     
/*  992 */     if (entity instanceof MCH_EntityBaseBullet) {
/*      */       
/*  994 */       if (this.field_70170_p.field_72995_K)
/*      */       {
/*  996 */         return false;
/*      */       }
/*      */       
/*  999 */       MCH_EntityBaseBullet blt = (MCH_EntityBaseBullet)entity;
/*      */       
/* 1001 */       if (W_Entity.isEqual(blt.shootingAircraft, this.shootingAircraft)) {
/* 1002 */         return false;
/*      */       }
/* 1004 */       if (W_Entity.isEqual(blt.shootingEntity, this.shootingEntity))
/*      */       {
/* 1006 */         return false;
/*      */       }
/*      */     } 
/*      */     
/* 1010 */     if (entity instanceof mcheli.aircraft.MCH_EntitySeat) {
/* 1011 */       return false;
/*      */     }
/* 1013 */     if (entity instanceof mcheli.aircraft.MCH_EntityHitBox)
/*      */     {
/* 1015 */       return false;
/*      */     }
/*      */     
/* 1018 */     if (W_Entity.isEqual(entity, this.shootingEntity)) {
/* 1019 */       return false;
/*      */     }
/* 1021 */     if (this.shootingAircraft instanceof MCH_EntityAircraft) {
/*      */       
/* 1023 */       if (W_Entity.isEqual(entity, this.shootingAircraft))
/*      */       {
/* 1025 */         return false;
/*      */       }
/*      */       
/* 1028 */       if (((MCH_EntityAircraft)this.shootingAircraft).isMountedEntity(entity)) {
/* 1029 */         return false;
/*      */       }
/*      */     } 
/* 1032 */     for (String s : MCH_Config.IgnoreBulletHitList) {
/*      */       
/* 1034 */       if (entity.getClass().getName().toLowerCase().indexOf(s.toLowerCase()) >= 0) {
/* 1035 */         return false;
/*      */       }
/*      */     } 
/* 1038 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public void notifyHitBullet() {
/* 1043 */     if (this.shootingAircraft instanceof MCH_EntityAircraft && W_EntityPlayer.isPlayer(this.shootingEntity))
/*      */     {
/* 1045 */       MCH_PacketNotifyHitBullet.send((MCH_EntityAircraft)this.shootingAircraft, (EntityPlayer)this.shootingEntity);
/*      */     }
/*      */ 
/*      */     
/* 1049 */     if (W_EntityPlayer.isPlayer(this.shootingEntity))
/*      */     {
/* 1051 */       MCH_PacketNotifyHitBullet.send(null, (EntityPlayer)this.shootingEntity);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void onImpact(RayTraceResult m, float damageFactor) {
/* 1058 */     if (!this.field_70170_p.field_72995_K) {
/*      */       
/* 1060 */       if (m.field_72308_g != null) {
/*      */         
/* 1062 */         onImpactEntity(m.field_72308_g, damageFactor);
/* 1063 */         this.piercing = 0;
/*      */       } 
/*      */       
/* 1066 */       float expPower = this.explosionPower * damageFactor;
/* 1067 */       float expPowerInWater = this.explosionPowerInWater * damageFactor;
/* 1068 */       double dx = 0.0D;
/* 1069 */       double dy = 0.0D;
/* 1070 */       double dz = 0.0D;
/*      */       
/* 1072 */       if (this.piercing > 0)
/*      */       {
/* 1074 */         this.piercing--;
/*      */         
/* 1076 */         if (expPower > 0.0F)
/*      */         {
/* 1078 */           newExplosion(m.field_72307_f.field_72450_a + dx, m.field_72307_f.field_72448_b + dy, m.field_72307_f.field_72449_c + dz, 1.0F, 1.0F, false);
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 1083 */         if (expPowerInWater == 0.0F) {
/*      */           
/* 1085 */           if ((getInfo()).isFAE)
/*      */           {
/* 1087 */             newFAExplosion(this.field_70165_t, this.field_70163_u, this.field_70161_v, expPower, (getInfo()).explosionBlock);
/*      */           }
/* 1089 */           else if (expPower > 0.0F)
/*      */           {
/* 1091 */             newExplosion(m.field_72307_f.field_72450_a + dx, m.field_72307_f.field_72448_b + dy, m.field_72307_f.field_72449_c + dz, expPower, 
/* 1092 */                 (getInfo()).explosionBlock, false);
/*      */           }
/* 1094 */           else if (expPower < 0.0F)
/*      */           {
/* 1096 */             playExplosionSound();
/*      */           }
/*      */         
/* 1099 */         } else if (m.field_72308_g != null) {
/*      */           
/* 1101 */           if (func_70090_H())
/*      */           {
/* 1103 */             newExplosion(m.field_72307_f.field_72450_a + dx, m.field_72307_f.field_72448_b + dy, m.field_72307_f.field_72449_c + dz, expPowerInWater, expPowerInWater, true);
/*      */           
/*      */           }
/*      */           else
/*      */           {
/* 1108 */             newExplosion(m.field_72307_f.field_72450_a + dx, m.field_72307_f.field_72448_b + dy, m.field_72307_f.field_72449_c + dz, expPower, 
/* 1109 */                 (getInfo()).explosionBlock, false);
/*      */           }
/*      */         
/*      */         }
/* 1113 */         else if (func_70090_H() || MCH_Lib.isBlockInWater(this.field_70170_p, m.func_178782_a().func_177958_n(), m
/* 1114 */             .func_178782_a().func_177956_o(), m.func_178782_a().func_177952_p())) {
/*      */ 
/*      */           
/* 1117 */           newExplosion(m.func_178782_a().func_177958_n(), m.func_178782_a().func_177956_o(), m.func_178782_a().func_177952_p(), expPowerInWater, expPowerInWater, true);
/*      */         
/*      */         }
/* 1120 */         else if (expPower > 0.0F) {
/*      */           
/* 1122 */           newExplosion(m.field_72307_f.field_72450_a + dx, m.field_72307_f.field_72448_b + dy, m.field_72307_f.field_72449_c + dz, expPower, (getInfo()).explosionBlock, false);
/*      */         
/*      */         }
/* 1125 */         else if (expPower < 0.0F) {
/*      */           
/* 1127 */           playExplosionSound();
/*      */         } 
/*      */         
/* 1130 */         func_70106_y();
/*      */       }
/*      */     
/* 1133 */     } else if (getInfo() != null && ((getInfo()).explosion == 0 || (getInfo()).modeNum >= 2)) {
/*      */       
/* 1135 */       if (W_MovingObjectPosition.isHitTypeTile(m)) {
/*      */         
/* 1137 */         float p = (getInfo()).power;
/*      */         
/* 1139 */         for (int i = 0; i < p / 3.0F; i++)
/*      */         {
/*      */           
/* 1142 */           MCH_ParticlesUtil.spawnParticleTileCrack(this.field_70170_p, m.func_178782_a().func_177958_n(), m.func_178782_a().func_177956_o(), m
/* 1143 */               .func_178782_a().func_177952_p(), m.field_72307_f.field_72450_a + (this.field_70146_Z.nextFloat() - 0.5D) * p / 10.0D, m.field_72307_f.field_72448_b + 0.1D, m.field_72307_f.field_72449_c + (this.field_70146_Z
/* 1144 */               .nextFloat() - 0.5D) * p / 10.0D, -this.field_70159_w * p / 2.0D, (p / 2.0F), -this.field_70179_y * p / 2.0D);
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void onImpactEntity(Entity entity, float damageFactor) {
/* 1153 */     if (!entity.field_70128_L) {
/*      */       
/* 1155 */       MCH_Lib.DbgLog(this.field_70170_p, "MCH_EntityBaseBullet.onImpactEntity:Damage=%d:" + entity.getClass(), new Object[] {
/*      */ 
/*      */             
/* 1158 */             Integer.valueOf(getPower())
/*      */           });
/*      */       
/* 1161 */       MCH_Lib.applyEntityHurtResistantTimeConfig(entity);
/*      */       
/* 1163 */       DamageSource ds = DamageSource.func_76356_a((Entity)this, this.shootingEntity);
/* 1164 */       float damage = MCH_Config.applyDamageVsEntity(entity, ds, getPower() * damageFactor);
/* 1165 */       damage *= (getInfo() != null) ? getInfo().getDamageFactor(entity) : 1.0F;
/*      */       
/* 1167 */       entity.func_70097_a(ds, damage);
/*      */       
/* 1169 */       if (this instanceof MCH_EntityBullet && entity instanceof net.minecraft.entity.passive.EntityVillager)
/*      */       {
/*      */         
/* 1172 */         if (this.shootingEntity != null && this.shootingEntity instanceof EntityPlayerMP && this.shootingEntity
/* 1173 */           .func_184187_bx() instanceof mcheli.aircraft.MCH_EntitySeat)
/*      */         {
/*      */           
/* 1176 */           MCH_CriteriaTriggers.VILLAGER_HURT_BULLET.trigger((EntityPlayerMP)this.shootingEntity);
/*      */         }
/*      */       }
/*      */       
/* 1180 */       if (!entity.field_70128_L);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1185 */     notifyHitBullet();
/*      */   }
/*      */ 
/*      */   
/*      */   public void newFAExplosion(double x, double y, double z, float exp, float expBlock) {
/* 1190 */     MCH_Explosion.ExplosionResult result = MCH_Explosion.newExplosion(this.field_70170_p, (Entity)this, this.shootingEntity, x, y, z, exp, expBlock, true, true, 
/* 1191 */         (getInfo()).flaming, false, 15);
/*      */     
/* 1193 */     if (result != null && result.hitEntity)
/*      */     {
/* 1195 */       notifyHitBullet();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void newExplosion(double x, double y, double z, float exp, float expBlock, boolean inWater) {
/*      */     MCH_Explosion.ExplosionResult result;
/* 1203 */     if (!inWater) {
/*      */       
/* 1205 */       result = MCH_Explosion.newExplosion(this.field_70170_p, (Entity)this, this.shootingEntity, x, y, z, exp, expBlock, 
/* 1206 */           (this.field_70146_Z.nextInt(3) == 0), true, (getInfo()).flaming, true, 0, 
/* 1207 */           (getInfo() != null) ? (getInfo()).damageFactor : null);
/*      */     }
/*      */     else {
/*      */       
/* 1211 */       result = MCH_Explosion.newExplosionInWater(this.field_70170_p, (Entity)this, this.shootingEntity, x, y, z, exp, expBlock, 
/* 1212 */           (this.field_70146_Z.nextInt(3) == 0), true, (getInfo()).flaming, true, 0, 
/* 1213 */           (getInfo() != null) ? (getInfo()).damageFactor : null);
/*      */     } 
/*      */     
/* 1216 */     if (result != null && result.hitEntity)
/*      */     {
/* 1218 */       notifyHitBullet();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void playExplosionSound() {
/* 1224 */     MCH_Explosion.playExplosionSound(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void func_70014_b(NBTTagCompound par1NBTTagCompound) {
/* 1230 */     par1NBTTagCompound.func_74782_a("direction", (NBTBase)func_70087_a(new double[] { this.field_70159_w, this.field_70181_x, this.field_70179_y }));
/*      */ 
/*      */ 
/*      */     
/* 1234 */     par1NBTTagCompound.func_74778_a("WeaponName", func_70005_c_());
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void func_70037_a(NBTTagCompound par1NBTTagCompound) {
/* 1240 */     func_70106_y();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean func_70067_L() {
/* 1246 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public float func_70111_Y() {
/* 1252 */     return 1.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean func_70097_a(DamageSource ds, float par2) {
/* 1259 */     if (func_180431_b(ds))
/*      */     {
/* 1261 */       return false;
/*      */     }
/*      */     
/* 1264 */     if (!this.field_70170_p.field_72995_K)
/*      */     {
/* 1266 */       if (par2 > 0.0F && ds.func_76355_l().equalsIgnoreCase("thrown")) {
/*      */         
/* 1268 */         func_70018_K();
/*      */ 
/*      */         
/* 1271 */         Vec3d pos = new Vec3d(this.field_70165_t, this.field_70163_u, this.field_70161_v);
/* 1272 */         RayTraceResult m = new RayTraceResult(pos, EnumFacing.DOWN, new BlockPos(pos));
/*      */         
/* 1274 */         onImpact(m, 1.0F);
/* 1275 */         return true;
/*      */       } 
/*      */     }
/*      */     
/* 1279 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   @SideOnly(Side.CLIENT)
/*      */   public float getShadowSize() {
/* 1285 */     return 0.0F;
/*      */   }
/*      */ 
/*      */   
/*      */   public float getBrightness(float par1) {
/* 1290 */     return 1.0F;
/*      */   }
/*      */ 
/*      */   
/*      */   @SideOnly(Side.CLIENT)
/*      */   public int getBrightnessForRender(float par1) {
/* 1296 */     return 15728880;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getPower() {
/* 1301 */     return this.power;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setPower(int power) {
/* 1306 */     this.power = power;
/*      */   }
/*      */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\weapon\MCH_EntityBaseBullet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */