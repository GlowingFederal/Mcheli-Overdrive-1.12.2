/*    */ package mcheli.eval.eval.exp;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ParenExpression
/*    */   extends Col1Expression
/*    */ {
/*    */   public ParenExpression() {
/* 13 */     setOperator("(");
/* 14 */     setEndOperator(")");
/*    */   }
/*    */ 
/*    */   
/*    */   protected ParenExpression(ParenExpression from, ShareExpValue s) {
/* 19 */     super(from, s);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public AbstractExpression dup(ShareExpValue s) {
/* 25 */     return new ParenExpression(this, s);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected long operateLong(long val) {
/* 31 */     return val;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected double operateDouble(double val) {
/* 37 */     return val;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Object evalObject() {
/* 43 */     return this.exp.evalObject();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 49 */     if (this.exp == null)
/*    */     {
/* 51 */       return "";
/*    */     }
/* 53 */     return getOperator() + this.exp.toString() + getEndOperator();
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\eval\eval\exp\ParenExpression.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */