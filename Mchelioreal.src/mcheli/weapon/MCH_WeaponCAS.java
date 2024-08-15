/*     */ package mcheli.weapon;
/*     */ 
/*     */ import mcheli.MCH_Lib;
/*     */ import mcheli.wrapper.W_MovingObjectPosition;
/*     */ import mcheli.wrapper.W_WorldFunc;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MCH_WeaponCAS
/*     */   extends MCH_WeaponBase
/*     */ {
/*     */   private double targetPosX;
/*     */   private double targetPosY;
/*     */   private double targetPosZ;
/*     */   public int direction;
/*     */   private int startTick;
/*     */   private int cntAtk;
/*     */   private Entity shooter;
/*     */   public Entity user;
/*     */   
/*     */   public MCH_WeaponCAS(World w, Vec3d v, float yaw, float pitch, String nm, MCH_WeaponInfo wi) {
/*  32 */     super(w, v, yaw, pitch, nm, wi);
/*  33 */     this.acceleration = 4.0F;
/*  34 */     this.explosionPower = 2;
/*  35 */     this.power = 32;
/*  36 */     this.interval = 65236;
/*  37 */     if (w.field_72995_K)
/*     */     {
/*  39 */       this.interval -= 10;
/*     */     }
/*  41 */     this.targetPosX = 0.0D;
/*  42 */     this.targetPosY = 0.0D;
/*  43 */     this.targetPosZ = 0.0D;
/*  44 */     this.direction = 0;
/*  45 */     this.startTick = 0;
/*  46 */     this.cntAtk = 3;
/*  47 */     this.shooter = null;
/*  48 */     this.user = null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void update(int countWait) {
/*  54 */     super.update(countWait);
/*  55 */     if (!this.worldObj.field_72995_K && this.cntAtk < 3 && countWait != 0)
/*     */     {
/*  57 */       if (this.tick == this.startTick) {
/*     */         
/*  59 */         double x = 0.0D;
/*  60 */         double z = 0.0D;
/*  61 */         if (this.cntAtk >= 1) {
/*     */           
/*  63 */           double sign = (this.cntAtk == 1) ? 1.0D : -1.0D;
/*  64 */           if (this.direction == 0 || this.direction == 2) {
/*     */             
/*  66 */             x = rand.nextDouble() * 10.0D * sign;
/*  67 */             z = (rand.nextDouble() - 0.5D) * 10.0D;
/*     */           } 
/*  69 */           if (this.direction == 1 || this.direction == 3) {
/*     */             
/*  71 */             z = rand.nextDouble() * 10.0D * sign;
/*  72 */             x = (rand.nextDouble() - 0.5D) * 10.0D;
/*     */           } 
/*     */         } 
/*  75 */         spawnA10(this.targetPosX + x, this.targetPosY + 20.0D, this.targetPosZ + z);
/*     */         
/*  77 */         this.startTick = this.tick + 45;
/*  78 */         this.cntAtk++;
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void modifyParameters() {
/*  86 */     if (this.interval > 65286)
/*     */     {
/*  88 */       this.interval = 65286;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void setTargetPosition(double x, double y, double z) {
/*  94 */     this.targetPosX = x;
/*  95 */     this.targetPosY = y;
/*  96 */     this.targetPosZ = z;
/*     */   }
/*     */ 
/*     */   
/*     */   public void spawnA10(double x, double y, double z) {
/* 101 */     double mX = 0.0D;
/* 102 */     double mY = 0.0D;
/* 103 */     double mZ = 0.0D;
/*     */ 
/*     */     
/* 106 */     if (this.direction == 0) {
/*     */       
/* 108 */       mZ += 3.0D;
/* 109 */       z -= 90.0D;
/*     */     } 
/* 111 */     if (this.direction == 1) {
/*     */       
/* 113 */       mX -= 3.0D;
/* 114 */       x += 90.0D;
/*     */     } 
/* 116 */     if (this.direction == 2) {
/*     */       
/* 118 */       mZ -= 3.0D;
/* 119 */       z += 90.0D;
/*     */     } 
/* 121 */     if (this.direction == 3) {
/*     */       
/* 123 */       mX += 3.0D;
/* 124 */       x -= 90.0D;
/*     */     } 
/* 126 */     MCH_EntityA10 a10 = new MCH_EntityA10(this.worldObj, x, y, z);
/* 127 */     a10.setWeaponName(this.name);
/*     */     
/* 129 */     a10.field_70126_B = a10.field_70177_z = (90 * this.direction);
/* 130 */     a10.field_70159_w = mX;
/* 131 */     a10.field_70181_x = mY;
/* 132 */     a10.field_70179_y = mZ;
/* 133 */     a10.direction = this.direction;
/* 134 */     a10.shootingEntity = this.user;
/* 135 */     a10.shootingAircraft = this.shooter;
/* 136 */     a10.explosionPower = this.explosionPower;
/* 137 */     a10.power = this.power;
/* 138 */     a10.acceleration = this.acceleration;
/*     */     
/* 140 */     this.worldObj.func_72838_d((Entity)a10);
/*     */     
/* 142 */     W_WorldFunc.MOD_playSoundEffect(this.worldObj, x, y, z, "a-10_snd", 150.0F, 1.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean shot(MCH_WeaponParam prm) {
/* 148 */     float yaw = prm.user.field_70177_z;
/* 149 */     float pitch = prm.user.field_70125_A;
/* 150 */     double tX = (-MathHelper.func_76126_a(yaw / 180.0F * 3.1415927F) * MathHelper.func_76134_b(pitch / 180.0F * 3.1415927F));
/* 151 */     double tZ = (MathHelper.func_76134_b(yaw / 180.0F * 3.1415927F) * MathHelper.func_76134_b(pitch / 180.0F * 3.1415927F));
/* 152 */     double tY = -MathHelper.func_76126_a(pitch / 180.0F * 3.1415927F);
/* 153 */     double dist = MathHelper.func_76133_a(tX * tX + tY * tY + tZ * tZ);
/*     */     
/* 155 */     if (this.worldObj.field_72995_K) {
/*     */       
/* 157 */       tX = tX * 80.0D / dist;
/* 158 */       tY = tY * 80.0D / dist;
/* 159 */       tZ = tZ * 80.0D / dist;
/*     */     }
/*     */     else {
/*     */       
/* 163 */       tX = tX * 150.0D / dist;
/* 164 */       tY = tY * 150.0D / dist;
/* 165 */       tZ = tZ * 150.0D / dist;
/*     */     } 
/*     */     
/* 168 */     Vec3d src = W_WorldFunc.getWorldVec3(this.worldObj, prm.entity.field_70165_t, prm.entity.field_70163_u + 2.0D, prm.entity.field_70161_v);
/* 169 */     Vec3d dst = W_WorldFunc.getWorldVec3(this.worldObj, prm.entity.field_70165_t + tX, prm.entity.field_70163_u + tY + 2.0D, prm.entity.field_70161_v + tZ);
/*     */ 
/*     */     
/* 172 */     RayTraceResult m = W_WorldFunc.clip(this.worldObj, src, dst);
/*     */     
/* 174 */     if (m != null && W_MovingObjectPosition.isHitTypeTile(m)) {
/*     */       
/* 176 */       this.targetPosX = m.field_72307_f.field_72450_a;
/* 177 */       this.targetPosY = m.field_72307_f.field_72448_b;
/* 178 */       this.targetPosZ = m.field_72307_f.field_72449_c;
/*     */       
/* 180 */       this.direction = (int)MCH_Lib.getRotate360((yaw + 45.0F)) / 90;
/* 181 */       this.direction += rand.nextBoolean() ? -1 : 1;
/* 182 */       this.direction %= 4;
/*     */       
/* 184 */       if (this.direction < 0)
/*     */       {
/* 186 */         this.direction += 4;
/*     */       }
/* 188 */       this.user = prm.user;
/* 189 */       this.shooter = prm.entity;
/*     */       
/* 191 */       if (prm.entity != null)
/*     */       {
/* 193 */         playSoundClient(prm.entity, 1.0F, 1.0F);
/*     */       }
/* 195 */       this.startTick = 50;
/* 196 */       this.cntAtk = 0;
/*     */       
/* 198 */       return true;
/*     */     } 
/* 200 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shot(Entity user, double px, double py, double pz, int option1, int option2) {
/* 205 */     float yaw = user.field_70177_z;
/* 206 */     float pitch = user.field_70125_A;
/* 207 */     double tX = (-MathHelper.func_76126_a(yaw / 180.0F * 3.1415927F) * MathHelper.func_76134_b(pitch / 180.0F * 3.1415927F));
/* 208 */     double tZ = (MathHelper.func_76134_b(yaw / 180.0F * 3.1415927F) * MathHelper.func_76134_b(pitch / 180.0F * 3.1415927F));
/* 209 */     double tY = -MathHelper.func_76126_a(pitch / 180.0F * 3.1415927F);
/* 210 */     double dist = MathHelper.func_76133_a(tX * tX + tY * tY + tZ * tZ);
/*     */     
/* 212 */     if (this.worldObj.field_72995_K) {
/*     */       
/* 214 */       tX = tX * 80.0D / dist;
/* 215 */       tY = tY * 80.0D / dist;
/* 216 */       tZ = tZ * 80.0D / dist;
/*     */     }
/*     */     else {
/*     */       
/* 220 */       tX = tX * 120.0D / dist;
/* 221 */       tY = tY * 120.0D / dist;
/* 222 */       tZ = tZ * 120.0D / dist;
/*     */     } 
/*     */     
/* 225 */     Vec3d src = W_WorldFunc.getWorldVec3(this.worldObj, px, py, pz);
/* 226 */     Vec3d dst = W_WorldFunc.getWorldVec3(this.worldObj, px + tX, py + tY, pz + tZ);
/*     */     
/* 228 */     RayTraceResult m = W_WorldFunc.clip(this.worldObj, src, dst);
/*     */     
/* 230 */     if (W_MovingObjectPosition.isHitTypeTile(m)) {
/*     */       
/* 232 */       if (this.worldObj.field_72995_K) {
/*     */         
/* 234 */         double dx = m.field_72307_f.field_72450_a - px;
/*     */         
/* 236 */         double dz = m.field_72307_f.field_72449_c - pz;
/*     */         
/* 238 */         if (Math.sqrt(dx * dx + dz * dz) < 20.0D)
/*     */         {
/* 240 */           return false;
/*     */         }
/*     */       } 
/*     */       
/* 244 */       this.targetPosX = m.field_72307_f.field_72450_a;
/* 245 */       this.targetPosY = m.field_72307_f.field_72448_b;
/* 246 */       this.targetPosZ = m.field_72307_f.field_72449_c;
/*     */       
/* 248 */       this.direction = (int)MCH_Lib.getRotate360((yaw + 45.0F)) / 90;
/* 249 */       this.direction += rand.nextBoolean() ? -1 : 1;
/* 250 */       this.direction %= 4;
/*     */       
/* 252 */       if (this.direction < 0)
/*     */       {
/* 254 */         this.direction += 4;
/*     */       }
/*     */       
/* 257 */       this.user = user;
/* 258 */       this.shooter = null;
/*     */       
/* 260 */       this.tick = 0;
/* 261 */       this.startTick = 50;
/* 262 */       this.cntAtk = 0;
/*     */       
/* 264 */       return true;
/*     */     } 
/*     */     
/* 267 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\weapon\MCH_WeaponCAS.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */