/*     */ package mcheli.throwable;
/*     */ 
/*     */ import javax.annotation.Nullable;
/*     */ import mcheli.MCH_Explosion;
/*     */ import mcheli.MCH_Lib;
/*     */ import mcheli.particles.MCH_ParticleParam;
/*     */ import mcheli.particles.MCH_ParticlesUtil;
/*     */ import mcheli.wrapper.W_WorldFunc;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.projectile.EntityThrowable;
/*     */ import net.minecraft.network.datasync.DataParameter;
/*     */ import net.minecraft.network.datasync.DataSerializers;
/*     */ import net.minecraft.network.datasync.EntityDataManager;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.common.registry.IThrowableEntity;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MCH_EntityThrowable
/*     */   extends EntityThrowable
/*     */   implements IThrowableEntity
/*     */ {
/*  35 */   private static final DataParameter<String> INFO_NAME = EntityDataManager.func_187226_a(MCH_EntityThrowable.class, DataSerializers.field_187194_d);
/*     */   
/*     */   private int countOnUpdate;
/*     */   
/*     */   private MCH_ThrowableInfo throwableInfo;
/*     */   
/*     */   public double boundPosX;
/*     */   
/*     */   public double boundPosY;
/*     */   public double boundPosZ;
/*     */   public RayTraceResult lastOnImpact;
/*     */   public int noInfoCount;
/*     */   
/*     */   public MCH_EntityThrowable(World par1World) {
/*  49 */     super(par1World);
/*  50 */     init();
/*     */   }
/*     */ 
/*     */   
/*     */   public MCH_EntityThrowable(World par1World, EntityLivingBase par2EntityLivingBase, float acceleration) {
/*  55 */     super(par1World, par2EntityLivingBase);
/*  56 */     this.field_70159_w *= acceleration;
/*  57 */     this.field_70181_x *= acceleration;
/*  58 */     this.field_70179_y *= acceleration;
/*  59 */     init();
/*     */   }
/*     */ 
/*     */   
/*     */   public MCH_EntityThrowable(World par1World, double par2, double par4, double par6) {
/*  64 */     super(par1World, par2, par4, par6);
/*  65 */     init();
/*     */   }
/*     */ 
/*     */   
/*     */   public MCH_EntityThrowable(World worldIn, double x, double y, double z, float yaw, float pitch) {
/*  70 */     this(worldIn);
/*  71 */     func_70105_a(0.25F, 0.25F);
/*  72 */     func_70012_b(x, y, z, yaw, pitch);
/*     */     
/*  74 */     this.field_70165_t -= (MathHelper.func_76134_b(this.field_70177_z / 180.0F * 3.1415927F) * 0.16F);
/*  75 */     this.field_70163_u -= 0.10000000149011612D;
/*  76 */     this.field_70161_v -= (MathHelper.func_76126_a(this.field_70177_z / 180.0F * 3.1415927F) * 0.16F);
/*     */     
/*  78 */     func_70107_b(this.field_70165_t, this.field_70163_u, this.field_70161_v);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  86 */     func_184538_a((Entity)null, pitch, yaw, 0.0F, 1.5F, 1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public void init() {
/*  91 */     this.lastOnImpact = null;
/*  92 */     this.countOnUpdate = 0;
/*  93 */     setInfo((MCH_ThrowableInfo)null);
/*  94 */     this.noInfoCount = 0;
/*     */ 
/*     */     
/*  97 */     this.field_70180_af.func_187214_a(INFO_NAME, "");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_184538_a(Entity entityThrower, float rotationPitchIn, float rotationYawIn, float pitchOffset, float velocity, float inaccuracy) {
/* 104 */     float f = 0.4F;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 109 */     this
/* 110 */       .field_70159_w = (-MathHelper.func_76126_a(rotationYawIn / 180.0F * 3.1415927F) * MathHelper.func_76134_b(rotationPitchIn / 180.0F * 3.1415927F) * f);
/* 111 */     this
/* 112 */       .field_70179_y = (MathHelper.func_76134_b(rotationYawIn / 180.0F * 3.1415927F) * MathHelper.func_76134_b(rotationPitchIn / 180.0F * 3.1415927F) * f);
/* 113 */     this.field_70181_x = (-MathHelper.func_76126_a((rotationPitchIn + pitchOffset) / 180.0F * 3.1415927F) * f);
/* 114 */     func_70186_c(this.field_70159_w, this.field_70181_x, this.field_70179_y, velocity, 1.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_70106_y() {
/* 120 */     String s = (getInfo() != null) ? (getInfo()).name : "null";
/* 121 */     MCH_Lib.DbgLog(this.field_70170_p, "MCH_EntityThrowable.setDead(%s)", new Object[] { s });
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 126 */     super.func_70106_y();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_70071_h_() {
/* 132 */     this.boundPosX = this.field_70165_t;
/* 133 */     this.boundPosY = this.field_70163_u;
/* 134 */     this.boundPosZ = this.field_70161_v;
/*     */     
/* 136 */     if (getInfo() != null) {
/*     */       
/* 138 */       Block block = W_WorldFunc.getBlock(this.field_70170_p, (int)(this.field_70165_t + 0.5D), (int)this.field_70163_u, (int)(this.field_70161_v + 0.5D));
/*     */       
/* 140 */       Material mat = W_WorldFunc.getBlockMaterial(this.field_70170_p, (int)(this.field_70165_t + 0.5D), (int)this.field_70163_u, (int)(this.field_70161_v + 0.5D));
/*     */       
/* 142 */       if (block != null && mat == Material.field_151586_h) {
/*     */         
/* 144 */         this.field_70181_x += (getInfo()).gravityInWater;
/*     */       }
/*     */       else {
/*     */         
/* 148 */         this.field_70181_x += (getInfo()).gravity;
/*     */       } 
/*     */     } 
/*     */     
/* 152 */     super.func_70071_h_();
/*     */     
/* 154 */     if (this.lastOnImpact != null) {
/*     */       
/* 156 */       boundBullet(this.lastOnImpact);
/* 157 */       func_70107_b(this.boundPosX + this.field_70159_w, this.boundPosY + this.field_70181_x, this.boundPosZ + this.field_70179_y);
/*     */       
/* 159 */       this.lastOnImpact = null;
/*     */     } 
/*     */     
/* 162 */     this.countOnUpdate++;
/*     */     
/* 164 */     if (this.countOnUpdate >= 2147483632) {
/*     */       
/* 166 */       func_70106_y();
/*     */       
/*     */       return;
/*     */     } 
/* 170 */     if (getInfo() == null) {
/*     */ 
/*     */       
/* 173 */       String s = (String)this.field_70180_af.func_187225_a(INFO_NAME);
/*     */       
/* 175 */       if (!s.isEmpty())
/*     */       {
/* 177 */         setInfo(MCH_ThrowableInfoManager.get(s));
/*     */       }
/*     */       
/* 180 */       if (getInfo() == null) {
/*     */         
/* 182 */         this.noInfoCount++;
/*     */         
/* 184 */         if (this.noInfoCount > 10)
/*     */         {
/* 186 */           func_70106_y();
/*     */         }
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/*     */     
/* 193 */     if (this.field_70128_L) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 198 */     if (!this.field_70170_p.field_72995_K) {
/*     */       
/* 200 */       if (this.countOnUpdate == (getInfo()).timeFuse)
/*     */       {
/* 202 */         if ((getInfo()).explosion > 0) {
/*     */           
/* 204 */           MCH_Explosion.newExplosion(this.field_70170_p, null, null, this.field_70165_t, this.field_70163_u, this.field_70161_v, 
/* 205 */               (getInfo()).explosion, (getInfo()).explosion, true, true, false, true, 0);
/*     */           
/* 207 */           func_70106_y();
/*     */           
/*     */           return;
/*     */         } 
/*     */       }
/* 212 */       if (this.countOnUpdate >= (getInfo()).aliveTime)
/*     */       {
/* 214 */         func_70106_y();
/*     */       
/*     */       }
/*     */     }
/* 218 */     else if (this.countOnUpdate >= (getInfo()).timeFuse) {
/*     */       
/* 220 */       if ((getInfo()).explosion <= 0)
/*     */       {
/* 222 */         for (int i = 0; i < (getInfo()).smokeNum; i++) {
/*     */ 
/*     */           
/* 225 */           float r = (getInfo()).smokeColor.r * 0.9F + this.field_70146_Z.nextFloat() * 0.1F;
/* 226 */           float g = (getInfo()).smokeColor.g * 0.9F + this.field_70146_Z.nextFloat() * 0.1F;
/* 227 */           float b = (getInfo()).smokeColor.b * 0.9F + this.field_70146_Z.nextFloat() * 0.1F;
/*     */           
/* 229 */           if ((getInfo()).smokeColor.r == (getInfo()).smokeColor.g)
/*     */           {
/* 231 */             g = r;
/*     */           }
/*     */           
/* 234 */           if ((getInfo()).smokeColor.r == (getInfo()).smokeColor.b)
/*     */           {
/* 236 */             b = r;
/*     */           }
/*     */           
/* 239 */           if ((getInfo()).smokeColor.g == (getInfo()).smokeColor.b)
/*     */           {
/* 241 */             b = g;
/*     */           }
/*     */           
/* 244 */           spawnParticle("explode", 4, 
/* 245 */               (getInfo()).smokeSize + this.field_70146_Z.nextFloat() * (getInfo()).smokeSize / 3.0F, r, g, b, 
/* 246 */               (getInfo()).smokeVelocityHorizontal * (this.field_70146_Z.nextFloat() - 0.5F), 
/* 247 */               (getInfo()).smokeVelocityVertical * this.field_70146_Z.nextFloat(), 
/* 248 */               (getInfo()).smokeVelocityHorizontal * (this.field_70146_Z.nextFloat() - 0.5F));
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void spawnParticle(String name, int num, float size, float r, float g, float b, float mx, float my, float mz) {
/* 256 */     if (this.field_70170_p.field_72995_K) {
/*     */       
/* 258 */       if (name.isEmpty() || num < 1)
/*     */         return; 
/* 260 */       double x = (this.field_70165_t - this.field_70169_q) / num;
/* 261 */       double y = (this.field_70163_u - this.field_70167_r) / num;
/* 262 */       double z = (this.field_70161_v - this.field_70166_s) / num;
/* 263 */       for (int i = 0; i < num; i++) {
/*     */         
/* 265 */         MCH_ParticleParam prm = new MCH_ParticleParam(this.field_70170_p, "smoke", this.field_70169_q + x * i, 1.0D + this.field_70167_r + y * i, this.field_70166_s + z * i);
/*     */ 
/*     */         
/* 268 */         prm.setMotion(mx, my, mz);
/* 269 */         prm.size = size;
/* 270 */         prm.setColor(1.0F, r, g, b);
/* 271 */         prm.isEffectWind = true;
/* 272 */         prm.toWhite = true;
/*     */         
/* 274 */         MCH_ParticlesUtil.spawnParticle(prm);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected float func_70185_h() {
/* 282 */     return 0.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void boundBullet(RayTraceResult m) {
/* 288 */     if (m.field_178784_b == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 293 */     float bound = (getInfo()).bound;
/*     */     
/* 295 */     switch (m.field_178784_b) {
/*     */ 
/*     */ 
/*     */       
/*     */       case DOWN:
/*     */       case UP:
/* 301 */         this.field_70159_w *= 0.8999999761581421D;
/* 302 */         this.field_70179_y *= 0.8999999761581421D;
/* 303 */         this.boundPosY = m.field_72307_f.field_72448_b;
/*     */         
/* 305 */         if ((m.field_178784_b == EnumFacing.DOWN && this.field_70181_x > 0.0D) || (m.field_178784_b == EnumFacing.UP && this.field_70181_x < 0.0D)) {
/*     */ 
/*     */           
/* 308 */           this.field_70181_x = -this.field_70181_x * bound;
/*     */           
/*     */           break;
/*     */         } 
/* 312 */         this.field_70181_x = 0.0D;
/*     */         break;
/*     */ 
/*     */       
/*     */       case NORTH:
/* 317 */         if (this.field_70179_y > 0.0D)
/*     */         {
/* 319 */           this.field_70179_y = -this.field_70179_y * bound;
/*     */         }
/*     */         break;
/*     */       
/*     */       case SOUTH:
/* 324 */         if (this.field_70179_y < 0.0D)
/*     */         {
/* 326 */           this.field_70179_y = -this.field_70179_y * bound;
/*     */         }
/*     */         break;
/*     */       
/*     */       case WEST:
/* 331 */         if (this.field_70159_w > 0.0D)
/*     */         {
/* 333 */           this.field_70159_w = -this.field_70159_w * bound;
/*     */         }
/*     */         break;
/*     */       
/*     */       case EAST:
/* 338 */         if (this.field_70159_w < 0.0D)
/*     */         {
/* 340 */           this.field_70159_w = -this.field_70159_w * bound;
/*     */         }
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_70184_a(RayTraceResult m) {
/* 351 */     if (getInfo() != null)
/*     */     {
/* 353 */       this.lastOnImpact = m;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public MCH_ThrowableInfo getInfo() {
/* 360 */     return this.throwableInfo;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setInfo(MCH_ThrowableInfo info) {
/* 365 */     this.throwableInfo = info;
/* 366 */     if (info != null)
/*     */     {
/* 368 */       if (!this.field_70170_p.field_72995_K)
/*     */       {
/*     */         
/* 371 */         this.field_70180_af.func_187227_b(INFO_NAME, info.name);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setThrower(Entity entity) {
/* 379 */     if (entity instanceof EntityLivingBase)
/*     */     {
/* 381 */       this.field_70192_c = (EntityLivingBase)entity;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\throwable\MCH_EntityThrowable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */