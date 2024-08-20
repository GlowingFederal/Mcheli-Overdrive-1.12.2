package mcheli;

import java.util.HashMap;
import net.minecraft.entity.Entity;

public class MCH_DamageFactor {
  private HashMap<Class<? extends Entity>, Float> map = new HashMap<>();
  
  public void clear() {
    this.map.clear();
  }
  
  public void add(Class<? extends Entity> c, float value) {
    this.map.put(c, Float.valueOf(value));
  }
  
  public float getDamageFactor(Class<? extends Entity> c) {
    if (this.map.containsKey(c))
      return ((Float)this.map.get(c)).floatValue(); 
    return 1.0F;
  }
  
  public float getDamageFactor(Entity e) {
    return (e != null) ? getDamageFactor((Class)e.getClass()) : 1.0F;
  }
}
