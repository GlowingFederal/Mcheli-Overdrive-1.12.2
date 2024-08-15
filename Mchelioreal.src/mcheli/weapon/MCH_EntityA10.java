/*     */ package mcheli.weapon;
/*     */ 
/*     */ import javax.annotation.Nullable;
/*     */ import mcheli.wrapper.W_Entity;
/*     */ import mcheli.wrapper.W_WorldFunc;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.network.datasync.DataParameter;
/*     */ import net.minecraft.network.datasync.DataSerializers;
/*     */ import net.minecraft.network.datasync.EntityDataManager;
/*     */ import net.minecraft.util.DamageSource;
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
/*     */ public class MCH_EntityA10
/*     */   extends W_Entity
/*     */ {
/*  28 */   private static final DataParameter<String> WEAPON_NAME = EntityDataManager.func_187226_a(MCH_EntityA10.class, DataSerializers.field_187194_d);
/*     */   
/*  30 */   public final int DESPAWN_COUNT = 70;
/*     */   
/*  32 */   public int despawnCount = 0;
/*     */   public Entity shootingAircraft;
/*     */   public Entity shootingEntity;
/*  35 */   public int shotCount = 0;
/*  36 */   public int direction = 0;
/*     */   
/*     */   public int power;
/*     */   public float acceleration;
/*     */   public int explosionPower;
/*     */   public boolean isFlaming;
/*     */   public String name;
/*     */   public MCH_WeaponInfo weaponInfo;
/*  44 */   static int snd_num = 0;
/*     */ 
/*     */   
/*     */   public MCH_EntityA10(World world) {
/*  48 */     super(world);
/*     */     
/*  50 */     this.field_70158_ak = true;
/*  51 */     this.field_70156_m = false;
/*  52 */     func_70105_a(5.0F, 3.0F);
/*     */     
/*  54 */     this.field_70159_w = 0.0D;
/*  55 */     this.field_70181_x = 0.0D;
/*  56 */     this.field_70179_y = 0.0D;
/*  57 */     this.power = 32;
/*  58 */     this.acceleration = 4.0F;
/*  59 */     this.explosionPower = 1;
/*  60 */     this.isFlaming = false;
/*  61 */     this.shootingEntity = null;
/*  62 */     this.shootingAircraft = null;
/*  63 */     this.field_70178_ae = true;
/*     */ 
/*     */     
/*  66 */     this._renderDistanceWeight *= 10.0D;
/*     */   }
/*     */ 
/*     */   
/*     */   public MCH_EntityA10(World world, double x, double y, double z) {
/*  71 */     this(world);
/*  72 */     this.field_70142_S = this.field_70169_q = this.field_70165_t = x;
/*  73 */     this.field_70137_T = this.field_70167_r = this.field_70163_u = y;
/*  74 */     this.field_70136_U = this.field_70166_s = this.field_70161_v = z;
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
/*     */   protected void func_70088_a() {
/*  87 */     this.field_70180_af.func_187214_a(WEAPON_NAME, "");
/*     */   }
/*     */ 
/*     */   
/*     */   public void setWeaponName(String s) {
/*  92 */     if (s != null && !s.isEmpty()) {
/*     */       
/*  94 */       this.weaponInfo = MCH_WeaponInfoManager.get(s);
/*     */       
/*  96 */       if (this.weaponInfo != null && !this.field_70170_p.field_72995_K)
/*     */       {
/*     */         
/*  99 */         this.field_70180_af.func_187227_b(WEAPON_NAME, s);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getWeaponName() {
/* 107 */     return (String)this.field_70180_af.func_187225_a(WEAPON_NAME);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public MCH_WeaponInfo getInfo() {
/* 113 */     return this.weaponInfo;
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
/* 133 */     return false;
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
/* 145 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_70106_y() {
/* 151 */     super.func_70106_y();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_70071_h_() {
/* 157 */     super.func_70071_h_();
/*     */     
/* 159 */     if (!this.field_70128_L)
/*     */     {
/* 161 */       this.despawnCount++;
/*     */     }
/*     */     
/* 164 */     if (this.weaponInfo == null) {
/*     */       
/* 166 */       setWeaponName(getWeaponName());
/*     */       
/* 168 */       if (this.weaponInfo == null) {
/*     */         
/* 170 */         func_70106_y();
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/* 175 */     if (this.field_70170_p.field_72995_K) {
/*     */       
/* 177 */       onUpdate_Client();
/*     */     }
/*     */     else {
/*     */       
/* 181 */       onUpdate_Server();
/*     */     } 
/*     */     
/* 184 */     if (!this.field_70128_L)
/*     */     {
/* 186 */       if (this.despawnCount <= 20) {
/*     */         
/* 188 */         this.field_70181_x = -0.3D;
/*     */       }
/*     */       else {
/*     */         
/* 192 */         func_70107_b(this.field_70165_t + this.field_70159_w, this.field_70163_u + this.field_70181_x, this.field_70161_v + this.field_70179_y);
/* 193 */         this.field_70181_x += 0.02D;
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isRender() {
/* 200 */     return (this.despawnCount > 20);
/*     */   }
/*     */ 
/*     */   
/*     */   private void onUpdate_Client() {
/* 205 */     this.shotCount += 4;
/*     */   }
/*     */ 
/*     */   
/*     */   private void onUpdate_Server() {
/* 210 */     if (!this.field_70128_L)
/*     */     {
/* 212 */       if (this.despawnCount > 70) {
/*     */         
/* 214 */         func_70106_y();
/*     */       }
/* 216 */       else if (this.despawnCount > 0 && this.shotCount < 40) {
/*     */         
/* 218 */         for (int i = 0; i < 2; i++) {
/*     */           
/* 220 */           shotGAU8(true, this.shotCount);
/* 221 */           this.shotCount++;
/*     */         } 
/* 223 */         if (this.shotCount == 38)
/*     */         {
/* 225 */           W_WorldFunc.MOD_playSoundEffect(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v, "gau-8_snd", 150.0F, 1.0F);
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void shotGAU8(boolean playSound, int cnt) {
/* 234 */     float yaw = (90 * this.direction);
/* 235 */     float pitch = 30.0F;
/* 236 */     double x = this.field_70165_t;
/* 237 */     double y = this.field_70163_u;
/* 238 */     double z = this.field_70161_v;
/* 239 */     double tX = this.field_70146_Z.nextDouble() - 0.5D;
/* 240 */     double tY = -2.6D;
/* 241 */     double tZ = this.field_70146_Z.nextDouble() - 0.5D;
/*     */     
/* 243 */     if (this.direction == 0) {
/*     */       
/* 245 */       tZ += 10.0D;
/* 246 */       z += cnt * 0.6D;
/*     */     } 
/*     */     
/* 249 */     if (this.direction == 1) {
/*     */       
/* 251 */       tX -= 10.0D;
/* 252 */       x -= cnt * 0.6D;
/*     */     } 
/*     */     
/* 255 */     if (this.direction == 2) {
/*     */       
/* 257 */       tZ -= 10.0D;
/* 258 */       z -= cnt * 0.6D;
/*     */     } 
/*     */     
/* 261 */     if (this.direction == 3) {
/*     */       
/* 263 */       tX += 10.0D;
/* 264 */       x += cnt * 0.6D;
/*     */     } 
/*     */     
/* 267 */     double dist = MathHelper.func_76133_a(tX * tX + tY * tY + tZ * tZ);
/* 268 */     tX = tX * 4.0D / dist;
/* 269 */     tY = tY * 4.0D / dist;
/* 270 */     tZ = tZ * 4.0D / dist;
/*     */     
/* 272 */     MCH_EntityBullet e = new MCH_EntityBullet(this.field_70170_p, x, y, z, tX, tY, tZ, yaw, pitch, this.acceleration);
/*     */     
/* 274 */     e.setName(getWeaponName());
/* 275 */     e.explosionPower = (this.shotCount % 4 == 0) ? this.explosionPower : 0;
/* 276 */     e.setPower(this.power);
/* 277 */     e.shootingEntity = this.shootingEntity;
/* 278 */     e.shootingAircraft = this.shootingAircraft;
/*     */     
/* 280 */     this.field_70170_p.func_72838_d((Entity)e);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_70014_b(NBTTagCompound par1NBTTagCompound) {
/* 286 */     par1NBTTagCompound.func_74778_a("WeaponName", getWeaponName());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_70037_a(NBTTagCompound par1NBTTagCompound) {
/* 292 */     this.despawnCount = 200;
/* 293 */     if (par1NBTTagCompound.func_74764_b("WeaponName"))
/*     */     {
/* 295 */       setWeaponName(par1NBTTagCompound.func_74779_i("WeaponName"));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public float getShadowSize() {
/* 302 */     return 10.0F;
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\weapon\MCH_EntityA10.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */