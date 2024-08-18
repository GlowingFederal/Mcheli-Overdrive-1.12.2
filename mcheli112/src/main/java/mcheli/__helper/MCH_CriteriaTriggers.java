package mcheli.__helper;

import mcheli.__helper.criterion.MCH_SimpleTrigger;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.ICriterionTrigger;

public class MCH_CriteriaTriggers {
  public static final MCH_SimpleTrigger PUT_AIRCRAFT = create("put_aircraft");
  
  public static final MCH_SimpleTrigger SUPPLY_AMMO = create("supply_ammo");
  
  public static final MCH_SimpleTrigger SUPPLY_FUEL = create("supply_fuel");
  
  public static final MCH_SimpleTrigger RELIEF_SUPPLIES = create("relief_supplies");
  
  public static final MCH_SimpleTrigger RIDING_VALKYRIES = create("riding_valkyries");
  
  public static final MCH_SimpleTrigger VILLAGER_HURT_BULLET = create("villager_hurt_bullet");
  
  public static void registerTriggers() {
    CriteriaTriggers.func_192118_a((ICriterionTrigger)PUT_AIRCRAFT);
    CriteriaTriggers.func_192118_a((ICriterionTrigger)SUPPLY_AMMO);
    CriteriaTriggers.func_192118_a((ICriterionTrigger)SUPPLY_FUEL);
    CriteriaTriggers.func_192118_a((ICriterionTrigger)RELIEF_SUPPLIES);
    CriteriaTriggers.func_192118_a((ICriterionTrigger)RIDING_VALKYRIES);
    CriteriaTriggers.func_192118_a((ICriterionTrigger)VILLAGER_HURT_BULLET);
  }
  
  private static MCH_SimpleTrigger create(String shortName) {
    return new MCH_SimpleTrigger(MCH_Utils.suffix(shortName));
  }
}
