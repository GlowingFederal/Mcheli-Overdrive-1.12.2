package mcheli.wrapper;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public abstract class W_TickHandler implements ITickHandler {
  protected Minecraft mc;
  
  public W_TickHandler(Minecraft m) {
    this.mc = m;
  }
  
  public void onPlayerTickPre(EntityPlayer player) {}
  
  public void onPlayerTickPost(EntityPlayer player) {}
  
  public void onRenderTickPre(float partialTicks) {}
  
  public void onRenderTickPost(float partialTicks) {}
  
  public void onTickPre() {}
  
  public void onTickPost() {}
  
  @SubscribeEvent
  public void onPlayerTickEvent(TickEvent.PlayerTickEvent event) {
    if (event.phase == TickEvent.Phase.START)
      onPlayerTickPre(event.player); 
    if (event.phase == TickEvent.Phase.END)
      onPlayerTickPost(event.player); 
  }
  
  @SubscribeEvent
  public void onClientTickEvent(TickEvent.ClientTickEvent event) {
    if (event.phase == TickEvent.Phase.START)
      onTickPre(); 
    if (event.phase == TickEvent.Phase.END)
      onTickPost(); 
  }
  
  @SubscribeEvent
  public void onRenderTickEvent(TickEvent.RenderTickEvent event) {
    if (event.phase == TickEvent.Phase.START)
      onRenderTickPre(event.renderTickTime); 
    if (event.phase == TickEvent.Phase.END)
      onRenderTickPost(event.renderTickTime); 
  }
  
  enum TickType {
    RENDER, CLIENT;
  }
}
