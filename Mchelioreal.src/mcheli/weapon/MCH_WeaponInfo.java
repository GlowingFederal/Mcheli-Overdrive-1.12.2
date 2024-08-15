/*     */ package mcheli.weapon;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import mcheli.MCH_BaseInfo;
/*     */ import mcheli.MCH_Color;
/*     */ import mcheli.MCH_DamageFactor;
/*     */ import mcheli.__helper.addon.AddonResourceLocation;
/*     */ import mcheli.helicopter.MCH_EntityHeli;
/*     */ import mcheli.plane.MCP_EntityPlane;
/*     */ import mcheli.tank.MCH_EntityTank;
/*     */ import mcheli.vehicle.MCH_EntityVehicle;
/*     */ import mcheli.wrapper.W_Item;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MCH_WeaponInfo
/*     */   extends MCH_BaseInfo
/*     */ {
/*     */   public final String name;
/*     */   public String displayName;
/*     */   public String type;
/*     */   public int power;
/*     */   public float acceleration;
/*     */   public float accelerationInWater;
/*     */   public int explosion;
/*     */   public int explosionBlock;
/*     */   public int explosionInWater;
/*     */   public int explosionAltitude;
/*     */   public int delayFuse;
/*     */   public float bound;
/*     */   public int timeFuse;
/*     */   public boolean flaming;
/*     */   public MCH_SightType sight;
/*     */   public float[] zoom;
/*     */   public int delay;
/*     */   public int reloadTime;
/*     */   public int round;
/*     */   public int suppliedNum;
/*     */   public int maxAmmo;
/*     */   public List<RoundItem> roundItems;
/*     */   public int soundDelay;
/*     */   public float soundVolume;
/*     */   public float soundPitch;
/*     */   public float soundPitchRandom;
/*     */   public int soundPattern;
/*     */   public int lockTime;
/*     */   public boolean ridableOnly;
/*     */   public float proximityFuseDist;
/*     */   public int rigidityTime;
/*     */   public float accuracy;
/*     */   public int bomblet;
/*     */   public int bombletSTime;
/*     */   public float bombletDiff;
/*     */   public int modeNum;
/*     */   public int fixMode;
/*     */   public int piercing;
/*     */   public int heatCount;
/*     */   public int maxHeatCount;
/*     */   public boolean isFAE;
/*     */   public boolean isGuidedTorpedo;
/*     */   public float gravity;
/*     */   public float gravityInWater;
/*     */   public float velocityInWater;
/*     */   public boolean destruct;
/*     */   public String trajectoryParticleName;
/*     */   public int trajectoryParticleStartTick;
/*     */   public boolean disableSmoke;
/*     */   public MCH_Cartridge cartridge;
/*     */   public MCH_Color color;
/*     */   public MCH_Color colorInWater;
/*     */   public String soundFileName;
/*     */   public float smokeSize;
/*     */   public int smokeNum;
/*     */   public int smokeMaxAge;
/*     */   public Item dispenseItem;
/*     */   public int dispenseDamege;
/*     */   public int dispenseRange;
/*     */   public int recoilBufCount;
/*     */   public int recoilBufCountSpeed;
/*     */   public float length;
/*     */   public float radius;
/*     */   public float angle;
/*     */   public boolean displayMortarDistance;
/*     */   public boolean fixCameraPitch;
/*     */   public float cameraRotationSpeedPitch;
/*     */   public int target;
/*     */   public int markTime;
/*     */   public float recoil;
/*     */   public String bulletModelName;
/*     */   public MCH_BulletModel bulletModel;
/*     */   public String bombletModelName;
/*     */   public MCH_BulletModel bombletModel;
/*     */   public MCH_DamageFactor damageFactor;
/*     */   public String group;
/*     */   public List<MuzzleFlash> listMuzzleFlash;
/*     */   public List<MuzzleFlash> listMuzzleFlashSmoke;
/*     */   
/*     */   public MCH_WeaponInfo(AddonResourceLocation location, String path) {
/* 110 */     super(location, path);
/*     */     
/* 112 */     this.name = location.func_110623_a();
/* 113 */     this.displayName = this.name;
/* 114 */     this.type = "";
/* 115 */     this.power = 0;
/* 116 */     this.acceleration = 1.0F;
/* 117 */     this.accelerationInWater = 1.0F;
/* 118 */     this.explosion = 0;
/* 119 */     this.explosionBlock = -1;
/* 120 */     this.explosionInWater = 0;
/* 121 */     this.explosionAltitude = 0;
/* 122 */     this.delayFuse = 0;
/* 123 */     this.timeFuse = 0;
/* 124 */     this.flaming = false;
/* 125 */     this.sight = MCH_SightType.NONE;
/* 126 */     this.zoom = new float[] { 1.0F };
/*     */ 
/*     */ 
/*     */     
/* 130 */     this.delay = 10;
/* 131 */     this.reloadTime = 30;
/* 132 */     this.round = 0;
/* 133 */     this.suppliedNum = 1;
/* 134 */     this.roundItems = new ArrayList<>();
/* 135 */     this.maxAmmo = 0;
/* 136 */     this.soundDelay = 0;
/* 137 */     this.soundPattern = 0;
/* 138 */     this.soundVolume = 1.0F;
/* 139 */     this.soundPitch = 1.0F;
/* 140 */     this.soundPitchRandom = 0.1F;
/* 141 */     this.lockTime = 30;
/* 142 */     this.ridableOnly = false;
/* 143 */     this.proximityFuseDist = 0.0F;
/* 144 */     this.rigidityTime = 7;
/* 145 */     this.accuracy = 0.0F;
/* 146 */     this.bomblet = 0;
/* 147 */     this.bombletSTime = 10;
/* 148 */     this.bombletDiff = 0.3F;
/* 149 */     this.modeNum = 0;
/* 150 */     this.fixMode = 0;
/* 151 */     this.piercing = 0;
/* 152 */     this.heatCount = 0;
/* 153 */     this.maxHeatCount = 0;
/* 154 */     this.bulletModelName = "";
/* 155 */     this.bombletModelName = "";
/* 156 */     this.bulletModel = null;
/* 157 */     this.bombletModel = null;
/* 158 */     this.isFAE = false;
/* 159 */     this.isGuidedTorpedo = false;
/* 160 */     this.gravity = 0.0F;
/* 161 */     this.gravityInWater = 0.0F;
/* 162 */     this.velocityInWater = 0.999F;
/* 163 */     this.destruct = false;
/*     */     
/* 165 */     this.trajectoryParticleName = "explode";
/* 166 */     this.trajectoryParticleStartTick = 0;
/* 167 */     this.cartridge = null;
/* 168 */     this.disableSmoke = false;
/* 169 */     this.color = new MCH_Color();
/* 170 */     this.colorInWater = new MCH_Color();
/* 171 */     this.soundFileName = this.name + "_snd";
/* 172 */     this.smokeMaxAge = 100;
/* 173 */     this.smokeNum = 1;
/* 174 */     this.smokeSize = 2.0F;
/* 175 */     this.dispenseItem = null;
/* 176 */     this.dispenseDamege = 0;
/* 177 */     this.dispenseRange = 1;
/* 178 */     this.recoilBufCount = 2;
/* 179 */     this.recoilBufCountSpeed = 3;
/* 180 */     this.length = 0.0F;
/* 181 */     this.radius = 0.0F;
/* 182 */     this.target = 1;
/* 183 */     this.recoil = 0.0F;
/* 184 */     this.damageFactor = null;
/* 185 */     this.group = "";
/* 186 */     this.listMuzzleFlash = null;
/* 187 */     this.listMuzzleFlashSmoke = null;
/* 188 */     this.displayMortarDistance = false;
/* 189 */     this.fixCameraPitch = false;
/* 190 */     this.cameraRotationSpeedPitch = 1.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean validate() throws Exception {
/* 197 */     if (this.explosionBlock < 0)
/*     */     {
/* 199 */       this.explosionBlock = this.explosion;
/*     */     }
/*     */     
/* 202 */     if (this.fixMode >= this.modeNum)
/*     */     {
/* 204 */       this.fixMode = 0;
/*     */     }
/*     */     
/* 207 */     if (this.round <= 0)
/*     */     {
/* 209 */       this.round = this.maxAmmo;
/*     */     }
/*     */     
/* 212 */     if (this.round > this.maxAmmo)
/*     */     {
/* 214 */       this.round = this.maxAmmo;
/*     */     }
/*     */     
/* 217 */     if (this.explosion <= 0)
/*     */     {
/* 219 */       this.isFAE = false;
/*     */     }
/*     */     
/* 222 */     if (this.delayFuse <= 0)
/*     */     {
/* 224 */       this.bound = 0.0F;
/*     */     }
/*     */     
/* 227 */     if (this.isFAE)
/*     */     {
/* 229 */       this.explosionInWater = 0;
/*     */     }
/*     */     
/* 232 */     if (this.bomblet > 0 && this.bombletSTime < 1)
/*     */     {
/* 234 */       this.bombletSTime = 1;
/*     */     }
/*     */     
/* 237 */     if (this.destruct)
/*     */     {
/* 239 */       this.delay = 1000000;
/*     */     }
/*     */     
/* 242 */     this.angle = (float)(Math.atan2(this.radius, this.length) * 180.0D / Math.PI);
/* 243 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void loadItemData(String item, String data) {
/* 249 */     if (item.compareTo("displayname") == 0) {
/*     */       
/* 251 */       this.displayName = data;
/*     */     }
/* 253 */     else if (item.compareTo("type") == 0) {
/*     */       
/* 255 */       this.type = data.toLowerCase();
/* 256 */       if (this.type.equalsIgnoreCase("bomb") || this.type.equalsIgnoreCase("dispenser"))
/*     */       {
/* 258 */         this.gravity = -0.03F;
/* 259 */         this.gravityInWater = -0.03F;
/*     */       }
/*     */     
/* 262 */     } else if (item.compareTo("group") == 0) {
/*     */       
/* 264 */       this.group = data.toLowerCase().trim();
/*     */     }
/* 266 */     else if (item.compareTo("power") == 0) {
/*     */       
/* 268 */       this.power = toInt(data);
/*     */     }
/* 270 */     else if (item.equalsIgnoreCase("sound")) {
/*     */       
/* 272 */       this.soundFileName = data.toLowerCase().trim();
/*     */     }
/* 274 */     else if (item.compareTo("acceleration") == 0) {
/*     */       
/* 276 */       this.acceleration = toFloat(data, 0.0F, 100.0F);
/*     */     }
/* 278 */     else if (item.compareTo("accelerationinwater") == 0) {
/*     */       
/* 280 */       this.accelerationInWater = toFloat(data, 0.0F, 100.0F);
/*     */     }
/* 282 */     else if (item.compareTo("gravity") == 0) {
/*     */       
/* 284 */       this.gravity = toFloat(data, -50.0F, 50.0F);
/*     */     }
/* 286 */     else if (item.compareTo("gravityinwater") == 0) {
/*     */       
/* 288 */       this.gravityInWater = toFloat(data, -50.0F, 50.0F);
/*     */     }
/* 290 */     else if (item.equalsIgnoreCase("VelocityInWater")) {
/*     */       
/* 292 */       this.velocityInWater = toFloat(data);
/*     */     }
/* 294 */     else if (item.compareTo("explosion") == 0) {
/*     */       
/* 296 */       this.explosion = toInt(data, 0, 50);
/*     */     }
/* 298 */     else if (item.equalsIgnoreCase("explosionBlock")) {
/*     */       
/* 300 */       this.explosionBlock = toInt(data, 0, 50);
/*     */     }
/* 302 */     else if (item.compareTo("explosioninwater") == 0) {
/*     */       
/* 304 */       this.explosionInWater = toInt(data, 0, 50);
/*     */     }
/* 306 */     else if (item.equalsIgnoreCase("ExplosionAltitude")) {
/*     */       
/* 308 */       this.explosionAltitude = toInt(data, 0, 100);
/*     */     }
/* 310 */     else if (item.equalsIgnoreCase("TimeFuse")) {
/*     */       
/* 312 */       this.timeFuse = toInt(data, 0, 100000);
/*     */     }
/* 314 */     else if (item.equalsIgnoreCase("DelayFuse")) {
/*     */       
/* 316 */       this.delayFuse = toInt(data, 0, 100000);
/*     */     }
/* 318 */     else if (item.equalsIgnoreCase("Bound")) {
/*     */       
/* 320 */       this.bound = toFloat(data, 0.0F, 100000.0F);
/*     */     }
/* 322 */     else if (item.compareTo("flaming") == 0) {
/*     */       
/* 324 */       this.flaming = toBool(data);
/*     */     }
/* 326 */     else if (item.equalsIgnoreCase("DisplayMortarDistance")) {
/*     */       
/* 328 */       this.displayMortarDistance = toBool(data);
/*     */     }
/* 330 */     else if (item.equalsIgnoreCase("FixCameraPitch")) {
/*     */       
/* 332 */       this.fixCameraPitch = toBool(data);
/*     */     }
/* 334 */     else if (item.equalsIgnoreCase("CameraRotationSpeedPitch")) {
/*     */       
/* 336 */       this.cameraRotationSpeedPitch = toFloat(data, 0.0F, 100.0F);
/*     */     }
/* 338 */     else if (item.compareTo("sight") == 0) {
/*     */       
/* 340 */       data = data.toLowerCase();
/* 341 */       if (data.compareTo("movesight") == 0)
/*     */       {
/* 343 */         this.sight = MCH_SightType.ROCKET;
/*     */       }
/* 345 */       if (data.compareTo("missilesight") == 0)
/*     */       {
/* 347 */         this.sight = MCH_SightType.LOCK;
/*     */       }
/*     */     }
/* 350 */     else if (item.equalsIgnoreCase("Zoom")) {
/*     */       
/* 352 */       String[] s = splitParam(data);
/* 353 */       if (s.length > 0)
/*     */       {
/* 355 */         this.zoom = new float[s.length];
/* 356 */         for (int i = 0; i < s.length; i++)
/*     */         {
/* 358 */           this.zoom[i] = toFloat(s[i], 0.1F, 10.0F);
/*     */         }
/*     */       }
/*     */     
/* 362 */     } else if (item.compareTo("delay") == 0) {
/*     */       
/* 364 */       this.delay = toInt(data, 0, 100000);
/*     */     }
/* 366 */     else if (item.compareTo("reloadtime") == 0) {
/*     */       
/* 368 */       this.reloadTime = toInt(data, 3, 1000);
/*     */     }
/* 370 */     else if (item.compareTo("round") == 0) {
/*     */       
/* 372 */       this.round = toInt(data, 1, 30000);
/*     */     }
/* 374 */     else if (item.equalsIgnoreCase("MaxAmmo")) {
/*     */       
/* 376 */       this.maxAmmo = toInt(data, 0, 30000);
/*     */     }
/* 378 */     else if (item.equalsIgnoreCase("SuppliedNum")) {
/*     */       
/* 380 */       this.suppliedNum = toInt(data, 1, 30000);
/*     */     }
/* 382 */     else if (item.equalsIgnoreCase("Item")) {
/*     */       
/* 384 */       String[] s = data.split("\\s*,\\s*");
/* 385 */       if (s.length >= 2 && s[1].length() > 0 && this.roundItems.size() < 3) {
/*     */         
/* 387 */         int n = toInt(s[0], 1, 64);
/* 388 */         if (n > 0)
/*     */         {
/* 390 */           int damage = (s.length >= 3) ? toInt(s[2], 0, 100000000) : 0;
/*     */           
/* 392 */           this.roundItems.add(new RoundItem(n, s[1].toLowerCase().trim(), damage));
/*     */         }
/*     */       
/*     */       } 
/* 396 */     } else if (item.compareTo("sounddelay") == 0) {
/*     */       
/* 398 */       this.soundDelay = toInt(data, 0, 1000);
/*     */     }
/* 400 */     else if (item.compareTo("soundpattern") != 0) {
/*     */ 
/*     */       
/* 403 */       if (item.compareTo("soundvolume") == 0) {
/*     */         
/* 405 */         this.soundVolume = toFloat(data, 0.0F, 1000.0F);
/*     */       }
/* 407 */       else if (item.compareTo("soundpitch") == 0) {
/*     */         
/* 409 */         this.soundPitch = toFloat(data, 0.0F, 1.0F);
/*     */       }
/* 411 */       else if (item.equalsIgnoreCase("SoundPitchRandom")) {
/*     */         
/* 413 */         this.soundPitchRandom = toFloat(data, 0.0F, 1.0F);
/*     */       }
/* 415 */       else if (item.compareTo("locktime") == 0) {
/*     */         
/* 417 */         this.lockTime = toInt(data, 2, 1000);
/*     */       }
/* 419 */       else if (item.equalsIgnoreCase("RidableOnly")) {
/*     */         
/* 421 */         this.ridableOnly = toBool(data);
/*     */       }
/* 423 */       else if (item.compareTo("proximityfusedist") == 0) {
/*     */         
/* 425 */         this.proximityFuseDist = toFloat(data, 0.0F, 2000.0F);
/*     */       }
/* 427 */       else if (item.equalsIgnoreCase("RigidityTime")) {
/*     */         
/* 429 */         this.rigidityTime = toInt(data, 0, 1000000);
/*     */       }
/* 431 */       else if (item.compareTo("accuracy") == 0) {
/*     */         
/* 433 */         this.accuracy = toFloat(data, 0.0F, 1000.0F);
/*     */       }
/* 435 */       else if (item.compareTo("bomblet") == 0) {
/*     */         
/* 437 */         this.bomblet = toInt(data, 0, 1000);
/*     */       }
/* 439 */       else if (item.compareTo("bombletstime") == 0) {
/*     */         
/* 441 */         this.bombletSTime = toInt(data, 0, 1000);
/*     */       }
/* 443 */       else if (item.equalsIgnoreCase("BombletDiff")) {
/*     */         
/* 445 */         this.bombletDiff = toFloat(data, 0.0F, 1000.0F);
/*     */       }
/* 447 */       else if (item.equalsIgnoreCase("RecoilBufCount")) {
/*     */         
/* 449 */         String[] s = splitParam(data);
/* 450 */         if (s.length >= 1)
/*     */         {
/* 452 */           this.recoilBufCount = toInt(s[0], 1, 10000);
/*     */         }
/* 454 */         if (s.length >= 2 && this.recoilBufCount > 2)
/*     */         {
/* 456 */           this.recoilBufCountSpeed = toInt(s[1], 1, 10000) - 1;
/* 457 */           if (this.recoilBufCountSpeed > this.recoilBufCount / 2)
/*     */           {
/* 459 */             this.recoilBufCountSpeed = this.recoilBufCount / 2;
/*     */           }
/*     */         }
/*     */       
/* 463 */       } else if (item.compareTo("modenum") == 0) {
/*     */         
/* 465 */         this.modeNum = toInt(data, 0, 1000);
/*     */       }
/* 467 */       else if (item.equalsIgnoreCase("FixMode")) {
/*     */         
/* 469 */         this.fixMode = toInt(data, 0, 10);
/*     */       }
/* 471 */       else if (item.compareTo("piercing") == 0) {
/*     */         
/* 473 */         this.piercing = toInt(data, 0, 100000);
/*     */       }
/* 475 */       else if (item.compareTo("heatcount") == 0) {
/*     */         
/* 477 */         this.heatCount = toInt(data, 0, 100000);
/*     */       }
/* 479 */       else if (item.compareTo("maxheatcount") == 0) {
/*     */         
/* 481 */         this.maxHeatCount = toInt(data, 0, 100000);
/*     */       }
/* 483 */       else if (item.compareTo("modelbullet") == 0) {
/*     */         
/* 485 */         this.bulletModelName = data.toLowerCase().trim();
/*     */       }
/* 487 */       else if (item.equalsIgnoreCase("ModelBomblet")) {
/*     */         
/* 489 */         this.bombletModelName = data.toLowerCase().trim();
/*     */       }
/* 491 */       else if (item.compareTo("fae") == 0) {
/*     */         
/* 493 */         this.isFAE = toBool(data);
/*     */       }
/* 495 */       else if (item.compareTo("guidedtorpedo") == 0) {
/*     */         
/* 497 */         this.isGuidedTorpedo = toBool(data);
/*     */       }
/* 499 */       else if (item.compareTo("destruct") == 0) {
/*     */         
/* 501 */         this.destruct = toBool(data);
/*     */       }
/* 503 */       else if (item.equalsIgnoreCase("AddMuzzleFlash")) {
/*     */         
/* 505 */         String[] s = splitParam(data);
/* 506 */         if (s.length >= 7)
/*     */         {
/* 508 */           if (this.listMuzzleFlash == null)
/*     */           {
/* 510 */             this.listMuzzleFlash = new ArrayList<>();
/*     */           }
/*     */ 
/*     */ 
/*     */           
/* 515 */           this.listMuzzleFlash.add(new MuzzleFlash(toFloat(s[0]), toFloat(s[1]), 0.0F, 
/* 516 */                 toInt(s[2]), toFloat(s[3]) / 255.0F, toFloat(s[4]) / 255.0F, toFloat(s[5]) / 255.0F, 
/* 517 */                 toFloat(s[6]) / 255.0F, 1));
/*     */         }
/*     */       
/*     */       }
/* 521 */       else if (item.equalsIgnoreCase("AddMuzzleFlashSmoke")) {
/*     */         
/* 523 */         String[] s = splitParam(data);
/* 524 */         if (s.length >= 9)
/*     */         {
/* 526 */           if (this.listMuzzleFlashSmoke == null)
/*     */           {
/* 528 */             this.listMuzzleFlashSmoke = new ArrayList<>();
/*     */           }
/*     */ 
/*     */ 
/*     */           
/* 533 */           this.listMuzzleFlashSmoke.add(new MuzzleFlash(toFloat(s[0]), toFloat(s[2]), 
/* 534 */                 toFloat(s[3]), toInt(s[4]), toFloat(s[5]) / 255.0F, toFloat(s[6]) / 255.0F, 
/* 535 */                 toFloat(s[7]) / 255.0F, toFloat(s[8]) / 255.0F, toInt(s[1], 1, 1000)));
/*     */         
/*     */         }
/*     */       
/*     */       }
/* 540 */       else if (item.equalsIgnoreCase("TrajectoryParticle")) {
/*     */         
/* 542 */         this.trajectoryParticleName = data.toLowerCase().trim();
/* 543 */         if (this.trajectoryParticleName.equalsIgnoreCase("none"))
/*     */         {
/* 545 */           this.trajectoryParticleName = "";
/*     */         }
/*     */       }
/* 548 */       else if (item.equalsIgnoreCase("TrajectoryParticleStartTick")) {
/*     */         
/* 550 */         this.trajectoryParticleStartTick = toInt(data, 0, 10000);
/*     */       }
/* 552 */       else if (item.equalsIgnoreCase("DisableSmoke")) {
/*     */         
/* 554 */         this.disableSmoke = toBool(data);
/*     */       }
/* 556 */       else if (item.equalsIgnoreCase("SetCartridge")) {
/*     */         
/* 558 */         String[] s = data.split("\\s*,\\s*");
/* 559 */         if (s.length > 0 && s[0].length() > 0)
/*     */         {
/* 561 */           float ac = (s.length >= 2) ? toFloat(s[1]) : 0.0F;
/* 562 */           float yw = (s.length >= 3) ? toFloat(s[2]) : 0.0F;
/* 563 */           float pt = (s.length >= 4) ? toFloat(s[3]) : 0.0F;
/* 564 */           float sc = (s.length >= 5) ? toFloat(s[4]) : 1.0F;
/* 565 */           float gr = (s.length >= 6) ? toFloat(s[5]) : -0.04F;
/* 566 */           float bo = (s.length >= 7) ? toFloat(s[6]) : 0.5F;
/* 567 */           this.cartridge = new MCH_Cartridge(s[0].toLowerCase(), ac, yw, pt, bo, gr, sc);
/*     */         }
/*     */       
/* 570 */       } else if (item.equalsIgnoreCase("BulletColorInWater") || item.equalsIgnoreCase("BulletColor") || item
/* 571 */         .equalsIgnoreCase("SmokeColor")) {
/*     */ 
/*     */         
/* 574 */         String[] s = data.split("\\s*,\\s*");
/* 575 */         if (s.length >= 4) {
/*     */ 
/*     */ 
/*     */           
/* 579 */           MCH_Color c = new MCH_Color(0.003921569F * toInt(s[0], 0, 255), 0.003921569F * toInt(s[1], 0, 255), 0.003921569F * toInt(s[2], 0, 255), 0.003921569F * toInt(s[3], 0, 255));
/*     */           
/* 581 */           if (item.equalsIgnoreCase("BulletColorInWater"))
/*     */           {
/* 583 */             this.colorInWater = c;
/*     */           }
/*     */           else
/*     */           {
/* 587 */             this.color = c;
/*     */           }
/*     */         
/*     */         } 
/* 591 */       } else if (item.equalsIgnoreCase("SmokeSize")) {
/*     */         
/* 593 */         this.smokeSize = toFloat(data, 0.0F, 100.0F);
/*     */       }
/* 595 */       else if (item.equalsIgnoreCase("SmokeNum")) {
/*     */         
/* 597 */         this.smokeNum = toInt(data, 1, 100);
/*     */       }
/* 599 */       else if (item.equalsIgnoreCase("SmokeMaxAge")) {
/*     */         
/* 601 */         this.smokeMaxAge = toInt(data, 2, 1000);
/*     */       }
/* 603 */       else if (item.equalsIgnoreCase("DispenseItem")) {
/*     */         
/* 605 */         String[] s = data.split("\\s*,\\s*");
/* 606 */         if (s.length >= 2)
/*     */         {
/* 608 */           this.dispenseDamege = toInt(s[1], 0, 100000000);
/*     */         }
/* 610 */         this.dispenseItem = W_Item.getItemByName(s[0]);
/*     */       }
/* 612 */       else if (item.equalsIgnoreCase("DispenseRange")) {
/*     */         
/* 614 */         this.dispenseRange = toInt(data, 1, 100);
/*     */       }
/* 616 */       else if (item.equalsIgnoreCase("Length")) {
/*     */         
/* 618 */         this.length = toInt(data, 1, 300);
/*     */       }
/* 620 */       else if (item.equalsIgnoreCase("Radius")) {
/*     */         
/* 622 */         this.radius = toInt(data, 1, 1000);
/*     */       }
/* 624 */       else if (item.equalsIgnoreCase("Target")) {
/*     */         
/* 626 */         if (data.indexOf("block") >= 0)
/*     */         {
/* 628 */           this.target = 64;
/*     */         }
/*     */         else
/*     */         {
/* 632 */           this.target = 0;
/* 633 */           this.target |= (data.indexOf("planes") >= 0) ? 32 : 0;
/* 634 */           this.target |= (data.indexOf("helicopters") >= 0) ? 16 : 0;
/* 635 */           this.target |= (data.indexOf("vehicles") >= 0) ? 8 : 0;
/* 636 */           this.target |= (data.indexOf("tanks") >= 0) ? 8 : 0;
/* 637 */           this.target |= (data.indexOf("players") >= 0) ? 4 : 0;
/* 638 */           this.target |= (data.indexOf("monsters") >= 0) ? 2 : 0;
/* 639 */           this.target |= (data.indexOf("others") >= 0) ? 1 : 0;
/*     */         }
/*     */       
/* 642 */       } else if (item.equalsIgnoreCase("MarkTime")) {
/*     */         
/* 644 */         this.markTime = toInt(data, 1, 30000) + 1;
/*     */       }
/* 646 */       else if (item.equalsIgnoreCase("Recoil")) {
/*     */         
/* 648 */         this.recoil = toFloat(data, 0.0F, 100.0F);
/*     */       }
/* 650 */       else if (item.equalsIgnoreCase("DamageFactor")) {
/*     */         
/* 652 */         String[] s = splitParam(data);
/*     */         
/* 654 */         if (s.length >= 2) {
/*     */           Class<MCH_EntityVehicle> clazz;
/* 656 */           Class<? extends Entity> c = null;
/* 657 */           String className = s[0].toLowerCase();
/*     */           
/* 659 */           if (className.equals("player")) {
/*     */             
/* 661 */             Class<EntityPlayer> clazz1 = EntityPlayer.class;
/*     */           }
/* 663 */           else if (className.equals("heli") || className.equals("helicopter")) {
/*     */             
/* 665 */             Class<MCH_EntityHeli> clazz1 = MCH_EntityHeli.class;
/*     */           }
/* 667 */           else if (className.equals("plane")) {
/*     */             
/* 669 */             Class<MCP_EntityPlane> clazz1 = MCP_EntityPlane.class;
/*     */           }
/* 671 */           else if (className.equals("tank")) {
/*     */             
/* 673 */             Class<MCH_EntityTank> clazz1 = MCH_EntityTank.class;
/*     */           }
/* 675 */           else if (className.equals("vehicle")) {
/*     */             
/* 677 */             clazz = MCH_EntityVehicle.class;
/*     */           } 
/*     */           
/* 680 */           if (clazz != null) {
/*     */             
/* 682 */             if (this.damageFactor == null)
/*     */             {
/* 684 */               this.damageFactor = new MCH_DamageFactor();
/*     */             }
/* 686 */             this.damageFactor.add(clazz, toFloat(s[1], 0.0F, 1000000.0F));
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public float getDamageFactor(Entity e) {
/* 695 */     return (this.damageFactor != null) ? this.damageFactor.getDamageFactor(e) : 1.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getWeaponTypeName() {
/* 700 */     if (this.type.equalsIgnoreCase("MachineGun1"))
/* 701 */       return "MachineGun"; 
/* 702 */     if (this.type.equalsIgnoreCase("MachineGun2"))
/* 703 */       return "MachineGun"; 
/* 704 */     if (this.type.equalsIgnoreCase("Torpedo"))
/* 705 */       return "Torpedo"; 
/* 706 */     if (this.type.equalsIgnoreCase("CAS"))
/* 707 */       return "CAS"; 
/* 708 */     if (this.type.equalsIgnoreCase("Rocket"))
/* 709 */       return "Rocket"; 
/* 710 */     if (this.type.equalsIgnoreCase("ASMissile"))
/* 711 */       return "AS Missile"; 
/* 712 */     if (this.type.equalsIgnoreCase("AAMissile"))
/* 713 */       return "AA Missile"; 
/* 714 */     if (this.type.equalsIgnoreCase("TVMissile"))
/* 715 */       return "TV Missile"; 
/* 716 */     if (this.type.equalsIgnoreCase("ATMissile"))
/* 717 */       return "AT Missile"; 
/* 718 */     if (this.type.equalsIgnoreCase("Bomb"))
/* 719 */       return "Bomb"; 
/* 720 */     if (this.type.equalsIgnoreCase("MkRocket"))
/* 721 */       return "Mk Rocket"; 
/* 722 */     if (this.type.equalsIgnoreCase("Dummy"))
/* 723 */       return "Dummy"; 
/* 724 */     if (this.type.equalsIgnoreCase("Smoke"))
/* 725 */       return "Smoke"; 
/* 726 */     if (this.type.equalsIgnoreCase("Smoke"))
/* 727 */       return "Smoke"; 
/* 728 */     if (this.type.equalsIgnoreCase("Dispenser"))
/* 729 */       return "Dispenser"; 
/* 730 */     if (this.type.equalsIgnoreCase("TargetingPod"))
/* 731 */       return "Targeting Pod"; 
/* 732 */     return "";
/*     */   }
/*     */ 
/*     */   
/*     */   public class MuzzleFlash
/*     */   {
/*     */     public final float dist;
/*     */     public final float size;
/*     */     public final float range;
/*     */     public final int age;
/*     */     public final float a;
/*     */     public final float r;
/*     */     public final float g;
/*     */     public final float b;
/*     */     public final int num;
/*     */     
/*     */     public MuzzleFlash(float dist, float size, float range, int age, float a, float r, float g, float b, int num) {
/* 749 */       this.dist = dist;
/* 750 */       this.size = size;
/* 751 */       this.range = range;
/* 752 */       this.age = age;
/* 753 */       this.a = a;
/* 754 */       this.r = r;
/* 755 */       this.g = g;
/* 756 */       this.b = b;
/* 757 */       this.num = num;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public class RoundItem
/*     */   {
/*     */     public final int num;
/*     */     public final String itemName;
/*     */     public final int damage;
/* 767 */     public ItemStack itemStack = ItemStack.field_190927_a;
/*     */ 
/*     */     
/*     */     public RoundItem(int n, String name, int damage) {
/* 771 */       this.num = n;
/* 772 */       this.itemName = name;
/* 773 */       this.damage = damage;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\weapon\MCH_WeaponInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */