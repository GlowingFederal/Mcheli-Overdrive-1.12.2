/*     */ package mcheli.weapon;
/*     */ 
/*     */ import mcheli.MCH_Config;
/*     */ import mcheli.particles.MCH_ParticleParam;
/*     */ import mcheli.particles.MCH_ParticlesUtil;
/*     */ import mcheli.wrapper.W_Blocks;
/*     */ import mcheli.wrapper.W_MovingObjectPosition;
/*     */ import mcheli.wrapper.W_WorldFunc;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.network.datasync.DataParameter;
/*     */ import net.minecraft.network.datasync.DataSerializers;
/*     */ import net.minecraft.network.datasync.EntityDataManager;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MCH_EntityMarkerRocket
/*     */   extends MCH_EntityBaseBullet
/*     */ {
/*  27 */   private static final DataParameter<Byte> MARKER_STATUS = EntityDataManager.func_187226_a(MCH_EntityMarkerRocket.class, DataSerializers.field_187191_a);
/*     */ 
/*     */   
/*     */   public int countDown;
/*     */ 
/*     */   
/*     */   public MCH_EntityMarkerRocket(World par1World) {
/*  34 */     super(par1World);
/*  35 */     setMarkerStatus(0);
/*  36 */     this.countDown = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public MCH_EntityMarkerRocket(World par1World, double posX, double posY, double posZ, double targetX, double targetY, double targetZ, float yaw, float pitch, double acceleration) {
/*  42 */     super(par1World, posX, posY, posZ, targetX, targetY, targetZ, yaw, pitch, acceleration);
/*  43 */     setMarkerStatus(0);
/*  44 */     this.countDown = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_70088_a() {
/*  50 */     super.func_70088_a();
/*     */     
/*  52 */     this.field_70180_af.func_187214_a(MARKER_STATUS, Byte.valueOf((byte)0));
/*     */   }
/*     */ 
/*     */   
/*     */   public void setMarkerStatus(int n) {
/*  57 */     if (!this.field_70170_p.field_72995_K)
/*     */     {
/*     */       
/*  60 */       this.field_70180_af.func_187227_b(MARKER_STATUS, Byte.valueOf((byte)n));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMarkerStatus() {
/*  67 */     return ((Byte)this.field_70180_af.func_187225_a(MARKER_STATUS)).byteValue();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_70097_a(DamageSource par1DamageSource, float par2) {
/*  73 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_70071_h_() {
/*  79 */     int status = getMarkerStatus();
/*     */     
/*  81 */     if (this.field_70170_p.field_72995_K) {
/*     */       
/*  83 */       if (getInfo() == null)
/*     */       {
/*  85 */         super.func_70071_h_();
/*     */       }
/*     */       
/*  88 */       if (getInfo() != null && !(getInfo()).disableSmoke)
/*     */       {
/*  90 */         if (status != 0)
/*     */         {
/*  92 */           if (status == 1)
/*     */           {
/*  94 */             super.func_70071_h_();
/*     */             
/*  96 */             spawnParticle((getInfo()).trajectoryParticleName, 3, 5.0F * (getInfo()).smokeSize * 0.5F);
/*     */           }
/*     */           else
/*     */           {
/* 100 */             float gb = this.field_70146_Z.nextFloat() * 0.3F;
/*     */             
/* 102 */             spawnParticle("explode", 5, (10 + this.field_70146_Z.nextInt(4)), this.field_70146_Z.nextFloat() * 0.2F + 0.8F, gb, gb, (this.field_70146_Z
/* 103 */                 .nextFloat() - 0.5F) * 0.7F, 0.3F + this.field_70146_Z.nextFloat() * 0.3F, (this.field_70146_Z
/* 104 */                 .nextFloat() - 0.5F) * 0.7F);
/*     */           }
/*     */         
/*     */         }
/*     */       }
/* 109 */     } else if (status == 0 || func_70090_H()) {
/*     */       
/* 111 */       func_70106_y();
/*     */     }
/* 113 */     else if (status == 1) {
/*     */       
/* 115 */       super.func_70071_h_();
/*     */     }
/* 117 */     else if (this.countDown > 0) {
/*     */       
/* 119 */       this.countDown--;
/*     */       
/* 121 */       if (this.countDown == 40) {
/*     */         
/* 123 */         int num = 6 + this.field_70146_Z.nextInt(2);
/*     */         
/* 125 */         for (int i = 0; i < num; i++)
/*     */         {
/*     */ 
/*     */ 
/*     */           
/* 130 */           MCH_EntityBomb e = new MCH_EntityBomb(this.field_70170_p, this.field_70165_t + ((this.field_70146_Z.nextFloat() - 0.5F) * 15.0F), (260.0F + this.field_70146_Z.nextFloat() * 10.0F + (i * 30)), this.field_70161_v + ((this.field_70146_Z.nextFloat() - 0.5F) * 15.0F), 0.0D, -0.5D, 0.0D, 0.0F, 90.0F, 4.0D);
/*     */           
/* 132 */           e.setName(func_70005_c_());
/* 133 */           e.explosionPower = 3 + this.field_70146_Z.nextInt(2);
/* 134 */           e.explosionPowerInWater = 0;
/* 135 */           e.setPower(30);
/* 136 */           e.piercing = 0;
/* 137 */           e.shootingAircraft = this.shootingAircraft;
/* 138 */           e.shootingEntity = this.shootingEntity;
/*     */           
/* 140 */           this.field_70170_p.func_72838_d((Entity)e);
/*     */         }
/*     */       
/*     */       } 
/*     */     } else {
/*     */       
/* 146 */       func_70106_y();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void spawnParticle(String name, int num, float size, float r, float g, float b, float mx, float my, float mz) {
/* 152 */     if (this.field_70170_p.field_72995_K) {
/*     */       
/* 154 */       if (name.isEmpty() || num < 1 || num > 50) {
/*     */         return;
/*     */       }
/* 157 */       double x = (this.field_70165_t - this.field_70169_q) / num;
/* 158 */       double y = (this.field_70163_u - this.field_70167_r) / num;
/* 159 */       double z = (this.field_70161_v - this.field_70166_s) / num;
/*     */       
/* 161 */       for (int i = 0; i < num; i++) {
/*     */         
/* 163 */         MCH_ParticleParam prm = new MCH_ParticleParam(this.field_70170_p, "smoke", this.field_70169_q + x * i, this.field_70167_r + y * i, this.field_70166_s + z * i);
/*     */ 
/*     */         
/* 166 */         prm.motionX = mx;
/* 167 */         prm.motionY = my;
/* 168 */         prm.motionZ = mz;
/* 169 */         prm.size = size + this.field_70146_Z.nextFloat();
/* 170 */         prm.setColor(1.0F, r, g, b);
/* 171 */         prm.isEffectWind = true;
/*     */         
/* 173 */         MCH_ParticlesUtil.spawnParticle(prm);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onImpact(RayTraceResult m, float damageFactor) {
/* 182 */     if (this.field_70170_p.field_72995_K) {
/*     */       return;
/*     */     }
/* 185 */     if (m.field_72308_g != null || W_MovingObjectPosition.isHitTypeEntity(m)) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 193 */     BlockPos blockpos = m.func_178782_a();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 215 */     blockpos = blockpos.func_177972_a(m.field_178784_b);
/*     */ 
/*     */     
/* 218 */     if (this.field_70170_p.func_175623_d(blockpos)) {
/*     */       
/* 220 */       if (MCH_Config.Explosion_FlamingBlock.prmBool)
/*     */       {
/*     */         
/* 223 */         W_WorldFunc.setBlock(this.field_70170_p, blockpos, (Block)W_Blocks.field_150480_ab);
/*     */       }
/*     */       
/* 226 */       int noAirBlockCount = 0;
/*     */ 
/*     */       
/* 229 */       for (int i = 1; i < 256; i++) {
/*     */ 
/*     */         
/* 232 */         if (!this.field_70170_p.func_175623_d(blockpos.func_177981_b(i))) {
/*     */           
/* 234 */           noAirBlockCount++;
/*     */           
/* 236 */           if (noAirBlockCount >= 5) {
/*     */             break;
/*     */           }
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 243 */       if (noAirBlockCount < 5)
/*     */       {
/* 245 */         setMarkerStatus(2);
/*     */         
/* 247 */         func_70107_b(blockpos.func_177958_n() + 0.5D, blockpos.func_177956_o() + 1.1D, blockpos.func_177952_p() + 0.5D);
/* 248 */         this.field_70169_q = this.field_70165_t;
/* 249 */         this.field_70167_r = this.field_70163_u;
/* 250 */         this.field_70166_s = this.field_70161_v;
/* 251 */         this.countDown = 100;
/*     */       }
/*     */       else
/*     */       {
/* 255 */         func_70106_y();
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 260 */       func_70106_y();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public MCH_BulletModel getDefaultBulletModel() {
/* 267 */     return MCH_DefaultBulletModels.Rocket;
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\weapon\MCH_EntityMarkerRocket.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */