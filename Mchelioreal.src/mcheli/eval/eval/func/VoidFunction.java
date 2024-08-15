/*    */ package mcheli.eval.eval.func;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class VoidFunction
/*    */   implements Function
/*    */ {
/*    */   public long evalLong(Object object, String name, Long[] args) throws Throwable {
/* 14 */     System.out.println(object + "." + name + "関数が呼ばれた(long)");
/* 15 */     for (int i = 0; i < args.length; i++)
/*    */     {
/* 17 */       System.out.println("arg[" + i + "] " + args[i]);
/*    */     }
/*    */     
/* 20 */     return 0L;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public double evalDouble(Object object, String name, Double[] args) throws Throwable {
/* 26 */     System.out.println(object + "." + name + "関数が呼ばれた(double)");
/* 27 */     for (int i = 0; i < args.length; i++)
/*    */     {
/* 29 */       System.out.println("arg[" + i + "] " + args[i]);
/*    */     }
/*    */     
/* 32 */     return 0.0D;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Object evalObject(Object object, String name, Object[] args) throws Throwable {
/* 38 */     System.out.println(object + "." + name + "関数が呼ばれた(Object)");
/* 39 */     for (int i = 0; i < args.length; i++)
/*    */     {
/* 41 */       System.out.println("arg[" + i + "] " + args[i]);
/*    */     }
/*    */     
/* 44 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\eval\eval\func\VoidFunction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */