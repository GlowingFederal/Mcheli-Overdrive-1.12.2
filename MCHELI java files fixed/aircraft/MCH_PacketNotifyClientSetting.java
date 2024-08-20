package mcheli.aircraft;

import com.google.common.io.ByteArrayDataInput;
import java.io.DataOutputStream;
import java.io.IOException;
import mcheli.MCH_Config;
import mcheli.MCH_Packet;
import mcheli.wrapper.W_EntityRenderer;
import mcheli.wrapper.W_Network;
import mcheli.wrapper.W_PacketBase;

public class MCH_PacketNotifyClientSetting extends MCH_Packet {
  public boolean dismountAll = true;
  
  public boolean heliAutoThrottleDown;
  
  public boolean planeAutoThrottleDown;
  
  public boolean tankAutoThrottleDown;
  
  public boolean shaderSupport = false;
  
  public int getMessageID() {
    return 536875072;
  }
  
  public void readData(ByteArrayDataInput di) {
    try {
      byte data = 0;
      data = di.readByte();
      this.dismountAll = getBit(data, 0);
      this.heliAutoThrottleDown = getBit(data, 1);
      this.planeAutoThrottleDown = getBit(data, 2);
      this.tankAutoThrottleDown = getBit(data, 3);
      this.shaderSupport = getBit(data, 4);
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public void writeData(DataOutputStream dos) {
    try {
      byte data = 0;
      data = setBit(data, 0, this.dismountAll);
      data = setBit(data, 1, this.heliAutoThrottleDown);
      data = setBit(data, 2, this.planeAutoThrottleDown);
      data = setBit(data, 3, this.tankAutoThrottleDown);
      data = setBit(data, 4, this.shaderSupport);
      dos.writeByte(data);
    } catch (IOException e) {
      e.printStackTrace();
    } 
  }
  
  public static void send() {
    MCH_PacketNotifyClientSetting s = new MCH_PacketNotifyClientSetting();
    s.dismountAll = MCH_Config.DismountAll.prmBool;
    s.heliAutoThrottleDown = MCH_Config.AutoThrottleDownHeli.prmBool;
    s.planeAutoThrottleDown = MCH_Config.AutoThrottleDownPlane.prmBool;
    s.tankAutoThrottleDown = MCH_Config.AutoThrottleDownTank.prmBool;
    s.shaderSupport = W_EntityRenderer.isShaderSupport();
    W_Network.sendToServer((W_PacketBase)s);
  }
}
