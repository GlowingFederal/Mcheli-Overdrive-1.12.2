package mcheli.eval.eval.func;

import java.lang.reflect.Method;

public class InvokeFunction implements Function {
  public long evalLong(Object object, String name, Long[] args) throws Throwable {
    if (object == null)
      return 0L; 
    Object r = callMethod(object, name, (Object[])args);
    return ((Number)r).longValue();
  }
  
  public double evalDouble(Object object, String name, Double[] args) throws Throwable {
    if (object == null)
      return 0.0D; 
    Object r = callMethod(object, name, (Object[])args);
    return ((Number)r).doubleValue();
  }
  
  public Object evalObject(Object object, String name, Object[] args) throws Throwable {
    if (object == null)
      return null; 
    return callMethod(object, name, args);
  }
  
  public static Object callMethod(Object obj, String name, Object[] args) throws Exception {
    Class<?> c = obj.getClass();
    Class<?>[] types = new Class[args.length];
    for (int i = 0; i < types.length; i++)
      types[i] = args[i].getClass(); 
    Method m = c.getMethod(name, types);
    return m.invoke(obj, args);
  }
}
