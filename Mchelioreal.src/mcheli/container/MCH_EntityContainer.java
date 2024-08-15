/*     */ package mcheli.container;
/*     */ 
/*     */ import java.util.List;
/*     */ import mcheli.MCH_Config;
/*     */ import mcheli.MCH_Lib;
/*     */ import mcheli.MCH_MOD;
/*     */ import mcheli.__helper.entity.IEntityItemStackPickable;
/*     */ import mcheli.aircraft.MCH_EntityAircraft;
/*     */ import mcheli.aircraft.MCH_IEntityCanRideAircraft;
/*     */ import mcheli.aircraft.MCH_SeatRackInfo;
/*     */ import mcheli.multiplay.MCH_Multiplay;
/*     */ import mcheli.wrapper.W_AxisAlignedBB;
/*     */ import mcheli.wrapper.W_Block;
/*     */ import mcheli.wrapper.W_Blocks;
/*     */ import mcheli.wrapper.W_EntityContainer;
/*     */ import mcheli.wrapper.W_WorldFunc;
/*     */ import net.minecraft.block.material.Material;
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
/*     */ 
/*     */ 
/*     */ public class MCH_EntityContainer
/*     */   extends W_EntityContainer
/*     */   implements MCH_IEntityCanRideAircraft, IEntityItemStackPickable
/*     */ {
/*     */   public static final float Y_OFFSET = 0.5F;
/*  51 */   private static final DataParameter<Integer> TIME_SINCE_HIT = EntityDataManager.func_187226_a(MCH_EntityContainer.class, DataSerializers.field_187192_b);
/*     */   
/*  53 */   private static final DataParameter<Integer> FORWARD_DIR = EntityDataManager.func_187226_a(MCH_EntityContainer.class, DataSerializers.field_187192_b);
/*     */   
/*  55 */   private static final DataParameter<Integer> DAMAGE_TAKEN = EntityDataManager.func_187226_a(MCH_EntityContainer.class, DataSerializers.field_187192_b);
/*     */   
/*     */   private double speedMultiplier;
/*     */   
/*     */   private int boatPosRotationIncrements;
/*     */   
/*     */   private double boatX;
/*     */   
/*     */   private double boatY;
/*     */   private double boatZ;
/*     */   private double boatYaw;
/*     */   private double boatPitch;
/*     */   @SideOnly(Side.CLIENT)
/*     */   private double velocityX;
/*     */   @SideOnly(Side.CLIENT)
/*     */   private double velocityY;
/*     */   @SideOnly(Side.CLIENT)
/*     */   private double velocityZ;
/*     */   
/*     */   public MCH_EntityContainer(World par1World) {
/*  75 */     super(par1World);
/*  76 */     this.speedMultiplier = 0.07D;
/*  77 */     this.field_70156_m = true;
/*  78 */     func_70105_a(2.0F, 1.0F);
/*     */     
/*  80 */     this.field_70138_W = 0.6F;
/*  81 */     this.field_70178_ae = true;
/*     */     
/*  83 */     this._renderDistanceWeight = 2.0D;
/*     */   }
/*     */ 
/*     */   
/*     */   public MCH_EntityContainer(World par1World, double par2, double par4, double par6) {
/*  88 */     this(par1World);
/*     */     
/*  90 */     func_70107_b(par2, par4 + 0.5D, par6);
/*  91 */     this.field_70159_w = 0.0D;
/*  92 */     this.field_70181_x = 0.0D;
/*  93 */     this.field_70179_y = 0.0D;
/*  94 */     this.field_70169_q = par2;
/*  95 */     this.field_70167_r = par4;
/*  96 */     this.field_70166_s = par6;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean func_70041_e_() {
/* 102 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_70088_a() {
/* 111 */     this.field_70180_af.func_187214_a(TIME_SINCE_HIT, Integer.valueOf(0));
/* 112 */     this.field_70180_af.func_187214_a(FORWARD_DIR, Integer.valueOf(1));
/* 113 */     this.field_70180_af.func_187214_a(DAMAGE_TAKEN, Integer.valueOf(0));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AxisAlignedBB func_70114_g(Entity par1Entity) {
/* 120 */     return par1Entity.func_174813_aQ();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AxisAlignedBB func_70046_E() {
/* 127 */     return func_174813_aQ();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_70104_M() {
/* 133 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int func_70302_i_() {
/* 139 */     return 54;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getInvName() {
/* 145 */     return "Container " + super.getInvName();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double func_70042_X() {
/* 151 */     return -0.3D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_70097_a(DamageSource ds, float damage) {
/* 158 */     if (func_180431_b(ds))
/*     */     {
/* 160 */       return false;
/*     */     }
/*     */     
/* 163 */     if (this.field_70170_p.field_72995_K || this.field_70128_L)
/*     */     {
/* 165 */       return false;
/*     */     }
/*     */     
/* 168 */     damage = MCH_Config.applyDamageByExternal((Entity)this, ds, damage);
/*     */     
/* 170 */     if (!MCH_Multiplay.canAttackEntity(ds, (Entity)this))
/*     */     {
/* 172 */       return false;
/*     */     }
/*     */     
/* 175 */     if (ds.func_76346_g() instanceof EntityPlayer && ds.func_76355_l().equalsIgnoreCase("player")) {
/*     */       
/* 177 */       MCH_Lib.DbgLog(this.field_70170_p, "MCH_EntityContainer.attackEntityFrom:damage=%.1f:%s", new Object[] {
/*     */             
/* 179 */             Float.valueOf(damage), ds.func_76355_l()
/*     */           });
/* 181 */       W_WorldFunc.MOD_playSoundAtEntity((Entity)this, "hit", 1.0F, 1.3F);
/* 182 */       setDamageTaken(getDamageTaken() + (int)(damage * 20.0F));
/*     */     }
/*     */     else {
/*     */       
/* 186 */       return false;
/*     */     } 
/* 188 */     setForwardDirection(-getForwardDirection());
/* 189 */     setTimeSinceHit(10);
/* 190 */     func_70018_K();
/*     */     
/* 192 */     boolean flag = (ds.func_76346_g() instanceof EntityPlayer && ((EntityPlayer)ds.func_76346_g()).field_71075_bZ.field_75098_d);
/*     */     
/* 194 */     if (flag || getDamageTaken() > 40.0F) {
/*     */       
/* 196 */       if (!flag)
/*     */       {
/* 198 */         func_145778_a((Item)MCH_MOD.itemContainer, 1, 0.0F);
/*     */       }
/*     */       
/* 201 */       func_70106_y();
/*     */     } 
/* 203 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_70057_ab() {
/* 210 */     setForwardDirection(-getForwardDirection());
/* 211 */     setTimeSinceHit(10);
/* 212 */     setDamageTaken(getDamageTaken() * 11);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_70067_L() {
/* 218 */     return !this.field_70128_L;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_180426_a(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean teleport) {
/* 236 */     this.boatPosRotationIncrements = posRotationIncrements + 10;
/* 237 */     this.boatX = x;
/* 238 */     this.boatY = y;
/* 239 */     this.boatZ = z;
/* 240 */     this.boatYaw = yaw;
/* 241 */     this.boatPitch = pitch;
/* 242 */     this.field_70159_w = this.velocityX;
/* 243 */     this.field_70181_x = this.velocityY;
/* 244 */     this.field_70179_y = this.velocityZ;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_70016_h(double par1, double par3, double par5) {
/* 251 */     this.velocityX = this.field_70159_w = par1;
/* 252 */     this.velocityY = this.field_70181_x = par3;
/* 253 */     this.velocityZ = this.field_70179_y = par5;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_70071_h_() {
/* 259 */     super.func_70071_h_();
/*     */     
/* 261 */     if (getTimeSinceHit() > 0)
/*     */     {
/* 263 */       setTimeSinceHit(getTimeSinceHit() - 1);
/*     */     }
/*     */     
/* 266 */     if (getDamageTaken() > 0.0F)
/*     */     {
/* 268 */       setDamageTaken(getDamageTaken() - 1);
/*     */     }
/*     */     
/* 271 */     this.field_70169_q = this.field_70165_t;
/* 272 */     this.field_70167_r = this.field_70163_u;
/* 273 */     this.field_70166_s = this.field_70161_v;
/* 274 */     byte b0 = 5;
/* 275 */     double d0 = 0.0D;
/*     */     
/* 277 */     for (int i = 0; i < b0; i++) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 283 */       AxisAlignedBB boundingBox = func_174813_aQ();
/* 284 */       double d1 = boundingBox.field_72338_b + (boundingBox.field_72337_e - boundingBox.field_72338_b) * (i + 0) / b0 - 0.125D;
/* 285 */       double d2 = boundingBox.field_72338_b + (boundingBox.field_72337_e - boundingBox.field_72338_b) * (i + 1) / b0 - 0.125D;
/* 286 */       AxisAlignedBB axisalignedbb = W_AxisAlignedBB.getAABB(boundingBox.field_72340_a, d1, boundingBox.field_72339_c, boundingBox.field_72336_d, d2, boundingBox.field_72334_f);
/*     */ 
/*     */ 
/*     */       
/* 290 */       if (this.field_70170_p.func_72875_a(axisalignedbb, Material.field_151586_h)) {
/*     */         
/* 292 */         d0 += 1.0D / b0;
/*     */       
/*     */       }
/* 295 */       else if (this.field_70170_p.func_72875_a(axisalignedbb, Material.field_151587_i)) {
/*     */         
/* 297 */         d0 += 1.0D / b0;
/*     */       } 
/*     */     } 
/*     */     
/* 301 */     double d3 = Math.sqrt(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
/*     */     
/* 303 */     if (d3 > 0.2625D);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 309 */     if (this.field_70170_p.field_72995_K) {
/*     */       
/* 311 */       if (this.boatPosRotationIncrements > 0)
/*     */       {
/* 313 */         double d4 = this.field_70165_t + (this.boatX - this.field_70165_t) / this.boatPosRotationIncrements;
/* 314 */         double d5 = this.field_70163_u + (this.boatY - this.field_70163_u) / this.boatPosRotationIncrements;
/* 315 */         double d11 = this.field_70161_v + (this.boatZ - this.field_70161_v) / this.boatPosRotationIncrements;
/* 316 */         double d10 = MathHelper.func_76138_g(this.boatYaw - this.field_70177_z);
/* 317 */         this.field_70177_z = (float)(this.field_70177_z + d10 / this.boatPosRotationIncrements);
/* 318 */         this.field_70125_A = (float)(this.field_70125_A + (this.boatPitch - this.field_70125_A) / this.boatPosRotationIncrements);
/*     */         
/* 320 */         this.boatPosRotationIncrements--;
/* 321 */         func_70107_b(d4, d5, d11);
/* 322 */         func_70101_b(this.field_70177_z, this.field_70125_A);
/*     */       }
/*     */       else
/*     */       {
/* 326 */         double d4 = this.field_70165_t + this.field_70159_w;
/* 327 */         double d5 = this.field_70163_u + this.field_70181_x;
/* 328 */         double d11 = this.field_70161_v + this.field_70179_y;
/* 329 */         func_70107_b(d4, d5, d11);
/*     */         
/* 331 */         if (this.field_70122_E) {
/*     */ 
/*     */           
/* 334 */           this.field_70159_w *= 0.8999999761581421D;
/*     */           
/* 336 */           this.field_70179_y *= 0.8999999761581421D;
/*     */         } 
/*     */         
/* 339 */         this.field_70159_w *= 0.99D;
/* 340 */         this.field_70181_x *= 0.95D;
/* 341 */         this.field_70179_y *= 0.99D;
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 346 */       if (d0 < 1.0D) {
/*     */         
/* 348 */         double d = d0 * 2.0D - 1.0D;
/* 349 */         this.field_70181_x += 0.04D * d;
/*     */       }
/*     */       else {
/*     */         
/* 353 */         if (this.field_70181_x < 0.0D)
/*     */         {
/* 355 */           this.field_70181_x /= 2.0D;
/*     */         }
/*     */         
/* 358 */         this.field_70181_x += 0.007D;
/*     */       } 
/*     */       
/* 361 */       double d4 = Math.sqrt(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
/*     */       
/* 363 */       if (d4 > 0.35D) {
/*     */         
/* 365 */         double d = 0.35D / d4;
/* 366 */         this.field_70159_w *= d;
/* 367 */         this.field_70179_y *= d;
/* 368 */         d4 = 0.35D;
/*     */       } 
/*     */       
/* 371 */       if (d4 > d3 && this.speedMultiplier < 0.35D) {
/*     */         
/* 373 */         this.speedMultiplier += (0.35D - this.speedMultiplier) / 35.0D;
/*     */         
/* 375 */         if (this.speedMultiplier > 0.35D)
/*     */         {
/* 377 */           this.speedMultiplier = 0.35D;
/*     */         }
/*     */       }
/*     */       else {
/*     */         
/* 382 */         this.speedMultiplier -= (this.speedMultiplier - 0.07D) / 35.0D;
/*     */         
/* 384 */         if (this.speedMultiplier < 0.07D)
/*     */         {
/* 386 */           this.speedMultiplier = 0.07D;
/*     */         }
/*     */       } 
/*     */       
/* 390 */       if (this.field_70122_E) {
/*     */ 
/*     */         
/* 393 */         this.field_70159_w *= 0.8999999761581421D;
/* 394 */         this.field_70179_y *= 0.8999999761581421D;
/*     */       } 
/*     */ 
/*     */       
/* 398 */       func_70091_d(MoverType.SELF, this.field_70159_w, this.field_70181_x, this.field_70179_y);
/*     */       
/* 400 */       this.field_70159_w *= 0.99D;
/* 401 */       this.field_70181_x *= 0.95D;
/* 402 */       this.field_70179_y *= 0.99D;
/*     */       
/* 404 */       this.field_70125_A = 0.0F;
/* 405 */       double d5 = this.field_70177_z;
/* 406 */       double d11 = this.field_70169_q - this.field_70165_t;
/* 407 */       double d10 = this.field_70166_s - this.field_70161_v;
/*     */       
/* 409 */       if (d11 * d11 + d10 * d10 > 0.001D)
/*     */       {
/* 411 */         d5 = (float)(Math.atan2(d10, d11) * 180.0D / Math.PI);
/*     */       }
/*     */       
/* 414 */       double d12 = MathHelper.func_76138_g(d5 - this.field_70177_z);
/*     */       
/* 416 */       if (d12 > 5.0D)
/*     */       {
/* 418 */         d12 = 5.0D;
/*     */       }
/*     */       
/* 421 */       if (d12 < -5.0D)
/*     */       {
/* 423 */         d12 = -5.0D;
/*     */       }
/*     */       
/* 426 */       this.field_70177_z = (float)(this.field_70177_z + d12);
/* 427 */       func_70101_b(this.field_70177_z, this.field_70125_A);
/*     */       
/* 429 */       if (!this.field_70170_p.field_72995_K) {
/*     */ 
/*     */ 
/*     */         
/* 433 */         List<Entity> list = this.field_70170_p.func_72839_b((Entity)this, 
/* 434 */             func_174813_aQ().func_72314_b(0.2D, 0.0D, 0.2D));
/*     */         
/* 436 */         if (list != null && !list.isEmpty())
/*     */         {
/* 438 */           for (int l = 0; l < list.size(); l++) {
/*     */             
/* 440 */             Entity entity = list.get(l);
/*     */             
/* 442 */             if (entity.func_70104_M() && entity instanceof MCH_EntityContainer)
/*     */             {
/* 444 */               entity.func_70108_f((Entity)this);
/*     */             }
/*     */           } 
/*     */         }
/*     */         
/* 449 */         if (MCH_Config.Collision_DestroyBlock.prmBool)
/*     */         {
/* 451 */           for (int l = 0; l < 4; l++) {
/*     */             
/* 453 */             int i1 = MathHelper.func_76128_c(this.field_70165_t + ((l % 2) - 0.5D) * 0.8D);
/* 454 */             int j1 = MathHelper.func_76128_c(this.field_70161_v + ((l / 2) - 0.5D) * 0.8D);
/*     */             
/* 456 */             for (int k1 = 0; k1 < 2; k1++) {
/*     */               
/* 458 */               int l1 = MathHelper.func_76128_c(this.field_70163_u) + k1;
/*     */               
/* 460 */               if (W_WorldFunc.isEqualBlock(this.field_70170_p, i1, l1, j1, W_Block.getSnowLayer())) {
/*     */ 
/*     */                 
/* 463 */                 this.field_70170_p.func_175698_g(new BlockPos(i1, l1, j1));
/*     */               }
/* 465 */               else if (W_WorldFunc.isEqualBlock(this.field_70170_p, i1, l1, j1, W_Blocks.field_150392_bi)) {
/*     */                 
/* 467 */                 W_WorldFunc.destroyBlock(this.field_70170_p, i1, l1, j1, true);
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_70014_b(NBTTagCompound par1NBTTagCompound) {
/* 479 */     super.func_70014_b(par1NBTTagCompound);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_70037_a(NBTTagCompound par1NBTTagCompound) {
/* 485 */     super.func_70037_a(par1NBTTagCompound);
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public float getShadowSize() {
/* 491 */     return 2.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_184230_a(EntityPlayer player, EnumHand hand) {
/* 498 */     if (player != null)
/*     */     {
/*     */       
/* 501 */       displayInventory(player);
/*     */     }
/* 503 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDamageTaken(int par1) {
/* 509 */     this.field_70180_af.func_187227_b(DAMAGE_TAKEN, Integer.valueOf(par1));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDamageTaken() {
/* 515 */     return ((Integer)this.field_70180_af.func_187225_a(DAMAGE_TAKEN)).intValue();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTimeSinceHit(int par1) {
/* 521 */     this.field_70180_af.func_187227_b(TIME_SINCE_HIT, Integer.valueOf(par1));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTimeSinceHit() {
/* 527 */     return ((Integer)this.field_70180_af.func_187225_a(TIME_SINCE_HIT)).intValue();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setForwardDirection(int par1) {
/* 533 */     this.field_70180_af.func_187227_b(FORWARD_DIR, Integer.valueOf(par1));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getForwardDirection() {
/* 539 */     return ((Integer)this.field_70180_af.func_187225_a(FORWARD_DIR)).intValue();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canRideAircraft(MCH_EntityAircraft ac, int seatID, MCH_SeatRackInfo info) {
/* 545 */     for (String s : info.names) {
/*     */       
/* 547 */       if (s.equalsIgnoreCase("container"))
/*     */       {
/* 549 */         return (ac.func_184187_bx() == null && func_184187_bx() == null);
/*     */       }
/*     */     } 
/* 552 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSkipNormalRender() {
/* 558 */     return func_184187_bx() instanceof mcheli.aircraft.MCH_EntitySeat;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getPickedResult(RayTraceResult target) {
/* 564 */     return new ItemStack((Item)MCH_MOD.itemContainer);
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\container\MCH_EntityContainer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */