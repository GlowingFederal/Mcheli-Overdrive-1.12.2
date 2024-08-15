/*     */ package mcheli.eval.eval.exp;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Col1Expression
/*     */   extends AbstractExpression
/*     */ {
/*     */   protected AbstractExpression exp;
/*     */   
/*     */   public static AbstractExpression create(AbstractExpression exp, String string, int pos, AbstractExpression x) {
/*  15 */     Col1Expression n = (Col1Expression)exp;
/*  16 */     n.setExpression(x);
/*  17 */     n.setPos(string, pos);
/*  18 */     return n;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Col1Expression() {}
/*     */ 
/*     */   
/*     */   protected Col1Expression(Col1Expression from, ShareExpValue s) {
/*  27 */     super(from, s);
/*  28 */     if (from.exp != null)
/*     */     {
/*  30 */       this.exp = from.exp.dup(s);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void setExpression(AbstractExpression x) {
/*  36 */     this.exp = x;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected final int getCols() {
/*  42 */     return 1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected final int getFirstPos() {
/*  48 */     return this.exp.getFirstPos();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public long evalLong() {
/*  54 */     return operateLong(this.exp.evalLong());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double evalDouble() {
/*  60 */     return operateDouble(this.exp.evalDouble());
/*     */   }
/*     */ 
/*     */   
/*     */   protected abstract long operateLong(long paramLong);
/*     */ 
/*     */   
/*     */   protected abstract double operateDouble(double paramDouble);
/*     */   
/*     */   protected void search() {
/*  70 */     this.share.srch.search(this);
/*  71 */     if (this.share.srch.end()) {
/*     */       return;
/*     */     }
/*     */     
/*  75 */     if (this.share.srch.search1_begin(this))
/*     */       return; 
/*  77 */     if (this.share.srch.end()) {
/*     */       return;
/*     */     }
/*     */     
/*  81 */     this.exp.search();
/*  82 */     if (this.share.srch.end()) {
/*     */       return;
/*     */     }
/*     */     
/*  86 */     this.share.srch.search1_end(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected AbstractExpression replace() {
/*  92 */     this.exp = this.exp.replace();
/*  93 */     return this.share.repl.replace1(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected AbstractExpression replaceVar() {
/*  99 */     this.exp = this.exp.replaceVar();
/* 100 */     return this.share.repl.replaceVar1(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 106 */     if (obj instanceof Col1Expression) {
/*     */       
/* 108 */       Col1Expression e = (Col1Expression)obj;
/* 109 */       if (getClass() == e.getClass()) {
/*     */         
/* 111 */         if (this.exp == null)
/* 112 */           return (e.exp == null); 
/* 113 */         if (e.exp == null)
/* 114 */           return false; 
/* 115 */         return this.exp.equals(e.exp);
/*     */       } 
/*     */     } 
/* 118 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 124 */     return getClass().hashCode() ^ this.exp.hashCode();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void dump(int n) {
/* 130 */     StringBuffer sb = new StringBuffer();
/* 131 */     for (int i = 0; i < n; i++)
/*     */     {
/* 133 */       sb.append(' ');
/*     */     }
/* 135 */     sb.append(getOperator());
/* 136 */     System.out.println(sb.toString());
/* 137 */     if (this.exp != null)
/*     */     {
/* 139 */       this.exp.dump(n + 1);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 146 */     if (this.exp == null)
/*     */     {
/* 148 */       return getOperator();
/*     */     }
/* 150 */     StringBuffer sb = new StringBuffer();
/* 151 */     if (this.exp.getPriority() > this.prio) {
/*     */       
/* 153 */       sb.append(getOperator());
/* 154 */       sb.append(this.exp.toString());
/*     */     }
/* 156 */     else if (this.exp.getPriority() == this.prio) {
/*     */       
/* 158 */       sb.append(getOperator());
/* 159 */       sb.append(' ');
/* 160 */       sb.append(this.exp.toString());
/*     */     }
/*     */     else {
/*     */       
/* 164 */       sb.append(getOperator());
/* 165 */       sb.append(this.share.paren.getOperator());
/* 166 */       sb.append(this.exp.toString());
/* 167 */       sb.append(this.share.paren.getEndOperator());
/*     */     } 
/* 169 */     return sb.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\eval\eval\exp\Col1Expression.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */