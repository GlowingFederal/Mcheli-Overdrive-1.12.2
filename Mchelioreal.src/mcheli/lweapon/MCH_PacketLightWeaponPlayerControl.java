/*    */ package mcheli.lweapon;
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
/*    */ public class MCH_PacketLightWeaponPlayerControl
/*    */   extends MCH_Packet
/*    */ {
/*    */   public boolean useWeapon = false;
/* 30 */   public int useWeaponOption1 = 0;
/* 31 */   public int useWeaponOption2 = 0;
/* 32 */   public double useWeaponPosX = 0.0D;
/* 33 */   public double useWeaponPosY = 0.0D;
/* 34 */   public double useWeaponPosZ = 0.0D;
/* 35 */   public int cmpReload = 0;
/* 36 */   public int camMode = 0;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMessageID() {
/* 42 */     return 536936464;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readData(ByteArrayDataInput data) {
/*    */     try {
/* 50 */       this.useWeapon = (data.readByte() != 0);
/* 51 */       if (this.useWeapon) {
/*    */         
/* 53 */         this.useWeaponOption1 = data.readInt();
/* 54 */         this.useWeaponOption2 = data.readInt();
/* 55 */         this.useWeaponPosX = data.readDouble();
/* 56 */         this.useWeaponPosY = data.readDouble();
/* 57 */         this.useWeaponPosZ = data.readDouble();
/*    */       } 
/* 59 */       this.cmpReload = data.readByte();
/* 60 */       this.camMode = data.readByte();
/*    */     }
/* 62 */     catch (Exception e) {
/*    */       
/* 64 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writeData(DataOutputStream dos) {
/*    */     try {
/* 73 */       dos.writeByte(this.useWeapon ? 1 : 0);
/* 74 */       if (this.useWeapon) {
/*    */         
/* 76 */         dos.writeInt(this.useWeaponOption1);
/* 77 */         dos.writeInt(this.useWeaponOption2);
/* 78 */         dos.writeDouble(this.useWeaponPosX);
/* 79 */         dos.writeDouble(this.useWeaponPosY);
/* 80 */         dos.writeDouble(this.useWeaponPosZ);
/*    */       } 
/* 82 */       dos.writeByte(this.cmpReload);
/* 83 */       dos.writeByte(this.camMode);
/*    */     }
/* 85 */     catch (IOException e) {
/*    */       
/* 87 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\lweapon\MCH_PacketLightWeaponPlayerControl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */