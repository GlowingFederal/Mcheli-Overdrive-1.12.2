/*     */ package mcheli.eval.eval;
/*     */ 
/*     */ import mcheli.eval.eval.exp.AbstractExpression;
/*     */ import mcheli.eval.eval.exp.AndExpression;
/*     */ import mcheli.eval.eval.exp.ArrayExpression;
/*     */ import mcheli.eval.eval.exp.BitAndExpression;
/*     */ import mcheli.eval.eval.exp.BitNotExpression;
/*     */ import mcheli.eval.eval.exp.BitOrExpression;
/*     */ import mcheli.eval.eval.exp.BitXorExpression;
/*     */ import mcheli.eval.eval.exp.CommaExpression;
/*     */ import mcheli.eval.eval.exp.DecAfterExpression;
/*     */ import mcheli.eval.eval.exp.DecBeforeExpression;
/*     */ import mcheli.eval.eval.exp.DivExpression;
/*     */ import mcheli.eval.eval.exp.EqualExpression;
/*     */ import mcheli.eval.eval.exp.FieldExpression;
/*     */ import mcheli.eval.eval.exp.FuncArgExpression;
/*     */ import mcheli.eval.eval.exp.FunctionExpression;
/*     */ import mcheli.eval.eval.exp.GreaterEqualExpression;
/*     */ import mcheli.eval.eval.exp.GreaterThanExpression;
/*     */ import mcheli.eval.eval.exp.IfExpression;
/*     */ import mcheli.eval.eval.exp.IncAfterExpression;
/*     */ import mcheli.eval.eval.exp.IncBeforeExpression;
/*     */ import mcheli.eval.eval.exp.LessEqualExpression;
/*     */ import mcheli.eval.eval.exp.LessThanExpression;
/*     */ import mcheli.eval.eval.exp.LetAndExpression;
/*     */ import mcheli.eval.eval.exp.LetDivExpression;
/*     */ import mcheli.eval.eval.exp.LetExpression;
/*     */ import mcheli.eval.eval.exp.LetMinusExpression;
/*     */ import mcheli.eval.eval.exp.LetModExpression;
/*     */ import mcheli.eval.eval.exp.LetMultExpression;
/*     */ import mcheli.eval.eval.exp.LetOrExpression;
/*     */ import mcheli.eval.eval.exp.LetPlusExpression;
/*     */ import mcheli.eval.eval.exp.LetPowerExpression;
/*     */ import mcheli.eval.eval.exp.LetShiftLeftExpression;
/*     */ import mcheli.eval.eval.exp.LetShiftRightExpression;
/*     */ import mcheli.eval.eval.exp.LetShiftRightLogicalExpression;
/*     */ import mcheli.eval.eval.exp.LetXorExpression;
/*     */ import mcheli.eval.eval.exp.MinusExpression;
/*     */ import mcheli.eval.eval.exp.ModExpression;
/*     */ import mcheli.eval.eval.exp.MultExpression;
/*     */ import mcheli.eval.eval.exp.NotEqualExpression;
/*     */ import mcheli.eval.eval.exp.NotExpression;
/*     */ import mcheli.eval.eval.exp.OrExpression;
/*     */ import mcheli.eval.eval.exp.ParenExpression;
/*     */ import mcheli.eval.eval.exp.PlusExpression;
/*     */ import mcheli.eval.eval.exp.PowerExpression;
/*     */ import mcheli.eval.eval.exp.ShiftLeftExpression;
/*     */ import mcheli.eval.eval.exp.ShiftRightExpression;
/*     */ import mcheli.eval.eval.exp.ShiftRightLogicalExpression;
/*     */ import mcheli.eval.eval.exp.SignMinusExpression;
/*     */ import mcheli.eval.eval.exp.SignPlusExpression;
/*     */ import mcheli.eval.eval.lex.LexFactory;
/*     */ import mcheli.eval.eval.rule.AbstractRule;
/*     */ import mcheli.eval.eval.rule.Col1AfterRule;
/*     */ import mcheli.eval.eval.rule.Col2RightJoinRule;
/*     */ import mcheli.eval.eval.rule.Col2Rule;
/*     */ import mcheli.eval.eval.rule.IfRule;
/*     */ import mcheli.eval.eval.rule.JavaRuleFactory;
/*     */ import mcheli.eval.eval.rule.PrimaryRule;
/*     */ import mcheli.eval.eval.rule.ShareRuleValue;
/*     */ import mcheli.eval.eval.rule.SignRule;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ExpRuleFactory
/*     */ {
/*     */   private static ExpRuleFactory me;
/*     */   protected Rule rule;
/*     */   protected AbstractRule topRule;
/*     */   protected LexFactory defaultLexFactory;
/*     */   
/*     */   public static ExpRuleFactory getInstance() {
/*  78 */     if (me == null)
/*     */     {
/*  80 */       me = new ExpRuleFactory();
/*     */     }
/*  82 */     return me;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Rule getDefaultRule() {
/*  87 */     return getInstance().getRule();
/*     */   }
/*     */ 
/*     */   
/*     */   public static Rule getJavaRule() {
/*  92 */     return JavaRuleFactory.getInstance().getRule();
/*     */   }
/*     */ 
/*     */   
/*     */   public ExpRuleFactory() {
/*  97 */     ShareRuleValue share = new ShareRuleValue();
/*  98 */     share.lexFactory = getLexFactory();
/*  99 */     init(share);
/* 100 */     this.rule = (Rule)share;
/*     */   }
/*     */ 
/*     */   
/*     */   public Rule getRule() {
/* 105 */     return this.rule;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void init(ShareRuleValue share) {
/* 110 */     AbstractRule rule = null;
/*     */     
/* 112 */     rule = add(rule, createCommaRule(share));
/* 113 */     rule = add(rule, createLetRule(share));
/* 114 */     rule = add(rule, createIfRule(share));
/* 115 */     rule = add(rule, createOrRule(share));
/* 116 */     rule = add(rule, createAndRule(share));
/* 117 */     rule = add(rule, createBitOrRule(share));
/* 118 */     rule = add(rule, createBitXorRule(share));
/* 119 */     rule = add(rule, createBitAndRule(share));
/* 120 */     rule = add(rule, createEqualRule(share));
/* 121 */     rule = add(rule, createGreaterRule(share));
/* 122 */     rule = add(rule, createShiftRule(share));
/* 123 */     rule = add(rule, createPlusRule(share));
/* 124 */     rule = add(rule, createMultRule(share));
/* 125 */     rule = add(rule, createSignRule(share));
/* 126 */     rule = add(rule, createPowerRule(share));
/* 127 */     rule = add(rule, createCol1AfterRule(share));
/* 128 */     rule = add(rule, createPrimaryRule(share));
/* 129 */     this.topRule.initPriority(1);
/* 130 */     share.topRule = this.topRule;
/*     */     
/* 132 */     initFuncArgRule(share);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void initFuncArgRule(ShareRuleValue share) {
/* 137 */     AbstractRule argRule = share.funcArgRule = createFuncArgRule(share);
/*     */     
/* 139 */     String[] a_opes = argRule.getOperators();
/* 140 */     String[] t_opes = this.topRule.getOperators();
/*     */     
/* 142 */     boolean match = false;
/*     */     int i;
/* 144 */     label20: for (i = 0; i < a_opes.length; i++) {
/*     */       
/* 146 */       for (int j = 0; j < t_opes.length; j++) {
/* 147 */         if (a_opes[i].equals(t_opes[j])) {
/*     */           
/* 149 */           match = true;
/*     */           break label20;
/*     */         } 
/*     */       } 
/*     */     } 
/* 154 */     if (match) {
/*     */       
/* 156 */       argRule.nextRule = this.topRule.nextRule;
/*     */     }
/*     */     else {
/*     */       
/* 160 */       argRule.nextRule = this.topRule;
/*     */     } 
/* 162 */     argRule.prio = this.topRule.prio;
/*     */   }
/*     */ 
/*     */   
/*     */   protected final AbstractRule add(AbstractRule rule, AbstractRule r) {
/* 167 */     if (r == null)
/* 168 */       return rule; 
/* 169 */     if (this.topRule == null)
/*     */     {
/* 171 */       this.topRule = r;
/*     */     }
/* 173 */     if (rule != null)
/*     */     {
/* 175 */       rule.nextRule = r;
/*     */     }
/* 177 */     return r;
/*     */   }
/*     */ 
/*     */   
/*     */   protected AbstractRule createCommaRule(ShareRuleValue share) {
/* 182 */     Col2Rule col2Rule = new Col2Rule(share);
/* 183 */     col2Rule.addExpression(createCommaExpression());
/* 184 */     return (AbstractRule)col2Rule;
/*     */   }
/*     */ 
/*     */   
/*     */   protected AbstractExpression createCommaExpression() {
/* 189 */     return (AbstractExpression)new CommaExpression();
/*     */   }
/*     */ 
/*     */   
/*     */   protected AbstractRule createLetRule(ShareRuleValue share) {
/* 194 */     Col2RightJoinRule col2RightJoinRule = new Col2RightJoinRule(share);
/* 195 */     col2RightJoinRule.addExpression(createLetExpression());
/* 196 */     col2RightJoinRule.addExpression(createLetMultExpression());
/* 197 */     col2RightJoinRule.addExpression(createLetDivExpression());
/* 198 */     col2RightJoinRule.addExpression(createLetModExpression());
/* 199 */     col2RightJoinRule.addExpression(createLetPlusExpression());
/* 200 */     col2RightJoinRule.addExpression(createLetMinusExpression());
/* 201 */     col2RightJoinRule.addExpression(createLetShiftLeftExpression());
/* 202 */     col2RightJoinRule.addExpression(createLetShiftRightExpression());
/* 203 */     col2RightJoinRule.addExpression(createLetShiftRightLogicalExpression());
/* 204 */     col2RightJoinRule.addExpression(createLetAndExpression());
/* 205 */     col2RightJoinRule.addExpression(createLetOrExpression());
/* 206 */     col2RightJoinRule.addExpression(createLetXorExpression());
/* 207 */     col2RightJoinRule.addExpression(createLetPowerExpression());
/* 208 */     return (AbstractRule)col2RightJoinRule;
/*     */   }
/*     */ 
/*     */   
/*     */   protected AbstractExpression createLetExpression() {
/* 213 */     return (AbstractExpression)new LetExpression();
/*     */   }
/*     */ 
/*     */   
/*     */   protected AbstractExpression createLetMultExpression() {
/* 218 */     return (AbstractExpression)new LetMultExpression();
/*     */   }
/*     */ 
/*     */   
/*     */   protected AbstractExpression createLetDivExpression() {
/* 223 */     return (AbstractExpression)new LetDivExpression();
/*     */   }
/*     */ 
/*     */   
/*     */   protected AbstractExpression createLetModExpression() {
/* 228 */     return (AbstractExpression)new LetModExpression();
/*     */   }
/*     */ 
/*     */   
/*     */   protected AbstractExpression createLetPlusExpression() {
/* 233 */     return (AbstractExpression)new LetPlusExpression();
/*     */   }
/*     */ 
/*     */   
/*     */   protected AbstractExpression createLetMinusExpression() {
/* 238 */     return (AbstractExpression)new LetMinusExpression();
/*     */   }
/*     */ 
/*     */   
/*     */   protected AbstractExpression createLetShiftLeftExpression() {
/* 243 */     return (AbstractExpression)new LetShiftLeftExpression();
/*     */   }
/*     */ 
/*     */   
/*     */   protected AbstractExpression createLetShiftRightExpression() {
/* 248 */     return (AbstractExpression)new LetShiftRightExpression();
/*     */   }
/*     */ 
/*     */   
/*     */   protected AbstractExpression createLetShiftRightLogicalExpression() {
/* 253 */     return (AbstractExpression)new LetShiftRightLogicalExpression();
/*     */   }
/*     */ 
/*     */   
/*     */   protected AbstractExpression createLetAndExpression() {
/* 258 */     return (AbstractExpression)new LetAndExpression();
/*     */   }
/*     */ 
/*     */   
/*     */   protected AbstractExpression createLetOrExpression() {
/* 263 */     return (AbstractExpression)new LetOrExpression();
/*     */   }
/*     */ 
/*     */   
/*     */   protected AbstractExpression createLetXorExpression() {
/* 268 */     return (AbstractExpression)new LetXorExpression();
/*     */   }
/*     */ 
/*     */   
/*     */   protected AbstractExpression createLetPowerExpression() {
/* 273 */     return (AbstractExpression)new LetPowerExpression();
/*     */   }
/*     */ 
/*     */   
/*     */   protected AbstractRule createIfRule(ShareRuleValue share) {
/* 278 */     IfRule me = new IfRule(share);
/* 279 */     me.addExpression(me.cond = createIfExpression());
/* 280 */     return (AbstractRule)me;
/*     */   }
/*     */ 
/*     */   
/*     */   protected AbstractExpression createIfExpression() {
/* 285 */     return (AbstractExpression)new IfExpression();
/*     */   }
/*     */ 
/*     */   
/*     */   protected AbstractRule createOrRule(ShareRuleValue share) {
/* 290 */     Col2Rule col2Rule = new Col2Rule(share);
/* 291 */     col2Rule.addExpression(createOrExpression());
/* 292 */     return (AbstractRule)col2Rule;
/*     */   }
/*     */ 
/*     */   
/*     */   protected AbstractExpression createOrExpression() {
/* 297 */     return (AbstractExpression)new OrExpression();
/*     */   }
/*     */ 
/*     */   
/*     */   protected AbstractRule createAndRule(ShareRuleValue share) {
/* 302 */     Col2Rule col2Rule = new Col2Rule(share);
/* 303 */     col2Rule.addExpression(createAndExpression());
/* 304 */     return (AbstractRule)col2Rule;
/*     */   }
/*     */ 
/*     */   
/*     */   protected AbstractExpression createAndExpression() {
/* 309 */     return (AbstractExpression)new AndExpression();
/*     */   }
/*     */ 
/*     */   
/*     */   protected AbstractRule createBitOrRule(ShareRuleValue share) {
/* 314 */     Col2Rule col2Rule = new Col2Rule(share);
/* 315 */     col2Rule.addExpression(createBitOrExpression());
/* 316 */     return (AbstractRule)col2Rule;
/*     */   }
/*     */ 
/*     */   
/*     */   protected AbstractExpression createBitOrExpression() {
/* 321 */     return (AbstractExpression)new BitOrExpression();
/*     */   }
/*     */ 
/*     */   
/*     */   protected AbstractRule createBitXorRule(ShareRuleValue share) {
/* 326 */     Col2Rule col2Rule = new Col2Rule(share);
/* 327 */     col2Rule.addExpression(createBitXorExpression());
/* 328 */     return (AbstractRule)col2Rule;
/*     */   }
/*     */ 
/*     */   
/*     */   protected AbstractExpression createBitXorExpression() {
/* 333 */     return (AbstractExpression)new BitXorExpression();
/*     */   }
/*     */ 
/*     */   
/*     */   protected AbstractRule createBitAndRule(ShareRuleValue share) {
/* 338 */     Col2Rule col2Rule = new Col2Rule(share);
/* 339 */     col2Rule.addExpression(createBitAndExpression());
/* 340 */     return (AbstractRule)col2Rule;
/*     */   }
/*     */ 
/*     */   
/*     */   protected AbstractExpression createBitAndExpression() {
/* 345 */     return (AbstractExpression)new BitAndExpression();
/*     */   }
/*     */ 
/*     */   
/*     */   protected AbstractRule createEqualRule(ShareRuleValue share) {
/* 350 */     Col2Rule col2Rule = new Col2Rule(share);
/* 351 */     col2Rule.addExpression(createEqualExpression());
/* 352 */     col2Rule.addExpression(createNotEqualExpression());
/* 353 */     return (AbstractRule)col2Rule;
/*     */   }
/*     */ 
/*     */   
/*     */   protected AbstractExpression createEqualExpression() {
/* 358 */     return (AbstractExpression)new EqualExpression();
/*     */   }
/*     */ 
/*     */   
/*     */   protected AbstractExpression createNotEqualExpression() {
/* 363 */     return (AbstractExpression)new NotEqualExpression();
/*     */   }
/*     */ 
/*     */   
/*     */   protected AbstractRule createGreaterRule(ShareRuleValue share) {
/* 368 */     Col2Rule col2Rule = new Col2Rule(share);
/* 369 */     col2Rule.addExpression(createLessThanExpression());
/* 370 */     col2Rule.addExpression(createLessEqualExpression());
/* 371 */     col2Rule.addExpression(createGreaterThanExpression());
/* 372 */     col2Rule.addExpression(createGreaterEqualExpression());
/* 373 */     return (AbstractRule)col2Rule;
/*     */   }
/*     */ 
/*     */   
/*     */   protected AbstractExpression createLessThanExpression() {
/* 378 */     return (AbstractExpression)new LessThanExpression();
/*     */   }
/*     */ 
/*     */   
/*     */   protected AbstractExpression createLessEqualExpression() {
/* 383 */     return (AbstractExpression)new LessEqualExpression();
/*     */   }
/*     */ 
/*     */   
/*     */   protected AbstractExpression createGreaterThanExpression() {
/* 388 */     return (AbstractExpression)new GreaterThanExpression();
/*     */   }
/*     */ 
/*     */   
/*     */   protected AbstractExpression createGreaterEqualExpression() {
/* 393 */     return (AbstractExpression)new GreaterEqualExpression();
/*     */   }
/*     */ 
/*     */   
/*     */   protected AbstractRule createShiftRule(ShareRuleValue share) {
/* 398 */     Col2Rule col2Rule = new Col2Rule(share);
/* 399 */     col2Rule.addExpression(createShiftLeftExpression());
/* 400 */     col2Rule.addExpression(createShiftRightExpression());
/* 401 */     col2Rule.addExpression(createShiftRightLogicalExpression());
/* 402 */     return (AbstractRule)col2Rule;
/*     */   }
/*     */ 
/*     */   
/*     */   protected AbstractExpression createShiftLeftExpression() {
/* 407 */     return (AbstractExpression)new ShiftLeftExpression();
/*     */   }
/*     */ 
/*     */   
/*     */   protected AbstractExpression createShiftRightExpression() {
/* 412 */     return (AbstractExpression)new ShiftRightExpression();
/*     */   }
/*     */ 
/*     */   
/*     */   protected AbstractExpression createShiftRightLogicalExpression() {
/* 417 */     return (AbstractExpression)new ShiftRightLogicalExpression();
/*     */   }
/*     */ 
/*     */   
/*     */   protected AbstractRule createPlusRule(ShareRuleValue share) {
/* 422 */     Col2Rule col2Rule = new Col2Rule(share);
/* 423 */     col2Rule.addExpression(createPlusExpression());
/* 424 */     col2Rule.addExpression(createMinusExpression());
/* 425 */     return (AbstractRule)col2Rule;
/*     */   }
/*     */ 
/*     */   
/*     */   protected AbstractExpression createPlusExpression() {
/* 430 */     return (AbstractExpression)new PlusExpression();
/*     */   }
/*     */ 
/*     */   
/*     */   protected AbstractExpression createMinusExpression() {
/* 435 */     return (AbstractExpression)new MinusExpression();
/*     */   }
/*     */ 
/*     */   
/*     */   protected AbstractRule createMultRule(ShareRuleValue share) {
/* 440 */     Col2Rule col2Rule = new Col2Rule(share);
/* 441 */     col2Rule.addExpression(createMultExpression());
/* 442 */     col2Rule.addExpression(createDivExpression());
/* 443 */     col2Rule.addExpression(createModExpression());
/* 444 */     return (AbstractRule)col2Rule;
/*     */   }
/*     */ 
/*     */   
/*     */   protected AbstractExpression createMultExpression() {
/* 449 */     return (AbstractExpression)new MultExpression();
/*     */   }
/*     */ 
/*     */   
/*     */   protected AbstractExpression createDivExpression() {
/* 454 */     return (AbstractExpression)new DivExpression();
/*     */   }
/*     */ 
/*     */   
/*     */   protected AbstractExpression createModExpression() {
/* 459 */     return (AbstractExpression)new ModExpression();
/*     */   }
/*     */ 
/*     */   
/*     */   protected AbstractRule createSignRule(ShareRuleValue share) {
/* 464 */     SignRule signRule = new SignRule(share);
/* 465 */     signRule.addExpression(createSignPlusExpression());
/* 466 */     signRule.addExpression(createSignMinusExpression());
/* 467 */     signRule.addExpression(createBitNotExpression());
/* 468 */     signRule.addExpression(createNotExpression());
/* 469 */     signRule.addExpression(createIncBeforeExpression());
/* 470 */     signRule.addExpression(createDecBeforeExpression());
/* 471 */     return (AbstractRule)signRule;
/*     */   }
/*     */ 
/*     */   
/*     */   protected AbstractExpression createSignPlusExpression() {
/* 476 */     return (AbstractExpression)new SignPlusExpression();
/*     */   }
/*     */ 
/*     */   
/*     */   protected AbstractExpression createSignMinusExpression() {
/* 481 */     return (AbstractExpression)new SignMinusExpression();
/*     */   }
/*     */ 
/*     */   
/*     */   protected AbstractExpression createBitNotExpression() {
/* 486 */     return (AbstractExpression)new BitNotExpression();
/*     */   }
/*     */ 
/*     */   
/*     */   protected AbstractExpression createNotExpression() {
/* 491 */     return (AbstractExpression)new NotExpression();
/*     */   }
/*     */ 
/*     */   
/*     */   protected AbstractExpression createIncBeforeExpression() {
/* 496 */     return (AbstractExpression)new IncBeforeExpression();
/*     */   }
/*     */ 
/*     */   
/*     */   protected AbstractExpression createDecBeforeExpression() {
/* 501 */     return (AbstractExpression)new DecBeforeExpression();
/*     */   }
/*     */ 
/*     */   
/*     */   protected AbstractRule createPowerRule(ShareRuleValue share) {
/* 506 */     Col2Rule col2Rule = new Col2Rule(share);
/* 507 */     col2Rule.addExpression(createPowerExpression());
/* 508 */     return (AbstractRule)col2Rule;
/*     */   }
/*     */ 
/*     */   
/*     */   protected AbstractExpression createPowerExpression() {
/* 513 */     return (AbstractExpression)new PowerExpression();
/*     */   }
/*     */ 
/*     */   
/*     */   protected AbstractRule createCol1AfterRule(ShareRuleValue share) {
/* 518 */     Col1AfterRule me = new Col1AfterRule(share);
/* 519 */     me.addExpression(me.func = createFunctionExpression());
/* 520 */     me.addExpression(me.array = createArrayExpression());
/* 521 */     me.addExpression(createIncAfterExpression());
/* 522 */     me.addExpression(createDecAfterExpression());
/* 523 */     me.addExpression(me.field = createFieldExpression());
/* 524 */     return (AbstractRule)me;
/*     */   }
/*     */ 
/*     */   
/*     */   protected AbstractExpression createFunctionExpression() {
/* 529 */     return (AbstractExpression)new FunctionExpression();
/*     */   }
/*     */ 
/*     */   
/*     */   protected AbstractExpression createArrayExpression() {
/* 534 */     return (AbstractExpression)new ArrayExpression();
/*     */   }
/*     */ 
/*     */   
/*     */   protected AbstractExpression createIncAfterExpression() {
/* 539 */     return (AbstractExpression)new IncAfterExpression();
/*     */   }
/*     */ 
/*     */   
/*     */   protected AbstractExpression createDecAfterExpression() {
/* 544 */     return (AbstractExpression)new DecAfterExpression();
/*     */   }
/*     */ 
/*     */   
/*     */   protected AbstractExpression createFieldExpression() {
/* 549 */     return (AbstractExpression)new FieldExpression();
/*     */   }
/*     */ 
/*     */   
/*     */   protected AbstractRule createPrimaryRule(ShareRuleValue share) {
/* 554 */     PrimaryRule primaryRule = new PrimaryRule(share);
/* 555 */     primaryRule.addExpression(createParenExpression());
/*     */     
/* 557 */     return (AbstractRule)primaryRule;
/*     */   }
/*     */ 
/*     */   
/*     */   protected AbstractExpression createParenExpression() {
/* 562 */     return (AbstractExpression)new ParenExpression();
/*     */   }
/*     */ 
/*     */   
/*     */   protected AbstractRule createFuncArgRule(ShareRuleValue share) {
/* 567 */     Col2Rule col2Rule = new Col2Rule(share);
/* 568 */     col2Rule.addExpression(createFuncArgExpression());
/* 569 */     return (AbstractRule)col2Rule;
/*     */   }
/*     */ 
/*     */   
/*     */   protected AbstractExpression createFuncArgExpression() {
/* 574 */     return (AbstractExpression)new FuncArgExpression();
/*     */   }
/*     */ 
/*     */   
/*     */   protected LexFactory getLexFactory() {
/* 579 */     if (this.defaultLexFactory == null)
/*     */     {
/* 581 */       this.defaultLexFactory = new LexFactory();
/*     */     }
/* 583 */     return this.defaultLexFactory;
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\eval\eval\ExpRuleFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */