/*    */ package mcheli.eval.eval.rule;
/*    */ 
/*    */ import mcheli.eval.eval.EvalException;
/*    */ import mcheli.eval.eval.exp.AbstractExpression;
/*    */ import mcheli.eval.eval.exp.Col3Expression;
/*    */ import mcheli.eval.eval.lex.Lex;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class IfRule
/*    */   extends AbstractRule
/*    */ {
/*    */   public AbstractExpression cond;
/*    */   
/*    */   public IfRule(ShareRuleValue share) {
/* 20 */     super(share);
/*    */   }
/*    */   
/*    */   protected AbstractExpression parse(Lex lex) {
/*    */     String ope;
/*    */     int pos;
/* 26 */     AbstractExpression x = this.nextRule.parse(lex);
/* 27 */     switch (lex.getType()) {
/*    */       
/*    */       case 2147483634:
/* 30 */         ope = lex.getOperator();
/* 31 */         pos = lex.getPos();
/* 32 */         if (isMyOperator(ope) && lex.isOperator(this.cond.getOperator()))
/*    */         {
/* 34 */           x = parseCond(lex, x, ope, pos);
/*    */         }
/*    */         
/* 37 */         return x;
/*    */     } 
/* 39 */     return x;
/*    */   }
/*    */ 
/*    */   
/*    */   protected AbstractExpression parseCond(Lex lex, AbstractExpression x, String ope, int pos) {
/* 44 */     AbstractExpression y = parse(lex.next());
/* 45 */     if (!lex.isOperator(this.cond.getEndOperator()))
/*    */     {
/* 47 */       throw new EvalException(1001, new String[] { this.cond
/*    */             
/* 49 */             .getEndOperator() }, lex);
/*    */     }
/*    */ 
/*    */     
/* 53 */     AbstractExpression z = parse(lex.next());
/* 54 */     x = Col3Expression.create(newExpression(ope, lex.getShare()), lex.getString(), pos, x, y, z);
/*    */     
/* 56 */     return x;
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\eval\eval\rule\IfRule.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */