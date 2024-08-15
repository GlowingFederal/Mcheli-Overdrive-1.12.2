/*     */ package mcheli.eval.eval.exp;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Col3Expression
/*     */   extends AbstractExpression
/*     */ {
/*     */   protected AbstractExpression exp1;
/*     */   protected AbstractExpression exp2;
/*     */   protected AbstractExpression exp3;
/*     */   
/*     */   public static AbstractExpression create(AbstractExpression exp, String string, int pos, AbstractExpression x, AbstractExpression y, AbstractExpression z) {
/*  18 */     Col3Expression n = (Col3Expression)exp;
/*  19 */     n.setExpression(x, y, z);
/*  20 */     n.setPos(string, pos);
/*  21 */     return n;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Col3Expression() {}
/*     */ 
/*     */   
/*     */   protected Col3Expression(Col3Expression from, ShareExpValue s) {
/*  30 */     super(from, s);
/*  31 */     if (from.exp1 != null)
/*  32 */       this.exp1 = from.exp1.dup(s); 
/*  33 */     if (from.exp2 != null)
/*  34 */       this.exp2 = from.exp2.dup(s); 
/*  35 */     if (from.exp3 != null)
/*     */     {
/*  37 */       this.exp3 = from.exp3.dup(s);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public final void setExpression(AbstractExpression x, AbstractExpression y, AbstractExpression z) {
/*  43 */     this.exp1 = x;
/*  44 */     this.exp2 = y;
/*  45 */     this.exp3 = z;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected final int getCols() {
/*  51 */     return 3;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getFirstPos() {
/*  57 */     return this.exp1.getFirstPos();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void search() {
/*  63 */     this.share.srch.search(this);
/*  64 */     if (this.share.srch.end()) {
/*     */       return;
/*     */     }
/*     */     
/*  68 */     if (this.share.srch.search3_begin(this))
/*     */       return; 
/*  70 */     if (this.share.srch.end()) {
/*     */       return;
/*     */     }
/*     */     
/*  74 */     this.exp1.search();
/*  75 */     if (this.share.srch.end()) {
/*     */       return;
/*     */     }
/*     */     
/*  79 */     if (this.share.srch.search3_2(this))
/*     */       return; 
/*  81 */     if (this.share.srch.end()) {
/*     */       return;
/*     */     }
/*     */     
/*  85 */     this.exp2.search();
/*  86 */     if (this.share.srch.end()) {
/*     */       return;
/*     */     }
/*     */     
/*  90 */     if (this.share.srch.search3_3(this))
/*     */       return; 
/*  92 */     if (this.share.srch.end()) {
/*     */       return;
/*     */     }
/*     */     
/*  96 */     this.exp3.search();
/*  97 */     if (this.share.srch.end()) {
/*     */       return;
/*     */     }
/*     */     
/* 101 */     this.share.srch.search3_end(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected AbstractExpression replace() {
/* 107 */     this.exp1 = this.exp1.replace();
/* 108 */     this.exp2 = this.exp2.replace();
/* 109 */     this.exp3 = this.exp3.replace();
/* 110 */     return this.share.repl.replace3(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected AbstractExpression replaceVar() {
/* 116 */     this.exp1 = this.exp1.replace();
/* 117 */     this.exp2 = this.exp2.replaceVar();
/* 118 */     this.exp3 = this.exp3.replaceVar();
/* 119 */     return this.share.repl.replaceVar3(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 125 */     if (obj instanceof Col3Expression) {
/*     */       
/* 127 */       Col3Expression e = (Col3Expression)obj;
/* 128 */       if (getClass() == e.getClass())
/*     */       {
/* 130 */         return (this.exp1.equals(e.exp1) && this.exp2.equals(e.exp2) && this.exp3.equals(e.exp3));
/*     */       }
/*     */     } 
/*     */     
/* 134 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 140 */     return getClass().hashCode() ^ this.exp1.hashCode() ^ this.exp2.hashCode() * 2 ^ this.exp3.hashCode() * 3;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void dump(int n) {
/* 146 */     StringBuffer sb = new StringBuffer();
/* 147 */     for (int i = 0; i < n; i++)
/*     */     {
/* 149 */       sb.append(' ');
/*     */     }
/* 151 */     sb.append(getOperator());
/* 152 */     System.out.println(sb.toString());
/* 153 */     this.exp1.dump(n + 1);
/* 154 */     this.exp2.dump(n + 1);
/* 155 */     this.exp3.dump(n + 1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 161 */     StringBuffer sb = new StringBuffer();
/* 162 */     if (this.exp1.getPriority() <= this.prio || this.exp1.getCols() >= 2) {
/*     */       
/* 164 */       sb.append(this.share.paren.getOperator());
/* 165 */       sb.append(this.exp1.toString());
/* 166 */       sb.append(this.share.paren.getEndOperator());
/*     */     }
/*     */     else {
/*     */       
/* 170 */       sb.append(this.exp1.toString());
/*     */     } 
/* 172 */     sb.append(' ');
/* 173 */     sb.append(getOperator());
/* 174 */     sb.append(' ');
/* 175 */     if (this.exp2.getPriority() <= this.prio || this.exp2.getCols() >= 2) {
/*     */       
/* 177 */       sb.append(this.share.paren.getOperator());
/* 178 */       sb.append(this.exp2.toString());
/* 179 */       sb.append(this.share.paren.getEndOperator());
/*     */     }
/*     */     else {
/*     */       
/* 183 */       sb.append(this.exp2.toString());
/*     */     } 
/* 185 */     sb.append(' ');
/* 186 */     sb.append(getEndOperator());
/* 187 */     sb.append(' ');
/* 188 */     if (this.exp3.getPriority() <= this.prio || this.exp3.getCols() >= 2) {
/*     */       
/* 190 */       sb.append(this.share.paren.getOperator());
/* 191 */       sb.append(this.exp3.toString());
/* 192 */       sb.append(this.share.paren.getEndOperator());
/*     */     }
/*     */     else {
/*     */       
/* 196 */       sb.append(this.exp3.toString());
/*     */     } 
/* 198 */     return sb.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\eval\eval\exp\Col3Expression.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */