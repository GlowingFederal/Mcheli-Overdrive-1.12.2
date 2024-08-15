/*     */ package mcheli.wrapper;
/*     */ 
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraftforge.client.event.MouseEvent;
/*     */ import net.minecraftforge.client.event.RenderLivingEvent;
/*     */ import net.minecraftforge.client.event.RenderPlayerEvent;
/*     */ import net.minecraftforge.event.entity.EntityJoinWorldEvent;
/*     */ import net.minecraftforge.event.world.WorldEvent;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class W_ClientEventHook
/*     */ {
/*     */   @SubscribeEvent
/*     */   public void onEvent_MouseEvent(MouseEvent event) {
/*  22 */     mouseEvent(event);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void mouseEvent(MouseEvent event) {}
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onEvent_renderLivingEventSpecialsPre(RenderLivingEvent.Specials.Pre<EntityLivingBase> event) {
/*  32 */     renderLivingEventSpecialsPre(event);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderLivingEventSpecialsPre(RenderLivingEvent.Specials.Pre<EntityLivingBase> event) {}
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onEvent_renderLivingEventSpecialsPost(RenderLivingEvent.Specials.Post<EntityLivingBase> event) {
/*  42 */     renderLivingEventSpecialsPost(event);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderLivingEventSpecialsPost(RenderLivingEvent.Specials.Post<EntityLivingBase> event) {}
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onEvent_renderLivingEventPre(RenderLivingEvent.Pre<EntityLivingBase> event) {
/*  52 */     renderLivingEventPre(event);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderLivingEventPre(RenderLivingEvent.Pre<EntityLivingBase> event) {}
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onEvent_renderLivingEventPost(RenderLivingEvent.Post<EntityLivingBase> event) {
/*  62 */     renderLivingEventPost(event);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderLivingEventPost(RenderLivingEvent.Post<EntityLivingBase> event) {}
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onEvent_renderPlayerPre(RenderPlayerEvent.Pre event) {
/*  72 */     renderPlayerPre(event);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderPlayerPre(RenderPlayerEvent.Pre event) {}
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void Event_renderPlayerPost(RenderPlayerEvent.Post event) {
/*  82 */     renderPlayerPost(event);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderPlayerPost(RenderPlayerEvent.Post event) {}
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onEvent_WorldEventUnload(WorldEvent.Unload event) {
/*  92 */     worldEventUnload(event);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void worldEventUnload(WorldEvent.Unload event) {}
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onEvent_EntityJoinWorldEvent(EntityJoinWorldEvent event) {
/* 102 */     entityJoinWorldEvent(event);
/*     */   }
/*     */   
/*     */   public void entityJoinWorldEvent(EntityJoinWorldEvent event) {}
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\wrapper\W_ClientEventHook.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */