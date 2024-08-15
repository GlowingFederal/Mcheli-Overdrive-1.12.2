/*    */ package mcheli.eval.eval.exp;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AndExpression
/*    */   extends Col2OpeExpression
/*    */ {
/*    */   public AndExpression() {
/* 13 */     setOperator("&&");
/*    */   }
/*    */ 
/*    */   
/*    */   protected AndExpression(AndExpression from, ShareExpValue s) {
/* 18 */     super(from, s);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public AbstractExpression dup(ShareExpValue s) {
/* 24 */     return new AndExpression(this, s);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public long evalLong() {
/* 30 */     long val = this.expl.evalLong();
/* 31 */     if (val == 0L)
/* 32 */       return val; 
/* 33 */     return this.expr.evalLong();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public double evalDouble() {
/* 39 */     double val = this.expl.evalDouble();
/* 40 */     if (val == 0.0D)
/* 41 */       return val; 
/* 42 */     return this.expr.evalDouble();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Object evalObject() {
/* 48 */     Object val = this.expl.evalObject();
/* 49 */     if (!this.share.oper.bool(val))
/*    */     {
/* 51 */       return val;
/*    */     }
/* 53 */     return this.expr.evalObject();
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\eval\eval\exp\AndExpression.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */