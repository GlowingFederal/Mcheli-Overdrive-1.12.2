package mcheli.multiplay;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;
import mcheli.MCH_Lib;
import mcheli.__helper.MCH_Utils;
import mcheli.__helper.entity.IEntitySinglePassenger;
import mcheli.aircraft.MCH_EntityAircraft;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.server.CommandScoreboard;
import net.minecraft.command.server.CommandTeleport;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class MCH_Multiplay {
  public static boolean canSpotEntityWithFilter(int filter, Entity entity) {
    if (entity instanceof mcheli.plane.MCP_EntityPlane)
      return ((filter & 0x20) != 0); 
    if (entity instanceof mcheli.helicopter.MCH_EntityHeli)
      return ((filter & 0x10) != 0); 
    if (entity instanceof mcheli.vehicle.MCH_EntityVehicle || entity instanceof mcheli.tank.MCH_EntityTank)
      return ((filter & 0x8) != 0); 
    if (entity instanceof EntityPlayer)
      return ((filter & 0x4) != 0); 
    if (entity instanceof EntityLivingBase) {
      if (isMonster(entity))
        return ((filter & 0x2) != 0); 
      return ((filter & 0x1) != 0);
    } 
    return false;
  }
  
  public static boolean isMonster(Entity entity) {
    return (entity.getClass().toString().toLowerCase().indexOf("monster") >= 0);
  }
  
  public static final MCH_TargetType[][] ENTITY_SPOT_TABLE = new MCH_TargetType[][] { { MCH_TargetType.NONE, MCH_TargetType.NONE }, { MCH_TargetType.OTHER_MOB, MCH_TargetType.OTHER_MOB }, { MCH_TargetType.MONSTER, MCH_TargetType.MONSTER }, { MCH_TargetType.NONE, MCH_TargetType.NO_TEAM_PLAYER }, { MCH_TargetType.NONE, MCH_TargetType.SAME_TEAM_PLAYER }, { MCH_TargetType.NONE, MCH_TargetType.OTHER_TEAM_PLAYER }, { MCH_TargetType.NONE, MCH_TargetType.NONE }, { MCH_TargetType.NONE, MCH_TargetType.NO_TEAM_PLAYER }, { MCH_TargetType.NONE, MCH_TargetType.SAME_TEAM_PLAYER }, { MCH_TargetType.NONE, MCH_TargetType.OTHER_TEAM_PLAYER } };
  
  public static MCH_TargetType canSpotEntity(Entity user, double posX, double posY, double posZ, Entity target, boolean checkSee) {
    if (!(user instanceof EntityLivingBase))
      return MCH_TargetType.NONE; 
    EntityLivingBase spotter = (EntityLivingBase)user;
    int col = (spotter.func_96124_cp() == null) ? 0 : 1;
    int row = 0;
    if (target instanceof EntityLivingBase)
      if (!isMonster(target)) {
        row = 1;
      } else {
        row = 2;
      }  
    if (spotter.func_96124_cp() != null) {
      if (target instanceof EntityPlayer) {
        EntityPlayer player = (EntityPlayer)target;
        if (player.func_96124_cp() == null) {
          row = 3;
        } else if (spotter.func_184191_r((Entity)player)) {
          row = 4;
        } else {
          row = 5;
        } 
      } else if (target instanceof MCH_EntityAircraft) {
        MCH_EntityAircraft ac = (MCH_EntityAircraft)target;
        EntityPlayer rideEntity = ac.getFirstMountPlayer();
        if (rideEntity == null) {
          row = 6;
        } else if (rideEntity.func_96124_cp() == null) {
          row = 7;
        } else if (spotter.func_184191_r((Entity)rideEntity)) {
          row = 8;
        } else {
          row = 9;
        } 
      } 
    } else if (target instanceof EntityPlayer || target instanceof MCH_EntityAircraft) {
      row = 0;
    } 
    MCH_TargetType ret = ENTITY_SPOT_TABLE[row][col];
    if (checkSee && ret != MCH_TargetType.NONE) {
      Vec3d vs = new Vec3d(posX, posY, posZ);
      Vec3d ve = new Vec3d(target.field_70165_t, target.field_70163_u + target.func_70047_e(), target.field_70161_v);
      RayTraceResult mop = target.field_70170_p.func_72933_a(vs, ve);
      if (mop != null && mop.field_72313_a == RayTraceResult.Type.BLOCK)
        ret = MCH_TargetType.NONE; 
    } 
    return ret;
  }
  
  public static boolean canAttackEntity(DamageSource ds, Entity target) {
    return canAttackEntity(ds.func_76346_g(), target);
  }
  
  public static boolean canAttackEntity(Entity attacker, Entity target) {
    if (attacker != null && target != null) {
      EntityPlayer attackPlayer = null;
      EntityPlayer targetPlayer = null;
      if (attacker instanceof EntityPlayer)
        attackPlayer = (EntityPlayer)attacker; 
      if (target instanceof EntityPlayer) {
        targetPlayer = (EntityPlayer)target;
      } else if (target instanceof IEntitySinglePassenger && ((IEntitySinglePassenger)target)
        .getRiddenByEntity() instanceof EntityPlayer) {
        targetPlayer = (EntityPlayer)((IEntitySinglePassenger)target).getRiddenByEntity();
      } 
      if (target instanceof MCH_EntityAircraft) {
        MCH_EntityAircraft ac = (MCH_EntityAircraft)target;
        if (ac.getRiddenByEntity() instanceof EntityPlayer)
          targetPlayer = (EntityPlayer)ac.getRiddenByEntity(); 
      } 
      if (attackPlayer != null && targetPlayer != null)
        if (!attackPlayer.func_96122_a(targetPlayer))
          return false;  
    } 
    return true;
  }
  
  public static void jumpSpawnPoint(EntityPlayer player) {
    MCH_Lib.DbgLog(false, "JumpSpawnPoint", new Object[0]);
    CommandTeleport cmd = new CommandTeleport();
    if (cmd.func_184882_a(MCH_Utils.getServer(), (ICommandSender)player)) {
      MinecraftServer minecraftServer = MCH_Utils.getServer();
      for (String playerName : minecraftServer.func_184103_al().func_72369_d()) {
        try {
          EntityPlayerMP jumpPlayer = CommandTeleport.func_184888_a(minecraftServer, (ICommandSender)player, playerName);
          BlockPos cc = null;
          if (jumpPlayer != null && jumpPlayer.field_71093_bK == player.field_71093_bK) {
            cc = jumpPlayer.getBedLocation(jumpPlayer.field_71093_bK);
            if (cc != null)
              cc = EntityPlayer.func_180467_a((World)minecraftServer.func_71218_a(jumpPlayer.field_71093_bK), cc, true); 
            if (cc == null)
              cc = jumpPlayer.field_70170_p.field_73011_w.getRandomizedSpawnPoint(); 
          } 
          if (cc != null) {
            String[] cmdStr = { playerName, String.format("%.1f", new Object[] { Double.valueOf(cc.func_177958_n() + 0.5D) }), String.format("%.1f", new Object[] { Double.valueOf(cc.func_177956_o() + 0.1D) }), String.format("%.1f", new Object[] { Double.valueOf(cc.func_177952_p() + 0.5D) }) };
            cmd.func_184881_a(minecraftServer, (ICommandSender)player, cmdStr);
          } 
        } catch (CommandException e) {
          e.printStackTrace();
        } 
      } 
    } 
  }
  
  public static void shuffleTeam(EntityPlayer player) {
    Collection<ScorePlayerTeam> teams = player.field_70170_p.func_96441_U().func_96525_g();
    int teamNum = teams.size();
    MCH_Lib.DbgLog(false, "ShuffleTeam:%d teams ----------", new Object[] { Integer.valueOf(teamNum) });
    if (teamNum > 0) {
      CommandScoreboard cmd = new CommandScoreboard();
      if (cmd.func_184882_a(MCH_Utils.getServer(), (ICommandSender)player)) {
        List<String> list = Arrays.asList(MCH_Utils.getServer().func_184103_al().func_72369_d());
        Collections.shuffle(list);
        ArrayList<String> listTeam = new ArrayList<>();
        for (ScorePlayerTeam o : teams) {
          ScorePlayerTeam team = o;
          listTeam.add(team.func_96661_b());
        } 
        Collections.shuffle(listTeam);
        int i = 0;
        int j;
        for (j = 0; i < list.size(); i++) {
          listTeam.set(j, (String)listTeam.get(j) + " " + (String)list.get(i));
          j++;
          if (j >= teamNum)
            j = 0; 
        } 
        for (j = 0; j < listTeam.size(); j++) {
          String exe_cmd = "teams join " + (String)listTeam.get(j);
          String[] process_cmd = exe_cmd.split(" ");
          if (process_cmd.length > 3) {
            MCH_Lib.DbgLog(false, "ShuffleTeam:" + exe_cmd, new Object[0]);
            try {
              cmd.func_184881_a(MCH_Utils.getServer(), (ICommandSender)player, process_cmd);
            } catch (CommandException e) {
              e.printStackTrace();
            } 
          } 
        } 
      } 
    } 
  }
  
  public static boolean spotEntity(EntityLivingBase player, @Nullable MCH_EntityAircraft ac, double posX, double posY, double posZ, int targetFilter, float spotLength, int markTime, float angle) {
    boolean ret = false;
    if (!player.field_70170_p.field_72995_K) {
      float acRoll = 0.0F;
      if (ac != null)
        acRoll = ac.getRotRoll(); 
      Vec3d vv = MCH_Lib.RotVec3(0.0D, 0.0D, 1.0D, -player.field_70177_z, -player.field_70125_A, -acRoll);
      double tx = vv.field_72450_a;
      double tz = vv.field_72449_c;
      List<Entity> list = player.field_70170_p.func_72839_b((Entity)player, player
          .func_174813_aQ().func_72314_b(spotLength, spotLength, spotLength));
      List<Integer> entityList = new ArrayList<>();
      Vec3d pos = new Vec3d(posX, posY, posZ);
      for (int i = 0; i < list.size(); i++) {
        Entity entity = list.get(i);
        if (canSpotEntityWithFilter(targetFilter, entity)) {
          MCH_TargetType stopType = canSpotEntity((Entity)player, posX, posY, posZ, entity, true);
          if (stopType != MCH_TargetType.NONE && stopType != MCH_TargetType.SAME_TEAM_PLAYER) {
            double dist = entity.func_70092_e(pos.field_72450_a, pos.field_72448_b, pos.field_72449_c);
            if (dist > 1.0D && dist < (spotLength * spotLength)) {
              double cx = entity.field_70165_t - pos.field_72450_a;
              double cy = entity.field_70163_u - pos.field_72448_b;
              double cz = entity.field_70161_v - pos.field_72449_c;
              double h = MCH_Lib.getPosAngle(tx, tz, cx, cz);
              double v = Math.atan2(cy, Math.sqrt(cx * cx + cz * cz)) * 180.0D / Math.PI;
              v = Math.abs(v + player.field_70125_A);
              if (h < (angle * 2.0F) && v < (angle * 2.0F))
                entityList.add(Integer.valueOf(entity.func_145782_y())); 
            } 
          } 
        } 
      } 
      if (entityList.size() > 0) {
        int[] entityId = new int[entityList.size()];
        for (int j = 0; j < entityId.length; j++)
          entityId[j] = ((Integer)entityList.get(j)).intValue(); 
        sendSpotedEntityListToSameTeam(player, markTime, entityId);
        ret = true;
      } else {
        ret = false;
      } 
    } 
    return ret;
  }
  
  public static void sendSpotedEntityListToSameTeam(EntityLivingBase player, int count, int[] entityId) {
    PlayerList svCnf = MCH_Utils.getServer().func_184103_al();
    for (EntityPlayer notifyPlayer : svCnf.func_181057_v()) {
      if (player == notifyPlayer || player.func_184191_r((Entity)notifyPlayer))
        MCH_PacketNotifySpotedEntity.send(notifyPlayer, count, entityId); 
    } 
  }
  
  public static boolean markPoint(EntityPlayer player, double posX, double posY, double posZ) {
    Vec3d vs = new Vec3d(posX, posY, posZ);
    Vec3d ve = MCH_Lib.Rot2Vec3(player.field_70177_z, player.field_70125_A);
    ve = vs.func_72441_c(ve.field_72450_a * 300.0D, ve.field_72448_b * 300.0D, ve.field_72449_c * 300.0D);
    RayTraceResult mop = player.field_70170_p.func_72901_a(vs, ve, true);
    if (mop != null && mop.field_72313_a == RayTraceResult.Type.BLOCK) {
      sendMarkPointToSameTeam(player, mop.func_178782_a().func_177958_n(), mop.func_178782_a().func_177956_o(), mop
          .func_178782_a().func_177952_p());
      return true;
    } 
    sendMarkPointToSameTeam(player, 0, 1000, 0);
    return false;
  }
  
  public static void sendMarkPointToSameTeam(EntityPlayer player, int x, int y, int z) {
    PlayerList svCnf = MCH_Utils.getServer().func_184103_al();
    for (EntityPlayer notifyPlayer : svCnf.func_181057_v()) {
      if (player == notifyPlayer || player.func_184191_r((Entity)notifyPlayer))
        MCH_PacketNotifyMarkPoint.send(notifyPlayer, x, y, z); 
    } 
  }
}
