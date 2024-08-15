/*    */ package mcheli.eval.eval.exp;
/*    */ 
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FuncArgExpression
/*    */   extends Col2OpeExpression
/*    */ {
/*    */   public FuncArgExpression() {
/* 15 */     setOperator(",");
/*    */   }
/*    */ 
/*    */   
/*    */   protected FuncArgExpression(FuncArgExpression from, ShareExpValue s) {
/* 20 */     super(from, s);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public AbstractExpression dup(ShareExpValue s) {
/* 26 */     return new FuncArgExpression(this, s);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void evalArgsLong(List<Long> args) {
/* 32 */     this.expl.evalArgsLong(args);
/* 33 */     this.expr.evalArgsLong(args);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void evalArgsDouble(List<Double> args) {
/* 39 */     this.expl.evalArgsDouble(args);
/* 40 */     this.expr.evalArgsDouble(args);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void evalArgsObject(List<Object> args) {
/* 46 */     this.expl.evalArgsObject(args);
/* 47 */     this.expr.evalArgsObject(args);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected String toStringLeftSpace() {
/* 53 */     return "";
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\eval\eval\exp\FuncArgExpression.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */