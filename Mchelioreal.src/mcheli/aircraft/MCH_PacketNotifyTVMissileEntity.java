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
/*    */ 
/*    */ public class MCH_PacketNotifyTVMissileEntity
/*    */   extends MCH_Packet
/*    */ {
/* 24 */   public int entityID_Ac = -1;
/* 25 */   public int entityID_TVMissile = -1;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMessageID() {
/* 31 */     return 268439600;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readData(ByteArrayDataInput data) {
/*    */     try {
/* 39 */       this.entityID_Ac = data.readInt();
/* 40 */       this.entityID_TVMissile = data.readInt();
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
/* 53 */       dos.writeInt(this.entityID_Ac);
/* 54 */       dos.writeInt(this.entityID_TVMissile);
/*    */     }
/* 56 */     catch (IOException e) {
/*    */       
/* 58 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public static void send(int heliEntityID, int tvMissileEntityID) {
/* 64 */     MCH_PacketNotifyTVMissileEntity s = new MCH_PacketNotifyTVMissileEntity();
/*    */     
/* 66 */     s.entityID_Ac = heliEntityID;
/* 67 */     s.entityID_TVMissile = tvMissileEntityID;
/*    */     
/* 69 */     W_Network.sendToAllPlayers((W_PacketBase)s);
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\aircraft\MCH_PacketNotifyTVMissileEntity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */