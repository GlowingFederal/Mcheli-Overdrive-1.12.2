package mcheli.__helper.info;

import javax.annotation.Nullable;
import mcheli.__helper.addon.AddonResourceLocation;

public interface IContentFactory {
  @Nullable
  IContentData create(AddonResourceLocation paramAddonResourceLocation, String paramString);
  
  ContentType getType();
}
