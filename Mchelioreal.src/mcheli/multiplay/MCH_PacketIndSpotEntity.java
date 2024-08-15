/*    */ package mcheli.multiplay;
/*    */ 
/*    */ import com.google.common.io.ByteArrayDataInput;
/*    */ import java.io.DataOutputStream;
/*    */ import mcheli.MCH_Packet;
/*    */ import mcheli.wrapper.W_Network;
/*    */ import mcheli.wrapper.W_PacketBase;
/*    */ import net.minecraft.entity.EntityLivingBase;
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
/*    */ public class MCH_PacketIndSpotEntity
/*    */   extends MCH_Packet
/*    */ {
/* 23 */   public int targetFilter = -1;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMessageID() {
/* 29 */     return 536873216;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readData(ByteArrayDataInput data) {
/*    */     try {
/* 37 */       this.targetFilter = data.readInt();
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
/* 50 */       dos.writeInt(this.targetFilter);
/*    */     }
/* 52 */     catch (Exception e) {
/*    */       
/* 54 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public static void send(EntityLivingBase spoter, int targetFilter) {
/* 60 */     MCH_PacketIndSpotEntity s = new MCH_PacketIndSpotEntity();
/* 61 */     s.targetFilter = targetFilter;
/* 62 */     W_Network.sendToServer((W_PacketBase)s);
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\multiplay\MCH_PacketIndSpotEntity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */