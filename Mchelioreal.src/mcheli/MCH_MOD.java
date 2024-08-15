/*     */ package mcheli;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.util.Calendar;
/*     */ import java.util.Map;
/*     */ import mcheli.__helper.MCH_Blocks;
/*     */ import mcheli.__helper.MCH_Entities;
/*     */ import mcheli.__helper.MCH_Items;
/*     */ import mcheli.__helper.MCH_Logger;
/*     */ import mcheli.__helper.MCH_Utils;
/*     */ import mcheli.__helper.info.ContentRegistries;
/*     */ import mcheli.aircraft.MCH_EntityHide;
/*     */ import mcheli.aircraft.MCH_EntityHitBox;
/*     */ import mcheli.aircraft.MCH_EntitySeat;
/*     */ import mcheli.aircraft.MCH_ItemAircraft;
/*     */ import mcheli.aircraft.MCH_ItemFuel;
/*     */ import mcheli.block.MCH_DraftingTableBlock;
/*     */ import mcheli.block.MCH_DraftingTableTileEntity;
/*     */ import mcheli.chain.MCH_EntityChain;
/*     */ import mcheli.chain.MCH_ItemChain;
/*     */ import mcheli.command.MCH_Command;
/*     */ import mcheli.container.MCH_EntityContainer;
/*     */ import mcheli.container.MCH_ItemContainer;
/*     */ import mcheli.flare.MCH_EntityFlare;
/*     */ import mcheli.gltd.MCH_EntityGLTD;
/*     */ import mcheli.gltd.MCH_ItemGLTD;
/*     */ import mcheli.gui.MCH_GuiCommonHandler;
/*     */ import mcheli.helicopter.MCH_EntityHeli;
/*     */ import mcheli.helicopter.MCH_HeliInfo;
/*     */ import mcheli.helicopter.MCH_ItemHeli;
/*     */ import mcheli.lweapon.MCH_ItemLightWeaponBase;
/*     */ import mcheli.lweapon.MCH_ItemLightWeaponBullet;
/*     */ import mcheli.mob.MCH_EntityGunner;
/*     */ import mcheli.mob.MCH_ItemSpawnGunner;
/*     */ import mcheli.parachute.MCH_EntityParachute;
/*     */ import mcheli.parachute.MCH_ItemParachute;
/*     */ import mcheli.plane.MCP_EntityPlane;
/*     */ import mcheli.plane.MCP_ItemPlane;
/*     */ import mcheli.plane.MCP_PlaneInfo;
/*     */ import mcheli.tank.MCH_EntityTank;
/*     */ import mcheli.tank.MCH_ItemTank;
/*     */ import mcheli.tank.MCH_TankInfo;
/*     */ import mcheli.throwable.MCH_EntityThrowable;
/*     */ import mcheli.throwable.MCH_ItemThrowable;
/*     */ import mcheli.throwable.MCH_ThrowableInfo;
/*     */ import mcheli.tool.MCH_ItemWrench;
/*     */ import mcheli.tool.rangefinder.MCH_ItemRangeFinder;
/*     */ import mcheli.uav.MCH_EntityUavStation;
/*     */ import mcheli.uav.MCH_ItemUavStation;
/*     */ import mcheli.vehicle.MCH_EntityVehicle;
/*     */ import mcheli.vehicle.MCH_ItemVehicle;
/*     */ import mcheli.vehicle.MCH_VehicleInfo;
/*     */ import mcheli.weapon.MCH_EntityA10;
/*     */ import mcheli.weapon.MCH_EntityAAMissile;
/*     */ import mcheli.weapon.MCH_EntityASMissile;
/*     */ import mcheli.weapon.MCH_EntityATMissile;
/*     */ import mcheli.weapon.MCH_EntityBomb;
/*     */ import mcheli.weapon.MCH_EntityBullet;
/*     */ import mcheli.weapon.MCH_EntityDispensedItem;
/*     */ import mcheli.weapon.MCH_EntityMarkerRocket;
/*     */ import mcheli.weapon.MCH_EntityRocket;
/*     */ import mcheli.weapon.MCH_EntityTorpedo;
/*     */ import mcheli.weapon.MCH_EntityTvMissile;
/*     */ import mcheli.weapon.MCH_WeaponInfoManager;
/*     */ import mcheli.wrapper.NetworkMod;
/*     */ import mcheli.wrapper.W_Item;
/*     */ import mcheli.wrapper.W_ItemList;
/*     */ import mcheli.wrapper.W_LanguageRegistry;
/*     */ import mcheli.wrapper.W_NetworkRegistry;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.command.CommandHandler;
/*     */ import net.minecraft.command.ICommand;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemBlock;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.fml.common.FMLCommonHandler;
/*     */ import net.minecraftforge.fml.common.Loader;
/*     */ import net.minecraftforge.fml.common.Mod;
/*     */ import net.minecraftforge.fml.common.Mod.EventHandler;
/*     */ import net.minecraftforge.fml.common.Mod.Instance;
/*     */ import net.minecraftforge.fml.common.SidedProxy;
/*     */ import net.minecraftforge.fml.common.event.FMLInitializationEvent;
/*     */ import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
/*     */ import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
/*     */ import net.minecraftforge.fml.common.event.FMLServerStartedEvent;
/*     */ import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
/*     */ import net.minecraftforge.fml.common.network.IGuiHandler;
/*     */ import net.minecraftforge.fml.common.registry.GameRegistry;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Mod(modid = "mcheli", name = "MC Helicopter MOD", dependencies = "required-after:forge@[14.23.5.2854,)", guiFactory = "mcheli.__helper.config.MODGuiFactory")
/*     */ @NetworkMod(clientSideRequired = true, serverSideRequired = false)
/*     */ public class MCH_MOD
/*     */ {
/*     */   public static final String MOD_ID = "mcheli";
/*     */   @Deprecated
/*     */   public static final String DOMAIN = "mcheli";
/*     */   public static final String MOD_NAME = "MC Helicopter MOD";
/*     */   public static final String MCVER = "1.12.2";
/* 103 */   public static String VER = "";
/*     */   
/*     */   public static final String MOD_CH = "MCHeli_CH";
/*     */   
/*     */   public static final String ADDON_FOLDER_NAME = "mcheli_addons";
/*     */   
/*     */   @Instance("mcheli")
/*     */   public static MCH_MOD instance;
/*     */   
/*     */   @SidedProxy(clientSide = "mcheli.MCH_ClientProxy", serverSide = "mcheli.MCH_CommonProxy")
/*     */   public static MCH_CommonProxy proxy;
/*     */   
/* 115 */   public static MCH_PacketHandler packetHandler = new MCH_PacketHandler();
/*     */   
/*     */   public static MCH_Config config;
/*     */   
/*     */   public static String sourcePath;
/*     */   
/*     */   private static File sourceFile;
/*     */   
/*     */   private static File addonDir;
/*     */   public static MCH_InvisibleItem invisibleItem;
/*     */   public static MCH_ItemGLTD itemGLTD;
/*     */   public static MCH_ItemLightWeaponBullet itemStingerBullet;
/*     */   public static MCH_ItemLightWeaponBase itemStinger;
/*     */   public static MCH_ItemLightWeaponBullet itemJavelinBullet;
/*     */   public static MCH_ItemLightWeaponBase itemJavelin;
/*     */   public static MCH_ItemUavStation[] itemUavStation;
/*     */   public static MCH_ItemParachute itemParachute;
/*     */   public static MCH_ItemContainer itemContainer;
/*     */   public static MCH_ItemChain itemChain;
/*     */   public static MCH_ItemFuel itemFuel;
/*     */   public static MCH_ItemWrench itemWrench;
/*     */   public static MCH_ItemRangeFinder itemRangeFinder;
/*     */   public static MCH_ItemSpawnGunner itemSpawnGunnerVsPlayer;
/*     */   public static MCH_ItemSpawnGunner itemSpawnGunnerVsMonster;
/*     */   public static MCH_CreativeTabs creativeTabs;
/*     */   public static MCH_CreativeTabs creativeTabsHeli;
/*     */   public static MCH_CreativeTabs creativeTabsPlane;
/*     */   public static MCH_CreativeTabs creativeTabsTank;
/*     */   public static MCH_CreativeTabs creativeTabsVehicle;
/*     */   public static MCH_DraftingTableBlock blockDraftingTable;
/*     */   public static MCH_DraftingTableBlock blockDraftingTableLit;
/*     */   public static ItemBlock itemDraftingTable;
/*     */   public static Item sampleHelmet;
/* 148 */   private static Boolean isSep01 = null;
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void PreInit(FMLPreInitializationEvent evt) {
/* 154 */     MCH_Logger.setLogger(evt.getModLog());
/* 155 */     VER = Loader.instance().activeModContainer().getVersion();
/*     */     
/* 157 */     MCH_Lib.init();
/* 158 */     MCH_Lib.Log("MC Ver:1.12.2 MOD Ver:" + VER + "", new Object[0]);
/* 159 */     MCH_Lib.Log("Start load...", new Object[0]);
/*     */ 
/*     */     
/* 162 */     sourcePath = evt.getSourceFile().getPath();
/* 163 */     sourceFile = evt.getSourceFile();
/* 164 */     addonDir = new File(evt.getModConfigurationDirectory().getParentFile(), "/mcheli_addons/");
/*     */     
/* 166 */     MCH_Lib.Log("SourcePath: " + sourcePath, new Object[0]);
/* 167 */     MCH_Lib.Log("CurrentDirectory:" + (new File(".")).getAbsolutePath(), new Object[0]);
/*     */     
/* 169 */     proxy.init();
/* 170 */     creativeTabs = new MCH_CreativeTabs("MC Heli Item");
/* 171 */     creativeTabsHeli = new MCH_CreativeTabs("MC Heli Helicopters");
/* 172 */     creativeTabsPlane = new MCH_CreativeTabs("MC Heli Planes");
/* 173 */     creativeTabsTank = new MCH_CreativeTabs("MC Heli Tanks");
/* 174 */     creativeTabsVehicle = new MCH_CreativeTabs("MC Heli Vehicles");
/*     */     
/* 176 */     W_ItemList.init();
/*     */     
/* 178 */     proxy.loadConfig("config/mcheli.cfg");
/* 179 */     config = proxy.config;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 188 */     ContentRegistries.loadContents(addonDir);
/*     */     
/* 190 */     MCH_SoundsJson.updateGenerated();
/*     */     
/* 192 */     MCH_Lib.Log("Register item", new Object[0]);
/* 193 */     registerItemSpawnGunner();
/* 194 */     registerItemRangeFinder();
/* 195 */     registerItemWrench();
/* 196 */     registerItemFuel();
/* 197 */     registerItemGLTD();
/* 198 */     registerItemChain();
/* 199 */     registerItemParachute();
/* 200 */     registerItemContainer();
/* 201 */     registerItemUavStation();
/* 202 */     registerItemInvisible();
/* 203 */     registerItemThrowable();
/* 204 */     registerItemLightWeaponBullet();
/* 205 */     registerItemLightWeapon();
/* 206 */     registerItemAircraft();
/*     */     
/* 208 */     blockDraftingTable = new MCH_DraftingTableBlock(MCH_Config.BlockID_DraftingTableOFF.prmInt, false);
/* 209 */     blockDraftingTable.func_149663_c("drafting_table");
/* 210 */     blockDraftingTable.func_149647_a(creativeTabs);
/* 211 */     blockDraftingTableLit = new MCH_DraftingTableBlock(MCH_Config.BlockID_DraftingTableON.prmInt, true);
/* 212 */     blockDraftingTableLit.func_149663_c("lit_drafting_table");
/*     */ 
/*     */     
/* 215 */     MCH_Blocks.register((Block)blockDraftingTable, "drafting_table");
/* 216 */     MCH_Blocks.register((Block)blockDraftingTableLit, "lit_drafting_table");
/* 217 */     itemDraftingTable = MCH_Items.registerBlock((Block)blockDraftingTable);
/* 218 */     W_LanguageRegistry.addName(blockDraftingTable, "Drafting Table");
/*     */     
/* 220 */     W_LanguageRegistry.addNameForObject(blockDraftingTable, "ja_jp", "製図台");
/*     */     
/* 222 */     MCH_Achievement.PreInit();
/*     */     
/* 224 */     MCH_Lib.Log("Register system", new Object[0]);
/* 225 */     W_NetworkRegistry.registerChannel(packetHandler, "MCHeli_CH");
/* 226 */     MinecraftForge.EVENT_BUS.register(new MCH_EventHook());
/* 227 */     proxy.registerClientTick();
/* 228 */     W_NetworkRegistry.registerGuiHandler(this, (IGuiHandler)new MCH_GuiCommonHandler());
/*     */     
/* 230 */     MCH_Lib.Log("Register entity", new Object[0]);
/* 231 */     registerEntity();
/*     */     
/* 233 */     MCH_Lib.Log("Register renderer", new Object[0]);
/* 234 */     proxy.registerRenderer();
/*     */     
/* 236 */     MCH_Lib.Log("Register models", new Object[0]);
/* 237 */     proxy.registerModels();
/*     */     
/* 239 */     MCH_Lib.Log("Register Sounds", new Object[0]);
/* 240 */     proxy.registerSounds();
/*     */ 
/*     */     
/* 243 */     proxy.updateGeneratedLanguage();
/*     */     
/* 245 */     MCH_Lib.Log("End load", new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void init(FMLInitializationEvent evt) {
/* 252 */     GameRegistry.registerTileEntity(MCH_DraftingTableTileEntity.class, MCH_Utils.suffix("drafting_table"));
/* 253 */     proxy.registerBlockRenderer();
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void postInit(FMLPostInitializationEvent evt) {
/* 259 */     creativeTabs.setFixedIconItem(MCH_Config.CreativeTabIcon.prmString);
/* 260 */     creativeTabsHeli.setFixedIconItem(MCH_Config.CreativeTabIconHeli.prmString);
/* 261 */     creativeTabsPlane.setFixedIconItem(MCH_Config.CreativeTabIconPlane.prmString);
/* 262 */     creativeTabsTank.setFixedIconItem(MCH_Config.CreativeTabIconTank.prmString);
/* 263 */     creativeTabsVehicle.setFixedIconItem(MCH_Config.CreativeTabIconVehicle.prmString);
/*     */ 
/*     */     
/* 266 */     proxy.registerRecipeDescriptions();
/* 267 */     MCH_WeaponInfoManager.setRoundItems();
/*     */     
/* 269 */     proxy.readClientModList();
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void onStartServer(FMLServerStartingEvent event) {
/* 275 */     proxy.registerServerTick();
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerEntity() {
/* 280 */     MCH_Entities.register(MCH_EntitySeat.class, "MCH.E.Seat", 100, this, 400, 10, true);
/* 281 */     MCH_Entities.register(MCH_EntityHeli.class, "MCH.E.Heli", 101, this, 400, 10, true);
/* 282 */     MCH_Entities.register(MCH_EntityGLTD.class, "MCH.E.GLTD", 102, this, 200, 10, true);
/* 283 */     MCH_Entities.register(MCP_EntityPlane.class, "MCH.E.Plane", 103, this, 400, 10, true);
/* 284 */     MCH_Entities.register(MCH_EntityChain.class, "MCH.E.Chain", 104, this, 200, 10, true);
/* 285 */     MCH_Entities.register(MCH_EntityHitBox.class, "MCH.E.PSeat", 105, this, 200, 10, true);
/* 286 */     MCH_Entities.register(MCH_EntityParachute.class, "MCH.E.Parachute", 106, this, 200, 10, true);
/* 287 */     MCH_Entities.register(MCH_EntityContainer.class, "MCH.E.Container", 107, this, 200, 10, true);
/* 288 */     MCH_Entities.register(MCH_EntityVehicle.class, "MCH.E.Vehicle", 108, this, 400, 10, true);
/* 289 */     MCH_Entities.register(MCH_EntityUavStation.class, "MCH.E.UavStation", 109, this, 400, 10, true);
/* 290 */     MCH_Entities.register(MCH_EntityHitBox.class, "MCH.E.HitBox", 110, this, 200, 10, true);
/* 291 */     MCH_Entities.register(MCH_EntityHide.class, "MCH.E.Hide", 111, this, 200, 10, true);
/* 292 */     MCH_Entities.register(MCH_EntityTank.class, "MCH.E.Tank", 112, this, 400, 10, true);
/* 293 */     MCH_Entities.register(MCH_EntityRocket.class, "MCH.E.Rocket", 200, this, 530, 5, true);
/* 294 */     MCH_Entities.register(MCH_EntityTvMissile.class, "MCH.E.TvMissle", 201, this, 530, 5, true);
/* 295 */     MCH_Entities.register(MCH_EntityBullet.class, "MCH.E.Bullet", 202, this, 530, 5, true);
/* 296 */     MCH_Entities.register(MCH_EntityA10.class, "MCH.E.A10", 203, this, 530, 5, true);
/* 297 */     MCH_Entities.register(MCH_EntityAAMissile.class, "MCH.E.AAM", 204, this, 530, 5, true);
/* 298 */     MCH_Entities.register(MCH_EntityASMissile.class, "MCH.E.ASM", 205, this, 530, 5, true);
/* 299 */     MCH_Entities.register(MCH_EntityTorpedo.class, "MCH.E.Torpedo", 206, this, 530, 5, true);
/* 300 */     MCH_Entities.register(MCH_EntityATMissile.class, "MCH.E.ATMissle", 207, this, 530, 5, true);
/* 301 */     MCH_Entities.register(MCH_EntityBomb.class, "MCH.E.Bomb", 208, this, 530, 5, true);
/* 302 */     MCH_Entities.register(MCH_EntityMarkerRocket.class, "MCH.E.MkRocket", 209, this, 530, 5, true);
/* 303 */     MCH_Entities.register(MCH_EntityDispensedItem.class, "MCH.E.DispItem", 210, this, 530, 5, true);
/* 304 */     MCH_Entities.register(MCH_EntityFlare.class, "MCH.E.Flare", 300, this, 330, 10, true);
/* 305 */     MCH_Entities.register(MCH_EntityThrowable.class, "MCH.E.Throwable", 400, this, 330, 10, true);
/* 306 */     MCH_Entities.register(MCH_EntityGunner.class, "MCH.E.Gunner", 500, this, 530, 5, true);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void registerCommand(FMLServerStartedEvent e) {
/* 313 */     CommandHandler handler = (CommandHandler)FMLCommonHandler.instance().getSidedDelegate().getServer().func_71187_D();
/* 314 */     handler.func_71560_a((ICommand)new MCH_Command());
/*     */   }
/*     */ 
/*     */   
/*     */   private void registerItemSpawnGunner() {
/* 319 */     String name = "spawn_gunner_vs_monster";
/* 320 */     MCH_ItemSpawnGunner item = new MCH_ItemSpawnGunner();
/* 321 */     item.targetType = 0;
/* 322 */     item.primaryColor = 12632224;
/* 323 */     item.secondaryColor = 12582912;
/* 324 */     itemSpawnGunnerVsMonster = item;
/* 325 */     registerItem((W_Item)item, name, creativeTabs);
/* 326 */     W_LanguageRegistry.addName(item, "Gunner (vs Monster)");
/*     */     
/* 328 */     W_LanguageRegistry.addNameForObject(item, "ja_jp", "対モンスター 射撃手");
/*     */     
/* 330 */     name = "spawn_gunner_vs_player";
/* 331 */     item = new MCH_ItemSpawnGunner();
/* 332 */     item.targetType = 1;
/* 333 */     item.primaryColor = 12632224;
/* 334 */     item.secondaryColor = 49152;
/* 335 */     itemSpawnGunnerVsPlayer = item;
/* 336 */     registerItem((W_Item)item, name, creativeTabs);
/* 337 */     W_LanguageRegistry.addName(item, "Gunner (vs Player of other team)");
/*     */     
/* 339 */     W_LanguageRegistry.addNameForObject(item, "ja_jp", "対他チームプレイヤー 射撃手");
/*     */   }
/*     */ 
/*     */   
/*     */   private void registerItemRangeFinder() {
/* 344 */     String name = "rangefinder";
/* 345 */     MCH_ItemRangeFinder item = new MCH_ItemRangeFinder(MCH_Config.ItemID_RangeFinder.prmInt);
/* 346 */     itemRangeFinder = item;
/* 347 */     registerItem((W_Item)item, name, creativeTabs);
/* 348 */     W_LanguageRegistry.addName(item, "Laser Rangefinder");
/*     */     
/* 350 */     W_LanguageRegistry.addNameForObject(item, "ja_jp", "レーザー レンジ ファインダー");
/*     */   }
/*     */ 
/*     */   
/*     */   private void registerItemWrench() {
/* 355 */     String name = "wrench";
/* 356 */     MCH_ItemWrench item = new MCH_ItemWrench(MCH_Config.ItemID_Wrench.prmInt, Item.ToolMaterial.IRON);
/* 357 */     itemWrench = item;
/* 358 */     registerItem((W_Item)item, name, creativeTabs);
/* 359 */     W_LanguageRegistry.addName(item, "Wrench");
/*     */     
/* 361 */     W_LanguageRegistry.addNameForObject(item, "ja_jp", "レンチ");
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerItemInvisible() {
/* 366 */     String name = "internal";
/* 367 */     MCH_InvisibleItem item = new MCH_InvisibleItem(MCH_Config.ItemID_InvisibleItem.prmInt);
/* 368 */     invisibleItem = item;
/* 369 */     registerItem(item, name, null);
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerItemUavStation() {
/* 374 */     String[] dispName = { "UAV Station", "Portable UAV Controller" };
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 379 */     String[] localName = { "UAVステーション", "携帯UAV制御端末" };
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 384 */     itemUavStation = new MCH_ItemUavStation[MCH_ItemUavStation.UAV_STATION_KIND_NUM];
/* 385 */     String name = "uav_station";
/* 386 */     for (int i = 0; i < itemUavStation.length; i++) {
/*     */       
/* 388 */       String nn = (i > 0) ? ("" + (i + 1)) : "";
/* 389 */       MCH_ItemUavStation item = new MCH_ItemUavStation((MCH_Config.ItemID_UavStation[i]).prmInt, 1 + i);
/* 390 */       itemUavStation[i] = item;
/* 391 */       registerItem((W_Item)item, name + nn, creativeTabs);
/* 392 */       W_LanguageRegistry.addName(item, dispName[i]);
/*     */       
/* 394 */       W_LanguageRegistry.addNameForObject(item, "ja_jp", localName[i]);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerItemParachute() {
/* 400 */     String name = "parachute";
/* 401 */     MCH_ItemParachute item = new MCH_ItemParachute(MCH_Config.ItemID_Parachute.prmInt);
/* 402 */     itemParachute = item;
/* 403 */     registerItem((W_Item)item, name, creativeTabs);
/* 404 */     W_LanguageRegistry.addName(item, "Parachute");
/*     */     
/* 406 */     W_LanguageRegistry.addNameForObject(item, "ja_jp", "パラシュート");
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerItemContainer() {
/* 411 */     String name = "container";
/* 412 */     MCH_ItemContainer item = new MCH_ItemContainer(MCH_Config.ItemID_Container.prmInt);
/* 413 */     itemContainer = item;
/* 414 */     registerItem((W_Item)item, name, creativeTabs);
/* 415 */     W_LanguageRegistry.addName(item, "Container");
/*     */     
/* 417 */     W_LanguageRegistry.addNameForObject(item, "ja_jp", "コンテナ");
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerItemLightWeapon() {
/* 422 */     String name = "fim92";
/*     */     
/* 424 */     MCH_ItemLightWeaponBase item = new MCH_ItemLightWeaponBase(MCH_Config.ItemID_Stinger.prmInt, itemStingerBullet);
/* 425 */     itemStinger = item;
/* 426 */     registerItem((W_Item)item, name, creativeTabs);
/* 427 */     W_LanguageRegistry.addName(item, "FIM-92 Stinger");
/*     */     
/* 429 */     name = "fgm148";
/* 430 */     item = new MCH_ItemLightWeaponBase(MCH_Config.ItemID_Stinger.prmInt, itemJavelinBullet);
/* 431 */     itemJavelin = item;
/* 432 */     registerItem((W_Item)item, name, creativeTabs);
/* 433 */     W_LanguageRegistry.addName(item, "FGM-148 Javelin");
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerItemLightWeaponBullet() {
/* 438 */     String name = "fim92_bullet";
/* 439 */     MCH_ItemLightWeaponBullet item = new MCH_ItemLightWeaponBullet(MCH_Config.ItemID_StingerMissile.prmInt);
/* 440 */     itemStingerBullet = item;
/* 441 */     registerItem((W_Item)item, name, creativeTabs);
/* 442 */     W_LanguageRegistry.addName(item, "FIM-92 Stinger missile");
/*     */     
/* 444 */     name = "fgm148_bullet";
/* 445 */     item = new MCH_ItemLightWeaponBullet(MCH_Config.ItemID_StingerMissile.prmInt);
/* 446 */     itemJavelinBullet = item;
/* 447 */     registerItem((W_Item)item, name, creativeTabs);
/* 448 */     W_LanguageRegistry.addName(item, "FGM-148 Javelin missile");
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerItemChain() {
/* 453 */     String name = "chain";
/* 454 */     MCH_ItemChain item = new MCH_ItemChain(MCH_Config.ItemID_Chain.prmInt);
/* 455 */     itemChain = item;
/* 456 */     registerItem((W_Item)item, name, creativeTabs);
/*     */     
/* 458 */     W_LanguageRegistry.addName(item, "Chain");
/*     */     
/* 460 */     W_LanguageRegistry.addNameForObject(item, "ja_jp", "鎖");
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerItemFuel() {
/* 465 */     String name = "fuel";
/* 466 */     MCH_ItemFuel item = new MCH_ItemFuel(MCH_Config.ItemID_Fuel.prmInt);
/* 467 */     itemFuel = item;
/*     */     
/* 469 */     registerItem((W_Item)item, name, creativeTabs);
/*     */     
/* 471 */     W_LanguageRegistry.addName(item, "Fuel");
/*     */     
/* 473 */     W_LanguageRegistry.addNameForObject(item, "ja_jp", "燃料");
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerItemGLTD() {
/* 478 */     String name = "gltd";
/* 479 */     MCH_ItemGLTD item = new MCH_ItemGLTD(MCH_Config.ItemID_GLTD.prmInt);
/* 480 */     itemGLTD = item;
/*     */     
/* 482 */     registerItem((W_Item)item, name, creativeTabs);
/*     */     
/* 484 */     W_LanguageRegistry.addName(item, "GLTD:Target Designator");
/*     */     
/* 486 */     W_LanguageRegistry.addNameForObject(item, "ja_jp", "GLTD:レーザー目標指示装置");
/*     */   }
/*     */ 
/*     */   
/*     */   public static void registerItem(W_Item item, String name, MCH_CreativeTabs ct) {
/* 491 */     item.func_77655_b("mcheli:" + name);
/*     */ 
/*     */     
/* 494 */     if (ct != null) {
/*     */       
/* 496 */       item.func_77637_a(ct);
/* 497 */       ct.addIconItem((Item)item);
/*     */     } 
/*     */ 
/*     */     
/* 501 */     MCH_Items.register((Item)item, name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void registerItemThrowable() {
/* 508 */     for (Map.Entry<String, MCH_ThrowableInfo> entry : (Iterable<Map.Entry<String, MCH_ThrowableInfo>>)ContentRegistries.throwable().entries()) {
/*     */ 
/*     */       
/* 511 */       MCH_ThrowableInfo info = entry.getValue();
/* 512 */       info.item = (W_Item)new MCH_ItemThrowable(info.itemID);
/* 513 */       info.item.func_77625_d(info.stackSize);
/*     */ 
/*     */       
/* 516 */       registerItem(info.item, entry.getKey(), creativeTabs);
/*     */       
/* 518 */       MCH_ItemThrowable.registerDispenseBehavior((Item)info.item);
/*     */       
/* 520 */       info.itemID = W_Item.getIdFromItem((Item)info.item) - 256;
/*     */       
/* 522 */       W_LanguageRegistry.addName(info.item, info.displayName);
/* 523 */       for (String lang : info.displayNameLang.keySet())
/*     */       {
/* 525 */         W_LanguageRegistry.addNameForObject(info.item, lang, (String)info.displayNameLang.get(lang));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void registerItemAircraft() {
/* 533 */     for (Map.Entry<String, MCH_HeliInfo> entry : (Iterable<Map.Entry<String, MCH_HeliInfo>>)ContentRegistries.heli().entries()) {
/*     */ 
/*     */       
/* 536 */       MCH_HeliInfo info = entry.getValue();
/* 537 */       info.item = new MCH_ItemHeli(info.itemID);
/* 538 */       info.item.func_77656_e(info.maxHp);
/*     */       
/* 540 */       if (!info.canRide && (info.ammoSupplyRange > 0.0F || info.fuelSupplyRange > 0.0F)) {
/*     */ 
/*     */         
/* 543 */         registerItem((W_Item)info.item, entry.getKey(), creativeTabs);
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 548 */         registerItem((W_Item)info.item, entry.getKey(), creativeTabsHeli);
/*     */       } 
/*     */       
/* 551 */       MCH_ItemAircraft.registerDispenseBehavior((Item)info.item);
/*     */       
/* 553 */       info.itemID = W_Item.getIdFromItem((Item)info.item) - 256;
/*     */       
/* 555 */       W_LanguageRegistry.addName(info.item, info.displayName);
/* 556 */       for (String lang : info.displayNameLang.keySet())
/*     */       {
/* 558 */         W_LanguageRegistry.addNameForObject(info.item, lang, (String)info.displayNameLang.get(lang));
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 563 */     for (Map.Entry<String, MCP_PlaneInfo> entry : (Iterable<Map.Entry<String, MCP_PlaneInfo>>)ContentRegistries.plane().entries()) {
/*     */ 
/*     */       
/* 566 */       MCP_PlaneInfo info = entry.getValue();
/* 567 */       info.item = new MCP_ItemPlane(info.itemID);
/* 568 */       info.item.func_77656_e(info.maxHp);
/*     */       
/* 570 */       if (!info.canRide && (info.ammoSupplyRange > 0.0F || info.fuelSupplyRange > 0.0F)) {
/*     */ 
/*     */         
/* 573 */         registerItem((W_Item)info.item, entry.getKey(), creativeTabs);
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 578 */         registerItem((W_Item)info.item, entry.getKey(), creativeTabsPlane);
/*     */       } 
/*     */       
/* 581 */       MCH_ItemAircraft.registerDispenseBehavior((Item)info.item);
/*     */       
/* 583 */       info.itemID = W_Item.getIdFromItem((Item)info.item) - 256;
/*     */       
/* 585 */       W_LanguageRegistry.addName(info.item, info.displayName);
/* 586 */       for (String lang : info.displayNameLang.keySet())
/*     */       {
/* 588 */         W_LanguageRegistry.addNameForObject(info.item, lang, (String)info.displayNameLang.get(lang));
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 593 */     for (Map.Entry<String, MCH_TankInfo> entry : (Iterable<Map.Entry<String, MCH_TankInfo>>)ContentRegistries.tank().entries()) {
/*     */ 
/*     */       
/* 596 */       MCH_TankInfo info = entry.getValue();
/* 597 */       info.item = new MCH_ItemTank(info.itemID);
/* 598 */       info.item.func_77656_e(info.maxHp);
/*     */       
/* 600 */       if (!info.canRide && (info.ammoSupplyRange > 0.0F || info.fuelSupplyRange > 0.0F)) {
/*     */ 
/*     */         
/* 603 */         registerItem((W_Item)info.item, entry.getKey(), creativeTabs);
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 608 */         registerItem((W_Item)info.item, entry.getKey(), creativeTabsTank);
/*     */       } 
/*     */       
/* 611 */       MCH_ItemAircraft.registerDispenseBehavior((Item)info.item);
/*     */       
/* 613 */       info.itemID = W_Item.getIdFromItem((Item)info.item) - 256;
/*     */       
/* 615 */       W_LanguageRegistry.addName(info.item, info.displayName);
/* 616 */       for (String lang : info.displayNameLang.keySet())
/*     */       {
/* 618 */         W_LanguageRegistry.addNameForObject(info.item, lang, (String)info.displayNameLang.get(lang));
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 623 */     for (Map.Entry<String, MCH_VehicleInfo> entry : (Iterable<Map.Entry<String, MCH_VehicleInfo>>)ContentRegistries.vehicle().entries()) {
/*     */ 
/*     */       
/* 626 */       MCH_VehicleInfo info = entry.getValue();
/* 627 */       info.item = new MCH_ItemVehicle(info.itemID);
/* 628 */       info.item.func_77656_e(info.maxHp);
/*     */       
/* 630 */       if (!info.canRide && (info.ammoSupplyRange > 0.0F || info.fuelSupplyRange > 0.0F)) {
/*     */ 
/*     */         
/* 633 */         registerItem((W_Item)info.item, entry.getKey(), creativeTabs);
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 638 */         registerItem((W_Item)info.item, entry.getKey(), creativeTabsVehicle);
/*     */       } 
/*     */       
/* 641 */       MCH_ItemAircraft.registerDispenseBehavior((Item)info.item);
/*     */       
/* 643 */       info.itemID = W_Item.getIdFromItem((Item)info.item) - 256;
/*     */       
/* 645 */       W_LanguageRegistry.addName(info.item, info.displayName);
/* 646 */       for (String lang : info.displayNameLang.keySet())
/*     */       {
/* 648 */         W_LanguageRegistry.addNameForObject(info.item, lang, (String)info.displayNameLang.get(lang));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static Logger getLogger() {
/* 656 */     return MCH_Logger.get();
/*     */   }
/*     */ 
/*     */   
/*     */   public static File getSource() {
/* 661 */     return sourceFile;
/*     */   }
/*     */ 
/*     */   
/*     */   public static File getAddonDir() {
/* 666 */     return addonDir;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isTodaySep01() {
/* 671 */     if (isSep01 == null) {
/*     */       
/* 673 */       Calendar c = Calendar.getInstance();
/* 674 */       isSep01 = Boolean.valueOf((c.get(2) + 1 == 9 && c.get(5) == 1));
/*     */     } 
/*     */     
/* 677 */     return isSep01.booleanValue();
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\MCH_MOD.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */