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
/*    */ 
/*    */ public class MCH_PacketNotifyOnMountEntity
/*    */   extends MCH_Packet
/*    */ {
/* 28 */   public int entityID_Ac = -1;
/* 29 */   public int entityID_rider = -1;
/* 30 */   public int seatID = -1;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMessageID() {
/* 36 */     return 268439632;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readData(ByteArrayDataInput data) {
/*    */     try {
/* 44 */       this.entityID_Ac = data.readInt();
/* 45 */       this.entityID_rider = data.readInt();
/* 46 */       this.seatID = data.readByte();
/*    */     }
/* 48 */     catch (Exception e) {
/*    */       
/* 50 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writeData(DataOutputStream dos) {
/*    */     try {
/* 59 */       dos.writeInt(this.entityID_Ac);
/* 60 */       dos.writeInt(this.entityID_rider);
/* 61 */       dos.writeByte(this.seatID);
/*    */     }
/* 63 */     catch (IOException e) {
/*    */       
/* 65 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public static void send(MCH_EntityAircraft ac, Entity rider, int seatId) {
/* 71 */     if (ac == null || rider == null) {
/*    */       return;
/*    */     }
/* 74 */     Entity pilot = ac.getRiddenByEntity();
/*    */     
/* 76 */     if (!(pilot instanceof EntityPlayer) || pilot.field_70128_L) {
/*    */       return;
/*    */     }
/*    */     
/* 80 */     MCH_PacketNotifyOnMountEntity s = new MCH_PacketNotifyOnMountEntity();
/*    */     
/* 82 */     s.entityID_Ac = W_Entity.getEntityId((Entity)ac);
/* 83 */     s.entityID_rider = W_Entity.getEntityId(rider);
/* 84 */     s.seatID = seatId;
/*    */     
/* 86 */     W_Network.sendToPlayer((W_PacketBase)s, (EntityPlayer)pilot);
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\aircraft\MCH_PacketNotifyOnMountEntity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */