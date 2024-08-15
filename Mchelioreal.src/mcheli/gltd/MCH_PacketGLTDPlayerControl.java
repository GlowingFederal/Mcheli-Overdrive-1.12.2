/*    */ package mcheli.gltd;
/*    */ 
/*    */ import com.google.common.io.ByteArrayDataInput;
/*    */ import java.io.DataOutputStream;
/*    */ import java.io.IOException;
/*    */ import mcheli.MCH_Packet;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MCH_PacketGLTDPlayerControl
/*    */   extends MCH_Packet
/*    */ {
/* 30 */   public byte switchCameraMode = -1;
/* 31 */   public byte switchWeapon = -1;
/*    */   public boolean useWeapon = false;
/* 33 */   public int useWeaponOption1 = 0;
/* 34 */   public int useWeaponOption2 = 0;
/* 35 */   public double useWeaponPosX = 0.0D;
/* 36 */   public double useWeaponPosY = 0.0D;
/* 37 */   public double useWeaponPosZ = 0.0D;
/*    */ 
/*    */   
/*    */   public boolean unmount = false;
/*    */ 
/*    */   
/*    */   public int getMessageID() {
/* 44 */     return 536887312;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readData(ByteArrayDataInput data) {
/*    */     try {
/* 52 */       this.switchCameraMode = data.readByte();
/* 53 */       this.switchWeapon = data.readByte();
/* 54 */       this.useWeapon = (data.readByte() != 0);
/* 55 */       if (this.useWeapon) {
/*    */         
/* 57 */         this.useWeaponOption1 = data.readInt();
/* 58 */         this.useWeaponOption2 = data.readInt();
/* 59 */         this.useWeaponPosX = data.readDouble();
/* 60 */         this.useWeaponPosY = data.readDouble();
/* 61 */         this.useWeaponPosZ = data.readDouble();
/*    */       } 
/* 63 */       this.unmount = (data.readByte() != 0);
/*    */     }
/* 65 */     catch (Exception e) {
/*    */       
/* 67 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writeData(DataOutputStream dos) {
/*    */     try {
/* 76 */       dos.writeByte(this.switchCameraMode);
/* 77 */       dos.writeByte(this.switchWeapon);
/* 78 */       dos.writeByte(this.useWeapon ? 1 : 0);
/* 79 */       if (this.useWeapon) {
/*    */         
/* 81 */         dos.writeInt(this.useWeaponOption1);
/* 82 */         dos.writeInt(this.useWeaponOption2);
/* 83 */         dos.writeDouble(this.useWeaponPosX);
/* 84 */         dos.writeDouble(this.useWeaponPosY);
/* 85 */         dos.writeDouble(this.useWeaponPosZ);
/*    */       } 
/* 87 */       dos.writeByte(this.unmount ? 1 : 0);
/*    */     }
/* 89 */     catch (IOException e) {
/*    */       
/* 91 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\gltd\MCH_PacketGLTDPlayerControl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */