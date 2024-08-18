package mcheli.eval.eval.rule;

import mcheli.eval.eval.EvalException;
import mcheli.eval.eval.exp.AbstractExpression;
import mcheli.eval.eval.exp.Col3Expression;
import mcheli.eval.eval.lex.Lex;

public class IfRule extends AbstractRule {
  public AbstractExpression cond;
  
  public IfRule(ShareRuleValue share) {
    super(share);
  }
  
  protected AbstractExpression parse(Lex lex) {
    String ope;
    int pos;
    AbstractExpression x = this.nextRule.parse(lex);
    switch (lex.getType()) {
      case 2147483634:
        ope = lex.getOperator();
        pos = lex.getPos();
        if (isMyOperator(ope) && lex.isOperator(this.cond.getOperator()))
          x = parseCond(lex, x, ope, pos); 
        return x;
    } 
    return x;
  }
  
  protected AbstractExpression parseCond(Lex lex, AbstractExpression x, String ope, int pos) {
    AbstractExpression y = parse(lex.next());
    if (!lex.isOperator(this.cond.getEndOperator()))
      throw new EvalException(1001, new String[] { this.cond
            
            .getEndOperator() }, lex); 
    AbstractExpression z = parse(lex.next());
    x = Col3Expression.create(newExpression(ope, lex.getShare()), lex.getString(), pos, x, y, z);
    return x;
  }
}
