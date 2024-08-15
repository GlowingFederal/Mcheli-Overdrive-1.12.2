/*    */ package mcheli.eval.eval.lex;
/*    */ 
/*    */ import java.util.List;
/*    */ import mcheli.eval.eval.exp.ShareExpValue;
/*    */ import mcheli.eval.eval.rule.ShareRuleValue;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LexFactory
/*    */ {
/*    */   public Lex create(String str, List<String>[] opeList, ShareRuleValue share, ShareExpValue exp) {
/* 18 */     return new Lex(str, opeList, share.paren, exp);
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\eval\eval\lex\LexFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */