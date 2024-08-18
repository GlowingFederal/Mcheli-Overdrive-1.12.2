package mcheli.eval.eval.rule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import mcheli.eval.eval.exp.AbstractExpression;
import mcheli.eval.eval.exp.ShareExpValue;
import mcheli.eval.eval.lex.Lex;

public abstract class AbstractRule {
  public AbstractRule nextRule;
  
  protected ShareRuleValue share;
  
  private final Map<String, AbstractExpression> opes;
  
  public int prio;
  
  public AbstractRule(ShareRuleValue share) {
    this.opes = new HashMap<>();
    this.share = share;
  }
  
  public final void addExpression(AbstractExpression exp) {
    if (exp == null)
      return; 
    String ope = exp.getOperator();
    addOperator(ope, exp);
    addLexOperator(exp.getEndOperator());
    if (exp instanceof mcheli.eval.eval.exp.ParenExpression)
      this.share.paren = exp; 
  }
  
  public final void addOperator(String ope, AbstractExpression exp) {
    this.opes.put(ope, exp);
    addLexOperator(ope);
  }
  
  public final String[] getOperators() {
    List<String> list = new ArrayList<>();
    for (Iterator<String> i = this.opes.keySet().iterator(); i.hasNext();)
      list.add(i.next()); 
    return list.<String>toArray(new String[list.size()]);
  }
  
  public final void addLexOperator(String ope) {
    if (ope == null)
      return; 
    int n = ope.length() - 1;
    if (this.share.opeList[n] == null)
      this.share.opeList[n] = new ArrayList<>(); 
    this.share.opeList[n].add(ope);
  }
  
  protected final boolean isMyOperator(String ope) {
    return this.opes.containsKey(ope);
  }
  
  protected final AbstractExpression newExpression(String ope, ShareExpValue share) {
    try {
      AbstractExpression org = this.opes.get(ope);
      AbstractExpression n = org.dup(share);
      n.setPriority(this.prio);
      n.share = share;
      return n;
    } catch (RuntimeException e) {
      throw e;
    } catch (Exception e) {
      throw new RuntimeException(e);
    } 
  }
  
  public final void initPriority(int prio) {
    this.prio = prio;
    if (this.nextRule != null)
      this.nextRule.initPriority(prio + 1); 
  }
  
  protected abstract AbstractExpression parse(Lex paramLex);
}
