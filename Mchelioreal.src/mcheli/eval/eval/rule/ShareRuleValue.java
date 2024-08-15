/*     */ package mcheli.eval.eval.rule;
/*     */ 
/*     */ import java.util.List;
/*     */ import mcheli.eval.eval.EvalException;
/*     */ import mcheli.eval.eval.Expression;
/*     */ import mcheli.eval.eval.Rule;
/*     */ import mcheli.eval.eval.exp.AbstractExpression;
/*     */ import mcheli.eval.eval.exp.ShareExpValue;
/*     */ import mcheli.eval.eval.lex.Lex;
/*     */ import mcheli.eval.eval.lex.LexFactory;
/*     */ import mcheli.eval.eval.oper.Operator;
/*     */ import mcheli.eval.eval.ref.Refactor;
/*     */ import mcheli.eval.eval.srch.Search;
/*     */ import mcheli.eval.eval.var.Variable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ShareRuleValue
/*     */   extends Rule
/*     */ {
/*     */   public AbstractRule topRule;
/*     */   public AbstractRule funcArgRule;
/*     */   public LexFactory lexFactory;
/*  34 */   protected List<String>[] opeList = (List<String>[])new List[4];
/*     */   
/*     */   public AbstractExpression paren;
/*     */ 
/*     */   
/*     */   public Expression parse(String str) {
/*  40 */     if (str == null)
/*  41 */       return null; 
/*  42 */     if (str.trim().length() <= 0)
/*     */     {
/*  44 */       return new EmptyExpression();
/*     */     }
/*     */     
/*  47 */     ShareExpValue exp = new ShareExpValue();
/*  48 */     AbstractExpression x = parse(str, exp);
/*     */     
/*  50 */     exp.setAbstractExpression(x);
/*  51 */     return (Expression)exp;
/*     */   }
/*     */ 
/*     */   
/*     */   public AbstractExpression parse(String str, ShareExpValue exp) {
/*  56 */     if (str == null)
/*     */     {
/*  58 */       return null;
/*     */     }
/*  60 */     Lex lex = this.lexFactory.create(str, (List[])this.opeList, this, exp);
/*  61 */     lex.check();
/*     */     
/*  63 */     AbstractExpression x = this.topRule.parse(lex);
/*  64 */     if (lex.getType() != Integer.MAX_VALUE)
/*     */     {
/*  66 */       throw new EvalException(1005, lex);
/*     */     }
/*  68 */     return x;
/*     */   }
/*     */ 
/*     */   
/*     */   class EmptyExpression
/*     */     extends Expression
/*     */   {
/*     */     public long evalLong() {
/*  76 */       return 0L;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public double evalDouble() {
/*  82 */       return 0.0D;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Object eval() {
/*  88 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void optimizeLong(Variable var) {}
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void optimizeDouble(Variable var) {}
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void optimize(Variable var, Operator oper) {}
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void search(Search srch) {}
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void refactorName(Refactor ref) {}
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void refactorFunc(Refactor ref, Rule rule) {}
/*     */ 
/*     */ 
/*     */     
/*     */     public Expression dup() {
/* 124 */       return new EmptyExpression();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean same(Expression obj) {
/* 130 */       if (obj instanceof EmptyExpression)
/*     */       {
/* 132 */         return true;
/*     */       }
/* 134 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public String toString() {
/* 140 */       return "";
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\eval\eval\rule\ShareRuleValue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */