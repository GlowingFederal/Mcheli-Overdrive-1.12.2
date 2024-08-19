package mcheli.tool;

import com.google.common.io.ByteArrayDataInput;
import mcheli.MCH_Config;
import mcheli.__helper.network.HandleSide;
import mcheli.multiplay.MCH_Multiplay;
import mcheli.multiplay.MCH_PacketIndSpotEntity;
import mcheli.wrapper.W_WorldFunc;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.fml.relauncher.Side;

public class MCH_ToolPacketHandler {
  @HandleSide({Side.SERVER})
  public static void onPacket_IndSpotEntity(EntityPlayer player, ByteArrayDataInput data, IThreadListener scheduler) {
    if (player.world.isRemote)
      return; 
    MCH_PacketIndSpotEntity pc = new MCH_PacketIndSpotEntity();
    pc.readData(data);
    scheduler.addScheduledTask(() -> {
          ItemStack itemStack = player.getHeldItemMainhand();
          if (!itemStack.func_190926_b() && itemStack.getItem() instanceof mcheli.tool.rangefinder.MCH_ItemRangeFinder)
            if (pc.targetFilter == 0) {
              if (MCH_Multiplay.markPoint(player, player.posX, player.posY + player.getEyeHeight(), player.posZ)) {
                W_WorldFunc.MOD_playSoundAtEntity((Entity)player, "pi", 1.0F, 1.0F);
              } else {
                W_WorldFunc.MOD_playSoundAtEntity((Entity)player, "ng", 1.0F, 1.0F);
              } 
            } else if (itemStack.getMetadata() < itemStack.getMaxDamage()) {
              if (MCH_Config.RangeFinderConsume.prmBool)
                itemStack.damageItem(1, (EntityLivingBase)player); 
              int time = ((pc.targetFilter & 0xFC) == 0) ? 60 : MCH_Config.RangeFinderSpotTime.prmInt;
              if (MCH_Multiplay.spotEntity((EntityLivingBase)player, null, player.posX, player.posY + player.getEyeHeight(), player.posZ, pc.targetFilter, MCH_Config.RangeFinderSpotDist.prmInt, time, 20.0F)) {
                W_WorldFunc.MOD_playSoundAtEntity((Entity)player, "pi", 1.0F, 1.0F);
              } else {
                W_WorldFunc.MOD_playSoundAtEntity((Entity)player, "ng", 1.0F, 1.0F);
              } 
            }  
        });
  }
}
