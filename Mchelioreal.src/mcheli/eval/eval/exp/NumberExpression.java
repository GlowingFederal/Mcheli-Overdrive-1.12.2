/*     */ package mcheli.eval.eval.exp;
/*     */ 
/*     */ import mcheli.eval.eval.EvalException;
/*     */ import mcheli.eval.eval.lex.Lex;
/*     */ import mcheli.eval.util.NumberUtil;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NumberExpression
/*     */   extends WordExpression
/*     */ {
/*     */   public static AbstractExpression create(Lex lex, int prio) {
/*  17 */     AbstractExpression exp = new NumberExpression(lex.getWord());
/*  18 */     exp.setPos(lex.getString(), lex.getPos());
/*  19 */     exp.setPriority(prio);
/*  20 */     exp.share = lex.getShare();
/*  21 */     return exp;
/*     */   }
/*     */ 
/*     */   
/*     */   public NumberExpression(String str) {
/*  26 */     super(str);
/*     */   }
/*     */ 
/*     */   
/*     */   protected NumberExpression(NumberExpression from, ShareExpValue s) {
/*  31 */     super(from, s);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AbstractExpression dup(ShareExpValue s) {
/*  37 */     return new NumberExpression(this, s);
/*     */   }
/*     */ 
/*     */   
/*     */   public static NumberExpression create(AbstractExpression from, String word) {
/*  42 */     NumberExpression n = new NumberExpression(word);
/*  43 */     n.string = from.string;
/*  44 */     n.pos = from.pos;
/*  45 */     n.prio = from.prio;
/*  46 */     n.share = from.share;
/*  47 */     return n;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long evalLong() {
/*     */     try {
/*  55 */       return NumberUtil.parseLong(this.word);
/*     */     }
/*  57 */     catch (Exception e) {
/*     */ 
/*     */       
/*     */       try {
/*  61 */         return Long.parseLong(this.word);
/*     */       }
/*  63 */       catch (Exception e1) {
/*     */ 
/*     */         
/*     */         try {
/*  67 */           return (long)Double.parseDouble(this.word);
/*     */         }
/*  69 */         catch (Exception e2) {
/*     */           
/*  71 */           throw new EvalException(2003, this.word, this.string, this.pos, e2);
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
/*  82 */       return Double.parseDouble(this.word);
/*     */     }
/*  84 */     catch (Exception e) {
/*     */ 
/*     */       
/*     */       try {
/*  88 */         return NumberUtil.parseLong(this.word);
/*     */       }
/*  90 */       catch (Exception e2) {
/*     */         
/*  92 */         throw new EvalException(2003, this.word, this.string, this.pos, e);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object evalObject() {
/*     */     try {
/* 102 */       return new Long(NumberUtil.parseLong(this.word));
/*     */     }
/* 104 */     catch (Exception e) {
/*     */ 
/*     */       
/*     */       try {
/* 108 */         return Long.valueOf(this.word);
/*     */       }
/* 110 */       catch (Exception e1) {
/*     */ 
/*     */         
/*     */         try {
/* 114 */           return Double.valueOf(this.word);
/*     */         }
/* 116 */         catch (Exception e2) {
/*     */           
/* 118 */           throw new EvalException(2003, this.word, this.string, this.pos, e2);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\eval\eval\exp\NumberExpression.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */