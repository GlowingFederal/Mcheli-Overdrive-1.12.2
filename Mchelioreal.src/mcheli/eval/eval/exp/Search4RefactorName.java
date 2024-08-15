/*    */ package mcheli.eval.eval.exp;
/*    */ 
/*    */ import mcheli.eval.eval.EvalException;
/*    */ import mcheli.eval.eval.ref.Refactor;
/*    */ import mcheli.eval.eval.srch.SearchAdapter;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Search4RefactorName
/*    */   extends SearchAdapter
/*    */ {
/*    */   protected Refactor ref;
/*    */   
/*    */   Search4RefactorName(Refactor ref) {
/* 19 */     this.ref = ref;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void search0(WordExpression exp) {
/* 25 */     if (exp instanceof VariableExpression) {
/*    */       
/* 27 */       String name = this.ref.getNewName(null, exp.getWord());
/* 28 */       if (name != null)
/*    */       {
/* 30 */         exp.setWord(name);
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean search2_2(Col2Expression exp) {
/* 38 */     if (exp instanceof FieldExpression) {
/*    */       
/* 40 */       AbstractExpression exp1 = exp.expl;
/* 41 */       Object obj = exp1.getVariable();
/* 42 */       if (obj == null)
/*    */       {
/* 44 */         throw new EvalException(2104, toString(), exp1.string, exp1.pos, null);
/*    */       }
/*    */       
/* 47 */       AbstractExpression exp2 = exp.expr;
/* 48 */       String name = this.ref.getNewName(obj, exp2.getWord());
/* 49 */       if (name != null)
/*    */       {
/* 51 */         exp2.setWord(name);
/*    */       }
/* 53 */       return true;
/*    */     } 
/* 55 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean searchFunc_2(FunctionExpression exp) {
/* 61 */     Object obj = null;
/* 62 */     if (exp.target != null)
/*    */     {
/* 64 */       obj = exp.target.getVariable();
/*    */     }
/* 66 */     String name = this.ref.getNewFuncName(obj, exp.name);
/* 67 */     if (name != null)
/*    */     {
/* 69 */       exp.name = name;
/*    */     }
/*    */     
/* 72 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\eval\eval\exp\Search4RefactorName.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */