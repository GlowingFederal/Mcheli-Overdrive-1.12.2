/*    */ package mcheli.aircraft;
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
/*    */ public class MCH_PacketSeatPlayerControl
/*    */   extends MCH_Packet
/*    */ {
/*    */   public boolean isUnmount = false;
/* 25 */   public byte switchSeat = 0;
/*    */   
/*    */   public boolean parachuting;
/*    */ 
/*    */   
/*    */   public int getMessageID() {
/* 31 */     return 536875040;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readData(ByteArrayDataInput data) {
/*    */     try {
/* 39 */       byte bf = data.readByte();
/* 40 */       this.isUnmount = ((bf >> 3 & 0x1) != 0);
/* 41 */       this.switchSeat = (byte)(bf >> 1 & 0x3);
/* 42 */       this.parachuting = ((bf >> 0 & 0x1) != 0);
/*    */     }
/* 44 */     catch (Exception e) {
/*    */       
/* 46 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writeData(DataOutputStream dos) {
/*    */     try {
/* 55 */       byte bf = (byte)((this.isUnmount ? 8 : 0) | this.switchSeat << 1 | (this.parachuting ? 1 : 0));
/*    */       
/* 57 */       dos.writeByte(bf);
/*    */     }
/* 59 */     catch (IOException e) {
/*    */       
/* 61 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\aircraft\MCH_PacketSeatPlayerControl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */