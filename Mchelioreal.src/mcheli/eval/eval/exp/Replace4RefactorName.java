/*    */ package mcheli.eval.eval.exp;
/*    */ 
/*    */ import mcheli.eval.eval.EvalException;
/*    */ import mcheli.eval.eval.ref.Refactor;
/*    */ import mcheli.eval.eval.repl.ReplaceAdapter;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Deprecated
/*    */ public class Replace4RefactorName
/*    */   extends ReplaceAdapter
/*    */ {
/*    */   protected Refactor ref;
/*    */   
/*    */   Replace4RefactorName(Refactor ref) {
/* 21 */     this.ref = ref;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void var(VariableExpression exp) {
/* 26 */     String name = this.ref.getNewName(null, exp.getWord());
/* 27 */     if (name != null)
/*    */     {
/* 29 */       exp.setWord(name);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   protected void field(FieldExpression exp) {
/* 35 */     AbstractExpression exp1 = exp.expl;
/* 36 */     Object obj = exp1.getVariable();
/* 37 */     if (obj == null)
/*    */     {
/* 39 */       throw new EvalException(2104, toString(), exp1.string, exp1.pos, null);
/*    */     }
/*    */     
/* 42 */     AbstractExpression exp2 = exp.expr;
/* 43 */     String name = this.ref.getNewName(obj, exp2.getWord());
/* 44 */     if (name != null)
/*    */     {
/* 46 */       exp2.setWord(name);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   protected void func(FunctionExpression exp) {
/* 52 */     Object obj = null;
/* 53 */     if (exp.target != null)
/*    */     {
/* 55 */       obj = exp.target.getVariable();
/*    */     }
/* 57 */     String name = this.ref.getNewFuncName(obj, exp.name);
/* 58 */     if (name != null)
/*    */     {
/* 60 */       exp.name = name;
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public AbstractExpression replace0(WordExpression exp) {
/* 67 */     if (exp instanceof VariableExpression)
/* 68 */       var((VariableExpression)exp); 
/* 69 */     return exp;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public AbstractExpression replace2(Col2Expression exp) {
/* 75 */     if (exp instanceof FieldExpression)
/* 76 */       field((FieldExpression)exp); 
/* 77 */     return exp;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public AbstractExpression replaceFunc(FunctionExpression exp) {
/* 83 */     func(exp);
/* 84 */     return exp;
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractExpression replaceVar(AbstractExpression exp) {
/* 89 */     if (exp instanceof VariableExpression) {
/*    */       
/* 91 */       var((VariableExpression)exp);
/*    */     }
/* 93 */     else if (exp instanceof FieldExpression) {
/*    */       
/* 95 */       field((FieldExpression)exp);
/*    */     }
/* 97 */     else if (exp instanceof FunctionExpression) {
/* 98 */       func((FunctionExpression)exp);
/* 99 */     }  return exp;
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\eval\eval\exp\Replace4RefactorName.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */