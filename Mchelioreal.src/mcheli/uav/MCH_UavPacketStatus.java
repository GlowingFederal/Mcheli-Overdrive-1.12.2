/*    */ package mcheli.uav;
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
/*    */ public class MCH_UavPacketStatus
/*    */   extends MCH_Packet
/*    */ {
/* 25 */   public byte posUavX = 0;
/* 26 */   public byte posUavY = 0;
/* 27 */   public byte posUavZ = 0;
/*    */ 
/*    */   
/*    */   public boolean continueControl = false;
/*    */ 
/*    */   
/*    */   public int getMessageID() {
/* 34 */     return 537133072;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readData(ByteArrayDataInput data) {
/*    */     try {
/* 42 */       this.posUavX = data.readByte();
/* 43 */       this.posUavY = data.readByte();
/* 44 */       this.posUavZ = data.readByte();
/* 45 */       this.continueControl = (data.readByte() != 0);
/*    */     }
/* 47 */     catch (Exception e) {
/*    */       
/* 49 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writeData(DataOutputStream dos) {
/*    */     try {
/* 58 */       dos.writeByte(this.posUavX);
/* 59 */       dos.writeByte(this.posUavY);
/* 60 */       dos.writeByte(this.posUavZ);
/* 61 */       dos.writeByte(this.continueControl ? 1 : 0);
/*    */     }
/* 63 */     catch (IOException e) {
/*    */       
/* 65 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mchel\\uav\MCH_UavPacketStatus.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */