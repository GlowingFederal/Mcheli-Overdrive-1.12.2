/*    */ package mcheli.__helper.client.model;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PooledModelParameters
/*    */ {
/*    */   private static EntityLivingBase heldItemUser;
/* 17 */   private static ItemStack rendererTargetItem = ItemStack.field_190927_a;
/* 18 */   private static ItemCameraTransforms.TransformType transformType = ItemCameraTransforms.TransformType.NONE;
/*    */ 
/*    */   
/*    */   static void setItemAndUser(ItemStack itemstack, @Nullable EntityLivingBase user) {
/* 22 */     rendererTargetItem = itemstack;
/* 23 */     heldItemUser = user;
/*    */   }
/*    */ 
/*    */   
/*    */   static void setTransformType(ItemCameraTransforms.TransformType type) {
/* 28 */     transformType = type;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public static EntityLivingBase getEntity() {
/* 34 */     return heldItemUser;
/*    */   }
/*    */ 
/*    */   
/*    */   public static ItemStack getTargetRendererStack() {
/* 39 */     return rendererTargetItem;
/*    */   }
/*    */ 
/*    */   
/*    */   public static ItemCameraTransforms.TransformType getTransformType() {
/* 44 */     return transformType;
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\__helper\client\model\PooledModelParameters.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */