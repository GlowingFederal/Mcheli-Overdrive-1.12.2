/*     */ package mcheli.tank;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import mcheli.MCH_Config;
/*     */ import mcheli.MCH_MOD;
/*     */ import mcheli.__helper.addon.AddonResourceLocation;
/*     */ import mcheli.aircraft.MCH_AircraftInfo;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MCH_TankInfo
/*     */   extends MCH_AircraftInfo
/*     */ {
/*     */   public MCH_ItemTank item;
/*     */   public int weightType;
/*     */   public float weightedCenterZ;
/*     */   
/*     */   public Item getItem() {
/*  28 */     return (Item)this.item;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MCH_TankInfo(AddonResourceLocation location, String path) {
/*  35 */     super(location, path);
/*  36 */     this.item = null;
/*  37 */     this.weightType = 0;
/*  38 */     this.weightedCenterZ = 0.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<MCH_AircraftInfo.Wheel> getDefaultWheelList() {
/*  44 */     List<MCH_AircraftInfo.Wheel> list = new ArrayList<>();
/*  45 */     list.add(new MCH_AircraftInfo.Wheel(this, this, new Vec3d(1.5D, -0.24D, 2.0D)));
/*  46 */     list.add(new MCH_AircraftInfo.Wheel(this, this, new Vec3d(1.5D, -0.24D, -2.0D)));
/*  47 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float getDefaultSoundRange() {
/*  53 */     return 50.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float getDefaultRotorSpeed() {
/*  59 */     return 47.94F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private float getDefaultStepHeight() {
/*  66 */     return 0.6F;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float getMaxSpeed() {
/*  72 */     return 1.8F;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDefaultMaxZoom() {
/*  78 */     return 8;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDefaultHudName(int seatId) {
/*  84 */     if (seatId <= 0)
/*  85 */       return "tank"; 
/*  86 */     if (seatId == 1)
/*  87 */       return "tank"; 
/*  88 */     return "gunner";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean validate() throws Exception {
/*  95 */     this.speed = (float)(this.speed * MCH_Config.AllTankSpeed.prmDouble);
/*     */ 
/*     */     
/*  98 */     return super.validate();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void loadItemData(String item, String data) {
/* 104 */     super.loadItemData(item, data);
/*     */     
/* 106 */     if (item.equalsIgnoreCase("WeightType")) {
/*     */       
/* 108 */       data = data.toLowerCase();
/* 109 */       this.weightType = data.equals("car") ? 1 : (data.equals("tank") ? 2 : 0);
/*     */     }
/* 111 */     else if (item.equalsIgnoreCase("WeightedCenterZ")) {
/*     */       
/* 113 */       this.weightedCenterZ = toFloat(data, -1000.0F, 1000.0F);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDirectoryName() {
/* 120 */     return "tanks";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getKindName() {
/* 126 */     return "tank";
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
/* 138 */     MCH_MOD.proxy.registerModelsTank(this, true);
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\tank\MCH_TankInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */