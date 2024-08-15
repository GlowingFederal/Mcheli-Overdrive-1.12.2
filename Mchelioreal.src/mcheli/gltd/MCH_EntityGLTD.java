/*     */ package mcheli.gltd;
/*     */ 
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import mcheli.MCH_Camera;
/*     */ import mcheli.MCH_Config;
/*     */ import mcheli.MCH_MOD;
/*     */ import mcheli.__helper.entity.IEntityItemStackPickable;
/*     */ import mcheli.__helper.entity.IEntitySinglePassenger;
/*     */ import mcheli.multiplay.MCH_Multiplay;
/*     */ import mcheli.weapon.MCH_WeaponCAS;
/*     */ import mcheli.weapon.MCH_WeaponInfo;
/*     */ import mcheli.weapon.MCH_WeaponInfoManager;
/*     */ import mcheli.wrapper.W_Block;
/*     */ import mcheli.wrapper.W_Entity;
/*     */ import mcheli.wrapper.W_WorldFunc;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.MoverType;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.network.datasync.DataParameter;
/*     */ import net.minecraft.network.datasync.DataSerializers;
/*     */ import net.minecraft.network.datasync.EntityDataManager;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraft.util.math.Vec3d;
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
/*     */ public class MCH_EntityGLTD
/*     */   extends W_Entity
/*     */   implements IEntitySinglePassenger, IEntityItemStackPickable
/*     */ {
/*     */   public static final float Y_OFFSET = 0.25F;
/*  49 */   private static final DataParameter<Integer> TIME_SINCE_HIT = EntityDataManager.func_187226_a(MCH_EntityGLTD.class, DataSerializers.field_187192_b);
/*     */   
/*  51 */   private static final DataParameter<Integer> FORWARD_DIR = EntityDataManager.func_187226_a(MCH_EntityGLTD.class, DataSerializers.field_187192_b);
/*     */   
/*  53 */   private static final DataParameter<Integer> DAMAGE_TAKEN = EntityDataManager.func_187226_a(MCH_EntityGLTD.class, DataSerializers.field_187192_b);
/*     */   
/*     */   private boolean isBoatEmpty;
/*     */   
/*     */   private double speedMultiplier;
/*     */   
/*     */   private int gltdPosRotInc;
/*     */   private double gltdX;
/*     */   private double gltdY;
/*     */   private double gltdZ;
/*     */   private double gltdYaw;
/*     */   private double gltdPitch;
/*     */   @SideOnly(Side.CLIENT)
/*     */   private double velocityX;
/*     */   @SideOnly(Side.CLIENT)
/*     */   private double velocityY;
/*     */   @SideOnly(Side.CLIENT)
/*     */   private double velocityZ;
/*     */   public final MCH_Camera camera;
/*     */   public boolean zoomDir;
/*     */   public final MCH_WeaponCAS weaponCAS;
/*     */   public int countWait;
/*     */   public boolean isUsedPlayer;
/*     */   public float renderRotaionYaw;
/*     */   public float renderRotaionPitch;
/*     */   public int retryRiddenByEntityCheck;
/*     */   public Entity lastRiddenByEntity;
/*     */   
/*     */   public MCH_EntityGLTD(World world) {
/*  82 */     super(world);
/*  83 */     this.isBoatEmpty = true;
/*  84 */     this.speedMultiplier = 0.07D;
/*  85 */     this.field_70156_m = true;
/*  86 */     func_70105_a(0.5F, 0.5F);
/*     */     
/*  88 */     this.camera = new MCH_Camera(world, (Entity)this);
/*     */     
/*  90 */     MCH_WeaponInfo wi = MCH_WeaponInfoManager.get("a10gau8");
/*     */     
/*  92 */     this.weaponCAS = new MCH_WeaponCAS(world, Vec3d.field_186680_a, 0.0F, 0.0F, "a10gau8", wi);
/*  93 */     this.weaponCAS.interval += (this.weaponCAS.interval > 0) ? 150 : 65386;
/*  94 */     this.weaponCAS.displayName = "A-10 GAU-8 Avenger";
/*     */     
/*  96 */     this.field_70158_ak = true;
/*  97 */     this.countWait = 0;
/*     */     
/*  99 */     this.retryRiddenByEntityCheck = 0;
/* 100 */     this.lastRiddenByEntity = null;
/*     */     
/* 102 */     this.isUsedPlayer = false;
/* 103 */     this.renderRotaionYaw = 0.0F;
/* 104 */     this.renderRotaionYaw = 0.0F;
/* 105 */     this.renderRotaionPitch = 0.0F;
/* 106 */     this.zoomDir = true;
/*     */ 
/*     */     
/* 109 */     this._renderDistanceWeight = 2.0D;
/*     */   }
/*     */ 
/*     */   
/*     */   public MCH_EntityGLTD(World par1World, double x, double y, double z) {
/* 114 */     this(par1World);
/*     */     
/* 116 */     func_70107_b(x, y, z);
/* 117 */     this.field_70159_w = 0.0D;
/* 118 */     this.field_70181_x = 0.0D;
/* 119 */     this.field_70179_y = 0.0D;
/* 120 */     this.field_70169_q = x;
/* 121 */     this.field_70167_r = y;
/* 122 */     this.field_70166_s = z;
/* 123 */     this.camera.setPosition(x, y, z);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean func_70041_e_() {
/* 129 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_70088_a() {
/* 138 */     this.field_70180_af.func_187214_a(TIME_SINCE_HIT, Integer.valueOf(0));
/* 139 */     this.field_70180_af.func_187214_a(FORWARD_DIR, Integer.valueOf(1));
/* 140 */     this.field_70180_af.func_187214_a(DAMAGE_TAKEN, Integer.valueOf(0));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AxisAlignedBB func_70114_g(Entity par1Entity) {
/* 147 */     return par1Entity.func_174813_aQ();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AxisAlignedBB func_70046_E() {
/* 154 */     return func_174813_aQ();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_70104_M() {
/* 160 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double func_70042_X() {
/* 166 */     return this.field_70131_O * 0.0D - 0.3D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_70097_a(DamageSource ds, float damage) {
/* 173 */     if (func_180431_b(ds))
/*     */     {
/* 175 */       return false;
/*     */     }
/*     */     
/* 178 */     if (!this.field_70170_p.field_72995_K && !this.field_70128_L) {
/*     */       
/* 180 */       damage = MCH_Config.applyDamageByExternal((Entity)this, ds, damage);
/*     */       
/* 182 */       if (!MCH_Multiplay.canAttackEntity(ds, (Entity)this))
/*     */       {
/* 184 */         return false;
/*     */       }
/*     */       
/* 187 */       setForwardDirection(-getForwardDirection());
/* 188 */       setTimeSinceHit(10);
/* 189 */       setDamageTaken((int)(getDamageTaken() + damage * 100.0F));
/* 190 */       func_70018_K();
/*     */       
/* 192 */       boolean flag = (ds.func_76346_g() instanceof EntityPlayer && ((EntityPlayer)ds.func_76346_g()).field_71075_bZ.field_75098_d);
/*     */       
/* 194 */       if (flag || getDamageTaken() > 40.0F) {
/*     */         
/* 196 */         Entity riddenByEntity = getRiddenByEntity();
/*     */ 
/*     */         
/* 199 */         this.camera.initCamera(0, riddenByEntity);
/*     */ 
/*     */         
/* 202 */         if (riddenByEntity != null)
/*     */         {
/*     */           
/* 205 */           riddenByEntity.func_184220_m((Entity)this);
/*     */         }
/*     */         
/* 208 */         if (!flag)
/*     */         {
/* 210 */           func_145778_a((Item)MCH_MOD.itemGLTD, 1, 0.0F);
/*     */         }
/*     */         
/* 213 */         W_WorldFunc.MOD_playSoundEffect(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v, "hit", 1.0F, 1.0F);
/*     */         
/* 215 */         func_70106_y();
/*     */       } 
/*     */       
/* 218 */       return true;
/*     */     } 
/*     */     
/* 221 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_70057_ab() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_70067_L() {
/* 233 */     return !this.field_70128_L;
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void setPositionAndRotation2(double par1, double par3, double par5, float par7, float par8, int par9) {
/* 239 */     if (this.isBoatEmpty) {
/*     */       
/* 241 */       this.gltdPosRotInc = par9 + 5;
/*     */     }
/*     */     else {
/*     */       
/* 245 */       double x = par1 - this.field_70165_t;
/* 246 */       double y = par3 - this.field_70163_u;
/* 247 */       double z = par5 - this.field_70161_v;
/*     */       
/* 249 */       if (x * x + y * y + z * z <= 1.0D) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 254 */       this.gltdPosRotInc = 3;
/*     */     } 
/*     */     
/* 257 */     this.gltdX = par1;
/* 258 */     this.gltdY = par3;
/* 259 */     this.gltdZ = par5;
/* 260 */     this.gltdYaw = par7;
/* 261 */     this.gltdPitch = par8;
/* 262 */     this.field_70159_w = this.velocityX;
/* 263 */     this.field_70181_x = this.velocityY;
/* 264 */     this.field_70179_y = this.velocityZ;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_70016_h(double x, double y, double z) {
/* 271 */     this.velocityX = this.field_70159_w = x;
/* 272 */     this.velocityY = this.field_70181_x = y;
/* 273 */     this.velocityZ = this.field_70179_y = z;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_70071_h_() {
/* 279 */     super.func_70071_h_();
/*     */     
/* 281 */     if (getTimeSinceHit() > 0)
/*     */     {
/* 283 */       setTimeSinceHit(getTimeSinceHit() - 1);
/*     */     }
/*     */     
/* 286 */     if (getDamageTaken() > 0.0F)
/*     */     {
/* 288 */       setDamageTaken(getDamageTaken() - 1);
/*     */     }
/*     */     
/* 291 */     this.field_70169_q = this.field_70165_t;
/* 292 */     this.field_70167_r = this.field_70163_u;
/* 293 */     this.field_70166_s = this.field_70161_v;
/*     */     
/* 295 */     double d3 = Math.sqrt(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
/* 296 */     Entity riddenByEntity = getRiddenByEntity();
/*     */ 
/*     */     
/* 299 */     if (riddenByEntity != null)
/*     */     {
/*     */       
/* 302 */       this.camera.updateViewer(0, riddenByEntity);
/*     */     }
/*     */     
/* 305 */     if (this.field_70170_p.field_72995_K && this.isBoatEmpty) {
/*     */       
/* 307 */       if (this.gltdPosRotInc > 0)
/*     */       {
/* 309 */         double d4 = this.field_70165_t + (this.gltdX - this.field_70165_t) / this.gltdPosRotInc;
/* 310 */         double d5 = this.field_70163_u + (this.gltdY - this.field_70163_u) / this.gltdPosRotInc;
/* 311 */         double d11 = this.field_70161_v + (this.gltdZ - this.field_70161_v) / this.gltdPosRotInc;
/* 312 */         double d10 = MathHelper.func_76138_g(this.gltdYaw - this.field_70177_z);
/* 313 */         this.field_70177_z = (float)(this.field_70177_z + d10 / this.gltdPosRotInc);
/* 314 */         this.field_70125_A = (float)(this.field_70125_A + (this.gltdPitch - this.field_70125_A) / this.gltdPosRotInc);
/*     */         
/* 316 */         this.gltdPosRotInc--;
/* 317 */         func_70107_b(d4, d5, d11);
/* 318 */         func_70101_b(this.field_70177_z, this.field_70125_A);
/*     */       }
/*     */       else
/*     */       {
/* 322 */         double d4 = this.field_70165_t + this.field_70159_w;
/* 323 */         double d5 = this.field_70163_u + this.field_70181_x;
/* 324 */         double d11 = this.field_70161_v + this.field_70179_y;
/* 325 */         func_70107_b(d4, d5, d11);
/*     */         
/* 327 */         if (this.field_70122_E) {
/*     */           
/* 329 */           this.field_70159_w *= 0.5D;
/* 330 */           this.field_70181_x *= 0.5D;
/* 331 */           this.field_70179_y *= 0.5D;
/*     */         } 
/*     */         
/* 334 */         this.field_70159_w *= 0.99D;
/* 335 */         this.field_70181_x *= 0.95D;
/* 336 */         this.field_70179_y *= 0.99D;
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 341 */       this.field_70181_x -= 0.04D;
/*     */       
/* 343 */       double d4 = Math.sqrt(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
/*     */       
/* 345 */       if (d4 > 0.35D) {
/*     */         
/* 347 */         double d = 0.35D / d4;
/* 348 */         this.field_70159_w *= d;
/* 349 */         this.field_70179_y *= d;
/* 350 */         d4 = 0.35D;
/*     */       } 
/*     */       
/* 353 */       if (d4 > d3 && this.speedMultiplier < 0.35D) {
/*     */         
/* 355 */         this.speedMultiplier += (0.35D - this.speedMultiplier) / 35.0D;
/*     */         
/* 357 */         if (this.speedMultiplier > 0.35D)
/*     */         {
/* 359 */           this.speedMultiplier = 0.35D;
/*     */         }
/*     */       }
/*     */       else {
/*     */         
/* 364 */         this.speedMultiplier -= (this.speedMultiplier - 0.07D) / 35.0D;
/*     */         
/* 366 */         if (this.speedMultiplier < 0.07D)
/*     */         {
/* 368 */           this.speedMultiplier = 0.07D;
/*     */         }
/*     */       } 
/*     */       
/* 372 */       if (this.field_70122_E) {
/*     */         
/* 374 */         this.field_70159_w *= 0.5D;
/* 375 */         this.field_70181_x *= 0.5D;
/* 376 */         this.field_70179_y *= 0.5D;
/*     */       } 
/*     */ 
/*     */       
/* 380 */       func_70091_d(MoverType.SELF, this.field_70159_w, this.field_70181_x, this.field_70179_y);
/*     */       
/* 382 */       this.field_70159_w *= 0.99D;
/* 383 */       this.field_70181_x *= 0.95D;
/* 384 */       this.field_70179_y *= 0.99D;
/*     */       
/* 386 */       this.field_70125_A = 0.0F;
/* 387 */       double d5 = this.field_70177_z;
/* 388 */       double d11 = this.field_70169_q - this.field_70165_t;
/* 389 */       double d10 = this.field_70166_s - this.field_70161_v;
/*     */       
/* 391 */       if (d11 * d11 + d10 * d10 > 0.001D)
/*     */       {
/* 393 */         d5 = (float)(Math.atan2(d10, d11) * 180.0D / Math.PI);
/*     */       }
/*     */       
/* 396 */       double d12 = MathHelper.func_76138_g(d5 - this.field_70177_z);
/*     */       
/* 398 */       if (d12 > 20.0D)
/*     */       {
/* 400 */         d12 = 20.0D;
/*     */       }
/*     */       
/* 403 */       if (d12 < -20.0D)
/*     */       {
/* 405 */         d12 = -20.0D;
/*     */       }
/*     */       
/* 408 */       this.field_70177_z = (float)(this.field_70177_z + d12);
/* 409 */       func_70101_b(this.field_70177_z, this.field_70125_A);
/*     */       
/* 411 */       if (!this.field_70170_p.field_72995_K) {
/*     */         
/* 413 */         if (MCH_Config.Collision_DestroyBlock.prmBool)
/*     */         {
/* 415 */           for (int l = 0; l < 4; l++) {
/*     */             
/* 417 */             int i1 = MathHelper.func_76128_c(this.field_70165_t + ((l % 2) - 0.5D) * 0.8D);
/* 418 */             int j1 = MathHelper.func_76128_c(this.field_70161_v + ((l / 2) - 0.5D) * 0.8D);
/*     */             
/* 420 */             for (int k1 = 0; k1 < 2; k1++) {
/*     */               
/* 422 */               int l1 = MathHelper.func_76128_c(this.field_70163_u) + k1;
/*     */               
/* 424 */               if (W_WorldFunc.isEqualBlock(this.field_70170_p, i1, l1, j1, W_Block.getSnowLayer()))
/*     */               {
/*     */                 
/* 427 */                 this.field_70170_p.func_175698_g(new BlockPos(i1, l1, j1));
/*     */               }
/*     */             } 
/*     */           } 
/*     */         }
/*     */         
/* 433 */         riddenByEntity = getRiddenByEntity();
/*     */         
/* 435 */         if (riddenByEntity != null && riddenByEntity.field_70128_L)
/*     */         {
/*     */           
/* 438 */           riddenByEntity.func_184210_p();
/*     */         }
/*     */       } 
/*     */     } 
/* 442 */     updateCameraPosition(false);
/*     */     
/* 444 */     if (this.countWait > 0)
/* 445 */       this.countWait--; 
/* 446 */     if (this.countWait < 0) {
/* 447 */       this.countWait++;
/*     */     }
/* 449 */     this.weaponCAS.update(this.countWait);
/*     */     
/* 451 */     riddenByEntity = getRiddenByEntity();
/*     */ 
/*     */     
/* 454 */     if (this.lastRiddenByEntity != null && riddenByEntity == null) {
/*     */       
/* 456 */       if (this.retryRiddenByEntityCheck < 3)
/*     */       {
/* 458 */         this.retryRiddenByEntityCheck++;
/* 459 */         setUnmoundPosition(this.lastRiddenByEntity);
/*     */       }
/*     */       else
/*     */       {
/* 463 */         unmountEntity();
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 468 */       this.retryRiddenByEntityCheck = 0;
/*     */     } 
/*     */     
/* 471 */     riddenByEntity = getRiddenByEntity();
/*     */ 
/*     */     
/* 474 */     if (riddenByEntity != null)
/*     */     {
/*     */       
/* 477 */       this.lastRiddenByEntity = riddenByEntity;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void setUnmoundPosition(Entity e) {
/* 483 */     if (e == null) {
/*     */       return;
/*     */     }
/* 486 */     float yaw = this.field_70177_z;
/* 487 */     double d0 = Math.sin(yaw * Math.PI / 180.0D) * 1.2D;
/* 488 */     double d1 = -Math.cos(yaw * Math.PI / 180.0D) * 1.2D;
/* 489 */     e.func_70107_b(this.field_70165_t + d0, this.field_70163_u + func_70042_X() + e.func_70033_W() + 1.0D, this.field_70161_v + d1);
/*     */     
/* 491 */     e.field_70142_S = e.field_70169_q = e.field_70165_t;
/* 492 */     e.field_70137_T = e.field_70167_r = e.field_70163_u;
/* 493 */     e.field_70136_U = e.field_70166_s = e.field_70161_v;
/*     */   }
/*     */ 
/*     */   
/*     */   public void unmountEntity() {
/* 498 */     this.camera.setMode(0, 0);
/* 499 */     this.camera.setCameraZoom(1.0F);
/*     */     
/* 501 */     if (!this.field_70170_p.field_72995_K) {
/*     */       
/* 503 */       Entity riddenByEntity = getRiddenByEntity();
/*     */ 
/*     */       
/* 506 */       if (riddenByEntity != null) {
/*     */ 
/*     */         
/* 509 */         if (!riddenByEntity.field_70128_L)
/*     */         {
/*     */           
/* 512 */           riddenByEntity.func_184210_p();
/*     */         }
/*     */       }
/* 515 */       else if (this.lastRiddenByEntity != null && !this.lastRiddenByEntity.field_70128_L) {
/*     */         
/* 517 */         this.camera.updateViewer(0, this.lastRiddenByEntity);
/* 518 */         setUnmoundPosition(this.lastRiddenByEntity);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 523 */     this.lastRiddenByEntity = null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateCameraPosition(boolean foreceUpdate) {
/* 528 */     Entity riddenByEntity = getRiddenByEntity();
/*     */ 
/*     */     
/* 531 */     if (foreceUpdate || (riddenByEntity != null && this.camera != null)) {
/*     */       
/* 533 */       double x = -Math.sin(this.field_70177_z * Math.PI / 180.0D) * 0.6D;
/* 534 */       double z = Math.cos(this.field_70177_z * Math.PI / 180.0D) * 0.6D;
/*     */       
/* 536 */       this.camera.setPosition(this.field_70165_t + x, this.field_70163_u + 0.7D, this.field_70161_v + z);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void zoomCamera(float f) {
/* 543 */     float z = this.camera.getCameraZoom();
/* 544 */     z += f;
/* 545 */     if (z < 1.0F)
/* 546 */       z = 1.0F; 
/* 547 */     if (z > 10.0F)
/* 548 */       z = 10.0F; 
/* 549 */     this.camera.setCameraZoom(z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_184232_k(Entity passenger) {
/* 557 */     if (func_184196_w(passenger)) {
/*     */       
/* 559 */       double x = Math.sin(this.field_70177_z * Math.PI / 180.0D) * 0.5D;
/* 560 */       double z = -Math.cos(this.field_70177_z * Math.PI / 180.0D) * 0.5D;
/*     */ 
/*     */       
/* 563 */       passenger.func_70107_b(this.field_70165_t + x, this.field_70163_u + func_70042_X() + passenger.func_70033_W(), this.field_70161_v + z);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void switchWeapon(int id) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean useCurrentWeapon(int option1, int option2) {
/* 574 */     Entity riddenByEntity = getRiddenByEntity();
/*     */ 
/*     */     
/* 577 */     if (this.countWait == 0 && riddenByEntity != null)
/*     */     {
/*     */ 
/*     */       
/* 581 */       if (this.weaponCAS.shot(riddenByEntity, this.camera.posX, this.camera.posY, this.camera.posZ, option1, option2)) {
/*     */ 
/*     */         
/* 584 */         this.countWait = this.weaponCAS.interval;
/*     */         
/* 586 */         if (this.field_70170_p.field_72995_K) {
/*     */           
/* 588 */           this.countWait += (this.countWait > 0) ? 10 : -10;
/*     */         }
/*     */         else {
/*     */           
/* 592 */           W_WorldFunc.MOD_playSoundEffect(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v, "gltd", 0.5F, 1.0F);
/*     */         } 
/*     */         
/* 595 */         return true;
/*     */       } 
/*     */     }
/* 598 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_70014_b(NBTTagCompound par1NBTTagCompound) {}
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_70037_a(NBTTagCompound par1NBTTagCompound) {}
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public float getShadowSize() {
/* 614 */     return 0.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_184230_a(EntityPlayer player, EnumHand hand) {
/* 621 */     Entity riddenByEntity = getRiddenByEntity();
/*     */ 
/*     */     
/* 624 */     if (riddenByEntity != null && riddenByEntity instanceof EntityPlayer && riddenByEntity != player)
/*     */     {
/* 626 */       return true;
/*     */     }
/*     */     
/* 629 */     player.field_70177_z = MathHelper.func_76142_g(this.field_70177_z);
/* 630 */     player.field_70125_A = MathHelper.func_76142_g(this.field_70125_A);
/*     */     
/* 632 */     if (!this.field_70170_p.field_72995_K) {
/*     */ 
/*     */       
/* 635 */       player.func_184220_m((Entity)this);
/*     */     }
/*     */     else {
/*     */       
/* 639 */       this.zoomDir = true;
/* 640 */       this.camera.setCameraZoom(1.0F);
/*     */       
/* 642 */       if (this.countWait > 0) {
/* 643 */         this.countWait = -this.countWait;
/*     */       }
/* 645 */       if (this.countWait > -60) {
/* 646 */         this.countWait = -60;
/*     */       }
/*     */     } 
/* 649 */     updateCameraPosition(true);
/*     */     
/* 651 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDamageTaken(int par1) {
/* 657 */     this.field_70180_af.func_187227_b(DAMAGE_TAKEN, Integer.valueOf(par1));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDamageTaken() {
/* 663 */     return ((Integer)this.field_70180_af.func_187225_a(DAMAGE_TAKEN)).intValue();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTimeSinceHit(int par1) {
/* 669 */     this.field_70180_af.func_187227_b(TIME_SINCE_HIT, Integer.valueOf(par1));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTimeSinceHit() {
/* 675 */     return ((Integer)this.field_70180_af.func_187225_a(TIME_SINCE_HIT)).intValue();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setForwardDirection(int par1) {
/* 681 */     this.field_70180_af.func_187227_b(FORWARD_DIR, Integer.valueOf(par1));
/*     */   }
/*     */ 
/*     */   
/*     */   public int getForwardDirection() {
/* 686 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void setIsBoatEmpty(boolean par1) {
/* 692 */     this.isBoatEmpty = par1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Entity getRiddenByEntity() {
/* 699 */     List<Entity> passengers = func_184188_bt();
/* 700 */     return passengers.isEmpty() ? null : passengers.get(0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getPickedResult(RayTraceResult target) {
/* 706 */     return new ItemStack((Item)MCH_MOD.itemGLTD);
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\gltd\MCH_EntityGLTD.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */