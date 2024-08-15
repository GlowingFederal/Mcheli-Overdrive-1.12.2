/*    */ package mcheli.eval.eval.exp;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class Col2OpeExpression
/*    */   extends Col2Expression
/*    */ {
/*    */   protected Col2OpeExpression() {}
/*    */   
/*    */   protected Col2OpeExpression(Col2Expression from, ShareExpValue s) {
/* 17 */     super(from, s);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected final long operateLong(long vl, long vr) {
/* 23 */     throw new RuntimeException("この関数が呼ばれてはいけない");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected final double operateDouble(double vl, double vr) {
/* 29 */     throw new RuntimeException("この関数が呼ばれてはいけない");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected final Object operateObject(Object vl, Object vr) {
/* 35 */     throw new RuntimeException("この関数が呼ばれてはいけない");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected AbstractExpression replace() {
/* 41 */     this.expl = this.expl.replace();
/* 42 */     this.expr = this.expr.replace();
/* 43 */     return this.share.repl.replace2(this);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected AbstractExpression replaceVar() {
/* 49 */     this.expl = this.expl.replaceVar();
/* 50 */     this.expr = this.expr.replaceVar();
/* 51 */     return this.share.repl.replaceVar2(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\eval\eval\exp\Col2OpeExpression.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */