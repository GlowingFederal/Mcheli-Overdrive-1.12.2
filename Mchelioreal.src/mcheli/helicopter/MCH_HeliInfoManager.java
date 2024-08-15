/*    */ package mcheli.helicopter;
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
/*    */ public class MCH_HeliInfoManager
/*    */   extends MCH_AircraftInfoManager<MCH_HeliInfo>
/*    */ {
/* 19 */   private static final MCH_HeliInfoManager instance = new MCH_HeliInfoManager();
/*    */ 
/*    */ 
/*    */   
/*    */   public static MCH_HeliInfoManager getInstance() {
/* 24 */     return instance;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public static MCH_HeliInfo get(String name) {
/* 31 */     return (MCH_HeliInfo)ContentRegistries.heli().get(name);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public MCH_HeliInfo newInfo(AddonResourceLocation name, String filepath) {
/* 39 */     return new MCH_HeliInfo(name, filepath);
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
/*    */   public static MCH_HeliInfo getFromItem(Item item) {
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
/*    */   public MCH_HeliInfo getAcInfoFromItem(Item item) {
/* 64 */     return (MCH_HeliInfo)ContentRegistries.heli().findFirst(info -> (info.item == item));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected boolean contains(String name) {
/* 70 */     return ContentRegistries.heli().contains(name);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected int size() {
/* 76 */     return ContentRegistries.heli().size();
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\helicopter\MCH_HeliInfoManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */