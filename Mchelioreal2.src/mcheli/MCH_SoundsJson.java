package mcheli;

import com.google.common.base.Joiner;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimaps;
import java.io.File;
import java.util.List;
import java.util.stream.Collectors;
import mcheli.__helper.MCH_Utils;
import mcheli.__helper.addon.GeneratedAddonPack;

public class MCH_SoundsJson {
  public static void updateGenerated() {
    File soundsDir = new File(MCH_MOD.getSource().getPath() + "/assets/mcheli/sounds");
    File[] soundFiles = soundsDir.listFiles(f -> {
          String s = f.getName().toLowerCase();
          return (f.isFile() && s.length() >= 5 && s.substring(s.length() - 4).compareTo(".ogg") == 0);
        });
    ListMultimap listMultimap = Multimaps.newListMultimap(Maps.newLinkedHashMap(), Lists::newLinkedList);
    List<String> lines = Lists.newLinkedList();
    int cnt = 0;
    if (soundFiles != null) {
      for (File f : soundFiles) {
        String name = f.getName().toLowerCase();
        int ei = name.lastIndexOf(".");
        name = name.substring(0, ei);
        String key = name;
        char c = key.charAt(key.length() - 1);
        if (c >= '0' && c <= '9')
          key = key.substring(0, key.length() - 1); 
        listMultimap.put(key, name);
      } 
      lines.add("{");
      for (String key : listMultimap.keySet()) {
        cnt++;
        String sounds = Joiner.on(",").join((Iterable)listMultimap.get(key).stream()
            .map(name -> '"' + MCH_Utils.suffix(name).toString() + '"').collect(Collectors.toList()));
        String line = "\"" + key + "\": {\"category\": \"master\",\"sounds\": [" + sounds + "]}";
        if (cnt < listMultimap.keySet().size())
          line = line + ","; 
        lines.add(line);
      } 
      lines.add("}");
      lines.add("");
    } 
    GeneratedAddonPack.instance().updateAssetFile("sounds.json", lines);
    MCH_Utils.logger().info("Update sounds.json, %d sounds.", Integer.valueOf(cnt));
  }
}
