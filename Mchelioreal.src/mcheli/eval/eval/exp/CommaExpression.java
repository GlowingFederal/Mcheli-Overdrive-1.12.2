/*    */ package mcheli.eval.eval.exp;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CommaExpression
/*    */   extends Col2OpeExpression
/*    */ {
/*    */   public CommaExpression() {
/* 13 */     setOperator(",");
/*    */   }
/*    */ 
/*    */   
/*    */   protected CommaExpression(CommaExpression from, ShareExpValue s) {
/* 18 */     super(from, s);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public AbstractExpression dup(ShareExpValue s) {
/* 24 */     return new CommaExpression(this, s);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public long evalLong() {
/* 30 */     this.expl.evalLong();
/* 31 */     return this.expr.evalLong();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public double evalDouble() {
/* 37 */     this.expl.evalDouble();
/* 38 */     return this.expr.evalDouble();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Object evalObject() {
/* 44 */     this.expl.evalObject();
/* 45 */     return this.expr.evalObject();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected String toStringLeftSpace() {
/* 51 */     return "";
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\eval\eval\exp\CommaExpression.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */