/*     */ package mcheli.eval.eval.exp;
/*     */ 
/*     */ import mcheli.eval.eval.EvalException;
/*     */ import mcheli.eval.eval.lex.Lex;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class VariableExpression
/*     */   extends WordExpression
/*     */ {
/*     */   public static AbstractExpression create(Lex lex, int prio) {
/*  16 */     AbstractExpression exp = new VariableExpression(lex.getWord());
/*  17 */     exp.setPos(lex.getString(), lex.getPos());
/*  18 */     exp.setPriority(prio);
/*  19 */     exp.share = lex.getShare();
/*  20 */     return exp;
/*     */   }
/*     */ 
/*     */   
/*     */   public VariableExpression(String str) {
/*  25 */     super(str);
/*     */   }
/*     */ 
/*     */   
/*     */   protected VariableExpression(VariableExpression from, ShareExpValue s) {
/*  30 */     super(from, s);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AbstractExpression dup(ShareExpValue s) {
/*  36 */     return new VariableExpression(this, s);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long evalLong() {
/*     */     try {
/*  44 */       return this.share.var.evalLong(getVarValue());
/*     */     }
/*  46 */     catch (EvalException e) {
/*     */       
/*  48 */       throw e;
/*     */     }
/*  50 */     catch (Exception e) {
/*     */       
/*  52 */       throw new EvalException(2003, this.word, this.string, this.pos, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double evalDouble() {
/*     */     try {
/*  61 */       return this.share.var.evalDouble(getVarValue());
/*     */     }
/*  63 */     catch (EvalException e) {
/*     */       
/*  65 */       throw e;
/*     */     }
/*  67 */     catch (Exception e) {
/*     */       
/*  69 */       throw new EvalException(2003, this.word, this.string, this.pos, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object evalObject() {
/*  76 */     return getVarValue();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void let(Object val, int pos) {
/*  82 */     String name = getWord();
/*     */     
/*     */     try {
/*  85 */       this.share.var.setValue(name, val);
/*     */     }
/*  87 */     catch (EvalException e) {
/*     */       
/*  89 */       throw e;
/*     */     }
/*  91 */     catch (Exception e) {
/*     */       
/*  93 */       throw new EvalException(2102, name, this.string, pos, e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private Object getVarValue() {
/*     */     Object val;
/*  99 */     String word = getWord();
/*     */ 
/*     */     
/*     */     try {
/* 103 */       val = this.share.var.getObject(word);
/*     */     }
/* 105 */     catch (EvalException e) {
/*     */       
/* 107 */       throw e;
/*     */     }
/* 109 */     catch (Exception e) {
/*     */       
/* 111 */       throw new EvalException(2101, word, this.string, this.pos, e);
/*     */     } 
/*     */     
/* 114 */     if (val == null)
/*     */     {
/* 116 */       throw new EvalException(2103, word, this.string, this.pos, null);
/*     */     }
/*     */     
/* 119 */     return val;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Object getVariable() {
/*     */     try {
/* 127 */       return this.share.var.getObject(this.word);
/*     */     }
/* 129 */     catch (EvalException e) {
/*     */       
/* 131 */       throw e;
/*     */     }
/* 133 */     catch (Exception e) {
/*     */       
/* 135 */       throw new EvalException(2002, this.word, this.string, this.pos, null);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\eval\eval\exp\VariableExpression.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */