package mcheli.debug._v1.model;

import java.io.InputStream;
import mcheli.__helper.debug.DebugException;

public interface DebugModelParser {
  void parse(InputStream paramInputStream) throws DebugException;
}
