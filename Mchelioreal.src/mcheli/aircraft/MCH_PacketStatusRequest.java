/*    */ package mcheli.aircraft;
/*    */ 
/*    */ import com.google.common.io.ByteArrayDataInput;
/*    */ import java.io.DataOutputStream;
/*    */ import java.io.IOException;
/*    */ import mcheli.MCH_Packet;
/*    */ import mcheli.wrapper.W_Entity;
/*    */ import mcheli.wrapper.W_Network;
/*    */ import mcheli.wrapper.W_PacketBase;
/*    */ import net.minecraft.entity.Entity;
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
/*    */ public class MCH_PacketStatusRequest
/*    */   extends MCH_Packet
/*    */ {
/* 24 */   public int entityID_AC = -1;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMessageID() {
/* 30 */     return 536875104;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readData(ByteArrayDataInput data) {
/*    */     try {
/* 38 */       this.entityID_AC = data.readInt();
/*    */     }
/* 40 */     catch (Exception e) {
/*    */       
/* 42 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writeData(DataOutputStream dos) {
/*    */     try {
/* 51 */       dos.writeInt(this.entityID_AC);
/*    */     }
/* 53 */     catch (IOException e) {
/*    */       
/* 55 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public static void requestStatus(MCH_EntityAircraft ac) {
/* 61 */     if (ac.field_70170_p.field_72995_K) {
/*    */       
/* 63 */       MCH_PacketStatusRequest s = new MCH_PacketStatusRequest();
/* 64 */       s.entityID_AC = W_Entity.getEntityId((Entity)ac);
/* 65 */       W_Network.sendToServer((W_PacketBase)s);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\aircraft\MCH_PacketStatusRequest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */