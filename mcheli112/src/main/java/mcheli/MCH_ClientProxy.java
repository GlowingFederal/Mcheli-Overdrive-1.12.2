package mcheli;

import java.io.File;
import java.util.List;
import mcheli.__helper.addon.AddonManager;
import mcheli.__helper.addon.AddonPack;
import mcheli.__helper.client.MCH_ItemModelRenderers;
import mcheli.__helper.client.RecipeDescriptionManager;
import mcheli.__helper.client._IModelCustom;
import mcheli.__helper.client.model.LegacyModelLoader;
import mcheli.__helper.client.renderer.item.BuiltInDraftingTableItemRenderer;
import mcheli.__helper.client.renderer.item.BuiltInGLTDItemRenderer;
import mcheli.__helper.client.renderer.item.BuiltInInvisibleItemRenderer;
import mcheli.__helper.client.renderer.item.BuiltInLightWeaponItemRenderer;
import mcheli.__helper.client.renderer.item.BuiltInRangeFinderItemRenderer;
import mcheli.__helper.client.renderer.item.BuiltInWrenchItemRenderer;
import mcheli.__helper.client.renderer.item.IItemModelRenderer;
import mcheli.__helper.info.ContentRegistries;
import mcheli.aircraft.MCH_AircraftInfo;
import mcheli.aircraft.MCH_EntityAircraft;
import mcheli.aircraft.MCH_EntityHide;
import mcheli.aircraft.MCH_EntitySeat;
import mcheli.aircraft.MCH_RenderAircraft;
import mcheli.aircraft.MCH_SoundUpdater;
import mcheli.block.MCH_DraftingTableRenderer;
import mcheli.block.MCH_DraftingTableTileEntity;
import mcheli.chain.MCH_EntityChain;
import mcheli.chain.MCH_RenderChain;
import mcheli.command.MCH_GuiTitle;
import mcheli.container.MCH_EntityContainer;
import mcheli.container.MCH_RenderContainer;
import mcheli.debug.MCH_RenderTest;
import mcheli.flare.MCH_EntityFlare;
import mcheli.flare.MCH_RenderFlare;
import mcheli.gltd.MCH_EntityGLTD;
import mcheli.gltd.MCH_RenderGLTD;
import mcheli.helicopter.MCH_EntityHeli;
import mcheli.helicopter.MCH_HeliInfo;
import mcheli.helicopter.MCH_RenderHeli;
import mcheli.mob.MCH_EntityGunner;
import mcheli.mob.MCH_RenderGunner;
import mcheli.multiplay.MCH_MultiplayClient;
import mcheli.parachute.MCH_EntityParachute;
import mcheli.parachute.MCH_RenderParachute;
import mcheli.particles.MCH_ParticlesUtil;
import mcheli.plane.MCP_EntityPlane;
import mcheli.plane.MCP_PlaneInfo;
import mcheli.plane.MCP_RenderPlane;
import mcheli.tank.MCH_EntityTank;
import mcheli.tank.MCH_RenderTank;
import mcheli.tank.MCH_TankInfo;
import mcheli.throwable.MCH_EntityThrowable;
import mcheli.throwable.MCH_RenderThrowable;
import mcheli.throwable.MCH_ThrowableInfo;
import mcheli.uav.MCH_EntityUavStation;
import mcheli.uav.MCH_RenderUavStation;
import mcheli.vehicle.MCH_EntityVehicle;
import mcheli.vehicle.MCH_RenderVehicle;
import mcheli.vehicle.MCH_VehicleInfo;
import mcheli.weapon.MCH_BulletModel;
import mcheli.weapon.MCH_DefaultBulletModels;
import mcheli.weapon.MCH_EntityA10;
import mcheli.weapon.MCH_EntityAAMissile;
import mcheli.weapon.MCH_EntityASMissile;
import mcheli.weapon.MCH_EntityATMissile;
import mcheli.weapon.MCH_EntityBomb;
import mcheli.weapon.MCH_EntityBullet;
import mcheli.weapon.MCH_EntityCartridge;
import mcheli.weapon.MCH_EntityDispensedItem;
import mcheli.weapon.MCH_EntityMarkerRocket;
import mcheli.weapon.MCH_EntityRocket;
import mcheli.weapon.MCH_EntityTorpedo;
import mcheli.weapon.MCH_EntityTvMissile;
import mcheli.weapon.MCH_RenderA10;
import mcheli.weapon.MCH_RenderAAMissile;
import mcheli.weapon.MCH_RenderASMissile;
import mcheli.weapon.MCH_RenderBomb;
import mcheli.weapon.MCH_RenderBullet;
import mcheli.weapon.MCH_RenderCartridge;
import mcheli.weapon.MCH_RenderNone;
import mcheli.weapon.MCH_RenderTvMissile;
import mcheli.weapon.MCH_WeaponInfo;
import mcheli.wrapper.W_LanguageRegistry;
import mcheli.wrapper.W_McClient;
import mcheli.wrapper.W_Reflection;
import mcheli.wrapper.W_TickRegistry;
import mcheli.wrapper.modelloader.W_ModelCustom;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.relauncher.Side;

public class MCH_ClientProxy extends MCH_CommonProxy {
  public String lastLoadHUDPath = "";
  
  public String getDataDir() {
    return (Minecraft.func_71410_x()).field_71412_D.getPath();
  }
  
  public void registerRenderer() {
    RenderingRegistry.registerEntityRenderingHandler(MCH_EntitySeat.class, 
        MCH_RenderTest.factory(0.0F, 0.3125F, 0.0F, "seat"));
    RenderingRegistry.registerEntityRenderingHandler(MCH_EntityHeli.class, MCH_RenderHeli.FACTORY);
    RenderingRegistry.registerEntityRenderingHandler(MCP_EntityPlane.class, MCP_RenderPlane.FACTORY);
    RenderingRegistry.registerEntityRenderingHandler(MCH_EntityTank.class, MCH_RenderTank.FACTORY);
    RenderingRegistry.registerEntityRenderingHandler(MCH_EntityGLTD.class, MCH_RenderGLTD.FACTORY);
    RenderingRegistry.registerEntityRenderingHandler(MCH_EntityChain.class, MCH_RenderChain.FACTORY);
    RenderingRegistry.registerEntityRenderingHandler(MCH_EntityParachute.class, MCH_RenderParachute.FACTORY);
    RenderingRegistry.registerEntityRenderingHandler(MCH_EntityContainer.class, MCH_RenderContainer.FACTORY);
    RenderingRegistry.registerEntityRenderingHandler(MCH_EntityVehicle.class, MCH_RenderVehicle.FACTORY);
    RenderingRegistry.registerEntityRenderingHandler(MCH_EntityUavStation.class, MCH_RenderUavStation.FACTORY);
    RenderingRegistry.registerEntityRenderingHandler(MCH_EntityCartridge.class, MCH_RenderCartridge.FACTORY);
    RenderingRegistry.registerEntityRenderingHandler(MCH_EntityHide.class, MCH_RenderNull.FACTORY);
    RenderingRegistry.registerEntityRenderingHandler(MCH_ViewEntityDummy.class, MCH_RenderNull.FACTORY);
    RenderingRegistry.registerEntityRenderingHandler(MCH_EntityRocket.class, MCH_RenderBullet.FACTORY);
    RenderingRegistry.registerEntityRenderingHandler(MCH_EntityTvMissile.class, MCH_RenderTvMissile.FACTORY);
    RenderingRegistry.registerEntityRenderingHandler(MCH_EntityBullet.class, MCH_RenderBullet.FACTORY);
    RenderingRegistry.registerEntityRenderingHandler(MCH_EntityA10.class, MCH_RenderA10.FACTORY);
    RenderingRegistry.registerEntityRenderingHandler(MCH_EntityAAMissile.class, MCH_RenderAAMissile.FACTORY);
    RenderingRegistry.registerEntityRenderingHandler(MCH_EntityASMissile.class, MCH_RenderASMissile.FACTORY);
    RenderingRegistry.registerEntityRenderingHandler(MCH_EntityATMissile.class, MCH_RenderTvMissile.FACTORY);
    RenderingRegistry.registerEntityRenderingHandler(MCH_EntityTorpedo.class, MCH_RenderBullet.FACTORY);
    RenderingRegistry.registerEntityRenderingHandler(MCH_EntityBomb.class, MCH_RenderBomb.FACTORY);
    RenderingRegistry.registerEntityRenderingHandler(MCH_EntityMarkerRocket.class, MCH_RenderBullet.FACTORY);
    RenderingRegistry.registerEntityRenderingHandler(MCH_EntityDispensedItem.class, MCH_RenderNone.FACTORY);
    RenderingRegistry.registerEntityRenderingHandler(MCH_EntityFlare.class, MCH_RenderFlare.FACTORY);
    RenderingRegistry.registerEntityRenderingHandler(MCH_EntityThrowable.class, MCH_RenderThrowable.FACTORY);
    RenderingRegistry.registerEntityRenderingHandler(MCH_EntityGunner.class, MCH_RenderGunner.FACTORY);
    MCH_ItemModelRenderers.registerRenderer((Item)MCH_MOD.itemJavelin, (IItemModelRenderer)new BuiltInLightWeaponItemRenderer());
    MCH_ItemModelRenderers.registerRenderer((Item)MCH_MOD.itemStinger, (IItemModelRenderer)new BuiltInLightWeaponItemRenderer());
    MCH_ItemModelRenderers.registerRenderer((Item)MCH_MOD.invisibleItem, (IItemModelRenderer)new BuiltInInvisibleItemRenderer());
    MCH_ItemModelRenderers.registerRenderer((Item)MCH_MOD.itemGLTD, (IItemModelRenderer)new BuiltInGLTDItemRenderer());
    MCH_ItemModelRenderers.registerRenderer((Item)MCH_MOD.itemWrench, (IItemModelRenderer)new BuiltInWrenchItemRenderer());
    MCH_ItemModelRenderers.registerRenderer((Item)MCH_MOD.itemRangeFinder, (IItemModelRenderer)new BuiltInRangeFinderItemRenderer());
    MCH_ItemModelRenderers.registerRenderer((Item)MCH_MOD.itemDraftingTable, (IItemModelRenderer)new BuiltInDraftingTableItemRenderer());
    ModelLoaderRegistry.registerLoader((ICustomModelLoader)LegacyModelLoader.INSTANCE);
  }
  
  public void registerBlockRenderer() {
    ClientRegistry.bindTileEntitySpecialRenderer(MCH_DraftingTableTileEntity.class, (TileEntitySpecialRenderer)new MCH_DraftingTableRenderer());
  }
  
  public void registerModels() {
    MCH_ModelManager.setForceReloadMode(true);
    MCH_RenderAircraft.debugModel = MCH_ModelManager.load("box");
    MCH_ModelManager.load("a-10");
    MCH_RenderGLTD.model = MCH_ModelManager.load("gltd");
    MCH_ModelManager.load("chain");
    MCH_ModelManager.load("container");
    MCH_ModelManager.load("parachute1");
    MCH_ModelManager.load("parachute2");
    MCH_ModelManager.load("lweapons", "fim92");
    MCH_ModelManager.load("lweapons", "fgm148");
    for (String s : MCH_RenderUavStation.MODEL_NAME)
      MCH_ModelManager.load(s); 
    MCH_ModelManager.load("wrench");
    MCH_ModelManager.load("rangefinder");
    ContentRegistries.heli().forEachValue(info -> registerModelsHeli(info, false));
    ContentRegistries.plane().forEachValue(info -> registerModelsPlane(info, false));
    ContentRegistries.tank().forEachValue(info -> registerModelsTank(info, false));
    ContentRegistries.vehicle().forEachValue(info -> registerModelsVehicle(info, false));
    registerModels_Bullet();
    MCH_DefaultBulletModels.Bullet = loadBulletModel("bullet");
    MCH_DefaultBulletModels.AAMissile = loadBulletModel("aamissile");
    MCH_DefaultBulletModels.ATMissile = loadBulletModel("asmissile");
    MCH_DefaultBulletModels.ASMissile = loadBulletModel("asmissile");
    MCH_DefaultBulletModels.Bomb = loadBulletModel("bomb");
    MCH_DefaultBulletModels.Rocket = loadBulletModel("rocket");
    MCH_DefaultBulletModels.Torpedo = loadBulletModel("torpedo");
    for (MCH_ThrowableInfo wi : ContentRegistries.throwable().values())
      wi.model = MCH_ModelManager.load("throwable", wi.name); 
    MCH_ModelManager.load("blocks", "drafting_table");
  }
  
  public static void registerModels_Bullet() {
    for (MCH_WeaponInfo wi : ContentRegistries.weapon().values()) {
      _IModelCustom m = null;
      if (!wi.bulletModelName.isEmpty()) {
        m = MCH_ModelManager.load("bullets", wi.bulletModelName);
        if (m != null)
          wi.bulletModel = new MCH_BulletModel(wi.bulletModelName, m); 
      } 
      if (!wi.bombletModelName.isEmpty()) {
        m = MCH_ModelManager.load("bullets", wi.bombletModelName);
        if (m != null)
          wi.bombletModel = new MCH_BulletModel(wi.bombletModelName, m); 
      } 
      if (wi.cartridge != null && !wi.cartridge.name.isEmpty()) {
        wi.cartridge.model = MCH_ModelManager.load("bullets", wi.cartridge.name);
        if (wi.cartridge.model == null)
          wi.cartridge = null; 
      } 
    } 
  }
  
  public void registerModelsHeli(MCH_HeliInfo info, boolean reload) {
    MCH_ModelManager.setForceReloadMode(reload);
    info.model = MCH_ModelManager.load("helicopters", info.name);
    for (MCH_HeliInfo.Rotor rotor : info.rotorList)
      rotor.model = loadPartModel("helicopters", info.name, info.model, rotor.modelName); 
    registerCommonPart("helicopters", (MCH_AircraftInfo)info);
    MCH_ModelManager.setForceReloadMode(false);
  }
  
  public void registerModelsPlane(MCP_PlaneInfo info, boolean reload) {
    MCH_ModelManager.setForceReloadMode(reload);
    info.model = MCH_ModelManager.load("planes", info.name);
    for (MCH_AircraftInfo.DrawnPart n : info.nozzles)
      n.model = loadPartModel("planes", info.name, info.model, n.modelName); 
    for (MCP_PlaneInfo.Rotor r : info.rotorList) {
      r.model = loadPartModel("planes", info.name, info.model, r.modelName);
      for (MCP_PlaneInfo.Blade b : r.blades)
        b.model = loadPartModel("planes", info.name, info.model, b.modelName); 
    } 
    for (MCP_PlaneInfo.Wing w : info.wingList) {
      w.model = loadPartModel("planes", info.name, info.model, w.modelName);
      if (w.pylonList != null)
        for (MCP_PlaneInfo.Pylon p : w.pylonList)
          p.model = loadPartModel("planes", info.name, info.model, p.modelName);  
    } 
    registerCommonPart("planes", (MCH_AircraftInfo)info);
    MCH_ModelManager.setForceReloadMode(false);
  }
  
  public void registerModelsVehicle(MCH_VehicleInfo info, boolean reload) {
    MCH_ModelManager.setForceReloadMode(reload);
    info.model = MCH_ModelManager.load("vehicles", info.name);
    for (MCH_VehicleInfo.VPart vp : info.partList) {
      vp.model = loadPartModel("vehicles", info.name, info.model, vp.modelName);
      if (vp.child != null)
        registerVCPModels(info, vp); 
    } 
    registerCommonPart("vehicles", (MCH_AircraftInfo)info);
    MCH_ModelManager.setForceReloadMode(false);
  }
  
  public void registerModelsTank(MCH_TankInfo info, boolean reload) {
    MCH_ModelManager.setForceReloadMode(reload);
    info.model = MCH_ModelManager.load("tanks", info.name);
    registerCommonPart("tanks", (MCH_AircraftInfo)info);
    MCH_ModelManager.setForceReloadMode(false);
  }
  
  private MCH_BulletModel loadBulletModel(String name) {
    _IModelCustom m = MCH_ModelManager.load("bullets", name);
    return (m != null) ? new MCH_BulletModel(name, m) : null;
  }
  
  private _IModelCustom loadPartModel(String path, String name, _IModelCustom body, String part) {
    if (body instanceof W_ModelCustom)
      if (((W_ModelCustom)body).containsPart("$" + part))
        return null;  
    return MCH_ModelManager.load(path, name + "_" + part);
  }
  
  private void registerCommonPart(String path, MCH_AircraftInfo info) {
    for (MCH_AircraftInfo.Hatch h : info.hatchList)
      h.model = loadPartModel(path, info.name, info.model, h.modelName); 
    for (MCH_AircraftInfo.Camera c : info.cameraList)
      c.model = loadPartModel(path, info.name, info.model, c.modelName); 
    for (MCH_AircraftInfo.Throttle c : info.partThrottle)
      c.model = loadPartModel(path, info.name, info.model, c.modelName); 
    for (MCH_AircraftInfo.RotPart c : info.partRotPart)
      c.model = loadPartModel(path, info.name, info.model, c.modelName); 
    for (MCH_AircraftInfo.PartWeapon p : info.partWeapon) {
      p.model = loadPartModel(path, info.name, info.model, p.modelName);
      for (MCH_AircraftInfo.PartWeaponChild wc : p.child)
        wc.model = loadPartModel(path, info.name, info.model, wc.modelName); 
    } 
    for (MCH_AircraftInfo.Canopy c : info.canopyList)
      c.model = loadPartModel(path, info.name, info.model, c.modelName); 
    for (MCH_AircraftInfo.DrawnPart n : info.landingGear)
      n.model = loadPartModel(path, info.name, info.model, n.modelName); 
    for (MCH_AircraftInfo.WeaponBay w : info.partWeaponBay)
      w.model = loadPartModel(path, info.name, info.model, w.modelName); 
    for (MCH_AircraftInfo.CrawlerTrack c : info.partCrawlerTrack)
      c.model = loadPartModel(path, info.name, info.model, c.modelName); 
    for (MCH_AircraftInfo.TrackRoller c : info.partTrackRoller)
      c.model = loadPartModel(path, info.name, info.model, c.modelName); 
    for (MCH_AircraftInfo.PartWheel c : info.partWheel)
      c.model = loadPartModel(path, info.name, info.model, c.modelName); 
    for (MCH_AircraftInfo.PartWheel c : info.partSteeringWheel)
      c.model = loadPartModel(path, info.name, info.model, c.modelName); 
  }
  
  private void registerVCPModels(MCH_VehicleInfo info, MCH_VehicleInfo.VPart vp) {
    for (MCH_VehicleInfo.VPart vcp : vp.child) {
      vcp.model = loadPartModel("vehicles", info.name, info.model, vcp.modelName);
      if (vcp.child != null)
        registerVCPModels(info, vcp); 
    } 
  }
  
  public void registerClientTick() {
    Minecraft mc = Minecraft.func_71410_x();
    MCH_ClientCommonTickHandler.instance = new MCH_ClientCommonTickHandler(mc, MCH_MOD.config);
    W_TickRegistry.registerTickHandler(MCH_ClientCommonTickHandler.instance, Side.CLIENT);
  }
  
  public boolean isRemote() {
    return true;
  }
  
  public String side() {
    return "Client";
  }
  
  public MCH_SoundUpdater CreateSoundUpdater(MCH_EntityAircraft aircraft) {
    if (aircraft == null || !aircraft.field_70170_p.field_72995_K)
      return null; 
    return new MCH_SoundUpdater(Minecraft.func_71410_x(), aircraft, (Minecraft.func_71410_x()).field_71439_g);
  }
  
  public void registerSounds() {
    super.registerSounds();
    W_McClient.addSound("alert.ogg");
    W_McClient.addSound("locked.ogg");
    W_McClient.addSound("gltd.ogg");
    W_McClient.addSound("zoom.ogg");
    W_McClient.addSound("ng.ogg");
    W_McClient.addSound("a-10_snd.ogg");
    W_McClient.addSound("gau-8_snd.ogg");
    W_McClient.addSound("hit.ogg");
    W_McClient.addSound("helidmg.ogg");
    W_McClient.addSound("heli.ogg");
    W_McClient.addSound("plane.ogg");
    W_McClient.addSound("plane_cc.ogg");
    W_McClient.addSound("plane_cv.ogg");
    W_McClient.addSound("chain.ogg");
    W_McClient.addSound("chain_ct.ogg");
    W_McClient.addSound("eject_seat.ogg");
    W_McClient.addSound("fim92_snd.ogg");
    W_McClient.addSound("fim92_reload.ogg");
    W_McClient.addSound("lockon.ogg");
    for (MCH_WeaponInfo info : ContentRegistries.weapon().values())
      W_McClient.addSound(info.soundFileName + ".ogg"); 
    for (MCH_AircraftInfo info : ContentRegistries.plane().values()) {
      if (!info.soundMove.isEmpty())
        W_McClient.addSound(info.soundMove + ".ogg"); 
    } 
    for (MCH_AircraftInfo info : ContentRegistries.heli().values()) {
      if (!info.soundMove.isEmpty())
        W_McClient.addSound(info.soundMove + ".ogg"); 
    } 
    for (MCH_AircraftInfo info : ContentRegistries.tank().values()) {
      if (!info.soundMove.isEmpty())
        W_McClient.addSound(info.soundMove + ".ogg"); 
    } 
    for (MCH_AircraftInfo info : ContentRegistries.vehicle().values()) {
      if (!info.soundMove.isEmpty())
        W_McClient.addSound(info.soundMove + ".ogg"); 
    } 
  }
  
  public void loadConfig(String fileName) {
    this.lastConfigFileName = fileName;
    this.config = new MCH_Config((Minecraft.func_71410_x()).field_71412_D.getPath(), "/" + fileName);
    this.config.load();
    this.config.write();
  }
  
  public void reconfig() {
    MCH_Lib.DbgLog(false, "MCH_ClientProxy.reconfig()", new Object[0]);
    loadConfig(this.lastConfigFileName);
    MCH_ClientCommonTickHandler.instance.updatekeybind(this.config);
  }
  
  public void reloadHUD() {
    ContentRegistries.hud().reloadAll();
  }
  
  public Entity getClientPlayer() {
    return (Entity)(Minecraft.func_71410_x()).field_71439_g;
  }
  
  public void init() {
    MinecraftForge.EVENT_BUS.register(new MCH_ParticlesUtil());
    MinecraftForge.EVENT_BUS.register(new MCH_ClientEventHook());
  }
  
  public void setCreativeDigDelay(int n) {
    W_Reflection.setCreativeDigSpeed(n);
  }
  
  public boolean isFirstPerson() {
    return ((Minecraft.func_71410_x()).field_71474_y.field_74320_O == 0);
  }
  
  public boolean isSinglePlayer() {
    return Minecraft.func_71410_x().func_71356_B();
  }
  
  public void readClientModList() {
    try {
      Minecraft mc = Minecraft.func_71410_x();
      MCH_MultiplayClient.readModList(mc.func_110432_I().func_148255_b(), mc.func_110432_I().func_111285_a());
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public void printChatMessage(ITextComponent chat, int showTime, int pos) {
    ((MCH_GuiTitle)MCH_ClientCommonTickHandler.instance.gui_Title).setupTitle(chat, showTime, pos);
  }
  
  public void hitBullet() {
    MCH_ClientCommonTickHandler.instance.gui_Common.hitBullet();
  }
  
  public void clientLocked() {
    MCH_ClientCommonTickHandler.isLocked = true;
  }
  
  public void setRenderEntityDistanceWeight(double renderDistWeight) {
    Entity.func_184227_b(renderDistWeight);
  }
  
  public List<AddonPack> loadAddonPacks(File addonDir) {
    return AddonManager.loadAddonsAndAddResources(addonDir);
  }
  
  public boolean canLoadContentDirName(String dir) {
    return ("hud".equals(dir) || super.canLoadContentDirName(dir));
  }
  
  public void updateGeneratedLanguage() {
    W_LanguageRegistry.updateGeneratedLang();
  }
  
  public void registerRecipeDescriptions() {
    RecipeDescriptionManager.registerDescriptionInfos(Minecraft.func_71410_x().func_110442_L());
  }
}
