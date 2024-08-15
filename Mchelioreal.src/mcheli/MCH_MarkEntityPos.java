/*    */ package mcheli;
/*    */ 
/*    */ import java.nio.FloatBuffer;
/*    */ import javax.annotation.Nullable;
/*    */ import mcheli.__helper.entity.ITargetMarkerObject;
/*    */ import org.lwjgl.BufferUtils;
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
/*    */ 
/*    */ 
/*    */ public class MCH_MarkEntityPos
/*    */ {
/*    */   public FloatBuffer pos;
/*    */   public int type;
/*    */   private ITargetMarkerObject target;
/*    */   
/*    */   public MCH_MarkEntityPos(int type, ITargetMarkerObject target) {
/* 27 */     this.type = type;
/* 28 */     this.pos = BufferUtils.createFloatBuffer(3);
/*    */     
/* 30 */     this.target = target;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public MCH_MarkEntityPos(int type) {
/* 36 */     this(type, (ITargetMarkerObject)null);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public ITargetMarkerObject getTarget() {
/* 42 */     return this.target;
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\MCH_MarkEntityPos.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */