package mcheli.__helper.debug;

import mcheli.debug._v1.PrintStreamWrapper;

public interface DebugInfoObject {
  void printInfo(PrintStreamWrapper paramPrintStreamWrapper);
  
  default void printInfo() {
    printInfo(PrintStreamWrapper.create(System.out));
  }
}
