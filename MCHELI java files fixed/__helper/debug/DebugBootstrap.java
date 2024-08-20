package mcheli.__helper.debug;

import mcheli.MCH_ClientProxy;
import mcheli.MCH_CommonProxy;
import mcheli.MCH_MOD;
import mcheli.__helper.MCH_Logger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DebugBootstrap {
  private static final Logger LOGGER = LogManager.getLogger("Debug log");
  
  public static void init() {
    MCH_Logger.setLogger(LOGGER);
    MCH_MOD.instance = new MCH_MOD();
    MCH_MOD.proxy = (MCH_CommonProxy)new MCH_ClientProxy();
  }
}
