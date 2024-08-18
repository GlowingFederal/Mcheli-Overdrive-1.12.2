package mcheli.__helper.info;

import com.google.common.collect.Lists;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import mcheli.__helper.MCH_Logger;

public class FileContentLoader extends ContentLoader implements Closeable {
  private ZipFile resourcePackZipFile;
  
  public FileContentLoader(String domain, File addonDir, String loaderVersion, Predicate<String> fileFilter) {
    super(domain, addonDir, loaderVersion, fileFilter);
  }
  
  private ZipFile getResourcePackZipFile() throws IOException {
    if (this.resourcePackZipFile == null)
      this.resourcePackZipFile = new ZipFile(this.dir); 
    return this.resourcePackZipFile;
  }
  
  protected List<ContentLoader.ContentEntry> getEntries() {
    return walkEntries("2".equals(this.loaderVersion));
  }
  
  private List<ContentLoader.ContentEntry> walkEntries(boolean findDeep) {
    List<ContentLoader.ContentEntry> list = Lists.newLinkedList();
    try {
      ZipFile zipfile = getResourcePackZipFile();
      Iterator<? extends ZipEntry> itr = zipfile.stream().filter(e -> filter(e, findDeep)).iterator();
      while (itr.hasNext()) {
        String name = ((ZipEntry)itr.next()).getName();
        String[] s = name.split("/");
        String typeDirName = (s.length >= 3) ? s[2] : null;
        IContentFactory factory = getFactory(typeDirName);
        if (factory != null)
          list.add(makeEntry(name, factory, false)); 
      } 
    } catch (IOException e) {
      MCH_Logger.get().error("IO Error from file loader!", e);
    } 
    return list;
  }
  
  private boolean filter(ZipEntry zipEntry, boolean deep) {
    String name = zipEntry.getName();
    String[] split = name.split("/");
    String lootDir = (split.length >= 2) ? split[0] : "";
    if (!zipEntry.isDirectory())
      if (deep || "assets".equals(lootDir) || split.length <= 2)
        return isReadable(name);  
    return false;
  }
  
  protected InputStream getInputStreamByName(String name) throws IOException {
    ZipFile zipfile = getResourcePackZipFile();
    ZipEntry zipentry = zipfile.getEntry(name);
    if (zipentry == null)
      throw new FileNotFoundException(String.format("'%s' in AddonPack '%s'", new Object[] { this.dir, name })); 
    return zipfile.getInputStream(zipentry);
  }
  
  protected void finalize() throws Throwable {
    close();
    super.finalize();
  }
  
  public void close() throws IOException {
    if (this.resourcePackZipFile != null) {
      this.resourcePackZipFile.close();
      this.resourcePackZipFile = null;
    } 
  }
}
