package mcheli.aircraft;

import com.google.common.io.ByteArrayDataInput;
import java.io.DataOutputStream;
import java.io.IOException;
import mcheli.MCH_Packet;
import mcheli.wrapper.W_Entity;
import mcheli.wrapper.W_Network;
import mcheli.wrapper.W_PacketBase;
import net.minecraft.entity.Entity;

public class MCH_PacketIndRotation extends MCH_Packet {
  public int entityID_Ac = -1;
  
  public float yaw = 0.0F;
  
  public float pitch = 0.0F;
  
  public float roll = 0.0F;
  
  public boolean rollRev = false;
  
  public int getMessageID() {
    return 536875062;
  }
  
  public void readData(ByteArrayDataInput data) {
    try {
      this.entityID_Ac = data.readInt();
      this.yaw = data.readFloat();
      this.pitch = data.readFloat();
      this.roll = data.readFloat();
      this.rollRev = (data.readByte() != 0);
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public void writeData(DataOutputStream dos) {
    try {
      dos.writeInt(this.entityID_Ac);
      dos.writeFloat(this.yaw);
      dos.writeFloat(this.pitch);
      dos.writeFloat(this.roll);
      dos.writeByte(this.rollRev ? 1 : 0);
    } catch (IOException e) {
      e.printStackTrace();
    } 
  }
  
  public static void send(MCH_EntityAircraft ac) {
    if (ac == null)
      return; 
    MCH_PacketIndRotation s = new MCH_PacketIndRotation();
    s.entityID_Ac = W_Entity.getEntityId((Entity)ac);
    s.yaw = ac.getRotYaw();
    s.pitch = ac.getRotPitch();
    s.roll = ac.getRotRoll();
    s.rollRev = ac.aircraftRollRev;
    W_Network.sendToServer((W_PacketBase)s);
  }
}
