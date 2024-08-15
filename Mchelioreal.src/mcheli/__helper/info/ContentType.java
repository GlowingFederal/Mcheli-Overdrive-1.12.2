/*    */ package mcheli.__helper.info;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum ContentType
/*    */ {
/* 10 */   HELICOPTER("helicopter", "helicopters"),
/* 11 */   PLANE("plane", "plane"),
/* 12 */   TANK("tank", "tanks"),
/* 13 */   VEHICLE("vehicle", "vehicles"),
/*    */   
/* 15 */   WEAPON("weapon", "weapons"),
/*    */   
/* 17 */   THROWABLE("throwable", "throwable"),
/* 18 */   HUD("hud", "hud");
/*    */   
/*    */   public final String type;
/*    */   
/*    */   public final String dirName;
/*    */   
/*    */   ContentType(String typeName, String dirName) {
/* 25 */     this.type = typeName;
/* 26 */     this.dirName = dirName;
/*    */   }
/*    */ 
/*    */   
/*    */   public static boolean validateDirName(String dirName) {
/* 31 */     for (ContentType type : values()) {
/*    */       
/* 33 */       if (type.dirName.equals(dirName))
/*    */       {
/* 35 */         return true;
/*    */       }
/*    */     } 
/*    */     
/* 39 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\__helper\info\ContentType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */