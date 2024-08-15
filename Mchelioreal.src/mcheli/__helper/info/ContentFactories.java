/*    */ package mcheli.__helper.info;
/*    */ 
/*    */ import com.google.common.collect.Maps;
/*    */ import java.util.Map;
/*    */ import java.util.function.BiFunction;
/*    */ import javax.annotation.Nullable;
/*    */ import mcheli.__helper.MCH_Utils;
/*    */ import mcheli.__helper.addon.AddonResourceLocation;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ContentFactories
/*    */ {
/* 27 */   private static final Map<String, IContentFactory> TABLE = Maps.newHashMap();
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public static IContentFactory getFactory(@Nullable String dirName) {
/* 32 */     return (dirName == null) ? null : TABLE.get(dirName);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private static IContentFactory createFactory(final ContentType type, final BiFunction<AddonResourceLocation, String, IContentData> function) {
/* 38 */     return new IContentFactory()
/*    */       {
/*    */         
/*    */         public IContentData create(AddonResourceLocation location, String filepath)
/*    */         {
/* 43 */           return function.apply(location, filepath);
/*    */         }
/*    */ 
/*    */ 
/*    */         
/*    */         public ContentType getType() {
/* 49 */           return type;
/*    */         }
/*    */       };
/*    */   }
/*    */ 
/*    */   
/*    */   static {
/* 56 */     TABLE.put("helicopters", createFactory(ContentType.HELICOPTER, mcheli.helicopter.MCH_HeliInfo::new));
/* 57 */     TABLE.put("planes", createFactory(ContentType.PLANE, mcheli.plane.MCP_PlaneInfo::new));
/* 58 */     TABLE.put("tanks", createFactory(ContentType.TANK, mcheli.tank.MCH_TankInfo::new));
/* 59 */     TABLE.put("vehicles", createFactory(ContentType.VEHICLE, mcheli.vehicle.MCH_VehicleInfo::new));
/* 60 */     TABLE.put("throwable", createFactory(ContentType.THROWABLE, mcheli.throwable.MCH_ThrowableInfo::new));
/* 61 */     TABLE.put("weapons", createFactory(ContentType.WEAPON, mcheli.weapon.MCH_WeaponInfo::new));
/*    */     
/* 63 */     if (MCH_Utils.isClient())
/*    */     {
/* 65 */       TABLE.put("hud", createFactory(ContentType.HUD, mcheli.hud.MCH_Hud::new));
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\__helper\info\ContentFactories.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */