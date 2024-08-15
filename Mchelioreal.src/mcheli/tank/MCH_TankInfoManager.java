/*    */ package mcheli.tank;
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
/*    */ public class MCH_TankInfoManager
/*    */   extends MCH_AircraftInfoManager<MCH_TankInfo>
/*    */ {
/* 19 */   private static MCH_TankInfoManager instance = new MCH_TankInfoManager();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public static MCH_TankInfo get(String name) {
/* 26 */     return (MCH_TankInfo)ContentRegistries.tank().get(name);
/*    */   }
/*    */ 
/*    */   
/*    */   public static MCH_TankInfoManager getInstance() {
/* 31 */     return instance;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public MCH_TankInfo newInfo(AddonResourceLocation name, String filepath) {
/* 39 */     return new MCH_TankInfo(name, filepath);
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
/*    */   public static MCH_TankInfo getFromItem(Item item) {
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
/*    */   @Nullable
/*    */   public MCH_TankInfo getAcInfoFromItem(Item item) {
/* 64 */     return (MCH_TankInfo)ContentRegistries.tank().findFirst(info -> (info.item == item));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected boolean contains(String name) {
/* 70 */     return ContentRegistries.tank().contains(name);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected int size() {
/* 76 */     return ContentRegistries.tank().size();
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\tank\MCH_TankInfoManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */