package mcheli.helicopter;

import java.util.ArrayList;
import java.util.List;
import mcheli.MCH_Config;
import mcheli.MCH_MOD;
import mcheli.__helper.addon.AddonResourceLocation;
import mcheli.aircraft.MCH_AircraftInfo;
import net.minecraft.item.Item;

public class MCH_HeliInfo extends MCH_AircraftInfo {
  public MCH_ItemHeli item;
  
  public boolean isEnableFoldBlade;
  
  public List<Rotor> rotorList;
  
  public MCH_HeliInfo(AddonResourceLocation location, String path) {
    super(location, path);
    this.item = null;
    this.isEnableGunnerMode = false;
    this.isEnableFoldBlade = false;
    this.rotorList = new ArrayList<>();
    this.minRotationPitch = -20.0F;
    this.maxRotationPitch = 20.0F;
  }
  
  public boolean validate() throws Exception {
    this.speed = (float)(this.speed * MCH_Config.AllHeliSpeed.prmDouble);
    return super.validate();
  }
  
  public float getDefaultSoundRange() {
    return 80.0F;
  }
  
  public float getDefaultRotorSpeed() {
    return 79.99F;
  }
  
  public int getDefaultMaxZoom() {
    return 8;
  }
  
  public Item getItem() {
    return (Item)this.item;
  }
  
  public String getDefaultHudName(int seatId) {
    if (seatId <= 0)
      return "heli"; 
    if (seatId == 1)
      return "heli_gnr"; 
    return "gunner";
  }
  
  public void loadItemData(String item, String data) {
    super.loadItemData(item, data);
    if (item.compareTo("enablefoldblade") == 0) {
      this.isEnableFoldBlade = toBool(data);
    } else if (item.compareTo("addrotor") == 0 || item.compareTo("addrotorold") == 0) {
      String[] s = data.split("\\s*,\\s*");
      if (s.length == 8 || s.length == 9) {
        boolean cfb = (s.length == 9 && toBool(s[8]));
        Rotor e = new Rotor(this, toInt(s[0]), toInt(s[1]), toFloat(s[2]), toFloat(s[3]), toFloat(s[4]), toFloat(s[5]), toFloat(s[6]), toFloat(s[7]), "blade" + this.rotorList.size(), cfb, (item.compareTo("addrotorold") == 0));
        this.rotorList.add(e);
      } 
    } 
  }
  
  public String getDirectoryName() {
    return "helicopters";
  }
  
  public String getKindName() {
    return "helicopter";
  }
  
  public void onPostReload() {
    MCH_MOD.proxy.registerModelsHeli(this, true);
  }
  
  public class Rotor extends MCH_AircraftInfo.DrawnPart {
    public final int bladeNum;
    
    public final int bladeRot;
    
    public final boolean haveFoldFunc;
    
    public final boolean oldRenderMethod;

    public Rotor(MCH_HeliInfo paramMCH_HeliInfo, int b, int br, float x, float y, float z, float rx, float ry, float rz, String model, boolean hf, boolean old) {
      super(MCH_HeliInfo.this, x, y, z, rx, ry, rz, model);
      this.bladeNum = b;
      this.bladeRot = br;
      this.haveFoldFunc = hf;
      this.oldRenderMethod = old;
    }
  }
}
