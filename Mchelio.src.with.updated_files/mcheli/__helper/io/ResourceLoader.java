package mcheli.__helper.io;

import com.google.common.base.CharMatcher;
import com.google.common.collect.Lists;
import java.io.BufferedInputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import javax.annotation.Nullable;
import org.jline.utils.OSUtils;

public abstract class ResourceLoader implements Closeable {
  protected final File dir;
  
  ResourceLoader(File file) {
    this.dir = file;
  }
  
  public List<ResourceEntry> loadAll() throws IOException {
    return loadAll(null);
  }
  
  public abstract List<ResourceEntry> loadAll(@Nullable Predicate<? super ResourceEntry> paramPredicate) throws IOException;
  
  public Optional<ResourceEntry> loadFirst() throws IOException {
    return loadAll(null).stream().findFirst();
  }
  
  public abstract ResourceEntry load(String paramString) throws IOException, FileNotFoundException;
  
  public abstract InputStream getInputStreamFromEntry(ResourceEntry paramResourceEntry) throws IOException;
  
  public InputStream getInputStream(String relativePath) throws IOException {
    return getInputStreamFromEntry(load(relativePath));
  }
  
  protected void finalize() throws Throwable {
    close();
    super.finalize();
  }
  
  public static ResourceLoader create(File file) {
    if (file.isDirectory())
      return new DirectoryLoader(file); 
    return new ZipJarFileLoader(file);
  }
  
  public static class ResourceEntry {
    private String path;
    
    private boolean isDirectory;
    
    public ResourceEntry(String path, boolean isDirectory) {
      this.path = path;
      this.isDirectory = isDirectory;
    }
    
    public boolean isDirectory() {
      return this.isDirectory;
    }
    
    public String getPath() {
      return this.path;
    }
  }
  
  static class ZipJarFileLoader extends ResourceLoader {
    private ZipFile resourcePackZipFile;
    
    ZipJarFileLoader(File file) {
      super(file);
    }
    
    public List<ResourceLoader.ResourceEntry> loadAll(@Nullable Predicate<? super ResourceLoader.ResourceEntry> filePathFilter) throws IOException {
      ZipFile zipfile = getResourcePackZipFile();
      filePathFilter = (filePathFilter == null) ? (path -> true) : filePathFilter;
      List<ResourceLoader.ResourceEntry> list = (List<ResourceLoader.ResourceEntry>)zipfile.stream().<ResourceLoader.ResourceEntry>map(enrty -> new ResourceLoader.ResourceEntry(enrty.getName(), enrty.isDirectory())).filter(filePathFilter).collect(Collectors.toList());
      return list;
    }
    
    public ResourceLoader.ResourceEntry load(String relativePath) throws IOException, FileNotFoundException {
      ZipFile zipfile = getResourcePackZipFile();
      ZipEntry zipEntry = zipfile.getEntry(relativePath);
      if (zipEntry != null)
        return new ResourceLoader.ResourceEntry(zipEntry.getName(), zipEntry.isDirectory()); 
      throw new FileNotFoundException(relativePath);
    }
    
    public InputStream getInputStreamFromEntry(ResourceLoader.ResourceEntry resource) throws IOException {
      ZipFile zipfile = getResourcePackZipFile();
      ZipEntry zipentry = zipfile.getEntry(resource.getPath());
      if (zipentry == null)
        throw new FileNotFoundException(
            String.format("'%s' in ResourcePack '%s'", new Object[] { this.dir, resource.getPath() })); 
      return zipfile.getInputStream(zipentry);
    }
    
    private ZipFile getResourcePackZipFile() throws IOException {
      if (this.resourcePackZipFile == null)
        this.resourcePackZipFile = new ZipFile(this.dir); 
      return this.resourcePackZipFile;
    }
    
    public void close() throws IOException {
      if (this.resourcePackZipFile != null) {
        this.resourcePackZipFile.close();
        this.resourcePackZipFile = null;
      } 
    }
  }
  
  static class DirectoryLoader extends ResourceLoader {
    private static final boolean ON_WINDOWS = OSUtils.IS_WINDOWS;
    
    private static final CharMatcher BACKSLASH_MATCHER = CharMatcher.is('\\');
    
    DirectoryLoader(File file) {
      super(file);
    }
    
    public List<ResourceLoader.ResourceEntry> loadAll(@Nullable Predicate<? super ResourceLoader.ResourceEntry> filePathFilter) throws IOException {
      List<ResourceLoader.ResourceEntry> list = Lists.newLinkedList();
      filePathFilter = (filePathFilter == null) ? (path -> true) : filePathFilter;
      loadFiles(this.dir, list, filePathFilter);
      return list;
    }
    
    private void loadFiles(File dir, List<ResourceLoader.ResourceEntry> list, Predicate<? super ResourceLoader.ResourceEntry> filePathFilter) throws IOException {
      if (dir.exists())
        if (dir.isDirectory()) {
          for (File file : dir.listFiles())
            loadFiles(file, list, filePathFilter); 
        } else {
          Path file = dir.toPath();
          Path root = this.dir.toPath();
          String s = root.relativize(file).toString();
          if (ON_WINDOWS)
            s = BACKSLASH_MATCHER.replaceFrom(s, '/'); 
          ResourceLoader.ResourceEntry resourceFile = new ResourceLoader.ResourceEntry(s, dir.isDirectory());
          if (filePathFilter.test(resourceFile))
            list.add(resourceFile); 
        }  
    }
    
    public ResourceLoader.ResourceEntry load(String relativePath) throws IOException, FileNotFoundException {
      File file = getFile(relativePath);
      if (file != null && file.exists())
        return new ResourceLoader.ResourceEntry(relativePath, file.isDirectory()); 
      throw new FileNotFoundException(relativePath);
    }
    
    public InputStream getInputStreamFromEntry(ResourceLoader.ResourceEntry resource) throws IOException {
      File file1 = getFile(resource.getPath());
      if (file1 == null)
        throw new FileNotFoundException(
            String.format("'%s' in ResourcePack '%s'", new Object[] { this.dir, resource.getPath() })); 
      return new BufferedInputStream(new FileInputStream(file1));
    }
    
    @Nullable
    private File getFile(String filepath) {
      try {
        File file1 = new File(this.dir, filepath);
        if (file1.isFile() && validatePath(file1, filepath))
          return file1; 
      } catch (IOException iOException) {}
      return null;
    }
    
    private boolean validatePath(File file, String filepath) throws IOException {
      String s = file.getCanonicalPath();
      if (ON_WINDOWS)
        s = BACKSLASH_MATCHER.replaceFrom(s, '/'); 
      return s.endsWith(filepath);
    }
    
    public void close() throws IOException {}
  }
}
