/*     */ package mcheli.weapon;
/*     */ 
/*     */ import mcheli.MCH_Lib;
/*     */ import mcheli.wrapper.W_MovingObjectPosition;
/*     */ import mcheli.wrapper.W_WorldFunc;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.math.MathHelper;
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
/*     */ public class MCH_WeaponTorpedo
/*     */   extends MCH_WeaponBase
/*     */ {
/*     */   public MCH_WeaponTorpedo(World w, Vec3d v, float yaw, float pitch, String nm, MCH_WeaponInfo wi) {
/*  22 */     super(w, v, yaw, pitch, nm, wi);
/*  23 */     this.acceleration = 0.5F;
/*  24 */     this.explosionPower = 8;
/*  25 */     this.power = 35;
/*  26 */     this.interval = -100;
/*     */     
/*  28 */     if (w.field_72995_K) {
/*  29 */       this.interval -= 10;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shot(MCH_WeaponParam prm) {
/*  35 */     if (getInfo() != null) {
/*     */       
/*  37 */       if ((getInfo()).isGuidedTorpedo)
/*     */       {
/*  39 */         return shotGuided(prm);
/*     */       }
/*     */       
/*  42 */       return shotNoGuided(prm);
/*     */     } 
/*     */     
/*  45 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean shotNoGuided(MCH_WeaponParam prm) {
/*  50 */     if (this.worldObj.field_72995_K)
/*     */     {
/*  52 */       return true;
/*     */     }
/*  54 */     float yaw = prm.rotYaw;
/*  55 */     float pitch = prm.rotPitch;
/*  56 */     double mx = (-MathHelper.func_76126_a(yaw / 180.0F * 3.1415927F) * MathHelper.func_76134_b(pitch / 180.0F * 3.1415927F));
/*  57 */     double mz = (MathHelper.func_76134_b(yaw / 180.0F * 3.1415927F) * MathHelper.func_76134_b(pitch / 180.0F * 3.1415927F));
/*  58 */     double my = -MathHelper.func_76126_a(pitch / 180.0F * 3.1415927F);
/*     */     
/*  60 */     mx = mx * (getInfo()).acceleration + prm.entity.field_70159_w;
/*  61 */     my = my * (getInfo()).acceleration + prm.entity.field_70181_x;
/*  62 */     mz = mz * (getInfo()).acceleration + prm.entity.field_70179_y;
/*     */     
/*  64 */     this.acceleration = MathHelper.func_76133_a(mx * mx + my * my + mz * mz);
/*     */     
/*  66 */     MCH_EntityTorpedo e = new MCH_EntityTorpedo(this.worldObj, prm.posX, prm.posY, prm.posZ, mx, my, mz, yaw, 0.0F, this.acceleration);
/*     */ 
/*     */     
/*  69 */     e.setName(this.name);
/*  70 */     e.setParameterFromWeapon(this, prm.entity, prm.user);
/*     */     
/*  72 */     e.field_70159_w = mx;
/*  73 */     e.field_70181_x = my;
/*  74 */     e.field_70179_y = mz;
/*  75 */     e.accelerationInWater = (getInfo() != null) ? (getInfo()).accelerationInWater : 1.0D;
/*     */     
/*  77 */     this.worldObj.func_72838_d((Entity)e);
/*     */     
/*  79 */     playSound(prm.entity);
/*     */     
/*  81 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean shotGuided(MCH_WeaponParam prm) {
/*  86 */     float yaw = prm.user.field_70177_z;
/*  87 */     float pitch = prm.user.field_70125_A;
/*  88 */     Vec3d v = MCH_Lib.RotVec3(0.0D, 0.0D, 1.0D, -yaw, -pitch, -prm.rotRoll);
/*  89 */     double tX = v.field_72450_a;
/*  90 */     double tZ = v.field_72449_c;
/*  91 */     double tY = v.field_72448_b;
/*  92 */     double dist = MathHelper.func_76133_a(tX * tX + tY * tY + tZ * tZ);
/*     */     
/*  94 */     if (this.worldObj.field_72995_K) {
/*     */       
/*  96 */       tX = tX * 100.0D / dist;
/*  97 */       tY = tY * 100.0D / dist;
/*  98 */       tZ = tZ * 100.0D / dist;
/*     */     }
/*     */     else {
/*     */       
/* 102 */       tX = tX * 150.0D / dist;
/* 103 */       tY = tY * 150.0D / dist;
/* 104 */       tZ = tZ * 150.0D / dist;
/*     */     } 
/*     */     
/* 107 */     Vec3d src = W_WorldFunc.getWorldVec3(this.worldObj, prm.user.field_70165_t, prm.user.field_70163_u, prm.user.field_70161_v);
/* 108 */     Vec3d dst = W_WorldFunc.getWorldVec3(this.worldObj, prm.user.field_70165_t + tX, prm.user.field_70163_u + tY, prm.user.field_70161_v + tZ);
/*     */     
/* 110 */     RayTraceResult m = W_WorldFunc.clip(this.worldObj, src, dst);
/*     */ 
/*     */     
/* 113 */     if (m != null && W_MovingObjectPosition.isHitTypeTile(m) && MCH_Lib.isBlockInWater(this.worldObj, m
/* 114 */         .func_178782_a().func_177958_n(), m.func_178782_a().func_177956_o(), m.func_178782_a().func_177952_p())) {
/*     */       
/* 116 */       if (!this.worldObj.field_72995_K) {
/*     */         
/* 118 */         double mx = (-MathHelper.func_76126_a(yaw / 180.0F * 3.1415927F) * MathHelper.func_76134_b(pitch / 180.0F * 3.1415927F));
/* 119 */         double mz = (MathHelper.func_76134_b(yaw / 180.0F * 3.1415927F) * MathHelper.func_76134_b(pitch / 180.0F * 3.1415927F));
/* 120 */         double my = -MathHelper.func_76126_a(pitch / 180.0F * 3.1415927F);
/*     */         
/* 122 */         mx = mx * (getInfo()).acceleration + prm.entity.field_70159_w;
/* 123 */         my = my * (getInfo()).acceleration + prm.entity.field_70181_x;
/* 124 */         mz = mz * (getInfo()).acceleration + prm.entity.field_70179_y;
/*     */         
/* 126 */         this.acceleration = MathHelper.func_76133_a(mx * mx + my * my + mz * mz);
/*     */         
/* 128 */         MCH_EntityTorpedo e = new MCH_EntityTorpedo(this.worldObj, prm.posX, prm.posY, prm.posZ, prm.entity.field_70159_w, prm.entity.field_70181_x, prm.entity.field_70179_y, yaw, 0.0F, this.acceleration);
/*     */ 
/*     */         
/* 131 */         e.setName(this.name);
/* 132 */         e.setParameterFromWeapon(this, prm.entity, prm.user);
/*     */         
/* 134 */         e.targetPosX = m.field_72307_f.field_72450_a;
/* 135 */         e.targetPosY = m.field_72307_f.field_72448_b;
/* 136 */         e.targetPosZ = m.field_72307_f.field_72449_c;
/* 137 */         e.field_70159_w = mx;
/* 138 */         e.field_70181_x = my;
/* 139 */         e.field_70179_y = mz;
/* 140 */         e.accelerationInWater = (getInfo() != null) ? (getInfo()).accelerationInWater : 1.0D;
/*     */         
/* 142 */         this.worldObj.func_72838_d((Entity)e);
/*     */         
/* 144 */         playSound(prm.entity);
/*     */       } 
/*     */       
/* 147 */       return true;
/*     */     } 
/* 149 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\weapon\MCH_WeaponTorpedo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */