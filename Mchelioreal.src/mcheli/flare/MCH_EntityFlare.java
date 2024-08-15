/*     */ package mcheli.flare;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import mcheli.particles.MCH_ParticleParam;
/*     */ import mcheli.particles.MCH_ParticlesUtil;
/*     */ import mcheli.wrapper.W_Entity;
/*     */ import mcheli.wrapper.W_WorldFunc;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MCH_EntityFlare
/*     */   extends W_Entity
/*     */   implements IEntityAdditionalSpawnData
/*     */ {
/*  27 */   public double gravity = -0.013D;
/*  28 */   public double airResistance = 0.992D;
/*     */   
/*     */   public float size;
/*     */   public int fuseCount;
/*     */   
/*     */   public MCH_EntityFlare(World par1World) {
/*  34 */     super(par1World);
/*  35 */     func_70105_a(1.0F, 1.0F);
/*  36 */     this.field_70126_B = this.field_70177_z;
/*  37 */     this.field_70127_C = this.field_70125_A;
/*  38 */     this.size = 6.0F;
/*  39 */     this.fuseCount = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public MCH_EntityFlare(World par1World, double pX, double pY, double pZ, double mX, double mY, double mZ, float size, int fuseCount) {
/*  45 */     this(par1World);
/*  46 */     func_70012_b(pX, pY, pZ, 0.0F, 0.0F);
/*     */     
/*  48 */     this.field_70159_w = mX;
/*  49 */     this.field_70181_x = mY;
/*  50 */     this.field_70179_y = mZ;
/*  51 */     this.size = size;
/*  52 */     this.fuseCount = fuseCount;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_180431_b(DamageSource source) {
/*  59 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public boolean func_70112_a(double par1) {
/*  67 */     double d1 = func_174813_aQ().func_72320_b() * 4.0D;
/*  68 */     d1 *= 64.0D;
/*  69 */     return (par1 < d1 * d1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_70106_y() {
/*  75 */     super.func_70106_y();
/*     */     
/*  77 */     if (this.fuseCount > 0 && this.field_70170_p.field_72995_K) {
/*     */       
/*  79 */       this.fuseCount = 0;
/*     */ 
/*     */       
/*  82 */       for (int i = 0; i < 20; i++) {
/*     */         
/*  84 */         double x = (this.field_70146_Z.nextDouble() - 0.5D) * 10.0D;
/*  85 */         double y = (this.field_70146_Z.nextDouble() - 0.5D) * 10.0D;
/*  86 */         double z = (this.field_70146_Z.nextDouble() - 0.5D) * 10.0D;
/*  87 */         MCH_ParticleParam prm = new MCH_ParticleParam(this.field_70170_p, "smoke", this.field_70165_t + x, this.field_70163_u + y, this.field_70161_v + z);
/*     */         
/*  89 */         prm.age = 200 + this.field_70146_Z.nextInt(100);
/*  90 */         prm.size = (20 + this.field_70146_Z.nextInt(25));
/*  91 */         prm.motionX = (this.field_70146_Z.nextDouble() - 0.5D) * 0.45D;
/*  92 */         prm.motionY = (this.field_70146_Z.nextDouble() - 0.5D) * 0.01D;
/*  93 */         prm.motionZ = (this.field_70146_Z.nextDouble() - 0.5D) * 0.45D;
/*  94 */         prm.a = this.field_70146_Z.nextFloat() * 0.1F + 0.85F;
/*  95 */         prm.b = this.field_70146_Z.nextFloat() * 0.2F + 0.5F;
/*  96 */         prm.g = prm.b + 0.05F;
/*  97 */         prm.r = prm.b + 0.1F;
/*  98 */         MCH_ParticlesUtil.spawnParticle(prm);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeSpawnData(ByteBuf buffer) {
/*     */     try {
/* 108 */       buffer.writeByte(this.fuseCount);
/*     */     }
/* 110 */     catch (Exception e) {
/*     */       
/* 112 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readSpawnData(ByteBuf additionalData) {
/*     */     try {
/* 121 */       this.fuseCount = additionalData.readByte();
/*     */     }
/* 123 */     catch (Exception e) {
/*     */       
/* 125 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_70071_h_() {
/* 132 */     if (this.fuseCount > 0 && this.field_70173_aa >= this.fuseCount) {
/*     */       
/* 134 */       func_70106_y();
/*     */     
/*     */     }
/* 137 */     else if (!this.field_70170_p.field_72995_K && !this.field_70170_p.func_175667_e(new BlockPos(this.field_70165_t, this.field_70163_u, this.field_70161_v))) {
/*     */       
/* 139 */       func_70106_y();
/*     */     }
/* 141 */     else if (this.field_70173_aa > 300 && !this.field_70170_p.field_72995_K) {
/*     */       
/* 143 */       func_70106_y();
/*     */     }
/*     */     else {
/*     */       
/* 147 */       super.func_70071_h_();
/*     */       
/* 149 */       if (!this.field_70170_p.field_72995_K)
/*     */       {
/* 151 */         onUpdateCollided();
/*     */       }
/*     */       
/* 154 */       this.field_70165_t += this.field_70159_w;
/* 155 */       this.field_70163_u += this.field_70181_x;
/* 156 */       this.field_70161_v += this.field_70179_y;
/*     */       
/* 158 */       if (this.field_70170_p.field_72995_K) {
/*     */ 
/*     */         
/* 161 */         double x = (this.field_70165_t - this.field_70169_q) / 2.0D;
/* 162 */         double y = (this.field_70163_u - this.field_70167_r) / 2.0D;
/* 163 */         double z = (this.field_70161_v - this.field_70166_s) / 2.0D;
/* 164 */         for (int i = 0; i < 2; i++) {
/*     */           
/* 166 */           MCH_ParticleParam prm = new MCH_ParticleParam(this.field_70170_p, "smoke", this.field_70169_q + x * i, this.field_70167_r + y * i, this.field_70166_s + z * i);
/*     */ 
/*     */           
/* 169 */           prm.size = 6.0F + this.field_70146_Z.nextFloat();
/* 170 */           if (this.size < 5.0F) {
/*     */             
/* 172 */             MCH_ParticleParam tmp290_288 = prm;
/* 173 */             tmp290_288.a = (float)(tmp290_288.a * 0.75D);
/* 174 */             if (this.field_70146_Z.nextInt(2) == 0);
/*     */           } 
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
/* 189 */           if (this.fuseCount > 0) {
/*     */             
/* 191 */             prm.a = this.field_70146_Z.nextFloat() * 0.1F + 0.85F;
/* 192 */             prm.b = this.field_70146_Z.nextFloat() * 0.1F + 0.5F;
/* 193 */             prm.g = prm.b + 0.05F;
/* 194 */             prm.r = prm.b + 0.1F;
/*     */           } 
/* 196 */           MCH_ParticlesUtil.spawnParticle(prm);
/*     */         } 
/*     */       } 
/*     */       
/* 200 */       this.field_70181_x += this.gravity;
/* 201 */       this.field_70159_w *= this.airResistance;
/* 202 */       this.field_70179_y *= this.airResistance;
/*     */       
/* 204 */       if (func_70090_H() && !this.field_70170_p.field_72995_K)
/*     */       {
/* 206 */         func_70106_y();
/*     */       }
/* 208 */       if (this.field_70122_E && !this.field_70170_p.field_72995_K)
/*     */       {
/* 210 */         func_70106_y();
/*     */       }
/*     */       
/* 213 */       func_70107_b(this.field_70165_t, this.field_70163_u, this.field_70161_v);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onUpdateCollided() {
/* 219 */     Vec3d vec3 = W_WorldFunc.getWorldVec3(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v);
/* 220 */     Vec3d vec31 = W_WorldFunc.getWorldVec3(this.field_70170_p, this.field_70165_t + this.field_70159_w, this.field_70163_u + this.field_70181_x, this.field_70161_v + this.field_70179_y);
/*     */ 
/*     */     
/* 223 */     RayTraceResult mop = W_WorldFunc.clip(this.field_70170_p, vec3, vec31);
/* 224 */     vec3 = W_WorldFunc.getWorldVec3(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v);
/* 225 */     vec31 = W_WorldFunc.getWorldVec3(this.field_70170_p, this.field_70165_t + this.field_70159_w, this.field_70163_u + this.field_70181_x, this.field_70161_v + this.field_70179_y);
/*     */ 
/*     */     
/* 228 */     if (mop != null) {
/*     */       
/* 230 */       vec31 = W_WorldFunc.getWorldVec3(this.field_70170_p, mop.field_72307_f.field_72450_a, mop.field_72307_f.field_72448_b, mop.field_72307_f.field_72449_c);
/* 231 */       onImpact(mop);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onImpact(RayTraceResult par1MovingObjectPosition) {
/* 238 */     if (!this.field_70170_p.field_72995_K)
/*     */     {
/* 240 */       func_70106_y();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_70014_b(NBTTagCompound par1NBTTagCompound) {
/* 247 */     par1NBTTagCompound.func_74782_a("direction", (NBTBase)func_70087_a(new double[] { this.field_70159_w, this.field_70181_x, this.field_70179_y }));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_70037_a(NBTTagCompound par1NBTTagCompound) {
/* 256 */     func_70106_y();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_70067_L() {
/* 262 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float func_70111_Y() {
/* 268 */     return 1.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_70097_a(DamageSource par1DamageSource, float par2) {
/* 274 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public float getShadowSize() {
/* 280 */     return 0.0F;
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\flare\MCH_EntityFlare.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */