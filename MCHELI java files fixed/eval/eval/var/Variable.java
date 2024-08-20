package mcheli.eval.eval.var;

public interface Variable {
  void setValue(Object paramObject1, Object paramObject2);
  
  Object getObject(Object paramObject);
  
  long evalLong(Object paramObject);
  
  double evalDouble(Object paramObject);
  
  Object getObject(Object paramObject, int paramInt);
  
  void setValue(Object paramObject1, int paramInt, Object paramObject2);
  
  Object getObject(Object paramObject, String paramString);
  
  void setValue(Object paramObject1, String paramString, Object paramObject2);
}
