/*     */ package mcheli.plane;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import mcheli.MCH_Config;
/*     */ import mcheli.MCH_MOD;
/*     */ import mcheli.__helper.addon.AddonResourceLocation;
/*     */ import mcheli.aircraft.MCH_AircraftInfo;
/*     */ import net.minecraft.item.Item;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MCP_PlaneInfo
/*     */   extends MCH_AircraftInfo
/*     */ {
/*     */   public MCP_ItemPlane item;
/*     */   public List<MCH_AircraftInfo.DrawnPart> nozzles;
/*     */   public List<Rotor> rotorList;
/*     */   public List<Wing> wingList;
/*     */   public boolean isEnableVtol;
/*     */   public boolean isDefaultVtol;
/*     */   public float vtolYaw;
/*     */   public float vtolPitch;
/*     */   public boolean isEnableAutoPilot;
/*     */   public boolean isVariableSweepWing;
/*     */   public float sweepWingSpeed;
/*     */   
/*     */   public Item getItem() {
/*  35 */     return (Item)this.item;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MCP_PlaneInfo(AddonResourceLocation location, String path) {
/*  42 */     super(location, path);
/*  43 */     this.item = null;
/*  44 */     this.nozzles = new ArrayList<>();
/*  45 */     this.rotorList = new ArrayList<>();
/*  46 */     this.wingList = new ArrayList<>();
/*  47 */     this.isEnableVtol = false;
/*  48 */     this.vtolYaw = 0.3F;
/*  49 */     this.vtolPitch = 0.2F;
/*  50 */     this.isEnableAutoPilot = false;
/*  51 */     this.isVariableSweepWing = false;
/*  52 */     this.sweepWingSpeed = this.speed;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float getDefaultRotorSpeed() {
/*  58 */     return 47.94F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean haveNozzle() {
/*  68 */     return (this.nozzles.size() > 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean haveRotor() {
/*  73 */     return (this.rotorList.size() > 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean haveWing() {
/*  78 */     return (this.wingList.size() > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float getMaxSpeed() {
/*  84 */     return 1.8F;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDefaultMaxZoom() {
/*  90 */     return 8;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDefaultHudName(int seatId) {
/*  96 */     if (seatId <= 0) {
/*  97 */       return "plane";
/*     */     }
/*  99 */     if (seatId == 1) {
/* 100 */       return "plane";
/*     */     }
/* 102 */     return "gunner";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean validate() throws Exception {
/* 109 */     if (haveHatch() && haveWing()) {
/*     */       
/* 111 */       this.wingList.clear();
/* 112 */       this.hatchList.clear();
/*     */     } 
/*     */     
/* 115 */     this.speed = (float)(this.speed * MCH_Config.AllPlaneSpeed.prmDouble);
/* 116 */     this.sweepWingSpeed = (float)(this.sweepWingSpeed * MCH_Config.AllPlaneSpeed.prmDouble);
/*     */ 
/*     */     
/* 119 */     return super.validate();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void loadItemData(String item, String data) {
/* 125 */     super.loadItemData(item, data);
/*     */     
/* 127 */     if (item.compareTo("addpartrotor") == 0) {
/*     */       
/* 129 */       String[] s = data.split("\\s*,\\s*");
/* 130 */       if (s.length >= 6)
/*     */       {
/* 132 */         float m = (s.length >= 7) ? (toFloat(s[6], -180.0F, 180.0F) / 90.0F) : 1.0F;
/*     */         
/* 134 */         Rotor e = new Rotor(this, toFloat(s[0]), toFloat(s[1]), toFloat(s[2]), toFloat(s[3]), toFloat(s[4]), toFloat(s[5]), m, "rotor" + this.rotorList.size());
/*     */         
/* 136 */         this.rotorList.add(e);
/*     */       }
/*     */     
/* 139 */     } else if (item.compareTo("addblade") == 0) {
/*     */       
/* 141 */       int idx = this.rotorList.size() - 1;
/* 142 */       Rotor r = (this.rotorList.size() > 0) ? this.rotorList.get(idx) : null;
/*     */       
/* 144 */       if (r != null) {
/*     */         
/* 146 */         String[] s = data.split("\\s*,\\s*");
/*     */         
/* 148 */         if (s.length == 8)
/*     */         {
/*     */           
/* 151 */           Blade b = new Blade(this, toInt(s[0]), toInt(s[1]), toFloat(s[2]), toFloat(s[3]), toFloat(s[4]), toFloat(s[5]), toFloat(s[6]), toFloat(s[7]), "blade" + idx);
/*     */           
/* 153 */           r.blades.add(b);
/*     */         }
/*     */       
/*     */       } 
/* 157 */     } else if (item.compareTo("addpartwing") == 0) {
/*     */       
/* 159 */       String[] s = data.split("\\s*,\\s*");
/*     */       
/* 161 */       if (s.length == 7)
/*     */       {
/*     */         
/* 164 */         Wing n = new Wing(this, toFloat(s[0]), toFloat(s[1]), toFloat(s[2]), toFloat(s[3]), toFloat(s[4]), toFloat(s[5]), toFloat(s[6]), "wing" + this.wingList.size());
/*     */         
/* 166 */         this.wingList.add(n);
/*     */       }
/*     */     
/* 169 */     } else if (item.equalsIgnoreCase("AddPartPylon")) {
/*     */       
/* 171 */       String[] s = data.split("\\s*,\\s*");
/*     */       
/* 173 */       if (s.length >= 7 && this.wingList.size() > 0)
/*     */       {
/* 175 */         Wing w = this.wingList.get(this.wingList.size() - 1);
/*     */         
/* 177 */         if (w.pylonList == null) {
/* 178 */           w.pylonList = new ArrayList<>();
/*     */         }
/*     */ 
/*     */         
/* 182 */         Pylon n = new Pylon(this, toFloat(s[0]), toFloat(s[1]), toFloat(s[2]), toFloat(s[3]), toFloat(s[4]), toFloat(s[5]), toFloat(s[6]), w.modelName + "_pylon" + w.pylonList.size());
/*     */         
/* 184 */         w.pylonList.add(n);
/*     */       }
/*     */     
/* 187 */     } else if (item.compareTo("addpartnozzle") == 0) {
/*     */       
/* 189 */       String[] s = data.split("\\s*,\\s*");
/*     */       
/* 191 */       if (s.length == 6)
/*     */       {
/*     */         
/* 194 */         MCH_AircraftInfo.DrawnPart n = new MCH_AircraftInfo.DrawnPart(this, this, toFloat(s[0]), toFloat(s[1]), toFloat(s[2]), toFloat(s[3]), toFloat(s[4]), toFloat(s[5]), "nozzle" + this.nozzles.size());
/*     */         
/* 196 */         this.nozzles.add(n);
/*     */       }
/*     */     
/* 199 */     } else if (item.compareTo("variablesweepwing") == 0) {
/*     */       
/* 201 */       this.isVariableSweepWing = toBool(data);
/*     */     }
/* 203 */     else if (item.compareTo("sweepwingspeed") == 0) {
/*     */       
/* 205 */       this.sweepWingSpeed = toFloat(data, 0.0F, 5.0F);
/*     */     }
/* 207 */     else if (item.compareTo("enablevtol") == 0) {
/*     */       
/* 209 */       this.isEnableVtol = toBool(data);
/*     */     }
/* 211 */     else if (item.compareTo("defaultvtol") == 0) {
/*     */       
/* 213 */       this.isDefaultVtol = toBool(data);
/*     */     }
/* 215 */     else if (item.compareTo("vtolyaw") == 0) {
/*     */       
/* 217 */       this.vtolYaw = toFloat(data, 0.0F, 1.0F);
/*     */     }
/* 219 */     else if (item.compareTo("vtolpitch") == 0) {
/*     */       
/* 221 */       this.vtolPitch = toFloat(data, 0.01F, 1.0F);
/*     */     }
/* 223 */     else if (item.compareTo("enableautopilot") == 0) {
/*     */       
/* 225 */       this.isEnableAutoPilot = toBool(data);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDirectoryName() {
/* 232 */     return "planes";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getKindName() {
/* 238 */     return "plane";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onPostReload() {
/* 250 */     MCH_MOD.proxy.registerModelsPlane(this, true);
/*     */   }
/*     */ 
/*     */   
/*     */   public class Blade
/*     */     extends MCH_AircraftInfo.DrawnPart
/*     */   {
/*     */     public final int numBlade;
/*     */     public final int rotBlade;
/*     */     
/*     */     public Blade(MCP_PlaneInfo paramMCP_PlaneInfo, int num, int r, float px, float py, float pz, float rx, float ry, float rz, String name) {
/* 261 */       super(MCP_PlaneInfo.this, paramMCP_PlaneInfo, px, py, pz, rx, ry, rz, name);
/* 262 */       this.numBlade = num;
/* 263 */       this.rotBlade = r;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public class Pylon
/*     */     extends MCH_AircraftInfo.DrawnPart
/*     */   {
/*     */     public final float maxRotFactor;
/*     */     public final float maxRot;
/*     */     
/*     */     public Pylon(MCP_PlaneInfo paramMCP_PlaneInfo, float px, float py, float pz, float rx, float ry, float rz, float mr, String name) {
/* 275 */       super(MCP_PlaneInfo.this, paramMCP_PlaneInfo, px, py, pz, rx, ry, rz, name);
/* 276 */       this.maxRot = mr;
/* 277 */       this.maxRotFactor = this.maxRot / 90.0F;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public class Rotor
/*     */     extends MCH_AircraftInfo.DrawnPart
/*     */   {
/*     */     public List<MCP_PlaneInfo.Blade> blades;
/*     */     public final float maxRotFactor;
/*     */     
/*     */     public Rotor(MCP_PlaneInfo paramMCP_PlaneInfo, float x, float y, float z, float rx, float ry, float rz, float mrf, String model) {
/* 289 */       super(MCP_PlaneInfo.this, paramMCP_PlaneInfo, x, y, z, rx, ry, rz, model);
/* 290 */       this.blades = new ArrayList<>();
/* 291 */       this.maxRotFactor = mrf;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public class Wing
/*     */     extends MCH_AircraftInfo.DrawnPart
/*     */   {
/*     */     public final float maxRotFactor;
/*     */     public final float maxRot;
/*     */     public List<MCP_PlaneInfo.Pylon> pylonList;
/*     */     
/*     */     public Wing(MCP_PlaneInfo paramMCP_PlaneInfo, float px, float py, float pz, float rx, float ry, float rz, float mr, String name) {
/* 304 */       super(MCP_PlaneInfo.this, paramMCP_PlaneInfo, px, py, pz, rx, ry, rz, name);
/* 305 */       this.maxRot = mr;
/* 306 */       this.maxRotFactor = this.maxRot / 90.0F;
/* 307 */       this.pylonList = null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\plane\MCP_PlaneInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */