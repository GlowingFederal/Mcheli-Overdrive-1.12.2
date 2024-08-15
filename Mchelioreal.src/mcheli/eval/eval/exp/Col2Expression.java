/*     */ package mcheli.eval.eval.exp;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Col2Expression
/*     */   extends AbstractExpression
/*     */ {
/*     */   public AbstractExpression expl;
/*     */   public AbstractExpression expr;
/*     */   
/*     */   public static AbstractExpression create(AbstractExpression exp, String string, int pos, AbstractExpression x, AbstractExpression y) {
/*  17 */     Col2Expression n = (Col2Expression)exp;
/*  18 */     n.setExpression(x, y);
/*  19 */     n.setPos(string, pos);
/*  20 */     return n;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Col2Expression() {}
/*     */ 
/*     */   
/*     */   protected Col2Expression(Col2Expression from, ShareExpValue s) {
/*  29 */     super(from, s);
/*  30 */     if (from.expl != null)
/*  31 */       this.expl = from.expl.dup(s); 
/*  32 */     if (from.expr != null)
/*     */     {
/*  34 */       this.expr = from.expr.dup(s);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public final void setExpression(AbstractExpression x, AbstractExpression y) {
/*  40 */     this.expl = x;
/*  41 */     this.expr = y;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected final int getCols() {
/*  47 */     return 2;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected final int getFirstPos() {
/*  53 */     return this.expl.getFirstPos();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public long evalLong() {
/*  59 */     return operateLong(this.expl.evalLong(), this.expr.evalLong());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double evalDouble() {
/*  65 */     return operateDouble(this.expl.evalDouble(), this.expr.evalDouble());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object evalObject() {
/*  71 */     return operateObject(this.expl.evalObject(), this.expr.evalObject());
/*     */   }
/*     */ 
/*     */   
/*     */   protected abstract long operateLong(long paramLong1, long paramLong2);
/*     */ 
/*     */   
/*     */   protected abstract double operateDouble(double paramDouble1, double paramDouble2);
/*     */   
/*     */   protected abstract Object operateObject(Object paramObject1, Object paramObject2);
/*     */   
/*     */   protected void search() {
/*  83 */     this.share.srch.search(this);
/*  84 */     if (this.share.srch.end()) {
/*     */       return;
/*     */     }
/*     */     
/*  88 */     if (this.share.srch.search2_begin(this))
/*     */       return; 
/*  90 */     if (this.share.srch.end()) {
/*     */       return;
/*     */     }
/*     */     
/*  94 */     this.expl.search();
/*  95 */     if (this.share.srch.end()) {
/*     */       return;
/*     */     }
/*     */     
/*  99 */     if (this.share.srch.search2_2(this))
/*     */       return; 
/* 101 */     if (this.share.srch.end()) {
/*     */       return;
/*     */     }
/*     */     
/* 105 */     this.expr.search();
/* 106 */     if (this.share.srch.end()) {
/*     */       return;
/*     */     }
/*     */     
/* 110 */     this.share.srch.search2_end(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected AbstractExpression replace() {
/* 116 */     this.expl = this.expl.replace();
/* 117 */     this.expr = this.expr.replace();
/* 118 */     return this.share.repl.replace2(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected AbstractExpression replaceVar() {
/* 124 */     this.expl = this.expl.replaceVar();
/* 125 */     this.expr = this.expr.replaceVar();
/* 126 */     return this.share.repl.replaceVar2(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 132 */     if (obj instanceof Col2Expression) {
/*     */       
/* 134 */       Col2Expression e = (Col2Expression)obj;
/* 135 */       if (getClass() == e.getClass())
/*     */       {
/* 137 */         return (this.expl.equals(e.expl) && this.expr.equals(e.expr));
/*     */       }
/*     */     } 
/* 140 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 146 */     return getClass().hashCode() ^ this.expl.hashCode() ^ this.expr.hashCode() * 2;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void dump(int n) {
/* 152 */     StringBuffer sb = new StringBuffer();
/* 153 */     for (int i = 0; i < n; i++)
/*     */     {
/* 155 */       sb.append(' ');
/*     */     }
/* 157 */     sb.append(getOperator());
/* 158 */     System.out.println(sb.toString());
/* 159 */     this.expl.dump(n + 1);
/* 160 */     this.expr.dump(n + 1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 166 */     StringBuffer sb = new StringBuffer();
/* 167 */     if (this.expl.getPriority() < this.prio) {
/*     */       
/* 169 */       sb.append(this.share.paren.getOperator());
/* 170 */       sb.append(this.expl.toString());
/* 171 */       sb.append(this.share.paren.getEndOperator());
/*     */     }
/*     */     else {
/*     */       
/* 175 */       sb.append(this.expl.toString());
/*     */     } 
/* 177 */     sb.append(toStringLeftSpace());
/* 178 */     sb.append(getOperator());
/* 179 */     sb.append(' ');
/* 180 */     if (this.expr.getPriority() < this.prio) {
/*     */       
/* 182 */       sb.append(this.share.paren.getOperator());
/* 183 */       sb.append(this.expr.toString());
/* 184 */       sb.append(this.share.paren.getEndOperator());
/*     */     }
/*     */     else {
/*     */       
/* 188 */       sb.append(this.expr.toString());
/*     */     } 
/* 190 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   protected String toStringLeftSpace() {
/* 195 */     return " ";
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\eval\eval\exp\Col2Expression.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */