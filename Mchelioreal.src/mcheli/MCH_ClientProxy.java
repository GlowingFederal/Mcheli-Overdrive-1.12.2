/*     */ package mcheli;
/*     */ import java.io.File;
/*     */ import java.util.List;
/*     */ import mcheli.__helper.addon.AddonManager;
/*     */ import mcheli.__helper.addon.AddonPack;
/*     */ import mcheli.__helper.client.MCH_ItemModelRenderers;
/*     */ import mcheli.__helper.client.RecipeDescriptionManager;
/*     */ import mcheli.__helper.client._IModelCustom;
/*     */ import mcheli.__helper.client.model.LegacyModelLoader;
/*     */ import mcheli.__helper.client.renderer.item.BuiltInDraftingTableItemRenderer;
/*     */ import mcheli.__helper.client.renderer.item.BuiltInGLTDItemRenderer;
/*     */ import mcheli.__helper.client.renderer.item.BuiltInInvisibleItemRenderer;
/*     */ import mcheli.__helper.client.renderer.item.BuiltInLightWeaponItemRenderer;
/*     */ import mcheli.__helper.client.renderer.item.BuiltInRangeFinderItemRenderer;
/*     */ import mcheli.__helper.client.renderer.item.BuiltInWrenchItemRenderer;
/*     */ import mcheli.__helper.client.renderer.item.IItemModelRenderer;
/*     */ import mcheli.__helper.info.ContentRegistries;
/*     */ import mcheli.aircraft.MCH_AircraftInfo;
/*     */ import mcheli.aircraft.MCH_EntityAircraft;
/*     */ import mcheli.aircraft.MCH_EntityHide;
/*     */ import mcheli.aircraft.MCH_EntitySeat;
/*     */ import mcheli.aircraft.MCH_RenderAircraft;
/*     */ import mcheli.aircraft.MCH_SoundUpdater;
/*     */ import mcheli.block.MCH_DraftingTableRenderer;
/*     */ import mcheli.block.MCH_DraftingTableTileEntity;
/*     */ import mcheli.chain.MCH_EntityChain;
/*     */ import mcheli.chain.MCH_RenderChain;
/*     */ import mcheli.command.MCH_GuiTitle;
/*     */ import mcheli.container.MCH_EntityContainer;
/*     */ import mcheli.container.MCH_RenderContainer;
/*     */ import mcheli.debug.MCH_RenderTest;
/*     */ import mcheli.flare.MCH_EntityFlare;
/*     */ import mcheli.flare.MCH_RenderFlare;
/*     */ import mcheli.gltd.MCH_EntityGLTD;
/*     */ import mcheli.gltd.MCH_RenderGLTD;
/*     */ import mcheli.helicopter.MCH_EntityHeli;
/*     */ import mcheli.helicopter.MCH_HeliInfo;
/*     */ import mcheli.helicopter.MCH_RenderHeli;
/*     */ import mcheli.mob.MCH_EntityGunner;
/*     */ import mcheli.mob.MCH_RenderGunner;
/*     */ import mcheli.multiplay.MCH_MultiplayClient;
/*     */ import mcheli.parachute.MCH_EntityParachute;
/*     */ import mcheli.parachute.MCH_RenderParachute;
/*     */ import mcheli.particles.MCH_ParticlesUtil;
/*     */ import mcheli.plane.MCP_EntityPlane;
/*     */ import mcheli.plane.MCP_PlaneInfo;
/*     */ import mcheli.plane.MCP_RenderPlane;
/*     */ import mcheli.tank.MCH_EntityTank;
/*     */ import mcheli.tank.MCH_RenderTank;
/*     */ import mcheli.tank.MCH_TankInfo;
/*     */ import mcheli.throwable.MCH_EntityThrowable;
/*     */ import mcheli.throwable.MCH_RenderThrowable;
/*     */ import mcheli.throwable.MCH_ThrowableInfo;
/*     */ import mcheli.uav.MCH_EntityUavStation;
/*     */ import mcheli.uav.MCH_RenderUavStation;
/*     */ import mcheli.vehicle.MCH_EntityVehicle;
/*     */ import mcheli.vehicle.MCH_RenderVehicle;
/*     */ import mcheli.vehicle.MCH_VehicleInfo;
/*     */ import mcheli.weapon.MCH_BulletModel;
/*     */ import mcheli.weapon.MCH_DefaultBulletModels;
/*     */ import mcheli.weapon.MCH_EntityA10;
/*     */ import mcheli.weapon.MCH_EntityAAMissile;
/*     */ import mcheli.weapon.MCH_EntityASMissile;
/*     */ import mcheli.weapon.MCH_EntityATMissile;
/*     */ import mcheli.weapon.MCH_EntityBomb;
/*     */ import mcheli.weapon.MCH_EntityBullet;
/*     */ import mcheli.weapon.MCH_EntityCartridge;
/*     */ import mcheli.weapon.MCH_EntityDispensedItem;
/*     */ import mcheli.weapon.MCH_EntityMarkerRocket;
/*     */ import mcheli.weapon.MCH_EntityRocket;
/*     */ import mcheli.weapon.MCH_EntityTorpedo;
/*     */ import mcheli.weapon.MCH_EntityTvMissile;
/*     */ import mcheli.weapon.MCH_RenderA10;
/*     */ import mcheli.weapon.MCH_RenderAAMissile;
/*     */ import mcheli.weapon.MCH_RenderASMissile;
/*     */ import mcheli.weapon.MCH_RenderBomb;
/*     */ import mcheli.weapon.MCH_RenderBullet;
/*     */ import mcheli.weapon.MCH_RenderCartridge;
/*     */ import mcheli.weapon.MCH_RenderNone;
/*     */ import mcheli.weapon.MCH_RenderTvMissile;
/*     */ import mcheli.weapon.MCH_WeaponInfo;
/*     */ import mcheli.wrapper.W_LanguageRegistry;
/*     */ import mcheli.wrapper.W_McClient;
/*     */ import mcheli.wrapper.W_Reflection;
/*     */ import mcheli.wrapper.W_TickRegistry;
/*     */ import mcheli.wrapper.modelloader.W_ModelCustom;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.util.text.ITextComponent;
/*     */ import net.minecraftforge.client.model.ICustomModelLoader;
/*     */ import net.minecraftforge.client.model.ModelLoaderRegistry;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.fml.client.registry.ClientRegistry;
/*     */ import net.minecraftforge.fml.client.registry.RenderingRegistry;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ 
/*     */ public class MCH_ClientProxy extends MCH_CommonProxy {
/* 100 */   public String lastLoadHUDPath = "";
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDataDir() {
/* 105 */     return (Minecraft.func_71410_x()).field_71412_D.getPath();
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
/*     */   public void registerRenderer() {
/* 145 */     RenderingRegistry.registerEntityRenderingHandler(MCH_EntitySeat.class, 
/* 146 */         MCH_RenderTest.factory(0.0F, 0.3125F, 0.0F, "seat"));
/* 147 */     RenderingRegistry.registerEntityRenderingHandler(MCH_EntityHeli.class, MCH_RenderHeli.FACTORY);
/* 148 */     RenderingRegistry.registerEntityRenderingHandler(MCP_EntityPlane.class, MCP_RenderPlane.FACTORY);
/* 149 */     RenderingRegistry.registerEntityRenderingHandler(MCH_EntityTank.class, MCH_RenderTank.FACTORY);
/* 150 */     RenderingRegistry.registerEntityRenderingHandler(MCH_EntityGLTD.class, MCH_RenderGLTD.FACTORY);
/* 151 */     RenderingRegistry.registerEntityRenderingHandler(MCH_EntityChain.class, MCH_RenderChain.FACTORY);
/* 152 */     RenderingRegistry.registerEntityRenderingHandler(MCH_EntityParachute.class, MCH_RenderParachute.FACTORY);
/* 153 */     RenderingRegistry.registerEntityRenderingHandler(MCH_EntityContainer.class, MCH_RenderContainer.FACTORY);
/* 154 */     RenderingRegistry.registerEntityRenderingHandler(MCH_EntityVehicle.class, MCH_RenderVehicle.FACTORY);
/* 155 */     RenderingRegistry.registerEntityRenderingHandler(MCH_EntityUavStation.class, MCH_RenderUavStation.FACTORY);
/* 156 */     RenderingRegistry.registerEntityRenderingHandler(MCH_EntityCartridge.class, MCH_RenderCartridge.FACTORY);
/* 157 */     RenderingRegistry.registerEntityRenderingHandler(MCH_EntityHide.class, MCH_RenderNull.FACTORY);
/*     */     
/* 159 */     RenderingRegistry.registerEntityRenderingHandler(MCH_ViewEntityDummy.class, MCH_RenderNull.FACTORY);
/*     */     
/* 161 */     RenderingRegistry.registerEntityRenderingHandler(MCH_EntityRocket.class, MCH_RenderBullet.FACTORY);
/* 162 */     RenderingRegistry.registerEntityRenderingHandler(MCH_EntityTvMissile.class, MCH_RenderTvMissile.FACTORY);
/* 163 */     RenderingRegistry.registerEntityRenderingHandler(MCH_EntityBullet.class, MCH_RenderBullet.FACTORY);
/* 164 */     RenderingRegistry.registerEntityRenderingHandler(MCH_EntityA10.class, MCH_RenderA10.FACTORY);
/* 165 */     RenderingRegistry.registerEntityRenderingHandler(MCH_EntityAAMissile.class, MCH_RenderAAMissile.FACTORY);
/* 166 */     RenderingRegistry.registerEntityRenderingHandler(MCH_EntityASMissile.class, MCH_RenderASMissile.FACTORY);
/* 167 */     RenderingRegistry.registerEntityRenderingHandler(MCH_EntityATMissile.class, MCH_RenderTvMissile.FACTORY);
/* 168 */     RenderingRegistry.registerEntityRenderingHandler(MCH_EntityTorpedo.class, MCH_RenderBullet.FACTORY);
/* 169 */     RenderingRegistry.registerEntityRenderingHandler(MCH_EntityBomb.class, MCH_RenderBomb.FACTORY);
/* 170 */     RenderingRegistry.registerEntityRenderingHandler(MCH_EntityMarkerRocket.class, MCH_RenderBullet.FACTORY);
/* 171 */     RenderingRegistry.registerEntityRenderingHandler(MCH_EntityDispensedItem.class, MCH_RenderNone.FACTORY);
/*     */     
/* 173 */     RenderingRegistry.registerEntityRenderingHandler(MCH_EntityFlare.class, MCH_RenderFlare.FACTORY);
/*     */     
/* 175 */     RenderingRegistry.registerEntityRenderingHandler(MCH_EntityThrowable.class, MCH_RenderThrowable.FACTORY);
/*     */     
/* 177 */     RenderingRegistry.registerEntityRenderingHandler(MCH_EntityGunner.class, MCH_RenderGunner.FACTORY);
/*     */ 
/*     */     
/* 180 */     MCH_ItemModelRenderers.registerRenderer((Item)MCH_MOD.itemJavelin, (IItemModelRenderer)new BuiltInLightWeaponItemRenderer());
/*     */     
/* 182 */     MCH_ItemModelRenderers.registerRenderer((Item)MCH_MOD.itemStinger, (IItemModelRenderer)new BuiltInLightWeaponItemRenderer());
/*     */     
/* 184 */     MCH_ItemModelRenderers.registerRenderer((Item)MCH_MOD.invisibleItem, (IItemModelRenderer)new BuiltInInvisibleItemRenderer());
/*     */ 
/*     */     
/* 187 */     MCH_ItemModelRenderers.registerRenderer((Item)MCH_MOD.itemGLTD, (IItemModelRenderer)new BuiltInGLTDItemRenderer());
/*     */     
/* 189 */     MCH_ItemModelRenderers.registerRenderer((Item)MCH_MOD.itemWrench, (IItemModelRenderer)new BuiltInWrenchItemRenderer());
/*     */     
/* 191 */     MCH_ItemModelRenderers.registerRenderer((Item)MCH_MOD.itemRangeFinder, (IItemModelRenderer)new BuiltInRangeFinderItemRenderer());
/*     */     
/* 193 */     MCH_ItemModelRenderers.registerRenderer((Item)MCH_MOD.itemDraftingTable, (IItemModelRenderer)new BuiltInDraftingTableItemRenderer());
/*     */     
/* 195 */     ModelLoaderRegistry.registerLoader((ICustomModelLoader)LegacyModelLoader.INSTANCE);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerBlockRenderer() {
/* 201 */     ClientRegistry.bindTileEntitySpecialRenderer(MCH_DraftingTableTileEntity.class, (TileEntitySpecialRenderer)new MCH_DraftingTableRenderer());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerModels() {
/* 210 */     MCH_ModelManager.setForceReloadMode(true);
/*     */     
/* 212 */     MCH_RenderAircraft.debugModel = MCH_ModelManager.load("box");
/* 213 */     MCH_ModelManager.load("a-10");
/* 214 */     MCH_RenderGLTD.model = MCH_ModelManager.load("gltd");
/* 215 */     MCH_ModelManager.load("chain");
/* 216 */     MCH_ModelManager.load("container");
/* 217 */     MCH_ModelManager.load("parachute1");
/* 218 */     MCH_ModelManager.load("parachute2");
/* 219 */     MCH_ModelManager.load("lweapons", "fim92");
/* 220 */     MCH_ModelManager.load("lweapons", "fgm148");
/*     */     
/* 222 */     for (String s : MCH_RenderUavStation.MODEL_NAME)
/*     */     {
/* 224 */       MCH_ModelManager.load(s);
/*     */     }
/*     */     
/* 227 */     MCH_ModelManager.load("wrench");
/* 228 */     MCH_ModelManager.load("rangefinder");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 234 */     ContentRegistries.heli().forEachValue(info -> registerModelsHeli(info, false));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 240 */     ContentRegistries.plane().forEachValue(info -> registerModelsPlane(info, false));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 247 */     ContentRegistries.tank().forEachValue(info -> registerModelsTank(info, false));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 253 */     ContentRegistries.vehicle().forEachValue(info -> registerModelsVehicle(info, false));
/*     */     
/* 255 */     registerModels_Bullet();
/*     */     
/* 257 */     MCH_DefaultBulletModels.Bullet = loadBulletModel("bullet");
/* 258 */     MCH_DefaultBulletModels.AAMissile = loadBulletModel("aamissile");
/* 259 */     MCH_DefaultBulletModels.ATMissile = loadBulletModel("asmissile");
/* 260 */     MCH_DefaultBulletModels.ASMissile = loadBulletModel("asmissile");
/* 261 */     MCH_DefaultBulletModels.Bomb = loadBulletModel("bomb");
/* 262 */     MCH_DefaultBulletModels.Rocket = loadBulletModel("rocket");
/* 263 */     MCH_DefaultBulletModels.Torpedo = loadBulletModel("torpedo");
/*     */ 
/*     */     
/* 266 */     for (MCH_ThrowableInfo wi : ContentRegistries.throwable().values())
/*     */     {
/* 268 */       wi.model = MCH_ModelManager.load("throwable", wi.name);
/*     */     }
/*     */     
/* 271 */     MCH_ModelManager.load("blocks", "drafting_table");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void registerModels_Bullet() {
/* 277 */     for (MCH_WeaponInfo wi : ContentRegistries.weapon().values()) {
/*     */ 
/*     */       
/* 280 */       _IModelCustom m = null;
/*     */       
/* 282 */       if (!wi.bulletModelName.isEmpty()) {
/*     */         
/* 284 */         m = MCH_ModelManager.load("bullets", wi.bulletModelName);
/*     */         
/* 286 */         if (m != null)
/*     */         {
/* 288 */           wi.bulletModel = new MCH_BulletModel(wi.bulletModelName, m);
/*     */         }
/*     */       } 
/*     */       
/* 292 */       if (!wi.bombletModelName.isEmpty()) {
/*     */         
/* 294 */         m = MCH_ModelManager.load("bullets", wi.bombletModelName);
/*     */         
/* 296 */         if (m != null)
/*     */         {
/* 298 */           wi.bombletModel = new MCH_BulletModel(wi.bombletModelName, m);
/*     */         }
/*     */       } 
/*     */       
/* 302 */       if (wi.cartridge != null && !wi.cartridge.name.isEmpty()) {
/*     */         
/* 304 */         wi.cartridge.model = MCH_ModelManager.load("bullets", wi.cartridge.name);
/*     */         
/* 306 */         if (wi.cartridge.model == null)
/*     */         {
/* 308 */           wi.cartridge = null;
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerModelsHeli(MCH_HeliInfo info, boolean reload) {
/* 318 */     MCH_ModelManager.setForceReloadMode(reload);
/*     */ 
/*     */     
/* 321 */     info.model = MCH_ModelManager.load("helicopters", info.name);
/*     */     
/* 323 */     for (MCH_HeliInfo.Rotor rotor : info.rotorList)
/*     */     {
/* 325 */       rotor.model = loadPartModel("helicopters", info.name, info.model, rotor.modelName);
/*     */     }
/*     */     
/* 328 */     registerCommonPart("helicopters", (MCH_AircraftInfo)info);
/*     */     
/* 330 */     MCH_ModelManager.setForceReloadMode(false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerModelsPlane(MCP_PlaneInfo info, boolean reload) {
/* 337 */     MCH_ModelManager.setForceReloadMode(reload);
/*     */ 
/*     */     
/* 340 */     info.model = MCH_ModelManager.load("planes", info.name);
/*     */     
/* 342 */     for (MCH_AircraftInfo.DrawnPart n : info.nozzles)
/*     */     {
/* 344 */       n.model = loadPartModel("planes", info.name, info.model, n.modelName);
/*     */     }
/*     */     
/* 347 */     for (MCP_PlaneInfo.Rotor r : info.rotorList) {
/*     */       
/* 349 */       r.model = loadPartModel("planes", info.name, info.model, r.modelName);
/* 350 */       for (MCP_PlaneInfo.Blade b : r.blades)
/*     */       {
/* 352 */         b.model = loadPartModel("planes", info.name, info.model, b.modelName);
/*     */       }
/*     */     } 
/*     */     
/* 356 */     for (MCP_PlaneInfo.Wing w : info.wingList) {
/*     */       
/* 358 */       w.model = loadPartModel("planes", info.name, info.model, w.modelName);
/* 359 */       if (w.pylonList != null)
/*     */       {
/* 361 */         for (MCP_PlaneInfo.Pylon p : w.pylonList)
/*     */         {
/* 363 */           p.model = loadPartModel("planes", info.name, info.model, p.modelName);
/*     */         }
/*     */       }
/*     */     } 
/*     */     
/* 368 */     registerCommonPart("planes", (MCH_AircraftInfo)info);
/*     */     
/* 370 */     MCH_ModelManager.setForceReloadMode(false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerModelsVehicle(MCH_VehicleInfo info, boolean reload) {
/* 377 */     MCH_ModelManager.setForceReloadMode(reload);
/*     */ 
/*     */     
/* 380 */     info.model = MCH_ModelManager.load("vehicles", info.name);
/*     */     
/* 382 */     for (MCH_VehicleInfo.VPart vp : info.partList) {
/*     */       
/* 384 */       vp.model = loadPartModel("vehicles", info.name, info.model, vp.modelName);
/* 385 */       if (vp.child != null)
/*     */       {
/* 387 */         registerVCPModels(info, vp);
/*     */       }
/*     */     } 
/* 390 */     registerCommonPart("vehicles", (MCH_AircraftInfo)info);
/*     */     
/* 392 */     MCH_ModelManager.setForceReloadMode(false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerModelsTank(MCH_TankInfo info, boolean reload) {
/* 399 */     MCH_ModelManager.setForceReloadMode(reload);
/*     */ 
/*     */     
/* 402 */     info.model = MCH_ModelManager.load("tanks", info.name);
/*     */     
/* 404 */     registerCommonPart("tanks", (MCH_AircraftInfo)info);
/*     */     
/* 406 */     MCH_ModelManager.setForceReloadMode(false);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private MCH_BulletModel loadBulletModel(String name) {
/* 412 */     _IModelCustom m = MCH_ModelManager.load("bullets", name);
/* 413 */     return (m != null) ? new MCH_BulletModel(name, m) : null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private _IModelCustom loadPartModel(String path, String name, _IModelCustom body, String part) {
/* 419 */     if (body instanceof W_ModelCustom)
/*     */     {
/* 421 */       if (((W_ModelCustom)body).containsPart("$" + part))
/*     */       {
/* 423 */         return null;
/*     */       }
/*     */     }
/* 426 */     return MCH_ModelManager.load(path, name + "_" + part);
/*     */   }
/*     */ 
/*     */   
/*     */   private void registerCommonPart(String path, MCH_AircraftInfo info) {
/* 431 */     for (MCH_AircraftInfo.Hatch h : info.hatchList)
/*     */     {
/* 433 */       h.model = loadPartModel(path, info.name, info.model, h.modelName);
/*     */     }
/*     */     
/* 436 */     for (MCH_AircraftInfo.Camera c : info.cameraList)
/*     */     {
/* 438 */       c.model = loadPartModel(path, info.name, info.model, c.modelName);
/*     */     }
/*     */     
/* 441 */     for (MCH_AircraftInfo.Throttle c : info.partThrottle)
/*     */     {
/* 443 */       c.model = loadPartModel(path, info.name, info.model, c.modelName);
/*     */     }
/*     */     
/* 446 */     for (MCH_AircraftInfo.RotPart c : info.partRotPart)
/*     */     {
/* 448 */       c.model = loadPartModel(path, info.name, info.model, c.modelName);
/*     */     }
/*     */     
/* 451 */     for (MCH_AircraftInfo.PartWeapon p : info.partWeapon) {
/*     */       
/* 453 */       p.model = loadPartModel(path, info.name, info.model, p.modelName);
/* 454 */       for (MCH_AircraftInfo.PartWeaponChild wc : p.child)
/*     */       {
/* 456 */         wc.model = loadPartModel(path, info.name, info.model, wc.modelName);
/*     */       }
/*     */     } 
/*     */     
/* 460 */     for (MCH_AircraftInfo.Canopy c : info.canopyList)
/*     */     {
/* 462 */       c.model = loadPartModel(path, info.name, info.model, c.modelName);
/*     */     }
/*     */     
/* 465 */     for (MCH_AircraftInfo.DrawnPart n : info.landingGear)
/*     */     {
/* 467 */       n.model = loadPartModel(path, info.name, info.model, n.modelName);
/*     */     }
/*     */     
/* 470 */     for (MCH_AircraftInfo.WeaponBay w : info.partWeaponBay)
/*     */     {
/* 472 */       w.model = loadPartModel(path, info.name, info.model, w.modelName);
/*     */     }
/*     */     
/* 475 */     for (MCH_AircraftInfo.CrawlerTrack c : info.partCrawlerTrack)
/*     */     {
/* 477 */       c.model = loadPartModel(path, info.name, info.model, c.modelName);
/*     */     }
/*     */     
/* 480 */     for (MCH_AircraftInfo.TrackRoller c : info.partTrackRoller)
/*     */     {
/* 482 */       c.model = loadPartModel(path, info.name, info.model, c.modelName);
/*     */     }
/*     */     
/* 485 */     for (MCH_AircraftInfo.PartWheel c : info.partWheel)
/*     */     {
/* 487 */       c.model = loadPartModel(path, info.name, info.model, c.modelName);
/*     */     }
/*     */     
/* 490 */     for (MCH_AircraftInfo.PartWheel c : info.partSteeringWheel)
/*     */     {
/* 492 */       c.model = loadPartModel(path, info.name, info.model, c.modelName);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void registerVCPModels(MCH_VehicleInfo info, MCH_VehicleInfo.VPart vp) {
/* 498 */     for (MCH_VehicleInfo.VPart vcp : vp.child) {
/*     */       
/* 500 */       vcp.model = loadPartModel("vehicles", info.name, info.model, vcp.modelName);
/*     */       
/* 502 */       if (vcp.child != null)
/*     */       {
/* 504 */         registerVCPModels(info, vcp);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerClientTick() {
/* 512 */     Minecraft mc = Minecraft.func_71410_x();
/* 513 */     MCH_ClientCommonTickHandler.instance = new MCH_ClientCommonTickHandler(mc, MCH_MOD.config);
/*     */     
/* 515 */     W_TickRegistry.registerTickHandler(MCH_ClientCommonTickHandler.instance, Side.CLIENT);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isRemote() {
/* 521 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String side() {
/* 527 */     return "Client";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public MCH_SoundUpdater CreateSoundUpdater(MCH_EntityAircraft aircraft) {
/* 533 */     if (aircraft == null || !aircraft.field_70170_p.field_72995_K) {
/* 534 */       return null;
/*     */     }
/* 536 */     return new MCH_SoundUpdater(Minecraft.func_71410_x(), aircraft, (Minecraft.func_71410_x()).field_71439_g);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerSounds() {
/* 542 */     super.registerSounds();
/*     */     
/* 544 */     W_McClient.addSound("alert.ogg");
/* 545 */     W_McClient.addSound("locked.ogg");
/* 546 */     W_McClient.addSound("gltd.ogg");
/* 547 */     W_McClient.addSound("zoom.ogg");
/* 548 */     W_McClient.addSound("ng.ogg");
/* 549 */     W_McClient.addSound("a-10_snd.ogg");
/* 550 */     W_McClient.addSound("gau-8_snd.ogg");
/* 551 */     W_McClient.addSound("hit.ogg");
/* 552 */     W_McClient.addSound("helidmg.ogg");
/* 553 */     W_McClient.addSound("heli.ogg");
/* 554 */     W_McClient.addSound("plane.ogg");
/* 555 */     W_McClient.addSound("plane_cc.ogg");
/* 556 */     W_McClient.addSound("plane_cv.ogg");
/* 557 */     W_McClient.addSound("chain.ogg");
/* 558 */     W_McClient.addSound("chain_ct.ogg");
/* 559 */     W_McClient.addSound("eject_seat.ogg");
/* 560 */     W_McClient.addSound("fim92_snd.ogg");
/* 561 */     W_McClient.addSound("fim92_reload.ogg");
/* 562 */     W_McClient.addSound("lockon.ogg");
/*     */ 
/*     */     
/* 565 */     for (MCH_WeaponInfo info : ContentRegistries.weapon().values())
/*     */     {
/* 567 */       W_McClient.addSound(info.soundFileName + ".ogg");
/*     */     }
/*     */ 
/*     */     
/* 571 */     for (MCH_AircraftInfo info : ContentRegistries.plane().values()) {
/*     */       
/* 573 */       if (!info.soundMove.isEmpty()) {
/* 574 */         W_McClient.addSound(info.soundMove + ".ogg");
/*     */       }
/*     */     } 
/*     */     
/* 578 */     for (MCH_AircraftInfo info : ContentRegistries.heli().values()) {
/*     */       
/* 580 */       if (!info.soundMove.isEmpty()) {
/* 581 */         W_McClient.addSound(info.soundMove + ".ogg");
/*     */       }
/*     */     } 
/*     */     
/* 585 */     for (MCH_AircraftInfo info : ContentRegistries.tank().values()) {
/*     */       
/* 587 */       if (!info.soundMove.isEmpty()) {
/* 588 */         W_McClient.addSound(info.soundMove + ".ogg");
/*     */       }
/*     */     } 
/*     */     
/* 592 */     for (MCH_AircraftInfo info : ContentRegistries.vehicle().values()) {
/*     */       
/* 594 */       if (!info.soundMove.isEmpty())
/*     */       {
/* 596 */         W_McClient.addSound(info.soundMove + ".ogg");
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void loadConfig(String fileName) {
/* 604 */     this.lastConfigFileName = fileName;
/*     */     
/* 606 */     this.config = new MCH_Config((Minecraft.func_71410_x()).field_71412_D.getPath(), "/" + fileName);
/* 607 */     this.config.load();
/* 608 */     this.config.write();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void reconfig() {
/* 614 */     MCH_Lib.DbgLog(false, "MCH_ClientProxy.reconfig()", new Object[0]);
/* 615 */     loadConfig(this.lastConfigFileName);
/* 616 */     MCH_ClientCommonTickHandler.instance.updatekeybind(this.config);
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
/*     */ 
/*     */   
/*     */   public void reloadHUD() {
/* 630 */     ContentRegistries.hud().reloadAll();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Entity getClientPlayer() {
/* 636 */     return (Entity)(Minecraft.func_71410_x()).field_71439_g;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void init() {
/* 642 */     MinecraftForge.EVENT_BUS.register(new MCH_ParticlesUtil());
/* 643 */     MinecraftForge.EVENT_BUS.register(new MCH_ClientEventHook());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCreativeDigDelay(int n) {
/* 649 */     W_Reflection.setCreativeDigSpeed(n);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFirstPerson() {
/* 655 */     return ((Minecraft.func_71410_x()).field_71474_y.field_74320_O == 0);
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
/*     */   
/*     */   public boolean isSinglePlayer() {
/* 668 */     return Minecraft.func_71410_x().func_71356_B();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readClientModList() {
/*     */     try {
/* 676 */       Minecraft mc = Minecraft.func_71410_x();
/* 677 */       MCH_MultiplayClient.readModList(mc.func_110432_I().func_148255_b(), mc.func_110432_I().func_111285_a());
/*     */     }
/* 679 */     catch (Exception e) {
/*     */       
/* 681 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void printChatMessage(ITextComponent chat, int showTime, int pos) {
/* 689 */     ((MCH_GuiTitle)MCH_ClientCommonTickHandler.instance.gui_Title).setupTitle(chat, showTime, pos);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void hitBullet() {
/* 695 */     MCH_ClientCommonTickHandler.instance.gui_Common.hitBullet();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void clientLocked() {
/* 701 */     MCH_ClientCommonTickHandler.isLocked = true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRenderEntityDistanceWeight(double renderDistWeight) {
/* 707 */     Entity.func_184227_b(renderDistWeight);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<AddonPack> loadAddonPacks(File addonDir) {
/* 713 */     return AddonManager.loadAddonsAndAddResources(addonDir);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canLoadContentDirName(String dir) {
/* 719 */     return ("hud".equals(dir) || super.canLoadContentDirName(dir));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateGeneratedLanguage() {
/* 725 */     W_LanguageRegistry.updateGeneratedLang();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerRecipeDescriptions() {
/* 731 */     RecipeDescriptionManager.registerDescriptionInfos(Minecraft.func_71410_x().func_110442_L());
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\MCH_ClientProxy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */