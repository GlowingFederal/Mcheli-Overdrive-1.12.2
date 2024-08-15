/*     */ package mcheli.eval.eval;
/*     */ 
/*     */ import mcheli.eval.eval.exp.AbstractExpression;
/*     */ import mcheli.eval.eval.func.Function;
/*     */ import mcheli.eval.eval.oper.Operator;
/*     */ import mcheli.eval.eval.ref.Refactor;
/*     */ import mcheli.eval.eval.repl.Replace;
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
/*     */ public abstract class Expression
/*     */ {
/*     */   public Variable var;
/*     */   public Function func;
/*     */   public Operator oper;
/*     */   public Search srch;
/*     */   public Replace repl;
/*     */   protected AbstractExpression ae;
/*     */   
/*     */   public static Expression parse(String str) {
/*  28 */     return ExpRuleFactory.getDefaultRule().parse(str);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setVariable(Variable var) {
/*  33 */     this.var = var;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setFunction(Function func) {
/*  38 */     this.func = func;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setOperator(Operator oper) {
/*  43 */     this.oper = oper;
/*     */   }
/*     */ 
/*     */   
/*     */   public abstract long evalLong();
/*     */ 
/*     */   
/*     */   public abstract double evalDouble();
/*     */   
/*     */   public abstract Object eval();
/*     */   
/*     */   public abstract void optimizeLong(Variable paramVariable);
/*     */   
/*     */   public abstract void optimizeDouble(Variable paramVariable);
/*     */   
/*     */   public abstract void optimize(Variable paramVariable, Operator paramOperator);
/*     */   
/*     */   public abstract void search(Search paramSearch);
/*     */   
/*     */   public abstract void refactorName(Refactor paramRefactor);
/*     */   
/*     */   public abstract void refactorFunc(Refactor paramRefactor, Rule paramRule);
/*     */   
/*     */   public abstract Expression dup();
/*     */   
/*     */   public boolean equals(Object obj) {
/*  69 */     if (obj instanceof Expression) {
/*     */       
/*  71 */       AbstractExpression e = ((Expression)obj).ae;
/*  72 */       if (this.ae == null && e == null)
/*  73 */         return true; 
/*  74 */       if (this.ae == null || e == null)
/*  75 */         return false; 
/*  76 */       return this.ae.equals(e);
/*     */     } 
/*  78 */     return super.equals(obj);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  84 */     if (this.ae == null)
/*  85 */       return 0; 
/*  86 */     return this.ae.hashCode();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean same(Expression obj) {
/*  91 */     AbstractExpression e = obj.ae;
/*  92 */     if (this.ae == null)
/*     */     {
/*  94 */       return (e == null);
/*     */     }
/*  96 */     return this.ae.same(e);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 101 */     return (this.ae == null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 107 */     if (this.ae == null)
/* 108 */       return ""; 
/* 109 */     return this.ae.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\eval\eval\Expression.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */