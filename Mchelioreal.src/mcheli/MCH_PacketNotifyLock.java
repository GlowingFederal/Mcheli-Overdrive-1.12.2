/*    */ package mcheli;
/*    */ 
/*    */ import com.google.common.io.ByteArrayDataInput;
/*    */ import java.io.DataOutputStream;
/*    */ import java.io.IOException;
/*    */ import mcheli.wrapper.W_Network;
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
/*    */ public class MCH_PacketNotifyLock
/*    */   extends MCH_Packet
/*    */ {
/* 25 */   public int entityID = -1;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMessageID() {
/* 31 */     return 536873984;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readData(ByteArrayDataInput data) {
/*    */     try {
/* 39 */       this.entityID = data.readInt();
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
/* 52 */       dos.writeInt(this.entityID);
/*    */     }
/* 54 */     catch (IOException e) {
/*    */       
/* 56 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public static void send(Entity target) {
/* 62 */     if (target != null) {
/*    */       
/* 64 */       MCH_PacketNotifyLock s = new MCH_PacketNotifyLock();
/* 65 */       s.entityID = target.func_145782_y();
/* 66 */       W_Network.sendToServer(s);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public static void sendToPlayer(EntityPlayer entity) {
/* 72 */     if (entity instanceof net.minecraft.entity.player.EntityPlayerMP) {
/*    */       
/* 74 */       MCH_PacketNotifyLock s = new MCH_PacketNotifyLock();
/* 75 */       W_Network.sendToPlayer(s, entity);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\MCH_PacketNotifyLock.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */