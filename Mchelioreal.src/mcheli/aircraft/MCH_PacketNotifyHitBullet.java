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
/*    */ public class MCH_PacketNotifyHitBullet
/*    */   extends MCH_Packet
/*    */ {
/* 25 */   public int entityID_Ac = -1;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMessageID() {
/* 31 */     return 268439602;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readData(ByteArrayDataInput data) {
/*    */     try {
/* 39 */       this.entityID_Ac = data.readInt();
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
/* 52 */       dos.writeInt(this.entityID_Ac);
/*    */     }
/* 54 */     catch (IOException e) {
/*    */       
/* 56 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public static void send(MCH_EntityAircraft ac, EntityPlayer rider) {
/* 62 */     if (rider == null || rider.field_70128_L) {
/*    */       return;
/*    */     }
/*    */     
/* 66 */     MCH_PacketNotifyHitBullet s = new MCH_PacketNotifyHitBullet();
/* 67 */     s.entityID_Ac = (ac != null && !ac.field_70128_L) ? W_Entity.getEntityId((Entity)ac) : -1;
/* 68 */     W_Network.sendToPlayer((W_PacketBase)s, rider);
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\aircraft\MCH_PacketNotifyHitBullet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */