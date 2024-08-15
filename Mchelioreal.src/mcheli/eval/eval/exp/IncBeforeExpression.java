/*    */ package mcheli.eval.eval.exp;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class IncBeforeExpression
/*    */   extends Col1Expression
/*    */ {
/*    */   public IncBeforeExpression() {
/* 13 */     setOperator("++");
/*    */   }
/*    */ 
/*    */   
/*    */   protected IncBeforeExpression(IncBeforeExpression from, ShareExpValue s) {
/* 18 */     super(from, s);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public AbstractExpression dup(ShareExpValue s) {
/* 24 */     return new IncBeforeExpression(this, s);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected long operateLong(long val) {
/* 30 */     val++;
/* 31 */     this.exp.let(val, this.pos);
/* 32 */     return val;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected double operateDouble(double val) {
/* 38 */     val++;
/* 39 */     this.exp.let(val, this.pos);
/* 40 */     return val;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Object evalObject() {
/* 46 */     Object val = this.exp.evalObject();
/* 47 */     val = this.share.oper.inc(val, 1);
/* 48 */     this.exp.let(val, this.pos);
/* 49 */     return val;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected AbstractExpression replace() {
/* 55 */     this.exp = this.exp.replaceVar();
/* 56 */     return this.share.repl.replaceVar1(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\eval\eval\exp\IncBeforeExpression.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */