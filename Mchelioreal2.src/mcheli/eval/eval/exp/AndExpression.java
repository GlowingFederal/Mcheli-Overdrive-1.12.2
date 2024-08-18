package mcheli.eval.eval.exp;

public class AndExpression extends Col2OpeExpression {
  public AndExpression() {
    setOperator("&&");
  }
  
  protected AndExpression(AndExpression from, ShareExpValue s) {
    super(from, s);
  }
  
  public AbstractExpression dup(ShareExpValue s) {
    return new AndExpression(this, s);
  }
  
  public long evalLong() {
    long val = this.expl.evalLong();
    if (val == 0L)
      return val; 
    return this.expr.evalLong();
  }
  
  public double evalDouble() {
    double val = this.expl.evalDouble();
    if (val == 0.0D)
      return val; 
    return this.expr.evalDouble();
  }
  
  public Object evalObject() {
    Object val = this.expl.evalObject();
    if (!this.share.oper.bool(val))
      return val; 
    return this.expr.evalObject();
  }
}
