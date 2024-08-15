/*    */ package mcheli.command;
/*    */ 
/*    */ import com.google.common.io.ByteArrayDataInput;
/*    */ import java.io.DataOutputStream;
/*    */ import java.io.IOException;
/*    */ import mcheli.MCH_Packet;
/*    */ import mcheli.wrapper.W_Network;
/*    */ import mcheli.wrapper.W_PacketBase;
/*    */ import net.minecraft.util.text.ITextComponent;
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
/*    */ 
/*    */ public class MCH_PacketTitle
/*    */   extends MCH_Packet
/*    */ {
/* 29 */   public ITextComponent chatComponent = null;
/* 30 */   public int showTime = 1;
/* 31 */   public int position = 0;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMessageID() {
/* 37 */     return 268438272;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readData(ByteArrayDataInput data) {
/*    */     try {
/* 46 */       this.chatComponent = ITextComponent.Serializer.func_150699_a(data.readUTF());
/* 47 */       this.showTime = data.readShort();
/* 48 */       this.position = data.readShort();
/*    */     }
/* 50 */     catch (Exception e) {
/*    */       
/* 52 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writeData(DataOutputStream dos) {
/*    */     try {
/* 62 */       dos.writeUTF(ITextComponent.Serializer.func_150696_a(this.chatComponent));
/* 63 */       dos.writeShort(this.showTime);
/* 64 */       dos.writeShort(this.position);
/*    */     }
/* 66 */     catch (IOException e) {
/*    */       
/* 68 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static void send(ITextComponent chat, int showTime, int pos) {
/* 75 */     MCH_PacketTitle s = new MCH_PacketTitle();
/* 76 */     s.chatComponent = chat;
/* 77 */     s.showTime = showTime;
/* 78 */     s.position = pos;
/* 79 */     W_Network.sendToAllPlayers((W_PacketBase)s);
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\command\MCH_PacketTitle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */