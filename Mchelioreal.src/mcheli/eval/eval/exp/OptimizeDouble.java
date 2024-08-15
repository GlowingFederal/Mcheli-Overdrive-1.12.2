/*    */ package mcheli.eval.eval.exp;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class OptimizeDouble
/*    */   extends OptimizeObject
/*    */ {
/*    */   protected boolean isTrue(AbstractExpression x) {
/* 14 */     return (x.evalDouble() != 0.0D);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected AbstractExpression toConst(AbstractExpression exp) {
/*    */     try {
/* 22 */       double val = exp.evalDouble();
/* 23 */       return NumberExpression.create(exp, Double.toString(val));
/*    */     }
/* 25 */     catch (Exception exception) {
/*    */ 
/*    */       
/* 28 */       return exp;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\eval\eval\exp\OptimizeDouble.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */