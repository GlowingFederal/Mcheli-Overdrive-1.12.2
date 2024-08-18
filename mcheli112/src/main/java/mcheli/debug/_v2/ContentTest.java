package mcheli.debug._v2;

import com.google.common.collect.Lists;
import java.io.File;
import java.util.List;
import mcheli.__helper.debug.DebugBootstrap;
import mcheli.__helper.info.ContentRegistries;
import mcheli.__helper.info.IContentData;

public class ContentTest {
  public static void main(String[] args) {
    DebugBootstrap.init();
    File debugDir = new File("./run/");
    List<IContentData> contents = Lists.newLinkedList();
    System.out.println(debugDir.getAbsolutePath());
    ContentRegistries.loadContents(debugDir);
  }
}
