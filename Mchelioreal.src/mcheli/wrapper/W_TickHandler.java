/*    */ package mcheli.wrapper;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ import net.minecraftforge.fml.common.gameevent.TickEvent;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class W_TickHandler
/*    */   implements ITickHandler
/*    */ {
/*    */   protected Minecraft mc;
/*    */   
/*    */   public W_TickHandler(Minecraft m) {
/* 20 */     this.mc = m;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onPlayerTickPre(EntityPlayer player) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void onPlayerTickPost(EntityPlayer player) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void onRenderTickPre(float partialTicks) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void onRenderTickPost(float partialTicks) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void onTickPre() {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void onTickPost() {}
/*    */ 
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onPlayerTickEvent(TickEvent.PlayerTickEvent event) {
/* 50 */     if (event.phase == TickEvent.Phase.START)
/*    */     {
/* 52 */       onPlayerTickPre(event.player);
/*    */     }
/* 54 */     if (event.phase == TickEvent.Phase.END)
/*    */     {
/* 56 */       onPlayerTickPost(event.player);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onClientTickEvent(TickEvent.ClientTickEvent event) {
/* 63 */     if (event.phase == TickEvent.Phase.START)
/*    */     {
/* 65 */       onTickPre();
/*    */     }
/* 67 */     if (event.phase == TickEvent.Phase.END)
/*    */     {
/* 69 */       onTickPost();
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onRenderTickEvent(TickEvent.RenderTickEvent event) {
/* 76 */     if (event.phase == TickEvent.Phase.START)
/*    */     {
/* 78 */       onRenderTickPre(event.renderTickTime);
/*    */     }
/* 80 */     if (event.phase == TickEvent.Phase.END)
/*    */     {
/* 82 */       onRenderTickPost(event.renderTickTime);
/*    */     }
/*    */   }
/*    */   
/*    */   enum TickType
/*    */   {
/* 88 */     RENDER, CLIENT;
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\wrapper\W_TickHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */