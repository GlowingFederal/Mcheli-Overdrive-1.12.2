package mcheli;

import java.io.File;
import java.util.Calendar;
import java.util.Map;
import mcheli.__helper.MCH_Blocks;
import mcheli.__helper.MCH_Entities;
import mcheli.__helper.MCH_Items;
import mcheli.__helper.MCH_Logger;
import mcheli.__helper.MCH_Utils;
import mcheli.__helper.info.ContentRegistries;
import mcheli.aircraft.MCH_EntityHide;
import mcheli.aircraft.MCH_EntityHitBox;
import mcheli.aircraft.MCH_EntitySeat;
import mcheli.aircraft.MCH_ItemAircraft;
import mcheli.aircraft.MCH_ItemFuel;
import mcheli.block.MCH_DraftingTableBlock;
import mcheli.block.MCH_DraftingTableTileEntity;
import mcheli.chain.MCH_EntityChain;
import mcheli.chain.MCH_ItemChain;
import mcheli.command.MCH_Command;
import mcheli.container.MCH_EntityContainer;
import mcheli.container.MCH_ItemContainer;
import mcheli.flare.MCH_EntityFlare;
import mcheli.gltd.MCH_EntityGLTD;
import mcheli.gltd.MCH_ItemGLTD;
import mcheli.gui.MCH_GuiCommonHandler;
import mcheli.helicopter.MCH_EntityHeli;
import mcheli.helicopter.MCH_HeliInfo;
import mcheli.helicopter.MCH_ItemHeli;
import mcheli.lweapon.MCH_ItemLightWeaponBase;
import mcheli.lweapon.MCH_ItemLightWeaponBullet;
import mcheli.mob.MCH_EntityGunner;
import mcheli.mob.MCH_ItemSpawnGunner;
import mcheli.parachute.MCH_EntityParachute;
import mcheli.parachute.MCH_ItemParachute;
import mcheli.plane.MCP_EntityPlane;
import mcheli.plane.MCP_ItemPlane;
import mcheli.plane.MCP_PlaneInfo;
import mcheli.tank.MCH_EntityTank;
import mcheli.tank.MCH_ItemTank;
import mcheli.tank.MCH_TankInfo;
import mcheli.throwable.MCH_EntityThrowable;
import mcheli.throwable.MCH_ItemThrowable;
import mcheli.throwable.MCH_ThrowableInfo;
import mcheli.tool.MCH_ItemWrench;
import mcheli.tool.rangefinder.MCH_ItemRangeFinder;
import mcheli.uav.MCH_EntityUavStation;
import mcheli.uav.MCH_ItemUavStation;
import mcheli.vehicle.MCH_EntityVehicle;
import mcheli.vehicle.MCH_ItemVehicle;
import mcheli.vehicle.MCH_VehicleInfo;
import mcheli.weapon.MCH_EntityA10;
import mcheli.weapon.MCH_EntityAAMissile;
import mcheli.weapon.MCH_EntityASMissile;
import mcheli.weapon.MCH_EntityATMissile;
import mcheli.weapon.MCH_EntityBomb;
import mcheli.weapon.MCH_EntityBullet;
import mcheli.weapon.MCH_EntityDispensedItem;
import mcheli.weapon.MCH_EntityMarkerRocket;
import mcheli.weapon.MCH_EntityRocket;
import mcheli.weapon.MCH_EntityTorpedo;
import mcheli.weapon.MCH_EntityTvMissile;
import mcheli.weapon.MCH_WeaponInfoManager;
import mcheli.wrapper.NetworkMod;
import mcheli.wrapper.W_Item;
import mcheli.wrapper.W_ItemList;
import mcheli.wrapper.W_LanguageRegistry;
import mcheli.wrapper.W_NetworkRegistry;
import net.minecraft.block.Block;
import net.minecraft.command.CommandHandler;
import net.minecraft.command.ICommand;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.apache.logging.log4j.Logger;

@Mod(modid = "mcheli", name = "MC Helicopter MOD", dependencies = "required-after:forge@[14.23.5.2854,)", guiFactory = "mcheli.__helper.config.MODGuiFactory")
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class MCH_MOD {
  public static final String MOD_ID = "mcheli";
  
  @Deprecated
  public static final String DOMAIN = "mcheli";
  
  public static final String MOD_NAME = "MC Helicopter MOD";
  
  public static final String MCVER = "1.12.2";
  
  public static String VER = "";
  
  public static final String MOD_CH = "MCHeli_CH";
  
  public static final String ADDON_FOLDER_NAME = "mcheli_addons";
  
  @Instance("mcheli")
  public static MCH_MOD instance;
  
  @SidedProxy(clientSide = "mcheli.MCH_ClientProxy", serverSide = "mcheli.MCH_CommonProxy")
  public static MCH_CommonProxy proxy;
  
  public static MCH_PacketHandler packetHandler = new MCH_PacketHandler();
  
  public static MCH_Config config;
  
  public static String sourcePath;
  
  private static File sourceFile;
  
  private static File addonDir;
  
  public static MCH_InvisibleItem invisibleItem;
  
  public static MCH_ItemGLTD itemGLTD;
  
  public static MCH_ItemLightWeaponBullet itemStingerBullet;
  
  public static MCH_ItemLightWeaponBase itemStinger;
  
  public static MCH_ItemLightWeaponBullet itemJavelinBullet;
  
  public static MCH_ItemLightWeaponBase itemJavelin;
  
  public static MCH_ItemUavStation[] itemUavStation;
  
  public static MCH_ItemParachute itemParachute;
  
  public static MCH_ItemContainer itemContainer;
  
  public static MCH_ItemChain itemChain;
  
  public static MCH_ItemFuel itemFuel;
  
  public static MCH_ItemWrench itemWrench;
  
  public static MCH_ItemRangeFinder itemRangeFinder;
  
  public static MCH_ItemSpawnGunner itemSpawnGunnerVsPlayer;
  
  public static MCH_ItemSpawnGunner itemSpawnGunnerVsMonster;
  
  public static MCH_CreativeTabs creativeTabs;
  
  public static MCH_CreativeTabs creativeTabsHeli;
  
  public static MCH_CreativeTabs creativeTabsPlane;
  
  public static MCH_CreativeTabs creativeTabsTank;
  
  public static MCH_CreativeTabs creativeTabsVehicle;
  
  public static MCH_DraftingTableBlock blockDraftingTable;
  
  public static MCH_DraftingTableBlock blockDraftingTableLit;
  
  public static ItemBlock itemDraftingTable;
  
  public static Item sampleHelmet;
  
  private static Boolean isSep01 = null;
  
  @EventHandler
  public void PreInit(FMLPreInitializationEvent evt) {
    MCH_Logger.setLogger(evt.getModLog());
    VER = Loader.instance().activeModContainer().getVersion();
    MCH_Lib.init();
    MCH_Lib.Log("MC Ver:1.12.2 MOD Ver:" + VER + "", new Object[0]);
    MCH_Lib.Log("Start load...", new Object[0]);
    sourcePath = evt.getSourceFile().getPath();
    sourceFile = evt.getSourceFile();
    addonDir = new File(evt.getModConfigurationDirectory().getParentFile(), "/mcheli_addons/");
    MCH_Lib.Log("SourcePath: " + sourcePath, new Object[0]);
    MCH_Lib.Log("CurrentDirectory:" + (new File(".")).getAbsolutePath(), new Object[0]);
    proxy.init();
    creativeTabs = new MCH_CreativeTabs("MC Heli Item");
    creativeTabsHeli = new MCH_CreativeTabs("MC Heli Helicopters");
    creativeTabsPlane = new MCH_CreativeTabs("MC Heli Planes");
    creativeTabsTank = new MCH_CreativeTabs("MC Heli Tanks");
    creativeTabsVehicle = new MCH_CreativeTabs("MC Heli Vehicles");
    W_ItemList.init();
    proxy.loadConfig("config/mcheli.cfg");
    config = proxy.config;
    ContentRegistries.loadContents(addonDir);
    MCH_SoundsJson.updateGenerated();
    MCH_Lib.Log("Register item", new Object[0]);
    registerItemSpawnGunner();
    registerItemRangeFinder();
    registerItemWrench();
    registerItemFuel();
    registerItemGLTD();
    registerItemChain();
    registerItemParachute();
    registerItemContainer();
    registerItemUavStation();
    registerItemInvisible();
    registerItemThrowable();
    registerItemLightWeaponBullet();
    registerItemLightWeapon();
    registerItemAircraft();
    blockDraftingTable = new MCH_DraftingTableBlock(MCH_Config.BlockID_DraftingTableOFF.prmInt, false);
    blockDraftingTable.func_149663_c("drafting_table");
    blockDraftingTable.func_149647_a(creativeTabs);
    blockDraftingTableLit = new MCH_DraftingTableBlock(MCH_Config.BlockID_DraftingTableON.prmInt, true);
    blockDraftingTableLit.func_149663_c("lit_drafting_table");
    MCH_Blocks.register((Block)blockDraftingTable, "drafting_table");
    MCH_Blocks.register((Block)blockDraftingTableLit, "lit_drafting_table");
    itemDraftingTable = MCH_Items.registerBlock((Block)blockDraftingTable);
    W_LanguageRegistry.addName(blockDraftingTable, "Drafting Table");
    W_LanguageRegistry.addNameForObject(blockDraftingTable, "ja_jp", "製図台");
    MCH_Achievement.PreInit();
    MCH_Lib.Log("Register system", new Object[0]);
    W_NetworkRegistry.registerChannel(packetHandler, "MCHeli_CH");
    MinecraftForge.EVENT_BUS.register(new MCH_EventHook());
    proxy.registerClientTick();
    W_NetworkRegistry.registerGuiHandler(this, (IGuiHandler)new MCH_GuiCommonHandler());
    MCH_Lib.Log("Register entity", new Object[0]);
    registerEntity();
    MCH_Lib.Log("Register renderer", new Object[0]);
    proxy.registerRenderer();
    MCH_Lib.Log("Register models", new Object[0]);
    proxy.registerModels();
    MCH_Lib.Log("Register Sounds", new Object[0]);
    proxy.registerSounds();
    proxy.updateGeneratedLanguage();
    MCH_Lib.Log("End load", new Object[0]);
  }
  
  @EventHandler
  public void init(FMLInitializationEvent evt) {
    GameRegistry.registerTileEntity(MCH_DraftingTableTileEntity.class, MCH_Utils.suffix("drafting_table"));
    proxy.registerBlockRenderer();
  }
  
  @EventHandler
  public void postInit(FMLPostInitializationEvent evt) {
    creativeTabs.setFixedIconItem(MCH_Config.CreativeTabIcon.prmString);
    creativeTabsHeli.setFixedIconItem(MCH_Config.CreativeTabIconHeli.prmString);
    creativeTabsPlane.setFixedIconItem(MCH_Config.CreativeTabIconPlane.prmString);
    creativeTabsTank.setFixedIconItem(MCH_Config.CreativeTabIconTank.prmString);
    creativeTabsVehicle.setFixedIconItem(MCH_Config.CreativeTabIconVehicle.prmString);
    proxy.registerRecipeDescriptions();
    MCH_WeaponInfoManager.setRoundItems();
    proxy.readClientModList();
  }
  
  @EventHandler
  public void onStartServer(FMLServerStartingEvent event) {
    proxy.registerServerTick();
  }
  
  public void registerEntity() {
    MCH_Entities.register(MCH_EntitySeat.class, "MCH.E.Seat", 100, this, 400, 10, true);
    MCH_Entities.register(MCH_EntityHeli.class, "MCH.E.Heli", 101, this, 400, 10, true);
    MCH_Entities.register(MCH_EntityGLTD.class, "MCH.E.GLTD", 102, this, 200, 10, true);
    MCH_Entities.register(MCP_EntityPlane.class, "MCH.E.Plane", 103, this, 400, 10, true);
    MCH_Entities.register(MCH_EntityChain.class, "MCH.E.Chain", 104, this, 200, 10, true);
    MCH_Entities.register(MCH_EntityHitBox.class, "MCH.E.PSeat", 105, this, 200, 10, true);
    MCH_Entities.register(MCH_EntityParachute.class, "MCH.E.Parachute", 106, this, 200, 10, true);
    MCH_Entities.register(MCH_EntityContainer.class, "MCH.E.Container", 107, this, 200, 10, true);
    MCH_Entities.register(MCH_EntityVehicle.class, "MCH.E.Vehicle", 108, this, 400, 10, true);
    MCH_Entities.register(MCH_EntityUavStation.class, "MCH.E.UavStation", 109, this, 400, 10, true);
    MCH_Entities.register(MCH_EntityHitBox.class, "MCH.E.HitBox", 110, this, 200, 10, true);
    MCH_Entities.register(MCH_EntityHide.class, "MCH.E.Hide", 111, this, 200, 10, true);
    MCH_Entities.register(MCH_EntityTank.class, "MCH.E.Tank", 112, this, 400, 10, true);
    MCH_Entities.register(MCH_EntityRocket.class, "MCH.E.Rocket", 200, this, 530, 5, true);
    MCH_Entities.register(MCH_EntityTvMissile.class, "MCH.E.TvMissle", 201, this, 530, 5, true);
    MCH_Entities.register(MCH_EntityBullet.class, "MCH.E.Bullet", 202, this, 530, 5, true);
    MCH_Entities.register(MCH_EntityA10.class, "MCH.E.A10", 203, this, 530, 5, true);
    MCH_Entities.register(MCH_EntityAAMissile.class, "MCH.E.AAM", 204, this, 530, 5, true);
    MCH_Entities.register(MCH_EntityASMissile.class, "MCH.E.ASM", 205, this, 530, 5, true);
    MCH_Entities.register(MCH_EntityTorpedo.class, "MCH.E.Torpedo", 206, this, 530, 5, true);
    MCH_Entities.register(MCH_EntityATMissile.class, "MCH.E.ATMissle", 207, this, 530, 5, true);
    MCH_Entities.register(MCH_EntityBomb.class, "MCH.E.Bomb", 208, this, 530, 5, true);
    MCH_Entities.register(MCH_EntityMarkerRocket.class, "MCH.E.MkRocket", 209, this, 530, 5, true);
    MCH_Entities.register(MCH_EntityDispensedItem.class, "MCH.E.DispItem", 210, this, 530, 5, true);
    MCH_Entities.register(MCH_EntityFlare.class, "MCH.E.Flare", 300, this, 330, 10, true);
    MCH_Entities.register(MCH_EntityThrowable.class, "MCH.E.Throwable", 400, this, 330, 10, true);
    MCH_Entities.register(MCH_EntityGunner.class, "MCH.E.Gunner", 500, this, 530, 5, true);
  }
  
  @EventHandler
  public void registerCommand(FMLServerStartedEvent e) {
    CommandHandler handler = (CommandHandler)FMLCommonHandler.instance().getSidedDelegate().getServer().func_71187_D();
    handler.func_71560_a((ICommand)new MCH_Command());
  }
  
  private void registerItemSpawnGunner() {
    String name = "spawn_gunner_vs_monster";
    MCH_ItemSpawnGunner item = new MCH_ItemSpawnGunner();
    item.targetType = 0;
    item.primaryColor = 12632224;
    item.secondaryColor = 12582912;
    itemSpawnGunnerVsMonster = item;
    registerItem((W_Item)item, name, creativeTabs);
    W_LanguageRegistry.addName(item, "Gunner (vs Monster)");
    W_LanguageRegistry.addNameForObject(item, "ja_jp", "対モンスター 射撃手");
    name = "spawn_gunner_vs_player";
    item = new MCH_ItemSpawnGunner();
    item.targetType = 1;
    item.primaryColor = 12632224;
    item.secondaryColor = 49152;
    itemSpawnGunnerVsPlayer = item;
    registerItem((W_Item)item, name, creativeTabs);
    W_LanguageRegistry.addName(item, "Gunner (vs Player of other team)");
    W_LanguageRegistry.addNameForObject(item, "ja_jp", "対他チームプレイヤー 射撃手");
  }
  
  private void registerItemRangeFinder() {
    String name = "rangefinder";
    MCH_ItemRangeFinder item = new MCH_ItemRangeFinder(MCH_Config.ItemID_RangeFinder.prmInt);
    itemRangeFinder = item;
    registerItem((W_Item)item, name, creativeTabs);
    W_LanguageRegistry.addName(item, "Laser Rangefinder");
    W_LanguageRegistry.addNameForObject(item, "ja_jp", "レーザー レンジ ファインダー");
  }
  
  private void registerItemWrench() {
    String name = "wrench";
    MCH_ItemWrench item = new MCH_ItemWrench(MCH_Config.ItemID_Wrench.prmInt, Item.ToolMaterial.IRON);
    itemWrench = item;
    registerItem((W_Item)item, name, creativeTabs);
    W_LanguageRegistry.addName(item, "Wrench");
    W_LanguageRegistry.addNameForObject(item, "ja_jp", "レンチ");
  }
  
  public void registerItemInvisible() {
    String name = "internal";
    MCH_InvisibleItem item = new MCH_InvisibleItem(MCH_Config.ItemID_InvisibleItem.prmInt);
    invisibleItem = item;
    registerItem(item, name, null);
  }
  
  public void registerItemUavStation() {
    String[] dispName = { "UAV Station", "Portable UAV Controller" };
    String[] localName = { "UAVステーション", "携帯UAV制御端末" };
    itemUavStation = new MCH_ItemUavStation[MCH_ItemUavStation.UAV_STATION_KIND_NUM];
    String name = "uav_station";
    for (int i = 0; i < itemUavStation.length; i++) {
      String nn = (i > 0) ? ("" + (i + 1)) : "";
      MCH_ItemUavStation item = new MCH_ItemUavStation((MCH_Config.ItemID_UavStation[i]).prmInt, 1 + i);
      itemUavStation[i] = item;
      registerItem((W_Item)item, name + nn, creativeTabs);
      W_LanguageRegistry.addName(item, dispName[i]);
      W_LanguageRegistry.addNameForObject(item, "ja_jp", localName[i]);
    } 
  }
  
  public void registerItemParachute() {
    String name = "parachute";
    MCH_ItemParachute item = new MCH_ItemParachute(MCH_Config.ItemID_Parachute.prmInt);
    itemParachute = item;
    registerItem((W_Item)item, name, creativeTabs);
    W_LanguageRegistry.addName(item, "Parachute");
    W_LanguageRegistry.addNameForObject(item, "ja_jp", "パラシュート");
  }
  
  public void registerItemContainer() {
    String name = "container";
    MCH_ItemContainer item = new MCH_ItemContainer(MCH_Config.ItemID_Container.prmInt);
    itemContainer = item;
    registerItem((W_Item)item, name, creativeTabs);
    W_LanguageRegistry.addName(item, "Container");
    W_LanguageRegistry.addNameForObject(item, "ja_jp", "コンテナ");
  }
  
  public void registerItemLightWeapon() {
    String name = "fim92";
    MCH_ItemLightWeaponBase item = new MCH_ItemLightWeaponBase(MCH_Config.ItemID_Stinger.prmInt, itemStingerBullet);
    itemStinger = item;
    registerItem((W_Item)item, name, creativeTabs);
    W_LanguageRegistry.addName(item, "FIM-92 Stinger");
    name = "fgm148";
    item = new MCH_ItemLightWeaponBase(MCH_Config.ItemID_Stinger.prmInt, itemJavelinBullet);
    itemJavelin = item;
    registerItem((W_Item)item, name, creativeTabs);
    W_LanguageRegistry.addName(item, "FGM-148 Javelin");
  }
  
  public void registerItemLightWeaponBullet() {
    String name = "fim92_bullet";
    MCH_ItemLightWeaponBullet item = new MCH_ItemLightWeaponBullet(MCH_Config.ItemID_StingerMissile.prmInt);
    itemStingerBullet = item;
    registerItem((W_Item)item, name, creativeTabs);
    W_LanguageRegistry.addName(item, "FIM-92 Stinger missile");
    name = "fgm148_bullet";
    item = new MCH_ItemLightWeaponBullet(MCH_Config.ItemID_StingerMissile.prmInt);
    itemJavelinBullet = item;
    registerItem((W_Item)item, name, creativeTabs);
    W_LanguageRegistry.addName(item, "FGM-148 Javelin missile");
  }
  
  public void registerItemChain() {
    String name = "chain";
    MCH_ItemChain item = new MCH_ItemChain(MCH_Config.ItemID_Chain.prmInt);
    itemChain = item;
    registerItem((W_Item)item, name, creativeTabs);
    W_LanguageRegistry.addName(item, "Chain");
    W_LanguageRegistry.addNameForObject(item, "ja_jp", "鎖");
  }
  
  public void registerItemFuel() {
    String name = "fuel";
    MCH_ItemFuel item = new MCH_ItemFuel(MCH_Config.ItemID_Fuel.prmInt);
    itemFuel = item;
    registerItem((W_Item)item, name, creativeTabs);
    W_LanguageRegistry.addName(item, "Fuel");
    W_LanguageRegistry.addNameForObject(item, "ja_jp", "燃料");
  }
  
  public void registerItemGLTD() {
    String name = "gltd";
    MCH_ItemGLTD item = new MCH_ItemGLTD(MCH_Config.ItemID_GLTD.prmInt);
    itemGLTD = item;
    registerItem((W_Item)item, name, creativeTabs);
    W_LanguageRegistry.addName(item, "GLTD:Target Designator");
    W_LanguageRegistry.addNameForObject(item, "ja_jp", "GLTD:レーザー目標指示装置");
  }
  
  public static void registerItem(W_Item item, String name, MCH_CreativeTabs ct) {
    item.func_77655_b("mcheli:" + name);
    if (ct != null) {
      item.func_77637_a(ct);
      ct.addIconItem((Item)item);
    } 
    MCH_Items.register((Item)item, name);
  }
  
  public static void registerItemThrowable() {
    for (Map.Entry<String, MCH_ThrowableInfo> entry : (Iterable<Map.Entry<String, MCH_ThrowableInfo>>)ContentRegistries.throwable().entries()) {
      MCH_ThrowableInfo info = entry.getValue();
      info.item = (W_Item)new MCH_ItemThrowable(info.itemID);
      info.item.func_77625_d(info.stackSize);
      registerItem(info.item, entry.getKey(), creativeTabs);
      MCH_ItemThrowable.registerDispenseBehavior((Item)info.item);
      info.itemID = W_Item.getIdFromItem((Item)info.item) - 256;
      W_LanguageRegistry.addName(info.item, info.displayName);
      for (String lang : info.displayNameLang.keySet())
        W_LanguageRegistry.addNameForObject(info.item, lang, (String)info.displayNameLang.get(lang)); 
    } 
  }
  
  public static void registerItemAircraft() {
    for (Map.Entry<String, MCH_HeliInfo> entry : (Iterable<Map.Entry<String, MCH_HeliInfo>>)ContentRegistries.heli().entries()) {
      MCH_HeliInfo info = entry.getValue();
      info.item = new MCH_ItemHeli(info.itemID);
      info.item.func_77656_e(info.maxHp);
      if (!info.canRide && (info.ammoSupplyRange > 0.0F || info.fuelSupplyRange > 0.0F)) {
        registerItem((W_Item)info.item, entry.getKey(), creativeTabs);
      } else {
        registerItem((W_Item)info.item, entry.getKey(), creativeTabsHeli);
      } 
      MCH_ItemAircraft.registerDispenseBehavior((Item)info.item);
      info.itemID = W_Item.getIdFromItem((Item)info.item) - 256;
      W_LanguageRegistry.addName(info.item, info.displayName);
      for (String lang : info.displayNameLang.keySet())
        W_LanguageRegistry.addNameForObject(info.item, lang, (String)info.displayNameLang.get(lang)); 
    } 
    for (Map.Entry<String, MCP_PlaneInfo> entry : (Iterable<Map.Entry<String, MCP_PlaneInfo>>)ContentRegistries.plane().entries()) {
      MCP_PlaneInfo info = entry.getValue();
      info.item = new MCP_ItemPlane(info.itemID);
      info.item.func_77656_e(info.maxHp);
      if (!info.canRide && (info.ammoSupplyRange > 0.0F || info.fuelSupplyRange > 0.0F)) {
        registerItem((W_Item)info.item, entry.getKey(), creativeTabs);
      } else {
        registerItem((W_Item)info.item, entry.getKey(), creativeTabsPlane);
      } 
      MCH_ItemAircraft.registerDispenseBehavior((Item)info.item);
      info.itemID = W_Item.getIdFromItem((Item)info.item) - 256;
      W_LanguageRegistry.addName(info.item, info.displayName);
      for (String lang : info.displayNameLang.keySet())
        W_LanguageRegistry.addNameForObject(info.item, lang, (String)info.displayNameLang.get(lang)); 
    } 
    for (Map.Entry<String, MCH_TankInfo> entry : (Iterable<Map.Entry<String, MCH_TankInfo>>)ContentRegistries.tank().entries()) {
      MCH_TankInfo info = entry.getValue();
      info.item = new MCH_ItemTank(info.itemID);
      info.item.func_77656_e(info.maxHp);
      if (!info.canRide && (info.ammoSupplyRange > 0.0F || info.fuelSupplyRange > 0.0F)) {
        registerItem((W_Item)info.item, entry.getKey(), creativeTabs);
      } else {
        registerItem((W_Item)info.item, entry.getKey(), creativeTabsTank);
      } 
      MCH_ItemAircraft.registerDispenseBehavior((Item)info.item);
      info.itemID = W_Item.getIdFromItem((Item)info.item) - 256;
      W_LanguageRegistry.addName(info.item, info.displayName);
      for (String lang : info.displayNameLang.keySet())
        W_LanguageRegistry.addNameForObject(info.item, lang, (String)info.displayNameLang.get(lang)); 
    } 
    for (Map.Entry<String, MCH_VehicleInfo> entry : (Iterable<Map.Entry<String, MCH_VehicleInfo>>)ContentRegistries.vehicle().entries()) {
      MCH_VehicleInfo info = entry.getValue();
      info.item = new MCH_ItemVehicle(info.itemID);
      info.item.func_77656_e(info.maxHp);
      if (!info.canRide && (info.ammoSupplyRange > 0.0F || info.fuelSupplyRange > 0.0F)) {
        registerItem((W_Item)info.item, entry.getKey(), creativeTabs);
      } else {
        registerItem((W_Item)info.item, entry.getKey(), creativeTabsVehicle);
      } 
      MCH_ItemAircraft.registerDispenseBehavior((Item)info.item);
      info.itemID = W_Item.getIdFromItem((Item)info.item) - 256;
      W_LanguageRegistry.addName(info.item, info.displayName);
      for (String lang : info.displayNameLang.keySet())
        W_LanguageRegistry.addNameForObject(info.item, lang, (String)info.displayNameLang.get(lang)); 
    } 
  }
  
  @Deprecated
  public static Logger getLogger() {
    return MCH_Logger.get();
  }
  
  public static File getSource() {
    return sourceFile;
  }
  
  public static File getAddonDir() {
    return addonDir;
  }
  
  public static boolean isTodaySep01() {
    if (isSep01 == null) {
      Calendar c = Calendar.getInstance();
      isSep01 = Boolean.valueOf((c.get(2) + 1 == 9 && c.get(5) == 1));
    } 
    return isSep01.booleanValue();
  }
}
