/*    */ package mcheli.eval.eval.exp;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SignPlusExpression
/*    */   extends Col1Expression
/*    */ {
/*    */   public SignPlusExpression() {
/* 13 */     setOperator("+");
/*    */   }
/*    */ 
/*    */   
/*    */   protected SignPlusExpression(SignPlusExpression from, ShareExpValue s) {
/* 18 */     super(from, s);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public AbstractExpression dup(ShareExpValue s) {
/* 24 */     return new SignPlusExpression(this, s);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected long operateLong(long val) {
/* 30 */     return val;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected double operateDouble(double val) {
/* 36 */     return val;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Object evalObject() {
/* 42 */     return this.share.oper.signPlus(this.exp.evalObject());
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\eval\eval\exp\SignPlusExpression.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */