/*     */ package mcheli.eval.eval.exp;
/*     */ 
/*     */ import mcheli.eval.eval.EvalException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FieldExpression
/*     */   extends Col2OpeExpression
/*     */ {
/*     */   public FieldExpression() {
/*  15 */     setOperator(".");
/*     */   }
/*     */ 
/*     */   
/*     */   protected FieldExpression(FieldExpression from, ShareExpValue s) {
/*  20 */     super(from, s);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AbstractExpression dup(ShareExpValue s) {
/*  26 */     return new FieldExpression(this, s);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long evalLong() {
/*     */     try {
/*  34 */       return this.share.var.evalLong(getVariable());
/*     */     }
/*  36 */     catch (EvalException e) {
/*     */       
/*  38 */       throw e;
/*     */     }
/*  40 */     catch (Exception e) {
/*     */       
/*  42 */       throw new EvalException(2003, toString(), this.string, this.pos, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double evalDouble() {
/*     */     try {
/*  51 */       return this.share.var.evalDouble(getVariable());
/*     */     }
/*  53 */     catch (EvalException e) {
/*     */       
/*  55 */       throw e;
/*     */     }
/*  57 */     catch (Exception e) {
/*     */       
/*  59 */       throw new EvalException(2003, toString(), this.string, this.pos, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object evalObject() {
/*  66 */     return getVariable();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Object getVariable() {
/*  72 */     Object obj = this.expl.getVariable();
/*  73 */     if (obj == null)
/*     */     {
/*  75 */       throw new EvalException(2104, this.expl.toString(), this.string, this.pos, null);
/*     */     }
/*     */     
/*  78 */     String word = this.expr.getWord();
/*     */     
/*     */     try {
/*  81 */       return this.share.var.getObject(obj, word);
/*     */     }
/*  83 */     catch (EvalException e) {
/*     */       
/*  85 */       throw e;
/*     */     }
/*  87 */     catch (Exception e) {
/*     */       
/*  89 */       throw new EvalException(2301, toString(), this.string, this.pos, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void let(Object val, int pos) {
/*  96 */     Object obj = this.expl.getVariable();
/*  97 */     if (obj == null)
/*     */     {
/*  99 */       throw new EvalException(2104, this.expl.toString(), this.string, pos, null);
/*     */     }
/*     */     
/* 102 */     String word = this.expr.getWord();
/*     */     
/*     */     try {
/* 105 */       this.share.var.setValue(obj, word, val);
/*     */     }
/* 107 */     catch (EvalException e) {
/*     */       
/* 109 */       throw e;
/*     */     }
/* 111 */     catch (Exception e) {
/*     */       
/* 113 */       throw new EvalException(2302, toString(), this.string, pos, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected AbstractExpression replace() {
/* 120 */     this.expl = this.expl.replaceVar();
/*     */     
/* 122 */     return this.share.repl.replace2(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected AbstractExpression replaceVar() {
/* 128 */     this.expl = this.expl.replaceVar();
/*     */     
/* 130 */     return this.share.repl.replaceVar2(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 136 */     StringBuffer sb = new StringBuffer();
/* 137 */     sb.append(this.expl.toString());
/* 138 */     sb.append('.');
/* 139 */     sb.append(this.expr.toString());
/* 140 */     return sb.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\eval\eval\exp\FieldExpression.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */