/*    */ package mcheli.wrapper;
/*    */ 
/*    */ import mcheli.__helper.network.MCH_WrapPacketData;
/*    */ import mcheli.__helper.network.MCH_WrapPacketHandler;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraftforge.fml.common.network.IGuiHandler;
/*    */ import net.minecraftforge.fml.common.network.NetworkRegistry;
/*    */ import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class W_NetworkRegistry
/*    */ {
/*    */   public static W_PacketHandler packetHandler;
/*    */   
/*    */   public static void registerChannel(W_PacketHandler handler, String name) {
/* 22 */     packetHandler = handler;
/*    */ 
/*    */     
/* 25 */     W_Network.INSTANCE.registerMessage((IMessageHandler)new MCH_WrapPacketHandler(), MCH_WrapPacketData.class, 0, Side.SERVER);
/* 26 */     W_Network.INSTANCE.registerMessage((IMessageHandler)new MCH_WrapPacketHandler(), MCH_WrapPacketData.class, 0, Side.CLIENT);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static void handlePacket(EntityPlayer player, byte[] data) {}
/*    */ 
/*    */   
/*    */   public static void registerGuiHandler(Object mod, IGuiHandler handler) {
/* 35 */     NetworkRegistry.INSTANCE.registerGuiHandler(mod, handler);
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\wrapper\W_NetworkRegistry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */