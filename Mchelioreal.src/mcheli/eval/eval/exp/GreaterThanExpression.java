/*    */ package mcheli.eval.eval.exp;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GreaterThanExpression
/*    */   extends Col2Expression
/*    */ {
/*    */   public GreaterThanExpression() {
/* 13 */     setOperator(">");
/*    */   }
/*    */ 
/*    */   
/*    */   protected GreaterThanExpression(GreaterThanExpression from, ShareExpValue s) {
/* 18 */     super(from, s);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public AbstractExpression dup(ShareExpValue s) {
/* 24 */     return new GreaterThanExpression(this, s);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected long operateLong(long vl, long vr) {
/* 30 */     return (vl > vr) ? 1L : 0L;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected double operateDouble(double vl, double vr) {
/* 36 */     return (vl > vr) ? 1.0D : 0.0D;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected Object operateObject(Object vl, Object vr) {
/* 42 */     return this.share.oper.greaterThan(vl, vr);
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\eval\eval\exp\GreaterThanExpression.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */