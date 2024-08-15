/*    */ package mcheli.weapon;
/*    */ 
/*    */ import mcheli.wrapper.W_Entity;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ import net.minecraft.util.math.Vec3d;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MCH_WeaponAAMissile
/*    */   extends MCH_WeaponEntitySeeker
/*    */ {
/*    */   public MCH_WeaponAAMissile(World w, Vec3d v, float yaw, float pitch, String nm, MCH_WeaponInfo wi) {
/* 19 */     super(w, v, yaw, pitch, nm, wi);
/* 20 */     this.power = 12;
/* 21 */     this.acceleration = 2.5F;
/* 22 */     this.explosionPower = 4;
/* 23 */     this.interval = 5;
/* 24 */     if (w.field_72995_K)
/*    */     {
/* 26 */       this.interval += 5;
/*    */     }
/* 28 */     this.guidanceSystem.canLockInAir = true;
/* 29 */     this.guidanceSystem.ridableOnly = wi.ridableOnly;
/*    */   }
/*    */ 
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
/* 47 */     boolean result = false;
/* 48 */     if (!this.worldObj.field_72995_K) {
/*    */       
/* 50 */       Entity tgtEnt = prm.user.field_70170_p.func_73045_a(prm.option1);
/*    */       
/* 52 */       if (tgtEnt != null && !tgtEnt.field_70128_L)
/*    */       {
/* 54 */         playSound(prm.entity);
/*    */         
/* 56 */         float yaw = prm.entity.field_70177_z + this.fixRotationYaw;
/* 57 */         float pitch = prm.entity.field_70125_A + this.fixRotationPitch;
/* 58 */         double tX = (-MathHelper.func_76126_a(yaw / 180.0F * 3.1415927F) * MathHelper.func_76134_b(pitch / 180.0F * 3.1415927F));
/* 59 */         double tZ = (MathHelper.func_76134_b(yaw / 180.0F * 3.1415927F) * MathHelper.func_76134_b(pitch / 180.0F * 3.1415927F));
/* 60 */         double tY = -MathHelper.func_76126_a(pitch / 180.0F * 3.1415927F);
/* 61 */         MCH_EntityAAMissile e = new MCH_EntityAAMissile(this.worldObj, prm.posX, prm.posY, prm.posZ, tX, tY, tZ, yaw, pitch, this.acceleration);
/*    */ 
/*    */         
/* 64 */         e.setName(this.name);
/* 65 */         e.setParameterFromWeapon(this, prm.entity, prm.user);
/* 66 */         e.setTargetEntity(tgtEnt);
/*    */         
/* 68 */         this.worldObj.func_72838_d((Entity)e);
/* 69 */         result = true;
/*    */       }
/*    */     
/*    */     }
/* 73 */     else if (this.guidanceSystem.lock(prm.user)) {
/*    */       
/* 75 */       if (this.guidanceSystem.lastLockEntity != null) {
/*    */         
/* 77 */         result = true;
/* 78 */         this.optionParameter1 = W_Entity.getEntityId(this.guidanceSystem.lastLockEntity);
/*    */       } 
/*    */     } 
/*    */     
/* 82 */     return result;
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\weapon\MCH_WeaponAAMissile.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */