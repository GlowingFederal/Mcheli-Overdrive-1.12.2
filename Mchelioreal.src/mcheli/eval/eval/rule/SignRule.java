/*    */ package mcheli.eval.eval.rule;
/*    */ 
/*    */ import mcheli.eval.eval.exp.AbstractExpression;
/*    */ import mcheli.eval.eval.exp.Col1Expression;
/*    */ import mcheli.eval.eval.lex.Lex;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SignRule
/*    */   extends AbstractRule
/*    */ {
/*    */   public SignRule(ShareRuleValue share) {
/* 17 */     super(share);
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractExpression parse(Lex lex) {
/*    */     String ope;
/* 23 */     switch (lex.getType()) {
/*    */       
/*    */       case 2147483634:
/* 26 */         ope = lex.getOperator();
/* 27 */         if (isMyOperator(ope)) {
/*    */           
/* 29 */           int pos = lex.getPos();
/* 30 */           return Col1Expression.create(newExpression(ope, lex.getShare()), lex.getString(), pos, 
/* 31 */               parse(lex.next()));
/*    */         } 
/*    */         
/* 34 */         return this.nextRule.parse(lex);
/*    */     } 
/* 36 */     return this.nextRule.parse(lex);
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\eval\eval\rule\SignRule.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */