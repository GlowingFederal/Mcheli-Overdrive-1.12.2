/*    */ package mcheli.__helper.entity;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface ITargetMarkerObject
/*    */ {
/*    */   double getX();
/*    */   
/*    */   double getY();
/*    */   
/*    */   double getZ();
/*    */   
/*    */   @Nullable
/*    */   default Entity getEntity() {
/* 26 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   static ITargetMarkerObject fromEntity(Entity target) {
/* 31 */     return new EntityWrapper(target);
/*    */   }
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public static class EntityWrapper
/*    */     implements ITargetMarkerObject
/*    */   {
/*    */     private final Entity target;
/*    */     
/*    */     public EntityWrapper(Entity entity) {
/* 41 */       this.target = entity;
/*    */     }
/*    */ 
/*    */ 
/*    */     
/*    */     public double getX() {
/* 47 */       return this.target.field_70165_t;
/*    */     }
/*    */ 
/*    */ 
/*    */     
/*    */     public double getY() {
/* 53 */       return this.target.field_70163_u;
/*    */     }
/*    */ 
/*    */ 
/*    */     
/*    */     public double getZ() {
/* 59 */       return this.target.field_70161_v;
/*    */     }
/*    */ 
/*    */ 
/*    */     
/*    */     public Entity getEntity() {
/* 65 */       return this.target;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\__helper\entity\ITargetMarkerObject.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */