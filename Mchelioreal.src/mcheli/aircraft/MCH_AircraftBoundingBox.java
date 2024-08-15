/*     */ package mcheli.aircraft;
/*     */ 
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraft.util.math.Vec3d;
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
/*     */ public class MCH_AircraftBoundingBox
/*     */   extends AxisAlignedBB
/*     */ {
/*     */   private final MCH_EntityAircraft ac;
/*     */   
/*     */   protected MCH_AircraftBoundingBox(MCH_EntityAircraft ac) {
/*  22 */     this(ac, ac.func_174813_aQ());
/*     */   }
/*     */ 
/*     */   
/*     */   public MCH_AircraftBoundingBox(MCH_EntityAircraft ac, AxisAlignedBB aabb) {
/*  27 */     super(aabb.field_72340_a, aabb.field_72338_b, aabb.field_72339_c, aabb.field_72336_d, aabb.field_72337_e, aabb.field_72334_f);
/*  28 */     this.ac = ac;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AxisAlignedBB NewAABB(double x1, double y1, double z1, double x2, double y2, double z2) {
/*  34 */     return new MCH_AircraftBoundingBox(this.ac, new AxisAlignedBB(x1, y1, z1, x2, y2, z2));
/*     */   }
/*     */ 
/*     */   
/*     */   public double getDistSq(AxisAlignedBB a1, AxisAlignedBB a2) {
/*  39 */     double x1 = (a1.field_72336_d + a1.field_72340_a) / 2.0D;
/*  40 */     double y1 = (a1.field_72337_e + a1.field_72338_b) / 2.0D;
/*  41 */     double z1 = (a1.field_72334_f + a1.field_72339_c) / 2.0D;
/*  42 */     double x2 = (a2.field_72336_d + a2.field_72340_a) / 2.0D;
/*  43 */     double y2 = (a2.field_72337_e + a2.field_72338_b) / 2.0D;
/*  44 */     double z2 = (a2.field_72334_f + a2.field_72339_c) / 2.0D;
/*  45 */     double dx = x1 - x2;
/*  46 */     double dy = y1 - y2;
/*  47 */     double dz = z1 - z2;
/*  48 */     return dx * dx + dy * dy + dz * dz;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_72326_a(AxisAlignedBB aabb) {
/*  54 */     boolean ret = false;
/*  55 */     double dist = 1.0E7D;
/*  56 */     this.ac.lastBBDamageFactor = 1.0F;
/*     */     
/*  58 */     if (super.func_72326_a(aabb)) {
/*     */       
/*  60 */       dist = getDistSq(aabb, this);
/*  61 */       ret = true;
/*     */     } 
/*     */     
/*  64 */     for (MCH_BoundingBox bb : this.ac.extraBoundingBox) {
/*     */ 
/*     */       
/*  67 */       if (bb.getBoundingBox().func_72326_a(aabb)) {
/*     */         
/*  69 */         double dist2 = getDistSq(aabb, this);
/*     */         
/*  71 */         if (dist2 < dist) {
/*     */           
/*  73 */           dist = dist2;
/*  74 */           this.ac.lastBBDamageFactor = bb.damegeFactor;
/*     */         } 
/*  76 */         ret = true;
/*     */       } 
/*     */     } 
/*     */     
/*  80 */     return ret;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AxisAlignedBB func_72314_b(double x, double y, double z) {
/*  86 */     double d3 = this.field_72340_a - x;
/*  87 */     double d4 = this.field_72338_b - y;
/*  88 */     double d5 = this.field_72339_c - z;
/*  89 */     double d6 = this.field_72336_d + x;
/*  90 */     double d7 = this.field_72337_e + y;
/*  91 */     double d8 = this.field_72334_f + z;
/*  92 */     return NewAABB(d3, d4, d5, d6, d7, d8);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AxisAlignedBB func_111270_a(AxisAlignedBB other) {
/*  98 */     double d0 = Math.min(this.field_72340_a, other.field_72340_a);
/*  99 */     double d1 = Math.min(this.field_72338_b, other.field_72338_b);
/* 100 */     double d2 = Math.min(this.field_72339_c, other.field_72339_c);
/* 101 */     double d3 = Math.max(this.field_72336_d, other.field_72336_d);
/* 102 */     double d4 = Math.max(this.field_72337_e, other.field_72337_e);
/* 103 */     double d5 = Math.max(this.field_72334_f, other.field_72334_f);
/* 104 */     return NewAABB(d0, d1, d2, d3, d4, d5);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AxisAlignedBB func_72321_a(double x, double y, double z) {
/* 110 */     double d3 = this.field_72340_a;
/* 111 */     double d4 = this.field_72338_b;
/* 112 */     double d5 = this.field_72339_c;
/* 113 */     double d6 = this.field_72336_d;
/* 114 */     double d7 = this.field_72337_e;
/* 115 */     double d8 = this.field_72334_f;
/*     */     
/* 117 */     if (x < 0.0D)
/*     */     {
/* 119 */       d3 += x;
/*     */     }
/*     */     
/* 122 */     if (x > 0.0D)
/*     */     {
/* 124 */       d6 += x;
/*     */     }
/*     */     
/* 127 */     if (y < 0.0D)
/*     */     {
/* 129 */       d4 += y;
/*     */     }
/*     */     
/* 132 */     if (y > 0.0D)
/*     */     {
/* 134 */       d7 += y;
/*     */     }
/*     */     
/* 137 */     if (z < 0.0D)
/*     */     {
/* 139 */       d5 += z;
/*     */     }
/*     */     
/* 142 */     if (z > 0.0D)
/*     */     {
/* 144 */       d8 += z;
/*     */     }
/*     */     
/* 147 */     return NewAABB(d3, d4, d5, d6, d7, d8);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AxisAlignedBB func_191195_a(double x, double y, double z) {
/* 153 */     double d3 = this.field_72340_a + x;
/* 154 */     double d4 = this.field_72338_b + y;
/* 155 */     double d5 = this.field_72339_c + z;
/* 156 */     double d6 = this.field_72336_d - x;
/* 157 */     double d7 = this.field_72337_e - y;
/* 158 */     double d8 = this.field_72334_f - z;
/* 159 */     return NewAABB(d3, d4, d5, d6, d7, d8);
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB copy() {
/* 164 */     return NewAABB(this.field_72340_a, this.field_72338_b, this.field_72339_c, this.field_72336_d, this.field_72337_e, this.field_72334_f);
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getOffsetBoundingBox(double x, double y, double z) {
/* 169 */     return NewAABB(this.field_72340_a + x, this.field_72338_b + y, this.field_72339_c + z, this.field_72336_d + x, this.field_72337_e + y, this.field_72334_f + z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RayTraceResult func_72327_a(Vec3d v1, Vec3d v2) {
/* 176 */     this.ac.lastBBDamageFactor = 1.0F;
/*     */     
/* 178 */     RayTraceResult mop = super.func_72327_a(v1, v2);
/* 179 */     double dist = 1.0E7D;
/*     */     
/* 181 */     if (mop != null)
/*     */     {
/* 183 */       dist = v1.func_72438_d(mop.field_72307_f);
/*     */     }
/*     */     
/* 186 */     for (MCH_BoundingBox bb : this.ac.extraBoundingBox) {
/*     */ 
/*     */       
/* 189 */       RayTraceResult mop2 = bb.getBoundingBox().func_72327_a(v1, v2);
/*     */       
/* 191 */       if (mop2 != null) {
/*     */         
/* 193 */         double dist2 = v1.func_72438_d(mop2.field_72307_f);
/*     */         
/* 195 */         if (dist2 < dist) {
/*     */           
/* 197 */           mop = mop2;
/* 198 */           dist = dist2;
/* 199 */           this.ac.lastBBDamageFactor = bb.damegeFactor;
/*     */         } 
/*     */       } 
/*     */     } 
/* 203 */     return mop;
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\aircraft\MCH_AircraftBoundingBox.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */