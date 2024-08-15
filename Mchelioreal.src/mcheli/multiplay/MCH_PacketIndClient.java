/*    */ package mcheli.multiplay;
/*    */ 
/*    */ import com.google.common.io.ByteArrayDataInput;
/*    */ import java.io.DataOutputStream;
/*    */ import java.io.IOException;
/*    */ import mcheli.MCH_Packet;
/*    */ import mcheli.wrapper.W_Network;
/*    */ import mcheli.wrapper.W_PacketBase;
/*    */ import net.minecraft.entity.player.EntityPlayer;
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
/*    */ public class MCH_PacketIndClient
/*    */   extends MCH_Packet
/*    */ {
/* 25 */   public int CmdID = -1;
/*    */   
/*    */   public String CmdStr;
/*    */ 
/*    */   
/*    */   public int getMessageID() {
/* 31 */     return 268438032;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readData(ByteArrayDataInput data) {
/*    */     try {
/* 39 */       this.CmdID = data.readInt();
/* 40 */       this.CmdStr = data.readUTF();
/*    */     }
/* 42 */     catch (Exception e) {
/*    */       
/* 44 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writeData(DataOutputStream dos) {
/*    */     try {
/* 53 */       dos.writeInt(this.CmdID);
/* 54 */       dos.writeUTF(this.CmdStr);
/*    */     }
/* 56 */     catch (IOException e) {
/*    */       
/* 58 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public static void send(EntityPlayer player, int cmd_id, String str) {
/* 64 */     if (cmd_id <= 0) {
/*    */       return;
/*    */     }
/*    */     
/* 68 */     MCH_PacketIndClient s = new MCH_PacketIndClient();
/* 69 */     s.CmdID = cmd_id;
/* 70 */     s.CmdStr = str;
/* 71 */     W_Network.sendToPlayer((W_PacketBase)s, player);
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\multiplay\MCH_PacketIndClient.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */