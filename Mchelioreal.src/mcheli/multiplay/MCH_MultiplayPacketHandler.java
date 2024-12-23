/*     */ package mcheli.multiplay;
/*     */ 
/*     */ import com.google.common.io.ByteArrayDataInput;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.text.DateFormat;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import mcheli.MCH_Lib;
/*     */ import mcheli.MCH_PacketNotifyServerSettings;
/*     */ import mcheli.__helper.MCH_Utils;
/*     */ import mcheli.__helper.network.HandleSide;
/*     */ import mcheli.aircraft.MCH_EntityAircraft;
/*     */ import net.minecraft.command.ICommandManager;
/*     */ import net.minecraft.command.ICommandSender;
/*     */ import net.minecraft.command.server.CommandScoreboard;
/*     */ import net.minecraft.command.server.CommandSummon;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.IThreadListener;
/*     */ import net.minecraft.util.text.ITextComponent;
/*     */ import net.minecraft.util.text.TextComponentString;
/*     */ import net.minecraft.util.text.TextFormatting;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MCH_MultiplayPacketHandler
/*     */ {
/*  39 */   private static final Logger logger = LogManager.getLogger();
/*     */   
/*  41 */   private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");
/*  42 */   private static byte[] imageData = null;
/*  43 */   private static String lastPlayerName = "";
/*  44 */   private static double lastDataPercent = 0.0D;
/*     */   
/*  46 */   public static EntityPlayer modListRequestPlayer = null;
/*  47 */   private static int playerInfoId = 0;
/*     */ 
/*     */ 
/*     */   
/*     */   @HandleSide({Side.SERVER})
/*     */   public static void onPacket_Command(EntityPlayer player, ByteArrayDataInput data, IThreadListener scheduler) {
/*  53 */     if (player.field_70170_p.field_72995_K) {
/*     */       return;
/*     */     }
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
/* 104 */     MCH_PacketIndMultiplayCommand pc = new MCH_PacketIndMultiplayCommand();
/* 105 */     pc.readData(data);
/*     */     
/* 107 */     scheduler.func_152344_a(() -> {
/*     */           ICommandManager icommandmanager;
/*     */           MinecraftServer minecraftServer = MCH_Utils.getServer();
/*     */           MCH_Lib.DbgLog(false, "MCH_MultiplayPacketHandler.onPacket_Command cmd:%d:%s", new Object[] { Integer.valueOf(pc.CmdID), pc.CmdStr });
/*     */           switch (pc.CmdID) {
/*     */             case 256:
/*     */               MCH_Multiplay.shuffleTeam(player);
/*     */               return;
/*     */             case 512:
/*     */               MCH_Multiplay.jumpSpawnPoint(player);
/*     */               return;
/*     */             case 768:
/*     */               icommandmanager = minecraftServer.func_71187_D();
/*     */               icommandmanager.func_71556_a((ICommandSender)player, pc.CmdStr);
/*     */               return;
/*     */             case 1024:
/*     */               if ((new CommandScoreboard()).func_184882_a(minecraftServer, (ICommandSender)player)) {
/*     */                 minecraftServer.func_71188_g(!minecraftServer.func_71219_W());
/*     */                 MCH_PacketNotifyServerSettings.send(null);
/*     */               } 
/*     */               return;
/*     */             case 1280:
/*     */               destoryAllAircraft(player);
/*     */               return;
/*     */           } 
/*     */           MCH_Lib.DbgLog(false, "MCH_MultiplayPacketHandler.onPacket_Command unknown cmd:%d:%s", new Object[] { Integer.valueOf(pc.CmdID), pc.CmdStr });
/*     */         });
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
/*     */   private static void destoryAllAircraft(EntityPlayer player) {
/* 149 */     CommandSummon cmd = new CommandSummon();
/*     */ 
/*     */     
/* 152 */     if (cmd.func_184882_a(MCH_Utils.getServer(), (ICommandSender)player))
/*     */     {
/*     */       
/* 155 */       for (Entity e : player.field_70170_p.field_72996_f) {
/*     */         
/* 157 */         if (e instanceof MCH_EntityAircraft)
/*     */         {
/* 159 */           ((MCH_EntityAircraft)e).func_70106_y();
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @HandleSide({Side.CLIENT})
/*     */   public static void onPacket_NotifySpotedEntity(EntityPlayer player, ByteArrayDataInput data, IThreadListener scheduler) {
/* 170 */     if (!player.field_70170_p.field_72995_K) {
/*     */       return;
/*     */     }
/*     */     
/* 174 */     MCH_PacketNotifySpotedEntity pc = new MCH_PacketNotifySpotedEntity();
/* 175 */     pc.readData(data);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 185 */     scheduler.func_152344_a(() -> {
/*     */           if (pc.count > 0) {
/*     */             for (int i = 0; i < pc.num; i++) {
/*     */               MCH_GuiTargetMarker.addSpotedEntity(pc.entityId[i], pc.count);
/*     */             }
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @HandleSide({Side.CLIENT})
/*     */   public static void onPacket_NotifyMarkPoint(EntityPlayer player, ByteArrayDataInput data, IThreadListener scheduler) {
/* 201 */     if (!player.field_70170_p.field_72995_K) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 206 */     MCH_PacketNotifyMarkPoint pc = new MCH_PacketNotifyMarkPoint();
/* 207 */     pc.readData(data);
/*     */ 
/*     */     
/* 210 */     scheduler.func_152344_a(() -> MCH_GuiTargetMarker.markPoint(pc.px, pc.py, pc.pz));
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
/*     */   @HandleSide({Side.SERVER})
/*     */   public static void onPacket_LargeData(EntityPlayer player, ByteArrayDataInput data, IThreadListener scheduler) {
/* 225 */     if (player.field_70170_p.field_72995_K) {
/*     */       return;
/*     */     }
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
/* 330 */     MCH_PacketLargeData pc = new MCH_PacketLargeData();
/* 331 */     pc.readData(data);
/*     */     
/* 333 */     scheduler.func_152344_a(() -> {
/*     */           try {
/*     */             if (pc.imageDataIndex < 0 || pc.imageDataTotalSize <= 0) {
/*     */               return;
/*     */             }
/*     */ 
/*     */             
/*     */             if (pc.imageDataIndex == 0) {
/*     */               if (imageData != null && !lastPlayerName.isEmpty()) {
/*     */                 LogError("[mcheli]Err1:Saving the %s screen shot to server FAILED!!!", new Object[] { lastPlayerName });
/*     */               }
/*     */ 
/*     */               
/*     */               imageData = new byte[pc.imageDataTotalSize];
/*     */ 
/*     */               
/*     */               lastPlayerName = player.func_145748_c_().func_150254_d();
/*     */ 
/*     */               
/*     */               lastDataPercent = 0.0D;
/*     */             } 
/*     */ 
/*     */             
/*     */             double dataPercent = ((pc.imageDataIndex + pc.imageDataSize) / pc.imageDataTotalSize) * 100.0D;
/*     */ 
/*     */             
/*     */             if (dataPercent - lastDataPercent >= 10.0D || lastDataPercent == 0.0D) {
/*     */               LogInfo("[mcheli]Saving the %s screen shot to server. %.0f%% : %dbyte / %dbyte", new Object[] { player.func_145748_c_(), Double.valueOf(dataPercent), Integer.valueOf(pc.imageDataIndex), Integer.valueOf(pc.imageDataTotalSize) });
/*     */ 
/*     */               
/*     */               lastDataPercent = dataPercent;
/*     */             } 
/*     */ 
/*     */             
/*     */             if (imageData == null) {
/*     */               if (imageData != null && lastPlayerName.isEmpty()) {
/*     */                 LogError("[mcheli]Err2:Saving the %s screen shot to server FAILED!!!", new Object[] { player.func_145748_c_() });
/*     */               }
/*     */ 
/*     */               
/*     */               imageData = null;
/*     */ 
/*     */               
/*     */               lastPlayerName = "";
/*     */ 
/*     */               
/*     */               lastDataPercent = 0.0D;
/*     */               
/*     */               return;
/*     */             } 
/*     */             
/*     */             for (int i = 0; i < pc.imageDataSize; i++) {
/*     */               imageData[pc.imageDataIndex + i] = pc.buf[i];
/*     */             }
/*     */             
/*     */             if (pc.imageDataIndex + pc.imageDataSize >= pc.imageDataTotalSize) {
/*     */               DataOutputStream dos = null;
/*     */               
/*     */               String dt = dateFormat.format(new Date()).toString();
/*     */               
/*     */               File file = new File("screenshots_op");
/*     */               
/*     */               file.mkdir();
/*     */               
/*     */               file = new File(file, player.func_145748_c_() + "_" + dt + ".png");
/*     */               
/*     */               String s = file.getAbsolutePath();
/*     */               
/*     */               LogInfo("[mcheli]Save Screenshot has been completed: %s", new Object[] { s });
/*     */               
/*     */               FileOutputStream fos = new FileOutputStream(s);
/*     */               
/*     */               dos = new DataOutputStream(fos);
/*     */               
/*     */               dos.write(imageData);
/*     */               
/*     */               dos.flush();
/*     */               
/*     */               dos.close();
/*     */               
/*     */               imageData = null;
/*     */               
/*     */               lastPlayerName = "";
/*     */               
/*     */               lastDataPercent = 0.0D;
/*     */             } 
/* 419 */           } catch (Exception e) {
/*     */             e.printStackTrace();
/*     */           } 
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void LogInfo(String format, Object... args) {
/* 428 */     logger.info(String.format(format, args));
/*     */   }
/*     */ 
/*     */   
/*     */   public static void LogError(String format, Object... args) {
/* 433 */     logger.error(String.format(format, args));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @HandleSide({Side.CLIENT})
/*     */   public static void onPacket_IndClient(EntityPlayer player, ByteArrayDataInput data, IThreadListener scheduler) {
/* 440 */     if (!player.field_70170_p.field_72995_K) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 445 */     MCH_PacketIndClient pc = new MCH_PacketIndClient();
/* 446 */     pc.readData(data);
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
/* 458 */     scheduler.func_152344_a(() -> {
/*     */           if (pc.CmdID == 1) {
/*     */             MCH_MultiplayClient.startSendImageData();
/*     */           } else if (pc.CmdID == 2) {
/*     */             MCH_MultiplayClient.sendModsInfo(player.func_145748_c_().func_150254_d(), player.func_145748_c_().func_150260_c(), Integer.parseInt(pc.CmdStr));
/*     */           } 
/*     */         });
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
/*     */   public static int getPlayerInfoId(EntityPlayer player) {
/* 477 */     modListRequestPlayer = player;
/*     */     
/* 479 */     playerInfoId++;
/*     */     
/* 481 */     if (playerInfoId > 1000000)
/*     */     {
/* 483 */       playerInfoId = 1;
/*     */     }
/*     */     
/* 486 */     return playerInfoId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @HandleSide({Side.CLIENT, Side.SERVER})
/*     */   public static void onPacket_ModList(EntityPlayer player, ByteArrayDataInput data, IThreadListener scheduler) {
/* 496 */     MCH_PacketModList pc = new MCH_PacketModList();
/* 497 */     pc.readData(data);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 504 */     if (player.field_70170_p.field_72995_K) {
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
/* 518 */       scheduler.func_152344_a(() -> {
/*     */             MCH_Lib.DbgLog(player.field_70170_p, "MCH_MultiplayPacketHandler.onPacket_ModList : ID=%d, Num=%d", new Object[] { Integer.valueOf(pc.id), Integer.valueOf(pc.num) });
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*     */             if (pc.firstData) {
/*     */               MCH_Lib.Log(TextFormatting.RED + "###### " + player.func_145748_c_() + " ######", new Object[0]);
/*     */             }
/*     */ 
/*     */ 
/*     */             
/*     */             for (String s : pc.list) {
/*     */               MCH_Lib.Log(s, new Object[0]);
/*     */ 
/*     */ 
/*     */               
/*     */               player.func_145747_a((ITextComponent)new TextComponentString(s));
/*     */             } 
/*     */           });
/* 538 */     } else if (pc.id == playerInfoId) {
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
/* 555 */       scheduler.func_152344_a(() -> {
/*     */             if (modListRequestPlayer != null) {
/*     */               MCH_PacketModList.send(modListRequestPlayer, pc);
/*     */             } else {
/*     */               if (pc.firstData)
/*     */                 LogInfo("###### " + player.func_145748_c_() + " ######", new Object[0]); 
/*     */               for (String s : pc.list)
/*     */                 LogInfo(s, new Object[0]); 
/*     */             } 
/*     */           });
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\multiplay\MCH_MultiplayPacketHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */