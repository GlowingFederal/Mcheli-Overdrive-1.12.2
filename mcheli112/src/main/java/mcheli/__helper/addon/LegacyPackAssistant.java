package mcheli.__helper.addon;

import java.io.File;
import java.util.Arrays;
import mcheli.MCH_MOD;
import mcheli.MCH_OutputFile;

public class LegacyPackAssistant {
  private static File templeteAddonDir = null;
  
  public static void generateDirectoryPack() {
    if (templeteAddonDir == null)
      templeteAddonDir = new File(MCH_MOD.getAddonDir(), "/templete-addon/"); 
    Arrays.<String>stream(new String[] { "helicopters", "planes", "tanks", "vehicles", "weapons", "throwable", "hud" }).forEach(name -> {
          File dir = new File(templeteAddonDir, "/assets/mcheli/" + name + "/");
          if (!dir.exists())
            dir.mkdirs(); 
        });
    Arrays.<String>stream(new String[] { "helicopters", "planes", "tanks", "vehicles", "bullets", "throwable" }).forEach(name -> {
          File modelsDir = new File(templeteAddonDir, "/assets/mcheli/models/" + name + "/");
          if (!modelsDir.exists())
            modelsDir.mkdirs(); 
          File texturesDir = new File(templeteAddonDir, "/assets/mcheli/textures/" + name + "/");
          if (!texturesDir.exists())
            texturesDir.mkdirs(); 
        });
    MCH_OutputFile file = new MCH_OutputFile();
    if (file.openUTF8(templeteAddonDir.getPath() + "/pack.meta")) {
      String[] lines = { 
          "{", "  \"pack\": {", "    \"description\": \"Template addon\"", "  },", "  \"addon\": {", "    \"domain\": \"template_addon\",", "    \"version\": \"1.0\",", "    \"credits\": \"\",", "    \"description\": \"\",", "    \"loader_version\": \"1\",", 
          "    \"authors\": []", "  }", "}" };
      for (String s : lines)
        file.writeLine(s); 
    } 
    file.close();
  }
}
