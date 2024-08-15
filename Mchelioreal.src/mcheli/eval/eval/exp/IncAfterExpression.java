/*    */ package mcheli.eval.eval.exp;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class IncAfterExpression
/*    */   extends Col1AfterExpression
/*    */ {
/*    */   public IncAfterExpression() {
/* 13 */     setOperator("++");
/*    */   }
/*    */ 
/*    */   
/*    */   protected IncAfterExpression(IncAfterExpression from, ShareExpValue s) {
/* 18 */     super(from, s);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public AbstractExpression dup(ShareExpValue s) {
/* 24 */     return new IncAfterExpression(this, s);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected long operateLong(long val) {
/* 30 */     this.exp.let(val + 1L, this.pos);
/* 31 */     return val;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected double operateDouble(double val) {
/* 37 */     this.exp.let(val + 1.0D, this.pos);
/* 38 */     return val;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Object evalObject() {
/* 44 */     Object val = this.exp.evalObject();
/* 45 */     this.exp.let(this.share.oper.inc(val, 1), this.pos);
/* 46 */     return val;
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\eval\eval\exp\IncAfterExpression.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */