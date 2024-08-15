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
/*    */ public class MCH_PacketIndReload
/*    */   extends MCH_Packet
/*    */ {
/* 25 */   public int entityID_Ac = -1;
/* 26 */   public int weaponID = -1;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMessageID() {
/* 32 */     return 536875059;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readData(ByteArrayDataInput data) {
/*    */     try {
/* 40 */       this.entityID_Ac = data.readInt();
/* 41 */       this.weaponID = data.readByte();
/*    */     }
/* 43 */     catch (Exception e) {
/*    */       
/* 45 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writeData(DataOutputStream dos) {
/*    */     try {
/* 54 */       dos.writeInt(this.entityID_Ac);
/* 55 */       dos.writeByte(this.weaponID);
/*    */     }
/* 57 */     catch (IOException e) {
/*    */       
/* 59 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public static void send(MCH_EntityAircraft ac, int weaponId) {
/* 65 */     if (ac == null) {
/*    */       return;
/*    */     }
/*    */ 
/*    */     
/* 70 */     MCH_PacketIndReload s = new MCH_PacketIndReload();
/*    */     
/* 72 */     s.entityID_Ac = W_Entity.getEntityId((Entity)ac);
/* 73 */     s.weaponID = weaponId;
/*    */     
/* 75 */     W_Network.sendToServer((W_PacketBase)s);
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\aircraft\MCH_PacketIndReload.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */