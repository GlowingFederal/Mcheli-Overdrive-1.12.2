/*     */ package mcheli.eval.eval.srch;
/*     */ 
/*     */ import mcheli.eval.eval.exp.AbstractExpression;
/*     */ import mcheli.eval.eval.exp.Col1Expression;
/*     */ import mcheli.eval.eval.exp.Col2Expression;
/*     */ import mcheli.eval.eval.exp.Col3Expression;
/*     */ import mcheli.eval.eval.exp.FunctionExpression;
/*     */ import mcheli.eval.eval.exp.WordExpression;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SearchAdapter
/*     */   implements Search
/*     */ {
/*     */   protected boolean end = false;
/*     */   
/*     */   public boolean end() {
/*  23 */     return this.end;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setEnd() {
/*  28 */     this.end = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void search(AbstractExpression exp) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void search0(WordExpression exp) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean search1_begin(Col1Expression exp) {
/*  44 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void search1_end(Col1Expression exp) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean search2_begin(Col2Expression exp) {
/*  55 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean search2_2(Col2Expression exp) {
/*  61 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void search2_end(Col2Expression exp) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean search3_begin(Col3Expression exp) {
/*  72 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean search3_2(Col3Expression exp3) {
/*  78 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean search3_3(Col3Expression exp) {
/*  84 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void search3_end(Col3Expression exp) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean searchFunc_begin(FunctionExpression exp) {
/*  95 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean searchFunc_2(FunctionExpression exp) {
/* 101 */     return false;
/*     */   }
/*     */   
/*     */   public void searchFunc_end(FunctionExpression exp) {}
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\eval\eval\srch\SearchAdapter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */