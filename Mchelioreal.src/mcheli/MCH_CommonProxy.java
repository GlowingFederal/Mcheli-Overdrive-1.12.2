/*     */ package mcheli;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.util.List;
/*     */ import mcheli.__helper.MCH_SoundEvents;
/*     */ import mcheli.__helper.MCH_Utils;
/*     */ import mcheli.__helper.addon.AddonManager;
/*     */ import mcheli.__helper.addon.AddonPack;
/*     */ import mcheli.__helper.addon.AddonResourceLocation;
/*     */ import mcheli.__helper.info.ContentRegistries;
/*     */ import mcheli.aircraft.MCH_AircraftInfo;
/*     */ import mcheli.aircraft.MCH_EntityAircraft;
/*     */ import mcheli.aircraft.MCH_SoundUpdater;
/*     */ import mcheli.helicopter.MCH_HeliInfo;
/*     */ import mcheli.plane.MCP_PlaneInfo;
/*     */ import mcheli.tank.MCH_TankInfo;
/*     */ import mcheli.vehicle.MCH_VehicleInfo;
/*     */ import mcheli.weapon.MCH_WeaponInfo;
/*     */ import mcheli.wrapper.W_LanguageRegistry;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.text.ITextComponent;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MCH_CommonProxy
/*     */ {
/*  32 */   protected static String[] CONTENT_DIRS = new String[] { "helicopters", "planes", "tanks", "vehicles", "weapons", "throwable" };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  37 */   public MCH_Config config = null;
/*     */   
/*     */   public String lastConfigFileName;
/*     */ 
/*     */   
/*     */   public String getDataDir() {
/*  43 */     return MCH_Utils.getServer().func_71270_I();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerRenderer() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerBlockRenderer() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerModels() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerModelsHeli(MCH_HeliInfo info, boolean reload) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerModelsPlane(MCP_PlaneInfo info, boolean reload) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerModelsVehicle(MCH_VehicleInfo info, boolean reload) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerModelsTank(MCH_TankInfo info, boolean reload) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerClientTick() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerServerTick() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isRemote() {
/*  89 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public String side() {
/*  94 */     return "Server";
/*     */   }
/*     */ 
/*     */   
/*     */   public MCH_SoundUpdater CreateSoundUpdater(MCH_EntityAircraft aircraft) {
/*  99 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerSounds() {
/* 104 */     MCH_SoundEvents.registerSoundEventName("alert");
/* 105 */     MCH_SoundEvents.registerSoundEventName("locked");
/* 106 */     MCH_SoundEvents.registerSoundEventName("gltd");
/* 107 */     MCH_SoundEvents.registerSoundEventName("zoom");
/* 108 */     MCH_SoundEvents.registerSoundEventName("ng");
/* 109 */     MCH_SoundEvents.registerSoundEventName("a-10_snd");
/* 110 */     MCH_SoundEvents.registerSoundEventName("gau-8_snd");
/* 111 */     MCH_SoundEvents.registerSoundEventName("hit");
/* 112 */     MCH_SoundEvents.registerSoundEventName("helidmg");
/* 113 */     MCH_SoundEvents.registerSoundEventName("heli");
/* 114 */     MCH_SoundEvents.registerSoundEventName("plane");
/* 115 */     MCH_SoundEvents.registerSoundEventName("plane_cc");
/* 116 */     MCH_SoundEvents.registerSoundEventName("plane_cv");
/* 117 */     MCH_SoundEvents.registerSoundEventName("chain");
/* 118 */     MCH_SoundEvents.registerSoundEventName("chain_ct");
/* 119 */     MCH_SoundEvents.registerSoundEventName("eject_seat");
/* 120 */     MCH_SoundEvents.registerSoundEventName("fim92_snd");
/* 121 */     MCH_SoundEvents.registerSoundEventName("fim92_reload");
/* 122 */     MCH_SoundEvents.registerSoundEventName("lockon");
/* 123 */     MCH_SoundEvents.registerSoundEventName("wrench");
/*     */     
/* 125 */     for (MCH_WeaponInfo info : ContentRegistries.weapon().values())
/*     */     {
/* 127 */       MCH_SoundEvents.registerSoundEventName(info.soundFileName);
/*     */     }
/*     */     
/* 130 */     for (MCH_AircraftInfo info : ContentRegistries.plane().values()) {
/*     */       
/* 132 */       if (!info.soundMove.isEmpty()) {
/* 133 */         MCH_SoundEvents.registerSoundEventName(info.soundMove);
/*     */       }
/*     */     } 
/* 136 */     for (MCH_AircraftInfo info : ContentRegistries.heli().values()) {
/*     */       
/* 138 */       if (!info.soundMove.isEmpty()) {
/* 139 */         MCH_SoundEvents.registerSoundEventName(info.soundMove);
/*     */       }
/*     */     } 
/* 142 */     for (MCH_AircraftInfo info : ContentRegistries.tank().values()) {
/*     */       
/* 144 */       if (!info.soundMove.isEmpty()) {
/* 145 */         MCH_SoundEvents.registerSoundEventName(info.soundMove);
/*     */       }
/*     */     } 
/* 148 */     for (MCH_AircraftInfo info : ContentRegistries.vehicle().values()) {
/*     */       
/* 150 */       if (!info.soundMove.isEmpty())
/*     */       {
/* 152 */         MCH_SoundEvents.registerSoundEventName(info.soundMove);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void loadConfig(String fileName) {
/* 159 */     this.lastConfigFileName = fileName;
/*     */     
/* 161 */     this.config = new MCH_Config("./", fileName);
/*     */     
/* 163 */     this.config.load();
/* 164 */     this.config.write();
/*     */   }
/*     */ 
/*     */   
/*     */   public void reconfig() {
/* 169 */     MCH_Lib.DbgLog(false, "MCH_CommonProxy.reconfig()", new Object[0]);
/* 170 */     loadConfig(this.lastConfigFileName);
/*     */   }
/*     */ 
/*     */   
/*     */   public void save() {
/* 175 */     MCH_Lib.DbgLog(false, "MCH_CommonProxy.save()", new Object[0]);
/* 176 */     this.config.write();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void reloadHUD() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Entity getClientPlayer() {
/* 189 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCreativeDigDelay(int n) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void init() {}
/*     */ 
/*     */   
/*     */   public boolean isFirstPerson() {
/* 202 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSinglePlayer() {
/* 214 */     return MCH_Utils.getServer().func_71264_H();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void readClientModList() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void printChatMessage(ITextComponent chat, int showTime, int pos) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void hitBullet() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void clientLocked() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRenderEntityDistanceWeight(double renderDistWeight) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public List<AddonPack> loadAddonPacks(File addonDir) {
/* 240 */     return AddonManager.loadAddons(addonDir);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onLoadStartAddons(int addonSize) {}
/*     */ 
/*     */   
/*     */   public void onLoadStepAddon(String addonDomain) {
/* 249 */     MCH_Utils.logger().debug("addon(" + addonDomain + ") loading start.");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onLoadFinishAddons() {}
/*     */ 
/*     */   
/*     */   public void onLoadStartContents(String typeName, int fileSize) {
/* 258 */     MCH_Utils.logger().debug("content type(" + typeName + ") loading start. steps:" + fileSize);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onLoadFinishContents(String typeName) {}
/*     */ 
/*     */   
/*     */   public void onParseStartFile(AddonResourceLocation location) {
/* 267 */     MCH_Utils.logger().debug("content file(" + location + ") loading start.");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onParseFinishFile(AddonResourceLocation location) {}
/*     */ 
/*     */   
/*     */   public boolean canLoadContentDirName(String dir) {
/* 276 */     return MCH_Utils.inArray((Object[])CONTENT_DIRS, dir);
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateGeneratedLanguage() {
/* 281 */     W_LanguageRegistry.clear();
/*     */   }
/*     */   
/*     */   public void registerRecipeDescriptions() {}
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\MCH_CommonProxy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */