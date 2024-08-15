/*    */ package mcheli.__helper;
/*    */ 
/*    */ import mcheli.__helper.criterion.MCH_SimpleTrigger;
/*    */ import net.minecraft.advancements.CriteriaTriggers;
/*    */ import net.minecraft.advancements.ICriterionTrigger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MCH_CriteriaTriggers
/*    */ {
/* 13 */   public static final MCH_SimpleTrigger PUT_AIRCRAFT = create("put_aircraft");
/* 14 */   public static final MCH_SimpleTrigger SUPPLY_AMMO = create("supply_ammo");
/* 15 */   public static final MCH_SimpleTrigger SUPPLY_FUEL = create("supply_fuel");
/* 16 */   public static final MCH_SimpleTrigger RELIEF_SUPPLIES = create("relief_supplies");
/* 17 */   public static final MCH_SimpleTrigger RIDING_VALKYRIES = create("riding_valkyries");
/* 18 */   public static final MCH_SimpleTrigger VILLAGER_HURT_BULLET = create("villager_hurt_bullet");
/*    */ 
/*    */   
/*    */   public static void registerTriggers() {
/* 22 */     CriteriaTriggers.func_192118_a((ICriterionTrigger)PUT_AIRCRAFT);
/* 23 */     CriteriaTriggers.func_192118_a((ICriterionTrigger)SUPPLY_AMMO);
/* 24 */     CriteriaTriggers.func_192118_a((ICriterionTrigger)SUPPLY_FUEL);
/* 25 */     CriteriaTriggers.func_192118_a((ICriterionTrigger)RELIEF_SUPPLIES);
/* 26 */     CriteriaTriggers.func_192118_a((ICriterionTrigger)RIDING_VALKYRIES);
/* 27 */     CriteriaTriggers.func_192118_a((ICriterionTrigger)VILLAGER_HURT_BULLET);
/*    */   }
/*    */ 
/*    */   
/*    */   private static MCH_SimpleTrigger create(String shortName) {
/* 32 */     return new MCH_SimpleTrigger(MCH_Utils.suffix(shortName));
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\__helper\MCH_CriteriaTriggers.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */