/*    */ package mcheli.__helper.client;
/*    */ 
/*    */ import mcheli.wrapper.W_Lib;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraftforge.client.event.RenderPlayerEvent;
/*    */ import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @EventBusSubscriber(modid = "mcheli", value = {Side.CLIENT})
/*    */ public class RenderPlayerEventHandler
/*    */ {
/* 23 */   private static final Minecraft mc = Minecraft.func_71410_x();
/*    */   
/*    */   private static Entity cacheViewEntity;
/*    */   
/*    */   @SubscribeEvent
/*    */   static void onRenderPlayerPre(RenderPlayerEvent.Pre event) {
/* 29 */     RenderManager renderManager = event.getRenderer().func_177068_d();
/* 30 */     EntityPlayer player = event.getEntityPlayer();
/* 31 */     Entity entity = player.func_184187_bx();
/*    */     
/* 33 */     if (W_Lib.isClientPlayer((Entity)event.getEntityPlayer()) && renderManager.field_78734_h != player && entity instanceof mcheli.uav.MCH_EntityUavStation) {
/*    */ 
/*    */       
/* 36 */       cacheViewEntity = mc.func_175606_aa();
/* 37 */       renderManager.field_78734_h = (Entity)player;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   @SubscribeEvent
/*    */   static void onRenderPlayerPost(RenderPlayerEvent.Post event) {
/* 44 */     RenderManager renderManager = event.getRenderer().func_177068_d();
/* 45 */     EntityPlayer player = event.getEntityPlayer();
/* 46 */     Entity entity = player.func_184187_bx();
/*    */     
/* 48 */     if (cacheViewEntity != null) {
/*    */       
/* 50 */       if (W_Lib.isClientPlayer((Entity)event.getEntityPlayer()) && renderManager.field_78734_h != player && entity instanceof mcheli.uav.MCH_EntityUavStation)
/*    */       {
/*    */         
/* 53 */         renderManager.field_78734_h = cacheViewEntity;
/*    */       }
/*    */       
/* 56 */       cacheViewEntity = null;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\__helper\client\RenderPlayerEventHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */