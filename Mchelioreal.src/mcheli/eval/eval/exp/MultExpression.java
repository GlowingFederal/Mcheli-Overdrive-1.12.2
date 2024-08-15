/*    */ package mcheli.eval.eval.exp;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MultExpression
/*    */   extends Col2Expression
/*    */ {
/*    */   public MultExpression() {
/* 13 */     setOperator("*");
/*    */   }
/*    */ 
/*    */   
/*    */   protected MultExpression(MultExpression from, ShareExpValue s) {
/* 18 */     super(from, s);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public AbstractExpression dup(ShareExpValue s) {
/* 24 */     return new MultExpression(this, s);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected long operateLong(long vl, long vr) {
/* 30 */     return vl * vr;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected double operateDouble(double vl, double vr) {
/* 36 */     return vl * vr;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected Object operateObject(Object vl, Object vr) {
/* 42 */     return this.share.oper.mult(vl, vr);
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\eval\eval\exp\MultExpression.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */