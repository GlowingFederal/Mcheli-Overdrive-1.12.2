package mcheli.block;

import com.google.common.io.ByteArrayDataInput;
import java.io.DataOutputStream;
import java.io.IOException;
import mcheli.MCH_Lib;
import mcheli.MCH_Packet;
import mcheli.__helper.network.PacketHelper;
import mcheli.wrapper.W_Network;
import mcheli.wrapper.W_PacketBase;
import net.minecraft.item.crafting.IRecipe;

public class MCH_DraftingTableCreatePacket extends MCH_Packet {
  public IRecipe recipe;
  
  public int getMessageID() {
    return 537395216;
  }
  
  public void readData(ByteArrayDataInput data) {
    try {
      this.recipe = PacketHelper.readRecipe(data);
    } catch (Exception exception) {}
  }
  
  public void writeData(DataOutputStream dos) {
    try {
      PacketHelper.writeRecipe(dos, this.recipe);
    } catch (IOException e) {
      e.printStackTrace();
    } 
  }
  
  public static void send(IRecipe recipe) {
    if (recipe != null) {
      MCH_DraftingTableCreatePacket s = new MCH_DraftingTableCreatePacket();
      s.recipe = recipe;
      W_Network.sendToServer((W_PacketBase)s);
      MCH_Lib.DbgLog(true, "MCH_DraftingTableCreatePacket.send recipe = " + recipe.getRegistryName(), new Object[0]);
    } 
  }
}
