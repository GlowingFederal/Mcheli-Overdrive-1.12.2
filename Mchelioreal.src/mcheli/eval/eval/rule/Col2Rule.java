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
/*    */ public class Col2Rule
/*    */   extends AbstractRule
/*    */ {
/*    */   public Col2Rule(ShareRuleValue share) {
/* 17 */     super(share);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected AbstractExpression parse(Lex lex) {
/* 23 */     AbstractExpression x = this.nextRule.parse(lex);
/*    */     
/*    */     while (true) {
/*    */       String ope;
/* 27 */       switch (lex.getType()) {
/*    */         
/*    */         case 2147483634:
/* 30 */           ope = lex.getOperator();
/* 31 */           if (isMyOperator(ope)) {
/*    */             
/* 33 */             int pos = lex.getPos();
/* 34 */             AbstractExpression y = this.nextRule.parse(lex.next());
/* 35 */             x = Col2Expression.create(newExpression(ope, lex.getShare()), lex.getString(), pos, x, y);
/*    */             continue;
/*    */           } 
/* 38 */           return x;
/*    */       } 
/*    */       break;
/*    */     } 
/* 42 */     return x;
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\eval\eval\rule\Col2Rule.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */