/*     */ package mcheli;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.PrintWriter;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import mcheli.wrapper.W_Block;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.DamageSource;
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
/*     */ public class MCH_Config
/*     */ {
/*     */   public static String mcPath;
/*     */   public static String configFilePath;
/*     */   public static boolean DebugLog;
/*     */   public static String configVer;
/*     */   public static int hitMarkColorRGB;
/*     */   public static float hitMarkColorAlpha;
/*     */   public static List<Block> bulletBreakableBlocks;
/*  35 */   public static final List<Block> dummyBreakableBlocks = new ArrayList<>();
/*  36 */   public static final List<Material> dummyBreakableMaterials = new ArrayList<>();
/*     */   
/*     */   public static List<Block> carNoBreakableBlocks;
/*     */   
/*     */   public static List<Block> carBreakableBlocks;
/*     */   
/*     */   public static List<Material> carBreakableMaterials;
/*     */   
/*     */   public static List<Block> tankNoBreakableBlocks;
/*     */   
/*     */   public static List<Block> tankBreakableBlocks;
/*     */   
/*     */   public static List<Material> tankBreakableMaterials;
/*     */   
/*     */   public static MCH_ConfigPrm KeyUp;
/*     */   
/*     */   public static MCH_ConfigPrm KeyDown;
/*     */   
/*     */   public static MCH_ConfigPrm KeyRight;
/*     */   
/*     */   public static MCH_ConfigPrm KeyLeft;
/*     */   
/*     */   public static MCH_ConfigPrm KeySwitchMode;
/*     */   
/*     */   public static MCH_ConfigPrm KeySwitchHovering;
/*     */   
/*     */   public static MCH_ConfigPrm KeyAttack;
/*     */   
/*     */   public static MCH_ConfigPrm KeyUseWeapon;
/*     */   
/*     */   public static MCH_ConfigPrm KeySwitchWeapon1;
/*     */   
/*     */   public static MCH_ConfigPrm KeySwitchWeapon2;
/*     */   
/*     */   public static MCH_ConfigPrm KeySwWeaponMode;
/*     */   
/*     */   public static MCH_ConfigPrm KeyZoom;
/*     */   
/*     */   public static MCH_ConfigPrm KeyCameraMode;
/*     */   
/*     */   public static MCH_ConfigPrm KeyUnmount;
/*     */   
/*     */   public static MCH_ConfigPrm KeyFlare;
/*     */   
/*     */   public static MCH_ConfigPrm KeyExtra;
/*     */   public static MCH_ConfigPrm KeyCameraDistUp;
/*     */   public static MCH_ConfigPrm KeyCameraDistDown;
/*     */   public static MCH_ConfigPrm KeyFreeLook;
/*     */   public static MCH_ConfigPrm KeyGUI;
/*     */   public static MCH_ConfigPrm KeyGearUpDown;
/*     */   public static MCH_ConfigPrm KeyPutToRack;
/*     */   public static MCH_ConfigPrm KeyDownFromRack;
/*     */   public static MCH_ConfigPrm KeyScoreboard;
/*     */   public static MCH_ConfigPrm KeyMultiplayManager;
/*     */   public static List<MCH_ConfigPrm> DamageVs;
/*     */   public static List<String> IgnoreBulletHitList;
/*     */   public static MCH_ConfigPrm IgnoreBulletHitItem;
/*     */   public static DamageFactor[] DamageFactorList;
/*     */   public static DamageFactor DamageVsEntity;
/*     */   public static DamageFactor DamageVsLiving;
/*     */   public static DamageFactor DamageVsPlayer;
/*     */   public static DamageFactor DamageVsMCHeliAircraft;
/*     */   public static DamageFactor DamageVsMCHeliTank;
/*     */   public static DamageFactor DamageVsMCHeliVehicle;
/*     */   public static DamageFactor DamageVsMCHeliOther;
/*     */   public static DamageFactor DamageAircraftByExternal;
/*     */   public static DamageFactor DamageTankByExternal;
/*     */   public static DamageFactor DamageVehicleByExternal;
/*     */   public static DamageFactor DamageOtherByExternal;
/*     */   public static List<MCH_ConfigPrm> CommandPermission;
/*     */   public static List<CommandPermission> CommandPermissionList;
/*     */   public static MCH_ConfigPrm TestMode;
/*     */   public static MCH_ConfigPrm __TextureAlpha;
/*     */   public static MCH_ConfigPrm EnableCommand;
/*     */   public static MCH_ConfigPrm PlaceableOnSpongeOnly;
/*     */   public static MCH_ConfigPrm HideKeybind;
/*     */   public static MCH_ConfigPrm ItemDamage;
/*     */   public static MCH_ConfigPrm ItemFuel;
/*     */   public static MCH_ConfigPrm AutoRepairHP;
/*     */   public static MCH_ConfigPrm Collision_DestroyBlock;
/*     */   public static MCH_ConfigPrm Explosion_DestroyBlock;
/*     */   public static MCH_ConfigPrm Explosion_FlamingBlock;
/*     */   public static MCH_ConfigPrm BulletBreakableBlock;
/*     */   public static MCH_ConfigPrm Collision_Car_BreakableBlock;
/*     */   public static MCH_ConfigPrm Collision_Car_NoBreakableBlock;
/*     */   public static MCH_ConfigPrm Collision_Car_BreakableMaterial;
/*     */   public static MCH_ConfigPrm Collision_Tank_BreakableBlock;
/*     */   public static MCH_ConfigPrm Collision_Tank_NoBreakableBlock;
/*     */   public static MCH_ConfigPrm Collision_Tank_BreakableMaterial;
/*     */   public static MCH_ConfigPrm Collision_EntityDamage;
/*     */   public static MCH_ConfigPrm Collision_EntityTankDamage;
/*     */   public static MCH_ConfigPrm LWeaponAutoFire;
/*     */   public static MCH_ConfigPrm DismountAll;
/*     */   public static MCH_ConfigPrm MountMinecartHeli;
/*     */   public static MCH_ConfigPrm MountMinecartPlane;
/*     */   public static MCH_ConfigPrm MountMinecartVehicle;
/*     */   public static MCH_ConfigPrm MountMinecartTank;
/*     */   public static MCH_ConfigPrm AutoThrottleDownHeli;
/*     */   public static MCH_ConfigPrm AutoThrottleDownPlane;
/*     */   public static MCH_ConfigPrm AutoThrottleDownTank;
/*     */   public static MCH_ConfigPrm DisableItemRender;
/*     */   public static MCH_ConfigPrm RenderDistanceWeight;
/*     */   public static MCH_ConfigPrm MobRenderDistanceWeight;
/*     */   public static MCH_ConfigPrm CreativeTabIcon;
/*     */   public static MCH_ConfigPrm CreativeTabIconHeli;
/*     */   public static MCH_ConfigPrm CreativeTabIconPlane;
/*     */   public static MCH_ConfigPrm CreativeTabIconTank;
/*     */   public static MCH_ConfigPrm CreativeTabIconVehicle;
/*     */   public static MCH_ConfigPrm DisableShader;
/*     */   public static MCH_ConfigPrm AliveTimeOfCartridge;
/*     */   public static MCH_ConfigPrm InfinityAmmo;
/*     */   public static MCH_ConfigPrm InfinityFuel;
/*     */   public static MCH_ConfigPrm HitMarkColor;
/*     */   public static MCH_ConfigPrm SmoothShading;
/*     */   public static MCH_ConfigPrm EnableModEntityRender;
/*     */   public static MCH_ConfigPrm DisableRenderLivingSpecials;
/*     */   public static MCH_ConfigPrm PreventingBroken;
/*     */   public static MCH_ConfigPrm DropItemInCreativeMode;
/*     */   public static MCH_ConfigPrm BreakableOnlyPickaxe;
/*     */   public static MCH_ConfigPrm InvertMouse;
/*     */   public static MCH_ConfigPrm MouseSensitivity;
/*     */   public static MCH_ConfigPrm MouseControlStickModeHeli;
/*     */   public static MCH_ConfigPrm MouseControlStickModePlane;
/*     */   public static MCH_ConfigPrm MouseControlFlightSimMode;
/*     */   public static MCH_ConfigPrm SwitchWeaponWithMouseWheel;
/*     */   public static MCH_ConfigPrm AllPlaneSpeed;
/*     */   public static MCH_ConfigPrm AllHeliSpeed;
/*     */   public static MCH_ConfigPrm AllTankSpeed;
/*     */   public static MCH_ConfigPrm HurtResistantTime;
/*     */   public static MCH_ConfigPrm DisplayHUDThirdPerson;
/*     */   public static MCH_ConfigPrm DisableCameraDistChange;
/*     */   public static MCH_ConfigPrm EnableReplaceTextureManager;
/*     */   public static MCH_ConfigPrm DisplayEntityMarker;
/*     */   public static MCH_ConfigPrm EntityMarkerSize;
/*     */   public static MCH_ConfigPrm BlockMarkerSize;
/*     */   public static MCH_ConfigPrm DisplayMarkThroughWall;
/*     */   public static MCH_ConfigPrm ReplaceRenderViewEntity;
/*     */   public static MCH_ConfigPrm StingerLockRange;
/*     */   public static MCH_ConfigPrm DefaultExplosionParticle;
/*     */   public static MCH_ConfigPrm RangeFinderSpotDist;
/*     */   public static MCH_ConfigPrm RangeFinderSpotTime;
/*     */   public static MCH_ConfigPrm RangeFinderConsume;
/*     */   public static MCH_ConfigPrm EnablePutRackInFlying;
/*     */   public static MCH_ConfigPrm EnableDebugBoundingBox;
/*     */   public static MCH_ConfigPrm DespawnCount;
/*     */   public static MCH_ConfigPrm HitBoxDelayTick;
/*     */   public static MCH_ConfigPrm EnableRotationLimit;
/*     */   public static MCH_ConfigPrm PitchLimitMax;
/*     */   public static MCH_ConfigPrm PitchLimitMin;
/*     */   public static MCH_ConfigPrm RollLimit;
/*     */   public static MCH_ConfigPrm RangeOfGunner_VsMonster_Vertical;
/*     */   public static MCH_ConfigPrm RangeOfGunner_VsMonster_Horizontal;
/*     */   public static MCH_ConfigPrm RangeOfGunner_VsPlayer_Vertical;
/*     */   public static MCH_ConfigPrm RangeOfGunner_VsPlayer_Horizontal;
/*     */   public static MCH_ConfigPrm FixVehicleAtPlacedPoint;
/*     */   public static MCH_ConfigPrm KillPassengersWhenDestroyed;
/*     */   public static MCH_ConfigPrm ItemID_Fuel;
/*     */   public static MCH_ConfigPrm ItemID_GLTD;
/*     */   public static MCH_ConfigPrm ItemID_Chain;
/*     */   public static MCH_ConfigPrm ItemID_Parachute;
/*     */   public static MCH_ConfigPrm ItemID_Container;
/*     */   public static MCH_ConfigPrm ItemID_Stinger;
/*     */   public static MCH_ConfigPrm ItemID_StingerMissile;
/*     */   public static MCH_ConfigPrm[] ItemID_UavStation;
/*     */   public static MCH_ConfigPrm ItemID_InvisibleItem;
/*     */   public static MCH_ConfigPrm ItemID_DraftingTable;
/*     */   public static MCH_ConfigPrm ItemID_Wrench;
/*     */   public static MCH_ConfigPrm ItemID_RangeFinder;
/*     */   public static MCH_ConfigPrm BlockID_DraftingTableOFF;
/*     */   public static MCH_ConfigPrm BlockID_DraftingTableON;
/*     */   public static MCH_ConfigPrm[] KeyConfig;
/*     */   public static MCH_ConfigPrm[] General;
/* 208 */   public final String destroyBlockNames = "glass_pane, stained_glass_pane, tallgrass, double_plant, yellow_flower, red_flower, vine, wheat, reeds, waterlily";
/*     */ 
/*     */   
/*     */   public MCH_Config(String minecraftPath, String cfgFile) {
/* 212 */     mcPath = minecraftPath;
/* 213 */     configFilePath = mcPath + cfgFile;
/*     */     
/* 215 */     DebugLog = false;
/*     */     
/* 217 */     configVer = "0.0.0";
/*     */     
/* 219 */     bulletBreakableBlocks = new ArrayList<>();
/* 220 */     carBreakableBlocks = new ArrayList<>();
/* 221 */     carNoBreakableBlocks = new ArrayList<>();
/* 222 */     carBreakableMaterials = new ArrayList<>();
/* 223 */     tankBreakableBlocks = new ArrayList<>();
/* 224 */     tankNoBreakableBlocks = new ArrayList<>();
/* 225 */     tankBreakableMaterials = new ArrayList<>();
/*     */     
/* 227 */     KeyUp = new MCH_ConfigPrm("KeyUp", 17);
/* 228 */     KeyDown = new MCH_ConfigPrm("KeyDown", 31);
/* 229 */     KeyRight = new MCH_ConfigPrm("KeyRight", 32);
/* 230 */     KeyLeft = new MCH_ConfigPrm("KeyLeft", 30);
/* 231 */     KeySwitchMode = new MCH_ConfigPrm("KeySwitchGunner", 35);
/* 232 */     KeySwitchHovering = new MCH_ConfigPrm("KeySwitchHovering", 57);
/* 233 */     KeyAttack = new MCH_ConfigPrm("KeyAttack", -100);
/* 234 */     KeyUseWeapon = new MCH_ConfigPrm("KeyUseWeapon", -99);
/* 235 */     KeySwitchWeapon1 = new MCH_ConfigPrm("KeySwitchWeapon1", -98);
/* 236 */     KeySwitchWeapon2 = new MCH_ConfigPrm("KeySwitchWeapon2", 34);
/* 237 */     KeySwWeaponMode = new MCH_ConfigPrm("KeySwitchWeaponMode", 45);
/* 238 */     KeyZoom = new MCH_ConfigPrm("KeyZoom", 44);
/* 239 */     KeyCameraMode = new MCH_ConfigPrm("KeyCameraMode", 46);
/* 240 */     KeyUnmount = new MCH_ConfigPrm("KeyUnmountMob", 21);
/* 241 */     KeyFlare = new MCH_ConfigPrm("KeyFlare", 47);
/* 242 */     KeyExtra = new MCH_ConfigPrm("KeyExtra", 33);
/* 243 */     KeyCameraDistUp = new MCH_ConfigPrm("KeyCameraDistanceUp", 201);
/* 244 */     KeyCameraDistDown = new MCH_ConfigPrm("KeyCameraDistanceDown", 209);
/* 245 */     KeyFreeLook = new MCH_ConfigPrm("KeyFreeLook", 29);
/* 246 */     KeyGUI = new MCH_ConfigPrm("KeyGUI", 19);
/* 247 */     KeyGearUpDown = new MCH_ConfigPrm("KeyGearUpDown", 48);
/* 248 */     KeyPutToRack = new MCH_ConfigPrm("KeyPutToRack", 36);
/* 249 */     KeyDownFromRack = new MCH_ConfigPrm("KeyDownFromRack", 22);
/* 250 */     KeyScoreboard = new MCH_ConfigPrm("KeyScoreboard", 38);
/* 251 */     KeyMultiplayManager = new MCH_ConfigPrm("KeyMultiplayManager", 50);
/* 252 */     KeyConfig = new MCH_ConfigPrm[] { KeyUp, KeyDown, KeyRight, KeyLeft, KeySwitchMode, KeySwitchHovering, KeySwitchWeapon1, KeySwitchWeapon2, KeySwWeaponMode, KeyZoom, KeyCameraMode, KeyUnmount, KeyFlare, KeyExtra, KeyCameraDistUp, KeyCameraDistDown, KeyFreeLook, KeyGUI, KeyGearUpDown, KeyPutToRack, KeyDownFromRack, KeyScoreboard, KeyMultiplayManager };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 260 */     DamageVs = new ArrayList<>();
/* 261 */     CommandPermission = new ArrayList<>();
/* 262 */     CommandPermissionList = new ArrayList<>();
/* 263 */     IgnoreBulletHitList = new ArrayList<>();
/* 264 */     IgnoreBulletHitItem = new MCH_ConfigPrm("IgnoreBulletHit", "");
/*     */     
/* 266 */     TestMode = new MCH_ConfigPrm("TestMode", false);
/* 267 */     __TextureAlpha = new MCH_ConfigPrm("__TextureAlphaDebug", 1.0D);
/*     */     
/* 269 */     EnableCommand = new MCH_ConfigPrm("EnableCommand", true);
/* 270 */     PlaceableOnSpongeOnly = new MCH_ConfigPrm("PlaceableOnSpongeOnly", false);
/* 271 */     HideKeybind = new MCH_ConfigPrm("HideKeybind", false);
/* 272 */     ItemDamage = new MCH_ConfigPrm("ItemDamage", true);
/* 273 */     ItemFuel = new MCH_ConfigPrm("ItemFuel", true);
/* 274 */     AutoRepairHP = new MCH_ConfigPrm("AutoRepairHP", 0.5D);
/* 275 */     Collision_DestroyBlock = new MCH_ConfigPrm("Collision_DestroyBlock", true);
/* 276 */     Explosion_DestroyBlock = new MCH_ConfigPrm("Explosion_DestroyBlock", true);
/* 277 */     Explosion_FlamingBlock = new MCH_ConfigPrm("Explosion_FlamingBlock", true);
/* 278 */     Collision_Car_BreakableBlock = new MCH_ConfigPrm("Collision_Car_BreakableBlock", "double_plant, glass_pane,stained_glass_pane");
/*     */ 
/*     */     
/* 281 */     Collision_Car_NoBreakableBlock = new MCH_ConfigPrm("Collision_Car_NoBreakBlock", "torch");
/* 282 */     Collision_Car_BreakableMaterial = new MCH_ConfigPrm("Collision_Car_BreakableMaterial", "cactus, cake, gourd, leaves, vine, plants");
/*     */ 
/*     */     
/* 285 */     Collision_Tank_BreakableBlock = new MCH_ConfigPrm("Collision_Tank_BreakableBlock", "nether_brick_fence");
/* 286 */     Collision_Tank_BreakableBlock.validVer = "1.0.0";
/* 287 */     Collision_Tank_NoBreakableBlock = new MCH_ConfigPrm("Collision_Tank_NoBreakBlock", "torch, glowstone");
/* 288 */     Collision_Tank_BreakableMaterial = new MCH_ConfigPrm("Collision_Tank_BreakableMaterial", "cactus, cake, carpet, circuits, glass, gourd, leaves, vine, wood, plants");
/*     */ 
/*     */     
/* 291 */     Collision_EntityDamage = new MCH_ConfigPrm("Collision_EntityDamage", true);
/* 292 */     Collision_EntityTankDamage = new MCH_ConfigPrm("Collision_EntityTankDamage", false);
/* 293 */     LWeaponAutoFire = new MCH_ConfigPrm("LWeaponAutoFire", false);
/* 294 */     DismountAll = new MCH_ConfigPrm("DismountAll", false);
/* 295 */     MountMinecartHeli = new MCH_ConfigPrm("MountMinecartHeli", true);
/* 296 */     MountMinecartPlane = new MCH_ConfigPrm("MountMinecartPlane", true);
/* 297 */     MountMinecartVehicle = new MCH_ConfigPrm("MountMinecartVehicle", false);
/* 298 */     MountMinecartTank = new MCH_ConfigPrm("MountMinecartTank", true);
/* 299 */     AutoThrottleDownHeli = new MCH_ConfigPrm("AutoThrottleDownHeli", true);
/* 300 */     AutoThrottleDownPlane = new MCH_ConfigPrm("AutoThrottleDownPlane", false);
/* 301 */     AutoThrottleDownTank = new MCH_ConfigPrm("AutoThrottleDownTank", false);
/* 302 */     DisableItemRender = new MCH_ConfigPrm("DisableItemRender", 1);
/* 303 */     DisableItemRender.desc = ";DisableItemRender = 0 ~ 3 (1 = Recommended)";
/* 304 */     RenderDistanceWeight = new MCH_ConfigPrm("RenderDistanceWeight", 10.0D);
/* 305 */     MobRenderDistanceWeight = new MCH_ConfigPrm("MobRenderDistanceWeight", 1.0D);
/* 306 */     CreativeTabIcon = new MCH_ConfigPrm("CreativeTabIconItem", "fuel");
/* 307 */     CreativeTabIconHeli = new MCH_ConfigPrm("CreativeTabIconHeli", "ah-64");
/* 308 */     CreativeTabIconPlane = new MCH_ConfigPrm("CreativeTabIconPlane", "f22a");
/* 309 */     CreativeTabIconTank = new MCH_ConfigPrm("CreativeTabIconTank", "merkava_mk4");
/* 310 */     CreativeTabIconVehicle = new MCH_ConfigPrm("CreativeTabIconVehicle", "mk15");
/* 311 */     DisableShader = new MCH_ConfigPrm("DisableShader", false);
/* 312 */     AliveTimeOfCartridge = new MCH_ConfigPrm("AliveTimeOfCartridge", 200);
/* 313 */     InfinityAmmo = new MCH_ConfigPrm("InfinityAmmo", false);
/* 314 */     InfinityFuel = new MCH_ConfigPrm("InfinityFuel", false);
/* 315 */     HitMarkColor = new MCH_ConfigPrm("HitMarkColor", "255, 255, 0, 0");
/* 316 */     HitMarkColor.desc = ";HitMarkColor = Alpha, Red, Green, Blue";
/* 317 */     SmoothShading = new MCH_ConfigPrm("SmoothShading", true);
/* 318 */     BulletBreakableBlock = new MCH_ConfigPrm("BulletBreakableBlocks", "glass_pane, stained_glass_pane, tallgrass, double_plant, yellow_flower, red_flower, vine, wheat, reeds, waterlily");
/*     */     
/* 320 */     BulletBreakableBlock.validVer = "0.10.4";
/* 321 */     EnableModEntityRender = new MCH_ConfigPrm("EnableModEntityRender", true);
/* 322 */     DisableRenderLivingSpecials = new MCH_ConfigPrm("DisableRenderLivingSpecials", true);
/* 323 */     PreventingBroken = new MCH_ConfigPrm("PreventingBroken", false);
/* 324 */     DropItemInCreativeMode = new MCH_ConfigPrm("DropItemInCreativeMode", false);
/* 325 */     BreakableOnlyPickaxe = new MCH_ConfigPrm("BreakableOnlyPickaxe", false);
/* 326 */     InvertMouse = new MCH_ConfigPrm("InvertMouse", false);
/* 327 */     MouseSensitivity = new MCH_ConfigPrm("MouseSensitivity", 10.0D);
/* 328 */     MouseControlStickModeHeli = new MCH_ConfigPrm("MouseControlStickModeHeli", false);
/* 329 */     MouseControlStickModePlane = new MCH_ConfigPrm("MouseControlStickModePlane", false);
/* 330 */     MouseControlFlightSimMode = new MCH_ConfigPrm("MouseControlFlightSimMode", false);
/* 331 */     MouseControlFlightSimMode.desc = ";MouseControlFlightSimMode = true ( Yaw:key, Roll=mouse )";
/* 332 */     SwitchWeaponWithMouseWheel = new MCH_ConfigPrm("SwitchWeaponWithMouseWheel", true);
/* 333 */     AllHeliSpeed = new MCH_ConfigPrm("AllHeliSpeed", 1.0D);
/* 334 */     AllPlaneSpeed = new MCH_ConfigPrm("AllPlaneSpeed", 1.0D);
/* 335 */     AllTankSpeed = new MCH_ConfigPrm("AllTankSpeed", 1.0D);
/* 336 */     HurtResistantTime = new MCH_ConfigPrm("HurtResistantTime", 0.0D);
/* 337 */     DisplayHUDThirdPerson = new MCH_ConfigPrm("DisplayHUDThirdPerson", false);
/* 338 */     DisableCameraDistChange = new MCH_ConfigPrm("DisableThirdPersonCameraDistChange", false);
/* 339 */     EnableReplaceTextureManager = new MCH_ConfigPrm("EnableReplaceTextureManager", true);
/* 340 */     DisplayEntityMarker = new MCH_ConfigPrm("DisplayEntityMarker", true);
/* 341 */     DisplayMarkThroughWall = new MCH_ConfigPrm("DisplayMarkThroughWall", true);
/* 342 */     EntityMarkerSize = new MCH_ConfigPrm("EntityMarkerSize", 10.0D);
/* 343 */     BlockMarkerSize = new MCH_ConfigPrm("BlockMarkerSize", 10.0D);
/* 344 */     ReplaceRenderViewEntity = new MCH_ConfigPrm("ReplaceRenderViewEntity", true);
/* 345 */     StingerLockRange = new MCH_ConfigPrm("StingerLockRange", 320.0D);
/* 346 */     StingerLockRange.validVer = "1.0.0";
/* 347 */     DefaultExplosionParticle = new MCH_ConfigPrm("DefaultExplosionParticle", false);
/* 348 */     RangeFinderSpotDist = new MCH_ConfigPrm("RangeFinderSpotDist", 400);
/* 349 */     RangeFinderSpotTime = new MCH_ConfigPrm("RangeFinderSpotTime", 15);
/* 350 */     RangeFinderConsume = new MCH_ConfigPrm("RangeFinderConsume", true);
/* 351 */     EnablePutRackInFlying = new MCH_ConfigPrm("EnablePutRackInFlying", true);
/* 352 */     EnableDebugBoundingBox = new MCH_ConfigPrm("EnableDebugBoundingBox", true);
/* 353 */     DespawnCount = new MCH_ConfigPrm("DespawnCount", 25);
/* 354 */     HitBoxDelayTick = new MCH_ConfigPrm("HitBoxDelayTick", 0);
/* 355 */     EnableRotationLimit = new MCH_ConfigPrm("EnableRotationLimit", false);
/* 356 */     PitchLimitMax = new MCH_ConfigPrm("PitchLimitMax", 10);
/* 357 */     PitchLimitMin = new MCH_ConfigPrm("PitchLimitMin", -10);
/* 358 */     RollLimit = new MCH_ConfigPrm("RollLimit", 35);
/* 359 */     RangeOfGunner_VsMonster_Horizontal = new MCH_ConfigPrm("RangeOfGunner_VsMonster_Horizontal", 80);
/* 360 */     RangeOfGunner_VsMonster_Vertical = new MCH_ConfigPrm("RangeOfGunner_VsMonster_Vertical", 160);
/* 361 */     RangeOfGunner_VsPlayer_Horizontal = new MCH_ConfigPrm("RangeOfGunner_VsPlayer_Horizontal", 200);
/* 362 */     RangeOfGunner_VsPlayer_Vertical = new MCH_ConfigPrm("RangeOfGunner_VsPlayer_Vertical", 300);
/* 363 */     FixVehicleAtPlacedPoint = new MCH_ConfigPrm("FixVehicleAtPlacedPoint", true);
/* 364 */     KillPassengersWhenDestroyed = new MCH_ConfigPrm("KillPassengersWhenDestroyed", false);
/*     */     
/* 366 */     hitMarkColorAlpha = 1.0F;
/* 367 */     hitMarkColorRGB = 16711680;
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
/*     */ 
/*     */ 
/*     */     
/* 392 */     ItemID_GLTD = new MCH_ConfigPrm("ItemID_GLTD", 28799);
/* 393 */     ItemID_Chain = new MCH_ConfigPrm("ItemID_Chain", 28798);
/* 394 */     ItemID_Parachute = new MCH_ConfigPrm("ItemID_Parachute", 28797);
/* 395 */     ItemID_Container = new MCH_ConfigPrm("ItemID_Container", 28796);
/* 396 */     ItemID_UavStation = new MCH_ConfigPrm[] { new MCH_ConfigPrm("ItemID_UavStation", 28795), new MCH_ConfigPrm("ItemID_UavStation2", 28790) };
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 401 */     ItemID_InvisibleItem = new MCH_ConfigPrm("ItemID_Internal", 28794);
/* 402 */     ItemID_Fuel = new MCH_ConfigPrm("ItemID_Fuel", 28793);
/* 403 */     ItemID_DraftingTable = new MCH_ConfigPrm("ItemID_DraftingTable", 28792);
/* 404 */     ItemID_Wrench = new MCH_ConfigPrm("ItemID_Wrench", 28791);
/* 405 */     ItemID_RangeFinder = new MCH_ConfigPrm("ItemID_RangeFinder", 28789);
/* 406 */     ItemID_Stinger = new MCH_ConfigPrm("ItemID_Stinger", 28900);
/* 407 */     ItemID_StingerMissile = new MCH_ConfigPrm("ItemID_StingerMissile", 28901);
/*     */     
/* 409 */     BlockID_DraftingTableOFF = new MCH_ConfigPrm("BlockID_DraftingTable", 3450);
/* 410 */     BlockID_DraftingTableON = new MCH_ConfigPrm("BlockID_DraftingTableON", 3451);
/*     */     
/* 412 */     General = new MCH_ConfigPrm[] { TestMode, __TextureAlpha, EnableCommand, null, PlaceableOnSpongeOnly, ItemDamage, ItemFuel, AutoRepairHP, Explosion_DestroyBlock, Explosion_FlamingBlock, BulletBreakableBlock, Collision_DestroyBlock, Collision_Car_BreakableBlock, Collision_Car_BreakableMaterial, Collision_Tank_BreakableBlock, Collision_Tank_BreakableMaterial, Collision_EntityDamage, Collision_EntityTankDamage, InfinityAmmo, InfinityFuel, DismountAll, MountMinecartHeli, MountMinecartPlane, MountMinecartVehicle, MountMinecartTank, PreventingBroken, DropItemInCreativeMode, BreakableOnlyPickaxe, AllHeliSpeed, AllPlaneSpeed, AllTankSpeed, HurtResistantTime, StingerLockRange, RangeFinderSpotDist, RangeFinderSpotTime, RangeFinderConsume, EnablePutRackInFlying, EnableDebugBoundingBox, DespawnCount, HitBoxDelayTick, EnableRotationLimit, PitchLimitMax, PitchLimitMin, RollLimit, RangeOfGunner_VsMonster_Horizontal, RangeOfGunner_VsMonster_Vertical, RangeOfGunner_VsPlayer_Horizontal, RangeOfGunner_VsPlayer_Vertical, FixVehicleAtPlacedPoint, KillPassengersWhenDestroyed, null, InvertMouse, MouseSensitivity, MouseControlStickModeHeli, MouseControlStickModePlane, MouseControlFlightSimMode, AutoThrottleDownHeli, AutoThrottleDownPlane, AutoThrottleDownTank, SwitchWeaponWithMouseWheel, LWeaponAutoFire, DisableItemRender, HideKeybind, RenderDistanceWeight, MobRenderDistanceWeight, CreativeTabIcon, CreativeTabIconHeli, CreativeTabIconPlane, CreativeTabIconTank, CreativeTabIconVehicle, DisableShader, DefaultExplosionParticle, AliveTimeOfCartridge, HitMarkColor, SmoothShading, EnableModEntityRender, DisableRenderLivingSpecials, DisplayHUDThirdPerson, DisableCameraDistChange, EnableReplaceTextureManager, DisplayEntityMarker, EntityMarkerSize, BlockMarkerSize, ReplaceRenderViewEntity, null };
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 442 */     DamageVsEntity = new DamageFactor(this, "DamageVsEntity");
/* 443 */     DamageVsLiving = new DamageFactor(this, "DamageVsLiving");
/* 444 */     DamageVsPlayer = new DamageFactor(this, "DamageVsPlayer");
/* 445 */     DamageVsMCHeliAircraft = new DamageFactor(this, "DamageVsMCHeliAircraft");
/* 446 */     DamageVsMCHeliTank = new DamageFactor(this, "DamageVsMCHeliTank");
/* 447 */     DamageVsMCHeliVehicle = new DamageFactor(this, "DamageVsMCHeliVehicle");
/* 448 */     DamageVsMCHeliOther = new DamageFactor(this, "DamageVsMCHeliOther");
/* 449 */     DamageAircraftByExternal = new DamageFactor(this, "DamageMCHeliAircraftByExternal");
/* 450 */     DamageTankByExternal = new DamageFactor(this, "DamageMCHeliTankByExternal");
/* 451 */     DamageVehicleByExternal = new DamageFactor(this, "DamageMCHeliVehicleByExternal");
/* 452 */     DamageOtherByExternal = new DamageFactor(this, "DamageMCHeliOtherByExternal");
/* 453 */     DamageFactorList = new DamageFactor[] { DamageVsEntity, DamageVsLiving, DamageVsPlayer, DamageVsMCHeliAircraft, DamageVsMCHeliTank, DamageVsMCHeliVehicle, DamageVsMCHeliOther, DamageAircraftByExternal, DamageTankByExternal, DamageVehicleByExternal, DamageOtherByExternal };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBlockListFromString(List<Block> list, String str) {
/* 463 */     list.clear();
/* 464 */     String[] s = str.split("\\s*,\\s*");
/* 465 */     for (String blockName : s) {
/*     */       
/* 467 */       Block b = W_Block.getBlockFromName(blockName);
/* 468 */       if (b != null)
/*     */       {
/* 470 */         list.add(b);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setMaterialListFromString(List<Material> list, String str) {
/* 477 */     list.clear();
/* 478 */     String[] s = str.split("\\s*,\\s*");
/* 479 */     for (String name : s) {
/*     */       
/* 481 */       Material m = MCH_Lib.getMaterialFromName(name);
/* 482 */       if (m != null)
/*     */       {
/* 484 */         list.add(m);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void correctionParameter() {
/* 491 */     String[] s = HitMarkColor.prmString.split("\\s*,\\s*");
/* 492 */     if (s.length == 4) {
/*     */       
/* 494 */       hitMarkColorAlpha = toInt255(s[0]) / 255.0F;
/* 495 */       hitMarkColorRGB = toInt255(s[1]) << 16 | toInt255(s[2]) << 8 | toInt255(s[3]);
/*     */     } 
/*     */     
/* 498 */     AllHeliSpeed.prmDouble = MCH_Lib.RNG(AllHeliSpeed.prmDouble, 0.0D, 1000.0D);
/* 499 */     AllPlaneSpeed.prmDouble = MCH_Lib.RNG(AllPlaneSpeed.prmDouble, 0.0D, 1000.0D);
/* 500 */     AllTankSpeed.prmDouble = MCH_Lib.RNG(AllTankSpeed.prmDouble, 0.0D, 1000.0D);
/*     */     
/* 502 */     setBlockListFromString(bulletBreakableBlocks, BulletBreakableBlock.prmString);
/* 503 */     setBlockListFromString(carBreakableBlocks, Collision_Car_BreakableBlock.prmString);
/* 504 */     setBlockListFromString(carNoBreakableBlocks, Collision_Car_NoBreakableBlock.prmString);
/* 505 */     setMaterialListFromString(carBreakableMaterials, Collision_Car_BreakableMaterial.prmString);
/* 506 */     setBlockListFromString(tankBreakableBlocks, Collision_Tank_BreakableBlock.prmString);
/* 507 */     setBlockListFromString(tankNoBreakableBlocks, Collision_Tank_NoBreakableBlock.prmString);
/* 508 */     setMaterialListFromString(tankBreakableMaterials, Collision_Tank_BreakableMaterial.prmString);
/*     */     
/* 510 */     if (EntityMarkerSize.prmDouble < 0.0D)
/*     */     {
/* 512 */       EntityMarkerSize.prmDouble = 0.0D;
/*     */     }
/* 514 */     if (BlockMarkerSize.prmDouble < 0.0D)
/*     */     {
/* 516 */       BlockMarkerSize.prmDouble = 0.0D;
/*     */     }
/*     */     
/* 519 */     if (HurtResistantTime.prmDouble < 0.0D)
/*     */     {
/* 521 */       HurtResistantTime.prmDouble = 0.0D;
/*     */     }
/* 523 */     if (HurtResistantTime.prmDouble > 10000.0D)
/*     */     {
/* 525 */       HurtResistantTime.prmDouble = 10000.0D;
/*     */     }
/*     */     
/* 528 */     if (MobRenderDistanceWeight.prmDouble < 0.1D) {
/*     */       
/* 530 */       MobRenderDistanceWeight.prmDouble = 0.1D;
/*     */     }
/* 532 */     else if (MobRenderDistanceWeight.prmDouble > 10.0D) {
/*     */       
/* 534 */       MobRenderDistanceWeight.prmDouble = 10.0D;
/*     */     } 
/*     */     
/* 537 */     for (MCH_ConfigPrm p : CommandPermission) {
/*     */       
/* 539 */       CommandPermission cpm = new CommandPermission(this, p.prmString);
/* 540 */       if (!cpm.name.isEmpty())
/*     */       {
/* 542 */         CommandPermissionList.add(cpm);
/*     */       }
/*     */     } 
/*     */     
/* 546 */     if (IgnoreBulletHitList.size() <= 0) {
/*     */       
/* 548 */       IgnoreBulletHitList.add("flansmod.common.guns.EntityBullet");
/* 549 */       IgnoreBulletHitList.add("flansmod.common.guns.EntityGrenade");
/*     */     } 
/*     */     
/* 552 */     boolean isNoDamageVsSetting = (DamageVs.size() <= 0);
/*     */     
/* 554 */     for (MCH_ConfigPrm p : DamageVs) {
/*     */       
/* 556 */       for (DamageFactor df : DamageFactorList) {
/*     */         
/* 558 */         if (p.name.equals(df.itemName))
/*     */         {
/* 560 */           df.list.add(newDamageEntity(p.prmString));
/*     */         }
/*     */       } 
/*     */     } 
/* 564 */     for (DamageFactor df : DamageFactorList) {
/*     */       
/* 566 */       if (df.list.size() <= 0) {
/*     */         
/* 568 */         DamageVs.add(new MCH_ConfigPrm(df.itemName, "1.0"));
/*     */       }
/*     */       else {
/*     */         
/* 572 */         boolean foundCommon = false;
/* 573 */         for (DamageEntity n : df.list) {
/*     */           
/* 575 */           if (n.name.isEmpty()) {
/*     */             
/* 577 */             foundCommon = true;
/*     */             break;
/*     */           } 
/*     */         } 
/* 581 */         if (!foundCommon)
/*     */         {
/* 583 */           DamageVs.add(new MCH_ConfigPrm(df.itemName, "1.0"));
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 588 */     if (DespawnCount.prmInt <= 0)
/*     */     {
/* 590 */       DespawnCount.prmInt = 1;
/*     */     }
/*     */     
/* 593 */     if (HitBoxDelayTick.prmInt < 0)
/*     */     {
/* 595 */       HitBoxDelayTick.prmInt = 0;
/*     */     }
/* 597 */     if (HitBoxDelayTick.prmInt > 50)
/*     */     {
/* 599 */       HitBoxDelayTick.prmInt = 50;
/*     */     }
/*     */     
/* 602 */     PitchLimitMax.prmInt = (PitchLimitMax.prmInt < 0) ? 0 : ((PitchLimitMax.prmInt > 80) ? 80 : PitchLimitMax.prmInt);
/*     */     
/* 604 */     PitchLimitMin.prmInt = (PitchLimitMin.prmInt > 0) ? 0 : ((PitchLimitMin.prmInt < -80) ? -80 : PitchLimitMin.prmInt);
/*     */     
/* 606 */     RollLimit.prmInt = (RollLimit.prmInt < 0) ? 0 : ((RollLimit.prmInt > 80) ? 80 : RollLimit.prmInt);
/*     */     
/* 608 */     if (isNoDamageVsSetting) {
/*     */       
/* 610 */       DamageVs.add(new MCH_ConfigPrm("DamageVsEntity", "3.0, flansmod"));
/* 611 */       DamageVs.add(new MCH_ConfigPrm("DamageMCHeliAircraftByExternal", "0.5, flansmod"));
/* 612 */       DamageVs.add(new MCH_ConfigPrm("DamageMCHeliVehicleByExternal", "0.5, flansmod"));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public DamageEntity newDamageEntity(String s) {
/* 618 */     String[] splt = s.split("\\s*,\\s*");
/* 619 */     if (splt.length == 1)
/*     */     {
/* 621 */       return new DamageEntity(this, Double.parseDouble(splt[0]), "");
/*     */     }
/* 623 */     if (splt.length == 2)
/*     */     {
/* 625 */       return new DamageEntity(this, Double.parseDouble(splt[0]), splt[1]);
/*     */     }
/* 627 */     return new DamageEntity(this, 1.0D, "");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static float applyDamageByExternal(Entity target, DamageSource ds, float damage) {
/*     */     List<DamageEntity> list;
/* 634 */     if (target instanceof mcheli.helicopter.MCH_EntityHeli || target instanceof mcheli.plane.MCP_EntityPlane) {
/*     */       
/* 636 */       list = DamageAircraftByExternal.list;
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 641 */     else if (target instanceof mcheli.tank.MCH_EntityTank) {
/*     */       
/* 643 */       list = DamageTankByExternal.list;
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 648 */     else if (target instanceof mcheli.vehicle.MCH_EntityVehicle) {
/*     */       
/* 650 */       list = DamageVehicleByExternal.list;
/*     */     }
/*     */     else {
/*     */       
/* 654 */       list = DamageOtherByExternal.list;
/*     */     } 
/*     */ 
/*     */     
/* 658 */     Entity attacker = ds.func_76346_g();
/* 659 */     Entity attackerSource = ds.func_76364_f();
/* 660 */     for (DamageEntity de : list) {
/*     */       
/* 662 */       if (de.name.isEmpty() || (attacker != null && attacker.getClass().toString().indexOf(de.name) > 0) || (attackerSource != null && attackerSource
/* 663 */         .getClass().toString().indexOf(de.name) > 0))
/*     */       {
/* 665 */         damage = (float)(damage * de.factor);
/*     */       }
/*     */     } 
/* 668 */     return damage;
/*     */   }
/*     */   
/*     */   public static float applyDamageVsEntity(Entity target, DamageSource ds, float damage) {
/*     */     List<DamageEntity> list;
/* 673 */     if (target == null)
/*     */     {
/* 675 */       return damage;
/*     */     }
/* 677 */     String targetName = target.getClass().toString();
/*     */ 
/*     */     
/* 680 */     if (target instanceof mcheli.helicopter.MCH_EntityHeli || target instanceof mcheli.plane.MCP_EntityPlane) {
/*     */       
/* 682 */       list = DamageVsMCHeliAircraft.list;
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 687 */     else if (target instanceof mcheli.tank.MCH_EntityTank) {
/*     */       
/* 689 */       list = DamageVsMCHeliTank.list;
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 694 */     else if (target instanceof mcheli.vehicle.MCH_EntityVehicle) {
/*     */       
/* 696 */       list = DamageVsMCHeliVehicle.list;
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 701 */     else if (targetName.indexOf("mcheli.") > 0) {
/*     */       
/* 703 */       list = DamageVsMCHeliOther.list;
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 708 */     else if (target instanceof net.minecraft.entity.player.EntityPlayer) {
/*     */       
/* 710 */       list = DamageVsPlayer.list;
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 715 */     else if (target instanceof net.minecraft.entity.EntityLivingBase) {
/*     */       
/* 717 */       list = DamageVsLiving.list;
/*     */     }
/*     */     else {
/*     */       
/* 721 */       list = DamageVsEntity.list;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 729 */     for (DamageEntity de : list) {
/*     */       
/* 731 */       if (de.name.isEmpty() || targetName.indexOf(de.name) > 0)
/*     */       {
/* 733 */         damage = (float)(damage * de.factor);
/*     */       }
/*     */     } 
/*     */     
/* 737 */     return damage;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static List<Block> getBreakableBlockListFromType(int n) {
/* 743 */     if (n == 2)
/* 744 */       return tankBreakableBlocks; 
/* 745 */     if (n == 1)
/* 746 */       return carBreakableBlocks; 
/* 747 */     return dummyBreakableBlocks;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static List<Block> getNoBreakableBlockListFromType(int n) {
/* 753 */     if (n == 2)
/* 754 */       return tankNoBreakableBlocks; 
/* 755 */     if (n == 1)
/* 756 */       return carNoBreakableBlocks; 
/* 757 */     return dummyBreakableBlocks;
/*     */   }
/*     */ 
/*     */   
/*     */   public static List<Material> getBreakableMaterialListFromType(int n) {
/* 762 */     if (n == 2)
/* 763 */       return tankBreakableMaterials; 
/* 764 */     if (n == 1)
/* 765 */       return carBreakableMaterials; 
/* 766 */     return dummyBreakableMaterials;
/*     */   }
/*     */ 
/*     */   
/*     */   public int toInt255(String s) {
/* 771 */     int a = Integer.valueOf(s).intValue();
/* 772 */     return (a > 255) ? 255 : ((a < 0) ? 0 : a);
/*     */   }
/*     */ 
/*     */   
/*     */   public void load() {
/* 777 */     MCH_InputFile file = new MCH_InputFile();
/* 778 */     if (file.open(configFilePath)) {
/*     */       
/* 780 */       String str = file.readLine();
/* 781 */       while (str != null) {
/*     */         
/* 783 */         if (str.trim().equalsIgnoreCase("McHeliOutputDebugLog")) {
/*     */           
/* 785 */           DebugLog = true;
/*     */         }
/*     */         else {
/*     */           
/* 789 */           readConfigData(str);
/*     */         } 
/* 791 */         str = file.readLine();
/*     */       } 
/*     */       
/* 794 */       file.close();
/*     */       
/* 796 */       MCH_Lib.Log("loaded " + file.file.getAbsolutePath(), new Object[0]);
/*     */     }
/*     */     else {
/*     */       
/* 800 */       MCH_Lib.Log("" + (new File(configFilePath)).getAbsolutePath() + " not found.", new Object[0]);
/*     */     } 
/*     */     
/* 803 */     correctionParameter();
/*     */   }
/*     */ 
/*     */   
/*     */   private void readConfigData(String str) {
/* 808 */     String[] s = str.split("=");
/*     */     
/* 810 */     if (s.length != 2)
/*     */       return; 
/* 812 */     s[0] = s[0].trim();
/* 813 */     s[1] = s[1].trim();
/*     */     
/* 815 */     if (s[0].equalsIgnoreCase("MOD_Version")) {
/*     */       
/* 817 */       configVer = s[1];
/*     */       
/*     */       return;
/*     */     } 
/* 821 */     if (s[0].equalsIgnoreCase("CommandPermission"))
/*     */     {
/* 823 */       CommandPermission.add(new MCH_ConfigPrm("CommandPermission", s[1]));
/*     */     }
/*     */     
/* 826 */     for (DamageFactor item : DamageFactorList) {
/*     */       
/* 828 */       if (item.itemName.equalsIgnoreCase(s[0]))
/*     */       {
/* 830 */         DamageVs.add(new MCH_ConfigPrm(item.itemName, s[1]));
/*     */       }
/*     */     } 
/*     */     
/* 834 */     if (IgnoreBulletHitItem.compare(s[0]))
/*     */     {
/* 836 */       IgnoreBulletHitList.add(s[1]);
/*     */     }
/*     */     
/* 839 */     for (MCH_ConfigPrm p : KeyConfig) {
/*     */       
/* 841 */       if (p != null && p.compare(s[0]) && p.isValidVer(configVer)) {
/*     */         
/* 843 */         p.setPrm(s[1]);
/*     */         return;
/*     */       } 
/*     */     } 
/* 847 */     for (MCH_ConfigPrm p : General) {
/*     */       
/* 849 */       if (p != null && p.compare(s[0]) && p.isValidVer(configVer)) {
/*     */         
/* 851 */         p.setPrm(s[1]);
/*     */         return;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void write() {
/* 859 */     MCH_OutputFile file = new MCH_OutputFile();
/* 860 */     if (file.open(configFilePath)) {
/*     */       
/* 862 */       writeConfigData(file.pw);
/* 863 */       file.close();
/* 864 */       MCH_Lib.Log("update " + file.file.getAbsolutePath(), new Object[0]);
/*     */     }
/*     */     else {
/*     */       
/* 868 */       MCH_Lib.Log("" + (new File(configFilePath)).getAbsolutePath() + " cannot open.", new Object[0]);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void writeConfigData(PrintWriter pw) {
/* 874 */     pw.println("[General]");
/* 875 */     pw.println("MOD_Name = mcheli");
/* 876 */     pw.println("MOD_Version = " + MCH_MOD.VER);
/* 877 */     pw.println("MOD_MC_Version = 1.12.2");
/* 878 */     pw.println();
/*     */     
/* 880 */     if (DebugLog) {
/*     */       
/* 882 */       pw.println("McHeliOutputDebugLog");
/* 883 */       pw.println();
/*     */     } 
/*     */     
/* 886 */     for (MCH_ConfigPrm p : General) {
/*     */       
/* 888 */       if (p != null) {
/*     */         
/* 890 */         if (!p.desc.isEmpty())
/* 891 */           pw.println(p.desc); 
/* 892 */         pw.println(p.name + " = " + p);
/*     */       }
/*     */       else {
/*     */         
/* 896 */         pw.println("");
/*     */       } 
/*     */     } 
/* 899 */     pw.println();
/*     */     
/* 901 */     for (MCH_ConfigPrm p : DamageVs)
/*     */     {
/* 903 */       pw.println(p.name + " = " + p);
/*     */     }
/* 905 */     pw.println();
/*     */     
/* 907 */     for (String s : IgnoreBulletHitList)
/*     */     {
/* 909 */       pw.println(IgnoreBulletHitItem.name + " = " + s);
/*     */     }
/* 911 */     pw.println();
/*     */     
/* 913 */     pw.println(";CommandPermission = commandName(eg, modlist, status, fill...):playerName1, playerName2, playerName3...");
/*     */     
/* 915 */     if (CommandPermission.size() == 0) {
/*     */       
/* 917 */       pw.println(";CommandPermission = modlist :example1, example2");
/* 918 */       pw.println(";CommandPermission = status :  example2");
/*     */     } 
/* 920 */     for (MCH_ConfigPrm p : CommandPermission)
/*     */     {
/* 922 */       pw.println(p.name + " = " + p);
/*     */     }
/* 924 */     pw.println();
/*     */     
/* 926 */     pw.println();
/* 927 */     pw.println("[Key config]");
/* 928 */     pw.println("http://minecraft.gamepedia.com/Key_codes");
/* 929 */     pw.println();
/*     */     
/* 931 */     for (MCH_ConfigPrm p : KeyConfig)
/*     */     {
/* 933 */       pw.println(p.name + " = " + p);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public class CommandPermission
/*     */   {
/*     */     public final String name;
/*     */     
/*     */     public final String[] players;
/*     */     
/*     */     public CommandPermission(MCH_Config arg1, String param) {
/* 945 */       String[] s = param.split(":");
/* 946 */       if (s.length == 2) {
/*     */         
/* 948 */         this.name = s[0].toLowerCase().trim();
/* 949 */         this.players = s[1].trim().split("\\s*,\\s*");
/*     */       }
/*     */       else {
/*     */         
/* 953 */         this.name = "";
/* 954 */         this.players = new String[0];
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   class DamageEntity
/*     */   {
/*     */     public final double factor;
/*     */     
/*     */     public final String name;
/*     */     
/*     */     public DamageEntity(MCH_Config paramMCH_Config, double factor, String name) {
/* 967 */       this.factor = factor;
/* 968 */       this.name = name;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   class DamageFactor
/*     */   {
/*     */     public final String itemName;
/*     */     public List<MCH_Config.DamageEntity> list;
/*     */     
/*     */     public DamageFactor(MCH_Config paramMCH_Config, String itemName) {
/* 979 */       this.itemName = itemName;
/* 980 */       this.list = new ArrayList<>();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\MCH_Config.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */