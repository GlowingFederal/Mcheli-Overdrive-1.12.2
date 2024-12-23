package mcheli.gltd;

import com.google.common.io.ByteArrayDataInput;
import java.io.DataOutputStream;
import java.io.IOException;
import mcheli.MCH_Packet;

public class MCH_PacketGLTDPlayerControl extends MCH_Packet {
  public byte switchCameraMode = -1;
  
  public byte switchWeapon = -1;
  
  public boolean useWeapon = false;
  
  public int useWeaponOption1 = 0;
  
  public int useWeaponOption2 = 0;
  
  public double useWeaponPosX = 0.0D;
  
  public double useWeaponPosY = 0.0D;
  
  public double useWeaponPosZ = 0.0D;
  
  public boolean unmount = false;
  
  public int getMessageID() {
    return 536887312;
  }
  
  public void readData(ByteArrayDataInput data) {
    try {
      this.switchCameraMode = data.readByte();
      this.switchWeapon = data.readByte();
      this.useWeapon = (data.readByte() != 0);
      if (this.useWeapon) {
        this.useWeaponOption1 = data.readInt();
        this.useWeaponOption2 = data.readInt();
        this.useWeaponPosX = data.readDouble();
        this.useWeaponPosY = data.readDouble();
        this.useWeaponPosZ = data.readDouble();
      } 
      this.unmount = (data.readByte() != 0);
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public void writeData(DataOutputStream dos) {
    try {
      dos.writeByte(this.switchCameraMode);
      dos.writeByte(this.switchWeapon);
      dos.writeByte(this.useWeapon ? 1 : 0);
      if (this.useWeapon) {
        dos.writeInt(this.useWeaponOption1);
        dos.writeInt(this.useWeaponOption2);
        dos.writeDouble(this.useWeaponPosX);
        dos.writeDouble(this.useWeaponPosY);
        dos.writeDouble(this.useWeaponPosZ);
      } 
      dos.writeByte(this.unmount ? 1 : 0);
    } catch (IOException e) {
      e.printStackTrace();
    } 
  }
}
