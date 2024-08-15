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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MCH_PacketIndRotation
/*    */   extends MCH_Packet
/*    */ {
/* 28 */   public int entityID_Ac = -1;
/* 29 */   public float yaw = 0.0F;
/* 30 */   public float pitch = 0.0F;
/* 31 */   public float roll = 0.0F;
/*    */ 
/*    */   
/*    */   public boolean rollRev = false;
/*    */ 
/*    */   
/*    */   public int getMessageID() {
/* 38 */     return 536875062;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readData(ByteArrayDataInput data) {
/*    */     try {
/* 46 */       this.entityID_Ac = data.readInt();
/* 47 */       this.yaw = data.readFloat();
/* 48 */       this.pitch = data.readFloat();
/* 49 */       this.roll = data.readFloat();
/* 50 */       this.rollRev = (data.readByte() != 0);
/*    */     }
/* 52 */     catch (Exception e) {
/*    */       
/* 54 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writeData(DataOutputStream dos) {
/*    */     try {
/* 63 */       dos.writeInt(this.entityID_Ac);
/* 64 */       dos.writeFloat(this.yaw);
/* 65 */       dos.writeFloat(this.pitch);
/* 66 */       dos.writeFloat(this.roll);
/* 67 */       dos.writeByte(this.rollRev ? 1 : 0);
/*    */     }
/* 69 */     catch (IOException e) {
/*    */       
/* 71 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public static void send(MCH_EntityAircraft ac) {
/* 77 */     if (ac == null) {
/*    */       return;
/*    */     }
/*    */ 
/*    */     
/* 82 */     MCH_PacketIndRotation s = new MCH_PacketIndRotation();
/*    */     
/* 84 */     s.entityID_Ac = W_Entity.getEntityId((Entity)ac);
/* 85 */     s.yaw = ac.getRotYaw();
/* 86 */     s.pitch = ac.getRotPitch();
/* 87 */     s.roll = ac.getRotRoll();
/* 88 */     s.rollRev = ac.aircraftRollRev;
/*    */     
/* 90 */     W_Network.sendToServer((W_PacketBase)s);
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\aircraft\MCH_PacketIndRotation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */