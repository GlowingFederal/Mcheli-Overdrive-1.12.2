package mcheli.hud;

import javax.annotation.Nullable;
import mcheli.__helper.info.ContentRegistries;

public class MCH_HudManager {
  @Nullable
  public static MCH_Hud get(String name) {
    return (MCH_Hud)ContentRegistries.hud().get(name);
  }
  
  public static boolean contains(String name) {
    return ContentRegistries.hud().contains(name);
  }
  
  public static int size() {
    return ContentRegistries.hud().size();
  }
}
