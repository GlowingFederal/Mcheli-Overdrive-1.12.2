/*    */ package mcheli.aircraft;
/*    */ 
/*    */ import com.google.common.io.ByteArrayDataInput;
/*    */ import java.io.DataOutputStream;
/*    */ import java.io.IOException;
/*    */ import mcheli.MCH_Config;
/*    */ import mcheli.MCH_Packet;
/*    */ import mcheli.wrapper.W_EntityRenderer;
/*    */ import mcheli.wrapper.W_Network;
/*    */ import mcheli.wrapper.W_PacketBase;
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
/*    */ public class MCH_PacketNotifyClientSetting
/*    */   extends MCH_Packet
/*    */ {
/*    */   public boolean dismountAll = true;
/*    */   public boolean heliAutoThrottleDown;
/*    */   public boolean planeAutoThrottleDown;
/*    */   public boolean tankAutoThrottleDown;
/*    */   public boolean shaderSupport = false;
/*    */   
/*    */   public int getMessageID() {
/* 36 */     return 536875072;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readData(ByteArrayDataInput di) {
/*    */     try {
/* 44 */       byte data = 0;
/* 45 */       data = di.readByte();
/* 46 */       this.dismountAll = getBit(data, 0);
/* 47 */       this.heliAutoThrottleDown = getBit(data, 1);
/* 48 */       this.planeAutoThrottleDown = getBit(data, 2);
/* 49 */       this.tankAutoThrottleDown = getBit(data, 3);
/* 50 */       this.shaderSupport = getBit(data, 4);
/*    */     }
/* 52 */     catch (Exception e) {
/*    */       
/* 54 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writeData(DataOutputStream dos) {
/*    */     try {
/* 63 */       byte data = 0;
/* 64 */       data = setBit(data, 0, this.dismountAll);
/* 65 */       data = setBit(data, 1, this.heliAutoThrottleDown);
/* 66 */       data = setBit(data, 2, this.planeAutoThrottleDown);
/* 67 */       data = setBit(data, 3, this.tankAutoThrottleDown);
/* 68 */       data = setBit(data, 4, this.shaderSupport);
/* 69 */       dos.writeByte(data);
/*    */     }
/* 71 */     catch (IOException e) {
/*    */       
/* 73 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public static void send() {
/* 79 */     MCH_PacketNotifyClientSetting s = new MCH_PacketNotifyClientSetting();
/*    */     
/* 81 */     s.dismountAll = MCH_Config.DismountAll.prmBool;
/* 82 */     s.heliAutoThrottleDown = MCH_Config.AutoThrottleDownHeli.prmBool;
/* 83 */     s.planeAutoThrottleDown = MCH_Config.AutoThrottleDownPlane.prmBool;
/* 84 */     s.tankAutoThrottleDown = MCH_Config.AutoThrottleDownTank.prmBool;
/* 85 */     s.shaderSupport = W_EntityRenderer.isShaderSupport();
/*    */     
/* 87 */     W_Network.sendToServer((W_PacketBase)s);
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\aircraft\MCH_PacketNotifyClientSetting.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */