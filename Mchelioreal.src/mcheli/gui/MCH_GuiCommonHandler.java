/*     */ package mcheli.gui;
/*     */ 
/*     */ import mcheli.MCH_Config;
/*     */ import mcheli.MCH_Lib;
/*     */ import mcheli.__helper.MCH_Utils;
/*     */ import mcheli.__helper.network.PooledGuiParameter;
/*     */ import mcheli.aircraft.MCH_AircraftGui;
/*     */ import mcheli.aircraft.MCH_AircraftGuiContainer;
/*     */ import mcheli.aircraft.MCH_EntityAircraft;
/*     */ import mcheli.block.MCH_DraftingTableGui;
/*     */ import mcheli.block.MCH_DraftingTableGuiContainer;
/*     */ import mcheli.multiplay.MCH_ContainerScoreboard;
/*     */ import mcheli.multiplay.MCH_GuiScoreboard;
/*     */ import mcheli.uav.MCH_ContainerUavStation;
/*     */ import mcheli.uav.MCH_EntityUavStation;
/*     */ import mcheli.uav.MCH_GuiUavStation;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.common.network.IGuiHandler;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MCH_GuiCommonHandler
/*     */   implements IGuiHandler
/*     */ {
/*     */   public static final int GUIID_UAV_STATION = 0;
/*     */   public static final int GUIID_AIRCRAFT = 1;
/*     */   public static final int GUIID_CONFG = 2;
/*     */   public static final int GUIID_INVENTORY = 3;
/*     */   public static final int GUIID_DRAFTING = 4;
/*     */   public static final int GUIID_MULTI_MNG = 5;
/*     */   
/*     */   public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
/*     */     Entity uavStation;
/*     */     MCH_EntityAircraft ac;
/*  40 */     MCH_Lib.DbgLog(world, "MCH_GuiCommonHandler.getServerGuiElement ID=%d (%d, %d, %d)", new Object[] {
/*     */           
/*  42 */           Integer.valueOf(id), Integer.valueOf(x), Integer.valueOf(y), Integer.valueOf(z)
/*     */         });
/*     */     
/*  45 */     switch (id) {
/*     */       
/*     */       case 0:
/*  48 */         uavStation = PooledGuiParameter.getEntity(player);
/*  49 */         PooledGuiParameter.resetEntity(player);
/*     */ 
/*     */         
/*  52 */         if (uavStation instanceof MCH_EntityUavStation)
/*     */         {
/*     */           
/*  55 */           return new MCH_ContainerUavStation(player.field_71071_by, (MCH_EntityUavStation)uavStation);
/*     */         }
/*     */         break;
/*     */ 
/*     */       
/*     */       case 1:
/*  61 */         ac = null;
/*  62 */         if (player.func_184187_bx() instanceof MCH_EntityAircraft) {
/*     */           
/*  64 */           ac = (MCH_EntityAircraft)player.func_184187_bx();
/*     */         }
/*  66 */         else if (player.func_184187_bx() instanceof MCH_EntityUavStation) {
/*     */           
/*  68 */           ac = ((MCH_EntityUavStation)player.func_184187_bx()).getControlAircract();
/*     */         } 
/*     */         
/*  71 */         if (ac != null)
/*     */         {
/*  73 */           return new MCH_AircraftGuiContainer(player, ac);
/*     */         }
/*     */         break;
/*     */ 
/*     */       
/*     */       case 2:
/*  79 */         return new MCH_ConfigGuiContainer(player);
/*     */       
/*     */       case 4:
/*  82 */         return new MCH_DraftingTableGuiContainer(player, x, y, z);
/*     */ 
/*     */       
/*     */       case 5:
/*  86 */         if (!MCH_Utils.getServer().func_71264_H() || MCH_Config.DebugLog)
/*     */         {
/*  88 */           return new MCH_ContainerScoreboard(player);
/*     */         }
/*     */         break;
/*     */     } 
/*  92 */     return null;
/*     */   }
/*     */   
/*     */   public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
/*     */     Entity uavStation;
/*     */     MCH_EntityAircraft ac;
/*  98 */     MCH_Lib.DbgLog(world, "MCH_GuiCommonHandler.getClientGuiElement ID=%d (%d, %d, %d)", new Object[] {
/*     */           
/* 100 */           Integer.valueOf(id), Integer.valueOf(x), Integer.valueOf(y), Integer.valueOf(z)
/*     */         });
/*     */     
/* 103 */     switch (id) {
/*     */       
/*     */       case 0:
/* 106 */         uavStation = PooledGuiParameter.getEntity(player);
/* 107 */         PooledGuiParameter.resetEntity(player);
/*     */ 
/*     */         
/* 110 */         if (uavStation instanceof MCH_EntityUavStation)
/*     */         {
/*     */           
/* 113 */           return new MCH_GuiUavStation(player.field_71071_by, (MCH_EntityUavStation)uavStation);
/*     */         }
/*     */         break;
/*     */ 
/*     */       
/*     */       case 1:
/* 119 */         ac = null;
/* 120 */         if (player.func_184187_bx() instanceof MCH_EntityAircraft) {
/*     */           
/* 122 */           ac = (MCH_EntityAircraft)player.func_184187_bx();
/*     */         }
/* 124 */         else if (player.func_184187_bx() instanceof MCH_EntityUavStation) {
/*     */           
/* 126 */           ac = ((MCH_EntityUavStation)player.func_184187_bx()).getControlAircract();
/*     */         } 
/*     */         
/* 129 */         if (ac != null)
/*     */         {
/* 131 */           return new MCH_AircraftGui(player, ac);
/*     */         }
/*     */         break;
/*     */ 
/*     */       
/*     */       case 2:
/* 137 */         return new MCH_ConfigGui(player);
/*     */       
/*     */       case 4:
/* 140 */         return new MCH_DraftingTableGui(player, x, y, z);
/*     */       
/*     */       case 5:
/* 143 */         return new MCH_GuiScoreboard(player);
/*     */     } 
/*     */     
/* 146 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\gui\MCH_GuiCommonHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */