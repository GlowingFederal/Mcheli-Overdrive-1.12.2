/*     */ package mcheli.block;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import mcheli.MCH_IRecipeList;
/*     */ import mcheli.MCH_ModelManager;
/*     */ import mcheli.__helper.client.RecipeDescriptionManager;
/*     */ import mcheli.aircraft.MCH_AircraftInfo;
/*     */ import mcheli.aircraft.MCH_AircraftInfoManager;
/*     */ import mcheli.plane.MCP_PlaneInfo;
/*     */ import mcheli.weapon.MCH_WeaponInfo;
/*     */ import mcheli.weapon.MCH_WeaponInfoManager;
/*     */ import mcheli.wrapper.modelloader.W_ModelCustom;
/*     */ import net.minecraft.item.crafting.IRecipe;
/*     */ import net.minecraft.util.ResourceLocation;
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
/*     */ public class MCH_CurrentRecipe
/*     */ {
/*     */   public final IRecipe recipe;
/*     */   public final int index;
/*     */   public final String displayName;
/*     */   public final List<ResourceLocation> descTexture;
/*     */   private final MCH_AircraftInfo acInfo;
/*     */   public List<String> infoItem;
/*     */   public List<String> infoData;
/*     */   private int descMaxPage;
/*     */   private int descPage;
/*     */   private W_ModelCustom model;
/*     */   public int modelRot;
/*     */   private ResourceLocation modelTexture;
/*     */   
/*     */   public MCH_CurrentRecipe(MCH_IRecipeList list, int idx) {
/*  44 */     if (list.getRecipeListSize() > 0) {
/*     */       
/*  46 */       this.recipe = list.getRecipe(idx);
/*     */     }
/*     */     else {
/*     */       
/*  50 */       this.recipe = null;
/*     */     } 
/*     */     
/*  53 */     this.index = idx;
/*  54 */     this.displayName = (this.recipe != null) ? this.recipe.func_77571_b().func_82833_r() : "None";
/*  55 */     this.descTexture = getDescTexture(this.recipe);
/*  56 */     this.descPage = 0;
/*  57 */     this.descMaxPage = this.descTexture.size();
/*     */     
/*  59 */     MCH_AircraftInfo info = null;
/*     */     
/*  61 */     if (list instanceof MCH_AircraftInfoManager) {
/*     */ 
/*     */       
/*  64 */       info = ((MCH_AircraftInfoManager)list).getAcInfoFromItem(this.recipe);
/*     */       
/*  66 */       if (info != null) {
/*     */         
/*  68 */         this.descMaxPage++;
/*     */         
/*  70 */         String dir = info.getDirectoryName();
/*  71 */         String name = info.name;
/*     */         
/*  73 */         this.model = MCH_ModelManager.get(dir, name);
/*     */         
/*  75 */         if (this.model != null) {
/*     */           
/*  77 */           this.modelTexture = new ResourceLocation("mcheli", "textures/" + dir + "/" + name + ".png");
/*     */           
/*  79 */           this.descMaxPage++;
/*     */           
/*  81 */           if (list instanceof mcheli.plane.MCP_PlaneInfoManager) {
/*     */             
/*  83 */             this.modelRot = 0;
/*     */           }
/*     */           else {
/*     */             
/*  87 */             this.modelRot = 1;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  93 */     getAcInfoText(info);
/*  94 */     this.acInfo = info;
/*     */   }
/*     */ 
/*     */   
/*     */   private void getAcInfoText(MCH_AircraftInfo info) {
/*  99 */     this.infoItem = new ArrayList<>();
/* 100 */     this.infoData = new ArrayList<>();
/*     */     
/* 102 */     if (info == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 107 */     getAcInfoTextSub("Name", info.getItemStack().func_82833_r());
/* 108 */     getAcInfoTextSub("HP", "" + info.maxHp);
/*     */     
/* 110 */     int seatNum = !info.isUAV ? info.getNumSeat() : (info.getNumSeat() - 1);
/*     */     
/* 112 */     getAcInfoTextSub("Num of Seat", "" + seatNum);
/* 113 */     getAcInfoTextSub("GunnerMode", info.isEnableGunnerMode ? "YES" : "NO");
/* 114 */     getAcInfoTextSub("NightVision", info.isEnableNightVision ? "YES" : "NO");
/* 115 */     getAcInfoTextSub("Radar", info.isEnableEntityRadar ? "YES" : "NO");
/* 116 */     getAcInfoTextSub("Inventory", "" + info.inventorySize);
/*     */     
/* 118 */     if (info instanceof MCP_PlaneInfo) {
/*     */       
/* 120 */       MCP_PlaneInfo pinfo = (MCP_PlaneInfo)info;
/*     */       
/* 122 */       getAcInfoTextSub("VTOL", pinfo.isEnableVtol ? "YES" : "NO");
/*     */     } 
/*     */     
/* 125 */     if (info.getWeaponNum() > 0) {
/*     */       
/* 127 */       getAcInfoTextSub("Armed----------------");
/*     */       
/* 129 */       for (int i = 0; i < info.getWeaponNum(); i++) {
/*     */         
/* 131 */         String type = (info.getWeaponSetById(i)).type;
/* 132 */         MCH_WeaponInfo winfo = MCH_WeaponInfoManager.get(type);
/*     */         
/* 134 */         if (winfo != null) {
/*     */           
/* 136 */           getAcInfoTextSub(winfo.getWeaponTypeName(), winfo.displayName);
/*     */         }
/*     */         else {
/*     */           
/* 140 */           getAcInfoTextSub("ERROR", "Not found weapon " + (i + 1));
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void getAcInfoTextSub(String item, String data) {
/* 148 */     this.infoItem.add(item + " :");
/* 149 */     this.infoData.add(data);
/*     */   }
/*     */ 
/*     */   
/*     */   private void getAcInfoTextSub(String item) {
/* 154 */     this.infoItem.add(item);
/* 155 */     this.infoData.add("");
/*     */   }
/*     */ 
/*     */   
/*     */   public void switchNextPage() {
/* 160 */     if (this.descMaxPage >= 2) {
/*     */       
/* 162 */       this.descPage = (this.descPage + 1) % this.descMaxPage;
/*     */     }
/*     */     else {
/*     */       
/* 166 */       this.descPage = 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void switchPrevPage() {
/* 172 */     this.descPage--;
/*     */     
/* 174 */     if (this.descPage < 0 && this.descMaxPage >= 2) {
/*     */       
/* 176 */       this.descPage = this.descMaxPage - 1;
/*     */     }
/*     */     else {
/*     */       
/* 180 */       this.descPage = 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int getDescCurrentPage() {
/* 186 */     return this.descPage;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDescCurrentPage(int page) {
/* 191 */     if (this.descMaxPage > 0) {
/*     */       
/* 193 */       this.descPage = (page < this.descMaxPage) ? page : (this.descMaxPage - 1);
/*     */     }
/*     */     else {
/*     */       
/* 197 */       this.descPage = 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int getDescMaxPage() {
/* 203 */     return this.descMaxPage;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public ResourceLocation getCurrentPageTexture() {
/* 209 */     if (this.descPage < this.descTexture.size())
/*     */     {
/* 211 */       return this.descTexture.get(this.descPage);
/*     */     }
/*     */     
/* 214 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public W_ModelCustom getModel() {
/* 219 */     return this.model;
/*     */   }
/*     */ 
/*     */   
/*     */   public ResourceLocation getModelTexture() {
/* 224 */     return this.modelTexture;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public MCH_AircraftInfo getAcInfo() {
/* 230 */     return this.acInfo;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isCurrentPageTexture() {
/* 235 */     return (this.descPage >= 0 && this.descPage < this.descTexture.size());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isCurrentPageModel() {
/* 240 */     if (getAcInfo() != null && getModel() != null)
/*     */     {
/* 242 */       if (this.descPage == this.descTexture.size())
/*     */       {
/* 244 */         return true;
/*     */       }
/*     */     }
/* 247 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isCurrentPageAcInfo() {
/* 252 */     if (getAcInfo() != null)
/*     */     {
/* 254 */       if (this.descPage == this.descMaxPage - 1)
/*     */       {
/* 256 */         return true;
/*     */       }
/*     */     }
/* 259 */     return false;
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
/*     */ 
/*     */ 
/*     */   
/*     */   private List<ResourceLocation> getDescTexture(@Nullable IRecipe r) {
/* 296 */     return (List<ResourceLocation>)RecipeDescriptionManager.getDescriptionTextures(r.getRegistryName());
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\block\MCH_CurrentRecipe.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */