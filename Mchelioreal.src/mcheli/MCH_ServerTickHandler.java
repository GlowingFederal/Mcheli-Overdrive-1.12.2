/*    */ package mcheli;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Iterator;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ import net.minecraftforge.fml.common.gameevent.TickEvent;
/*    */ import net.minecraftforge.fml.common.network.internal.FMLProxyPacket;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Deprecated
/*    */ public class MCH_ServerTickHandler
/*    */ {
/* 20 */   HashMap<String, Integer> rcvMap = new HashMap<>();
/* 21 */   HashMap<String, Integer> sndMap = new HashMap<>();
/* 22 */   int sndPacketNum = 0;
/* 23 */   int rcvPacketNum = 0;
/*    */   
/*    */   int tick;
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onServerTickEvent(TickEvent.ServerTickEvent event) {
/* 29 */     if (event.phase != TickEvent.Phase.START || event.phase == TickEvent.Phase.END);
/*    */   }
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
/*    */ 
/*    */   
/*    */   private void onServerTickPre() {}
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
/*    */ 
/*    */   
/*    */   public void putMap(HashMap<String, Integer> map, Iterator iterator) {
/* 69 */     while (iterator.hasNext()) {
/*    */       
/* 71 */       Object o = iterator.next();
/* 72 */       String key = o.getClass().getName().toString();
/* 73 */       if (key.startsWith("net.minecraft.")) {
/*    */         
/* 75 */         key = "Minecraft";
/*    */       }
/* 77 */       else if (o instanceof FMLProxyPacket) {
/*    */         
/* 79 */         FMLProxyPacket p = (FMLProxyPacket)o;
/* 80 */         key = p.channel();
/*    */       }
/*    */       else {
/*    */         
/* 84 */         key = "Unknown!";
/*    */       } 
/*    */       
/* 87 */       if (map.containsKey(key)) {
/*    */         
/* 89 */         map.put(key, Integer.valueOf(1 + ((Integer)map.get(key)).intValue()));
/*    */         
/*    */         continue;
/*    */       } 
/* 93 */       map.put(key, Integer.valueOf(1));
/*    */     } 
/*    */   }
/*    */   
/*    */   private void onServerTickPost() {}
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\MCH_ServerTickHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */