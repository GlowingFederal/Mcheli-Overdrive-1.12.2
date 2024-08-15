/*    */ package mcheli.plane;
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
/*    */ public class MCP_PlanePacketPlayerControl
/*    */   extends MCH_PacketPlayerControlBase
/*    */ {
/* 22 */   public byte switchVtol = -1;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMessageID() {
/* 28 */     return 536903696;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void readData(ByteArrayDataInput data) {
/* 34 */     super.readData(data);
/*    */     
/*    */     try {
/* 37 */       this.switchVtol = data.readByte();
/*    */     }
/* 39 */     catch (Exception e) {
/*    */       
/* 41 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void writeData(DataOutputStream dos) {
/* 48 */     super.writeData(dos);
/*    */     
/*    */     try {
/* 51 */       dos.writeByte(this.switchVtol);
/*    */     }
/* 53 */     catch (IOException e) {
/*    */       
/* 55 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\plane\MCP_PlanePacketPlayerControl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */