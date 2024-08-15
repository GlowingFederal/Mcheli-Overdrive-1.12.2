/*    */ package mcheli.eval.eval.exp;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BitNotExpression
/*    */   extends Col1Expression
/*    */ {
/*    */   public BitNotExpression() {
/* 13 */     setOperator("~");
/*    */   }
/*    */ 
/*    */   
/*    */   protected BitNotExpression(BitNotExpression from, ShareExpValue s) {
/* 18 */     super(from, s);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public AbstractExpression dup(ShareExpValue s) {
/* 24 */     return new BitNotExpression(this, s);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected long operateLong(long val) {
/* 30 */     return val ^ 0xFFFFFFFFFFFFFFFFL;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected double operateDouble(double val) {
/* 36 */     return ((long)val ^ 0xFFFFFFFFFFFFFFFFL);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Object evalObject() {
/* 42 */     return this.share.oper.bitNot(this.exp.evalObject());
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\eval\eval\exp\BitNotExpression.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */