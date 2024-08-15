/*     */ package mcheli.parachute;
/*     */ 
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import mcheli.MCH_Lib;
/*     */ import mcheli.__helper.entity.IEntitySinglePassenger;
/*     */ import mcheli.particles.MCH_ParticleParam;
/*     */ import mcheli.particles.MCH_ParticlesUtil;
/*     */ import mcheli.wrapper.W_AxisAlignedBB;
/*     */ import mcheli.wrapper.W_Block;
/*     */ import mcheli.wrapper.W_Entity;
/*     */ import mcheli.wrapper.W_Lib;
/*     */ import mcheli.wrapper.W_WorldFunc;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.MoverType;
/*     */ import net.minecraft.entity.item.EntityBoat;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.network.datasync.DataParameter;
/*     */ import net.minecraft.network.datasync.DataSerializers;
/*     */ import net.minecraft.network.datasync.EntityDataManager;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MCH_EntityParachute
/*     */   extends W_Entity
/*     */   implements IEntitySinglePassenger
/*     */ {
/*  43 */   private static final DataParameter<Byte> TYPE = EntityDataManager.func_187226_a(MCH_EntityParachute.class, DataSerializers.field_187191_a);
/*     */   
/*     */   private double speedMultiplier;
/*     */   
/*     */   private int paraPosRotInc;
/*     */   private double paraX;
/*     */   private double paraY;
/*     */   private double paraZ;
/*     */   private double paraYaw;
/*     */   private double paraPitch;
/*     */   @SideOnly(Side.CLIENT)
/*     */   private double velocityX;
/*     */   @SideOnly(Side.CLIENT)
/*     */   private double velocityY;
/*     */   @SideOnly(Side.CLIENT)
/*     */   private double velocityZ;
/*     */   public Entity user;
/*     */   public int onGroundCount;
/*     */   
/*     */   public MCH_EntityParachute(World par1World) {
/*  63 */     super(par1World);
/*  64 */     this.speedMultiplier = 0.07D;
/*  65 */     this.field_70156_m = true;
/*  66 */     func_70105_a(1.5F, 0.6F);
/*     */     
/*  68 */     this.user = null;
/*  69 */     this.onGroundCount = 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public MCH_EntityParachute(World par1World, double par2, double par4, double par6) {
/*  74 */     this(par1World);
/*     */     
/*  76 */     func_70107_b(par2, par4, par6);
/*  77 */     this.field_70159_w = 0.0D;
/*  78 */     this.field_70181_x = 0.0D;
/*  79 */     this.field_70179_y = 0.0D;
/*  80 */     this.field_70169_q = par2;
/*  81 */     this.field_70167_r = par4;
/*  82 */     this.field_70166_s = par6;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean func_70041_e_() {
/*  88 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_70088_a() {
/*  95 */     this.field_70180_af.func_187214_a(TYPE, Byte.valueOf((byte)0));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setType(int n) {
/* 101 */     this.field_70180_af.func_187227_b(TYPE, Byte.valueOf((byte)n));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getType() {
/* 107 */     return ((Byte)this.field_70180_af.func_187225_a(TYPE)).byteValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AxisAlignedBB func_70114_g(Entity par1Entity) {
/* 114 */     return par1Entity.func_174813_aQ();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AxisAlignedBB func_70046_E() {
/* 121 */     return func_174813_aQ();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_70104_M() {
/* 127 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double func_70042_X() {
/* 133 */     return this.field_70131_O * 0.0D - 0.30000001192092896D;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_70097_a(DamageSource par1DamageSource, float par2) {
/* 139 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_70067_L() {
/* 145 */     return !this.field_70128_L;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_180426_a(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean teleport) {
/* 155 */     this.paraPosRotInc = posRotationIncrements + 10;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 162 */     this.paraX = x;
/* 163 */     this.paraY = y;
/* 164 */     this.paraZ = z;
/* 165 */     this.paraYaw = yaw;
/* 166 */     this.paraPitch = pitch;
/* 167 */     this.field_70159_w = this.velocityX;
/* 168 */     this.field_70181_x = this.velocityY;
/* 169 */     this.field_70179_y = this.velocityZ;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_70016_h(double par1, double par3, double par5) {
/* 176 */     this.velocityX = this.field_70159_w = par1;
/* 177 */     this.velocityY = this.field_70181_x = par3;
/* 178 */     this.velocityZ = this.field_70179_y = par5;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_70106_y() {
/* 184 */     super.func_70106_y();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_70071_h_() {
/* 190 */     super.func_70071_h_();
/*     */     
/* 192 */     if (!this.field_70170_p.field_72995_K && this.field_70173_aa % 10 == 0)
/*     */     {
/* 194 */       MCH_Lib.DbgLog(this.field_70170_p, "MCH_EntityParachute.onUpdate %d, %.3f", new Object[] {
/*     */             
/* 196 */             Integer.valueOf(this.field_70173_aa), Double.valueOf(this.field_70181_x)
/*     */           });
/*     */     }
/*     */     
/* 200 */     if (isOpenParachute() && this.field_70181_x > -0.3D && this.field_70173_aa > 20)
/*     */     {
/* 202 */       this.field_70143_R = (float)(this.field_70143_R * 0.85D);
/*     */     }
/*     */     
/* 205 */     if (!this.field_70170_p.field_72995_K && this.user != null && this.user.func_184187_bx() == null) {
/*     */ 
/*     */       
/* 208 */       this.user.func_184220_m((Entity)this);
/* 209 */       this.field_70177_z = this.field_70126_B = this.user.field_70177_z;
/* 210 */       this.user = null;
/*     */     } 
/*     */     
/* 213 */     this.field_70169_q = this.field_70165_t;
/* 214 */     this.field_70167_r = this.field_70163_u;
/* 215 */     this.field_70166_s = this.field_70161_v;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 221 */     double d1 = (func_174813_aQ()).field_72338_b + ((func_174813_aQ()).field_72337_e - (func_174813_aQ()).field_72338_b) * 0.0D / 5.0D - 0.125D;
/*     */     
/* 223 */     double d2 = (func_174813_aQ()).field_72338_b + ((func_174813_aQ()).field_72337_e - (func_174813_aQ()).field_72338_b) * 1.0D / 5.0D - 0.125D;
/* 224 */     AxisAlignedBB axisalignedbb = W_AxisAlignedBB.getAABB((func_174813_aQ()).field_72340_a, d1, 
/* 225 */         (func_174813_aQ()).field_72339_c, (func_174813_aQ()).field_72336_d, d2, 
/* 226 */         (func_174813_aQ()).field_72334_f);
/*     */ 
/*     */     
/* 229 */     if (this.field_70170_p.func_72875_a(axisalignedbb, Material.field_151586_h)) {
/*     */       
/* 231 */       onWaterSetBoat();
/* 232 */       func_70106_y();
/*     */     } 
/*     */     
/* 235 */     if (this.field_70170_p.field_72995_K) {
/*     */       
/* 237 */       onUpdateClient();
/*     */     }
/*     */     else {
/*     */       
/* 241 */       onUpdateServer();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onUpdateClient() {
/* 247 */     if (this.paraPosRotInc > 0) {
/*     */       
/* 249 */       double x = this.field_70165_t + (this.paraX - this.field_70165_t) / this.paraPosRotInc;
/* 250 */       double y = this.field_70163_u + (this.paraY - this.field_70163_u) / this.paraPosRotInc;
/* 251 */       double z = this.field_70161_v + (this.paraZ - this.field_70161_v) / this.paraPosRotInc;
/* 252 */       double yaw = MathHelper.func_76138_g(this.paraYaw - this.field_70177_z);
/* 253 */       this.field_70177_z = (float)(this.field_70177_z + yaw / this.paraPosRotInc);
/* 254 */       this.field_70125_A = (float)(this.field_70125_A + (this.paraPitch - this.field_70125_A) / this.paraPosRotInc);
/*     */       
/* 256 */       this.paraPosRotInc--;
/*     */       
/* 258 */       func_70107_b(x, y, z);
/* 259 */       func_70101_b(this.field_70177_z, this.field_70125_A);
/*     */ 
/*     */       
/* 262 */       if (getRiddenByEntity() != null)
/*     */       {
/*     */         
/* 265 */         func_70101_b((getRiddenByEntity()).field_70126_B, this.field_70125_A);
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 270 */       func_70107_b(this.field_70165_t + this.field_70159_w, this.field_70163_u + this.field_70181_x, this.field_70161_v + this.field_70179_y);
/*     */       
/* 272 */       if (this.field_70122_E);
/*     */ 
/*     */ 
/*     */       
/* 276 */       this.field_70159_w *= 0.99D;
/* 277 */       this.field_70181_x *= 0.95D;
/* 278 */       this.field_70179_y *= 0.99D;
/*     */     } 
/*     */     
/* 281 */     if (!isOpenParachute() && this.field_70181_x > 0.01D) {
/*     */       
/* 283 */       float color = 0.6F + this.field_70146_Z.nextFloat() * 0.2F;
/* 284 */       double dx = this.field_70169_q - this.field_70165_t;
/* 285 */       double dy = this.field_70167_r - this.field_70163_u;
/* 286 */       double dz = this.field_70166_s - this.field_70161_v;
/* 287 */       int num = 1 + (int)(MathHelper.func_76133_a(dx * dx + dy * dy + dz * dz) * 2.0D);
/*     */       double i;
/* 289 */       for (i = 0.0D; i < num; i++) {
/*     */         
/* 291 */         MCH_ParticleParam prm = new MCH_ParticleParam(this.field_70170_p, "smoke", this.field_70169_q + (this.field_70165_t - this.field_70169_q) * i / num * 0.8D, this.field_70167_r + (this.field_70163_u - this.field_70167_r) * i / num * 0.8D, this.field_70166_s + (this.field_70161_v - this.field_70166_s) * i / num * 0.8D);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 296 */         prm.motionX = this.field_70159_w * 0.5D + (this.field_70146_Z.nextDouble() - 0.5D) * 0.5D;
/* 297 */         prm.motionX = this.field_70181_x * -0.5D + (this.field_70146_Z.nextDouble() - 0.5D) * 0.5D;
/* 298 */         prm.motionX = this.field_70179_y * 0.5D + (this.field_70146_Z.nextDouble() - 0.5D) * 0.5D;
/* 299 */         prm.size = 5.0F;
/* 300 */         prm.setColor(0.8F + this.field_70146_Z.nextFloat(), color, color, color);
/* 301 */         MCH_ParticlesUtil.spawnParticle(prm);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onUpdateServer() {
/* 308 */     double prevSpeed = Math.sqrt(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
/* 309 */     double gravity = this.field_70122_E ? 0.01D : 0.03D;
/*     */     
/* 311 */     if (getType() == 2 && this.field_70173_aa < 20)
/*     */     {
/* 313 */       gravity = 0.01D;
/*     */     }
/*     */     
/* 316 */     this.field_70181_x -= gravity;
/*     */     
/* 318 */     if (isOpenParachute()) {
/*     */ 
/*     */       
/* 321 */       if (W_Lib.isEntityLivingBase(getRiddenByEntity())) {
/*     */ 
/*     */         
/* 324 */         double mv = W_Lib.getEntityMoveDist(getRiddenByEntity());
/*     */         
/* 326 */         if (!isOpenParachute())
/*     */         {
/* 328 */           mv = 0.0D;
/*     */         }
/*     */         
/* 331 */         if (mv > 0.0D) {
/*     */ 
/*     */ 
/*     */           
/* 335 */           double mx = -Math.sin(((getRiddenByEntity()).field_70177_z * 3.1415927F / 180.0F));
/* 336 */           double mz = Math.cos(((getRiddenByEntity()).field_70177_z * 3.1415927F / 180.0F));
/* 337 */           this.field_70159_w += mx * this.speedMultiplier * 0.05D;
/* 338 */           this.field_70179_y += mz * this.speedMultiplier * 0.05D;
/*     */         } 
/*     */       } 
/*     */       
/* 342 */       double speed = Math.sqrt(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
/*     */       
/* 344 */       if (speed > 0.35D) {
/*     */         
/* 346 */         this.field_70159_w *= 0.35D / speed;
/* 347 */         this.field_70179_y *= 0.35D / speed;
/* 348 */         speed = 0.35D;
/*     */       } 
/*     */       
/* 351 */       if (speed > prevSpeed && this.speedMultiplier < 0.35D) {
/*     */         
/* 353 */         this.speedMultiplier += (0.35D - this.speedMultiplier) / 35.0D;
/*     */         
/* 355 */         if (this.speedMultiplier > 0.35D)
/*     */         {
/* 357 */           this.speedMultiplier = 0.35D;
/*     */         }
/*     */       }
/*     */       else {
/*     */         
/* 362 */         this.speedMultiplier -= (this.speedMultiplier - 0.07D) / 35.0D;
/*     */         
/* 364 */         if (this.speedMultiplier < 0.07D)
/*     */         {
/* 366 */           this.speedMultiplier = 0.07D;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 371 */     if (this.field_70122_E) {
/*     */       
/* 373 */       this.onGroundCount++;
/*     */       
/* 375 */       if (this.onGroundCount > 5) {
/*     */         
/* 377 */         onGroundAndDead();
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/*     */     
/* 383 */     func_70091_d(MoverType.SELF, this.field_70159_w, this.field_70181_x, this.field_70179_y);
/*     */     
/* 385 */     if (getType() == 2 && this.field_70173_aa < 20) {
/*     */       
/* 387 */       this.field_70181_x *= 0.95D;
/*     */     }
/*     */     else {
/*     */       
/* 391 */       this.field_70181_x *= 0.9D;
/*     */     } 
/*     */     
/* 394 */     if (isOpenParachute()) {
/*     */       
/* 396 */       this.field_70159_w *= 0.95D;
/* 397 */       this.field_70179_y *= 0.95D;
/*     */     }
/*     */     else {
/*     */       
/* 401 */       this.field_70159_w *= 0.99D;
/* 402 */       this.field_70179_y *= 0.99D;
/*     */     } 
/*     */     
/* 405 */     this.field_70125_A = 0.0F;
/* 406 */     double yaw = this.field_70177_z;
/* 407 */     double dx = this.field_70169_q - this.field_70165_t;
/* 408 */     double dz = this.field_70166_s - this.field_70161_v;
/*     */     
/* 410 */     if (dx * dx + dz * dz > 0.001D)
/*     */     {
/* 412 */       yaw = (float)(Math.atan2(dx, dz) * 180.0D / Math.PI);
/*     */     }
/*     */     
/* 415 */     double yawDiff = MathHelper.func_76138_g(yaw - this.field_70177_z);
/*     */     
/* 417 */     if (yawDiff > 20.0D)
/*     */     {
/* 419 */       yawDiff = 20.0D;
/*     */     }
/*     */     
/* 422 */     if (yawDiff < -20.0D)
/*     */     {
/* 424 */       yawDiff = -20.0D;
/*     */     }
/*     */ 
/*     */     
/* 428 */     if (getRiddenByEntity() != null) {
/*     */ 
/*     */       
/* 431 */       func_70101_b((getRiddenByEntity()).field_70177_z, this.field_70125_A);
/*     */     }
/*     */     else {
/*     */       
/* 435 */       this.field_70177_z = (float)(this.field_70177_z + yawDiff);
/* 436 */       func_70101_b(this.field_70177_z, this.field_70125_A);
/*     */     } 
/*     */ 
/*     */     
/* 440 */     List<Entity> list = this.field_70170_p.func_72839_b((Entity)this, 
/* 441 */         func_174813_aQ().func_72314_b(0.2D, 0.0D, 0.2D));
/*     */     
/* 443 */     if (list != null && !list.isEmpty())
/*     */     {
/* 445 */       for (int l = 0; l < list.size(); l++) {
/*     */         
/* 447 */         Entity entity = list.get(l);
/*     */ 
/*     */         
/* 450 */         if (entity != getRiddenByEntity() && entity.func_70104_M() && entity instanceof MCH_EntityParachute)
/*     */         {
/* 452 */           entity.func_70108_f((Entity)this);
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 458 */     if (getRiddenByEntity() != null && (getRiddenByEntity()).field_70128_L)
/*     */     {
/*     */       
/* 461 */       func_70106_y();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void onGroundAndDead() {
/* 467 */     this.field_70163_u += 1.2D;
/*     */     
/* 469 */     func_184232_k(getRiddenByEntity());
/* 470 */     func_70106_y();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onWaterSetBoat() {
/* 475 */     if (this.field_70170_p.field_72995_K) {
/*     */       return;
/*     */     }
/* 478 */     if (getType() != 2) {
/*     */       return;
/*     */     }
/*     */     
/* 482 */     if (getRiddenByEntity() == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 487 */     int px = (int)(this.field_70165_t + 0.5D);
/* 488 */     int py = (int)(this.field_70163_u + 0.5D);
/* 489 */     int pz = (int)(this.field_70161_v + 0.5D);
/* 490 */     boolean foundBlock = false;
/*     */     
/* 492 */     for (int y = 0; y < 5; y++) {
/*     */       
/* 494 */       if (py + y < 0 || py + y > 255) {
/*     */         break;
/*     */       }
/* 497 */       Block block = W_WorldFunc.getBlock(this.field_70170_p, px, py - y, pz);
/*     */       
/* 499 */       if (block == W_Block.getWater()) {
/*     */         
/* 501 */         py -= y;
/* 502 */         foundBlock = true;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/* 507 */     if (!foundBlock) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 512 */     int countWater = 0;
/*     */ 
/*     */     
/* 515 */     for (int i = 0; i < 3; i++) {
/*     */       
/* 517 */       if (py + i < 0 || py + i > 255) {
/*     */         break;
/*     */       }
/* 520 */       for (int x = -2; x <= 2; x++) {
/*     */         
/* 522 */         for (int z = -2; z <= 2; z++) {
/*     */           
/* 524 */           Block block = W_WorldFunc.getBlock(this.field_70170_p, px + x, py - i, pz + z);
/*     */           
/* 526 */           if (block == W_Block.getWater()) {
/*     */             
/* 528 */             countWater++;
/*     */             
/* 530 */             if (countWater > 37) {
/*     */               break;
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 539 */     if (countWater > 37) {
/*     */       
/* 541 */       EntityBoat entityboat = new EntityBoat(this.field_70170_p, px, (py + 1.0F), pz);
/* 542 */       entityboat.field_70177_z = this.field_70177_z - 90.0F;
/*     */       
/* 544 */       this.field_70170_p.func_72838_d((Entity)entityboat);
/*     */       
/* 546 */       getRiddenByEntity().func_184220_m((Entity)entityboat);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isOpenParachute() {
/* 552 */     return (getType() != 2 || this.field_70181_x < -0.1D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_184232_k(Entity passenger) {
/* 560 */     if (func_184196_w(passenger)) {
/*     */       
/* 562 */       double x = -Math.sin(this.field_70177_z * Math.PI / 180.0D) * 0.1D;
/* 563 */       double z = Math.cos(this.field_70177_z * Math.PI / 180.0D) * 0.1D;
/*     */       
/* 565 */       passenger.func_70107_b(this.field_70165_t + x, this.field_70163_u + func_70042_X() + passenger.func_70033_W(), this.field_70161_v + z);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_70014_b(NBTTagCompound nbt) {
/* 573 */     nbt.func_74774_a("ParachuteModelType", (byte)getType());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_70037_a(NBTTagCompound nbt) {
/* 579 */     setType(nbt.func_74771_c("ParachuteModelType"));
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public float getShadowSize() {
/* 585 */     return 4.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_184230_a(EntityPlayer player, EnumHand hand) {
/* 592 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Entity getRiddenByEntity() {
/* 599 */     List<Entity> passengers = func_184188_bt();
/* 600 */     return passengers.isEmpty() ? null : passengers.get(0);
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\parachute\MCH_EntityParachute.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */