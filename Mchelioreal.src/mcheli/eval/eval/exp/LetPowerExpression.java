/*    */ package mcheli.eval.eval.exp;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LetPowerExpression
/*    */   extends PowerExpression
/*    */ {
/*    */   public LetPowerExpression() {
/* 13 */     setOperator("**=");
/*    */   }
/*    */ 
/*    */   
/*    */   protected LetPowerExpression(LetPowerExpression from, ShareExpValue s) {
/* 18 */     super(from, s);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public AbstractExpression dup(ShareExpValue s) {
/* 24 */     return new LetPowerExpression(this, s);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public long evalLong() {
/* 30 */     long val = super.evalLong();
/* 31 */     this.expl.let(val, this.pos);
/* 32 */     return val;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public double evalDouble() {
/* 38 */     double val = super.evalDouble();
/* 39 */     this.expl.let(val, this.pos);
/* 40 */     return val;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Object evalObject() {
/* 46 */     Object val = super.evalObject();
/* 47 */     this.expl.let(val, this.pos);
/* 48 */     return val;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected AbstractExpression replace() {
/* 54 */     this.expl = this.expl.replaceVar();
/* 55 */     this.expr = this.expr.replace();
/* 56 */     return this.share.repl.replaceLet(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\eval\eval\exp\LetPowerExpression.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */