/*    */ package mcheli;
/*    */ 
/*    */ import com.google.common.io.ByteArrayDataInput;
/*    */ import java.io.DataOutputStream;
/*    */ import java.io.IOException;
/*    */ import mcheli.wrapper.W_Network;
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
/*    */ public class MCH_PacketIndOpenScreen
/*    */   extends MCH_Packet
/*    */ {
/* 22 */   public int guiID = -1;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMessageID() {
/* 28 */     return 536872992;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readData(ByteArrayDataInput data) {
/*    */     try {
/* 36 */       this.guiID = data.readInt();
/*    */     }
/* 38 */     catch (Exception e) {
/*    */       
/* 40 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writeData(DataOutputStream dos) {
/*    */     try {
/* 49 */       dos.writeInt(this.guiID);
/*    */     }
/* 51 */     catch (IOException e) {
/*    */       
/* 53 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public static void send(int gui_id) {
/* 59 */     if (gui_id < 0) {
/*    */       return;
/*    */     }
/*    */     
/* 63 */     MCH_PacketIndOpenScreen s = new MCH_PacketIndOpenScreen();
/* 64 */     s.guiID = gui_id;
/* 65 */     W_Network.sendToServer(s);
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\MCH_PacketIndOpenScreen.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */