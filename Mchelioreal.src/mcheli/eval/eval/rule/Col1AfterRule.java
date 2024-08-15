/*     */ package mcheli.eval.eval.rule;
/*     */ 
/*     */ import mcheli.eval.eval.EvalException;
/*     */ import mcheli.eval.eval.exp.AbstractExpression;
/*     */ import mcheli.eval.eval.exp.Col1Expression;
/*     */ import mcheli.eval.eval.exp.Col2Expression;
/*     */ import mcheli.eval.eval.exp.FunctionExpression;
/*     */ import mcheli.eval.eval.lex.Lex;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Col1AfterRule
/*     */   extends AbstractRule
/*     */ {
/*     */   public AbstractExpression func;
/*     */   public AbstractExpression array;
/*     */   public AbstractExpression field;
/*     */   
/*     */   public Col1AfterRule(ShareRuleValue share) {
/*  24 */     super(share);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AbstractExpression parse(Lex lex) {
/*  30 */     AbstractExpression x = this.nextRule.parse(lex);
/*     */     
/*     */     while (true) {
/*     */       String ope;
/*     */       int pos;
/*  35 */       switch (lex.getType()) {
/*     */         
/*     */         case 2147483634:
/*  38 */           ope = lex.getOperator();
/*  39 */           pos = lex.getPos();
/*  40 */           if (isMyOperator(ope)) {
/*     */             
/*  42 */             if (lex.isOperator(this.func.getOperator())) {
/*     */               
/*  44 */               x = parseFunc(lex, x);
/*     */               continue;
/*     */             } 
/*  47 */             if (lex.isOperator(this.array.getOperator())) {
/*     */               
/*  49 */               x = parseArray(lex, x, ope, pos);
/*     */               continue;
/*     */             } 
/*  52 */             if (lex.isOperator(this.field.getOperator())) {
/*     */               
/*  54 */               x = parseField(lex, x, ope, pos);
/*     */               continue;
/*     */             } 
/*  57 */             x = Col1Expression.create(newExpression(ope, lex.getShare()), lex.getString(), pos, x);
/*  58 */             lex.next();
/*     */             continue;
/*     */           } 
/*  61 */           return x;
/*     */       } 
/*     */       break;
/*     */     } 
/*  65 */     return x;
/*     */   }
/*     */ 
/*     */   
/*     */   protected AbstractExpression parseFunc(Lex lex, AbstractExpression x) {
/*  70 */     AbstractExpression a = null;
/*  71 */     lex.next();
/*  72 */     if (!lex.isOperator(this.func.getEndOperator())) {
/*     */       
/*  74 */       a = this.share.funcArgRule.parse(lex);
/*  75 */       if (!lex.isOperator(this.func.getEndOperator()))
/*     */       {
/*  77 */         throw new EvalException(1001, new String[] { this.func
/*     */               
/*  79 */               .getEndOperator() }, lex);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/*  84 */     lex.next();
/*  85 */     x = FunctionExpression.create(x, a, this.prio, lex.getShare());
/*  86 */     return x;
/*     */   }
/*     */ 
/*     */   
/*     */   protected AbstractExpression parseArray(Lex lex, AbstractExpression x, String ope, int pos) {
/*  91 */     AbstractExpression y = this.share.topRule.parse(lex.next());
/*  92 */     if (!lex.isOperator(this.array.getEndOperator()))
/*     */     {
/*  94 */       throw new EvalException(1001, new String[] { this.array
/*     */             
/*  96 */             .getEndOperator() }, lex);
/*     */     }
/*     */ 
/*     */     
/* 100 */     lex.next();
/* 101 */     x = Col2Expression.create(newExpression(ope, lex.getShare()), lex.getString(), pos, x, y);
/*     */     
/* 103 */     return x;
/*     */   }
/*     */ 
/*     */   
/*     */   protected AbstractExpression parseField(Lex lex, AbstractExpression x, String ope, int pos) {
/* 108 */     AbstractExpression y = this.nextRule.parse(lex.next());
/* 109 */     x = Col2Expression.create(newExpression(ope, lex.getShare()), lex.getString(), pos, x, y);
/*     */     
/* 111 */     return x;
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\eval\eval\rule\Col1AfterRule.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */