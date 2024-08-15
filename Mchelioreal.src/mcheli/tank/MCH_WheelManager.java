/*     */ package mcheli.tank;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import mcheli.MCH_Config;
/*     */ import mcheli.MCH_Lib;
/*     */ import mcheli.aircraft.MCH_AircraftInfo;
/*     */ import mcheli.aircraft.MCH_EntityAircraft;
/*     */ import mcheli.particles.MCH_ParticlesUtil;
/*     */ import mcheli.wrapper.W_Block;
/*     */ import mcheli.wrapper.W_Blocks;
/*     */ import mcheli.wrapper.W_Lib;
/*     */ import mcheli.wrapper.W_WorldFunc;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.MoverType;
/*     */ import net.minecraft.init.Blocks;
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
/*     */ 
/*     */ public class MCH_WheelManager
/*     */ {
/*     */   public final MCH_EntityAircraft parent;
/*     */   public MCH_EntityWheel[] wheels;
/*     */   private double minZ;
/*     */   private double maxZ;
/*     */   private double avgZ;
/*     */   public Vec3d weightedCenter;
/*     */   public float targetPitch;
/*     */   public float targetRoll;
/*     */   public float prevYaw;
/*  41 */   private static Random rand = new Random();
/*     */ 
/*     */   
/*     */   public MCH_WheelManager(MCH_EntityAircraft ac) {
/*  45 */     this.parent = ac;
/*  46 */     this.wheels = new MCH_EntityWheel[0];
/*     */     
/*  48 */     this.weightedCenter = Vec3d.field_186680_a;
/*     */   }
/*     */ 
/*     */   
/*     */   public void createWheels(World w, List<MCH_AircraftInfo.Wheel> list, Vec3d weightedCenter) {
/*  53 */     this.wheels = new MCH_EntityWheel[list.size() * 2];
/*     */     
/*  55 */     this.minZ = 999999.0D;
/*  56 */     this.maxZ = -999999.0D;
/*     */     
/*  58 */     this.weightedCenter = weightedCenter;
/*     */     
/*  60 */     for (int i = 0; i < this.wheels.length; i++) {
/*     */       
/*  62 */       MCH_EntityWheel wheel = new MCH_EntityWheel(w);
/*  63 */       wheel.setParents(this.parent);
/*  64 */       Vec3d wp = ((MCH_AircraftInfo.Wheel)list.get(i / 2)).pos;
/*  65 */       wheel.setWheelPos(new Vec3d((i % 2 == 0) ? wp.field_72450_a : -wp.field_72450_a, wp.field_72448_b, wp.field_72449_c), this.weightedCenter);
/*     */       
/*  67 */       Vec3d v = this.parent.getTransformedPosition(wheel.pos.field_72450_a, wheel.pos.field_72448_b, wheel.pos.field_72449_c);
/*  68 */       wheel.func_70012_b(v.field_72450_a, v.field_72448_b + 1.0D, v.field_72449_c, 0.0F, 0.0F);
/*  69 */       this.wheels[i] = wheel;
/*     */       
/*  71 */       if (wheel.pos.field_72449_c <= this.minZ)
/*  72 */         this.minZ = wheel.pos.field_72449_c; 
/*  73 */       if (wheel.pos.field_72449_c >= this.maxZ)
/*  74 */         this.maxZ = wheel.pos.field_72449_c; 
/*     */     } 
/*  76 */     this.avgZ = this.maxZ - this.minZ;
/*     */   }
/*     */ 
/*     */   
/*     */   public void move(double x, double y, double z) {
/*  81 */     MCH_EntityAircraft ac = this.parent;
/*  82 */     if (ac.getAcInfo() == null) {
/*     */       return;
/*     */     }
/*     */     
/*  86 */     boolean showLog = (ac.field_70173_aa % 1 == 1);
/*     */     
/*  88 */     if (showLog)
/*     */     {
/*  90 */       MCH_Lib.DbgLog(ac.field_70170_p, "[" + (ac.field_70170_p.field_72995_K ? "Client" : "Server") + "] ==============================", new Object[0]);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  95 */     for (MCH_EntityWheel wheel : this.wheels) {
/*     */       
/*  97 */       wheel.field_70169_q = wheel.field_70165_t;
/*  98 */       wheel.field_70167_r = wheel.field_70163_u;
/*  99 */       wheel.field_70166_s = wheel.field_70161_v;
/* 100 */       Vec3d v = ac.getTransformedPosition(wheel.pos.field_72450_a, wheel.pos.field_72448_b, wheel.pos.field_72449_c);
/* 101 */       wheel.field_70159_w = v.field_72450_a - wheel.field_70165_t + x;
/* 102 */       wheel.field_70181_x = v.field_72448_b - wheel.field_70163_u;
/* 103 */       wheel.field_70179_y = v.field_72449_c - wheel.field_70161_v + z;
/*     */     } 
/*     */     
/* 106 */     for (MCH_EntityWheel wheel : this.wheels) {
/*     */       
/* 108 */       wheel.field_70181_x *= 0.15D;
/*     */       
/* 110 */       wheel.func_70091_d(MoverType.SELF, wheel.field_70159_w, wheel.field_70181_x, wheel.field_70179_y);
/*     */       
/* 112 */       double f = 1.0D;
/*     */       
/* 114 */       wheel.func_70091_d(MoverType.SELF, 0.0D, -0.1D * f, 0.0D);
/*     */     } 
/*     */     
/* 117 */     int zmog = -1;
/*     */     int i;
/* 119 */     for (i = 0; i < this.wheels.length / 2; i++) {
/*     */       
/* 121 */       zmog = i;
/* 122 */       MCH_EntityWheel w1 = this.wheels[i * 2 + 0];
/* 123 */       MCH_EntityWheel w2 = this.wheels[i * 2 + 1];
/* 124 */       if (!w1.isPlus)
/*     */       {
/* 126 */         if (w1.field_70122_E || w2.field_70122_E) {
/*     */           
/* 128 */           zmog = -1;
/*     */           break;
/*     */         } 
/*     */       }
/*     */     } 
/* 133 */     if (zmog >= 0) {
/*     */       
/* 135 */       (this.wheels[zmog * 2 + 0]).field_70122_E = true;
/* 136 */       (this.wheels[zmog * 2 + 1]).field_70122_E = true;
/*     */     } 
/*     */     
/* 139 */     zmog = -1;
/* 140 */     for (i = this.wheels.length / 2 - 1; i >= 0; i--) {
/*     */       
/* 142 */       zmog = i;
/* 143 */       MCH_EntityWheel w1 = this.wheels[i * 2 + 0];
/* 144 */       MCH_EntityWheel w2 = this.wheels[i * 2 + 1];
/* 145 */       if (w1.isPlus)
/*     */       {
/* 147 */         if (w1.field_70122_E || w2.field_70122_E) {
/*     */           
/* 149 */           zmog = -1;
/*     */           break;
/*     */         } 
/*     */       }
/*     */     } 
/* 154 */     if (zmog >= 0) {
/*     */       
/* 156 */       (this.wheels[zmog * 2 + 0]).field_70122_E = true;
/* 157 */       (this.wheels[zmog * 2 + 1]).field_70122_E = true;
/*     */     } 
/*     */ 
/*     */     
/* 161 */     Vec3d rv = Vec3d.field_186680_a;
/* 162 */     Vec3d wc = ac.getTransformedPosition(this.weightedCenter);
/*     */ 
/*     */ 
/*     */     
/* 166 */     wc = new Vec3d(wc.field_72450_a - ac.field_70165_t, this.weightedCenter.field_72448_b, wc.field_72449_c - ac.field_70161_v);
/*     */     
/* 168 */     for (int j = 0; j < this.wheels.length / 2; j++) {
/*     */       
/* 170 */       MCH_EntityWheel w1 = this.wheels[j * 2 + 0];
/* 171 */       MCH_EntityWheel w2 = this.wheels[j * 2 + 1];
/* 172 */       Vec3d v1 = new Vec3d(w1.field_70165_t - ac.field_70165_t + wc.field_72450_a, w1.field_70163_u - ac.field_70163_u + wc.field_72448_b, w1.field_70161_v - ac.field_70161_v + wc.field_72449_c);
/* 173 */       Vec3d v2 = new Vec3d(w2.field_70165_t - ac.field_70165_t + wc.field_72450_a, w2.field_70163_u - ac.field_70163_u + wc.field_72448_b, w2.field_70161_v - ac.field_70161_v + wc.field_72449_c);
/* 174 */       Vec3d v = (w1.pos.field_72449_c >= 0.0D) ? v2.func_72431_c(v1) : v1.func_72431_c(v2);
/*     */       
/* 176 */       v = v.func_72432_b();
/* 177 */       double f = Math.abs(w1.pos.field_72449_c / this.avgZ);
/*     */       
/* 179 */       if (!w1.field_70122_E && !w2.field_70122_E)
/*     */       {
/* 181 */         f = 0.0D;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 187 */       rv = rv.func_72441_c(v.field_72450_a * f, v.field_72448_b * f, v.field_72449_c * f);
/*     */       
/* 189 */       if (showLog) {
/*     */ 
/*     */         
/* 192 */         v = v.func_178785_b((float)(ac.getRotYaw() * Math.PI / 180.0D));
/* 193 */         MCH_Lib.DbgLog(ac.field_70170_p, "%2d : %.2f :[%+.1f, %+.1f, %+.1f][%s %d %d][%+.2f(%+.2f), %+.2f(%+.2f)][%+.1f, %+.1f, %+.1f]", new Object[] {
/*     */ 
/*     */ 
/*     */               
/* 197 */               Integer.valueOf(j), Double.valueOf(f), Double.valueOf(v.field_72450_a), Double.valueOf(v.field_72448_b), 
/* 198 */               Double.valueOf(v.field_72449_c), w1.isPlus ? "+" : "-", Integer.valueOf(w1.field_70122_E ? 1 : 0), 
/* 199 */               Integer.valueOf(w2.field_70122_E ? 1 : 0), Double.valueOf(w1.field_70163_u - w1.field_70167_r), 
/* 200 */               Double.valueOf(w1.field_70181_x), Double.valueOf(w2.field_70163_u - w2.field_70167_r), 
/* 201 */               Double.valueOf(w2.field_70181_x), Double.valueOf(v.field_72450_a), Double.valueOf(v.field_72448_b), 
/* 202 */               Double.valueOf(v.field_72449_c)
/*     */             });
/*     */       } 
/*     */     } 
/*     */     
/* 207 */     rv = rv.func_72432_b();
/* 208 */     if (rv.field_72448_b > 0.01D && rv.field_72448_b < 0.7D) {
/*     */       
/* 210 */       ac.field_70159_w += rv.field_72450_a / 50.0D;
/* 211 */       ac.field_70179_y += rv.field_72449_c / 50.0D;
/*     */     } 
/*     */ 
/*     */     
/* 215 */     rv = rv.func_178785_b((float)(ac.getRotYaw() * Math.PI / 180.0D));
/* 216 */     float pitch = (float)(90.0D - Math.atan2(rv.field_72448_b, rv.field_72449_c) * 180.0D / Math.PI);
/* 217 */     float roll = -((float)(90.0D - Math.atan2(rv.field_72448_b, rv.field_72450_a) * 180.0D / Math.PI));
/*     */     
/* 219 */     float ogpf = (ac.getAcInfo()).onGroundPitchFactor;
/* 220 */     if (pitch - ac.getRotPitch() > ogpf)
/* 221 */       pitch = ac.getRotPitch() + ogpf; 
/* 222 */     if (pitch - ac.getRotPitch() < -ogpf)
/* 223 */       pitch = ac.getRotPitch() - ogpf; 
/* 224 */     float ogrf = (ac.getAcInfo()).onGroundRollFactor;
/* 225 */     if (roll - ac.getRotRoll() > ogrf)
/* 226 */       roll = ac.getRotRoll() + ogrf; 
/* 227 */     if (roll - ac.getRotRoll() < -ogrf)
/*     */     {
/* 229 */       roll = ac.getRotRoll() - ogrf;
/*     */     }
/* 231 */     this.targetPitch = pitch;
/* 232 */     this.targetRoll = roll;
/*     */     
/* 234 */     if (!W_Lib.isClientPlayer(ac.getRiddenByEntity())) {
/*     */ 
/*     */       
/* 237 */       ac.setRotPitch(pitch);
/* 238 */       ac.setRotRoll(roll);
/*     */     } 
/*     */     
/* 241 */     if (showLog)
/*     */     {
/* 243 */       MCH_Lib.DbgLog(ac.field_70170_p, "%+03d, %+03d :[%.2f, %.2f, %.2f] yaw=%.2f, pitch=%.2f, roll=%.2f", new Object[] {
/*     */             
/* 245 */             Integer.valueOf((int)pitch), Integer.valueOf((int)roll), Double.valueOf(rv.field_72450_a), 
/* 246 */             Double.valueOf(rv.field_72448_b), Double.valueOf(rv.field_72449_c), Float.valueOf(ac.getRotYaw()), 
/* 247 */             Float.valueOf(this.targetPitch), Float.valueOf(this.targetRoll)
/*     */           });
/*     */     }
/*     */     
/* 251 */     for (MCH_EntityWheel wheel : this.wheels) {
/*     */       
/* 253 */       Vec3d v = getTransformedPosition(wheel.pos.field_72450_a, wheel.pos.field_72448_b, wheel.pos.field_72449_c, ac, ac.getRotYaw(), this.targetPitch, this.targetRoll);
/*     */ 
/*     */       
/* 256 */       double rangeH = 2.0D;
/* 257 */       double poy = (wheel.field_70138_W / 2.0F);
/*     */ 
/*     */       
/* 260 */       if (wheel.field_70165_t > v.field_72450_a + rangeH) {
/*     */         
/* 262 */         wheel.field_70165_t = v.field_72450_a + rangeH;
/* 263 */         wheel.field_70163_u = v.field_72448_b + poy;
/*     */       } 
/*     */       
/* 266 */       if (wheel.field_70165_t < v.field_72450_a - rangeH) {
/*     */         
/* 268 */         wheel.field_70165_t = v.field_72450_a - rangeH;
/* 269 */         wheel.field_70163_u = v.field_72448_b + poy;
/*     */       } 
/*     */       
/* 272 */       if (wheel.field_70161_v > v.field_72449_c + rangeH) {
/*     */         
/* 274 */         wheel.field_70161_v = v.field_72449_c + rangeH;
/* 275 */         wheel.field_70163_u = v.field_72448_b + poy;
/*     */       } 
/*     */       
/* 278 */       if (wheel.field_70161_v < v.field_72449_c - rangeH) {
/*     */         
/* 280 */         wheel.field_70161_v = v.field_72449_c - rangeH;
/* 281 */         wheel.field_70163_u = v.field_72448_b + poy;
/*     */       } 
/*     */       
/* 284 */       wheel.func_70080_a(wheel.field_70165_t, wheel.field_70163_u, wheel.field_70161_v, 0.0F, 0.0F);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Vec3d getTransformedPosition(double x, double y, double z, MCH_EntityAircraft ac, float yaw, float pitch, float roll) {
/* 291 */     Vec3d v = MCH_Lib.RotVec3(x, y, z, -yaw, -pitch, -roll);
/* 292 */     return v.func_72441_c(ac.field_70165_t, ac.field_70163_u, ac.field_70161_v);
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateBlock() {
/* 297 */     if (!MCH_Config.Collision_DestroyBlock.prmBool) {
/*     */       return;
/*     */     }
/*     */     
/* 301 */     MCH_EntityAircraft ac = this.parent;
/*     */     
/* 303 */     for (MCH_EntityWheel w : this.wheels) {
/*     */       
/* 305 */       Vec3d v = ac.getTransformedPosition(w.pos);
/* 306 */       int x = (int)(v.field_72450_a + 0.5D);
/* 307 */       int y = (int)(v.field_72448_b + 0.5D);
/* 308 */       int z = (int)(v.field_72449_c + 0.5D);
/* 309 */       BlockPos blockpos = new BlockPos(x, y, z);
/*     */       
/* 311 */       IBlockState iblockstate = ac.field_70170_p.func_180495_p(blockpos);
/*     */ 
/*     */       
/* 314 */       if (iblockstate.func_177230_c() == W_Block.getSnowLayer())
/*     */       {
/*     */         
/* 317 */         ac.field_70170_p.func_175698_g(blockpos);
/*     */       }
/*     */ 
/*     */       
/* 321 */       if (iblockstate.func_177230_c() == W_Blocks.field_150392_bi || iblockstate.func_177230_c() == W_Blocks.field_150414_aQ)
/*     */       {
/* 323 */         W_WorldFunc.destroyBlock(ac.field_70170_p, x, y, z, false);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void particleLandingGear() {
/* 330 */     if (this.wheels.length <= 0) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 335 */     MCH_EntityAircraft ac = this.parent;
/* 336 */     double d = ac.field_70159_w * ac.field_70159_w + ac.field_70179_y * ac.field_70179_y + Math.abs(this.prevYaw - ac.getRotYaw());
/* 337 */     this.prevYaw = ac.getRotYaw();
/*     */     
/* 339 */     if (d > 0.001D)
/*     */     {
/* 341 */       for (int i = 0; i < 2; i++) {
/*     */         
/* 343 */         MCH_EntityWheel w = this.wheels[rand.nextInt(this.wheels.length)];
/* 344 */         Vec3d v = ac.getTransformedPosition(w.pos);
/* 345 */         int x = MathHelper.func_76128_c(v.field_72450_a + 0.5D);
/* 346 */         int y = MathHelper.func_76128_c(v.field_72448_b - 0.5D);
/* 347 */         int z = MathHelper.func_76128_c(v.field_72449_c + 0.5D);
/* 348 */         BlockPos blockpos = new BlockPos(x, y, z);
/*     */         
/* 350 */         IBlockState iblockstate = ac.field_70170_p.func_180495_p(blockpos);
/*     */ 
/*     */         
/* 353 */         if (Block.func_149680_a(iblockstate.func_177230_c(), Blocks.field_150350_a)) {
/*     */           
/* 355 */           y = MathHelper.func_76128_c(v.field_72448_b + 0.5D);
/* 356 */           blockpos = new BlockPos(x, y, z);
/*     */           
/* 358 */           iblockstate = ac.field_70170_p.func_180495_p(blockpos);
/*     */         } 
/*     */ 
/*     */         
/* 362 */         if (!Block.func_149680_a(iblockstate.func_177230_c(), Blocks.field_150350_a))
/*     */         {
/* 364 */           MCH_ParticlesUtil.spawnParticleTileCrack(ac.field_70170_p, x, y, z, v.field_72450_a + rand.nextFloat() - 0.5D, v.field_72448_b + 0.1D, v.field_72449_c + rand
/* 365 */               .nextFloat() - 0.5D, -ac.field_70159_w * 4.0D + (rand
/* 366 */               .nextFloat() - 0.5D) * 0.1D, rand.nextFloat() * 0.5D, -ac.field_70179_y * 4.0D + (rand
/* 367 */               .nextFloat() - 0.5D) * 0.1D);
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\tank\MCH_WheelManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */