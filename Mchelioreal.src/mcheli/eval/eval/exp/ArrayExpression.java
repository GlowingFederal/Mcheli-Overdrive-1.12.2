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
/*     */ public class ArrayExpression
/*     */   extends Col2OpeExpression
/*     */ {
/*     */   public ArrayExpression() {
/*  15 */     setOperator("[");
/*  16 */     setEndOperator("]");
/*     */   }
/*     */ 
/*     */   
/*     */   protected ArrayExpression(ArrayExpression from, ShareExpValue s) {
/*  21 */     super(from, s);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AbstractExpression dup(ShareExpValue s) {
/*  27 */     return new ArrayExpression(this, s);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long evalLong() {
/*     */     try {
/*  35 */       return this.share.var.evalLong(getVariable());
/*     */     }
/*  37 */     catch (EvalException e) {
/*     */       
/*  39 */       throw e;
/*     */     }
/*  41 */     catch (Exception e) {
/*     */       
/*  43 */       throw new EvalException(2201, toString(), this.string, this.pos, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double evalDouble() {
/*     */     try {
/*  52 */       return this.share.var.evalDouble(getVariable());
/*     */     }
/*  54 */     catch (EvalException e) {
/*     */       
/*  56 */       throw e;
/*     */     }
/*  58 */     catch (Exception e) {
/*     */       
/*  60 */       throw new EvalException(2201, toString(), this.string, this.pos, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object evalObject() {
/*  67 */     return getVariable();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Object getVariable() {
/*  73 */     Object obj = this.expl.getVariable();
/*  74 */     if (obj == null)
/*     */     {
/*  76 */       throw new EvalException(2104, this.expl.toString(), this.string, this.pos, null);
/*     */     }
/*     */     
/*  79 */     int index = (int)this.expr.evalLong();
/*     */     
/*     */     try {
/*  82 */       return this.share.var.getObject(obj, index);
/*     */     }
/*  84 */     catch (EvalException e) {
/*     */       
/*  86 */       throw e;
/*     */     }
/*  88 */     catch (Exception e) {
/*     */       
/*  90 */       throw new EvalException(2201, toString(), this.string, this.pos, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void let(Object val, int pos) {
/*  97 */     Object obj = this.expl.getVariable();
/*  98 */     if (obj == null)
/*     */     {
/* 100 */       throw new EvalException(2104, this.expl.toString(), this.string, pos, null);
/*     */     }
/*     */     
/* 103 */     int index = (int)this.expr.evalLong();
/*     */     
/*     */     try {
/* 106 */       this.share.var.setValue(obj, index, val);
/*     */     }
/* 108 */     catch (EvalException e) {
/*     */       
/* 110 */       throw e;
/*     */     }
/* 112 */     catch (Exception e) {
/*     */       
/* 114 */       throw new EvalException(2202, toString(), this.string, pos, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected AbstractExpression replaceVar() {
/* 121 */     this.expl = this.expl.replaceVar();
/* 122 */     this.expr = this.expr.replace();
/* 123 */     return this.share.repl.replaceVar2(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 129 */     StringBuffer sb = new StringBuffer();
/* 130 */     sb.append(this.expl.toString());
/* 131 */     sb.append('[');
/* 132 */     sb.append(this.expr.toString());
/* 133 */     sb.append(']');
/* 134 */     return sb.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\eval\eval\exp\ArrayExpression.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */