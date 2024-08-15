/*    */ package mcheli.eval.eval.ref;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RefactorFuncName
/*    */   extends RefactorAdapter
/*    */ {
/*    */   protected Class<?> targetClass;
/*    */   protected String oldName;
/*    */   protected String newName;
/*    */   
/*    */   public RefactorFuncName(Class<?> targetClass, String oldName, String newName) {
/* 19 */     this.targetClass = targetClass;
/* 20 */     this.oldName = oldName;
/* 21 */     this.newName = newName;
/* 22 */     if (oldName == null || newName == null)
/*    */     {
/* 24 */       throw new NullPointerException();
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getNewFuncName(Object target, String name) {
/* 31 */     if (!name.equals(this.oldName))
/* 32 */       return null; 
/* 33 */     if (this.targetClass == null) {
/*    */       
/* 35 */       if (target == null)
/*    */       {
/* 37 */         return this.newName;
/*    */       }
/*    */     }
/* 40 */     else if (target != null && this.targetClass.isAssignableFrom(target.getClass())) {
/*    */       
/* 42 */       return this.newName;
/*    */     } 
/* 44 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\eval\eval\ref\RefactorFuncName.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */