/*     */ package mcheli.eval.eval.exp;
/*     */ 
/*     */ import java.util.List;
/*     */ import mcheli.eval.eval.EvalException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractExpression
/*     */ {
/*     */   public static final int TRUE = 1;
/*     */   public static final int FALSE = 0;
/*     */   
/*     */   protected final boolean isTrue(boolean lng) {
/*  20 */     if (lng)
/*     */     {
/*  22 */       return (evalLong() != 0L);
/*     */     }
/*  24 */     return (evalDouble() != 0.0D);
/*     */   }
/*     */   
/*  27 */   protected String string = null;
/*     */   
/*  29 */   protected int pos = -1;
/*     */   
/*     */   private String ope1;
/*     */   
/*     */   private String ope2;
/*     */   
/*     */   public ShareExpValue share;
/*     */   
/*     */   protected int prio;
/*     */ 
/*     */   
/*     */   protected AbstractExpression() {}
/*     */   
/*     */   protected AbstractExpression(AbstractExpression from, ShareExpValue s) {
/*  43 */     this.string = from.string;
/*  44 */     this.pos = from.pos;
/*  45 */     this.prio = from.prio;
/*  46 */     if (s != null) {
/*     */       
/*  48 */       this.share = s;
/*     */     }
/*     */     else {
/*     */       
/*  52 */       this.share = from.share;
/*     */     } 
/*  54 */     this.ope1 = from.ope1;
/*  55 */     this.ope2 = from.ope2;
/*     */   }
/*     */ 
/*     */   
/*     */   public abstract AbstractExpression dup(ShareExpValue paramShareExpValue);
/*     */   
/*     */   public final String getOperator() {
/*  62 */     return this.ope1;
/*     */   }
/*     */ 
/*     */   
/*     */   public final String getEndOperator() {
/*  67 */     return this.ope2;
/*     */   }
/*     */ 
/*     */   
/*     */   public final void setOperator(String ope) {
/*  72 */     this.ope1 = ope;
/*     */   }
/*     */ 
/*     */   
/*     */   public final void setEndOperator(String ope) {
/*  77 */     this.ope2 = ope;
/*     */   }
/*     */ 
/*     */   
/*     */   protected String getWord() {
/*  82 */     return getOperator();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setWord(String word) {
/*  87 */     throw new EvalException(2001, word, this.string, this.pos, null);
/*     */   }
/*     */ 
/*     */   
/*     */   protected abstract int getCols();
/*     */   
/*     */   protected final void setPos(String string, int pos) {
/*  94 */     this.string = string;
/*  95 */     this.pos = pos;
/*     */   }
/*     */ 
/*     */   
/*     */   protected abstract int getFirstPos();
/*     */   
/*     */   public final void setPriority(int prio) {
/* 102 */     this.prio = prio;
/*     */   }
/*     */ 
/*     */   
/*     */   protected final int getPriority() {
/* 107 */     return this.prio;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void let(Object val, int pos) {
/* 112 */     throw new EvalException(2004, toString(), this.string, pos, null);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void let(long val, int pos) {
/* 117 */     let(new Long(val), pos);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void let(double val, int pos) {
/* 122 */     let(new Double(val), pos);
/*     */   }
/*     */ 
/*     */   
/*     */   protected Object getVariable() {
/* 127 */     String word = toString();
/* 128 */     throw new EvalException(2002, word, this.string, this.pos, null);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void evalArgsLong(List<Long> args) {
/* 133 */     args.add(new Long(evalLong()));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void evalArgsDouble(List<Double> args) {
/* 138 */     args.add(new Double(evalDouble()));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void evalArgsObject(List<Object> args) {
/* 143 */     args.add(evalObject());
/*     */   }
/*     */ 
/*     */   
/*     */   public abstract long evalLong();
/*     */ 
/*     */   
/*     */   public abstract double evalDouble();
/*     */ 
/*     */   
/*     */   public abstract Object evalObject();
/*     */   
/*     */   protected abstract void search();
/*     */   
/*     */   protected abstract AbstractExpression replace();
/*     */   
/*     */   protected abstract AbstractExpression replaceVar();
/*     */   
/*     */   public abstract boolean equals(Object paramObject);
/*     */   
/*     */   public abstract int hashCode();
/*     */   
/*     */   public boolean same(AbstractExpression exp) {
/* 166 */     return (same(getOperator(), exp.getOperator()) && same(getEndOperator(), exp.getEndOperator()) && 
/* 167 */       equals(exp));
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean same(String str1, String str2) {
/* 172 */     if (str1 == null)
/*     */     {
/* 174 */       return (str2 == null);
/*     */     }
/* 176 */     return str1.equals(str2);
/*     */   }
/*     */   
/*     */   public abstract void dump(int paramInt);
/*     */   
/*     */   public abstract String toString();
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\eval\eval\exp\AbstractExpression.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */