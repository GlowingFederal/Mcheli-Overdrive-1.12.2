/*    */ package mcheli.eval.eval.func;
/*    */ 
/*    */ import java.lang.reflect.Method;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class InvokeFunction
/*    */   implements Function
/*    */ {
/*    */   public long evalLong(Object object, String name, Long[] args) throws Throwable {
/* 16 */     if (object == null)
/*    */     {
/* 18 */       return 0L;
/*    */     }
/*    */     
/* 21 */     Object r = callMethod(object, name, (Object[])args);
/* 22 */     return ((Number)r).longValue();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public double evalDouble(Object object, String name, Double[] args) throws Throwable {
/* 28 */     if (object == null)
/*    */     {
/* 30 */       return 0.0D;
/*    */     }
/*    */     
/* 33 */     Object r = callMethod(object, name, (Object[])args);
/* 34 */     return ((Number)r).doubleValue();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Object evalObject(Object object, String name, Object[] args) throws Throwable {
/* 40 */     if (object == null)
/*    */     {
/* 42 */       return null;
/*    */     }
/*    */     
/* 45 */     return callMethod(object, name, args);
/*    */   }
/*    */ 
/*    */   
/*    */   public static Object callMethod(Object obj, String name, Object[] args) throws Exception {
/* 50 */     Class<?> c = obj.getClass();
/* 51 */     Class<?>[] types = new Class[args.length];
/* 52 */     for (int i = 0; i < types.length; i++)
/*    */     {
/* 54 */       types[i] = args[i].getClass();
/*    */     }
/* 56 */     Method m = c.getMethod(name, types);
/* 57 */     return m.invoke(obj, args);
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\eval\eval\func\InvokeFunction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */