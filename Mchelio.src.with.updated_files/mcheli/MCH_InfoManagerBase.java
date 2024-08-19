package mcheli;

import java.io.File;
import java.io.FileFilter;
import mcheli.__helper.addon.AddonResourceLocation;

public abstract class MCH_InfoManagerBase<T extends MCH_BaseInfo> {
  public abstract T newInfo(AddonResourceLocation paramAddonResourceLocation, String paramString);
  
  protected void put(String name, T info) {}
  
  protected abstract boolean contains(String paramString);
  
  protected abstract int size();
  
  public boolean load(String path, String type) {
    path = path.replace('\\', '/');
    File dir = new File(path + type);
    File[] files = dir.listFiles(new FileFilter() {
          public boolean accept(File pathname) {
            String s = pathname.getName().toLowerCase();
            return (pathname.isFile() && s.length() >= 5 && s
              .substring(s.length() - 4).compareTo(".txt") == 0);
          }
        });
    if (files == null || files.length <= 0)
      return false; 
    for (File f : files) {
      int line = 0;
      MCH_InputFile inFile = new MCH_InputFile();
    } 
    MCH_Lib.Log("Read %d %s", new Object[] { Integer.valueOf(size()), type });
    return (size() > 0);
  }
}
