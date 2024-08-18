package mcheli.eval.eval.rule;

import mcheli.eval.eval.EvalException;
import mcheli.eval.eval.exp.AbstractExpression;
import mcheli.eval.eval.exp.CharExpression;
import mcheli.eval.eval.exp.Col1Expression;
import mcheli.eval.eval.exp.NumberExpression;
import mcheli.eval.eval.exp.StringExpression;
import mcheli.eval.eval.exp.VariableExpression;
import mcheli.eval.eval.lex.Lex;

public class PrimaryRule extends AbstractRule {
  public PrimaryRule(ShareRuleValue share) {
    super(share);
  }
  
  public final AbstractExpression parse(Lex lex) {
    AbstractExpression n;
    AbstractExpression w;
    AbstractExpression s;
    AbstractExpression c;
    String ope;
    int pos;
    switch (lex.getType()) {
      case 2147483633:
        n = NumberExpression.create(lex, this.prio);
        lex.next();
        return n;
      case 2147483632:
        w = VariableExpression.create(lex, this.prio);
        lex.next();
        return w;
      case 2147483635:
        s = StringExpression.create(lex, this.prio);
        lex.next();
        return s;
      case 2147483636:
        c = CharExpression.create(lex, this.prio);
        lex.next();
        return c;
      case 2147483634:
        ope = lex.getOperator();
        pos = lex.getPos();
        if (isMyOperator(ope)) {
          if (ope.equals(this.share.paren.getOperator()))
            return parseParen(lex, ope, pos); 
          return Col1Expression.create(newExpression(ope, lex.getShare()), lex.getString(), pos, 
              parse(lex.next()));
        } 
        throw new EvalException(1002, lex);
      case 2147483647:
        throw new EvalException(1004, lex);
    } 
    throw new EvalException(1003, lex);
  }
  
  protected AbstractExpression parseParen(Lex lex, String ope, int pos) {
    AbstractExpression s = this.share.topRule.parse(lex.next());
    if (!lex.isOperator(this.share.paren.getEndOperator()))
      throw new EvalException(1001, new String[] { this.share.paren
            
            .getEndOperator() }, lex); 
    lex.next();
    return Col1Expression.create(newExpression(ope, lex.getShare()), lex.getString(), pos, s);
  }
}
