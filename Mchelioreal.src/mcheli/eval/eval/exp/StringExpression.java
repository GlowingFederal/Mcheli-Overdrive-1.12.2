/*     */ package mcheli.eval.eval.exp;
/*     */ 
/*     */ import mcheli.eval.eval.EvalException;
/*     */ import mcheli.eval.eval.lex.Lex;
/*     */ import mcheli.eval.util.CharUtil;
/*     */ import mcheli.eval.util.NumberUtil;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StringExpression
/*     */   extends WordExpression
/*     */ {
/*     */   public static AbstractExpression create(Lex lex, int prio) {
/*  18 */     String str = lex.getWord();
/*  19 */     str = CharUtil.escapeString(str, 1, str.length() - 2);
/*  20 */     AbstractExpression exp = new StringExpression(str);
/*  21 */     exp.setPos(lex.getString(), lex.getPos());
/*  22 */     exp.setPriority(prio);
/*  23 */     exp.share = lex.getShare();
/*  24 */     return exp;
/*     */   }
/*     */ 
/*     */   
/*     */   public StringExpression(String str) {
/*  29 */     super(str);
/*  30 */     setOperator("\"");
/*  31 */     setEndOperator("\"");
/*     */   }
/*     */ 
/*     */   
/*     */   protected StringExpression(StringExpression from, ShareExpValue s) {
/*  36 */     super(from, s);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AbstractExpression dup(ShareExpValue s) {
/*  42 */     return new StringExpression(this, s);
/*     */   }
/*     */ 
/*     */   
/*     */   public static StringExpression create(AbstractExpression from, String word) {
/*  47 */     StringExpression n = new StringExpression(word);
/*  48 */     n.string = from.string;
/*  49 */     n.pos = from.pos;
/*  50 */     n.prio = from.prio;
/*  51 */     n.share = from.share;
/*  52 */     return n;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long evalLong() {
/*     */     try {
/*  60 */       return NumberUtil.parseLong(this.word);
/*     */     }
/*  62 */     catch (Exception e) {
/*     */ 
/*     */       
/*     */       try {
/*  66 */         return Long.parseLong(this.word);
/*     */       }
/*  68 */       catch (Exception e1) {
/*     */ 
/*     */         
/*     */         try {
/*  72 */           return (long)Double.parseDouble(this.word);
/*     */         }
/*  74 */         catch (Exception e2) {
/*     */           
/*  76 */           throw new EvalException(2003, this.word, this.string, this.pos, e2);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double evalDouble() {
/*     */     try {
/*  87 */       return Double.parseDouble(this.word);
/*     */     }
/*  89 */     catch (Exception e) {
/*     */ 
/*     */       
/*     */       try {
/*  93 */         return NumberUtil.parseLong(this.word);
/*     */       }
/*  95 */       catch (Exception e2) {
/*     */         
/*  97 */         throw new EvalException(2003, this.word, this.string, this.pos, e);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object evalObject() {
/* 105 */     return this.word;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 111 */     if (obj instanceof StringExpression) {
/*     */       
/* 113 */       StringExpression e = (StringExpression)obj;
/* 114 */       return this.word.equals(e.word);
/*     */     } 
/* 116 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 122 */     return this.word.hashCode();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 128 */     StringBuffer sb = new StringBuffer();
/* 129 */     sb.append(getOperator());
/* 130 */     sb.append(this.word);
/* 131 */     sb.append(getEndOperator());
/* 132 */     return sb.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\eval\eval\exp\StringExpression.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */