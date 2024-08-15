/*    */ package mcheli.__helper.network;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PooledGuiParameter
/*    */ {
/*    */   private static Entity clientEntity;
/*    */   private static Entity serverEntity;
/*    */   
/*    */   public static void setEntity(EntityPlayer player, @Nullable Entity target) {
/* 20 */     if (player.field_70170_p.field_72995_K) {
/*    */       
/* 22 */       clientEntity = target;
/*    */     }
/*    */     else {
/*    */       
/* 26 */       serverEntity = target;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public static Entity getEntity(EntityPlayer player) {
/* 33 */     return player.field_70170_p.field_72995_K ? clientEntity : serverEntity;
/*    */   }
/*    */ 
/*    */   
/*    */   public static void resetEntity(EntityPlayer player) {
/* 38 */     setEntity(player, null);
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\__helper\network\PooledGuiParameter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */