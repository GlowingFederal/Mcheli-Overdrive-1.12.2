/*      */ package mcheli.aircraft;
/*      */ 
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collections;
/*      */ import java.util.Comparator;
/*      */ import java.util.HashMap;
/*      */ import java.util.List;
/*      */ import mcheli.MCH_BaseInfo;
/*      */ import mcheli.MCH_MOD;
/*      */ import mcheli.__helper.addon.AddonResourceLocation;
/*      */ import mcheli.__helper.client._IModelCustom;
/*      */ import mcheli.__helper.info.IItemContent;
/*      */ import mcheli.hud.MCH_Hud;
/*      */ import mcheli.hud.MCH_HudManager;
/*      */ import mcheli.weapon.MCH_WeaponInfoManager;
/*      */ import net.minecraft.item.ItemStack;
/*      */ import net.minecraft.item.crafting.IRecipe;
/*      */ import net.minecraft.util.math.MathHelper;
/*      */ import net.minecraft.util.math.Vec3d;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class MCH_AircraftInfo
/*      */   extends MCH_BaseInfo
/*      */   implements IItemContent
/*      */ {
/*      */   public final String name;
/*      */   public String displayName;
/*      */   public HashMap<String, String> displayNameLang;
/*      */   public int itemID;
/*      */   public List<String> recipeString;
/*      */   public List<IRecipe> recipe;
/*      */   public boolean isShapedRecipe;
/*      */   public String category;
/*      */   public boolean creativeOnly;
/*      */   public boolean invulnerable;
/*      */   public boolean isEnableGunnerMode;
/*      */   public int cameraZoom;
/*      */   public boolean isEnableConcurrentGunnerMode;
/*      */   public boolean isEnableNightVision;
/*      */   public boolean isEnableEntityRadar;
/*      */   public boolean isEnableEjectionSeat;
/*      */   public boolean isEnableParachuting;
/*      */   public Flare flare;
/*      */   public float bodyHeight;
/*      */   public float bodyWidth;
/*      */   public boolean isFloat;
/*      */   public float floatOffset;
/*      */   public float gravity;
/*      */   public float gravityInWater;
/*      */   public int maxHp;
/*      */   public float armorMinDamage;
/*      */   public float armorMaxDamage;
/*      */   public float armorDamageFactor;
/*      */   public boolean enableBack;
/*      */   public int inventorySize;
/*      */   public boolean isUAV;
/*      */   public boolean isSmallUAV;
/*      */   public boolean isTargetDrone;
/*      */   public float autoPilotRot;
/*      */   public float onGroundPitch;
/*      */   public boolean canMoveOnGround;
/*      */   public boolean canRotOnGround;
/*      */   public List<WeaponSet> weaponSetList;
/*      */   public List<MCH_SeatInfo> seatList;
/*      */   public List<Integer[]> exclusionSeatList;
/*      */   public List<MCH_Hud> hudList;
/*      */   public MCH_Hud hudTvMissile;
/*      */   public float damageFactor;
/*      */   public float submergedDamageHeight;
/*      */   public boolean regeneration;
/*      */   public List<MCH_BoundingBox> extraBoundingBox;
/*      */   public List<Wheel> wheels;
/*      */   public int maxFuel;
/*      */   public float fuelConsumption;
/*      */   public float fuelSupplyRange;
/*      */   public float ammoSupplyRange;
/*      */   public float repairOtherVehiclesRange;
/*      */   public int repairOtherVehiclesValue;
/*      */   public float stealth;
/*      */   public boolean canRide;
/*      */   public float entityWidth;
/*      */   public float entityHeight;
/*      */   public float entityPitch;
/*      */   public float entityRoll;
/*      */   public float stepHeight;
/*      */   public List<MCH_SeatRackInfo> entityRackList;
/*      */   public int mobSeatNum;
/*      */   public int entityRackNum;
/*      */   public MCH_MobDropOption mobDropOption;
/*      */   public List<RepellingHook> repellingHooks;
/*      */   public List<RideRack> rideRacks;
/*      */   public List<ParticleSplash> particleSplashs;
/*      */   public List<SearchLight> searchLights;
/*      */   public float rotorSpeed;
/*      */   public boolean enableSeaSurfaceParticle;
/*      */   public float pivotTurnThrottle;
/*      */   public float trackRollerRot;
/*      */   public float partWheelRot;
/*      */   public float onGroundPitchFactor;
/*      */   public float onGroundRollFactor;
/*      */   public Vec3d turretPosition;
/*      */   public boolean defaultFreelook;
/*      */   public Vec3d unmountPosition;
/*      */   public float thirdPersonDist;
/*      */   public float markerWidth;
/*      */   public float markerHeight;
/*      */   public float bbZmin;
/*      */   public float bbZmax;
/*      */   public float bbZ;
/*      */   public boolean alwaysCameraView;
/*      */   public List<CameraPosition> cameraPosition;
/*      */   public float cameraRotationSpeed;
/*      */   public float speed;
/*      */   public float motionFactor;
/*      */   public float mobilityYaw;
/*      */   public float mobilityPitch;
/*      */   public float mobilityRoll;
/*      */   public float mobilityYawOnGround;
/*      */   public float minRotationPitch;
/*      */   public float maxRotationPitch;
/*      */   public float minRotationRoll;
/*      */   public float maxRotationRoll;
/*      */   public boolean limitRotation;
/*      */   public float throttleUpDown;
/*      */   public float throttleUpDownOnEntity;
/*      */   private List<String> textureNameList;
/*      */   public int textureCount;
/*      */   public float particlesScale;
/*      */   public boolean hideEntity;
/*      */   public boolean smoothShading;
/*      */   public String soundMove;
/*      */   public float soundRange;
/*      */   public float soundVolume;
/*      */   public float soundPitch;
/*      */   public _IModelCustom model;
/*      */   public List<Hatch> hatchList;
/*      */   public List<Camera> cameraList;
/*      */   public List<PartWeapon> partWeapon;
/*      */   public List<WeaponBay> partWeaponBay;
/*      */   public List<Canopy> canopyList;
/*      */   public List<LandingGear> landingGear;
/*      */   public List<Throttle> partThrottle;
/*      */   public List<RotPart> partRotPart;
/*      */   public List<CrawlerTrack> partCrawlerTrack;
/*      */   public List<TrackRoller> partTrackRoller;
/*      */   public List<PartWheel> partWheel;
/*      */   public List<PartWheel> partSteeringWheel;
/*      */   public List<Hatch> lightHatchList;
/*  154 */   private String lastWeaponType = "";
/*  155 */   private int lastWeaponIndex = -1;
/*      */ 
/*      */   
/*      */   private PartWeapon lastWeaponPart;
/*      */ 
/*      */ 
/*      */   
/*      */   public ItemStack getItemStack() {
/*  163 */     return new ItemStack(getItem());
/*      */   }
/*      */ 
/*      */   
/*      */   public abstract String getDirectoryName();
/*      */ 
/*      */   
/*      */   public abstract String getKindName();
/*      */   
/*      */   public MCH_AircraftInfo(AddonResourceLocation location, String path) {
/*  173 */     super(location, path);
/*      */     
/*  175 */     this.name = location.func_110623_a();
/*  176 */     this.displayName = this.name;
/*  177 */     this.displayNameLang = new HashMap<>();
/*  178 */     this.itemID = 0;
/*  179 */     this.recipeString = new ArrayList<>();
/*  180 */     this.recipe = new ArrayList<>();
/*  181 */     this.isShapedRecipe = true;
/*  182 */     this.category = "zzz";
/*      */     
/*  184 */     this.creativeOnly = false;
/*  185 */     this.invulnerable = false;
/*  186 */     this.isEnableGunnerMode = false;
/*  187 */     this.isEnableConcurrentGunnerMode = false;
/*  188 */     this.isEnableNightVision = false;
/*  189 */     this.isEnableEntityRadar = false;
/*  190 */     this.isEnableEjectionSeat = false;
/*  191 */     this.isEnableParachuting = false;
/*  192 */     this.flare = new Flare(this);
/*  193 */     this.weaponSetList = new ArrayList<>();
/*  194 */     this.seatList = new ArrayList<>();
/*  195 */     this.exclusionSeatList = (List)new ArrayList<>();
/*  196 */     this.hudList = new ArrayList<>();
/*  197 */     this.hudTvMissile = null;
/*  198 */     this.bodyHeight = 0.7F;
/*  199 */     this.bodyWidth = 2.0F;
/*  200 */     this.isFloat = false;
/*  201 */     this.floatOffset = 0.0F;
/*  202 */     this.gravity = -0.04F;
/*  203 */     this.gravityInWater = -0.04F;
/*  204 */     this.maxHp = 50;
/*  205 */     this.damageFactor = 0.2F;
/*  206 */     this.submergedDamageHeight = 0.0F;
/*  207 */     this.inventorySize = 0;
/*  208 */     this.armorDamageFactor = 1.0F;
/*  209 */     this.armorMaxDamage = 100000.0F;
/*  210 */     this.armorMinDamage = 0.0F;
/*  211 */     this.enableBack = false;
/*  212 */     this.isUAV = false;
/*  213 */     this.isSmallUAV = false;
/*  214 */     this.isTargetDrone = false;
/*  215 */     this.autoPilotRot = -0.6F;
/*  216 */     this.regeneration = false;
/*  217 */     this.onGroundPitch = 0.0F;
/*  218 */     this.canMoveOnGround = true;
/*  219 */     this.canRotOnGround = true;
/*  220 */     this.cameraZoom = getDefaultMaxZoom();
/*  221 */     this.extraBoundingBox = new ArrayList<>();
/*  222 */     this.maxFuel = 0;
/*  223 */     this.fuelConsumption = 1.0F;
/*  224 */     this.fuelSupplyRange = 0.0F;
/*  225 */     this.ammoSupplyRange = 0.0F;
/*  226 */     this.repairOtherVehiclesRange = 0.0F;
/*  227 */     this.repairOtherVehiclesValue = 10;
/*  228 */     this.stealth = 0.0F;
/*  229 */     this.canRide = true;
/*  230 */     this.entityWidth = 1.0F;
/*  231 */     this.entityHeight = 1.0F;
/*  232 */     this.entityPitch = 0.0F;
/*  233 */     this.entityRoll = 0.0F;
/*  234 */     this.stepHeight = getDefaultStepHeight();
/*  235 */     this.entityRackList = new ArrayList<>();
/*  236 */     this.mobSeatNum = 0;
/*  237 */     this.entityRackNum = 0;
/*  238 */     this.mobDropOption = new MCH_MobDropOption();
/*  239 */     this.repellingHooks = new ArrayList<>();
/*  240 */     this.rideRacks = new ArrayList<>();
/*  241 */     this.particleSplashs = new ArrayList<>();
/*  242 */     this.searchLights = new ArrayList<>();
/*  243 */     this.markerHeight = 1.0F;
/*  244 */     this.markerWidth = 2.0F;
/*  245 */     this.bbZmax = 1.0F;
/*  246 */     this.bbZmin = -1.0F;
/*  247 */     this.rotorSpeed = getDefaultRotorSpeed();
/*  248 */     this.wheels = getDefaultWheelList();
/*  249 */     this.onGroundPitchFactor = 0.0F;
/*  250 */     this.onGroundRollFactor = 0.0F;
/*  251 */     this.turretPosition = new Vec3d(0.0D, 0.0D, 0.0D);
/*  252 */     this.defaultFreelook = false;
/*  253 */     this.unmountPosition = null;
/*  254 */     this.thirdPersonDist = 4.0F;
/*      */     
/*  256 */     this.cameraPosition = new ArrayList<>();
/*  257 */     this.alwaysCameraView = false;
/*  258 */     this.cameraRotationSpeed = 100.0F;
/*      */     
/*  260 */     this.speed = 0.1F;
/*  261 */     this.motionFactor = 0.96F;
/*  262 */     this.mobilityYaw = 1.0F;
/*  263 */     this.mobilityPitch = 1.0F;
/*  264 */     this.mobilityRoll = 1.0F;
/*  265 */     this.mobilityYawOnGround = 1.0F;
/*  266 */     this.minRotationPitch = getMinRotationPitch();
/*  267 */     this.maxRotationPitch = getMaxRotationPitch();
/*  268 */     this.minRotationRoll = getMinRotationPitch();
/*  269 */     this.maxRotationRoll = getMaxRotationPitch();
/*  270 */     this.limitRotation = false;
/*  271 */     this.throttleUpDown = 1.0F;
/*  272 */     this.throttleUpDownOnEntity = 2.0F;
/*  273 */     this.pivotTurnThrottle = 0.0F;
/*  274 */     this.trackRollerRot = 30.0F;
/*  275 */     this.partWheelRot = 30.0F;
/*      */     
/*  277 */     this.textureNameList = new ArrayList<>();
/*  278 */     this.textureNameList.add(this.name);
/*  279 */     this.textureCount = 0;
/*  280 */     this.particlesScale = 1.0F;
/*  281 */     this.enableSeaSurfaceParticle = false;
/*  282 */     this.hideEntity = false;
/*  283 */     this.smoothShading = true;
/*      */     
/*  285 */     this.soundMove = "";
/*  286 */     this.soundPitch = 1.0F;
/*  287 */     this.soundVolume = 1.0F;
/*  288 */     this.soundRange = getDefaultSoundRange();
/*      */     
/*  290 */     this.model = null;
/*  291 */     this.hatchList = new ArrayList<>();
/*  292 */     this.cameraList = new ArrayList<>();
/*  293 */     this.partWeapon = new ArrayList<>();
/*  294 */     this.lastWeaponPart = null;
/*  295 */     this.partWeaponBay = new ArrayList<>();
/*  296 */     this.canopyList = new ArrayList<>();
/*  297 */     this.landingGear = new ArrayList<>();
/*  298 */     this.partThrottle = new ArrayList<>();
/*  299 */     this.partRotPart = new ArrayList<>();
/*  300 */     this.partCrawlerTrack = new ArrayList<>();
/*  301 */     this.partTrackRoller = new ArrayList<>();
/*  302 */     this.partWheel = new ArrayList<>();
/*  303 */     this.partSteeringWheel = new ArrayList<>();
/*  304 */     this.lightHatchList = new ArrayList<>();
/*      */   }
/*      */ 
/*      */   
/*      */   public float getDefaultSoundRange() {
/*  309 */     return 100.0F;
/*      */   }
/*      */ 
/*      */   
/*      */   public List<Wheel> getDefaultWheelList() {
/*  314 */     return new ArrayList<>();
/*      */   }
/*      */ 
/*      */   
/*      */   public float getDefaultRotorSpeed() {
/*  319 */     return 0.0F;
/*      */   }
/*      */ 
/*      */   
/*      */   private float getDefaultStepHeight() {
/*  324 */     return 0.0F;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean haveRepellingHook() {
/*  329 */     return (this.repellingHooks.size() > 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean haveFlare() {
/*  334 */     return (this.flare.types.length > 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean haveCanopy() {
/*  339 */     return (this.canopyList.size() > 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean haveLandingGear() {
/*  344 */     return (this.landingGear.size() > 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract String getDefaultHudName(int paramInt);
/*      */ 
/*      */   
/*      */   public boolean validate() throws Exception {
/*  353 */     if (this.cameraPosition.size() <= 0)
/*      */     {
/*  355 */       this.cameraPosition.add(new CameraPosition(this));
/*      */     }
/*      */     
/*  358 */     this.bbZ = (this.bbZmax + this.bbZmin) / 2.0F;
/*      */     
/*  360 */     if (this.isTargetDrone)
/*      */     {
/*  362 */       this.isUAV = true;
/*      */     }
/*      */     
/*  365 */     if (this.isEnableParachuting && this.repellingHooks.size() > 0) {
/*      */       
/*  367 */       this.isEnableParachuting = false;
/*  368 */       this.repellingHooks.clear();
/*      */     } 
/*      */     
/*  371 */     if (this.isUAV) {
/*      */       
/*  373 */       this.alwaysCameraView = true;
/*  374 */       if (this.seatList.size() == 0) {
/*      */         
/*  376 */         MCH_SeatInfo s = new MCH_SeatInfo(new Vec3d(0.0D, 0.0D, 0.0D), false);
/*  377 */         this.seatList.add(s);
/*      */       } 
/*      */     } 
/*      */     
/*  381 */     this.mobSeatNum = this.seatList.size();
/*  382 */     this.entityRackNum = this.entityRackList.size();
/*      */     
/*  384 */     if (getNumSeat() < 1)
/*      */     {
/*      */       
/*  387 */       throw new Exception("At least one seat must be set.");
/*      */     }
/*  389 */     if (getNumHud() < getNumSeat())
/*      */     {
/*  391 */       for (int j = getNumHud(); j < getNumSeat(); j++)
/*      */       {
/*  393 */         this.hudList.add(MCH_HudManager.get(getDefaultHudName(j)));
/*      */       }
/*      */     }
/*      */     
/*  397 */     if (getNumSeat() == 1 && getNumHud() == 1)
/*      */     {
/*  399 */       this.hudList.add(MCH_HudManager.get(getDefaultHudName(1)));
/*      */     }
/*      */     
/*  402 */     for (MCH_SeatRackInfo ei : this.entityRackList)
/*      */     {
/*  404 */       this.seatList.add(ei);
/*      */     }
/*  406 */     this.entityRackList.clear();
/*      */     
/*  408 */     if (this.hudTvMissile == null)
/*      */     {
/*  410 */       this.hudTvMissile = MCH_HudManager.get("tv_missile");
/*      */     }
/*      */     
/*  413 */     if (this.textureNameList.size() < 1)
/*      */     {
/*  415 */       throw new Exception("At least one texture must be set.");
/*      */     }
/*  417 */     if (this.itemID <= 0);
/*      */ 
/*      */ 
/*      */     
/*  421 */     for (int i = 0; i < this.partWeaponBay.size(); i++) {
/*      */       
/*  423 */       WeaponBay wb = this.partWeaponBay.get(i);
/*      */       
/*  425 */       String[] weaponNames = wb.weaponName.split("\\s*/\\s*");
/*      */       
/*  427 */       if (weaponNames.length <= 0) {
/*      */         
/*  429 */         this.partWeaponBay.remove(i);
/*      */       }
/*      */       else {
/*      */         
/*  433 */         List<Integer> list = new ArrayList<>();
/*  434 */         for (String s : weaponNames) {
/*      */           
/*  436 */           int id = getWeaponIdByName(s);
/*  437 */           if (id >= 0)
/*      */           {
/*  439 */             list.add(Integer.valueOf(id));
/*      */           }
/*      */         } 
/*  442 */         if (list.size() <= 0) {
/*      */           
/*  444 */           this.partWeaponBay.remove(i);
/*      */         
/*      */         }
/*      */         else {
/*      */           
/*  449 */           ((WeaponBay)this.partWeaponBay.get(i)).weaponIds = list.<Integer>toArray(new Integer[0]);
/*      */         } 
/*      */       } 
/*      */     } 
/*  453 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getInfo_MaxSeatNum() {
/*  458 */     return 30;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getNumSeatAndRack() {
/*  463 */     return this.seatList.size();
/*      */   }
/*      */ 
/*      */   
/*      */   public int getNumSeat() {
/*  468 */     return this.mobSeatNum;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getNumRack() {
/*  473 */     return this.entityRackNum;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getNumHud() {
/*  478 */     return this.hudList.size();
/*      */   }
/*      */ 
/*      */   
/*      */   public float getMaxSpeed() {
/*  483 */     return 0.8F;
/*      */   }
/*      */ 
/*      */   
/*      */   public float getMinRotationPitch() {
/*  488 */     return -89.9F;
/*      */   }
/*      */ 
/*      */   
/*      */   public float getMaxRotationPitch() {
/*  493 */     return 80.0F;
/*      */   }
/*      */ 
/*      */   
/*      */   public float getMinRotationRoll() {
/*  498 */     return -80.0F;
/*      */   }
/*      */ 
/*      */   
/*      */   public float getMaxRotationRoll() {
/*  503 */     return 80.0F;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getDefaultMaxZoom() {
/*  508 */     return 1;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean haveHatch() {
/*  513 */     return (this.hatchList.size() > 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean havePartCamera() {
/*  518 */     return (this.cameraList.size() > 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean havePartThrottle() {
/*  523 */     return (this.partThrottle.size() > 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public WeaponSet getWeaponSetById(int id) {
/*  528 */     return (id >= 0 && id < this.weaponSetList.size()) ? this.weaponSetList.get(id) : null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Weapon getWeaponById(int id) {
/*  534 */     WeaponSet ws = getWeaponSetById(id);
/*  535 */     return (ws != null) ? ws.weapons.get(0) : null;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getWeaponIdByName(String s) {
/*  540 */     for (int i = 0; i < this.weaponSetList.size(); i++) {
/*      */       
/*  542 */       if (((WeaponSet)this.weaponSetList.get(i)).type.equalsIgnoreCase(s))
/*      */       {
/*  544 */         return i;
/*      */       }
/*      */     } 
/*  547 */     return -1;
/*      */   }
/*      */ 
/*      */   
/*      */   public Weapon getWeaponByName(String s) {
/*  552 */     for (int i = 0; i < this.weaponSetList.size(); i++) {
/*      */       
/*  554 */       if (((WeaponSet)this.weaponSetList.get(i)).type.equalsIgnoreCase(s))
/*      */       {
/*  556 */         return getWeaponById(i);
/*      */       }
/*      */     } 
/*  559 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getWeaponNum() {
/*  564 */     return this.weaponSetList.size();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void loadItemData(String item, String data) {
/*  570 */     if (item.compareTo("displayname") == 0) {
/*      */       
/*  572 */       this.displayName = data.trim();
/*      */     }
/*  574 */     else if (item.compareTo("adddisplayname") == 0) {
/*      */       
/*  576 */       String[] s = data.split("\\s*,\\s*");
/*      */       
/*  578 */       if (s != null && s.length == 2)
/*      */       {
/*      */         
/*  581 */         this.displayNameLang.put(s[0].toLowerCase().trim(), s[1].trim());
/*      */       }
/*      */     }
/*  584 */     else if (item.equalsIgnoreCase("Category")) {
/*      */       
/*  586 */       this.category = data.toUpperCase().replaceAll("[,;:]", ".").replaceAll("[ \t]", "");
/*      */     }
/*  588 */     else if (item.equalsIgnoreCase("CanRide")) {
/*      */       
/*  590 */       this.canRide = toBool(data, true);
/*      */     }
/*  592 */     else if (item.equalsIgnoreCase("CreativeOnly")) {
/*      */       
/*  594 */       this.creativeOnly = toBool(data, false);
/*      */     }
/*  596 */     else if (item.equalsIgnoreCase("Invulnerable")) {
/*      */       
/*  598 */       this.invulnerable = toBool(data, false);
/*      */     }
/*  600 */     else if (item.equalsIgnoreCase("MaxFuel")) {
/*      */       
/*  602 */       this.maxFuel = toInt(data, 0, 100000000);
/*      */     }
/*  604 */     else if (item.equalsIgnoreCase("FuelConsumption")) {
/*      */       
/*  606 */       this.fuelConsumption = toFloat(data, 0.0F, 10000.0F);
/*      */     }
/*  608 */     else if (item.equalsIgnoreCase("FuelSupplyRange")) {
/*      */       
/*  610 */       this.fuelSupplyRange = toFloat(data, 0.0F, 1000.0F);
/*      */     }
/*  612 */     else if (item.equalsIgnoreCase("AmmoSupplyRange")) {
/*      */       
/*  614 */       this.ammoSupplyRange = toFloat(data, 0.0F, 1000.0F);
/*      */     }
/*  616 */     else if (item.equalsIgnoreCase("RepairOtherVehicles")) {
/*      */       
/*  618 */       String[] s = splitParam(data);
/*  619 */       if (s.length >= 1) {
/*      */         
/*  621 */         this.repairOtherVehiclesRange = toFloat(s[0], 0.0F, 1000.0F);
/*  622 */         if (s.length >= 2) {
/*  623 */           this.repairOtherVehiclesValue = toInt(s[1], 0, 10000000);
/*      */         }
/*      */       } 
/*  626 */     } else if (item.compareTo("itemid") == 0) {
/*      */       
/*  628 */       this.itemID = toInt(data, 0, 65535);
/*      */     }
/*  630 */     else if (item.compareTo("addtexture") == 0) {
/*      */       
/*  632 */       this.textureNameList.add(data.toLowerCase());
/*      */     }
/*  634 */     else if (item.compareTo("particlesscale") == 0) {
/*      */       
/*  636 */       this.particlesScale = toFloat(data, 0.0F, 50.0F);
/*      */     }
/*  638 */     else if (item.equalsIgnoreCase("EnableSeaSurfaceParticle")) {
/*      */       
/*  640 */       this.enableSeaSurfaceParticle = toBool(data);
/*      */     }
/*  642 */     else if (item.equalsIgnoreCase("AddParticleSplash")) {
/*      */       
/*  644 */       String[] s = splitParam(data);
/*  645 */       if (s.length >= 3)
/*      */       {
/*  647 */         Vec3d v = toVec3(s[0], s[1], s[2]);
/*  648 */         int num = (s.length >= 4) ? toInt(s[3], 1, 100) : 2;
/*  649 */         float size = (s.length >= 5) ? toFloat(s[4]) : 2.0F;
/*  650 */         float acc = (s.length >= 6) ? toFloat(s[5]) : 1.0F;
/*  651 */         int age = (s.length >= 7) ? toInt(s[6], 1, 100000) : 80;
/*  652 */         float motionY = (s.length >= 8) ? toFloat(s[7]) : 0.01F;
/*  653 */         float gravity = (s.length >= 9) ? toFloat(s[8]) : 0.0F;
/*  654 */         this.particleSplashs
/*  655 */           .add(new ParticleSplash(this, v, num, size, acc, age, motionY, gravity));
/*      */       }
/*      */     
/*  658 */     } else if (item.equalsIgnoreCase("AddSearchLight") || item.equalsIgnoreCase("AddFixedSearchLight") || item
/*  659 */       .equalsIgnoreCase("AddSteeringSearchLight")) {
/*      */ 
/*      */       
/*  662 */       String[] s = splitParam(data);
/*  663 */       if (s.length >= 7)
/*      */       {
/*  665 */         Vec3d v = toVec3(s[0], s[1], s[2]);
/*  666 */         int cs = hex2dec(s[3]);
/*  667 */         int ce = hex2dec(s[4]);
/*  668 */         float h = toFloat(s[5]);
/*  669 */         float w = toFloat(s[6]);
/*  670 */         float yaw = (s.length >= 8) ? toFloat(s[7]) : 0.0F;
/*  671 */         float pitch = (s.length >= 9) ? toFloat(s[8]) : 0.0F;
/*  672 */         float stRot = (s.length >= 10) ? toFloat(s[9]) : 0.0F;
/*  673 */         boolean fixDir = !item.equalsIgnoreCase("AddSearchLight");
/*  674 */         boolean steering = item.equalsIgnoreCase("AddSteeringSearchLight");
/*  675 */         this.searchLights.add(new SearchLight(this, v, cs, ce, h, w, fixDir, yaw, pitch, steering, stRot));
/*      */       }
/*      */     
/*      */     }
/*  679 */     else if (item.equalsIgnoreCase("AddPartLightHatch")) {
/*      */       
/*  681 */       String[] s = splitParam(data);
/*  682 */       if (s.length >= 6)
/*      */       {
/*  684 */         float mx = (s.length >= 7) ? toFloat(s[6], -1800.0F, 1800.0F) : 90.0F;
/*  685 */         this.lightHatchList.add(new Hatch(this, 
/*  686 */               toFloat(s[0]), toFloat(s[1]), toFloat(s[2]), toFloat(s[3]), 
/*  687 */               toFloat(s[4]), toFloat(s[5]), mx, "light_hatch" + this.lightHatchList.size(), false));
/*      */       
/*      */       }
/*      */     
/*      */     }
/*  692 */     else if (item.equalsIgnoreCase("AddRepellingHook")) {
/*      */ 
/*      */       
/*  695 */       String[] s = splitParam(data);
/*  696 */       if (s != null && s.length >= 3)
/*      */       {
/*  698 */         int inv = (s.length >= 4) ? toInt(s[3], 1, 100000) : 10;
/*  699 */         this.repellingHooks.add(new RepellingHook(this, toVec3(s[0], s[1], s[2]), inv));
/*      */       }
/*      */     
/*  702 */     } else if (item.equalsIgnoreCase("AddRack")) {
/*      */ 
/*      */       
/*  705 */       String[] s = data.toLowerCase().split("\\s*,\\s*");
/*  706 */       if (s != null && s.length >= 7)
/*      */       {
/*  708 */         String[] names = s[0].split("\\s*/\\s*");
/*  709 */         float range = (s.length >= 8) ? toFloat(s[7]) : 6.0F;
/*  710 */         float para = (s.length >= 9) ? toFloat(s[8], 0.0F, 1000000.0F) : 20.0F;
/*  711 */         float yaw = (s.length >= 10) ? toFloat(s[9]) : 0.0F;
/*  712 */         float pitch = (s.length >= 11) ? toFloat(s[10]) : 0.0F;
/*  713 */         boolean rs = (s.length >= 12) ? toBool(s[11]) : false;
/*  714 */         this.entityRackList.add(new MCH_SeatRackInfo(names, toDouble(s[1]), toDouble(s[2]), toDouble(s[3]), new CameraPosition(this, 
/*  715 */                 toVec3(s[4], s[5], s[6]).func_72441_c(0.0D, 1.5D, 0.0D)), range, para, yaw, pitch, rs));
/*      */       
/*      */       }
/*      */ 
/*      */     
/*      */     }
/*  721 */     else if (item.equalsIgnoreCase("RideRack")) {
/*      */       
/*  723 */       String[] s = splitParam(data);
/*  724 */       if (s.length >= 2)
/*      */       {
/*      */         
/*  727 */         RideRack r = new RideRack(this, s[0].trim().toLowerCase(), toInt(s[1], 1, 10000));
/*  728 */         this.rideRacks.add(r);
/*      */       }
/*      */     
/*  731 */     } else if (item.equalsIgnoreCase("AddSeat") || item.equalsIgnoreCase("AddGunnerSeat") || item
/*  732 */       .equalsIgnoreCase("AddFixRotSeat")) {
/*      */ 
/*      */       
/*  735 */       if (this.seatList.size() >= getInfo_MaxSeatNum()) {
/*      */         return;
/*      */       }
/*      */       
/*  739 */       String[] s = splitParam(data);
/*  740 */       if (s.length < 3) {
/*      */         return;
/*      */       }
/*      */       
/*  744 */       Vec3d p = toVec3(s[0], s[1], s[2]);
/*  745 */       if (item.equalsIgnoreCase("AddSeat")) {
/*      */         
/*  747 */         boolean rs = (s.length >= 4) ? toBool(s[3]) : false;
/*  748 */         MCH_SeatInfo seat = new MCH_SeatInfo(p, rs);
/*  749 */         this.seatList.add(seat);
/*      */       } else {
/*      */         MCH_SeatInfo seat;
/*      */ 
/*      */         
/*  754 */         if (s.length >= 6) {
/*      */ 
/*      */           
/*  757 */           CameraPosition c = new CameraPosition(this, toVec3(s[3], s[4], s[5]));
/*      */           
/*  759 */           boolean sg = (s.length >= 7) ? toBool(s[6]) : false;
/*      */           
/*  761 */           if (item.equalsIgnoreCase("AddGunnerSeat")) {
/*      */ 
/*      */             
/*  764 */             if (s.length >= 9)
/*      */             {
/*  766 */               float minPitch = toFloat(s[7], -90.0F, 90.0F);
/*  767 */               float maxPitch = toFloat(s[8], -90.0F, 90.0F);
/*  768 */               if (minPitch > maxPitch) {
/*      */                 
/*  770 */                 float t = minPitch;
/*  771 */                 minPitch = maxPitch;
/*  772 */                 maxPitch = t;
/*      */               } 
/*  774 */               boolean rs = (s.length >= 10) ? toBool(s[9]) : false;
/*  775 */               seat = new MCH_SeatInfo(p, true, c, true, sg, false, 0.0F, 0.0F, minPitch, maxPitch, rs);
/*      */             }
/*      */             else
/*      */             {
/*  779 */               seat = new MCH_SeatInfo(p, true, c, true, sg, false, 0.0F, 0.0F, false);
/*      */             }
/*      */           
/*      */           } else {
/*      */             
/*  784 */             boolean fixRot = (s.length >= 9);
/*  785 */             float fixYaw = fixRot ? toFloat(s[7]) : 0.0F;
/*  786 */             float fixPitch = fixRot ? toFloat(s[8]) : 0.0F;
/*  787 */             boolean rs = (s.length >= 10) ? toBool(s[9]) : false;
/*  788 */             seat = new MCH_SeatInfo(p, true, c, true, sg, fixRot, fixYaw, fixPitch, rs);
/*      */           }
/*      */         
/*      */         } else {
/*      */           
/*  793 */           seat = new MCH_SeatInfo(p, true, new CameraPosition(this), false, false, false, 0.0F, 0.0F, false);
/*      */         } 
/*      */         
/*  796 */         this.seatList.add(seat);
/*      */       }
/*      */     
/*  799 */     } else if (item.equalsIgnoreCase("SetWheelPos")) {
/*      */       
/*  801 */       String[] s = splitParam(data);
/*  802 */       if (s.length >= 4)
/*      */       {
/*  804 */         float x = Math.abs(toFloat(s[0]));
/*  805 */         float y = toFloat(s[1]);
/*  806 */         this.wheels.clear();
/*  807 */         for (int i = 2; i < s.length; i++)
/*      */         {
/*  809 */           this.wheels.add(new Wheel(this, new Vec3d(x, y, toFloat(s[i]))));
/*      */         }
/*      */         
/*  812 */         Collections.sort(this.wheels, new Comparator<Wheel>()
/*      */             {
/*      */               
/*      */               public int compare(MCH_AircraftInfo.Wheel arg0, MCH_AircraftInfo.Wheel arg1)
/*      */               {
/*  817 */                 return (arg0.pos.field_72449_c > arg1.pos.field_72449_c) ? -1 : 1;
/*      */               }
/*      */             });
/*      */       
/*      */       }
/*      */     
/*      */     }
/*  824 */     else if (item.equalsIgnoreCase("ExclusionSeat")) {
/*      */       
/*  826 */       String[] s = splitParam(data);
/*  827 */       if (s.length >= 2)
/*      */       {
/*  829 */         Integer[] a = new Integer[s.length];
/*  830 */         for (int i = 0; i < a.length; i++)
/*      */         {
/*  832 */           a[i] = Integer.valueOf(toInt(s[i], 1, 10000) - 1);
/*      */         }
/*  834 */         this.exclusionSeatList.add(a);
/*      */       }
/*      */     
/*  837 */     } else if (MCH_MOD.proxy.isRemote() && item.equalsIgnoreCase("HUD")) {
/*      */       
/*  839 */       this.hudList.clear();
/*  840 */       String[] ss = data.split("\\s*,\\s*");
/*  841 */       for (String s : ss)
/*      */       {
/*  843 */         MCH_Hud hud = MCH_HudManager.get(s);
/*  844 */         if (hud == null)
/*      */         {
/*  846 */           hud = MCH_Hud.NoDisp;
/*      */         }
/*  848 */         this.hudList.add(hud);
/*      */       }
/*      */     
/*  851 */     } else if (item.compareTo("enablenightvision") == 0) {
/*      */       
/*  853 */       this.isEnableNightVision = toBool(data);
/*      */     }
/*  855 */     else if (item.compareTo("enableentityradar") == 0) {
/*      */       
/*  857 */       this.isEnableEntityRadar = toBool(data);
/*      */     }
/*  859 */     else if (item.equalsIgnoreCase("EnableEjectionSeat")) {
/*      */       
/*  861 */       this.isEnableEjectionSeat = toBool(data);
/*      */     }
/*  863 */     else if (item.equalsIgnoreCase("EnableParachuting")) {
/*      */       
/*  865 */       this.isEnableParachuting = toBool(data);
/*      */     }
/*  867 */     else if (item.equalsIgnoreCase("MobDropOption")) {
/*      */       
/*  869 */       String[] s = splitParam(data);
/*  870 */       if (s.length >= 3)
/*      */       {
/*  872 */         this.mobDropOption.pos = toVec3(s[0], s[1], s[2]);
/*  873 */         this.mobDropOption.interval = (s.length >= 4) ? toInt(s[3]) : 12;
/*      */       }
/*      */     
/*  876 */     } else if (item.equalsIgnoreCase("Width")) {
/*      */       
/*  878 */       this.bodyWidth = toFloat(data, 0.1F, 1000.0F);
/*      */     }
/*  880 */     else if (item.equalsIgnoreCase("Height")) {
/*      */       
/*  882 */       this.bodyHeight = toFloat(data, 0.1F, 1000.0F);
/*      */     }
/*  884 */     else if (item.compareTo("float") == 0) {
/*      */       
/*  886 */       this.isFloat = toBool(data);
/*      */     }
/*  888 */     else if (item.compareTo("floatoffset") == 0) {
/*      */ 
/*      */       
/*  891 */       this.floatOffset = -toFloat(data);
/*      */     }
/*  893 */     else if (item.compareTo("gravity") == 0) {
/*      */       
/*  895 */       this.gravity = toFloat(data, -50.0F, 50.0F);
/*      */     }
/*  897 */     else if (item.compareTo("gravityinwater") == 0) {
/*      */       
/*  899 */       this.gravityInWater = toFloat(data, -50.0F, 50.0F);
/*      */     }
/*  901 */     else if (item.compareTo("cameraposition") == 0) {
/*      */       
/*  903 */       String[] s = data.split("\\s*,\\s*");
/*  904 */       if (s.length >= 3)
/*      */       {
/*  906 */         this.alwaysCameraView = (s.length >= 4) ? toBool(s[3]) : false;
/*  907 */         boolean fixRot = (s.length >= 5);
/*  908 */         float yaw = (s.length >= 5) ? toFloat(s[4]) : 0.0F;
/*  909 */         float pitch = (s.length >= 6) ? toFloat(s[5]) : 0.0F;
/*  910 */         this.cameraPosition
/*  911 */           .add(new CameraPosition(this, toVec3(s[0], s[1], s[2]), fixRot, yaw, pitch));
/*      */       }
/*      */     
/*  914 */     } else if (item.equalsIgnoreCase("UnmountPosition")) {
/*      */       
/*  916 */       String[] s = data.split("\\s*,\\s*");
/*  917 */       if (s.length >= 3)
/*      */       {
/*  919 */         this.unmountPosition = toVec3(s[0], s[1], s[2]);
/*      */       }
/*      */     }
/*  922 */     else if (item.equalsIgnoreCase("ThirdPersonDist")) {
/*      */       
/*  924 */       this.thirdPersonDist = toFloat(data, 4.0F, 100.0F);
/*      */     }
/*  926 */     else if (item.equalsIgnoreCase("TurretPosition")) {
/*      */       
/*  928 */       String[] s = data.split("\\s*,\\s*");
/*  929 */       if (s.length >= 3)
/*      */       {
/*  931 */         this.turretPosition = toVec3(s[0], s[1], s[2]);
/*      */       }
/*      */     }
/*  934 */     else if (item.equalsIgnoreCase("CameraRotationSpeed")) {
/*      */       
/*  936 */       this.cameraRotationSpeed = toFloat(data, 0.0F, 10000.0F);
/*      */     }
/*  938 */     else if (item.compareTo("regeneration") == 0) {
/*      */       
/*  940 */       this.regeneration = toBool(data);
/*      */     }
/*  942 */     else if (item.compareTo("speed") == 0) {
/*      */       
/*  944 */       this.speed = toFloat(data, 0.0F, getMaxSpeed());
/*      */     }
/*  946 */     else if (item.equalsIgnoreCase("EnableBack")) {
/*      */       
/*  948 */       this.enableBack = toBool(data);
/*      */     }
/*  950 */     else if (item.equalsIgnoreCase("MotionFactor")) {
/*      */       
/*  952 */       this.motionFactor = toFloat(data, 0.0F, 1.0F);
/*      */     }
/*  954 */     else if (item.equalsIgnoreCase("MobilityYawOnGround")) {
/*      */       
/*  956 */       this.mobilityYawOnGround = toFloat(data, 0.0F, 100.0F);
/*      */     }
/*  958 */     else if (item.equalsIgnoreCase("MobilityYaw")) {
/*      */       
/*  960 */       this.mobilityYaw = toFloat(data, 0.0F, 100.0F);
/*      */     }
/*  962 */     else if (item.equalsIgnoreCase("MobilityPitch")) {
/*      */       
/*  964 */       this.mobilityPitch = toFloat(data, 0.0F, 100.0F);
/*      */     }
/*  966 */     else if (item.equalsIgnoreCase("MobilityRoll")) {
/*      */       
/*  968 */       this.mobilityRoll = toFloat(data, 0.0F, 100.0F);
/*      */     }
/*  970 */     else if (item.equalsIgnoreCase("MinRotationPitch")) {
/*      */       
/*  972 */       this.limitRotation = true;
/*  973 */       this.minRotationPitch = toFloat(data, getMinRotationPitch(), 0.0F);
/*      */     }
/*  975 */     else if (item.equalsIgnoreCase("MaxRotationPitch")) {
/*      */       
/*  977 */       this.limitRotation = true;
/*  978 */       this.maxRotationPitch = toFloat(data, 0.0F, getMaxRotationPitch());
/*      */     }
/*  980 */     else if (item.equalsIgnoreCase("MinRotationRoll")) {
/*      */       
/*  982 */       this.limitRotation = true;
/*  983 */       this.minRotationRoll = toFloat(data, getMinRotationRoll(), 0.0F);
/*      */     }
/*  985 */     else if (item.equalsIgnoreCase("MaxRotationRoll")) {
/*      */       
/*  987 */       this.limitRotation = true;
/*  988 */       this.maxRotationRoll = toFloat(data, 0.0F, getMaxRotationRoll());
/*      */     }
/*  990 */     else if (item.compareTo("throttleupdown") == 0) {
/*      */       
/*  992 */       this.throttleUpDown = toFloat(data, 0.0F, 3.0F);
/*      */     }
/*  994 */     else if (item.equalsIgnoreCase("ThrottleUpDownOnEntity")) {
/*      */       
/*  996 */       this.throttleUpDownOnEntity = toFloat(data, 0.0F, 100000.0F);
/*      */     }
/*  998 */     else if (item.equalsIgnoreCase("Stealth")) {
/*      */       
/* 1000 */       this.stealth = toFloat(data, 0.0F, 1.0F);
/*      */     }
/* 1002 */     else if (item.equalsIgnoreCase("EntityWidth")) {
/*      */       
/* 1004 */       this.entityWidth = toFloat(data, -100.0F, 100.0F);
/*      */     }
/* 1006 */     else if (item.equalsIgnoreCase("EntityHeight")) {
/*      */       
/* 1008 */       this.entityHeight = toFloat(data, -100.0F, 100.0F);
/*      */     }
/* 1010 */     else if (item.equalsIgnoreCase("EntityPitch")) {
/*      */       
/* 1012 */       this.entityPitch = toFloat(data, -360.0F, 360.0F);
/*      */     }
/* 1014 */     else if (item.equalsIgnoreCase("EntityRoll")) {
/*      */       
/* 1016 */       this.entityRoll = toFloat(data, -360.0F, 360.0F);
/*      */     }
/* 1018 */     else if (item.equalsIgnoreCase("StepHeight")) {
/*      */       
/* 1020 */       this.stepHeight = toFloat(data, 0.0F, 1000.0F);
/*      */     }
/* 1022 */     else if (item.equalsIgnoreCase("CanMoveOnGround")) {
/*      */       
/* 1024 */       this.canMoveOnGround = toBool(data);
/*      */     }
/* 1026 */     else if (item.equalsIgnoreCase("CanRotOnGround")) {
/*      */       
/* 1028 */       this.canRotOnGround = toBool(data);
/*      */     }
/* 1030 */     else if (item.equalsIgnoreCase("AddWeapon") || item.equalsIgnoreCase("AddTurretWeapon")) {
/*      */       
/* 1032 */       String[] s = data.split("\\s*,\\s*");
/* 1033 */       String type = s[0].toLowerCase();
/* 1034 */       if (s.length >= 4 && MCH_WeaponInfoManager.contains(type))
/*      */       {
/* 1036 */         float y = (s.length >= 5) ? toFloat(s[4]) : 0.0F;
/* 1037 */         float p = (s.length >= 6) ? toFloat(s[5]) : 0.0F;
/* 1038 */         boolean canUsePilot = (s.length >= 7) ? toBool(s[6]) : true;
/* 1039 */         int seatID = (s.length >= 8) ? (toInt(s[7], 1, getInfo_MaxSeatNum()) - 1) : 0;
/* 1040 */         if (seatID <= 0)
/* 1041 */           canUsePilot = true; 
/* 1042 */         float dfy = (s.length >= 9) ? toFloat(s[8]) : 0.0F;
/* 1043 */         dfy = MathHelper.func_76142_g(dfy);
/* 1044 */         float mny = (s.length >= 10) ? toFloat(s[9]) : 0.0F;
/* 1045 */         float mxy = (s.length >= 11) ? toFloat(s[10]) : 0.0F;
/* 1046 */         float mnp = (s.length >= 12) ? toFloat(s[11]) : 0.0F;
/* 1047 */         float mxp = (s.length >= 13) ? toFloat(s[12]) : 0.0F;
/*      */ 
/*      */         
/* 1050 */         Weapon e = new Weapon(this, toFloat(s[1]), toFloat(s[2]), toFloat(s[3]), y, p, canUsePilot, seatID, dfy, mny, mxy, mnp, mxp, item.equalsIgnoreCase("AddTurretWeapon"));
/*      */         
/* 1052 */         if (type.compareTo(this.lastWeaponType) != 0) {
/*      */           
/* 1054 */           this.weaponSetList.add(new WeaponSet(this, type));
/* 1055 */           this.lastWeaponIndex++;
/* 1056 */           this.lastWeaponType = type;
/*      */         } 
/* 1058 */         ((WeaponSet)this.weaponSetList.get(this.lastWeaponIndex)).weapons.add(e);
/*      */       }
/*      */     
/* 1061 */     } else if (item.equalsIgnoreCase("AddPartWeapon") || item.equalsIgnoreCase("AddPartRotWeapon") || item
/* 1062 */       .equalsIgnoreCase("AddPartTurretWeapon") || item.equalsIgnoreCase("AddPartTurretRotWeapon") || item
/* 1063 */       .equalsIgnoreCase("AddPartWeaponMissile")) {
/*      */ 
/*      */       
/* 1066 */       String[] s = data.split("\\s*,\\s*");
/*      */       
/* 1068 */       if (s.length >= 7)
/*      */       {
/* 1070 */         float rx = 0.0F;
/* 1071 */         float ry = 0.0F;
/* 1072 */         float rz = 0.0F;
/* 1073 */         float rb = 0.0F;
/*      */         
/* 1075 */         boolean isRot = (item.equalsIgnoreCase("AddPartRotWeapon") || item.equalsIgnoreCase("AddPartTurretRotWeapon"));
/*      */         
/* 1077 */         boolean isMissile = item.equalsIgnoreCase("AddPartWeaponMissile");
/*      */         
/* 1079 */         boolean turret = (item.equalsIgnoreCase("AddPartTurretWeapon") || item.equalsIgnoreCase("AddPartTurretRotWeapon"));
/*      */         
/* 1081 */         if (isRot) {
/*      */ 
/*      */           
/* 1084 */           rx = (s.length >= 10) ? toFloat(s[7]) : 0.0F;
/* 1085 */           ry = (s.length >= 10) ? toFloat(s[8]) : 0.0F;
/* 1086 */           rz = (s.length >= 10) ? toFloat(s[9]) : -1.0F;
/*      */         
/*      */         }
/*      */         else {
/*      */           
/* 1091 */           rb = (s.length >= 8) ? toFloat(s[7]) : 0.0F;
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/* 1096 */         PartWeapon w = new PartWeapon(this, splitParamSlash(s[0].toLowerCase().trim()), isRot, isMissile, toBool(s[1]), toBool(s[2]), toBool(s[3]), toFloat(s[4]), toFloat(s[5]), toFloat(s[6]), "weapon" + this.partWeapon.size(), rx, ry, rz, rb, turret);
/*      */ 
/*      */         
/* 1099 */         this.lastWeaponPart = w;
/* 1100 */         this.partWeapon.add(w);
/*      */       }
/*      */     
/* 1103 */     } else if (item.equalsIgnoreCase("AddPartWeaponChild")) {
/*      */       
/* 1105 */       String[] s = data.split("\\s*,\\s*");
/* 1106 */       if (s.length >= 5 && this.lastWeaponPart != null)
/*      */       {
/* 1108 */         float rb = (s.length >= 6) ? toFloat(s[5]) : 0.0F;
/*      */ 
/*      */         
/* 1111 */         PartWeaponChild w = new PartWeaponChild(this, this.lastWeaponPart.name, toBool(s[0]), toBool(s[1]), toFloat(s[2]), toFloat(s[3]), toFloat(s[4]), this.lastWeaponPart.modelName + "_" + this.lastWeaponPart.child.size(), 0.0F, 0.0F, 0.0F, rb);
/*      */ 
/*      */         
/* 1114 */         this.lastWeaponPart.child.add(w);
/*      */       }
/*      */     
/* 1117 */     } else if (item.compareTo("addrecipe") == 0 || item.compareTo("addshapelessrecipe") == 0) {
/*      */       
/* 1119 */       this.isShapedRecipe = (item.compareTo("addrecipe") == 0);
/* 1120 */       this.recipeString.add(data.toUpperCase());
/*      */     }
/* 1122 */     else if (item.compareTo("maxhp") == 0) {
/*      */       
/* 1124 */       this.maxHp = toInt(data, 1, 1000000000);
/*      */     }
/* 1126 */     else if (item.compareTo("inventorysize") == 0) {
/*      */       
/* 1128 */       this.inventorySize = toInt(data, 0, 54);
/*      */     }
/* 1130 */     else if (item.compareTo("damagefactor") == 0) {
/*      */       
/* 1132 */       this.damageFactor = toFloat(data, 0.0F, 1.0F);
/*      */     }
/* 1134 */     else if (item.equalsIgnoreCase("SubmergedDamageHeight")) {
/*      */       
/* 1136 */       this.submergedDamageHeight = toFloat(data, -1000.0F, 1000.0F);
/*      */     }
/* 1138 */     else if (item.equalsIgnoreCase("ArmorDamageFactor")) {
/*      */       
/* 1140 */       this.armorDamageFactor = toFloat(data, 0.0F, 10000.0F);
/*      */     }
/* 1142 */     else if (item.equalsIgnoreCase("ArmorMinDamage")) {
/*      */       
/* 1144 */       this.armorMinDamage = toFloat(data, 0.0F, 1000000.0F);
/*      */     }
/* 1146 */     else if (item.equalsIgnoreCase("ArmorMaxDamage")) {
/*      */       
/* 1148 */       this.armorMaxDamage = toFloat(data, 0.0F, 1000000.0F);
/*      */     }
/* 1150 */     else if (item.equalsIgnoreCase("FlareType")) {
/*      */       
/* 1152 */       String[] s = data.split("\\s*,\\s*");
/* 1153 */       this.flare.types = new int[s.length];
/* 1154 */       for (int i = 0; i < s.length; i++)
/*      */       {
/* 1156 */         this.flare.types[i] = toInt(s[i], 1, 10);
/*      */       }
/*      */     }
/* 1159 */     else if (item.equalsIgnoreCase("FlareOption")) {
/*      */       
/* 1161 */       String[] s = splitParam(data);
/* 1162 */       if (s.length >= 3)
/*      */       {
/* 1164 */         this.flare.pos = toVec3(s[0], s[1], s[2]);
/*      */       }
/*      */     }
/* 1167 */     else if (item.equalsIgnoreCase("Sound")) {
/*      */       
/* 1169 */       this.soundMove = data.toLowerCase();
/*      */     }
/* 1171 */     else if (item.equalsIgnoreCase("SoundRange")) {
/*      */       
/* 1173 */       this.soundRange = toFloat(data, 1.0F, 1000.0F);
/*      */     }
/* 1175 */     else if (item.equalsIgnoreCase("SoundVolume")) {
/*      */       
/* 1177 */       this.soundVolume = toFloat(data, 0.0F, 10.0F);
/*      */     }
/* 1179 */     else if (item.equalsIgnoreCase("SoundPitch")) {
/*      */       
/* 1181 */       this.soundPitch = toFloat(data, 0.0F, 10.0F);
/*      */     }
/* 1183 */     else if (item.equalsIgnoreCase("UAV")) {
/*      */       
/* 1185 */       this.isUAV = toBool(data);
/* 1186 */       this.isSmallUAV = false;
/*      */     }
/* 1188 */     else if (item.equalsIgnoreCase("SmallUAV")) {
/*      */       
/* 1190 */       this.isUAV = toBool(data);
/* 1191 */       this.isSmallUAV = true;
/*      */     }
/* 1193 */     else if (item.equalsIgnoreCase("TargetDrone")) {
/*      */       
/* 1195 */       this.isTargetDrone = toBool(data);
/*      */     }
/* 1197 */     else if (item.compareTo("autopilotrot") == 0) {
/*      */       
/* 1199 */       this.autoPilotRot = toFloat(data, -5.0F, 5.0F);
/*      */     }
/* 1201 */     else if (item.compareTo("ongroundpitch") == 0) {
/*      */ 
/*      */       
/* 1204 */       this.onGroundPitch = -toFloat(data, -90.0F, 90.0F);
/*      */     }
/* 1206 */     else if (item.compareTo("enablegunnermode") == 0) {
/*      */       
/* 1208 */       this.isEnableGunnerMode = toBool(data);
/*      */     }
/* 1210 */     else if (item.compareTo("hideentity") == 0) {
/*      */       
/* 1212 */       this.hideEntity = toBool(data);
/*      */     }
/* 1214 */     else if (item.equalsIgnoreCase("SmoothShading")) {
/*      */       
/* 1216 */       this.smoothShading = toBool(data);
/*      */     }
/* 1218 */     else if (item.compareTo("concurrentgunnermode") == 0) {
/*      */       
/* 1220 */       this.isEnableConcurrentGunnerMode = toBool(data);
/*      */     }
/* 1222 */     else if (item.equalsIgnoreCase("AddPartWeaponBay") || item.equalsIgnoreCase("AddPartSlideWeaponBay")) {
/*      */       
/* 1224 */       boolean slide = item.equalsIgnoreCase("AddPartSlideWeaponBay");
/* 1225 */       String[] s = data.split("\\s*,\\s*");
/* 1226 */       WeaponBay n = null;
/* 1227 */       if (slide)
/*      */       {
/* 1229 */         if (s.length >= 4)
/*      */         {
/*      */           
/* 1232 */           n = new WeaponBay(this, s[0].trim().toLowerCase(), toFloat(s[1]), toFloat(s[2]), toFloat(s[3]), 0.0F, 0.0F, 0.0F, 90.0F, "wb" + this.partWeaponBay.size(), slide);
/*      */           
/* 1234 */           this.partWeaponBay.add(n);
/*      */         }
/*      */       
/*      */       }
/* 1238 */       else if (s.length >= 7)
/*      */       {
/* 1240 */         float mx = (s.length >= 8) ? toFloat(s[7], -180.0F, 180.0F) : 90.0F;
/*      */ 
/*      */         
/* 1243 */         n = new WeaponBay(this, s[0].trim().toLowerCase(), toFloat(s[1]), toFloat(s[2]), toFloat(s[3]), toFloat(s[4]), toFloat(s[5]), toFloat(s[6]), mx / 90.0F, "wb" + this.partWeaponBay.size(), slide);
/*      */         
/* 1245 */         this.partWeaponBay.add(n);
/*      */       }
/*      */     
/*      */     }
/* 1249 */     else if (item.compareTo("addparthatch") == 0 || item.compareTo("addpartslidehatch") == 0) {
/*      */       
/* 1251 */       boolean slide = (item.compareTo("addpartslidehatch") == 0);
/* 1252 */       String[] s = data.split("\\s*,\\s*");
/* 1253 */       Hatch n = null;
/* 1254 */       if (slide)
/*      */       {
/* 1256 */         if (s.length >= 3)
/*      */         {
/*      */           
/* 1259 */           n = new Hatch(this, toFloat(s[0]), toFloat(s[1]), toFloat(s[2]), 0.0F, 0.0F, 0.0F, 90.0F, "hatch" + this.hatchList.size(), slide);
/*      */           
/* 1261 */           this.hatchList.add(n);
/*      */         }
/*      */       
/*      */       }
/* 1265 */       else if (s.length >= 6)
/*      */       {
/* 1267 */         float mx = (s.length >= 7) ? toFloat(s[6], -180.0F, 180.0F) : 90.0F;
/*      */         
/* 1269 */         n = new Hatch(this, toFloat(s[0]), toFloat(s[1]), toFloat(s[2]), toFloat(s[3]), toFloat(s[4]), toFloat(s[5]), mx, "hatch" + this.hatchList.size(), slide);
/*      */         
/* 1271 */         this.hatchList.add(n);
/*      */       }
/*      */     
/*      */     }
/* 1275 */     else if (item.compareTo("addpartcanopy") == 0 || item.compareTo("addpartslidecanopy") == 0) {
/*      */       
/* 1277 */       String[] s = data.split("\\s*,\\s*");
/* 1278 */       boolean slide = (item.compareTo("addpartslidecanopy") == 0);
/*      */       
/* 1280 */       int canopyNum = this.canopyList.size();
/* 1281 */       if (canopyNum > 0)
/* 1282 */         canopyNum--; 
/* 1283 */       if (slide)
/*      */       {
/* 1285 */         if (s.length >= 3)
/*      */         {
/*      */           
/* 1288 */           Canopy c = new Canopy(this, toFloat(s[0]), toFloat(s[1]), toFloat(s[2]), 0.0F, 0.0F, 0.0F, 90.0F, "canopy" + canopyNum, slide);
/*      */           
/* 1290 */           this.canopyList.add(c);
/*      */           
/* 1292 */           if (canopyNum == 0)
/*      */           {
/* 1294 */             c = new Canopy(this, toFloat(s[0]), toFloat(s[1]), toFloat(s[2]), 0.0F, 0.0F, 0.0F, 90.0F, "canopy", slide);
/*      */ 
/*      */             
/* 1297 */             this.canopyList.add(c);
/*      */           }
/*      */         
/*      */         }
/*      */       
/*      */       }
/* 1303 */       else if (s.length >= 6)
/*      */       {
/* 1305 */         float mx = (s.length >= 7) ? toFloat(s[6], -180.0F, 180.0F) : 90.0F;
/* 1306 */         mx /= 90.0F;
/*      */         
/* 1308 */         Canopy c = new Canopy(this, toFloat(s[0]), toFloat(s[1]), toFloat(s[2]), toFloat(s[3]), toFloat(s[4]), toFloat(s[5]), mx, "canopy" + canopyNum, slide);
/*      */         
/* 1310 */         this.canopyList.add(c);
/*      */         
/* 1312 */         if (canopyNum == 0)
/*      */         {
/*      */           
/* 1315 */           c = new Canopy(this, toFloat(s[0]), toFloat(s[1]), toFloat(s[2]), toFloat(s[3]), toFloat(s[4]), toFloat(s[5]), mx, "canopy", slide);
/*      */           
/* 1317 */           this.canopyList.add(c);
/*      */         }
/*      */       
/*      */       }
/*      */     
/* 1322 */     } else if (item.equalsIgnoreCase("AddPartLG") || item.equalsIgnoreCase("AddPartSlideRotLG") || item
/* 1323 */       .equalsIgnoreCase("AddPartLGRev") || item.equalsIgnoreCase("AddPartLGHatch")) {
/*      */ 
/*      */       
/* 1326 */       String[] s = data.split("\\s*,\\s*");
/* 1327 */       if (!item.equalsIgnoreCase("AddPartSlideRotLG") && s.length >= 6) {
/*      */         
/* 1329 */         float maxRot = (s.length >= 7) ? toFloat(s[6], -180.0F, 180.0F) : 90.0F;
/* 1330 */         maxRot /= 90.0F;
/*      */ 
/*      */         
/* 1333 */         LandingGear n = new LandingGear(this, toFloat(s[0]), toFloat(s[1]), toFloat(s[2]), toFloat(s[3]), toFloat(s[4]), toFloat(s[5]), "lg" + this.landingGear.size(), maxRot, item.equalsIgnoreCase("AddPartLgRev"), item.equalsIgnoreCase("AddPartLGHatch"));
/*      */         
/* 1335 */         if (s.length >= 8) {
/*      */           
/* 1337 */           n.enableRot2 = true;
/* 1338 */           n.maxRotFactor2 = (s.length >= 11) ? toFloat(s[10], -180.0F, 180.0F) : 90.0F;
/* 1339 */           n.maxRotFactor2 /= 90.0F;
/* 1340 */           n.rot2 = new Vec3d(toFloat(s[7]), toFloat(s[8]), toFloat(s[9]));
/*      */         } 
/*      */         
/* 1343 */         this.landingGear.add(n);
/*      */       } 
/* 1345 */       if (item.equalsIgnoreCase("AddPartSlideRotLG") && s.length >= 9)
/*      */       {
/* 1347 */         float maxRot = (s.length >= 10) ? toFloat(s[9], -180.0F, 180.0F) : 90.0F;
/* 1348 */         maxRot /= 90.0F;
/*      */         
/* 1350 */         LandingGear n = new LandingGear(this, toFloat(s[3]), toFloat(s[4]), toFloat(s[5]), toFloat(s[6]), toFloat(s[7]), toFloat(s[8]), "lg" + this.landingGear.size(), maxRot, false, false);
/*      */ 
/*      */         
/* 1353 */         n.slide = new Vec3d(toFloat(s[0]), toFloat(s[1]), toFloat(s[2]));
/*      */         
/* 1355 */         this.landingGear.add(n);
/*      */       }
/*      */     
/* 1358 */     } else if (item.equalsIgnoreCase("AddPartThrottle")) {
/*      */       
/* 1360 */       String[] s = data.split("\\s*,\\s*");
/* 1361 */       if (s.length >= 7)
/*      */       {
/* 1363 */         float x = (s.length >= 8) ? toFloat(s[7]) : 0.0F;
/* 1364 */         float y = (s.length >= 9) ? toFloat(s[8]) : 0.0F;
/* 1365 */         float z = (s.length >= 10) ? toFloat(s[9]) : 0.0F;
/*      */ 
/*      */         
/* 1368 */         Throttle c = new Throttle(this, toFloat(s[0]), toFloat(s[1]), toFloat(s[2]), toFloat(s[3]), toFloat(s[4]), toFloat(s[5]), toFloat(s[6]), "throttle" + this.partThrottle.size(), x, y, z);
/*      */         
/* 1370 */         this.partThrottle.add(c);
/*      */       }
/*      */     
/* 1373 */     } else if (item.equalsIgnoreCase("AddPartRotation")) {
/*      */       
/* 1375 */       String[] s = data.split("\\s*,\\s*");
/* 1376 */       if (s.length >= 7)
/*      */       {
/* 1378 */         boolean always = (s.length >= 8) ? toBool(s[7]) : true;
/*      */ 
/*      */         
/* 1381 */         RotPart c = new RotPart(this, toFloat(s[0]), toFloat(s[1]), toFloat(s[2]), toFloat(s[3]), toFloat(s[4]), toFloat(s[5]), toFloat(s[6]), always, "rotpart" + this.partThrottle.size());
/*      */         
/* 1383 */         this.partRotPart.add(c);
/*      */       }
/*      */     
/* 1386 */     } else if (item.compareTo("addpartcamera") == 0) {
/*      */       
/* 1388 */       String[] s = data.split("\\s*,\\s*");
/* 1389 */       if (s.length >= 3)
/*      */       {
/* 1391 */         boolean ys = (s.length >= 4) ? toBool(s[3]) : true;
/* 1392 */         boolean ps = (s.length >= 5) ? toBool(s[4]) : false;
/*      */         
/* 1394 */         Camera c = new Camera(this, toFloat(s[0]), toFloat(s[1]), toFloat(s[2]), 0.0F, -1.0F, 0.0F, "camera" + this.cameraList.size(), ys, ps);
/*      */         
/* 1396 */         this.cameraList.add(c);
/*      */       }
/*      */     
/* 1399 */     } else if (item.equalsIgnoreCase("AddPartWheel")) {
/*      */       
/* 1401 */       String[] s = splitParam(data);
/* 1402 */       if (s.length >= 3)
/*      */       {
/* 1404 */         float rd = (s.length >= 4) ? toFloat(s[3], -1800.0F, 1800.0F) : 0.0F;
/* 1405 */         float rx = (s.length >= 7) ? toFloat(s[4]) : 0.0F;
/* 1406 */         float ry = (s.length >= 7) ? toFloat(s[5]) : 1.0F;
/* 1407 */         float rz = (s.length >= 7) ? toFloat(s[6]) : 0.0F;
/* 1408 */         float px = (s.length >= 10) ? toFloat(s[7]) : toFloat(s[0]);
/* 1409 */         float py = (s.length >= 10) ? toFloat(s[8]) : toFloat(s[1]);
/* 1410 */         float pz = (s.length >= 10) ? toFloat(s[9]) : toFloat(s[2]);
/* 1411 */         this.partWheel.add(new PartWheel(this, toFloat(s[0]), toFloat(s[1]), toFloat(s[2]), rx, ry, rz, rd, px, py, pz, "wheel" + this.partWheel
/* 1412 */               .size()));
/*      */       
/*      */       }
/*      */     
/*      */     }
/* 1417 */     else if (item.equalsIgnoreCase("AddPartSteeringWheel")) {
/*      */       
/* 1419 */       String[] s = splitParam(data);
/* 1420 */       if (s.length >= 7)
/*      */       {
/* 1422 */         this.partSteeringWheel.add(new PartWheel(this, toFloat(s[0]), toFloat(s[1]), 
/* 1423 */               toFloat(s[2]), toFloat(s[3]), toFloat(s[4]), toFloat(s[5]), toFloat(s[6]), "steering_wheel" + this.partSteeringWheel
/* 1424 */               .size()));
/*      */       
/*      */       }
/*      */     
/*      */     }
/* 1429 */     else if (item.equalsIgnoreCase("AddTrackRoller")) {
/*      */       
/* 1431 */       String[] s = splitParam(data);
/* 1432 */       if (s.length >= 3)
/*      */       {
/* 1434 */         this.partTrackRoller.add(new TrackRoller(this, toFloat(s[0]), toFloat(s[1]), 
/* 1435 */               toFloat(s[2]), "track_roller" + this.partTrackRoller.size()));
/*      */       
/*      */       }
/*      */     
/*      */     }
/* 1440 */     else if (item.equalsIgnoreCase("AddCrawlerTrack")) {
/*      */       
/* 1442 */       this.partCrawlerTrack.add(createCrawlerTrack(data, "crawler_track" + this.partCrawlerTrack.size()));
/*      */     
/*      */     }
/* 1445 */     else if (item.equalsIgnoreCase("PivotTurnThrottle")) {
/*      */       
/* 1447 */       this.pivotTurnThrottle = toFloat(data, 0.0F, 1.0F);
/*      */     }
/* 1449 */     else if (item.equalsIgnoreCase("TrackRollerRot")) {
/*      */       
/* 1451 */       this.trackRollerRot = toFloat(data, -10000.0F, 10000.0F);
/*      */     }
/* 1453 */     else if (item.equalsIgnoreCase("PartWheelRot")) {
/*      */       
/* 1455 */       this.partWheelRot = toFloat(data, -10000.0F, 10000.0F);
/*      */     }
/* 1457 */     else if (item.compareTo("camerazoom") == 0) {
/*      */       
/* 1459 */       this.cameraZoom = toInt(data, 1, 10);
/*      */     }
/* 1461 */     else if (item.equalsIgnoreCase("DefaultFreelook")) {
/*      */       
/* 1463 */       this.defaultFreelook = toBool(data);
/*      */     }
/* 1465 */     else if (item.equalsIgnoreCase("BoundingBox")) {
/*      */       
/* 1467 */       String[] s = data.split("\\s*,\\s*");
/* 1468 */       if (s.length >= 5)
/*      */       {
/* 1470 */         float df = (s.length >= 6) ? toFloat(s[5]) : 1.0F;
/*      */         
/* 1472 */         MCH_BoundingBox c = new MCH_BoundingBox(toFloat(s[0]), toFloat(s[1]), toFloat(s[2]), toFloat(s[3]), toFloat(s[4]), df);
/*      */         
/* 1474 */         this.extraBoundingBox.add(c);
/* 1475 */         if ((c.getBoundingBox()).field_72337_e > this.markerHeight)
/*      */         {
/* 1477 */           this.markerHeight = (float)(c.getBoundingBox()).field_72337_e;
/*      */         }
/* 1479 */         this.markerWidth = (float)Math.max(this.markerWidth, Math.abs((c.getBoundingBox()).field_72336_d) / 2.0D);
/* 1480 */         this.markerWidth = (float)Math.max(this.markerWidth, Math.abs((c.getBoundingBox()).field_72340_a) / 2.0D);
/* 1481 */         this.markerWidth = (float)Math.max(this.markerWidth, Math.abs((c.getBoundingBox()).field_72334_f) / 2.0D);
/* 1482 */         this.markerWidth = (float)Math.max(this.markerWidth, Math.abs((c.getBoundingBox()).field_72339_c) / 2.0D);
/*      */         
/* 1484 */         this.bbZmin = (float)Math.min(this.bbZmin, (c.getBoundingBox()).field_72339_c);
/* 1485 */         this.bbZmax = (float)Math.min(this.bbZmax, (c.getBoundingBox()).field_72334_f);
/*      */       }
/*      */     
/* 1488 */     } else if (item.equalsIgnoreCase("RotorSpeed")) {
/*      */       
/* 1490 */       this.rotorSpeed = toFloat(data, -10000.0F, 10000.0F);
/* 1491 */       if (this.rotorSpeed > 0.01D)
/* 1492 */         this.rotorSpeed = (float)(this.rotorSpeed - 0.01D); 
/* 1493 */       if (this.rotorSpeed < -0.01D) {
/* 1494 */         this.rotorSpeed = (float)(this.rotorSpeed + 0.01D);
/*      */       }
/* 1496 */     } else if (item.equalsIgnoreCase("OnGroundPitchFactor")) {
/*      */       
/* 1498 */       this.onGroundPitchFactor = toFloat(data, 0.0F, 180.0F);
/*      */     }
/* 1500 */     else if (item.equalsIgnoreCase("OnGroundRollFactor")) {
/*      */       
/* 1502 */       this.onGroundRollFactor = toFloat(data, 0.0F, 180.0F);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public CrawlerTrack createCrawlerTrack(String data, String name) {
/* 1508 */     String[] s = splitParam(data);
/* 1509 */     int PC = s.length - 3;
/*      */     
/* 1511 */     boolean REV = toBool(s[0]);
/* 1512 */     float LEN = toFloat(s[1], 0.001F, 1000.0F) * 0.9F;
/* 1513 */     float Z = toFloat(s[2]);
/*      */     
/* 1515 */     if (PC < 4)
/*      */     {
/* 1517 */       return null;
/*      */     }
/* 1519 */     double[] cx = new double[PC];
/* 1520 */     double[] cy = new double[PC];
/* 1521 */     for (int i = 0; i < PC; i++) {
/*      */       
/* 1523 */       int idx = !REV ? i : (PC - i - 1);
/* 1524 */       String[] xy = splitParamSlash(s[3 + idx]);
/* 1525 */       cx[i] = toFloat(xy[0]);
/* 1526 */       cy[i] = toFloat(xy[1]);
/*      */     } 
/*      */     
/* 1529 */     List<CrawlerTrackPrm> lp = new ArrayList<>();
/*      */     
/* 1531 */     lp.add(new CrawlerTrackPrm(this, (float)cx[0], (float)cy[0]));
/* 1532 */     double dist = 0.0D; int j;
/* 1533 */     for (j = 0; j < PC; j++) {
/*      */       
/* 1535 */       double x = cx[(j + 1) % PC] - cx[j];
/* 1536 */       double y = cy[(j + 1) % PC] - cy[j];
/* 1537 */       dist += Math.sqrt(x * x + y * y);
/* 1538 */       double dist2 = dist;
/* 1539 */       for (int k = 1; dist >= LEN; k++) {
/*      */         
/* 1541 */         lp.add(new CrawlerTrackPrm(this, (float)(cx[j] + x * (LEN * k) / dist2), (float)(cy[j] + y * (LEN * k) / dist2)));
/*      */ 
/*      */         
/* 1544 */         dist -= LEN;
/*      */       } 
/*      */     } 
/*      */     
/* 1548 */     for (j = 0; j < lp.size(); j++) {
/*      */       
/* 1550 */       CrawlerTrackPrm pp = lp.get((j + lp.size() - 1) % lp.size());
/* 1551 */       CrawlerTrackPrm cp = lp.get(j);
/* 1552 */       CrawlerTrackPrm np = lp.get((j + 1) % lp.size());
/*      */       
/* 1554 */       float pr = (float)(Math.atan2((pp.x - cp.x), (pp.y - cp.y)) * 180.0D / Math.PI);
/* 1555 */       float nr = (float)(Math.atan2((np.x - cp.x), (np.y - cp.y)) * 180.0D / Math.PI);
/* 1556 */       float ppr = (pr + 360.0F) % 360.0F;
/* 1557 */       float nnr = nr + 180.0F;
/* 1558 */       if (nnr < ppr - 0.3D || nnr > ppr + 0.3D)
/*      */       {
/* 1560 */         if (nnr - ppr < 100.0F && nnr - ppr > -100.0F)
/*      */         {
/* 1562 */           nnr = (nnr + ppr) / 2.0F;
/*      */         }
/*      */       }
/* 1565 */       cp.r = nnr;
/*      */     } 
/*      */     
/* 1568 */     CrawlerTrack c = new CrawlerTrack(this, name);
/* 1569 */     c.len = LEN;
/* 1570 */     c.cx = cx;
/* 1571 */     c.cy = cy;
/* 1572 */     c.lp = lp;
/* 1573 */     c.z = Z;
/* 1574 */     c.side = (Z >= 0.0F) ? 1 : 0;
/*      */     
/* 1576 */     return c;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getTextureName() {
/* 1581 */     String s = this.textureNameList.get(this.textureCount);
/* 1582 */     this.textureCount = (this.textureCount + 1) % this.textureNameList.size();
/* 1583 */     return s;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getNextTextureName(String base) {
/* 1588 */     if (this.textureNameList.size() >= 2)
/*      */     {
/* 1590 */       for (int i = 0; i < this.textureNameList.size(); i++) {
/*      */         
/* 1592 */         String s = this.textureNameList.get(i);
/* 1593 */         if (s.equalsIgnoreCase(base)) {
/*      */           
/* 1595 */           i = (i + 1) % this.textureNameList.size();
/* 1596 */           return this.textureNameList.get(i);
/*      */         } 
/*      */       } 
/*      */     }
/* 1600 */     return base;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String[] getCannotReloadItem() {
/* 1610 */     return new String[] { "DisplayName", "AddDisplayName", "ItemID", "AddRecipe", "AddShapelessRecipe", "InventorySize", "Sound", "UAV", "SmallUAV", "TargetDrone", "Category" };
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canReloadItem(String item) {
/* 1620 */     String[] ignoreItems = getCannotReloadItem();
/* 1621 */     for (String s : ignoreItems) {
/*      */       
/* 1623 */       if (s.equalsIgnoreCase(item))
/*      */       {
/* 1625 */         return false;
/*      */       }
/*      */     } 
/* 1628 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public class Camera
/*      */     extends DrawnPart
/*      */   {
/*      */     public final boolean yawSync;
/*      */     
/*      */     public final boolean pitchSync;
/*      */     
/*      */     public Camera(MCH_AircraftInfo paramMCH_AircraftInfo, float px, float py, float pz, float rx, float ry, float rz, String name, boolean ys, boolean ps) {
/* 1640 */       super(paramMCH_AircraftInfo, px, py, pz, rx, ry, rz, name);
/* 1641 */       this.yawSync = ys;
/* 1642 */       this.pitchSync = ps;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public class CameraPosition
/*      */   {
/*      */     public final Vec3d pos;
/*      */     
/*      */     public final boolean fixRot;
/*      */     public final float yaw;
/*      */     public final float pitch;
/*      */     
/*      */     public CameraPosition(MCH_AircraftInfo paramMCH_AircraftInfo, Vec3d vec3, boolean fixRot, float yaw, float pitch) {
/* 1656 */       this.pos = vec3;
/* 1657 */       this.fixRot = fixRot;
/* 1658 */       this.yaw = yaw;
/* 1659 */       this.pitch = pitch;
/*      */     }
/*      */ 
/*      */     
/*      */     public CameraPosition(MCH_AircraftInfo paramMCH_AircraftInfo, Vec3d vec3) {
/* 1664 */       this(paramMCH_AircraftInfo, vec3, false, 0.0F, 0.0F);
/*      */     }
/*      */ 
/*      */     
/*      */     public CameraPosition(MCH_AircraftInfo paramMCH_AircraftInfo) {
/* 1669 */       this(paramMCH_AircraftInfo, new Vec3d(0.0D, 0.0D, 0.0D));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public class Canopy
/*      */     extends DrawnPart
/*      */   {
/*      */     public final float maxRotFactor;
/*      */     
/*      */     public final boolean isSlide;
/*      */     
/*      */     public Canopy(MCH_AircraftInfo paramMCH_AircraftInfo, float px, float py, float pz, float rx, float ry, float rz, float mr, String name, boolean slide) {
/* 1682 */       super(paramMCH_AircraftInfo, px, py, pz, rx, ry, rz, name);
/* 1683 */       this.maxRotFactor = mr;
/* 1684 */       this.isSlide = slide;
/*      */     }
/*      */   }
/*      */   
/*      */   public class CrawlerTrack
/*      */     extends DrawnPart {
/* 1690 */     public float len = 0.35F;
/*      */     
/*      */     public double[] cx;
/*      */     public double[] cy;
/*      */     public List<MCH_AircraftInfo.CrawlerTrackPrm> lp;
/*      */     public float z;
/*      */     public int side;
/*      */     
/*      */     public CrawlerTrack(MCH_AircraftInfo paramMCH_AircraftInfo, String name) {
/* 1699 */       super(paramMCH_AircraftInfo, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, name);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public class CrawlerTrackPrm
/*      */   {
/*      */     float x;
/*      */     float y;
/*      */     float nx;
/*      */     float ny;
/*      */     float r;
/*      */     
/*      */     public CrawlerTrackPrm(MCH_AircraftInfo paramMCH_AircraftInfo, float x, float y) {
/* 1713 */       this.x = x;
/* 1714 */       this.y = y;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public class DrawnPart
/*      */   {
/*      */     public final Vec3d pos;
/*      */     
/*      */     public final Vec3d rot;
/*      */     
/*      */     public final String modelName;
/*      */     public _IModelCustom model;
/*      */     
/*      */     public DrawnPart(MCH_AircraftInfo paramMCH_AircraftInfo, float px, float py, float pz, float rx, float ry, float rz, String name) {
/* 1729 */       this.pos = new Vec3d(px, py, pz);
/* 1730 */       this.rot = new Vec3d(rx, ry, rz);
/* 1731 */       this.modelName = name;
/* 1732 */       this.model = null;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public class Flare
/*      */   {
/* 1743 */     public int[] types = new int[0];
/* 1744 */     public Vec3d pos = new Vec3d(0.0D, 0.0D, 0.0D);
/*      */     
/*      */     public Flare(MCH_AircraftInfo paramMCH_AircraftInfo) {}
/*      */   }
/*      */   
/*      */   public class Hatch
/*      */     extends DrawnPart
/*      */   {
/*      */     public final float maxRotFactor;
/*      */     public final float maxRot;
/*      */     public final boolean isSlide;
/*      */     
/*      */     public Hatch(MCH_AircraftInfo paramMCH_AircraftInfo, float px, float py, float pz, float rx, float ry, float rz, float mr, String name, boolean slide) {
/* 1757 */       super(paramMCH_AircraftInfo, px, py, pz, rx, ry, rz, name);
/* 1758 */       this.maxRot = mr;
/* 1759 */       this.maxRotFactor = this.maxRot / 90.0F;
/* 1760 */       this.isSlide = slide;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public class LandingGear
/*      */     extends DrawnPart
/*      */   {
/*      */     public Vec3d slide;
/*      */     public final float maxRotFactor;
/*      */     public boolean enableRot2;
/*      */     public Vec3d rot2;
/*      */     public float maxRotFactor2;
/*      */     public final boolean reverse;
/*      */     public final boolean hatch;
/*      */     
/*      */     public LandingGear(MCH_AircraftInfo paramMCH_AircraftInfo, float x, float y, float z, float rx, float ry, float rz, String model, float maxRotF, boolean rev, boolean isHatch) {
/* 1777 */       super(paramMCH_AircraftInfo, x, y, z, rx, ry, rz, model);
/* 1778 */       this.slide = null;
/* 1779 */       this.maxRotFactor = maxRotF;
/* 1780 */       this.enableRot2 = false;
/* 1781 */       this.rot2 = new Vec3d(0.0D, 0.0D, 0.0D);
/* 1782 */       this.maxRotFactor2 = 0.0F;
/* 1783 */       this.reverse = rev;
/* 1784 */       this.hatch = isHatch;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public class ParticleSplash
/*      */   {
/*      */     public final int num;
/*      */     
/*      */     public final float acceleration;
/*      */     public final float size;
/*      */     public final Vec3d pos;
/*      */     public final int age;
/*      */     public final float motionY;
/*      */     public final float gravity;
/*      */     
/*      */     public ParticleSplash(MCH_AircraftInfo paramMCH_AircraftInfo, Vec3d v, int nm, float siz, float acc, int ag, float my, float gr) {
/* 1801 */       this.num = nm;
/* 1802 */       this.pos = v;
/* 1803 */       this.size = siz;
/* 1804 */       this.acceleration = acc;
/* 1805 */       this.age = ag;
/* 1806 */       this.motionY = my;
/* 1807 */       this.gravity = gr;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public class PartWeapon
/*      */     extends DrawnPart
/*      */   {
/*      */     public final String[] name;
/*      */     
/*      */     public final boolean rotBarrel;
/*      */     public final boolean isMissile;
/*      */     public final boolean hideGM;
/*      */     public final boolean yaw;
/*      */     public final boolean pitch;
/*      */     public final float recoilBuf;
/*      */     public List<MCH_AircraftInfo.PartWeaponChild> child;
/*      */     public final boolean turret;
/*      */     
/*      */     public PartWeapon(MCH_AircraftInfo paramMCH_AircraftInfo, String[] name, boolean rotBrl, boolean missile, boolean hgm, boolean y, boolean p, float px, float py, float pz, String modelName, float rx, float ry, float rz, float rb, boolean turret) {
/* 1827 */       super(paramMCH_AircraftInfo, px, py, pz, rx, ry, rz, modelName);
/* 1828 */       this.name = name;
/* 1829 */       this.rotBarrel = rotBrl;
/* 1830 */       this.isMissile = missile;
/* 1831 */       this.hideGM = hgm;
/* 1832 */       this.yaw = y;
/* 1833 */       this.pitch = p;
/* 1834 */       this.recoilBuf = rb;
/* 1835 */       this.child = new ArrayList<>();
/* 1836 */       this.turret = turret;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public class PartWeaponChild
/*      */     extends DrawnPart
/*      */   {
/*      */     public final String[] name;
/*      */     public final boolean yaw;
/*      */     public final boolean pitch;
/*      */     public final float recoilBuf;
/*      */     
/*      */     public PartWeaponChild(MCH_AircraftInfo paramMCH_AircraftInfo, String[] name, boolean y, boolean p, float px, float py, float pz, String modelName, float rx, float ry, float rz, float rb) {
/* 1850 */       super(paramMCH_AircraftInfo, px, py, pz, rx, ry, rz, modelName);
/* 1851 */       this.name = name;
/* 1852 */       this.yaw = y;
/* 1853 */       this.pitch = p;
/* 1854 */       this.recoilBuf = rb;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public class PartWheel
/*      */     extends DrawnPart
/*      */   {
/*      */     final float rotDir;
/*      */     final Vec3d pos2;
/*      */     
/*      */     public PartWheel(MCH_AircraftInfo paramMCH_AircraftInfo, float px, float py, float pz, float rx, float ry, float rz, float rd, float px2, float py2, float pz2, String name) {
/* 1866 */       super(paramMCH_AircraftInfo, px, py, pz, rx, ry, rz, name);
/* 1867 */       this.rotDir = rd;
/* 1868 */       this.pos2 = new Vec3d(px2, py2, pz2);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public PartWheel(MCH_AircraftInfo paramMCH_AircraftInfo, float px, float py, float pz, float rx, float ry, float rz, float rd, String name) {
/* 1874 */       this(paramMCH_AircraftInfo, px, py, pz, rx, ry, rz, rd, px, py, pz, name);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public class RepellingHook
/*      */   {
/*      */     final Vec3d pos;
/*      */     final int interval;
/*      */     
/*      */     public RepellingHook(MCH_AircraftInfo paramMCH_AircraftInfo, Vec3d pos, int inv) {
/* 1885 */       this.pos = pos;
/* 1886 */       this.interval = inv;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public class RideRack
/*      */   {
/*      */     public final String name;
/*      */     public final int rackID;
/*      */     
/*      */     public RideRack(MCH_AircraftInfo paramMCH_AircraftInfo, String n, int id) {
/* 1897 */       this.name = n;
/* 1898 */       this.rackID = id;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public class RotPart
/*      */     extends DrawnPart
/*      */   {
/*      */     public final float rotSpeed;
/*      */     public final boolean rotAlways;
/*      */     
/*      */     public RotPart(MCH_AircraftInfo paramMCH_AircraftInfo, float px, float py, float pz, float rx, float ry, float rz, float mr, boolean a, String name) {
/* 1910 */       super(paramMCH_AircraftInfo, px, py, pz, rx, ry, rz, name);
/* 1911 */       this.rotSpeed = mr;
/* 1912 */       this.rotAlways = a;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public class SearchLight
/*      */   {
/*      */     public final int colorStart;
/*      */     
/*      */     public final int colorEnd;
/*      */     public final Vec3d pos;
/*      */     public final float height;
/*      */     public final float width;
/*      */     public final float angle;
/*      */     public final boolean fixDir;
/*      */     public final float yaw;
/*      */     public final float pitch;
/*      */     public final boolean steering;
/*      */     public final float stRot;
/*      */     
/*      */     public SearchLight(MCH_AircraftInfo paramMCH_AircraftInfo, Vec3d pos, int cs, int ce, float h, float w, boolean fix, float y, float p, boolean st, float stRot) {
/* 1933 */       this.colorStart = cs;
/* 1934 */       this.colorEnd = ce;
/* 1935 */       this.pos = pos;
/* 1936 */       this.height = h;
/* 1937 */       this.width = w;
/* 1938 */       this.angle = (float)(Math.atan2((w / 2.0F), h) * 180.0D / Math.PI);
/* 1939 */       this.fixDir = fix;
/* 1940 */       this.steering = st;
/* 1941 */       this.yaw = y;
/* 1942 */       this.pitch = p;
/* 1943 */       this.stRot = stRot;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public class Throttle
/*      */     extends DrawnPart
/*      */   {
/*      */     public final Vec3d slide;
/*      */     public final float rot2;
/*      */     
/*      */     public Throttle(MCH_AircraftInfo paramMCH_AircraftInfo, float px, float py, float pz, float rx, float ry, float rz, float rot, String name, float px2, float py2, float pz2) {
/* 1955 */       super(paramMCH_AircraftInfo, px, py, pz, rx, ry, rz, name);
/* 1956 */       this.rot2 = rot;
/* 1957 */       this.slide = new Vec3d(px2, py2, pz2);
/*      */     }
/*      */   }
/*      */   
/*      */   public class TrackRoller
/*      */     extends DrawnPart
/*      */   {
/*      */     final int side;
/*      */     
/*      */     public TrackRoller(MCH_AircraftInfo paramMCH_AircraftInfo, float px, float py, float pz, String name) {
/* 1967 */       super(paramMCH_AircraftInfo, px, py, pz, 0.0F, 0.0F, 0.0F, name);
/* 1968 */       this.side = (px >= 0.0F) ? 1 : 0;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public class Weapon
/*      */   {
/*      */     public final Vec3d pos;
/*      */     
/*      */     public final float yaw;
/*      */     public final float pitch;
/*      */     public final boolean canUsePilot;
/*      */     public final int seatID;
/*      */     public final float defaultYaw;
/*      */     public final float minYaw;
/*      */     public final float maxYaw;
/*      */     public final float minPitch;
/*      */     public final float maxPitch;
/*      */     public final boolean turret;
/*      */     
/*      */     public Weapon(MCH_AircraftInfo paramMCH_AircraftInfo, float x, float y, float z, float yaw, float pitch, boolean canPirot, int seatId, float defy, float mny, float mxy, float mnp, float mxp, boolean turret) {
/* 1989 */       this.pos = new Vec3d(x, y, z);
/* 1990 */       this.yaw = yaw;
/* 1991 */       this.pitch = pitch;
/* 1992 */       this.canUsePilot = canPirot;
/* 1993 */       this.seatID = seatId;
/* 1994 */       this.defaultYaw = defy;
/* 1995 */       this.minYaw = mny;
/* 1996 */       this.maxYaw = mxy;
/* 1997 */       this.minPitch = mnp;
/* 1998 */       this.maxPitch = mxp;
/* 1999 */       this.turret = turret;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public class WeaponBay
/*      */     extends DrawnPart
/*      */   {
/*      */     public final float maxRotFactor;
/*      */     public final boolean isSlide;
/*      */     private final String weaponName;
/*      */     public Integer[] weaponIds;
/*      */     
/*      */     public WeaponBay(MCH_AircraftInfo paramMCH_AircraftInfo, String wn, float px, float py, float pz, float rx, float ry, float rz, float mr, String name, boolean slide) {
/* 2013 */       super(paramMCH_AircraftInfo, px, py, pz, rx, ry, rz, name);
/* 2014 */       this.maxRotFactor = mr;
/* 2015 */       this.isSlide = slide;
/* 2016 */       this.weaponName = wn;
/* 2017 */       this.weaponIds = new Integer[0];
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public class WeaponSet
/*      */   {
/*      */     public final String type;
/*      */     public ArrayList<MCH_AircraftInfo.Weapon> weapons;
/*      */     
/*      */     public WeaponSet(MCH_AircraftInfo paramMCH_AircraftInfo, String t) {
/* 2028 */       this.type = t;
/* 2029 */       this.weapons = new ArrayList<>();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public class Wheel
/*      */   {
/*      */     public final float size;
/*      */     public final Vec3d pos;
/*      */     
/*      */     public Wheel(MCH_AircraftInfo paramMCH_AircraftInfo, Vec3d v, float sz) {
/* 2040 */       this.pos = v;
/* 2041 */       this.size = sz;
/*      */     }
/*      */ 
/*      */     
/*      */     public Wheel(MCH_AircraftInfo paramMCH_AircraftInfo, Vec3d v) {
/* 2046 */       this(paramMCH_AircraftInfo, v, 1.0F);
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\aircraft\MCH_AircraftInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */