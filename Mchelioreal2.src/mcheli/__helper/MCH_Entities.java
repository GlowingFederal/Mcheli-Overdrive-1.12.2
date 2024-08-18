package mcheli.__helper;

import net.minecraftforge.fml.common.registry.EntityRegistry;

public class MCH_Entities {
  public static <T extends net.minecraft.entity.Entity> void register(Class<T> entityClass, String entityName, int id, Object mod, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates) {
    EntityRegistry.registerModEntity(MCH_Utils.suffix(entityName), entityClass, entityName, id, mod, trackingRange, updateFrequency, sendsVelocityUpdates);
  }
}
