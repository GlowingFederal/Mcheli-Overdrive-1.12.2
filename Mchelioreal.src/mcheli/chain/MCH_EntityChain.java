/*     */ package mcheli.chain;
/*     */ 
/*     */ import java.util.List;
/*     */ import mcheli.aircraft.MCH_EntityAircraft;
/*     */ import mcheli.wrapper.W_Entity;
/*     */ import mcheli.wrapper.W_WorldFunc;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.MoverType;
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
/*     */ public class MCH_EntityChain
/*     */   extends W_Entity
/*     */ {
/*  32 */   private static final DataParameter<Integer> TOWED_ID = EntityDataManager.func_187226_a(MCH_EntityChain.class, DataSerializers.field_187192_b);
/*     */   
/*  34 */   private static final DataParameter<Integer> TOW_ID = EntityDataManager.func_187226_a(MCH_EntityChain.class, DataSerializers.field_187192_b);
/*     */   
/*     */   private int isServerTowEntitySearchCount;
/*     */   
/*     */   public Entity towEntity;
/*     */   
/*     */   public Entity towedEntity;
/*     */   public String towEntityUUID;
/*     */   public String towedEntityUUID;
/*     */   private int chainLength;
/*     */   private boolean isTowing;
/*     */   
/*     */   public MCH_EntityChain(World world) {
/*  47 */     super(world);
/*  48 */     this.field_70156_m = true;
/*  49 */     func_70105_a(1.0F, 1.0F);
/*     */ 
/*     */     
/*  52 */     this.towEntity = null;
/*  53 */     this.towedEntity = null;
/*  54 */     this.towEntityUUID = "";
/*  55 */     this.towedEntityUUID = "";
/*  56 */     this.isTowing = false;
/*  57 */     this.field_70158_ak = true;
/*     */     
/*  59 */     setChainLength(4);
/*     */     
/*  61 */     this.isServerTowEntitySearchCount = 50;
/*     */   }
/*     */ 
/*     */   
/*     */   public MCH_EntityChain(World par1World, double par2, double par4, double par6) {
/*  66 */     this(par1World);
/*     */     
/*  68 */     func_70107_b(par2, par4, par6);
/*  69 */     this.field_70159_w = 0.0D;
/*  70 */     this.field_70181_x = 0.0D;
/*  71 */     this.field_70179_y = 0.0D;
/*  72 */     this.field_70169_q = par2;
/*  73 */     this.field_70167_r = par4;
/*  74 */     this.field_70166_s = par6;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean func_70041_e_() {
/*  80 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_70088_a() {
/*  88 */     this.field_70180_af.func_187214_a(TOWED_ID, Integer.valueOf(0));
/*  89 */     this.field_70180_af.func_187214_a(TOW_ID, Integer.valueOf(0));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AxisAlignedBB func_70114_g(Entity par1Entity) {
/*  96 */     return par1Entity.func_174813_aQ();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AxisAlignedBB func_70046_E() {
/* 102 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_70104_M() {
/* 108 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_70097_a(DamageSource d, float par2) {
/* 114 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setChainLength(int n) {
/* 119 */     if (n > 15)
/* 120 */       n = 15; 
/* 121 */     if (n < 3)
/* 122 */       n = 3; 
/* 123 */     this.chainLength = n;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getChainLength() {
/* 128 */     return this.chainLength;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_70106_y() {
/* 134 */     super.func_70106_y();
/*     */     
/* 136 */     playDisconnectTowingEntity();
/*     */     
/* 138 */     this.isTowing = false;
/* 139 */     this.towEntity = null;
/* 140 */     this.towedEntity = null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isTowingEntity() {
/* 145 */     return (this.isTowing && !this.field_70128_L && this.towedEntity != null && this.towEntity != null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_70067_L() {
/* 151 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setTowEntity(Entity towedEntity, Entity towEntity) {
/* 156 */     if (towedEntity != null && towEntity != null && !towedEntity.field_70128_L && !towEntity.field_70128_L && 
/* 157 */       !W_Entity.isEqual(towedEntity, towEntity)) {
/*     */       
/* 159 */       this.isTowing = true;
/* 160 */       this.towedEntity = towedEntity;
/* 161 */       this.towEntity = towEntity;
/*     */       
/* 163 */       if (!this.field_70170_p.field_72995_K) {
/*     */ 
/*     */ 
/*     */         
/* 167 */         this.field_70180_af.func_187227_b(TOWED_ID, Integer.valueOf(W_Entity.getEntityId(towedEntity)));
/* 168 */         this.field_70180_af.func_187227_b(TOW_ID, Integer.valueOf(W_Entity.getEntityId(towEntity)));
/*     */         
/* 170 */         this.isServerTowEntitySearchCount = 0;
/*     */       } 
/*     */       
/* 173 */       if (towEntity instanceof MCH_EntityAircraft)
/*     */       {
/* 175 */         ((MCH_EntityAircraft)towEntity).setTowChainEntity(this);
/*     */       }
/*     */       
/* 178 */       if (towedEntity instanceof MCH_EntityAircraft)
/*     */       {
/* 180 */         ((MCH_EntityAircraft)towedEntity).setTowedChainEntity(this);
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 185 */       this.isTowing = false;
/* 186 */       this.towedEntity = null;
/* 187 */       this.towEntity = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void searchTowingEntity() {
/* 193 */     if ((this.towedEntity == null || this.towEntity == null) && !this.towedEntityUUID.isEmpty() && 
/*     */       
/* 195 */       !this.towEntityUUID.isEmpty() && func_174813_aQ() != null) {
/*     */ 
/*     */       
/* 198 */       List<Entity> list = this.field_70170_p.func_72839_b((Entity)this, 
/*     */           
/* 200 */           func_174813_aQ().func_72314_b(32.0D, 32.0D, 32.0D));
/*     */       
/* 202 */       if (list != null)
/*     */       {
/* 204 */         for (int i = 0; i < list.size(); i++) {
/*     */           
/* 206 */           Entity entity = list.get(i);
/* 207 */           String uuid = entity.getPersistentID().toString();
/*     */           
/* 209 */           if (this.towedEntity == null && uuid.compareTo(this.towedEntityUUID) == 0) {
/*     */             
/* 211 */             this.towedEntity = entity;
/*     */           }
/* 213 */           else if (this.towEntity == null && uuid.compareTo(this.towEntityUUID) == 0) {
/*     */             
/* 215 */             this.towEntity = entity;
/*     */           }
/* 217 */           else if (this.towEntity != null && this.towedEntity != null) {
/*     */             
/* 219 */             setTowEntity(this.towedEntity, this.towEntity);
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_70071_h_() {
/* 230 */     super.func_70071_h_();
/*     */     
/* 232 */     if (this.towedEntity == null || this.towedEntity.field_70128_L || this.towEntity == null || this.towEntity.field_70128_L) {
/*     */       
/* 234 */       this.towedEntity = null;
/* 235 */       this.towEntity = null;
/* 236 */       this.isTowing = false;
/*     */     } 
/*     */     
/* 239 */     if (this.towedEntity != null)
/*     */     {
/* 241 */       this.towedEntity.field_70143_R = 0.0F;
/*     */     }
/*     */     
/* 244 */     this.field_70169_q = this.field_70165_t;
/* 245 */     this.field_70167_r = this.field_70163_u;
/* 246 */     this.field_70166_s = this.field_70161_v;
/*     */     
/* 248 */     if (this.field_70170_p.field_72995_K) {
/*     */       
/* 250 */       onUpdate_Client();
/*     */     }
/*     */     else {
/*     */       
/* 254 */       onUpdate_Server();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onUpdate_Client() {
/* 260 */     if (!isTowingEntity())
/*     */     {
/*     */ 
/*     */       
/* 264 */       setTowEntity(this.field_70170_p.func_73045_a(((Integer)this.field_70180_af.func_187225_a(TOWED_ID)).intValue()), this.field_70170_p
/* 265 */           .func_73045_a(((Integer)this.field_70180_af.func_187225_a(TOW_ID)).intValue()));
/*     */     }
/*     */     
/* 268 */     double d4 = this.field_70165_t + this.field_70159_w;
/* 269 */     double d5 = this.field_70163_u + this.field_70181_x;
/* 270 */     double d11 = this.field_70161_v + this.field_70179_y;
/*     */     
/* 272 */     func_70107_b(d4, d5, d11);
/*     */     
/* 274 */     if (this.field_70122_E) {
/*     */       
/* 276 */       this.field_70159_w *= 0.5D;
/* 277 */       this.field_70181_x *= 0.5D;
/* 278 */       this.field_70179_y *= 0.5D;
/*     */     } 
/*     */     
/* 281 */     this.field_70159_w *= 0.99D;
/* 282 */     this.field_70181_x *= 0.95D;
/* 283 */     this.field_70179_y *= 0.99D;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onUpdate_Server() {
/* 288 */     if (this.isServerTowEntitySearchCount > 0) {
/*     */       
/* 290 */       searchTowingEntity();
/*     */       
/* 292 */       if (this.towEntity != null && this.towedEntity != null)
/*     */       {
/* 294 */         this.isServerTowEntitySearchCount = 0;
/*     */       }
/*     */       else
/*     */       {
/* 298 */         this.isServerTowEntitySearchCount--;
/*     */       }
/*     */     
/* 301 */     } else if (this.towEntity == null || this.towedEntity == null) {
/*     */       
/* 303 */       func_70106_y();
/*     */     } 
/*     */     
/* 306 */     this.field_70181_x -= 0.01D;
/*     */     
/* 308 */     if (!this.isTowing) {
/*     */       
/* 310 */       this.field_70159_w *= 0.8D;
/* 311 */       this.field_70181_x *= 0.8D;
/* 312 */       this.field_70179_y *= 0.8D;
/*     */     } 
/*     */ 
/*     */     
/* 316 */     func_70091_d(MoverType.SELF, this.field_70159_w, this.field_70181_x, this.field_70179_y);
/*     */     
/* 318 */     if (isTowingEntity()) {
/*     */       
/* 320 */       func_70107_b(this.towEntity.field_70165_t, this.towEntity.field_70163_u + 2.0D, this.towEntity.field_70161_v);
/* 321 */       updateTowingEntityPosRot();
/*     */     } 
/*     */     
/* 324 */     this.field_70159_w *= 0.99D;
/* 325 */     this.field_70181_x *= 0.95D;
/* 326 */     this.field_70179_y *= 0.99D;
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateTowingEntityPosRot() {
/* 331 */     double dx = this.towedEntity.field_70165_t - this.towEntity.field_70165_t;
/* 332 */     double dy = this.towedEntity.field_70163_u - this.towEntity.field_70163_u;
/* 333 */     double dz = this.towedEntity.field_70161_v - this.towEntity.field_70161_v;
/* 334 */     double dist = MathHelper.func_76133_a(dx * dx + dy * dy + dz * dz);
/* 335 */     float DIST = getChainLength();
/* 336 */     float MAX_DIST = (getChainLength() + 2);
/*     */     
/* 338 */     if (dist > DIST) {
/*     */ 
/*     */       
/* 341 */       this.towedEntity.field_70177_z = (float)(Math.atan2(dz, dx) * 180.0D / Math.PI) + 90.0F;
/* 342 */       this.towedEntity.field_70126_B = this.towedEntity.field_70177_z;
/*     */       
/* 344 */       double tmp = dist - DIST;
/* 345 */       float accl = 0.001F;
/* 346 */       this.towedEntity.field_70159_w -= dx * accl / tmp;
/* 347 */       this.towedEntity.field_70181_x -= dy * accl / tmp;
/* 348 */       this.towedEntity.field_70179_y -= dz * accl / tmp;
/*     */       
/* 350 */       if (dist > MAX_DIST)
/*     */       {
/* 352 */         this.towedEntity.func_70107_b(this.towEntity.field_70165_t + dx * MAX_DIST / dist, this.towEntity.field_70163_u + dy * MAX_DIST / dist, this.towEntity.field_70161_v + dz * MAX_DIST / dist);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void playDisconnectTowingEntity() {
/* 360 */     W_WorldFunc.MOD_playSoundEffect(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v, "chain_ct", 1.0F, 1.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_70014_b(NBTTagCompound nbt) {
/* 366 */     if (this.isTowing && this.towEntity != null && !this.towEntity.field_70128_L && this.towedEntity != null && !this.towedEntity.field_70128_L) {
/*     */ 
/*     */       
/* 369 */       nbt.func_74778_a("TowEntityUUID", this.towEntity.getPersistentID().toString());
/* 370 */       nbt.func_74778_a("TowedEntityUUID", this.towedEntity.getPersistentID().toString());
/* 371 */       nbt.func_74768_a("ChainLength", getChainLength());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_70037_a(NBTTagCompound nbt) {
/* 378 */     this.towEntityUUID = nbt.func_74779_i("TowEntityUUID");
/* 379 */     this.towedEntityUUID = nbt.func_74779_i("TowedEntityUUID");
/* 380 */     setChainLength(nbt.func_74762_e("ChainLength"));
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public float getShadowSize() {
/* 386 */     return 0.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_184230_a(EntityPlayer player, EnumHand hand) {
/* 396 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\chain\MCH_EntityChain.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */