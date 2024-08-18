package mcheli.__helper.info;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.io.Files;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import mcheli.MCH_MOD;
import mcheli.__helper.MCH_Logger;
import mcheli.__helper.MCH_Utils;
import mcheli.__helper.addon.AddonResourceLocation;

public abstract class ContentLoader {
  protected final String domain;
  
  protected final File dir;
  
  protected final String loaderVersion;
  
  private Predicate<String> fileFilter;
  
  public ContentLoader(String domain, File addonDir, String loaderVersion, Predicate<String> fileFilter) {
    this.domain = domain;
    this.dir = addonDir;
    this.loaderVersion = loaderVersion;
    this.fileFilter = fileFilter;
  }
  
  public boolean isReadable(String path) {
    return this.fileFilter.test(path);
  }
  
  @Nullable
  public IContentFactory getFactory(@Nullable String dirName) {
    return ContentFactories.getFactory(dirName);
  }
  
  public Multimap<ContentType, ContentEntry> load() {
    LinkedHashMultimap linkedHashMultimap = LinkedHashMultimap.create();
    List<ContentEntry> list = getEntries();
    for (ContentEntry entry : list)
      linkedHashMultimap.put(entry.getType(), entry); 
    return (Multimap<ContentType, ContentEntry>)linkedHashMultimap;
  }
  
  protected abstract List<ContentEntry> getEntries();
  
  protected abstract InputStream getInputStreamByName(String paramString) throws IOException;
  
  public <TYPE extends IContentData> List<TYPE> reloadAndParse(Class<TYPE> clazz, List<TYPE> oldContents, IContentFactory factory) {
    List<TYPE> list = Lists.newLinkedList();
    for (IContentData iContentData : oldContents) {
      try {
        ContentEntry entry = makeEntry(iContentData.getContentPath(), factory, true);
        IContentData content = entry.parse();
        if (content != null) {
          content.onPostReload();
        } else {
          content = iContentData;
        } 
        if (clazz.isInstance(content))
          list.add(clazz.cast(content)); 
      } catch (IOException e) {
        MCH_Logger.get().error("IO Error from file loader!", e);
      } 
    } 
    return list;
  }
  
  public IContentData reloadAndParseSingle(IContentData oldData, String dir) {
    IContentData content = oldData;
    try {
      ContentEntry entry = makeEntry(oldData.getContentPath(), getFactory(dir), true);
      content = entry.parse();
      if (content != null) {
        content.onPostReload();
      } else {
        content = oldData;
      } 
    } catch (IOException e) {
      MCH_Logger.get().error("IO Error from file loader!", e);
    } 
    return content;
  }
  
  protected ContentEntry makeEntry(String filepath, @Nullable IContentFactory factory, boolean reload) throws IOException {
    List<String> lines = null;
    try (BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(
            getInputStreamByName(filepath), StandardCharsets.UTF_8))) {
      lines = bufferedreader.lines().collect((Collector)Collectors.toList());
    } 
    return new ContentEntry(filepath, this.domain, factory, lines, reload);
  }
  
  static class ContentEntry {
    private String filepath;
    
    private String domain;
    
    private IContentFactory factory;
    
    private List<String> lines;
    
    private boolean reload;
    
    private ContentEntry(String filepath, String domain, @Nullable IContentFactory factory, List<String> lines, boolean reload) {
      this.filepath = filepath;
      this.domain = domain;
      this.factory = factory;
      this.lines = lines;
      this.reload = reload;
    }
    
    @Nullable
    public IContentData parse() {
      AddonResourceLocation location = MCH_Utils.addon(this.domain, Files.getNameWithoutExtension(this.filepath));
      if (!this.reload)
        MCH_MOD.proxy.onParseStartFile(location); 
      try {
        IContentData content = this.factory.create(location, this.filepath);
        if (content != null) {
          content.parse(this.lines, Files.getFileExtension(this.filepath), this.reload);
          if (!content.validate())
            MCH_Logger.get().debug("Invalid content info: " + this.filepath); 
        } 
        return content;
      } catch (Exception e) {
        String msg = "An error occurred while file loading ";
        if (e instanceof ContentParseException)
          msg = msg + "at line:" + ((ContentParseException)e).getLineNo() + "."; 
        MCH_Logger.get().error(msg + " file:{}, domain:{}", location.func_110623_a(), this.domain, e);
        return null;
      } finally {
        MCH_MOD.proxy.onParseFinishFile(location);
      } 
    }
    
    public ContentType getType() {
      return this.factory.getType();
    }
  }
}
