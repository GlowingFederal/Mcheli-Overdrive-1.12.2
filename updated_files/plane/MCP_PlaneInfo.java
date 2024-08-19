package mcheli.plane;

import java.util.ArrayList;
import java.util.List;
import mcheli.MCH_Config;
import mcheli.MCH_MOD;
import mcheli.__helper.addon.AddonResourceLocation;
import mcheli.aircraft.MCH_AircraftInfo;
import net.minecraft.item.Item;

public class MCP_PlaneInfo extends MCH_AircraftInfo {
  public MCP_ItemPlane item;
  
  public List<MCH_AircraftInfo.DrawnPart> nozzles;
  
  public List<Rotor> rotorList;
  
  public List<Wing> wingList;
  
  public boolean isEnableVtol;
  
  public boolean isDefaultVtol;
  
  public float vtolYaw;
  
  public float vtolPitch;
  
  public boolean isEnableAutoPilot;
  
  public boolean isVariableSweepWing;
  
  public float sweepWingSpeed;
  
  public Item getItem() {
    return (Item)this.item;
  }
  
  public MCP_PlaneInfo(AddonResourceLocation location, String path) {
    super(location, path);
    this.item = null;
    this.nozzles = new ArrayList<>();
    this.rotorList = new ArrayList<>();
    this.wingList = new ArrayList<>();
    this.isEnableVtol = false;
    this.vtolYaw = 0.3F;
    this.vtolPitch = 0.2F;
    this.isEnableAutoPilot = false;
    this.isVariableSweepWing = false;
    this.sweepWingSpeed = this.speed;
  }
  
  public float getDefaultRotorSpeed() {
    return 47.94F;
  }
  
  public boolean haveNozzle() {
    return (this.nozzles.size() > 0);
  }
  
  public boolean haveRotor() {
    return (this.rotorList.size() > 0);
  }
  
  public boolean haveWing() {
    return (this.wingList.size() > 0);
  }
  
  public float getMaxSpeed() {
    return 1.8F;
  }
  
  public int getDefaultMaxZoom() {
    return 8;
  }
  
  public String getDefaultHudName(int seatId) {
    if (seatId <= 0)
      return "plane"; 
    if (seatId == 1)
      return "plane"; 
    return "gunner";
  }
  
  public boolean validate() throws Exception {
    if (haveHatch() && haveWing()) {
      this.wingList.clear();
      this.hatchList.clear();
    } 
    this.speed = (float)(this.speed * MCH_Config.AllPlaneSpeed.prmDouble);
    this.sweepWingSpeed = (float)(this.sweepWingSpeed * MCH_Config.AllPlaneSpeed.prmDouble);
    return super.validate();
  }
  
  public void loadItemData(String item, String data) {
    super.loadItemData(item, data);
    if (item.compareTo("addpartrotor") == 0) {
      String[] s = data.split("\\s*,\\s*");
      if (s.length >= 6) {
        float m = (s.length >= 7) ? (toFloat(s[6], -180.0F, 180.0F) / 90.0F) : 1.0F;
        Rotor e = new Rotor(this, toFloat(s[0]), toFloat(s[1]), toFloat(s[2]), toFloat(s[3]), toFloat(s[4]), toFloat(s[5]), m, "rotor" + this.rotorList.size());
        this.rotorList.add(e);
      } 
    } else if (item.compareTo("addblade") == 0) {
      int idx = this.rotorList.size() - 1;
      Rotor r = (this.rotorList.size() > 0) ? this.rotorList.get(idx) : null;
      if (r != null) {
        String[] s = data.split("\\s*,\\s*");
        if (s.length == 8) {
          Blade b = new Blade(this, toInt(s[0]), toInt(s[1]), toFloat(s[2]), toFloat(s[3]), toFloat(s[4]), toFloat(s[5]), toFloat(s[6]), toFloat(s[7]), "blade" + idx);
          r.blades.add(b);
        } 
      } 
    } else if (item.compareTo("addpartwing") == 0) {
      String[] s = data.split("\\s*,\\s*");
      if (s.length == 7) {
        Wing n = new Wing(this, toFloat(s[0]), toFloat(s[1]), toFloat(s[2]), toFloat(s[3]), toFloat(s[4]), toFloat(s[5]), toFloat(s[6]), "wing" + this.wingList.size());
        this.wingList.add(n);
      } 
    } else if (item.equalsIgnoreCase("AddPartPylon")) {
      String[] s = data.split("\\s*,\\s*");
      if (s.length >= 7 && this.wingList.size() > 0) {
        Wing w = this.wingList.get(this.wingList.size() - 1);
        if (w.pylonList == null)
          w.pylonList = new ArrayList<>(); 
        Pylon n = new Pylon(this, toFloat(s[0]), toFloat(s[1]), toFloat(s[2]), toFloat(s[3]), toFloat(s[4]), toFloat(s[5]), toFloat(s[6]), w.modelName + "_pylon" + w.pylonList.size());
        w.pylonList.add(n);
      } 
    } else if (item.compareTo("addpartnozzle") == 0) {
      String[] s = data.split("\\s*,\\s*");
      if (s.length == 6) {
        MCH_AircraftInfo.DrawnPart n = new MCH_AircraftInfo.DrawnPart(this, this, toFloat(s[0]), toFloat(s[1]), toFloat(s[2]), toFloat(s[3]), toFloat(s[4]), toFloat(s[5]), "nozzle" + this.nozzles.size());
        this.nozzles.add(n);
      } 
    } else if (item.compareTo("variablesweepwing") == 0) {
      this.isVariableSweepWing = toBool(data);
    } else if (item.compareTo("sweepwingspeed") == 0) {
      this.sweepWingSpeed = toFloat(data, 0.0F, 5.0F);
    } else if (item.compareTo("enablevtol") == 0) {
      this.isEnableVtol = toBool(data);
    } else if (item.compareTo("defaultvtol") == 0) {
      this.isDefaultVtol = toBool(data);
    } else if (item.compareTo("vtolyaw") == 0) {
      this.vtolYaw = toFloat(data, 0.0F, 1.0F);
    } else if (item.compareTo("vtolpitch") == 0) {
      this.vtolPitch = toFloat(data, 0.01F, 1.0F);
    } else if (item.compareTo("enableautopilot") == 0) {
      this.isEnableAutoPilot = toBool(data);
    } 
  }
  
  public String getDirectoryName() {
    return "planes";
  }
  
  public String getKindName() {
    return "plane";
  }
  
  public void onPostReload() {
    MCH_MOD.proxy.registerModelsPlane(this, true);
  }
  
  public class Blade extends MCH_AircraftInfo.DrawnPart {
    public final int numBlade;
    
    public final int rotBlade;
    
    public Blade(MCP_PlaneInfo paramMCP_PlaneInfo, int num, int r, float px, float py, float pz, float rx, float ry, float rz, String name) {
      super(MCP_PlaneInfo.this, paramMCP_PlaneInfo, px, py, pz, rx, ry, rz, name);
      this.numBlade = num;
      this.rotBlade = r;
    }
  }
  
  public class Pylon extends MCH_AircraftInfo.DrawnPart {
    public final float maxRotFactor;
    
    public final float maxRot;
    
    public Pylon(MCP_PlaneInfo paramMCP_PlaneInfo, float px, float py, float pz, float rx, float ry, float rz, float mr, String name) {
      super(MCP_PlaneInfo.this, paramMCP_PlaneInfo, px, py, pz, rx, ry, rz, name);
      this.maxRot = mr;
      this.maxRotFactor = this.maxRot / 90.0F;
    }
  }
  
  public class Rotor extends MCH_AircraftInfo.DrawnPart {
    public List<MCP_PlaneInfo.Blade> blades;
    
    public final float maxRotFactor;
    
    public Rotor(MCP_PlaneInfo paramMCP_PlaneInfo, float x, float y, float z, float rx, float ry, float rz, float mrf, String model) {
      super(MCP_PlaneInfo.this, paramMCP_PlaneInfo, x, y, z, rx, ry, rz, model);
      this.blades = new ArrayList<>();
      this.maxRotFactor = mrf;
    }
  }
  
  public class Wing extends MCH_AircraftInfo.DrawnPart {
    public final float maxRotFactor;
    
    public final float maxRot;
    
    public List<MCP_PlaneInfo.Pylon> pylonList;
    
    public Wing(MCP_PlaneInfo paramMCP_PlaneInfo, float px, float py, float pz, float rx, float ry, float rz, float mr, String name) {
      super(MCP_PlaneInfo.this, paramMCP_PlaneInfo, px, py, pz, rx, ry, rz, name);
      this.maxRot = mr;
      this.maxRotFactor = this.maxRot / 90.0F;
      this.pylonList = null;
    }
  }
}
