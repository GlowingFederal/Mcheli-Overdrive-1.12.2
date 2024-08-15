/*    */ package mcheli.eval.eval.exp;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class Col1AfterExpression
/*    */   extends Col1Expression
/*    */ {
/*    */   protected Col1AfterExpression() {}
/*    */   
/*    */   protected Col1AfterExpression(Col1Expression from, ShareExpValue s) {
/* 17 */     super(from, s);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected AbstractExpression replace() {
/* 23 */     this.exp = this.exp.replaceVar();
/* 24 */     return this.share.repl.replaceVar1(this);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected AbstractExpression replaceVar() {
/* 30 */     return replace();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 36 */     if (this.exp == null)
/*    */     {
/* 38 */       return getOperator();
/*    */     }
/* 40 */     StringBuffer sb = new StringBuffer();
/* 41 */     if (this.exp.getPriority() > this.prio) {
/*    */       
/* 43 */       sb.append(this.exp.toString());
/* 44 */       sb.append(getOperator());
/*    */     }
/* 46 */     else if (this.exp.getPriority() == this.prio) {
/*    */       
/* 48 */       sb.append(this.exp.toString());
/* 49 */       sb.append(' ');
/* 50 */       sb.append(getOperator());
/*    */     }
/*    */     else {
/*    */       
/* 54 */       sb.append(this.share.paren.getOperator());
/* 55 */       sb.append(this.exp.toString());
/* 56 */       sb.append(this.share.paren.getEndOperator());
/* 57 */       sb.append(getOperator());
/*    */     } 
/* 59 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\eval\eval\exp\Col1AfterExpression.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */