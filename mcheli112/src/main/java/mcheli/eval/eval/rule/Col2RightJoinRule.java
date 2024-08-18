package mcheli.eval.eval.rule;

import mcheli.eval.eval.exp.AbstractExpression;
import mcheli.eval.eval.exp.Col2Expression;
import mcheli.eval.eval.lex.Lex;

public class Col2RightJoinRule extends AbstractRule {
  public Col2RightJoinRule(ShareRuleValue share) {
    super(share);
  }
  
  protected AbstractExpression parse(Lex lex) {
    String ope;
    AbstractExpression x = this.nextRule.parse(lex);
    switch (lex.getType()) {
      case 2147483634:
        ope = lex.getOperator();
        if (isMyOperator(ope)) {
          int pos = lex.getPos();
          AbstractExpression y = parse(lex.next());
          x = Col2Expression.create(newExpression(ope, lex.getShare()), lex.getString(), pos, x, y);
        } 
        return x;
    } 
    return x;
  }
}
