package mcheli.eval.eval.rule;

import java.util.List;
import mcheli.eval.eval.EvalException;
import mcheli.eval.eval.Expression;
import mcheli.eval.eval.Rule;
import mcheli.eval.eval.exp.AbstractExpression;
import mcheli.eval.eval.exp.ShareExpValue;
import mcheli.eval.eval.lex.Lex;
import mcheli.eval.eval.lex.LexFactory;
import mcheli.eval.eval.oper.Operator;
import mcheli.eval.eval.ref.Refactor;
import mcheli.eval.eval.srch.Search;
import mcheli.eval.eval.var.Variable;

public class ShareRuleValue extends Rule {
  public AbstractRule topRule;
  
  public AbstractRule funcArgRule;
  
  public LexFactory lexFactory;
  
  protected List<String>[] opeList = (List<String>[])new List[4];
  
  public AbstractExpression paren;
  
  public Expression parse(String str) {
    if (str == null)
      return null; 
    if (str.trim().length() <= 0)
      return new EmptyExpression(); 
    ShareExpValue exp = new ShareExpValue();
    AbstractExpression x = parse(str, exp);
    exp.setAbstractExpression(x);
    return (Expression)exp;
  }
  
  public AbstractExpression parse(String str, ShareExpValue exp) {
    if (str == null)
      return null; 
    Lex lex = this.lexFactory.create(str, (List[])this.opeList, this, exp);
    lex.check();
    AbstractExpression x = this.topRule.parse(lex);
    if (lex.getType() != Integer.MAX_VALUE)
      throw new EvalException(1005, lex); 
    return x;
  }
  
  class EmptyExpression extends Expression {
    public long evalLong() {
      return 0L;
    }
    
    public double evalDouble() {
      return 0.0D;
    }
    
    public Object eval() {
      return null;
    }
    
    public void optimizeLong(Variable var) {}
    
    public void optimizeDouble(Variable var) {}
    
    public void optimize(Variable var, Operator oper) {}
    
    public void search(Search srch) {}
    
    public void refactorName(Refactor ref) {}
    
    public void refactorFunc(Refactor ref, Rule rule) {}
    
    public Expression dup() {
      return new EmptyExpression();
    }
    
    public boolean same(Expression obj) {
      if (obj instanceof EmptyExpression)
        return true; 
      return false;
    }
    
    public String toString() {
      return "";
    }
  }
}
