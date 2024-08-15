/*     */ package mcheli.vehicle;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
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
/*     */ public class MCH_VehicleInfo
/*     */   extends MCH_AircraftInfo
/*     */ {
/*     */   public MCH_ItemVehicle item;
/*     */   public boolean isEnableMove;
/*     */   public boolean isEnableRot;
/*     */   public List<VPart> partList;
/*     */   
/*     */   public float getMinRotationPitch() {
/*  27 */     return -90.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float getMaxRotationPitch() {
/*  33 */     return 90.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItem() {
/*  39 */     return (Item)this.item;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MCH_VehicleInfo(AddonResourceLocation location, String path) {
/*  46 */     super(location, path);
/*  47 */     this.item = null;
/*  48 */     this.isEnableMove = false;
/*  49 */     this.isEnableRot = false;
/*  50 */     this.partList = new ArrayList<>();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean validate() throws Exception {
/*  58 */     return super.validate();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDefaultHudName(int seatId) {
/*  64 */     return "vehicle";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void loadItemData(String item, String data) {
/*  70 */     super.loadItemData(item, data);
/*     */     
/*  72 */     if (item.compareTo("canmove") == 0) {
/*     */       
/*  74 */       this.isEnableMove = toBool(data);
/*     */     }
/*  76 */     else if (item.compareTo("canrotation") == 0) {
/*     */       
/*  78 */       this.isEnableRot = toBool(data);
/*     */     }
/*  80 */     else if (item.compareTo("rotationpitchmin") == 0) {
/*     */       
/*  82 */       super.loadItemData("minrotationpitch", data);
/*     */     }
/*  84 */     else if (item.compareTo("rotationpitchmax") == 0) {
/*     */       
/*  86 */       super.loadItemData("maxrotationpitch", data);
/*     */     }
/*  88 */     else if (item.compareTo("addpart") == 0) {
/*     */       
/*  90 */       String[] s = data.split("\\s*,\\s*");
/*  91 */       if (s.length >= 7)
/*     */       {
/*  93 */         float rb = (s.length >= 8) ? toFloat(s[7]) : 0.0F;
/*     */         
/*  95 */         VPart n = new VPart(this, toFloat(s[4]), toFloat(s[5]), toFloat(s[6]), "part" + this.partList.size(), toBool(s[0]), toBool(s[1]), toBool(s[2]), toInt(s[3]), rb);
/*     */         
/*  97 */         this.partList.add(n);
/*     */       }
/*     */     
/* 100 */     } else if (item.compareTo("addchildpart") == 0) {
/*     */       
/* 102 */       if (this.partList.size() > 0) {
/*     */         
/* 104 */         String[] s = data.split("\\s*,\\s*");
/*     */         
/* 106 */         if (s.length >= 7) {
/*     */           
/* 108 */           float rb = (s.length >= 8) ? toFloat(s[7]) : 0.0F;
/* 109 */           VPart p = this.partList.get(this.partList.size() - 1);
/* 110 */           if (p.child == null) {
/* 111 */             p.child = new ArrayList<>();
/*     */           }
/*     */           
/* 114 */           VPart n = new VPart(this, toFloat(s[4]), toFloat(s[5]), toFloat(s[6]), p.modelName + "_" + p.child.size(), toBool(s[0]), toBool(s[1]), toBool(s[2]), toInt(s[3]), rb);
/*     */           
/* 116 */           p.child.add(n);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDirectoryName() {
/* 125 */     return "vehicles";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getKindName() {
/* 131 */     return "vehicle";
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
/* 143 */     MCH_MOD.proxy.registerModelsVehicle(this, true);
/*     */   }
/*     */ 
/*     */   
/*     */   public class VPart
/*     */     extends MCH_AircraftInfo.DrawnPart
/*     */   {
/*     */     public final boolean rotPitch;
/*     */     public final boolean rotYaw;
/*     */     public final int type;
/*     */     public List<VPart> child;
/*     */     public final boolean drawFP;
/*     */     public final float recoilBuf;
/*     */     
/*     */     public VPart(MCH_VehicleInfo paramMCH_VehicleInfo, float x, float y, float z, String model, boolean drawfp, boolean roty, boolean rotp, int type, float rb) {
/* 158 */       super(MCH_VehicleInfo.this, paramMCH_VehicleInfo, x, y, z, 0.0F, 0.0F, 0.0F, model);
/* 159 */       this.rotYaw = roty;
/* 160 */       this.rotPitch = rotp;
/* 161 */       this.type = type;
/* 162 */       this.child = null;
/* 163 */       this.drawFP = drawfp;
/* 164 */       this.recoilBuf = rb;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\vehicle\MCH_VehicleInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */