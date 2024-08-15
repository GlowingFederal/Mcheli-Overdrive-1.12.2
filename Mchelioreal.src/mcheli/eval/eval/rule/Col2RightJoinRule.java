/*    */ package mcheli.eval.eval.rule;
/*    */ 
/*    */ import mcheli.eval.eval.exp.AbstractExpression;
/*    */ import mcheli.eval.eval.exp.Col2Expression;
/*    */ import mcheli.eval.eval.lex.Lex;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Col2RightJoinRule
/*    */   extends AbstractRule
/*    */ {
/*    */   public Col2RightJoinRule(ShareRuleValue share) {
/* 17 */     super(share);
/*    */   }
/*    */ 
/*    */   
/*    */   protected AbstractExpression parse(Lex lex) {
/*    */     String ope;
/* 23 */     AbstractExpression x = this.nextRule.parse(lex);
/* 24 */     switch (lex.getType()) {
/*    */       
/*    */       case 2147483634:
/* 27 */         ope = lex.getOperator();
/* 28 */         if (isMyOperator(ope)) {
/*    */           
/* 30 */           int pos = lex.getPos();
/* 31 */           AbstractExpression y = parse(lex.next());
/* 32 */           x = Col2Expression.create(newExpression(ope, lex.getShare()), lex.getString(), pos, x, y);
/*    */         } 
/*    */         
/* 35 */         return x;
/*    */     } 
/* 37 */     return x;
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\eval\eval\rule\Col2RightJoinRule.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */