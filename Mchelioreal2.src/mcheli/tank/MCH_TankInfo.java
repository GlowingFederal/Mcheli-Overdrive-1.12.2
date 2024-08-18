package mcheli.tank;

import java.util.ArrayList;
import java.util.List;
import mcheli.MCH_Config;
import mcheli.MCH_MOD;
import mcheli.__helper.addon.AddonResourceLocation;
import mcheli.aircraft.MCH_AircraftInfo;
import net.minecraft.item.Item;
import net.minecraft.util.math.Vec3d;

public class MCH_TankInfo extends MCH_AircraftInfo {
  public MCH_ItemTank item;
  
  public int weightType;
  
  public float weightedCenterZ;
  
  public Item getItem() {
    return (Item)this.item;
  }
  
  public MCH_TankInfo(AddonResourceLocation location, String path) {
    super(location, path);
    this.item = null;
    this.weightType = 0;
    this.weightedCenterZ = 0.0F;
  }
  
  public List<MCH_AircraftInfo.Wheel> getDefaultWheelList() {
    List<MCH_AircraftInfo.Wheel> list = new ArrayList<>();
    list.add(new MCH_AircraftInfo.Wheel(this, this, new Vec3d(1.5D, -0.24D, 2.0D)));
    list.add(new MCH_AircraftInfo.Wheel(this, this, new Vec3d(1.5D, -0.24D, -2.0D)));
    return list;
  }
  
  public float getDefaultSoundRange() {
    return 50.0F;
  }
  
  public float getDefaultRotorSpeed() {
    return 47.94F;
  }
  
  private float getDefaultStepHeight() {
    return 0.6F;
  }
  
  public float getMaxSpeed() {
    return 1.8F;
  }
  
  public int getDefaultMaxZoom() {
    return 8;
  }
  
  public String getDefaultHudName(int seatId) {
    if (seatId <= 0)
      return "tank"; 
    if (seatId == 1)
      return "tank"; 
    return "gunner";
  }
  
  public boolean validate() throws Exception {
    this.speed = (float)(this.speed * MCH_Config.AllTankSpeed.prmDouble);
    return super.validate();
  }
  
  public void loadItemData(String item, String data) {
    super.loadItemData(item, data);
    if (item.equalsIgnoreCase("WeightType")) {
      data = data.toLowerCase();
      this.weightType = data.equals("car") ? 1 : (data.equals("tank") ? 2 : 0);
    } else if (item.equalsIgnoreCase("WeightedCenterZ")) {
      this.weightedCenterZ = toFloat(data, -1000.0F, 1000.0F);
    } 
  }
  
  public String getDirectoryName() {
    return "tanks";
  }
  
  public String getKindName() {
    return "tank";
  }
  
  public void onPostReload() {
    MCH_MOD.proxy.registerModelsTank(this, true);
  }
}
