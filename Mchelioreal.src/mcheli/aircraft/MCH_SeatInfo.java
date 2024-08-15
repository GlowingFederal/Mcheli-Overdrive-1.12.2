/*    */ package mcheli.aircraft;
/*    */ 
/*    */ import net.minecraft.util.math.Vec3d;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MCH_SeatInfo
/*    */ {
/*    */   public final Vec3d pos;
/*    */   public final boolean gunner;
/*    */   private final MCH_AircraftInfo.CameraPosition camPos;
/*    */   public boolean invCamPos;
/*    */   public final boolean switchgunner;
/*    */   public final boolean fixRot;
/*    */   public final float fixYaw;
/*    */   public final float fixPitch;
/*    */   public final float minPitch;
/*    */   public final float maxPitch;
/*    */   public final boolean rotSeat;
/*    */   
/*    */   public MCH_SeatInfo(Vec3d p, boolean g, MCH_AircraftInfo.CameraPosition cp, boolean icp, boolean sg, boolean fr, float yaw, float pitch, float pmin, float pmax, boolean rotSeat) {
/* 28 */     this.camPos = cp;
/* 29 */     this.pos = p;
/* 30 */     this.gunner = g;
/* 31 */     this.invCamPos = icp;
/* 32 */     this.switchgunner = sg;
/* 33 */     this.fixRot = fr;
/* 34 */     this.fixYaw = yaw;
/* 35 */     this.fixPitch = pitch;
/* 36 */     this.minPitch = pmin;
/* 37 */     this.maxPitch = pmax;
/* 38 */     this.rotSeat = rotSeat;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public MCH_SeatInfo(Vec3d p, boolean g, MCH_AircraftInfo.CameraPosition cp, boolean icp, boolean sg, boolean fr, float yaw, float pitch, boolean rotSeat) {
/* 44 */     this(p, g, cp, icp, sg, fr, yaw, pitch, -30.0F, 70.0F, rotSeat);
/*    */   }
/*    */ 
/*    */   
/*    */   public MCH_SeatInfo(Vec3d p, MCH_AircraftInfo.CameraPosition cp, float yaw, float pitch, boolean rotSeat) {
/* 49 */     this(p, false, cp, false, false, false, yaw, pitch, -30.0F, 70.0F, rotSeat);
/*    */   }
/*    */ 
/*    */   
/*    */   public MCH_SeatInfo(Vec3d p, boolean rotSeat) {
/* 54 */     this(p, false, null, false, false, false, 0.0F, 0.0F, -30.0F, 70.0F, rotSeat);
/*    */   }
/*    */ 
/*    */   
/*    */   public MCH_AircraftInfo.CameraPosition getCamPos() {
/* 59 */     return this.camPos;
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\aircraft\MCH_SeatInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */