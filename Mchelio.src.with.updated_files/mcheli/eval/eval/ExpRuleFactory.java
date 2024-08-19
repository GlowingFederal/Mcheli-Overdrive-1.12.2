package mcheli.eval.eval;

import mcheli.eval.eval.exp.AbstractExpression;
import mcheli.eval.eval.exp.AndExpression;
import mcheli.eval.eval.exp.ArrayExpression;
import mcheli.eval.eval.exp.BitAndExpression;
import mcheli.eval.eval.exp.BitNotExpression;
import mcheli.eval.eval.exp.BitOrExpression;
import mcheli.eval.eval.exp.BitXorExpression;
import mcheli.eval.eval.exp.CommaExpression;
import mcheli.eval.eval.exp.DecAfterExpression;
import mcheli.eval.eval.exp.DecBeforeExpression;
import mcheli.eval.eval.exp.DivExpression;
import mcheli.eval.eval.exp.EqualExpression;
import mcheli.eval.eval.exp.FieldExpression;
import mcheli.eval.eval.exp.FuncArgExpression;
import mcheli.eval.eval.exp.FunctionExpression;
import mcheli.eval.eval.exp.GreaterEqualExpression;
import mcheli.eval.eval.exp.GreaterThanExpression;
import mcheli.eval.eval.exp.IfExpression;
import mcheli.eval.eval.exp.IncAfterExpression;
import mcheli.eval.eval.exp.IncBeforeExpression;
import mcheli.eval.eval.exp.LessEqualExpression;
import mcheli.eval.eval.exp.LessThanExpression;
import mcheli.eval.eval.exp.LetAndExpression;
import mcheli.eval.eval.exp.LetDivExpression;
import mcheli.eval.eval.exp.LetExpression;
import mcheli.eval.eval.exp.LetMinusExpression;
import mcheli.eval.eval.exp.LetModExpression;
import mcheli.eval.eval.exp.LetMultExpression;
import mcheli.eval.eval.exp.LetOrExpression;
import mcheli.eval.eval.exp.LetPlusExpression;
import mcheli.eval.eval.exp.LetPowerExpression;
import mcheli.eval.eval.exp.LetShiftLeftExpression;
import mcheli.eval.eval.exp.LetShiftRightExpression;
import mcheli.eval.eval.exp.LetShiftRightLogicalExpression;
import mcheli.eval.eval.exp.LetXorExpression;
import mcheli.eval.eval.exp.MinusExpression;
import mcheli.eval.eval.exp.ModExpression;
import mcheli.eval.eval.exp.MultExpression;
import mcheli.eval.eval.exp.NotEqualExpression;
import mcheli.eval.eval.exp.NotExpression;
import mcheli.eval.eval.exp.OrExpression;
import mcheli.eval.eval.exp.ParenExpression;
import mcheli.eval.eval.exp.PlusExpression;
import mcheli.eval.eval.exp.PowerExpression;
import mcheli.eval.eval.exp.ShiftLeftExpression;
import mcheli.eval.eval.exp.ShiftRightExpression;
import mcheli.eval.eval.exp.ShiftRightLogicalExpression;
import mcheli.eval.eval.exp.SignMinusExpression;
import mcheli.eval.eval.exp.SignPlusExpression;
import mcheli.eval.eval.lex.LexFactory;
import mcheli.eval.eval.rule.AbstractRule;
import mcheli.eval.eval.rule.Col1AfterRule;
import mcheli.eval.eval.rule.Col2RightJoinRule;
import mcheli.eval.eval.rule.Col2Rule;
import mcheli.eval.eval.rule.IfRule;
import mcheli.eval.eval.rule.JavaRuleFactory;
import mcheli.eval.eval.rule.PrimaryRule;
import mcheli.eval.eval.rule.ShareRuleValue;
import mcheli.eval.eval.rule.SignRule;

public class ExpRuleFactory {
  private static ExpRuleFactory me;
  
  protected Rule rule;
  
  protected AbstractRule topRule;
  
  protected LexFactory defaultLexFactory;
  
  public static ExpRuleFactory getInstance() {
    if (me == null)
      me = new ExpRuleFactory(); 
    return me;
  }
  
  public static Rule getDefaultRule() {
    return getInstance().getRule();
  }
  
  public static Rule getJavaRule() {
    return JavaRuleFactory.getInstance().getRule();
  }
  
  public ExpRuleFactory() {
    ShareRuleValue share = new ShareRuleValue();
    share.lexFactory = getLexFactory();
    init(share);
    this.rule = (Rule)share;
  }
  
  public Rule getRule() {
    return this.rule;
  }
  
  protected void init(ShareRuleValue share) {
    AbstractRule rule = null;
    rule = add(rule, createCommaRule(share));
    rule = add(rule, createLetRule(share));
    rule = add(rule, createIfRule(share));
    rule = add(rule, createOrRule(share));
    rule = add(rule, createAndRule(share));
    rule = add(rule, createBitOrRule(share));
    rule = add(rule, createBitXorRule(share));
    rule = add(rule, createBitAndRule(share));
    rule = add(rule, createEqualRule(share));
    rule = add(rule, createGreaterRule(share));
    rule = add(rule, createShiftRule(share));
    rule = add(rule, createPlusRule(share));
    rule = add(rule, createMultRule(share));
    rule = add(rule, createSignRule(share));
    rule = add(rule, createPowerRule(share));
    rule = add(rule, createCol1AfterRule(share));
    rule = add(rule, createPrimaryRule(share));
    this.topRule.initPriority(1);
    share.topRule = this.topRule;
    initFuncArgRule(share);
  }
  
  protected void initFuncArgRule(ShareRuleValue share) {
    AbstractRule argRule = share.funcArgRule = createFuncArgRule(share);
    String[] a_opes = argRule.getOperators();
    String[] t_opes = this.topRule.getOperators();
    boolean match = false;
    int i;
    label20: for (i = 0; i < a_opes.length; i++) {
      for (int j = 0; j < t_opes.length; j++) {
        if (a_opes[i].equals(t_opes[j])) {
          match = true;
          break label20;
        } 
      } 
    } 
    if (match) {
      argRule.nextRule = this.topRule.nextRule;
    } else {
      argRule.nextRule = this.topRule;
    } 
    argRule.prio = this.topRule.prio;
  }
  
  protected final AbstractRule add(AbstractRule rule, AbstractRule r) {
    if (r == null)
      return rule; 
    if (this.topRule == null)
      this.topRule = r; 
    if (rule != null)
      rule.nextRule = r; 
    return r;
  }
  
  protected AbstractRule createCommaRule(ShareRuleValue share) {
    Col2Rule col2Rule = new Col2Rule(share);
    col2Rule.addExpression(createCommaExpression());
    return (AbstractRule)col2Rule;
  }
  
  protected AbstractExpression createCommaExpression() {
    return (AbstractExpression)new CommaExpression();
  }
  
  protected AbstractRule createLetRule(ShareRuleValue share) {
    Col2RightJoinRule col2RightJoinRule = new Col2RightJoinRule(share);
    col2RightJoinRule.addExpression(createLetExpression());
    col2RightJoinRule.addExpression(createLetMultExpression());
    col2RightJoinRule.addExpression(createLetDivExpression());
    col2RightJoinRule.addExpression(createLetModExpression());
    col2RightJoinRule.addExpression(createLetPlusExpression());
    col2RightJoinRule.addExpression(createLetMinusExpression());
    col2RightJoinRule.addExpression(createLetShiftLeftExpression());
    col2RightJoinRule.addExpression(createLetShiftRightExpression());
    col2RightJoinRule.addExpression(createLetShiftRightLogicalExpression());
    col2RightJoinRule.addExpression(createLetAndExpression());
    col2RightJoinRule.addExpression(createLetOrExpression());
    col2RightJoinRule.addExpression(createLetXorExpression());
    col2RightJoinRule.addExpression(createLetPowerExpression());
    return (AbstractRule)col2RightJoinRule;
  }
  
  protected AbstractExpression createLetExpression() {
    return (AbstractExpression)new LetExpression();
  }
  
  protected AbstractExpression createLetMultExpression() {
    return (AbstractExpression)new LetMultExpression();
  }
  
  protected AbstractExpression createLetDivExpression() {
    return (AbstractExpression)new LetDivExpression();
  }
  
  protected AbstractExpression createLetModExpression() {
    return (AbstractExpression)new LetModExpression();
  }
  
  protected AbstractExpression createLetPlusExpression() {
    return (AbstractExpression)new LetPlusExpression();
  }
  
  protected AbstractExpression createLetMinusExpression() {
    return (AbstractExpression)new LetMinusExpression();
  }
  
  protected AbstractExpression createLetShiftLeftExpression() {
    return (AbstractExpression)new LetShiftLeftExpression();
  }
  
  protected AbstractExpression createLetShiftRightExpression() {
    return (AbstractExpression)new LetShiftRightExpression();
  }
  
  protected AbstractExpression createLetShiftRightLogicalExpression() {
    return (AbstractExpression)new LetShiftRightLogicalExpression();
  }
  
  protected AbstractExpression createLetAndExpression() {
    return (AbstractExpression)new LetAndExpression();
  }
  
  protected AbstractExpression createLetOrExpression() {
    return (AbstractExpression)new LetOrExpression();
  }
  
  protected AbstractExpression createLetXorExpression() {
    return (AbstractExpression)new LetXorExpression();
  }
  
  protected AbstractExpression createLetPowerExpression() {
    return (AbstractExpression)new LetPowerExpression();
  }
  
  protected AbstractRule createIfRule(ShareRuleValue share) {
    IfRule me = new IfRule(share);
    me.addExpression(me.cond = createIfExpression());
    return (AbstractRule)me;
  }
  
  protected AbstractExpression createIfExpression() {
    return (AbstractExpression)new IfExpression();
  }
  
  protected AbstractRule createOrRule(ShareRuleValue share) {
    Col2Rule col2Rule = new Col2Rule(share);
    col2Rule.addExpression(createOrExpression());
    return (AbstractRule)col2Rule;
  }
  
  protected AbstractExpression createOrExpression() {
    return (AbstractExpression)new OrExpression();
  }
  
  protected AbstractRule createAndRule(ShareRuleValue share) {
    Col2Rule col2Rule = new Col2Rule(share);
    col2Rule.addExpression(createAndExpression());
    return (AbstractRule)col2Rule;
  }
  
  protected AbstractExpression createAndExpression() {
    return (AbstractExpression)new AndExpression();
  }
  
  protected AbstractRule createBitOrRule(ShareRuleValue share) {
    Col2Rule col2Rule = new Col2Rule(share);
    col2Rule.addExpression(createBitOrExpression());
    return (AbstractRule)col2Rule;
  }
  
  protected AbstractExpression createBitOrExpression() {
    return (AbstractExpression)new BitOrExpression();
  }
  
  protected AbstractRule createBitXorRule(ShareRuleValue share) {
    Col2Rule col2Rule = new Col2Rule(share);
    col2Rule.addExpression(createBitXorExpression());
    return (AbstractRule)col2Rule;
  }
  
  protected AbstractExpression createBitXorExpression() {
    return (AbstractExpression)new BitXorExpression();
  }
  
  protected AbstractRule createBitAndRule(ShareRuleValue share) {
    Col2Rule col2Rule = new Col2Rule(share);
    col2Rule.addExpression(createBitAndExpression());
    return (AbstractRule)col2Rule;
  }
  
  protected AbstractExpression createBitAndExpression() {
    return (AbstractExpression)new BitAndExpression();
  }
  
  protected AbstractRule createEqualRule(ShareRuleValue share) {
    Col2Rule col2Rule = new Col2Rule(share);
    col2Rule.addExpression(createEqualExpression());
    col2Rule.addExpression(createNotEqualExpression());
    return (AbstractRule)col2Rule;
  }
  
  protected AbstractExpression createEqualExpression() {
    return (AbstractExpression)new EqualExpression();
  }
  
  protected AbstractExpression createNotEqualExpression() {
    return (AbstractExpression)new NotEqualExpression();
  }
  
  protected AbstractRule createGreaterRule(ShareRuleValue share) {
    Col2Rule col2Rule = new Col2Rule(share);
    col2Rule.addExpression(createLessThanExpression());
    col2Rule.addExpression(createLessEqualExpression());
    col2Rule.addExpression(createGreaterThanExpression());
    col2Rule.addExpression(createGreaterEqualExpression());
    return (AbstractRule)col2Rule;
  }
  
  protected AbstractExpression createLessThanExpression() {
    return (AbstractExpression)new LessThanExpression();
  }
  
  protected AbstractExpression createLessEqualExpression() {
    return (AbstractExpression)new LessEqualExpression();
  }
  
  protected AbstractExpression createGreaterThanExpression() {
    return (AbstractExpression)new GreaterThanExpression();
  }
  
  protected AbstractExpression createGreaterEqualExpression() {
    return (AbstractExpression)new GreaterEqualExpression();
  }
  
  protected AbstractRule createShiftRule(ShareRuleValue share) {
    Col2Rule col2Rule = new Col2Rule(share);
    col2Rule.addExpression(createShiftLeftExpression());
    col2Rule.addExpression(createShiftRightExpression());
    col2Rule.addExpression(createShiftRightLogicalExpression());
    return (AbstractRule)col2Rule;
  }
  
  protected AbstractExpression createShiftLeftExpression() {
    return (AbstractExpression)new ShiftLeftExpression();
  }
  
  protected AbstractExpression createShiftRightExpression() {
    return (AbstractExpression)new ShiftRightExpression();
  }
  
  protected AbstractExpression createShiftRightLogicalExpression() {
    return (AbstractExpression)new ShiftRightLogicalExpression();
  }
  
  protected AbstractRule createPlusRule(ShareRuleValue share) {
    Col2Rule col2Rule = new Col2Rule(share);
    col2Rule.addExpression(createPlusExpression());
    col2Rule.addExpression(createMinusExpression());
    return (AbstractRule)col2Rule;
  }
  
  protected AbstractExpression createPlusExpression() {
    return (AbstractExpression)new PlusExpression();
  }
  
  protected AbstractExpression createMinusExpression() {
    return (AbstractExpression)new MinusExpression();
  }
  
  protected AbstractRule createMultRule(ShareRuleValue share) {
    Col2Rule col2Rule = new Col2Rule(share);
    col2Rule.addExpression(createMultExpression());
    col2Rule.addExpression(createDivExpression());
    col2Rule.addExpression(createModExpression());
    return (AbstractRule)col2Rule;
  }
  
  protected AbstractExpression createMultExpression() {
    return (AbstractExpression)new MultExpression();
  }
  
  protected AbstractExpression createDivExpression() {
    return (AbstractExpression)new DivExpression();
  }
  
  protected AbstractExpression createModExpression() {
    return (AbstractExpression)new ModExpression();
  }
  
  protected AbstractRule createSignRule(ShareRuleValue share) {
    SignRule signRule = new SignRule(share);
    signRule.addExpression(createSignPlusExpression());
    signRule.addExpression(createSignMinusExpression());
    signRule.addExpression(createBitNotExpression());
    signRule.addExpression(createNotExpression());
    signRule.addExpression(createIncBeforeExpression());
    signRule.addExpression(createDecBeforeExpression());
    return (AbstractRule)signRule;
  }
  
  protected AbstractExpression createSignPlusExpression() {
    return (AbstractExpression)new SignPlusExpression();
  }
  
  protected AbstractExpression createSignMinusExpression() {
    return (AbstractExpression)new SignMinusExpression();
  }
  
  protected AbstractExpression createBitNotExpression() {
    return (AbstractExpression)new BitNotExpression();
  }
  
  protected AbstractExpression createNotExpression() {
    return (AbstractExpression)new NotExpression();
  }
  
  protected AbstractExpression createIncBeforeExpression() {
    return (AbstractExpression)new IncBeforeExpression();
  }
  
  protected AbstractExpression createDecBeforeExpression() {
    return (AbstractExpression)new DecBeforeExpression();
  }
  
  protected AbstractRule createPowerRule(ShareRuleValue share) {
    Col2Rule col2Rule = new Col2Rule(share);
    col2Rule.addExpression(createPowerExpression());
    return (AbstractRule)col2Rule;
  }
  
  protected AbstractExpression createPowerExpression() {
    return (AbstractExpression)new PowerExpression();
  }
  
  protected AbstractRule createCol1AfterRule(ShareRuleValue share) {
    Col1AfterRule me = new Col1AfterRule(share);
    me.addExpression(me.func = createFunctionExpression());
    me.addExpression(me.array = createArrayExpression());
    me.addExpression(createIncAfterExpression());
    me.addExpression(createDecAfterExpression());
    me.addExpression(me.field = createFieldExpression());
    return (AbstractRule)me;
  }
  
  protected AbstractExpression createFunctionExpression() {
    return (AbstractExpression)new FunctionExpression();
  }
  
  protected AbstractExpression createArrayExpression() {
    return (AbstractExpression)new ArrayExpression();
  }
  
  protected AbstractExpression createIncAfterExpression() {
    return (AbstractExpression)new IncAfterExpression();
  }
  
  protected AbstractExpression createDecAfterExpression() {
    return (AbstractExpression)new DecAfterExpression();
  }
  
  protected AbstractExpression createFieldExpression() {
    return (AbstractExpression)new FieldExpression();
  }
  
  protected AbstractRule createPrimaryRule(ShareRuleValue share) {
    PrimaryRule primaryRule = new PrimaryRule(share);
    primaryRule.addExpression(createParenExpression());
    return (AbstractRule)primaryRule;
  }
  
  protected AbstractExpression createParenExpression() {
    return (AbstractExpression)new ParenExpression();
  }
  
  protected AbstractRule createFuncArgRule(ShareRuleValue share) {
    Col2Rule col2Rule = new Col2Rule(share);
    col2Rule.addExpression(createFuncArgExpression());
    return (AbstractRule)col2Rule;
  }
  
  protected AbstractExpression createFuncArgExpression() {
    return (AbstractExpression)new FuncArgExpression();
  }
  
  protected LexFactory getLexFactory() {
    if (this.defaultLexFactory == null)
      this.defaultLexFactory = new LexFactory(); 
    return this.defaultLexFactory;
  }
}
