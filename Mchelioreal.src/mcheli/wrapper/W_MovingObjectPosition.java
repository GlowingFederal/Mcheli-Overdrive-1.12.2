/*    */ package mcheli.wrapper;
/*    */ 
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.util.math.RayTraceResult;
/*    */ import net.minecraft.util.math.Vec3d;
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
/*    */ 
/*    */ public class W_MovingObjectPosition
/*    */ {
/*    */   public static boolean isHitTypeEntity(RayTraceResult m) {
/* 21 */     if (m == null)
/*    */     {
/* 23 */       return false;
/*    */     }
/*    */     
/* 26 */     return (m.field_72313_a == RayTraceResult.Type.ENTITY);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static boolean isHitTypeTile(RayTraceResult m) {
/* 32 */     if (m == null)
/*    */     {
/* 34 */       return false;
/*    */     }
/*    */     
/* 37 */     return (m.field_72313_a == RayTraceResult.Type.BLOCK);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static RayTraceResult newMOP(int p1, int p2, int p3, int p4, Vec3d p5, boolean p6) {
/* 44 */     return new RayTraceResult(p5, EnumFacing.func_82600_a(p4), new BlockPos(p1, p2, p3));
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\wrapper\W_MovingObjectPosition.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */