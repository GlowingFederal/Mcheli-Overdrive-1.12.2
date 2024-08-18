package mcheli.eval.eval.exp;

import mcheli.eval.eval.repl.ReplaceAdapter;

public class OptimizeObject extends ReplaceAdapter {
  protected boolean isConst(AbstractExpression x) {
    return (x instanceof NumberExpression || x instanceof StringExpression || x instanceof CharExpression);
  }
  
  protected boolean isTrue(AbstractExpression x) {
    return (x.evalLong() != 0L);
  }
  
  protected AbstractExpression toConst(AbstractExpression exp) {
    try {
      Object val = exp.evalObject();
      if (val instanceof String)
        return StringExpression.create(exp, (String)val); 
      if (val instanceof Character)
        return CharExpression.create(exp, val.toString()); 
      return NumberExpression.create(exp, val.toString());
    } catch (Exception exception) {
      return exp;
    } 
  }
  
  public AbstractExpression replace0(WordExpression exp) {
    if (exp instanceof VariableExpression)
      return toConst(exp); 
    return exp;
  }
  
  public AbstractExpression replace1(Col1Expression exp) {
    if (exp instanceof ParenExpression)
      return exp.exp; 
    if (exp instanceof SignPlusExpression)
      return exp.exp; 
    if (isConst(exp.exp))
      return toConst(exp); 
    return exp;
  }
  
  public AbstractExpression replace2(Col2Expression exp) {
    boolean const_l = isConst(exp.expl);
    boolean const_r = isConst(exp.expr);
    if (const_l && const_r)
      return toConst(exp); 
    return exp;
  }
  
  public AbstractExpression replace2(Col2OpeExpression exp) {
    if (exp instanceof ArrayExpression) {
      if (isConst(exp.expr))
        return toConst(exp); 
      return exp;
    } 
    if (exp instanceof FieldExpression)
      return toConst(exp); 
    boolean const_l = isConst(exp.expl);
    if (exp instanceof AndExpression) {
      if (const_l) {
        if (isTrue(exp.expl))
          return exp.expr; 
        return exp.expl;
      } 
      return exp;
    } 
    if (exp instanceof OrExpression) {
      if (const_l) {
        if (isTrue(exp.expl))
          return exp.expl; 
        return exp.expr;
      } 
      return exp;
    } 
    if (exp instanceof CommaExpression) {
      if (const_l)
        return exp.expr; 
      return exp;
    } 
    return exp;
  }
  
  public AbstractExpression replace3(Col3Expression exp) {
    if (isConst(exp.exp1)) {
      if (isTrue(exp.exp1))
        return exp.exp2; 
      return exp.exp3;
    } 
    return exp;
  }
}
