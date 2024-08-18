package mcheli.wrapper;

import javax.annotation.Nullable;
import mcheli.MCH_MOD;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

public class W_Lib {
  public static boolean isEntityLivingBase(Entity entity) {
    return entity instanceof EntityLivingBase;
  }
  
  public static EntityLivingBase castEntityLivingBase(Object entity) {
    return (EntityLivingBase)entity;
  }
  
  public static Class<EntityLivingBase> getEntityLivingBaseClass() {
    return EntityLivingBase.class;
  }
  
  public static double getEntityMoveDist(@Nullable Entity entity) {
    if (entity == null)
      return 0.0D; 
    return (entity instanceof EntityLivingBase) ? ((EntityLivingBase)entity).field_191988_bg : 0.0D;
  }
  
  public static boolean isClientPlayer(@Nullable Entity entity) {
    if (entity instanceof net.minecraft.entity.player.EntityPlayer)
      if (entity.field_70170_p.field_72995_K)
        return W_Entity.isEqual(MCH_MOD.proxy.getClientPlayer(), entity);  
    return false;
  }
  
  public static boolean isFirstPerson() {
    return MCH_MOD.proxy.isFirstPerson();
  }
}
