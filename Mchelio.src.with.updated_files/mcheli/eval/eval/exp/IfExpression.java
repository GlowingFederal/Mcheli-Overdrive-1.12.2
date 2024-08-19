package mcheli.eval.eval.exp;

public class IfExpression extends Col3Expression {
  public IfExpression() {
    setOperator("?");
    setEndOperator(":");
  }
  
  protected IfExpression(IfExpression from, ShareExpValue s) {
    super(from, s);
  }
  
  public AbstractExpression dup(ShareExpValue s) {
    return new IfExpression(this, s);
  }
  
  public long evalLong() {
    if (this.exp1.evalLong() != 0L)
      return this.exp2.evalLong(); 
    return this.exp3.evalLong();
  }
  
  public double evalDouble() {
    if (this.exp1.evalDouble() != 0.0D)
      return this.exp2.evalDouble(); 
    return this.exp3.evalDouble();
  }
  
  public Object evalObject() {
    if (this.share.oper.bool(this.exp1.evalObject()))
      return this.exp2.evalObject(); 
    return this.exp3.evalObject();
  }
}
