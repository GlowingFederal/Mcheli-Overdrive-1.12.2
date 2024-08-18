package mcheli;

import java.util.List;
import mcheli.__helper.addon.AddonResourceLocation;
import mcheli.__helper.info.ContentParseException;
import mcheli.__helper.info.IContentData;
import net.minecraft.util.math.Vec3d;

public abstract class MCH_BaseInfo implements IContentData {
  public final String filePath;
  
  public final AddonResourceLocation location;
  
  public MCH_BaseInfo(AddonResourceLocation location, String filePath) {
    this.location = location;
    this.filePath = filePath;
  }
  
  public boolean toBool(String s) {
    return s.equalsIgnoreCase("true");
  }
  
  public boolean toBool(String s, boolean defaultValue) {
    if (s.equalsIgnoreCase("true"))
      return true; 
    if (s.equalsIgnoreCase("false"))
      return false; 
    return defaultValue;
  }
  
  public float toFloat(String s) {
    return Float.parseFloat(s);
  }
  
  public float toFloat(String s, float min, float max) {
    float f = Float.parseFloat(s);
    return (f > max) ? max : ((f < min) ? min : f);
  }
  
  public double toDouble(String s) {
    return Double.parseDouble(s);
  }
  
  public Vec3d toVec3(String x, String y, String z) {
    return new Vec3d(toDouble(x), toDouble(y), toDouble(z));
  }
  
  public int toInt(String s) {
    return Integer.parseInt(s);
  }
  
  public int toInt(String s, int min, int max) {
    int f = Integer.parseInt(s);
    return (f > max) ? max : ((f < min) ? min : f);
  }
  
  public int hex2dec(String s) {
    if (!s.startsWith("0x") && !s.startsWith("0X") && s.indexOf(false) != 35)
      return (int)(Long.decode("0x" + s).longValue() & 0xFFFFFFFFFFFFFFFFL); 
    return (int)(Long.decode(s).longValue() & 0xFFFFFFFFFFFFFFFFL);
  }
  
  public String[] splitParam(String data) {
    return data.split("\\s*,\\s*");
  }
  
  public String[] splitParamSlash(String data) {
    return data.split("\\s*/\\s*");
  }
  
  public boolean validate() throws Exception {
    return true;
  }
  
  protected void loadItemData(String item, String data) {}
  
  public void onPostReload() {}
  
  public boolean canReloadItem(String item) {
    return false;
  }
  
  public void parse(List<String> lines, String fileExtension, boolean reload) throws Exception {
    if ("txt".equals(fileExtension)) {
      int line = 0;
      try {
        for (String str : lines) {
          line++;
          str = str.trim();
          int eqIdx = str.indexOf('=');
          if (eqIdx >= 0 && str.length() > eqIdx + 1)
            loadItemData(str.substring(0, eqIdx).trim().toLowerCase(), str
                .substring(eqIdx + 1).trim()); 
        } 
      } catch (Exception e) {
        throw new ContentParseException(e, line);
      } 
    } 
  }
  
  public AddonResourceLocation getLoation() {
    return this.location;
  }
  
  public String getContentPath() {
    return this.filePath;
  }
}
