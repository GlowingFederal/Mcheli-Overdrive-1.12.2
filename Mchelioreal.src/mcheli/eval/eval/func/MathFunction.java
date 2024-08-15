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
/*    */ public class MathFunction
/*    */   implements Function
/*    */ {
/*    */   public long evalLong(Object object, String name, Long[] args) throws Throwable {
/* 16 */     Class<?>[] types = new Class[args.length];
/* 17 */     for (int i = 0; i < types.length; i++)
/*    */     {
/* 19 */       types[i] = long.class;
/*    */     }
/*    */     
/* 22 */     Method m = Math.class.getMethod(name, types);
/* 23 */     Object ret = m.invoke(null, (Object[])args);
/*    */     
/* 25 */     return ((Long)ret).longValue();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public double evalDouble(Object object, String name, Double[] args) throws Throwable {
/* 31 */     Class<?>[] types = new Class[args.length];
/* 32 */     for (int i = 0; i < types.length; i++)
/*    */     {
/* 34 */       types[i] = double.class;
/*    */     }
/*    */     
/* 37 */     Method m = Math.class.getMethod(name, types);
/* 38 */     Object ret = m.invoke(null, (Object[])args);
/*    */     
/* 40 */     return ((Double)ret).doubleValue();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Object evalObject(Object object, String name, Object[] args) throws Throwable {
/* 46 */     Class<?>[] types = new Class[args.length];
/* 47 */     for (int i = 0; i < types.length; i++) {
/*    */       
/* 49 */       Class<?> c = args[i].getClass();
/* 50 */       if (Double.class.isAssignableFrom(c)) {
/*    */         
/* 52 */         c = double.class;
/*    */       }
/* 54 */       else if (Float.class.isAssignableFrom(c)) {
/*    */         
/* 56 */         c = float.class;
/*    */       }
/* 58 */       else if (Integer.class.isAssignableFrom(c)) {
/*    */         
/* 60 */         c = int.class;
/*    */       }
/* 62 */       else if (Number.class.isAssignableFrom(c)) {
/*    */         
/* 64 */         c = long.class;
/*    */       } 
/* 66 */       types[i] = c;
/*    */     } 
/*    */     
/* 69 */     Method m = Math.class.getMethod(name, types);
/* 70 */     return m.invoke(null, args);
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\eval\eval\func\MathFunction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */