/*    */ package mcheli.eval.eval.exp;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BitAndExpression
/*    */   extends Col2Expression
/*    */ {
/*    */   public BitAndExpression() {
/* 13 */     setOperator("&");
/*    */   }
/*    */ 
/*    */   
/*    */   protected BitAndExpression(BitAndExpression from, ShareExpValue s) {
/* 18 */     super(from, s);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public AbstractExpression dup(ShareExpValue s) {
/* 24 */     return new BitAndExpression(this, s);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected long operateLong(long vl, long vr) {
/* 30 */     return vl & vr;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected double operateDouble(double vl, double vr) {
/* 36 */     return ((long)vl & (long)vr);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected Object operateObject(Object vl, Object vr) {
/* 42 */     return this.share.oper.bitAnd(vl, vr);
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\eval\eval\exp\BitAndExpression.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */