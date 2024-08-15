/*    */ package mcheli.wrapper;
/*    */ 
/*    */ import mcheli.__helper.network.MCH_WrapPacketData;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.entity.player.EntityPlayerMP;
/*    */ import net.minecraftforge.fml.common.network.NetworkRegistry;
/*    */ import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
/*    */ import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class W_Network
/*    */ {
/* 20 */   static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel("MCHeli_CH");
/*    */ 
/*    */ 
/*    */   
/*    */   public static void sendToServer(W_PacketBase pkt) {
/* 25 */     INSTANCE.sendToServer((IMessage)new MCH_WrapPacketData(pkt));
/*    */   }
/*    */ 
/*    */   
/*    */   public static void sendToPlayer(W_PacketBase pkt, EntityPlayer player) {
/* 30 */     if (player instanceof EntityPlayerMP)
/*    */     {
/*    */       
/* 33 */       INSTANCE.sendTo((IMessage)new MCH_WrapPacketData(pkt), (EntityPlayerMP)player);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public static void sendToAllAround(W_PacketBase pkt, Entity sender, double renge) {
/* 39 */     NetworkRegistry.TargetPoint t = new NetworkRegistry.TargetPoint(sender.field_71093_bK, sender.field_70165_t, sender.field_70163_u, sender.field_70161_v, renge);
/*    */ 
/*    */     
/* 42 */     INSTANCE.sendToAllAround((IMessage)new MCH_WrapPacketData(pkt), t);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static void sendToAllPlayers(W_PacketBase pkt) {
/* 48 */     INSTANCE.sendToAll((IMessage)new MCH_WrapPacketData(pkt));
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\wrapper\W_Network.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */