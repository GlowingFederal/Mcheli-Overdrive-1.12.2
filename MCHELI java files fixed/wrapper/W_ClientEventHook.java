package mcheli.wrapper;

import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class W_ClientEventHook {
  @SubscribeEvent
  public void onEvent_MouseEvent(MouseEvent event) {
    mouseEvent(event);
  }
  
  public void mouseEvent(MouseEvent event) {}
  
  @SubscribeEvent
  public void onEvent_renderLivingEventSpecialsPre(RenderLivingEvent.Specials.Pre<EntityLivingBase> event) {
    renderLivingEventSpecialsPre(event);
  }
  
  public void renderLivingEventSpecialsPre(RenderLivingEvent.Specials.Pre<EntityLivingBase> event) {}
  
  @SubscribeEvent
  public void onEvent_renderLivingEventSpecialsPost(RenderLivingEvent.Specials.Post<EntityLivingBase> event) {
    renderLivingEventSpecialsPost(event);
  }
  
  public void renderLivingEventSpecialsPost(RenderLivingEvent.Specials.Post<EntityLivingBase> event) {}
  
  @SubscribeEvent
  public void onEvent_renderLivingEventPre(RenderLivingEvent.Pre<EntityLivingBase> event) {
    renderLivingEventPre(event);
  }
  
  public void renderLivingEventPre(RenderLivingEvent.Pre<EntityLivingBase> event) {}
  
  @SubscribeEvent
  public void onEvent_renderLivingEventPost(RenderLivingEvent.Post<EntityLivingBase> event) {
    renderLivingEventPost(event);
  }
  
  public void renderLivingEventPost(RenderLivingEvent.Post<EntityLivingBase> event) {}
  
  @SubscribeEvent
  public void onEvent_renderPlayerPre(RenderPlayerEvent.Pre event) {
    renderPlayerPre(event);
  }
  
  public void renderPlayerPre(RenderPlayerEvent.Pre event) {}
  
  @SubscribeEvent
  public void Event_renderPlayerPost(RenderPlayerEvent.Post event) {
    renderPlayerPost(event);
  }
  
  public void renderPlayerPost(RenderPlayerEvent.Post event) {}
  
  @SubscribeEvent
  public void onEvent_WorldEventUnload(WorldEvent.Unload event) {
    worldEventUnload(event);
  }
  
  public void worldEventUnload(WorldEvent.Unload event) {}
  
  @SubscribeEvent
  public void onEvent_EntityJoinWorldEvent(EntityJoinWorldEvent event) {
    entityJoinWorldEvent(event);
  }
  
  public void entityJoinWorldEvent(EntityJoinWorldEvent event) {}
}
