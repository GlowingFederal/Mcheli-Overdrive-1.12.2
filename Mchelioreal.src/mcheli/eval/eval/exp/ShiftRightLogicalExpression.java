/*    */ package mcheli.eval.eval.exp;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ShiftRightLogicalExpression
/*    */   extends Col2Expression
/*    */ {
/*    */   public ShiftRightLogicalExpression() {
/* 13 */     setOperator(">>>");
/*    */   }
/*    */ 
/*    */   
/*    */   protected ShiftRightLogicalExpression(ShiftRightLogicalExpression from, ShareExpValue s) {
/* 18 */     super(from, s);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public AbstractExpression dup(ShareExpValue s) {
/* 24 */     return new ShiftRightLogicalExpression(this, s);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected long operateLong(long vl, long vr) {
/* 30 */     return vl >>> (int)vr;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected double operateDouble(double vl, double vr) {
/* 36 */     if (vl < 0.0D)
/* 37 */       vl = -vl; 
/* 38 */     return vl / Math.pow(2.0D, vr);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected Object operateObject(Object vl, Object vr) {
/* 44 */     return this.share.oper.shiftRightLogical(vl, vr);
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\eval\eval\exp\ShiftRightLogicalExpression.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */