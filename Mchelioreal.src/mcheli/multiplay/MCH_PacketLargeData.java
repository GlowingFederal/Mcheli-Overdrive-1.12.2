/*    */ package mcheli.multiplay;
/*    */ 
/*    */ import com.google.common.io.ByteArrayDataInput;
/*    */ import java.io.DataOutputStream;
/*    */ import mcheli.MCH_Packet;
/*    */ import mcheli.wrapper.W_Network;
/*    */ import mcheli.wrapper.W_PacketBase;
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
/*    */ public class MCH_PacketLargeData
/*    */   extends MCH_Packet
/*    */ {
/* 25 */   public int imageDataIndex = -1;
/* 26 */   public int imageDataSize = 0;
/* 27 */   public int imageDataTotalSize = 0;
/*    */   
/*    */   public byte[] buf;
/*    */ 
/*    */   
/*    */   public int getMessageID() {
/* 33 */     return 536873472;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readData(ByteArrayDataInput data) {
/*    */     try {
/* 41 */       this.imageDataIndex = data.readInt();
/* 42 */       this.imageDataSize = data.readInt();
/* 43 */       this.imageDataTotalSize = data.readInt();
/* 44 */       this.buf = new byte[this.imageDataSize];
/* 45 */       data.readFully(this.buf);
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
/* 58 */       MCH_MultiplayClient.readImageData(dos);
/*    */       
/*    */       return;
/* 61 */     } catch (Exception e) {
/*    */       
/* 63 */       e.printStackTrace();
/*    */       return;
/*    */     } 
/*    */   }
/*    */   
/*    */   public static void send() {
/* 69 */     MCH_PacketLargeData p = new MCH_PacketLargeData();
/* 70 */     W_Network.sendToServer((W_PacketBase)p);
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\multiplay\MCH_PacketLargeData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */