/*    */ package mcheli.eval.eval.exp;
/*    */ 
/*    */ import mcheli.eval.eval.Rule;
/*    */ import mcheli.eval.eval.ref.Refactor;
/*    */ import mcheli.eval.eval.repl.ReplaceAdapter;
/*    */ import mcheli.eval.eval.rule.ShareRuleValue;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Replace4RefactorGetter
/*    */   extends ReplaceAdapter
/*    */ {
/*    */   protected Refactor ref;
/*    */   protected ShareRuleValue rule;
/*    */   
/*    */   Replace4RefactorGetter(Refactor ref, Rule rule) {
/* 21 */     this.ref = ref;
/* 22 */     this.rule = (ShareRuleValue)rule;
/*    */   }
/*    */ 
/*    */   
/*    */   protected AbstractExpression var(VariableExpression exp) {
/* 27 */     String name = this.ref.getNewName(null, exp.getWord());
/* 28 */     if (name == null)
/*    */     {
/* 30 */       return exp;
/*    */     }
/* 32 */     return this.rule.parse(name, exp.share);
/*    */   }
/*    */ 
/*    */   
/*    */   protected AbstractExpression field(FieldExpression exp) {
/* 37 */     AbstractExpression exp1 = exp.expl;
/* 38 */     Object obj = exp1.getVariable();
/* 39 */     if (obj == null)
/*    */     {
/* 41 */       return exp;
/*    */     }
/* 43 */     AbstractExpression exp2 = exp.expr;
/* 44 */     String name = this.ref.getNewName(obj, exp2.getWord());
/* 45 */     if (name == null)
/*    */     {
/* 47 */       return exp;
/*    */     }
/* 49 */     exp.expr = this.rule.parse(name, exp2.share);
/* 50 */     return exp;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public AbstractExpression replace0(WordExpression exp) {
/* 56 */     if (exp instanceof VariableExpression)
/* 57 */       return var((VariableExpression)exp); 
/* 58 */     return exp;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public AbstractExpression replace2(Col2OpeExpression exp) {
/* 64 */     if (exp instanceof FieldExpression)
/* 65 */       return field((FieldExpression)exp); 
/* 66 */     return exp;
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\eval\eval\exp\Replace4RefactorGetter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */