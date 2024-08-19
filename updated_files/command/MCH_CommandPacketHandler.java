package mcheli.command;

import com.google.common.io.ByteArrayDataInput;
import mcheli.MCH_MOD;
import mcheli.__helper.network.HandleSide;
import mcheli.aircraft.MCH_EntityAircraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.fml.relauncher.Side;

public class MCH_CommandPacketHandler {
  @HandleSide({Side.CLIENT})
  public static void onPacketTitle(EntityPlayer player, ByteArrayDataInput data, IThreadListener scheduler) {
    if (player == null || !player.world.isRemote)
      return; 
    MCH_PacketTitle req = new MCH_PacketTitle();
    req.readData(data);
    scheduler.addScheduledTask(() -> MCH_MOD.proxy.printChatMessage(req.chatComponent, req.showTime, req.position));
  }
  
  @HandleSide({Side.SERVER})
  public static void onPacketSave(EntityPlayer player, ByteArrayDataInput data, IThreadListener scheduler) {
    if (player == null || player.world.isRemote)
      return; 
    MCH_PacketCommandSave req = new MCH_PacketCommandSave();
    req.readData(data);
    scheduler.addScheduledTask(() -> {
          MCH_EntityAircraft ac = MCH_EntityAircraft.getAircraft_RiddenOrControl((Entity)player);
          if (ac != null)
            ac.setCommand(req.str, player); 
        });
  }
}
