package mcheli.eval.eval.func;

public interface Function {
  long evalLong(Object paramObject, String paramString, Long[] paramArrayOfLong) throws Throwable;
  
  double evalDouble(Object paramObject, String paramString, Double[] paramArrayOfDouble) throws Throwable;
  
  Object evalObject(Object paramObject, String paramString, Object[] paramArrayOfObject) throws Throwable;
}
