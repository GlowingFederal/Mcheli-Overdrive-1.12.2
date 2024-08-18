package mcheli.__helper.info;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.function.BiFunction;
import javax.annotation.Nullable;
import mcheli.__helper.MCH_Utils;
import mcheli.__helper.addon.AddonResourceLocation;

public class ContentFactories {
  private static final Map<String, IContentFactory> TABLE = Maps.newHashMap();
  
  @Nullable
  public static IContentFactory getFactory(@Nullable String dirName) {
    return (dirName == null) ? null : TABLE.get(dirName);
  }
  
  private static IContentFactory createFactory(final ContentType type, final BiFunction<AddonResourceLocation, String, IContentData> function) {
    return new IContentFactory() {
        public IContentData create(AddonResourceLocation location, String filepath) {
          return function.apply(location, filepath);
        }
        
        public ContentType getType() {
          return type;
        }
      };
  }
  
  static {
    TABLE.put("helicopters", createFactory(ContentType.HELICOPTER, mcheli.helicopter.MCH_HeliInfo::new));
    TABLE.put("planes", createFactory(ContentType.PLANE, mcheli.plane.MCP_PlaneInfo::new));
    TABLE.put("tanks", createFactory(ContentType.TANK, mcheli.tank.MCH_TankInfo::new));
    TABLE.put("vehicles", createFactory(ContentType.VEHICLE, mcheli.vehicle.MCH_VehicleInfo::new));
    TABLE.put("throwable", createFactory(ContentType.THROWABLE, mcheli.throwable.MCH_ThrowableInfo::new));
    TABLE.put("weapons", createFactory(ContentType.WEAPON, mcheli.weapon.MCH_WeaponInfo::new));
    if (MCH_Utils.isClient())
      TABLE.put("hud", createFactory(ContentType.HUD, mcheli.hud.MCH_Hud::new)); 
  }
}
