/*    */ package mcheli.plane;
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
/*    */ public class MCP_PlaneInfoManager
/*    */   extends MCH_AircraftInfoManager<MCP_PlaneInfo>
/*    */ {
/* 19 */   private static MCP_PlaneInfoManager instance = new MCP_PlaneInfoManager();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static MCP_PlaneInfo get(String name) {
/* 25 */     return (MCP_PlaneInfo)ContentRegistries.plane().get(name);
/*    */   }
/*    */ 
/*    */   
/*    */   public static MCP_PlaneInfoManager getInstance() {
/* 30 */     return instance;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public MCP_PlaneInfo newInfo(AddonResourceLocation name, String filepath) {
/* 37 */     return new MCP_PlaneInfo(name, filepath);
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
/*    */   public static MCP_PlaneInfo getFromItem(@Nullable Item item) {
/* 50 */     return getInstance().getAcInfoFromItem(item);
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
/*    */   public MCP_PlaneInfo getAcInfoFromItem(@Nullable Item item) {
/* 64 */     return (MCP_PlaneInfo)ContentRegistries.plane().findFirst(info -> (info.item == item));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected boolean contains(String name) {
/* 70 */     return ContentRegistries.plane().contains(name);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected int size() {
/* 76 */     return ContentRegistries.plane().size();
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\plane\MCP_PlaneInfoManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */