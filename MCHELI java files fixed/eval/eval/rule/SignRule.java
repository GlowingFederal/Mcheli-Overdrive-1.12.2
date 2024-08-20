package mcheli.eval.eval.rule;

import mcheli.eval.eval.exp.AbstractExpression;
import mcheli.eval.eval.exp.Col1Expression;
import mcheli.eval.eval.lex.Lex;

public class SignRule extends AbstractRule {
  public SignRule(ShareRuleValue share) {
    super(share);
  }
  
  public AbstractExpression parse(Lex lex) {
    String ope;
    switch (lex.getType()) {
      case 2147483634:
        ope = lex.getOperator();
        if (isMyOperator(ope)) {
          int pos = lex.getPos();
          return Col1Expression.create(newExpression(ope, lex.getShare()), lex.getString(), pos, 
              parse(lex.next()));
        } 
        return this.nextRule.parse(lex);
    } 
    return this.nextRule.parse(lex);
  }
}
