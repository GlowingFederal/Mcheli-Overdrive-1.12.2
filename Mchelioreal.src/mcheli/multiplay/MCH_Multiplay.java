/*     */ package mcheli.multiplay;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import mcheli.MCH_Lib;
/*     */ import mcheli.__helper.MCH_Utils;
/*     */ import mcheli.__helper.entity.IEntitySinglePassenger;
/*     */ import mcheli.aircraft.MCH_EntityAircraft;
/*     */ import net.minecraft.command.CommandException;
/*     */ import net.minecraft.command.ICommandSender;
/*     */ import net.minecraft.command.server.CommandScoreboard;
/*     */ import net.minecraft.command.server.CommandTeleport;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.scoreboard.ScorePlayerTeam;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.server.management.PlayerList;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.World;
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
/*     */ public class MCH_Multiplay
/*     */ {
/*     */   public static boolean canSpotEntityWithFilter(int filter, Entity entity) {
/*  44 */     if (entity instanceof mcheli.plane.MCP_EntityPlane)
/*     */     {
/*  46 */       return ((filter & 0x20) != 0);
/*     */     }
/*  48 */     if (entity instanceof mcheli.helicopter.MCH_EntityHeli)
/*     */     {
/*  50 */       return ((filter & 0x10) != 0);
/*     */     }
/*  52 */     if (entity instanceof mcheli.vehicle.MCH_EntityVehicle || entity instanceof mcheli.tank.MCH_EntityTank)
/*     */     {
/*  54 */       return ((filter & 0x8) != 0);
/*     */     }
/*  56 */     if (entity instanceof EntityPlayer)
/*     */     {
/*  58 */       return ((filter & 0x4) != 0);
/*     */     }
/*  60 */     if (entity instanceof EntityLivingBase) {
/*     */       
/*  62 */       if (isMonster(entity))
/*     */       {
/*  64 */         return ((filter & 0x2) != 0);
/*     */       }
/*     */       
/*  67 */       return ((filter & 0x1) != 0);
/*     */     } 
/*     */     
/*  70 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isMonster(Entity entity) {
/*  75 */     return (entity.getClass().toString().toLowerCase().indexOf("monster") >= 0);
/*     */   }
/*     */   
/*  78 */   public static final MCH_TargetType[][] ENTITY_SPOT_TABLE = new MCH_TargetType[][] { { MCH_TargetType.NONE, MCH_TargetType.NONE }, { MCH_TargetType.OTHER_MOB, MCH_TargetType.OTHER_MOB }, { MCH_TargetType.MONSTER, MCH_TargetType.MONSTER }, { MCH_TargetType.NONE, MCH_TargetType.NO_TEAM_PLAYER }, { MCH_TargetType.NONE, MCH_TargetType.SAME_TEAM_PLAYER }, { MCH_TargetType.NONE, MCH_TargetType.OTHER_TEAM_PLAYER }, { MCH_TargetType.NONE, MCH_TargetType.NONE }, { MCH_TargetType.NONE, MCH_TargetType.NO_TEAM_PLAYER }, { MCH_TargetType.NONE, MCH_TargetType.SAME_TEAM_PLAYER }, { MCH_TargetType.NONE, MCH_TargetType.OTHER_TEAM_PLAYER } };
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
/*     */   public static MCH_TargetType canSpotEntity(Entity user, double posX, double posY, double posZ, Entity target, boolean checkSee) {
/* 115 */     if (!(user instanceof EntityLivingBase))
/*     */     {
/* 117 */       return MCH_TargetType.NONE;
/*     */     }
/*     */     
/* 120 */     EntityLivingBase spotter = (EntityLivingBase)user;
/* 121 */     int col = (spotter.func_96124_cp() == null) ? 0 : 1;
/* 122 */     int row = 0;
/*     */     
/* 124 */     if (target instanceof EntityLivingBase)
/*     */     {
/* 126 */       if (!isMonster(target)) {
/*     */         
/* 128 */         row = 1;
/*     */       }
/*     */       else {
/*     */         
/* 132 */         row = 2;
/*     */       } 
/*     */     }
/*     */     
/* 136 */     if (spotter.func_96124_cp() != null) {
/*     */       
/* 138 */       if (target instanceof EntityPlayer) {
/*     */         
/* 140 */         EntityPlayer player = (EntityPlayer)target;
/*     */         
/* 142 */         if (player.func_96124_cp() == null)
/*     */         {
/* 144 */           row = 3;
/*     */         }
/* 146 */         else if (spotter.func_184191_r((Entity)player))
/*     */         {
/* 148 */           row = 4;
/*     */         }
/*     */         else
/*     */         {
/* 152 */           row = 5;
/*     */         }
/*     */       
/* 155 */       } else if (target instanceof MCH_EntityAircraft) {
/*     */         
/* 157 */         MCH_EntityAircraft ac = (MCH_EntityAircraft)target;
/* 158 */         EntityPlayer rideEntity = ac.getFirstMountPlayer();
/*     */         
/* 160 */         if (rideEntity == null)
/*     */         {
/* 162 */           row = 6;
/*     */         }
/* 164 */         else if (rideEntity.func_96124_cp() == null)
/*     */         {
/* 166 */           row = 7;
/*     */         }
/* 168 */         else if (spotter.func_184191_r((Entity)rideEntity))
/*     */         {
/* 170 */           row = 8;
/*     */         }
/*     */         else
/*     */         {
/* 174 */           row = 9;
/*     */         }
/*     */       
/*     */       }
/*     */     
/*     */     }
/* 180 */     else if (target instanceof EntityPlayer || target instanceof MCH_EntityAircraft) {
/*     */       
/* 182 */       row = 0;
/*     */     } 
/*     */     
/* 185 */     MCH_TargetType ret = ENTITY_SPOT_TABLE[row][col];
/* 186 */     if (checkSee && ret != MCH_TargetType.NONE) {
/*     */       
/* 188 */       Vec3d vs = new Vec3d(posX, posY, posZ);
/* 189 */       Vec3d ve = new Vec3d(target.field_70165_t, target.field_70163_u + target.func_70047_e(), target.field_70161_v);
/*     */       
/* 191 */       RayTraceResult mop = target.field_70170_p.func_72933_a(vs, ve);
/*     */ 
/*     */       
/* 194 */       if (mop != null && mop.field_72313_a == RayTraceResult.Type.BLOCK)
/*     */       {
/* 196 */         ret = MCH_TargetType.NONE;
/*     */       }
/*     */     } 
/*     */     
/* 200 */     return ret;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean canAttackEntity(DamageSource ds, Entity target) {
/* 205 */     return canAttackEntity(ds.func_76346_g(), target);
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean canAttackEntity(Entity attacker, Entity target) {
/* 210 */     if (attacker != null && target != null) {
/*     */       
/* 212 */       EntityPlayer attackPlayer = null;
/* 213 */       EntityPlayer targetPlayer = null;
/*     */       
/* 215 */       if (attacker instanceof EntityPlayer)
/*     */       {
/* 217 */         attackPlayer = (EntityPlayer)attacker;
/*     */       }
/*     */       
/* 220 */       if (target instanceof EntityPlayer) {
/*     */         
/* 222 */         targetPlayer = (EntityPlayer)target;
/*     */       
/*     */       }
/* 225 */       else if (target instanceof IEntitySinglePassenger && ((IEntitySinglePassenger)target)
/* 226 */         .getRiddenByEntity() instanceof EntityPlayer) {
/*     */ 
/*     */         
/* 229 */         targetPlayer = (EntityPlayer)((IEntitySinglePassenger)target).getRiddenByEntity();
/*     */       } 
/*     */       
/* 232 */       if (target instanceof MCH_EntityAircraft) {
/*     */         
/* 234 */         MCH_EntityAircraft ac = (MCH_EntityAircraft)target;
/*     */         
/* 236 */         if (ac.getRiddenByEntity() instanceof EntityPlayer)
/*     */         {
/* 238 */           targetPlayer = (EntityPlayer)ac.getRiddenByEntity();
/*     */         }
/*     */       } 
/*     */       
/* 242 */       if (attackPlayer != null && targetPlayer != null)
/*     */       {
/* 244 */         if (!attackPlayer.func_96122_a(targetPlayer))
/*     */         {
/* 246 */           return false;
/*     */         }
/*     */       }
/*     */     } 
/*     */     
/* 251 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void jumpSpawnPoint(EntityPlayer player) {
/* 256 */     MCH_Lib.DbgLog(false, "JumpSpawnPoint", new Object[0]);
/* 257 */     CommandTeleport cmd = new CommandTeleport();
/*     */ 
/*     */     
/* 260 */     if (cmd.func_184882_a(MCH_Utils.getServer(), (ICommandSender)player)) {
/*     */ 
/*     */       
/* 263 */       MinecraftServer minecraftServer = MCH_Utils.getServer();
/*     */ 
/*     */       
/* 266 */       for (String playerName : minecraftServer.func_184103_al().func_72369_d()) {
/*     */ 
/*     */         
/*     */         try {
/* 270 */           EntityPlayerMP jumpPlayer = CommandTeleport.func_184888_a(minecraftServer, (ICommandSender)player, playerName);
/*     */           
/* 272 */           BlockPos cc = null;
/*     */           
/* 274 */           if (jumpPlayer != null && jumpPlayer.field_71093_bK == player.field_71093_bK) {
/*     */             
/* 276 */             cc = jumpPlayer.getBedLocation(jumpPlayer.field_71093_bK);
/*     */             
/* 278 */             if (cc != null)
/*     */             {
/*     */               
/* 281 */               cc = EntityPlayer.func_180467_a((World)minecraftServer.func_71218_a(jumpPlayer.field_71093_bK), cc, true);
/*     */             }
/*     */ 
/*     */             
/* 285 */             if (cc == null)
/*     */             {
/* 287 */               cc = jumpPlayer.field_70170_p.field_73011_w.getRandomizedSpawnPoint();
/*     */             }
/*     */           } 
/*     */           
/* 291 */           if (cc != null)
/*     */           {
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
/* 303 */             String[] cmdStr = { playerName, String.format("%.1f", new Object[] { Double.valueOf(cc.func_177958_n() + 0.5D) }), String.format("%.1f", new Object[] { Double.valueOf(cc.func_177956_o() + 0.1D) }), String.format("%.1f", new Object[] {
/*     */ 
/*     */                     
/* 306 */                     Double.valueOf(cc.func_177952_p() + 0.5D)
/*     */                   }) };
/*     */ 
/*     */ 
/*     */             
/* 311 */             cmd.func_184881_a(minecraftServer, (ICommandSender)player, cmdStr);
/*     */           }
/*     */         
/* 314 */         } catch (CommandException e) {
/*     */           
/* 316 */           e.printStackTrace();
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void shuffleTeam(EntityPlayer player) {
/* 324 */     Collection<ScorePlayerTeam> teams = player.field_70170_p.func_96441_U().func_96525_g();
/* 325 */     int teamNum = teams.size();
/*     */     
/* 327 */     MCH_Lib.DbgLog(false, "ShuffleTeam:%d teams ----------", new Object[] {
/*     */           
/* 329 */           Integer.valueOf(teamNum)
/*     */         });
/*     */     
/* 332 */     if (teamNum > 0) {
/*     */       
/* 334 */       CommandScoreboard cmd = new CommandScoreboard();
/*     */ 
/*     */       
/* 337 */       if (cmd.func_184882_a(MCH_Utils.getServer(), (ICommandSender)player)) {
/*     */ 
/*     */         
/* 340 */         List<String> list = Arrays.asList(MCH_Utils.getServer().func_184103_al().func_72369_d());
/*     */         
/* 342 */         Collections.shuffle(list);
/*     */         
/* 344 */         ArrayList<String> listTeam = new ArrayList<>();
/*     */         
/* 346 */         for (ScorePlayerTeam o : teams) {
/*     */           
/* 348 */           ScorePlayerTeam team = o;
/* 349 */           listTeam.add(team.func_96661_b());
/*     */         } 
/*     */         
/* 352 */         Collections.shuffle(listTeam);
/*     */         
/* 354 */         int i = 0;
/*     */         int j;
/* 356 */         for (j = 0; i < list.size(); i++) {
/*     */           
/* 358 */           listTeam.set(j, (String)listTeam.get(j) + " " + (String)list.get(i));
/* 359 */           j++;
/*     */           
/* 361 */           if (j >= teamNum)
/*     */           {
/* 363 */             j = 0;
/*     */           }
/*     */         } 
/*     */         
/* 367 */         for (j = 0; j < listTeam.size(); j++) {
/*     */           
/* 369 */           String exe_cmd = "teams join " + (String)listTeam.get(j);
/* 370 */           String[] process_cmd = exe_cmd.split(" ");
/*     */           
/* 372 */           if (process_cmd.length > 3) {
/*     */             
/* 374 */             MCH_Lib.DbgLog(false, "ShuffleTeam:" + exe_cmd, new Object[0]);
/*     */ 
/*     */             
/*     */             try {
/* 378 */               cmd.func_184881_a(MCH_Utils.getServer(), (ICommandSender)player, process_cmd);
/*     */             }
/* 380 */             catch (CommandException e) {
/*     */               
/* 382 */               e.printStackTrace();
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean spotEntity(EntityLivingBase player, @Nullable MCH_EntityAircraft ac, double posX, double posY, double posZ, int targetFilter, float spotLength, int markTime, float angle) {
/* 394 */     boolean ret = false;
/*     */     
/* 396 */     if (!player.field_70170_p.field_72995_K) {
/*     */ 
/*     */ 
/*     */       
/* 400 */       float acRoll = 0.0F;
/*     */       
/* 402 */       if (ac != null)
/*     */       {
/*     */ 
/*     */         
/* 406 */         acRoll = ac.getRotRoll();
/*     */       }
/*     */       
/* 409 */       Vec3d vv = MCH_Lib.RotVec3(0.0D, 0.0D, 1.0D, -player.field_70177_z, -player.field_70125_A, -acRoll);
/* 410 */       double tx = vv.field_72450_a;
/* 411 */       double tz = vv.field_72449_c;
/*     */ 
/*     */       
/* 414 */       List<Entity> list = player.field_70170_p.func_72839_b((Entity)player, player
/* 415 */           .func_174813_aQ().func_72314_b(spotLength, spotLength, spotLength));
/* 416 */       List<Integer> entityList = new ArrayList<>();
/* 417 */       Vec3d pos = new Vec3d(posX, posY, posZ);
/*     */       
/* 419 */       for (int i = 0; i < list.size(); i++) {
/*     */         
/* 421 */         Entity entity = list.get(i);
/*     */         
/* 423 */         if (canSpotEntityWithFilter(targetFilter, entity)) {
/*     */           
/* 425 */           MCH_TargetType stopType = canSpotEntity((Entity)player, posX, posY, posZ, entity, true);
/*     */           
/* 427 */           if (stopType != MCH_TargetType.NONE && stopType != MCH_TargetType.SAME_TEAM_PLAYER) {
/*     */             
/* 429 */             double dist = entity.func_70092_e(pos.field_72450_a, pos.field_72448_b, pos.field_72449_c);
/*     */             
/* 431 */             if (dist > 1.0D && dist < (spotLength * spotLength)) {
/*     */               
/* 433 */               double cx = entity.field_70165_t - pos.field_72450_a;
/* 434 */               double cy = entity.field_70163_u - pos.field_72448_b;
/* 435 */               double cz = entity.field_70161_v - pos.field_72449_c;
/* 436 */               double h = MCH_Lib.getPosAngle(tx, tz, cx, cz);
/* 437 */               double v = Math.atan2(cy, Math.sqrt(cx * cx + cz * cz)) * 180.0D / Math.PI;
/*     */               
/* 439 */               v = Math.abs(v + player.field_70125_A);
/*     */               
/* 441 */               if (h < (angle * 2.0F) && v < (angle * 2.0F))
/*     */               {
/* 443 */                 entityList.add(Integer.valueOf(entity.func_145782_y()));
/*     */               }
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 450 */       if (entityList.size() > 0) {
/*     */         
/* 452 */         int[] entityId = new int[entityList.size()];
/*     */         
/* 454 */         for (int j = 0; j < entityId.length; j++)
/*     */         {
/* 456 */           entityId[j] = ((Integer)entityList.get(j)).intValue();
/*     */         }
/*     */         
/* 459 */         sendSpotedEntityListToSameTeam(player, markTime, entityId);
/* 460 */         ret = true;
/*     */       }
/*     */       else {
/*     */         
/* 464 */         ret = false;
/*     */       } 
/*     */     } 
/*     */     
/* 468 */     return ret;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void sendSpotedEntityListToSameTeam(EntityLivingBase player, int count, int[] entityId) {
/* 474 */     PlayerList svCnf = MCH_Utils.getServer().func_184103_al();
/*     */ 
/*     */     
/* 477 */     for (EntityPlayer notifyPlayer : svCnf.func_181057_v()) {
/*     */       
/* 479 */       if (player == notifyPlayer || player.func_184191_r((Entity)notifyPlayer))
/*     */       {
/* 481 */         MCH_PacketNotifySpotedEntity.send(notifyPlayer, count, entityId);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean markPoint(EntityPlayer player, double posX, double posY, double posZ) {
/* 488 */     Vec3d vs = new Vec3d(posX, posY, posZ);
/* 489 */     Vec3d ve = MCH_Lib.Rot2Vec3(player.field_70177_z, player.field_70125_A);
/* 490 */     ve = vs.func_72441_c(ve.field_72450_a * 300.0D, ve.field_72448_b * 300.0D, ve.field_72449_c * 300.0D);
/*     */     
/* 492 */     RayTraceResult mop = player.field_70170_p.func_72901_a(vs, ve, true);
/*     */ 
/*     */     
/* 495 */     if (mop != null && mop.field_72313_a == RayTraceResult.Type.BLOCK) {
/*     */ 
/*     */       
/* 498 */       sendMarkPointToSameTeam(player, mop.func_178782_a().func_177958_n(), mop.func_178782_a().func_177956_o(), mop
/* 499 */           .func_178782_a().func_177952_p());
/* 500 */       return true;
/*     */     } 
/*     */     
/* 503 */     sendMarkPointToSameTeam(player, 0, 1000, 0);
/*     */     
/* 505 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void sendMarkPointToSameTeam(EntityPlayer player, int x, int y, int z) {
/* 511 */     PlayerList svCnf = MCH_Utils.getServer().func_184103_al();
/*     */ 
/*     */     
/* 514 */     for (EntityPlayer notifyPlayer : svCnf.func_181057_v()) {
/*     */       
/* 516 */       if (player == notifyPlayer || player.func_184191_r((Entity)notifyPlayer))
/*     */       {
/* 518 */         MCH_PacketNotifyMarkPoint.send(notifyPlayer, x, y, z);
/*     */       }
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\multiplay\MCH_Multiplay.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */