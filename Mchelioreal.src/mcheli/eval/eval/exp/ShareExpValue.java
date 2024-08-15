/*     */ package mcheli.eval.eval.exp;
/*     */ 
/*     */ import mcheli.eval.eval.Expression;
/*     */ import mcheli.eval.eval.Rule;
/*     */ import mcheli.eval.eval.func.Function;
/*     */ import mcheli.eval.eval.func.InvokeFunction;
/*     */ import mcheli.eval.eval.oper.JavaExOperator;
/*     */ import mcheli.eval.eval.oper.Operator;
/*     */ import mcheli.eval.eval.ref.Refactor;
/*     */ import mcheli.eval.eval.repl.Replace;
/*     */ import mcheli.eval.eval.srch.Search;
/*     */ import mcheli.eval.eval.var.MapVariable;
/*     */ import mcheli.eval.eval.var.Variable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ShareExpValue
/*     */   extends Expression
/*     */ {
/*     */   public AbstractExpression paren;
/*     */   
/*     */   public void setAbstractExpression(AbstractExpression ae) {
/*  26 */     this.ae = ae;
/*     */   }
/*     */ 
/*     */   
/*     */   public void initVar() {
/*  31 */     if (this.var == null)
/*     */     {
/*  33 */       this.var = (Variable)new MapVariable();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void initOper() {
/*  39 */     if (this.oper == null)
/*     */     {
/*  41 */       this.oper = (Operator)new JavaExOperator();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void initFunc() {
/*  47 */     if (this.func == null)
/*     */     {
/*  49 */       this.func = (Function)new InvokeFunction();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public long evalLong() {
/*  56 */     initVar();
/*  57 */     initFunc();
/*  58 */     return this.ae.evalLong();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double evalDouble() {
/*  64 */     initVar();
/*  65 */     initFunc();
/*  66 */     return this.ae.evalDouble();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object eval() {
/*  72 */     initVar();
/*  73 */     initOper();
/*  74 */     initFunc();
/*  75 */     return this.ae.evalObject();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void optimizeLong(Variable var) {
/*  81 */     optimize(var, (Replace)new OptimizeLong());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void optimizeDouble(Variable var) {
/*  87 */     optimize(var, (Replace)new OptimizeDouble());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void optimize(Variable var, Operator oper) {
/*  93 */     Operator bak = this.oper;
/*  94 */     this.oper = oper;
/*     */     
/*     */     try {
/*  97 */       optimize(var, (Replace)new OptimizeObject());
/*     */     }
/*     */     finally {
/*     */       
/* 101 */       this.oper = bak;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void optimize(Variable var, Replace repl) {
/*     */     MapVariable mapVariable;
/* 107 */     Variable bak = this.var;
/* 108 */     if (var == null)
/*     */     {
/* 110 */       mapVariable = new MapVariable();
/*     */     }
/* 112 */     this.var = (Variable)mapVariable;
/* 113 */     this.repl = repl;
/*     */     
/*     */     try {
/* 116 */       this.ae = this.ae.replace();
/*     */     }
/*     */     finally {
/*     */       
/* 120 */       this.var = bak;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void search(Search srch) {
/* 127 */     if (srch == null)
/*     */     {
/* 129 */       throw new NullPointerException();
/*     */     }
/* 131 */     this.srch = srch;
/* 132 */     this.ae.search();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void refactorName(Refactor ref) {
/* 138 */     if (ref == null)
/*     */     {
/* 140 */       throw new NullPointerException();
/*     */     }
/* 142 */     this.srch = (Search)new Search4RefactorName(ref);
/* 143 */     this.ae.search();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void refactorFunc(Refactor ref, Rule rule) {
/* 149 */     if (ref == null)
/*     */     {
/* 151 */       throw new NullPointerException();
/*     */     }
/* 153 */     this.repl = (Replace)new Replace4RefactorGetter(ref, rule);
/* 154 */     this.ae.replace();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean same(Expression obj) {
/* 160 */     if (obj instanceof ShareExpValue) {
/*     */       
/* 162 */       AbstractExpression p = ((ShareExpValue)obj).paren;
/* 163 */       return (this.paren.same(p) && super.same(obj));
/*     */     } 
/* 165 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Expression dup() {
/* 171 */     ShareExpValue n = new ShareExpValue();
/* 172 */     n.ae = this.ae.dup(n);
/* 173 */     n.paren = this.paren.dup(n);
/* 174 */     return n;
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\eval\eval\exp\ShareExpValue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */