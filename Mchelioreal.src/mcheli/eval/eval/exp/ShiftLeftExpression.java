/*    */ package mcheli.eval.eval.exp;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ShiftLeftExpression
/*    */   extends Col2Expression
/*    */ {
/*    */   public ShiftLeftExpression() {
/* 13 */     setOperator("<<");
/*    */   }
/*    */ 
/*    */   
/*    */   protected ShiftLeftExpression(ShiftLeftExpression from, ShareExpValue s) {
/* 18 */     super(from, s);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public AbstractExpression dup(ShareExpValue s) {
/* 24 */     return new ShiftLeftExpression(this, s);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected long operateLong(long vl, long vr) {
/* 30 */     return vl << (int)vr;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected double operateDouble(double vl, double vr) {
/* 36 */     return vl * Math.pow(2.0D, vr);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected Object operateObject(Object vl, Object vr) {
/* 42 */     return this.share.oper.shiftLeft(vl, vr);
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\eval\eval\exp\ShiftLeftExpression.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */