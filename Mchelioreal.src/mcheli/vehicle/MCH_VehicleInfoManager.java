/*    */ package mcheli.vehicle;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import mcheli.MCH_BaseInfo;
/*    */ import mcheli.__helper.addon.AddonResourceLocation;
/*    */ import mcheli.__helper.info.ContentRegistries;
/*    */ import mcheli.aircraft.MCH_AircraftInfo;
/*    */ import mcheli.aircraft.MCH_AircraftInfoManager;
/*    */ import net.minecraft.item.Item;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MCH_VehicleInfoManager
/*    */   extends MCH_AircraftInfoManager<MCH_VehicleInfo>
/*    */ {
/* 19 */   private static MCH_VehicleInfoManager instance = new MCH_VehicleInfoManager();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public static MCH_VehicleInfo get(String name) {
/* 26 */     return (MCH_VehicleInfo)ContentRegistries.vehicle().get(name);
/*    */   }
/*    */ 
/*    */   
/*    */   public static MCH_VehicleInfoManager getInstance() {
/* 31 */     return instance;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public MCH_VehicleInfo newInfo(AddonResourceLocation name, String filepath) {
/* 39 */     return new MCH_VehicleInfo(name, filepath);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public static MCH_VehicleInfo getFromItem(Item item) {
/* 52 */     return getInstance().getAcInfoFromItem(item);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public MCH_VehicleInfo getAcInfoFromItem(Item item) {
/* 66 */     return (MCH_VehicleInfo)ContentRegistries.vehicle().findFirst(info -> (info.item == item));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected boolean contains(String name) {
/* 72 */     return ContentRegistries.vehicle().contains(name);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected int size() {
/* 78 */     return ContentRegistries.vehicle().size();
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\vehicle\MCH_VehicleInfoManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */