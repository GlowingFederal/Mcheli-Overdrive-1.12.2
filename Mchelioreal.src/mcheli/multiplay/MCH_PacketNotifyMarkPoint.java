/*    */ package mcheli.multiplay;
/*    */ 
/*    */ import com.google.common.io.ByteArrayDataInput;
/*    */ import java.io.DataOutputStream;
/*    */ import mcheli.MCH_Packet;
/*    */ import mcheli.wrapper.W_Network;
/*    */ import mcheli.wrapper.W_PacketBase;
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
/*    */ public class MCH_PacketNotifyMarkPoint
/*    */   extends MCH_Packet
/*    */ {
/* 25 */   public int px = this.pz = 0;
/* 26 */   public int py = 0;
/*    */   
/*    */   public int pz;
/*    */ 
/*    */   
/*    */   public int getMessageID() {
/* 32 */     return 268437762;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readData(ByteArrayDataInput data) {
/*    */     try {
/* 40 */       this.px = data.readInt();
/* 41 */       this.py = data.readInt();
/* 42 */       this.pz = data.readInt();
/*    */     }
/* 44 */     catch (Exception e) {
/*    */       
/* 46 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writeData(DataOutputStream dos) {
/*    */     try {
/* 55 */       dos.writeInt(this.px);
/* 56 */       dos.writeInt(this.py);
/* 57 */       dos.writeInt(this.pz);
/*    */     }
/* 59 */     catch (Exception e) {
/*    */       
/* 61 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public static void send(EntityPlayer player, int x, int y, int z) {
/* 67 */     MCH_PacketNotifyMarkPoint pkt = new MCH_PacketNotifyMarkPoint();
/* 68 */     pkt.px = x;
/* 69 */     pkt.py = y;
/* 70 */     pkt.pz = z;
/* 71 */     W_Network.sendToPlayer((W_PacketBase)pkt, player);
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\multiplay\MCH_PacketNotifyMarkPoint.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */