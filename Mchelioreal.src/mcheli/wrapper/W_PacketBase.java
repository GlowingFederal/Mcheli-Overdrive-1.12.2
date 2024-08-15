/*    */ package mcheli.wrapper;
/*    */ 
/*    */ import com.google.common.io.ByteArrayDataInput;
/*    */ import com.google.common.io.ByteStreams;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class W_PacketBase
/*    */   implements IMessage
/*    */ {
/*    */   ByteArrayDataInput data;
/*    */   
/*    */   public byte[] createData() {
/* 21 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void fromBytes(ByteBuf buf) {
/* 27 */     byte[] dst = new byte[(buf.array()).length - 1];
/* 28 */     buf.getBytes(0, dst);
/* 29 */     this.data = ByteStreams.newDataInput(dst);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void toBytes(ByteBuf buf) {
/* 35 */     buf.writeBytes(createData());
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\wrapper\W_PacketBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */