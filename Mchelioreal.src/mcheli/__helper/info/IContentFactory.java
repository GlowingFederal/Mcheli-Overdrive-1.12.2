package mcheli.__helper.info;

import javax.annotation.Nullable;
import mcheli.__helper.addon.AddonResourceLocation;

public interface IContentFactory {
  @Nullable
  IContentData create(AddonResourceLocation paramAddonResourceLocation, String paramString);
  
  ContentType getType();
}


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\__helper\info\IContentFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */