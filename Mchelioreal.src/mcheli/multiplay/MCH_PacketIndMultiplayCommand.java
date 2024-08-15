/*    */ package mcheli.multiplay;
/*    */ 
/*    */ import com.google.common.io.ByteArrayDataInput;
/*    */ import java.io.DataOutputStream;
/*    */ import java.io.IOException;
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
/*    */ public class MCH_PacketIndMultiplayCommand
/*    */   extends MCH_Packet
/*    */ {
/* 24 */   public int CmdID = -1;
/*    */   
/*    */   public String CmdStr;
/*    */ 
/*    */   
/*    */   public int getMessageID() {
/* 30 */     return 536873088;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readData(ByteArrayDataInput data) {
/*    */     try {
/* 38 */       this.CmdID = data.readInt();
/* 39 */       this.CmdStr = data.readUTF();
/*    */     }
/* 41 */     catch (Exception e) {
/*    */       
/* 43 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writeData(DataOutputStream dos) {
/*    */     try {
/* 52 */       dos.writeInt(this.CmdID);
/* 53 */       dos.writeUTF(this.CmdStr);
/*    */     }
/* 55 */     catch (IOException e) {
/*    */       
/* 57 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public static void send(int cmd_id, String str) {
/* 63 */     if (cmd_id <= 0) {
/*    */       return;
/*    */     }
/*    */     
/* 67 */     MCH_PacketIndMultiplayCommand s = new MCH_PacketIndMultiplayCommand();
/* 68 */     s.CmdID = cmd_id;
/* 69 */     s.CmdStr = str;
/* 70 */     W_Network.sendToServer((W_PacketBase)s);
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\multiplay\MCH_PacketIndMultiplayCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */