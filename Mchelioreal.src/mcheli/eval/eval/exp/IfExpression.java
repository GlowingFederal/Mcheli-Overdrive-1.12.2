/*    */ package mcheli.eval.eval.exp;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class IfExpression
/*    */   extends Col3Expression
/*    */ {
/*    */   public IfExpression() {
/* 13 */     setOperator("?");
/* 14 */     setEndOperator(":");
/*    */   }
/*    */ 
/*    */   
/*    */   protected IfExpression(IfExpression from, ShareExpValue s) {
/* 19 */     super(from, s);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public AbstractExpression dup(ShareExpValue s) {
/* 25 */     return new IfExpression(this, s);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public long evalLong() {
/* 31 */     if (this.exp1.evalLong() != 0L)
/*    */     {
/* 33 */       return this.exp2.evalLong();
/*    */     }
/* 35 */     return this.exp3.evalLong();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public double evalDouble() {
/* 41 */     if (this.exp1.evalDouble() != 0.0D)
/*    */     {
/* 43 */       return this.exp2.evalDouble();
/*    */     }
/* 45 */     return this.exp3.evalDouble();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Object evalObject() {
/* 51 */     if (this.share.oper.bool(this.exp1.evalObject()))
/*    */     {
/* 53 */       return this.exp2.evalObject();
/*    */     }
/* 55 */     return this.exp3.evalObject();
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\eval\eval\exp\IfExpression.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */