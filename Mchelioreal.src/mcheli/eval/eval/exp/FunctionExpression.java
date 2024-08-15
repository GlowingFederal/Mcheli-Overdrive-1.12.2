/*     */ package mcheli.eval.eval.exp;
/*     */ 
/*     */ import java.util.ArrayList;
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
/*     */ public class FunctionExpression
/*     */   extends Col1Expression
/*     */ {
/*     */   protected AbstractExpression target;
/*     */   String name;
/*     */   
/*     */   public static AbstractExpression create(AbstractExpression x, AbstractExpression args, int prio, ShareExpValue share) {
/*     */     AbstractExpression obj;
/*  23 */     if (x instanceof VariableExpression) {
/*     */       
/*  25 */       obj = null;
/*     */     }
/*  27 */     else if (x instanceof FieldExpression) {
/*     */       
/*  29 */       FieldExpression fieldExpression = (FieldExpression)x;
/*     */       
/*  31 */       obj = fieldExpression.expl;
/*  32 */       x = fieldExpression.expr;
/*     */     }
/*     */     else {
/*     */       
/*  36 */       throw new EvalException(1101, x.toString(), x.string, x.pos, null);
/*     */     } 
/*     */     
/*  39 */     String name = x.getWord();
/*  40 */     FunctionExpression f = new FunctionExpression(obj, name);
/*  41 */     f.setExpression(args);
/*  42 */     f.setPos(x.string, x.pos);
/*  43 */     f.setPriority(prio);
/*  44 */     f.share = share;
/*  45 */     return f;
/*     */   }
/*     */ 
/*     */   
/*     */   public FunctionExpression() {
/*  50 */     setOperator("(");
/*  51 */     setEndOperator(")");
/*     */   }
/*     */ 
/*     */   
/*     */   public FunctionExpression(AbstractExpression obj, String word) {
/*  56 */     this();
/*  57 */     this.target = obj;
/*  58 */     this.name = word;
/*     */   }
/*     */ 
/*     */   
/*     */   protected FunctionExpression(FunctionExpression from, ShareExpValue s) {
/*  63 */     super(from, s);
/*  64 */     if (from.target != null)
/*     */     {
/*  66 */       this.target = from.target.dup(s);
/*     */     }
/*  68 */     this.name = from.name;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AbstractExpression dup(ShareExpValue s) {
/*  74 */     return new FunctionExpression(this, s);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public long evalLong() {
/*  80 */     Object obj = null;
/*  81 */     if (this.target != null)
/*     */     {
/*  83 */       obj = this.target.getVariable();
/*     */     }
/*  85 */     List<Long> args = evalArgsLong();
/*     */     
/*     */     try {
/*  88 */       Long[] arr = new Long[args.size()];
/*  89 */       return this.share.func.evalLong(obj, this.name, args.<Long>toArray(arr));
/*     */     }
/*  91 */     catch (EvalException e) {
/*     */       
/*  93 */       throw e;
/*     */     }
/*  95 */     catch (Throwable e) {
/*     */       
/*  97 */       throw new EvalException(2401, this.name, this.string, this.pos, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double evalDouble() {
/* 104 */     Object obj = null;
/* 105 */     if (this.target != null)
/*     */     {
/* 107 */       obj = this.target.getVariable();
/*     */     }
/* 109 */     List<Double> args = evalArgsDouble();
/*     */     
/*     */     try {
/* 112 */       Double[] arr = new Double[args.size()];
/* 113 */       return this.share.func.evalDouble(obj, this.name, args.<Double>toArray(arr));
/*     */     }
/* 115 */     catch (EvalException e) {
/*     */       
/* 117 */       throw e;
/*     */     }
/* 119 */     catch (Throwable e) {
/*     */       
/* 121 */       throw new EvalException(2401, this.name, this.string, this.pos, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object evalObject() {
/* 128 */     Object obj = null;
/* 129 */     if (this.target != null)
/*     */     {
/* 131 */       obj = this.target.getVariable();
/*     */     }
/* 133 */     List<Object> args = evalArgsObject();
/*     */     
/*     */     try {
/* 136 */       Object[] arr = new Object[args.size()];
/* 137 */       return this.share.func.evalObject(obj, this.name, args.toArray(arr));
/*     */     }
/* 139 */     catch (EvalException e) {
/*     */       
/* 141 */       throw e;
/*     */     }
/* 143 */     catch (Throwable e) {
/*     */       
/* 145 */       throw new EvalException(2401, this.name, this.string, this.pos, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private List<Long> evalArgsLong() {
/* 151 */     List<Long> args = new ArrayList<>();
/* 152 */     if (this.exp != null)
/*     */     {
/* 154 */       this.exp.evalArgsLong(args);
/*     */     }
/* 156 */     return args;
/*     */   }
/*     */ 
/*     */   
/*     */   private List<Double> evalArgsDouble() {
/* 161 */     List<Double> args = new ArrayList<>();
/* 162 */     if (this.exp != null)
/*     */     {
/* 164 */       this.exp.evalArgsDouble(args);
/*     */     }
/* 166 */     return args;
/*     */   }
/*     */ 
/*     */   
/*     */   private List<Object> evalArgsObject() {
/* 171 */     List<Object> args = new ArrayList();
/* 172 */     if (this.exp != null)
/*     */     {
/* 174 */       this.exp.evalArgsObject(args);
/*     */     }
/* 176 */     return args;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Object getVariable() {
/* 182 */     return evalObject();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected long operateLong(long val) {
/* 188 */     throw new RuntimeException("この関数が呼ばれてはいけない。サブクラスで実装要");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected double operateDouble(double val) {
/* 194 */     throw new RuntimeException("この関数が呼ばれてはいけない。サブクラスで実装要");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void search() {
/* 200 */     this.share.srch.search(this);
/* 201 */     if (this.share.srch.end()) {
/*     */       return;
/*     */     }
/*     */     
/* 205 */     if (this.share.srch.searchFunc_begin(this))
/*     */       return; 
/* 207 */     if (this.share.srch.end()) {
/*     */       return;
/*     */     }
/*     */     
/* 211 */     if (this.target != null) {
/*     */       
/* 213 */       this.target.search();
/* 214 */       if (this.share.srch.end()) {
/*     */         return;
/*     */       }
/*     */     } 
/*     */     
/* 219 */     if (this.share.srch.searchFunc_2(this))
/*     */       return; 
/* 221 */     if (this.share.srch.end()) {
/*     */       return;
/*     */     }
/*     */     
/* 225 */     if (this.exp != null) {
/*     */       
/* 227 */       this.exp.search();
/* 228 */       if (this.share.srch.end()) {
/*     */         return;
/*     */       }
/*     */     } 
/*     */     
/* 233 */     this.share.srch.searchFunc_end(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected AbstractExpression replace() {
/* 239 */     if (this.target != null)
/*     */     {
/* 241 */       this.target = this.target.replace();
/*     */     }
/* 243 */     if (this.exp != null)
/*     */     {
/* 245 */       this.exp = this.exp.replace();
/*     */     }
/* 247 */     return this.share.repl.replaceFunc(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 253 */     if (obj instanceof FunctionExpression) {
/*     */       
/* 255 */       FunctionExpression e = (FunctionExpression)obj;
/* 256 */       return (this.name.equals(e.name) && equals(this.target, e.target) && equals(this.exp, e.exp));
/*     */     } 
/*     */     
/* 259 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean equals(AbstractExpression e1, AbstractExpression e2) {
/* 264 */     if (e1 == null)
/*     */     {
/* 266 */       return (e2 == null);
/*     */     }
/* 268 */     if (e2 == null)
/* 269 */       return false; 
/* 270 */     return e1.equals(e2);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 276 */     int t = (this.target != null) ? this.target.hashCode() : 0;
/* 277 */     int a = (this.exp != null) ? this.exp.hashCode() : 0;
/* 278 */     return this.name.hashCode() ^ t ^ a * 2;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 284 */     StringBuffer sb = new StringBuffer();
/* 285 */     if (this.target != null) {
/*     */       
/* 287 */       sb.append(this.target.toString());
/* 288 */       sb.append('.');
/*     */     } 
/* 290 */     sb.append(this.name);
/* 291 */     sb.append('(');
/* 292 */     if (this.exp != null)
/*     */     {
/* 294 */       sb.append(this.exp.toString());
/*     */     }
/* 296 */     sb.append(')');
/* 297 */     return sb.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\eval\eval\exp\FunctionExpression.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */