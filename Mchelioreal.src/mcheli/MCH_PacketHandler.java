/*     */ package mcheli;
/*     */ 
/*     */ import com.google.common.io.ByteArrayDataInput;
/*     */ import mcheli.aircraft.MCH_AircraftPacketHandler;
/*     */ import mcheli.block.MCH_DraftingTablePacketHandler;
/*     */ import mcheli.command.MCH_CommandPacketHandler;
/*     */ import mcheli.gltd.MCH_GLTDPacketHandler;
/*     */ import mcheli.helicopter.MCH_HeliPacketHandler;
/*     */ import mcheli.lweapon.MCH_LightWeaponPacketHandler;
/*     */ import mcheli.multiplay.MCH_MultiplayPacketHandler;
/*     */ import mcheli.plane.MCP_PlanePacketHandler;
/*     */ import mcheli.tank.MCH_TankPacketHandler;
/*     */ import mcheli.tool.MCH_ToolPacketHandler;
/*     */ import mcheli.uav.MCH_UavPacketHandler;
/*     */ import mcheli.vehicle.MCH_VehiclePacketHandler;
/*     */ import mcheli.wrapper.W_PacketHandler;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.util.IThreadListener;
/*     */ import net.minecraftforge.fml.common.FMLCommonHandler;
/*     */ import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
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
/*     */ public class MCH_PacketHandler
/*     */   extends W_PacketHandler
/*     */ {
/*     */   public void onPacket(ByteArrayDataInput data, EntityPlayer entityPlayer, MessageContext ctx) {
/*  37 */     int msgid = getMessageId(data);
/*  38 */     IThreadListener handler = FMLCommonHandler.instance().getWorldThread(ctx.netHandler);
/*     */     
/*  40 */     switch (msgid) {
/*     */ 
/*     */       
/*     */       default:
/*  44 */         MCH_Lib.DbgLog(entityPlayer.field_70170_p, "MCH_PacketHandler.onPacket invalid MSGID=0x%X(%d)", new Object[] {
/*     */               
/*  46 */               Integer.valueOf(msgid), Integer.valueOf(msgid)
/*     */             });
/*     */         return;
/*     */ 
/*     */       
/*     */       case 268437520:
/*  52 */         MCH_CommonPacketHandler.onPacketEffectExplosion(entityPlayer, data, handler);
/*     */         return;
/*     */       
/*     */       case 536872992:
/*  56 */         MCH_CommonPacketHandler.onPacketIndOpenScreen(entityPlayer, data, handler);
/*     */         return;
/*     */       
/*     */       case 268437568:
/*  60 */         MCH_CommonPacketHandler.onPacketNotifyServerSettings(entityPlayer, data, handler);
/*     */         return;
/*     */       
/*     */       case 536873984:
/*  64 */         MCH_CommonPacketHandler.onPacketNotifyLock(entityPlayer, data, handler);
/*     */         return;
/*     */ 
/*     */       
/*     */       case 536873088:
/*  69 */         MCH_MultiplayPacketHandler.onPacket_Command(entityPlayer, data, handler);
/*     */         return;
/*     */       
/*     */       case 268437761:
/*  73 */         MCH_MultiplayPacketHandler.onPacket_NotifySpotedEntity(entityPlayer, data, handler);
/*     */         return;
/*     */       
/*     */       case 268437762:
/*  77 */         MCH_MultiplayPacketHandler.onPacket_NotifyMarkPoint(entityPlayer, data, handler);
/*     */         return;
/*     */       
/*     */       case 536873472:
/*  81 */         MCH_MultiplayPacketHandler.onPacket_LargeData(entityPlayer, data, handler);
/*     */         return;
/*     */       
/*     */       case 536873473:
/*  85 */         MCH_MultiplayPacketHandler.onPacket_ModList(entityPlayer, data, handler);
/*     */         return;
/*     */       
/*     */       case 268438032:
/*  89 */         MCH_MultiplayPacketHandler.onPacket_IndClient(entityPlayer, data, handler);
/*     */         return;
/*     */ 
/*     */       
/*     */       case 268438272:
/*  94 */         MCH_CommandPacketHandler.onPacketTitle(entityPlayer, data, handler);
/*     */         return;
/*     */       
/*     */       case 536873729:
/*  98 */         MCH_CommandPacketHandler.onPacketSave(entityPlayer, data, handler);
/*     */         return;
/*     */ 
/*     */       
/*     */       case 536873216:
/* 103 */         MCH_ToolPacketHandler.onPacket_IndSpotEntity(entityPlayer, data, handler);
/*     */         return;
/*     */ 
/*     */       
/*     */       case 536879120:
/* 108 */         MCH_HeliPacketHandler.onPacket_PlayerControl(entityPlayer, data, handler);
/*     */         return;
/*     */ 
/*     */       
/*     */       case 536875104:
/* 113 */         MCH_AircraftPacketHandler.onPacketStatusRequest(entityPlayer, data, handler);
/*     */         return;
/*     */       
/*     */       case 268439649:
/* 117 */         MCH_AircraftPacketHandler.onPacketStatusResponse(entityPlayer, data, handler);
/*     */         return;
/*     */       
/*     */       case 536875024:
/* 121 */         MCH_AircraftPacketHandler.onPacketSeatListRequest(entityPlayer, data, handler);
/*     */         return;
/*     */       
/*     */       case 268439569:
/* 125 */         MCH_AircraftPacketHandler.onPacketSeatListResponse(entityPlayer, data, handler);
/*     */         return;
/*     */       
/*     */       case 536875040:
/* 129 */         MCH_AircraftPacketHandler.onPacket_PlayerControl(entityPlayer, data, handler);
/*     */         return;
/*     */       
/*     */       case 268439600:
/* 133 */         MCH_AircraftPacketHandler.onPacketNotifyTVMissileEntity(entityPlayer, data, handler);
/*     */         return;
/*     */       
/*     */       case 536875072:
/* 137 */         MCH_AircraftPacketHandler.onPacket_ClientSetting(entityPlayer, data, handler);
/*     */         return;
/*     */       
/*     */       case 268439632:
/* 141 */         MCH_AircraftPacketHandler.onPacketOnMountEntity(entityPlayer, data, handler);
/*     */         return;
/*     */       
/*     */       case 268439601:
/* 145 */         MCH_AircraftPacketHandler.onPacketNotifyWeaponID(entityPlayer, data, handler);
/*     */         return;
/*     */       
/*     */       case 268439602:
/* 149 */         MCH_AircraftPacketHandler.onPacketNotifyHitBullet(entityPlayer, data, handler);
/*     */         return;
/*     */       
/*     */       case 536875059:
/* 153 */         MCH_AircraftPacketHandler.onPacketIndReload(entityPlayer, data, handler);
/*     */         return;
/*     */       
/*     */       case 536875062:
/* 157 */         MCH_AircraftPacketHandler.onPacketIndRotation(entityPlayer, data, handler);
/*     */         return;
/*     */       
/*     */       case 536875063:
/* 161 */         MCH_AircraftPacketHandler.onPacketNotifyInfoReloaded(entityPlayer, data, handler);
/*     */         return;
/*     */       
/*     */       case 268439604:
/* 165 */         MCH_AircraftPacketHandler.onPacketNotifyAmmoNum(entityPlayer, data, handler);
/*     */         return;
/*     */       
/*     */       case 536875061:
/* 169 */         MCH_AircraftPacketHandler.onPacketIndNotifyAmmoNum(entityPlayer, data, handler);
/*     */         return;
/*     */ 
/*     */       
/*     */       case 536887312:
/* 174 */         MCH_GLTDPacketHandler.onPacket_GLTDPlayerControl(entityPlayer, data, handler);
/*     */         return;
/*     */ 
/*     */       
/*     */       case 536903696:
/* 179 */         MCP_PlanePacketHandler.onPacket_PlayerControl(entityPlayer, data, handler);
/*     */         return;
/*     */ 
/*     */       
/*     */       case 537919504:
/* 184 */         MCH_TankPacketHandler.onPacket_PlayerControl(entityPlayer, data, handler);
/*     */         return;
/*     */ 
/*     */       
/*     */       case 536936464:
/* 189 */         MCH_LightWeaponPacketHandler.onPacket_PlayerControl(entityPlayer, data, handler);
/*     */         return;
/*     */ 
/*     */       
/*     */       case 537002000:
/* 194 */         MCH_VehiclePacketHandler.onPacket_PlayerControl(entityPlayer, data, handler);
/*     */         return;
/*     */ 
/*     */       
/*     */       case 537133072:
/* 199 */         MCH_UavPacketHandler.onPacketUavStatus(entityPlayer, data, handler);
/*     */         return;
/*     */       case 537395216:
/*     */         break;
/*     */     } 
/* 204 */     MCH_DraftingTablePacketHandler.onPacketCreate(entityPlayer, data, handler);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getMessageId(ByteArrayDataInput data) {
/*     */     try {
/* 213 */       return data.readInt();
/*     */     }
/* 215 */     catch (Exception e) {
/*     */       
/* 217 */       e.printStackTrace();
/*     */ 
/*     */       
/* 220 */       return 0;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\MCH_PacketHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */