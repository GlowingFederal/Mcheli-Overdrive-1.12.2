package mcheli.__helper.info;

import java.util.List;
import mcheli.__helper.addon.AddonResourceLocation;

public interface IContentData {
  void parse(List<String> paramList, String paramString, boolean paramBoolean) throws Exception;
  
  boolean validate() throws Exception;
  
  void onPostReload();
  
  AddonResourceLocation getLoation();
  
  String getContentPath();
}
