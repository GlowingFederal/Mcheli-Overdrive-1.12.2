package mcheli.eval.eval.oper;

import java.math.BigDecimal;
import java.math.BigInteger;

public class JavaExOperator implements Operator {
  boolean inLong(Object x) {
    if (x instanceof Long)
      return true; 
    if (x instanceof Integer)
      return true; 
    if (x instanceof Short)
      return true; 
    if (x instanceof Byte)
      return true; 
    if (x instanceof BigInteger)
      return true; 
    if (x instanceof BigDecimal)
      return true; 
    return false;
  }
  
  long l(Object x) {
    return ((Number)x).longValue();
  }
  
  boolean inDouble(Object x) {
    if (x instanceof Double)
      return true; 
    if (x instanceof Float)
      return true; 
    return false;
  }
  
  double d(Object x) {
    return ((Number)x).doubleValue();
  }
  
  Object n(long n, Object x) {
    if (x instanceof Long)
      return new Long(n); 
    if (x instanceof Double)
      return new Double(n); 
    if (x instanceof Integer)
      return new Integer((int)n); 
    if (x instanceof Short)
      return new Short((short)(int)n); 
    if (x instanceof Byte)
      return new Byte((byte)(int)n); 
    if (x instanceof Float)
      return new Float((float)n); 
    if (x instanceof BigInteger)
      return BigInteger.valueOf(n); 
    if (x instanceof BigDecimal)
      return BigDecimal.valueOf(n); 
    if (x instanceof String)
      return String.valueOf(n); 
    return new Long(n);
  }
  
  Object n(long n, Object x, Object y) {
    if (x instanceof Byte || y instanceof Byte)
      return new Byte((byte)(int)n); 
    if (x instanceof Short || y instanceof Short)
      return new Short((short)(int)n); 
    if (x instanceof Integer || y instanceof Integer)
      return new Integer((int)n); 
    if (x instanceof Long || y instanceof Long)
      return new Long(n); 
    if (x instanceof BigInteger || y instanceof BigInteger)
      return BigInteger.valueOf(n); 
    if (x instanceof BigDecimal || y instanceof BigDecimal)
      return BigDecimal.valueOf(n); 
    if (x instanceof Float || y instanceof Float)
      return new Float((float)n); 
    if (x instanceof Double || y instanceof Double)
      return new Double(n); 
    if (x instanceof String || y instanceof String)
      return String.valueOf(n); 
    return new Long(n);
  }
  
  Object n(double n, Object x) {
    if (x instanceof Float)
      return new Float(n); 
    if (x instanceof String)
      return String.valueOf(n); 
    return new Double(n);
  }
  
  Object n(double n, Object x, Object y) {
    if (x instanceof Float || y instanceof Float)
      return new Float(n); 
    if (x instanceof Number || y instanceof Number)
      return new Double(n); 
    if (x instanceof String || y instanceof String)
      return String.valueOf(n); 
    return new Double(n);
  }
  
  Object nn(long n, Object x) {
    if (x instanceof BigDecimal)
      return BigDecimal.valueOf(n); 
    if (x instanceof BigInteger)
      return BigInteger.valueOf(n); 
    return new Long(n);
  }
  
  Object nn(long n, Object x, Object y) {
    if (x instanceof BigDecimal || y instanceof BigDecimal)
      return BigDecimal.valueOf(n); 
    if (x instanceof BigInteger || y instanceof BigInteger)
      return BigInteger.valueOf(n); 
    return new Long(n);
  }
  
  Object nn(double n, Object x) {
    if (inLong(x))
      return new Long((long)n); 
    return new Double(n);
  }
  
  Object nn(double n, Object x, Object y) {
    if (inLong(x) && inLong(y))
      return new Long((long)n); 
    return new Double(n);
  }
  
  RuntimeException undefined(Object x) {
    String c = null;
    if (x != null)
      c = x.getClass().getName(); 
    return new RuntimeException("未定義単項演算：" + c);
  }
  
  RuntimeException undefined(Object x, Object y) {
    String c1 = null;
    String c2 = null;
    if (x != null)
      c1 = x.getClass().getName(); 
    if (y != null)
      c2 = y.getClass().getName(); 
    return new RuntimeException("未定義二項演算：" + c1 + " , " + c2);
  }
  
  public Object power(Object x, Object y) {
    if (x == null && y == null)
      return null; 
    return nn(Math.pow(d(x), d(y)), x, y);
  }
  
  public Object signPlus(Object x) {
    return x;
  }
  
  public Object signMinus(Object x) {
    if (x == null)
      return null; 
    if (inLong(x))
      return n(-l(x), x); 
    if (inDouble(x))
      return n(-d(x), x); 
    if (x instanceof Boolean)
      return x; 
    throw undefined(x);
  }
  
  public Object plus(Object x, Object y) {
    if (x == null && y == null)
      return null; 
    if (inLong(x) && inLong(y))
      return nn(l(x) + l(y), x, y); 
    if (inDouble(x) && inDouble(y))
      return nn(d(x) + d(y), x, y); 
    if (x instanceof String || y instanceof String)
      return String.valueOf(x) + String.valueOf(y); 
    if (x instanceof Character || y instanceof Character)
      return String.valueOf(x) + String.valueOf(y); 
    throw undefined(x, y);
  }
  
  public Object minus(Object x, Object y) {
    if (x == null && y == null)
      return null; 
    if (inLong(x) && inLong(y))
      return nn(l(x) - l(y), x, y); 
    if (inDouble(x) && inDouble(y))
      return nn(d(x) - d(y), x, y); 
    throw undefined(x, y);
  }
  
  public Object mult(Object x, Object y) {
    if (x == null && y == null)
      return null; 
    if (inLong(x) && inLong(y))
      return nn(l(x) * l(y), x, y); 
    if (inDouble(x) && inDouble(y))
      return nn(d(x) * d(y), x, y); 
    String s = null;
    int ct = 0;
    boolean str = false;
    if (x instanceof String && y instanceof Number) {
      s = (String)x;
      ct = (int)l(y);
      str = true;
    } else if (y instanceof String && x instanceof Number) {
      s = (String)y;
      ct = (int)l(x);
      str = true;
    } 
    if (str) {
      StringBuffer sb = new StringBuffer(s.length() * ct);
      for (int i = 0; i < ct; i++)
        sb.append(s); 
      return sb.toString();
    } 
    throw undefined(x, y);
  }
  
  public Object div(Object x, Object y) {
    if (x == null && y == null)
      return null; 
    if (inLong(x) && inLong(y))
      return nn(l(x) / l(y), x); 
    if (inDouble(x) && inDouble(y))
      return nn(d(x) / d(y), x); 
    if (x instanceof String && y instanceof String) {
      String s = (String)x;
      String r = (String)y;
      return s.split(r);
    } 
    throw undefined(x, y);
  }
  
  public Object mod(Object x, Object y) {
    if (x == null && y == null)
      return null; 
    if (inLong(x) && inLong(y))
      return nn(l(x) % l(y), x); 
    if (inDouble(x) && inDouble(y))
      return nn(d(x) % d(y), x); 
    throw undefined(x, y);
  }
  
  public Object bitNot(Object x) {
    if (x == null)
      return null; 
    if (x instanceof Number)
      return n(l(x) ^ 0xFFFFFFFFFFFFFFFFL, x); 
    throw undefined(x);
  }
  
  public Object shiftLeft(Object x, Object y) {
    if (x == null && y == null)
      return null; 
    if (inLong(x) && inLong(y))
      return n(l(x) << (int)l(y), x); 
    if (inDouble(x) && inDouble(y))
      return n(d(x) * Math.pow(2.0D, d(y)), x); 
    throw undefined(x, y);
  }
  
  public Object shiftRight(Object x, Object y) {
    if (x == null && y == null)
      return null; 
    if (inLong(x) && inLong(y))
      return n(l(x) >> (int)l(y), x); 
    if (inDouble(x) && inDouble(y))
      return n(d(x) / Math.pow(2.0D, d(y)), x); 
    throw undefined(x, y);
  }
  
  public Object shiftRightLogical(Object x, Object y) {
    if (x == null && y == null)
      return null; 
    if (x instanceof Byte && y instanceof Number)
      return n((l(x) & 0xFFL) >>> (int)l(y), x); 
    if (x instanceof Short && y instanceof Number)
      return n((l(x) & 0xFFFFL) >>> (int)l(y), x); 
    if (x instanceof Integer && y instanceof Number)
      return n((l(x) & 0xFFFFFFFFFFFFFFFFL) >>> (int)l(y), x); 
    if (inLong(x) && y instanceof Number)
      return n(l(x) >>> (int)l(y), x); 
    if (inDouble(x) && y instanceof Number) {
      double t = d(x);
      if (t < 0.0D)
        t = -t; 
      return n(t / Math.pow(2.0D, d(y)), x);
    } 
    throw undefined(x, y);
  }
  
  public Object bitAnd(Object x, Object y) {
    if (x == null && y == null)
      return null; 
    if (x instanceof Number && y instanceof Number)
      return n(l(x) & l(y), x); 
    throw undefined(x, y);
  }
  
  public Object bitOr(Object x, Object y) {
    if (x == null && y == null)
      return null; 
    if (x instanceof Number && y instanceof Number)
      return n(l(x) | l(y), x); 
    throw undefined(x, y);
  }
  
  public Object bitXor(Object x, Object y) {
    if (x == null && y == null)
      return null; 
    if (x instanceof Number && y instanceof Number)
      return n(l(x) ^ l(y), x); 
    throw undefined(x, y);
  }
  
  public Object not(Object x) {
    if (x == null)
      return null; 
    if (x instanceof Boolean)
      return ((Boolean)x).booleanValue() ? Boolean.FALSE : Boolean.TRUE; 
    if (x instanceof Number) {
      if (l(x) == 0L)
        return Boolean.TRUE; 
      return Boolean.FALSE;
    } 
    throw undefined(x);
  }
  
  int compare(Object x, Object y) {
    if (x == null && y == null)
      return 0; 
    if (x == null && y != null)
      return -1; 
    if (x != null && y == null)
      return 1; 
    if (inLong(x) && inLong(y)) {
      long c = l(x) - l(y);
      if (c == 0L)
        return 0; 
      if (c < 0L)
        return -1; 
      return 1;
    } 
    if (x instanceof Number && y instanceof Number) {
      double n = d(x) - d(y);
      if (n == 0.0D)
        return 0; 
      return (n < 0.0D) ? -1 : 1;
    } 
    Class<?> xc = x.getClass();
    Class<?> yc = y.getClass();
    if (xc.isAssignableFrom(yc) && x instanceof Comparable)
      return ((Comparable<Object>)x).compareTo(y); 
    if (yc.isAssignableFrom(xc) && y instanceof Comparable)
      return -((Comparable)y).compareTo((T)x); 
    if (x.equals(y))
      return 0; 
    throw undefined(x, y);
  }
  
  public Object equal(Object x, Object y) {
    return (compare(x, y) == 0) ? Boolean.TRUE : Boolean.FALSE;
  }
  
  public Object notEqual(Object x, Object y) {
    return (compare(x, y) != 0) ? Boolean.TRUE : Boolean.FALSE;
  }
  
  public Object lessThan(Object x, Object y) {
    return (compare(x, y) < 0) ? Boolean.TRUE : Boolean.FALSE;
  }
  
  public Object lessEqual(Object x, Object y) {
    return (compare(x, y) <= 0) ? Boolean.TRUE : Boolean.FALSE;
  }
  
  public Object greaterThan(Object x, Object y) {
    return (compare(x, y) > 0) ? Boolean.TRUE : Boolean.FALSE;
  }
  
  public Object greaterEqual(Object x, Object y) {
    return (compare(x, y) >= 0) ? Boolean.TRUE : Boolean.FALSE;
  }
  
  public boolean bool(Object x) {
    if (x == null)
      return false; 
    if (x instanceof Boolean)
      return ((Boolean)x).booleanValue(); 
    if (x instanceof Number)
      return (((Number)x).longValue() != 0L); 
    return Boolean.valueOf(x.toString()).booleanValue();
  }
  
  public Object inc(Object x, int inc) {
    if (x == null)
      return null; 
    if (inLong(x))
      return n(l(x) + inc, x); 
    if (inDouble(x))
      return n(d(x) + inc, x); 
    throw undefined(x);
  }
}
