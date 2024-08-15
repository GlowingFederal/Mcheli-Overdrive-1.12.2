/*    */ package mcheli.eval.eval.exp;
/*    */ 
/*    */ import mcheli.eval.eval.EvalException;
/*    */ import mcheli.eval.eval.lex.Lex;
/*    */ import mcheli.eval.util.CharUtil;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CharExpression
/*    */   extends WordExpression
/*    */ {
/*    */   public static AbstractExpression create(Lex lex, int prio) {
/* 17 */     String str = lex.getWord();
/* 18 */     str = CharUtil.escapeString(str, 1, str.length() - 2);
/* 19 */     AbstractExpression exp = new CharExpression(str);
/* 20 */     exp.setPos(lex.getString(), lex.getPos());
/* 21 */     exp.setPriority(prio);
/* 22 */     exp.share = lex.getShare();
/* 23 */     return exp;
/*    */   }
/*    */ 
/*    */   
/*    */   public CharExpression(String str) {
/* 28 */     super(str);
/* 29 */     setOperator("'");
/* 30 */     setEndOperator("'");
/*    */   }
/*    */ 
/*    */   
/*    */   protected CharExpression(CharExpression from, ShareExpValue s) {
/* 35 */     super(from, s);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public AbstractExpression dup(ShareExpValue s) {
/* 41 */     return new CharExpression(this, s);
/*    */   }
/*    */ 
/*    */   
/*    */   public static CharExpression create(AbstractExpression from, String word) {
/* 46 */     CharExpression n = new CharExpression(word);
/* 47 */     n.string = from.string;
/* 48 */     n.pos = from.pos;
/* 49 */     n.prio = from.prio;
/* 50 */     n.share = from.share;
/* 51 */     return n;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public long evalLong() {
/*    */     try {
/* 59 */       return this.word.charAt(0);
/*    */     }
/* 61 */     catch (Exception e) {
/*    */       
/* 63 */       throw new EvalException(2003, this.word, this.string, this.pos, e);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public double evalDouble() {
/*    */     try {
/* 72 */       return this.word.charAt(0);
/*    */     }
/* 74 */     catch (Exception e) {
/*    */       
/* 76 */       throw new EvalException(2003, this.word, this.string, this.pos, e);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Object evalObject() {
/* 83 */     return new Character(this.word.charAt(0));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 89 */     StringBuffer sb = new StringBuffer();
/* 90 */     sb.append(getOperator());
/* 91 */     sb.append(this.word);
/* 92 */     sb.append(getEndOperator());
/* 93 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\eval\eval\exp\CharExpression.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */