/*    */ package mcheli.eval.eval.repl;
/*    */ 
/*    */ import mcheli.eval.eval.exp.AbstractExpression;
/*    */ import mcheli.eval.eval.exp.Col1Expression;
/*    */ import mcheli.eval.eval.exp.Col2Expression;
/*    */ import mcheli.eval.eval.exp.Col2OpeExpression;
/*    */ import mcheli.eval.eval.exp.Col3Expression;
/*    */ import mcheli.eval.eval.exp.FunctionExpression;
/*    */ import mcheli.eval.eval.exp.WordExpression;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ReplaceAdapter
/*    */   implements Replace
/*    */ {
/*    */   public AbstractExpression replace0(WordExpression exp) {
/* 21 */     return (AbstractExpression)exp;
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractExpression replace1(Col1Expression exp) {
/* 26 */     return (AbstractExpression)exp;
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractExpression replace2(Col2Expression exp) {
/* 31 */     return (AbstractExpression)exp;
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractExpression replace2(Col2OpeExpression exp) {
/* 36 */     return (AbstractExpression)exp;
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractExpression replace3(Col3Expression exp) {
/* 41 */     return (AbstractExpression)exp;
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractExpression replaceVar0(WordExpression exp) {
/* 46 */     return (AbstractExpression)exp;
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractExpression replaceVar1(Col1Expression exp) {
/* 51 */     return (AbstractExpression)exp;
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractExpression replaceVar2(Col2Expression exp) {
/* 56 */     return (AbstractExpression)exp;
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractExpression replaceVar2(Col2OpeExpression exp) {
/* 61 */     return (AbstractExpression)exp;
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractExpression replaceVar3(Col3Expression exp) {
/* 66 */     return (AbstractExpression)exp;
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractExpression replaceFunc(FunctionExpression exp) {
/* 71 */     return (AbstractExpression)exp;
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractExpression replaceLet(Col2Expression exp) {
/* 76 */     return (AbstractExpression)exp;
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\eval\eval\repl\ReplaceAdapter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */