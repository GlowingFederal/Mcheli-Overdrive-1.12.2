/*     */ package mcheli.particles;
/*     */ 
/*     */ import mcheli.MCH_Lib;
/*     */ import mcheli.__helper.entity.ITargetMarkerObject;
/*     */ import mcheli.multiplay.MCH_GuiTargetMarker;
/*     */ import mcheli.wrapper.W_Reflection;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.client.renderer.BufferBuilder;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.scoreboard.Team;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.World;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MCH_EntityParticleMarkPoint
/*     */   extends MCH_EntityParticleBase
/*     */   implements ITargetMarkerObject
/*     */ {
/*     */   final Team taem;
/*     */   
/*     */   public MCH_EntityParticleMarkPoint(World par1World, double x, double y, double z, Team team) {
/*  31 */     super(par1World, x, y, z, 0.0D, 0.0D, 0.0D);
/*  32 */     setParticleMaxAge(30);
/*  33 */     this.taem = team;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_189213_a() {
/*  39 */     this.field_187123_c = this.field_187126_f;
/*  40 */     this.field_187124_d = this.field_187127_g;
/*  41 */     this.field_187125_e = this.field_187128_h;
/*     */     
/*  43 */     EntityPlayerSP entityPlayerSP = (Minecraft.func_71410_x()).field_71439_g;
/*     */     
/*  45 */     if (entityPlayerSP == null) {
/*     */ 
/*     */       
/*  48 */       func_187112_i();
/*     */     }
/*  50 */     else if (entityPlayerSP.func_96124_cp() == null && this.taem != null) {
/*     */ 
/*     */       
/*  53 */       func_187112_i();
/*     */     
/*     */     }
/*  56 */     else if (entityPlayerSP.func_96124_cp() != null && !entityPlayerSP.func_184194_a(this.taem)) {
/*     */ 
/*     */       
/*  59 */       func_187112_i();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_187112_i() {
/*  68 */     super.func_187112_i();
/*     */     
/*  70 */     MCH_Lib.DbgLog(true, "MCH_EntityParticleMarkPoint.setExpired : " + this, new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int func_70537_b() {
/*  76 */     return 3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_180434_a(BufferBuilder buffer, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
/*  84 */     GL11.glPushMatrix();
/*     */     
/*  86 */     Minecraft mc = Minecraft.func_71410_x();
/*  87 */     EntityPlayerSP entityPlayerSP = mc.field_71439_g;
/*     */     
/*  89 */     if (entityPlayerSP == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/*  94 */     double ix = field_70556_an;
/*  95 */     double iy = field_70554_ao;
/*  96 */     double iz = field_70555_ap;
/*     */ 
/*     */     
/*  99 */     if (mc.field_71474_y.field_74320_O > 0 && entityIn != null) {
/*     */ 
/*     */       
/* 102 */       Entity viewer = entityIn;
/* 103 */       double dist = W_Reflection.getThirdPersonDistance();
/* 104 */       float yaw = (mc.field_71474_y.field_74320_O != 2) ? -viewer.field_70177_z : -viewer.field_70177_z;
/* 105 */       float pitch = (mc.field_71474_y.field_74320_O != 2) ? -viewer.field_70125_A : -viewer.field_70125_A;
/* 106 */       Vec3d v = MCH_Lib.RotVec3(0.0D, 0.0D, -dist, yaw, pitch);
/*     */       
/* 108 */       if (mc.field_71474_y.field_74320_O == 2)
/*     */       {
/*     */ 
/*     */ 
/*     */         
/* 113 */         v = new Vec3d(-v.field_72450_a, -v.field_72448_b, -v.field_72449_c);
/*     */       }
/*     */       
/* 116 */       Vec3d vs = new Vec3d(viewer.field_70165_t, viewer.field_70163_u + viewer.func_70047_e(), viewer.field_70161_v);
/*     */       
/* 118 */       RayTraceResult mop = entityIn.field_70170_p.func_72933_a(vs.func_72441_c(0.0D, 0.0D, 0.0D), vs
/* 119 */           .func_72441_c(v.field_72450_a, v.field_72448_b, v.field_72449_c));
/* 120 */       double block_dist = dist;
/*     */ 
/*     */       
/* 123 */       if (mop != null && mop.field_72313_a == RayTraceResult.Type.BLOCK) {
/*     */         
/* 125 */         block_dist = vs.func_72438_d(mop.field_72307_f) - 0.4D;
/*     */         
/* 127 */         if (block_dist < 0.0D)
/*     */         {
/* 129 */           block_dist = 0.0D;
/*     */         }
/*     */       } 
/*     */       
/* 133 */       GL11.glTranslated(v.field_72450_a * block_dist / dist, v.field_72448_b * block_dist / dist, v.field_72449_c * block_dist / dist);
/* 134 */       ix += v.field_72450_a * block_dist / dist;
/* 135 */       iy += v.field_72448_b * block_dist / dist;
/* 136 */       iz += v.field_72449_c * block_dist / dist;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 142 */     double px = (float)(this.field_187123_c + (this.field_187126_f - this.field_187123_c) * partialTicks - ix);
/* 143 */     double py = (float)(this.field_187124_d + (this.field_187127_g - this.field_187124_d) * partialTicks - iy);
/* 144 */     double pz = (float)(this.field_187125_e + (this.field_187128_h - this.field_187125_e) * partialTicks - iz);
/*     */     
/* 146 */     double scale = Math.sqrt(px * px + py * py + pz * pz) / 100.0D;
/*     */     
/* 148 */     if (scale < 1.0D)
/*     */     {
/* 150 */       scale = 1.0D;
/*     */     }
/*     */     
/* 153 */     MCH_GuiTargetMarker.addMarkEntityPos(100, this, px / scale, py / scale, pz / scale, false);
/* 154 */     GL11.glPopMatrix();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double getX() {
/* 160 */     return this.field_187126_f;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double getY() {
/* 166 */     return this.field_187127_g;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double getZ() {
/* 172 */     return this.field_187128_h;
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\particles\MCH_EntityParticleMarkPoint.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */