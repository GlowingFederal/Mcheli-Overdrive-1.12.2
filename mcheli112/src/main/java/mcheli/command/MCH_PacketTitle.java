package mcheli.command;

import com.google.common.io.ByteArrayDataInput;
import java.io.DataOutputStream;
import java.io.IOException;
import mcheli.MCH_Packet;
import mcheli.wrapper.W_Network;
import mcheli.wrapper.W_PacketBase;
import net.minecraft.util.text.ITextComponent;

public class MCH_PacketTitle extends MCH_Packet {
  public ITextComponent chatComponent = null;
  
  public int showTime = 1;
  
  public int position = 0;
  
  public int getMessageID() {
    return 268438272;
  }
  
  public void readData(ByteArrayDataInput data) {
    try {
      this.chatComponent = ITextComponent.Serializer.func_150699_a(data.readUTF());
      this.showTime = data.readShort();
      this.position = data.readShort();
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public void writeData(DataOutputStream dos) {
    try {
      dos.writeUTF(ITextComponent.Serializer.func_150696_a(this.chatComponent));
      dos.writeShort(this.showTime);
      dos.writeShort(this.position);
    } catch (IOException e) {
      e.printStackTrace();
    } 
  }
  
  public static void send(ITextComponent chat, int showTime, int pos) {
    MCH_PacketTitle s = new MCH_PacketTitle();
    s.chatComponent = chat;
    s.showTime = showTime;
    s.position = pos;
    W_Network.sendToAllPlayers((W_PacketBase)s);
  }
}
