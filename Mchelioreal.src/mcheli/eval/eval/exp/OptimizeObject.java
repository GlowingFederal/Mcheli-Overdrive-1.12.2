/*     */ package mcheli.eval.eval.exp;
/*     */ 
/*     */ import mcheli.eval.eval.repl.ReplaceAdapter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class OptimizeObject
/*     */   extends ReplaceAdapter
/*     */ {
/*     */   protected boolean isConst(AbstractExpression x) {
/*  15 */     return (x instanceof NumberExpression || x instanceof StringExpression || x instanceof CharExpression);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isTrue(AbstractExpression x) {
/*  21 */     return (x.evalLong() != 0L);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected AbstractExpression toConst(AbstractExpression exp) {
/*     */     try {
/*  28 */       Object val = exp.evalObject();
/*  29 */       if (val instanceof String)
/*     */       {
/*  31 */         return StringExpression.create(exp, (String)val);
/*     */       }
/*  33 */       if (val instanceof Character)
/*     */       {
/*  35 */         return CharExpression.create(exp, val.toString());
/*     */       }
/*  37 */       return NumberExpression.create(exp, val.toString());
/*     */     }
/*  39 */     catch (Exception exception) {
/*     */ 
/*     */       
/*  42 */       return exp;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public AbstractExpression replace0(WordExpression exp) {
/*  48 */     if (exp instanceof VariableExpression)
/*     */     {
/*  50 */       return toConst(exp);
/*     */     }
/*  52 */     return exp;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AbstractExpression replace1(Col1Expression exp) {
/*  58 */     if (exp instanceof ParenExpression)
/*     */     {
/*  60 */       return exp.exp;
/*     */     }
/*  62 */     if (exp instanceof SignPlusExpression)
/*     */     {
/*  64 */       return exp.exp;
/*     */     }
/*  66 */     if (isConst(exp.exp))
/*     */     {
/*  68 */       return toConst(exp);
/*     */     }
/*  70 */     return exp;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AbstractExpression replace2(Col2Expression exp) {
/*  76 */     boolean const_l = isConst(exp.expl);
/*  77 */     boolean const_r = isConst(exp.expr);
/*  78 */     if (const_l && const_r)
/*     */     {
/*  80 */       return toConst(exp);
/*     */     }
/*     */     
/*  83 */     return exp;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AbstractExpression replace2(Col2OpeExpression exp) {
/*  89 */     if (exp instanceof ArrayExpression) {
/*     */       
/*  91 */       if (isConst(exp.expr))
/*     */       {
/*  93 */         return toConst(exp);
/*     */       }
/*  95 */       return exp;
/*     */     } 
/*  97 */     if (exp instanceof FieldExpression)
/*     */     {
/*  99 */       return toConst(exp);
/*     */     }
/*     */     
/* 102 */     boolean const_l = isConst(exp.expl);
/* 103 */     if (exp instanceof AndExpression) {
/*     */       
/* 105 */       if (const_l) {
/*     */         
/* 107 */         if (isTrue(exp.expl))
/*     */         {
/* 109 */           return exp.expr;
/*     */         }
/* 111 */         return exp.expl;
/*     */       } 
/*     */       
/* 114 */       return exp;
/*     */     } 
/* 116 */     if (exp instanceof OrExpression) {
/*     */       
/* 118 */       if (const_l) {
/*     */         
/* 120 */         if (isTrue(exp.expl))
/*     */         {
/* 122 */           return exp.expl;
/*     */         }
/* 124 */         return exp.expr;
/*     */       } 
/*     */       
/* 127 */       return exp;
/*     */     } 
/* 129 */     if (exp instanceof CommaExpression) {
/*     */       
/* 131 */       if (const_l)
/*     */       {
/* 133 */         return exp.expr;
/*     */       }
/* 135 */       return exp;
/*     */     } 
/*     */     
/* 138 */     return exp;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AbstractExpression replace3(Col3Expression exp) {
/* 144 */     if (isConst(exp.exp1)) {
/*     */       
/* 146 */       if (isTrue(exp.exp1))
/*     */       {
/* 148 */         return exp.exp2;
/*     */       }
/* 150 */       return exp.exp3;
/*     */     } 
/*     */     
/* 153 */     return exp;
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\eval\eval\exp\OptimizeObject.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */