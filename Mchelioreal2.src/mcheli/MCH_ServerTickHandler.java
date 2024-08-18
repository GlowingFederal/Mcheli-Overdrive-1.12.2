package mcheli;

import java.util.HashMap;
import java.util.Iterator;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.network.internal.FMLProxyPacket;

@Deprecated
public class MCH_ServerTickHandler {
  HashMap<String, Integer> rcvMap = new HashMap<>();
  
  HashMap<String, Integer> sndMap = new HashMap<>();
  
  int sndPacketNum = 0;
  
  int rcvPacketNum = 0;
  
  int tick;
  
  @SubscribeEvent
  public void onServerTickEvent(TickEvent.ServerTickEvent event) {
    if (event.phase != TickEvent.Phase.START || event.phase == TickEvent.Phase.END);
  }
  
  private void onServerTickPre() {}
  
  public void putMap(HashMap<String, Integer> map, Iterator iterator) {
    while (iterator.hasNext()) {
      Object o = iterator.next();
      String key = o.getClass().getName().toString();
      if (key.startsWith("net.minecraft.")) {
        key = "Minecraft";
      } else if (o instanceof FMLProxyPacket) {
        FMLProxyPacket p = (FMLProxyPacket)o;
        key = p.channel();
      } else {
        key = "Unknown!";
      } 
      if (map.containsKey(key)) {
        map.put(key, Integer.valueOf(1 + ((Integer)map.get(key)).intValue()));
        continue;
      } 
      map.put(key, Integer.valueOf(1));
    } 
  }
  
  private void onServerTickPost() {}
}
