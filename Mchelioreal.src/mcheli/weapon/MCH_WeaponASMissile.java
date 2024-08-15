/*    */ package mcheli.weapon;
/*    */ 
/*    */ import mcheli.MCH_Lib;
/*    */ import mcheli.wrapper.W_MovingObjectPosition;
/*    */ import mcheli.wrapper.W_WorldFunc;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ import net.minecraft.util.math.RayTraceResult;
/*    */ import net.minecraft.util.math.Vec3d;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MCH_WeaponASMissile
/*    */   extends MCH_WeaponBase
/*    */ {
/*    */   public MCH_WeaponASMissile(World w, Vec3d v, float yaw, float pitch, String nm, MCH_WeaponInfo wi) {
/* 22 */     super(w, v, yaw, pitch, nm, wi);
/* 23 */     this.acceleration = 3.0F;
/* 24 */     this.explosionPower = 9;
/* 25 */     this.power = 40;
/* 26 */     this.interval = 65186;
/*    */     
/* 28 */     if (w.field_72995_K) {
/* 29 */       this.interval -= 10;
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isCooldownCountReloadTime() {
/* 35 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void update(int countWait) {
/* 41 */     super.update(countWait);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean shot(MCH_WeaponParam prm) {
/* 47 */     float yaw = prm.user.field_70177_z;
/* 48 */     float pitch = prm.user.field_70125_A;
/* 49 */     double tX = (-MathHelper.func_76126_a(yaw / 180.0F * 3.1415927F) * MathHelper.func_76134_b(pitch / 180.0F * 3.1415927F));
/* 50 */     double tZ = (MathHelper.func_76134_b(yaw / 180.0F * 3.1415927F) * MathHelper.func_76134_b(pitch / 180.0F * 3.1415927F));
/* 51 */     double tY = -MathHelper.func_76126_a(pitch / 180.0F * 3.1415927F);
/* 52 */     double dist = MathHelper.func_76133_a(tX * tX + tY * tY + tZ * tZ);
/*    */     
/* 54 */     if (this.worldObj.field_72995_K) {
/*    */       
/* 56 */       tX = tX * 200.0D / dist;
/* 57 */       tY = tY * 200.0D / dist;
/* 58 */       tZ = tZ * 200.0D / dist;
/*    */     }
/*    */     else {
/*    */       
/* 62 */       tX = tX * 250.0D / dist;
/* 63 */       tY = tY * 250.0D / dist;
/* 64 */       tZ = tZ * 250.0D / dist;
/*    */     } 
/*    */     
/* 67 */     Vec3d src = W_WorldFunc.getWorldVec3(this.worldObj, prm.entity.field_70165_t, prm.entity.field_70163_u + 1.62D, prm.entity.field_70161_v);
/* 68 */     Vec3d dst = W_WorldFunc.getWorldVec3(this.worldObj, prm.entity.field_70165_t + tX, prm.entity.field_70163_u + 1.62D + tY, prm.entity.field_70161_v + tZ);
/*    */ 
/*    */     
/* 71 */     RayTraceResult m = W_WorldFunc.clip(this.worldObj, src, dst);
/*    */ 
/*    */     
/* 74 */     if (m != null && W_MovingObjectPosition.isHitTypeTile(m) && !MCH_Lib.isBlockInWater(this.worldObj, m
/* 75 */         .func_178782_a().func_177958_n(), m.func_178782_a().func_177956_o(), m.func_178782_a().func_177952_p())) {
/*    */       
/* 77 */       if (!this.worldObj.field_72995_K) {
/*    */         
/* 79 */         MCH_EntityASMissile e = new MCH_EntityASMissile(this.worldObj, prm.posX, prm.posY, prm.posZ, tX, tY, tZ, yaw, pitch, this.acceleration);
/*    */ 
/*    */         
/* 82 */         e.setName(this.name);
/* 83 */         e.setParameterFromWeapon(this, prm.entity, prm.user);
/*    */         
/* 85 */         e.targetPosX = m.field_72307_f.field_72450_a;
/* 86 */         e.targetPosY = m.field_72307_f.field_72448_b;
/* 87 */         e.targetPosZ = m.field_72307_f.field_72449_c;
/* 88 */         this.worldObj.func_72838_d((Entity)e);
/*    */         
/* 90 */         playSound(prm.entity);
/*    */       } 
/*    */       
/* 93 */       return true;
/*    */     } 
/*    */     
/* 96 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\weapon\MCH_WeaponASMissile.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */