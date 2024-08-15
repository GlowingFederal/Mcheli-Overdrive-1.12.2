/*    */ package mcheli.__helper.network;
/*    */ 
/*    */ import com.google.common.io.ByteArrayDataInput;
/*    */ import mcheli.MCH_Lib;
/*    */ import mcheli.wrapper.W_NetworkRegistry;
/*    */ import mcheli.wrapper.W_PacketDummy;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
/*    */ import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
/*    */ import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MCH_WrapPacketHandler
/*    */   implements IMessageHandler<MCH_WrapPacketData, W_PacketDummy>
/*    */ {
/*    */   public W_PacketDummy onMessage(MCH_WrapPacketData message, MessageContext ctx) {
/*    */     try {
/* 25 */       ByteArrayDataInput data = message.createData();
/*    */       
/* 27 */       if (ctx.side.isClient())
/*    */       {
/* 29 */         if (MCH_Lib.getClientPlayer() != null)
/*    */         {
/* 31 */           W_NetworkRegistry.packetHandler.onPacket(data, (EntityPlayer)MCH_Lib.getClientPlayer(), ctx);
/*    */         }
/*    */       }
/*    */       else
/*    */       {
/* 36 */         W_NetworkRegistry.packetHandler.onPacket(data, (EntityPlayer)(ctx.getServerHandler()).field_147369_b, ctx);
/*    */       }
/*    */     
/* 39 */     } catch (Exception e) {
/*    */       
/* 41 */       e.printStackTrace();
/*    */     } 
/*    */     
/* 44 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\__helper\network\MCH_WrapPacketHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */