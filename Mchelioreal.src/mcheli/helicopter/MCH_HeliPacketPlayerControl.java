/*    */ package mcheli.helicopter;
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
/*    */ public class MCH_HeliPacketPlayerControl
/*    */   extends MCH_PacketPlayerControlBase
/*    */ {
/* 23 */   public byte switchFold = -1;
/* 24 */   public int unhitchChainId = -1;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMessageID() {
/* 30 */     return 536879120;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void readData(ByteArrayDataInput data) {
/* 36 */     super.readData(data);
/*    */     
/*    */     try {
/* 39 */       this.switchFold = data.readByte();
/* 40 */       this.unhitchChainId = data.readInt();
/*    */     }
/* 42 */     catch (Exception e) {
/*    */       
/* 44 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void writeData(DataOutputStream dos) {
/* 51 */     super.writeData(dos);
/*    */     
/*    */     try {
/* 54 */       dos.writeByte(this.switchFold);
/* 55 */       dos.writeInt(this.unhitchChainId);
/*    */     }
/* 57 */     catch (IOException e) {
/*    */       
/* 59 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\helicopter\MCH_HeliPacketPlayerControl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */