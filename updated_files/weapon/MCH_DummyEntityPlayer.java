package mcheli.weapon;

import mcheli.wrapper.ChatMessageComponent;
import mcheli.wrapper.W_EntityPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

public class MCH_DummyEntityPlayer extends W_EntityPlayer {
  public MCH_DummyEntityPlayer(World worldIn, EntityPlayer player) {
    super(worldIn, player);
  }
  
  public void addChatMessage(ITextComponent var1) {}
  
  public boolean canCommandSenderUseCommand(int var1, String var2) {
    return false;
  }
  
  public Entity getCommandSenderEntity() {
    return super.getCommandSenderEntity();
  }
  
  public boolean isSpectator() {
    return false;
  }
  
  public boolean isCreative() {
    return false;
  }
  
  public void sendChatToPlayer(ChatMessageComponent chatmessagecomponent) {}
}
