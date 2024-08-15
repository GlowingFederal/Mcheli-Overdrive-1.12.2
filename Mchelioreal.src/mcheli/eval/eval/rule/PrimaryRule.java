/*    */ package mcheli.eval.eval.rule;
/*    */ 
/*    */ import mcheli.eval.eval.EvalException;
/*    */ import mcheli.eval.eval.exp.AbstractExpression;
/*    */ import mcheli.eval.eval.exp.CharExpression;
/*    */ import mcheli.eval.eval.exp.Col1Expression;
/*    */ import mcheli.eval.eval.exp.NumberExpression;
/*    */ import mcheli.eval.eval.exp.StringExpression;
/*    */ import mcheli.eval.eval.exp.VariableExpression;
/*    */ import mcheli.eval.eval.lex.Lex;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PrimaryRule
/*    */   extends AbstractRule
/*    */ {
/*    */   public PrimaryRule(ShareRuleValue share) {
/* 22 */     super(share); } public final AbstractExpression parse(Lex lex) { AbstractExpression n;
/*    */     AbstractExpression w;
/*    */     AbstractExpression s;
/*    */     AbstractExpression c;
/*    */     String ope;
/*    */     int pos;
/* 28 */     switch (lex.getType()) {
/*    */       
/*    */       case 2147483633:
/* 31 */         n = NumberExpression.create(lex, this.prio);
/* 32 */         lex.next();
/* 33 */         return n;
/*    */       case 2147483632:
/* 35 */         w = VariableExpression.create(lex, this.prio);
/* 36 */         lex.next();
/* 37 */         return w;
/*    */       case 2147483635:
/* 39 */         s = StringExpression.create(lex, this.prio);
/* 40 */         lex.next();
/* 41 */         return s;
/*    */       case 2147483636:
/* 43 */         c = CharExpression.create(lex, this.prio);
/* 44 */         lex.next();
/* 45 */         return c;
/*    */       case 2147483634:
/* 47 */         ope = lex.getOperator();
/* 48 */         pos = lex.getPos();
/* 49 */         if (isMyOperator(ope)) {
/*    */           
/* 51 */           if (ope.equals(this.share.paren.getOperator()))
/*    */           {
/* 53 */             return parseParen(lex, ope, pos);
/*    */           }
/* 55 */           return Col1Expression.create(newExpression(ope, lex.getShare()), lex.getString(), pos, 
/* 56 */               parse(lex.next()));
/*    */         } 
/*    */         
/* 59 */         throw new EvalException(1002, lex);
/*    */       case 2147483647:
/* 61 */         throw new EvalException(1004, lex);
/*    */     } 
/* 63 */     throw new EvalException(1003, lex); }
/*    */ 
/*    */ 
/*    */   
/*    */   protected AbstractExpression parseParen(Lex lex, String ope, int pos) {
/* 68 */     AbstractExpression s = this.share.topRule.parse(lex.next());
/* 69 */     if (!lex.isOperator(this.share.paren.getEndOperator()))
/*    */     {
/* 71 */       throw new EvalException(1001, new String[] { this.share.paren
/*    */             
/* 73 */             .getEndOperator() }, lex);
/*    */     }
/*    */ 
/*    */     
/* 77 */     lex.next();
/* 78 */     return Col1Expression.create(newExpression(ope, lex.getShare()), lex.getString(), pos, s);
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\eval\eval\rule\PrimaryRule.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */