/*    */ package mcheli.tank;
/*    */ 
/*    */ import com.google.common.io.ByteArrayDataInput;
/*    */ import java.io.DataOutputStream;
/*    */ import java.io.IOException;
/*    */ import mcheli.aircraft.MCH_PacketPlayerControlBase;
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
/*    */ public class MCH_TankPacketPlayerControl
/*    */   extends MCH_PacketPlayerControlBase
/*    */ {
/* 22 */   public byte switchVtol = -1;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMessageID() {
/* 28 */     return 537919504;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void readData(ByteArrayDataInput data) {
/* 34 */     super.readData(data);
/*    */ 
/*    */     
/*    */     try {
/* 38 */       this.switchVtol = data.readByte();
/*    */     }
/* 40 */     catch (Exception e) {
/*    */       
/* 42 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void writeData(DataOutputStream dos) {
/* 49 */     super.writeData(dos);
/*    */ 
/*    */     
/*    */     try {
/* 53 */       dos.writeByte(this.switchVtol);
/*    */     }
/* 55 */     catch (IOException e) {
/*    */       
/* 57 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\tank\MCH_TankPacketPlayerControl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */