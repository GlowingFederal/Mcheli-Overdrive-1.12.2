/*    */ package mcheli.hud;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum MCH_HudItemStringArgs
/*    */ {
/* 11 */   NONE,
/* 12 */   NAME,
/* 13 */   ALTITUDE,
/* 14 */   DATE,
/* 15 */   MC_THOR,
/* 16 */   MC_TMIN,
/* 17 */   MC_TSEC,
/* 18 */   MAX_HP,
/* 19 */   HP,
/* 20 */   HP_PER,
/* 21 */   POS_X,
/* 22 */   POS_Y,
/* 23 */   POS_Z,
/* 24 */   MOTION_X,
/* 25 */   MOTION_Y,
/* 26 */   MOTION_Z,
/* 27 */   INVENTORY,
/* 28 */   WPN_NAME,
/* 29 */   WPN_AMMO,
/* 30 */   WPN_RM_AMMO,
/* 31 */   RELOAD_PER,
/* 32 */   RELOAD_SEC,
/* 33 */   MORTAR_DIST,
/* 34 */   MC_VER,
/* 35 */   MOD_VER,
/* 36 */   MOD_NAME,
/* 37 */   YAW,
/* 38 */   PITCH,
/* 39 */   ROLL,
/* 40 */   PLYR_YAW,
/* 41 */   PLYR_PITCH,
/* 42 */   TVM_POS_X,
/* 43 */   TVM_POS_Y,
/* 44 */   TVM_POS_Z,
/* 45 */   TVM_DIFF,
/* 46 */   CAM_ZOOM,
/* 47 */   UAV_DIST,
/* 48 */   KEY_GUI,
/* 49 */   THROTTLE;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static MCH_HudItemStringArgs toArgs(String name) {
/* 57 */     MCH_HudItemStringArgs a = NONE;
/*    */     
/*    */     try {
/* 60 */       a = valueOf(name.toUpperCase());
/*    */     }
/* 62 */     catch (Exception e) {
/*    */       
/* 64 */       e.printStackTrace();
/*    */     } finally {}
/*    */ 
/*    */ 
/*    */     
/* 69 */     return a;
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\hud\MCH_HudItemStringArgs.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */