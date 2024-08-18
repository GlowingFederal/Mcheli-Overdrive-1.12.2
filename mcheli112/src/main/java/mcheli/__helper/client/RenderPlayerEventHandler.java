package mcheli.__helper.client;

import mcheli.wrapper.W_Lib;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@EventBusSubscriber(modid = "mcheli", value = {Side.CLIENT})
public class RenderPlayerEventHandler {
  private static final Minecraft mc = Minecraft.func_71410_x();
  
  private static Entity cacheViewEntity;
  
  @SubscribeEvent
  static void onRenderPlayerPre(RenderPlayerEvent.Pre event) {
    RenderManager renderManager = event.getRenderer().func_177068_d();
    EntityPlayer player = event.getEntityPlayer();
    Entity entity = player.func_184187_bx();
    if (W_Lib.isClientPlayer((Entity)event.getEntityPlayer()) && renderManager.field_78734_h != player && entity instanceof mcheli.uav.MCH_EntityUavStation) {
      cacheViewEntity = mc.func_175606_aa();
      renderManager.field_78734_h = (Entity)player;
    } 
  }
  
  @SubscribeEvent
  static void onRenderPlayerPost(RenderPlayerEvent.Post event) {
    RenderManager renderManager = event.getRenderer().func_177068_d();
    EntityPlayer player = event.getEntityPlayer();
    Entity entity = player.func_184187_bx();
    if (cacheViewEntity != null) {
      if (W_Lib.isClientPlayer((Entity)event.getEntityPlayer()) && renderManager.field_78734_h != player && entity instanceof mcheli.uav.MCH_EntityUavStation)
        renderManager.field_78734_h = cacheViewEntity; 
      cacheViewEntity = null;
    } 
  }
}
