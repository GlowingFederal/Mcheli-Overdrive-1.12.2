/*     */ package mcheli.throwable;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import mcheli.MCH_BaseInfo;
/*     */ import mcheli.MCH_Color;
/*     */ import mcheli.__helper.addon.AddonResourceLocation;
/*     */ import mcheli.__helper.client._IModelCustom;
/*     */ import mcheli.__helper.info.IItemContent;
/*     */ import mcheli.wrapper.W_Item;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.crafting.IRecipe;
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
/*     */ 
/*     */ public class MCH_ThrowableInfo
/*     */   extends MCH_BaseInfo
/*     */   implements IItemContent
/*     */ {
/*     */   public final String name;
/*     */   public String displayName;
/*     */   public HashMap<String, String> displayNameLang;
/*     */   public int itemID;
/*     */   public W_Item item;
/*     */   public List<String> recipeString;
/*     */   public List<IRecipe> recipe;
/*     */   public boolean isShapedRecipe;
/*     */   public int power;
/*     */   public float acceleration;
/*     */   public float accelerationInWater;
/*     */   public float dispenseAcceleration;
/*     */   public int explosion;
/*     */   public int delayFuse;
/*     */   public float bound;
/*     */   public int timeFuse;
/*     */   public boolean flaming;
/*     */   public int stackSize;
/*     */   public float soundVolume;
/*     */   public float soundPitch;
/*     */   public float proximityFuseDist;
/*     */   public float accuracy;
/*     */   public int aliveTime;
/*     */   public int bomblet;
/*     */   public float bombletDiff;
/*     */   public _IModelCustom model;
/*     */   public float smokeSize;
/*     */   public int smokeNum;
/*     */   public float smokeVelocityVertical;
/*     */   public float smokeVelocityHorizontal;
/*     */   public float gravity;
/*     */   public float gravityInWater;
/*     */   public String particleName;
/*     */   public boolean disableSmoke;
/*     */   public MCH_Color smokeColor;
/*     */   
/*     */   public MCH_ThrowableInfo(AddonResourceLocation location, String path) {
/*  66 */     super(location, path);
/*     */ 
/*     */     
/*  69 */     this.name = location.func_110623_a();
/*  70 */     this.displayName = location.func_110623_a();
/*  71 */     this.displayNameLang = new HashMap<>();
/*  72 */     this.itemID = 0;
/*  73 */     this.item = null;
/*  74 */     this.recipeString = new ArrayList<>();
/*  75 */     this.recipe = new ArrayList<>();
/*  76 */     this.isShapedRecipe = true;
/*  77 */     this.power = 0;
/*  78 */     this.acceleration = 1.0F;
/*  79 */     this.accelerationInWater = 1.0F;
/*  80 */     this.dispenseAcceleration = 1.0F;
/*  81 */     this.explosion = 0;
/*  82 */     this.delayFuse = 0;
/*  83 */     this.bound = 0.2F;
/*  84 */     this.timeFuse = 0;
/*  85 */     this.flaming = false;
/*  86 */     this.stackSize = 1;
/*  87 */     this.soundVolume = 1.0F;
/*  88 */     this.soundPitch = 1.0F;
/*  89 */     this.proximityFuseDist = 0.0F;
/*  90 */     this.accuracy = 0.0F;
/*  91 */     this.aliveTime = 10;
/*  92 */     this.bomblet = 0;
/*  93 */     this.bombletDiff = 0.3F;
/*  94 */     this.model = null;
/*  95 */     this.smokeSize = 10.0F;
/*  96 */     this.smokeNum = 0;
/*  97 */     this.smokeVelocityVertical = 1.0F;
/*  98 */     this.smokeVelocityHorizontal = 1.0F;
/*  99 */     this.gravity = 0.0F;
/* 100 */     this.gravityInWater = -0.04F;
/* 101 */     this.particleName = "explode";
/* 102 */     this.disableSmoke = true;
/* 103 */     this.smokeColor = new MCH_Color();
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
/*     */   public boolean validate() throws Exception {
/* 115 */     this.timeFuse *= 20;
/* 116 */     this.aliveTime *= 20;
/* 117 */     this.delayFuse *= 20;
/* 118 */     return super.validate();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItem() {
/* 124 */     return (Item)this.item;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void loadItemData(String item, String data) {
/* 130 */     if (item.compareTo("displayname") == 0) {
/*     */       
/* 132 */       this.displayName = data;
/*     */     }
/* 134 */     else if (item.compareTo("adddisplayname") == 0) {
/*     */       
/* 136 */       String[] s = data.split("\\s*,\\s*");
/* 137 */       if (s != null && s.length == 2)
/*     */       {
/* 139 */         this.displayNameLang.put(s[0].trim(), s[1].trim());
/*     */       }
/*     */     }
/* 142 */     else if (item.compareTo("itemid") == 0) {
/*     */       
/* 144 */       this.itemID = toInt(data, 0, 65535);
/*     */     }
/* 146 */     else if (item.compareTo("addrecipe") == 0 || item.compareTo("addshapelessrecipe") == 0) {
/*     */       
/* 148 */       this.isShapedRecipe = (item.compareTo("addrecipe") == 0);
/* 149 */       this.recipeString.add(data.toUpperCase());
/*     */     }
/* 151 */     else if (item.compareTo("power") == 0) {
/*     */       
/* 153 */       this.power = toInt(data);
/*     */     }
/* 155 */     else if (item.compareTo("acceleration") == 0) {
/*     */       
/* 157 */       this.acceleration = toFloat(data, 0.0F, 100.0F);
/*     */     }
/* 159 */     else if (item.compareTo("accelerationinwater") == 0) {
/*     */       
/* 161 */       this.accelerationInWater = toFloat(data, 0.0F, 100.0F);
/*     */     }
/* 163 */     else if (item.equalsIgnoreCase("DispenseAcceleration")) {
/*     */       
/* 165 */       this.dispenseAcceleration = toFloat(data, 0.0F, 1000.0F);
/*     */     }
/* 167 */     else if (item.compareTo("explosion") == 0) {
/*     */       
/* 169 */       this.explosion = toInt(data, 0, 50);
/*     */     }
/* 171 */     else if (item.equalsIgnoreCase("DelayFuse")) {
/*     */       
/* 173 */       this.delayFuse = toInt(data, 0, 100000);
/*     */     }
/* 175 */     else if (item.equalsIgnoreCase("Bound")) {
/*     */       
/* 177 */       this.bound = toFloat(data, 0.0F, 100000.0F);
/*     */     }
/* 179 */     else if (item.equalsIgnoreCase("TimeFuse")) {
/*     */       
/* 181 */       this.timeFuse = toInt(data, 0, 100000);
/*     */     }
/* 183 */     else if (item.compareTo("flaming") == 0) {
/*     */       
/* 185 */       this.flaming = toBool(data);
/*     */     }
/* 187 */     else if (item.equalsIgnoreCase("StackSize")) {
/*     */       
/* 189 */       this.stackSize = toInt(data, 1, 64);
/*     */     }
/* 191 */     else if (item.compareTo("soundvolume") == 0) {
/*     */       
/* 193 */       this.soundVolume = toFloat(data, 0.0F, 1000.0F);
/*     */     }
/* 195 */     else if (item.compareTo("soundpitch") == 0) {
/*     */       
/* 197 */       this.soundPitch = toFloat(data, 0.0F, 1.0F);
/*     */     }
/* 199 */     else if (item.compareTo("proximityfusedist") == 0) {
/*     */       
/* 201 */       this.proximityFuseDist = toFloat(data, 0.0F, 20.0F);
/*     */     }
/* 203 */     else if (item.compareTo("accuracy") == 0) {
/*     */       
/* 205 */       this.accuracy = toFloat(data, 0.0F, 1000.0F);
/*     */     }
/* 207 */     else if (item.equalsIgnoreCase("aliveTime")) {
/*     */       
/* 209 */       this.aliveTime = toInt(data, 0, 1000000);
/*     */     }
/* 211 */     else if (item.compareTo("bomblet") == 0) {
/*     */       
/* 213 */       this.bomblet = toInt(data, 0, 1000);
/*     */     }
/* 215 */     else if (item.equalsIgnoreCase("BombletDiff")) {
/*     */       
/* 217 */       this.bombletDiff = toFloat(data, 0.0F, 1000.0F);
/*     */     }
/* 219 */     else if (item.equalsIgnoreCase("SmokeSize")) {
/*     */       
/* 221 */       this.smokeSize = toFloat(data, 0.0F, 1000.0F);
/*     */     }
/* 223 */     else if (item.equalsIgnoreCase("SmokeNum")) {
/*     */       
/* 225 */       this.smokeNum = toInt(data, 0, 1000);
/*     */     }
/* 227 */     else if (item.equalsIgnoreCase("SmokeVelocityVertical")) {
/*     */       
/* 229 */       this.smokeVelocityVertical = toFloat(data, -100.0F, 100.0F);
/*     */     }
/* 231 */     else if (item.equalsIgnoreCase("SmokeVelocityHorizontal")) {
/*     */       
/* 233 */       this.smokeVelocityHorizontal = toFloat(data, 0.0F, 1000.0F);
/*     */     }
/* 235 */     else if (item.compareTo("gravity") == 0) {
/*     */       
/* 237 */       this.gravity = toFloat(data, -50.0F, 50.0F);
/*     */     }
/* 239 */     else if (item.equalsIgnoreCase("gravityInWater")) {
/*     */       
/* 241 */       this.gravityInWater = toFloat(data, -50.0F, 50.0F);
/*     */     }
/* 243 */     else if (item.compareTo("particle") == 0) {
/*     */       
/* 245 */       this.particleName = data.toLowerCase().trim();
/* 246 */       if (this.particleName.equalsIgnoreCase("none"))
/*     */       {
/* 248 */         this.particleName = "";
/*     */       }
/*     */     }
/* 251 */     else if (item.equalsIgnoreCase("DisableSmoke")) {
/*     */       
/* 253 */       this.disableSmoke = toBool(data);
/*     */     }
/* 255 */     else if (item.equalsIgnoreCase("SmokeColor")) {
/*     */       
/* 257 */       String[] s = data.split("\\s*,\\s*");
/* 258 */       if (s.length >= 3)
/*     */       {
/*     */         
/* 261 */         this
/* 262 */           .smokeColor = new MCH_Color(1.0F, 0.003921569F * toInt(s[0], 0, 255), 0.003921569F * toInt(s[1], 0, 255), 0.003921569F * toInt(s[2], 0, 255));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public class RoundItem
/*     */   {
/*     */     public final int num;
/*     */     public final Item item;
/*     */     
/*     */     public RoundItem(MCH_ThrowableInfo paramMCH_ThrowableInfo, int n, Item i) {
/* 274 */       this.num = n;
/* 275 */       this.item = i;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\throwable\MCH_ThrowableInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */