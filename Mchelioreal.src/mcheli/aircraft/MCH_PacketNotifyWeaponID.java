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
/*    */ 
/*    */ public class MCH_PacketNotifyWeaponID
/*    */   extends MCH_Packet
/*    */ {
/* 29 */   public int entityID_Ac = -1;
/* 30 */   public int seatID = -1;
/* 31 */   public int weaponID = -1;
/* 32 */   public short ammo = 0;
/* 33 */   public short restAmmo = 0;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMessageID() {
/* 39 */     return 268439601;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readData(ByteArrayDataInput data) {
/*    */     try {
/* 47 */       this.entityID_Ac = data.readInt();
/* 48 */       this.seatID = data.readByte();
/* 49 */       this.weaponID = data.readByte();
/* 50 */       this.ammo = data.readShort();
/* 51 */       this.restAmmo = data.readShort();
/*    */     }
/* 53 */     catch (Exception e) {
/*    */       
/* 55 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writeData(DataOutputStream dos) {
/*    */     try {
/* 64 */       dos.writeInt(this.entityID_Ac);
/* 65 */       dos.writeByte(this.seatID);
/* 66 */       dos.writeByte(this.weaponID);
/* 67 */       dos.writeShort(this.ammo);
/* 68 */       dos.writeShort(this.restAmmo);
/*    */     }
/* 70 */     catch (IOException e) {
/*    */       
/* 72 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public static void send(Entity sender, int sid, int wid, int ammo, int rest_ammo) {
/* 78 */     MCH_PacketNotifyWeaponID s = new MCH_PacketNotifyWeaponID();
/*    */     
/* 80 */     s.entityID_Ac = W_Entity.getEntityId(sender);
/* 81 */     s.seatID = sid;
/* 82 */     s.weaponID = wid;
/* 83 */     s.ammo = (short)ammo;
/* 84 */     s.restAmmo = (short)rest_ammo;
/*    */     
/* 86 */     W_Network.sendToAllAround((W_PacketBase)s, sender, 150.0D);
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\aircraft\MCH_PacketNotifyWeaponID.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */