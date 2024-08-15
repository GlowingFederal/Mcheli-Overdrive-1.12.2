/*    */ package mcheli.debug._v1;
/*    */ 
/*    */ import java.io.PrintStream;
/*    */ import java.util.ArrayDeque;
/*    */ import java.util.Deque;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PrintStreamWrapper
/*    */ {
/*    */   private final PrintStream ps;
/*    */   private Deque<String> stack;
/*    */   private String nestStr;
/*    */   
/*    */   private PrintStreamWrapper(PrintStream stream) {
/* 23 */     this.ps = stream;
/* 24 */     this.stack = new ArrayDeque<>();
/* 25 */     this.nestStr = "  ";
/*    */   }
/*    */ 
/*    */   
/*    */   public void setNestStr(String nestStr) {
/* 30 */     this.nestStr = nestStr;
/*    */   }
/*    */ 
/*    */   
/*    */   public PrintStreamWrapper push(String label) {
/* 35 */     println(label);
/* 36 */     return push();
/*    */   }
/*    */ 
/*    */   
/*    */   public PrintStreamWrapper push() {
/* 41 */     this.stack.addLast(this.nestStr);
/* 42 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public PrintStreamWrapper pop() {
/* 47 */     this.stack.removeLast();
/* 48 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   private void printNest() {
/* 53 */     this.stack.forEach(str -> this.ps.print(str));
/*    */   }
/*    */ 
/*    */   
/*    */   public void println(Object o) {
/* 58 */     printNest();
/* 59 */     this.ps.println(o);
/*    */   }
/*    */ 
/*    */   
/*    */   public void println(String s) {
/* 64 */     printNest();
/* 65 */     this.ps.println(s);
/*    */   }
/*    */ 
/*    */   
/*    */   public void println() {
/* 70 */     printNest();
/* 71 */     this.ps.println();
/*    */   }
/*    */ 
/*    */   
/*    */   public static PrintStreamWrapper create(PrintStream stream) {
/* 76 */     return new PrintStreamWrapper(stream);
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\debug\_v1\PrintStreamWrapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */