/*     */ package mcheli.eval.eval.rule;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import mcheli.eval.eval.exp.AbstractExpression;
/*     */ import mcheli.eval.eval.exp.ShareExpValue;
/*     */ import mcheli.eval.eval.lex.Lex;
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
/*     */ public abstract class AbstractRule
/*     */ {
/*     */   public AbstractRule nextRule;
/*     */   protected ShareRuleValue share;
/*     */   private final Map<String, AbstractExpression> opes;
/*     */   public int prio;
/*     */   
/*     */   public AbstractRule(ShareRuleValue share) {
/*  44 */     this.opes = new HashMap<>();
/*     */     this.share = share;
/*     */   }
/*     */ 
/*     */   
/*     */   public final void addOperator(String ope, AbstractExpression exp) {
/*  50 */     this.opes.put(ope, exp);
/*     */     
/*  52 */     addLexOperator(ope); }
/*     */   public final void addExpression(AbstractExpression exp) { if (exp == null)
/*     */       return;  String ope = exp.getOperator(); addOperator(ope, exp);
/*     */     addLexOperator(exp.getEndOperator());
/*     */     if (exp instanceof mcheli.eval.eval.exp.ParenExpression)
/*  57 */       this.share.paren = exp;  } public final String[] getOperators() { List<String> list = new ArrayList<>();
/*  58 */     for (Iterator<String> i = this.opes.keySet().iterator(); i.hasNext();)
/*     */     {
/*  60 */       list.add(i.next());
/*     */     }
/*  62 */     return list.<String>toArray(new String[list.size()]); }
/*     */ 
/*     */ 
/*     */   
/*     */   public final void addLexOperator(String ope) {
/*  67 */     if (ope == null)
/*     */       return; 
/*  69 */     int n = ope.length() - 1;
/*  70 */     if (this.share.opeList[n] == null)
/*  71 */       this.share.opeList[n] = new ArrayList<>(); 
/*  72 */     this.share.opeList[n].add(ope);
/*     */   }
/*     */ 
/*     */   
/*     */   protected final boolean isMyOperator(String ope) {
/*  77 */     return this.opes.containsKey(ope);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected final AbstractExpression newExpression(String ope, ShareExpValue share) {
/*     */     try {
/*  84 */       AbstractExpression org = this.opes.get(ope);
/*  85 */       AbstractExpression n = org.dup(share);
/*  86 */       n.setPriority(this.prio);
/*  87 */       n.share = share;
/*  88 */       return n;
/*     */     }
/*  90 */     catch (RuntimeException e) {
/*     */       
/*  92 */       throw e;
/*     */     }
/*  94 */     catch (Exception e) {
/*     */       
/*  96 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public final void initPriority(int prio) {
/* 102 */     this.prio = prio;
/*     */     
/* 104 */     if (this.nextRule != null)
/*     */     {
/* 106 */       this.nextRule.initPriority(prio + 1);
/*     */     }
/*     */   }
/*     */   
/*     */   protected abstract AbstractExpression parse(Lex paramLex);
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\eval\eval\rule\AbstractRule.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */