/*    */ package mcheli.aircraft;
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
/*    */ public class MCH_PacketNotifyInfoReloaded
/*    */   extends MCH_Packet
/*    */ {
/* 23 */   public int type = -1;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMessageID() {
/* 29 */     return 536875063;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readData(ByteArrayDataInput data) {
/*    */     try {
/* 37 */       this.type = data.readInt();
/*    */     }
/* 39 */     catch (Exception e) {
/*    */       
/* 41 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writeData(DataOutputStream dos) {
/*    */     try {
/* 50 */       dos.writeInt(this.type);
/*    */     }
/* 52 */     catch (IOException e) {
/*    */       
/* 54 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public static void sendRealodAc() {
/* 60 */     MCH_PacketNotifyInfoReloaded s = new MCH_PacketNotifyInfoReloaded();
/* 61 */     s.type = 0;
/* 62 */     W_Network.sendToServer((W_PacketBase)s);
/*    */   }
/*    */ 
/*    */   
/*    */   public static void sendRealodAllWeapon() {
/* 67 */     MCH_PacketNotifyInfoReloaded s = new MCH_PacketNotifyInfoReloaded();
/* 68 */     s.type = 1;
/* 69 */     W_Network.sendToServer((W_PacketBase)s);
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\aircraft\MCH_PacketNotifyInfoReloaded.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */