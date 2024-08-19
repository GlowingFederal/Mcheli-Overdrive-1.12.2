package mcheli.__helper.addon;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import java.io.File;
import java.util.List;
import mcheli.MCH_MOD;
import mcheli.MCH_OutputFile;
import mcheli.__helper.MCH_Utils;

public class GeneratedAddonPack extends AddonPack {
  private static GeneratedAddonPack instance = null;
  
  private static File generatedDir = null;
  
  public static GeneratedAddonPack instance() {
    if (instance == null)
      instance = new GeneratedAddonPack(); 
    return instance;
  }
  
  public static boolean isGeneratedAddonName(File file) {
    return "generated".equals(file.getName());
  }
  
  private GeneratedAddonPack() {
    super("@generated", "Generated", "1.0", null, "EMB4-MCHeli", (List<String>)ImmutableList.of("EMB4", "Murachiki27"), "Generated addon(auto generate or update files)", "1", 
        ImmutableMap.of());
  }
  
  public File getFile() {
    if (generatedDir == null)
      generatedDir = new File(MCH_MOD.getAddonDir(), "/generated/"); 
    return generatedDir;
  }
  
  public boolean updateAssetFile(String targetAssetPath, List<String> lines) {
    File assets = checkExistAssets();
    MCH_OutputFile file = new MCH_OutputFile();
    if (file.openUTF8(assets.getPath() + "/" + targetAssetPath)) {
      for (String s : lines)
        file.writeLine(s); 
      file.close();
      MCH_Utils.logger().info("Update file:" + file.file.getAbsolutePath());
      return true;
    } 
    return false;
  }
  
  public File checkMkdirsAssets(String dir) {
    File assets = new File(getFile(), "/assets/mcheli/" + dir + "/");
    if (!assets.exists())
      assets.mkdirs(); 
    return assets;
  }
  
  private File checkExistAssets() {
    File assets = new File(getFile(), "/assets/mcheli/");
    if (!assets.exists())
      assets.mkdirs(); 
    return assets;
  }
}
