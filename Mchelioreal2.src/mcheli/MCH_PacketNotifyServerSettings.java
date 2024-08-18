package mcheli;

import com.google.common.io.ByteArrayDataInput;
import java.io.DataOutputStream;
import java.io.IOException;
import javax.annotation.Nullable;
import mcheli.__helper.MCH_Utils;
import mcheli.wrapper.W_Network;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

public class MCH_PacketNotifyServerSettings extends MCH_Packet {
  public boolean enableCamDistChange = true;
  
  public boolean enableEntityMarker = true;
  
  public boolean enablePVP = true;
  
  public double stingerLockRange = 120.0D;
  
  public boolean enableDebugBoundingBox = true;
  
  public boolean enableRotationLimit = false;
  
  public byte pitchLimitMax = 10;
  
  public byte pitchLimitMin = 10;
  
  public byte rollLimit = 35;
  
  public int getMessageID() {
    return 268437568;
  }
  
  public void readData(ByteArrayDataInput data) {
    try {
      byte b = data.readByte();
      this.enableCamDistChange = getBit(b, 0);
      this.enableEntityMarker = getBit(b, 1);
      this.enablePVP = getBit(b, 2);
      this.enableDebugBoundingBox = getBit(b, 3);
      this.enableRotationLimit = getBit(b, 4);
      this.stingerLockRange = data.readFloat();
      this.pitchLimitMax = data.readByte();
      this.pitchLimitMin = data.readByte();
      this.rollLimit = data.readByte();
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public void writeData(DataOutputStream dos) {
    try {
      byte b = 0;
      b = setBit(b, 0, this.enableCamDistChange);
      b = setBit(b, 1, this.enableEntityMarker);
      b = setBit(b, 2, this.enablePVP);
      b = setBit(b, 3, this.enableDebugBoundingBox);
      b = setBit(b, 4, this.enableRotationLimit);
      dos.writeByte(b);
      dos.writeFloat((float)this.stingerLockRange);
      dos.writeByte(this.pitchLimitMax);
      dos.writeByte(this.pitchLimitMin);
      dos.writeByte(this.rollLimit);
    } catch (IOException e) {
      e.printStackTrace();
    } 
  }
  
  public static void send(@Nullable EntityPlayerMP player) {
    MCH_PacketNotifyServerSettings s = new MCH_PacketNotifyServerSettings();
    s.enableCamDistChange = !MCH_Config.DisableCameraDistChange.prmBool;
    s.enableEntityMarker = MCH_Config.DisplayEntityMarker.prmBool;
    s.enablePVP = MCH_Utils.getServer().func_71219_W();
    s.stingerLockRange = MCH_Config.StingerLockRange.prmDouble;
    s.enableDebugBoundingBox = MCH_Config.EnableDebugBoundingBox.prmBool;
    s.enableRotationLimit = MCH_Config.EnableRotationLimit.prmBool;
    s.pitchLimitMax = (byte)MCH_Config.PitchLimitMax.prmInt;
    s.pitchLimitMin = (byte)MCH_Config.PitchLimitMin.prmInt;
    s.rollLimit = (byte)MCH_Config.RollLimit.prmInt;
    if (player != null) {
      W_Network.sendToPlayer(s, (EntityPlayer)player);
    } else {
      W_Network.sendToAllPlayers(s);
    } 
  }
  
  public static void sendAll() {
    send((EntityPlayerMP)null);
  }
}
