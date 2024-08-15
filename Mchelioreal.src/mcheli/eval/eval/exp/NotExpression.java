/*    */ package mcheli.eval.eval.exp;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NotExpression
/*    */   extends Col1Expression
/*    */ {
/*    */   public NotExpression() {
/* 13 */     setOperator("!");
/*    */   }
/*    */ 
/*    */   
/*    */   protected NotExpression(NotExpression from, ShareExpValue s) {
/* 18 */     super(from, s);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public AbstractExpression dup(ShareExpValue s) {
/* 24 */     return new NotExpression(this, s);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected long operateLong(long val) {
/* 30 */     return (val == 0L) ? 1L : 0L;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected double operateDouble(double val) {
/* 36 */     return (val == 0.0D) ? 1.0D : 0.0D;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Object evalObject() {
/* 42 */     return this.share.oper.not(this.exp.evalObject());
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\eval\eval\exp\NotExpression.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */