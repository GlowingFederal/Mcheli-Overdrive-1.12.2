/*     */ package mcheli.weapon;
/*     */ 
/*     */ import java.util.List;
/*     */ import mcheli.wrapper.W_Lib;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MCH_EntityBomb
/*     */   extends MCH_EntityBaseBullet
/*     */ {
/*     */   public MCH_EntityBomb(World par1World) {
/*  24 */     super(par1World);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public MCH_EntityBomb(World par1World, double posX, double posY, double posZ, double targetX, double targetY, double targetZ, float yaw, float pitch, double acceleration) {
/*  30 */     super(par1World, posX, posY, posZ, targetX, targetY, targetZ, yaw, pitch, acceleration);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_70071_h_() {
/*  36 */     super.func_70071_h_();
/*     */     
/*  38 */     if (!this.field_70170_p.field_72995_K && getInfo() != null) {
/*     */       
/*  40 */       this.field_70159_w *= 0.999D;
/*  41 */       this.field_70179_y *= 0.999D;
/*     */       
/*  43 */       if (func_70090_H()) {
/*     */         
/*  45 */         this.field_70159_w *= (getInfo()).velocityInWater;
/*  46 */         this.field_70181_x *= (getInfo()).velocityInWater;
/*  47 */         this.field_70179_y *= (getInfo()).velocityInWater;
/*     */       } 
/*     */       
/*  50 */       float dist = (getInfo()).proximityFuseDist;
/*     */       
/*  52 */       if (dist > 0.1F && getCountOnUpdate() % 10 == 0) {
/*     */ 
/*     */         
/*  55 */         List<Entity> list = this.field_70170_p.func_72839_b((Entity)this, 
/*  56 */             func_174813_aQ().func_72314_b(dist, dist, dist));
/*     */         
/*  58 */         if (list != null)
/*     */         {
/*  60 */           for (int i = 0; i < list.size(); i++) {
/*     */             
/*  62 */             Entity entity = list.get(i);
/*     */             
/*  64 */             if (W_Lib.isEntityLivingBase(entity) && canBeCollidedEntity(entity)) {
/*     */ 
/*     */               
/*  67 */               RayTraceResult m = new RayTraceResult(new Vec3d(this.field_70165_t, this.field_70163_u, this.field_70161_v), EnumFacing.DOWN, new BlockPos(this.field_70165_t + 0.5D, this.field_70163_u + 0.5D, this.field_70161_v + 0.5D));
/*     */ 
/*     */ 
/*     */               
/*  71 */               onImpact(m, 1.0F);
/*     */               
/*     */               break;
/*     */             } 
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/*  79 */     onUpdateBomblet();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void sprinkleBomblet() {
/*  85 */     if (!this.field_70170_p.field_72995_K) {
/*     */ 
/*     */       
/*  88 */       MCH_EntityBomb e = new MCH_EntityBomb(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v, this.field_70159_w, this.field_70181_x, this.field_70179_y, this.field_70146_Z.nextInt(360), 0.0F, this.acceleration);
/*     */       
/*  90 */       e.setParameterFromWeapon(this, this.shootingAircraft, this.shootingEntity);
/*  91 */       e.setName(func_70005_c_());
/*     */ 
/*     */       
/*  94 */       float RANDOM = (getInfo()).bombletDiff;
/*     */       
/*  96 */       e.field_70159_w = this.field_70159_w * 1.0D + ((this.field_70146_Z.nextFloat() - 0.5F) * RANDOM);
/*  97 */       e.field_70181_x = this.field_70181_x * 1.0D / 2.0D + ((this.field_70146_Z.nextFloat() - 0.5F) * RANDOM / 2.0F);
/*  98 */       e.field_70179_y = this.field_70179_y * 1.0D + ((this.field_70146_Z.nextFloat() - 0.5F) * RANDOM);
/*  99 */       e.setBomblet();
/*     */       
/* 101 */       this.field_70170_p.func_72838_d((Entity)e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public MCH_BulletModel getDefaultBulletModel() {
/* 108 */     return MCH_DefaultBulletModels.Bomb;
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\weapon\MCH_EntityBomb.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */