package mcheli.debug._v1;

import java.io.PrintStream;
import java.util.ArrayDeque;
import java.util.Deque;

public class PrintStreamWrapper {
  private final PrintStream ps;
  
  private Deque<String> stack;
  
  private String nestStr;
  
  private PrintStreamWrapper(PrintStream stream) {
    this.ps = stream;
    this.stack = new ArrayDeque<>();
    this.nestStr = "  ";
  }
  
  public void setNestStr(String nestStr) {
    this.nestStr = nestStr;
  }
  
  public PrintStreamWrapper push(String label) {
    println(label);
    return push();
  }
  
  public PrintStreamWrapper push() {
    this.stack.addLast(this.nestStr);
    return this;
  }
  
  public PrintStreamWrapper pop() {
    this.stack.removeLast();
    return this;
  }
  
  private void printNest() {
    this.stack.forEach(str -> this.ps.print(str));
  }
  
  public void println(Object o) {
    printNest();
    this.ps.println(o);
  }
  
  public void println(String s) {
    printNest();
    this.ps.println(s);
  }
  
  public void println() {
    printNest();
    this.ps.println();
  }
  
  public static PrintStreamWrapper create(PrintStream stream) {
    return new PrintStreamWrapper(stream);
  }
}
