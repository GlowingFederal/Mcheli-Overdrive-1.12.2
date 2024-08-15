/*    */ package mcheli.wrapper;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import mcheli.MCH_MOD;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class W_Lib
/*    */ {
/*    */   public static boolean isEntityLivingBase(Entity entity) {
/* 20 */     return entity instanceof EntityLivingBase;
/*    */   }
/*    */ 
/*    */   
/*    */   public static EntityLivingBase castEntityLivingBase(Object entity) {
/* 25 */     return (EntityLivingBase)entity;
/*    */   }
/*    */ 
/*    */   
/*    */   public static Class<EntityLivingBase> getEntityLivingBaseClass() {
/* 30 */     return EntityLivingBase.class;
/*    */   }
/*    */ 
/*    */   
/*    */   public static double getEntityMoveDist(@Nullable Entity entity) {
/* 35 */     if (entity == null)
/*    */     {
/* 37 */       return 0.0D;
/*    */     }
/*    */     
/* 40 */     return (entity instanceof EntityLivingBase) ? ((EntityLivingBase)entity).field_191988_bg : 0.0D;
/*    */   }
/*    */ 
/*    */   
/*    */   public static boolean isClientPlayer(@Nullable Entity entity) {
/* 45 */     if (entity instanceof net.minecraft.entity.player.EntityPlayer)
/*    */     {
/* 47 */       if (entity.field_70170_p.field_72995_K)
/*    */       {
/* 49 */         return W_Entity.isEqual(MCH_MOD.proxy.getClientPlayer(), entity);
/*    */       }
/*    */     }
/* 52 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public static boolean isFirstPerson() {
/* 57 */     return MCH_MOD.proxy.isFirstPerson();
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\wrapper\W_Lib.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */