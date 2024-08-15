/*    */ package mcheli.vehicle;
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
/*    */ 
/*    */ public class MCH_PacketVehiclePlayerControl
/*    */   extends MCH_PacketPlayerControlBase
/*    */ {
/* 23 */   public byte switchFold = -1;
/* 24 */   public int unhitchChainId = -1;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMessageID() {
/* 30 */     return 537002000;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void readData(ByteArrayDataInput data) {
/* 36 */     super.readData(data);
/*    */ 
/*    */     
/*    */     try {
/* 40 */       this.switchFold = data.readByte();
/* 41 */       this.unhitchChainId = data.readInt();
/*    */     }
/* 43 */     catch (Exception e) {
/*    */       
/* 45 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void writeData(DataOutputStream dos) {
/* 52 */     super.writeData(dos);
/*    */ 
/*    */     
/*    */     try {
/* 56 */       dos.writeByte(this.switchFold);
/* 57 */       dos.writeInt(this.unhitchChainId);
/*    */     }
/* 59 */     catch (IOException e) {
/*    */       
/* 61 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\vehicle\MCH_PacketVehiclePlayerControl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */