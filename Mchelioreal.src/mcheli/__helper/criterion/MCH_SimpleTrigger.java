/*    */ package mcheli.__helper.criterion;
/*    */ 
/*    */ import com.google.common.collect.Maps;
/*    */ import com.google.gson.JsonDeserializationContext;
/*    */ import com.google.gson.JsonObject;
/*    */ import java.util.Map;
/*    */ import net.minecraft.advancements.ICriterionInstance;
/*    */ import net.minecraft.advancements.ICriterionTrigger;
/*    */ import net.minecraft.advancements.PlayerAdvancements;
/*    */ import net.minecraft.entity.player.EntityPlayerMP;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MCH_SimpleTrigger
/*    */   implements ICriterionTrigger<MCH_SimpleListeners.SimpleInstance>
/*    */ {
/* 21 */   private final Map<PlayerAdvancements, MCH_SimpleListeners> listeners = Maps.newHashMap();
/*    */   
/*    */   private final ResourceLocation id;
/*    */   
/*    */   public MCH_SimpleTrigger(ResourceLocation id) {
/* 26 */     this.id = id;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ResourceLocation func_192163_a() {
/* 32 */     return this.id;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void func_192165_a(PlayerAdvancements playerAdvancementsIn, ICriterionTrigger.Listener<MCH_SimpleListeners.SimpleInstance> listener) {
/* 39 */     MCH_SimpleListeners listeners = this.listeners.get(playerAdvancementsIn);
/*    */     
/* 41 */     if (listeners == null) {
/*    */       
/* 43 */       listeners = new MCH_SimpleListeners(playerAdvancementsIn);
/* 44 */       this.listeners.put(playerAdvancementsIn, listeners);
/*    */     } 
/*    */     
/* 47 */     listeners.add(listener);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void func_192164_b(PlayerAdvancements playerAdvancementsIn, ICriterionTrigger.Listener<MCH_SimpleListeners.SimpleInstance> listener) {
/* 54 */     MCH_SimpleListeners listeners = this.listeners.get(playerAdvancementsIn);
/*    */     
/* 56 */     if (listeners != null) {
/*    */       
/* 58 */       listeners.remove(listener);
/*    */       
/* 60 */       if (listeners.isEmpty())
/*    */       {
/* 62 */         this.listeners.remove(playerAdvancementsIn);
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void func_192167_a(PlayerAdvancements playerAdvancementsIn) {
/* 70 */     this.listeners.remove(playerAdvancementsIn);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public MCH_SimpleListeners.SimpleInstance deserializeInstance(JsonObject json, JsonDeserializationContext context) {
/* 76 */     return new MCH_SimpleListeners.SimpleInstance(this.id);
/*    */   }
/*    */ 
/*    */   
/*    */   public void trigger(EntityPlayerMP player) {
/* 81 */     MCH_SimpleListeners listener = this.listeners.get(player.func_192039_O());
/*    */     
/* 83 */     if (listener != null)
/*    */     {
/* 85 */       listener.trigger();
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\__helper\criterion\MCH_SimpleTrigger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */