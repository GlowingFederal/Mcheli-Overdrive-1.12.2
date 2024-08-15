/*    */ package mcheli.eval.eval.rule;
/*    */ 
/*    */ import mcheli.eval.eval.ExpRuleFactory;
/*    */ import mcheli.eval.eval.exp.AbstractExpression;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class JavaRuleFactory
/*    */   extends ExpRuleFactory
/*    */ {
/*    */   private static JavaRuleFactory me;
/*    */   
/*    */   public static ExpRuleFactory getInstance() {
/* 18 */     if (me == null)
/*    */     {
/* 20 */       me = new JavaRuleFactory();
/*    */     }
/* 22 */     return me;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected AbstractRule createCommaRule(ShareRuleValue share) {
/* 28 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected AbstractRule createPowerRule(ShareRuleValue share) {
/* 34 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected AbstractExpression createLetPowerExpression() {
/* 40 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\eval\eval\rule\JavaRuleFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */