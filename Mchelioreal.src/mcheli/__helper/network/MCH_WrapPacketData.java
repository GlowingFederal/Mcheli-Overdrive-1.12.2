/*    */ package mcheli.__helper.network;
/*    */ 
/*    */ import com.google.common.io.ByteArrayDataInput;
/*    */ import com.google.common.io.ByteStreams;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import mcheli.wrapper.W_PacketBase;
/*    */ import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MCH_WrapPacketData
/*    */   implements IMessage
/*    */ {
/*    */   private byte[] data;
/*    */   
/*    */   public MCH_WrapPacketData() {
/* 22 */     this.data = new byte[0];
/*    */   }
/*    */ 
/*    */   
/*    */   public MCH_WrapPacketData(W_PacketBase packet) {
/* 27 */     this.data = packet.createData();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void fromBytes(ByteBuf buf) {
/* 33 */     this.data = new byte[buf.capacity()];
/* 34 */     buf.getBytes(0, this.data);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void toBytes(ByteBuf buf) {
/* 40 */     buf.writeBytes(this.data);
/*    */   }
/*    */ 
/*    */   
/*    */   public ByteArrayDataInput createData() {
/* 45 */     return ByteStreams.newDataInput(this.data);
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\__helper\network\MCH_WrapPacketData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */