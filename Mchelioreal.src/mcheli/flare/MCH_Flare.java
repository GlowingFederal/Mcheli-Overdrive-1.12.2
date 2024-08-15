/*     */ package mcheli.flare;
/*     */ 
/*     */ import java.util.Random;
/*     */ import mcheli.MCH_Lib;
/*     */ import mcheli.aircraft.MCH_EntityAircraft;
/*     */ import mcheli.particles.MCH_ParticleParam;
/*     */ import mcheli.particles.MCH_ParticlesUtil;
/*     */ import mcheli.wrapper.W_McClient;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.util.SoundCategory;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MCH_Flare
/*     */ {
/*     */   public final World worldObj;
/*     */   public final MCH_EntityAircraft aircraft;
/*     */   public final Random rand;
/*     */   public int numFlare;
/*     */   public int tick;
/*     */   private int flareType;
/*  31 */   private static FlareParam[] FLARE_DATA = null;
/*     */ 
/*     */   
/*     */   public MCH_Flare(World w, MCH_EntityAircraft ac) {
/*  35 */     this.worldObj = w;
/*  36 */     this.aircraft = ac;
/*  37 */     this.rand = new Random();
/*     */     
/*  39 */     this.tick = 0;
/*  40 */     this.numFlare = 0;
/*  41 */     this.flareType = 0;
/*     */     
/*  43 */     if (FLARE_DATA == null) {
/*     */       
/*  45 */       int delay = w.field_72995_K ? 50 : 0;
/*  46 */       FLARE_DATA = new FlareParam[11];
/*  47 */       FLARE_DATA[1] = new FlareParam(1, 3, 200 + delay, 100, 16);
/*  48 */       FLARE_DATA[2] = new FlareParam(3, 5, 300 + delay, 200, 16);
/*  49 */       FLARE_DATA[3] = new FlareParam(2, 3, 200 + delay, 100, 16);
/*  50 */       FLARE_DATA[4] = new FlareParam(1, 3, 200 + delay, 100, 16);
/*  51 */       FLARE_DATA[5] = new FlareParam(2, 3, 200 + delay, 100, 16);
/*  52 */       FLARE_DATA[10] = new FlareParam(8, 1, 250 + delay, 60, 1);
/*     */       
/*  54 */       FLARE_DATA[0] = FLARE_DATA[1];
/*  55 */       FLARE_DATA[6] = FLARE_DATA[1];
/*  56 */       FLARE_DATA[7] = FLARE_DATA[1];
/*  57 */       FLARE_DATA[8] = FLARE_DATA[1];
/*  58 */       FLARE_DATA[9] = FLARE_DATA[1];
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isInPreparation() {
/*  64 */     return (this.tick != 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isUsing() {
/*  69 */     int type = getFlareType();
/*  70 */     return (this.tick != 0 && type < FLARE_DATA.length && this.tick > (FLARE_DATA[type]).tickWait - (FLARE_DATA[type]).tickEnable);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getFlareType() {
/*  76 */     return this.flareType;
/*     */   }
/*     */ 
/*     */   
/*     */   public void spawnParticle(String name, int num, float size) {
/*  81 */     if (this.worldObj.field_72995_K) {
/*     */       
/*  83 */       if (name.isEmpty() || num < 1 || num > 50)
/*     */         return; 
/*  85 */       double x = (this.aircraft.field_70165_t - this.aircraft.field_70169_q) / num;
/*  86 */       double y = (this.aircraft.field_70163_u - this.aircraft.field_70167_r) / num;
/*  87 */       double z = (this.aircraft.field_70161_v - this.aircraft.field_70166_s) / num;
/*  88 */       for (int i = 0; i < num; i++) {
/*     */         
/*  90 */         MCH_ParticleParam prm = new MCH_ParticleParam(this.worldObj, "smoke", this.aircraft.field_70169_q + x * i, this.aircraft.field_70167_r + y * i, this.aircraft.field_70166_s + z * i);
/*     */ 
/*     */         
/*  93 */         prm.size = size + this.rand.nextFloat();
/*  94 */         MCH_ParticlesUtil.spawnParticle(prm);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean use(int type) {
/* 101 */     boolean result = false;
/*     */     
/* 103 */     MCH_Lib.DbgLog(this.aircraft.field_70170_p, "MCH_Flare.use type = %d", new Object[] {
/*     */           
/* 105 */           Integer.valueOf(type)
/*     */         });
/*     */     
/* 108 */     this.flareType = type;
/*     */     
/* 110 */     if (type <= 0 && type >= FLARE_DATA.length)
/*     */     {
/* 112 */       return false;
/*     */     }
/*     */     
/* 115 */     if (this.worldObj.field_72995_K) {
/*     */       
/* 117 */       if (this.tick == 0)
/*     */       {
/* 119 */         this.tick = (FLARE_DATA[getFlareType()]).tickWait;
/* 120 */         result = true;
/* 121 */         this.numFlare = 0;
/*     */         
/* 123 */         W_McClient.playSoundClick(1.0F, 1.0F);
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 128 */       result = true;
/* 129 */       this.numFlare = 0;
/* 130 */       this.tick = (FLARE_DATA[getFlareType()]).tickWait;
/*     */       
/* 132 */       this.aircraft.getEntityData().func_74757_a("FlareUsing", true);
/*     */     } 
/*     */     
/* 135 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public void update() {
/* 140 */     int type = getFlareType();
/*     */     
/* 142 */     if (this.aircraft == null || this.aircraft.field_70128_L || type <= 0 || type > FLARE_DATA.length) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 147 */     if (this.tick > 0)
/*     */     {
/* 149 */       this.tick--;
/*     */     }
/*     */     
/* 152 */     if (!this.worldObj.field_72995_K)
/*     */     {
/* 154 */       if (this.tick > 0 && this.tick % (FLARE_DATA[type]).interval == 0 && this.numFlare < (FLARE_DATA[type]).numFlareMax) {
/*     */ 
/*     */         
/* 157 */         Vec3d v = (this.aircraft.getAcInfo()).flare.pos;
/* 158 */         v = this.aircraft.getTransformedPosition(v.field_72450_a, v.field_72448_b, v.field_72449_c, this.aircraft.field_70169_q, this.aircraft.field_70167_r, this.aircraft.field_70166_s);
/*     */ 
/*     */         
/* 161 */         spawnFlare(v);
/*     */       } 
/*     */     }
/*     */     
/* 165 */     if (!isUsing() && this.aircraft.getEntityData().func_74767_n("FlareUsing"))
/*     */     {
/* 167 */       this.aircraft.getEntityData().func_74757_a("FlareUsing", false);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void spawnFlare(Vec3d v) {
/* 173 */     this.numFlare++;
/*     */     
/* 175 */     int type = getFlareType();
/* 176 */     int num = (FLARE_DATA[type]).num;
/*     */     
/* 178 */     double x = v.field_72450_a - this.aircraft.field_70159_w * 2.0D;
/* 179 */     double y = v.field_72448_b - this.aircraft.field_70181_x * 2.0D - 1.0D;
/* 180 */     double z = v.field_72449_c - this.aircraft.field_70179_y * 2.0D;
/*     */     
/* 182 */     this.worldObj.func_184133_a(null, new BlockPos(x, y, z), SoundEvents.field_187646_bt, SoundCategory.BLOCKS, 0.5F, 2.6F + (this.worldObj.field_73012_v
/* 183 */         .nextFloat() - this.worldObj.field_73012_v.nextFloat()) * 0.8F);
/*     */     
/* 185 */     for (int i = 0; i < num; i++) {
/*     */       
/* 187 */       x = v.field_72450_a - this.aircraft.field_70159_w * 2.0D;
/* 188 */       y = v.field_72448_b - this.aircraft.field_70181_x * 2.0D - 1.0D;
/* 189 */       z = v.field_72449_c - this.aircraft.field_70179_y * 2.0D;
/*     */       
/* 191 */       double tx = 0.0D;
/* 192 */       double ty = this.aircraft.field_70181_x;
/* 193 */       double tz = 0.0D;
/* 194 */       int fuseCount = 0;
/* 195 */       double r = this.aircraft.field_70177_z;
/*     */       
/* 197 */       if (type == 1) {
/*     */         
/* 199 */         tx = MathHelper.func_76126_a(this.rand.nextFloat() * 360.0F);
/* 200 */         tz = MathHelper.func_76134_b(this.rand.nextFloat() * 360.0F);
/*     */       }
/* 202 */       else if (type == 2 || type == 3) {
/*     */         
/* 204 */         if (i == 0)
/* 205 */           r += 90.0D; 
/* 206 */         if (i == 1)
/* 207 */           r -= 90.0D; 
/* 208 */         if (i == 2)
/* 209 */           r += 180.0D; 
/* 210 */         r *= 0.017453292519943295D;
/* 211 */         tx = -Math.sin(r) + (this.rand.nextFloat() - 0.5D) * 0.6D;
/* 212 */         tz = Math.cos(r) + (this.rand.nextFloat() - 0.5D) * 0.6D;
/*     */       }
/* 214 */       else if (type == 4) {
/*     */         
/* 216 */         r *= 0.017453292519943295D;
/* 217 */         tx = -Math.sin(r) + (this.rand.nextFloat() - 0.5D) * 1.3D;
/* 218 */         tz = Math.cos(r) + (this.rand.nextFloat() - 0.5D) * 1.3D;
/*     */       }
/* 220 */       else if (type == 5) {
/*     */         
/* 222 */         r *= 0.017453292519943295D;
/* 223 */         tx = -Math.sin(r) + (this.rand.nextFloat() - 0.5D) * 0.9D;
/* 224 */         tz = Math.cos(r) + (this.rand.nextFloat() - 0.5D) * 0.9D;
/* 225 */         tx *= 0.3D;
/* 226 */         tz *= 0.3D;
/*     */       } 
/*     */       
/* 229 */       tx += this.aircraft.field_70159_w;
/* 230 */       ty += this.aircraft.field_70181_x / 2.0D;
/* 231 */       tz += this.aircraft.field_70179_y;
/*     */       
/* 233 */       if (type == 10) {
/*     */         
/* 235 */         r += (360 / num / 2 + i * 360 / num);
/* 236 */         r *= 0.017453292519943295D;
/* 237 */         tx = -Math.sin(r) * 2.0D;
/* 238 */         tz = Math.cos(r) * 2.0D;
/* 239 */         ty = 0.7D;
/* 240 */         y += 2.0D;
/* 241 */         fuseCount = 10;
/*     */       } 
/*     */       
/* 244 */       MCH_EntityFlare e = new MCH_EntityFlare(this.worldObj, x, y, z, tx * 0.5D, ty * 0.5D, tz * 0.5D, 6.0F, fuseCount);
/*     */       
/* 246 */       e.field_70125_A = this.rand.nextFloat() * 360.0F;
/* 247 */       e.field_70177_z = this.rand.nextFloat() * 360.0F;
/* 248 */       e.field_70127_C = this.rand.nextFloat() * 360.0F;
/* 249 */       e.field_70126_B = this.rand.nextFloat() * 360.0F;
/*     */       
/* 251 */       if (type == 4) {
/*     */         
/* 253 */         e.gravity *= 0.6D;
/* 254 */         e.airResistance = 0.995D;
/*     */       } 
/*     */       
/* 257 */       this.worldObj.func_72838_d((Entity)e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   class FlareParam
/*     */   {
/*     */     public final int num;
/*     */     public final int interval;
/*     */     public final int tickWait;
/*     */     public final int tickEnable;
/*     */     public final int numFlareMax;
/*     */     
/*     */     public FlareParam(int num, int interval, int tickWait, int tickEnable, int numFlareMax) {
/* 271 */       this.num = num;
/* 272 */       this.interval = interval;
/* 273 */       this.tickWait = tickWait;
/* 274 */       this.tickEnable = tickEnable;
/* 275 */       this.numFlareMax = numFlareMax;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\flare\MCH_Flare.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */