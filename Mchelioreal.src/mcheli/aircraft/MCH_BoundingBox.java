/*    */ package mcheli.aircraft;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import mcheli.MCH_Config;
/*    */ import mcheli.MCH_Lib;
/*    */ import net.minecraft.util.math.AxisAlignedBB;
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
/*    */ public class MCH_BoundingBox
/*    */ {
/*    */   private AxisAlignedBB boundingBox;
/*    */   public final double offsetX;
/*    */   public final double offsetY;
/*    */   public final double offsetZ;
/*    */   public final float width;
/*    */   public final float height;
/*    */   public Vec3d rotatedOffset;
/* 27 */   public List<Vec3d> pos = new ArrayList<>();
/*    */   
/*    */   public final float damegeFactor;
/*    */   
/*    */   public MCH_BoundingBox(double x, double y, double z, float w, float h, float df) {
/* 32 */     this.offsetX = x;
/* 33 */     this.offsetY = y;
/* 34 */     this.offsetZ = z;
/* 35 */     this.width = w;
/* 36 */     this.height = h;
/* 37 */     this.damegeFactor = df;
/* 38 */     this.boundingBox = new AxisAlignedBB(x - (w / 2.0F), y - (h / 2.0F), z - (w / 2.0F), x + (w / 2.0F), y + (h / 2.0F), z + (w / 2.0F));
/*    */     
/* 40 */     updatePosition(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   public void add(double x, double y, double z) {
/* 45 */     this.pos.add(0, new Vec3d(x, y, z));
/* 46 */     while (this.pos.size() > MCH_Config.HitBoxDelayTick.prmInt + 2)
/*    */     {
/* 48 */       this.pos.remove(MCH_Config.HitBoxDelayTick.prmInt + 2);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public MCH_BoundingBox copy() {
/* 54 */     return new MCH_BoundingBox(this.offsetX, this.offsetY, this.offsetZ, this.width, this.height, this.damegeFactor);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void updatePosition(double posX, double posY, double posZ, float yaw, float pitch, float roll) {
/* 60 */     Vec3d v = new Vec3d(this.offsetX, this.offsetY, this.offsetZ);
/* 61 */     this.rotatedOffset = MCH_Lib.RotVec3(v, -yaw, -pitch, -roll);
/*    */     
/* 63 */     add(posX + this.rotatedOffset.field_72450_a, posY + this.rotatedOffset.field_72448_b, posZ + this.rotatedOffset.field_72449_c);
/*    */     
/* 65 */     int index = MCH_Config.HitBoxDelayTick.prmInt;
/*    */     
/* 67 */     Vec3d cp = (index + 0 < this.pos.size()) ? this.pos.get(index + 0) : this.pos.get(this.pos.size() - 1);
/*    */     
/* 69 */     Vec3d pp = (index + 1 < this.pos.size()) ? this.pos.get(index + 1) : this.pos.get(this.pos.size() - 1);
/*    */     
/* 71 */     double sx = (this.width + Math.abs(cp.field_72450_a - pp.field_72450_a)) / 2.0D;
/* 72 */     double sy = (this.height + Math.abs(cp.field_72448_b - pp.field_72448_b)) / 2.0D;
/* 73 */     double sz = (this.width + Math.abs(cp.field_72449_c - pp.field_72449_c)) / 2.0D;
/* 74 */     double x = (cp.field_72450_a + pp.field_72450_a) / 2.0D;
/* 75 */     double y = (cp.field_72448_b + pp.field_72448_b) / 2.0D;
/* 76 */     double z = (cp.field_72449_c + pp.field_72449_c) / 2.0D;
/*    */ 
/*    */     
/* 79 */     this.boundingBox = new AxisAlignedBB(x - sx, y - sy, z - sz, x + sx, y + sy, z + sz);
/*    */   }
/*    */ 
/*    */   
/*    */   public AxisAlignedBB getBoundingBox() {
/* 84 */     return this.boundingBox;
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\aircraft\MCH_BoundingBox.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */