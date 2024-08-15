/*    */ package mcheli.eval.eval.exp;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class OptimizeLong
/*    */   extends OptimizeObject
/*    */ {
/*    */   protected boolean isTrue(AbstractExpression x) {
/* 14 */     return (x.evalLong() != 0L);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected AbstractExpression toConst(AbstractExpression exp) {
/*    */     try {
/* 22 */       long val = exp.evalLong();
/* 23 */       return NumberExpression.create(exp, Long.toString(val));
/*    */     }
/* 25 */     catch (Exception exception) {
/*    */ 
/*    */       
/* 28 */       return exp;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\eval\eval\exp\OptimizeLong.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */