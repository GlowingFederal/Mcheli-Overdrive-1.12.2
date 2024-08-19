package mcheli.eval.eval.exp;

public abstract class Col2Expression extends AbstractExpression {
  public AbstractExpression expl;
  
  public AbstractExpression expr;
  
  public static AbstractExpression create(AbstractExpression exp, String string, int pos, AbstractExpression x, AbstractExpression y) {
    Col2Expression n = (Col2Expression)exp;
    n.setExpression(x, y);
    n.setPos(string, pos);
    return n;
  }
  
  protected Col2Expression() {}
  
  protected Col2Expression(Col2Expression from, ShareExpValue s) {
    super(from, s);
    if (from.expl != null)
      this.expl = from.expl.dup(s); 
    if (from.expr != null)
      this.expr = from.expr.dup(s); 
  }
  
  public final void setExpression(AbstractExpression x, AbstractExpression y) {
    this.expl = x;
    this.expr = y;
  }
  
  protected final int getCols() {
    return 2;
  }
  
  protected final int getFirstPos() {
    return this.expl.getFirstPos();
  }
  
  public long evalLong() {
    return operateLong(this.expl.evalLong(), this.expr.evalLong());
  }
  
  public double evalDouble() {
    return operateDouble(this.expl.evalDouble(), this.expr.evalDouble());
  }
  
  public Object evalObject() {
    return operateObject(this.expl.evalObject(), this.expr.evalObject());
  }
  
  protected abstract long operateLong(long paramLong1, long paramLong2);
  
  protected abstract double operateDouble(double paramDouble1, double paramDouble2);
  
  protected abstract Object operateObject(Object paramObject1, Object paramObject2);
  
  protected void search() {
    this.share.srch.search(this);
    if (this.share.srch.end())
      return; 
    if (this.share.srch.search2_begin(this))
      return; 
    if (this.share.srch.end())
      return; 
    this.expl.search();
    if (this.share.srch.end())
      return; 
    if (this.share.srch.search2_2(this))
      return; 
    if (this.share.srch.end())
      return; 
    this.expr.search();
    if (this.share.srch.end())
      return; 
    this.share.srch.search2_end(this);
  }
  
  protected AbstractExpression replace() {
    this.expl = this.expl.replace();
    this.expr = this.expr.replace();
    return this.share.repl.replace2(this);
  }
  
  protected AbstractExpression replaceVar() {
    this.expl = this.expl.replaceVar();
    this.expr = this.expr.replaceVar();
    return this.share.repl.replaceVar2(this);
  }
  
  public boolean equals(Object obj) {
    if (obj instanceof Col2Expression) {
      Col2Expression e = (Col2Expression)obj;
      if (getClass() == e.getClass())
        return (this.expl.equals(e.expl) && this.expr.equals(e.expr)); 
    } 
    return false;
  }
  
  public int hashCode() {
    return getClass().hashCode() ^ this.expl.hashCode() ^ this.expr.hashCode() * 2;
  }
  
  public void dump(int n) {
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < n; i++)
      sb.append(' '); 
    sb.append(getOperator());
    System.out.println(sb.toString());
    this.expl.dump(n + 1);
    this.expr.dump(n + 1);
  }
  
  public String toString() {
    StringBuffer sb = new StringBuffer();
    if (this.expl.getPriority() < this.prio) {
      sb.append(this.share.paren.getOperator());
      sb.append(this.expl.toString());
      sb.append(this.share.paren.getEndOperator());
    } else {
      sb.append(this.expl.toString());
    } 
    sb.append(toStringLeftSpace());
    sb.append(getOperator());
    sb.append(' ');
    if (this.expr.getPriority() < this.prio) {
      sb.append(this.share.paren.getOperator());
      sb.append(this.expr.toString());
      sb.append(this.share.paren.getEndOperator());
    } else {
      sb.append(this.expr.toString());
    } 
    return sb.toString();
  }
  
  protected String toStringLeftSpace() {
    return " ";
  }
}
