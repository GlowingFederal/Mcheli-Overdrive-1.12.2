/*    */ package mcheli.__helper.criterion;
/*    */ 
/*    */ import com.google.common.collect.Sets;
/*    */ import java.util.Set;
/*    */ import net.minecraft.advancements.ICriterionTrigger;
/*    */ import net.minecraft.advancements.PlayerAdvancements;
/*    */ import net.minecraft.advancements.critereon.AbstractCriterionInstance;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MCH_SimpleListeners
/*    */ {
/* 19 */   private final Set<ICriterionTrigger.Listener<SimpleInstance>> listeners = Sets.newHashSet();
/*    */   
/*    */   private final PlayerAdvancements playerAdvancements;
/*    */   
/*    */   public MCH_SimpleListeners(PlayerAdvancements playerAdvancements) {
/* 24 */     this.playerAdvancements = playerAdvancements;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isEmpty() {
/* 29 */     return this.listeners.isEmpty();
/*    */   }
/*    */ 
/*    */   
/*    */   public void add(ICriterionTrigger.Listener<SimpleInstance> listener) {
/* 34 */     this.listeners.add(listener);
/*    */   }
/*    */ 
/*    */   
/*    */   public void remove(ICriterionTrigger.Listener<SimpleInstance> listener) {
/* 39 */     this.listeners.remove(listener);
/*    */   }
/*    */ 
/*    */   
/*    */   public void trigger() {
/* 44 */     for (ICriterionTrigger.Listener<SimpleInstance> listener : this.listeners)
/*    */     {
/* 46 */       listener.func_192159_a(this.playerAdvancements);
/*    */     }
/*    */   }
/*    */   
/*    */   static class SimpleInstance
/*    */     extends AbstractCriterionInstance
/*    */   {
/*    */     public SimpleInstance(ResourceLocation criterionIn) {
/* 54 */       super(criterionIn);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\__helper\criterion\MCH_SimpleListeners.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */