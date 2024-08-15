/*     */ package mcheli.weapon;
/*     */ 
/*     */ import mcheli.wrapper.W_Entity;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MCH_WeaponATMissile
/*     */   extends MCH_WeaponEntitySeeker
/*     */ {
/*     */   public MCH_WeaponATMissile(World w, Vec3d v, float yaw, float pitch, String nm, MCH_WeaponInfo wi) {
/*  19 */     super(w, v, yaw, pitch, nm, wi);
/*  20 */     this.power = 32;
/*  21 */     this.acceleration = 2.0F;
/*  22 */     this.explosionPower = 4;
/*  23 */     this.interval = -100;
/*  24 */     if (w.field_72995_K)
/*  25 */       this.interval -= 10; 
/*  26 */     this.numMode = 2;
/*     */     
/*  28 */     this.guidanceSystem.canLockOnGround = true;
/*  29 */     this.guidanceSystem.ridableOnly = wi.ridableOnly;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isCooldownCountReloadTime() {
/*  35 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/*  41 */     String opt = "";
/*  42 */     if (getCurrentMode() == 1)
/*  43 */       opt = " [TA]"; 
/*  44 */     return super.getName() + opt;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void update(int countWait) {
/*  50 */     super.update(countWait);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean shot(MCH_WeaponParam prm) {
/*  56 */     if (this.worldObj.field_72995_K)
/*     */     {
/*  58 */       return shotClient(prm.entity, prm.user);
/*     */     }
/*  60 */     return shotServer(prm);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean shotClient(Entity entity, Entity user) {
/*  65 */     boolean result = false;
/*     */     
/*  67 */     if (this.guidanceSystem.lock(user))
/*     */     {
/*  69 */       if (this.guidanceSystem.lastLockEntity != null) {
/*     */         
/*  71 */         result = true;
/*  72 */         this.optionParameter1 = W_Entity.getEntityId(this.guidanceSystem.lastLockEntity);
/*     */       } 
/*     */     }
/*     */     
/*  76 */     this.optionParameter2 = getCurrentMode();
/*     */     
/*  78 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean shotServer(MCH_WeaponParam prm) {
/*  83 */     Entity tgtEnt = null;
/*     */     
/*  85 */     tgtEnt = prm.user.field_70170_p.func_73045_a(prm.option1);
/*     */     
/*  87 */     if (tgtEnt == null || tgtEnt.field_70128_L)
/*     */     {
/*  89 */       return false;
/*     */     }
/*     */     
/*  92 */     float yaw = prm.user.field_70177_z + this.fixRotationYaw;
/*  93 */     float pitch = prm.entity.field_70125_A + this.fixRotationPitch;
/*  94 */     double tX = (-MathHelper.func_76126_a(yaw / 180.0F * 3.1415927F) * MathHelper.func_76134_b(pitch / 180.0F * 3.1415927F));
/*  95 */     double tZ = (MathHelper.func_76134_b(yaw / 180.0F * 3.1415927F) * MathHelper.func_76134_b(pitch / 180.0F * 3.1415927F));
/*  96 */     double tY = -MathHelper.func_76126_a(pitch / 180.0F * 3.1415927F);
/*     */     
/*  98 */     MCH_EntityATMissile e = new MCH_EntityATMissile(this.worldObj, prm.posX, prm.posY, prm.posZ, tX, tY, tZ, yaw, pitch, this.acceleration);
/*     */ 
/*     */     
/* 101 */     e.setName(this.name);
/* 102 */     e.setParameterFromWeapon(this, prm.entity, prm.user);
/* 103 */     e.setTargetEntity(tgtEnt);
/* 104 */     e.guidanceType = prm.option2;
/*     */     
/* 106 */     this.worldObj.func_72838_d((Entity)e);
/*     */     
/* 108 */     playSound(prm.entity);
/*     */     
/* 110 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\weapon\MCH_WeaponATMissile.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */