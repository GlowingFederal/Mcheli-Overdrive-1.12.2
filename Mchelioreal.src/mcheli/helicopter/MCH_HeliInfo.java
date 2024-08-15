/*     */ package mcheli.helicopter;
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
/*     */ 
/*     */ public class MCH_HeliInfo
/*     */   extends MCH_AircraftInfo
/*     */ {
/*     */   public MCH_ItemHeli item;
/*     */   public boolean isEnableFoldBlade;
/*     */   public List<Rotor> rotorList;
/*     */   
/*     */   public MCH_HeliInfo(AddonResourceLocation location, String path) {
/*  28 */     super(location, path);
/*  29 */     this.item = null;
/*  30 */     this.isEnableGunnerMode = false;
/*  31 */     this.isEnableFoldBlade = false;
/*  32 */     this.rotorList = new ArrayList<>();
/*  33 */     this.minRotationPitch = -20.0F;
/*  34 */     this.maxRotationPitch = 20.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean validate() throws Exception {
/*  41 */     this.speed = (float)(this.speed * MCH_Config.AllHeliSpeed.prmDouble);
/*     */     
/*  43 */     return super.validate();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float getDefaultSoundRange() {
/*  49 */     return 80.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float getDefaultRotorSpeed() {
/*  55 */     return 79.99F;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDefaultMaxZoom() {
/*  61 */     return 8;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItem() {
/*  67 */     return (Item)this.item;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDefaultHudName(int seatId) {
/*  73 */     if (seatId <= 0)
/*  74 */       return "heli"; 
/*  75 */     if (seatId == 1)
/*  76 */       return "heli_gnr"; 
/*  77 */     return "gunner";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void loadItemData(String item, String data) {
/*  83 */     super.loadItemData(item, data);
/*     */     
/*  85 */     if (item.compareTo("enablefoldblade") == 0) {
/*     */       
/*  87 */       this.isEnableFoldBlade = toBool(data);
/*     */     }
/*  89 */     else if (item.compareTo("addrotor") == 0 || item.compareTo("addrotorold") == 0) {
/*     */       
/*  91 */       String[] s = data.split("\\s*,\\s*");
/*  92 */       if (s.length == 8 || s.length == 9) {
/*     */         
/*  94 */         boolean cfb = (s.length == 9 && toBool(s[8]));
/*     */ 
/*     */         
/*  97 */         Rotor e = new Rotor(this, toInt(s[0]), toInt(s[1]), toFloat(s[2]), toFloat(s[3]), toFloat(s[4]), toFloat(s[5]), toFloat(s[6]), toFloat(s[7]), "blade" + this.rotorList.size(), cfb, (item.compareTo("addrotorold") == 0));
/*     */         
/*  99 */         this.rotorList.add(e);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDirectoryName() {
/* 107 */     return "helicopters";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getKindName() {
/* 113 */     return "helicopter";
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
/* 125 */     MCH_MOD.proxy.registerModelsHeli(this, true);
/*     */   }
/*     */ 
/*     */   
/*     */   public class Rotor
/*     */     extends MCH_AircraftInfo.DrawnPart
/*     */   {
/*     */     public final int bladeNum;
/*     */     public final int bladeRot;
/*     */     public final boolean haveFoldFunc;
/*     */     public final boolean oldRenderMethod;
/*     */     
/*     */     public Rotor(MCH_HeliInfo paramMCH_HeliInfo, int b, int br, float x, float y, float z, float rx, float ry, float rz, String model, boolean hf, boolean old) {
/* 138 */       super(MCH_HeliInfo.this, paramMCH_HeliInfo, x, y, z, rx, ry, rz, model);
/* 139 */       this.bladeNum = b;
/* 140 */       this.bladeRot = br;
/* 141 */       this.haveFoldFunc = hf;
/* 142 */       this.oldRenderMethod = old;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\helicopter\MCH_HeliInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */